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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.evangelsoft.econnect.dataformat.CodeValue;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-21 下午11:41:32
@version 1.0   * @since    */
public class FieldDialog extends JDialog{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel pageIdLabel;
	private JTextField pageIdField;
	private JLabel pageNameLabel;
	private JTextField pageNameField;
	FieldDialog(){
		this.setTitle("添加自定义项");
		this.setModal(true);
		this.getContentPane().setPreferredSize(new Dimension(280,130));
		pageIdLabel=new JLabel("字段ID");
		pageIdField=new JTextField();
		pageNameLabel=new JLabel("字段名称");
		pageNameField=new JTextField();

		GridBagLayout layout = new GridBagLayout();
		layout.rowHeights = new int[]{10,10,10,10,10,30};
		layout.columnWidths = new int[] { 5,5,5,5 };
		this.getContentPane().setLayout(layout);

		this.getContentPane().add(pageIdLabel, new GridBagConstraints(1, 1, 1, 1,
				0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(pageIdField, new GridBagConstraints(2, 1, 1, 1,
				1, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.getContentPane().add(pageNameLabel, new GridBagConstraints(1,3, 1, 1,
				0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(pageNameField, new GridBagConstraints(2, 3, 1, 1,
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
				FieldDialog.this.dispose();
			}
		});
		this.getContentPane().add(operatePanel, new GridBagConstraints(1, 5, 3, 1,
				1, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		pack();
	}
	CodeValue codeValue;
	public static CodeValue select(JComponent com){
		FieldDialog dialog=new FieldDialog();
		dialog.setLocationRelativeTo(com);
		dialog.setVisible(true);
		return dialog.codeValue;

	}

	public class ConfirmAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			codeValue=new CodeValue(pageIdField.getText(),pageNameField.getText());
			FieldDialog.this.dispose();
		}
	}

}
