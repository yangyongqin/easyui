package com.evangelsoft.easyui.print.client;

import java.util.ArrayList;
import java.util.List;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.print.type.PrintItem;

/**
 * @author yyq ��ӡ��������
 */
public class PrintPage {

	/**
	 * �����������ݼ�
	 */
	private StorageDataSet paneDataSet;

	private PrintDesignFrame printDesignFrame;

	/**
	 * ��ӡԪ�ؼ���
	 */
	private StorageDataSet itemDataSet;

	/**
	 * ��弯��
	 */
	List<PrintDesignPanel> linkedPanel = new ArrayList<PrintDesignPanel>();

	/**
	 * ҳ��
	 */
	private int pageWidth;

	/**
	 *
	 */
	/* private String heightAuto; */
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
	 * @Description: ��ֱ���뷽ʽ
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
	 * @Description: ˮƽ���뷽ʽ
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
	 * @Description: �Ƿ�Ӵ�
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
	 * @Description: �Ƿ�б��
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
	 * @Description: �Ƿ��»��߻���
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
	 * @Description: �Ƿ��л���
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
