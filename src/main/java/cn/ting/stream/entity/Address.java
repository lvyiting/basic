package cn.ting.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lvyiting
 * @date : 2025-04-26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	/**
	 * 城市名称（支持国际城市）
	 * @example "上海市", "New York"
	 */
	private String city;

	/**
	 * 邮政编码（格式校验）
	 * @pattern 中国大陆：\d{6}, 美国：\d{5}(-\d{4})?
	 */
	private String zipCode;

	/**
	 * 是否主要地址（每个客户有且仅有一个主要地址）
	 * @rule 修改主要地址时需同步更新旧地址状态
	 */
	private Boolean isPrimary;
}
