package com.evangelsoft.easyui.template.client;


import javax.swing.JComponent;

/** * @author  ������
 E-mail:
@date ��2016-3-24 ����10:36:07
@version 1.0   * @since    */
public class UIComponent {

	public final String STRING_ID="COMPONENT_TYPE";
	public static String TEXT="TEXT";//�ı�
	public static String TEXTAREA="TEXTAREA";//�ı���
	public static String COMBO="COMBO";//����
	public static String BOOL="BOOL";//�߼�
	public static String PASS_TEXT="PASS_TEXT";//�����
	public static String DATE="DATE";//����
	public static String TIME="TIME";//ʱ��
	public static String DATATIME="DATATIME";//ʱ���
	public static String IMAGE="IMAGE";//ͼƬ
	public static String REFERENCE="REFERENCE";//����
	public static String OBJECT="OBJECT";//����


	private ULabel lable;
	private JComponent component;
	private int sequence;//��ʾ˳��;nu;4;0;0;u
	private int width=1;//���;nu;4;0;0;u
	private int height;//�߶�;nu;4;0;0;u
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
