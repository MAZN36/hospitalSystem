/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HDrugInfo;
import com.thinkgem.jeesite.modules.hospital.dao.HDrugInfoDao;

/**
 * 药品信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HDrugInfoService extends CrudService<HDrugInfoDao, HDrugInfo> {

	public HDrugInfo get(String id) {
		return super.get(id);
	}
	
	public List<HDrugInfo> findList(HDrugInfo hDrugInfo) {
		return super.findList(hDrugInfo);
	}
	
	public Page<HDrugInfo> findPage(Page<HDrugInfo> page, HDrugInfo hDrugInfo) {
		return super.findPage(page, hDrugInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(HDrugInfo hDrugInfo) {
		super.save(hDrugInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HDrugInfo hDrugInfo) {
		super.delete(hDrugInfo);
	}
	
}