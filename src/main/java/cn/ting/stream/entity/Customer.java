package cn.ting.stream.entity;

import java.util.List;
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
public class Customer {
	/** 客户唯一标识（加密存储） */
	private Long id;

	/**
	 * 客户姓名（显示名称，支持特殊字符）
	 * @example "张*三", "John_Doe"
	 */
	private String name;

	/**
	 * 会员等级（数值型分级体系）
	 * @value 1-5 (1:普通会员, 3:黄金会员, 5:钻石会员)
	 * @rule 等级影响折扣计算 @see OrderItem#discount
	 */
	private Integer tier;

	/**
	 * 收货地址列表（至少包含1个主要地址）
	 * @see Address
	 * @rule 必须存在且仅有一个 isPrimary=true 的地址
	 */
	private List<Address> addresses;
}
