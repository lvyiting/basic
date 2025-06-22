package cn.ting.current.entity;

import lombok.Data;

/**
 * 职位基础信息
 *
 * @author : lvyiting
 * @date : 2025-06-22
 **/
@Data
public class JobBasic {
	private final long id;
	private final String title;
	private final int salary;
	private final String description;
}
