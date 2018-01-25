package com.evangelsoft.easyui.print.client;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.borland.dbswing.TableScrollPane;
import com.evangelsoft.easyui.print.type.PrintDesignManage;
import com.evangelsoft.easyui.print.type.PrintDesignView;
import com.evangelsoft.easyui.tool.SwingUtils;

/**
 * ClassName: PrintDesignManagePanel 
 * @Description: 设计管理面板
 * @author yyq
 * @date 2017年12月30日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintDesignManagePanel extends JPanel implements MouseMotionListener, PrintDesignManage {

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields linkedPanel : 只管理一级面板，按照下标顺序由高到低
	 */
	private List<PrintDesignView> linkedPanel = new ArrayList<PrintDesignView>();

	/**
	 * @Fields printPanelMap : 所有面板管理器
	 */
	private HashMap<Integer, PrintDesignView> printPanelMap = new HashMap<Integer, PrintDesignView>();

	JPanel topPanel, bottomPanel, leftPanel, rightPanel, centerPanel;

	/**
	 * 页宽
	 */
	private int pageWidth;

	/**
	 * 页高
	 */
	private int pageHeight;

	/**
	 * 方向，横向，纵向
	 */
	private int orientation;

	/**
	 * 左边距
	 */
	private int leftMargin;

	/**
	 * 右边距
	 */
	private int rightmargin;

	/**
	 * 上边距
	 */
	private int topMargin;

	/**
	 * 下边距
	 */
	private int bottomMargin;

	/**
	 * 一行显示元素数量
	 */
	private int colNum;

	/**
	 * 每个元素宽度
	 */
	private int colWidth;

	private PrintPage printPage;

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

	public PrintDesignManagePanel(PrintPage printPage) {
		this(printPage, 595, 842);
	}

	public PrintDesignManagePanel(PrintPage printPage, int width, int height) {
		this(printPage, width, height, 5, 5, 5, 5);
	}

	public PrintDesignManagePanel(PrintPage printPage, int width, int height, int leftMargin, int rightMargin) {
		this(printPage, width, height, 0, 0, leftMargin, rightMargin);
	}

	public PrintDesignManagePanel(PrintPage printPage, int width, int height, int margin) {
		this(printPage, width, height, margin, margin, margin, margin);
	}

	public PrintDesignManagePanel(PrintPage printPage, int width, int height, int topMargin, int bottomMargin,
			int leftMargin, int rightMargin) {
		this.printPage = printPage;
		this.setLayout(new BorderLayout());
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(1, topMargin));
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(1, bottomMargin));
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(leftMargin, 1));
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(rightMargin, 1));
		centerPanel = new JPanel();
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.setBackground(SystemColor.WHITE);
		this.centerPanel.setBackground(SystemColor.WHITE);

		this.topPanel.setBackground(SystemColor.GREEN);
		this.bottomPanel.setBackground(SystemColor.GREEN);
		this.leftPanel.setBackground(SystemColor.GREEN);
		this.rightPanel.setBackground(SystemColor.GREEN);
		this.centerPanel.setBackground(SystemColor.GREEN);
		centerPanel.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.setPreferredSize(new Dimension(width, height));
	}

	public List<PrintDesignView> getLinkedPanel() {
		return linkedPanel;
	}

	public void setLinkedPanel(List<PrintDesignView> linkedPanel) {
		this.linkedPanel = linkedPanel;
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public int getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	public int getRightmargin() {
		return rightmargin;
	}

	public void setRightmargin(int rightmargin) {
		this.rightmargin = rightmargin;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
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

	public HashMap<Integer, PrintDesignView> getPrintPanelMap() {
		return printPanelMap;
	}

	public void setPrintPanelMap(HashMap<Integer, PrintDesignView> printPanelMap) {
		this.printPanelMap = printPanelMap;
	}

	public PrintPage getPrintPage() {
		return printPage;
	}

	public void setPrintPage(PrintPage printPage) {
		this.printPage = printPage;
	}

	public PrintDesignView addPrintDesignPanel() {
		// 默认之定义显示
		return this.addPrintDesignPanel(linkedPanel.size(), 200, PrintDesignView.ZDY_VIEW, "自定义");
	}

	public PrintDesignView addPrintDesignPanel(String type, String watermark) {
		// 默认之定义显示
		return this.addPrintDesignPanel(linkedPanel.size(), 200, type, watermark);
	}

	public PrintDesignView addPrintDesignPanel(int height, String type, String watermark) {
		return this.addPrintDesignPanel(linkedPanel.size(), height, type, watermark);
	}

	/**
	 * @Description: 插入添加到指定下标
	 * @param panel
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017年12月31日
	 */
	public PrintDesignView addPrintDesignPanel(int index, int height, String type, String watermark) {

		PrintDesignView panel = new PrintDesignPanel(this, watermark, type, printPage.getPaneDataSet(), true);
		this.centerPanel.add((PrintDesignPanel) panel);
		int y = 1;
		if (linkedPanel.size() > 0) {
			PrintDesignView lastPanel = linkedPanel.get(linkedPanel.size() - 1);
			y = lastPanel.getLocation().y + lastPanel.getHeight();
		}
		panel.setLocation(1, y);
		panel.setSize(this.getWidth(), height);

		panel.setBackground(SystemColor.WHITE);
		panel.addMouseMotionListener(this);
		this.linkedPanel.add(panel);
		return panel;
	}

	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		this.centerPanel.addMouseListener(l);
	}

	// 删除一个面板
	public void delete(PrintDesignView panel) {
		// boolean isDelete = false;
		PrintDesignView tempView = null;
		int height = 0;
		for (int i = 0; i < linkedPanel.size(); i++) {
			if (panel.equals(linkedPanel.get(i))) {
				// 如果下面还有，就 将下面的面板向上移动
				this.centerPanel.remove((PrintDesignPanel) panel);
				tempView = panel;
				height = tempView.getHeight();
			}
			if (tempView != null) {
				if (i < linkedPanel.size() - 1) {
					PrintDesignView nextPanel = linkedPanel.get(i + 1);
					nextPanel.setIndex(i);
					nextPanel.setLocation(nextPanel.getLocation().x, nextPanel.getLocation().y - height);
				}
			}

		}
		linkedPanel.remove(tempView);
		printPanelMap.remove(tempView.getUniqueId());
		this.centerPanel.repaint();
	}

	private int direction = Direction.UP;

	PrintDesignPanel panel;

	// private

	@Override
	public void mouseDragged(MouseEvent e) {
		if (panel == null) {
			return;
		}
		// int y = e.getY();
		Point pointFrame = SwingUtils.getMousePoint((JPanel) panel);
		int y = pointFrame.y;

		/*
		 * if (e.getPoint().getY() <= 3) { // y = e.getY();
		 * this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)); }
		 */

		Dimension dimension = panel.getSize();
		Point point = panel.getLocation();
		dimension.setSize(dimension.getWidth(), y);
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

		if (point.x < 0) {
			point.x = 0;
		}
		if (point.x >= panel.getParent().getWidth() - 3) {
			point.x = panel.getParent().getWidth() - 3;
		}
		if (point.y < 0) {
			point.y = 0;
		}
		if (point.y >= panel.getParent().getHeight() - 3) {
			point.y = panel.getParent().getHeight() - 3;
		}
		panel.setLocation(point.x, point.y);

		int statrY = point.y + panel.getHeight();

		// PrintDesignView tempView = null;
		for (int i = panel.getIndex(); i < linkedPanel.size() - 1; i++) {
			PrintDesignView nextPanel = linkedPanel.get(i + 1);
			nextPanel.setLocation(nextPanel.getLocation().x, statrY);
			statrY = statrY + nextPanel.getHeight();
			// ((JPanel) nextPanel).setVisible(false);
		}
		panel.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
		this.centerPanel.repaint();
		panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PrintDesignPanel panel;
		if (e.getSource() instanceof PrintTable) {
			PrintTable table = (PrintTable) e.getSource();
			panel = (PrintDesignPanel) table.getPanel();
		} else if (e.getSource() instanceof PrintTableScrollPane) {
			PrintTableScrollPane table = (PrintTableScrollPane) e.getSource();
			panel = (PrintDesignPanel) table.getParent().getParent();
		} else {
			panel = (PrintDesignPanel) e.getSource();
		}
		// 不能直接从向上变为向下
		if (panel.getHeight() - e.getPoint().getY() <= 3 && direction != Direction.UP) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			direction = Direction.DOWN;
			this.panel = panel;
		}
		/*
		 * else if (e.getPoint().getY() <= 3 && direction != Direction.DOWN) {
		 * panel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		 * direction = Direction.UP; this.panel = panel; if (panel.getIndex() ==
		 * 0) { this.panel = null; } else { this.panel = (PrintDesignPanel)
		 * this.linkedPanel.get(panel.getIndex() - 1); } panel = this.panel; }
		 */
		else {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			direction = 0;
			// panel.setBackground(SystemColor.white);
			this.panel = null;
		}
	}

	/**
	 * @Description: 切换面板显示顺序
	 * @param oldIndex
	 * @param newIndex   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void changeIndex(int oldIndex, int newIndex) {
		if (newIndex < 0 || newIndex >= linkedPanel.size()) {
			return;
		}
		if (oldIndex < newIndex) {
			// 如果是向下移动
			for (int i = oldIndex; i <= newIndex; i++) {
				this.centerPanel.remove((PrintDesignPanel) this.linkedPanel.get(i));
			}

			PrintDesignView printView = linkedPanel.remove(oldIndex);
			Point point = printView.getLocation();
			linkedPanel.add(newIndex, printView);
			// 循环显示界面
			for (int i = oldIndex; i <= newIndex; i++) {
				PrintDesignView view = this.linkedPanel.get(i);
				view.setLocation(point.x, point.y);
				// 计算下一个面板启始坐标
				point.y = point.y + view.getHeight();
				view.setIndex(i);
				this.centerPanel.add((PrintDesignPanel) view);
			}
		} else if (oldIndex > newIndex) {

			PrintDesignView printView = linkedPanel.get(newIndex);
			Point point = printView.getLocation();
			for (int i = newIndex; i <= oldIndex; i++) {
				this.centerPanel.remove((PrintDesignPanel) this.linkedPanel.get(i));
			}
			PrintDesignView oldView = linkedPanel.remove(oldIndex);
			linkedPanel.add(newIndex, oldView);
			// 循环显示界面
			for (int i = newIndex; i <= oldIndex; i++) {
				PrintDesignView view = this.linkedPanel.get(i);
				view.setLocation(point.x, point.y);
				view.setIndex(i);
				// 计算下一个面板启始坐标
				point.y = point.y + view.getHeight();
				this.centerPanel.add((PrintDesignPanel) view);
			}
		}
		this.centerPanel.repaint();
	}
}
