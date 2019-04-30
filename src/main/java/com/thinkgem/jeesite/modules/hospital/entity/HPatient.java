/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 病人信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HPatient extends DataEntity<HPatient> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户id
	private String address;		// 家庭住址
	private String bloodType;		// 血型
	private String education;		// 学历
	private String sts;		// 状态
	
	public HPatient() {
		super();
	}

	public HPatient(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="家庭住址长度必须介于 0 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=64, message="血型长度必须介于 0 和 64 之间")
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	@Length(min=0, max=64, message="学历长度必须介于 0 和 64 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}