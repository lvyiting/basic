package cn.ting.p2;

import java.util.HashMap;

import org.junit.Test;

/**
 * computeIfAbsent方法练习
 *
 * @author : lvyiting
 * @date : 2025/10/30
 **/
public class Practice1 {

	/**
	 * 测试computeIfAbsent方法
	 * 存在则不处理直接返回
	 * 不存在则新增并返回
	 */
	@Test
	public void test1() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		String s1 = map.computeIfAbsent("1", k -> k + "进化");
		String s2 = map.computeIfAbsent("3", k -> k+"进化");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(map);
	}

	/**
	 * 测试computeIfPresent方法
	 * 存在则处理并返回
	 * 不存在则不处理直接返回null
	 */
	@Test
	public void test2() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		String s1 = map.computeIfPresent("1", (k, v) -> k +v+ "进化");
		String s2 = map.computeIfPresent("3", (k, v) -> k+v+"进化");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(map);
	}

	/**
	 * 测试compute方法
	 * 存在则处理并返回
	 * 不存在也处理并返回
	 */
	@Test
	public void test3() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		String s1 = map.compute("1", (k, v) -> k +v+ "进化");
		String s2 = map.compute("3", (k, v) -> k+v+"进化");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(map);
	}

	/**
	 * 测试merge方法
	 * 存在则处理并返回
	 * 不存在则新增并返回
	 */
	@Test
	public void test4() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		String s1 = map.merge("1", "进化", (oldValue, newValue) -> oldValue + newValue);
		String s2 = map.merge("3", "进化", (oldValue, newValue) -> oldValue + newValue);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(map);
	}

	/**
	 * 测试getOrDefault方法
	 * 存在则返回对应值
	 * 不存在则返回默认值
	 */
	@Test
	public void test5() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		System.out.println(map.getOrDefault("1", "不存在"));
		System.out.println(map.getOrDefault("3", "不存在"));
	}

	/**
	 * 测试putIfAbsent方法
	 * 存在则不处理直接返回对应值
	 * 不存在则新增并返回
	 * 应用场景：不涉及函数式表达的新增操作
	 */
	@Test
	public void test6() {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "原始");
		map.put("2", "原始");
		String s1 = map.putIfAbsent("1", "进化");
		String s2 = map.putIfAbsent("3", "进化");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(map);
	}
}
