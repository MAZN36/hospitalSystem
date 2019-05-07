/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.hospital.entity.HDiagnoseInfo;
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.entity.HPrescriptInfo;
import com.thinkgem.jeesite.modules.hospital.entity.HRegistration;
import com.thinkgem.jeesite.modules.hospital.service.HDiagnoseInfoService;
import com.thinkgem.jeesite.modules.hospital.service.HDoctorService;
import com.thinkgem.jeesite.modules.hospital.service.HPrescriptInfoService;
import com.thinkgem.jeesite.modules.hospital.service.HRegistrationService;
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
import com.thinkgem.jeesite.modules.hospital.entity.HCharge;
import com.thinkgem.jeesite.modules.hospital.service.HChargeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@Autowired
	private HDiagnoseInfoService diagnoseInfoService;
	@Autowired
	private HPrescriptInfoService prescriptInfoService;
	@Autowired
	private HRegistrationService registrationService;
	@Autowired
	private HDoctorService doctorService;
	
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
	@RequestMapping(value = "saveCharge")
	public void saveCharge(HCharge hCharge){
		hCharge.setSts("B");
		hCharge.setPaymentDate(new Date());
		hChargeService.save(hCharge);
	}
	
	@RequiresPermissions("hospital:hCharge:edit")
	@RequestMapping(value = "delete")
	public String delete(HCharge hCharge, RedirectAttributes redirectAttributes) {
		hChargeService.delete(hCharge);
		addMessage(redirectAttributes, "删除收费单信息成功");
		return "redirect:"+Global.getAdminPath()+"/hospital/hCharge/?repage";
	}
	@RequiresPermissions("hospital:hCharge:edit")
	@RequestMapping(value = "chargeInfoList")
	public String chargeInfoList(HCharge hCharge,  Model model){
		HDiagnoseInfo diagnoseInfo = diagnoseInfoService.get(hCharge.getDiagnoseId());
		model.addAttribute("hCharge",hCharge);
		//获取病例信息
		model.addAttribute("diagnoseInfo",diagnoseInfo==null ? new HDiagnoseInfo() : diagnoseInfo);
		HPrescriptInfo prescriptInfo = new HPrescriptInfo();
		prescriptInfo.setDiagnoseId(diagnoseInfo.getId());
		//获取处方信息
		List<HPrescriptInfo> prescriptInfoList = prescriptInfoService.findList(prescriptInfo);
		model.addAttribute("prescriptInfoList",prescriptInfoList == null ? new ArrayList<HPrescriptInfo>() : prescriptInfoList);
		//获取预约信息
		HRegistration hRegistration = registrationService.get(diagnoseInfo.getRegistrationId());
		model.addAttribute("hRegistration",hRegistration==null ? new HRegistration() : hRegistration);
		//获取医生信息
		HDoctor doctor = doctorService.get(diagnoseInfo.getDoctorId());
		model.addAttribute("doctor",doctor==null ? new HDoctor() : doctor);
		return "modules/hospital/hChargeInfoList";
	}

}