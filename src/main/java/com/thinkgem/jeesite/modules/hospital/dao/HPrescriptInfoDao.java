/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hospital.entity.HPrescriptInfo;

/**
 * 处方信息DAO接口
 * @author mz
 * @version 2019-04-30
 */
@MyBatisDao
public interface HPrescriptInfoDao extends CrudDao<HPrescriptInfo> {
	
}