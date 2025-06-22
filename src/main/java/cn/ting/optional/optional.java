package cn.ting.optional;

import cn.ting.optional.entity.Address;
import cn.ting.optional.entity.Order;
import cn.ting.optional.entity.User;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * optional练习
 *
 * @author : lvyiting
 * @date : 2025-04-26
 **/
public class optional {

	/**
	 * 为空时返回默认值
	 */
	@Test
	public void test1() {
		List<String> list = Arrays.asList("a", "b", "c");
		List<String> strings = Optional.ofNullable(list).orElse(null);
		System.out.println(strings);
	}

	/**
	 * 安全获取用户的邮政编码，不存在则返回 "000000"
	 */
	@Test
	public void test2() {
		User user = new User("Alice", Arrays.asList(new Address("123456", "成都", true)));
//		String postCode = Optional.ofNullable(user)
//				.map(User::getAddresses)
//		        .flatMap(Address::getPostCode)
//				.orElse("000000");
//		System.out.println(postCode);
	}

	/**
	 * 存在订单时发送邮件，否则不执行任何操作
	 */
	@Test
	public void test3() {
		User user = new User("Alice", Arrays.asList(new Address("123456", "成都", true)));

		Order order = new Order(1, user);
		Optional.ofNullable(order).ifPresent(System.out::println);
	}

	/**
	 * 嵌套 Optional 解包
	 */
	@Test
	public void test4() {
		Optional<Optional<String>> nestedOpt = Optional.of(Optional.of("deep"));
		String empty = nestedOpt.flatMap(Function.identity()).orElse("empty");
		System.out.println(empty);
	}

	/**
	 * 要求：若值大于 40，则转成十六进制字符串；否则返回空 Optional
	 */
	@Test
	public void test5() {
		String result = Optional.of(42)
				.filter(i -> i > 40)
				.map(Integer::toHexString)
				.get();
		System.out.println(result);
	}

	/**
	 * 要求：提取所有非空值并求和
	 */
	@Test
	public void test6() {
		List<Optional<Integer>> list = Arrays.asList(Optional.of(1), Optional.empty(), Optional.of(3));
		int sum = list.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.mapToInt(Integer::intValue)
				.sum();
		System.out.println(sum);
	}

	/**
	 * 要求：Optional 优雅地获取 user 的 email 字段（user 可能为 null，email 也可能为 null），并返回 email 的小写形式；如果 email 为空，则返回 “unknown”。
	 */
	@Test
	public void test7() {
		User user = new User("Alice", Arrays.asList(new Address("123456", "成都", true)));
		Optional.ofNullable(user)
				.map(User::getName)
				.map(String::toLowerCase)
				.orElse("unknown");
	}

	/**
	 * 要求：返回所有以 “A” 开头并且长度大于2的用户名列表（如果 userNames 为空则返回空表）。
	 */
	@Test
	public void test8() {
		List<String> userNames = Lists.newArrayList("Alice", "Bob", "Charlie", "David", "Aw");
		List<String> filtered = Optional.ofNullable(userNames)
				.orElseGet(Collections::emptyList)
				.stream()
				.filter(n -> n.startsWith("A") && n.length() > 2)
				.collect(Collectors.toList());
		filtered.forEach(System.out::println);
	}

	/**
	 * 要求：用 Optional 链式写法优雅地获取 order 的 city 字段，如果任一层为 null 则返回 “未知城市”。
	 */
	@Test
	public void test9() {
//		User user = new User("Alice", Arrays.asList(new Address("123456", "成都")));
//		String city = Optional.ofNullable(user)
//				.map(User::getAddresses)
//				.map(Address::getCity)
//				.orElse("未知城市");
//		System.out.println(city);
	}

	/**
	 * 要求：你需要把字符串转成整数（可能抛异常），再加100返回，如果转换失败则返回-1。
	 */
	@Test
	public void test10() {
		String str = "123a";
		Integer integer = Optional.ofNullable(str)
				.map(p -> {
					try {
						return Integer.parseInt(str);
					} catch (NumberFormatException e) {
						return null;
					}
				})
				.map(i -> i + 100)
				.orElse(-1);
		System.out.println(integer);
	}

	/**
	 * 要求：返回key=“score” 对应的 List 里的最大值（如果没有该key或列表为空则返回-1）。
	 */
	@Test
	public void test11() {
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("score", Arrays.asList(2, 4, 6));
		System.out.println(Optional.ofNullable(map)
				.map(m -> m.get("score"))
				.orElseGet(Collections::emptyList)
				.stream()
				.max(Integer::compareTo)
				.orElse(-1));
	}

	/**
	 * 获取订单中客户的主要地址城市名称，如果客户没有主要地址或订单为空，返回 "Unknown"
	 */
	@Test
	public void test12() {
		User user = new User("Alice", Arrays.asList(new Address("123456", "成都", false)));
		String city = Optional.ofNullable(user)
				.map(User::getAddresses)
				.map(list -> list.stream()
						.filter(Address::getIsPrimary)
						.findFirst()
						.orElse(null))
				.map(Address::getCity)
				.orElse("Unknown");
		System.out.println(city);
	}

	/**
	 * 从订单列表中获取所有用户的主要地址的邮编，去重
	 * 如果邮编为空，使用默认值 "DEFAULT_POSTCODE"
	 */
	@Test
	public void test13() {
		User user = new User("Alice", Arrays.asList(new Address("123456", "成都", true)));
		List<Order> orders = Arrays.asList(new Order(1, user));
		Set<String> postCodes = orders.stream()
				.map(Order::getUser) // 可能为null
				.filter(Objects::nonNull)
				.map(User::getAddresses) // 可能为null
				.filter(Objects::nonNull)
				.flatMap(List::stream)
				.filter(addr -> Boolean.TRUE.equals(addr.getIsPrimary()))
				.map(Address::getPostCode) // 可能为null
				.filter(Objects::nonNull)
				.map(postCode -> postCode.isEmpty() ? "DEFAULT_POSTCODE" : postCode)
				.collect(Collectors.toSet());
		System.out.println(postCodes);
	}

	/**
	 * 使用并行流验证所有订单是否全部满足：
	 *
	 * 订单ID为奇数
	 * 用户名称包含字母 "a"
	 * 主要地址城市以 "S" 开头
	 */
	@Test
	public void test14() {
		User user = new User("alice", Arrays.asList(new Address("123456", "s成都", true)));
		List<Order> orders = Arrays.asList(new Order(1, user));
		boolean b = orders.parallelStream().allMatch(order -> {
			if (order.getId() == null || order.getId() % 2 != 1) {
				return false;
			}
			if (!order.getUser().getName().contains("a")) {
				return false;
			}
			return order.getUser().getAddresses()
					.stream()
					.filter(Objects::nonNull)
					.filter(Address::getIsPrimary)
					.anyMatch(i -> i.getCity().startsWith("s"));
		});
		System.out.println(b);
	}


}
