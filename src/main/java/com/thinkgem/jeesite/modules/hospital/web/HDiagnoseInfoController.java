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
import com.thinkgem.jeesite.modules.hospital.entity.HDiagnoseInfo;
import com.thinkgem.jeesite.modules.hospital.service.HDiagnoseInfoService;

/**
 * 诊断信息Controller
 * @author mz
 * @version 2019-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/hDiagnoseInfo")
public class HDiagnoseInfoController extends BaseController {

	@Autowired
	private HDiagnoseInfoService hDiagnoseInfoService;
	
	@ModelAttribute
	public HDiagnoseInfo get(@RequestParam(required=false) String id) {
		HDiagnoseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hDiagnoseInfoService.get(id);
		}
		if (entity == null){
			entity = new HDiagnoseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("hospital:hDiagnoseInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HDiagnoseInfo hDiagnoseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HDiagnoseInfo> page = hDiagnoseInfoService.findPage(new Page<HDiagnoseInfo>(request, response), hDiagnoseInfo); 
		model.addAttribute("page", page);
		return "modules/hospital/hDiagnoseInfoList";
	}

	@RequiresPermissions("hospital:hDiagnoseInfo:view")
	@RequestMapping(value = "form")
	public String form(HDiagnoseInfo hDiagnoseInfo, Model model) {
		model.addAttribute("hDiagnoseInfo", hDiagnoseInfo);
		return "modules/hospital/hDiagnoseInfoForm";
	}

	@RequiresPermissions("hospital:hDiagnoseInfo:edit")
	@RequestMapping(value = "save")
	public String save(HDiagnoseInfo hDiagnoseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hDiagnoseInfo)){
			return form(hDiagnoseInfo, model);
		}
		hDiagnoseInfoService.save(hDiagnoseInfo);
		addMessage(redirectAttributes, "保存诊断信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDiagnoseInfo/?repage";
	}
	
	@RequiresPermissions("hospital:hDiagnoseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(HDiagnoseInfo hDiagnoseInfo, RedirectAttributes redirectAttributes) {
		hDiagnoseInfoService.delete(hDiagnoseInfo);
		addMessage(redirectAttributes, "删除诊断信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hDiagnoseInfo/?repage";
	}

}