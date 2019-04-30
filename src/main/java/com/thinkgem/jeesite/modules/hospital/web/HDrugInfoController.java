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
import com.thinkgem.jeesite.modules.hospital.entity.HDrugInfo;
import com.thinkgem.jeesite.modules.hospital.service.HDrugInfoService;

/**
 * 药品信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hDrugInfo")
public class HDrugInfoController extends BaseController {

	@Autowired
	private HDrugInfoService hDrugInfoService;
	
	@ModelAttribute
	public HDrugInfo get(@RequestParam(required=false) String id) {
		HDrugInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hDrugInfoService.get(id);
		}
		if (entity == null){
			entity = new HDrugInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hDrugInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HDrugInfo hDrugInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HDrugInfo> page = hDrugInfoService.findPage(new Page<HDrugInfo>(request, response), hDrugInfo); 
		model.addAttribute("page", page);
		return "modules/hospital/hDrugInfoList";
	}

	@RequiresPermissions("hospital:hDrugInfo:view")
	@RequestMapping(value = "form")
	public String form(HDrugInfo hDrugInfo, Model model) {
		model.addAttribute("hDrugInfo", hDrugInfo);
		return "modules/hospital/hDrugInfoForm";
	}

	@RequiresPermissions("hospital:hDrugInfo:edit")
	@RequestMapping(value = "save")
	public String save(HDrugInfo hDrugInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hDrugInfo)){
			return form(hDrugInfo, model);
		}
		hDrugInfoService.save(hDrugInfo);
		addMessage(redirectAttributes, "保存药品信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDrugInfo/?repage";
	}
	
	@RequiresPermissions("hospital:hDrugInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(HDrugInfo hDrugInfo, RedirectAttributes redirectAttributes) {
		hDrugInfoService.delete(hDrugInfo);
		addMessage(redirectAttributes, "删除药品信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDrugInfo/?repage";
	}

}