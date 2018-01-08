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

	/**
	 * @Description: 设置当前面板宽度
	 * @param width   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setWidth(int width);

	@DataColumn(dataColumn = "WIDTH")
	public int getWidth();

	/**
	 * @Description: 设置当前面板高度
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "HEIGHT")
	public void setHeight(int height);

	/**
	 * @Description: 得到当前面板高度
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public int getHeight();

	/**
	 * @Description: 向前移动num位
	 * @param num   向前移动位数
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void toForward(int num);

	/**
	 * @Description: 向后移动位数
	 * @param num   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void toBack(int num);

	/**
	 * @Description: 自定义面板转为表格显示   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void toTable();

	public void toZdy();

	@DataColumn(dataColumn = "INDEX")
	public void toIndex(int index);

	/**
	 * @Description: 得到当前面板下标
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public int getIndex();

	/**
	 * @Description: 将当前面板置顶   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void toFisrt();

	/**
	 * @Description: 将当前面板移动到最尾   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void toLast();

	@DataColumn(dataColumn = "TYPE")
	public void setShowType(String type);

	public String getShowType();

	@DataColumn(dataColumn = "BACK_TEXT")
	public void setWatermark(String watermark);

	public String getWatermark();

	/**
	 * @Description: 得到当前页面信息
	 * @return   
	 * @return PrintPage  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public PrintPage getPrintPage();

	/**
	 * @Description: 删除一个子元素
	 * @param comp   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void remove(Component comp);

	/**
	 * @Description: UI重绘   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void repaint();

	/**
	 * @Description: 得到唯一ID
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public int getUniqueId();

	/**
	 * @Description: 设置唯一ID
	 * @param uniqueId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "UNIQUE_ID")
	public void setUniqueId(int uniqueId);

	/**
	 * @Description: 设置下标
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2018年1月9日
	 */
	@DataColumn(dataColumn = "PLATE_INDEX")
	public void setIndex(int index);

	/**
	 * @Description: 复制多个子元素
	 * @param itemList
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public List<PrintItem<?>> copyItems(List<PrintItem<?>> itemList);

	/**
	 * @Description: 复制一个子元素
	 * @param item
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public List<PrintItem<?>> copyItem(PrintItem<?> item);

	/**
	 * @Description: 添加子元素
	 * @param printType
	 * @param point
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public List<PrintItem<?>> addItem(final PrintElementType printType, Point point);

	/**
	 * @Description: 添加一个子元素
	 * @param printSource
	 * @param point
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public List<PrintItem<?>> addItem(PrintElementSource printSource, Point point);

	/**
	 * @Description: 得到当前面板坐标
	 * @return   
	 * @return Point  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public Point getLocation();

	/**
	 * @Description: 父级面板ID
	 * @param parentId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setParentId(int parentId);

	/**
	 * @Description: 设置X轴坐标
	 * @param x   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "X")
	public void setX(int x);

	/**
	 * @Description: 设置Y轴坐标
	 * @param y   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "Y")
	public void setY(int y);

	/**
	 * @Description: 设置坐标
	 * @param x
	 * @param y   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setLocation(int x, int y);

	/**
	 * @Description: 接收鼠标拖拽事件
	 * @param newHandler   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setTransferHandler(TransferHandler newHandler);

	/**
	 * @Description: 添加鼠标点击监听
	 * @param l   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void addMouseListener(MouseListener l);

	/**
	 * @Description: 设置组件大小
	 * @param width
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setSize(double width, double height);

	/**
	 * @Description: 设置背景色
	 * @param bg   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void setBackground(Color bg);

	public Color getBackground();

	/**
	 * @Description: 添加鼠标移动监听
	 * @param l   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public void addMouseMotionListener(MouseMotionListener l);

	/**
	 * @Description: 得到组件大小
	 * @return   
	 * @return Dimension  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public Dimension getSize();

	/**
	 * @Description: 得到上级容器
	 * @return   
	 * @return Container  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	public Container getParent();

	/**
	 * @Description: 设备表ID,数据来源
	 * @param tableName   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "TABLE")
	public void setTableId(String tableId);

	public String getTableId();

	/**
	 * @Description: 内容是否循环
	 * @param circulation   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月8日
	 */
	@DataColumn(dataColumn = "CIRCULATION")
	public void setCirculation(boolean circulation);

	public boolean isCirculation();

	/**
	 * @Description: 是否自动伸缩
	 * @param autoStretch   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2018年1月7日
	 */
	@DataColumn(dataColumn = "AUTO_STRETCH")
	public void setAutoStretch(boolean autoStretch);

	public boolean getAutoStretch();
}
