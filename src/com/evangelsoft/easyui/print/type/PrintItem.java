package com.evangelsoft.easyui.print.type;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.border.Border;

import com.evangelsoft.easyui.print.client.PrintPage;

@SuppressWarnings("rawtypes")
public interface PrintItem<T extends PrintItem> {

	/**
	 * @Description: 得到唯一标示
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public int getUniqueId();

	/**
	 * @Description: 设置唯一标示
	 * @param uniqueId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	@DataColumn(dataColumn = "UNIQUE_ID")
	public void setUniqueId(int uniqueId);

	/**
	 * @Description: TODO
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017年11月18日
	 */
	@DataColumn(dataColumn = "INDEX")
	public void setIndex(int index);

	/**
	 * @Description: 获取下标
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017年11月18日
	 */
	public int getIndex();

	/**
	 * @Description: 得到打印元素宽度
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public int getElementWidth();

	/**
	 * @Description: 设置打印元素宽度
	 * @param width   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	@DataColumn(dataColumn = "WIDTH")
	public void setWidth(int width);

	/**
	 * @Description: 设置打印元素高度
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public int getElementHeight();

	/**
	 * @Description: 得到打印元素高度
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	@DataColumn(dataColumn = "HEIGHT")
	public void setHeight(int height);

	/**
	 * @Description: 得到字体大小
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public int getFontSize();

	/**
	 * @Description: 设置字体大小
	 * @param fontSize   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	@DataColumn(dataColumn = "FONT_SIZE")
	public void setFontSize(int fontSize);

	/**
	 * @Description: 得到字体名称
	 * @return   
	 * @return String  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public String getFontName();

	/**
	 * @Description: 设置字体大小
	 * @param fontName   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	@DataColumn(dataColumn = "FONT_NAME")
	public void setFontName(String fontName);

	/**
	 * @Description: 设置边框
	 * @param border   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public void setBorder(Border border);

	/**
	 * @Description: 得到边框
	 * @return   
	 * @return Border  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public Border getBorder();

	/**
	 * @Description: 设置缩放比率
	 * @return   
	 * @return double  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public double setZoomRatio();

	/**
	 * @Description: 得到缩放比率
	 * @return   
	 * @return double  
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月23日
	 */
	public double getZoomRatio();

	Point getLocation();

	void setLocation(Point point);

	void setVerticalAlignment(int alignment);

	void setHorizontalAlignment(int alignment);

	void delete();

	@DataColumn(dataColumn = "BOLD")
	void setIsBold(boolean boolStr);

	@DataColumn(dataColumn = "ITALIC")
	void setIsitalic(boolean boolStr);

	@DataColumn(dataColumn = "STRIKETHROUGH")
	void setIsstrikethrough(boolean boolStr);

	@DataColumn(dataColumn = "UNDERLINE")
	void setIsUnderline(boolean boolStr);

	@DataColumn(dataColumn = "HORIZONTAL_ALIGNMENT")
	void setElementHorizontalAlignment(String alignment);

	@DataColumn(dataColumn = "VERTICAL_ALIGNMENT")
	void setElementVerticalAlignment(String alignment);

	Dimension getSize();

	public Container getParent();

	void setSize(Dimension dimension);

	public void addMouseListener(MouseListener l);

	public boolean getIsBold();

	public boolean getIsitalic();

	public boolean getIsstrikethrough();

	boolean getIsUnderline();

	String getElementHorizontalAlignment();

	String getElementVerticalAlignment();

	String getType();

	public PrintPage getPrintPage();

	PrintDesignView getParentPanel();

	Font getFont();

	void showEdit();

	void hideEdit();

	void setVisibleEdit(boolean aFlag);

	public void setValue(String columnName, Object value);

	@DataColumn(dataColumn = "TEXT")
	public void setText(String text);

	public String getText();

	public T clone();

	public int getRelationId();

	public void setRelationId(int relationId);

	public int getHorizontalAlignment();

	public int getVerticalAlignment();

	public String getRotation();

	@DataColumn(dataColumn = "ROTATION")
	public void setRotation(String rotation);

	@DataColumn(dataColumn = "FORECOLOR")
	public void setForeColor(String color);

	public String getForeColor();

	@DataColumn(dataColumn = "BACKCOLOR")
	public void setBackColor(String color);

	public String getBackColor();

	@DataColumn(dataColumn = "LINE_SIZE")
	public void setLineSize(int color);

	/**
	 * @Description: 线方向
	 * @param color   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2018年1月14日
	 */
	@DataColumn(dataColumn = "LINE_DIRECTION")
	public void setLineDirection(String color);

}
