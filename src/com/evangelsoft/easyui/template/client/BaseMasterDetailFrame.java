package com.evangelsoft.easyui.template.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetAware;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.EditListener;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.borland.jb.util.ErrorResponse;
import com.evangelsoft.easyui.print.client.PrintDataManage;
import com.evangelsoft.easyui.print.client.PrintDesignFrame;
import com.evangelsoft.easyui.print.client.PrintStorageDataSet;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.type.DefaultValue;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.jx.onlineseal.type.JXReport;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;

/**
 *
 * @author ������
 *Company
 *�������Զ�������ʼ��
 *2016��4��27��
 */
public class BaseMasterDetailFrame extends UMasterDetailFrame implements EditListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private UITabPanel headPane;// ͷ����壬�����ݼ����

	private JSplitPane middlelowerPane;// �������

	private UITabPanel bodyPane;// �м�ѡ��

	private UITabPanel tailPane;// �ײ����

	private JSplitPane detailFootPane;// ��ϸ�б��

	// private JSplitPane mainPane;// ����̳����ָ����
	// private JPanel rightPanel;// �ұ߱��ָ����

	private JButton viewButton;

	private JPopupMenu viewMenu;// �鿴

	private JMenuItem headerMaxItem;// ��ͷ���

	private JMenuItem bodyMaxItem;// �������

	private JMenuItem tailMaxItem;// ��β���

	private JMenuItem defaultItem;// Ĭ��

	public static String CARD_VIEW = "CARD_VIEW";// ��Ƭģʽ

	public static String LIST_VIEW = "LIST_VIEW";

	private JButton viewTypeButton;

	// ҳ������
	// private String frameType;
	private Object templateId = "0003";// ģ��ID

	private Object queryId = "0003";// ģ��ID

	private PrintButton printButton;// ��ӡ��ť

	private JButton printDesignButton;// ��ӡ��ť

	// ҳǩmap����
	// ���ݼ���
	HashMap<String, StorageDataSet> stroageDataMap = new HashMap<String, StorageDataSet>();

	/**
	 * ��ӡ�ӿ�Ԥ��
	 */
	// HashMap<String, PrintStorageDataSet> printDataSetPanelMap = new
	// HashMap<String, PrintStorageDataSet>();

	PrintDataManage printDataManage = new PrintDataManage();

	HashMap<String, UIComponent> atrrMap = new HashMap<String, UIComponent>();

	public void run(String templateId) {
		if (templateId.split(",").length == 2) {
			this.templateId = templateId.split(",")[0];
			this.queryId = templateId.split(",")[1];
		} else {
			this.templateId = templateId;
			this.queryId = templateId;
		}
		setVisible(true);
	}

	public BaseMasterDetailFrame() {
		try {
			printButton = new PrintButton();
			printButton.setText("��ӡ");
			this.mainToolBar.add(printButton);
			printButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					print();
				}
			});
			printDesignButton = new JButton();
			printDesignButton.setText("��ӡ���");
			this.mainToolBar.add(printDesignButton);
			printDesignButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					printDesign();
				}
			});

			this.setPreferredSize(new Dimension(770, 620));
			detailFootPane = new JSplitPane();
			detailFootPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			detailFootPane.setDividerLocation(150);
			detailFootPane.setOneTouchExpandable(true);
			// detailFootPane.setResizeWeight(1);
			// �м����
			mainPane = new JSplitPane();
			this.getContentPane().add(mainPane);
			mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// ��ָ�����Ȱ�����ҳ��ֳ���������
			mainPane.setDividerLocation(700);
			mainPane.setOneTouchExpandable(true);
			mainPane.setLeftComponent(detailFootPane);// �����Ҫ���� ռ���
			// rightPanel = new JPanel();
			// // rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			mainPane.setBottomComponent(null);
			// rightPanel.setDividerLocation(300);
			// rightPanel.setOneTouchExpandable(true);

			headPane = new UITabPanel();
			headPane.setComponent(listTablePane);
			// this.listTablePane.setViewportView(this.listTable);
			// detailFootPane.setTopComponent(listTablePane);
			// detailFootPane.setDividerLocation(150);

			listTable.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						changeViewType(CARD_VIEW);
					}
				}
			});

			bodyPane = new UITabPanel();

			middlelowerPane = new JSplitPane();
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			detailFootPane.setTopComponent(headPane);
			detailFootPane.setBottomComponent(middlelowerPane);
			// ָ�����ָ�����Ĵ�С�ı�ʱ��η������ռ䡣��Ĭ������£�ֵΪ 0
			// ��ʾ�ұ�/�ײ������������ж���ռ䣨���/����������̶�������ֵΪ 1
			// ��ʾ���/���������������ж���ռ䣨�ұ�/�ײ�������̶�����
			middlelowerPane.setResizeWeight(1);//
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			middlelowerPane.setTopComponent(bodyPane);
			middlelowerPane.setDividerLocation(200);
			//
			// bodyPane = new UITabPanel();
			// middlelowerPane.setTopComponent(bodyPane);
			// middlelowerPane.setDividerLocation(200);
			tailPane = new UITabPanel();
			middlelowerPane.setBottomComponent(tailPane);

			viewMenu = new JPopupMenu();
			headerMaxItem = new JMenuItem();
			headerMaxItem.setText("��ͷ���");
			viewMenu.add(headerMaxItem);
			headerMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// ��������
					detailFootPane.setTopComponent(headPane);
					detailFootPane.setBottomComponent(null);
					detailFootPane.setDividerSize(0);
					// BaseMasterDetailFrame.this.pack();

				}
			});

			bodyMaxItem = new JMenuItem();
			bodyMaxItem.setText("�������");
			bodyMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// ��������
					detailFootPane.setBottomComponent(middlelowerPane);
					detailFootPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(null);
					middlelowerPane.setTopComponent(bodyPane);
					detailFootPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(bodyMaxItem);

			tailMaxItem = new JMenuItem();
			tailMaxItem.setText("��β���");
			tailMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// ��������
					detailFootPane.setBottomComponent(middlelowerPane);
					detailFootPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(null);
					detailFootPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(tailMaxItem);

			defaultItem = new JMenuItem();
			defaultItem.setText("Ĭ��");
			defaultItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// ��ȡ�����С

					// ��������
					detailFootPane.setBottomComponent(middlelowerPane);
					detailFootPane.setTopComponent(headPane);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(bodyPane);
					detailFootPane.setDividerLocation(150);
					// rightPanel.setDividerLocation(300);
					// middlelowerPane.setDividerLocation(200);
					detailFootPane.setDividerSize(10);
					middlelowerPane.setDividerSize(10);
					BaseMasterDetailFrame.this.setPreferredSize(BaseMasterDetailFrame.this.getSize());
					BaseMasterDetailFrame.this.pack();
				}
			});
			viewButton = new JButton("��ʾ");
			viewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					viewMenu.show(viewButton, 0, viewButton.getHeight());
				}
			});
			viewMenu.add(defaultItem);
			mainToolBar.add(viewButton);
			// Ĭ�����б���ʾ
			viewTypeButton = new JButton("�б���ʾ");
			viewTypeButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (viewTypeButton.getText().equals("�б���ʾ")) {
						changeViewType(LIST_VIEW);
					} else {
						changeViewType(CARD_VIEW);
					}
				}
			});
			this.mainToolBar.add(viewTypeButton);

		} catch (Exception e) {
			e.printStackTrace();
		}
		pack();
	}

	// ��ʼ������
	public BaseMasterDetailFrame(Object templateId) {
		this();
		this.templateId = templateId;
	}

	protected Object prepareData() throws Exception {
		entityClass = BaseMasterDetail.class;
		return super.prepareData();
	}

	protected void prepared(Object arg0) throws Exception {
		entityClass = BaseMasterDetail.class;
		pranMap.put(BaseMasterDetail.TEMPLATE_ID, templateId);
		super.prepared(arg0);
		// ��ʼ������
		SysTemplate sysTemplate = (SysTemplate) (new RMIProxy(Consumer.getDefaultConsumer().getSession()))
				.newStub(SysTemplate.class);
		VariantHolder<String> errMsg = new VariantHolder<String>();
		VariantHolder<Object> data = new VariantHolder<Object>();
		data.value = new RecordSet[] { new RecordSet(), new RecordSet(), new RecordSet(), new RecordSet(),
				new RecordSet() };
		if (!sysTemplate.get(this.templateId, data, errMsg)) {
			throw new Exception(errMsg.value);
		}
		RecordSet[] datas = (RecordSet[]) data.value;
		// this.title=datas[0].getRecord(0).getField("TEMPLATE_NAME").getString();
		tempRecord = datas[0].getRecord(0);

		this.setTitle(datas[0].getRecord(0).getField("TEMPLATE_NAME").getString());
		// ��������
		String[] keys = tempRecord.getField("MAIN_KEY").getString().split(",");// ��ֵ������Ϊ��
		keyColumns = keys;

		// ��ʼ�������С
		if (!datas[0].getRecord(0).getField("DEFAULT_WIDTH").isNull()
				&& !datas[0].getRecord(0).getField("DEFAULT_HEIGHT").isNull()) {
			this.setPreferredSize(new Dimension(datas[0].getRecord(0).getField("DEFAULT_WIDTH").getNumber().intValue(),
					datas[0].getRecord(0).getField("DEFAULT_HEIGHT").getNumber().intValue()));
		}
		// ���ɶ�Ӧ��ҳǩ
		if (datas[2] != null && datas[2].recordCount() > 0) {
			for (int i = 0; i < datas[2].recordCount(); i++) {
				CodeValue codeVaalue = new CodeValue();
				codeVaalue.code = datas[2].getRecord(i).getField("TABS_ID").getString();
				codeVaalue.value = datas[2].getRecord(i).getField("TABS_NAME").getString();

				StorageDataSetPanel panle = createPagePanel(codeVaalue, datas[2].getRecord(i).getField("POSITION")
						.getString(), datas[2].getRecord(i).getField("TABS_TYPE").getString(), datas[2].getRecord(i)
						.getField("SEQUENCE").getNumber().intValue());
				if (panle != null) {
					PrintStorageDataSet printSet = new PrintStorageDataSet();
					printSet.setTableId(datas[2].getRecord(i).getField("TABS_ID").getString());
					printSet.setTableDesc(datas[2].getRecord(i).getField("TABS_NAME").getString());
					printSet.setDataSet(panle.getStorageDataSet());
					printDataManage.addPrintDataManage(printSet);
					// �����������������Ϊ����
					if (tempRecord.getField("MAIN_TABLE_ID").getString()
							.equals(datas[2].getRecord(i).getField("TABLE_ID").getString())) {
						printDataManage.setMainPrintStorageDataSet(printSet);
					} else if (printDataManage.getListPrintStorageDataSet() == null) {
						printDataManage.setListPrintStorageDataSet(printSet);
					}

					// this.printDataSetPanelMap.put(datas[2].getRecord(i).getField("TABS_ID").getString(),
					// printSet);
				}

				if (!datas[2].getRecord(i).getField("COL_NUM").isNull()) {
					panle.setColNum(datas[2].getRecord(i).getField("COL_NUM").getNumber().intValue());
				}
			}
		}
		headPane.setSelectedIndex(0);
		bodyPane.setSelectedIndex(0);
		tailPane.setSelectedIndex(0);
		// �����ֶ�ѡ��
		Map<String, List<Record>> colsMap = new HashMap<String, List<Record>>();

		if (datas[3] != null && datas[3].recordCount() > 0) {
			List<Record> recordList = null;
			for (int i = 0; i < datas[3].recordCount(); i++) {
				if (colsMap.containsKey(datas[3].getRecord(i).getField("TABS_ID").getString())) {
					recordList = colsMap.get(datas[3].getRecord(i).getField("TABS_ID").getString());
				} else {
					recordList = new ArrayList<Record>();
					colsMap.put(datas[3].getRecord(i).getField("TABS_ID").getString(), recordList);
				}
				recordList.add(datas[3].getRecord(i));
				// ���ֵ��Դ����ֵ����һ�ж�
				if (!datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").isNull()) {
					// �ж��Ƿ���Ĭ��ֵ
					if (!datas[3].getRecord(i).getField("DEFAULT_VALUE").isNull()) {
						// ����Ǳ���
						if (initValueMap.containsKey(datas[3].getRecord(i).getField("TABLE_ID").getString())) {
							initValueMap.get(datas[3].getRecord(i).getField("TABLE_ID").getString())
									.put(datas[3].getRecord(i).getField("COLUMN_ID").getString(),
											new DefaultValue(datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE")
													.getString(), datas[3].getRecord(i).getField("DEFAULT_VALUE")
													.getString(), masterDataSet));
						} else {
							HashMap<String, DefaultValue> map = new HashMap<String, DefaultValue>();
							map.put(datas[3].getRecord(i).getField("COLUMN_ID").getString(), new DefaultValue(datas[3]
									.getRecord(i).getField("DEFAULT_VALUE_SOURCE").getString(), datas[3].getRecord(i)
									.getField("DEFAULT_VALUE").getString(), masterDataSet));
							initValueMap.put(datas[3].getRecord(i).getField("TABLE_ID").getString(), map);
						}
					}
				}
			}
			//
			try {
				for (String key : colsMap.keySet()) {
					dataSetPanelMap.get(key).batchAdd(colsMap.get(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		for (StorageDataSetPanel panel : dataSetPanelMap.values()) {
			if (panel.getStorageDataSet() == masterDataSet) {
				continue;
			} else if (panel.getStorageDataSet() == detailDataSet) {

			}
			if (detailDataSet != panel.getStorageDataSet()) {
				detailDataSets.add(panel.getStorageDataSet());
				this.detailLoadings.put(panel.getStorageDataSet(), false);
				this.detailEditables.put(panel.getStorageDataSet(), true);
			}
			HashMap<DataSet, HashMap<DataSetAware, Boolean>> localObject1 = new HashMap<DataSet, HashMap<DataSetAware, Boolean>>();

			HashMap<DataSetAware, Boolean> localHashMap = (HashMap<DataSetAware, Boolean>) this.detailComponents
					.get(panel.getStorageDataSet());
			if (localHashMap == null) {
				localHashMap = new HashMap<DataSetAware, Boolean>();
				this.detailComponents.put(panel.getStorageDataSet(), localHashMap);
			}
			localObject1.put(panel.getStorageDataSet(), localHashMap);
			DataSetHelper.listDataAwareComponents(this.getContentPane(), localObject1);

			detailNewActions.put(panel.getStorageDataSet(), panel.getNewAction());
			detailDeleteActions.put(panel.getStorageDataSet(), panel.getDeleteAction());
			detailClearActions.put(panel.getStorageDataSet(), panel.getClearAction());
			if (panel.getBatchAction() != null) {
				detailBatchActions.put(panel.getStorageDataSet(), panel.getBatchAction());
			}
		}
		dataSetKeyMap.clear();
		for (String key : dataSetPanelMap.keySet()) {
			dataSetKeyMap.put(dataSetPanelMap.get(key).getStorageDataSet(), key);
		}

		HashMap<DataSet, HashMap<DataSetAware, Boolean>> localObject1 = new HashMap<DataSet, HashMap<DataSetAware, Boolean>>();
		localObject1.put(this.masterDataSet, this.masterComponents);
		DataSetHelper.listDataAwareComponents(this.getContentPane(), localObject1);
		DataSetHelper.enableDataAwareComponents(this.masterComponents, false);

		// Ĭ����ʾ�б�
		headPane.setViewType(UITabPanel.OTHER);
		headPane.updateUI();

		query = new QueryDialog(queryId, masterDataSet);
		query.setLocationRelativeTo(this);

		// ��ʾ��ӡ��ť
		printButton.init(datas[4]);
		autoView();

	}

	private StorageDataSetPanel createPagePanel(CodeValue codevalue, String pos, String type, int index) {
		// ����Ǳ�ͷ���߱�β����ֱ����masterDataSet,�����ϸ���һ����ֱ�Ӵ�detailDataSet����new����
		if (dataSetPanelMap.containsKey(codevalue.code)) {
			return null;
		}
		StorageDataSetPanel panel = null;
		masterDataSet.addEditListener(this);
		detailDataSet.addEditListener(this);

		if (pos.equals(DesignFrame.HEAD)) {
			panel = new StorageDataSetPanel(codevalue, type, null, pos, masterDataSet, false);
			headPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
		} else if (pos.equals(DesignFrame.BODY)) {
			if (index == 0) {
				// �����ϸ���һ���ǣ�ֱ���ø����detailDataSet
				panel = new StorageDataSetPanel(codevalue, type, pos, this.detailDataSet, true);
			} else {
				StorageDataSet dataset = new StorageDataSet();
				dataset.addEditListener(this);
				panel = new StorageDataSetPanel(codevalue, type, pos, dataset, true);
			}
			bodyPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
		} else if (pos.equals(DesignFrame.TAIL)) {
			panel = new StorageDataSetPanel(codevalue, type, null, pos, masterDataSet, false);
			tailPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			index = tailPane.getComponentCount();
		}
		dataSetPanelMap.put((String) codevalue.code, panel);
		return panel;
	}

	private UIComponent loadItem(StorageDataSetPanel panel, FieldRecord record) throws Exception {
		if (panel == null || record.getField("COLUMN_ID") == null) {
			System.out.println("�п�ֵ��");
		}
		if (atrrMap.containsKey(panel.getPageId() + "#" + record.getField("COLUMN_ID").getString())) {
			return null;
		}
		UIComponent uicom = panel.add(record);
		this.atrrMap
				.put(record.getField("TABS_ID").getString() + "#" + record.getField("COLUMN_ID").getString(), uicom);
		return uicom;
	}

	// private void addItem(StorageDataSetPanel panel, FieldRecord[] records)
	// throws Exception{
	// for(int i=0;i<records.length;i++){
	// FieldRecord record=records[i];
	// if(atrrMap.containsKey(panel.getPageId()+"#"+record.getField("COLUMN_ID").getString())){
	// break ;
	// }
	// UIComponent uicom=panel.add(record);
	// }
	// }

	public void autoView() {
		// ���
		if (bodyPane.getUseComponentCount() == 0) {
			if (tailPane.getUseComponentCount() != 0) {
				middlelowerPane.setTopComponent(null);
			} else {
				detailFootPane.setBottomComponent(null);
				middlelowerPane.setDividerSize(0);
			}
		} else {
			// detailFootPane.setBottomComponent(middlelowerPane);
			//
			// middlelowerPane.setTopComponent(bodyPane);
			// detailFootPane.setDividerLocation(150);
			// // rightPanel.setDividerLocation(300);
			// middlelowerPane.setDividerLocation(200);
			// detailFootPane.setDividerSize(10);
			// middlelowerPane.setDividerSize(10);
			//
			//
			// if(tailPane.getUseComponentCount()!=0){
			// middlelowerPane.setTopComponent(tailPane);
			// middlelowerPane.setBottomComponent(tailPane);
			// }
		}
		if (tailPane.getUseComponentCount() == 0) {
			this.tailPane.setVisible(false);
			this.middlelowerPane.setBottomComponent(null);
			this.middlelowerPane.setDividerSize(0);

		} else {
			this.tailPane.setVisible(true);
			this.middlelowerPane.setBottomComponent(tailPane);
			this.middlelowerPane.setDividerLocation(260);
			// this.middlelowerPane.setDividerSize(1);
		}
		this.pack();
	}

	protected void changeViewType(String type) {
		if (CARD_VIEW.equals(type)) {
			viewTypeButton.setText("�б���ʾ");
			headPane.setViewType(UITabPanel.ALL_PANEL);
		} else {
			viewTypeButton.setText("��Ƭ��ʾ");
			headPane.setViewType(UITabPanel.OTHER);
		}
		headPane.updateUI();
	}

	protected void showStatus() {
		super.showStatus();
		this.viewTypeButton.setEnabled(!this.cancelButton.isEnabled());
		// if(this.cancelButton.isEnabled()){
		// changeViewType(CARD_VIEW);
		// }
		// super.showStatus();
	}

	protected void showRowStatus() {
		if (masterDataSet.isEditingNewRow() && masterDataSet.isEmpty())
			if (this.cancelButton.isEnabled() && (masterDataSet.getStatus() & 0x4) != 0) {
				changeViewType(CARD_VIEW);
			}
	}

	public void addError(DataSet arg0, ReadWriteRow arg1, DataSetException arg2, ErrorResponse arg3) {
	}

	public void added(DataSet arg0) {
	}

	public void adding(DataSet arg0, ReadWriteRow arg1) throws Exception {
	}

	public void canceling(DataSet arg0) throws Exception {
	}

	public void deleteError(DataSet arg0, DataSetException arg1, ErrorResponse arg2) {
	}

	public void deleted(DataSet arg0) {
	}

	public void deleting(DataSet arg0) throws Exception {
	}

	public void editError(DataSet arg0, Column arg1, Variant arg2, DataSetException arg3, ErrorResponse arg4) {
	}

	public void inserted(DataSet dataSet) {
		// ���ҵ�
		String tableId = dataSetKeyMap.get(dataSet);
		if (initValueMap.containsKey(tableId)) {
			// System.out.println(tableId);
			// masterDataSet.setString("QUERY_TEMPLATE_ID", "AAAAAAA");
			// masterDataSet.setBigDecimal("QUERY_TEMPLATE_ID", BigDecimal.ONE);
			for (String key : initValueMap.get(tableId).keySet()) {
				// ȡ����ǰ��������
				try {
					DefaultValue.setObjectValue(dataSet, key, dataSet.getColumn(key).getDataType(),
							initValueMap.get(tableId).get(key).getValue());
				} catch (DataSetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// dataSet.setBigDecimal(key,
				// (BigDecimal)initValueMap.get(tableId).get(key).getValue());
			}
		}
	}

	/**
	//�����л���õķ���,��ʼ��ֵ�����������
	 *
	 */
	public void inserting(DataSet dataSet) throws Exception {
	}

	public void modifying(DataSet arg0) throws Exception {
	}

	public void updateError(DataSet arg0, ReadWriteRow arg1, DataSetException arg2, ErrorResponse arg3) {
	}

	public void updated(DataSet arg0) {
	}

	public void updating(DataSet arg0, ReadWriteRow arg1, ReadRow arg2) throws Exception {
	}

	/**
	 * �༭��֤
	 */
	void editValidateData(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow) {
		// ������֤�Ƿ�Ϊ�յ��ж�
	}

	@Override
	protected void validateDetail(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
			throws Exception {
		editValidateData(paramDataSet, paramReadWriteRow, paramReadRow);
	}

	@Override
	protected void validateMaster(ReadWriteRow paramReadWriteRow, ReadRow paramReadRow) throws Exception {
		editValidateData(masterDataSet, paramReadWriteRow, paramReadRow);
	}

	// ���ô�ӡ�ķ���
	public void print() {
		HashMap<String, Object> map = JXReport.buildParametersFromRow(masterDataSet, null);
		printButton.print(map, detailDataSet);
	}

	// ���ô�ӡ��Ʒ���
	public void printDesign() {
		// �����޸ĵİ�ť��Ҫ����
		PrintDesignFrame.createPrint(templateId.toString(), printDataManage);
	}
}
