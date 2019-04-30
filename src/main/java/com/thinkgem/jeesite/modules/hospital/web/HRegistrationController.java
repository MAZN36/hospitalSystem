/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hospital.entity.HRegistration;
import com.thinkgem.jeesite.modules.hospital.service.HRegistrationService;

/**
 * 挂号信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hRegistration")
public class HRegistrationController extends BaseController {

	@Autowired
	private HRegistrationService hRegistrationService;
	
	@ModelAttribute
	public HRegistration get(@RequestParam(required=false) String id) {
		HRegistration entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hRegistrationService.get(id);
		}
		if (entity == null){
			entity = new HRegistration();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hRegistration:view")
	@RequestMapping(value = {"list", ""})
	public String list(HRegistration hRegistration, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HRegistration> page = hRegistrationService.findPage(new Page<HRegistration>(request, response), hRegistration); 
		model.addAttribute("page", page);
		return "modules/hospital/hRegistrationList";
	}

	@RequiresPermissions("hospital:hRegistration:view")
	@RequestMapping(value = "form")
	public String form(HRegistration hRegistration, Model model) {
		model.addAttribute("hRegistration", hRegistration);
		return "modules/hospital/hRegistrationForm";
	}

	@RequiresPermissions("hospital:hRegistration:edit")
	@RequestMapping(value = "save")
	public String save(HRegistration hRegistration, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hRegistration)){
			return form(hRegistration, model);
		}
		hRegistrationService.save(hRegistration);
		addMessage(redirectAttributes, "保存挂号信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hRegistration/?repage";
	}
	
	@RequiresPermissions("hospital:hRegistration:edit")
	@RequestMapping(value = "delete")
	public String delete(HRegistration hRegistration, RedirectAttributes redirectAttributes) {
		hRegistrationService.delete(hRegistration);
		addMessage(redirectAttributes, "删除挂号信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hRegistration/?repage";
	}

}