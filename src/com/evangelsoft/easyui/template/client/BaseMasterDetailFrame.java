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
 * @author 杨永钦
 *Company
 *描述：自定义界面初始类
 *2016年4月27日
 */
public class BaseMasterDetailFrame extends UMasterDetailFrame implements EditListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private UITabPanel headPane;// 头部面板，主数据集面板

	private JSplitPane middlelowerPane;// 中下面板

	private UITabPanel bodyPane;// 中间选项

	private UITabPanel tailPane;// 底部面板

	private JSplitPane detailFootPane;// 明细列表和

	// private JSplitPane mainPane;// 父类继承主分割面板
	// private JPanel rightPanel;// 右边表格分割面板

	private JButton viewButton;

	private JPopupMenu viewMenu;// 查看

	private JMenuItem headerMaxItem;// 表头最大化

	private JMenuItem bodyMaxItem;// 表体最大化

	private JMenuItem tailMaxItem;// 表尾最大化

	private JMenuItem defaultItem;// 默认

	public static String CARD_VIEW = "CARD_VIEW";// 卡片模式

	public static String LIST_VIEW = "LIST_VIEW";

	private JButton viewTypeButton;

	// 页面类型
	// private String frameType;
	private Object templateId = "0003";// 模板ID

	private Object queryId = "0003";// 模板ID

	private PrintButton printButton;// 打印按钮

	private JButton printDesignButton;// 打印按钮

	// 页签map集合
	// 数据集合
	HashMap<String, StorageDataSet> stroageDataMap = new HashMap<String, StorageDataSet>();

	/**
	 * 打印接口预留
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
			printButton.setText("打印");
			this.mainToolBar.add(printButton);
			printButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					print();
				}
			});
			printDesignButton = new JButton();
			printDesignButton.setText("打印设计");
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
			// 中间面板
			mainPane = new JSplitPane();
			this.getContentPane().add(mainPane);
			mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 大分割面板先把整个页面分成左右俩边
			mainPane.setDividerLocation(700);
			mainPane.setOneTouchExpandable(true);
			mainPane.setLeftComponent(detailFootPane);// 表格主要内容 占左边
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
			// 指定当分隔窗格的大小改变时如何分配额外空间。在默认情况下，值为 0
			// 表示右边/底部的组件获得所有额外空间（左边/顶部的组件固定），而值为 1
			// 表示左边/顶部的组件获得所有额外空间（右边/底部的组件固定）。
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
			headerMaxItem.setText("表头最大化");
			viewMenu.add(headerMaxItem);
			headerMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// 隐藏中下
					detailFootPane.setTopComponent(headPane);
					detailFootPane.setBottomComponent(null);
					detailFootPane.setDividerSize(0);
					// BaseMasterDetailFrame.this.pack();

				}
			});

			bodyMaxItem = new JMenuItem();
			bodyMaxItem.setText("表体最大化");
			bodyMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// 隐藏中下
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
			tailMaxItem.setText("表尾最大化");
			tailMaxItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// 隐藏中下
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
			defaultItem.setText("默认");
			defaultItem.addActionListener(new AbstractAction() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					// 获取窗体大小

					// 隐藏中下
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
			viewButton = new JButton("显示");
			viewButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					viewMenu.show(viewButton, 0, viewButton.getHeight());
				}
			});
			viewMenu.add(defaultItem);
			mainToolBar.add(viewButton);
			// 默认是列表显示
			viewTypeButton = new JButton("列表显示");
			viewTypeButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (viewTypeButton.getText().equals("列表显示")) {
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

	// 初始化方法
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
		// 初始化界面
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
		// 设置主键
		String[] keys = tempRecord.getField("MAIN_KEY").getString().split(",");// 此值不允许为空
		keyColumns = keys;

		// 初始化界面大小
		if (!datas[0].getRecord(0).getField("DEFAULT_WIDTH").isNull()
				&& !datas[0].getRecord(0).getField("DEFAULT_HEIGHT").isNull()) {
			this.setPreferredSize(new Dimension(datas[0].getRecord(0).getField("DEFAULT_WIDTH").getNumber().intValue(),
					datas[0].getRecord(0).getField("DEFAULT_HEIGHT").getNumber().intValue()));
		}
		// 生成对应的页签
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
					// 如果是主表，则添设置为主表
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
		// 生成字段选项
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
				// 如果值来源设置值，逐一判断
				if (!datas[3].getRecord(i).getField("DEFAULT_VALUE_SOURCE").isNull()) {
					// 判断是否有默认值
					if (!datas[3].getRecord(i).getField("DEFAULT_VALUE").isNull()) {
						// 如果是变量
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

		// 默认显示列表
		headPane.setViewType(UITabPanel.OTHER);
		headPane.updateUI();

		query = new QueryDialog(queryId, masterDataSet);
		query.setLocationRelativeTo(this);

		// 显示打印按钮
		printButton.init(datas[4]);
		autoView();

	}

	private StorageDataSetPanel createPagePanel(CodeValue codevalue, String pos, String type, int index) {
		// 如果是表头或者表尾，则直接用masterDataSet,如果明细表第一个，直接传detailDataSet不能new对象
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
				// 如果明细表第一个是，直接用父类的detailDataSet
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
			System.out.println("有空值了");
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
		// 如果
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
			viewTypeButton.setText("列表显示");
			headPane.setViewType(UITabPanel.ALL_PANEL);
		} else {
			viewTypeButton.setText("卡片显示");
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
		// 查找到
		String tableId = dataSetKeyMap.get(dataSet);
		if (initValueMap.containsKey(tableId)) {
			// System.out.println(tableId);
			// masterDataSet.setString("QUERY_TEMPLATE_ID", "AAAAAAA");
			// masterDataSet.setBigDecimal("QUERY_TEMPLATE_ID", BigDecimal.ONE);
			for (String key : initValueMap.get(tableId).keySet()) {
				// 取到当前对象类型
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
	//插入行会调用的方法,初始化值，再这里设计
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
	 * 编辑验证
	 */
	void editValidateData(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow) {
		// 调用验证是否为空的判断
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

	// 调用打印的方法
	public void print() {
		HashMap<String, Object> map = JXReport.buildParametersFromRow(masterDataSet, null);
		printButton.print(map, detailDataSet);
	}

	// 调用打印设计方法
	public void printDesign() {
		// 还有修改的按钮需要考虑
		PrintDesignFrame.createPrint(templateId.toString(), printDataManage);
	}
}
