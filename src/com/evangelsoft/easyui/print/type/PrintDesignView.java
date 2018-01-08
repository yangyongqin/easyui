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
 * @Description: ��ӡ���Ľӿڣ������ӿڣ����������̳�JPanel��ʵ���࣬����ֱ�ӵ���JPanel�ķ�����û����ȷ��������
 * @author yyq
 * @date 2017��12��31��
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public interface PrintDesignView {

	/**
	 * @Fields TABLE_VIEW : �����ʾ
	 */
	public static String TABLE_VIEW = "T";

	/**
	 * @Fields ZDY_VIEW : �Զ�����ʾ
	 */
	public static String ZDY_VIEW = "Z";

	/**
	 * @Description: ���õ�ǰ�����
	 * @param width   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setWidth(int width);

	@DataColumn(dataColumn = "WIDTH")
	public int getWidth();

	/**
	 * @Description: ���õ�ǰ���߶�
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "HEIGHT")
	public void setHeight(int height);

	/**
	 * @Description: �õ���ǰ���߶�
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public int getHeight();

	/**
	 * @Description: ��ǰ�ƶ�numλ
	 * @param num   ��ǰ�ƶ�λ��
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void toForward(int num);

	/**
	 * @Description: ����ƶ�λ��
	 * @param num   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void toBack(int num);

	/**
	 * @Description: �Զ������תΪ�����ʾ   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void toTable();

	public void toZdy();

	@DataColumn(dataColumn = "INDEX")
	public void toIndex(int index);

	/**
	 * @Description: �õ���ǰ����±�
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public int getIndex();

	/**
	 * @Description: ����ǰ����ö�   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void toFisrt();

	/**
	 * @Description: ����ǰ����ƶ�����β   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void toLast();

	@DataColumn(dataColumn = "TYPE")
	public void setShowType(String type);

	public String getShowType();

	@DataColumn(dataColumn = "BACK_TEXT")
	public void setWatermark(String watermark);

	public String getWatermark();

	/**
	 * @Description: �õ���ǰҳ����Ϣ
	 * @return   
	 * @return PrintPage  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public PrintPage getPrintPage();

	/**
	 * @Description: ɾ��һ����Ԫ��
	 * @param comp   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void remove(Component comp);

	/**
	 * @Description: UI�ػ�   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void repaint();

	/**
	 * @Description: �õ�ΨһID
	 * @return   
	 * @return int  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public int getUniqueId();

	/**
	 * @Description: ����ΨһID
	 * @param uniqueId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "UNIQUE_ID")
	public void setUniqueId(int uniqueId);

	/**
	 * @Description: �����±�
	 * @param index   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2018��1��9��
	 */
	@DataColumn(dataColumn = "PLATE_INDEX")
	public void setIndex(int index);

	/**
	 * @Description: ���ƶ����Ԫ��
	 * @param itemList
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public List<PrintItem<?>> copyItems(List<PrintItem<?>> itemList);

	/**
	 * @Description: ����һ����Ԫ��
	 * @param item
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public List<PrintItem<?>> copyItem(PrintItem<?> item);

	/**
	 * @Description: �����Ԫ��
	 * @param printType
	 * @param point
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public List<PrintItem<?>> addItem(final PrintElementType printType, Point point);

	/**
	 * @Description: ���һ����Ԫ��
	 * @param printSource
	 * @param point
	 * @return   
	 * @return List<PrintItem<?>>  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public List<PrintItem<?>> addItem(PrintElementSource printSource, Point point);

	/**
	 * @Description: �õ���ǰ�������
	 * @return   
	 * @return Point  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public Point getLocation();

	/**
	 * @Description: �������ID
	 * @param parentId   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setParentId(int parentId);

	/**
	 * @Description: ����X������
	 * @param x   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "X")
	public void setX(int x);

	/**
	 * @Description: ����Y������
	 * @param y   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "Y")
	public void setY(int y);

	/**
	 * @Description: ��������
	 * @param x
	 * @param y   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setLocation(int x, int y);

	/**
	 * @Description: ���������ק�¼�
	 * @param newHandler   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setTransferHandler(TransferHandler newHandler);

	/**
	 * @Description: ������������
	 * @param l   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void addMouseListener(MouseListener l);

	/**
	 * @Description: ���������С
	 * @param width
	 * @param height   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setSize(double width, double height);

	/**
	 * @Description: ���ñ���ɫ
	 * @param bg   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void setBackground(Color bg);

	public Color getBackground();

	/**
	 * @Description: �������ƶ�����
	 * @param l   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public void addMouseMotionListener(MouseMotionListener l);

	/**
	 * @Description: �õ������С
	 * @return   
	 * @return Dimension  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public Dimension getSize();

	/**
	 * @Description: �õ��ϼ�����
	 * @return   
	 * @return Container  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	public Container getParent();

	/**
	 * @Description: �豸��ID,������Դ
	 * @param tableName   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "TABLE")
	public void setTableId(String tableId);

	public String getTableId();

	/**
	 * @Description: �����Ƿ�ѭ��
	 * @param circulation   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��8��
	 */
	@DataColumn(dataColumn = "CIRCULATION")
	public void setCirculation(boolean circulation);

	public boolean isCirculation();

	/**
	 * @Description: �Ƿ��Զ�����
	 * @param autoStretch   
	 * @return void  
	 * @throws
	 * @author yyq
	 * @date 2018��1��7��
	 */
	@DataColumn(dataColumn = "AUTO_STRETCH")
	public void setAutoStretch(boolean autoStretch);

	public boolean getAutoStretch();
}
