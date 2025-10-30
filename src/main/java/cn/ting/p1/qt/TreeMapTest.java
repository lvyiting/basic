package cn.ting.p1.qt;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * @author : lvyiting
 * @date : 2025/06/28
 **/
public class TreeMapTest {
	private static final TreeMap<Integer, String> treeMap = new TreeMap<>();

	static {
		treeMap.put(1, "one");
		treeMap.put(2, "two");
		treeMap.put(3, "three");
		treeMap.put(5, "four");
		treeMap.put(6, "five");
	}

	@Test
	public void test1() {
		//firstKey() / lastKey()
		/*
		firstKey()：返回当前映射中最小的key。
		lastKey()：返回当前映射中最大的key。
		 */
		System.out.println("========firstKey() / lastKey()===========================");
		System.out.println(treeMap.lastKey());
		System.out.println(treeMap.firstKey());
		System.out.println("============================");

		//ceilingEntry() / floorEntry()
		/*
		ceilingEntry(K key)：返回大于等于key的最小Entry（Map.Entry），找不到则返回null。
		floorEntry(K key)：返回小于等于key的最大Entry，找不到则返回null。
		*/
		System.out.println("========ceilingEntry() / floorEntry()===========================");
		System.out.println(treeMap.ceilingEntry(4));
		System.out.println(treeMap.floorEntry(4));
		System.out.println("============================");

		//subMap() / headMap() / tailMap()
		/*
		subMap(fromKey, toKey)：返回从fromKey（包含）到toKey（不包含）之间的子Map。
		headMap(toKey)：返回小于toKey的所有键值对的子Map。
		tailMap(fromKey)：返回大于等于fromKey的所有键值对的子Map。
		*/
		System.out.println("========subMap() / headMap() / tailMap()===========================");
		System.out.println(treeMap.subMap(2, 5));
		System.out.println(treeMap.headMap(3));
		System.out.println(treeMap.tailMap(3, false));

		//higherEntry() / lowerEntry()
		/*
		higherEntry(K key)：返回严格大于key的最小Entry，找不到则返回null。
		lowerEntry(K key)：返回严格小于key的最大Entry，找不到则返回null。
		*/
		System.out.println("========higherEntry() / lowerEntry()===========================");
		System.out.println(treeMap.higherEntry(6));
		System.out.println(treeMap.lowerEntry(4));
	}

	/**
	 * 某学校组织了一次数学考试，统计了所有学生的成绩。现有一个List<Integer>，每个元素是一个学生的分数。
	 * 请用TreeMap统计并输出每个“分数段”内的人数，分数段为：[0,59], [60,69], [70,79], [80,89], [90,100]。
	 * 输出时，分数段要按从低到高顺序。
	 */
	@Test
	public void test2() {
		int size = 20;
		ArrayList<Integer> scores = Lists.newArrayListWithCapacity(size);
		for (int i = 0; i < size; i++) {
			int i1 = ThreadLocalRandom.current().nextInt(10, 100);
			scores.add(i1);
		}
		System.out.println(scores);
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>() {
			{
				put(0, 0);
				put(60, 0);
				put(70, 0);
				put(80, 0);
				put(90, 0);
			}
		};
		for (Integer score : scores) {
			//a>=score a取最大 38 0
			Map.Entry<Integer, Integer> integerIntegerEntry = treeMap.floorEntry(score);
			treeMap.put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue() + 1);
		}
		System.out.println("结果：");
		System.out.println(treeMap);
	}
}
