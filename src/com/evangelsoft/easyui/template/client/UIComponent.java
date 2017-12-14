package com.evangelsoft.easyui.template.client;


import javax.swing.JComponent;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-24 下午10:36:07
@version 1.0   * @since    */
public class UIComponent {

	public final String STRING_ID="COMPONENT_TYPE";
	public static String TEXT="TEXT";//文本
	public static String TEXTAREA="TEXTAREA";//文本域
	public static String COMBO="COMBO";//下拉
	public static String BOOL="BOOL";//逻辑
	public static String PASS_TEXT="PASS_TEXT";//密码框
	public static String DATE="DATE";//日期
	public static String TIME="TIME";//时间
	public static String DATATIME="DATATIME";//时间戳
	public static String IMAGE="IMAGE";//图片
	public static String REFERENCE="REFERENCE";//引用
	public static String OBJECT="OBJECT";//对象


	private ULabel lable;
	private JComponent component;
	private int sequence;//显示顺序;nu;4;0;0;u
	private int width=1;//宽度;nu;4;0;0;u
	private int height;//高度;nu;4;0;0;u
	private String isCardShow;

	public ULabel getLable() {
		return lable;
	}
	public void setLable(ULabel lable) {
		this.lable = lable;
	}
	public JComponent getComponent() {
		return component;
	}
	public void setComponent(JComponent component) {
		this.component = component;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getIsCardShow() {
		return isCardShow;
	}
	public void setIsCardShow(String isCardShow) {
		this.isCardShow = isCardShow;
	}

}
