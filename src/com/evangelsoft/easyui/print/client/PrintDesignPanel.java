package com.evangelsoft.easyui.print.client;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.Transient;
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
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.StorageDataSet;
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

	// ���������޸ĸ߶Ƚ������ĸ߶ȱ�С

	private static final long serialVersionUID = 2342887949083952968L;

	DBTableModel defaultModel = null;

	private String watermark;// ˮӡ

	// 0�ǲ�����߶ȿ��
	private int maxWidth = 0;

	private int maxheight = 0;

	private int minWidth = 0;

	private int minheight = 0;

	// �����Ƴ���
	public final static int notLimited = 0;

	/**
	 * ��Ϳ�ȣ�̫С��ʾ�ῴ����
	 */
	public final static int SYS_MIN_WIDTH = 5;

	/**
	 * ��͸߶ȣ�̫С��ʾ�ῴ����
	 */
	public final static int SYS_MIN_HEIGHT = 5;

	// ҳ����Ϣ
	PrintPage printPage;

	private int direction;

	private static ZdyBorder zdyBorder = new ZdyBorder();

	// private int printId;
	private int uniqueId;

	private int index;

	private String viewType;

	// �Ƿ��Զ�����
	private boolean autoStretch;

	private boolean circulation;

	private String tableName;

	private int colNum;

	private int colWidth;

	private int colSpacing;

	private String backFont;

	//
	private Record record;

	private int width;

	private int height;

	private PrintTable table;

	PrintTableScrollPane tableScrollPane;

	private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);

	/**
	 * @Fields itemsMap : ��¼��ǰ������е�Ԫ��
	 */
	HashMap<Integer, PrintItem> itemsMap = new HashMap<Integer, PrintItem>();

	public PrintDesignPanel(PrintPage printPage, StorageDataSet dataSet, boolean isAdd) {
		this(printPage, null, null, dataSet, isAdd);
	}

	public PrintDesignPanel(PrintPage printPage, String watermark, String viewType, StorageDataSet dataSet,
			boolean isAdd) {
		this.printPage = printPage;
		// this.addMouseMotionListener(this);
		// this.index = linkedPanel.size();
		this.viewType = viewType;
		this.setLayout(null);
		this.setBorder(zdyBorder);
		this.watermark = watermark;
		// �����������Ĭ������
		if (isAdd) {
			insert(null, null, viewType, watermark);
		} else {
			// ������޸ģ�����б����ݴ�ŵ���ǰ����
		}
		table = new PrintTable(this);
		/* table.setTableHeader(new PrintTableHeader()); */
		// this.setLayout(new BorderLayout());
		tableScrollPane = new PrintTableScrollPane(table);
		table.setAutoscrolls(true);
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
					// �������ǰ���ƶ�
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
						if (dataSet.getBigDecimal("INDEX").intValue() == e.getFromIndex() + 1) {
							dataSet.setBigDecimal("INDEX", BigDecimal.valueOf(e.getToIndex() + 1));
						} else if (dataSet.getBigDecimal("INDEX").intValue() > start
								&& dataSet.getBigDecimal("INDEX").intValue() <= end + 1) {
							if (backward) {
								dataSet.setBigDecimal("INDEX", dataSet.getBigDecimal("INDEX").subtract(BigDecimal.ONE));
							} else {
								dataSet.setBigDecimal("INDEX", dataSet.getBigDecimal("INDEX").add(BigDecimal.ONE));
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
				// Ϊ�˼��ݱ�����϶���ʾ
				else if (canResize(getResizingColumn(e.getPoint()), tableHeader) != (tableHeader.getCursor() == resizeCursor)) {
					tableHeader.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					// �����ƶ���ʱ�򴥷�
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
					// ���϶��ĵ�Ԫ��Ҳ����Ϊͬ�����
					tableHeader.revalidate();
					tableHeader.repaint();
				} else if (direction == 2) {
					// �õ����ڱ�ѡ�е������Ȼ��
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
					// TODO �и���Ҫ���Լ��㣬������Ҫ��ȥ��ͷ�߶�
					// ��С�������أ�̫�پͿ������ˡ�������
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
				// TODO ���table.getHeight()����Ҫ���ԡ���
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

		// �������������ק
		/*
		 * table.setDragEnabled(true);// ����������ק // ������ק�¼�
		 * table.setTransferHandler(this.getTransferHandler());
		 */
		table.setBackground(SystemColor.WHITE);
		// �ر��Զ�����
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// TODO ���һ�пռ�¼��������ʾ
		defaultModel = new DBTableModel(table);
		// PrintTableModel dataModel = new PrintTableModel();
		// defaultModel.addRow(new Object[0]);
		table.setModel(defaultModel);
		table.setRowHeight(30);
		// �����ʾ������ʾ���

		tableScrollPane.setBorder(null);
		if (TABLE_VIEW.equals(viewType)) {
			this.add(tableScrollPane);
		}
		this.setBackground(SystemColor.WHITE);
	}

	void insert(int width, int height) {
		insert(width, height, null, null);
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
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
		this.uniqueId = uniqueId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
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
		int index = 0;
		// ��ȡ��ǰ��ѡ�е���Ŀ
		// �ȼ������ֵ+1������һ��Ψһ��ʶ
		printPage.getPaneDataSet().first();
		for (int i = 0; i < printPage.getPaneDataSet().rowCount(); i++) {
			int temp = printPage.getPaneDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			int tempIndex = printPage.getPaneDataSet().getBigDecimal("INDEX").intValue();
			if (index < tempIndex) {
				index = tempIndex;
			}
			printPage.getPaneDataSet().next();
		}
		printPage.getPaneDataSet().insertRow(false);
		printPage.getPaneDataSet().setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 1));
		printPage.getPaneDataSet().setString("AUTO_STRETCH", BoolStr.TRUE);
		printPage.getPaneDataSet().setString("VIEW_TYPE", "N");
		printPage.getPaneDataSet().setBigDecimal("INDEX", BigDecimal.valueOf(index + 1));
		// ���ø߶ȣ����Ϊ�ջ���Ϊ0.ȡ��Сֵ
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
		printPage.getPaneDataSet().setString("BACK_TEXT", backText == null ? "���" + max : backText);
		this.setUniqueId(max + 1);
		this.setWatermark(backText == null ? "���" + max : backText);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g2d.setColor(new Color(0, 0, 0));
		g2d.setFont(new Font("����", 0, 18));// ���ú�ɫ����,ͬ������
		g2d.drawString(this.watermark, (this.getWidth() - 100) / 2, (this.getHeight() + 15) / 2);// ����ˮӡ������ˮӡ���Ʒ�ʽ�����Լ��������޸�
		g.drawImage(bi, 0, 0, this);
	}

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
		super.setSize(width, height);
		this.printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(width));
		this.printPage.getPaneDataSet().setBigDecimal("HEIGHT", BigDecimal.valueOf(height));
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		this.printPage.getPaneDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(preferredSize.width));
		this.printPage.getPaneDataSet().setBigDecimal("HEIGHT", BigDecimal.valueOf(preferredSize.height));
	}

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
		// ����Ǳ��
		printPage.getItemDataSet().first();
		int max = 0;
		int index = 0;
		for (int i = 0; i < printPage.getItemDataSet().rowCount(); i++) {
			int temp = printPage.getItemDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			// ͬһ������Ψһ�±�
			if (printPage.getItemDataSet().getBigDecimal("PANEL_ID").intValue() == this.getUniqueId()) {

				int tempIndex = printPage.getItemDataSet().getBigDecimal("INDEX").intValue();
				if (index < tempIndex) {
					index = tempIndex;
				}
			}
			printPage.getItemDataSet().next();
		}
		PrintItem item = null;
		if (TABLE_VIEW.equals(viewType)) {

			// defaultModel.addColumn("��" + (index+1));
			// table.setModel(defaultModel);
			TableColumn column = new TableColumn();
			column.setModelIndex(table.getColumnModel().getColumnCount());
			// TableColumn column =
			// table.getColumnModel().getColumn(defaultModel.getColumnCount()-1);
			column.setHeaderValue(printType.getText() == null ? "��" + (table.getColumnModel().getColumnCount() + 1)
					: printType.getText());
			column.setPreferredWidth(100);

			// �໥���棬���ڼ���
			PrintTableCellHeaderRenderer haederRendere = new PrintTableCellHeaderRenderer(this, column,
					printPage.getItemDataSet());
			haederRendere.setUniqueId(max + 1);
			haederRendere.setIndex(index + 1);
			column.setHeaderRenderer(haederRendere);

			haederRendere.setParentPanel(this);

			PrintTableCellHeaderRenderer cellRendere = new PrintTableCellHeaderRenderer(this, column,
					printPage.getItemDataSet());
			cellRendere.setUniqueId(max + 2);

			// //������Ϊ�Զ���Ķ���
			column.setCellRenderer(cellRendere);
			addDataSetRow(haederRendere);
			addDataSetRow(cellRendere);
			haederRendere.setType(PrintElementType.TABLE_HEAD);
			cellRendere.setType(PrintElementType.TABLE_CELL);

			// ��ͷ��ֵ
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
				cellRendere.setText("ֵ" + (table.getColumnCount() + 1));
			}

			TableColumnModel model = table.getColumnModel();
			model.addColumn(column);
			table.setColumnModel(model);

			list.add(cellRendere);
			list.add(haederRendere);
			// �ƶ������
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

			this.add(printItem);
			printItem.setLocation(point);
			printItem.setSize(100, 30);
			item = printItem;
			item.setIndex(index + 1);

			printItem.setParentPanel(this);
			list.add(item);
		}
		for (PrintItem it : list) {
			// ����ǰѡ�е������帳���´����Ķ���
			// it.setFontName(printPage.getFontName());
			// it.setFontSize(printPage.getFontSize());
			// it.setWidth(100);
			// it.setHeight(30);
			// t��ӵ�
			itemsMap.put(it.getUniqueId(), it);
		}
		return list;
	}

	public <T extends PrintItem> PrintItem getDefaultValue(T item) {
		item.setFontName(this.getPrintPage().getFontName());
		item.setFontSize(this.getPrintPage().getFontSize());
		// dataSet.setBigDecimal("FONT_SIZE",
		// BigDecimal.valueOf(item.getFontSize()));// �����С
		item.setRotation("N");
		item.setIsBold(this.getPrintPage().isBold());
		// dataSet.setString("BOLD", BoolStr.getString(item.getIsBold()));//
		// �Ƿ�Ӵ�
		item.setIsitalic(this.getPrintPage().isItalic());
		// dataSet.setString("ITALIC", BoolStr.getString(item.getIsitalic()));//
		// �Ƿ�б��
		item.setIsUnderline(this.getPrintPage().isUnderline());
		// dataSet.setString("UNDERLINE",
		// BoolStr.getString(item.getIsUnderline()));// �»���
		item.setIsstrikethrough(this.getPrintPage().isStrikethrough());
		// dataSet.setString("STRIKETHROUGH",
		// BoolStr.getString(item.getIsstrikethrough()));// �Ƿ��л���
		item.setElementHorizontalAlignment(this.getPrintPage().getHorizontalAlignment() + "");
		// dataSet.setString("HORIZONTAL_ALIGNMENT",
		// item.getElementHorizontalAlignment());// ˮƽ���뷽ʽ
		item.setVerticalAlignment(this.getPrintPage().getVerticalAlignment());
		// dataSet.setString("VERTICAL_ALIGNMENT",
		// item.getElementVerticalAlignment());// ��ֱ���뷽ʽ
		return item;
	}

	private void addDataSetRow(PrintItem item) {
		StorageDataSet dataSet = printPage.getItemDataSet();
		dataSet.insertRow(false);
		dataSet.setBigDecimal("UNIQUE_ID", new BigDecimal(item.getUniqueId()));
		dataSet.setBigDecimal("INDEX", new BigDecimal(item.getIndex()));
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
			// ������ʾ�������һ��
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
		// ���Ƶ���¼�
		if (table != null) {
			table.addMouseListener(l);
			table.getTableHeader().addMouseListener(l);
			table.getParent().addMouseListener(l);
		}
	}

	// ��Ӹ��Ƶ�Ԫ����
	public List<PrintItem<?>> copyItems(List<PrintItem<?>> itemList) {
		Set<Integer> set = new HashSet<Integer>();
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		// ���������ʾ���,��Ҫ�����ظ�
		for (PrintItem<?> item : itemList) {
			// ��������˱��ֻ��Ҫͬ����ͷ������Ҫ������ʵ��
			if (!set.contains(item.getUniqueId())) {
				// ���������
				// �����ǰҲ�Ǳ��ֻ��Ҫ���һ��
				list.addAll(copyItem(item));
				set.add(item.getUniqueId());
				if (TABLE_VIEW.equals(viewType)) {
					set.add(item.getRelationId());
				}
			}
		}

		for (PrintItem it : list) {
			itemsMap.put(it.getUniqueId(), it);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<PrintItem<?>> copyItem(PrintItem<?> item) {
		List<PrintItem<?>> list = new ArrayList<PrintItem<?>>();
		// ����Ǳ��
		printPage.getItemDataSet().first();
		int max = 0;
		int index = 0;
		for (int i = 0; i < printPage.getItemDataSet().rowCount(); i++) {
			int temp = printPage.getItemDataSet().getBigDecimal("UNIQUE_ID").intValue();
			if (max < temp) {
				max = temp;
			}
			// ͬһ������Ψһ�±�
			if (printPage.getItemDataSet().getBigDecimal("PANEL_ID").intValue() == this.getUniqueId()) {

				int tempIndex = printPage.getItemDataSet().getBigDecimal("INDEX").intValue();
				if (index < tempIndex) {
					index = tempIndex;
				}
			}
			printPage.getItemDataSet().next();
		}

		// ���ճ�����ĵط��Ǳ��
		if (PrintElementType.TABLE_HEAD == item.getType() || PrintElementType.TABLE_CELL == item.getType()) {

			PrintItem headPrintItem = null;
			PrintItem cellPrintItem = null;
			// �����ǰ�Ǳ�ͷ
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
				// �ҵ�����������
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 2));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				// �ҵ������еĹ��������ݶ���

				column.setHeaderValue(headPrintItem.getText() == null ? "��"
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
				// ��������
				PrintItemTool.copy(headPrintItem, headerRenderer);
				PrintItemTool.copy(cellPrintItem, cellHeaderRenderer);

				table.getModel().setValueAt(cellPrintItem.getText(), 0, table.getColumnCount() - 1);
				// ���ƶ�Ӧ������
				list.add(headerRenderer);
				list.add(cellHeaderRenderer);
				// �����Ĺ�������ʾ������λ��
				JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
				jscrollBar.setValue(jscrollBar.getMaximum());
			} else {
				// ��������ڵ�ǰ��Ԫ���
				Point mousePoint = SwingUtils.getMousePoint(this);;
				// ����Ǳ�����ݸ��Ƶ���壬Ҳ��ʾ����

				PrintElementItem printItem = new PrintElementItem(PrintElementType.LABEL, this);
				printItem.setUniqueId(max + 1);
				// addDataSetRow(printItem);

				PrintItemTool.copy(item, printItem);
				this.add(printItem);
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
				// �����ǰ�Ǳ�ͷ
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
				// �ҵ�����������
				dataSet.setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(max + 2));
				dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.getUniqueId()));
				// �ҵ������еĹ��������ݶ���

				column.setHeaderValue(headPrintItem == null || headPrintItem.getText() == null ? "��"
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
				// ��������
				if (headPrintItem == null) {
					// ����ǿ�ȥĬ��ֵ
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
				// ���ƶ�Ӧ������
				list.add(headerRenderer);
				list.add(cellHeaderRenderer);
				// �����Ĺ�������ʾ������λ��
				JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
				jscrollBar.setValue(jscrollBar.getMaximum());
			} else {
				Point mousePoint = SwingUtils.getMousePoint(this);;
				// ����Ǳ�����ݸ��Ƶ���壬Ҳ��ʾ����

				PrintElementItem printItem = new PrintElementItem(PrintElementType.LABEL, this);
				printItem.setUniqueId(max + 1);
				// addDataSetRow(printItem);

				PrintItemTool.copy(item, printItem);
				this.add(printItem);
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
		// TODO Auto-generated method stub
		System.out.println("AAAAAAAA");
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		System.out.println("BBBBBBBBBBB");
	}

	public void toForward(int num) {
		// TODO Auto-generated method stub

	}

	public void toBack(int num) {
		// TODO Auto-generated method stub

	}

	public void toTable() {
		// TODO Auto-generated method stub

	}

	public void toZdy() {
		// TODO Auto-generated method stub

	}

	public void toIndex(int index) {
		// TODO Auto-generated method stub

	}

	public void toFisrt() {
		// TODO Auto-generated method stub

	}

	public void toLast() {
		// TODO Auto-generated method stub

	}

	public void setShowtype(String type) {
		// TODO Auto-generated method stub

	}

	public boolean getAutoStretch() {
		return autoStretch;
	}

	public void setAutoStretch(boolean autoStretch) {
		this.autoStretch = autoStretch;
	}

	public boolean isCirculation() {
		return circulation;
	}

	public void setCirculation(boolean circulation) {
		this.circulation = circulation;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getColWidth() {
		return colWidth;
	}

	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

	public int getColSpacing() {
		return colSpacing;
	}

	public void setColSpacing(int colSpacing) {
		this.colSpacing = colSpacing;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		this.table.addMouseMotionListener(l);
		tableScrollPane.addMouseMotionListener(l);
	}

	public void setSize(double width, double height) {
		// this.width = (int) Math.ceil(width);
		// this.height = (int) Math.ceil(height);
		super.setSize((int) width, (int) height);
		this.tableScrollPane.setSize((int) width, (int) height - 2);
		this.tableScrollPane.getViewport().setSize((int) width, (int) height - 2);
	}

}
