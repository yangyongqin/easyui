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
	 * 版本号
	 */
	private static final long serialVersionUID = 5641302705552761116L;
	/**
	 * //可能是编号，也可能是ID，所以得object类型，
	 * 所有编号和ID都能独立当主键的，ID必须是BigDecimal类型，编号String类型
	 */
	private Object queryKey;
	private StorageDataSet dataset;
//	private RecordSet recordSet;
	//主分隔面板
	private JSplitPane mainSplitPane;
	private JPanel rightPanel;
	private JTabbedPane  leftTabbedPane;//左边的备选和保存的方案
	private JTabbedPane rightTabbedPane;//右边的查询字段

	//所有条件面板
	private JPanel allfilterPanel;
	private JPanel schemePanel;//方案面板
//	DefaultListModel<QueryRecord> allFIlterListModel = new DefaultListModel<QueryRecord>();

	private JList<Object> schemeList;//方案list，留给后一期
	private JScrollPane allfilterScrollPane;
	private JScrollPane schemeScrollPane;//方案面板
	//查询方案
	//基本，高级
	private JPanel basicPanel;
	private JSplitPane seniorJPanel;

	//线程
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
	//左边条件树
	JList<Object> filterList=new JList<Object>();
	DefaultListModel<Object> filterModel=new DefaultListModel<Object>();

	private JButton sysChemeButton;//保存方案
	private JButton userChemeButton;//保存方案
	private JButton configButton;//查询
	private JButton cancelButton;//取消
	private JPanel operatePanel;//操作面板


	HashMap<String, Object> filterMap ;

	public	QueryDialog(Object queryKey,StorageDataSet dataset){
		this.queryKey=queryKey;
		this.dataset=dataset;
		this.setModal(true);
		init();//初始化方法
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				//初始化界面显示
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
	//初始化界面
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
					//双击则触发添加事件，未实现明天接着写
					List<Object> codeList=filterList.getSelectedValuesList();
					if(codeList.size()>0){
						//循环调用添加大方法
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
		leftTabbedPane.addTab("候选条件",allfilterScrollPane);


		schemePanel=new JPanel();
		JScrollPane schemeJScrollPane=new JScrollPane(schemePanel);
		schemeScrollPane=new JScrollPane(schemeJScrollPane);
		leftTabbedPane.addTab("查询方案",schemeScrollPane);




		//右边
		basicPanel=new JPanel();
		seniorJPanel=new JSplitPane();
//		JScrollPane 	basicScrollPane=new JScrollPane(basicPanel);
		rightTabbedPane.addTab("查询条件", basicPanel);

		JScrollPane 	seniorScrollPane=new JScrollPane(seniorJPanel);
		rightTabbedPane.addTab("高级", seniorScrollPane);

		seniorJPanel.setResizeWeight(1);//设置 额外空间左边全部占有
		//根据数据集，生成选项
		conditionTreePanel = new ConditionTreePanel();
		seniorJPanel.setLeftComponent(conditionTreePanel);
		sortListPanel = new SortListPanel();
		seniorJPanel.setRightComponent(sortListPanel);


		sysChemeButton=new JButton();
		sysChemeButton.setText("另存为系统方案");

		userChemeButton=new JButton();
		userChemeButton.setText("另存为用户方案");
		configButton=new JButton();
		configButton.setText("查询");
		configButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//先赋值，然后再取消
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
		cancelButton.setText("取消");
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
		//根据key去查找对应的信息
		SysQueryTemplate queryTemp = (SysQueryTemplate)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(SysQueryTemplate.class);
		VariantHolder<Object> datas=new VariantHolder<Object>();
		datas.value=new RecordSet[]{new RecordSet(),new RecordSet()};
		VariantHolder<String>errMsg =new VariantHolder<String>();

		if(	queryTemp.get(null, queryKey, datas, errMsg)){
			//解析。生成对应的查询按钮
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
					//添加到右边备选条件里面，供其他组件使用
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
		//如果是基本的被选中

		//如果是普通模式
		return filterMap;
	}

}
