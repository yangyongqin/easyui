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
import com.evangelsoft.easyui.print.type.PrintDesignView;

/**
 * ClassName: PrintDesignManagePanel 
 * @Description: ��ƹ������
 * @author yyq
 * @date 2017��12��30��
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintDesignManagePanel extends JPanel implements MouseMotionListener {

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields linkedPanel : ֻ����һ����壬�����±�˳���ɸߵ���
	 */
	private List<PrintDesignView> linkedPanel = new ArrayList<PrintDesignView>();

	/**
	 * @Fields printPanelMap : ������������
	 */
	private HashMap<Integer, PrintDesignView> printPanelMap = new HashMap<Integer, PrintDesignView>();

	JPanel topPanel, bottomPanel, leftPanel, rightPanel, centerPanel;

	/**
	 * ҳ��
	 */
	private int pageWidth;

	/**
	 * ҳ��
	 */
	private int pageHeight;

	/**
	 * ���򣬺�������
	 */
	private int orientation;

	/**
	 * ��߾�
	 */
	private int leftMargin;

	/**
	 * �ұ߾�
	 */
	private int rightmargin;

	/**
	 * �ϱ߾�
	 */
	private int topMargin;

	/**
	 * �±߾�
	 */
	private int bottomMargin;

	/**
	 * һ����ʾԪ������
	 */
	private int colNum;

	/**
	 * ÿ��Ԫ�ؿ��
	 */
	private int colWidth;

	private PrintPage printPage;

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
		// Ĭ��֮������ʾ
		return this.addPrintDesignPanel(linkedPanel.size(), 200, PrintDesignView.ZDY_VIEW, "�Զ���");
	}

	public PrintDesignView addPrintDesignPanel(String type, String watermark) {
		// Ĭ��֮������ʾ
		return this.addPrintDesignPanel(linkedPanel.size(), 200, type, watermark);
	}

	public PrintDesignView addPrintDesignPanel(int height, String type, String watermark) {
		return this.addPrintDesignPanel(linkedPanel.size(), height, type, watermark);
	}

	/**
	 * @Description: ������ӵ�ָ���±�
	 * @param panel
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017��12��31��
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

	// ɾ��һ�����
	public void delete(PrintDesignView panel) {
		// boolean isDelete = false;
		PrintDesignView tempView = null;
		for (int i = 0; i < linkedPanel.size(); i++) {
			if (panel.equals(linkedPanel.get(i))) {
				// ������滹�У��� ���������������ƶ�
				this.centerPanel.remove((PrintDesignPanel) panel);
				tempView = panel;
			}
			if (tempView != null) {
				if (i < linkedPanel.size() - 1) {
					PrintDesignView nextPanel = linkedPanel.get(i + 1);
					nextPanel.setLocation(nextPanel.getLocation().x, nextPanel.getLocation().y - panel.getHeight());
				}
			}

		}
		linkedPanel.remove(tempView);
		printPanelMap.remove(tempView.getUniqueId());
		this.centerPanel.repaint();
	}

	private int direction;

	@Override
	public void mouseDragged(MouseEvent e) {
		PrintDesignView panel;
		if (e.getSource() instanceof PrintTable) {
			PrintTable table = (PrintTable) e.getSource();
			panel = table.getPanel();
		} else if (e.getSource() instanceof TableScrollPane) {
			TableScrollPane table = (TableScrollPane) e.getSource();
			panel = (PrintDesignPanel) table.getParent();
		} else {
			panel = (PrintDesignPanel) e.getSource();
		}
		Dimension dimension = panel.getSize();
		Point point = panel.getLocation();
		switch (direction) {
			case Direction.LEFT_UP:
				dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight() - e.getY());
				point.setLocation(panel.getLocation().x + e.getX(), panel.getLocation().y + e.getY());
				break;
			case Direction.RIGHT_UP:
				dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(),
						dimension.getHeight() - e.getY());
				point.setLocation(panel.getLocation().x, panel.getLocation().y + e.getY());
				break;
			case Direction.UP:
				dimension.setSize(dimension.getWidth(), dimension.getHeight() - e.getY());
				point.setLocation(panel.getLocation().x, panel.getLocation().y + e.getY());
				break;
			case Direction.LEFT:
				dimension.setSize(dimension.getWidth() - e.getX(), dimension.getHeight());
				point.setLocation(panel.getLocation().x + e.getX(), panel.getLocation().y);
				break;
			case Direction.LEFT_DOWN:
				point.setLocation(panel.getLocation().x + e.getX(), panel.getLocation().y);
				dimension.setSize(dimension.getWidth() - e.getX(), e.getY());
				break;
			case Direction.RIGHT:
				dimension.setSize(e.getX(), dimension.getHeight());
				break;
			case Direction.RIGHT_DOWN:
				point.setLocation(panel.getLocation().x, panel.getLocation().y);
				dimension.setSize(dimension.getWidth() - dimension.getWidth() + e.getX(), e.getY());
				break;
			case Direction.DOWN:
				dimension.setSize(dimension.getWidth(), e.getY());
				break;

			default:
				return;
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
		panel.setSize(dimension.getWidth(), dimension.getHeight());
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

		PrintDesignView tempView = null;
		for (int i = 0; i < linkedPanel.size(); i++) {
			if (panel.equals(linkedPanel.get(i))) {
				// ������滹�У��� ���������������ƶ�
				tempView = panel;
			}
			if (tempView != null) {
				if (i < linkedPanel.size() - 1) {
					PrintDesignView nextPanel = linkedPanel.get(i + 1);
					nextPanel.setLocation(panel.getLocation().x, statrY);
					statrY = statrY + nextPanel.getHeight();
				}
			}

		}
		this.centerPanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PrintDesignPanel panel;
		if (e.getSource() instanceof PrintTable) {
			PrintTable table = (PrintTable) e.getSource();
			panel = (PrintDesignPanel) table.getPanel();
		} else if (e.getSource() instanceof TableScrollPane) {
			TableScrollPane table = (TableScrollPane) e.getSource();
			panel = (PrintDesignPanel) table.getParent();
		} else {
			panel = (PrintDesignPanel) e.getSource();
		}
		if (panel.getHeight() - e.getPoint().getY() <= 3) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			direction = Direction.DOWN;
		} else {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			direction = 0;
		}
	}

	/**
	 * @Description: �л������ʾ˳��
	 * @param oldIndex
	 * @param newIndex   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void changeIndex(int oldIndex, int newIndex) {
		if (oldIndex < newIndex) {
			//����������ƶ�
			
		} else if (oldIndex < newIndex) {
			//����������ƶ�
		}
	}
}
