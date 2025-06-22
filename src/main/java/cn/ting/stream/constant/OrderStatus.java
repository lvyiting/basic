package cn.ting.stream.constant;

/**
 * @author : lvyiting
 * @date : 2025-04-26
 **/
public enum OrderStatus {
	/** 待处理（未付款） */ PENDING,
	/** 已发货（运输中） */ SHIPPED,
	/** 已送达（签收成功） */ DELIVERED,
	/** 已取消（客户/系统取消） */ CANCELLED
}
