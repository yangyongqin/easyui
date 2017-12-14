package com.evangelsoft.easyui.print.type;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PrintItemTool {

	static Map<String, Method> methodMap = new HashMap<String, Method>();
	static {
		// 根据注解获取反射信息
		Method[] methods = PrintItemTool.class.getDeclaredMethods();
		for (Method method : methods) {
			DataColumn loginTokenAnnotation = method.getDeclaringClass().getAnnotation(DataColumn.class);
			if (loginTokenAnnotation != null)
				methodMap.put(loginTokenAnnotation.dataColumn(), method);
		}
	}
}
