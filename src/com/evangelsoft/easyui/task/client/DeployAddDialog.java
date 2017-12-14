package com.evangelsoft.easyui.task.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.JTextComponent;

import com.borland.dbswing.JdbButton;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbDateTimePicker;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.JdbTimePicker;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.EditAdapter;
import com.borland.dx.dataset.ExceptionEvent;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.template.client.SysTemplateFrame;
import com.evangelsoft.easyui.tool.DateUtil;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.framebase.SingleDataSetFrame;
import com.evangelsoft.workbench.hotel.room.client.CheckInBillFrame;
import com.google.gson.Gson;

public class DeployAddDialog extends SingleDataSetFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 6530665711410176529L;
	String[] weekDesc=new String[]{"����һ","���ڶ�","������","������","������","������","������"};

	private JTabbedPane tabbedPane;
	private JPanel mainPanel;
	private JLabel taskLabel;// ����
	private JdbTextField taskField;
	private JPanel taskJPanel;
	private JdbButton taskButton;

	private JLabel taskNameLabel;// ����
	private JdbTextField taskNameField;

	private JLabel taskDescLabel;// ��������
	private JdbTextField taskDescField;

	private JLabel taskDeployLabel;
	private JdbComboBox taskDeployBox;

	private JLabel execClassLabel;// ִ����
	private JdbTextField execClassField;

	StorageDataSet parmDetail;// ����
	private JPanel parmPanel;
	private TableScrollPane parmPane;
	private JdbTable parmTable;

	private JToolBar parmToolBar;
	private JButton parmNewButton;
	private JButton parmRemoveButton;
	private JButton parmClearButton;

	private JPanel triggerPanel;// �������
	private JLabel triggerTypeLabel;// ������ʽ
	// ��������
	private JRadioButton nowTriggerButton;// ��������
	private JRadioButton timerTriggerButton;// ��ʱ������

	private JPanel strategyPanel;//�������
	private JPanel freqPanel;//Ƶ�����
	private JLabel triggerFreqLabel;// ����Ƶ��
	private JdbComboBox triggerFreqBox;


	private JPanel timeConfigPanel;//ʱ������
	//������
	private JPanel freqIntervaPanel;

	private JLabel freqIntervaLabel;// ���
	private JSpinner freqIntervaSpinner;
	private JLabel tipLabel;// ��ʾ

	/*
	 * private JPanel datePanel;//
	 */
	private JPanel monthPanel;// �����
	private JPanel weekPanel;// �����
	private JPanel dayPanel;// ����

	private JPanel yearPanel;// ����

	private JPanel oneDayPanel;// һ����

//	private JLabel onceTriggerLabel;// ����һ��ʱ��
	private JRadioButton onceTriggerButton;
	private JdbTimePicker triggerTimePicker;

	private JRadioButton cycleTriggerButton;// ���ڷ���Ƶ��
	private JSpinner dayFreqIntervalSpinner;
	private JdbComboBox dayFreqUnit=new JdbComboBox();

	private JdbTimePicker triggerbeginTime;
	private JdbTimePicker triggerEndTime;

	private JLabel triggerbeginLabel;
	private JLabel triggerEndLabel;


	//��Чʱ��
	private JLabel	validStartDateTimeLabel;
	private JLabel	validEndDateTimeLabel;
	private JdbDateTimePicker	validStartDateTimePicker;
	private JdbDateTimePicker	validEndDateTimePicker;

	private JPanel validDatePanel;

	//������ť���
	private JPanel operatePanel;
	private JButton saveButton;
	private JButton cancelButton;

	private Column[] columns;
	private Record record;

	public DeployAddDialog(Column[] columns,Record record) {
		try {
			this.columns=columns;
			this.record=record;
			for(int i=0;i<columns.length;i++){
				this.columns[i]=columns[i].cloneColumn();
			}


			initialization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		headerPanel.setVisible(false);
		this.setResizable(true);
		this.setClosable(true);
		this.pack();

	}

	private void initialization() throws DataSetException, TooManyListenersException {


		this.setTitle(DataModel.getDefault().getCaption("TASK_DEPLOY"));
		this.setPreferredSize(new Dimension(650,600));


		this.dataSet.setColumns(columns.clone());

		this.dataSet.getColumn("TASK_PARM").addColumnChangeListener(new ColumnChangeAdapter(){
			@Override
			public void changed(DataSet dataSet, Column column, Variant value) {
				super.changed(dataSet, column, value);
				if(value!=null&&!value.isNull()){
					analysisParm(value.getString());
				}
			}

		});


		tabbedPane = new JTabbedPane();
		mainPanel = new JPanel();
		tabbedPane.add(mainPanel, "����", 0);
		this.getContentPane().add(tabbedPane);
		final GridBagLayout mainGridBagLayout = new GridBagLayout();
		mainGridBagLayout.rowHeights = new int[] { 5, 5, 5 };
		mainGridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5, 5 };
		mainPanel.setLayout(mainGridBagLayout);

		// ѡ����
		taskLabel = new JLabel(DataModel.getDefault().getLabel(
		"TASK_REG.TASK_CODE"));
		taskField = new JdbTextField();
		taskField.setDataSet(dataSet);
		taskField.setColumnName("TASK_CODE");

		taskJPanel = new JPanel();
		taskJPanel.setLayout(new BorderLayout());
		taskButton = new JdbButton();
		taskButton.setDataSet(dataSet);
		taskButton.setColumnName("TASK_CODE");
		taskJPanel.add(taskField);
		taskJPanel.add(taskButton, BorderLayout.EAST);
		taskButton.setMargin(new Insets(0, 0, 0, 0));
		taskButton
		.setIcon(new ImageIcon(
				SysTemplateFrame.class
				.getClassLoader()
				.getResource(
				"com/evangelsoft/workbench/resources/buttons/find.png")));

		mainPanel.add(taskLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));

		mainPanel.add(taskJPanel, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		taskNameLabel = new JLabel(DataModel.getDefault().getLabel(
		"TASK_DEPLOY.DEPLOY_NAME"));
		taskNameField = new JdbTextField();
		taskNameField.setDataSet(dataSet);
		taskNameField.setColumnName("DEPLOY_NAME");

		mainPanel.add(taskNameLabel, new GridBagConstraints(4, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		mainPanel.add(taskNameField, new GridBagConstraints(5, 1, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		taskDescLabel = new JLabel(DataModel.getDefault().getLabel(
		"TASK_REG.DESCRIPTION"));
		taskDescField = new JdbTextField();
		taskDescField.setDataSet(dataSet);
		taskDescField.setColumnName("DESCRIPTION");


		mainPanel.add(taskDescLabel, new GridBagConstraints(7, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		mainPanel.add(taskDescField, new GridBagConstraints(8, 1, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		taskDeployLabel = new JLabel(DataModel.getDefault().getLabel(
		"TASK_DEPLOY.STATUS"));
		taskDeployBox  = new JdbComboBox();
		taskDeployBox.setDataSet(dataSet);
		taskDeployBox.setColumnName("STATUS$DESC");
		mainPanel.add(taskDeployLabel, new GridBagConstraints(1, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		mainPanel.add(taskDeployBox, new GridBagConstraints(2, 3, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		execClassLabel = new JLabel();
		execClassLabel.setText(DataModel.getDefault().getLabel(
		"TASK_REG.TASK_CLASS"));
		execClassField = new JdbTextField();
		execClassField.setDataSet(dataSet);
		execClassField.setColumnName("TASK_CLASS");


		mainPanel.add(execClassLabel, new GridBagConstraints(4, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		mainPanel.add(execClassField, new GridBagConstraints(5, 3, 4, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		parmDetail = new StorageDataSet();

		final Column column = new Column();
		column.setColumnName("PARM_NAME");

		column.setScale(0);
		column.setPrecision(50);
		column.setWidth(20);
		column.setDataType(com.borland.dx.dataset.Variant.STRING);
		column.setCaption("������");

		final Column columnValue = new Column();
		columnValue.setColumnName("PARM_VALE");

		columnValue.setScale(0);
		columnValue.setPrecision(50);
		columnValue.setWidth(20);
		columnValue.setDataType(com.borland.dx.dataset.Variant.STRING);
		columnValue.setCaption("����ֵ");
		parmDetail.setColumns(new Column[] { column, columnValue });
		parmDetail.open();
		parmPanel = new JPanel();
		parmTable = new JdbTable();
		parmTable.setDataSet(parmDetail);
		parmPane = new TableScrollPane(parmTable);
		parmPanel.setLayout(new BorderLayout());
		parmPanel.add(parmPane, BorderLayout.CENTER);




		parmToolBar = new JToolBar(MessageFormat.format(DataModel
				.getDefault().getCaption("TOOL_BAR_OF"), DataModel.getDefault()
				.getCaption("HOTEL_ROOMS")));
		parmPanel.add(parmToolBar, BorderLayout.NORTH);

		parmNewButton = new JButton();
		parmNewButton.setAction(parmNewAction);
		parmNewButton.setText("");
		parmToolBar.add(parmNewButton);

		parmRemoveButton = new JButton();
		parmRemoveButton.setAction(parmDeleteAction);
		parmRemoveButton.setText("");
		parmToolBar.add(parmRemoveButton);

		parmClearButton = new JButton();
		parmClearButton.setAction(parmClearAction);
		parmClearButton.setText("");
		parmToolBar.add(parmClearButton);




		mainPanel.add(parmPanel, new GridBagConstraints(1, 5, 8, 1, 1.0, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		// d�ڶ�����壬�����������
		triggerPanel = new JPanel();
		tabbedPane.add(triggerPanel, "��������");

		triggerTypeLabel = new JLabel();
		triggerTypeLabel.setText("������ʽ:");
		nowTriggerButton = new JRadioButton();
		nowTriggerButton.setText("����ִ��");
		nowTriggerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//����ִ��Ҫ���� ,��ʱ����
				setSubclassEdit(triggerPanel,false);
				nowTriggerButton.setEnabled(true);
				timerTriggerButton.setEnabled(true);
			}
		});
		timerTriggerButton = new JRadioButton();
		timerTriggerButton.setText("��ʱִ��");
		timerTriggerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//��ʱ����Ҫ���ö�ʱ����
				setSubclassEdit(triggerPanel,true);
			}
		});

		ButtonGroup buttonGroup = new ButtonGroup();//�飬����������������ѡ��ť����һ������ֻ��ѡ������һ��
		buttonGroup.add(nowTriggerButton);
		buttonGroup.add(timerTriggerButton);
		strategyPanel=new JPanel();
		final GridBagLayout topGridBagLayout = new GridBagLayout();
		topGridBagLayout.rowHeights = new int[] { 5, 5, 5 ,5,5};
		topGridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5, 5 };

		triggerPanel.setLayout(new BorderLayout());
		triggerPanel.add(strategyPanel,BorderLayout.NORTH);

		strategyPanel.setLayout(topGridBagLayout);


		strategyPanel.add(triggerTypeLabel, new GridBagConstraints(1, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		strategyPanel.add(nowTriggerButton, new GridBagConstraints(5, 3, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		strategyPanel  .add(timerTriggerButton, new GridBagConstraints(6, 3, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		//ʱ���������
		timeConfigPanel=new JPanel();
		timeConfigPanel.setLayout(new BorderLayout());
		triggerPanel.add(timeConfigPanel,BorderLayout.CENTER);

		//������
		freqPanel=new JPanel();
		timeConfigPanel.add(freqPanel,BorderLayout.NORTH);




		triggerFreqLabel = new JLabel();
		triggerFreqLabel.setText("����Ƶ��");
		triggerFreqBox = new JdbComboBox();
		triggerFreqBox.setDataSet(dataSet);
		triggerFreqBox.setColumnName("TRIGGER_FREQ$DESC");


		final GridBagLayout freqGridBagLayout = new GridBagLayout();
		freqGridBagLayout.rowHeights = new int[] { 5, 5, 5 ,5,5};
		freqGridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5, 5,5,5,5,5,5,5 };
		freqPanel.setLayout(freqGridBagLayout);

		freqPanel.add(triggerFreqLabel, new GridBagConstraints(1, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		freqPanel .add(triggerFreqBox, new GridBagConstraints(2, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		//������
		freqIntervaPanel=new JPanel();
//		freqIntervaPanel.setPreferredSize(new Dimension(400,220));
		final GridBagLayout freqIntervaGridBagLayout = new GridBagLayout();
		freqIntervaGridBagLayout.rowHeights = new int[] { 5, 5, 5 };
		freqIntervaGridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5 };
		freqIntervaPanel.setLayout(freqIntervaGridBagLayout);
		freqPanel .add(freqIntervaPanel, new GridBagConstraints(4, 1, 3, 1, 1.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));





		freqIntervaLabel  = new JLabel("���:");
		freqIntervaSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		//���û��db�������


		freqIntervaPanel.add(freqIntervaLabel , new GridBagConstraints(1,1, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		freqIntervaPanel .add(freqIntervaSpinner, new GridBagConstraints(2,1, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		tipLabel=new JLabel("����Ч�ڵ�ʱ����ڣ����д���������������1����ʾһ�죬2��ʾ2��.");
		freqIntervaPanel .add(tipLabel, new GridBagConstraints(2,3, 4, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));


//		tipLabel = new JLabel();
//		// tipLabel.setText("��ʾ���д˳�����ݼ����1����ʾһ������һ�Σ�2��ʾ2������һ��");
//		tipLabel.setText("��ʾ���д���������������1����ʾһ�죬2��ʾ2��");

		weekPanel=new JPanel();
		for(int i=0;i<7;i++){
			JCheckBox box=new JCheckBox();
			box.setText(weekDesc[i]);
			box.putClientProperty("name", i);
			//��ӵ������
			weekPanel.add(box);
		}

		//��

//		weekPanel=new JPanel();
//		for(int i=1;i<8;i++){
//		JCheckBox box=new JCheckBox();
//		box.setText(String.valueOf(i));
//		box.putClientProperty("name", i);
//		//��ӵ������
//		weekPanel.add(box);
//		}
		monthPanel =new JPanel();
		//�����
		for(int i=1;i<13;i++){
			JCheckBox box=new JCheckBox();
			box.setText(String.valueOf(i));
			box.putClientProperty("name", i);
			//��ӵ������
			monthPanel.add(box);
		}
		//��ʾ�������
		dayPanel=new JPanel();

		final GridBagLayout dayLayout = new GridBagLayout();
		dayLayout.rowHeights = new int[] { 5, 5, 5 ,5,5};
		dayLayout.columnWidths = new int[] { 5, 5, 5, 5, 5, 5,5,5,5,5,};
		dayPanel.setLayout(dayLayout);
		for(int i=1;i<32;i++){
			JCheckBox box=new JCheckBox();
			box.setText(String.valueOf(i));
			box.putClientProperty("name", i);
			//��ӵ������
			dayPanel .add(box, new GridBagConstraints(((i-1)%8*2)+1,(i-1)/8+1, 1, 1, 1,
					0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));
		}
		JCheckBox lasDayBox=new JCheckBox();
		lasDayBox.setText("���һ��");
		lasDayBox.putClientProperty("name",-1);
		dayPanel .add(lasDayBox, new GridBagConstraints(15,4, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		freqIntervaPanel .add(monthPanel, new GridBagConstraints(1,5, 7, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		freqIntervaPanel .add(dayPanel, new GridBagConstraints(1,7, 4, 3, 1,
				0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		freqIntervaPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(4), ""));


		oneDayPanel=new JPanel();
		oneDayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(4), "һ����(������ʱ��)"));
		timeConfigPanel.add(oneDayPanel, BorderLayout.CENTER);


		final GridBagLayout oneDayGridBagLayout = new GridBagLayout();
		oneDayGridBagLayout.rowHeights = new int[] { 5, 5, 5 };
		oneDayGridBagLayout.columnWidths = new int[] { 5, 5, 5, 5, 5 };
		oneDayPanel.setLayout(oneDayGridBagLayout);

		//����һ��ʱ��
		onceTriggerButton=new JRadioButton();
		onceTriggerButton.setText(DataModel.getDefault().getLabel("TASK_DEPLOY.TRIGGER_TIME"));
		triggerTimePicker=new JdbTimePicker();
		triggerTimePicker.setDataSet(dataSet);
		triggerTimePicker.setColumnName("TRIGGER_TIME");

		oneDayPanel .add(onceTriggerButton, new GridBagConstraints(1, 1, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		oneDayPanel .add(triggerTimePicker, new GridBagConstraints(2, 1, 2, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		//Ƶ��
		cycleTriggerButton=new JRadioButton();
		cycleTriggerButton.setText("���ڷ���Ƶ�ʣ�");

		dayFreqIntervalSpinner=new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
//		dayFreqIntervalSpinner.setModel(JSpinner.NumberEditor .);

		triggerbeginLabel=new JLabel();
		triggerbeginLabel.setText(DataModel.getDefault().getLabel("TASK_DEPLOY.BEGIN_TIME"));
		dayFreqUnit=new JdbComboBox();
		dayFreqUnit.setDataSet(dataSet);
		dayFreqUnit.setColumnName("DAY_FREQ$DESC");


		triggerbeginTime=new JdbTimePicker();
		triggerbeginTime.setDataSet(dataSet);
		triggerbeginTime.setColumnName("BEGIN_TIME");
		triggerEndLabel=new JLabel(DataModel.getDefault().getLabel("TASK_DEPLOY.END_TIME"));
		triggerEndTime=new JdbTimePicker();
		triggerEndTime.setDataSet(dataSet);
		triggerEndTime.setColumnName("END_TIME");


		oneDayPanel .add(cycleTriggerButton, new GridBagConstraints(1, 3, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		oneDayPanel .add(dayFreqIntervalSpinner  , new GridBagConstraints(2, 3, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		oneDayPanel .add(dayFreqUnit , new GridBagConstraints(3, 3, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));


		oneDayPanel .add(triggerbeginLabel, new GridBagConstraints(5, 3, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		oneDayPanel .add(triggerbeginTime , new GridBagConstraints(6, 3, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));



		oneDayPanel .add(triggerEndLabel, new GridBagConstraints(5, 5, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		oneDayPanel .add(triggerEndTime , new GridBagConstraints(6, 5, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));


		ButtonGroup oneDayGroup = new ButtonGroup();//�飬����������������ѡ��ť����һ������ֻ��ѡ������һ��
		oneDayGroup.add(cycleTriggerButton);
		oneDayGroup.add(onceTriggerButton);


//		triggerPanel
		validStartDateTimeLabel=new JLabel();
		validStartDateTimeLabel.setText(DataModel.getDefault().getLabel("TASK_DEPLOY.VALID_START_TIME"));
		validEndDateTimeLabel=new JLabel();
		validEndDateTimeLabel.setText(DataModel.getDefault().getLabel("TASK_DEPLOY.VALID_END_TIME"));
		validStartDateTimePicker=new JdbDateTimePicker();
		validStartDateTimePicker.setDataSet(dataSet);
		validStartDateTimePicker.setColumnName("VALID_START_TIME");

		validEndDateTimePicker=new JdbDateTimePicker();
		validEndDateTimePicker.setDataSet(dataSet);
		validEndDateTimePicker.setColumnName("VALID_END_TIME");
		validDatePanel=new JPanel();
		validDatePanel.setLayout(oneDayGridBagLayout);

		validDatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(4), "��Ч�ڣ�������ʱ�䣩"));


		validDatePanel .add(validStartDateTimeLabel, new GridBagConstraints(1, 5, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		validDatePanel .add(validStartDateTimePicker , new GridBagConstraints(2, 5, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		validDatePanel .add(validEndDateTimeLabel, new GridBagConstraints(4, 5, 1, 1, 0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		validDatePanel .add(validEndDateTimePicker , new GridBagConstraints(5, 5, 1, 1, 1,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		timeConfigPanel .add(validDatePanel,BorderLayout.SOUTH);


		operatePanel=new JPanel();
		saveButton=new JButton();
		saveButton.setText("����");
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//����֮ǰ���������õ����ݼ�����
				dataSet.setBigDecimal("FREQ_INTERVAL", new BigDecimal( freqIntervaSpinner.getValue().toString()));
				if(nowTriggerButton.isSelected()){
					dataSet.setString("TRIGGER_TYPE","NOW");
				}else{
					//��ʱ
					dataSet.setString("TRIGGER_TYPE","TIMING");
				}
				BigDecimal deci=new BigDecimal( dayFreqIntervalSpinner.getValue().toString());
				dataSet.setBigDecimal("DAY_FREQ_INTERVAL", deci);
				//һ�����Ƿ���ʾ���
				if(onceTriggerButton.isSelected()){
					dataSet.setString("DAY_FREQ_INTERVAL_TYPE","NOW");
					dataSet.setString("TASK_EXEC_COUNT","ONEC");
				}else{
					//��ʱ
					dataSet.setString("DAY_FREQ_INTERVAL_TYPE","TIMING");
					dataSet.setString("TASK_EXEC_COUNT","MULTI");
				}
				StringBuffer temp=new StringBuffer();
				//����ѡ����
				for(int i=0;i<dayPanel.getComponents().length;i++){
					if(dayPanel.getComponents()[i]   instanceof JCheckBox){
						JCheckBox box=(JCheckBox)dayPanel.getComponents()[i]  ;
						if(box.isSelected()){
							if(temp.length()!=0){
								temp.append(",");
							}
							temp.append(box.getClientProperty("name"));
						}
					}
				}
				dataSet.setString("EXEC_DAY",temp.toString() );
				temp.setLength(0);

				//����ѡ�е���
				for(int i=0;i<monthPanel.getComponents().length;i++){
					if(monthPanel.getComponents()[i]   instanceof JCheckBox){
						JCheckBox box=(JCheckBox)monthPanel.getComponents()[i]  ;
						if(box.isSelected()){
							if(temp.length()!=0){
								temp.append(",");
							}
							temp.append(box.getClientProperty("name"));
						}
					}
				}
				dataSet.setString("EXEC_MONTH",temp.toString() );
				temp.setLength(0);
				//����ѡ�е���

				for(int i=0;i<weekPanel.getComponents().length;i++){
					if(weekPanel.getComponents()[i]   instanceof JCheckBox){
						JCheckBox box=(JCheckBox)weekPanel.getComponents()[i]  ;
						if(box.isSelected()){
							if(temp.length()!=0){
								temp.append(",");
							}
							temp.append(box.getClientProperty("name"));
						}
					}
				}
				dataSet.setString("EXEC_WEEK",temp.toString() );
//				DeployAddDialog.this.dispose();
				RecordFormat format = new RecordFormat("@");
				DataSetHelper.saveMetaToRecordFormat(dataSet, format);
				selections = new RecordSet(format);
				selections.setTrace(false);
				DataRow row = new DataRow(dataSet);
//				int[] rows = listTable.getSelectedRows();
//				for (int i : rows) {
				listTable.getDataSet().getDataRow(0, row);
				Record record = new Record(format);
				DataSetHelper.saveRowToRecord(row, record);
				selections.append(record);
				record.getField("DEPLOY_NAME").getString();

				DeployAddDialog.this.dispose();
//				}

			}

		});
		cancelButton=new JButton();

		cancelButton.setText("ȡ��");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DeployAddDialog.this.dispose();
			}

		});
		operatePanel.add(saveButton);
		operatePanel.add(cancelButton);
		this.getContentPane().add(operatePanel,BorderLayout.SOUTH);


		//��ʼ������
		this.dataSet.addEditListener(new EditAdapter(){

			@Override
			public void inserted(DataSet dataSet) {
				super.inserted(dataSet);
				try {
					dataSet.setString("STATUS","A");
					dataSet.setString("DAY_FREQ","H");
					dataSet.setString("TRIGGER_FREQ","D");
					dataSet.setTime("TRIGGER_TIME", new java.sql.Time(new java.util.Date().getTime()));
					dataSet.setTime("BEGIN_TIME", new java.sql.Time(new java.util.Date().getTime()));
					dataSet.setTime("END_TIME", new java.sql.Time((DateUtil.str2Date( DateUtil.FORMAT_TIME_PATTERN,"23:59:59")).getTime()));
					dataSet.setTimestamp("VALID_START_TIME",new java.util.Date().getTime() );
					dataSet.setString("TASK_EXEC_COUNT","MULTI");
					cycleTriggerButton.setSelected(true);


					timerTriggerButton.setSelected(true);

					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					//����ǰ����+1
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					//��ȥһ����
					calendar.add(Calendar.MILLISECOND, -1);
					//��õ�ǰ�����һ��
					Date edate = calendar.getTime();
					dataSet.setTimestamp("VALID_END_TIME",edate.getTime());


				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void showRowStatus() {
		//������ ��ѡ��ť��json��ʽ������
		super.showRowStatus();
	}

	public static RecordSet select(Component arg0, ConditionTree arg1,Column[] columns, boolean arg2) {
		DeployAddDialog dialog=new DeployAddDialog(columns,null);
		dialog.select(arg0, arg1, arg2,false);
		return dialog.selections;

	}
	public static RecordSet select(Component arg0, ConditionTree arg1,Column[] columns,Record record, boolean arg2) {
		DeployAddDialog dialog=new DeployAddDialog(columns,record);




		dialog.select(arg0, arg1, arg2,false);
		return dialog.selections;

	}


	@Override
	protected boolean beforeSave() {
		//����֮ǰ�����ݸ�ʽ
		return super.beforeSave();
	}

	//����֮��رյ�ǰҳ��
	@Override
	protected void afterSave() {
		DeployAddDialog.this.dispose();
	}
	private class ParmNewAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		ParmNewAction() {
			super(DataModel.getDefault().getCaption("NEW_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								CheckInBillFrame.class
								.getClassLoader()
								.getResource(
								"com/evangelsoft/workbench/resources/buttons/addDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("NEW_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			try {
				parmDetail.insertRow(false);
			} catch (DataSetException ex) {
				if (DataSetException.getExceptionListeners() != null) {
					DataSetException.getExceptionListeners().dispatch(
							new ExceptionEvent(parmDetail,
									parmTable, ex));
					return;
				} else {
					throw ex;
				}
			}
		}
	}



	@Override
	protected void prepared(Object arg0) throws Exception {
		if(record!=null){
			DataSetHelper.loadRowFromRecord(dataSet, record, true);
			// DataSetHelper.resetStatus(dataSet);
			dataSet.mergeChanges(true);
		}
	}
	private ParmNewAction parmNewAction = new ParmNewAction();

	private class ParmDeleteAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		ParmDeleteAction() {
			super(DataModel.getDefault().getCaption("DELETE_LINE"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								CheckInBillFrame.class
								.getClassLoader()
								.getResource(
								"com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("DELETE_LINE"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = parmTable.getDataSet();
			if (dataSet == null) {
				dataSet = parmDetail;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			dataSet.deleteRow();
		}
	}

	private ParmDeleteAction parmDeleteAction = new ParmDeleteAction();

	private class ParmClearAction extends AbstractAction {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		ParmClearAction() {
			super(DataModel.getDefault().getCaption("CLEAR_LINES"), null);
			if (!java.beans.Beans.isDesignTime()) {
				putValue(
						Action.SMALL_ICON,
						new ImageIcon(
								CheckInBillFrame.class
								.getClassLoader()
								.getResource(
								"com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
			}
			putValue(Action.SHORT_DESCRIPTION, DataModel.getDefault()
					.getCaption("CLEAR_LINES"));
		}

		public void actionPerformed(ActionEvent e) {
			DataSet dataSet = parmTable.getDataSet();
			if (dataSet == null) {
				dataSet = parmDetail;
			}
			if (!dataSet.isOpen()
					|| (dataSet.isEmpty() && !dataSet.isEditingNewRow())) {
				return;
			}
			dataSet.deleteAllRows();
		}
	}

	private ParmClearAction parmClearAction = new ParmClearAction();


	/**
	 * ��������������������û�����
	 * @param com
	 * @param isEnable
	 */
	public void setSubclassEdit(JComponent com,boolean isEnable){
		if(com.getComponentCount()>0){
			for (int i = 0; i < com.getComponentCount(); i++){
				Component tempCom=com.getComponent(i);
				if( tempCom instanceof JTextComponent ){
					((JTextComponent)tempCom).setEditable(isEnable);
				}else{
					tempCom.setEnabled(isEnable);
				}
				if(tempCom instanceof JComponent){
					setSubclassEdit((JComponent)tempCom,isEnable);
				}
			}
		}
	}
	//��������
	private void analysisParm(String parm){
		Gson gson=new Gson();
		try{
			HashMap<String,String> map=gson.fromJson(parm, HashMap.class);
			for(String key:map.keySet()){
				parmDetail.insertRow(false);
				parmDetail.setString("PARM_NAME",key );
				parmDetail.setString("PARM_VALE",map.get(key) );
			}
		}catch(Exception e){
		}
	}
}
