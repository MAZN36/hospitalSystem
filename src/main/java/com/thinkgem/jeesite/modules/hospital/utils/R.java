package com.thinkgem.jeesite.modules.hospital.utils;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public static Map<String, Object> appOk() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", "0");
		map.put("resultMsg", "操作成功!");
		return map;
	}
	public static Map<String, Object> appOk(String code, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", code);
		map.put("resultMsg", msg);
		return map;
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
