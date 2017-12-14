package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.borland.dbswing.JdbButton;
import com.borland.dbswing.JdbTextField;
import com.borland.dx.dataset.DataSet;

public class ReferencePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4027792514195258986L;

	private JdbTextField field;
	private JdbButton button;
	DataSet dataset;
	private String columnName;
	public	ReferencePanel(){
		field=new JdbTextField();
		button=new JdbButton();
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setIcon(new ImageIcon(
				this.getClass()
				.getClassLoader()
				.getResource(
						"com/evangelsoft/workbench/resources/buttons/find.png")));
		this.setLayout(new BorderLayout());
		this.add(field,BorderLayout.CENTER);
		this.add(button,BorderLayout.EAST);
	}
	public JdbTextField getField() {
		return field;
	}
	public void setField(JdbTextField field) {
		this.field = field;
	}
	public JdbButton getButton() {
		return button;
	}
	public void setButton(JdbButton button) {
		this.button = button;
	}
	public DataSet getDataSet() {
		return null;
	}
	public void setDataSet(DataSet dataset) {
		this.button.setDataSet(dataset);
		this.field.setDataSet(dataset);
		this.dataset=dataset;
	}
	public void setColumnName(String columnName){
		this.button.setColumnName(columnName);
		this.field.setColumnName(columnName);
//		this.field.setEditable(false);
//		this.button.setEnabled(false);
		this.columnName=columnName;
	}
	public String getColumnName() {
		return this.columnName;
	}
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.button.setEnabled(enabled);
		this.field.setEnabled(enabled);
	}

}
