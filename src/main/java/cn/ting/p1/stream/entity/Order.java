package cn.ting.p1.stream.entity;

import cn.ting.p1.stream.constant.OrderStatus;
import java.time.LocalDate;
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
public class Order {
	/** 订单唯一ID (数据库自增主键) */
	private Long id;

	/**
	 * 关联客户对象
	 * @see Customer
	 */
	private Customer customer;

	/**
	 * 下单日期（业务规则：不可晚于当前日期）
	 * @example 2023-09-15
	 */
	private LocalDate orderDate;

	/**
	 * 订单项列表（至少包含1个有效商品）
	 * @see OrderItem
	 * @rule 创建订单时必须校验 items 非空
	 */
	private List<OrderItem> items;

	/**
	 * 订单状态（状态流转规则：PENDING → SHIPPED → DELIVERED）
	 * @see OrderStatus
	 */
	private OrderStatus status;
}
