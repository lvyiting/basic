package cn.ting.binaryswitch;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二进制开关
 * 0关  1开
 * 返回开关是否开启
 *
 * @author : lvyiting
 * @date : 2025/07/09
 **/
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Switch {

	/**
	 * 饿汉式单例：全部关闭
	 */
	private static final Switch aSwitch = new Switch(0);

	/**
	 * 标志位
	 */
	private Integer flag;

	/**
	 * 配置信息
	 */
	private static final ImmutableMap<PositionType, String> config = ImmutableMap.of(
			PositionType.DRIVER, "0b11",
			PositionType.TRUCK_DRIVER, "0b01",
			PositionType.BUS_DRIVER, "0b10",
			PositionType.ENGINEERING_DRIVER, "0A00");

	/**
	 * 对外暴露接口
	 */
	public static Switch getSwitch(PositionType positionType) {
		return ObjectUtil.isNull(positionType) ? aSwitch : new Switch(getBinaryValue(positionType));
	}

	/**
	 * 获取二进制值
	 */
	public static Integer getBinaryValue(PositionType positionType) {
		String binary = config.get(positionType);
		if (StrUtil.isNotBlank(binary) && (binary.startsWith("0b") || binary.startsWith("0B"))) {
			binary = binary.substring(2);
		} else {
			binary = "0";
		}
		return Integer.parseUnsignedInt(binary, 2);
	}

	/**
	 * 判断开关是否开启
	 */
	public boolean isOpen(Flag flag) {
		int location = flag.getLocation();
		if (location <= 0) {
			return false;
		}
		int lon = 1 << (location - 1);
		return (this.flag & lon) != 0;
	}

	@Getter
	@AllArgsConstructor
	enum Flag {
		/**
		 * 职位列表
		 * 从右往左第一个
		 */
		POSITIONLIST(1),

		/**
		 * 职位详情
		 * 从右往左第二个
		 */
		POSITIONDETAIL(2);

		private int location;
	}

	public static void main(String[] args) {
		System.out.println(Switch.getSwitch(PositionType.BUS_DRIVER).isOpen(Flag.POSITIONLIST));
	}
}
