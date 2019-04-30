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
import com.thinkgem.jeesite.modules.hospital.entity.HCharge;
import com.thinkgem.jeesite.modules.hospital.service.HChargeService;

/**
 * 收费单信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hCharge")
public class HChargeController extends BaseController {

	@Autowired
	private HChargeService hChargeService;
	
	@ModelAttribute
	public HCharge get(@RequestParam(required=false) String id) {
		HCharge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hChargeService.get(id);
		}
		if (entity == null){
			entity = new HCharge();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hCharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(HCharge hCharge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HCharge> page = hChargeService.findPage(new Page<HCharge>(request, response), hCharge); 
		model.addAttribute("page", page);
		return "modules/hospital/hChargeList";
	}

	@RequiresPermissions("hospital:hCharge:view")
	@RequestMapping(value = "form")
	public String form(HCharge hCharge, Model model) {
		model.addAttribute("hCharge", hCharge);
		return "modules/hospital/hChargeForm";
	}

	@RequiresPermissions("hospital:hCharge:edit")
	@RequestMapping(value = "save")
	public String save(HCharge hCharge, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hCharge)){
			return form(hCharge, model);
		}
		hChargeService.save(hCharge);
		addMessage(redirectAttributes, "保存收费单信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hCharge/?repage";
	}
	
	@RequiresPermissions("hospital:hCharge:edit")
	@RequestMapping(value = "delete")
	public String delete(HCharge hCharge, RedirectAttributes redirectAttributes) {
		hChargeService.delete(hCharge);
		addMessage(redirectAttributes, "删除收费单信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hCharge/?repage";
	}

}