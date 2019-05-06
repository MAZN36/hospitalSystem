/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserDao userDao;

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
		User user = hDoctor.getUser();
		userDao.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		super.delete(hDoctor);
	}
	
}