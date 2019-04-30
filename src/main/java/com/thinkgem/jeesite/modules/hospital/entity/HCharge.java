/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 收费单信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HCharge extends DataEntity<HCharge> {
	
	private static final long serialVersionUID = 1L;
	private String patientId;		// 病人id
	private String diagnoseId;		// 病例id
	private Double receivePrice;		// 应收费用
	private Double proceedsPrice;		// 实收费用
	private String paymentType;		// 支付方式
	private String paymentNo;		// 流水号
	private Date paymentDate;		// 收费日期
	private String sts;		// 状态
	
	public HCharge() {
		super();
	}

	public HCharge(String id){
		super(id);
	}

	@Length(min=1, max=64, message="病人id长度必须介于 1 和 64 之间")
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	@Length(min=0, max=64, message="病例id长度必须介于 0 和 64 之间")
	public String getDiagnoseId() {
		return diagnoseId;
	}

	public void setDiagnoseId(String diagnoseId) {
		this.diagnoseId = diagnoseId;
	}
	
	public Double getReceivePrice() {
		return receivePrice;
	}

	public void setReceivePrice(Double receivePrice) {
		this.receivePrice = receivePrice;
	}
	
	public Double getProceedsPrice() {
		return proceedsPrice;
	}

	public void setProceedsPrice(Double proceedsPrice) {
		this.proceedsPrice = proceedsPrice;
	}
	
	@Length(min=0, max=64, message="支付方式长度必须介于 0 和 64 之间")
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	@Length(min=0, max=64, message="流水号长度必须介于 0 和 64 之间")
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}