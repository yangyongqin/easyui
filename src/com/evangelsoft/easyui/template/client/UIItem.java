package com.evangelsoft.easyui.template.client;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.evangelsoft.econnect.dataformat.Record;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-23 下午10:28:30
@version 1.0   * @since
面板组件
 */
public class UIItem {
	private JLabel label;
	private JComponent ompContent;

	private String template_id;//模板id;nu;8;0;0;u
	private String 	attribute_id;//属性;vc;50;0;10;u
	private String  attribute_name;//属性名;vc;50;0;10;u
	private String sequence;//显示顺序;nu;4;0;0;u
	private int width=1;//宽度;nu;4;0;0;u
	private int height;//高度;nu;4;0;0;u
	private int length;//数据长度;nu;6;0;6;n
	private double precise;//精度;nu;6;0;6;n
	private String isMust;//是否必须;vc;4;0;10;u
	private String editIsMust;//编辑是否必须;vc;4;0;10;u
	private String defaultValue;//默认值;vc;2000;0;30;n
	private String viewColor;//显示颜色;vc;10;0;10;u
	private String dataType;//数据类型;vc;50;0;10;
	private String dataType_desc;//数据类型;vc;50;0;10;u
	private String viewType;//显示方式;vc;50;0;10;u
	private String viewTypeDesc;//显示方式;vc;50;0;10;u
	private String viewFormula;//显示公式;vc;50;0;10;u
	private String editorFormula;//编辑公式;vc;50;0;10;u
	private String validateFormula;//验证公式;vc;50;0;10;u
	private String isTotal;//是否合计;vc;4;0;10;u
	private String isCardShow;//卡片是否显示;vc;4;0;10;u
	private String isListShow;//列表是否显示;vc;4;0;10;u
	private String def1;//自定义1;vc;50;0;10;u
	private String def2;//自定义2;vc;50;0;10;u
	private String def3;//自定义3;vc;50;0;10;u

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
