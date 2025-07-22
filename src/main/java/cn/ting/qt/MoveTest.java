package cn.ting.qt;

import org.junit.Test;

/**
 * 位运算符
 * 左移 <<
 * 右移 >>
 * @author : lvyiting
 * @date : 2025/06/30
 **/
public class MoveTest {

	/**
	 * 按位与：&
	 * 两位都为 1 时，结果才为 1
	 */
	@Test
	public void test1() {
		//false
		System.out.println(1>9 & 9>2);
		System.out.println((01 & 10)!=0);
	}

	/**
	 * 按位或：^
	 * 两位不同为 1，相同为 0
	 */
	@Test
	public void test2() {
		//true
		System.out.println(1>9 ^ 9>2);
	}

	/**
	 * 左移: <<
	 * 向左移动指定位数，低位补 0，相当于乘以 2 的 n 次方
	 */
	@Test
	public void test3() {
		//0000 0011
		int a = 3;
		//0000 1100
		System.out.println(a << 2);
	}

}
