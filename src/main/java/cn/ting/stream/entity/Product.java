package cn.ting.stream.entity;

import cn.ting.stream.constant.ProductCategory;
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
public class Product {
	/** 商品SKU（唯一商品编码） */
	private Long id;

	/** 商品展示名称（前端可见） */
	private String name;

	/**
	 * 商品分类（影响促销策略）
	 * @see ProductCategory
	 */
	private ProductCategory category;

	/**
	 * 商品单价（单位：元，精度：小数点后2位）
	 * @rule 价格变更需同步更新历史订单快照
	 */
	private Double price;

	/**
	 * 库存数量（实时库存）
	 * @rule 下单时需预扣库存，订单取消后恢复
	 * @alert 库存 ≤ 100 时触发预警
	 */
	private Integer stock;
}
