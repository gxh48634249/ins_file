package com.ins.csdn.tool;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用于分页查询传参
 *
 */
public class PageParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8521691651190868382L;
	/**
	 * 需要查询第几页
	 *
	 * */
	@ApiModelProperty(value="当前页数",name="pageIndex",example="1")
	private Integer pageIndex;
	/*
	 * 每页容量
	 * 
	 * */
	@ApiModelProperty(value="每页容量",name="pageSize",example="10")
	private Integer pageSize;
	/*
	 * 总信息数
	 * 
	 * */
	@ApiModelProperty(value="数据总量",name="totleInfo",example="90")
	private Long totleInfo;
	/*
	 * 总页数
	 * 
	 * */
	@ApiModelProperty(value="总页数",name="totlePage",example="9")
	private Integer totlePage;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotleInfo() {
		return totleInfo;
	}

	public void setTotleInfo(Long totleInfo) {
		this.totleInfo = totleInfo;
	}

	public Integer getTotlePage() {
		return totlePage;
	}

	public void setTotlePage(Integer totlePage) {
		this.totlePage = totlePage;
	}
	
	
}
