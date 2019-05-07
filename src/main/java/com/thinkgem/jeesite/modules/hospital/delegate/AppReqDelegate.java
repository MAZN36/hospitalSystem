package com.thinkgem.jeesite.modules.hospital.delegate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hospital.entity.HCharge;
import com.thinkgem.jeesite.modules.hospital.entity.HDiagnoseInfo;
import com.thinkgem.jeesite.modules.hospital.entity.HDoctor;
import com.thinkgem.jeesite.modules.hospital.entity.HDrugInfo;
import com.thinkgem.jeesite.modules.hospital.entity.HPatient;
import com.thinkgem.jeesite.modules.hospital.entity.HPrescriptInfo;
import com.thinkgem.jeesite.modules.hospital.entity.HRegistration;
import com.thinkgem.jeesite.modules.hospital.service.HChargeService;
import com.thinkgem.jeesite.modules.hospital.service.HDiagnoseInfoService;
import com.thinkgem.jeesite.modules.hospital.service.HDoctorService;
import com.thinkgem.jeesite.modules.hospital.service.HDrugInfoService;
import com.thinkgem.jeesite.modules.hospital.service.HPatientService;
import com.thinkgem.jeesite.modules.hospital.service.HPrescriptInfoService;
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

    @Autowired
    private HDiagnoseInfoService diagnoseInfoService;

    @Autowired
    private HPrescriptInfoService prescriptInfoService;

    @Autowired
    private HChargeService chargeService;

    @Autowired
    HDrugInfoService drugInfoService;

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

    /**
     * 获取诊断之后的病人列表
     * @return
     */
    public String diagnosePatientInfoList(JSONObject jsonObj){
        logger.info("开始获取诊断之后的病人列表...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        String docId = jsonObj.getString("loginId");
        //获取诊断信息
        HDiagnoseInfo diagnoseInfo = new HDiagnoseInfo();
        diagnoseInfo.setDoctorId(docId);
        List<HDiagnoseInfo> diagnoseInfoList = diagnoseInfoService.findList(diagnoseInfo);
        JSONArray diagnoseInfoArray = new JSONArray();
        JSONObject diagnoseInfoJson = null;
        JSONArray prescriptionArray = null;
        JSONObject prescriptionJson = null;
        if (diagnoseInfoList!=null){
            for(HDiagnoseInfo hDiagnoseInfo : diagnoseInfoList){
                diagnoseInfoJson = new JSONObject();
                //获取病人信息
                HPatient patient = patientService.get(hDiagnoseInfo.getPatientId());
                if (patient!=null&&patient.getUser()!=null){
                    diagnoseInfoJson.put("patientId",patient.getId());
                    diagnoseInfoJson.put("name",patient.getUser().getName());
                    diagnoseInfoJson.put("gender",DictUtils.getDictLabel(patient.getUser().getSex(),"sex","男"));
                    diagnoseInfoJson.put("age",patient.getUser().getAge());
                    //症状
                    diagnoseInfoJson.put("Symptom",hDiagnoseInfo.getSymptom());
                    diagnoseInfoJson.put("Diagnosis",hDiagnoseInfo.getDiagnoses());
                    //处方
                    HPrescriptInfo hPrescriptInfo = new HPrescriptInfo();
                    hPrescriptInfo.setDiagnoseId(hDiagnoseInfo.getId());
                    List<HPrescriptInfo> prescriptInfoList = prescriptInfoService.findList(hPrescriptInfo);
                    if (prescriptInfoList!=null){
                        prescriptionArray = new JSONArray();
                        for (HPrescriptInfo prescriptInfo : prescriptInfoList){
                            HDrugInfo drugInfo = prescriptInfo.getDrugInfo();
                            if (drugInfo!=null){
                                prescriptionJson = new JSONObject();
                                prescriptionJson.put("drugName",drugInfo.getDrugName());
                                prescriptionJson.put("usage",prescriptInfo.getDurgUsage());
                                prescriptionJson.put("num",prescriptInfo.getDurgNum());
                                prescriptionArray.add(prescriptionJson);
                            }
                        }
                        diagnoseInfoJson.put("prescription",prescriptionArray);
                    }
                    //获取医生信息
                    HDoctor doctor = doctorService.get(docId);
                    if (doctor!=null&&doctor.getUser()!=null){
                        diagnoseInfoJson.put("docId",doctor.getId());
                        diagnoseInfoJson.put("doc",doctor.getUser().getName());
                    }
                    diagnoseInfoArray.add(diagnoseInfoJson);
                }
            }
        }
        resultJson.put("patientList",diagnoseInfoArray);
        resultJson.putAll(R.appOk());
        logger.info("结束获取诊断之后的病人列表...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * TODO 实现DM
     * 一键开药方
     * @param jsonObj
     * @return
     */
    public String makePrescription(JSONObject jsonObj){
        logger.info("开始一键开药方...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        String symptom = jsonObj.getString("symptom");
        String diagnosis = jsonObj.getString("diagnosis");
        HPrescriptInfo hPrescriptInfo = new HPrescriptInfo();
        List<HPrescriptInfo> prescriptInfoList = prescriptInfoService.findList(hPrescriptInfo);
        JSONArray prescriptionArray = new JSONArray();
        JSONObject prescriptionJson = null;
        if (prescriptInfoList!=null){
            for (HPrescriptInfo prescriptInfo: prescriptInfoList){
                HDrugInfo drugInfo = prescriptInfo.getDrugInfo();
                if (drugInfo!=null){
                    prescriptionJson = new JSONObject();
                    prescriptionJson.put("drugName",drugInfo.getDrugName());
                    prescriptionJson.put("usage",prescriptInfo.getDurgUsage());
                    prescriptionJson.put("num",prescriptInfo.getDurgNum());
                    prescriptionArray.add(prescriptionJson);
                }
            }
        }
        resultJson.put("prescriptionList",prescriptionArray);
        resultJson.putAll(R.appOk());
        logger.info("结束一键开药方...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 确认开处方
     * @param jsonObj
     * @return
     */
    public String savePrescriptionList(JSONObject jsonObj){
        logger.info("开始确认开处方...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        String symptom = jsonObj.getString("symptom");
        String registrationId = jsonObj.getString("registrationId");
        String remarks = jsonObj.getString("remarks");
        String diagnosis = jsonObj.getString("diagnosis");
        JSONArray prescriptionList = jsonObj.getJSONArray("prescriptionList");//药方信息列表
        HRegistration hRegistration = registrationService.get(registrationId);
        double receivePrice = 0;
        if (hRegistration!=null&&hRegistration.getOffice()!=null){
            //生成诊断信息
            HDiagnoseInfo diagnoseInfo = new HDiagnoseInfo();
            diagnoseInfo.setPatientId(hRegistration.getPatientId());
            diagnoseInfo.setRegistrationId(hRegistration.getId());
            diagnoseInfo.setDoctorId(hRegistration.getDoctorId());
            diagnoseInfo.setDeptId(hRegistration.getOffice().getId());
            diagnoseInfo.setSymptom(symptom);
            diagnoseInfo.setDiagnoses(diagnosis);
            diagnoseInfo.setRemarks(remarks);
            diagnoseInfoService.save(diagnoseInfo);
            receivePrice = hRegistration.getPrice();
            hRegistration.setSts("C");
            registrationService.save(hRegistration);
            //生成收费单信息
            HCharge hCharge = new HCharge();
            hCharge.setPatientId(hRegistration.getPatientId());
            hCharge.setDiagnoseId(diagnoseInfo.getId());
            hCharge.setReceivePrice(receivePrice);
            hCharge.setPaymentNo(DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+"0000"+hRegistration.getQueueNo());
            hCharge.setSts("A");
            chargeService.save(hCharge);
            //生成处方信息
            if (prescriptionList!=null){
                for (Object obj : prescriptionList){
                    JSONObject prescriptionJson = (JSONObject) obj;
                    String drugId = prescriptionJson.getString("drugId");
                    String usage = prescriptionJson.getString("usage");
                    Integer num = prescriptionJson.getInteger("num");
                    num = num==null ? 0 : num;
                    HDrugInfo hDrugInfo = drugInfoService.get(drugId);
                    if (hDrugInfo!=null){
                        HPrescriptInfo prescriptInfo = new HPrescriptInfo();
                        prescriptInfo.setDiagnoseId(diagnoseInfo.getId());
                        prescriptInfo.setChargeId(hCharge.getId());
                        prescriptInfo.setDurgId(hDrugInfo.getId());
                        prescriptInfo.setDurgNum(num);
                        prescriptInfo.setDurgUsage(usage);
                        prescriptInfoService.save(prescriptInfo);
                        //更新药品库数量
                        int inventoryNum = hDrugInfo.getInventoryNum() - num;
                        hDrugInfo.setInventoryNum(inventoryNum<0 ? 0 : inventoryNum);
                        drugInfoService.save(hDrugInfo);
                        Double sellPrice = hDrugInfo.getSellPrice()==null ? 0 : hDrugInfo.getSellPrice();
                        receivePrice += sellPrice*num;
                    }
                }
                //更新收费信息
                hCharge.setReceivePrice(receivePrice);
                chargeService.save(hCharge);
            }
            resultJson.putAll(R.appOk());
        }else {
            resultJson.putAll(R.appOk("1","挂号信息不存在！"));
        }
        logger.info("结束确认开处方...请求参数为："+jsonObj);
        return resultJson.toString();
    }

    /**
     * 获取药品列表
     * @param jsonObj
     * @return
     */
    public String drugInfoList(JSONObject jsonObj){
        logger.info("开始获取药品列表...请求参数为："+jsonObj);
        JSONObject resultJson = new JSONObject();
        HDrugInfo hDrugInfo = new HDrugInfo();
        hDrugInfo.setSts("A");
        List<HDrugInfo> drugInfoList = drugInfoService.findList(hDrugInfo);
        JSONArray drugInfoArray = new JSONArray();
        JSONObject drugInfoJson = null;
        if (drugInfoList!=null){
            for (HDrugInfo drugInfo : drugInfoList){
                drugInfoJson = new JSONObject();
                drugInfoJson.put("drugId",drugInfo.getId());
                drugInfoJson.put("drugName",drugInfo.getDrugName());
                drugInfoJson.put("sellPrice",drugInfo.getSellPrice());
                drugInfoJson.put("inventoryNum",drugInfo.getInventoryNum());
                drugInfoJson.put("drugRemarks",drugInfo.getGrugRemarks());
                drugInfoArray.add(drugInfoJson);
            }
        }
        resultJson.put("drugList",drugInfoArray);
        resultJson.putAll(R.appOk());
        logger.info("结束获取药品列表...请求参数为："+jsonObj);
        return resultJson.toString();
    }
}
