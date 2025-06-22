package cn.ting.current.entity;

import lombok.Data;

/**
 * 职位统计信息
 *
 * @author : lvyiting
 * @date : 2025-06-22
 **/
@Data
public class JobStats {
	/**
	 * 职位ID
	 */
	private final long jobId;
	/**
	 * 职位浏览次数
	 */
	private final int viewCount;
	/**
	 * 职位申请率
	 */
	private final int applyRate;

}
