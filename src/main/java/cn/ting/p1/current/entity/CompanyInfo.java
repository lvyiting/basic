package cn.ting.p1.current.entity;

import lombok.Data;

/**
 * 公司信息
 *
 * @author : lvyiting
 * @date : 2025-06-22
 **/
@Data
public class CompanyInfo {
	/**
	 * 公司id
	 */
	private final long id;
	/**
	 * 公司名称
	 */
	private final String name;
	/**
	 * 公司评分
	 */
	private final double rating;
	/**
	 * 公司行业
	 */
	private final String industry;
}
