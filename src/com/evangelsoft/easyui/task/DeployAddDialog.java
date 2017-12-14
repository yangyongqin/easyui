package com.evangelsoft.easyui.task;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.template.client.SysTemplateFrame;
import com.evangelsoft.easyui.tool.DataTypeTool;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.dataformat.DataType;
import com.evangelsoft.swing.JTimePicker;


public class DeployAddDialog extends JDialog{

	/**
	 *
	 */
	private static final long serialVersionUID = 6530665711410176529L;

	private JTabbedPane tabbedPane;
	private JPanel mainPanel;
	private JLabel taskLabel;//任务
	private JTextField taskField;
	private JPanel taskJPanel;
	private JButton taskButton;

	private JLabel taskNameLabel;//任务
	private JTextField taskNameField;

	private JLabel taskDescLabel;//任务描述
	private JTextField taskDescField;

	private JLabel taskDeployLabel;
	private JComboBox<Object> taskDeployBox;

	private JLabel execClassLabel;//执行类
	private JTextField execClassField;

	StorageDataSet parmDetail;//协议单位消费历史
	private JPanel parmPanel;
	private TableScrollPane parmPane;
	private JdbTable parmTable;


	private JLabel triggerTypeLabel;//触发方式
	//立即触发
	private JRadioButton nowTriggerButton;//立即触发
	private JRadioButton timerTriggerButton;//定时器触发

	private JLabel triggerFreqLabel;//触发频率
	private JComboBox<Object> triggerFreqBox;

	private JLabel freqIntervaLabel;//间隔
	private JdbTextField freqIntervaField;
	private JLabel  tipLabel;//提示

	/*	private JPanel datePanel;//
	 */	private JPanel monthPanel;//月面板
	 private JPanel weekPanel;//周面板
	 private JPanel dayPanel;//日期

	 private JPanel oneDayPanel;//一天内

	 private JLabel onceTriggerLabel;//发生一次时间
	 private JRadioButton onceTriggerButton;
	 private JTimePicker triggerTimePicker;

	 private JRadioButton cycleTriggerButton;//周期发生频率
	 private JSpinner dayFreqIntervalSpinner;
	 private JTimePicker triggerbeginTime;
	 private JTimePicker triggerEndTime;

	 public DeployAddDialog(){
		 try{
			 initialization();
		 }catch(Exception e){

		 }
		 this.pack();
	 }

	 private void initialization(){

		 tabbedPane=new JTabbedPane();
		 mainPanel=new JPanel();
		 tabbedPane.add(mainPanel, "任务", 0);
		 this.getContentPane().add(tabbedPane);
		 final GridBagLayout mainGridBagLayout = new GridBagLayout();
		 mainGridBagLayout.rowHeights = new int[] { 5,5,5 };
		 mainGridBagLayout.columnWidths=new int[] { 5,5,5,5,5,5 };
		 mainPanel.setLayout(mainGridBagLayout);

		 //选择插件
		 taskLabel=new JLabel(DataModel.getDefault().getLabel("TASK_REG.TASK_CODE"));
		 taskField=new JTextField();
		 taskJPanel=new JPanel();
		 taskJPanel.setLayout(new BorderLayout());
		 taskButton=new JButton();
		 taskJPanel.add(taskField);
		 taskJPanel.add(taskButton,BorderLayout.EAST);
		 taskButton.setMargin(new Insets(0,0,0,0));
		 taskButton.setIcon(new ImageIcon(
				 SysTemplateFrame.class.getClassLoader().getResource(
				 "com/evangelsoft/workbench/resources/buttons/find.png")));
		 mainPanel.add(taskLabel, new GridBagConstraints(1, 1, 1, 1,
				 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));

		 mainPanel.add(taskJPanel,new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
				 GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		 taskNameLabel=new JLabel(DataModel.getDefault().getLabel("TASK_REG.TASK_CODE"));
		 taskNameField=new JTextField();

		 mainPanel.add(taskNameLabel, new GridBagConstraints(4, 1, 1, 1,
				 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));
		 mainPanel.add(taskNameField,new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
				 GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		 taskDescLabel=new JLabel(DataModel.getDefault().getLabel("TASK_REG.TASK_CODE"));
		 taskDescField=new JTextField();
		 mainPanel.add(taskDescLabel, new GridBagConstraints(7, 1, 1, 1,
				 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));
		 mainPanel.add(taskDescField,new GridBagConstraints(8, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
				 GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		 taskDeployLabel=new JLabel(DataModel.getDefault().getLabel("TASK_REG.TASK_CODE"));
		 taskDeployBox=new JComboBox<Object>();


		 mainPanel.add(taskDeployLabel, new GridBagConstraints(1, 3, 1, 1,
				 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));

		 mainPanel.add(taskDeployBox,new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
				 GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		 execClassLabel=new JLabel();
		 execClassLabel.setText(DataModel.getDefault().getLabel("TASK_REG.TASK_CLASS"));
		 execClassField=new JTextField();

		 mainPanel.add(execClassLabel, new GridBagConstraints(4, 3, 1, 1,
				 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));

		 mainPanel.add(execClassField,new GridBagConstraints(5, 3, 4, 1, 1.0, 0.0,GridBagConstraints.CENTER,
				 GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		 parmDetail=new StorageDataSet();

		 Column column = new Column();
		 column.setColumnName("PARM_NAME");

		 column.setScale(0);
		 column.setPrecision(50);
		 column.setWidth(20);
		 column.setDataType(DataType.TYPE_VARCHAR);
		 column.setCaption("参数名");

		 Column columnValue = new Column();
		 columnValue.setColumnName("PARM_VALE");

		 columnValue.setScale(0);
		 columnValue.setPrecision(50);
		 columnValue.setWidth(20);
		 columnValue.setDataType(DataType.TYPE_VARCHAR);
		 columnValue.setCaption("参数值");
		 parmDetail.setColumns(new Column[]{column,columnValue});
		 parmPanel=new JPanel();
		 parmTable=new JdbTable();
		 parmTable.setDataSet(parmDetail);
		 parmPane=new TableScrollPane(parmTable);
		 parmPanel.setLayout(new BorderLayout());
		 parmPanel.add(parmPane,BorderLayout.CENTER);

		 mainPanel.add(parmPanel,new GridBagConstraints(1, 5, 8, 1, 1.0, 1,GridBagConstraints.CENTER,
				 GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));



		 //d第二个面板，触发设置面板

	 }
	 public static void main(String[] args) {
		 DeployAddDialog dialog=new DeployAddDialog();
		 dialog.setVisible(true);
	}
}
