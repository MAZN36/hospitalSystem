/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 挂号信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HRegistration extends DataEntity<HRegistration> {
	
	private static final long serialVersionUID = 1L;
	private Double price;		// 挂号价格
	private String patientId;   //病人id
	private String doctorId;		// 挂号医师id
	private String registrationRemarks;		// 症状
	private Date registrationDate;		// 挂号时间
	private Date bookDate;		// 预约时间
	private Office office;		// 挂号科室
	private String queueNo;		// 排队号
	private String sts;		// 状态
	
	public HRegistration() {
		super();
	}

	public HRegistration(String id){
		super(id);
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Length(min=0, max=64, message="挂号医师id长度必须介于 0 和 64 之间")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	
	@Length(min=0, max=255, message="症状长度必须介于 0 和 255 之间")
	public String getRegistrationRemarks() {
		return registrationRemarks;
	}

	public void setRegistrationRemarks(String registrationRemarks) {
		this.registrationRemarks = registrationRemarks;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=64, message="排队号长度必须介于 0 和 64 之间")
	public String getQueueNo() {
		return queueNo;
	}

	public void setQueueNo(String queueNo) {
		this.queueNo = queueNo;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
}