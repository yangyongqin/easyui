package com.evangelsoft.easyui.template.client;

import java.util.HashMap;

import com.evangelsoft.econnect.dataformat.CodeValue;

/** * @author  ������
 E-mail:
@date ��2016-5-15 ����07:17:15
@version 1.0   * @since    */
public class DataSourceConfig {
//	public static int REFERENCE=1;
//	public static int COMBOBOX=2;

	public static String KEY_VALUE="K_V";
	public static String VALUE="VALUE";
	public static CodeValue KEV_CODEVALUE=new CodeValue(KEY_VALUE,"��ֵ");
	public static CodeValue VALUE_CODEVALUE=new CodeValue(VALUE,"ֵ");


	public static String SOURCE_CODE="CODE";//����
	public static String SOURCE_PHRASE="PHRASE";//����
	public static String SOURCE_FUNCTION="FUNCTION";//����
//	public static String SOURCE_CONSTANT="CONSTANT";//����
//	public static String SOURCE_VARIABLE="VARIABLE";//����
//	public static String SOURCE_PARAMETER="PARAMETER";//����
//	public static String SOURCE_SEQUENCE="SEQUENCE";//��ţ����
	public static String SOURCE_ZDY="ZDY";



	public static CodeValue codeValue=new CodeValue(SOURCE_CODE,"����");
	public static CodeValue phraseCodeValue=new CodeValue(SOURCE_PHRASE,"����");;
	public static CodeValue funcCodeValue=new CodeValue(SOURCE_FUNCTION,"����");
	public static CodeValue zdyCodeValue=new CodeValue(SOURCE_ZDY,"�Զ���");
	public static CodeValue MASTER_CodeValue=new CodeValue(SOURCE_ZDY,"�Զ���");

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


	private String valueType;//ֵ���ͣ�ֵ����  ��ֵ
	private String source;//��Դ�����磬���룬���ܣ�
	private String sourceValue;//��Դֵ
	private String codeType;//����
	private String phraseType;//����
	private String funcId;//����ID
	private String zdyClass;//�Զ�����
	private String zdyItems;//�Զ����б�����Ǹ��ַ�������,��, ���ŷָ�
	private String refColumn;//�����ֶ� ��, ���ŷָ�
	private String isEdit;//�������Ƿ��ܱ༭


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
