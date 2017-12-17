package com.evangelsoft.easyui.print.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.alee.utils.SwingUtils;
import com.borland.dbswing.JdbTextField;
import com.borland.dx.dataset.DataSet;
import com.evangelsoft.easyui.print.type.PrintItem;
import com.evangelsoft.easyui.print.type.PrintItemTool;
import com.evangelsoft.workbench.types.BoolStr;

public class PrintTableCellHeaderRenderer extends DefaultTableCellRenderer implements PrintItem<PrintTableCellHeaderRenderer> {

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	public static javax.swing.border.Border defaultBorder = new LineBorder(Color.BLACK, 1);

	private javax.swing.border.Border border = null;

	private TableColumn printTableColumn;

	/**
	 * @Fields uniqueId : 唯一id
	 */
	private int uniqueId;

	/**
	 * @Fields relationId : 关联的唯一表示ID
	 */
	private int relationId;

	/**
	 * @Fields index : 下标
	 */
	private int index;

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

	private PrintDesignPanel parentPanel;

	private PrintDesignPanel designPanel;

	private PrintPage printPage;

	private int width;

	private int height;

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

	private JdbTextField dbText;

	JLayeredPane layeredPane;

	PrintTableCellHeaderRenderer(PrintDesignPanel parentPanel, TableColumn printTableColumn, DataSet dataSet) {
		this.dataSet = dataSet;
		this.printTableColumn = printTableColumn;
		this.parentPanel = parentPanel;
		this.printPage = parentPanel.getPrintPage();

	}

	public javax.swing.border.Border getDefaultBorder() {
		return defaultBorder;
	}

	public void setDefaultBorder(javax.swing.border.Border newDefaultBorder) {
		defaultBorder = newDefaultBorder;
	}

	private JTable table = null;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (table != null) {
			this.table = table;
			// JTableHeader header = table.getTableHeader();
			// if (header != null) {
			// defaultForeground = header.getForeground();
			// defaultBackground = header.getBackground();
			// setForeground((foreground != null) ? foreground :
			// defaultForeground);
			// setBackground((background != null) ? background :
			// defaultBackground);
			// setFont(header.getFont());
			// }
		}

		if (this.getBorder() != null)
			setBorder(border);
		else
			setBorder(defaultBorder);

		// setHorizontalAlignment(alighment);

		setText((value == null) ? "" : value.toString());

		// 判断字体宽度决定是否现实tooltip
		// Font defaultfont = javax.swing.plaf.FontUIResource.
		Graphics g = table.getGraphics();
		if (g != null) {
			FontMetrics fontm = g.getFontMetrics();
			int textWidth = fontm.stringWidth(getText());
			int columnWidth = table.getColumnModel().getColumn(column).getWidth();
			if (textWidth > columnWidth)
				setToolTipText(getText());
			else
				setToolTipText(null);
		}
		setPreferredSize(new Dimension(getWidth(), 21));

		return this;
	}

	public void setBorder(javax.swing.border.Border newBorder) {
		border = newBorder;
		if (border != null)
			super.setBorder(border);
		else
			super.setBorder(defaultBorder);
		// 由父类重新绘制
		if (this.table != null) {
			this.table.getTableHeader().repaint();
			this.table.repaint();
		}

	}

	public javax.swing.border.Border getBorder() {
		return border;
	}

	public TableColumn getPrintTableColumn() {
		return printTableColumn;
	}

	public void setPrintTableColumn(TableColumn printTableColumn) {
		this.printTableColumn = printTableColumn;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
		if (toRow() > -1) {
			this.getDataSet().setBigDecimal("UNIQUE_ID", BigDecimal.valueOf(uniqueId));
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if (toRow() > -1) {
			this.getDataSet().setString("TYPE", type);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != this.text && !text.equals(this.text)) {
			this.text = text;
			// 如果当前是表头
			if (PrintElementType.TABLE_HEAD.equals(type)) {
				this.printTableColumn.setHeaderValue(text);
			} else {
				if (this.getTable() != null) {
					this.getTable().setValueAt(text, 0, this.printTableColumn.getModelIndex());
				}
			}
			// column.setHeaderValue
			if (this.dataSet != null) {
				if (toRow() > -1) {
					this.getDataSet().setString("TEXT", text);
				}
			}
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

	public boolean isIsitalic() {
		return isitalic;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
		this.updateFont();
		this.getDataSet().setString("BOLD", BoolStr.getString(isBold));
	}

	public boolean isUnderline() {
		return isUnderline;
	}

	public void setUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
		this.updateFont();
		this.getDataSet().setString("UNDERLINE", BoolStr.getString(isUnderline));
	}

	public boolean isIsstrikethrough() {
		return isstrikethrough;
	}

	public int getElementWidth() {
		return elementWidth;
	}

	public void setElementWidth(int elementWidth) {
		this.elementWidth = elementWidth;
	}

	public int getElementHeight() {
		return elementHeight;
	}

	public void setElementHeight(int elementHeight) {
		this.elementHeight = elementHeight;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public JTextField getEditText() {
		return editText;
	}

	public void setEditText(JTextField editText) {
		this.editText = editText;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public PrintDesignPanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(PrintDesignPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public PrintDesignPanel getDesignPanel() {
		return designPanel;
	}

	public void setDesignPanel(PrintDesignPanel designPanel) {
		this.designPanel = designPanel;
	}

	public PrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(PrintPage printPage) {
		this.printPage = printPage;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
		if (toRow() > -1) {
			this.setPreferredSize(new Dimension(width, this.getHeight()));
		}
	}

	@Override
	public void setHeight(int height) {
		if (height != this.height) {
			this.height = height;
			if (toRow() > -1) {
				if (this.getTable() != null) {
					// 如果当前是表头
					if (PrintElementType.TABLE_HEAD.equals(type)) {
						Dimension size = this.getTable().getTableHeader().getPreferredSize();
						size.height = height;
						this.getTable().getTableHeader().setPreferredSize(size);
						// hendrender.setHeight(point.y);
						// 被拖动的单元格也设置为同样宽度
						this.getTable().getTableHeader().revalidate();
						this.getTable().getTableHeader().repaint();
					} else {
						table.setRowHeight(height);
					}
				}
			}
		}
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
	public void delete() {
		// 将当前对象从表格中删除
		if (toRow() > -1) {
			dataSet.deleteRow();
			// 然后删除关联的数据集
			toRow(this.relationId);
			dataSet.deleteRow();
			// TODO 删除当前选择列
			// this.getTable().
			this.getTable().getColumnModel().removeColumn(this.printTableColumn);
			// 销毁对象
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
		this.updateFont();
		if (toRow() > -1) {
			if (!fontName.equals(this.getDataSet().getString("FONT_NAME"))) {
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
			if (isitalic != BoolStr.getBoolean(this.getDataSet().getString("ITALIC"))) {
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
		this.getDataSet().setString("BOLD", BoolStr.getString(isBold));
	}

	public boolean getIsUnderline() {
		return isUnderline;
	}

	public void setIsUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
		this.updateFont();
		this.getDataSet().setString("UNDERLINE", BoolStr.getString(isUnderline));
	}

	public boolean getIsstrikethrough() {
		return isstrikethrough;
	}

	public void setIsstrikethrough(boolean isstrikethrough) {
		if (this.isstrikethrough != isstrikethrough) {
			this.isstrikethrough = isstrikethrough;
			this.updateFont();
			this.getDataSet().setString("STRIKETHROUGH", BoolStr.getString(isstrikethrough));
		}
	}

	public String getElementHorizontalAlignment() {
		return elementHorizontalAlignment;
	}

	public void setElementHorizontalAlignment(String elementHorizontalAlignment) {
		// 不相等才赋值
		if (!elementHorizontalAlignment.equals(this.elementHorizontalAlignment)) {
			this.elementHorizontalAlignment = elementHorizontalAlignment;
			this.setHorizontalAlignment(Integer.parseInt(elementHorizontalAlignment));
		}
	}

	public String getElementVerticalAlignment() {
		return elementVerticalAlignment;
	}

	public void setElementVerticalAlignment(String elementVerticalAlignment) {
		this.elementVerticalAlignment = elementVerticalAlignment;
		// 改变当前显示
		this.setVerticalAlignment(Integer.parseInt(elementVerticalAlignment));
		// 保存到数据表格
		if (!elementVerticalAlignment.equals(this.getDataSet().getString("VERTICAL_ALIGNMENT"))) {
			this.getDataSet().setString("VERTICAL_ALIGNMENT", elementVerticalAlignment);
		}
	}

	public void updateFont() {
		int style = 0;
		if (this.getIsitalic()) {
			style += Font.ITALIC;
		}
		if (this.getIsitalic()) {
			style += Font.BOLD;
		}
		if (this.fontSize <= 0) {
			return;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		if (toRow() > -1) {
			this.getDataSet().setBigDecimal("INDEX", BigDecimal.valueOf(index));
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

	// 定位到指定的行
	public int toRow() {
		// 如果当前行不在
		return this.toRow(this.getUniqueId());
	}

	public int toRow(int uniqueId) {
		// 如果当前行不在
		if (dataSet != null) {
			if (dataSet.getBigDecimal("UNIQUE_ID").intValue() == uniqueId) {
				return dataSet.getRow();
			}
			dataSet.first();
			for (int i = 0; i < dataSet.rowCount(); i++) {
				if (dataSet.getBigDecimal("UNIQUE_ID").intValue() == uniqueId) {
					return i;
				}
				dataSet.next();
			}
		}
		return -1;
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
			// Point poinxxt = this.getMousePosition();
			// poinxxt=this.getLocationOnScreen()
			Point pointFrame = SwingUtils.getMousePoint(this.getPrintPage().getPrintDesignFrame());
			// if (point == null) {
			Point point = new Point(this.getWidth() / 2, this.getHeight() / 2);
			// }
			// Point point2 = SwingUtils.getMousePoint(this);
			dbText.setBounds(pointFrame.x - point.x - 20, pointFrame.y - point.y - 20, 220, 120);
		}
		dbText.setVisible(aFlag);

	}

	@Override
	public void setValue(String columnName, Object value) {
		PrintItemTool.setValue(this, columnName, value);
	}


	@Override
	public PrintTableCellHeaderRenderer clone(PrintTableCellHeaderRenderer entity) {
		return null;
	}

}
