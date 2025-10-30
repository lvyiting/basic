package cn.ting.p1.binaryswitch;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : lvyiting
 * @date : 2025/07/09
 **/
@Getter
@AllArgsConstructor
public enum PositionType {
	/**
	 * 司机
	 */
	DRIVER("司机"),

	/**
	 * 货车司机
	 */
	TRUCK_DRIVER("货车司机"),

	/**
	 * 客车司机
	 */
	BUS_DRIVER("客车司机"),

	/**
	 * 工程车司机
	 */
	ENGINEERING_DRIVER("工程车司机");

	private String desc;
}
