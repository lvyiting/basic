package cn.ting.optional.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lvyiting
 * @date : 2025-04-26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private Integer id;
	private User user;
}
