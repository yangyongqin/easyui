package com.evangelsoft.easyui.print.client;

public class PrintElementSource {

	/**
	 * ����
	 */
	public static String PARAMETER ="P";

	/**
	 * ������
	 */
	public static String TABLE ="T";

	/**
	 * ������Դ
	 */
	private String sourceType;
	/**
	 * ����
	 */
	private String tableName;
	/**
	 * �ֶ���
	 */
	private String fieldName;
	/**
	 * ��ʾ�ı�
	 */
	private String text;
	/**
	 * ��������
	 */
	private String dataType;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return text+"<"+fieldName+">";
	}

}
