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
 * @Description: ��ӡԤ����ʾ
 * @author yangyq02
 * @date 2017��09��11��
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */

public class PrintPreviewPanel extends JPanel{
	/**
	 * @Fields serialVersionUID : �汾��
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @Fields dataSetPanelMap : ���ݼ���
	 */
	private HashMap<String, PrintStorageDataSet> dataSetPanelMap=new HashMap<String, PrintStorageDataSet>();

	/**
	 * @Fields headPanel : ������������
	 */
	private JPanel headPanel;
	/**
	 * @Fields centrePanel : �м����
	 */
	private JPanel centrePanel;
	/**
	 * @Fields viewPanel : �м�������ʾ��Panel
	 */
	private JPanel viewPanel;//Ҫ�����ø���������������������

	private JScrollPane viewPane;
	private JPanel viewManagePane;
	//�ײ����
	protected JPanel footerPanel;

	//��ӡģ��
	private JLabel templateLabel;
	//ģ��
	private JComboBox<String>templateBox;
	//��ʾ��С�ٷֱ�
	private JLabel zoomLabel;
	private JComboBox<Integer> zoomBox;
	//�Ŵ�
	private JButton expandButton;// �Ŵ�
	//��С
	private JButton narrowButton;// ��С
	//��ӡ������
	private JButton printConfigButton;//��ӡ������
	//��ӡ
	private JButton printButton;
	//��ӡ
	private JButton printViewButton;
	//����pdf
	private JButton pdfButton;
	//����execl
	private JButton execlButton;

	private JToolBar mainToolBar;



	public PrintPreviewPanel(){
		initialization();
	}

	/**
	 * @Description: ��ʼ������
	 * @return void
	 * @throws
	 * @author yangyq02
	 * @date 2017��10��11��
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
		templateLabel.setText("ģ��:");
		templateBox=new JComboBox<String>();
		zoomLabel=new JLabel();
		zoomLabel.setText("���ű���:");
		zoomBox=new JComboBox<Integer>();
		expandButton=new JButton("�Ŵ�");
		narrowButton=new JButton("��С");
		printConfigButton=new JButton("��ӡ������");
		printViewButton=new JButton("��ӡԤ��");
		printButton=new JButton("��ӡ");

		pdfButton=new JButton("����pdf");
		execlButton=new JButton("����execl");

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
		//��Ļ�������С����Ҫ֪����Ļ��dpi ��˼��˵һӢ����ٸ�����
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(dpi);*/
		JFrame frame=new JFrame();
		frame.add(new PrintPreviewPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
