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
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.service.HDoctorService;

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

}