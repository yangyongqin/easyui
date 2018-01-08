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
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;

	public PrintPreviewFrame(PrintDesignManage printMange) {

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
