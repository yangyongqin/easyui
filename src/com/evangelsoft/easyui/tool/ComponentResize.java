package com.evangelsoft.easyui.tool;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.evangelsoft.easyui.print.client.PrintElementItem;

/**
 * @author yyq
 *组件缩放工具类
 */
public class ComponentResize {

	public static double PERCENT = 4;


	public static int getRealValue(int value){
		return (int) (value * PERCENT);
	}

	public static Rectangle getRealValue(Rectangle value){
		value.x = (int) (value.x * PERCENT);
		value.y = (int) (value.y * PERCENT);
		value.height = (int) (value.height * PERCENT);
		value.width = (int) (value.width * PERCENT);
		return value;
	}

	public static Rectangle getRealValue(int x, int y, int width, int height) {
		Rectangle rectangle = new Rectangle();
		rectangle.x = (int) (x * PERCENT);
		rectangle.y = (int) (y * PERCENT);
		rectangle.height = (int) (height * PERCENT);
		rectangle.width = (int) (width * PERCENT);
		return rectangle;
	}

	public static void reSetSize(Container container,double size,double realRatio){
		ComponentResize.PERCENT=size;

		if( container instanceof  PrintElementItem){
			//按比例更精确一点
			PrintElementItem item=(PrintElementItem)container;
			//			reSetSizeJLabel((JLabel)container,size,realRatio);
			PERCENT=realRatio;
			item.setRatio(realRatio);


			item.setNewBounds(item.getRealX()*realRatio,item.getRealY()*realRatio ,item.getRealWidth()*realRatio,item.getRealHeight()*realRatio);
			//			reSetSizeJLabel(item,size,realRatio);
			reSetSizeFont(item,size,realRatio);
			//			Dimension dimension = item.getPreferredSize();
			//			container.setPreferredSize(new Dimension( ComponentResize.getRealValue((int)(dimension.getWidth()+0.5)),ComponentResize.getRealValue((int)(dimension.getHeight()+0.5))));

			//			if(item.getRealHeight()<30){
			//			System.out.println(item.getRealHeight());
			//			}

		}else if(container instanceof JTableHeader){
			//不处理，JTable表格已经处理了。。。
		}
		else if(container instanceof JTable){
			JTable table=(JTable)container;
			if(table.getColumnModel().getColumnCount()>0){
				for(int i=0;i<table.getColumnModel().getColumnCount();i++){
					TableColumn column = table.getColumnModel().getColumn(i);
					//					DefaultTableCellRenderer renderer=(DefaultTableCellRenderer)column.getHeaderRenderer();
					column.setPreferredWidth(((int)(column.getPreferredWidth()*PERCENT+0.5)));
				}
				/*reSetSizeFont(table.getTableHeader(),size,realRatio);*/
				table.getTableHeader().setPreferredSize(new Dimension( (int)(table.getTableHeader().getPreferredSize().width*PERCENT+0.5),  ((int)(table.getTableHeader().getPreferredSize().height*PERCENT))));
				//				table.getTableHeader().add
				table.setRowHeight((int)(table.getHeight()*PERCENT+0.5));
			}
		}
		else if(container instanceof JScrollBar ){
			JScrollBar bar=(JScrollBar)container;
			bar.setPreferredSize(new Dimension( ((int)(bar.getPreferredSize().width*PERCENT)),((int)(bar.getPreferredSize().height*PERCENT))));
		}else if(container instanceof  CellRendererPane ){
			CellRendererPane bar=(CellRendererPane)container;
			bar.setPreferredSize(new Dimension( ((int)(bar.getPreferredSize().width*PERCENT)),((int)(bar.getPreferredSize().height*PERCENT))));
		}
		else if(container instanceof DefaultTableCellRenderer){
			//如果是表格 表头。
			//components[i].setBounds(ComponentResize.getRealValue(components[i].getBounds()));

			DefaultTableCellRenderer renderer=(DefaultTableCellRenderer)container;
			//			int width=(int)(renderer.getWidth()*PERCENT);
			renderer.setPreferredSize(new Dimension( ((int)(renderer.getWidth()*PERCENT)), ((int)(renderer.getHeight()*PERCENT))));
		}
		else if(container instanceof JScrollPane ){
			Component[] components = container.getComponents();
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JViewport){
					reSetSize((Container)components[i],size,realRatio);
				}
			}
		}else if(container instanceof JViewport){
			Component[] components = container.getComponents();
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof Container){
					reSetSize((Container)components[i],size,realRatio);
				}
			}
		}

		else{
			container.setBounds(ComponentResize.getRealValue(container.getBounds()));
			Dimension dimension = container.getPreferredSize();
			container.setPreferredSize(new Dimension( ComponentResize.getRealValue((int)(dimension.getWidth()+0.5)),ComponentResize.getRealValue((int)(dimension.getHeight()+0.5))));
			Component[] components = container.getComponents();


			for (int i = 0; i < components.length; i++) {
				if(container instanceof  PrintElementItem){

				}else if(components[i] instanceof  JComponent){
					reSetSizeFont((JComponent) components[i],size,realRatio);
				}

				if (!(container instanceof  PrintElementItem) &&components[i] instanceof JLabel){
					reSetSizeJLabel((JLabel)components[i],size,realRatio);
				}
				else if (components[i] instanceof Container){
					reSetSize((Container)components[i],size,realRatio);
				}else  if (components[i] instanceof Component){
					components[i].setBounds(ComponentResize.getRealValue(components[i].getBounds()));
				}
			}
		}
	}

	public static void reSetSizeFont(JComponent jComponent,double size,double realRatio){
		Font font=	jComponent.getFont();
		if(jComponent instanceof  PrintElementItem){
			//按比例更精确一点
			PrintElementItem item=(PrintElementItem)jComponent;
			item.setRatio(realRatio);
			item.setFont( new Font(font.getName(),font.getStyle(),(int)(item.getFontSize()*realRatio)));

		}else if(jComponent instanceof  JComponent){
			//			reSetSizeFont(jComponent,size,realRatio);
			jComponent.setFont(  new Font(font.getName(),font.getStyle(),(int)(font.getSize()*size)));
		}

		//		if( jComponent instanceof  PrintElementItem){
		//		}else{
		//		jComponent.setFont(reSetSizeFont(jComponent.getFont(), size, realRatio));
		//		}
	}

	public static void reSetSizeJLabel(JLabel jLabel,double size,double realRatio){
		reSetSize(jLabel,size,realRatio);
		if(jLabel.getIcon() instanceof ImageIcon){
			ImageIcon icon = (ImageIcon) jLabel.getIcon();
			Image image = icon.getImage();
			icon.setImage(reSetSizeImage(image,icon.getImageObserver()));
		}
	}

	public static Image reSetSizeImage(Image image,ImageObserver obs){
		return image = image.getScaledInstance(
				(int)(image.getWidth(obs)*PERCENT), (int)(image.getHeight(obs)*PERCENT), Image.SCALE_DEFAULT);
	}

	//	public static Font reSetSizeFont(Font defaultfont,double size,double realRatio){
	//	return  new Font(defaultfont.getName(),defaultfont.getStyle(),(int)(defaultfont.getSize()*PERCENT+0.5));
	//	}
	public static void main(String args[]){
		JFrame jf=new JFrame("test");
		jf.setBounds(100,100,200,300);
		JPanel jp=new JPanel();
		jp.setLayout(null);   //此处为关键点，不能有任何布局管理器
		JButton btn=new JButton("开始");
		btn.setBounds(100, 100, 100, 50);
		jp.add(btn);

		JLabel label=new JLabel("daicy");
		label.setBounds(5, 5, 100, 50);
		label.setBorder(BorderFactory.createLineBorder(Color.red, 3));
		jp.add(label);

		jf.add(jp);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ComponentResize.reSetSize(jf,3,3);
	}

}