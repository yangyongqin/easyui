package com.evangelsoft.easyui.print.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;


/**
 * ClassName: PrintPreviewFrame
 * @Description: 打印预览显示
 * @author yangyq02
 * @date 2017年09月11日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintPreviewPanel extends JPanel{
	/**
	 * @Fields serialVersionUID : 版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @Fields dataSetPanelMap : 数据集合
	 */
	private HashMap<String, PrintStorageDataSet> dataSetPanelMap=new HashMap<String, PrintStorageDataSet>();

	/**
	 * @Fields headPanel : 顶部，工具栏
	 */
	private JPanel headPanel;
	/**
	 * @Fields centrePanel : 中间面板
	 */
	private JPanel centrePanel;
	/**
	 * @Fields viewPanel : 中间用来显示的Panel
	 */
	private JPanel viewPanel;//要重新用个类来单独来处理。。。。

	private JScrollPane viewPane;
	private JPanel viewManagePane;
	//底部面板
	protected JPanel footerPanel;

	//打印模板
	private JLabel templateLabel;
	//模板
	private JComboBox<String>templateBox;
	//显示大小百分比
	private JLabel zoomLabel;
	private JComboBox<Integer> zoomBox;
	//放大
	private JButton expandButton;// 放大
	//缩小
	private JButton narrowButton;// 缩小
	//打印机设置
	private JButton printConfigButton;//打印机设置
	//打印
	private JButton printButton;
	//打印
	private JButton printViewButton;
	//导出pdf
	private JButton pdfButton;
	//导出execl
	private JButton execlButton;

	private JToolBar mainToolBar;



	public PrintPreviewPanel(){
		initialization();
	}

	/**
	 * @Description: 初始化方法
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017年10月11日
	 */
	void initialization(){

		this.setLayout(new BorderLayout());
		headPanel=new JPanel();
		this.add(headPanel,BorderLayout.NORTH);

		centrePanel=new JPanel();
		this.add(centrePanel);
		viewPanel=new JPanel();
		footerPanel=new JPanel();
		this.add(footerPanel,BorderLayout.SOUTH);
		templateLabel=new JLabel();
		templateLabel.setText("模板:");
		templateBox=new JComboBox<String>();
		zoomLabel=new JLabel();
		zoomLabel.setText("缩放比率:");
		zoomBox=new JComboBox<Integer>();
		expandButton=new JButton("放大");
		narrowButton=new JButton("缩小");
		printConfigButton=new JButton("打印机设置");
		printViewButton=new JButton("打印预览");
		printButton=new JButton("打印");

		pdfButton=new JButton("导出pdf");
		execlButton=new JButton("导出execl");

		headPanel.add(templateLabel);
		headPanel.add(templateBox);
		headPanel.add(zoomLabel);
		headPanel.add(zoomBox);
		headPanel.add(expandButton);
		headPanel.add(narrowButton);
		headPanel.add(printConfigButton);
		headPanel.add(printViewButton);
		headPanel.add(printButton);
		headPanel.add(pdfButton);
		headPanel.add(execlButton);


		viewManagePane=new JPanel();
//		viewManagePane.setLayout(new BorderLayout());

		viewPanel =new JPanel();
		viewPanel.setBackground(SystemColor.WHITE);
		viewPanel.setPreferredSize(new Dimension(597, 844));
		viewPanel.setLayout(null);
		viewManagePane.add(viewPanel);
		viewPane=new JScrollPane(viewManagePane);
		viewPane.setBorder(null);
		centrePanel.setLayout(new BorderLayout());
		centrePanel.add(viewPane);

	}
	public static void main(String[] args) {
		/*Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		//屏幕的物理大小还需要知道屏幕的dpi 意思是说一英寸多少个象素
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);*/
		JFrame frame=new JFrame();
		frame.add(new PrintPreviewPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
