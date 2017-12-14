package com.evangelsoft.easyui.template.client;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.evangelsoft.econnect.dataformat.Record;

/** * @author  ������
 E-mail:
@date ��2016-3-23 ����10:28:30
@version 1.0   * @since
������
 */
public class UIItem {
	private JLabel label;
	private JComponent ompContent;

	private String template_id;//ģ��id;nu;8;0;0;u
	private String 	attribute_id;//����;vc;50;0;10;u
	private String  attribute_name;//������;vc;50;0;10;u
	private String sequence;//��ʾ˳��;nu;4;0;0;u
	private int width=1;//���;nu;4;0;0;u
	private int height;//�߶�;nu;4;0;0;u
	private int length;//���ݳ���;nu;6;0;6;n
	private double precise;//����;nu;6;0;6;n
	private String isMust;//�Ƿ����;vc;4;0;10;u
	private String editIsMust;//�༭�Ƿ����;vc;4;0;10;u
	private String defaultValue;//Ĭ��ֵ;vc;2000;0;30;n
	private String viewColor;//��ʾ��ɫ;vc;10;0;10;u
	private String dataType;//��������;vc;50;0;10;
	private String dataType_desc;//��������;vc;50;0;10;u
	private String viewType;//��ʾ��ʽ;vc;50;0;10;u
	private String viewTypeDesc;//��ʾ��ʽ;vc;50;0;10;u
	private String viewFormula;//��ʾ��ʽ;vc;50;0;10;u
	private String editorFormula;//�༭��ʽ;vc;50;0;10;u
	private String validateFormula;//��֤��ʽ;vc;50;0;10;u
	private String isTotal;//�Ƿ�ϼ�;vc;4;0;10;u
	private String isCardShow;//��Ƭ�Ƿ���ʾ;vc;4;0;10;u
	private String isListShow;//�б��Ƿ���ʾ;vc;4;0;10;u
	private String def1;//�Զ���1;vc;50;0;10;u
	private String def2;//�Զ���2;vc;50;0;10;u
	private String def3;//�Զ���3;vc;50;0;10;u

	UIItem(Record record){

	}

	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public JComponent getOmpContent() {
		return ompContent;
	}
	public void setOmpContent(JComponent ompContent) {
		this.ompContent = ompContent;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getAttribute_id() {
		return attribute_id;
	}
	public void setAttribute_id(String attribute_id) {
		this.attribute_id = attribute_id;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
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
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getPrecise() {
		return precise;
	}
	public void setPrecise(double precise) {
		this.precise = precise;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public String getEditIsMust() {
		return editIsMust;
	}
	public void setEditIsMust(String editIsMust) {
		this.editIsMust = editIsMust;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getViewColor() {
		return viewColor;
	}
	public void setViewColor(String viewColor) {
		this.viewColor = viewColor;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataType_desc() {
		return dataType_desc;
	}
	public void setDataType_desc(String dataType_desc) {
		this.dataType_desc = dataType_desc;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getViewTypeDesc() {
		return viewTypeDesc;
	}
	public void setViewTypeDesc(String viewTypeDesc) {
		this.viewTypeDesc = viewTypeDesc;
	}
	public String getViewFormula() {
		return viewFormula;
	}
	public void setViewFormula(String viewFormula) {
		this.viewFormula = viewFormula;
	}
	public String getEditorFormula() {
		return editorFormula;
	}
	public void setEditorFormula(String editorFormula) {
		this.editorFormula = editorFormula;
	}
	public String getValidateFormula() {
		return validateFormula;
	}
	public void setValidateFormula(String validateFormula) {
		this.validateFormula = validateFormula;
	}
	public String getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}
	public String getIsCardShow() {
		return isCardShow;
	}
	public void setIsCardShow(String isCardShow) {
		this.isCardShow = isCardShow;
	}
	public String getIsListShow() {
		return isListShow;
	}
	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}
	public String getDef1() {
		return def1;
	}
	public void setDef1(String def1) {
		this.def1 = def1;
	}
	public String getDef2() {
		return def2;
	}
	public void setDef2(String def2) {
		this.def2 = def2;
	}
	public String getDef3() {
		return def3;
	}
	public void setDef3(String def3) {
		this.def3 = def3;
	}
}
