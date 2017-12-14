package com.evangelsoft.easyui.template.client.nc;

/**
 * 
 * 创建日期:(2003-2-27 14:35:42)
 * @author:赵继江
 */
public interface IWordType {
	
	int DELIMITER = 1;
	int OPERATOR = 2;
	int KEYWORD = 3;
	int CONSTANTS = 4;
	int STRING = 5;
	int VARIANT = 6;
	int METHOD = 7;
	int NOTE = 8;
	//
	int RESERVED1 = 11;
	int RESERVED2 = 12;
	int RESERVED3 = 13;
	int RESERVED4 = 14;
	int RESERVED5 = 15;
	int RESERVED6 = 16;
	int RESERVED7 = 17;
	int RESERVED8 = 18;
	int RESERVED9 = 19;
	//
	int DEFAULT = 0;

	//
	int MIN_TYPE = 0;
	int MAX_TYPE = 19;
}
