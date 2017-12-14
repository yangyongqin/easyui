package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.borland.dbswing.JdbButton;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnAware;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.ColumnCustomEditListener;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.config.intf.SysTable;
import com.evangelsoft.easyui.template.client.nc.DefaultTableHeaderCellRenderer;
import com.evangelsoft.easyui.template.client.nc.FormulaEditorDialog;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.tool.ColumnsHelp;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.config.client.SysCodeHelper;
import com.evangelsoft.workbench.framebase.MasterDetailFrame;
import com.evangelsoft.workbench.types.BoolStr;
import com.google.gson.Gson;

/**
 *
 * @author yyq
 *
 */
public class DesignFrame extends MasterDetailFrame {
	/**
	 *
	 */
	private JDialog dialog;

	private static final long serialVersionUID = -3120630848749287382L;
	private JSplitPane mainPane;// 主分割面板
	// private JSplitPane tableSplitPane;//左边表格分割面板
	private JSplitPane rightSplitPane;// 右边表格分割面板

	private JTabbedPane tablePane;// 表格和字段选项卡

	private JSplitPane leftSplitPane;// 左边分割。上表格，下属性
	private UITabPanel headPane;// 头部面板，主数据集面板
	private JSplitPane middlelowerPane;//中下面板
	private UITabPanel bodyPane;// 中间选项卡
	private UITabPanel tailPane;//底部面板

	private JList<FieldRecord> tableList;
	private JList<FieldRecord> fieldList;

	private JPanel attributePanel;//所有属性面板
	private JTabbedPane attributePane;//字段属性
	private JPanel tabsAttributePanel;//页签属性
	private JPanel frameAttributePanel;//窗体属性，先暂时不管了
	CardLayout	cardLayout= new CardLayout();


	private JPanel viewPanel;
	private JPanel seniorPanel;

	// private JPopupMenu functionMenu;
	private JToolBar mainToolBar;// 工具条
	private JButton operalButton;
	private JPopupMenu operalMenu;// 操作菜单
	private JMenuItem saveItem;// 保存模板
	private JMenuItem saveQueryItem;// 生成查询模板
	private JMenuItem saveprinttem;// 生成打印模板

	private JButton previewButton;
	private JPopupMenu previewMenu;// 查看
	private JMenuItem cardPreviewItem;// 卡片预览
	private JMenuItem listPreviewItem;// 列表预览


	private JButton viewButton;
	private JPopupMenu viewMenu;// 查看
	private JMenuItem headerMaxItem;// 表头最大化
	private JMenuItem bodyMaxItem;// 表体最大化
	private JMenuItem  tailMaxItem;//表尾最大化
	private JMenuItem  defaultItem;//默认

	private JPopupMenu rightMenu;//右键菜单
	private JMenuItem addTable;//
	private JMenuItem addField;// 添加当前选中字段
	private JMenuItem  addTableByNewTabs;//添加当前表到新的页签
	private JMenuItem  addFieldByNewTabs;//添加当前字段到新的页签
	private JMenuItem  addZdyTabs;//添加自定义页签
	private JMenuItem  addZdyField;//添加自定义项
	private JMenuItem  deleteItem;//删除当前选中
	private JMenuItem  sortItem;//添加自定义项
	private JMenuItem  editingItem;//编辑页签项




	private JPanel headerPanel;
	protected JPanel titlePanel;

	private JComponent currentCom;
	public final static String HEAD = "HEAD"; // 表头

	public final static  String BODY = "BODY"; // 表体

	public final static	 String TAIL = "TAIL"; // 表尾
	private String templateId;//模板ID
	DefaultListModel<FieldRecord> listModel = new DefaultListModel<FieldRecord>();
	//当前选中表字段
	private String currentTable;
	DefaultListModel<FieldRecord> currentFieldModel = new DefaultListModel<FieldRecord>();
	HashMap<String,DefaultListModel<FieldRecord>>fieldModeMap=new HashMap<String, DefaultListModel<FieldRecord>>();

	HashMap<String,PagePanel> pageMap=new HashMap<String, PagePanel>();
	HashMap<String,UIComponent> atrrMap=new HashMap<String, UIComponent>();
	HashMap<String,String> tableMap=new HashMap<String, String>();
	//	List<String> tables=new ArrayList<String>();


	//数据仓库
	private StorageDataSet dataTypeDataSet;
	private StorageDataSet boolStrDataSet;
	private StorageDataSet charCaseataSet;
	private StorageDataSet componentDataSet;
	private StorageDataSet pageTypeDataSet;
	private StorageDataSet postDataSet;
	private StorageDataSet valueSourceDaraSet;//默认值来源
	private StorageDataSet dataSourceDaraSet;//数据来源
	private StorageDataSet valueTypeDaraSet;//值类型

	public DesignFrame(String templateId) {
		this();//调用无参数构造方法
		this.templateId=templateId;
	}

	public DesignFrame() {
		try{
			this.setPreferredSize(new Dimension(1000, 600));
			this.addMouseListener(componentSelect);
			// 头部
			headerPanel = new JPanel();
			headerPanel.setLayout(new BorderLayout());
			getContentPane().add(headerPanel, BorderLayout.NORTH);

			mainToolBar = new JToolBar();
			headerPanel.add(mainToolBar, BorderLayout.NORTH);
			operalMenu = new JPopupMenu("操作");

			saveItem = new JMenuItem();
			saveItem.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					DesignFrame.this.saveButton.doClick();
				}
			});
			saveItem.setText("保存模板");
			operalMenu.add(saveItem);

			saveQueryItem = new JMenuItem();
			saveQueryItem.setText("生成查询模板");
			operalMenu.add(saveQueryItem);
			saveprinttem = new JMenuItem();
			saveprinttem.setText("生成打印模板");
			operalMenu.add(saveprinttem);

			operalButton = new JButton("操作");
			operalButton.setBorderPainted(false);//去边框
			operalButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					operalMenu.show(operalButton, 0, operalButton.getHeight());
				}
			});
			mainToolBar.add(operalButton);

			mainToolBar.addSeparator();

			previewButton=new JButton("预览");
			previewButton.setBorderPainted(false);//去掉边框

			previewMenu = new JPopupMenu();
			cardPreviewItem = new JMenuItem("卡片预览");
			listPreviewItem = new JMenuItem("列表预览");
			previewMenu.add(cardPreviewItem);
			previewMenu.add(listPreviewItem);
			mainToolBar.add(previewButton);
			previewButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					previewMenu.show(previewButton, 0, previewButton.getHeight());
				}
			});

			mainToolBar.addSeparator();
			viewButton=new JButton("显示");
			viewButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					viewMenu.show(viewButton, 0, viewButton.getHeight());
				}

			});
			viewMenu=new JPopupMenu();
			headerMaxItem=new JMenuItem();
			headerMaxItem.setText("表头最大化");
			viewMenu.add(headerMaxItem);
			headerMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//隐藏中下
					leftSplitPane.setTopComponent(headPane);
					leftSplitPane.setBottomComponent(null);
					leftSplitPane.setDividerSize(0);
					DesignFrame.this.pack();

				}
			});

			bodyMaxItem=new JMenuItem();
			bodyMaxItem.setText("表体最大化");
			bodyMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//隐藏中下
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(null);
					middlelowerPane.setTopComponent(bodyPane);
					leftSplitPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(bodyMaxItem);


			tailMaxItem=new JMenuItem();
			tailMaxItem.setText("表尾最大化");
			tailMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//隐藏中下
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(null);
					leftSplitPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(tailMaxItem);

			defaultItem=new JMenuItem();
			defaultItem.setText("默认");
			defaultItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//隐藏中下
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(headPane);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(bodyPane);
					leftSplitPane.setDividerLocation(200);
					//					rightSplitPane.setDividerLocation(300);
					middlelowerPane.setDividerLocation(200);
					leftSplitPane.setDividerSize(10);
					middlelowerPane.setDividerSize(10);

					DesignFrame.this.pack();
				}
			});

			viewMenu.add(defaultItem);
			mainToolBar.add(viewButton);


			this.titlePanel = new JPanel();
			this.titlePanel.setLayout(new BorderLayout());
			// titlePanel.add(new JTextField());
			this.headerPanel.add(this.titlePanel);

			// 中间面板
			mainPane = new JSplitPane();
			this.getContentPane().add(mainPane);
			mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 大分割面板先把整个页面分成左右俩边

			leftSplitPane = new JSplitPane();
			leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			leftSplitPane.setDividerLocation(200);
			mainPane.setDividerLocation(700);
			mainPane.setOneTouchExpandable(true);
			mainPane.setLeftComponent(leftSplitPane);// 表格主要内容 占左边
			mainPane.setResizeWeight(1);//

			rightSplitPane = new JSplitPane();
			rightSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			mainPane.setRightComponent(rightSplitPane);
			rightSplitPane.setDividerLocation(300);
			rightSplitPane.setOneTouchExpandable(true);

			//			JPanel panel=new JPanel();
			//			panel.setLayout(new BorderLayout());
			//			panel.add(headPane);
			headPane = new UITabPanel();
			headPane.setTransferHandler(new TargatTransferHandler(HEAD));

			headPane.addMouseListener(componentSelect);
			//			addHeaderPanel=new UIPanel();
			//			headPane.addTab("哈哈哈哈哈","呵呵呵呵", addHeaderPanel);
			//			UIPanel panel=	new UIPanel();
			//			panel.setTransferHandler(new TargatTransferHandler(HEAD));

			//			headPane.addTab("1233","3232434", panel);

			leftSplitPane.setTopComponent(headPane);
			middlelowerPane=new JSplitPane();
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			leftSplitPane.setBottomComponent(middlelowerPane);
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

			bodyPane = new UITabPanel();
			bodyPane.setTransferHandler(new TargatTransferHandler(BODY));
			bodyPane.addMouseListener(componentSelect);
			middlelowerPane.setTopComponent(bodyPane);
			middlelowerPane.setDividerLocation(200);
			tailPane=new UITabPanel();
			tailPane.setTransferHandler(new TargatTransferHandler(TAIL ));
			tailPane.addMouseListener(componentSelect);
			middlelowerPane.setBottomComponent(tailPane);


			attributePanel=new JPanel();
			rightSplitPane.setBottomComponent(attributePanel );
			attributePanel.setLayout(cardLayout);


			//字段属性面板
			attributePane = new JTabbedPane();
			viewPanel = new JPanel();
			JScrollPane scrollPane=new JScrollPane(viewPanel);
			seniorPanel = new JPanel();
			seniorPanel.setPreferredSize(new Dimension(100,500));
			JScrollPane seniorPane=new JScrollPane(seniorPanel);
			attributePane.add(scrollPane, "显示属性");
			attributePane.add(seniorPane, "高级属性");
			seniorPane.	setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			//窗体属性放置最上面优先显示，因为其他的不一定有属性需要显示
			frameAttributePanel=new JPanel();
			attributePanel.add("frame", frameAttributePanel);

			tabsAttributePanel=new JPanel();
			attributePanel.add("tabs", tabsAttributePanel);

			//属性的面板添加到卡片布局管理器中
			attributePanel.add("attribute", attributePane);


			initialize();
		}catch(Exception x){
			x.printStackTrace();
		}
		pack();
	}

	StorageDataSet tabsDataSet;//页签、
	StorageDataSet attributeDataSet;//页签

	protected void initialize() throws DataSetException, TooManyListenersException{
		this.listRowCountLabel.setVisible(false);
		this.listRowCountPromptLabel.setVisible(false);
		//初始化数据仓库
		dataTypeDataSet=new StorageDataSet();
		boolStrDataSet=new StorageDataSet();
		charCaseataSet=new StorageDataSet();
		componentDataSet=new StorageDataSet();
		pageTypeDataSet=new StorageDataSet();
		postDataSet=new StorageDataSet();
		valueSourceDaraSet=new StorageDataSet();
		dataSourceDaraSet=new StorageDataSet();
		valueTypeDaraSet=new StorageDataSet();
		String[] sastercolumn=new String[]{"TEMPLATE_ID"};
		masterDataSet.setColumns(ColumnsHelp.getColumns("SYS_TEMPLATE", sastercolumn));
		masterDataSet.open();


		Column  detailCoulmnId=new Column("SYS_TEMPLATE_DETAIL.TABLE_ID");

		Column detailCoulmnName=new Column("SYS_TABLE.TABLE_NAME");

		Column detailRemaks=new Column("SYS_TEMPLATE_DETAIL.REMARKS");
		detailDataSet.setColumns(new Column[]{detailCoulmnId,detailCoulmnName,detailRemaks});
		detailDataSet.open();

		tabsDataSet=new StorageDataSet();

		String[] tabscolumn=new String[]{"TABS_ID","TABS_NAME","TABLE_ID","TABS_TYPE","TABS_TYPE_DESC","POSITION","POSITION_DESC"
				,"SEQUENCE","COL_NUM","QUERY_SQL","WHERE_SQL"};
		tabsDataSet.setColumns(ColumnsHelp.getColumns("SYS_TABS",tabscolumn));
		tabsDataSet.getColumn("TABS_TYPE_DESC").setPickList(new PickListDescriptor(
				pageTypeDataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "TABS_TYPE" },
				"DESCRIPTION", true));
		tabsDataSet.getColumn("POSITION_DESC").setPickList(new PickListDescriptor(
				postDataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "POSITION" },
				"DESCRIPTION", true));
		tabsDataSet.getColumn("TABS_ID").addColumnChangeListener(new ColumnChangeAdapter(){
			private String oldValue;
			@Override
			public void validate(DataSet dataset, Column arg1, Variant value)
					throws Exception, DataSetException {
				super.validate(dataset, arg1, value);
				oldValue=dataset.getString("TABS_ID");
			}
			@Override
			public void changed(DataSet arg0, Column arg1, Variant value) {
				//如果页签ID改变了，则对应的属性的页签ID全变
				if(value!=null&&!value.isNull()){
					attributeDataSet.first();
					for(int i=0;i<attributeDataSet.rowCount();i++){
						if(oldValue.equals(attributeDataSet.getString("TABS_ID"))){
							attributeDataSet.setString("TABS_ID", value.getString());
						}
						attributeDataSet.next();
					}
				}
			}
		});
		tabsDataSet.getColumn("TABLE_ID").addColumnChangeListener(new ColumnChangeAdapter(){
			@Override
			public void changed(DataSet arg0, Column arg1, Variant value) {
				//如果页签ID改变了，则对应的属性的页签ID全变
				if(value!=null&&!value.isNull()){
					attributeDataSet.first();
					for(int i=0;i<attributeDataSet.rowCount();i++){
						if(value.getString().equals(attributeDataSet.getString("TABS_ID")));{
							attributeDataSet.setString("TABLE_ID", value.getString());
						}
						attributeDataSet.next();
					}
				}
			}
		});

		attributeDataSet=new StorageDataSet();
		String[] attributeColumn=new String[]{"TABS_ID","TABLE_ID","COLUMN_ID","COLUMN_NAME","SEQUENCE","COL","ROW","LENGTH"
				,"PRECISE","IS_MUST","IS_MUST_DESC","IS_EDIT","IS_EDIT_DESC","EDIT_IS_MUST","EDIT_IS_MUST_DESC","DEFAULT_VALUE","DATA_LENGTH","DEFAULT_WIDTH","VIEW_COLOR","DATA_TYPE","DATA_TYPE_DESC","VIEW_TYPE","VIEW_TYPE_DESC","DATA_SOURCE_CONFIG","DATA_SOURCE","DATA_SOURCE_DESC","VIEW_FORMULA","EDITOR_FORMULA","VALIDATE_FORMULA"
				,"IS_TOTAL","IS_TOTAL_DESC","IS_CARD_SHOW","IS_CARD_SHOW_DESC","IS_LIST_SHOW","IS_LIST_SHOW_DESC","DEF1","DEF2","DEF3"
				,"SEQUENCE","DEFAULT_VALUE_SOURCE","DEFAULT_VALUE_SOURCE_DESC","PH_TYPE","CODE_TYPE","FUNC_ID","ZDY_CLASS","ZDY_ITEMS","VALUE_TYPE","VALUE_TYPE_DESC","COMBO_IS_EDIT","COMBO_IS_EDIT_DESC","REF_COLUMNS","CHAR_CASE","SYS_CODE_DESC.CHAR_CASE_DESC"};
		attributeDataSet.setColumns(ColumnsHelp.getColumns("SYS_ATTRIBUTE",attributeColumn));

		String[] basic=new String[]{"COLUMN_ID","COLUMN_NAME","SEQUENCE","COL","ROW","LENGTH","DATA_TYPE_DESC","SYS_CODE_DESC.CHAR_CASE_DESC"
				,"PRECISE","IS_EDIT_DESC","IS_MUST_DESC","EDIT_IS_MUST_DESC","DEFAULT_VALUE","DATA_LENGTH","DEFAULT_WIDTH","VIEW_COLOR","IS_TOTAL_DESC","IS_CARD_SHOW_DESC","IS_LIST_SHOW_DESC"};
		attributeDataSet.getColumn("IS_MUST_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_MUST" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("EDIT_IS_MUST_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "EDIT_IS_MUST" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("DATA_TYPE_DESC").setPickList(new PickListDescriptor(
				dataTypeDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DATA_TYPE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("VIEW_TYPE_DESC").setPickList(new PickListDescriptor(
				componentDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "VIEW_TYPE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("IS_TOTAL_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_TOTAL" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("IS_CARD_SHOW_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_CARD_SHOW" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("IS_LIST_SHOW_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_LIST_SHOW" },
				"DESCRIPTION", true));
		attributeDataSet.getColumn("IS_CARD_SHOW_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_CARD_SHOW" },
				"DESCRIPTION", true));
		//大小写
		attributeDataSet.getColumn("CHAR_CASE_DESC").setPickList(new PickListDescriptor(
				charCaseataSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "CHAR_CASE" },
				"DESCRIPTION", true));
		//值来源
		attributeDataSet.getColumn("DEFAULT_VALUE_SOURCE_DESC").setPickList(new PickListDescriptor(
				valueSourceDaraSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DEFAULT_VALUE_SOURCE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("DATA_SOURCE_DESC").setPickList(new PickListDescriptor(
				dataSourceDaraSet , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DATA_SOURCE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("VALUE_TYPE_DESC").setPickList(new PickListDescriptor(
				valueTypeDaraSet  , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "VALUE_TYPE" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("COMBO_IS_EDIT_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet  , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "COMBO_IS_EDIT" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("IS_EDIT_DESC").setPickList(new PickListDescriptor(
				boolStrDataSet  , new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "IS_EDIT" },
				"DESCRIPTION", true));

		attributeDataSet.getColumn("VIEW_FORMULA").setCustomEditable(true);
		attributeDataSet.getColumn("IS_EDIT").setCustomEditable(true);


		attributeDataSet.getColumn("VIEW_FORMULA").addColumnCustomEditListener(formulaColumnCustomEditListener);
		attributeDataSet.getColumn("EDITOR_FORMULA").addColumnCustomEditListener(formulaColumnCustomEditListener);
		attributeDataSet.getColumn("VALIDATE_FORMULA").addColumnCustomEditListener(formulaColumnCustomEditListener);

		attributeDataSet.getColumn("DATA_SOURCE_CONFIG").addColumnCustomEditListener(new ColumnCustomEditListener(){
			public Variant customEdit(DataSet dataset, Column column) {
				String type=dataset.getString("VIEW_TYPE");
				String value=dataset.getString("DATA_SOURCE_CONFIG");
				if(UIComponent.COMBO.equals(type)||UIComponent.REFERENCE.equals(type)){
					//如果是引用和下拉则能够弹出显示
					//					DataSourceConfigClient client=new DataSourceConfigClient(type,value);
					//得到当前表对象所有字段，存放在hashmap中
					HashMap<String,String> map=new HashMap<String, String>();
					String tableId=dataset.getString("TABLE_ID");
					for(int i=0;i<dataset.rowCount();i++){
						DataRow row = new DataRow(dataset);
						dataset.getDataRow(i, row);
						if(tableId.equals(row.getString("TABLE_ID"))){
							map.put(row.getString("COLUMN_ID"), row.getString("COLUMN_NAME"));
						}
					}

					RecordFormat masterformat = new RecordFormat("@");
					DataSetHelper.saveMetaToRecordFormat(attributeDataSet , masterformat);
					DataRow masterRow =new DataRow(attributeDataSet);
					attributeDataSet.getDataRow(masterRow);
					Record record = new Record(masterformat);
					DataSetHelper.saveRowToRecord(masterRow, record);
					DataSourceConfig config=DataSourceConfigClient.select(DesignFrame.this,map, type, record);
					if(config!=null){
						//						dataset.setString("", config.getCodeType());
						dataset.setString("PH_TYPE", config.getPhraseType());
						dataset.setString("CODE_TYPE", config.getCodeType());
						dataset.setString("FUNC_ID", config.getFuncId());
						dataset.setString("ZDY_CLASS", config.getZdyClass());
						dataset.setString("DATA_SOURCE", config.getSourceValue());
						//						StringBuffer buf=new StringBuffer();

						dataset.setString("ZDY_ITEMS", config.getZdyItems());
//						dataset.setString("REF_COLUMNS", config.getValueType());
						dataset.setString("REF_COLUMNS", config.getRefColumn());
						dataset.setString("VALUE_TYPE", config.getValueType());
						dataset.setString("COMBO_IS_EDIT",config.getIsEdit());

						Gson gson=new Gson();


						dataset.setString("DATA_SOURCE_CONFIG", gson.toJson(config));
					}
				}
				return null;
			}
		});


		GridBagLayout filterlayout = new GridBagLayout();
		int[] rows=new int[attributeColumn.length+1];
		rows[0]=5;
		filterlayout.columnWidths = (new int[] { 5, 5,5,5});
		filterlayout.rowHeights=rows;
		viewPanel.setLayout(filterlayout);
		for(int i=0;i<basic.length;i++){
			rows[i+1]=5;
		}
		filterlayout.rowHeights=rows;
		for(int i=0;i<basic.length;i++){
			JLabel label=new JLabel();
			if(basic[i].indexOf(".")!=-1){
				label.setText(DataModel.getDefault().getLabel(basic[i]));
			}else{
				label.setText(DataModel.getDefault().getLabel("SYS_ATTRIBUTE."+basic[i]));
			}
			JComponent jcom=null;
			if(basic[i].equals("DATA_TYPE_DESC")||basic[i].equals("IS_MUST_DESC")||basic[i].equals("IS_TOTAL_DESC")||basic[i].equals("IS_CARD_SHOW_DESC")||basic[i].equals("IS_LIST_SHOW_DESC")||basic[i].equals("SYS_CODE_DESC.CHAR_CASE_DESC")||basic[i].equals("IS_EDIT_DESC")){
				JdbComboBox text=new JdbComboBox();
				//				text.setColumnName(basic[i]);
				jcom=text;
			}else{
				JdbTextField text=new JdbTextField();
				//				text.setColumnName(basic[i]);
				jcom=text;
			}
			ColumnAware aware=(ColumnAware)jcom;
			aware.setDataSet(attributeDataSet);
			if(basic[i].indexOf(".")!=-1){
				aware.setColumnName(basic[i].substring(basic[i].indexOf(".")+1,basic[i].length()));
			}else{
				aware.setColumnName(basic[i]);
			}

			//添加到基本显示属性
			viewPanel.add(label, new GridBagConstraints(1, i+1, 1,
					1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
			viewPanel.add(jcom, new GridBagConstraints(2, i+1, 1,
					1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		viewPanel.add(new JLabel(), new GridBagConstraints(1, attributeColumn.length,2,
				1, 1,1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		String[] senior=new String[]{"VIEW_TYPE_DESC","DEFAULT_VALUE_SOURCE_DESC","DEFAULT_VALUE","VIEW_FORMULA","EDITOR_FORMULA","VALIDATE_FORMULA","DATA_SOURCE_CONFIG","DATA_SOURCE_DESC","COMBO_IS_EDIT_DESC","PH_TYPE","CODE_TYPE","FUNC_ID","ZDY_CLASS","ZDY_ITEMS","VALUE_TYPE_DESC","REF_COLUMNS","DEF1","DEF2","DEF3"};


		GridBagLayout filterlayout2 = new GridBagLayout();
		int[] rows2=new int[senior.length+1];
		filterlayout2.columnWidths = (new int[] { 5, 5,5,5});
		rows2[0]=5;
		for(int i=0;i<senior.length;i++){
			rows2[i+1]=5;
		}
		filterlayout2.rowHeights=rows2;

		seniorPanel.setLayout(filterlayout2);
		for(int i=0;i<senior.length;i++){
			JLabel label=new JLabel();
			label.setText(DataModel.getDefault().getLabel("SYS_ATTRIBUTE."+senior[i]));
			JComponent jcom=null;
			if(senior[i].equals("VIEW_TYPE_DESC")||senior[i].equals("DEFAULT_VALUE_SOURCE_DESC")||senior[i].equals("DATA_SOURCE_DESC")|| senior[i].equals("VALUE_TYPE_DESC")||senior[i].equals("COMBO_IS_EDIT_DESC")){
				JdbComboBox text=new JdbComboBox();
				text.setDataSet(attributeDataSet);
				text.setColumnName(senior[i]);
				jcom=text;
			} else if(senior[i].equals("VIEW_FORMULA")||senior[i].equals("EDITOR_FORMULA")||senior[i].equals("VALIDATE_FORMULA")||senior[i].equals("DATA_SOURCE_CONFIG")){
				JPanel panel=new JPanel();
				panel.setLayout(new BorderLayout());
				JdbTextField text=new JdbTextField();
				text.setDataSet(attributeDataSet);
				text.setColumnName(senior[i]);
				JdbButton btn=new JdbButton();
				btn.setDataSet(attributeDataSet);
				btn.setColumnName(senior[i]);
				btn.setMargin(new Insets(0, 0, 0, 0));
				btn.setIcon(new ImageIcon(
						DesignFrame.class
						.getClassLoader()
						.getResource(
								"com/evangelsoft/workbench/resources/buttons/find.png")));

				panel.add(text);
				panel.add(btn,BorderLayout.EAST);
				jcom=panel;
			}else{
				JdbTextField text=new JdbTextField();
				text.setDataSet(attributeDataSet);
				text.setColumnName(senior[i]);
				text.setColumns(15);
//				text.setPreferredSize(new Dimension(100,0))
				jcom=text;
			}

			//添加到基本显示属性
			seniorPanel.add(label, new GridBagConstraints(1, i+1, 1,
					1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
			seniorPanel.add(jcom, new GridBagConstraints(2, i+1, 1,
					1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		seniorPanel.add(new JLabel(), new GridBagConstraints(1, attributeColumn.length,2,
				1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));


		tableList = new JList<FieldRecord>();
		JScrollPane scroll = new JScrollPane(tableList);
		tableList.setDragEnabled(true);// 设置允许拖拽
		tableList.setTransferHandler(new SourceTransferHandler());
		tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//表设置单选
		tableList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(tableList. getSelectedValue()!=null){
					FieldRecord field=(FieldRecord)tableList. getSelectedValue();
					currentFieldModel=fieldModeMap.get(field.getField("TABLE_ID").getString());
					if(currentFieldModel==null){
						//为空必须替换，因为Jlist不允许设置null，用一个初始对象覆盖
						currentFieldModel=new DefaultListModel<FieldRecord>();
					}
					fieldList.setModel(currentFieldModel);
				}
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(tableList. getSelectedValue()!=null){
					FieldRecord field=(FieldRecord)tableList. getSelectedValue();
					currentFieldModel=fieldModeMap.get(field.getField("TABLE_ID").getString());
					if(currentFieldModel==null){
						//为空必须替换，因为Jlist不允许设置null，用一个初始对象覆盖
						currentFieldModel=new DefaultListModel<FieldRecord>();
					}
					fieldList.setModel(currentFieldModel);
				}
			};
		});


		fieldList = new JList<FieldRecord>();
		fieldList.setDragEnabled(true);// 设置允许拖拽
		fieldList.setTransferHandler(new SourceTransferHandler());
		JScrollPane scroll2 = new JScrollPane(fieldList);

		tablePane = new JTabbedPane();
		// JPanel panelx=new JPanel();
		rightSplitPane.setTopComponent(tablePane);
		tablePane.add(scroll, "表");
		tablePane.add(scroll2, "字段");

		//添加页签属性
		String[] tabs=new String[]{"TABS_ID","TABS_NAME","TABLE_ID","TABS_TYPE_DESC","POSITION_DESC","SEQUENCE","COL_NUM","REMARKS","QUERY_SQL","WHERE_SQL"};
		tabsAttributePanel.setLayout(filterlayout);
		for(int i=0;i<tabs.length;i++){
			JLabel label=new JLabel();
			label.setText(DataModel.getDefault().getLabel("SYS_TABS."+tabs[i]));
			JComponent jcom=null;
			if(tabs[i].equals("TABS_TYPE_DESC")||tabs[i].equals("POSITION_DESC")){
				JdbComboBox text=new JdbComboBox();
				text.setDataSet(tabsDataSet);
				text.setColumnName(tabs[i]);
				jcom=text;
			}else{
				JdbTextField text=new JdbTextField();
				text.setDataSet(tabsDataSet);
				text.setColumnName(tabs[i]);
				jcom=text;
			}

			//添加到基本显示属性
			tabsAttributePanel.add(label, new GridBagConstraints(1, i+1, 1,
					1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
			tabsAttributePanel.add(jcom, new GridBagConstraints(2, i+1, 1,
					1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		tabsAttributePanel.add(new JLabel(), new GridBagConstraints(1, tabs.length+2,2,
				1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		/**
		 * 右键菜单
		 */


		rightMenu=new JPopupMenu();
		addTable=new JMenuItem("添加当前表");
		addField=new JMenuItem("添加当前选中项");
		addTableByNewTabs=new JMenuItem("添加当前表到新页签");
		addFieldByNewTabs=new JMenuItem("添加当前项到新页签");
		addZdyTabs=new JMenuItem("添加自定义页签");
		addZdyField=new JMenuItem("添加自定义字项");
		deleteItem=new JMenuItem("删除当前选中");
		sortItem =new JMenuItem("排序");
		editingItem=new JMenuItem("编辑当前页签项");

		rightMenu.add(addTable);
		rightMenu.add(addField);
		rightMenu.add(addTableByNewTabs);
		rightMenu.add(addFieldByNewTabs);
		rightMenu.add(addZdyTabs);
		rightMenu.add(addZdyField);
		rightMenu.add(deleteItem);
		rightMenu.add(sortItem);
		rightMenu.add(editingItem);

		addTable.addActionListener(itemAction);
		addField.addActionListener(itemAction);
		addTableByNewTabs.addActionListener(itemAction);
		addFieldByNewTabs.addActionListener(itemAction);
		addZdyTabs.addActionListener(itemAction);
		addZdyField.addActionListener(itemAction);
		deleteItem.addActionListener(itemAction);
		sortItem .addActionListener(itemAction);
		editingItem.addActionListener(itemAction);
	}
	ItemActionListener itemAction=new ItemActionListener();
	private class ItemActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try{
				Object obj = e.getSource();
				//当前选中表格
				PagePanel panel=	getCurrentPagePanel();
				//			String type=BODY.equals(panel.getPos())?PagePanel.TABLE_TYPE:PagePanel.PANEL_TYPE;
				if(addTable.equals(obj)){
					addTable(fieldModeMap.get(getCurrentTable()),getCurrentPagePanel());
				}//当前选中项
				else if(addField.equals(obj)){
					addItem(panel, getCurrentField());
				}
				else if(addTableByNewTabs.equals(obj)){

					String tableId=getCurrentTable();
					if(tableId==null){
						//					JOptionPane.showMessageDialog(null,"good");
						//					JOptionPane.showMessageDialog(null, "请选择表再执行此操作！");
						return ;
					}
					//				if(e.getSource() instanceof UITabPanel){
					//				//先创建页签，再添加
					//				CodeValue codevalue= PageDialog.select(null,tableId,tableMap.get(tableId));

					//				}

					CodeValue codeValue= PageDialog.select(null,tableId,tableMap.get(tableId));
					if(codeValue!=null){
						PagePanel page = addPagePanel(codeValue, getCurrentPos(),PagePanel.TABLE_TYPE ,tableId);
						if(page!=null){
							addTable(fieldModeMap.get(getCurrentTable()),page);
						}
					}
				}//
				else if(addFieldByNewTabs.equals(obj)){
					//选中字段添加到页签
					String tableId=getCurrentTable();
					//				CodeValue codeValue=new CodeValue(tableId,tableMap.get(tableId));
					CodeValue codeValue= PageDialog.select(null,tableId,tableMap.get(tableId));
					if(codeValue==null){
						return;
					}
					PagePanel newpage = addPagePanel(codeValue, panel.getPos(),PagePanel.TABLE_TYPE,tableId);
					if(newpage==null){
						return;
					}
					//					addTable(fieldModeMap.get(getCurrentTable()),newpage);
					if(fieldList.getSelectedValue()!=null){
						addItem(newpage,new FieldRecord[]{fieldList.getSelectedValue()});
					}
				}else if(addZdyTabs.equals(obj)){
					/**
					 * 添加自定义项还得处理，先写个备注，不要忘记不要忘记不要忘记不要忘记
					 */
					CodeValue codeValue= PageDialog.select(null,"自定义","自定义");
					PagePanel newpage = addPagePanel(codeValue, panel.getPos(),PagePanel.TABLE_TYPE,null);
					if(newpage==null){
						return;
					}

				}else if(addZdyField.equals(obj)){
					//添加自定义
					CodeValue codevalue = FieldDialog.select(DesignFrame.this);
					if(codevalue!=null){
						Record record = new Record(FieldRecord.format);
						record.getField("COLUMN_NAME").setString(codevalue.value.toString());
						record.getField("COLUMN_ID").setString(codevalue.code.toString());

						FieldRecord field=new FieldRecord(record,FieldRecord.TABLE_TYPE);
						addItem(panel, new FieldRecord[]{field});
					}
				}else if(deleteItem.equals(obj)){
					//删除控件
					if(currentCom instanceof ULabel){
						ULabel label=(ULabel)currentCom;
						deleteItem(label.getPageId(), label.getAttr());
					}else if(currentCom instanceof JTabbedPane){
						//删除整个面板，先不写了
						JTabbedPane  pane=(JTabbedPane)currentCom;
						PagePanel page=(PagePanel)pane.getSelectedComponent();
						//先删除这个面板下的所有组件
						deletePage(page);
						//						batchDeteleItem(page.getTableId(),page.getComponentMap().keySet().toArray(new String[0]));
					}else if(currentCom instanceof PagePanel){
						PagePanel page=(PagePanel)currentCom;
						deletePage(page);
						//						batchDeteleItem(page.getTableId(),page.getComponentMap().keySet().toArray(new String[0]));
					}else if(currentCom instanceof DefaultTableHeaderCellRenderer){
						DefaultTableHeaderCellRenderer label=(DefaultTableHeaderCellRenderer)currentCom;
						deleteItem(label.getPageId(), label.getColumnId());
					}else if(currentCom instanceof  UIScrollPane ){
						PagePanel page=getCurrentPagePanel();
						deletePage(page);
						//						UIScrollPane scroll=(UIScrollPane)currentCom;
					}
					//					DesignFrame.this.updateUI();
				}else if(sortItem.equals(obj)){

				}else if(editingItem.equals(obj)){

				}
			}catch(Exception ex){
				ex.printStackTrace();

			}
		}
	}
	/**
	 *
	 * @author yyq
	 *构造一个
	 */

	private static final DataFlavor FILTER_CREAT_FLAVOR = new DataFlavor(FieldRecord.class, ":record");

	private class BillItemTransferable implements Transferable,Serializable {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private int type;
		private FieldRecord record;
		public BillItemTransferable(FieldRecord record,int type){
			this.record=record;
			this.type=type;
			aflavor.add(FILTER_CREAT_FLAVOR);
		}
		private ArrayList<DataFlavor> aflavor = new ArrayList<DataFlavor>();

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return aflavor.contains(flavor);
		}
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] f = new DataFlavor[aflavor.size()];
			aflavor.toArray(f);
			return f;
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (FILTER_CREAT_FLAVOR.equals(flavor))
				return record;

			throw new UnsupportedFlavorException(flavor);
		}
	}

	private class SourceTransferHandler extends TransferHandler {


		@Override
		protected Transferable createTransferable(JComponent c) {
			Transferable tran=null;
			if(c.equals(tableList)){
				JList<FieldRecord> fieldList=(JList<FieldRecord>)c;
				FieldRecord record=fieldList.getSelectedValue();
				if(currentTable!=null){
					tran=new BillItemTransferable(record,FieldRecord.TABLE_TYPE);
				}
			}else if(c.equals(fieldList)){
				JList<FieldRecord> fieldList=(JList<FieldRecord>)c;
				FieldRecord record=fieldList.getSelectedValue();
				tran=new BillItemTransferable(record,FieldRecord.FIELD_TYPE);
			}
			return tran;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}

	}

	@SuppressWarnings("serial")
	private class TargatTransferHandler extends TransferHandler {

		private String pos ;

		public TargatTransferHandler(String pos) {
			super();
			this.pos = pos;
		}

		@Override
		public boolean importData(JComponent c, Transferable t) {
			if (canImport(c, t.getTransferDataFlavors())) {
				//数据处理，按钮加载到界面
				try {
					FieldRecord record=(FieldRecord)t.getTransferData(FILTER_CREAT_FLAVOR);
					PagePanel panel=null;
					if(c instanceof UITabPanel){
						//如果是则添加新页签
						//						DataRow locateRow = new DataRow(detailDataSet,
						//						new String[] { "TABLE_ID" });
						//						locateRow.setString("TABLE_ID",record.getField("TABLE_ID").getString());
						//						detailDataSet.locate(locateRow, Locate.FIRST);
						CodeValue codevalue= PageDialog.select(null,record.getField("TABLE_ID").getString(),tableMap.get(record.getField("TABLE_ID").getString()));
						if(codevalue!=null){
							//添加新
							panel=addPagePanel(codevalue,pos,pos.equals(BODY)?PagePanel.TABLE_TYPE:PagePanel.PANEL_TYPE,record.getField("TABLE_ID").getString());
							if(panel!=null){
								//								tabsDataSet.insertRow(false);
								//								tabsDataSet.setString("TABS_ID", (String)codevalue.code);
								//								tabsDataSet.setString("TABS_NAME",(String)codevalue.value);
								//								if(pos.equals(HEAD)||pos.equals(TAIL)){
								//								tabsDataSet.setString("TABLE_ID",  mainRecord.getField("MAIN_TABLE_ID").getString());
								//								}else{
								//								tabsDataSet.setString("TABLE_ID",  (String)codevalue.code);
								//								}
								//								tabsDataSet.setString("TABS_TYPE",null);
								//								tabsDataSet.setString("POSITION",pos );
								//								tabsDataSet.setBigDecimal("SEQUENCE",new BigDecimal( panel.getIndex()));
								//								tabsDataSet.setString("TABS_TYPE",panel.getType() );
								//								panel.setTableId(tabsDataSet.getString("TABLE_ID"));
							}
						}
					}else if(c instanceof PagePanel){
						panel=(PagePanel)c;
					}
					if(panel!=null){
						//
						if(record.getType()==FieldRecord.TABLE_TYPE){
							//
							DefaultListModel<FieldRecord> models = fieldModeMap.get(record.getField("TABLE_ID").getString());
							for(int i=0;i<models.size();i++){
								FieldRecord filed=models.get(i);
								addItem(panel,new FieldRecord[]{filed});
							}
						}else{
							addItem(panel,new FieldRecord[]{record});
						}
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return true;
		}


		@Override
		public boolean canImport(JComponent comp, DataFlavor[] flavors) {
			for (int i = 0; i < flavors.length; i++) {
				if (FILTER_CREAT_FLAVOR.equals(flavors[i])){
					return true;
				}
			}
			return false;
		}
	}
	private UIComponent loadItem(PagePanel panel, FieldRecord record) throws Exception{
		if(panel==null||record.getField("COLUMN_ID")==null){
			System.out.println("有空值了");
			return null;
		}
		if(atrrMap.containsKey(panel.getTableId()+"#"+record.getField("COLUMN_ID").getString())){
			return null;
		}
		UIComponent uicom=panel.add(record);
		uicom.getLable().addMouseListener(componentSelect);
		this.atrrMap.put(attributeDataSet.getString("TABLE_ID")+"#"+attributeDataSet.getString("COLUMN_ID"),uicom );
		return uicom;
	}
	private void addItem(PagePanel panel, FieldRecord[] records) throws Exception{
		for(int i=0;i<records.length;i++){
			FieldRecord record=records[i];
			if(atrrMap.containsKey(panel.getTableId()+"#"+record.getField("COLUMN_ID").getString())){
				continue ;
			}
			UIComponent uicom=panel.add(record);
			//			panel.updateUI();
			if(uicom!=null){
				//				com.getLable().addMouseListener(componentSelect);
				if(uicom!=null){
					attributeDataSet.insertRow(false);
					//					if(BODY.equals(pos)){
					attributeDataSet.setString("TABLE_ID", panel.getTableId());
					//					}

					attributeDataSet.setString("TABS_ID", panel.getPageId());
					attributeDataSet.setString("COLUMN_ID", record.getField("COLUMN_ID").getString());
					//					System.out.println(attributeDataSet.getString("TABS_ID")+"  "+attributeDataSet.getString("COLUMN_ID"));
					attributeDataSet.setString("COLUMN_NAME", record.getField("COLUMN_NAME").getString());
					attributeDataSet.setBigDecimal("COL", BigDecimal.ONE);
					attributeDataSet.setBigDecimal("ROW",BigDecimal.ONE);
					attributeDataSet.setBigDecimal("LENGTH",  record.getField("LENGTH").isNull()? new BigDecimal(50): record.getField("LENGTH").getNumber());
					attributeDataSet.setBigDecimal("PRECISE", record.getField("PRECISE").getNumber());
					attributeDataSet.setString("IS_MUST", BoolStr.TRUE);
					attributeDataSet.setString("EDIT_IS_MUST", BoolStr.TRUE);
					attributeDataSet.setBigDecimal("DATA_LENGTH", record.getField("LENGTH").getNumber());
					attributeDataSet.setString("DEFAULT_VALUE", record.getField("DEFAULT_VALUE").getString());
					//					attributeDataSet.setString("VIEW_COLOR", panel.getPageId());
					attributeDataSet.setString("DATA_TYPE", record.getField("DATA_TYPE").isNull()?com.evangelsoft.workbench.config.types.DataType.VARCHAR: record.getField("DATA_TYPE").getString());
					attributeDataSet.setString("VIEW_TYPE",UIComponent.TEXT);
					attributeDataSet.setString("IS_TOTAL", BoolStr.FALSE);
					attributeDataSet.setString("IS_EDIT", BoolStr.TRUE);
					attributeDataSet.setString("IS_CARD_SHOW", BoolStr.TRUE);
					attributeDataSet.setString("IS_LIST_SHOW", BoolStr.TRUE);
					attributeDataSet.setBigDecimal("DEFAULT_WIDTH", record.getField("WIDTH")==null||record.getField("WIDTH").getNumber()==null? new BigDecimal("20"):record.getField("WIDTH").getNumber()     );
					attributeDataSet.setBigDecimal("SEQUENCE",new BigDecimal (uicom.getSequence()));//设置序号
					//设置大小写
					attributeDataSet.setString("CHAR_CASE",record.getField("CHAR_CASE").isNull()?"N":record.getField("CHAR_CASE").getString());//设置大小写
				}
				uicom.getLable().addMouseListener(componentSelect);

				this.atrrMap.put(attributeDataSet.getString("TABLE_ID")+"#"+attributeDataSet.getString("COLUMN_ID"),uicom );
			}
		}
		panel.updateUI();

	}

	private class TabDropTargetListenerAdpter implements DropTargetListener{

		private JTabbedPane tabpanel = null;

		public TabDropTargetListenerAdpter(JTabbedPane tabpanel) {
			super();
			this.tabpanel = tabpanel;
		}

		public void dragEnter(DropTargetDragEvent dtde) {
			//			MouseEvent e = new MouseEvent(tabpanel, 0, 0, 0, dtde.getLocation().x, dtde.getLocation().y, 0, false);
		}

		public void dragExit(DropTargetEvent dte) {

		}

		public void dragOver(DropTargetDragEvent dtde) {

			int index = tabpanel.indexAtLocation(dtde.getLocation().x, dtde.getLocation().y);

			if (index != -1 && tabpanel.getSelectedIndex() != index) {
				tabpanel.setSelectedIndex(index);
				//				MouseEvent e = new MouseEvent(tabpanel, 0, 0, 0, dtde.getLocation().x, dtde.getLocation().y, 0, false);
			}
		}

		public void drop(DropTargetDropEvent dtde) {
			//			System.out.println(dtde.getDropAction());
		}

		public void dropActionChanged(DropTargetDragEvent dtde) {
		}
	}
	@Override
	protected void linkDetailDataSets() {
		super.linkDetailDataSets();
		detailDataSets.add(tabsDataSet);
		detailDataSets.add(attributeDataSet);

	}
	private Object searchKey = null;

	@Override
	protected void prepared(Object arg0) throws Exception {
		//数据类型
		DataSetHelper.loadFromRecordSet(dataTypeDataSet,
				SysCodeHelper.getRecordSet("DATA_TYPE"));
		DataSetHelper.loadFromRecordSet(boolStrDataSet,
				SysCodeHelper.getRecordSet("BOOLEAN"));
		DataSetHelper.loadFromRecordSet(componentDataSet,
				SysCodeHelper.getRecordSet("COMPONENT_TYPE"));
		DataSetHelper.loadFromRecordSet(pageTypeDataSet,
				SysCodeHelper.getRecordSet("PAGE_TYPE"));
		DataSetHelper.loadFromRecordSet(postDataSet ,
				SysCodeHelper.getRecordSet("POSITION"));
		//大小写
		DataSetHelper.loadFromRecordSet(charCaseataSet ,
				SysCodeHelper.getRecordSet("CHAR_CASE"));
		DataSetHelper.loadFromRecordSet(valueSourceDaraSet ,
				SysCodeHelper.getRecordSet("DEFAULT_VALUE_SOURCE"));
		DataSetHelper.loadFromRecordSet(dataSourceDaraSet ,
				SysCodeHelper.getRecordSet("DATA_SOURCE"));
		DataSetHelper.loadFromRecordSet(valueTypeDaraSet ,
				SysCodeHelper.getRecordSet("VALUE_TYPE"));
		if(searchKey!=null){
			loadEntity(fetchEntity(searchKey));
		}

	}

	@Override
	protected Object prepareData() throws Exception {
		entityClass=SysTemplate.class;
		keyColumns = new String[] {"TEMPLATE_ID" };
		// 指定明细数据集的键值，用于重复判断。
		//		detailKeyColumns.put(tabsDataSet, new String[] { "TABS_ID"});
		//		detailKeyColumns.put(attributeDataSet, new String[] { "TABS_ID","COLUMN_ID"});
		return super.prepareData();
	}

	Record mainRecord;//单据主要字段
	boolean isLoad=false;//是否加载过
	/**
	 * 数据刷新后加载表集合和列集合
	 */
	@Override
	protected void loadEntity(Object paramObject) {
		super.loadEntity(paramObject);
		if(isLoad==false){
			try{
				RecordSet[] sets=(RecordSet[])paramObject;
				mainRecord=sets[0].getRecord(0);
				this.setTitle(mainRecord.getField("TEMPLATE_NAME").getString());
				if(dialog!=null){
					dialog.setTitle(mainRecord.getField("TEMPLATE_NAME").getString());
				}
				this.getParent().getParent();
				fieldModeMap.clear();
				listModel.clear();
				for(int i=0;i<detailDataSet.rowCount();i++){
					if(i==0){
						currentTable=detailDataSet.getString("TABLE_ID");
					}
					Record record = new Record(FieldRecord.format);
					record.getField("TABLE_NAME").setString(detailDataSet.getString("TABLE_NAME"));
					record.getField("TABLE_ID").setString(detailDataSet.getString("TABLE_ID"));
					FieldRecord field=new FieldRecord(record,FieldRecord.TABLE_TYPE);
					//			tables.add(detailDataSet.getString("TABLE_ID"));
					tableMap.put(detailDataSet.getString("TABLE_ID"), detailDataSet.getString("TABLE_NAME"));
					listModel.add(i, field);
					detailDataSet.next();
				}
				tableList .setModel(listModel);
				if(detailDataSet.rowCount()>0){
					//查询所有的表,字段选择
					SysTable systable=(SysTable)(new RMIProxy(Consumer.getDefaultConsumer().getSession()))	.newStub(SysTable.class);
					VariantHolder<Object> data=new VariantHolder<Object>();
					data.value=new RecordSet();
					VariantHolder<String> errMsg=new VariantHolder<String>();
					try {
						systable.getList(tableMap.keySet().toArray(new String[0]), data, errMsg);
						RecordSet set=(RecordSet)data.value;
						for(int i=0;i<set.recordCount();i++){
							DefaultListModel<FieldRecord> tempMode=null;
							if(fieldModeMap .containsKey(set.getRecord(i).getField("TABLE_ID").getString())){
								tempMode=fieldModeMap.get(set.getRecord(i).getField("TABLE_ID").getString());
							}else{
								tempMode=new DefaultListModel<FieldRecord>();
								fieldModeMap.put(set.getRecord(i).getField("TABLE_ID").getString(), tempMode);
							}
							FieldRecord field=new FieldRecord(set.getRecord(i),FieldRecord.FIELD_TYPE);
							tempMode.add(tempMode.getSize(), field);
						}
						//				detailDataSet.first();
						currentFieldModel=fieldModeMap.get(currentTable);
						if(currentFieldModel!=null){
							fieldList.setModel(currentFieldModel);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				//生成页签
				if(tabsDataSet.rowCount()>0){
					tabsDataSet.first();
					for(int i=0;i<tabsDataSet.rowCount();i++){
						CodeValue codeVaalue=new CodeValue();
						codeVaalue.code=tabsDataSet.getString("TABS_ID");
						codeVaalue.value=tabsDataSet.getString("TABS_NAME");
						PagePanel panle=createPagePanel(codeVaalue,tabsDataSet.getString("POSITION"),tabsDataSet.getString("TABS_TYPE"),tabsDataSet.getString("TABLE_ID"));
						if(!tabsDataSet.isNull("COL_NUM")){
							panle.setColNum(tabsDataSet.getBigDecimal("COL_NUM").intValue());
						}
						tabsDataSet.next();
					}
				}
				headPane.setSelectedIndex(0);
				bodyPane.setSelectedIndex(0);
				tailPane.setSelectedIndex(0);
				//生成字段选项
				if(attributeDataSet.rowCount()>0){
					attributeDataSet.first();
					for(int i=0;i<attributeDataSet.rowCount();i++){
						Record record = new Record(FieldRecord.format);
						record.getField("TABLE_ID").setString(attributeDataSet.getString("TABLE_ID"));
						record.getField("COLUMN_NAME").setString(attributeDataSet.getString("COLUMN_NAME"));
						record.getField("COLUMN_ID").setString(attributeDataSet.getString("COLUMN_ID"));
						record.getField("TABS_ID").setString(attributeDataSet.getString("TABS_ID"));
						record.getField("COL").setNumber(attributeDataSet.getBigDecimal("COL"));
						record.getField("ROW").setNumber(attributeDataSet.getBigDecimal("ROW"));
						record.getField("VIEW_TYPE").setString(attributeDataSet.getString("VIEW_TYPE"));
						record.getField("DEFAULT_VALUE").setString(attributeDataSet.getString("DEFAULT_VALUE"));
						record.getField("IS_MUST").setString(attributeDataSet.getString("IS_MUST"));
						record.getField("VIEW_COLOR").setString(attributeDataSet.getString("VIEW_COLOR"));
						record.getField("DEF1").setString(attributeDataSet.getString("DEF1"));
						record.getField("DEF2").setString(attributeDataSet.getString("DEF2"));
						record.getField("DEF3").setString(attributeDataSet.getString("DEF3"));

						FieldRecord field=new FieldRecord(record,FieldRecord.TABLE_TYPE);
						this.loadItem(pageMap.get(attributeDataSet.getString("TABS_ID")), field);
						attributeDataSet.next();
					}
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			isLoad=true;
		}

	}
	ComponentSelectMouseAdapter componentSelect=new ComponentSelectMouseAdapter();
	protected class ComponentSelectMouseAdapter extends MouseAdapter {
		private final LineBorder clicedBorder = new LineBorder(Color.RED, 1);



		@Override
		public void mousePressed(MouseEvent e) {
			JComponent temp = (JComponent)e.getComponent();
			if(currentCom!=null){
				currentCom.setBorder(null);
				//				currentCom.updateUI();
			}
			if(currentCom instanceof DefaultTableHeaderCellRenderer ){
				DefaultTableHeaderCellRenderer header =(DefaultTableHeaderCellRenderer)currentCom;
				header.setBorder(null);
				header.updateUI();
				getCurrentPagePanel().getTable().updateUI();
			}
			if(temp instanceof JTableHeader){
				JTableHeader header =(JTableHeader)temp;
				//				JTable xx=header.getTable();
				int pick = header.columnAtPoint(e.getPoint());
				//				temp=(JComponent)	header.getComponent(pick);
				TableColumnModel tcm = header.getColumnModel();
				//				TableColumn column = tcm.getColumn(header.getTable().convertColumnIndexToModel(pick));
				TableColumn column = tcm.getColumn(pick);
				DefaultTableHeaderCellRenderer cellRenderer = (DefaultTableHeaderCellRenderer)column.getHeaderRenderer();
				cellRenderer.setBorder(clicedBorder);
				temp=cellRenderer;
				cardLayout.show(attributePanel, "attribute");
				attributeDataSet.first();
				for(int i=0;i<attributeDataSet.rowCount();i++){
					if(attributeDataSet.getString("TABS_ID").equals(cellRenderer.getPageId())&&attributeDataSet.getString("COLUMN_ID").equals(cellRenderer.getColumnId())){
						break;
					}
					attributeDataSet.next();
				}
			}else if(temp instanceof DefaultTableCellRenderer){
				//TODO
				System.out.println("貌似不可能被执行的代码");
			}
				
			currentCom=temp;
			currentCom.setBorder(clicedBorder);
			if(currentCom instanceof JInternalFrame){
				cardLayout.show(attributePanel, "frame");
			}else if(currentCom instanceof ULabel){
				ULabel label=(ULabel)	currentCom;
				cardLayout.show(attributePanel, "attribute");
				attributeDataSet.first();
				for(int i=0;i<attributeDataSet.rowCount();i++){
					if(attributeDataSet.getString("TABS_ID").equals(label.getPageId())&&attributeDataSet.getString("COLUMN_ID").equals(label.getAttr())){
						break;
					}
					attributeDataSet.next();
				}
			}else if(currentCom instanceof PagePanel){
				PagePanel page=(PagePanel)	currentCom;
				cardLayout.show(attributePanel, "tabs");
				tabsDataSet.first();
				for(int i=0;i<tabsDataSet.rowCount();i++){
					if(tabsDataSet.getString("TABS_ID").equals(page.getPageId())){
						break;
					}
					tabsDataSet.next();
				}

			}else if(currentCom instanceof TableScrollPane){
				PagePanel page=(PagePanel)currentCom.getParent().getParent();
				cardLayout.show(attributePanel, "tabs");
				tabsDataSet.first();
				for(int i=0;i<tabsDataSet.rowCount();i++){
					if(tabsDataSet.getString("TABS_ID").equals(page.getPageId())){
						break;
					}
					tabsDataSet.next();
				}
			}
			else if(currentCom instanceof   JTabbedPane ){
				JTabbedPane  pane=(JTabbedPane)currentCom;
				PagePanel page=(PagePanel)pane.getSelectedComponent();
				cardLayout.show(attributePanel, "tabs");
				tabsDataSet.first();
				for(int i=0;i<tabsDataSet.rowCount();i++){
					if(tabsDataSet.getString("TABS_ID").equals(page.getPageId())){
						break;
					}
					tabsDataSet.next();
				}
			}else if (currentCom instanceof UIScrollPane){
				UIScrollPane header =(UIScrollPane)currentCom;
				PagePanel page=pageMap.get(header.getPageId());
				cardLayout.show(attributePanel, "tabs");
				tabsDataSet.first();
				for(int i=0;i<tabsDataSet.rowCount();i++){
					if(tabsDataSet.getString("TABS_ID").equals(page.getPageId())){
						break;
					}
					tabsDataSet.next();
				}
			}

			if(e.getButton()==MouseEvent.BUTTON3){
				if(currentCom instanceof DefaultTableHeaderCellRenderer){
					DefaultTableHeaderCellRenderer header =(DefaultTableHeaderCellRenderer)currentCom;
					rightMenu.show(pageMap.get(header.getPageId()), e.getPoint().x, e.getPoint().y);
					//					header.gett
				}else if(currentCom instanceof JTableHeader){
					JTableHeader header =(JTableHeader)temp;


					rightMenu.show(header.getTable(), e.getPoint().x, e.getPoint().y);
				}else{
					rightMenu.show(currentCom, e.getPoint().x, e.getPoint().y);
				}
			}
			//			((JPanel)DesignFrame.this.getContentPane()).updateUI();
		}
	}

	private PagePanel addPagePanel(CodeValue codevalue, String pos,String type,String tableId){
		PagePanel panel=new PagePanel(codevalue,type,tableId,pos);
		if(panel!=null){
			if(pageMap.containsKey(codevalue.code)){
				return null;
			}
			int index=0;
			UITabPanel tabPanel=null;
			if(pos.equals(HEAD)){
				tabPanel=headPane;
				//				headPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
				//				index=headPane.getComponentCount();
			}else if(pos.equals(BODY)){
				tabPanel=bodyPane;
				//				bodyPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
				//				index=bodyPane.getComponentCount();
			}else if(pos.equals(TAIL)){
				tabPanel=tailPane;
			}
			tabPanel.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			index=tabPanel.getComponentCount();
			pageMap.put((String)codevalue.code, panel);
			panel.addMouseListener(componentSelect);
			panel.setTransferHandler(new TargatTransferHandler(pos));
			panel.setIndex(index);
			panel.setPos(pos);
			tabsDataSet.insertRow(false);
			tabsDataSet.setString("TABS_ID", (String)codevalue.code);
			tabsDataSet.setString("TABS_NAME",(String)codevalue.value);

			if(pos.equals(HEAD)||pos.equals(TAIL)){
				tabsDataSet.setString("TABLE_ID",  mainRecord.getField("MAIN_TABLE_ID").getString());
			}else{
				tabsDataSet.setString("TABLE_ID",  (String)codevalue.code);

			}
			panel.setTableId(tabsDataSet.getString("TABLE_ID"));
			tabsDataSet.setString("TABS_TYPE",null);
			tabsDataSet.setString("POSITION",pos );
			tabsDataSet.setBigDecimal("SEQUENCE",new BigDecimal( panel.getIndex()));
			tabsDataSet.setBigDecimal("COL_NUM",new BigDecimal( panel.getColNum()));
			if(pos.equals(BODY)){
				tabsDataSet.setString("TABS_TYPE",PagePanel.TABLE_TYPE );
			}else{
				tabsDataSet.setString("TABS_TYPE",PagePanel.PANEL_TYPE );
			}
			tabPanel.updateUI();
		}
		return panel;
	}

	private PagePanel createPagePanel(CodeValue codevalue, String pos,String type,String tableId){
		PagePanel panel=new PagePanel(codevalue, type,tableId,pos);
		if(panel!=null){
			if(pageMap.containsKey(codevalue.code)){
				JOptionPane.showMessageDialog(null,"页签【"+codevalue.code+"】已经存在！");
				return null;
			}
			int index=0;
			if(pos.equals(HEAD)){
				index=headPane.getComponentCount();
				headPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			}else if(pos.equals(BODY)){
				index=bodyPane.getComponentCount();
				bodyPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			}else if(pos.equals(TAIL)){
				index=tailPane.getComponentCount();
				tailPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			}
			pageMap.put((String)codevalue.code, panel);
			panel.addMouseListener(componentSelect);
			panel.setTransferHandler(new TargatTransferHandler(pos));
			panel.setIndex(index);
			panel.setPos(pos);

		}
		return panel;
	}

	private void addTable(DefaultListModel<FieldRecord> models,PagePanel page) throws Exception{
		//		DefaultListModel<FieldRecord> models = fieldModeMap.get(page.getTableId());
		FieldRecord[] fields=new FieldRecord[models.size()];
		for(int i=0;i<models.size();i++){
			fields[i]=models.get(i);
		}
		addItem(page,fields);
		//		}
	}
	private void addTabs(DefaultListModel<FieldRecord> models,String pos){
		//就是创建createPagePanel方法
	}
	//	获取当前table
	private String  getCurrentTable(){
		FieldRecord record= tableList.getSelectedValue();
		if(record==null){
			return null;
		}
		return record.getField("TABLE_ID").getString();
	}
	private String  getCurrentFieldId(){
		FieldRecord record= fieldList.getSelectedValue();
		return record.getField("TABLE_ID").getString();
	}

	private FieldRecord[]  getCurrentField(){
		return tableList.getSelectedValuesList().toArray(new FieldRecord[0]);
	}
	private String getCurrentPos(){
		if(currentCom instanceof UITabPanel){
			if(currentCom==headPane){
				return HEAD;
			}else if(currentCom==bodyPane){
				return BODY;
			}
			else if(currentCom==tailPane){
				return TAIL;
			}
		}else{
			return getCurrentPagePanel().getPos();
		}
		return null;
	}

	private UITabPanel getCurrentTabPanel(){
		PagePanel page=	getCurrentPagePanel();
		if(page.getPos().equals(HEAD)){
			return headPane;
		}else if(page.getPos().equals(BODY)){
			return bodyPane;
		}else if(page.getPos().equals(TAIL)){
			return tailPane;
		}
		return null;
	}

	//	获取当前面板的方法
	private PagePanel getCurrentPagePanel(){
		if(this.currentCom!=null){
			if(currentCom instanceof ULabel){

				return pageMap.get(((ULabel)currentCom).getPageId());
			}else if(currentCom instanceof PagePanel){
				return (PagePanel)currentCom;
			}else if(currentCom instanceof TableScrollPane){
				return(PagePanel)currentCom.getParent().getParent();
			}else if(currentCom instanceof   JTabbedPane ){
				JTabbedPane  pane=(JTabbedPane)currentCom;
				return (PagePanel)pane.getSelectedComponent();
			}else if(currentCom instanceof DefaultTableHeaderCellRenderer){
				DefaultTableHeaderCellRenderer header =(DefaultTableHeaderCellRenderer)currentCom;
				return pageMap.get(header.getPageId());
			}else if(currentCom instanceof UIScrollPane){
				UIScrollPane header =(UIScrollPane)currentCom;
				return pageMap.get(header.getPageId());
			}
		}
		return null;
	}
	//删除当前选中
	private void deleteItem(String tabsId,String attrId){

		attributeDataSet.first();
		for(int i=0;i<attributeDataSet.rowCount();i++){
			if(attributeDataSet.getString("TABS_ID").equals(tabsId)&&attributeDataSet.getString("COLUMN_ID").equals(attrId)){
				this.getCurrentPagePanel().remove(attributeDataSet.getString("TABLE_ID")+"#"+attrId);
				atrrMap.remove(attributeDataSet.getString("TABLE_ID")+"#"+attrId);
				attributeDataSet.deleteRow();

				return;
			}
			attributeDataSet.next();
		}
		this.getCurrentPagePanel().sort();
		//		this.getCurrentTabPanel().updateUI();
	}
	//批量删除一个页签中数据
	private void batchDeteleItem(String tableId,String[] attrs){
		for(int j=0;j<attrs.length;j++){
			attributeDataSet.first();
			for(int i=0;i<attributeDataSet.rowCount();i++){
				if((attributeDataSet.getString("TABLE_ID")+"#"+attributeDataSet.getString("COLUMN_ID")).equals(attrs[j])){
					atrrMap.remove(attributeDataSet.getString("TABLE_ID")+"#"+attributeDataSet.getString("COLUMN_ID"));

					attributeDataSet.deleteRow();
					break;
				}
				attributeDataSet.next();
			}
		}
		this.getCurrentPagePanel().sort();
	}

	private void deletePage (PagePanel page){
		tabsDataSet.first();

		for(int j=0;j<tabsDataSet.rowCount();j++){
			if(tabsDataSet.getString("TABS_ID").equals(page.getPageId())){
				//先删除所有控件
				UITabPanel tabPanel = getCurrentTabPanel();

				batchDeteleItem(page.getTableId(),page.getComponentMap().keySet().toArray(new String[0]));
				//在删除当前面板
				tabPanel.remove(page.getPageId());
				pageMap.remove(page.getPageId());
				tabsDataSet.deleteRow();
				tabPanel.updateUI();
				break;
			}
			tabsDataSet.next();
		}
	}

	private Container getfatherComponent(){
		return this.getParent();
	}
	FormulaColumnCustomEditListener formulaColumnCustomEditListener=new FormulaColumnCustomEditListener();
	private class  FormulaColumnCustomEditListener implements ColumnCustomEditListener{
		public Variant customEdit(DataSet dataset, Column col) {
			String str= dataset.getString( col.getColumnName());
			String s= FormulaEditorDialog.formulaEditor(str);
			dataset.setString(col.getColumnName(),s);
			return null;
		}
	}
	public void showByKey(Component paramComponent, Object key)
	{
		this.searchKey=key;
		this.masterLoading=true;
		if ((paramComponent instanceof JDesktopPane))
		{
			if (getParent() != null)
				getParent().remove(this);
		}
		Container localContainer = null;
		if (paramComponent != null)
			for (localContainer = paramComponent.getParent(); localContainer != null; localContainer = localContainer.getParent())
				if (((localContainer instanceof JDialog)) || ((localContainer instanceof JFrame)))
					break;
		if (localContainer == null)
			dialog = new JDialog();
		else if ((localContainer instanceof JDialog))
			dialog = new JDialog((JDialog)localContainer);
		else if ((localContainer instanceof JFrame))
			dialog = new JDialog((JFrame)localContainer);
		else
			dialog = new JDialog();
		dialog.setDefaultCloseOperation(0);
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		this.setBorder(null);
		InternalFrameAdapter local1 = new InternalFrameAdapter()
		{
			public void internalFrameClosed(InternalFrameEvent paramAnonymousInternalFrameEvent)
			{
				dialog.dispose();
				DesignFrame.this.removeInternalFrameListener(this);
			}
		};
		this.addInternalFrameListener(local1);
		this.setVisible(true);
		dialog.setTitle(this.getTitle());
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(this, "Center");
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent paramAnonymousWindowEvent)
			{
				DesignFrame.this.doDefaultCloseAction();
				if (!dialog.isVisible())
					dialog.dispose();
			}
		});
		this.addInternalFrameListener(local1);
		dialog.setModal(true);
		dialog.pack();
		dialog.setLocationRelativeTo(paramComponent);
		dialog.setVisible(true);
	}
}
