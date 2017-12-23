package com.evangelsoft.easyui.print.type;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.border.Border;

import com.evangelsoft.easyui.print.client.PrintDesignPanel;
import com.evangelsoft.easyui.print.client.PrintPage;

@SuppressWarnings("rawtypes")
public interface PrintItem<T extends PrintItem> {

	/**
	 * @Description: �õ�Ψһ��ʾ
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public int getUniqueId();

	/**
	 * @Description: ����Ψһ��ʾ
	 * @param uniqueId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	@DataColumn(dataColumn = "UNIQUE_ID")
	public void setUniqueId(int uniqueId);

	/**
	 * @Description: TODO
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017��11��18��
	 */
	@DataColumn(dataColumn = "INDEX")
	public void setIndex(int index);

	/**
	 * @Description: ��ȡ�±�
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2017��11��18��
	 */
	public int getIndex();

	/**
	 * @Description: �õ���ӡԪ�ؿ��
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public int getWidth();

	/**
	 * @Description: ���ô�ӡԪ�ؿ��
	 * @param width   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	@DataColumn(dataColumn = "WIDTH")
	public void setWidth(int width);

	/**
	 * @Description: ���ô�ӡԪ�ظ߶�
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public int getHeight();

	/**
	 * @Description: �õ���ӡԪ�ظ߶�
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	@DataColumn(dataColumn = "HEIGHT")
	public void setHeight(int height);

	/**
	 * @Description: �õ������С
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public int getFontSize();

	/**
	 * @Description: ���������С
	 * @param fontSize   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	@DataColumn(dataColumn = "FONT_SIZE")
	public void setFontSize(int fontSize);

	/**
	 * @Description: �õ���������
	 * @return   
	 * @return String  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public String getFontName();

	/**
	 * @Description: ���������С
	 * @param fontName   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	@DataColumn(dataColumn = "FONT_NAME")
	public void setFontName(String fontName);

	/**
	 * @Description: ���ñ߿�
	 * @param border   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public void setBorder(Border border);

	/**
	 * @Description: �õ��߿�
	 * @return   
	 * @return Border  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public Border getBorder();

	/**
	 * @Description: �������ű���
	 * @return   
	 * @return double  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public double setZoomRatio();

	/**
	 * @Description: �õ����ű���
	 * @return   
	 * @return double  
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��23��
	 */
	public double getZoomRatio();

	Point getLocation();

	void setLocation(Point point);

	@DataColumn(dataColumn = "VERTICAL_ALIGNMENT")
	void setVerticalAlignment(int alignment);

	@DataColumn(dataColumn = "HORIZONTAL_ALIGNMENT")
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

	void setElementHorizontalAlignment(String alignment);

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

	PrintDesignPanel getParentPanel();

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
}
