package com.thinkgem.jeesite.modules.hospital.delegate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.entity.HPatient;
import com.thinkgem.jeesite.modules.hospital.entity.HRegistration;
import com.thinkgem.jeesite.modules.hospital.service.HDoctorService;
import com.thinkgem.jeesite.modules.hospital.service.HPatientService;
import com.thinkgem.jeesite.modules.hospital.service.HRegistrationService;
import com.thinkgem.jeesite.modules.hospital.utils.R;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AppReqDelegate {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OfficeService officeService;

    @Autowired
    private HDoctorService doctorService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private HPatientService patientService;

    @Autowired
    private HRegistrationService registrationService;

    /**
     * 获取科室列表
     * @param jsonObj
     * @return
     */
    public String getDeptList(JSONObject jsonObj){
        logger.info("开始获取科室列表...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        Office dept = new Office();
        dept.setId("1");
        Office parentDept = new Office();
        parentDept.setParent(dept);
        parentDept.setDelFlag("0");
        JSONArray depList = new JSONArray();
        JSONObject depJson = null;
        JSONObject parentDepJson = null;
        JSONArray subDepArray = null;
        JSONObject subDepJson = null;

        List<Office> parentDeptList = officeService.findByParentId(parentDept);
        if (parentDeptList!=null){
            for (Office tempDept : parentDeptList){
                depJson = new JSONObject();
                parentDepJson = new JSONObject();
                parentDepJson.put("name",tempDept.getName());
                parentDepJson.put("id",tempDept.getId());
                depJson.put("parentDep",parentDepJson);
                parentDept.setParent(tempDept);
                List<Office> subDeptList = officeService.findByParentId(parentDept);
                if (subDeptList!=null){
                    subDepArray = new JSONArray();
                    for (Office subDept : subDeptList){
                        subDepJson = new JSONObject();
                        subDepJson.put("name",subDept.getName());
                        subDepJson.put("id",subDept.getId());
                        subDepArray.add(subDepJson);
                    }
                    depJson.put("subDep",subDepArray);
                }
                depList.add(depJson);
            }
        }
        resultJson.put("depList",depList);
        resultJson.putAll(R.appOk());
        logger.info("结束获取科室列表...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 获取医生列表
     * @param jsonObj
     * @return
     */
    public String getDoctorList(JSONObject jsonObj){
        logger.info("开始获取医生列表...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        JSONArray docArray = new JSONArray();
        JSONObject docJson = null;
        String depId = jsonObj.getString("depId");
        Office office = new Office();
        office.setId(depId);
        User user = new User();
        user.setOffice(office);
        HDoctor doctor = new HDoctor();
        doctor.setSts("1");
        doctor.setUser(user);
        List<HDoctor> doctorList = doctorService.findList(doctor);
        if (doctorList!=null){
            for (HDoctor hDoctor : doctorList){
                User doctorUser = hDoctor.getUser();
                if (doctorUser!=null){
                    docJson = new JSONObject();
                    docJson.put("img",doctorUser.getPhoto());
                    docJson.put("docId",hDoctor.getId());
                    docJson.put("name",doctorUser.getName());
                    Office office1 = doctorUser.getOffice();
                    if (office1!=null){
                        docJson.put("dep",office1.getName());
                    }
                    docJson.put("pro", DictUtils.getDictLabel(hDoctor.getJobName(),"job_type",""));
                    docArray.add(docJson);
                }
            }
        }
        resultJson.put("docList",docArray);
        resultJson.putAll(R.appOk());
        logger.info("结束获取医生列表...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * h5登录
     * @param jsonObj
     * @return
     */
    public String loginH5(JSONObject jsonObj){
        logger.info("开始登录...请求参数为："+jsonObj);
        String userName = jsonObj.getString("loginName");
        String passWord = jsonObj.getString("passWord");
        JSONObject resultJson = new JSONObject();
        try{
            User user = systemService.loginH5(userName, passWord);
            Role role = new Role();
            role.setUser(user);
            List<Role> roleList = systemService.findRole(role);
            if (roleList!=null&&roleList.size()>0){
                role = roleList.get(0);
                String enname = role.getEnname();
                if ("doctor".equals(enname)){ //医生
                    HDoctor doctor = new HDoctor();
                    doctor.setUser(user);
                    List<HDoctor> doctorList = doctorService.findList(doctor);
                    if (doctorList!=null&&doctorList.size()>0){
                        doctor = doctorList.get(0);
                        resultJson.put("loginId",doctor.getId());
                    }
                }else if ("patient".equals(enname)){ //病人
                    HPatient patient = new HPatient();
                    patient.setUser(user);
                    List<HPatient> patientList = patientService.findList(patient);
                    if (patientList!=null&&patientList.size()>0){
                        patient = patientList.get(0);
                        resultJson.put("loginId",patient.getId());
                    }
                }
                if (resultJson.containsKey("loginId")&& StringUtils.isBlank(resultJson.getString("loginId"))){
                    resultJson.putAll(R.appOk("1","该用户禁止登录"));
                }else {
                    resultJson.put("userId",user.getId());
                    resultJson.put("userName",user.getName());
                    resultJson.put("roleName",enname);
                    resultJson.putAll(R.appOk());
                }
            }else {
                resultJson.putAll(R.appOk("1","该用户禁止登录"));
            }
        }catch (Exception e){
            resultJson.putAll(R.appOk("1",e.getMessage()));
        }
        logger.info("结束登录...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 病人预约挂号
     * @param jsonObj
     * @return
     */
    public String registration(JSONObject jsonObj){
        logger.info("开始病人预约挂号...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        String docId = jsonObj.getString("docId");//医生
        String depId = jsonObj.getString("depId");//科室
        String loginId = jsonObj.getString("loginId");//用户名
        String userId = jsonObj.getString("userId");
        String bookDate = jsonObj.getString("bookDate");//预约时间
        HRegistration registration = new HRegistration();
        registration.setDoctorId(docId);
        Office office = new Office();
        office.setId(depId);
        registration.setOffice(office);
        registration.setPatientId(loginId);
        registration.setBookDate(DateUtils.parseDate(bookDate));
        List<HRegistration> registrationList = registrationService.findList(registration);
        if (registrationList!=null&&registrationList.size()>0){
            registration = registrationList.get(0);
            resultJson.putAll(R.appOk("1","你已经预约，请勿重复预约！"));
            resultJson.put("num",registration.getQueueNo());
        }else {
            registration.setPrice(Double.parseDouble(DictUtils.getDictLabels("A","registration_price","0")));
            registration.setRegistrationDate(new Date());
            Integer queueNo = registrationService.maxQueueNo();
            queueNo = queueNo==null ? 1 : (queueNo+1);
            registration.setQueueNo(queueNo.toString());
            resultJson.put("num",queueNo);
            registration.setSts("A");
            registrationService.save(registration);
            resultJson.putAll(R.appOk("0","预约成功！"));
        }
        HDoctor doctor = doctorService.get(docId);
        if (doctor!=null&&doctor.getUser()!=null){
            resultJson.put("doc",doctor.getUser().getName());
        }
        office = officeService.get(depId);
        if (office!=null){
            resultJson.put("dep",office.getName());
        }
        resultJson.put("time",bookDate);
        logger.info("结束病人预约挂号...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 获取医生端病人列表
     * @param jsonObj
     * @return
     */
    public String getRegistrationList(JSONObject jsonObj){
        logger.info("开始获取医生端病人列表...请求参数为："+jsonObj);
        String docId = jsonObj.getString("docId");
        JSONObject resultJson = new JSONObject();
        HRegistration registration = new HRegistration();
        registration.setBookDate(new Date());
        registration.setSts("AB");
        registration.setDoctorId(docId);
        List<HRegistration> registrationList = registrationService.findList(registration);
        JSONArray patientArray = new JSONArray();
        JSONObject patientJson = null;
        if(registrationList!=null){
            for (HRegistration hRegistration : registrationList){
                patientJson = new JSONObject();
                HPatient patient = patientService.get(hRegistration.getPatientId());
                if (patient!=null&&patient.getUser()!=null){
                    patientJson.put("registrationId",hRegistration.getId());
                    patientJson.put("name",patient.getUser().getName());
                    patientJson.put("identify",patient.getUser().getIdCard());
                    patientJson.put("gender",DictUtils.getDictLabel(patient.getUser().getSex(),"sex","男"));
                    patientJson.put("age",patient.getUser().getAge());
                    patientJson.put("time",DateUtils.formatDate(hRegistration.getBookDate()));
                    patientJson.put("sts",hRegistration.getSts());
                    patientArray.add(patientJson);
                }
            }
        }
        resultJson.put("patientList",patientArray);
        resultJson.putAll(R.appOk());
        logger.info("结束获取医生端病人列表...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 医生叫号
     * @return
     */
    public String callRegistration(JSONObject jsonObj){
        logger.info("开始医生叫号...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        String registrationId = jsonObj.getString("registrationId");
        String sts = jsonObj.getString("sts");
        HRegistration registration = registrationService.get(registrationId);
        if ("A".equals(sts)){
            registration.setSts("B");
            registrationService.save(registration);
        }
        resultJson.put("sts",registration.getSts());
        resultJson.putAll(R.appOk("0","叫号成功！"));
        logger.info("结束医生叫号...请求参数为："+jsonObj);
        return resultJson.toString();
    }
}
