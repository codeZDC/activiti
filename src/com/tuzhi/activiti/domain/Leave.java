package com.tuzhi.activiti.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author codeZ
 * @date 2018年4月23日 下午2:11:32
 * 
 */
public class Leave implements Serializable{

	//请假人,请假天数,请假日期,请假说明
	private String leavePerson;
	private Integer leaveDays;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//存日期时使用  
	private Date leaveDate;
	private String remarks;
	public String getLeavePerson() {
		return leavePerson;
	}
	public void setLeavePerson(String leavePerson) {
		this.leavePerson = leavePerson;
	}
	public Integer getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
