package com.thinkgem.jeesite.modules.hospital.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AppParamUtils {
	// app参数入口，所有app入参从这里获取压缩
/*	public String getRequestStr(HttpServletRequest request) {
		String strDecrypted = null;
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
			sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
//			strDecrypted = EncryptUtil.decryptThreeDESECB(sb.toString());
			strDecrypted = sb.toString();
			System.out.println("strDecrypted=" + strDecrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDecrypted;
	}*/

	// web-H5调用入口
	public String getJsonData(HttpServletRequest request) {
		String strDecrypted = null;
		StringBuffer sb = new StringBuffer();
		try {
			String requestJson = request.getParameter("H5params");
			if (requestJson != null) { // 如果API调用
				// String requestJson = request.getParameter("data");
				strDecrypted = requestJson;
			} else {
				BufferedReader br = new BufferedReader(
						new InputStreamReader((ServletInputStream) request.getInputStream(), "UTF-8"));
				sb = new StringBuffer("");
				String temp;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
				}
				br.close();
				JSONObject obj = new JSONObject();
				obj = JSONObject.parseObject(sb.toString());
				strDecrypted = sb.toString();
//				String H5params = obj.getString("H5params");
//				strDecrypted = H5params;
//				strDecrypted = H5params;
				// // 解密数据
				// strDecrypted = EncryptUtil.decryptThreeDESECB(sb.toString());
//				System.out.println("strDecrypted=" + strDecrypted);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDecrypted;
	}

}
