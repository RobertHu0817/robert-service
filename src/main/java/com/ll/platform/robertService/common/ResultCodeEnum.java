package com.ll.platform.robertService.common;

/**
 * @Title ResultCodeEnum.java
 * @Description 响应码枚举
 * @author Robert
 * @date 2019年11月26日
 */
public enum ResultCodeEnum {

	/** 通用错误 **/
	SUCCESS(200, "Success"), // 请求成功
	BAD_REQUEST(400, "Request message format error"), // 请求的语法错误，服务器无法理解
	UNAUTHORIZED(401, "Signing failed"), // 请求要求用户的身份认证
	FORBIDDEN(403, "Insufficient permissions"), // 理解客户端请求但拒绝执行
	NOT_FOUND(404, "The interface not exiest"), // 服务器无法根据客户端的请求找到资源
	METHOD_NOT_ALLOWED(405, "Method not allowed"), // 客户端请求中的方法被禁止
	SERVER_ERROR(500, "Server error,please contact the Robert"), // 服务器内部错误
	/* 参数错误 */
	PARAM_VALIDATE_FAIL(1000, "Parameter validation failed"), // 参数验证失败
	PARAM_NOT_COMPLETE(1001, "Required parameter failed"), // 必填参数缺失
	PARAM_VALUE_ILLEGAL(1002, "Illegal parameter value"), // 非法参数值
	PARAM_TYPE_BIND_ERROR(1003, "Parameter type error"), // 参数类型错误
	/* 未找到可用数据 */
	NO_STUDENT_FIND(1100, "Student account not found"), // 未找到学员账号
	NO_TEACHER_FIND(1101, "Teacher account not found"), // 未找到老师账号

	/* 业务校验不通过 */
	PHONE_EXIST_IN_FE(2100, "Phone number already exists in the user account table"), // 电话号码在用户账户表中已存在

	NO_DATA_AFFECTED(9000, "No data affected"),// 没有数据收到影响
	;

	private int code;
	private String message;

	ResultCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	ResultCodeEnum(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}
}
