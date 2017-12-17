package com.evangelsoft.easyui.print.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.collections.CollectionUtils;

import com.alee.utils.SwingUtils;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnAware;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.EditAdapter;
import com.borland.dx.dataset.ItemListDescriptor;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.borland.jb.util.ErrorResponse;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.print.type.PrintItemTool;
import com.evangelsoft.easyui.template.client.DesignFrame;
import com.evangelsoft.easyui.template.client.UMasterDetailFrame;
import com.evangelsoft.easyui.template.type.FrameType;
import com.evangelsoft.easyui.tool.ColumnsHelp;
import com.evangelsoft.easyui.tool.ComponentResize;
import com.evangelsoft.easyui.tool.DataTypeTool;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientutil.CodeTable;
import com.evangelsoft.workbench.config.client.SysCodeHelper;

/**
 * ClassName: PrintDesignFrame
 * @Description: 打印设计类
 * @author yyq
 * @date 2017-3-3
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintDesignFrame extends UMasterDetailFrame {

	// 计算像素和真实比例
	static {

	}

	private JSplitPane allPane;// 主分割面板

	private JSplitPane mainPane;// 主分割面板

	private JSplitPane leftPane;// 主分割面板

	private JTabbedPane dataSource;// 数据源

	private JPanel tableDataPanel;// 表数据

	private JPanel parameterPanel;// 参数面板

	private StorageDataSet parameterDataSet;

	private TableScrollPane parameterDataSetPanel;

	private JdbTable parameterDataSetTabel;

	private JPanel elementPanel;// 元素面板

	private JPanel middlePanel;

	// 工具条
	private JPanel toolBarPanel;

	private JToolBar toobar;

	private JLabel zoomLabel;

	// 页面设置 ,像素和还是尺寸
	private JComboBox<CodeValue> viewType;

	private JComboBox<PageType> pageType;

	private JComboBox<Integer> zoomBox;

	private JButton expandButton;// 放大

	private JButton narrowButton;// 缩小

	public JComboBox<String> fontBox;// 字体

	public JSpinner fontSize;// 字体大小

	private JButton leftAlignButton;// 左对齐

	private JButton rightAlignButton;// 右对齐

	private JButton verticalCenteringButton;// 垂直居中

	private JButton topAlignButton;// 上对齐，顶端对其

	private JButton downAlignButton;// 下对齐，底端对其

	private JButton horizontallyButton;// 水平居中

	private JPanel designPanel;// 设计面板

	private JScrollPane designPane;// 设计面板

	private JSplitPane rightPane;// 右边主件和属性

	//
	private JList<PrintElementType> componentList;// 组件面板

	DefaultListModel<PrintElementType> componentdModel = new DefaultListModel<PrintElementType>();

	private JPanel attributePanel;// 属性

	private JPanel pageAttributePanel;// 页面属性

	private JPanel elementAttributePanel;// 元素属性

	private JPanel panelAttributePanel;// 面板属性

	private JSplitPane dataPane;// 数据面板

	JScrollPane tableScrollPane;

	private JList<CodeValue> tableList;// 表属性

	DefaultListModel<CodeValue> tableListModel = new DefaultListModel<CodeValue>();

	JScrollPane fieldScrollPane;

	private JList<PrintElementSource> fieldList;// 字段属性

	/**
	 * 字段
	 */
	DefaultListModel<PrintElementSource> fieldListModel = new DefaultListModel<PrintElementSource>();

	HashMap<String, DefaultListModel<PrintElementSource>> fieldListMap = new HashMap<String, DefaultListModel<PrintElementSource>>();

	CardLayout cardLayout = new CardLayout();

	private JPanel pageHeadPane;// 头部面板，

	private JPanel tableHeadPane;// 头部面板，

	private JPanel tablePanel;// 中间选项卡

	private JPanel tableTailPanel;// 表尾

	private JPanel pageTailPanel;// 底部面板

	private JButton borderButton;// 默认边框

	private JButton customBorderButton;// 边框

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 3699743293823324038L;

	StorageDataSet elementDataSet;// 元素属性

	// 面板管理器，
	// List<PrintDesignPanel> linkedPanel = new ArrayList<PrintDesignPanel>();

	PrintPage printPage = new PrintPage(this);

	private String frameType;// 表单类型

	private HashMap<String, PrintStorageDataSet> dataSetPanelMap;

	private String mainTable;//

	private StorageDataSet dataSet;

	private ArrayList<Column> listColumn;

	private final LineBorder clicedBorder = new LineBorder(Color.RED, 3);

	private final LineBorder defaultBorder = new LineBorder(Color.GRAY, 1);

	// 选中的list的集合
	public ArrayList<PrintItem> selectList = new ArrayList<PrintItem>();

	// 选中的组件
	public PrintItem selectComp;

	// 上一次比例
	private double lastZoom = 1;

	private JPopupMenu rightMenu;// 右键菜单

	private JMenuItem copyItem;// 复制

	private JMenuItem pasteItem;// 粘贴

	private JMenuItem deleteItem;// 删除

	private JMenuItem copyForamtItem;// 复制

	private JMenuItem pasteForamtItem;// 复制

	private JPopupMenu panelRightMenu;// 面板的右键菜单

	private JMenuItem deletePaneItem;// 删除当前面板

	private JMenuItem toTableFormat;// 转为表格显示

	private JMenuItem toFreeFormat;// 转为自定义显示

	private JMenuItem insertPane;// 插入一个新的面板

	private JMenuItem addPane;// 添加一个新的面板

	private JMenuItem showAttribute;// 显示属性

	private boolean isCreate = false;

	private JDialog showDialog = new JDialog();

	private JdbTable elementTable;

	/**
	 * @Fields copyCacheItem : 用于缓存的对象
	 */
	private PrintItem copyCacheItem;

	/**
	 * @Fields copyCacheItem : 复制格式
	 */
	private PrintItem copyFormatCacheItem;

	private HashMap<String, JComponent> componentMap = new HashMap<String, JComponent>();

	/**
	 * 构造方法一般用于多表模式
	 *
	 * @param dataSetPanelMap
	 * @param mainTable
	 *            主表名称
	 * @param frameType
	 *            单据类型
	 */
	public PrintDesignFrame(String funcId, String frameType, HashMap<String, PrintStorageDataSet> dataSetPanelMap,
			String mainTable, boolean isCreate) {
		this.frameType = frameType;
		this.dataSetPanelMap = dataSetPanelMap;
		this.mainTable = mainTable;
		this.isCreate = isCreate;
		init();
	}

	public PrintDesignFrame(String funcId, HashMap<String, PrintStorageDataSet> dataSetPanelMap, String mainTable,
			boolean isCreate) {
		this.isCreate = isCreate;
		/* this.frameType = frameType; */
		this.dataSetPanelMap = dataSetPanelMap;
		this.mainTable = mainTable;
		this.isCreate = isCreate;
		init();
	}

	public PrintDesignFrame(String funcId, HashMap<String, PrintStorageDataSet> dataSetPanelMap, boolean isCreate) {
		/* this.frameType = frameType; */
		this.dataSetPanelMap = dataSetPanelMap;
		this.isCreate = isCreate;
		init();
	}

	public PrintDesignFrame(String funcId) {
		init();
	}

	public PrintDesignFrame() {
		init();
	}

	/**
	 * 构造方法一般用于单表模式和报表或者主从表只打印主表
	 *
	 * @param dataSet
	 */
	public PrintDesignFrame(String funcId, StorageDataSet dataSet, boolean isCreate) {
		this.frameType = FrameType.FRAME_SINGLE;
		this.dataSet = dataSet;
		this.listColumn = new ArrayList<Column>();
		listColumn.addAll(Arrays.asList(dataSet.getColumns()));
		init();

	}

	public PrintDesignFrame(String funcId, StorageDataSet masterDataSet, StorageDataSet detailDataSet, boolean isCreate) {
		PrintStorageDataSet master = new PrintStorageDataSet();
		master.setTableDesc("主表");
		master.setDataSet(masterDataSet);
		master.setTableName("master");
		if (dataSetPanelMap == null) {
			dataSetPanelMap = new HashMap<String, PrintStorageDataSet>();
		}
		dataSetPanelMap.put("master", master);

		PrintStorageDataSet detail = new PrintStorageDataSet();
		detail.setTableDesc("明细");
		detail.setDataSet(detailDataSet);
		detail.setTableName("detail");
		dataSetPanelMap.put("detail", detail);
		this.isCreate = isCreate;
		init();
	}

	/**
	 * 构造方法一般用于单表模式和报表或者主从表只打印主表
	 *
	 * @param listColumn
	 *            列集合
	 */
	public PrintDesignFrame(String funcId, List<Column> listColumn) {

		// 单表和报表统一用单表模式
		this.frameType = FrameType.FRAME_SINGLE;
		this.dataSet = new StorageDataSet();
		dataSet.setColumns(listColumn.toArray(new Column[0]));
		listColumn.addAll(listColumn);
		if (dataSetPanelMap == null || dataSetPanelMap.isEmpty()) {
			PrintStorageDataSet list = new PrintStorageDataSet();
			list.setTableDesc("列表");
			list.setDataSet(masterDataSet);
			list.setTableName("detail");
			dataSetPanelMap.put("list", list);
		}
		this.isCreate = true;
		init();
	}

	public static void addPrint() {

	}

	// 隐藏不需要的控件
	/*
	 * public PrintDesignFrame(String funcId) { }
	 */
	void init() {
		this.setTitle("打印设计");
		// 如果是单表模式
		this.headerPanel.setVisible(false);
		this.listFooterPanel.setVisible(false);
		this.footerPanel.setVisible(false);
		elementDataSet = new StorageDataSet();
		// elementDataSet.open();
		initialization();

		initTable();

		initAttr();
		// 初始化快捷键
		initQuickKey();
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap();
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();

		// final MovingCanvas movingCanvas = new MovingCanvas();
		// final InputMap im = movingCanvas.getInputMap();
		// final ActionMap am = movingCanvas.getActionMap();

		DirectionMoveAction upAction = new DirectionMoveAction(Direction.UP);
		DirectionMoveAction downAction = new DirectionMoveAction(Direction.DOWN);
		DirectionMoveAction leftAction = new DirectionMoveAction(Direction.LEFT);
		DirectionMoveAction rightAction = new DirectionMoveAction(Direction.RIGHT);

		inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
		actionMap.put("up", upAction);

		inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
		actionMap.put("down", downAction);

		inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
		actionMap.put("left", leftAction);

		inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		actionMap.put("right", rightAction);

		pack();

		printPage.setPaneDataSet(detailDataSet);
		printPage.setItemDataSet(elementDataSet);

		// 显示表元素，便于开发时候直观看数据
		elementTable = new JdbTable();
		showDialog.setPreferredSize(new Dimension(400, 300));
		showDialog.pack();
		TableScrollPane pane = new TableScrollPane(elementTable);
		showDialog.add(pane);
		elementTable.setDataSet(elementDataSet);
	}

	// 初始化界面
	public void initialization() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);
		System.out.println(width / dpi * 2.54);

		tableList = new JList<CodeValue>();
		tableList.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				// this.setIconTextGap(30);//设置图片和文字的间距
				super.getListCellRendererComponent(list, "  " + value.toString(), index, isSelected, cellHasFocus);
				return this;
			}
		});

		allPane = new JSplitPane();
		allPane.setResizeWeight(0.2);//
		allPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 分割面板先把整个页面分成左右俩边
		this.getContentPane().add(allPane);
		// 组件和数据
		dataPane = new JSplitPane();
		dataSource = new JTabbedPane();

		parameterPanel = new JPanel();
		tableDataPanel = new JPanel();
		dataSource.addTab("表数据", tableDataPanel);
		tableDataPanel.setLayout(new BorderLayout());
		tableList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableList.getSelectedValue() != null) {
					CodeValue code = tableList.getSelectedValue();
					fieldList.setModel(fieldListMap.get(code.code));
				}
			}
		});

		// tableList.addListSelectionListener(new ListSelectionListener(){

		// @Override
		// public void valueChanged(ListSelectionEvent e) {
		// if(e.)
		// CodeValue code = tableList.getSelectedValue();
		// fieldList.setModel(fieldListMap.get(code.code));
		// }

		// });
		tableDataPanel.add(tableList, BorderLayout.CENTER);

		dataSource.addTab("参数", parameterPanel);

		parameterDataSet = new StorageDataSet();

		Column column = new Column();
		column.setColumnName("KEY");
		column.setCaption("键");
		column.setScale(0);
		column.setPrecision(0);
		column.setWidth(12);
		// column.setDataType(DataType.TYPE_VARCHAR);
		column.setDataType(com.borland.dx.dataset.Variant.STRING);
		column.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column valueColumn = new Column();
		valueColumn.setColumnName("DESC");
		valueColumn.setCaption("描述");
		valueColumn.setScale(0);
		valueColumn.setPrecision(20);
		valueColumn.setWidth(12);
		/* valueColumn.setDataType(DataType.TYPE_VARCHAR); */
		valueColumn.setDataType(com.borland.dx.dataset.Variant.STRING);
		valueColumn.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column dataType = new Column();
		dataType.setColumnName("DATA_TYPE");
		dataType.setCaption("数据类型");
		dataType.setScale(0);
		dataType.setPrecision(20);
		dataType.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		dataType.setWidth(12);
		/* valueColumn.setDataType(DataType.TYPE_VARCHAR); */
		dataType.setDataType(com.borland.dx.dataset.Variant.STRING);
		dataType.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column dataTypeDesc = new Column();

		dataTypeDesc.setColumnName("DATA_TYPE_DESC");
		dataTypeDesc.setCaption("数据类型");
		dataTypeDesc.setScale(0);
		dataTypeDesc.setPrecision(20);
		dataTypeDesc.setWidth(12);
		/* valueColumn.setDataType(DataType.TYPE_VARCHAR); */
		dataTypeDesc.setDataType(com.borland.dx.dataset.Variant.STRING);
		dataTypeDesc.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		// column2.set
		StorageDataSet storagedataset1 = CodeTable.defaultCodeTable.getDataSet("DATA_TYPE");
		dataTypeDesc.setPickList(new PickListDescriptor(storagedataset1, new String[] { "CODE" },
				new String[] { "DESCRIPTION" }, new String[] { "DATA_TYPE" }, "DESCRIPTION", true));

		parameterDataSetTabel = new JdbTable();
		parameterDataSetTabel.setDragEnabled(true);// 设置允许拖拽
		parameterDataSetTabel.setTransferHandler(sourceHandler);
		parameterDataSetTabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 表设置单选

		// 只能单选
		parameterDataSetTabel.setDataSet(parameterDataSet);
		parameterDataSet.setColumns(new Column[] { column, valueColumn, dataType, dataTypeDesc });
		parameterDataSet.open();
		// bltnDataSetTabel.setEditable(false);
		parameterDataSetPanel = new TableScrollPane(parameterDataSetTabel);
		parameterPanel.setLayout(new BorderLayout());
		parameterPanel.add(parameterDataSetPanel, BorderLayout.CENTER);
		parameterDataSetTabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		parameterDataSetTabel.setEditable(false);

		leftPane = new JSplitPane();
		leftPane.setOrientation(JSplitPane.VERTICAL_SPLIT);// 分割面板先把整个页面分成上下俩边个
		leftPane.setResizeWeight(0.2);// nb
		leftPane.setTopComponent(dataSource);
		elementPanel = new JPanel();
		leftPane.setBottomComponent(elementPanel);

		fieldList = new JList<PrintElementSource>();
		fieldList.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				// this.setIconTextGap(30);//设置图片和文字的间距
				super.getListCellRendererComponent(list, "  " + value.toString(), index, isSelected, cellHasFocus);
				return this;
			}
		});
		JScrollPane fieldscroll = new JScrollPane(fieldList);
		fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fieldList.setDragEnabled(true);
		fieldList.setTransferHandler(sourceHandler);
		elementPanel.setLayout(new BorderLayout());
		elementPanel.add(fieldscroll);

		allPane.setLeftComponent(leftPane);
		mainPane = new JSplitPane();
		allPane.setRightComponent(mainPane);

		// 设计面板
		designPanel = new JPanel();
		// designPanel.setBackground(SystemColor.WHITE);
		designPanel.setLayout(null);
		// designPane.setBounds(0, 0, 842, 700);
		// designPanel.setSize(842, 700);
		designPanel.setPreferredSize(new Dimension(597, 844));
		final JPanel panel = new JPanel();

		// panel.setPreferredSize(new Dimension(597, 844));
		// panel.setLayout(new BorderLayout());
		panel.add(designPanel);
		designPane = new JScrollPane(panel);
		// designPane.setBounds(0, 0, 200, 100);

		// this.designPane.setHorizontalScrollBarPolicy(32);
		// this.designPane
		// .setVerticalScrollBarPolicy(22);

		// 因为要设置放大和所需，不能设置任何布局

		// LineBorder clicedBorder = new LineBorder(Color.RED, 1);

		designPanel.setBackground(SystemColor.WHITE);

		// JPanel panel=new JPanel();
		// panel.add(designPane);

		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(designPane);
		// middlePanel.setBackground(SystemColor.GRAY);

		mainPane.setLeftComponent(middlePanel);

		toolBarPanel = new JPanel();
		middlePanel.add(toolBarPanel, BorderLayout.NORTH);
		toobar = new JToolBar();
		// toolBarPanel.add(toobar);

		// zoomLabel=new JLabel("100%");
		viewType = new JComboBox<CodeValue>();
		viewType.addItem(new CodeValue("1", "像素"));
		viewType.addItem(new CodeValue("2", "尺寸"));
		viewType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// 计算尺寸
				}
			}

		});

		pageType = new JComboBox<PageType>();
		pageType.addItem(new PageType("A1", 0, 0));
		pageType.addItem(new PageType("A2", 1191, 1684));
		pageType.addItem(new PageType("A3", 1191, 842));
		pageType.addItem(new PageType("A4", 595, 842));
		pageType.addItem(new PageType("A5", 0, 0));
		pageType.addItem(new PageType("B4", 0, 0));
		pageType.addItem(new PageType("B5", 0, 0));
		pageType.addItem(new PageType("自定义", 0, 0));

		zoomBox = new JComboBox<Integer>();
		zoomBox.addItem(25);
		zoomBox.addItem(50);
		zoomBox.addItem(75);
		zoomBox.addItem(100);

		zoomBox.addItem(125);
		zoomBox.addItem(150);
		zoomBox.addItem(175);
		zoomBox.addItem(200);
		zoomBox.addItem(250);
		zoomBox.addItem(300);
		zoomBox.addItem(400);
		zoomBox.addItem(800);

		zoomBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// 计算放大或者缩小
					Double zoom = Double.parseDouble(e.getItem().toString()) / 100;

					BigDecimal b = new BigDecimal(zoom / lastZoom);
					double f1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
					ComponentResize.reSetSize(panel, f1, zoom);
					lastZoom = zoom;
				}
			}
		});
		// 默认100%
		zoomBox.setSelectedIndex(3);
		expandButton = new JButton();
		expandButton.setText("放大");
		expandButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//
				if (zoomBox.getSelectedIndex() < zoomBox.getItemCount() - 1) {
					zoomBox.setSelectedIndex(zoomBox.getSelectedIndex() + 1);
				}
			}

		});
		narrowButton = new JButton("缩小");
		narrowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//
				if (zoomBox.getSelectedIndex() > 0) {
					zoomBox.setSelectedIndex(zoomBox.getSelectedIndex() - 1);
				}
			}

		});

		fontBox = new JComboBox<String>();
		// 读取系统字体
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		int index = 0;
		for (int i = 0; i < fontName.length; i++) {
			if (fontName[i].equals(Font.DIALOG)) {
				index = i;
			}
			fontBox.addItem(fontName[i]);
		}
		fontBox.setSelectedIndex(index);

		fontSize = new JSpinner(new SpinnerNumberModel(12, 1, 100, 1));

		topAlignButton = new AlignmentButton("顶端对齐", null, JLabel.TOP);

		verticalCenteringButton = new AlignmentButton("垂直居中", null, JLabel.CENTER);

		downAlignButton = new AlignmentButton("底端对齐", null, JLabel.BOTTOM);

		leftAlignButton = new AlignmentButton("左对齐", JLabel.LEFT, null);
		horizontallyButton = new AlignmentButton("水平居中", JLabel.CENTER, null);
		rightAlignButton = new AlignmentButton("右对齐", JLabel.RIGHT, null);

		borderButton = new JButton("边框");
		// //
		customBorderButton = new JButton("自定义边框");

		toolBarPanel.setLayout(new FlowLayout());

		toolBarPanel.add(viewType);
		toolBarPanel.add(pageType);
		toolBarPanel.add(zoomBox);
		toobar.addSeparator();
		toolBarPanel.add(expandButton);
		toobar.addSeparator();
		toolBarPanel.add(narrowButton);
		toobar.addSeparator();
		toolBarPanel.add(fontBox);
		toobar.addSeparator();
		toolBarPanel.add(fontSize);
		toobar.addSeparator();
		toolBarPanel.add(topAlignButton);
		toobar.addSeparator();
		toolBarPanel.add(verticalCenteringButton);
		toobar.addSeparator();
		toolBarPanel.add(downAlignButton);
		toobar.addSeparator();
		toolBarPanel.add(leftAlignButton);
		toobar.addSeparator();
		toolBarPanel.add(horizontallyButton);
		toobar.addSeparator();
		toolBarPanel.add(rightAlignButton);
		toolBarPanel.add(borderButton);
		toolBarPanel.add(customBorderButton);

		rightPane = new JSplitPane();
		mainPane.setRightComponent(rightPane);

		// mainPane.setResizeWeight()
		mainPane.setResizeWeight(0.8);//

		// 返回确定额外空间如何分配的数
		componentList = new JList<PrintElementType>();
		rightPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		JScrollPane scroll = new JScrollPane(componentList);
		componentList.setDragEnabled(true);// 设置允许拖拽
		componentList.setTransferHandler(sourceHandler);
		componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 表设置单选

		rightPane.setTopComponent(scroll);
		attributePanel = new JPanel();
		rightPane.setBottomComponent(attributePanel);
		rightPane.setResizeWeight(0.4);// nb
		attributePanel.setLayout(cardLayout);

		pageAttributePanel = new JPanel();
		elementAttributePanel = new JPanel();
		panelAttributePanel = new JPanel();
		attributePanel.add(pageAttributePanel);
		attributePanel.add(panelAttributePanel);
		attributePanel.add(elementAttributePanel);

		// componentList.setLayout(new BoxLayout(componentList, 3));

		componentdModel.addElement(new PrintElementType("0", " 图表",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType("1", " 图片",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType("2", " 表格",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType("3", " 线条",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType("4", " 静态文本",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType("5", " 值控件",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentList.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value != null) {
					PrintElementType type = (PrintElementType) value;
					try {
						this.setHorizontalTextPosition(SwingConstants.TRAILING);
						ImageIcon icon = new ImageIcon(DesignFrame.class.getClassLoader().getResource(type.getIcon()));
						this.setIcon(icon);
						// this.setIconTextGap(30);设置图片和文字的间距
						this.setText(type.getDesc());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					// 设置选取与取消选取的前景与背景颜色.
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}

				return this;
			}
		});
		componentList.setModel(componentdModel);

		rightMenu = new JPopupMenu();
		copyItem = new JMenuItem("复制(Ctrl+c)");
		pasteItem = new JMenuItem("粘贴(Ctrl+v)");
		pasteItem.setEnabled(false);
		deleteItem = new JMenuItem("删除(delete)");
		copyForamtItem = new JMenuItem("复制格式(shift+c)");
		pasteForamtItem = new JMenuItem("粘贴格式shift+v");
		pasteForamtItem.setEnabled(false);

		rightMenu.add(copyItem);

		rightMenu.add(deleteItem);
		rightMenu.add(copyForamtItem);
		rightMenu.add(pasteForamtItem);

		copyItem.addActionListener(itemAction);
		pasteItem.addActionListener(itemAction);
		deleteItem.addActionListener(itemAction);
		copyForamtItem.addActionListener(itemAction);
		pasteForamtItem.addActionListener(itemAction);

		panelRightMenu = new JPopupMenu();
		deletePaneItem = new JMenuItem("删除当前面板");
		toTableFormat = new JMenuItem("切换为表格");
		toFreeFormat = new JMenuItem("自定义显示");
		insertPane = new JMenuItem("插入一个新的面板");
		addPane = new JMenuItem("添加一个新的面板");
		showAttribute = new JMenuItem("显示属性");

		panelRightMenu.add(pasteItem);
		panelRightMenu.add(deletePaneItem);
		panelRightMenu.add(toTableFormat);
		panelRightMenu.add(toFreeFormat);
		panelRightMenu.add(insertPane);
		panelRightMenu.add(addPane);
		panelRightMenu.add(showAttribute);

		deletePaneItem.addActionListener(itemAction);
		toTableFormat.addActionListener(itemAction);
		toFreeFormat.addActionListener(itemAction);
		insertPane.addActionListener(itemAction);
		addPane.addActionListener(itemAction);
		showAttribute.addActionListener(itemAction);

	}

	public void showFrame(String str) {
		showFrame();
	}

	public void showFrame() {

		final PrintDesignFrame print = this;
		print.setBorder(null);
		final JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 设置全屏时候用的
		// frame.setUndecorated(true);
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		frame.setVisible(true);
		frame.setTitle(print.getTitle());

		// 3.设置全屏模式

		frame.getContentPane().add(print);
		frame.getContentPane().setLayout(new BorderLayout());
		((BasicInternalFrameUI) print.getUI()).setNorthPane(null);
		frame.getContentPane().add(print, "Center");

		((BasicInternalFrameUI) print.getUI()).setNorthPane(null);
		print.setVisible(true);

		InternalFrameAdapter local1 = new InternalFrameAdapter() {

			public void internalFrameClosed(InternalFrameEvent paramAnonymousInternalFrameEvent) {
				frame.dispose();
				print.removeInternalFrameListener(this);
			}
		};
		print.addInternalFrameListener(local1);

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
				print.doDefaultCloseAction();
				frame.dispose();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
			}
		});
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private StorageDataSet boolStrDataSet;

	private StorageDataSet horizontalAlignmentDataSet;

	private StorageDataSet verticalAlignmentDataSet;

	private StorageDataSet textPositionDataSet;

	private StorageDataSet rotationDataSet;

	private StorageDataSet fontNameDataSet;

	private StorageDataSet printViewDataSet;

	/**
	 * 初始化表格
	 */
	void initTable() {
		boolStrDataSet = new StorageDataSet();
		horizontalAlignmentDataSet = new StorageDataSet();
		verticalAlignmentDataSet = new StorageDataSet();
		textPositionDataSet = new StorageDataSet();
		rotationDataSet = new StorageDataSet();
		fontNameDataSet = new StorageDataSet();
		printViewDataSet = new StorageDataSet();

		String[] columnStrs = new String[] { "FUNC_ID", "PAGE_WIDTH", "HEIGHT_AUTO", "HEIGHT_AUTO_DESC", "PAGE_HEIGHT",
				"ORIENTATION", "ORIENTATION_DESC", "LEFT_MARGIN", "RIGHT_MARGIN", "TOP_MARGIN", "BOTTOM_MARGIN",
				"COL_NUM", "COL_WIDTH" };
		masterDataSet.setColumns(ColumnsHelp.getColumns("SYS_PRINT_TEMPLATE_PAGE", columnStrs));
		masterDataSet.open();
		String[] plate = new String[] { "PRINT_ID", "UNIQUE_ID", "INDEX", "HEIGHT", "WIDTH", "AUTO_STRETCH",
				"AUTO_STRETCH_DESC", "VIEW_TYPE", "VIEW_TYPE_DESC", "BACK_TEXT" };
		detailDataSet.setColumns(ColumnsHelp.getColumns("SYS_PRINT_TEMPLATE_PLATE", plate));
		detailDataSet.getColumn("VIEW_TYPE_DESC").setPickList(
				new PickListDescriptor(printViewDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "VIEW_TYPE" }, "DESCRIPTION", true));

		detailDataSet.getColumn("AUTO_STRETCH_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "AUTO_STRETCH" }, "DESCRIPTION", true));

		//
		String[] detailColumn = new String[] { "PRINT_ID", "PANEL_ID", "UNIQUE_ID", "RELATION_ID", "INDEX", "TYPE",
				"X", "Y", "WIDTH", "HEIGHT", "FORECOLOR", "BACKCOLOR", "TEXT", "EXPRESSION", "FONT_NAME", "FONT_SIZE",
				"BOLD", "BOLD_DESC", "ITALIC", "ITALIC_DESC", "UNDERLINE", "UNDERLINE_DESC", "STRIKETHROUGH",
				"STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT", "HORIZONTAL_ALIGNMENT_DESC", "VERTICAL_ALIGNMENT",
				"VERTICAL_ALIGNMENT_DESC", "ROTATION", "ROTATION_DESC", "IMAGE_SCALE", "IMAGE_SCALE_DESC",
				"LINE_DIRECTION", "LINE_DIRECTION_DESC", "LINE_HEIGHT", "BAR_TYPE", "BAR_TYPE_DESC", "TEXT_POSITION",
				"TEXT_POSITION_DESC", "EXEC_CODE" };
		elementDataSet.setColumns(ColumnsHelp.getColumns("SYS_PRINT_TEMPLATE_ELEMENT", detailColumn));
		elementDataSet.getColumn("BOLD_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "BOLD" }, "DESCRIPTION", true));
		elementDataSet.getColumn("ITALIC_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "ITALIC" }, "DESCRIPTION", true));
		elementDataSet.getColumn("UNDERLINE_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "UNDERLINE" }, "DESCRIPTION", true));
		elementDataSet.getColumn("STRIKETHROUGH_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "STRIKETHROUGH" }, "DESCRIPTION", true));
		elementDataSet.getColumn("LINE_DIRECTION_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "LINE_DIRECTION" }, "DESCRIPTION", true));

		elementDataSet.getColumn("HORIZONTAL_ALIGNMENT_DESC").setPickList(
				new PickListDescriptor(horizontalAlignmentDataSet, new String[] { "CODE" },
						new String[] { "DESCRIPTION" }, new String[] { "HORIZONTAL_ALIGNMENT" }, "DESCRIPTION", true));
		elementDataSet.getColumn("VERTICAL_ALIGNMENT_DESC").setPickList(
				new PickListDescriptor(verticalAlignmentDataSet, new String[] { "CODE" },
						new String[] { "DESCRIPTION" }, new String[] { "VERTICAL_ALIGNMENT" }, "DESCRIPTION", true));

		elementDataSet.getColumn("ROTATION_DESC").setPickList(
				new PickListDescriptor(rotationDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "ROTATION" }, "DESCRIPTION", true));
		elementDataSet.getColumn("TEXT_POSITION_DESC").setPickList(
				new PickListDescriptor(textPositionDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "TEXT_POSITION" }, "DESCRIPTION", true));
		try {
			for (Column column : elementDataSet.getColumns()) {
				column.addColumnChangeListener(elementColumnChangeAdapter);
			}
		} catch (DataSetException e1) {
			e1.printStackTrace();
		} catch (TooManyListenersException e1) {
			e1.printStackTrace();
		}
		elementDataSet.addEditListener(new EditAdapter() {

			@Override
			public void updateError(DataSet arg0, ReadWriteRow arg1, DataSetException arg2, ErrorResponse arg3) {
				super.updateError(arg0, arg1, arg2, arg3);
			}

			@Override
			public void updated(DataSet arg0) {
				super.updated(arg0);
			}

			@Override
			public void updating(DataSet arg0, ReadWriteRow arg1, ReadRow arg2) throws Exception {
				super.updating(arg0, arg1, arg2);
			}

		});
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		int index = 0;
		for (int i = 0; i < fontName.length; i++) {
			if (fontName[i].equals(Font.DIALOG)) {
				index = i;
			}
			fontBox.addItem(fontName[i]);
		}
		fontBox.setSelectedIndex(index);
		elementDataSet.getColumn("FONT_NAME").setItemList(new ItemListDescriptor(fontNameDataSet, "FONT_NAME", true));
	}

	/**
	 * ClassName: ElementColumnChangeAdapter 
	 * @Description: 元素改变时候触发监听，通知UI显示等等
	 * @author yangyq02
	 * @date 2017年12月14日
	 */
	ElementColumnChangeAdapter elementColumnChangeAdapter = new ElementColumnChangeAdapter();

	private class ElementColumnChangeAdapter extends ColumnChangeAdapter {

		@Override
		public void changed(DataSet arg0, Column column, Variant value) {
			// 获取被选中的组合
			if (CollectionUtils.isNotEmpty(selectList)) {
				PrintItemTool.setValue(selectList, column.getColumnName(), value.getAsObject());
			}
		}

	}

	/**
	 * @Description: 初始化快捷键
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017年9月29日
	 */
	private void initQuickKey() {
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(2);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();
		// 复制快捷键
		KeyStroke ctrlc = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrlc, "ctrl+c");
		actionMap.put("ctrl+c", this.copyItemAction);
		// 粘贴快捷键
		KeyStroke ctrlv = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrlv, "ctrl+v");
		actionMap.put("ctrl+c", this.pasteItemAction);
		// 删除快捷键
		KeyStroke ctrld = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrld, "ctrl+d");
		actionMap.put("ctrl+d", this.deleteItemAction);

		KeyStroke shiftc = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK, false);
		inputMap.put(shiftc, "shift+c");
		actionMap.put("shift+c", this.copyFormatItemAction);
		// 粘贴快捷键
		KeyStroke shiftv = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK, false);
		inputMap.put(shiftv, "shift+v");
		actionMap.put("shift+v", this.pasteFormatAction);
	}

	void initAttr() {
		// 页面属性
		String[] pageAttr = new String[] { "PAGE_WIDTH", "HEIGHT_AUTO", "HEIGHT_AUTO_DESC", "PAGE_HEIGHT",
				"ORIENTATION_DESC", "LEFT_MARGIN", "RIGHT_MARGIN", "TOP_MARGIN", "BOTTOM_MARGIN", "COL_NUM",
				"COL_WIDTH" };

		GridBagLayout filterlayout = new GridBagLayout();
		int[] rows = new int[pageAttr.length + 1];
		rows[0] = 5;
		filterlayout.columnWidths = (new int[] { 5, 5, 5, 5 });
		filterlayout.rowHeights = rows;
		for (int i = 0; i < pageAttr.length; i++) {
			rows[i + 1] = 5;
		}
		filterlayout.rowHeights = rows;
		pageAttributePanel.setLayout(filterlayout);

		for (int i = 0; i < pageAttr.length; i++) {
			JLabel label = new JLabel();
			if (pageAttr[i].indexOf(".") != -1) {
				label.setText(DataModel.getDefault().getLabel(pageAttr[i]));
			} else {
				label.setText(DataModel.getDefault().getLabel("SYS_PRINT_TEMPLATE_PAGE." + pageAttr[i]));
			}
			JComponent jcom = null;
			if (pageAttr[i].equals("DATA_TYPE_DESC") || pageAttr[i].equals("IS_MUST_DESC")
					|| pageAttr[i].equals("IS_TOTAL_DESC") || pageAttr[i].equals("IS_CARD_SHOW_DESC")
					|| pageAttr[i].equals("IS_LIST_SHOW_DESC") || pageAttr[i].equals("SYS_CODE_DESC.CHAR_CASE_DESC")
					|| pageAttr[i].equals("IS_EDIT_DESC")) {
				JdbComboBox text = new JdbComboBox();
				text.setColumnName(pageAttr[i]);
				jcom = text;
			} else {
				JdbTextField text = new JdbTextField();
				text.setColumnName(pageAttr[i]);
				jcom = text;
			}
			ColumnAware aware = (ColumnAware) jcom;
			aware.setDataSet(masterDataSet);
			if (pageAttr[i].indexOf(".") != -1) {
				aware.setColumnName(pageAttr[i].substring(pageAttr[i].indexOf(".") + 1, pageAttr[i].length()));
			} else {
				aware.setColumnName(pageAttr[i]);
			}

			// 添加到基本显示属性
			pageAttributePanel.add(label, new GridBagConstraints(1, i + 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			pageAttributePanel.add(jcom, new GridBagConstraints(2, i + 1, 1, 1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}

		pageAttributePanel.add(new JPanel(), new GridBagConstraints(1, pageAttr.length + 2, 2, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// 页面属性
		String[] paneAttr = new String[] { "INDEX", "WIDTH", "HEIGHT", "VIEW_TYPE_DESC", "AUTO_STRETCH_DESC",
				"BACK_TEXT" };

		filterlayout = new GridBagLayout();
		rows = new int[paneAttr.length + 1];
		rows[0] = 5;
		filterlayout.columnWidths = (new int[] { 5, 5, 5, 5 });
		filterlayout.rowHeights = rows;
		for (int i = 0; i < paneAttr.length; i++) {
			rows[i + 1] = 5;
		}
		filterlayout.rowHeights = rows;
		panelAttributePanel.setLayout(filterlayout);

		for (int i = 0; i < paneAttr.length; i++) {
			JLabel label = new JLabel();
			if (paneAttr[i].indexOf(".") != -1) {
				label.setText(DataModel.getDefault().getLabel(paneAttr[i]));
			} else {
				label.setText(DataModel.getDefault().getLabel("SYS_PRINT_TEMPLATE_PLATE." + paneAttr[i]));
			}
			JComponent jcom = null;
			if (paneAttr[i].equals("AUTO_STRETCH_DESC") || paneAttr[i].equals("VIEW_TYPE_DESC")) {
				JdbComboBox text = new JdbComboBox();
				text.setColumnName(paneAttr[i]);
				jcom = text;
			} else {
				JdbTextField text = new JdbTextField();
				text.setColumnName(paneAttr[i]);
				jcom = text;
			}
			ColumnAware aware = (ColumnAware) jcom;
			aware.setDataSet(detailDataSet);
			if (paneAttr[i].indexOf(".") != -1) {
				aware.setColumnName(paneAttr[i].substring(paneAttr[i].indexOf(".") + 1, paneAttr[i].length()));
			} else {
				aware.setColumnName(paneAttr[i]);
			}

			// 添加到基本显示属性
			panelAttributePanel.add(label, new GridBagConstraints(1, i + 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			panelAttributePanel.add(jcom, new GridBagConstraints(2, i + 1, 1, 1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}

		panelAttributePanel.add(new JPanel(), new GridBagConstraints(1, paneAttr.length + 2, 2, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		String[] showColumn = { "X", "Y", "WIDTH", "HEIGHT", "FORECOLOR", "BACKCOLOR", "TEXT", "EXPRESSION",
				"FONT_NAME", "FONT_SIZE", "BOLD_DESC", "ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC",
				"HORIZONTAL_ALIGNMENT_DESC", "VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "IMAGE_SCALE_DESC",
				"LINE_DIRECTION_DESC", "LINE_HEIGHT", "BAR_TYPE", "TEXT_POSITION", "EXEC_CODE" };
		// 循环显示对应的列
		// 元素属性
		filterlayout = new GridBagLayout();
		rows = new int[showColumn.length];
		GridBagLayout attrlayout = new GridBagLayout();
		rows = new int[showColumn.length + 1];
		rows[0] = 5;
		attrlayout.columnWidths = (new int[] { 5, 5, 5, 5 });
		attrlayout.rowHeights = rows;
		for (int i = 0; i < showColumn.length; i++) {
			rows[i + 1] = 5;
		}
		filterlayout.rowHeights = rows;
		elementAttributePanel.setLayout(attrlayout);

		for (int i = 0; i < showColumn.length; i++) {
			JLabel label = new JLabel();
			if (showColumn[i].indexOf(".") != -1) {
				label.setText(DataModel.getDefault().getLabel(showColumn[i]));
			} else {
				label.setText(DataModel.getDefault().getLabel("SYS_PRINT_TEMPLATE_ELEMENT." + showColumn[i]));
			}
			JComponent jcom = null;
			if (showColumn[i].equals("ITALIC_DESC") || showColumn[i].equals("BOLD_DESC")
					|| showColumn[i].equals("UNDERLINE_DESC") || showColumn[i].equals("STRIKETHROUGH_DESC")
					|| showColumn[i].equals("HORIZONTAL_ALIGNMENT_DESC")
					|| showColumn[i].equals("VERTICAL_ALIGNMENT_DESC") || showColumn[i].equals("ROTATION_DESC")
					|| showColumn[i].equals("IMAGE_SCALE_DESC") || showColumn[i].equals("LINE_DIRECTION_DESC")
					|| showColumn[i].equals("BAR_TYPE_DESC") || showColumn[i].equals("TEXT_POSITION_DESC")) {
				JdbComboBox text = new JdbComboBox();
				text.setColumnName(showColumn[i]);
				jcom = text;
			} else if (showColumn[i].equals("FONT_NAME")) {
				JdbComboBox text = new JdbComboBox();
				text.setColumnName(showColumn[i]);
				text.setEditable(true);
				jcom = text;
			} else {
				JdbTextField text = new JdbTextField();
				text.setColumns(15);
				text.setColumnName(showColumn[i]);
				jcom = text;
			}
			ColumnAware aware = (ColumnAware) jcom;
			aware.setDataSet(elementDataSet);
			if (showColumn[i].indexOf(".") != -1) {
				aware.setColumnName(showColumn[i].substring(showColumn[i].indexOf(".") + 1, showColumn[i].length()));
			} else {
				aware.setColumnName(showColumn[i]);
			}
			// 将元素放到map缓存，后面根据不同的元素，显示不同的按钮
			componentMap.put(showColumn[i] + "_LABEL", label);
			componentMap.put(showColumn[i] + "_TEXT", jcom);
			// //添加到基本显示属性
			elementAttributePanel.add(label, new GridBagConstraints(1, i + 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			elementAttributePanel.add(jcom, new GridBagConstraints(2, i + 1, 1, 1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
		elementAttributePanel.add(new JPanel(), new GridBagConstraints(1, showColumn.length + 2, 2, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

	@Override
	protected void linkDetailDataSets() {
		super.linkDetailDataSets();
		detailDataSets.add(elementDataSet);
	}

	private static final DataFlavor FILTER_CREAT_FLAVOR = new DataFlavor(FieldRecord.class, ":record");

	private class BillItemTransferable implements Transferable, Serializable {

		/**
		 *
		 */
		private Object data;

		private static final long serialVersionUID = 1L;

		public BillItemTransferable(Object data) {
			this.data = data;
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
				return data;

			throw new UnsupportedFlavorException(flavor);
		}
	}

	/**
	 * @author yyq 拖拽事件源头
	 *
	 */
	SourceTransferHandler sourceHandler = new SourceTransferHandler();

	private class SourceTransferHandler extends TransferHandler {

		/**
		 * @Fields serialVersionUID : 版本号
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected Transferable createTransferable(JComponent c) {
			// sourceHandler.
			Transferable tran = null;
			if (c.equals(componentList)) {
				PrintElementType record = componentList.getSelectedValue();
				if (record != null) {
					tran = new BillItemTransferable(record);
				}
			} else if (c.equals(parameterDataSetTabel)) {
				PrintElementSource soucre = new PrintElementSource();
				soucre.setDataType(parameterDataSet.getString("DATA_TYPE"));
				soucre.setFieldName(parameterDataSet.getString("KEY"));
				soucre.setTableName("P");
				soucre.setText(parameterDataSet.getString("DESC"));
				soucre.setSourceType(PrintElementSource.PARAMETER);
				tran = new BillItemTransferable(soucre);
			} else if (c.equals(fieldList)) {
				PrintElementSource soucre = fieldList.getSelectedValue();
				tran = new BillItemTransferable(soucre);

			}
			return tran;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}

	}

	// SoucreDragGestureListener soucreDragGestureListener=new
	// SoucreDragGestureListener();

	private class SoucreDragGestureListener implements DragGestureListener {

		@Override
		public void dragGestureRecognized(DragGestureEvent event) {
			String txt = ((JLabel) event.getComponent()).getText();
			Transferable transferable = new StringSelection(txt);
			// 继续拖放操作,拖放过程中使用手状光标
			event.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), transferable);
		}

	}

	/**
	 * @author yyq 拖拽接收事件处理
	 */
	TargatTransferHandler targatTransferHandler = new TargatTransferHandler();

	private class TargatTransferHandler extends TransferHandler {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public TargatTransferHandler() {
			super();
		}

		@Override
		public boolean importData(JComponent c, Transferable t) {
			if (canImport(c, t.getTransferDataFlavors())) {
				try {
					PrintItem item = null;
					PrintDesignPanel panel = null;
					List<PrintItem> tempSelectList = null;
					// 如果接收的是表格
					if (c instanceof JTable) {
						// TODO 如果是表格
						// panel=XXX;
					} else if (c instanceof PrintDesignPanel) {
						panel = (PrintDesignPanel) c;
					}
					if (panel == null) {
						return true;
					}

					Point point = SwingUtils.getMousePoint(c);
					Object data = t.getTransferData(FILTER_CREAT_FLAVOR);

					// 清除之前选中的样式
					for (PrintItem com : selectList) {
						if (com != null)
							com.setBorder(defaultBorder);
					}
					selectList.clear();
					if (data instanceof PrintElementType) {
						tempSelectList = panel.addItem((PrintElementType) data, point);
						selectList.addAll(tempSelectList);
					} else if (data instanceof PrintElementSource) {
						tempSelectList = panel.addItem((PrintElementSource) data, point);
						selectList.addAll(tempSelectList);
					}
					if (tempSelectList != null && tempSelectList.size() > 0) {
						item = tempSelectList.get(0);
						/* item.setBorder(clicedBorder); */
						for (PrintItem com : selectList) {
							/* com.setBorder(defaultBorder); */
							com.setBorder(clicedBorder);
							com.addMouseListener(itemSelectAdapter);
						}
					}
					c.updateUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		@Override
		public boolean canImport(JComponent comp, DataFlavor[] flavors) {
			for (int i = 0; i < flavors.length; i++) {
				if (SourceItemTransferable.FILTER_CREAT_FLAVOR.equals(flavors[i])) {
					return true;
				}
			}
			return false;
		}
	}

	private class DirectionMoveAction extends AbstractAction {

		/**
		 * @Fields serialVersionUID : 版本号
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @Fields direction : 方向
		 */
		private int direction;

		private DirectionMoveAction(int direction) {
			this.direction = direction;
		}

		public void actionPerformed(ActionEvent e) {
			for (PrintItem item : selectList) {
				Point point = item.getLocation();
				if (this.direction == Direction.UP) {
					point.y = point.y - (int) (1 * lastZoom + 0.5);
				} else if (this.direction == Direction.DOWN) {
					point.y = point.y + (int) (1 * lastZoom + 0.5);
				} else if (this.direction == Direction.LEFT) {
					point.x = point.x - (int) (1 * lastZoom + 0.5);
				} else if (this.direction == Direction.RIGHT) {
					point.x = point.x + (int) (1 * lastZoom + 0.5);
				}
				item.setLocation(point);
			}
		}

	}

	private class AlignmentButton extends JButton {

		/**
		 * @Fields serialVersionUID : 版本号
		 */
		private static final long serialVersionUID = 1L;

		private Integer vertical;

		private Integer horizontal;

		public AlignmentButton(String text, Integer horizontal, Integer vertical) {
			this(horizontal, vertical);
			this.setText(text);
		}

		public AlignmentButton(Integer horizontal, Integer vertical) {
			this.vertical = vertical;
			this.horizontal = horizontal;
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for (PrintItem item : selectList) {
						if (AlignmentButton.this.vertical != null) {
							item.setVerticalAlignment(AlignmentButton.this.vertical);
						}
						if (AlignmentButton.this.horizontal != null) {
							item.setHorizontalAlignment(AlignmentButton.this.horizontal);
						}
					}
				}
			});
		}

	}

	public class PageType {

		private String text;

		private int width;

		private int height;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
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

		public PageType(String text, int width, int height) {
			super();
			this.text = text;
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString() {
			return this.text;
		}

	}

	@Override
	protected Object prepareData() throws Exception {
		DataSetHelper.loadFromRecordSet(boolStrDataSet, SysCodeHelper.getRecordSet("BOOLEAN"));
		DataSetHelper.loadFromRecordSet(horizontalAlignmentDataSet, SysCodeHelper.getRecordSet("HORIZONTAL_ALIGNMENT"));
		DataSetHelper.loadFromRecordSet(verticalAlignmentDataSet, SysCodeHelper.getRecordSet("VERTICAL_ALIGNMENT"));
		DataSetHelper.loadFromRecordSet(textPositionDataSet, SysCodeHelper.getRecordSet("TEXT_POSITION"));
		DataSetHelper.loadFromRecordSet(rotationDataSet, SysCodeHelper.getRecordSet("ROTATION"));
		DataSetHelper.loadFromRecordSet(printViewDataSet, SysCodeHelper.getRecordSet("PRINT_VIEW"));
		RecordFormat format = new RecordFormat("@");
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.FONT_NAME"));
		RecordSet set = new RecordSet(format);
		// 读取系统字体
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		for (int i = 0; i < fontName.length; i++) {
			set.append().getField("FONT_NAME").setString(fontName[i]);
		}
		DataSetHelper.loadFromRecordSet(fontNameDataSet, set);

		// 处理数据，因为prepared方法经常比较慢。。。。
		if (dataSetPanelMap != null) {
			for (String key : dataSetPanelMap.keySet()) {
				PrintStorageDataSet printtable = dataSetPanelMap.get(key);
				tableListModel.addElement(new CodeValue(printtable.getTableName(), printtable.getTableDesc() + "<"
						+ printtable.getTableName() + ">"));
				// 循环表数据，得到字段
				DefaultListModel<PrintElementSource> fieldListModel = new DefaultListModel<PrintElementSource>();
				for (int i = 0; i < printtable.getDataSet().getColumnCount(); i++) {
					Column column = printtable.getDataSet().getColumn(i);
					PrintElementSource source = new PrintElementSource();
					source.setDataType(DataTypeTool.dataTypeToFieldTypeTo(column.getDataType()));
					source.setTableName(key);
					source.setFieldName(column.getColumnName());
					source.setSourceType(PrintElementSource.PARAMETER);
					source.setText(column.getCaption());
					fieldListModel.add(i, source);
				}
				fieldListMap.put(key, fieldListModel);
			}
			tableList.setModel(tableListModel);
			if (tableListModel.size() > 0) {
				// 默认选择第一个
				tableList.setSelectedIndex(0);
				tableList.getSelectedValue();
				fieldList.setModel(fieldListMap.get(tableList.getSelectedValue().code));
			}

		}
		return null;
	}

	@Override
	protected void prepared(Object arg0) throws Exception {
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "OWNER_CODE");
		parameterDataSet.setString("DESC", "所属组织代码");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "OWNER_NUM");
		parameterDataSet.setString("DESC", "所属组织编号");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "OWNER_NAME");
		parameterDataSet.setString("DESC", "所属组织名称");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_CODE");
		parameterDataSet.setString("DESC", "用户代码");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_NUM");
		parameterDataSet.setString("DESC", "用户编号");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_NAME");
		parameterDataSet.setString("DESC", "用户名称");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DATE");
		parameterDataSet.setString("DESC", "日期(yyyyMMdd)");
		parameterDataSet.setString("DATA_TYPE", "DT");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "TIME");
		parameterDataSet.setString("DESC", "时间(HHmmss)");
		parameterDataSet.setString("DATA_TYPE", "TM");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DATETIME");
		parameterDataSet.setString("DESC", "日期时间(yyyyMMddHHmmss)");
		parameterDataSet.setString("DATA_TYPE", "TS");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "YEAR");
		parameterDataSet.setString("DESC", "年");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "MONTH");
		parameterDataSet.setString("DESC", "月");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DAY");
		parameterDataSet.setString("DESC", "日");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "HOUR");
		parameterDataSet.setString("DESC", "时");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "MINUTE");
		parameterDataSet.setString("DESC", "分");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "SECOND");
		parameterDataSet.setString("DESC", "秒");
		parameterDataSet.setString("DATA_TYPE", "VC");
		// 如果是创建，则默认一个初始化的面板显示
		if (isCreate) {
			// 如果是创建
			try {
				createDefaultTemplate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 如果是复制，
		else if (isCreate) {

		}// 如果是显示或修改
		else {
		}
		this.pack();
	}

	public static void createPrint(String funcId, StorageDataSet dataSet) {
		new PrintDesignFrame(funcId, dataSet, true).showFrame();

	}

	public static void createPrint(String funcId, HashMap<String, PrintStorageDataSet> printSet) {
		new PrintDesignFrame(funcId, printSet, true).showFrame();
	}

	public static void createPrint(String funcId, String frameType,
			HashMap<String, PrintStorageDataSet> dataSetPanelMap, String mainTable) {
		new PrintDesignFrame(funcId, frameType, dataSetPanelMap, mainTable, true).showFrame();
	}

	public static void createPrint(String funcId, StorageDataSet masterDataSet, StorageDataSet detailDataSet) {
		try {
			new PrintDesignFrame(funcId, masterDataSet, detailDataSet, true).showFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createPrint(String funcId, List<Column> listColumn) {
		new PrintDesignFrame(funcId, listColumn).showFrame();
	}

	/**
	 * @Description: TODO
	 * @param type
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月9日
	 */
	public void showElementAttr(String type) {
		// 先清空所有
		elementAttributePanel.removeAll();
		/*
		 * { "X", "Y", "WIDTH", "HEIGHT", "FORECOLOR", "BACKCOLOR", "TEXT",
		 * "EXPRESSION", "FONT_NAME", "FONT_SIZE", "BOLD_DESC", "ITALIC_DESC",
		 * "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
		 * "VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "IMAGE_SCALE_DESC",
		 * "LINE_DIRECTION", "LINE_HEIGHT", "BAR_TYPE", "TEXT_POSITION",
		 * "EXEC_CODE" };
		 */
		String[] showColumn;
		if (PrintElementType.CHART.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION" };
		} else if (PrintElementType.IMAGE.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "IMAGE_SCALE_DESC", "EXEC_CODE" };
		} else if (PrintElementType.TABLE.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION" };
		} else if (PrintElementType.LINE.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "LINE_DIRECTION_DESC", "LINE_HEIGHT" };
		} else if (PrintElementType.LABEL.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "TEXT", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC" };
		} else if (PrintElementType.TEXT.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "FONT_NAME", "FONT_SIZE",
					"BOLD_DESC", "ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC" };
		} else if (PrintElementType.BAR_CODE.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "BAR_TYPE" };
		} else if (PrintElementType.TABLE_HEAD.equals(type)) {
			showColumn = new String[] { "WIDTH", "HEIGHT", "TEXT", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC" };
		} else if (PrintElementType.TABLE_CELL.equals(type)) {
			showColumn = new String[] { "WIDTH", "HEIGHT", "EXPRESSION", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC" };
		} else {
			showColumn = new String[] {};
		}
		// 循环显示对应的列
		// 元素属性
		GridBagLayout filterlayout = new GridBagLayout();
		int[] rows = new int[showColumn.length];
		GridBagLayout attrlayout = new GridBagLayout();
		rows = new int[showColumn.length + 1];
		rows[0] = 5;
		attrlayout.columnWidths = (new int[] { 5, 5, 5, 5 });
		attrlayout.rowHeights = rows;
		for (int i = 0; i < showColumn.length; i++) {
			rows[i + 1] = 5;
		}
		filterlayout.rowHeights = rows;
		elementAttributePanel.setLayout(attrlayout);
		// 显示对应的值
		for (int i = 0; i < showColumn.length; i++) {
			try {
				JComponent label = componentMap.get(showColumn[i] + "_LABEL");
				JComponent jcom = componentMap.get(showColumn[i] + "_TEXT");
				elementAttributePanel.add(label, new GridBagConstraints(1, i + 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				elementAttributePanel.add(jcom, new GridBagConstraints(2, i + 1, 1, 1, 1, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			} catch (Exception e) {
				System.out.println(i);
				e.printStackTrace();
			}
		}
		elementAttributePanel.add(new JPanel(), new GridBagConstraints(1, showColumn.length + 2, 2, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

	ItemSelectAdapter itemSelectAdapter = new ItemSelectAdapter();

	public class ItemSelectAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (selectComp != null) {
				selectComp.setVisibleEdit(false);
			}
			// 是否按住ctrl键。如果按住就是多选的意思
			if (!e.isControlDown()) {
				for (PrintItem com : selectList) {
					com.setBorder(defaultBorder);
					// com.setVisibleEdit(false);
				}
				selectList.clear();
			}
			if (e.getSource() instanceof PrintElementItem) {
				// 选项卡切换到打印项
				PrintElementItem item = (PrintElementItem) e.getSource();

				// 选中对象改为当前点击的控件
				selectComp = item;

			}// 如果是设计面板
			else if (e.getSource() instanceof PrintDesignPanel) {
				PrintDesignPanel item = (PrintDesignPanel) e.getSource();
				detailDataSet.first();
				for (int i = 0; i < detailDataSet.rowCount(); i++) {
					if (detailDataSet.getBigDecimal("UNIQUE_ID").intValue() == item.getUniqueId()) {
						break;
					}
					detailDataSet.next();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					panelRightMenu.show(item, e.getPoint().x, e.getPoint().y);
				}
				// 定位到选择的行
				cardLayout.first(attributePanel);
				cardLayout.next(attributePanel);
			} else if (e.getSource() instanceof TableCellRenderer) {
				TableCellRenderer tableCellRenderer = (TableCellRenderer) e.getSource();
				// 如果是表头的某一列
			} else if (e.getSource() instanceof JTableHeader) {
				// 如果是表头被选中
				JTableHeader header = (JTableHeader) e.getSource();
				int pick = header.columnAtPoint(e.getPoint());
				if (pick < 0) {
					return;
				}
				TableColumnModel tcm = header.getColumnModel();
				// TableColumn column =
				// tcm.getColumn(header.getTable().convertColumnIndexToModel(pick));
				TableColumn column = tcm.getColumn(pick);
				column.getHeaderRenderer();
				PrintTableCellHeaderRenderer renderer = (PrintTableCellHeaderRenderer) column.getHeaderRenderer();
				selectComp = renderer;
			} else if (e.getSource() instanceof JTable) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (table.getCellRenderer(row, col) instanceof PrintTableCellHeaderRenderer) {
					PrintTableCellHeaderRenderer renderer = (PrintTableCellHeaderRenderer) table.getCellRenderer(row,
							col);
					selectComp = renderer;
				}
			} else {
				// 如果是页面
				cardLayout.first(attributePanel);
			}

			if (e.getSource() instanceof PrintItem || e.getSource() instanceof JTableHeader
					|| e.getSource() instanceof JTable) {
				selectList.remove(selectComp);
				// 确保最后选中的放在第一个
				selectList.add(0, selectComp);
				for (PrintItem com : selectList) {
					com.setBorder(clicedBorder);
				}
				// 如果是右键,显示右键菜单
				if (e.getButton() == MouseEvent.BUTTON3) {
					// TODO这里貌似可能问题,后面验证完善
					if (selectComp instanceof PrintTableCellHeaderRenderer) {
						// selectComp.getParentPanel();
						Point point = SwingUtils.getMousePoint(selectComp.getParentPanel());
						rightMenu.show(selectComp.getParentPanel(), point.x, point.y);
					} else {
						JLabel label = (JLabel) selectComp;
						rightMenu.show(label, e.getPoint().x, e.getPoint().y);
					}
				}
				// 定位到选择的行
				elementDataSet.first();
				for (int i = 0; i < elementDataSet.rowCount(); i++) {
					if (elementDataSet.getBigDecimal("UNIQUE_ID").intValue() == selectComp.getUniqueId()) {
						break;
					}
					elementDataSet.next();
				}
				showElementAttr(selectComp.getType());
				cardLayout.last(attributePanel);
				// 如果双击，则弹窗一个框框能够编辑
				if (e.getClickCount() == 2) {
					// 如果是双击就显示对应的文本编辑
					selectComp.setVisibleEdit(true);
				}
			}
		}
	}

	// 菜单项事件
	ItemActionListener itemAction = new ItemActionListener();

	private class ItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 复制
			if (e.getSource() == copyItem) {
				// 将当前选中的对象放到缓存里面
				copyCacheItem = selectComp;
			}// 粘贴
			else if (e.getSource() == pasteItem) {
				// 复制只有在面板上才会触发
				PrintDesignPanel panel = (PrintDesignPanel) e.getSource();
				panel.copyItems(selectList);
			}// 删除
			else if (e.getSource() == deleteItem) {
				for (int i = 0; i < selectList.size(); i++) {
					selectList.get(i).delete();
				}
			}// 复制格式
			else if (e.getSource() == copyForamtItem) {
				copyFormatCacheItem = selectComp;
			}// 粘贴格式
			else if (e.getSource() == pasteForamtItem) {
				// 将当前缓存内存的对象格式复制到选中的集合
				for (int i = 0; i < selectList.size(); i++) {
					PrintItem temp = selectList.get(i);
					temp.setFontName(copyFormatCacheItem.getFontName());
					temp.setFontSize(copyFormatCacheItem.getFontSize());
					temp.setIsBold(copyFormatCacheItem.getIsBold());
					temp.setIsitalic(copyFormatCacheItem.getIsitalic());
					temp.setIsstrikethrough(copyFormatCacheItem.getIsstrikethrough());
					temp.setIsUnderline(copyFormatCacheItem.getIsUnderline());
					temp.setElementHorizontalAlignment(copyFormatCacheItem.getElementHorizontalAlignment());
					temp.setElementVerticalAlignment(copyFormatCacheItem.getElementVerticalAlignment());
				}
			} else if (e.getSource() == showAttribute) {
				showDialog.setVisible(true);
			}
		}
	}

	/**
	 * @Description: 创建一个默认的模板
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017年9月29日
	 */
	private void createDefaultTemplate() {
		pageHeadPane = new PrintDesignPanel(printPage, "这个是页头", PrintDesignPanel.ZDY_VIEW, detailDataSet, true);
		// 添加拖动接收处理事件
		pageHeadPane.setTransferHandler(targatTransferHandler);
		pageHeadPane.setBounds(1, 1, 595, 200);
		pageHeadPane.setBackground(SystemColor.WHITE);
		designPanel.add(pageHeadPane);
		pageHeadPane.addMouseListener(itemSelectAdapter);

		tableHeadPane = new PrintDesignPanel(printPage, "这个是表头", PrintDesignPanel.ZDY_VIEW, detailDataSet, true);
		// 添加拖动接收处理事件
		tableHeadPane.setTransferHandler(targatTransferHandler);
		tableHeadPane.setBounds(1, 201, 595, 30);
		tableHeadPane.setBackground(SystemColor.WHITE);
		designPanel.add(tableHeadPane);
		tableHeadPane.addMouseListener(itemSelectAdapter);

		tablePanel = new PrintDesignPanel(printPage, "这个是表体", PrintDesignPanel.TABLE_VIEW, detailDataSet, true);
		// 添加拖动接收处理事件
		tablePanel.setTransferHandler(targatTransferHandler);
		tablePanel.setBounds(1, 232, 595, 300);
		tablePanel.setBackground(SystemColor.WHITE);
		tablePanel.addMouseListener(itemSelectAdapter);

		designPanel.add(tablePanel);

		tableTailPanel = new PrintDesignPanel(printPage, "这个是表尾", PrintDesignPanel.ZDY_VIEW, detailDataSet, true);
		// 添加拖动接收处理事件
		tableTailPanel.setTransferHandler(targatTransferHandler);
		tableTailPanel.setBounds(1, 532, 595, 30);
		tableTailPanel.setBackground(SystemColor.WHITE);
		tableTailPanel.addMouseListener(itemSelectAdapter);

		designPanel.add(tableTailPanel);

		pageTailPanel = new PrintDesignPanel(printPage, "这个是页尾", PrintDesignPanel.ZDY_VIEW, detailDataSet, true);
		// 添加拖动接收处理事件
		pageTailPanel.setTransferHandler(targatTransferHandler);
		pageTailPanel.setBounds(1, 562, 595, 240);
		pageTailPanel.setBackground(SystemColor.WHITE);
		pageTailPanel.addMouseListener(itemSelectAdapter);
		designPanel.add(pageTailPanel);

	}

	DeleteItemAction deleteItemAction = new DeleteItemAction();

	/**
	 * @Description: 批量删除选中项
	 * @author yyq
	 */
	private class DeleteItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectList.size() > 0) {
				// 选中的元素都被删掉。。
			}
		}

	}

	/**
	 * @Description: 复制选中的项
	 * @author yyq
	 */
	CopyItemAction copyItemAction = new CopyItemAction();

	private class CopyItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 将当前选中对象放在复制缓存对象
			copyCacheItem = selectComp;
		}

	}

	/**
	 * @Description: 复制格式
	 * @author yyq
	 */
	CopyFormatItemAction copyFormatItemAction = new CopyFormatItemAction();

	private class CopyFormatItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			copyFormatCacheItem = selectComp;
		}

	}

	/**
	 * @author yyq
	 * @Description: 粘贴的实现类
	 */
	PasteItemAction pasteItemAction = new PasteItemAction();

	private class PasteItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

	/**
	 * @author yyq
	 * @Description: 粘贴格式实现类
	 */
	PasteFormatItemAction pasteFormatAction = new PasteFormatItemAction();

	private class PasteFormatItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
		}

	}

	public PrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(PrintPage printPage) {
		this.printPage = printPage;
	}

	public JComboBox<CodeValue> getViewType() {
		return viewType;
	}

	public JComboBox<PageType> getPageType() {
		return pageType;
	}

	public JComboBox<Integer> getZoomBox() {
		return zoomBox;
	}

	public JComboBox<String> getFontBox() {
		return fontBox;
	}

	public String getFontName() {
		return fontBox.getSelectedItem().toString();
	}

	public int getFontSize() {
		return Integer.parseInt(fontSize.getValue().toString());
	}

	public ArrayList<PrintItem> getSelectList() {
		return selectList;
	}

	public PrintItem getSelectComp() {
		return selectComp;
	}

	/**
	 * @Description: 垂直对齐方式
	 * @return
	 * @return int
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public int getVerticalAlignment() {
		int alignment = JLabel.CENTER;
		if (topAlignButton.isSelected()) {
			alignment = JLabel.TOP;
		} else if (verticalCenteringButton.isSelected()) {
			alignment = JLabel.CENTER;
		} else if (downAlignButton.isSelected()) {
			alignment = JLabel.BOTTOM;
		}
		return alignment;
	}

	/**
	 * @Description: 水平对齐方式
	 * @return
	 * @return int
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public int getHorizontalAlignment() {
		int horizontalAlignment = JLabel.LEFT;
		if (leftAlignButton.isSelected()) {
			horizontalAlignment = JLabel.LEFT;
		} else if (horizontallyButton.isSelected()) {
			horizontalAlignment = JLabel.CENTER;
		} else if (rightAlignButton.isSelected()) {
			horizontalAlignment = JLabel.RIGHT;
		}
		return horizontalAlignment;
	}

	/**
	 * @Description: 是否加粗
	 * @return
	 * @return boolean
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public boolean isBold() {
		return false;
	}

	/**
	 * @Description: 是否斜体
	 * @return
	 * @return boolean
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public boolean isItalic() {
		return false;
	}

	/**
	 * @Description: 是否下划线划线
	 * @return
	 * @return boolean
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public boolean isUnderline() {
		return false;
	}

	/**
	 * @Description: 是否中划线
	 * @return
	 * @return boolean
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public boolean isStrikethrough() {
		return false;
	}

	// 增加
	public void insertItem() {

	}

}
