package com.evangelsoft.easyui.print.client;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.evangelsoft.easyui.print.type.PrintDesignManage;

public class PrintPreviewFrame extends JFrame {

	// ������
	private JPanel toolBarPanel;

	private JComboBox<Integer> zoomBox;

	private JButton expandButton;// �Ŵ�

	private JButton narrowButton;// ��С

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;

	public PrintPreviewFrame(PrintDesignManage printMange) {

	}

	/**
	 * @Description: ��ʼ���ķ���   
	 * @return void  
	 * @throws
	 * @author yangyq02
	 * @date 2018��1��10��
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
		expandButton.setText("�Ŵ�");

		narrowButton = new JButton("��С");
	}

	public static void main(String[] args) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();
		// ��Ļ�������С����Ҫ֪����Ļ��dpi ��˼��˵һӢ����ٸ�����
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);
	}

}
