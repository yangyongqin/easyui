package com.evangelsoft.easyui.print.client;

import java.util.ArrayList;
import java.util.List;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.print.type.PrintItem;

/**
 * @author yyq 打印面板管理器
 */
public class PrintPage {

	/**
	 * 缓存面板的数据集
	 */
	private StorageDataSet paneDataSet;

	private PrintDesignFrame printDesignFrame;

	/**
	 * 打印元素集合
	 */
	private StorageDataSet itemDataSet;

	/**
	 * 面板集合
	 */
	List<PrintDesignPanel> linkedPanel = new ArrayList<PrintDesignPanel>();

	/**
	 * 页宽
	 */
	private int pageWidth;

	/**
	 *
	 */
	/* private String heightAuto; */
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

	public PrintPage(PrintDesignFrame frame) {
		this.printDesignFrame = frame;
	}

	public List<PrintDesignPanel> getLinkedPanel() {
		return linkedPanel;
	}

	public void setLinkedPanel(List<PrintDesignPanel> linkedPanel) {
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

	public StorageDataSet getPaneDataSet() {
		return paneDataSet;
	}

	public void setPaneDataSet(StorageDataSet paneDataSet) {
		this.paneDataSet = paneDataSet;
	}

	public StorageDataSet getItemDataSet() {
		return itemDataSet;
	}

	public void setItemDataSet(StorageDataSet itemDataSet) {
		this.itemDataSet = itemDataSet;
	}

	public ArrayList<PrintItem<?>> getSelectList() {
		return printDesignFrame.selectList;
	}

	public PrintItem<?> getSelectComp() {
		return printDesignFrame.selectComp;
	}

	public String getFontName() {
		return printDesignFrame.getFontName();
	}

	public int getFontSize() {
		return printDesignFrame.getFontSize();
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
		return printDesignFrame.getVerticalAlignment();
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
		return printDesignFrame.getHorizontalAlignment();
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
		return printDesignFrame.isBold();
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
		return printDesignFrame.isItalic();
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
		return printDesignFrame.isUnderline();
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
		return printDesignFrame.isStrikethrough();
	}

	public PrintDesignFrame getPrintDesignFrame() {
		return printDesignFrame;
	}

	public void setPrintDesignFrame(PrintDesignFrame printDesignFrame) {
		this.printDesignFrame = printDesignFrame;
	}

}
