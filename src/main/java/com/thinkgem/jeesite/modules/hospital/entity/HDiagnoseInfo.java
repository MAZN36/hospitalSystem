/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 诊断信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HDiagnoseInfo extends DataEntity<HDiagnoseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String patientId;		// 病人id
	private String registrationId;		// 挂号单id
	private String doctorId;		// 医生id
	private String deptId;		// 科室id
	private String symptom;		// 诊断症状
	
	public HDiagnoseInfo() {
		super();
	}

	public HDiagnoseInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="病人id长度必须介于 0 和 64 之间")
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	@Length(min=0, max=64, message="挂号单id长度必须介于 0 和 64 之间")
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	
	@Length(min=0, max=64, message="医生id长度必须介于 0 和 64 之间")
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	
	@Length(min=0, max=64, message="科室id长度必须介于 0 和 64 之间")
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Length(min=0, max=255, message="诊断症状长度必须介于 0 和 255 之间")
	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	
}