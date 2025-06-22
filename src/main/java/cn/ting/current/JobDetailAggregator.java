package cn.ting.current;

import cn.ting.current.entity.CompanyInfo;
import cn.ting.current.entity.InterviewReview;
import cn.ting.current.entity.JobBasic;
import cn.ting.current.entity.JobDetailPage;
import cn.ting.current.entity.JobStats;
import cn.ting.current.entity.SimilarJob;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author : lvyiting
 * @date : 2025-06-22
 **/
public class JobDetailAggregator {
	// 线程池配置
	private static final ExecutorService ASYNC_POOL = Executors.newFixedThreadPool(8);

	public JobDetailPage loadJobDetail(long jobId) {
		// 0. 开始计时
		long startTime = System.currentTimeMillis();

		// 1. 获取基础职位信息（必须首先获取）
		CompletableFuture<JobBasic> basicFuture = DataService.getJobBasic(jobId)
				.exceptionally(ex -> {
					System.err.println("基础数据获取失败: " + ex.getMessage());
					return new JobBasic(jobId, "职位获取失败", 0, "");
				});

		// 2. 依赖基础数据的并行任务
		CompletableFuture<JobDetailPage> resultFuture = basicFuture.thenCompose(basic -> {
			// 2.1 并行获取：公司信息 + 面试评价 + 统计数据
			CompletableFuture<CompanyInfo> companyFuture = DataService.getCompanyInfo(1001)
					.exceptionally(ex -> {
						System.err.println("公司信息获取失败: " + ex.getMessage());
						return new CompanyInfo(1001, "未知公司", 0.0, "");
					});

			CompletableFuture<List<InterviewReview>> reviewsFuture = DataService.getInterviewReviews(jobId)
					.exceptionally(ex -> {
						System.err.println("评价获取失败: " + ex.getMessage());
						return Collections.emptyList();
					});

			CompletableFuture<JobStats> statsFuture = DataService.getJobStats(jobId)
					.exceptionally(ex -> {
						System.err.println("统计获取失败: " + ex.getMessage());
						return new JobStats(jobId, 0, 0);
					});

			// 2.2 依赖公司信息的任务：相似职位推荐
			CompletableFuture<List<SimilarJob>> similarJobsFuture = companyFuture
					.thenComposeAsync(company ->
									DataService.getSimilarJobs(company.getIndustry())
											.exceptionally(ex -> {
												System.err.println("推荐获取失败: " + ex.getMessage());
												return Collections.emptyList();
											}),
							ASYNC_POOL
					);

			// 3. 聚合所有结果
			return CompletableFuture.allOf(
							companyFuture, reviewsFuture, similarJobsFuture, statsFuture
					)
					.thenApplyAsync(v -> {
						// 注意：这里join不会阻塞，因为allOf确保已完成
						return new JobDetailPage(
								basic,
								companyFuture.join(),
								reviewsFuture.join(),
								similarJobsFuture.join(),
								statsFuture.join()
						);
					}, ASYNC_POOL);
		});

		// 4. 设置超时与回退
		try {
			return resultFuture.get(500, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			System.err.println("⚠️ 聚合超时，返回部分数据");
			return buildFallbackPage(basicFuture.getNow(null), jobId);
		} catch (Exception e) {
			System.err.println("❌ 聚合失败: " + e.getMessage());
			return buildFallbackPage(basicFuture.getNow(null), jobId);
		} finally {
			System.out.printf("⏱️ 总耗时: %dms\n",
					System.currentTimeMillis() - startTime);
		}
	}

	private JobDetailPage buildFallbackPage(JobBasic basic, long jobId) {
		return new JobDetailPage(
				basic != null ? basic : new JobBasic(jobId, "加载中...", 0, ""),
				null,
				null,
				null,
				null
		);
	}

	public static void main(String[] args) {
		cn.ting.current.JobDetailAggregator aggregator = new cn.ting.current.JobDetailAggregator();

		// 模拟连续5次请求（展示不同场景）
		for (int i = 0; i < 5; i++) {
			System.out.println("\n===== 请求 #" + (i + 1) + " =====");
			JobDetailPage page = aggregator.loadJobDetail(101);
			System.out.println(page.toString());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		ASYNC_POOL.shutdown();
	}
}

