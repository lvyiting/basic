package cn.ting.p1.qt;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

/**
 * @author : lvyiting
 * @date : 2025/06/28
 **/
public class ThreadLocalRandomTest {
	@Test
	public void test3() {
		Random random = ThreadLocalRandom.current();
		// [0,100)
		System.out.println(random.nextInt(100));
		//[10,20)
		System.out.println(random.nextInt(20-10)+10);
		//随机
		System.out.println(random.nextBoolean());
		//[0,1)
		System.out.println(random.nextDouble());
		//[1000,1500)
		System.out.println(ThreadLocalRandom.current().nextInt(1000, 1500));
	}
}
