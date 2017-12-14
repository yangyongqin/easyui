package com.evangelsoft.easyui.print.client;  

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.template.client.StorageDataSetPanel;


public class PrintPreviewFrame {
	private String frameType;// 表单类型
	/**
	 * @Fields dataSetPanelMap : 数据集合
	 */
	private StorageDataSet detailDateSet;	
	private HashMap<String, StorageDataSetPanel> dataSetPanelMap;
	public static void main(String[] args) {
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		//屏幕的物理大小还需要知道屏幕的dpi 意思是说一英寸多少个象素
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);
	}
}
