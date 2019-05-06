/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hospital.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hospital.delegate.AppReqDelegate;
import com.thinkgem.jeesite.modules.hospital.utils.AppParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试Controller
 * 
 * @author ThinkGem
 * @version 2014-02-28
 */
@CrossOrigin
@Controller
@RequestMapping(value = "${frontPath}/app")
public class AppReqController extends BaseController {

    @Autowired
    private AppReqDelegate reqDelegate;

	@CrossOrigin
	@RequestMapping(value = "getH5Response", method = RequestMethod.POST)
	@ResponseBody
	public String getH5Response(HttpServletRequest request) {
		AppParamUtils appReq = new AppParamUtils();
		String req = appReq.getJsonData(request);
		JSONObject jsonObj = null;
		JSONObject retWrongObj = null;
		String returnStr = "";
		String funCode = "";// 功能标识
		try {
			jsonObj = new JSONObject();
			jsonObj = JSONObject.parseObject(req);
			if (jsonObj.containsKey("funCode")) {
				funCode = (String) jsonObj.get("funCode");
			} else {
				retWrongObj = new JSONObject();
				retWrongObj.put("resultCode", "1");
				retWrongObj.put("resultMessage", "funCode为空！！");
				return retWrongObj.toJSONString();
			}
			logger.debug("手机H5请求参数funCode=" + funCode + ";jsonObj=" + jsonObj );
			returnStr = this.disPatchFunc(funCode, jsonObj,"H5");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("手机H5请求参数funCode=" + funCode + ";jsonObj=" + jsonObj + "异常，请检查！！");
		}
		logger.debug("----H5请求入参req=" + req);
		return returnStr;
	}

	/**
	 * 手机请求功能分发
	 *
	 * @param funCode
	 * @param reqStr
	 * @param jsonObj
	 * @throws Exception
	 */
	private String disPatchFunc(String funCode, JSONObject jsonObj,String type) {
		JSONObject retObj = null;
		JSONObject retWrongObj = null;
		String returnStr = "";
		try {
			retObj = new JSONObject();
			retWrongObj = new JSONObject();
			switch (funCode){
				case "getDeptList" : //获取科室列表
                    returnStr = reqDelegate.getDeptList(jsonObj);
					break;
				case "getDoctorList" : //获取医生列表
                    returnStr = reqDelegate.getDoctorList(jsonObj);
					break;
				case "loginH5" : //登录
                    returnStr = reqDelegate.loginH5(jsonObj);
					break;
				case "registration" : //病人预约挂号
                    returnStr = reqDelegate.registration(jsonObj);
					break;
				case "getRegistrationList" : //获取医生端病人列表
                    returnStr = reqDelegate.getRegistrationList(jsonObj);
					break;
				case "callRegistration" : //医生叫号
                    returnStr = reqDelegate.callRegistration(jsonObj);
					break;
				default:
					returnStr = "{\"resultCode\":\"1\",\"resultMessage\":\"未找到对应key值\"}";
					break;
			}
			logger.debug("返回h5的出参是returnStr=" + returnStr);
		} catch (Exception e) {
			e.printStackTrace();
			retWrongObj.put("resultCode", "1");
			retWrongObj.put("resultMessage", "系统异常！！");
			return retObj.toJSONString();
		}
		return returnStr;
	}
	public String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("x-forwarded-for");  
        logger.debug("x-forwarded-for:"+clientIp);
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
            clientIp = request.getHeader("Proxy-Client-IP");  
            logger.debug("Proxy-Client-IP:"+clientIp);
        }  
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
            clientIp = request.getHeader("WL-Proxy-Client-IP");
            logger.debug("WL-Proxy-Client-IP"+clientIp);
        }  
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
        	clientIp = request.getHeader("X-Real-IP");
        	logger.debug("X-Real-IP"+clientIp);
        }
        if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
            clientIp = request.getRemoteAddr();  
            logger.debug("request.getRemoteAddr()"+clientIp);
        } 
        return clientIp;  
    }
}
