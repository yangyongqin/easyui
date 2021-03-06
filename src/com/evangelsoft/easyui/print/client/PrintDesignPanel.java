package com.evangelsoft.easyui.print.client;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.border.AbstractBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.print.type.LineDirection;
import com.evangelsoft.easyui.print.type.PrintDesignView;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.print.type.PrintItemTool;
import com.evangelsoft.easyui.template.client.nc.StringUtil;
import com.evangelsoft.easyui.tool.SwingUtils;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.types.BoolStr;

public class PrintDesignPanel extends JPanel implements PrintDesignView {

	private HashMap<Integer, TableColumn> tableColumnMap = new HashMap<Integer, TableColumn>();

	// 用来排序，修改高度将其他的高度变小

	private static final long serialVersionUID = 2342887949083952968L;

	DBTableModel defaultModel = null;

	private String watermark;// 水印

	// 0是不管理高度宽度
	private int maxWidth = 0;

	private int maxheight = 0;

	private int minWidth = 0;

	private int minheight = 0;

	// 不限制常量
	public final static int notLimited = 0;

	/**
	 * 最低宽度，太小显示会看不到
	 */
	public final static int SYS_MIN_WIDTH = 5;

	/**
	 * 最低高度，太小显示会看不到
	 */
	public final static int SYS_MIN_HEIGHT = 5;

	// 页面信息
	PrintPage printPage;

	PrintDesignManagePanel managepane;

	private int direction;

	private static ZdyBorder zdyBorder = new ZdyBorder();

	// private int printId;
	private int uniqueId;

	private int index;

	private String viewType;

	// 是否自动伸缩
	private Boolean autoStretch;

	private Boolean circulation;

	private String tableId;

	private int colNum = -1;

	private int colWidth = -1;

	private int colSpacing = -1;

	private String backFont;

	private String showType;

	//
	private Record record;

	private double width, height;

	// private int height;

	private PrintTable table;

	PrintTableScrollPane tableScrollPane;

	private StorageDataSet paneDataSet;

	int x, y;

	private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);

	PrintContentPanel contentPanel;

	/**
	 * @Fields itemsMap : 记录当前面板所有的元素
	 */
	HashMap<Integer, PrintItem<?>> itemsMap = new HashMap<Integer, PrintItem<?>>();

	public PrintDesignPanel(PrintDesignManagePanel managepane, StorageDataSet dataSet, boolean isAdd) {
		this(managepane, null, null, dataSet, isAdd);
	}

	public PrintDesignPanel(PrintDesignManagePanel managepane, String watermark, String viewType,
			StorageDataSet dataSet, boolean isAdd) {

		contentPanel = new PrintContentPanel();
		this.setLayout(null);
		contentPanel.setLayout(null);
		// contentPanel.setBackground(SystemColor.red);
		contentPanel.setPreferredSize(this.getSize());
		contentPanel.setLocation(0, 0);
		this.add(contentPanel);
		/*
		 * contentPanel = new JPanel();
		 * contentPanel.setPreferredSize(this.getSize()); this.add(contentPanel,
		 * new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		 * GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		 * contentPanel.setBackground(SystemColor.BLUE);
		 */
		this.managepane = managepane;
		this.printPage = managepane.getPrintPage();
		paneDataSet = printPage.getPaneDataSet();
		this.viewType = viewType;

		this.setBorder(zdyBorder);
		this.watermark = watermark;
		// 如果是新增，默认数据
		table = new PrintTable(this);
		tableScrollPane = new PrintTableScrollPane(table);
		table.setAutoscrolls(true);
		if (isAdd) {
			insert(null, null, viewType, watermark);
		} else {
			// 如果是修改，则把列表数据存放到当前对象
		}

		/* table.setTableHeader(new PrintTableHeader()); */
		// this.setLayout(new BorderLayout());

		TableColumnModel model = table.getColumnModel();
		model.addColumnModelListener(new TableColumnModelListener() {

			@Override
			public void columnSelectionChanged(ListSelectionEvent e) {
			}

			@Override
			public void columnRemoved(TableColumnModelEvent e) {
			}

			@Override
			public void columnMoved(TableColumnModelEvent e) {
				if (e.getToIndex() != e.getFromIndex()) {
					System.out.println("start:" + e.getFromIndex() + "   to:" + e.getToIndex());
					// 如果是向前面移动
					boolean backward = true;
					int start = e.getFromIndex();
					int end = e.getToIndex();
					if (e.getFromIndex() > e.getToIndex()) {
						backward = false;
						end = e.getFromIndex();
						start = e.getToIndex();
					}
					StorageDataSet dataSet = PrintDesignPanel.this.printPage.getItemDataSet();
					int index = dataSet.getRow();
					dataSet.first();
					for (int i = 0; i < PrintDesignPanel.this.printPage.getItemDataSet().rowCount(); i++) {
						if (dataSet.getBigDecimal("PLATE_INDEX").intValue() == e.getFromIndex() + 1) {
							dataSet.setBigDecimal("PLATE_INDEX", BigDecimal.valueOf(e.getToIndex() + 1));
						} else if (dataSet.getBigDecimal("PLATE_INDEX").intValue() > start
								&& dataSet.getBigDecimal("PLATE_INDEX").intValue() <= end + 1) {
							if (backward) {
								dataSet.setBigDecimal("PLATE_INDEX",
										dataSet.getBigDecimal("PLATE_INDEX").subtract(BigDecimal.ONE));
							} else {
								dataSet.setBigDecimal("PLATE_INDEX",
										dataSet.getBigDecimal("PLATE_INDEX").add(BigDecimal.ONE));
							}
						}
						dataSet.next();
					}
					dataSet.goToRow(index);
				}
			}

			@Override
			public void columnMarginChanged(ChangeEvent e) {
			}

			@Override
			public void columnAdded(TableColumnModelEvent e) {
			}
		});
		final JTableHeader tableHeader = table.getTableHeader();
		// JComponent com=tableHeader.
		tableHeader.setBackground(SystemColor.WHITE);
		tableHeader.addMouseMotionListener(new MouseAdapter() {

			private boolean canResize(TableColumn column, JTableHeader header) {
				return (column != null) && header.getResizingAllowed() && column.getResizable();
			}

			private TableColumn getResizingColumn(Point p) {
				return getResizingColumn(p, tableHeader.columnAtPoint(p));
			}

			private TableColumn getResizingColumn(Point p, int column) {
				if (column == -1) {
					return null;
				}
				Rectangle r = tableHeader.getHeaderRect(column);
				r.grow(-3, 0);
				if (r.contains(p)) {
					return null;
				}
				int midPoint = r.x + r.width / 2;
				int columnIndex;
				if (tableHeader.getComponentOrientation().isLeftToRight()) {
					columnIndex = (p.x < midPoint) ? column - 1 : column;
				} else {
					columnIndex = (p.x < midPoint) ? column : column - 1;
				}
				if (columnIndex == -1) {
					return null;
				}
				return tableHeader.getColumnModel().getColumn(columnIndex);
			}

			private int direction = 0;

			@Override
			public void mouseMoved(MouseEvent e) {
				if (Math.abs(tableHeader.getHeight() - e.getPoint().y) < 5) {
					tableHeader.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					direction = 1;
				}
				// 为了兼容表格宽度拖动显示
				else if (canResize(getResizingColumn(e.getPoint()), tableHeader) != (tableHeader.getCursor() == resizeCursor)) {
					tableHeader.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					// 左右移动的时候触发
					direction = 2;
				} else {
					tableHeader.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					direction = 0;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// Dimension dimension = PrintDesignPanel.this.getSize();
				if (direction == 1) {
					Point point = e.getPoint();
					int index = tableHeader.columnAtPoint(point);
					TableColumn column = tableHeader.getColumnModel().getColumn(index);
					if (column != null) {
						PrintTableCellHeaderRenderer hendrender = (PrintTableCellHeaderRenderer) column
								.getHeaderRenderer();
						hendrender.setHeight(point.y);
					}
					tableHeader.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));

					Dimension size = tableHeader.getPreferredSize();
					size.height = point.y;
					tableHeader.setPreferredSize(size);
					// hendrender.setHeight(point.y);
					// 被拖动的单元格也设置为同样宽度
					tableHeader.revalidate();
					tableHeader.repaint();
				} else if (direction == 2) {
					// 得到正在被选中的组件，然后
					TableColumn column = getResizingColumn(e.getPoint());
					PrintTableCellHeaderRenderer hendrender = (PrintTableCellHeaderRenderer) column.getHeaderRenderer();
					hendrender.setWidth(column.getWidth());
				}
			}
		});
		table.addMouseMotionListener(new MouseAdapter() {

			private boolean isMove = false;

			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if (isMove) {
					Point point = e.getPoint();
					// 最小三个像素，太少就看不到了。。。。
					if (point.y > 2) {
						table.setRowHeight(point.y);
						int index = tableHeader.columnAtPoint(point);
						TableColumn column = tableHeader.getColumnModel().getColumn(index);
						if (column != null) {
							PrintTableCellHeaderRenderer hendrender = (PrintTableCellHeaderRenderer) column
									.getCellRenderer();
							hendrender.setHeight(point.y);
						}
						tableHeader.revalidate();
						tableHeader.repaint();
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				if (Math.abs(table.getHeight() - e.getPoint().y) < 5) {
					table.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					isMove = true;
				} else {
					table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					isMove = false;
				}
			}

		});
		tableScrollPane.setBackground(SystemColor.WHITE);
		tableScrollPane.getViewport().setBackground(SystemColor.WHITE);

		// 表格设置允许拖拽
		/*
		 * table.setDragEnabled(true);// 设置允许拖拽 // 复制拖拽事件
		 * table.setTransferHandler(this.getTransferHandler());
		 */
		table.setBackground(SystemColor.WHITE);
		// 关闭自动伸缩
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		defaultModel = new DBTableModel(table);
		// PrintTableModel dataModel = new PrintTableModel();
		// defaultModel.addRow(new Object[0]);
		table.setModel(defaultModel);
		table.setRowHeight(30);
		// 如果显示表格才显示表格

		tableScrollPane.setBorder(null);
		if (TABLE_VIEW.equals(viewType)) {
			// this.add(tableScrollPane);
			contentPanel.add(tableScrollPane);
		}
		// this.setBackground(SystemColor.WHITE);

		// 设置默认值
		this.setColNum(1);
		this.setColWidth(this.getWidth());
		this.setColSpacing(0);
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
		this.table.repaint();
		this.tableScrollPane.setWatermark(watermark);
		this.contentPanel.setWatermark(watermark);
		this.repaint();
	}

	public PrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(PrintPage printPage) {
		this.printPage = printPage;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		if (this.uniqueId != uniqueId) {
			if (toRow() > -1) {
				this.paneDataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(uniqueId));
			}
		}
		this.uniqueId = uniqueId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		if (this.index == 0 || this.index != index) {
			paneDataSet.setBigDecimal("PLATE_INDEX", BigDecimal.valueOf(index));
		}
		this.index = index;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getBackFont() {
		return backFont;
	}

	public void setBackFont(String backFont) {
		this.backFont = backFont;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	void insert(Integer width, Integer height, String viemType, String backText) {
		int max = 0;
		int index = -1;
		// 获取当前被选中的项目
		// 先计算最大值+1，用作一个唯一标识
		printPage.getPaneDataSet().first();
		for (int i = 0; i < printPage.getPaneDataSet().rowCount(); i++) {
			int temp = printPage.getPaneDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			int tempIndex = printPage.getPaneDataSet().getBigDecimal("PLATE_INDEX").intValue();
			if (index < tempIndex) {
				index = tempIndex;
			}
			printPage.getPaneDataSet().next();
		}
		printPage.getPaneDataSet().insertRow(false);
		printPage.getPaneDataSet().setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 1));
		this.setUniqueId(max + 1);
		printPage.setSelectPanel(this);

		printPage.getPaneDataSet().setString("AUTO_STRETCH", BoolStr.TRUE);
		printPage.getPaneDataSet().setString("VIEW_TYPE", "N");
		printPage.getPaneDataSet().setBigDecimal("PLATE_INDEX", BigDecimal.valueOf(index + 1));
		// 设置高度，如果为空或者为0.取最小值
		if (height == null || 0 == height) {
			printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(SYS_MIN_HEIGHT));
		} else {
			printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(height));
		}
		if (width == null || 0 == width) {
			printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(printPage.getPageWidth()));
		} else {
			printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(width));
		}
		printPage.getPaneDataSet().setString("BACK_TEXT", backText == null ? "面板" + max : backText);
		this.setUniqueId(max + 1);
		this.setIndex(index + 1);
		this.setWatermark(backText == null ? "面板" + max : backText);
	}

	/*
	 * @Override public void paintComponent(Graphics g) {
	 * 
	 * super.paintComponent(g); BufferedImage bi = new
	 * BufferedImage(this.getWidth(), this.getHeight(),
	 * BufferedImage.TYPE_INT_ARGB); Graphics2D g2d = bi.createGraphics();
	 * g2d.getComposite();
	 * g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	 * 0.2f)); g2d.setColor(new Color(0, 0, 0)); g2d.setFont(new Font("黑体", 0,
	 * 18));// 设置黑色字体,同样可以 g2d.drawString(this.watermark, (this.getWidth() -
	 * 100) / 2, (this.getHeight() + 15) / 2);// 绘制水印，具体水印绘制方式根据自己的需求修改
	 * g.drawImage(bi, 0, 0, this); }
	 */
	/*
	 * protected void paintComponent(Graphics g) { // 画俩条线，表示禁用的面板 // 判断线条方向
	 * Graphics2D g2d = (Graphics2D) g; // 添加抗锯齿效果
	 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); g2d.setStroke(new BasicStroke(3));
	 * g.drawLine(0, this.getHeight(), this.getWidth(), 0); g.drawLine(0, 0,
	 * this.getWidth(), this.getHeight()); // 关闭抗齿距效果
	 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_OFF); }
	 */

	private static class ZdyBorder extends AbstractBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 88251574878032615L;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			super.paintBorder(c, g, x, y, width, height);
			g.setColor(SystemColor.BLUE);
			// g.drawLine(x, height-1, width, height);
			// g.drawLine(x, height-2, width, height-1);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5.0f));
			g2.drawLine(x, height, width, height);
		}
	}

	@Override
	public void setSize(int width, int height) {
		if (toRow() > -1) {
			super.setSize(width, height);
			this.tableScrollPane.setSize((int) width, (int) height - 2);
			this.tableScrollPane.getViewport().setSize((int) width, (int) height - 2);
			if (this.width != width) {
				paneDataSet.setBigDecimal("WIDTH", BigDecimal.valueOf(width));
			}
			if (this.height != height) {
				paneDataSet.setBigDecimal("HEIGHT", BigDecimal.valueOf(height));
			}
			contentPanel.setSize(width, height - 1);
			/* contentPanel.setPreferredSize(new Dimension(width, height)); */
			this.height = height;
			this.width = width;
			this.setColWidth((this.getWidth() - (this.colSpacing * (colNum < 1 ? 1 : colNum)))
					/ (colNum < 1 ? 1 : colNum));
			repaintPanel();
		}
	}

	/*
	 * @Override public void setPreferredSize(Dimension preferredSize) {
	 * super.setPreferredSize(preferredSize);
	 * this.printPage.getPaneDataSet().setBigDecimal("WIDTH",
	 * BigDecimal.valueOf(preferredSize.width));
	 * this.printPage.getPaneDataSet().setBigDecimal("HEIGHT",
	 * BigDecimal.valueOf(preferredSize.height)); }
	 */

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(width));
		this.printPage.getPaneDataSet().setBigDecimal("HEIGHT", BigDecimal.valueOf(height));
	}

	public JTable getTable() {
		return table;
	}

	public List<PrintItem<?>> addItem(final PrintElementType printType, Point point) {
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		// 如果是表格
		printPage.getItemDataSet().first();
		int max = 0;
		int index = 0;
		for (int i = 0; i < printPage.getItemDataSet().rowCount(); i++) {
			int temp = printPage.getItemDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			// 同一个面板才唯一下标
			if (printPage.getItemDataSet().getBigDecimal("PANEL_ID").intValue() == this.getUniqueId()) {

				int tempIndex = printPage.getItemDataSet().getBigDecimal("ELEMENT_INDEX").intValue();
				if (index < tempIndex) {
					index = tempIndex;
				}
			}
			printPage.getItemDataSet().next();
		}
		PrintItem<?> item = null;
		if (TABLE_VIEW.equals(viewType)) {

			// defaultModel.addColumn("列" + (index+1));
			// table.setModel(defaultModel);
			TableColumn column = new TableColumn();
			column.setModelIndex(table.getColumnModel().getColumnCount());
			// TableColumn column =
			// table.getColumnModel().getColumn(defaultModel.getColumnCount()-1);
			column.setHeaderValue(printType.getText() == null ? "列" + (table.getColumnModel().getColumnCount() + 1)
					: printType.getText());
			column.setPreferredWidth(100);

			// 相互交叉，便于计算
			PrintTableCellHeaderRenderer haederRendere = new PrintTableCellHeaderRenderer(this, column,
					printPage.getItemDataSet());
			haederRendere.setUniqueId(max + 1);
			haederRendere.setIndex(index + 1);
			column.setHeaderRenderer(haederRendere);

			haederRendere.setParentPanel(this);

			PrintTableCellHeaderRenderer cellRendere = new PrintTableCellHeaderRenderer(this, column,
					printPage.getItemDataSet());
			cellRendere.setUniqueId(max + 2);

			// //列设置为自定义的对象
			column.setCellRenderer(cellRendere);
			addDataSetRow(haederRendere);
			addDataSetRow(cellRendere);
			haederRendere.setType(PrintElementType.TABLE_HEAD);
			cellRendere.setType(PrintElementType.TABLE_CELL);

			// 表头赋值
			haederRendere.setUniqueId(max + 1);
			haederRendere.setWidth(100);
			haederRendere.setHeight(table.getTableHeader().getHeight());
			haederRendere.setIndex(index + 1);
			haederRendere.setText(column.getHeaderValue().toString());

			cellRendere.setIndex(index + 1);
			cellRendere.setParentPanel(this);
			cellRendere.setUniqueId(max + 2);
			cellRendere.setRelationId(haederRendere.getUniqueId());
			haederRendere.setRelationId(cellRendere.getUniqueId());

			tableColumnMap.put(haederRendere.getUniqueId(), column);
			tableColumnMap.put(cellRendere.getUniqueId(), column);

			if (!StringUtil.isEmpty(printType.getFieldName())) {
				cellRendere.setText(printType.getTableName() + "." + printType.getFieldName());
			} else {
				cellRendere.setText("值" + (table.getColumnCount() + 1));
			}

			TableColumnModel model = table.getColumnModel();
			model.addColumn(column);
			table.setColumnModel(model);

			list.add(cellRendere);
			list.add(haederRendere);
			// 移动到最后
			final JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
			jscrollBar.setValue(jscrollBar.getMaximum());
		} else {
			// PrintElementType type = new
			// PrintElementType(PrintElementType.LABEL, printType.getText(),
			// null);
			PrintElementItem printItem = PrintElementItem.createInstance(printType, this);
			printItem.setUniqueId(max + 1);
			addDataSetRow(printItem);
			printItem.setText(printType.getText());

			/* this.add(printItem); */
			contentPanel.add(printItem);
			printItem.setLocation(point);
			printItem.setSize(100, 30);
			item = printItem;
			item.setIndex(index + 1);

			printItem.setParentPanel(this);
			list.add(item);
		}
		for (PrintItem<?> it : list) {
			// 将当前选中的姿字体赋给新创建的对象
			// it.setFontName(printPage.getFontName());
			// it.setFontSize(printPage.getFontSize());
			// it.setWidth(100);
			// it.setHeight(30);
			// t添加到
			it.setBackColor(it.getBackColor());
			it.setForeColor(it.getForeColor());
			it.setLineSize(1);
			it.setLineDirection(LineDirection.LEFT);
			itemsMap.put(it.getUniqueId(), it);
		}
		return list;
	}

	public <T extends PrintItem<?>> PrintItem<?> getDefaultValue(T item) {
		item.setFontName(this.getPrintPage().getFontName());
		item.setFontSize(this.getPrintPage().getFontSize());
		// dataSet.setBigDecimal("FONT_SIZE",
		// BigDecimal.valueOf(item.getFontSize()));// 字体大小
		item.setRotation("N");
		item.setIsBold(this.getPrintPage().isBold());
		// dataSet.setString("BOLD", BoolStr.getString(item.getIsBold()));//
		// 是否加粗
		item.setIsitalic(this.getPrintPage().isItalic());
		// dataSet.setString("ITALIC", BoolStr.getString(item.getIsitalic()));//
		// 是否斜体
		item.setIsUnderline(this.getPrintPage().isUnderline());
		// dataSet.setString("UNDERLINE",
		// BoolStr.getString(item.getIsUnderline()));// 下划线
		item.setIsstrikethrough(this.getPrintPage().isStrikethrough());
		// dataSet.setString("STRIKETHROUGH",
		// BoolStr.getString(item.getIsstrikethrough()));// 是否中划线
		item.setElementHorizontalAlignment(this.getPrintPage().getHorizontalAlignment() + "");
		// dataSet.setString("HORIZONTAL_ALIGNMENT",
		// item.getElementHorizontalAlignment());// 水平对齐方式
		item.setVerticalAlignment(this.getPrintPage().getVerticalAlignment());
		// dataSet.setString("VERTICAL_ALIGNMENT",
		// item.getElementVerticalAlignment());// 垂直对齐方式
		return item;
	}

	private void addDataSetRow(PrintItem<?> item) {
		StorageDataSet dataSet = printPage.getItemDataSet();
		dataSet.insertRow(false);
		dataSet.setBigDecimal("UNIQUE_ID", new BigDecimal(item.getUniqueId()));
		dataSet.setBigDecimal("ELEMENT_INDEX", new BigDecimal(item.getIndex()));
		dataSet.setString("TYPE", item.getType());
		dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(item.getParentPanel().getUniqueId()));
		getDefaultValue(item);
	}

	public List<PrintItem<?>> addItem(PrintElementSource printSource, Point point) {
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		PrintElementType printType = new PrintElementType(PrintElementType.LABEL, printSource.getText(), null);
		printType.setDataType(printSource.getDataType());
		printType.setDesc(printSource.getText());
		printType.setFieldName(printSource.getFieldName());
		printType.setTableName(printSource.getTableName());
		printType.setSourceType(printSource.getSourceType());
		printType.setText(printSource.getText());
		list.addAll(this.addItem(printType, point));
		if (!TABLE_VIEW.equals(viewType)) {
			// 自由显示则再添加一次
			printType.setType(PrintElementType.TEXT);
			point.setLocation(point.x + 100, point.y);
			list.addAll(this.addItem(printType, point));
		}
		return list;
	}

	public void loadItem(Record record) {

	}

	public void loadItem(RecordSet set) {

	}

	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		// 复制点击事件
		if (table != null) {
			table.addMouseListener(l);
			table.getTableHeader().addMouseListener(l);
			table.getParent().addMouseListener(l);
		}
	}

	// 添加复制的元素项
	public List<PrintItem<?>> copyItems(List<PrintItem<?>> itemList) {
		Set<Integer> set = new HashSet<Integer>();
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		// 如果当是显示表格,需要过滤重复
		for (PrintItem<?> item : itemList) {
			// 如果复制了表格，只需要同步表头，不需要俩个都实现
			if (!set.contains(item.getUniqueId())) {
				// 如果不包含
				// 如果当前也是表格，只需要添加一个
				list.addAll(copyItem(item));
				set.add(item.getUniqueId());
				if (TABLE_VIEW.equals(viewType)) {
					set.add(item.getRelationId());
				}
			}
		}

		for (PrintItem<?> it : list) {
			itemsMap.put(it.getUniqueId(), it);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<PrintItem<?>> copyItem(PrintItem<?> item) {
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		// 如果是表格
		printPage.getItemDataSet().first();
		int max = 0;
		int index = 0;
		for (int i = 0; i < printPage.getItemDataSet().rowCount(); i++) {
			int temp = printPage.getItemDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			// 同一个面板才唯一下标
			if (printPage.getItemDataSet().getBigDecimal("PANEL_ID").intValue() == this.getUniqueId()) {

				int tempIndex = printPage.getItemDataSet().getBigDecimal("PLATE_INDEX").intValue();
				if (index < tempIndex) {
					index = tempIndex;
				}
			}
			printPage.getItemDataSet().next();
		}

		// 如果粘贴到的地方是表格
		if (PrintElementType.TABLE_HEAD == item.getType() || PrintElementType.TABLE_CELL == item.getType()) {

			PrintItem headPrintItem = null;
			PrintItem cellPrintItem = null;
			// 如果当前是表头
			if (item.getType() == PrintElementType.TABLE_HEAD) {
				headPrintItem = item;
				cellPrintItem = itemsMap.get(item.getRelationId());
			} else {
				cellPrintItem = item;
				headPrintItem = itemsMap.get(item.getRelationId());
			}
			if (TABLE_VIEW.equals(viewType)) {
				TableColumn oldColumn = tableColumnMap.get(item.getUniqueId());
				TableColumn column = new TableColumn();
				column.setModelIndex(table.getColumnModel().getColumnCount());
				column.setHeaderValue(oldColumn.getHeaderValue());
				// column.setPreferredWidth(preferredWidth);
				column.setWidth(oldColumn.getWidth());
				column.setPreferredWidth(oldColumn.getPreferredWidth());

				StorageDataSet dataSet = printPage.getItemDataSet();
				dataSet.insertRow(false);
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 1));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				dataSet.insertRow(false);
				// 找到相关联的组件
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 2));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				// 找到缓存中的关联的数据对象

				column.setHeaderValue(headPrintItem.getText() == null ? "列"
						+ (table.getColumnModel().getColumnCount() + 1) : headPrintItem.getText());

				PrintTableCellHeaderRenderer headerRenderer = new PrintTableCellHeaderRenderer(this, column, dataSet);
				headerRenderer.setUniqueId(max + 1);
				headerRenderer.setRelationId(max + 2);
				headerRenderer.setType(PrintElementType.TABLE_HEAD);
				headerRenderer.setIndex(index + 1);

				PrintTableCellHeaderRenderer cellHeaderRenderer = new PrintTableCellHeaderRenderer(this, column,
						dataSet);
				cellHeaderRenderer.setUniqueId(max + 2);
				cellHeaderRenderer.setRelationId(max + 1);
				cellHeaderRenderer.setType(PrintElementType.TABLE_CELL);
				cellHeaderRenderer.setIndex(index + 1);

				column.setHeaderRenderer(headerRenderer);
				column.setCellRenderer(cellHeaderRenderer);

				TableColumnModel model = table.getColumnModel();
				model.addColumn(column);
				table.setColumnModel(model);
				// 复制属性
				PrintItemTool.copy(headPrintItem, headerRenderer);
				PrintItemTool.copy(cellPrintItem, cellHeaderRenderer);

				table.getModel().setValueAt(cellPrintItem.getText(), 0, table.getColumnCount() - 1);
				// 复制对应的属性
				list.add(headerRenderer);
				list.add(cellHeaderRenderer);
				// 将表格的滚动条显示在最后的位置
				JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
				jscrollBar.setValue(jscrollBar.getMaximum());
			} else {
				// 计算鼠标在当前单元格的
				Point mousePoint = SwingUtils.getMousePoint(this);;
				// 如果是表格内容复制到面板，也显示俩个

				PrintElementItem printItem = new PrintElementItem(PrintElementType.LABEL, this);
				printItem.setUniqueId(max + 1);
				// addDataSetRow(printItem);

				PrintItemTool.copy(item, printItem);
				/* this.add(printItem); */
				contentPanel.add(printItem);
				this.repaint();
				printItem.setLocation(mousePoint);
				printItem.setSize(headPrintItem.getElementWidth(), headPrintItem.getElementHeight());
				item = printItem;
				item.setIndex(index + 1);
				printItem.setParentPanel(this);
				list.add(item);
			}
		} else {
			if (TABLE_VIEW.equals(viewType)) {
				PrintItem headPrintItem = null;
				PrintItem cellPrintItem = null;
				// 如果当前是表头
				if (item.getType() == PrintElementType.TABLE_HEAD) {
					headPrintItem = item;
					cellPrintItem = itemsMap.get(item.getRelationId());
				} else {
					cellPrintItem = item;
					headPrintItem = itemsMap.get(item.getRelationId());
				}

				TableColumn oldColumn = tableColumnMap.get(item.getUniqueId());
				TableColumn column = new TableColumn();
				column.setModelIndex(table.getColumnModel().getColumnCount());
				column.setHeaderValue(oldColumn == null ? item.getText() : oldColumn.getHeaderValue());
				// column.setPreferredWidth(preferredWidth);
				column.setWidth(oldColumn == null ? item.getElementWidth() : oldColumn.getWidth());
				column.setPreferredWidth(oldColumn == null ? item.getElementWidth() : oldColumn.getPreferredWidth());

				StorageDataSet dataSet = printPage.getItemDataSet();
				dataSet.insertRow(false);
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 1));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				dataSet.insertRow(false);
				// 找到相关联的组件
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 2));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				// 找到缓存中的关联的数据对象

				column.setHeaderValue(headPrintItem == null || headPrintItem.getText() == null ? "列"
						+ (table.getColumnModel().getColumnCount() + 1) : headPrintItem.getText());

				PrintTableCellHeaderRenderer headerRenderer = new PrintTableCellHeaderRenderer(this, column, dataSet);
				headerRenderer.setUniqueId(max + 1);
				headerRenderer.setRelationId(max + 2);
				headerRenderer.setType(PrintElementType.TABLE_HEAD);
				headerRenderer.setIndex(index + 1);

				PrintTableCellHeaderRenderer cellHeaderRenderer = new PrintTableCellHeaderRenderer(this, column,
						dataSet);
				cellHeaderRenderer.setUniqueId(max + 2);
				cellHeaderRenderer.setRelationId(max + 1);
				cellHeaderRenderer.setType(PrintElementType.TABLE_CELL);
				cellHeaderRenderer.setIndex(index + 1);

				column.setHeaderRenderer(headerRenderer);
				column.setCellRenderer(cellHeaderRenderer);

				TableColumnModel model = table.getColumnModel();
				model.addColumn(column);
				table.setColumnModel(model);
				// 复制属性
				if (headPrintItem == null) {
					// 如果是空去默认值
					getDefaultValue(headerRenderer);
				} else {
					PrintItemTool.copy(headPrintItem, headerRenderer);
				}
				if (cellPrintItem == null) {
					getDefaultValue(cellHeaderRenderer);
				} else {
					PrintItemTool.copy(cellPrintItem, cellHeaderRenderer);
				}

				table.getModel().setValueAt(cellPrintItem.getText(), 0, table.getColumnCount() - 1);
				// 复制对应的属性
				list.add(headerRenderer);
				list.add(cellHeaderRenderer);
				// 将表格的滚动条显示在最后的位置
				JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
				jscrollBar.setValue(jscrollBar.getMaximum());
			} else {
				Point mousePoint = SwingUtils.getMousePoint(this);;
				// 如果是表格内容复制到面板，也显示俩个
				PrintElementItem printItem = new PrintElementItem(PrintElementType.LABEL, this);
				printItem.setUniqueId(max + 1);
				// addDataSetRow(printItem);

				PrintItemTool.copy(item, printItem);
				/* this.add(printItem); */
				contentPanel.add(printItem);
				this.repaint();
				printItem.setLocation(mousePoint);
				printItem.setSize(item.getElementWidth(), item.getElementHeight());
				item = printItem;
				item.setIndex(index + 1);
				printItem.setParentPanel(this);
				list.add(item);
			}
		}
		return list;
	}

	public PrintItem<?> getPrintItem(int uniqueId) {
		if (itemsMap.containsKey(uniqueId)) {
			itemsMap.get(uniqueId);
		}
		return null;
	}

	@Override
	public void setWidth(int width) {
		setSize(width, this.getSize().height);
	}

	/*
	 * public Dimension getSize() { return new Dimension((int) width, (int)
	 * height); }
	 */

	@Override
	public void setHeight(int height) {
		setSize((int) this.getSize().getWidth(), height);
	}

	public void toForward(int num) {
		toIndex(index - num);
	}

	public void toBack(int num) {
		toIndex(index + num);
	}

	public void toIndex(int newIndex) {
		managepane.changeIndex(index, newIndex);
	}

	public void toFisrt() {
		toIndex(0);
	}

	public void toLast() {
		toIndex(managepane.getLinkedPanel().size() - 1);
	}

	public void toTable() {
		setShowType(TABLE_VIEW);
	}

	public void toZdy() {
		setShowType(ZDY_VIEW);
	}

	public void setShowType(String type) {
		this.showType = type;
		// 根据类型将面板属性改成对应的值显示
		if (ZDY_VIEW.equals(type) && !type.equals(this.viewType)) {

		} else if (TABLE_VIEW.equals(type) && !type.equals(this.viewType)) {

		}
	}

	public boolean getAutoStretch() {
		return autoStretch;
	}

	public void setAutoStretch(boolean autoStretch) {
		if (this.autoStretch == null || this.autoStretch != autoStretch) {
			paneDataSet.setString("AUTO_STRETCH", BoolStr.getString(autoStretch));
			this.autoStretch = autoStretch;
		}

	}

	public boolean isCirculation() {
		return circulation;
	}

	public void setCirculation(boolean circulation) {
		if (this.circulation == null || this.circulation != circulation) {
			paneDataSet.setString("CIRCULATION", BoolStr.getString(circulation));
			this.circulation = circulation;
		}
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		if (this.table == null || !this.table.equals(tableId)) {
			this.tableId = tableId;
			paneDataSet.setString("TABLE_ID", tableId);
		}
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		try {
			if (this.colNum != colNum) {
				paneDataSet.setBigDecimal("COL_NUM", BigDecimal.valueOf(colNum));
			}
			this.colNum = colNum;
			repaintPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setColSpacing(int colSpacing) {
		if (this.colSpacing != colSpacing) {
			paneDataSet.setBigDecimal("COL_SPACING", BigDecimal.valueOf(colSpacing));
			this.colSpacing = colSpacing;
			setColWidth((this.getWidth() - (this.colSpacing * (colNum < 1 ? 1 : colNum))) / (colNum < 1 ? 1 : colNum));
			repaintPanel();
		}
	}

	public void repaintPanel() {
		this.removeAll();
		int width = (this.getWidth() - (this.colSpacing * (colNum < 1 ? 1 : colNum))) / (colNum < 1 ? 1 : colNum);
		contentPanel.setSize(new Dimension(width, this.getHeight() - 1));
		contentPanel.setLocation(0, 0);
		this.add(contentPanel);
		for (int i = 1; i < colNum; i++) {
			PrintDisablePanel panel2 = new PrintDisablePanel();
			panel2.setSize(new Dimension(width, this.getHeight() - 1));
			panel2.setLocation((width + colSpacing) * i, 0);
			this.add(panel2);
		}
		this.repaint();
	}

	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		if (this.colWidth != colWidth) {
			paneDataSet.setBigDecimal("COL_WIDTH", BigDecimal.valueOf(colWidth));
			this.colWidth = colWidth;
			this.setColSpacing((this.getWidth() - (this.colWidth * (colNum < 1 ? 1 : colNum)))
					/ (colNum < 1 ? 1 : colNum));
			// 计算宽度
			repaintPanel();
		}

	}

	public int getColSpacing() {
		return colSpacing;
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		this.table.addMouseMotionListener(l);
		tableScrollPane.addMouseMotionListener(l);
	}

	/*
	 * @Override public void setSize(int width, int height) { if (toRow() > -1)
	 * { super.setSize((int) width, (int) height);
	 * this.tableScrollPane.setSize((int) width, (int) height - 2);
	 * this.tableScrollPane.getViewport().setSize((int) width, (int) height -
	 * 2); if (this.width != width) { paneDataSet.setBigDecimal("WIDTH",
	 * BigDecimal.valueOf(width)); } if (this.height != height) {
	 * paneDataSet.setBigDecimal("HEIGHT", BigDecimal.valueOf(height)); }
	 * this.height = height; this.width = width; } }
	 */

	@Override
	public void setParentId(int parentId) {
		paneDataSet.setBigDecimal("PARENT_ID", BigDecimal.valueOf(parentId));
	}

	@Override
	public void setX(int x) {
		this.setLocation(x, this.getLocation().y);
	}

	@Override
	public void setY(int y) {
		this.setLocation(this.getLocation().x, y);
	}

	public Point getLocation() {
		return new Point(x, y);
	}

	public void setLocation(int x, int y) {
		if (toRow() > -1) {
			// 值不同就
			if (this.x != x) {
				paneDataSet.setBigDecimal("X", BigDecimal.valueOf(x));
			}
			if (this.y != y) {
				paneDataSet.setBigDecimal("Y", BigDecimal.valueOf(y));
			}
			this.x = x;
			this.y = y;
			super.setLocation(x, y);
		}
	}

	public int toRow() {
		// 如果当前行不在
		if (this.printPage.getPaneDataSet() != null) {
			if (this.printPage.getPaneDataSet().getBigDecimal("UNIQUE_ID").intValue() == this.getUniqueId()) {
				return this.printPage.getPaneDataSet().getRow();
			}
			this.printPage.getPaneDataSet().first();
			for (int i = 0; i < this.printPage.getPaneDataSet().rowCount(); i++) {
				if (this.printPage.getPaneDataSet().getBigDecimal("UNIQUE_ID").intValue() == this.getUniqueId()) {
					return i;
				}
				this.printPage.getPaneDataSet().next();
			}
		}
		return -1;
	}

	public String getShowType() {
		return showType;
	}

	public HashMap<Integer, PrintItem<?>> getItemsMap() {
		return itemsMap;
	}

	public void setItemsMap(HashMap<Integer, PrintItem<?>> itemsMap) {
		this.itemsMap = itemsMap;
	}

	@Override
	public String toString() {
		return "PrintDesignPanel [uniqueId=" + uniqueId + ", index=" + index + "]";
	}

	public void setLayout(LayoutManager mgr) {
		super.setLayout(mgr);
	}
}
