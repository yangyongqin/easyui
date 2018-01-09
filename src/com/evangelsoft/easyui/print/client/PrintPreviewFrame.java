package com.evangelsoft.easyui.print.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JFrame;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.print.type.PrintDesignManage;
import com.evangelsoft.easyui.template.client.StorageDataSetPanel;

public class PrintPreviewFrame extends JFrame {

	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;

	public PrintPreviewFrame(PrintDesignManage printMange) {

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
