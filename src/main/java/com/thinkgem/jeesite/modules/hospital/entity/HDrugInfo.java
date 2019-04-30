/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 药品信息Entity
 * @author mz
 * @version 2019-04-30
 */
public class HDrugInfo extends DataEntity<HDrugInfo> {
	
	private static final long serialVersionUID = 1L;
	private String drugNo;		// 药品编号
	private String drugName;		// 药品名称
	private String unit;		// 计量单位
	private String code;		// 条形码
	private String spec;		// 规格
	private String produceArea;		// 生产厂家
	private String madeArea;		// 国家地区
	private String grugRemarks;		// 药品描述
	private Double rentPrice;		// 药品进价
	private Double sellPrice;		// 药品售价
	private Integer inventoryNum;		// 库存数量
	private String sts;		// 状态
	
	public HDrugInfo() {
		super();
	}

	public HDrugInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="药品编号长度必须介于 0 和 64 之间")
	public String getDrugNo() {
		return drugNo;
	}

	public void setDrugNo(String drugNo) {
		this.drugNo = drugNo;
	}
	
	@Length(min=0, max=64, message="药品名称长度必须介于 0 和 64 之间")
	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	@Length(min=0, max=64, message="计量单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="条形码长度必须介于 0 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@Length(min=0, max=255, message="生产厂家长度必须介于 0 和 255 之间")
	public String getProduceArea() {
		return produceArea;
	}

	public void setProduceArea(String produceArea) {
		this.produceArea = produceArea;
	}
	
	@Length(min=0, max=255, message="国家地区长度必须介于 0 和 255 之间")
	public String getMadeArea() {
		return madeArea;
	}

	public void setMadeArea(String madeArea) {
		this.madeArea = madeArea;
	}
	
	@Length(min=0, max=255, message="药品描述长度必须介于 0 和 255 之间")
	public String getGrugRemarks() {
		return grugRemarks;
	}

	public void setGrugRemarks(String grugRemarks) {
		this.grugRemarks = grugRemarks;
	}
	
	public Double getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Double rentPrice) {
		this.rentPrice = rentPrice;
	}
	
	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}