package com.example.demo.entity.result;

public enum ExceptionMsg {
	SUCCESS("000000", "success!"), 
	FAILED("999999", "failed!"), 
	ParamError("000001", "参数错误！"),
	LoginNameOrPassWordError("000100", "用户名或者密码错误！"), 
	PhoneUsed("000101", "该手机已被注册"), 
	UserNameUsed("000102","该登录名称已存在"), 
	EmailNotRegister("000103", "该邮箱地址未注册"), 
	LinkOutdated("000104","该链接已过期，请重新请求"), 
	PassWordError("000105", "密码输入错误"), 
	UserNameLengthLimit("000106","用户名长度超限"),
	LoginNameNotExists("000107", "该用户未注册"), 
	USER_NOT_FOUND("000108", "该用户不存在"),
	NULL_PHONE_NUMBER("000109", "手机号为空"),
	UNFORMAT_PHONE_NUMBER("000110", "手机号码格式不正确"),
	EMPTY_FILE("000111", "please select a photo!"),
	IOEXCEPTION("000112", "io exception!"),
	
	FileEmpty("000400", "上传文件为空"), 
	LimitPictureSize("000401", "图片大小必须小于2M"), 
	LimitPictureType("000402","图片格式必须为'jpg'、'png'、'jpge'、'gif'、'bmp'");
	
	private ExceptionMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
