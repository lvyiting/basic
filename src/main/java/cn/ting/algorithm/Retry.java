package cn.ting.algorithm;

/**
 * 重试
 * @author : lvyiting
 * @date : 2025/06/27
 **/
public class Retry {

	/**
	 * 场景：API 请求失败时自动重试（如网络请求）。
	 * 题目：模拟调用一个成功率 30% 的 API，最多重试 3 次。
	 */
	@org.junit.Test
	public void test1() {
		int retryCount = 0;
		boolean success = false;
		while (!success && retryCount < 3) {
			retryCount++;
			System.out.println("尝试第" + retryCount + "次");
			success = callApi();
		}
		System.out.println(success?"成功":"失败");
	}

	// 模拟调用,成功率30%
	private static boolean callApi() {
		return Math.random() < 0.3;
	}
}
