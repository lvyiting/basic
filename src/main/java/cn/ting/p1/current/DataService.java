package cn.ting.p1.current;

import cn.ting.p1.current.entity.CompanyInfo;
import cn.ting.p1.current.entity.InterviewReview;
import cn.ting.p1.current.entity.JobBasic;
import cn.ting.p1.current.entity.JobStats;
import cn.ting.p1.current.entity.SimilarJob;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class DataService {
	public static CompletableFuture<JobBasic> getJobBasic(long jobId) {
		return CompletableFuture.supplyAsync(() -> {
			sleepRandom(50, 100);
			return new JobBasic(
					jobId,
					"Java高级工程师",
					30000,
					"负责招聘平台后端架构开发"
			);
		});
	}

	// 模拟公司信息（HTTP服务）
	public static CompletableFuture<CompanyInfo> getCompanyInfo(long companyId) {
		return CompletableFuture.supplyAsync(() -> {
			sleepRandom(80, 150);
			// 模拟10%概率的服务失败
			if (Math.random() < 0.1) {
				throw new RuntimeException("公司服务不可用");
			}
			return new CompanyInfo(
					companyId,
					"字节跳动",
					4.8,
					"计算机"
			);
		});
	}

	// 模拟面试评价（HTTP服务）
	public static CompletableFuture<List<InterviewReview>> getInterviewReviews(long jobId) {
		return CompletableFuture.supplyAsync(() -> {
			sleepRandom(100, 200);
			return Arrays.asList(
					new InterviewReview("面试官专业", 4),
					new InterviewReview("算法题较难", 3)
			);
		});
	}

	// 模拟相似职位推荐（需要公司行业信息）
	public static CompletableFuture<List<SimilarJob>> getSimilarJobs(String industry) {
		return CompletableFuture.supplyAsync(() -> {
			sleepRandom(120, 250);
			return Arrays.asList(
					new SimilarJob(201, "Java架构师", 40000,"计算机"),
					new SimilarJob(202, "后端技术专家", 45000,"计算机"),
					new SimilarJob(203, "web技术专家", 9000,"互联网")
			).stream().filter(job -> job.getIndustry().equals(industry)).collect(Collectors.toList());
		});
	}

	// 模拟职位统计（缓存服务）
	public static CompletableFuture<JobStats> getJobStats(long jobId) {
		return CompletableFuture.supplyAsync(() -> {
			sleepRandom(30, 60);
			return new JobStats(jobId, 1520, 85);
		});
	}

	private static void sleepRandom(int min, int max) {
		try {
			Thread.sleep(min + (int)(Math.random() * (max - min)));
		} catch (InterruptedException ignored) {}
	}
}



// ================= 服务聚合类 =================

