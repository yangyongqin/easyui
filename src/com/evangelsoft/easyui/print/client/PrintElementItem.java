package com.evangelsoft.easyui.print.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.borland.dbswing.JdbTextField;
import com.borland.dx.dataset.DataSet;
import com.evangelsoft.easyui.print.type.LineDirection;
import com.evangelsoft.easyui.print.type.PrintDesignView;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.print.type.PrintItemTool;
import com.evangelsoft.easyui.template.client.nc.StringUtil;
import com.evangelsoft.easyui.tool.SwingUtils;
import com.evangelsoft.workbench.types.BoolStr;

public class PrintElementItem extends JLabel implements Serializable, MouseMotionListener, PrintItem<PrintElementItem> {

	/**
	 *
	 */
	private static final long serialVersionUID = 0L;

	/**
	 * @Fields uniqueId : 唯一id
	 */
	private Integer uniqueId;

	/**
	 * @Fields index : 下标
	 */
	private int index;

	/**
	 * @Fields relationId : 关联的唯一表示ID
	 */
	private int relationId;

	/**
	 * 类型， 0图表,1图片,2表格,3线条,4静态文本,5值控件,6表头,7表格
	 */
	private String type;

	private String text;

	// private JComponent comp;
	private double realWidth;

	private double realHeight;

	private double realX;

	private double realY;

	private int fontSize;

	private String fontName;

	private boolean isitalic;

	private boolean isBold;

	private boolean isUnderline;

	private boolean isstrikethrough;

	private String elementHorizontalAlignment;

	private String elementVerticalAlignment;

	private int elementWidth;

	private int elementHeight;

	private String rotation;

	private String foreColor, backColor;

	/**
	 * @Fields lineDirection : 线方向
	 */
	private String lineDirection;

	private int lineSize = 1;

	/**
	 * @Fields ratio : 缩放比率，默认为1
	 */
	private double ratio = 1;

	/**
	 * 修改的数据
	 */
	private DataSet dataSet;

	/**
	 * 编辑时显示的文本框
	 */
	private JTextField editText;

	private int direction;

	private Point draggingAnchor = null;

	/**
	 * 多选的时候批量执行移动
	 */

	// private static ZdyBorder zdyBorder=new ZdyBorder();
	// 不限制常量
	public final static int notLimited = 0;

	/**
	 * 最低宽度，太小显示会看不到
	 */
	public final static int SYS_MIN_WIDTH = 3;

	/**
	 * 最低高度，太小显示会看不到
	 */
	public final static int SYS_MIN_HEIGHT = 3;

	// 0是不管理高度宽度
	private int maxWidth = 0;

	private int maxheight = 0;

	private int minWidth = 0;

	private int minheight = 0;

	private PrintDesignView parentPanel;

	// private PrintDesignView designPanel;

	private PrintPage printPage;

	private JdbTextField dbText;

	JLayeredPane layeredPane;

	public PrintElementItem(String type, PrintDesignView panel) {
		this.dataSet = panel.getPrintPage().getItemDataSet();
		this.printPage = panel.getPrintPage();
		this.parentPanel = panel;
		this.type = type;
		this.parentPanel = panel;
		this.addMouseMotionListener(this);
	}

	public static PrintElementItem createInstance(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = null;
		if (PrintElementType.CHART == type.getType()) {
			item = createChart(type, panel);
		} else if (PrintElementType.IMAGE == type.getType()) {
			item = createImage(type, panel);
		} else if (PrintElementType.TABLE == type.getType()) {
			item = createLabel(type, panel);
		} else if (PrintElementType.LINE == type.getType()) {
			item = createLine(type, panel);
		} else if (PrintElementType.LABEL == type.getType()) {
			item = createLabel(type, panel, type.getDesc());
		} else if (PrintElementType.TEXT == type.getType()) {
			item = createText(type, panel, type.getDesc());
		}
		item.addMouseMotionListener(item);
		return item;
	}

	public PrintElementItem(PrintElementType type, PrintDesignView panel, boolean isAdd) {
		this.dataSet = panel.getPrintPage().getItemDataSet();
		this.printPage = panel.getPrintPage();
		this.parentPanel = panel;
		this.type = type.getType();
		this.parentPanel = panel;
	}

	public PrintElementItem(PrintElementType type, PrintDesignView panel, String str, boolean isAdd) {
		this(type, panel, isAdd);
		this.setText(str);
	}

	public static PrintElementItem createLabel(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JLabel();
		return item;
	}

	public static PrintElementItem createText(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JTextField();
		return item;
	}

	public static PrintElementItem createLabel(PrintElementType type, PrintDesignView panel, String str) {
		PrintElementItem item = new PrintElementItem(type, panel, str, true);
		// item.comp = new JLabel(str);
		return item;
	}

	public static PrintElementItem createText(PrintElementType type, PrintDesignView panel, String text) {
		PrintElementItem item = new PrintElementItem(type, panel, text, true);
		// item.comp = new JTextField(text);
		return item;
	}

	public static PrintElementItem createLine(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JLabel();
		return item;
	}

	public static PrintElementItem createTable(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JTable();
		return item;
	}

	public static PrintElementItem createChart(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JPanel();
		return item;
	}

	public static PrintElementItem createImage(PrintElementType type, PrintDesignView panel) {
		PrintElementItem item = new PrintElementItem(type, panel, true);
		// item.comp = new JPanel();
		return item;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != null && text != this.text && !text.equals(this.text)) {
			this.text = text;
			super.setText(text);
			if (this.dataSet != null) {
				if (toRow() > -1) {
					this.getDataSet().setString("TEXT", text);
				}
			}
		}
	}

	public JTextField getEditText() {
		return editText;
	}

	public void setEditText(JTextField editText) {
		this.editText = editText;
	}

	// public PrintDesignView getDesignPanel() {
	// return designPanel;
	// }
	//
	// public void setDesignPanel(PrintDesignView designPanel) {
	// this.designPanel = designPanel;
	// }

	public PrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(PrintPage printPage) {
		this.printPage = printPage;
	}

	public void mouseMoved(MouseEvent e) {
		draggingAnchor = new Point(e.getX(), e.getY());
		if (e.getPoint().getY() <= 3 && e.getPoint().getX() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			direction = Direction.LEFT_UP;
		} else if (e.getPoint().getY() <= 3 && this.getWidth() - e.getPoint().getX() <= 10) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			direction = Direction.RIGHT_UP;
		} else if (this.getHeight() - e.getPoint().getY() <= 3 && e.getPoint().getX() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			direction = Direction.LEFT_DOWN;
		} else if (this.getHeight() - e.getPoint().getY() <= 3 && this.getWidth() - e.getPoint().getX() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			direction = Direction.RIGHT_DOWN;
		} else if (e.getPoint().getY() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			direction = Direction.UP;
		} else if (this.getSize().getHeight() - e.getPoint().getY() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			direction = Direction.DOWN;
		} else if (e.getPoint().getX() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			direction = Direction.LEFT;
		} else if (this.getSize().getWidth() - e.getPoint().getX() <= 3) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			direction = Direction.RIGHT;
		} else {
			// 设置为默认
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			direction = 0;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		// boolean isMove = false;
		// 如果是缩小
		Point point = e.getPoint();

		Container parent = this.getParent();
		if (printPage.getSelectList().size() > 0) {

			// for(int i=0;i<PrintDesignFrame.selectList.size();i++){
			// PrintElementItem item=PrintDesignFrame.selectList.get
			for (PrintItem<?> item : printPage.getSelectList()) {
				Dimension dimension = item.getSize();
				switch (direction) {
					case Direction.LEFT_UP:
						dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight() - e.getY());
						point.setLocation(item.getLocation().x + e.getX(), item.getLocation().y + e.getY());
						break;
					case Direction.RIGHT_UP:
						dimension.setSize(e.getX() - this.getWidth() + item.getElementWidth(), dimension.getHeight()
								- e.getY());
						// this.setSize(dimension);
						point.setLocation(item.getLocation().x, item.getLocation().y + e.getY());
						break;
					case Direction.UP:
						dimension.setSize(dimension.getWidth(), dimension.getHeight() - e.getY());
						// this.setSize(dimension);
						point.setLocation(item.getLocation().x, item.getLocation().y + e.getY());
						break;
					case Direction.LEFT:
						dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight());
						// this.setSize(dimension);
						point.setLocation(item.getLocation().x + e.getX(), item.getLocation().y);
						break;
					case Direction.LEFT_DOWN:
						point.setLocation(item.getLocation().x + e.getX(), item.getLocation().y);
						dimension.setSize(dimension.getWidth() - e.getX(),
								e.getY() - this.getHeight() + item.getElementHeight());
						break;
					case Direction.RIGHT:
						point.setLocation(item.getLocation().x, item.getLocation().y);
						dimension.setSize(e.getX() - this.getWidth() + item.getElementWidth(), dimension.getHeight());

						break;
					case Direction.RIGHT_DOWN:
						point.setLocation(item.getLocation().x, item.getLocation().y);
						dimension.setSize(e.getX() - this.getWidth() + item.getElementWidth(),
								e.getY() - this.getHeight() + item.getElementHeight());

						break;
					case Direction.DOWN:
						point.setLocation(item.getLocation().x, item.getLocation().y);
						dimension.setSize(dimension.getWidth(), e.getY() - this.getHeight() + item.getElementHeight());
						break;
					default:
						// isMove = true;
						point.setLocation(e.getPoint().x + item.getLocation().x - draggingAnchor.x, e.getPoint().y
								+ item.getLocation().y - draggingAnchor.y);
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
				// 最小为0，要不然就看不到了
				dimension.setSize(dimension.getWidth() < SYS_MIN_WIDTH ? SYS_MIN_WIDTH : dimension.getWidth(),
						dimension.getHeight() < SYS_MIN_HEIGHT ? SYS_MIN_HEIGHT : dimension.getHeight());
				// 不允许超过父类大小
				dimension.setSize(dimension.getWidth() > item.getParent().getWidth() ? item.getParent().getWidth() - 2
						: dimension.getWidth(), dimension.getHeight() > item.getParent().getHeight() ? item.getParent()
						.getHeight() - 2 : dimension.getHeight());

				item.setSize(dimension);

				if (point.x < 1) {
					point.x = 1;
				}
				if (point.y < 1) {
					point.y = 1;
				}
				if (point.x + dimension.getWidth() > parent.getWidth()) {
					point.x = (int) (parent.getWidth() - dimension.getWidth() - 1 + 0.5);
				}
				if (point.y + dimension.getHeight() > parent.getHeight()) {
					point.y = (int) (parent.getHeight() - dimension.getHeight() - 1 + 0.5);
				}
				item.setLocation(point);

			}
		} else {
			Dimension dimension = this.getSize();
			switch (direction) {
				case Direction.LEFT_UP:
					dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight() - e.getY());
					point.setLocation(this.getLocation().x + e.getX(), this.getLocation().y + e.getY());
					break;
				case Direction.RIGHT_UP:
					dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(),
							dimension.getHeight() - e.getY());
					// this.setSize(dimension);
					point.setLocation(this.getLocation().x, this.getLocation().y + e.getY());
					break;
				case Direction.UP:
					dimension.setSize(dimension.getWidth(), dimension.getHeight() - e.getY());
					// this.setSize(dimension);
					point.setLocation(this.getLocation().x, this.getLocation().y + e.getY());
					break;
				case Direction.LEFT:
					dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight());
					// this.setSize(dimension);
					point.setLocation(this.getLocation().x + e.getX(), this.getLocation().y);
					break;
				case Direction.LEFT_DOWN:
					point.setLocation(this.getLocation().x + e.getX(), this.getLocation().y);
					dimension.setSize(dimension.getWidth() - e.getX(), e.getY());
					break;
				case Direction.RIGHT:
					point.setLocation(this.getLocation().x, this.getLocation().y);
					dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(), dimension.getHeight());

					break;
				case Direction.RIGHT_DOWN:
					point.setLocation(this.getLocation().x, this.getLocation().y);
					dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(), e.getY());

					break;
				case Direction.DOWN:
					// dimension.setSize(dimension.getWidth(), e.getY());
					dimension.setSize(dimension.getWidth(), dimension.getHeight() + e.getY());
					break;
				default:
					// isMove = true;
					point.setLocation(e.getPoint().x + this.getLocation().x - draggingAnchor.x,
							e.getPoint().y + this.getLocation().y - draggingAnchor.y);
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
			// 最小为0，要不然就看不到了
			dimension.setSize(dimension.getWidth() < SYS_MIN_WIDTH ? SYS_MIN_WIDTH : dimension.getWidth(),
					dimension.getHeight() < SYS_MIN_HEIGHT ? SYS_MIN_HEIGHT : dimension.getHeight());
			// 不允许超过父类大小
			dimension.setSize(dimension.getWidth() > this.getParent().getWidth() ? this.getParent().getWidth() - 2
					: dimension.getWidth(), dimension.getHeight() > this.getParent().getHeight() ? this.getParent()
					.getHeight() - 2 : dimension.getHeight());

			this.setSize(dimension);

			if (point.x < 1) {
				point.x = 1;
			}
			if (point.y < 1) {
				point.y = 1;
			}
			if (point.x + dimension.getWidth() > parent.getWidth()) {
				point.x = (int) (parent.getWidth() - dimension.getWidth() - 1 + 0.5);
			}
			if (point.y + dimension.getHeight() > parent.getHeight()) {
				point.y = (int) (parent.getHeight() - dimension.getHeight() - 1 + 0.5);
			}
			this.setLocation(point);
		}
	}

	public double getRealWidth() {
		return realWidth;
	}

	public void setRealWidth(double realWidth) {
		this.realWidth = realWidth;
	}

	public double getRealHeight() {
		return realHeight;
	}

	public void setRealHeight(double realHeight) {
		this.realHeight = realHeight;
	}

	public double getRealX() {
		return realX;
	}

	public void setRealX(double realX) {
		this.realX = realX;
	}

	public double getRealY() {
		return realY;
	}

	public void setRealY(double realY) {
		this.realY = realY;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		if (toRow() > -1) {
			if (fontSize == this.getDataSet().getBigDecimal("FONT_SIZE").intValue()) {
				this.getDataSet().setBigDecimal("FONT_SIZE", BigDecimal.valueOf(fontSize));
			}
			this.updateFont();
		}
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxheight() {
		return maxheight;
	}

	public void setMaxheight(int maxheight) {
		this.maxheight = maxheight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinheight() {
		return minheight;
	}

	public void setMinheight(int minheight) {
		this.minheight = minheight;
	}

	public int getUniqueId() {
		return uniqueId == null ? 0 : uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		if (this.uniqueId == null) {
			this.uniqueId = uniqueId;

			if (toRow() > -1) {
				this.getDataSet().setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(uniqueId));
			}
		}
	}

	@Override
	public void setFont(Font font) {
		this.fontSize = (int) (font.getSize() / ratio + 0.5);
		super.setFont(font);
	}

	@Override
	public void setSize(int width, int height) {
		this.realWidth = width / ratio;
		this.realHeight = height / ratio;
		super.setSize(width, height);
	}

	@Override
	public void setSize(Dimension d) {
		this.realWidth = d.width / ratio;
		this.realHeight = d.height / ratio;
		super.setSize(d);
	}

	public void setNewBounds(double x, double y, double width, double height) {
		this.realWidth = width / ratio;
		this.realHeight = height / ratio;
		this.realX = x / ratio;
		this.realY = y / ratio;
		super.setBounds((int) (x + 0.5), (int) (y + 0.5), (int) (width + 0.5), (int) (height + 0.5));
	}

	public void setBounds(int x, int y, int width, int height) {
		this.realWidth = width / ratio;
		this.realHeight = height / ratio;
		this.realX = x / ratio;
		this.realY = y / ratio;
		super.setBounds((int) (x + 0.5), (int) (y), (int) (width), (int) (height));
		this.dataSet.setBigDecimal("WIDTH", BigDecimal.valueOf(realWidth));
		this.dataSet.setBigDecimal("HEIGHT", BigDecimal.valueOf(realHeight));
		this.dataSet.setBigDecimal("X", BigDecimal.valueOf(realX));
		this.dataSet.setBigDecimal("Y", BigDecimal.valueOf(realY));
	}

	@Override
	public void setLocation(int x, int y) {
		this.realX = x / ratio;
		this.realY = y / ratio;
		super.setLocation(x, y);
		this.dataSet.setBigDecimal("X", BigDecimal.valueOf(realX));
		this.dataSet.setBigDecimal("Y", BigDecimal.valueOf(realY));
	}

	@Override
	public void setLocation(Point p) {
		this.realX = p.x / ratio;
		this.realY = p.y / ratio;
		this.dataSet.setBigDecimal("X", BigDecimal.valueOf(realX));
		this.dataSet.setBigDecimal("Y", BigDecimal.valueOf(realY));
		super.setLocation(p);
	}

	public Point getRealLocation() {
		Point pont = new Point((int) (this.realX + 0.5), (int) (this.realY + 0.5));
		return pont;
	}

	@Override
	public void setBounds(Rectangle r) {
		this.realWidth = r.width / ratio;
		this.realHeight = r.height / ratio;
		this.realX = r.getX() / ratio;
		this.realY = r.getY() / ratio;
		this.dataSet.setBigDecimal("WIDTH", BigDecimal.valueOf(realWidth));
		this.dataSet.setBigDecimal("HEIGHT", BigDecimal.valueOf(realHeight));
		this.dataSet.setBigDecimal("X", BigDecimal.valueOf(realX));
		this.dataSet.setBigDecimal("Y", BigDecimal.valueOf(realY));
		super.setBounds(r);
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public Rectangle getRealBounds() {
		return new Rectangle((int) (this.realX + 0.5), (int) (this.realY + 0.5), (int) (this.realWidth + 0.5),
				(int) (this.realHeight + 0.5));
	}

	@Transient
	public Dimension getRealSize() {
		return new Dimension((int) (realWidth + 0.5), (int) (realHeight));
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
		this.updateFont();
		if (toRow() > -1) {
			if (fontName.equals(this.getDataSet().getString("FONT_NAME"))) {
				this.getDataSet().setString("FONT_NAME", fontName);
			}
		}
	}

	public boolean getIsitalic() {
		return isitalic;
	}

	public void setIsitalic(boolean isitalic) {
		this.isitalic = isitalic;
		this.updateFont();
		if (toRow() > -1) {
			if (isitalic == BoolStr.getBoolean(this.getDataSet().getString("ITALIC"))) {
				this.getDataSet().setString("ITALIC", BoolStr.getString(isitalic));
			}
		}
	}

	public boolean getIsBold() {
		return isBold;
	}

	public void setIsBold(boolean isBold) {
		this.isBold = isBold;
		this.updateFont();
		if (toRow() > -1) {
			if (isBold == BoolStr.getBoolean(this.getDataSet().getString("BOLD"))) {
				this.getDataSet().setString("BOLD", BoolStr.getString(isBold));
			}
		}
	}

	public boolean getIsUnderline() {
		return isUnderline;
	}

	public void setIsUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
		this.updateFont();
		if (toRow() > -1) {
			if (isUnderline == BoolStr.getBoolean(this.getDataSet().getString("UNDERLINE"))) {
				this.getDataSet().setString("UNDERLINE", BoolStr.getString(isUnderline));
			}
		}
	}

	public boolean getIsstrikethrough() {
		return isstrikethrough;
	}

	public void setIsstrikethrough(boolean isstrikethrough) {
		this.isstrikethrough = isstrikethrough;
		this.updateFont();
		if (toRow() > -1) {
			if (isstrikethrough == BoolStr.getBoolean(this.getDataSet().getString("STRIKETHROUGH"))) {
				this.getDataSet().setString("STRIKETHROUGH", BoolStr.getString(isstrikethrough));
			}
		}
	}

	public String getElementHorizontalAlignment() {
		return elementHorizontalAlignment;
	}

	public void setElementHorizontalAlignment(String elementHorizontalAlignment) {
		this.elementHorizontalAlignment = elementHorizontalAlignment;
		this.setHorizontalAlignment(Integer.parseInt(elementHorizontalAlignment));
	}

	public String getElementVerticalAlignment() {
		if (StringUtil.isEmpty(elementVerticalAlignment)) {
			elementVerticalAlignment = JLabel.CENTER + "";
		}
		return elementVerticalAlignment;
	}

	public void setElementVerticalAlignment(String elementVerticalAlignment) {
		this.elementVerticalAlignment = elementVerticalAlignment;
		// 改变当前显示
		this.setVerticalAlignment(Integer.parseInt(elementVerticalAlignment));
		// 保存到数据表格
		if (elementVerticalAlignment.equals(this.getDataSet().getString("VERTICAL_ALIGNMENT"))) {
			this.getDataSet().setString("VERTICAL_ALIGNMENT", elementVerticalAlignment);
		}
	}

	public int getElementWidth() {
		return this.getWidth();
	}

	public void setElementWidth(int elementWidth) {
		if (toRow() > -1) {
			this.elementWidth = elementWidth;
			if (this.getDataSet().getBigDecimal("WIDTH").intValue() == elementWidth) {
				this.getDataSet().setBigDecimal("WIDTH", BigDecimal.valueOf(elementWidth));
			}
		}
	}

	public int getElementHeight() {
		return this.getHeight();
	}

	public void setElementHeight(int elementHeight) {
		if (toRow() > -1) {
			this.elementHeight = elementHeight;
			if (this.getDataSet().getBigDecimal("HEIGHT").intValue() == elementHeight) {
				this.getDataSet().setBigDecimal("HEIGHT", BigDecimal.valueOf(elementHeight));
			}
		}
	}

	public void delete() {
		// 将当前对象从表格中删除
		if (toRow() > -1) {
			dataSet.deleteRow();
			// 删除当前选择对象
			this.getParentPanel().remove(this);
			this.getParentPanel().repaint();
		}

	}

	// 定位到指定的行
	public int toRow() {
		// 如果当前行不在
		if (dataSet != null) {
			if (dataSet.getBigDecimal("UNIQUE_ID").intValue() == this.getUniqueId()) {
				return dataSet.getRow();
			}
			dataSet.first();
			for (int i = 0; i < dataSet.rowCount(); i++) {
				if (dataSet.getBigDecimal("UNIQUE_ID").intValue() == this.getUniqueId()) {
					return i;
				}
				dataSet.next();
			}
		}
		return -1;
	}

	public void updateFont() {
		int style = 0;
		if (this.getIsitalic()) {
			style += Font.ITALIC;
		}
		if (this.getIsitalic()) {
			style += Font.BOLD;
		}
		Font vFont = new Font(this.getFontName(), style, this.fontSize);
		Map<TextAttribute, Object> map = null;
		// 如果包含下划线
		if (this.getIsUnderline()) {
			map = new HashMap<TextAttribute, Object>();
			map.put(TextAttribute.FONT, vFont);// 原字体
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);// 下划线
			/*
			 * c.setFont(Font.getFont(map));//设置新字体
			 */}
		// 如果包含中划线,删除线
		if (this.getIsstrikethrough()) {
			if (map == null) {
				map = new HashMap<TextAttribute, Object>();
			}
			map.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);// 中划线删除线
		}
		if (map != null) {
			vFont = Font.getFont(map);
		}
		this.setFont(vFont);
	}

	@Override
	public void setWidth(int width) {
	}

	@Override
	public void setHeight(int height) {
	}

	@Override
	public double setZoomRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getZoomRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBorder(Border border) {
		super.setBorder(border);
	}

	public PrintDesignView getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(PrintDesignView parentPanel) {
		this.parentPanel = parentPanel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		if (toRow() > -1) {
			this.getDataSet().setBigDecimal("ELEMENT_INDEX", BigDecimal.valueOf(index));
		}
	}

	public int getRelationId() {
		return relationId;
	}

	public void setRelationId(int relationId) {
		this.relationId = relationId;
		if (toRow() > -1) {
			this.getDataSet().setBigDecimal("RELATION_ID", BigDecimal.valueOf(relationId));
		}
	}

	@Override
	public void showEdit() {
		this.setVisibleEdit(true);
	}

	@Override
	public void hideEdit() {
		this.setVisibleEdit(false);
	}

	@Override
	public void setVisibleEdit(boolean aFlag) {
		if (layeredPane == null) {
			dbText = new JdbTextField();
			dbText.setDataSet(this.dataSet);
			// dbText.setText("TEXT");
			dbText.setColumnName("TEXT");
			layeredPane = this.getPrintPage().getPrintDesignFrame().getLayeredPane();

			layeredPane.add(dbText, 10, 0);
			// JEditorPane jep1 = new JEditorPane();
			// jep1.setBackground(Color.yellow);
			// jep1.setBounds(0, 0, 300, 300);
			// this.getPrintPage().getPrintDesignFrame().getLayeredPane().add(jep1,
			// 10, 1);
		}
		if (aFlag) {
			// 每次都必须重新计算显示的坐标
			Point pointFrame = SwingUtils.getMousePoint(this.getPrintPage().getPrintDesignFrame());
			// if (point == null) {
			Point point = SwingUtils.getMousePoint(this);
			// }

			// Point point222=SwingUtils.getRelativeLocation(this,
			// this.getPrintPage().getPrintDesignFrame());
			dbText.setBounds(pointFrame.x - point.x - 10, pointFrame.y - point.y - 10, 200, 100);
		}
		dbText.setVisible(aFlag);

	}

	@Override
	public void setValue(String columnName, Object value) {
		PrintItemTool.setValue(this, columnName, value);
	}

	@Override
	public PrintElementItem clone() {
		return null;
	}

	public String getRotation() {
		return rotation;
	}

	public void setRotation(String rotation) {
		if (toRow() > -1) {
			// 不相等才赋值
			if (StringUtil.isEmpty(this.rotation) || !rotation.equals(rotation)) {
				this.rotation = rotation;
				this.getDataSet().setString("ROTATION", rotation);
			}
		}
	}

	/**
	 * 重写绘制方法，主要是线条的显示
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// 如果是线条，
		if (PrintElementType.LINE.equals(type)) {

			// 判断线条方向
			Graphics2D g2d = (Graphics2D) g;
			// 添加抗锯齿效果
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(this.lineSize));
			if ("R".equals(this.lineDirection)) {
				g.drawLine(0, this.getHeight(), this.getWidth(), 0);
			} else {
				g.drawLine(0, 0, this.getWidth(), this.getHeight());
			}
			// 关闭抗齿距效果
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		} else {
			super.paintComponent(g);
		}
	}

	@Override
	public void setForeColor(String colorStr) {
		if (toRow() > -1) {
			if (StringUtil.isEmpty(colorStr)) {
				return;
			}
			// 不相等才赋值
			if (StringUtil.isEmpty(this.foreColor) || !foreColor.equals(colorStr)) {
				String temp = colorStr.replaceAll("\\#", "");
				int alpha = 00;
				if (temp.indexOf(",") > 0) {
					alpha = Integer.parseInt(temp.substring(0, 2), 16);
					temp = temp.substring(temp.indexOf(",") + 1);
				}
				Color color = new Color(Integer.parseInt(temp.substring(0, 2), 16), Integer.parseInt(
						temp.substring(2, 4), 16), Integer.parseInt(temp.substring(4), 16), alpha);
				this.setForeground(color);
				this.foreColor = colorStr;
				this.getDataSet().getColumn("FORECOLOR").setForeground(color);
			}
		}
	}

	@Override
	public void setBackColor(String colorStr) {
		if (toRow() > -1) {
			if (StringUtil.isEmpty(colorStr)) {
				return;
			}
			// 不相等才赋值
			if (StringUtil.isEmpty(this.backColor) || !rotation.equals(colorStr)) {
				String temp = colorStr.replaceAll("\\#", "");
				int alpha = 00;
				if (temp.indexOf(",") > 0) {
					alpha = Integer.parseInt(temp.substring(0, 2), 16);
					temp = temp.substring(temp.indexOf(",") + 1);
				}
				Color color = new Color(Integer.parseInt(temp.substring(0, 2), 16), Integer.parseInt(
						temp.substring(2, 4), 16), Integer.parseInt(temp.substring(4), 16), alpha);
				this.setBackground(color);
				this.backColor = colorStr;
				this.getDataSet().getColumn("BACKCOLOR").setForeground(color);
			}
		}
	}

	@Override
	public void setLineSize(int lineSize) {
		if (toRow() > -1) {
			// 不相等才赋值
			if (lineSize != this.lineSize) {
				this.lineSize = lineSize;
				this.getDataSet().setBigDecimal("LINE_SIZE", BigDecimal.valueOf(lineSize));
			}
		}
	}

	@Override
	public void setLineDirection(String lineDirection) {
		if (toRow() > -1) {
			// 不相等才赋值
			if (StringUtil.isEmpty(this.lineDirection) || !this.lineDirection.equals(lineDirection)) {
				this.lineDirection = lineDirection;
				this.getDataSet().setString("LINE_DIRECTION", lineDirection);
				this.repaint();
			}
		}
	}

	@Override
	public String getForeColor() {
		if (StringUtil.isEmpty(this.foreColor)) {
			Color color = this.getForeground();
			int alpha = color.getAlpha();
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			return String.format("%02x", alpha) + "," + String.format("#%02x%02x%02x", red, green, blue);
		}
		return this.foreColor;
	}

	@Override
	public String getBackColor() {
		if (StringUtil.isEmpty(this.backColor)) {
			Color color = this.getBackground();
			int alpha = color.getAlpha();
			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			return String.format("%02x", alpha) + "," + String.format("#%02x%02x%02x", red, green, blue);
		}
		return this.foreColor;
	}
}
