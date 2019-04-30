/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HPrescriptInfo;
import com.thinkgem.jeesite.modules.hospital.dao.HPrescriptInfoDao;

/**
 * 处方信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HPrescriptInfoService extends CrudService<HPrescriptInfoDao, HPrescriptInfo> {

	public HPrescriptInfo get(String id) {
		return super.get(id);
	}
	
	public List<HPrescriptInfo> findList(HPrescriptInfo hPrescriptInfo) {
		return super.findList(hPrescriptInfo);
	}
	
	public Page<HPrescriptInfo> findPage(Page<HPrescriptInfo> page, HPrescriptInfo hPrescriptInfo) {
		return super.findPage(page, hPrescriptInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(HPrescriptInfo hPrescriptInfo) {
		super.save(hPrescriptInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HPrescriptInfo hPrescriptInfo) {
		super.delete(hPrescriptInfo);
	}
	
}