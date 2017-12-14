package com.evangelsoft.easyui.template.client.nc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

/**
 *
 * ��������:(2003-2-12 10:26:56)
 *
 * @author:hxr
 */
public class DefaultTableHeaderCellRenderer extends
		javax.swing.table.DefaultTableCellRenderer {
	/** ����ͷ�߶� */
	public static final int		UITABLE_HEAD_HEIGHT	= 21;
	private javax.swing.border.Border border = null;

	public static javax.swing.border.Border defaultBorder=new LineBorder(Color.BLACK, 1);;

	private Color defaultForeground;

	private Color defaultBackground;

	private Color foreground = null;

	private Color background = null;

	private int alighment = JLabel.CENTER;
	private String pageId;
	private String  columnId;



	/**
	 * ExDefaultTableCellRenderer ������ע��.
	 */
	public DefaultTableHeaderCellRenderer(String pageId,String columnId) {
		super();
		this.pageId=pageId;
		this.columnId=columnId;
//		defaultBorder = UIManager.getBorder("TableHeader.cellBorder");
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @return int
	 */
	public int getAlighment() {
		return alighment;
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @return java.awt.Color
	 */
	public java.awt.Color getBackground() {
		return background;
	}

	/**
	 *
	 * ��������:(2003-2-12 10:33:41)
	 *
	 * @return javax.swing.border.Border
	 */
	public javax.swing.border.Border getBorder() {
		return border;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 *
	 * ��������:(2003-2-20 14:43:05)
	 *
	 * @return javax.swing.border.Border
	 */
	public javax.swing.border.Border getDefaultBorder() {
		return defaultBorder;
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @return java.awt.Color
	 */
	public java.awt.Color getForeground() {
		return foreground;
	}

	// implements javax.swing.table.TableCellRenderer
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		if (table != null) {
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				defaultForeground = header.getForeground();
				defaultBackground = header.getBackground();
				setForeground((foreground != null) ? foreground : defaultForeground);
				setBackground((background != null) ? background : defaultBackground);
				setFont(header.getFont());
			}
		}
		if (border != null)
			setBorder(border);
		else
			setBorder(defaultBorder);

		setHorizontalAlignment(alighment);

		setText((value == null) ? "" : value.toString());

		//�ж������Ⱦ����Ƿ���ʵtooltip
//		Font defaultfont = javax.swing.plaf.FontUIResource.
		Graphics g = table.getGraphics();
		if (g != null)
		{
			FontMetrics fontm = g.getFontMetrics();
			int textWidth = fontm.stringWidth(getText());
			int columnWidth = table.getColumnModel().getColumn(column).getWidth();
			if (textWidth > columnWidth)
				setToolTipText(getText());
			else
				setToolTipText(null);
		}
		setPreferredSize(new Dimension(getWidth(), UITABLE_HEAD_HEIGHT));

//		if (hasFocus)
//			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		if (isSelected)
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		return this;
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @param newAlighment
	 *            int
	 */
	public void setAlighment(int newAlighment) {
		alighment = newAlighment;
		super.setHorizontalAlignment(alighment);
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @param newBackground
	 *            java.awt.Color
	 */
	public void setBackground(java.awt.Color newBackground) {
		background = newBackground;

		if (background != null)
			super.setBackground(background);
		else if (defaultBackground != null)
			super.setBackground(defaultBackground);
	}

	/**
	 *
	 * ��������:(2003-2-12 10:33:41)
	 *
	 * @param newFocusBorder
	 *            javax.swing.border.Border
	 */
	public void setBorder(javax.swing.border.Border newBorder) {
		border = newBorder;
		if (border != null)
			super.setBorder(border);
		else
			super.setBorder(defaultBorder);
	}

	/**
	 *
	 * ��������:(2003-2-20 14:43:05)
	 *
	 * @param newDefaultBorder
	 *            javax.swing.border.Border
	 */
	public void setDefaultBorder(javax.swing.border.Border newDefaultBorder) {
		defaultBorder = newDefaultBorder;
	}

	/**
	 *
	 * ��������:(2003-2-12 11:19:57)
	 *
	 * @param newForeground
	 *            java.awt.Color
	 */
	public void setForeground(java.awt.Color newForeground) {
		foreground = newForeground;
		if (foreground != null)
			super.setForeground(foreground);
		else if (defaultForeground != null)
			super.setForeground(defaultForeground);
	}

}
