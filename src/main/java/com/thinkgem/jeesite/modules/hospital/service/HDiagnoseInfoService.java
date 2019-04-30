/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HDiagnoseInfo;
import com.thinkgem.jeesite.modules.hospital.dao.HDiagnoseInfoDao;

/**
 * 诊断信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HDiagnoseInfoService extends CrudService<HDiagnoseInfoDao, HDiagnoseInfo> {

	public HDiagnoseInfo get(String id) {
		return super.get(id);
	}
	
	public List<HDiagnoseInfo> findList(HDiagnoseInfo hDiagnoseInfo) {
		return super.findList(hDiagnoseInfo);
	}
	
	public Page<HDiagnoseInfo> findPage(Page<HDiagnoseInfo> page, HDiagnoseInfo hDiagnoseInfo) {
		return super.findPage(page, hDiagnoseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(HDiagnoseInfo hDiagnoseInfo) {
		super.save(hDiagnoseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HDiagnoseInfo hDiagnoseInfo) {
		super.delete(hDiagnoseInfo);
	}
	
}