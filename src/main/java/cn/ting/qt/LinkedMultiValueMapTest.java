package cn.ting.qt;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * LinkedMultiValueMap测试
 *一个key对应多个value
 * @author : lvyiting
 * @date : 2025/07/08
 **/
public class LinkedMultiValueMapTest {

	@Test
	public void test() {
		LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("name", "lvyiting");
		map.add("name", "lvyiting2");
		System.out.println(map.get("name"));
		// set 会覆盖原有的 list，只保留一个值
		map.set("name","zjc");
		System.out.println(map.get("name"));

		LinkedMultiValueMap<String, Object> maps = new LinkedMultiValueMap<>();
		map.forEach(maps::add);
		System.out.println(maps);
	}
}
