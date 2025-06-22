package cn.ting.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * 测试令牌桶算法
 *
 * @author : lvyiting
 * @date : 2025-06-21
 **/
public class RateLimiterDemo {

	/**
	 * 阻塞
	 * 时间戳转日期：
	 * https://www.matools.com/timestamp
	 */
	@Test
	public void tset1() {
		// 创建一个每秒只产生2个许可的限流器
		RateLimiter rateLimiter = RateLimiter.create(2.0);
		for (int i = 1; i <= 10; i++) {
			// 尝试获取许可，如果没有许可会阻塞，直到获取到为止
			rateLimiter.acquire();
			System.out.println("第 " + i + " 个请求被允许，时间：" + System.currentTimeMillis());
		}
	}

	/**
	 * 非阻塞
	 * 拒绝请求
	 */
	@Test
	public void tset2() {
		RateLimiter rateLimiter = RateLimiter.create(2.0);
		for (int i = 1; i <= 10; i++) {
			// 非阻塞，立即返回true/false
			if (rateLimiter.tryAcquire()) {
				System.out.println("第 " + i + " 个请求被允许，时间：" + System.currentTimeMillis());
			} else {
				System.out.println("第 " + i + " 个请求被拒绝，时间：" + System.currentTimeMillis());
			}
			try {
				Thread.sleep(100); // 模拟请求流量
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
