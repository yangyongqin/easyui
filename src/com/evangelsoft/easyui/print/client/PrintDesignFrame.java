package com.evangelsoft.easyui.print.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
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
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
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

import com.borland.dbswing.JdbButton;
import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbTable;
import com.borland.dbswing.JdbTextField;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.ColumnAware;
import com.borland.dx.dataset.ColumnChangeAdapter;
import com.borland.dx.dataset.ColumnCustomEditListener;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.ItemListDescriptor;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.print.type.LineType;
import com.evangelsoft.easyui.print.type.PrintDesignView;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.print.type.PrintItemTool;
import com.evangelsoft.easyui.print.type.PrintPanelTool;
import com.evangelsoft.easyui.template.client.UMasterDetailFrame;
import com.evangelsoft.easyui.template.type.FrameType;
import com.evangelsoft.easyui.tool.ColumnsHelp;
import com.evangelsoft.easyui.tool.ComponentResize;
import com.evangelsoft.easyui.tool.DataTypeTool;
import com.evangelsoft.easyui.tool.SwingUtils;
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
 * @Description: ��ӡ�����
 * @author yyq
 * @date 2017-3-3
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintDesignFrame extends UMasterDetailFrame {

	// �������غ���ʵ����
	static {

	}

	private JSplitPane allPane;// ���ָ����

	private JSplitPane mainPane;// ���ָ����

	private JSplitPane leftPane;// ���ָ����

	private JTabbedPane dataSource;// ����Դ

	private JPanel tableDataPanel;// ������

	private JPanel parameterPanel;// �������

	private StorageDataSet parameterDataSet;

	private TableScrollPane parameterDataSetPanel;

	private JdbTable parameterDataSetTabel;

	private JPanel elementPanel;// Ԫ�����

	private JPanel middlePanel;

	// ������
	private JPanel toolBarPanel;

	private JToolBar toobar;

	private JLabel zoomLabel;

	// ҳ������ ,���غͻ��ǳߴ�
	private JComboBox<CodeValue> viewType;

	private JComboBox<PageType> pageType;

	private JComboBox<Integer> zoomBox;

	private JButton expandButton;// �Ŵ�

	private JButton narrowButton;// ��С

	public JComboBox<String> fontBox;// ����

	public JSpinner fontSize;// �����С

	private JButton leftAlignButton;// �����

	private JButton rightAlignButton;// �Ҷ���

	private JButton verticalCenteringButton;// ��ֱ����

	private JButton topAlignButton;// �϶��룬���˶���

	private JButton downAlignButton;// �¶��룬�׶˶���

	private JButton horizontallyButton;// ˮƽ����

	private PrintDesignManagePanel designPanel;// ������

	private JScrollPane designPane;// ������

	private JSplitPane rightPane;// �ұ�����������

	//
	private JList<PrintElementType> componentList;// ������

	DefaultListModel<PrintElementType> componentdModel = new DefaultListModel<PrintElementType>();

	private JPanel attributePanel;// ����

	private JPanel pageAttributePanel;// ҳ������

	private JPanel elementAttributePanel;// Ԫ������

	private JPanel panelAttributePanel;// �������

	private JSplitPane dataPane;// �������

	JScrollPane tableScrollPane;

	private JList<CodeValue> tableList;// ������

	DefaultListModel<CodeValue> tableListModel = new DefaultListModel<CodeValue>();

	JScrollPane fieldScrollPane;

	private JList<PrintElementSource> fieldList;// �ֶ�����

	/**
	 * �ֶ�
	 */
	DefaultListModel<PrintElementSource> fieldListModel = new DefaultListModel<PrintElementSource>();

	HashMap<String, DefaultListModel<PrintElementSource>> fieldListMap = new HashMap<String, DefaultListModel<PrintElementSource>>();

	CardLayout cardLayout = new CardLayout();

	private PrintDesignView pageHeadPane;// ͷ����壬

	private PrintDesignView tableHeadPane;// ͷ����壬

	private PrintDesignView tablePanel;// �м�ѡ�

	private PrintDesignView tableTailPanel;// ��β

	private PrintDesignView pageTailPanel;// �ײ����

	private JButton borderButton;// Ĭ�ϱ߿�

	private JButton customBorderButton;// �߿�

	/**
	 * �汾��
	 */
	private static final long serialVersionUID = 3699743293823324038L;

	StorageDataSet elementDataSet;// Ԫ������

	// ����������
	// List<PrintDesignView> linkedPanel = new ArrayList<PrintDesignView>();

	PrintPage printPage = new PrintPage(this);

	private String frameType;// ������

	// private HashMap<String, PrintStorageDataSet> dataSetPanelMap;
	private PrintDataManage printDataManage;

	private String mainTable;//

	private StorageDataSet dataSet;

	private ArrayList<Column> listColumn;

	private final LineBorder clicedBorder = new LineBorder(Color.RED, 3);

	private final LineBorder defaultBorder = new LineBorder(Color.GRAY, 1);

	// ѡ�е�list�ļ���
	public List<PrintItem<?>> selectList = new ArrayList<PrintItem<?>>();

	/**
	 * @Fields selectPanel : ��ѡ�е����
	 */
	private PrintDesignView selectPanel;

	public PrintDesignView getSelectPanel() {
		return this.selectPanel;
	}

	public void setSelectPanel(PrintDesignView selectPanel) {
		this.selectPanel = selectPanel;
	}

	// ѡ�е����
	public PrintItem<?> selectComp;

	// ��һ�α���
	private double lastZoom = 1;

	private JPopupMenu rightMenu;// �Ҽ��˵�

	private JMenuItem copyItem;// ����

	private JMenuItem pasteItem;// ճ��

	private JMenuItem deleteItem;// ɾ��

	private JMenuItem copyForamtItem;// ����

	private JMenuItem pasteForamtItem;// ����

	private JPopupMenu panelRightMenu;// �����Ҽ��˵�

	private JMenuItem deletePaneItem;// ɾ����ǰ���

	private JMenuItem toTableFormat;// תΪ�����ʾ

	private JMenuItem toFreeFormat;// תΪ�Զ�����ʾ

	private JMenuItem insertPane;// ����һ���µ����

	private JMenuItem addPane;// ���һ���µ����

	private JMenuItem upMoveItem;// �����ƶ�

	private JMenuItem downMoveItem;

	private JPopupMenu pageRightMenu;// ҳ���Ҽ��˵�

	// private JMenuItem fastAddItem;// �������һ�����

	private JMenuItem addPanelByPageItem;// ���һ���µ����

	private JMenuItem pageConfigureItem;// ҳ����������

	private JMenuItem showAttribute;// ��ʾ����

	private JMenuItem showPanelAttribute;// ��ʾ�������

	private boolean isCreate = false;

	private JDialog showDialog = new JDialog();

	private JDialog showPanelDialog = new JDialog();

	private JdbTable elementTable;

	/**
	 * @Fields copyCacheItem : ���ڻ���Ķ���
	 */
	private ArrayList<PrintItem<?>> copyCacheItem = new ArrayList<PrintItem<?>>();

	/**
	 * @Fields copyCacheItem : ���Ƹ�ʽ
	 */
	private PrintItem<?> copyFormatCacheItem;

	private HashMap<String, JComponent> componentMap = new HashMap<String, JComponent>();

	private JTextField backColorField, foreColorField;

	/**
	 * ���췽��һ�����ڶ��ģʽ
	 *
	 * @param dataSetPanelMap
	 * @param mainTable
	 *            ��������
	 * @param frameType
	 *            ��������
	 */
	public PrintDesignFrame(String funcId, String frameType, PrintDataManage printDataManage, boolean isCreate) {
		this.frameType = frameType;
		this.printDataManage = printDataManage;
		this.mainTable = printDataManage.getMainPrintStorageDataSet().getTableId();
		this.isCreate = isCreate;
		init();
	}

	public PrintDesignFrame(String funcId, PrintDataManage printDataManage, String mainTable, boolean isCreate) {
		this.isCreate = isCreate;
		/* this.frameType = frameType; */
		this.printDataManage = printDataManage;
		this.mainTable = printDataManage.getMainPrintStorageDataSet().getTableId();
		this.isCreate = isCreate;
		init();
	}

	public PrintDesignFrame(String funcId, PrintDataManage printDataManage, boolean isCreate) {
		/* this.frameType = frameType; */
		this.printDataManage = printDataManage;
		this.isCreate = isCreate;
		this.mainTable = printDataManage.getMainPrintStorageDataSet().getTableId();
		init();
	}

	public PrintDesignFrame(String funcId) {
		init();
	}

	public PrintDesignFrame() {
		init();
	}

	/**
	 * ���췽��һ�����ڵ���ģʽ�ͱ���������ӱ�ֻ��ӡ����
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

		master.setTableDesc("����");
		master.setDataSet(masterDataSet);
		master.setTableId("master");

		printDataManage.setMainPrintStorageDataSet(master);

		PrintStorageDataSet detail = new PrintStorageDataSet();
		detail.setTableDesc("��ϸ");
		detail.setDataSet(detailDataSet);
		detail.setTableId("detail");
		printDataManage.setListPrintStorageDataSet(detail);
		this.isCreate = isCreate;
		init();
	}

	/**
	 * ���췽��һ�����ڵ���ģʽ�ͱ���������ӱ�ֻ��ӡ����
	 *
	 * @param listColumn
	 *            �м���
	 */
	public PrintDesignFrame(String funcId, List<Column> listColumn) {

		// ����ͱ���ͳһ�õ���ģʽ
		this.frameType = FrameType.FRAME_SINGLE;
		this.dataSet = new StorageDataSet();
		dataSet.setColumns(listColumn.toArray(new Column[0]));
		listColumn.addAll(listColumn);
		if (printDataManage == null) {
			PrintStorageDataSet list = new PrintStorageDataSet();
			list.setTableDesc("�б�");
			list.setDataSet(masterDataSet);
			list.setTableId("detail");
			printDataManage.setListPrintStorageDataSet(list);
		}
		this.isCreate = true;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			this.dispose();
		}
	}

	public static void addPrint() {

	}

	// ���ز���Ҫ�Ŀؼ�
	/*
	 * public PrintDesignFrame(String funcId) { }
	 */
	void init() {
		try {
			this.setTitle("��ӡ���");
			// ����ǵ���ģʽ
			this.headerPanel.setVisible(false);
			this.listFooterPanel.setVisible(false);
			this.footerPanel.setVisible(false);
			elementDataSet = new StorageDataSet();
			// elementDataSet.open();
			initialization();

			initTable();

			initAttr();
			// ��ʼ����ݼ�
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

			// ��ʾ��Ԫ�أ����ڿ���ʱ��ֱ�ۿ�����
			elementTable = new JdbTable();
			showDialog.setPreferredSize(new Dimension(400, 300));
			showDialog.pack();
			TableScrollPane pane = new TableScrollPane(elementTable);
			showDialog.add(pane);
			elementTable.setDataSet(elementDataSet);

			showPanelDialog = new JDialog();
			showPanelDialog.setPreferredSize(new Dimension(400, 300));
			showPanelDialog.pack();
			TableScrollPane panelPane = new TableScrollPane(detailTable);
			showPanelDialog.add(panelPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ʼ������
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
				// this.setIconTextGap(30);//����ͼƬ�����ֵļ��
				super.getListCellRendererComponent(list, "  " + value.toString(), index, isSelected, cellHasFocus);
				return this;
			}
		});

		allPane = new JSplitPane();
		allPane.setResizeWeight(0.2);//
		allPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// �ָ�����Ȱ�����ҳ��ֳ���������
		this.getContentPane().add(allPane);
		// ���������
		dataPane = new JSplitPane();
		dataSource = new JTabbedPane();

		parameterPanel = new JPanel();
		tableDataPanel = new JPanel();
		dataSource.addTab("������", tableDataPanel);
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

		dataSource.addTab("����", parameterPanel);

		parameterDataSet = new StorageDataSet();

		Column column = new Column();
		column.setColumnName("KEY");
		column.setCaption("��");
		column.setScale(0);
		column.setPrecision(0);
		column.setWidth(12);
		// column.setDataType(DataType.TYPE_VARCHAR);
		column.setDataType(com.borland.dx.dataset.Variant.STRING);
		column.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column valueColumn = new Column();
		valueColumn.setColumnName("DESC");
		valueColumn.setCaption("����");
		valueColumn.setScale(0);
		valueColumn.setPrecision(20);
		valueColumn.setWidth(12);
		/* valueColumn.setDataType(DataType.TYPE_VARCHAR); */
		valueColumn.setDataType(com.borland.dx.dataset.Variant.STRING);
		valueColumn.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column dataType = new Column();
		dataType.setColumnName("DATA_TYPE");
		dataType.setCaption("��������");
		dataType.setScale(0);
		dataType.setPrecision(20);
		dataType.setVisible(com.borland.jb.util.TriStateProperty.FALSE);
		dataType.setWidth(12);
		/* valueColumn.setDataType(DataType.TYPE_VARCHAR); */
		dataType.setDataType(com.borland.dx.dataset.Variant.STRING);
		dataType.setCharacterCase(com.borland.dx.dataset.CharacterCase.normal);

		Column dataTypeDesc = new Column();

		dataTypeDesc.setColumnName("DATA_TYPE_DESC");
		dataTypeDesc.setCaption("��������");
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
		parameterDataSetTabel.setDragEnabled(true);// ����������ק
		parameterDataSetTabel.setTransferHandler(sourceHandler);
		parameterDataSetTabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// �����õ�ѡ

		// ֻ�ܵ�ѡ
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
		leftPane.setOrientation(JSplitPane.VERTICAL_SPLIT);// �ָ�����Ȱ�����ҳ��ֳ��������߸�
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
				// this.setIconTextGap(30);//����ͼƬ�����ֵļ��
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

		// ������
		designPanel = new PrintDesignManagePanel(printPage);
		designPanel.addMouseListener(itemSelectAdapter);
		this.addMouseListener(itemSelectAdapter);
		// designPanel.setBackground(SystemColor.WHITE);
		// designPanel.setLayout(null);
		// designPane.setBounds(0, 0, 842, 700);
		// designPanel.setSize(842, 700);
		// designPanel.setPreferredSize(new Dimension(597, 844));
		final JPanel panel = new JPanel();

		// panel.setPreferredSize(new Dimension(597, 844));
		// panel.setLayout(new BorderLayout());
		panel.add(designPanel);
		designPane = new JScrollPane(panel);
		// designPane.setBounds(0, 0, 200, 100);

		// this.designPane.setHorizontalScrollBarPolicy(32);
		// this.designPane
		// .setVerticalScrollBarPolicy(22);

		// ��ΪҪ���÷Ŵ�����裬���������κβ���

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
		viewType.addItem(new CodeValue("1", "����"));
		viewType.addItem(new CodeValue("2", "�ߴ�"));
		viewType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// ����ߴ�
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
		pageType.addItem(new PageType("�Զ���", 0, 0));

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
					// ����Ŵ������С
					Double zoom = Double.parseDouble(e.getItem().toString()) / 100;

					BigDecimal b = new BigDecimal(zoom / lastZoom);
					double f1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
					ComponentResize.reSetSize(panel, f1, zoom);
					lastZoom = zoom;
				}
			}
		});
		// Ĭ��100%
		zoomBox.setSelectedIndex(3);
		expandButton = new JButton();
		expandButton.setText("�Ŵ�");
		expandButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//
				if (zoomBox.getSelectedIndex() < zoomBox.getItemCount() - 1) {
					zoomBox.setSelectedIndex(zoomBox.getSelectedIndex() + 1);
				}
			}

		});
		narrowButton = new JButton("��С");
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
		// ��ȡϵͳ����
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

		topAlignButton = new AlignmentButton("���˶���", null, JLabel.TOP);

		verticalCenteringButton = new AlignmentButton("��ֱ����", null, JLabel.CENTER);

		downAlignButton = new AlignmentButton("�׶˶���", null, JLabel.BOTTOM);

		leftAlignButton = new AlignmentButton("�����", JLabel.LEFT, null);
		horizontallyButton = new AlignmentButton("ˮƽ����", JLabel.CENTER, null);
		rightAlignButton = new AlignmentButton("�Ҷ���", JLabel.RIGHT, null);

		borderButton = new JButton("�߿�");
		// //
		customBorderButton = new JButton("�Զ���߿�");

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

		// ����ȷ������ռ���η������
		componentList = new JList<PrintElementType>();
		rightPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		JScrollPane scroll = new JScrollPane(componentList);
		componentList.setDragEnabled(true);// ����������ק
		componentList.setTransferHandler(sourceHandler);
		componentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// �����õ�ѡ

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

		componentdModel.addElement(new PrintElementType(PrintElementType.CHART, " ͼ��",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType(PrintElementType.IMAGE, " ͼƬ",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType(PrintElementType.TABLE, " ���",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType(PrintElementType.LINE, " ����",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType(PrintElementType.LABEL, " ��̬�ı�",
				"com/evangelsoft/workbench/resources/buttons/find.png"));
		componentdModel.addElement(new PrintElementType(PrintElementType.TEXT, " ֵ�ؼ�",
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
						ImageIcon icon = new ImageIcon(PrintDesignFrame.class.getClassLoader().getResource(
								type.getIcon()));
						this.setIcon(icon);
						// this.setIconTextGap(30);����ͼƬ�����ֵļ��
						this.setText(type.getDesc());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					// ����ѡȡ��ȡ��ѡȡ��ǰ���뱳����ɫ.
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}

				return this;
			}
		});
		componentList.setModel(componentdModel);

		rightMenu = new JPopupMenu();
		copyItem = new JMenuItem("����(Ctrl+c)");
		pasteItem = new JMenuItem("ճ��(Ctrl+v)");
		pasteItem.setEnabled(false);
		deleteItem = new JMenuItem("ɾ��(delete)");
		copyForamtItem = new JMenuItem("���Ƹ�ʽ(shift+c)");
		pasteForamtItem = new JMenuItem("ճ����ʽshift+v");
		pasteForamtItem.setEnabled(false);

		rightMenu.add(copyItem);

		rightMenu.add(deleteItem);
		rightMenu.add(copyForamtItem);
		rightMenu.add(pasteForamtItem);

		copyItem.addActionListener(copyItemAction);
		pasteItem.addActionListener(pasteItemAction);
		deleteItem.addActionListener(itemAction);
		copyForamtItem.addActionListener(itemAction);
		pasteForamtItem.addActionListener(itemAction);

		panelRightMenu = new JPopupMenu();
		deletePaneItem = new JMenuItem("ɾ����ǰ���");
		toTableFormat = new JMenuItem("�л�Ϊ���");
		toFreeFormat = new JMenuItem("�Զ�����ʾ");
		insertPane = new JMenuItem("����һ���µ����");
		addPane = new JMenuItem("���һ���µ����");
		upMoveItem = new JMenuItem("�����ƶ�");
		downMoveItem = new JMenuItem("�����ƶ�");

		showAttribute = new JMenuItem("��ʾ����");
		showPanelAttribute = new JMenuItem("��ʾ�������");

		panelRightMenu.add(pasteItem);
		panelRightMenu.add(deletePaneItem);
		panelRightMenu.add(toTableFormat);
		panelRightMenu.add(toFreeFormat);
		panelRightMenu.add(insertPane);
		panelRightMenu.add(addPane);
		panelRightMenu.add(upMoveItem);
		panelRightMenu.add(downMoveItem);
		panelRightMenu.add(showAttribute);
		panelRightMenu.add(showPanelAttribute);

		deletePaneItem.addActionListener(itemAction);
		toTableFormat.addActionListener(itemAction);
		toFreeFormat.addActionListener(itemAction);
		insertPane.addActionListener(itemAction);
		addPane.addActionListener(itemAction);
		upMoveItem.addActionListener(itemAction);
		downMoveItem.addActionListener(itemAction);

		showAttribute.addActionListener(itemAction);
		showPanelAttribute.addActionListener(itemAction);

		pageRightMenu = new JPopupMenu();
		addPanelByPageItem = new JMenuItem("���һ���µ����");
		pageConfigureItem = new JMenuItem("����ҳ������");
		pageRightMenu.add(addPanelByPageItem);
		pageRightMenu.add(pageConfigureItem);
		addPanelByPageItem.addActionListener(itemAction);
		pageConfigureItem.addActionListener(itemAction);
	}

	public void showFrame(String str) {
		showFrame();
	}

	public void showFrame() {

		final PrintDesignFrame print = this;
		print.setBorder(null);
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ����ȫ��ʱ���õ�
		// frame.setUndecorated(true);
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		frame.setVisible(true);
		frame.setTitle(print.getTitle());

		// 3.����ȫ��ģʽ

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

	private StorageDataSet lineDirectionDataSet;

	/**
	 * ��ʼ�����
	 * @throws TooManyListenersException 
	 * @throws DataSetException 
	 */
	void initTable() throws DataSetException, TooManyListenersException {
		boolStrDataSet = new StorageDataSet();
		horizontalAlignmentDataSet = new StorageDataSet();
		verticalAlignmentDataSet = new StorageDataSet();
		textPositionDataSet = new StorageDataSet();
		rotationDataSet = new StorageDataSet();
		fontNameDataSet = new StorageDataSet();
		printViewDataSet = new StorageDataSet();
		lineDirectionDataSet = new StorageDataSet();

		String[] columnStrs = new String[] { "FUNC_ID", "PAGE_WIDTH", "HEIGHT_AUTO", "HEIGHT_AUTO_DESC", "PAGE_HEIGHT",
				"ORIENTATION", "ORIENTATION_DESC", "LEFT_MARGIN", "RIGHT_MARGIN", "TOP_MARGIN", "BOTTOM_MARGIN",
				"COL_NUM", "COL_WIDTH" };
		masterDataSet.setColumns(ColumnsHelp.getColumns("SYS_PRINT_TEMPLATE_PAGE", columnStrs));
		masterDataSet.open();
		String[] plate = new String[] { "PRINT_ID", "UNIQUE_ID", "PLATE_INDEX", "HEIGHT", "WIDTH", "AUTO_STRETCH",
				"AUTO_STRETCH_DESC", "VIEW_TYPE", "VIEW_TYPE_DESC", "BACK_TEXT", "TABLE_ID", "CIRCULATION",
				"CIRCULATION_DESC", "COL_NUM", "COL_WIDTH", "COL_SPACING", "X", "Y", "PARENT_ID", "HEIGHT", "WIDTH" };
		detailDataSet.setColumns(ColumnsHelp.getColumns("SYS_PRINT_TEMPLATE_PLATE", plate));
		detailDataSet.getColumn("VIEW_TYPE_DESC").setPickList(
				new PickListDescriptor(printViewDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "VIEW_TYPE" }, "DESCRIPTION", true));

		detailDataSet.getColumn("AUTO_STRETCH_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "AUTO_STRETCH" }, "DESCRIPTION", true));
		detailDataSet.getColumn("CIRCULATION_DESC").setPickList(
				new PickListDescriptor(boolStrDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
						new String[] { "CIRCULATION" }, "DESCRIPTION", true));
		detailDataSet.getColumn("TABLE_ID").setItemList(
				new ItemListDescriptor(printDataManage.getPrintSet().keySet().toArray(), false));
		try {
			for (Column column : detailDataSet.getColumns()) {
				column.addColumnChangeListener(panelColumnChangeAdapter);
			}
		} catch (DataSetException e1) {
			e1.printStackTrace();
		} catch (TooManyListenersException e1) {
			e1.printStackTrace();
		}
		//
		String[] detailColumn = new String[] { "PRINT_ID", "PANEL_ID", "UNIQUE_ID", "RELATION_ID", "ELEMENT_INDEX",
				"TYPE", "X", "Y", "WIDTH", "HEIGHT", "FORECOLOR", "BACKCOLOR", "TEXT", "EXPRESSION", "FONT_NAME",
				"FONT_SIZE", "BOLD", "BOLD_DESC", "ITALIC", "ITALIC_DESC", "UNDERLINE", "UNDERLINE_DESC",
				"STRIKETHROUGH", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT", "HORIZONTAL_ALIGNMENT_DESC",
				"VERTICAL_ALIGNMENT", "VERTICAL_ALIGNMENT_DESC", "ROTATION", "ROTATION_DESC", "IMAGE_SCALE",
				"IMAGE_SCALE_DESC", "LINE_DIRECTION", "LINE_DIRECTION_DESC", "LINE_SIZE", "LINE_STYLE", "BAR_TYPE",
				"BAR_TYPE_DESC", "TEXT_POSITION", "TEXT_POSITION_DESC", "EXEC_CODE" };
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
				new PickListDescriptor(lineDirectionDataSet, new String[] { "CODE" }, new String[] { "DESCRIPTION" },
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
		elementDataSet.getColumn("FORECOLOR").setCustomEditable(true);
		elementDataSet.getColumn("BACKCOLOR").setCustomEditable(true);
		elementDataSet.getColumn("FORECOLOR").addColumnCustomEditListener(colorColumnCustomEditListener);
		elementDataSet.getColumn("BACKCOLOR").addColumnCustomEditListener(colorColumnCustomEditListener);
		// elementDataSet.getColumn("FORECOLOR").addColumnChangeListener(colorColumnChangeListener);
		// elementDataSet.getColumn("BACKCOLOR").addColumnChangeListener(colorColumnChangeListener);

		try {
			for (Column column : elementDataSet.getColumns()) {
				column.addColumnChangeListener(elementColumnChangeAdapter);
			}
		} catch (DataSetException e1) {
			e1.printStackTrace();
		} catch (TooManyListenersException e1) {
			e1.printStackTrace();
		}
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
	 * @Description: Ԫ�ظı�ʱ�򴥷�������֪ͨUI��ʾ�ȵ�
	 * @author yangyq02
	 * @date 2017��12��14��
	 */
	ElementColumnChangeAdapter elementColumnChangeAdapter = new ElementColumnChangeAdapter();

	private class ElementColumnChangeAdapter extends ColumnChangeAdapter {

		@Override
		public void changed(DataSet arg0, Column column, Variant value) {
			// ��ȡ��ѡ�е����
			if (CollectionUtils.isNotEmpty(selectList)) {
				PrintItemTool.setValue(selectList, column.getColumnName(), value.getAsObject());
			}
		}

	}

	PanelColumnChangeAdapter panelColumnChangeAdapter = new PanelColumnChangeAdapter();

	private class PanelColumnChangeAdapter extends ColumnChangeAdapter {

		@Override
		public void changed(DataSet arg0, Column column, Variant value) {
			// ��ȡ��ѡ�е����
			if (selectPanel != null) {
				PrintPanelTool.setValue(selectPanel, column.getColumnName(), value.getAsObject());
			}
		}

	}

	/**
	 * @Description: ��ʼ����ݼ�
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017��9��29��
	 */
	private void initQuickKey() {
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(2);
		ActionMap actionMap = ((JComponent) getContentPane()).getActionMap();
		// ���ƿ�ݼ�
		KeyStroke ctrlc = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrlc, "ctrl+c");
		actionMap.put("ctrl+c", this.copyItemAction);
		// ճ����ݼ�
		KeyStroke ctrlv = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrlv, "ctrl+v");
		actionMap.put("ctrl+v", this.pasteItemAction);
		// ɾ����ݼ�
		KeyStroke ctrld = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK, false);
		inputMap.put(ctrld, "ctrl+d");
		actionMap.put("ctrl+d", this.deleteItemAction);

		KeyStroke shiftc = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK, false);
		inputMap.put(shiftc, "shift+c");
		actionMap.put("shift+c", this.copyFormatItemAction);
		// ճ����ݼ�
		KeyStroke shiftv = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK, false);
		inputMap.put(shiftv, "shift+v");
		actionMap.put("shift+v", this.pasteFormatAction);
	}

	void initAttr() {
		// ҳ������
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

			// ��ӵ�������ʾ����
			pageAttributePanel.add(label, new GridBagConstraints(1, i + 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			pageAttributePanel.add(jcom, new GridBagConstraints(2, i + 1, 1, 1, 1, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}

		pageAttributePanel.add(new JPanel(), new GridBagConstraints(1, pageAttr.length + 2, 2, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// ҳ������
		String[] paneAttr = new String[] { "PLATE_INDEX", "WIDTH", "HEIGHT", "TABLE_ID", "VIEW_TYPE_DESC",
				"AUTO_STRETCH_DESC", "BACK_TEXT", "CIRCULATION_DESC", "COL_NUM", "COL_WIDTH", "COL_SPACING" };

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
			if (paneAttr[i].equals("AUTO_STRETCH_DESC") || paneAttr[i].equals("VIEW_TYPE_DESC")
					|| paneAttr[i].equals("CIRCULATION_DESC") || paneAttr[i].equals("TABLE_NAME")) {
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

			// ��ӵ�������ʾ����
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
				"LINE_DIRECTION_DESC", "LINE_SIZE", "LINE_STYLE", "BAR_TYPE", "TEXT_POSITION", "EXEC_CODE" };
		// ѭ����ʾ��Ӧ����
		// Ԫ������
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
			} else if (showColumn[i].equals("LINE_STYLE")) {
				// �����������ʽ������ʾ������ʽ
				// Integer xx[] = new Integer[] { 1, 2, 3 };
				Object elements[][] = { { new MyIcon(1) }, { new MyIcon(2) }, { new MyIcon(3) } };

				JComboBox<Object> text = new JComboBox<Object>(elements);
				ComboBoxRenderer reander = new ComboBoxRenderer();
				reander.setPreferredSize(new Dimension(100, 30));
				text.setRenderer(reander);
				jcom = text;

			} else if (showColumn[i].equals("FORECOLOR") || showColumn[i].equals("BACKCOLOR")) {
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				JdbButton button = new JdbButton();
				button.setDataSet(elementDataSet);
				button.setColumnName(showColumn[i]);
				button.setMargin(new Insets(0, 0, 0, 0));
				JdbTextField text = new JdbTextField();
				text.setColumns(15);
				text.setDataSet(elementDataSet);
				text.setColumnName(showColumn[i]);
				if (showColumn[i].equals("FORECOLOR")) {
					foreColorField = text;
				} else {
					backColorField = text;
				}

				panel.add(text);

				button.setIcon(new ImageIcon(PrintDesignFrame.class.getClassLoader().getResource(
						"com/evangelsoft/workbench/resources/buttons/find.png")));

				panel.add(button, BorderLayout.EAST);
				jcom = panel;
			} else {
				JdbTextField text = new JdbTextField();
				text.setColumns(15);
				text.setColumnName(showColumn[i]);
				jcom = text;
			}
			if (jcom instanceof ColumnAware) {
				ColumnAware aware = (ColumnAware) jcom;
				aware.setDataSet(elementDataSet);
				if (showColumn[i].indexOf(".") != -1) {
					aware.setColumnName(showColumn[i].substring(showColumn[i].indexOf(".") + 1, showColumn[i].length()));
				} else {
					aware.setColumnName(showColumn[i]);
				}
			}
			// ��Ԫ�طŵ�map���棬������ݲ�ͬ��Ԫ�أ���ʾ��ͬ�İ�ť
			componentMap.put(showColumn[i] + "_LABEL", label);
			componentMap.put(showColumn[i] + "_TEXT", jcom);
			// //��ӵ�������ʾ����
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
	 * @author yyq ��ק�¼�Դͷ
	 *
	 */
	SourceTransferHandler sourceHandler = new SourceTransferHandler();

	private class SourceTransferHandler extends TransferHandler {

		/**
		 * @Fields serialVersionUID : �汾��
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

	@SuppressWarnings("unused")
	private class SoucreDragGestureListener implements DragGestureListener {

		@Override
		public void dragGestureRecognized(DragGestureEvent event) {
			String txt = ((JLabel) event.getComponent()).getText();
			Transferable transferable = new StringSelection(txt);
			// �����ϷŲ���,�ϷŹ�����ʹ����״���
			event.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), transferable);
		}

	}

	/**
	 * @author yyq ��ק�����¼�����
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
					// PrintItem<?> item = null;
					PrintDesignView panel = null;
					List<PrintItem<?>> tempSelectList = null;
					// ������յ��Ǳ��
					if (c instanceof JTable) {
						// TODO ����Ǳ��
						// panel=XXX;
					} else if (c instanceof PrintDesignView) {
						panel = (PrintDesignView) c;
					}
					if (panel == null) {
						return true;
					}

					Point point = SwingUtils.getMousePoint(c);
					Object data = t.getTransferData(FILTER_CREAT_FLAVOR);

					// ���֮ǰѡ�е���ʽ
					for (PrintItem<?> com : selectList) {
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
						// item = tempSelectList.get(0);
						/* item.setBorder(clicedBorder); */
						for (PrintItem<?> com : selectList) {
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
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @Fields direction : ����
		 */
		private int direction;

		private DirectionMoveAction(int direction) {
			this.direction = direction;
		}

		public void actionPerformed(ActionEvent e) {
			for (PrintItem<?> item : selectList) {
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
		 * @Fields serialVersionUID : �汾��
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
					for (PrintItem<?> item : selectList) {
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
		DataSetHelper.loadFromRecordSet(lineDirectionDataSet, SysCodeHelper.getRecordSet("LINE_DIRECTION"));

		RecordFormat format = new RecordFormat("@");
		format.appendField(new RecordFieldFormat("SYS_PRINT_TEMPLATE_ELEMENT.FONT_NAME"));
		RecordSet set = new RecordSet(format);
		// ��ȡϵͳ����
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		for (int i = 0; i < fontName.length; i++) {
			set.append().getField("FONT_NAME").setString(fontName[i]);
		}
		DataSetHelper.loadFromRecordSet(fontNameDataSet, set);

		// �������ݣ���Ϊprepared���������Ƚ�����������
		if (printDataManage != null) {
			for (String key : printDataManage.getPrintSet().keySet()) {
				PrintStorageDataSet printtable = printDataManage.getPrintSet().get(key);
				tableListModel.addElement(new CodeValue(printtable.getTableId(), printtable.getTableDesc() + "<"
						+ printtable.getTableId() + ">"));
				// ѭ�������ݣ��õ��ֶ�
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
				// Ĭ��ѡ���һ��
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
		parameterDataSet.setString("DESC", "������֯����");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "OWNER_NUM");
		parameterDataSet.setString("DESC", "������֯���");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "OWNER_NAME");
		parameterDataSet.setString("DESC", "������֯����");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_CODE");
		parameterDataSet.setString("DESC", "�û�����");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_NUM");
		parameterDataSet.setString("DESC", "�û����");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "USER_NAME");
		parameterDataSet.setString("DESC", "�û�����");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DATE");
		parameterDataSet.setString("DESC", "����(yyyyMMdd)");
		parameterDataSet.setString("DATA_TYPE", "DT");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "TIME");
		parameterDataSet.setString("DESC", "ʱ��(HHmmss)");
		parameterDataSet.setString("DATA_TYPE", "TM");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DATETIME");
		parameterDataSet.setString("DESC", "����ʱ��(yyyyMMddHHmmss)");
		parameterDataSet.setString("DATA_TYPE", "TS");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "YEAR");
		parameterDataSet.setString("DESC", "��");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "MONTH");
		parameterDataSet.setString("DESC", "��");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "DAY");
		parameterDataSet.setString("DESC", "��");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "HOUR");
		parameterDataSet.setString("DESC", "ʱ");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "MINUTE");
		parameterDataSet.setString("DESC", "��");
		parameterDataSet.setString("DATA_TYPE", "VC");
		parameterDataSet.insertRow(false);
		parameterDataSet.setString("KEY", "SECOND");
		parameterDataSet.setString("DESC", "��");
		parameterDataSet.setString("DATA_TYPE", "VC");
		// ����Ǵ�������Ĭ��һ����ʼ���������ʾ
		if (isCreate) {
			// ����Ǵ���
			try {
				createDefaultTemplate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ����Ǹ��ƣ�
		else if (isCreate) {

		}// �������ʾ���޸�
		else {
		}
		this.pack();
	}

	public static void createPrint(String funcId, StorageDataSet dataSet) {
		new PrintDesignFrame(funcId, dataSet, true).showFrame();

	}

	public static void createPrint(String funcId, PrintDataManage printDataManage) {

		new PrintDesignFrame(funcId, printDataManage, true).showFrame();
	}

	public static void createPrint(String funcId, String frameType, PrintDataManage printDataManage) {
		new PrintDesignFrame(funcId, frameType, printDataManage, true).showFrame();
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
	 * @date 2017��10��9��
	 */
	public void showElementAttr(String type) {
		// ���������
		elementAttributePanel.removeAll();
		/*
		 * { "X", "Y", "WIDTH", "HEIGHT", "FORECOLOR", "BACKCOLOR", "TEXT",
		 * "EXPRESSION", "FONT_NAME", "FONT_SIZE", "BOLD_DESC", "ITALIC_DESC",
		 * "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
		 * "VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "IMAGE_SCALE_DESC",
		 * "LINE_DIRECTION", "LINE_SIZE", "BAR_TYPE", "TEXT_POSITION",
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
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "LINE_DIRECTION_DESC", "LINE_SIZE",
					"LINE_STYLE", "FORECOLOR" };
		} else if (PrintElementType.LABEL.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "TEXT", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "FORECOLOR", "BACKCOLOR" };
		} else if (PrintElementType.TEXT.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "FONT_NAME", "FONT_SIZE",
					"BOLD_DESC", "ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "FORECOLOR", "BACKCOLOR" };
		} else if (PrintElementType.BAR_CODE.equals(type)) {
			showColumn = new String[] { "X", "Y", "WIDTH", "HEIGHT", "EXPRESSION", "BAR_TYPE" };
		} else if (PrintElementType.TABLE_HEAD.equals(type)) {
			showColumn = new String[] { "WIDTH", "HEIGHT", "TEXT", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "FORECOLOR", "BACKCOLOR" };
		} else if (PrintElementType.TABLE_CELL.equals(type)) {
			showColumn = new String[] { "WIDTH", "HEIGHT", "EXPRESSION", "FONT_NAME", "FONT_SIZE", "BOLD_DESC",
					"ITALIC_DESC", "UNDERLINE_DESC", "STRIKETHROUGH_DESC", "HORIZONTAL_ALIGNMENT_DESC",
					"VERTICAL_ALIGNMENT_DESC", "ROTATION_DESC", "FORECOLOR", "BACKCOLOR" };
		} else {
			showColumn = new String[] {};
		}
		// ѭ����ʾ��Ӧ����
		// Ԫ������
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
		// ��ʾ��Ӧ��ֵ
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
			// �Ƿ�סctrl���������ס���Ƕ�ѡ����˼
			if (!e.isControlDown()) {
				for (PrintItem<?> com : selectList) {
					com.setBorder(defaultBorder);
					// com.setVisibleEdit(false);
				}
				selectList.clear();
			}
			if (e.getSource() instanceof PrintElementItem) {
				// ѡ��л�����ӡ��
				PrintElementItem item = (PrintElementItem) e.getSource();

				// ѡ�ж����Ϊ��ǰ����Ŀؼ�
				selectComp = item;
				selectPanel = item.getParentPanel();

			}// �����������
			else if (e.getSource() instanceof PrintDesignView || e.getSource() instanceof JViewport) {
				PrintDesignView item = null;
				if (e.getSource() instanceof JViewport) {
					JViewport view = (JViewport) e.getSource();
					PrintTable table = (PrintTable) view.getView();
					item = table.getPanel();
				} else {
					item = (PrintDesignView) e.getSource();
				}
				selectPanel = item;
				detailDataSet.first();
				for (int i = 0; i < detailDataSet.rowCount(); i++) {
					if (detailDataSet.getBigDecimal("UNIQUE_ID").intValue() == item.getUniqueId()) {
						break;
					}
					detailDataSet.next();
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					panelRightMenu.show((PrintDesignPanel) item, e.getPoint().x, e.getPoint().y);
				}

				// ��λ��ѡ�����
				cardLayout.first(attributePanel);
				cardLayout.next(attributePanel);
			} else if (e.getSource() instanceof TableCellRenderer) {
				// TODO
				// TableCellRenderer tableCellRenderer = (TableCellRenderer)
				// e.getSource();
				// ����Ǳ�ͷ��ĳһ��
			} else if (e.getSource() instanceof JTableHeader) {
				// ����Ǳ�ͷ��ѡ��
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
				selectPanel = renderer.getParentPanel();
			} else if (e.getSource() instanceof JTable) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (table.getCellRenderer(row, col) instanceof PrintTableCellHeaderRenderer) {
					PrintTableCellHeaderRenderer renderer = (PrintTableCellHeaderRenderer) table.getCellRenderer(row,
							col);
					selectComp = renderer;
					selectPanel = renderer.getParentPanel();
				}
			} else {
				// �����ҳ��
				cardLayout.first(attributePanel);
				if (e.getButton() == MouseEvent.BUTTON3) {
					pageRightMenu.show(designPanel, e.getPoint().x, e.getPoint().y);
				}
			}

			if (e.getSource() instanceof PrintItem || e.getSource() instanceof JTableHeader
					|| e.getSource() instanceof JTable) {
				if (!(e.getSource() instanceof JViewport)) {
					selectList.remove(selectComp);
					// ȷ�����ѡ�еķ��ڵ�һ��
					selectList.add(0, selectComp);
					for (PrintItem<?> com : selectList) {
						com.setBorder(clicedBorder);
					}
				}
				// ������Ҽ�,��ʾ�Ҽ��˵�
				if (e.getButton() == MouseEvent.BUTTON3) {
					// TODO����ò�ƿ�������,������֤����
					if (selectComp instanceof PrintTableCellHeaderRenderer) {
						// ����������ط������������ PrintDesignView
						Point point = SwingUtils.getMousePoint((PrintDesignPanel) selectComp.getParentPanel());
						rightMenu.show((PrintDesignPanel) selectComp.getParentPanel(), point.x, point.y);
					} else {
						JLabel label = (JLabel) selectComp;
						rightMenu.show(label, e.getPoint().x, e.getPoint().y);
					}
				}
				// ��λ��ѡ�����
				elementDataSet.first();
				for (int i = 0; i < elementDataSet.rowCount(); i++) {
					if (elementDataSet.getBigDecimal("UNIQUE_ID").intValue() == selectComp.getUniqueId()) {
						break;
					}
					elementDataSet.next();
				}
				showElementAttr(selectComp.getType());
				cardLayout.last(attributePanel);
				// ���˫�����򵯴�һ������ܹ��༭
				if (e.getClickCount() == 2) {
					// �����˫������ʾ��Ӧ���ı��༭
					selectComp.setVisibleEdit(true);
				}
			}
		}
	}

	// �˵����¼�
	ItemActionListener itemAction = new ItemActionListener();

	private class ItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// ����
			if (e.getSource() == copyItem) {
				// ����ǰѡ�еĶ���ŵ���������
				// copyCacheItem = selectList;
				copyCacheItem.clear();
				copyCacheItem.addAll(selectList);
				pasteItem.setEnabled(true);
			}// ճ��
			else if (e.getSource() == pasteItem) {
				// ճ����ȡ��ǰѡ�е����

				// ����ֻ��������ϲŻᴥ��
				PrintDesignView panel = selectPanel;
				List<PrintItem<?>> tempSelectList = panel.copyItems(copyCacheItem);

				// ���֮ǰѡ�е���ʽ
				for (PrintItem<?> com : selectList) {
					if (com != null)
						com.setBorder(defaultBorder);
				}
				selectList.clear();
				selectList = tempSelectList;
				if (tempSelectList != null && tempSelectList.size() > 0) {
					selectComp = tempSelectList.get(0);
					/* item.setBorder(clicedBorder); */
					for (PrintItem<?> com : tempSelectList) {
						/* com.setBorder(defaultBorder); */
						com.setBorder(clicedBorder);
						com.addMouseListener(itemSelectAdapter);
					}
				}
			}// ɾ��
			else if (e.getSource() == deleteItem) {
				for (int i = 0; i < selectList.size(); i++) {
					selectList.get(i).delete();
				}
			}// ���Ƹ�ʽ
			else if (e.getSource() == copyForamtItem) {
				copyFormatCacheItem = selectComp;
			}// ճ����ʽ
			else if (e.getSource() == pasteForamtItem) {
				// ����ǰ�����ڴ�Ķ����ʽ���Ƶ�ѡ�еļ���
				for (int i = 0; i < selectList.size(); i++) {
					PrintItem<?> temp = selectList.get(i);
					temp.setFontName(copyFormatCacheItem.getFontName());
					temp.setFontSize(copyFormatCacheItem.getFontSize());
					temp.setIsBold(copyFormatCacheItem.getIsBold());
					temp.setIsitalic(copyFormatCacheItem.getIsitalic());
					temp.setIsstrikethrough(copyFormatCacheItem.getIsstrikethrough());
					temp.setIsUnderline(copyFormatCacheItem.getIsUnderline());
					temp.setElementHorizontalAlignment(copyFormatCacheItem.getElementHorizontalAlignment());
					temp.setElementVerticalAlignment(copyFormatCacheItem.getElementVerticalAlignment());
				}

			} else if (e.getSource() == deletePaneItem) {
				// ɾ����ǰѡ�����
				// if(JOptionPane.showConfirmDialog(parentComponent,
				// "���Ƿ�ɾ����ǰ��弰������������飿"))
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "���Ƿ�ɾ����ǰ��弰������������飿", "",
						JOptionPane.YES_NO_OPTION)) {
					designPanel.delete(selectPanel);
					return;
				}
				// ɾ���������
			} else if (e.getSource() == showAttribute) {
				showDialog.setVisible(true);
			} else if (e.getSource() == pageConfigureItem) {

			} else if (e.getSource() == addPanelByPageItem || e.getSource() == addPane) {
				// ����һ���µ�
				PrintDesignView panel = designPanel.addPrintDesignPanel(100, PrintDesignView.ZDY_VIEW, "�Զ�������");
				// ����϶����մ����¼�
				panel.setTransferHandler(targatTransferHandler);
				panel.addMouseListener(itemSelectAdapter);
				panel.setTableId(printDataManage.getMainPrintStorageDataSet().getTableId());
				panel.setCirculation(false);// ��ѭ��
				panel.setAutoStretch(false);// ���������̶��߶�
			} else if (showPanelAttribute == e.getSource()) {
				showPanelDialog.setVisible(true);
			} else if (e.getSource() == upMoveItem) {
				selectPanel.toForward(1);
			} else if (e.getSource() == downMoveItem) {
				selectPanel.toBack(1);
			}
		}
	}

	/**
	 * @Description: ����һ��Ĭ�ϵ�ģ��
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017��9��29��
	 */
	private void createDefaultTemplate() {
		pageHeadPane = designPanel.addPrintDesignPanel(0, 100, PrintDesignView.ZDY_VIEW, "�����ҳͷ");
		// ����϶����մ����¼�
		pageHeadPane.setTransferHandler(targatTransferHandler);
		pageHeadPane.addMouseListener(itemSelectAdapter);
		pageHeadPane.setTableId(printDataManage.getMainPrintStorageDataSet().getTableId());
		pageHeadPane.setCirculation(false);// ��ѭ��
		pageHeadPane.setAutoStretch(false);// ���������̶��߶�
		tableHeadPane = designPanel.addPrintDesignPanel(1, 30, PrintDesignView.ZDY_VIEW, "����Ǳ�ͷ");
		// ����϶����մ����¼�
		tableHeadPane.setTransferHandler(targatTransferHandler);
		tableHeadPane.addMouseListener(itemSelectAdapter);
		tableHeadPane.setTableId(printDataManage.getMainPrintStorageDataSet().getTableId());
		tableHeadPane.setCirculation(false);// ��ѭ��
		tableHeadPane.setAutoStretch(false);// ���������̶��߶�
		tablePanel = designPanel.addPrintDesignPanel(2, 200, PrintDesignView.TABLE_VIEW, "����Ǳ���");
		// ����϶����մ����¼�
		tablePanel.setTransferHandler(targatTransferHandler);
		tablePanel.addMouseListener(itemSelectAdapter);
		tablePanel.setTableId(printDataManage.getListPrintStorageDataSet().getTableId());
		tablePanel.setCirculation(true);// ���Ĭ��ѭ��
		tablePanel.setAutoStretch(true);// �������Զ��������

		tableTailPanel = designPanel.addPrintDesignPanel(3, 100, PrintDesignView.ZDY_VIEW, "����Ǳ�β");
		// ����϶����մ����¼�
		tableTailPanel.setTransferHandler(targatTransferHandler);
		tableTailPanel.addMouseListener(itemSelectAdapter);
		tableTailPanel.setTableId(printDataManage.getMainPrintStorageDataSet().getTableId());
		tableTailPanel.setCirculation(false);// ��ѭ��
		tableTailPanel.setAutoStretch(false);// ���������̶��߶�

		pageTailPanel = designPanel.addPrintDesignPanel(4, 200, PrintDesignPanel.ZDY_VIEW, "�����ҳβ");
		// ����϶����մ����¼�
		pageTailPanel.setTransferHandler(targatTransferHandler);
		pageTailPanel.addMouseListener(itemSelectAdapter);
		pageTailPanel.setTableId(printDataManage.getMainPrintStorageDataSet().getTableId());
		pageTailPanel.setCirculation(false);
		pageTailPanel.setAutoStretch(false);// ���������̶��߶�
		designPanel.updateUI();
	}

	DeleteItemAction deleteItemAction = new DeleteItemAction();

	/**
	 * @Description: ����ɾ��ѡ����
	 * @author yyq
	 */
	@SuppressWarnings("serial")
	private class DeleteItemAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectList.size() > 0) {
				// ѡ�е�Ԫ�ض���ɾ������
			}
		}

	}

	/**
	 * @Description: ����ѡ�е���
	 * @author yyq
	 */
	CopyItemAction copyItemAction = new CopyItemAction();

	private class CopyItemAction extends AbstractAction {

		/**
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// ����ǰѡ�еĶ���ŵ���������
			// copyCacheItem = selectList;
			copyCacheItem.clear();
			copyCacheItem.addAll(selectList);
			pasteItem.setEnabled(true);

		}

	}

	/**
	 * @Description: ���Ƹ�ʽ
	 * @author yyq
	 */
	CopyFormatItemAction copyFormatItemAction = new CopyFormatItemAction();

	private class CopyFormatItemAction extends AbstractAction {

		/**
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			copyFormatCacheItem = selectComp;
		}

	}

	/**
	 * @author yyq
	 * @Description: ճ����ʵ����
	 */
	PasteItemAction pasteItemAction = new PasteItemAction();

	private class PasteItemAction extends AbstractAction {

		/**
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// ճ����ȡ��ǰѡ�е����

			// ����ֻ��������ϲŻᴥ��
			PrintDesignView panel = selectPanel;
			List<PrintItem<?>> tempSelectList = panel.copyItems(copyCacheItem);

			// ���֮ǰѡ�е���ʽ
			for (PrintItem<?> com : selectList) {
				if (com != null)
					com.setBorder(defaultBorder);
			}
			selectList.clear();
			selectList = tempSelectList;
			if (tempSelectList != null && tempSelectList.size() > 0) {
				selectComp = tempSelectList.get(0);
				/* item.setBorder(clicedBorder); */
				for (PrintItem<?> com : tempSelectList) {
					/* com.setBorder(defaultBorder); */
					com.setBorder(clicedBorder);
					com.addMouseListener(itemSelectAdapter);
				}
			}
		}

	}

	/**
	 * @author yyq
	 * @Description: ճ����ʽʵ����
	 */
	PasteFormatItemAction pasteFormatAction = new PasteFormatItemAction();

	private class PasteFormatItemAction extends AbstractAction {

		/**
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// ����ǰ�����ڴ�Ķ����ʽ���Ƶ�ѡ�еļ���
			for (int i = 0; i < selectList.size(); i++) {
				PrintItem<?> temp = selectList.get(i);
				temp.setFontName(copyFormatCacheItem.getFontName());
				temp.setFontSize(copyFormatCacheItem.getFontSize());
				temp.setIsBold(copyFormatCacheItem.getIsBold());
				temp.setIsitalic(copyFormatCacheItem.getIsitalic());
				temp.setIsstrikethrough(copyFormatCacheItem.getIsstrikethrough());
				temp.setIsUnderline(copyFormatCacheItem.getIsUnderline());
				temp.setElementHorizontalAlignment(copyFormatCacheItem.getElementHorizontalAlignment());
				temp.setElementVerticalAlignment(copyFormatCacheItem.getElementVerticalAlignment());
			}
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

	public List<PrintItem<?>> getSelectList() {
		return selectList;
	}

	public PrintItem<?> getSelectComp() {
		return selectComp;
	}

	/**
	 * @Description: ��ֱ���뷽ʽ
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
	 * @Description: ˮƽ���뷽ʽ
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
	 * @Description: �Ƿ�Ӵ�
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
	 * @Description: �Ƿ�б��
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
	 * @Description: �Ƿ��»��߻���
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
	 * @Description: �Ƿ��л���
	 * @return
	 * @return boolean
	 * @throws
	 * @author yyq
	 * @date 2017-9-7
	 */
	public boolean isStrikethrough() {
		return false;
	}

	private class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

		/**
		 * @Fields serialVersionUID : �汾��
		 */
		private static final long serialVersionUID = 1L;

		// ����Ҫ��ʵ�ֽӿڵķ�����
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			ImageIcon image = null;
			if (value instanceof Object[]) {
				Object[] values = (Object[]) value;
				image = (ImageIcon) values[0];
			}
			if (image != null) {

				this.setIcon(image);
			}
			return this;
		}

	}

	private class MyIcon extends ImageIcon {

		private static final long serialVersionUID = 1L;

		int type;

		public MyIcon(int type) {
			this.type = type;
		}

		@Override
		public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
			float[] dash = { 5, 5 };
			if (LineType.SOLID_LINE == type) {
				g.drawLine(0, c.getHeight() / 2, c.getWidth(), c.getHeight() / 2);
			} else if (LineType.DOTTED_LINE == type) {
				Graphics2D g2 = (Graphics2D) g;
				BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f); // ʵ�����»�ˢ
				g2.setStroke(bs); // �����µĻ�ˢ
				g.drawLine(0, c.getHeight() / 2, c.getWidth(), c.getHeight() / 2);
			} else {
				Graphics2D g2d = (Graphics2D) g;
				// ��ӿ����Ч��
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Color color = g.getColor();
				// g.setColor(Color.red);
				for (int i = 0; i < c.getWidth() / 5; i++) {
					g.fillOval(i * 5, c.getHeight() / 2, 2, 2);
				}
				g.setColor(color);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		}
	}

	ColorColumnCustomEditListener colorColumnCustomEditListener = new ColorColumnCustomEditListener();

	private class ColorColumnCustomEditListener implements ColumnCustomEditListener {

		@Override
		public Variant customEdit(DataSet dataSet, Column column) {
			Color color = JColorChooser.showDialog(PrintDesignFrame.this, "ѡȡ��ɫ", null);

			// ����û�ȡ����رմ���, �򷵻ص� color Ϊ null
			if (color == null) {
				return null;
			}
			int alpha = color.getAlpha();
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			String colorStr = String.format("%02x", alpha) + "," + String.format("#%02x%02x%02x", red, green, blue);
			dataSet.setString(column.getColumnName(), colorStr);
			/*
			 * if ("BACKCOLOR".equals(column.getColumnName())) {
			 * backColorField.setBackground(color); } else if
			 * ("FORECOLOR".equals(column.getColumnName())) {
			 * foreColorField.setForeground(color); }
			 */

			return null;
		}

	}

}
