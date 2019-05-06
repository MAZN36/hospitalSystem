package com.thinkgem.jeesite.modules.hospital.delegate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.hospital.utils.R;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppReqDelegate {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OfficeService officeService;

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
}
