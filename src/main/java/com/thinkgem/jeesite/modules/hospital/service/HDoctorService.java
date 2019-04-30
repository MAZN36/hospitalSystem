/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.dao.HDoctorDao;

/**
 * 医生信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HDoctorService extends CrudService<HDoctorDao, HDoctor> {

	public HDoctor get(String id) {
		return super.get(id);
	}
	
	public List<HDoctor> findList(HDoctor hDoctor) {
		return super.findList(hDoctor);
	}
	
	public Page<HDoctor> findPage(Page<HDoctor> page, HDoctor hDoctor) {
		return super.findPage(page, hDoctor);
	}
	
	@Transactional(readOnly = false)
	public void save(HDoctor hDoctor) {
		super.save(hDoctor);
	}
	
	@Transactional(readOnly = false)
	public void delete(HDoctor hDoctor) {
		super.delete(hDoctor);
	}
	
}