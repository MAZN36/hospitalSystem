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
import com.thinkgem.jeesite.modules.hospital.entity.HPrescriptInfo;
import com.thinkgem.jeesite.modules.hospital.service.HPrescriptInfoService;

/**
 * 处方信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hPrescriptInfo")
public class HPrescriptInfoController extends BaseController {

	@Autowired
	private HPrescriptInfoService hPrescriptInfoService;
	
	@ModelAttribute
	public HPrescriptInfo get(@RequestParam(required=false) String id) {
		HPrescriptInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hPrescriptInfoService.get(id);
		}
		if (entity == null){
			entity = new HPrescriptInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hPrescriptInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HPrescriptInfo hPrescriptInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HPrescriptInfo> page = hPrescriptInfoService.findPage(new Page<HPrescriptInfo>(request, response), hPrescriptInfo); 
		model.addAttribute("page", page);
		return "modules/hospital/hPrescriptInfoList";
	}

	@RequiresPermissions("hospital:hPrescriptInfo:view")
	@RequestMapping(value = "form")
	public String form(HPrescriptInfo hPrescriptInfo, Model model) {
		model.addAttribute("hPrescriptInfo", hPrescriptInfo);
		return "modules/hospital/hPrescriptInfoForm";
	}

	@RequiresPermissions("hospital:hPrescriptInfo:edit")
	@RequestMapping(value = "save")
	public String save(HPrescriptInfo hPrescriptInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hPrescriptInfo)){
			return form(hPrescriptInfo, model);
		}
		hPrescriptInfoService.save(hPrescriptInfo);
		addMessage(redirectAttributes, "保存处方信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hPrescriptInfo/?repage";
	}
	
	@RequiresPermissions("hospital:hPrescriptInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(HPrescriptInfo hPrescriptInfo, RedirectAttributes redirectAttributes) {
		hPrescriptInfoService.delete(hPrescriptInfo);
		addMessage(redirectAttributes, "删除处方信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hPrescriptInfo/?repage";
	}

}