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
	 * ���ͣ� 0ͼ��,1ͼƬ,2���,3����,4 ��ǩ ��̬�ı�,5�ı� ֵ�ؼ�,6���� ֵ�ؼ�,7��ͷ��8���
	 */
	private String type;

	/**
	 * ����
	 */
	private String desc;

	/**
	 * ͼƬ·��url
	 */
	private String icon;

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
