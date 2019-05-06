/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.service.HDoctorService;

import java.util.List;

/**
 * 医生信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hDoctor")
public class HDoctorController extends BaseController {

	@Autowired
	private HDoctorService hDoctorService;
	@Autowired
	private SystemService systemService;
	@ModelAttribute
	public HDoctor get(@RequestParam(required=false) String id) {
		HDoctor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hDoctorService.get(id);
		}
		if (entity == null){
			entity = new HDoctor();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hDoctor:view")
	@RequestMapping(value = {"list", ""})
	public String list(HDoctor hDoctor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HDoctor> page = hDoctorService.findPage(new Page<HDoctor>(request, response), hDoctor); 
		model.addAttribute("page", page);
		return "modules/hospital/hDoctorList";
	}

	@RequiresPermissions("hospital:hDoctor:view")
	@RequestMapping(value = "form")
	public String form(HDoctor hDoctor, Model model) {
		model.addAttribute("hDoctor", hDoctor);
		return "modules/hospital/hDoctorForm";
	}

	@RequiresPermissions("hospital:hDoctor:edit")
	@RequestMapping(value = "save")
	public String save(HDoctor hDoctor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hDoctor)){
			return form(hDoctor, model);
		}
		User user = hDoctor.getUser();
		user.setNo(hDoctor.getJobNumber());
		user.setLoginName(hDoctor.getJobNumber());
		user.setUserType("3");
		user.setLoginFlag(hDoctor.getSts());
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getName() + "'失败，工号"+hDoctor.getJobNumber()+"已存在");
			return form(hDoctor, model);
		}
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}else {
			user.setPassword(DictUtils.getDictLabel("1","default_password","123456"));
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		Role role = new Role();
		role.setRoleType("user");
		role.setEnname("doctor");
		List<Role> roleList = systemService.findRole(role);
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		hDoctor.setUser(user);
		hDoctorService.save(hDoctor);
		addMessage(redirectAttributes, "保存医生信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDoctor/?repage";
	}
	
	@RequiresPermissions("hospital:hDoctor:edit")
	@RequestMapping(value = "delete")
	public String delete(HDoctor hDoctor, RedirectAttributes redirectAttributes) {
		hDoctorService.delete(hDoctor);
		addMessage(redirectAttributes, "删除医生信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDoctor/?repage";
	}

	/**
	 * 验证工号是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("hospital:hDoctor:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

}