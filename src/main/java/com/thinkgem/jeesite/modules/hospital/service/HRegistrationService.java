/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HRegistration;
import com.thinkgem.jeesite.modules.hospital.dao.HRegistrationDao;

/**
 * 挂号信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HRegistrationService extends CrudService<HRegistrationDao, HRegistration> {

	public HRegistration get(String id) {
		return super.get(id);
	}
	
	public List<HRegistration> findList(HRegistration hRegistration) {
		return super.findList(hRegistration);
	}
	
	public Page<HRegistration> findPage(Page<HRegistration> page, HRegistration hRegistration) {
		return super.findPage(page, hRegistration);
	}
	
	@Transactional(readOnly = false)
	public void save(HRegistration hRegistration) {
		super.save(hRegistration);
	}
	
	@Transactional(readOnly = false)
	public void delete(HRegistration hRegistration) {
		super.delete(hRegistration);
	}
	
}