package com.evangelsoft.easyui.template.client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.workbench.swing.JInternalDialog;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-21 下午11:41:32
@version 1.0   * @since    */
public class CodeValueDialog extends JInternalFrame{


	/**
	 *新增下拉选项的弹窗
	 */
	private static final long serialVersionUID = 1L;
	private JLabel keyLabel;
	private JTextField keyField;
	private JLabel valueLabel;
	private JTextField valueField;

	private String returnType;
	String value;
	CodeValueDialog(JComponent dialog,String value){
		this(dialog);
		this.valueField.setText(value);
		keyField.setVisible(false);
		keyLabel.setVisible(false);
		new JDialog();
	}
	CodeValueDialog(JComponent dialog){
		this.setTitle("添加新值");
//		this.setModal(true);
		this.getContentPane().setPreferredSize(new Dimension(280,130));
		keyLabel=new JLabel("显示值：");
		keyField=new JTextField();
		valueLabel=new JLabel("数据值：");
		valueField=new JTextField();
		GridBagLayout layout = new GridBagLayout();
		layout.rowHeights = new int[]{10,10,10,10,10,30};
		layout.columnWidths = new int[] { 5,5,5,5 };
		this.getContentPane().setLayout(layout);

		this.getContentPane().add(keyLabel, new GridBagConstraints(1, 1, 1, 1,
				0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(keyField, new GridBagConstraints(2, 1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.getContentPane().add(valueLabel, new GridBagConstraints(1,3, 1, 1,
				0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(valueField, new GridBagConstraints(2, 3, 1, 1,
				1, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		JPanel operatePanel=new JPanel();
		JButton	confirmButton=new JButton();
		confirmButton.setText("确认");
		confirmButton.addActionListener(new ConfirmAction());
		JButton	cancelButton=new JButton();
		cancelButton.setText("取消");
		operatePanel.add(confirmButton);
		operatePanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CodeValueDialog.this.dispose();
			}
		});
		this.getContentPane().add(operatePanel, new GridBagConstraints(1, 5, 3, 1,
				1, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		pack();
	}
	CodeValueDialog(JComponent dialog, String pageId,String pageName){
		this(dialog);
		keyField.setText(pageId);
		valueField.setText(pageName);
	}
	CodeValue codeValue;
	public static String select(JComponent com,String value,String type){
		CodeValueDialog codeValue=null;
		if(DataSourceConfig.KEY_VALUE.equals(type)){
			String[] str=value.split("=");
			codeValue=	new CodeValueDialog(com,str[0],str.length>1?str[1]:str[0]);
		}else{
			codeValue=new CodeValueDialog(com,value);
		}
		codeValue.returnType=type;
//		codeValue.setVisible(true);
		JInternalDialog.showAsDialog(com, codeValue);
		return codeValue.value;
	}

	public class ConfirmAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(returnType==DataSourceConfig.KEY_VALUE){
				value=keyField.getText().trim()+"="+valueField.getText().trim();
			}else{
				value=valueField.getText().trim();
			}
			CodeValueDialog.this.dispose();
		}
	}
	public static void main(String[] args) {
		select(null,"key=value","key");
		//		CodeValueDialog dialog=new CodeValueDialog("key","value");
		//		dialog.setVisible(true);
	}

}
