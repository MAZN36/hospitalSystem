/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 处方信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HPrescriptInfo extends DataEntity<HPrescriptInfo> {
	
	private static final long serialVersionUID = 1L;
	private String diagnoseId;		// 诊断信息id
	private String chargeId;		// 收费单id
	private String durgId;		// 药品id
	private Integer durgNum;		// 药品数量
	private String durgUsage;		// 药品用法
	
	public HPrescriptInfo() {
		super();
	}

	public HPrescriptInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="诊断信息id长度必须介于 1 和 64 之间")
	public String getDiagnoseId() {
		return diagnoseId;
	}

	public void setDiagnoseId(String diagnoseId) {
		this.diagnoseId = diagnoseId;
	}
	
	@Length(min=0, max=64, message="收费单id长度必须介于 0 和 64 之间")
	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	
	@Length(min=0, max=64, message="药品id长度必须介于 0 和 64 之间")
	public String getDurgId() {
		return durgId;
	}

	public void setDurgId(String durgId) {
		this.durgId = durgId;
	}
	
	public Integer getDurgNum() {
		return durgNum;
	}

	public void setDurgNum(Integer durgNum) {
		this.durgNum = durgNum;
	}
	
	@Length(min=0, max=255, message="药品用法长度必须介于 0 和 255 之间")
	public String getDurgUsage() {
		return durgUsage;
	}

	public void setDurgUsage(String durgUsage) {
		this.durgUsage = durgUsage;
	}
	
}