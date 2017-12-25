package com.evangelsoft.easyui.print.client;

public class PrintElementType {

	public static String CHART = "0";

	public static String IMAGE = "1";

	public static String TABLE = "2";

	public static String LINE = "3";

	public static String LABEL = "4";

	public static String TEXT = "5";

	public static String BAR_CODE = "6";

	public static String TABLE_HEAD = "7";

	public static String TABLE_CELL = "8";

	/**
	 * 类型， 0图表,1图片,2表格,3线条,4 标签 静态文本,5文本 值控件,6条码 值控件,7表头，8表格
	 */
	private String type;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 图片路径url
	 */
	private String icon;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public PrintElementType(String type, String desc, String icon) {
		super();
		this.type = type;
		this.desc = desc;
		this.icon = icon;
		this.text = desc;
	}

	@Override
	public String toString() {
		return desc;
	}

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

}
