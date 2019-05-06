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

	/**
			* 手机请求功能分发
	 *
			 * @param funCode
	 * @param reqStr
	 * @param jsonObj
	 * @throws Exception
	private String disPatchFunc(String funCode, JSONObject jsonObj,String type) {
		JSONObject retObj = null;
		JSONObject retWrongObj = null;
		String returnStr = "";
		try {
			retObj = new JSONObject();
			retWrongObj = new JSONObject();
			if ("getCode".equals(funCode)) {//验证码
				jsonObj.put("code", "SMS_144853279");//登录验证码（学生登录
//				returnStr="{\"resultCode\":\"0\",\"resultMessage\":\"手机验证码返回成功\",\"mobileCode\":\"123456\"}";  //接口已经调通,测试数据暂时写死
				returnStr=sms.send(jsonObj);
			}else if("login".equals(funCode)){//登录
				returnStr = appLoginService.login(jsonObj);
			}else if ("modifyPwd".equals(funCode)){ //修改密码
				returnStr = appLoginService.modifyPwd(jsonObj);
			}else if("indexShow".equals(funCode)){//学生首页
				returnStr = trainerBannerService.indexShow(jsonObj);
			}else if("teacherIndexShow".equals(funCode)){//老师首页
				returnStr = trainerBannerService.indexTeaShow(jsonObj);
			}else if("register".equals(funCode)){//注册
				returnStr = this.register(jsonObj);
			}else if("makeLesson".equals(funCode)){ //约课信息
				returnStr = makeLessonInfoService.makeLesson(jsonObj);
			}else if("musicList".equals(funCode)) { //乐谱列表
				returnStr = plMusicListService.getMusicList(jsonObj);
			}else if("musicChapterList".equals(funCode)){ //乐谱目录列表
				returnStr = plMusicListService.getMusicChapterList(jsonObj);
			}else if("musicDetail".equals(funCode)){ //乐谱详情
				returnStr = plMusicListService.getMusicDetail(jsonObj);
			}else if("initAppointment".equals(funCode)){ //初始化课程预约首页
				returnStr = plClassesService.initAppointment(jsonObj);
			}else if ("celebrityVideoList".equals(funCode)){ //名家视频列表
				returnStr = plMusicService.getCelebrityVideoList(jsonObj);
			}else if ("appointmentList".equals(funCode)){ //已预约未分配列表
				returnStr = plClassesService.appointmentList(jsonObj);
			}else if ("appointmentSuccessList".equals(funCode)){ //已约课类表  tea
				jsonObj.put("classeState","已预约课程");
				jsonObj.put("classesSts","ABC");
				returnStr = plClassesService.appointmentOrLessonList(jsonObj);
			}else if("lessonFinish".equals(funCode)){ //已上课列表
				jsonObj.put("classeState","已上完课程");
				jsonObj.put("classesSts","D");
				returnStr = plClassesService.appointmentOrLessonList(jsonObj);
			}else if("queryStudentHaveClassesInfo".equals(funCode)){ //学生个人中心
				returnStr = studentDOM.queryStudentHaveClassesInfo(jsonObj);
			}else if("version".equals(funCode)){
				returnStr = versionDOM.getCurrentVersion(jsonObj);
			}else if("modifystuInfo".equals(funCode)){//学生完善个人信息
				returnStr = this.modifystuInfo(jsonObj);
			}else if("uploadPhoto".equals(funCode)){//上传头像
				returnStr = this.studentDOM.uploadPhoto(jsonObj);
			}else if ("savePrepareClasses".equals(funCode)){ //保存学生端选择的乐谱或上传的乐谱,即保存备课信息
				returnStr = prepareClassesService.savePrepareClasses(jsonObj);
			}else if ("getPrepareMusicList".equals(funCode)){ //获取备课信息中已经上传的图片
				returnStr = prepareClassesService.getPrepareMusicList(jsonObj);
			}else if("cancelLesson".equals(funCode)){ //取消课程
				returnStr = makeLessonInfoService.cancelLesson(jsonObj);
			}else if("addGuestBook".equals(funCode)){
				returnStr = guestBookDOM.addGuestBook(jsonObj);
			}else if("doUpCash".equals(funCode)){//提现申请
				returnStr = makeLessonInfoDOM.doUpCash(jsonObj);
			}else if("teaAccountInfo".equals(funCode)){//老师账户信息
				returnStr = makeLessonInfoDOM.teaAccountInfo(jsonObj);
			}else if("queryTeacherHaveClassesInfo".equals(funCode)){//老师个人信息
				returnStr = makeLessonInfoDOM.teaPersonInfo(jsonObj);
			}else if("getPrepareClassesList".equals(funCode)){ //获取未发送陪练单/已发送陪练单
				returnStr = prepareClassesService.getPrepareClassesList(jsonObj);
			}else if ("saveCommentDetailsInit".equals(funCode)){ //陪练单编辑初始化
				returnStr = commentDetailsService.saveCommentDetailsInit(jsonObj);
			}else if ("saveCommentDetails".equals(funCode)){ //陪练单保存-老师端
				returnStr = commentDetailsService.saveCommentDetails(jsonObj);
			}else if("initClass".equals(funCode)){
				returnStr = plClassesDom.initClasses(jsonObj);
			}else if("finish".equals(funCode)){
				returnStr = plClassesDom.finishClasses(jsonObj);
			}else if ("showCommentDetails".equals(funCode)){ //展示陪练单
				returnStr = commentDetailsService.showCommentDetails(jsonObj);
			}else if ("sendCommentDetails".equals(funCode)){ //老师向学生发送陪练单
				returnStr = commentDetailsService.sendCommentDetails(jsonObj);
			}else if ("saveTeacherCommentInit".equals(funCode)){ //学生对老师的评价初始化
				returnStr = commentDetailsService.saveTeacherCommentInit(jsonObj);
			}else if ("saveTeacherComment".equals(funCode)){ //保存学生对老师的评价
				returnStr = commentDetailsService.saveTeacherComment(jsonObj);
			}else if ("getTeacherCourseWareList".equals(funCode)){ // 获取老师端课件列表
				returnStr = plMusicService.getTeacherCourseWareList(jsonObj);
			}else if ("getTeacherCourseWare".equals(funCode)){ //获取课件详情
				returnStr = plMusicService.getTeacherCourseWare(jsonObj);
			}else if ("saveTeacherCourseWare".equals(funCode)){ //老师端课件保存
				returnStr = plMusicService.saveTeacherCourseWare(jsonObj);
			}else if ("deleteTeacherCourseWare".equals(funCode)){ //删除课件信息
				returnStr = plMusicService.deleteTeacherCourseWare(jsonObj);
			}else if ("getTeacherComment".equals(funCode)){ //查询学生对老师的评价
				returnStr = commentDetailsService.getTeacherComment(jsonObj);
			}else if ("getStudentPrepareClasses".equals(funCode)){ //老师端-获取学生备课信息
				returnStr = prepareClassesService.getStudentPrepareClasses(jsonObj);
			}else if ("saveStudentPrepareClasses".equals(funCode)){ //保存老师选择的课件信息
				returnStr = prepareClassesService.saveStudentPrepareClasses(jsonObj);
			}else if ("initMusicInfo".equals(funCode)){ //乐谱管理初始化
				returnStr = plMusicService.initMusicInfo(jsonObj);
			}else if ("saveMusicInfo".equals(funCode)){ //乐谱管理添加
				returnStr = plMusicService.saveMusicInfo(jsonObj);
			}else if ("getDateList".equals(funCode)){ //查询当天课程预约情况
				returnStr = plClassesService.getDateList(jsonObj);
			}else if("enterClassesRoom".equals(funCode)){
				returnStr = plClassesDom.enterClassesRoom(jsonObj);
			}else if ("getTeacherList".equals(funCode)){
				returnStr = makeLessonInfoService.getTeacherList(jsonObj);
			} else if ("getStsToken".equals(funCode)){
				returnStr = AliOSSUtils.getStsToken(jsonObj.getString("roleSessionName")).toString();
			}else if ("getDateByTeacher".equals(funCode)){
				returnStr = plClassesService.getDateByTeacher(jsonObj);
			}
			else {
				returnStr = "{\"resultCode\":\"1\",\"resultMessage\":\"未找到对应key值\"}";
			}
			logger.debug("返回app的出参是returnStr=" + returnStr);
			if("app".equals(type)) {
				returnStr = EncryptUtil.encryptThreeDESECB(returnStr);
			}
			logger.debug("加密出参是returnStr=" + returnStr);
		} catch (Exception e) {
			e.printStackTrace();
			retWrongObj.put("resultCode", "1");
			retWrongObj.put("resultMessage", "系统异常！！");
			return retObj.toJSONString();
		}
		return returnStr;
	}*/

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
