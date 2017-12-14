package com.evangelsoft.easyui.template.client;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.evangelsoft.workbench.panelbase.ConditionValuePanel;

public class UIConditionLeafNodeBean
{
	private String key;
	private String columnId;
	private int index;
	private JLabel textLabel;
	private JButton cancelBtn;
	private JComboBox<String> operatorBox;
	private JToggleButton dataModeButton;
	private JToggleButton expremodeButton;
	private JPanel valuePanel;;
	private ConditionValuePanel conditionPanel;
	private ConditionValuePanel condition2Panel;
	private JPanel modePanel;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public JLabel getTextLabel() {
		return textLabel;
	}
	public void setTextLabel(JLabel textLabel) {
		this.textLabel = textLabel;
	}
	public JButton getCancelBtn() {
		return cancelBtn;
	}
	public void setCancelBtn(JButton cancelBtn) {
		this.cancelBtn = cancelBtn;
	}
	public JComboBox<String> getOperatorBox() {
		return operatorBox;
	}
	public void setOperatorBox(JComboBox<String> operatorBox) {
		this.operatorBox = operatorBox;
	}
	public JToggleButton getDataModeButton() {
		return dataModeButton;
	}
	public void setDataModeButton(JToggleButton dataModeButton) {
		this.dataModeButton = dataModeButton;
	}
	public JToggleButton getExpremodeButton() {
		return expremodeButton;
	}
	public void setExpremodeButton(JToggleButton expremodeButton) {
		this.expremodeButton = expremodeButton;
	}
	public JPanel getModePanel() {
		return modePanel;
	}
	public void setModePanel(JPanel modePanel) {
		this.modePanel = modePanel;
	}
	public JPanel getValuePanel() {
		return valuePanel;
	}
	public void setValuePanel(JPanel valuePanel) {
		this.valuePanel = valuePanel;
	}
	public ConditionValuePanel getConditionPanel() {
		return conditionPanel;
	}
	public void setConditionPanel(ConditionValuePanel conditionPanel) {
		this.conditionPanel = conditionPanel;
	}
	public ConditionValuePanel getCondition2Panel() {
		return condition2Panel;
	}
	public void setCondition2Panel(ConditionValuePanel condition2Panel) {
		this.condition2Panel = condition2Panel;
	}
}
