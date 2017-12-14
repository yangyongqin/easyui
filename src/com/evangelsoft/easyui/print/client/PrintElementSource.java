package com.evangelsoft.easyui.print.client;

public class PrintElementSource {

	/**
	 * 参数
	 */
	public static String PARAMETER ="P";

	/**
	 * 表数据
	 */
	public static String TABLE ="T";

	/**
	 * 数据来源
	 */
	private String sourceType;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * 显示文本
	 */
	private String text;
	/**
	 * 数据类型
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
