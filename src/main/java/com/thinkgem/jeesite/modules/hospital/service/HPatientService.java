/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hospital.entity.HPatient;
import com.thinkgem.jeesite.modules.hospital.dao.HPatientDao;

/**
 * 病人信息Service
 * @author mz
 * @version 2019-04-30
 */
@Service
@Transactional(readOnly = true)
public class HPatientService extends CrudService<HPatientDao, HPatient> {

	@Autowired
	private UserDao userDao;

	public HPatient get(String id) {
		return super.get(id);
	}
	
	public List<HPatient> findList(HPatient hPatient) {
		return super.findList(hPatient);
	}
	
	public Page<HPatient> findPage(Page<HPatient> page, HPatient hPatient) {
		return super.findPage(page, hPatient);
	}
	
	@Transactional(readOnly = false)
	public void save(HPatient hPatient) {
		super.save(hPatient);
	}
	
	@Transactional(readOnly = false)
	public void delete(HPatient hPatient) {
		User user = hPatient.getUser();
		userDao.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		super.delete(hPatient);
	}
	
}