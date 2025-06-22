package cn.ting.current.entity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * @author : lvyiting
 * @date : 2025-06-22
 **/
@Data
public class JobDetailPage {
	private final JobBasic basic;
	private final CompanyInfo company;
	private final List<InterviewReview> reviews;
	private final List<SimilarJob> similarJobs;
	private final JobStats stats;



	@Override
	public String toString() {
		String companyName = company != null ? company.getName() : "N/A";
		double companyRating = company != null ? company.getRating() : 0.0;
		String companyIndustry = company != null ? company.getIndustry() : "N/A";

		String reviewsStr = "暂无评价";
		if (reviews != null && !reviews.isEmpty()) {
			reviewsStr = reviews.stream()
					.map(r -> r.getComment() + "(" + r.getRating() + "星)")
					.collect(Collectors.joining(", "));
		}

		String similarJobsStr = "无推荐";
		if (similarJobs != null && !similarJobs.isEmpty()) {
			similarJobsStr = similarJobs.stream()
					.map(j -> j.getTitle() + "(" + j.getSalary() + ")")
					.collect(Collectors.joining(", "));
		}

		int viewCount = stats != null ? stats.getViewCount() : 0;
		int applyRate = stats != null ? stats.getApplyRate() : 0;

		return String.format("======== 职位详情 [ID:%d] ========\n" +
						"\t\t\t\t职位: %s | 薪资: %d元\n" +
						"\t\t\t\t公司: %s | 评分: %.1f | 行业: %s\n" +
						"\t\t\t\t- 面试评价: %s\n" +
						"\t\t\t\t- 相似职位: %s\n" +
						"\t\t\t\t- 访问量: %d次 | 申请率: %d%%",
				basic.getId(),
				basic.getTitle(),
				basic.getSalary(),
				companyName,
				companyRating,
				companyIndustry,
				reviewsStr,
				similarJobsStr,
				viewCount,
				applyRate
		);
	}
}
