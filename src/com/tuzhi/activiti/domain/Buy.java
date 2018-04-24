package com.tuzhi.activiti.domain;

import java.io.Serializable;

/**
 * @author codeZ
 * @date 2018年4月24日 下午5:25:44
 * 
 */
public class Buy implements Serializable {
	
	private String person;
	
	private String cost;
	
	private String remarks;

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
