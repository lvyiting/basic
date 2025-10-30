package cn.ting.p1.qt;

import cn.hutool.core.collection.CollUtil;
import java.util.HashSet;
import java.util.Set;

public class DemoSetCompare {
	public static void main(String[] args) {
		// 1. 创建一些示例集合，用来演示比较
		Set<String> setA = new HashSet<>();
		setA.add("banana");
		setA.add("cherry");
		setA.add("dragonfruit");

		Set<String> setB = new HashSet<>();
		setB.add("banana");
		setB.add("cherry");

		Set<String> setEmpty = new HashSet<>();

		Set<String> setWithNull = new HashSet<>();
		setWithNull.add(null);
		setWithNull.add("mango");

		compareSets(setA, setB);
		compareSets(setA, setEmpty);
		compareSets(setWithNull, setB);
		compareSets(null, setB);
	}

	private static void compareSets(Set<String> set1, Set<String> set2) {
		System.out.println("=============================================");
		System.out.println("Comparing two sets:");
		System.out.println("Set1: " + set1);
		System.out.println("Set2: " + set2);

		if (set1 == null || set2 == null) {
			if (set1 == null && set2 == null) {
				System.out.println("Both sets are null, consider them not comparable or 'equal' at your discretion.");
			} else {
				System.out.println("One of the sets is null, so they are definitely not equal.");
			}
			System.out.println("=============================================\n");
			return;
		}

		boolean isEqual = set1.equals(set2);
		System.out.println("• set1=set2 => " + isEqual);

		Set<String> difference = new HashSet<>(set1);
		difference.removeAll(set2);
		System.out.println("• set1 - set2 =>  " + difference);

		Set<String> intersection = new HashSet<>(set1);
		intersection.retainAll(set2);
		System.out.println("• set1 ∩ set2 => " + intersection);

		Set<String> union = new HashSet<>(set1);
		union.addAll(set2);
		System.out.println("• set1 ∪ set2 => " + union);

		boolean set2IsSubset =CollUtil.isNotEmpty(set2) &&  set1.containsAll(set2);
		System.out.println("• set2 ⊆ set1 => " + set2IsSubset);

		System.out.println("• set1 contains null => " + set1.contains(null));
		System.out.println("• set2 contains null => " + set2.contains(null));

		System.out.println("=============================================\n");
	}
}

