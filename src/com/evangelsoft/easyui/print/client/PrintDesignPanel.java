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
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.template.client.nc.StringUtil;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.types.BoolStr;

public class PrintDesignPanel extends JPanel implements MouseMotionListener {

	/**
	 * @Fields TABLE_VIEW : �����ʾ
	 */
	public static String TABLE_VIEW = "T";

	/**
	 * @Fields ZDY_VIEW : �Զ�����ʾ
	 */
	public static String ZDY_VIEW = "N";

	// public LinkedHashMap<PrintDesignPanel, Integer> linkedPanel=new
	// LinkedHashMap<PrintDesignPanel, Integer>();

	// ���������޸ĸ߶Ƚ������ĸ߶ȱ�С
	private List<PrintDesignPanel> linkedPanel;

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
	private int autoStretch;

	private String backFont;

	//
	private Record record;

	private int width;

	private int height;

	private JTable table;

	TableScrollPane tableScrollPane;

	private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);

	public PrintDesignPanel(PrintPage printPage, StorageDataSet dataSet, boolean isAdd) {
		this(printPage, null, null, dataSet, isAdd);
	}

	public PrintDesignPanel(PrintPage printPage, String watermark, String viewType, StorageDataSet dataSet,
			boolean isAdd) {
		this.printPage = printPage;
		this.linkedPanel = printPage.getLinkedPanel();
		this.addMouseMotionListener(this);
		this.index = linkedPanel.size();
		linkedPanel.add(this);
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
		if (TABLE_VIEW.equals(viewType)) {
			table = new JTable() {

				/*
				 * public boolean isCellEditable(int row, int column) { return
				 * false;// ��������༭ }
				 */
			};
			/* table.setTableHeader(new PrintTableHeader()); */
			this.setLayout(new BorderLayout());

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			tableScrollPane = new TableScrollPane(table);
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
									dataSet.setBigDecimal("INDEX",
											dataSet.getBigDecimal("INDEX").subtract(BigDecimal.ONE));
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
					// TODO Auto-generated method stub
				}

				@Override
				public void columnAdded(TableColumnModelEvent e) {
					// TODO Auto-generated method stub
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

				// private boolean isMove = false;
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
						PrintTableCellHeaderRenderer hendrender = (PrintTableCellHeaderRenderer) column
								.getHeaderRenderer();
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
			// panel.add(table);
			// panel.add(table.getTableHeader(),BorderLayout.NORTH);
			tableScrollPane.setBackground(SystemColor.WHITE);
			tableScrollPane.getViewport().setBackground(SystemColor.WHITE);
			// pane.setBorder(new LineBorder(Color.GREEN, 5));
			this.add(tableScrollPane);
			// �������������ק
			/*
			 * table.setDragEnabled(true);// ����������ק // ������ק�¼�
			 * table.setTransferHandler(this.getTransferHandler());
			 */
			table.setBackground(SystemColor.WHITE);
			// �ر��Զ�����
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			// table.setPreferredSize(this.getPreferredSize());

			// TODO ���һ�пռ�¼��������ʾ
			defaultModel = new DBTableModel(table);
			// PrintTableModel dataModel = new PrintTableModel();
			// defaultModel.addRow(new Object[0]);
			table.setModel(defaultModel);
			table.setRowHeight(30);

			// table.getParent().getParent();
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
		g2d.setColor(new Color(0, 0, 0));// ���ú�ɫ����,ͬ������
		g2d.drawString(this.watermark, (this.getWidth() - 100) / 2, (this.getHeight() + 15) / 2);// ����ˮӡ������ˮӡ���Ʒ�ʽ�����Լ��������޸�
		g.drawImage(bi, 0, 0, this);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (PrintDesignPanel.this.getSize().getHeight() - e.getPoint().getY() <= 3) {
			PrintDesignPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			direction = Direction.DOWN;
		} else {
			PrintDesignPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			direction = 0;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// �������С
		Dimension dimension = PrintDesignPanel.this.getSize();
		Point point = PrintDesignPanel.this.getLocation();
		switch (direction) {
			case Direction.LEFT_UP:
				dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight() - e.getY());
				// PrintDesignPanel.this.setSize(dimension);
				point.setLocation(PrintDesignPanel.this.getLocation().x + e.getX(),
						PrintDesignPanel.this.getLocation().y + e.getY());
				break;
			case Direction.RIGHT_UP:
				dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(),
						dimension.getHeight() - e.getY());
				// PrintDesignPanel.this.setSize(dimension);
				point.setLocation(PrintDesignPanel.this.getLocation().x,
						PrintDesignPanel.this.getLocation().y + e.getY());
				break;
			case Direction.UP:
				dimension.setSize(dimension.getWidth(), dimension.getHeight() - e.getY());
				// PrintDesignPanel.this.setSize(dimension);
				point.setLocation(PrintDesignPanel.this.getLocation().x,
						PrintDesignPanel.this.getLocation().y + e.getY());
				break;
			case Direction.LEFT:
				dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight());
				// PrintDesignPanel.this.setSize(dimension);
				point.setLocation(PrintDesignPanel.this.getLocation().x + e.getX(),
						PrintDesignPanel.this.getLocation().y);
				break;
			case Direction.LEFT_DOWN:
				// System.out.println( e.getX());
				point.setLocation(PrintDesignPanel.this.getLocation().x + e.getX(),
						PrintDesignPanel.this.getLocation().y);
				dimension.setSize(dimension.getWidth() - e.getX(), e.getY());
				break;
			case Direction.RIGHT:
				dimension.setSize(e.getX(), dimension.getHeight());
				// PrintDesignPanel.this.setSize(dimension);

				break;
			case Direction.RIGHT_DOWN:
				point.setLocation(PrintDesignPanel.this.getLocation().x, PrintDesignPanel.this.getLocation().y);
				dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(), e.getY());

				break;
			case Direction.DOWN:
				dimension.setSize(dimension.getWidth(), e.getY());
				break;
		}
		if (maxWidth != 0 && maxWidth < dimension.getWidth()) {
			dimension.width = maxWidth;
		}
		if (minWidth != 0 && minWidth > dimension.getWidth()) {
			dimension.width = minWidth;
		}
		if (maxheight != 0 && maxheight < dimension.getWidth()) {
			dimension.height = maxheight;
		}
		if (minheight != 0 && minheight > dimension.getWidth()) {
			dimension.height = minheight;
		}
		// ��СΪ0��Ҫ��Ȼ�Ϳ�������
		dimension.setSize(dimension.getWidth() < SYS_MIN_WIDTH ? SYS_MIN_WIDTH : dimension.getWidth(),
				dimension.getHeight() < SYS_MIN_HEIGHT ? SYS_MIN_HEIGHT : dimension.getHeight());
		PrintDesignPanel.this.setSize(dimension);
		if (point.x < 0) {
			point.x = 0;
		}
		if (point.x >= this.getParent().getWidth() - 3) {
			point.x = this.getParent().getWidth() - 3;
		}
		if (point.y < 0) {
			point.y = 0;
		}
		if (point.y >= this.getParent().getHeight() - 3) {
			point.y = this.getParent().getHeight() - 3;
		}
		PrintDesignPanel.this.setLocation(point);
		int statrY = point.y + this.getHeight();
		for (int i = this.index + 1; i < linkedPanel.size(); i++) {
			// ������һ�����ĸ߶�
			PrintDesignPanel panel = linkedPanel.get(i);
			panel.setLocation(panel.getLocation().x, statrY);
			statrY = statrY + panel.getHeight();
		}
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

	public List<PrintItem> addItem(final PrintElementType printType, Point point) {
		List<PrintItem> list = new ArrayList<PrintItem>();
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

			if (!StringUtil.isEmpty(printType.getFieldName())) {
				cellRendere.setText(printType.getTableName() + "." + printType.getFieldName());
			} else {
				cellRendere.setText("ֵ" + table.getColumnCount());
			}

			TableColumnModel model = table.getColumnModel();
			model.addColumn(column);
			table.setColumnModel(model);

			list.add(cellRendere);
			list.add(haederRendere);
			// �ƶ������
			final JScrollBar jscrollBar = tableScrollPane.getHorizontalScrollBar();
			if (jscrollBar != null) {
				Thread thread = new Thread() {

					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							// ����50���ڸ���״̬
							this.sleep(100);
							jscrollBar.setValue(jscrollBar.getMaximum());
							Dimension size = table.getTableHeader().getPreferredSize();
							size.width = table.getWidth();
							table.getTableHeader().setPreferredSize(size);
							table.getTableHeader().revalidate();
							table.getTableHeader().repaint();
							if (!StringUtil.isEmpty(printType.getFieldName())) {
								table.getModel().setValueAt(printType.getTableName() + "." + printType.getFieldName(),
										0, table.getColumnCount() - 1);
							} else {
								table.getModel()
										.setValueAt("ֵ" + table.getColumnCount(), 0, table.getColumnCount() - 1);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				thread.start();
			}
		} else {
			PrintElementType type = new PrintElementType(PrintElementType.LABEL, printType.getText(), null);
			PrintElementItem printItem = PrintElementItem.createInstance(type, this);
			printItem.setUniqueId(max + 1);
			addDataSetRow(printItem);
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
			it.setFontName(printPage.getFontName());
			it.setFontSize(printPage.getFontSize());
			it.setWidth(100);
			it.setHeight(30);
		}
		return list;
	}

	private void addDataSetRow(PrintItem item) {
		StorageDataSet dataSet = printPage.getItemDataSet();
		dataSet.insertRow(false);
		dataSet.setBigDecimal("UNIQUE_ID", new BigDecimal(item.getUniqueId()));
		dataSet.setBigDecimal("INDEX", new BigDecimal(item.getIndex()));
		dataSet.setString("TYPE", PrintElementType.LABEL);
		dataSet.setBigDecimal("PANEL_ID", BigDecimal.valueOf(this.uniqueId));

		/*
		 * dataSet.setBigDecimal("X", Big); dataSet.setBigDecimal("Y",
		 * BigDecimal.valueOf(index + 1));
		 */
		// ���ø߶ȣ����Ϊ�ջ���Ϊ0.ȡ��Сֵ
		dataSet.setBigDecimal("HEIGHT", BigDecimal.valueOf(item.getHeight()));
		dataSet.setBigDecimal("WIDTH", BigDecimal.valueOf(item.getWidth()));

		// dataSet.setBigDecimal("FORECOLOR", );//ǰ��ɫ��������ɫ
		// dataSet.setBigDecimal("BACKCOLOR",);//����ɫ
		// dataSet.setBigDecimal("TEXT", );//��ʾ������
		dataSet.setString("FONT_NAME", printPage.getFontName());// ��������
		dataSet.setBigDecimal("FONT_SIZE", BigDecimal.valueOf(printPage.getFontSize()));// �����С
		dataSet.setString("ROTATION", "N");// ���
		dataSet.setString("BOLD", BoolStr.getString(printPage.isBold()));// �Ƿ�Ӵ�
		dataSet.setString("ITALIC", BoolStr.getString(printPage.isItalic()));// �Ƿ�б��
		dataSet.setString("UNDERLINE", BoolStr.getString(printPage.isUnderline()));// �»���
		dataSet.setString("STRIKETHROUGH", BoolStr.getString(printPage.isStrikethrough()));// �Ƿ��л���
		dataSet.setString("HORIZONTAL_ALIGNMENT", String.valueOf(printPage.getHorizontalAlignment()));// ˮƽ���뷽ʽ
		dataSet.setString("VERTICAL_ALIGNMENT", String.valueOf(printPage.getVerticalAlignment()));// ��ֱ���뷽ʽ
		// dataSet.setBigDecimal("WIDTH",);//��ת����
	}

	public List<PrintItem> addItem(PrintElementSource printSource, Point point) {
		List<PrintItem> list = new ArrayList<PrintItem>();
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
	public void copyItem(List<PrintItem> itemList) {
		for (PrintItem item : itemList) {

		}
	}
}
