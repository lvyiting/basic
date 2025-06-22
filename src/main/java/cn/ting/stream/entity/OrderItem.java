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
public class OrderItem {
	/**
	 * 关联商品信息（商品快照，下单后不可修改）
	 * @see Product
	 */
	private Product product;

	/**
	 * 购买数量（必须大于0）
	 * @rule 创建时校验 quantity ≥ 1
	 */
	private Integer quantity;

	/**
	 * 折扣率（基于客户等级的动态计算值）
	 * @range 0.0 ≤ discount ≤ 1.0 (0.9 表示9折)
	 * @example 0.8 → 商品价格 × 0.8
	 */
	private Double discount;
}
