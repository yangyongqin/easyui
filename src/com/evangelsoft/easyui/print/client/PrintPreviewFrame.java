package com.evangelsoft.easyui.print.client;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.evangelsoft.easyui.print.type.PrintDesignManage;

public class PrintPreviewFrame extends JFrame {

	// 工具条
	private JPanel toolBarPanel;

	private JComboBox<Integer> zoomBox;

	private JButton expandButton;// 放大

	private JButton narrowButton;// 缩小

	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	public PrintPreviewFrame(PrintDesignManage printMange) {

	}

	/**
	 * @Description: 初始化的方法   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018年1月10日
	 */
	void init() {
		toolBarPanel = new JPanel();
		zoomBox = new JComboBox<Integer>();
		zoomBox.addItem(25);
		zoomBox.addItem(50);
		zoomBox.addItem(75);
		zoomBox.addItem(100);

		zoomBox.addItem(125);
		zoomBox.addItem(150);
		zoomBox.addItem(175);
		zoomBox.addItem(200);
		zoomBox.addItem(250);
		zoomBox.addItem(300);
		zoomBox.addItem(400);
		zoomBox.addItem(800);

		expandButton = new JButton();
		expandButton.setText("放大");

		narrowButton = new JButton("缩小");
	}

	public static void main(String[] args) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();
		// 屏幕的物理大小还需要知道屏幕的dpi 意思是说一英寸多少个象素
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);
	}

}
