package com.evangelsoft.easyui.template.client;

import java.util.HashMap;

import com.evangelsoft.econnect.dataformat.CodeValue;

/** * @author  杨永钦
 E-mail:
@date ：2016-5-15 下午07:17:15
@version 1.0   * @since    */
public class DataSourceConfig {
//	public static int REFERENCE=1;
//	public static int COMBOBOX=2;

	public static String KEY_VALUE="K_V";
	public static String VALUE="VALUE";
	public static CodeValue KEV_CODEVALUE=new CodeValue(KEY_VALUE,"键值");
	public static CodeValue VALUE_CODEVALUE=new CodeValue(VALUE,"值");


	public static String SOURCE_CODE="CODE";//代码
	public static String SOURCE_PHRASE="PHRASE";//短语
	public static String SOURCE_FUNCTION="FUNCTION";//功能
//	public static String SOURCE_CONSTANT="CONSTANT";//常量
//	public static String SOURCE_VARIABLE="VARIABLE";//参数
//	public static String SOURCE_PARAMETER="PARAMETER";//参数
//	public static String SOURCE_SEQUENCE="SEQUENCE";//序号，编号
	public static String SOURCE_ZDY="ZDY";



	public static CodeValue codeValue=new CodeValue(SOURCE_CODE,"代码");
	public static CodeValue phraseCodeValue=new CodeValue(SOURCE_PHRASE,"短语");;
	public static CodeValue funcCodeValue=new CodeValue(SOURCE_FUNCTION,"功能");
	public static CodeValue zdyCodeValue=new CodeValue(SOURCE_ZDY,"自定义");
	public static CodeValue MASTER_CodeValue=new CodeValue(SOURCE_ZDY,"自定义");

	public static HashMap<String,CodeValue> sourceMap=new HashMap<String,CodeValue>();

	public static HashMap<String,CodeValue> returnTypeMap=new HashMap<String,CodeValue>();

	static{
		sourceMap.put(SOURCE_CODE, codeValue);
		sourceMap.put(SOURCE_PHRASE, phraseCodeValue);
		sourceMap.put(SOURCE_FUNCTION, funcCodeValue);
		sourceMap.put(SOURCE_ZDY, zdyCodeValue);


		returnTypeMap.put(KEY_VALUE, KEV_CODEVALUE);
		returnTypeMap.put(VALUE, VALUE_CODEVALUE);

	}


	private String valueType;//值类型，值，和  键值
	private String source;//来源，比如，代码，功能，
	private String sourceValue;//来源值
	private String codeType;//代码
	private String phraseType;//短语
	private String funcId;//功能ID
	private String zdyClass;//自定义类
	private String zdyItems;//自定义列表，这个是个字符串数组,以, 逗号分割
	private String refColumn;//对照字段 以, 逗号分割
	private String isEdit;//下拉框是否能编辑


	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceValue() {
		return sourceValue;
	}
	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getPhraseType() {
		return phraseType;
	}
	public void setPhraseType(String phraseType) {
		this.phraseType = phraseType;
	}
	public String getFuncId() {
		return funcId;
	}
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	public String getZdyClass() {
		return zdyClass;
	}
	public void setZdyClass(String zdyClass) {
		this.zdyClass = zdyClass;
	}
	public String getZdyItems() {
		return zdyItems;
	}
	public void setZdyItems(String zdyItems) {
		this.zdyItems = zdyItems;
	}
	public String getRefColumn() {
		return refColumn;
	}
	public void setRefColumn(String refColumn) {
		this.refColumn = refColumn;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}


}
