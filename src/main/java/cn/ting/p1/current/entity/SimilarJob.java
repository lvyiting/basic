package cn.ting.p1.current.entity;

import lombok.Data;

/**
 * 相似职位
 *
 * @author : lvyiting
 * @date : 2025-06-22
 **/
@Data
public class SimilarJob {
	/**
	 * 职位ID
	 */
	private final long id;
	/**
	 * 职位名称
	 */
	private final String title;
	/**
	 * 职位薪资
	 */
	private final int salary;
	/**
	 * 公司行业
	 */
	private final String industry;
}
