package cn.ting.qt;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Test;

/**
 * @author : lvyiting
 * @date : 2025-04-26
 **/
public class Qt {


	/**
	 * 知识点：String->Double
	 * Double.valueOf(str)
	 */
	@Test
	public void test1() {
		String str = "123,456";
		int i = str.indexOf(",");
		System.out.println(i);
		System.out.println(Double.valueOf(str.substring(0, i)));
	}

	@Test
	public void test2() {
		// 创建 TreeMap 实例
		TreeMap<Integer, String> treeMap = new TreeMap<>();

		// 添加键值对
		treeMap.put(3, "C");
		treeMap.put(1, "A");
		treeMap.put(4, "D");
		treeMap.put(2, "B");

		//遍历TreeMap（按键的自然顺序）
		for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
		System.out.println("-----------------------------------------");
		// 获取最小键和最大键
		System.out.println("First Key: " + treeMap.firstKey());
		System.out.println("Last Key: " + treeMap.lastKey());
		System.out.println("-----------------------------------------");
		// 检查键是否存在
		System.out.println("Contains key 2? " + treeMap.containsKey(2));
		System.out.println("-----------------------------------------");
		// 获取子映射
		SortedMap<Integer, String> subMap = treeMap.subMap(2, 5);
		System.out.println("SubMap (2-5):");
		for (Map.Entry<Integer, String> entry : subMap.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
		System.out.println("-----------------------------------------");
		// 查找小于等于 2 的最大键
		System.out.println("Floor key for 2: " + treeMap.floorKey(2));
		// 查找大于等于 3 的最小键
		System.out.println("Ceiling key for 3: " + treeMap.ceilingKey(3));
	}
}
