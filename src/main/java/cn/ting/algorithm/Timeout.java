package cn.ting.algorithm;

/**
 * @author : lvyiting
 * @date : 2025/06/27
 **/
public class Timeout {

	/**
	 * 场景：限制某个操作的执行时间（如数据库查询超时）。
	 * 题目：模拟一个耗时任务，最多执行 2 秒。
	 */
	@org.junit.Test
	public void test1() {
		long startTime = System.currentTimeMillis();
		final long TIMEOUT = 2000; // 2秒超时
		boolean isTimeout = false;
		int tasksCompleted = 0;

		// 执行任务直到超时或全部完成
		while (!isTimeout) {
			// 检查是否超时
			if (System.currentTimeMillis() - startTime > TIMEOUT) {
				isTimeout = true;
				break;
			}

			// 执行任务
			boolean taskSuccess = performTask();

			if (taskSuccess) {
				tasksCompleted++;
				System.out.println("任务 " + tasksCompleted + " 完成!");
			}

			// 检查是否所有任务都已完成（模拟实际场景）
			if (tasksCompleted >= 3) {
				break;
			}
		}

		System.out.println(isTimeout ? "操作超时终止！已完成 " + tasksCompleted + " 个任务"
				: "所有任务完成！用时: " + (System.currentTimeMillis() - startTime) + "ms");
	}


	// 模拟任务执行（随机成功/失败）
	private static boolean performTask() {
		try {
			// 随机任务耗时 300-800ms
			int delay = 300 + (int)(Math.random() * 500);
			Thread.sleep(delay);

			// 模拟70%成功率
			return Math.random() < 0.7;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
