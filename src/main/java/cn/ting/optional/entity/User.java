package cn.ting.optional.entity;

import java.util.List;
import java.util.Optional;
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
public class User {
	private String name;
	private List<Address> addresses; // 可能为null或空

}

