package cn.ting.p1.stream;

import cn.ting.p1.stream.constant.OrderStatus;
import cn.ting.p1.stream.constant.ProductCategory;
import cn.ting.p1.stream.entity.Address;
import cn.ting.p1.stream.entity.Customer;
import cn.ting.p1.stream.entity.Order;
import cn.ting.p1.stream.entity.OrderItem;
import cn.ting.p1.stream.entity.Product;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

import static cn.ting.p1.stream.constant.OrderStatus.SHIPPED;

/**
 * 这是一个java8新特性---------stream练习
 *
 * @author : lvyiting
 * @date : 2025-04-26
 **/
public class Stream {

	private static final Product apple = new Product(101L, "红富士苹果", ProductCategory.FOOD, 8.5, 200);
	private static final Product milk = new Product(102L, "特仑苏牛奶", ProductCategory.FOOD, 68.0, 50);
	private static final Product laptop = new Product(201L, "MacBook Pro", ProductCategory.ELECTRONICS, 14999.0, 10);
	private static final Product book = new Product(301L, "Java编程思想", ProductCategory.BOOKS, 99.0, 100);

	private static final List<Address> customer1Addresses = Arrays.asList(
			new Address("上海市", "200000", true),   // 主要地址
			new Address("杭州市", "310000", false)   // 备用地址
	);

	private static final List<Address> customer2Addresses = Arrays.asList(
			new Address("北京市", "100000", true)
	);

	private static final List<Address> customer3Addresses = Arrays.asList(
			new Address("深圳市", "518000", false),  // 异常数据：没有主要地址
			new Address("广州市", "510000", false)
	);

	public static final List<Order> orders = Arrays.asList(
			// 客户1（黄金会员）的订单
			new Order(1001L,
					new Customer(1L, "张三", 3, customer1Addresses),  // 黄金会员
					LocalDate.parse("2023-09-01"),
					Arrays.asList(
							new OrderItem(apple, 2, 0.1),   // 苹果 ×2，9折（折扣率0.1）
							new OrderItem(laptop, 1, 0.2)   // 笔记本 ×1，8折（非食品类）
					),
					OrderStatus.DELIVERED
			),

			// 客户2（钻石会员）的订单
			new Order(1002L,
					new Customer(2L, "李四", 5, customer2Addresses),  // 钻石会员
					LocalDate.parse("2023-09-02"),
					Arrays.asList(
							new OrderItem(milk, 5, 0.0)    // 牛奶 ×5，无折扣
					),
					OrderStatus.SHIPPED
			),

			// 客户3（普通会员）的订单
			new Order(1003L,
					new Customer(3L, "王五", 1, customer3Addresses),  // 普通会员（注意地址没有主要地址）
					LocalDate.parse("2023-09-03"),
					Arrays.asList(
							new OrderItem(apple, 4, 0.5),   // 苹果 ×4，5折
							new OrderItem(book, 3, 0.1)     // 图书 ×3，9折（非食品类）
					),
					OrderStatus.PENDING
			)
	);


	/**
	 * 知识点：
	 * mapToDouble的应用
	 * <p>
	 * 找出 上海地区 会员等级≥3的客户，在 2023年 下单 金额超过¥5000 的 所有订单ID
	 * Order->List<OrderItem>->Product(price)、discount
	 */
	@Test
	public void test1() {
		List<Long> collect = orders
				.stream()
				.filter(index ->
						index.getCustomer().getAddresses().stream()
								.anyMatch(i -> "上海".equals(i.getCity()) && i.getIsPrimary()))
				.filter(index -> index.getCustomer().getTier() >= 3)
				.filter(index -> index.getOrderDate().getYear() == 2023)
				.filter(index -> index.getItems().stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity() * i.getDiscount()).sum() > 5000)
				.map(Order::getId)
				.collect(Collectors.toList());
	}

	/**
	 * 知识点：
	 * list.contains和anyMatch的区别
	 */
	@Test
	public void test2() {
		List<String> list = Arrays.asList("a/d", "b/e", "c/f");
		//false
		System.out.println(list.contains("a"));
		//true
		System.out.println(list.stream().anyMatch(index -> index.contains("a")));
	}

	/**
	 * 知识点：
	 * flatMap是传递stream参数
	 * 获取所有已发货（SHIPPED）订单中电子产品（ELECTRONICS）的商品名称集合（去重）
	 */
	@Test
	public void test3() {
		List<Product> collect = orders.stream().filter(index -> index.getStatus().equals(SHIPPED))
				.flatMap(index -> index.getItems().stream())
				.filter(index -> index.getProduct().getCategory().equals(ProductCategory.ELECTRONICS))
				.map(OrderItem::getProduct)
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * 计算  每个客户  购买食品类（FOOD）商品的数量
	 */
	@Test
	public void test4() {
		Map<Long, Double> customerFoodTotal = orders.stream()
				.collect(Collectors.groupingBy(
						o -> o.getCustomer().getId(),
						Collectors.summingDouble(o -> o.getItems().stream()
								.filter(item -> item.getProduct().getCategory() == ProductCategory.FOOD)
								.mapToDouble(item -> item.getQuantity())
								.sum()
						)));
		System.out.println(customerFoodTotal);
	}

	/**
	 * 按 城市 分组，统计每个城市-非主地址- 用户中最畅销的-商品类别-前3
	 */
	@Test
	public void test5() {
		Map<String, List<ProductCategory>> cityTopCategories = orders.stream()
				.filter(o -> o.getCustomer().getAddresses().stream()
						.anyMatch(addr -> !addr.getIsPrimary()))
				.flatMap(o -> o.getItems().stream()
						.map(item -> new AbstractMap.SimpleEntry<>(
								o.getCustomer().getAddresses().stream()
										.filter(addr -> !addr.getIsPrimary())
										.findFirst()
										.map(Address::getCity)
										.orElse("Unknown"),
								item.getProduct().getCategory())))
				.filter(e -> e.getKey() != null)
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						Collectors.collectingAndThen(
								Collectors.toList(),
								list -> list.stream()
										.collect(Collectors.groupingBy(
												Map.Entry::getValue,
												Collectors.counting()))
										.entrySet().stream()
										.sorted(Map.Entry.<ProductCategory, Long>comparingByValue().reversed())
										.limit(3)
										.map(Map.Entry::getKey)
										.collect(Collectors.toList())
						)));
		System.out.println(cityTopCategories);
	}
}
