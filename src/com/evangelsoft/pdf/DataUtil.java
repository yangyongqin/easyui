package com.evangelsoft.pdf;

import org.apache.commons.lang.StringUtils;

public class DataUtil {

	/**
	* �ָ�·��
	* @param path
	* @return ���طָ���·��
	*/
	public static String[] separatePath(String path) {
		if (StringUtils.isBlank(path)) {
			return null;
		}
		String[] sep = path.split("\\.");
		return new String[] { sep[0], sep[1] };
	}

}