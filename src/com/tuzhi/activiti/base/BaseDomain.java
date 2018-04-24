package com.tuzhi.activiti.base;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 父类实体
 * @author 文哥
 *
 * 2017年3月15日 下午5:06:11
 */
public class BaseDomain{
	
	@Transient
	@JsonIgnore
	private Integer pageSize;   //页面大小
	@Transient
	@JsonIgnore
	private Integer pageNum;	//页码
	@Transient
	@JsonIgnore
	private String sortName;	//排序字段
	@Transient
	@JsonIgnore
	private String sortOrder;	//排序方式
	@Transient
	@JsonIgnore
	private String searchText;	//搜索文本
	
	public Integer getPageSize() {
		return pageSize== null ? 15 :pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum == null ? 1 :pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
