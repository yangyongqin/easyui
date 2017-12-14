package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.template.intf.SysQueryTemplate;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.client.WireWorker;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.condutil.SortItem;
import com.evangelsoft.econnect.condutil.SortList;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.workbench.clientutil.ConditionItemsHelper;
import com.evangelsoft.workbench.clientutil.SortItemsHelper;
import com.evangelsoft.workbench.clientutil.VisibleWireWorker;
import com.evangelsoft.workbench.panelbase.ConditionTreePanel;
import com.evangelsoft.workbench.panelbase.SortListPanel;
import com.evangelsoft.workbench.types.BoolStr;

public class QueryDialog extends  JDialog{
	/**
	 * �汾��
	 */
	private static final long serialVersionUID = 5641302705552761116L;
	/**
	 * //�����Ǳ�ţ�Ҳ������ID�����Ե�object���ͣ�
	 * ���б�ź�ID���ܶ����������ģ�ID������BigDecimal���ͣ����String����
	 */
	private Object queryKey;
	private StorageDataSet dataset;
//	private RecordSet recordSet;
	//���ָ����
	private JSplitPane mainSplitPane;
	private JPanel rightPanel;
	private JTabbedPane  leftTabbedPane;//��ߵı�ѡ�ͱ���ķ���
	private JTabbedPane rightTabbedPane;//�ұߵĲ�ѯ�ֶ�

	//�����������
	private JPanel allfilterPanel;
	private JPanel schemePanel;//�������
//	DefaultListModel<QueryRecord> allFIlterListModel = new DefaultListModel<QueryRecord>();

	private JList<Object> schemeList;//����list��������һ��
	private JScrollPane allfilterScrollPane;
	private JScrollPane schemeScrollPane;//�������
	//��ѯ����
	//�������߼�
	private JPanel basicPanel;
	private JSplitPane seniorJPanel;

	//�߳�
	protected VisibleWireWorker worker = new VisibleWireWorker();

	/** Panel of sort list. */
	protected SortListPanel sortListPanel;
	/** Panel of condition tree. */
	protected ConditionTreePanel conditionTreePanel;


//	ConditionLeafNodeEditorPanel editor=new ConditionLeafNodeEditorPanel();

	protected ArrayList<ConditionItem> conditionItems = new ArrayList<ConditionItem>();
	/** Sort items available in advanced query mode. */
	protected ArrayList<SortItem> sortItems = new ArrayList<SortItem>();

	protected String[] omitConditionItems = null;


	ConditionTree filterTree;
	protected SortList sortList;
	//���������
	JList<Object> filterList=new JList<Object>();
	DefaultListModel<Object> filterModel=new DefaultListModel<Object>();

	private JButton sysChemeButton;//���淽��
	private JButton userChemeButton;//���淽��
	private JButton configButton;//��ѯ
	private JButton cancelButton;//ȡ��
	private JPanel operatePanel;//�������


	HashMap<String, Object> filterMap ;

	public	QueryDialog(Object queryKey,StorageDataSet dataset){
		this.queryKey=queryKey;
		this.dataset=dataset;
		this.setModal(true);
		init();//��ʼ������
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				//��ʼ��������ʾ
				worker.setWorker(new WireWorker.Worker() {
					public Object work() throws Throwable {
						prepared(prepareData());
						return null;
					}
				});
				worker.start();
			}
		});
		this.setPreferredSize(new Dimension(750,500));
		pack();
	}
	//��ʼ������
	void	init(){
		mainSplitPane=new JSplitPane();
		mainSplitPane.setDividerLocation(160);
		this.getContentPane().add(mainSplitPane);
		leftTabbedPane=new JTabbedPane();
		rightTabbedPane=new JTabbedPane();
		rightPanel=new JPanel();

		mainSplitPane.setLeftComponent(leftTabbedPane);
		mainSplitPane.setRightComponent(rightPanel);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(rightTabbedPane);




		allfilterPanel=new JPanel();
		allfilterPanel.setLayout(new BorderLayout());
		allfilterPanel.add(filterList);
		filterList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2){
					//˫���򴥷�����¼���δʵ���������д
					List<Object> codeList=filterList.getSelectedValuesList();
					if(codeList.size()>0){
						//ѭ��������Ӵ󷽷�
						for(int i=0;i<codeList.size();i++){
							CodeValue codeValue=(CodeValue)codeList.get(i);
							try {
								conditionLeafNodePanel.addItem((Record)codeValue.value);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});


		allfilterScrollPane=new JScrollPane(allfilterPanel);
		leftTabbedPane.addTab("��ѡ����",allfilterScrollPane);


		schemePanel=new JPanel();
		JScrollPane schemeJScrollPane=new JScrollPane(schemePanel);
		schemeScrollPane=new JScrollPane(schemeJScrollPane);
		leftTabbedPane.addTab("��ѯ����",schemeScrollPane);




		//�ұ�
		basicPanel=new JPanel();
		seniorJPanel=new JSplitPane();
//		JScrollPane 	basicScrollPane=new JScrollPane(basicPanel);
		rightTabbedPane.addTab("��ѯ����", basicPanel);

		JScrollPane 	seniorScrollPane=new JScrollPane(seniorJPanel);
		rightTabbedPane.addTab("�߼�", seniorScrollPane);

		seniorJPanel.setResizeWeight(1);//���� ����ռ����ȫ��ռ��
		//�������ݼ�������ѡ��
		conditionTreePanel = new ConditionTreePanel();
		seniorJPanel.setLeftComponent(conditionTreePanel);
		sortListPanel = new SortListPanel();
		seniorJPanel.setRightComponent(sortListPanel);


		sysChemeButton=new JButton();
		sysChemeButton.setText("���Ϊϵͳ����");

		userChemeButton=new JButton();
		userChemeButton.setText("���Ϊ�û�����");
		configButton=new JButton();
		configButton.setText("��ѯ");
		configButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//�ȸ�ֵ��Ȼ����ȡ��
				filterMap=new HashMap<String, Object>();
				if(rightTabbedPane.getSelectedComponent()==basicPanel){
					try{
						filterTree=conditionLeafNodePanel.getTree();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}else{
					filterTree  = conditionTreePanel.getTree();
					sortList = sortListPanel.getList();
				}
				filterMap.put("filter", filterTree);
				filterMap.put("sort", sortList);

				QueryDialog.this.setVisible(false);
			}
		});

		cancelButton=new JButton();
		cancelButton.setText("ȡ��");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				filterMap=null;
				QueryDialog.this.setVisible(false);
			}
		});


		operatePanel=new JPanel();
		operatePanel.add(sysChemeButton);
		operatePanel.add(userChemeButton);
		operatePanel.add(configButton);
		operatePanel.add(cancelButton);
		rightPanel.add(operatePanel,BorderLayout.SOUTH);


//		this.basicPanel.add(editor,
//		new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
//		GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

	}
	protected Object prepareData() throws Exception {
		return null;
	}
	HashMap<String,String> realColumnMap=new HashMap<String,String>();
	HashMap<String,ConditionItem> itemsMap=new HashMap<String,ConditionItem>();
	UIConditionLeafNodePanel conditionLeafNodePanel=null;
	protected void prepared(Object data) throws Exception {
		ConditionItemsHelper.load(conditionItems,
				dataset,omitConditionItems);
		SortItemsHelper.load(sortItems, dataset);
		conditionTreePanel.setItems(conditionItems);
		sortListPanel.setItems(sortItems);
		for(int i=0;i<conditionItems.size();i++){
			itemsMap.put(conditionItems.get(i).name.trim(), conditionItems.get(i));
		}
		//����keyȥ���Ҷ�Ӧ����Ϣ
		SysQueryTemplate queryTemp = (SysQueryTemplate)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(SysQueryTemplate.class);
		VariantHolder<Object> datas=new VariantHolder<Object>();
		datas.value=new RecordSet[]{new RecordSet(),new RecordSet()};
		VariantHolder<String>errMsg =new VariantHolder<String>();

		if(	queryTemp.get(null, queryKey, datas, errMsg)){
			//���������ɶ�Ӧ�Ĳ�ѯ��ť
			RecordSet set=((RecordSet[])datas.value)[1];
			basicPanel.setLayout(new BorderLayout());
			conditionLeafNodePanel=new UIConditionLeafNodePanel(conditionItems,set);
//			conditionLeafNodePanel.setPreferredSize(new Dimension(100,0));
			conditionLeafNodePanel.setSize(new Dimension(100,100));
//			conditionLeafNodePanel.setSize(100, 100);
			JScrollPane scrol=new JScrollPane(conditionLeafNodePanel);
//			scrol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//			scrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			this.basicPanel.add(scrol);
			for(int i=0;i<set.recordCount();i++){
				if(BoolStr.TRUE.equals(set.getRecord(i).getField("IS_ENABLE").getString())){
					//��ӵ��ұ߱�ѡ�������棬���������ʹ��
					final Record record=set.getRecord(i);
					filterModel .add(i,new CodeValue(record.getField("COLUMN_ID").getString(),record){
						@Override
						public String toString() {
							return ((Record)this.value).getField("COLUMN_NAME").getString();
						}
					});
				}
			}
			filterList.setModel(filterModel);
		}
		pack();
	}
	public  HashMap<String,Object> getFilterMap ()throws Exception{
		filterMap=null;
		this.setVisible(true);
		//����ǻ����ı�ѡ��

		//�������ͨģʽ
		return filterMap;
	}

}
