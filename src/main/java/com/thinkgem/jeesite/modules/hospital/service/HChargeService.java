/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HCharge;
import com.thinkgem.jeesite.modules.hospital.dao.HChargeDao;

/**
 * 收费单信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HChargeService extends CrudService<HChargeDao, HCharge> {

	public HCharge get(String id) {
		return super.get(id);
	}
	
	public List<HCharge> findList(HCharge hCharge) {
		return super.findList(hCharge);
	}
	
	public Page<HCharge> findPage(Page<HCharge> page, HCharge hCharge) {
		return super.findPage(page, hCharge);
	}
	
	@Transactional(readOnly = false)
	public void save(HCharge hCharge) {
		super.save(hCharge);
	}
	
	@Transactional(readOnly = false)
	public void delete(HCharge hCharge) {
		super.delete(hCharge);
	}
	
}