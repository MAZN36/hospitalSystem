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
import com.thinkgem.jeesite.modules.hospital.entity.HPatient;
import com.thinkgem.jeesite.modules.hospital.service.HPatientService;

/**
 * 病人信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hPatient")
public class HPatientController extends BaseController {

	@Autowired
	private HPatientService hPatientService;
	
	@ModelAttribute
	public HPatient get(@RequestParam(required=false) String id) {
		HPatient entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hPatientService.get(id);
		}
		if (entity == null){
			entity = new HPatient();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hPatient:view")
	@RequestMapping(value = {"list", ""})
	public String list(HPatient hPatient, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HPatient> page = hPatientService.findPage(new Page<HPatient>(request, response), hPatient); 
		model.addAttribute("page", page);
		return "modules/hospital/hPatientList";
	}

	@RequiresPermissions("hospital:hPatient:view")
	@RequestMapping(value = "form")
	public String form(HPatient hPatient, Model model) {
		model.addAttribute("hPatient", hPatient);
		return "modules/hospital/hPatientForm";
	}

	@RequiresPermissions("hospital:hPatient:edit")
	@RequestMapping(value = "save")
	public String save(HPatient hPatient, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hPatient)){
			return form(hPatient, model);
		}
		hPatientService.save(hPatient);
		addMessage(redirectAttributes, "保存病人信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hPatient/?repage";
	}
	
	@RequiresPermissions("hospital:hPatient:edit")
	@RequestMapping(value = "delete")
	public String delete(HPatient hPatient, RedirectAttributes redirectAttributes) {
		hPatientService.delete(hPatient);
		addMessage(redirectAttributes, "删除病人信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hPatient/?repage";
	}

}