/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 医生信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HDoctor extends DataEntity<HDoctor> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String jobNumber;		// 工号
	private String jobName;		// 职称
	private String education;		// 学历
	private Integer workYear;		// 工龄
	private String nation;		// 民族
	private String doctorDuty;		// 职务
	private String sts;		// 状态
	private String introduce;		// 介绍
	
	public HDoctor() {
		super();
	}

	public HDoctor(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="工号长度必须介于 0 和 64 之间")
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Length(min=0, max=64, message="职称长度必须介于 0 和 64 之间")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Length(min=0, max=64, message="学历长度必须介于 0 和 64 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}
	
	@Length(min=0, max=64, message="民族长度必须介于 0 和 64 之间")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Length(min=0, max=64, message="职务长度必须介于 0 和 64 之间")
	public String getDoctorDuty() {
		return doctorDuty;
	}

	public void setDoctorDuty(String doctorDuty) {
		this.doctorDuty = doctorDuty;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
	@Length(min=0, max=255, message="介绍长度必须介于 0 和 255 之间")
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
}