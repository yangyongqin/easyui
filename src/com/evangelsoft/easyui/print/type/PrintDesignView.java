package com.evangelsoft.easyui.print.type;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.TransferHandler;

import com.evangelsoft.easyui.print.client.PrintElementSource;
import com.evangelsoft.easyui.print.client.PrintElementType;
import com.evangelsoft.easyui.print.client.PrintPage;

/**
 * ClassName: PrintDesignView 
 * @Description: 打印面板的接口，尽量接口，不用真正继承JPanel的实现类，避免直接调用JPanel的方法而没有正确保存数据
 * @author yyq
 * @date 2017年12月31日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public interface PrintDesignView {

	/**
	 * @Fields TABLE_VIEW : 表格显示
	 */
	public static String TABLE_VIEW = "T";

	/**
	 * @Fields ZDY_VIEW : 自定义显示
	 */
	public static String ZDY_VIEW = "Z";

	public void setWidth(int width);

	@DataColumn(dataColumn = "WIDTH")
	public int getWidth();

	@DataColumn(dataColumn = "HEIGHT")
	public void setHeight(int height);

	public int getHeight();

	public void toForward(int num);

	public void toBack(int num);

	public void toTable();

	public void toZdy();

	@DataColumn(dataColumn = "INDEX")
	public void toIndex(int index);

	public int getIndex();

	public void toFisrt();

	public void toLast();

	@DataColumn(dataColumn = "TYPE")
	public void setShowtype(String type);

	@DataColumn(dataColumn = "BACK_TEXT")
	public void setWatermark(String watermark);

	public PrintPage getPrintPage();

	public void remove(Component comp);

	public void repaint();

	public int getUniqueId();

	@DataColumn(dataColumn = "UNIQUE_ID")
	public void setUniqueId(int uniqueId);

	public List<PrintItem<?>> copyItems(List<PrintItem<?>> itemList);

	public List<PrintItem<?>> copyItem(PrintItem<?> item);

	public List<PrintItem<?>> addItem(final PrintElementType printType, Point point);

	public List<PrintItem<?>> addItem(PrintElementSource printSource, Point point);

	public Point getLocation();

	public void setParentId(int parentId);

	@DataColumn(dataColumn = "X")
	public void setX(int x);

	@DataColumn(dataColumn = "Y")
	public void setY(int y);

	public void setLocation(int x, int y);

	public void setTransferHandler(TransferHandler newHandler);

	public void addMouseListener(MouseListener l);

	public void setSize(double width, double height);

	public void setBackground(Color bg);

	public void addMouseMotionListener(MouseMotionListener l);

	public Dimension getSize();

	public Container getParent();
}
