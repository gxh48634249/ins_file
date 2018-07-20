package com.ins.csdn.tool;

import java.io.Serializable;


import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 用于前后端传值，勿作他用！
 * 包含构造器两个
 */
public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947989940583463441L;

	@ApiModelProperty(value="请求处理状态",name="status",example="1:success/2:fail")
	private Integer status;
	
	@ApiModelProperty(value="请求处理状态描述",name="message",example="")
	private String message;
	
	@ApiModelProperty(value="请求数据（如果存在）",name="data",example="数据、对象、字符串、时间、数字")
	private Object data;
	
	@ApiModelProperty(value="请求数据（备用）",name="subdata",example="数据、对象、字符串、时间、数字")
	private Object subdata;
	
	@ApiModelProperty(value="分页数据（用于分页请求使用）",name="pageParam")
	private PageParam pageParam;
	
	@ApiModelProperty(value="用户权限",name="token",example="")
	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Result() {

	}

	public Result(Integer status,String message) {
		this.message = message;
		this.status = status;
	}
	
	public Result(Integer status,String message,Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Result(Integer status, String message, Object data, PageParam pageParam) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.pageParam = pageParam;
	}

	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PageParam getPageParam() {
		return pageParam;
	}

	public void setPageParam(PageParam pageParam) {
		this.pageParam = pageParam;
	}

	public Object getSubdata() {
		return subdata;
	}

	public void setSubdata(Object subdata) {
		this.subdata = subdata;
	}
	
	

}
