package com.evangelsoft.easyui.template.client.nc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import com.borland.dx.dataset.StorageDataSet;
import com.evangelsoft.easyui.template.client.DesignFrame;
import com.evangelsoft.easyui.template.client.StorageDataSetPanel;
import com.evangelsoft.easyui.template.client.UIComponent;
import com.evangelsoft.easyui.template.client.UITabPanel;
import com.evangelsoft.easyui.template.intf.SysTemplate;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.framebase.MasterDetailFrame;

/**
 *
 * @author ������
 *Company
 *�������Զ�������ʼ��
 *2016��4��27��
 */
public class CustomBaseFrame extends MasterDetailFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private UITabPanel headPane;// ͷ����壬�����ݼ����
	private JSplitPane middlelowerPane;//�������
	private UITabPanel bodyPane;// �м�ѡ�
	private UITabPanel tailPane;//�ײ����
	private JSplitPane leftSplitPane;// ��߷ָ�ϱ��������
//	private JSplitPane mainPane;// ����̳����ָ����
	private JPanel rightPanel;// �ұ߱��ָ����

	private JButton viewButton;
	private JPopupMenu viewMenu;// �鿴
	private JMenuItem headerMaxItem;// ��ͷ���
	private JMenuItem bodyMaxItem;// �������
	private JMenuItem  tailMaxItem;//��β���
	private JMenuItem  defaultItem;//Ĭ��


	public static String CARD_VIEW="CARD_VIEW";//��Ƭģʽ
	public static String LIST_VIEW="LIST_VIEW";
	private JButton viewTypeButton;

	//ҳ������
	private String frameType;
	private Object templateId="0003";//ģ��ID

	//ҳǩmap����
	HashMap<String,StorageDataSetPanel> pageMap=new HashMap<String, StorageDataSetPanel>();
	//���ݼ���
	HashMap<String,StorageDataSet>  stroageDataMap=new HashMap<String, StorageDataSet>();
	HashMap<String,UIComponent> atrrMap=new HashMap<String, UIComponent>();

	public CustomBaseFrame(){
		try{
			this.setPreferredSize(new Dimension(770,620));
			leftSplitPane = new JSplitPane();
			leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			leftSplitPane.setDividerLocation(150);
			leftSplitPane.setOneTouchExpandable(true);
//			leftSplitPane.setResizeWeight(1);
			// �м����
			mainPane = new JSplitPane();
			this.getContentPane().add(mainPane);
			mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// ��ָ�����Ȱ�����ҳ��ֳ���������
			mainPane.setDividerLocation(700);
			mainPane.setOneTouchExpandable(true);
			mainPane.setLeftComponent(leftSplitPane);// �����Ҫ���� ռ���
			rightPanel = new JPanel();
//			rightPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			mainPane.setBottomComponent(rightPanel);
//			rightPanel.setDividerLocation(300);
//			rightPanel.setOneTouchExpandable(true);



			headPane = new UITabPanel();
			headPane.setComponent(listTablePane);
//			this.listTablePane.setViewportView(this.listTable);
//			leftSplitPane.setTopComponent(listTablePane);
//			leftSplitPane.setDividerLocation(150);

			listTable.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2){
						changeViewType(CARD_VIEW);
					}
				}
			});

			bodyPane = new UITabPanel();

			middlelowerPane=new JSplitPane();
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			leftSplitPane.setTopComponent(headPane);
			leftSplitPane.setBottomComponent(middlelowerPane);
			middlelowerPane.setResizeWeight(1);
			middlelowerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			middlelowerPane.setTopComponent(bodyPane);
			middlelowerPane.setDividerLocation(200);

			bodyPane = new UITabPanel();
			middlelowerPane.setTopComponent(bodyPane);
			middlelowerPane.setDividerLocation(200);
			tailPane=new UITabPanel();
			middlelowerPane.setBottomComponent(tailPane);


			viewMenu=new JPopupMenu();
			headerMaxItem=new JMenuItem();
			headerMaxItem.setText("��ͷ���");
			viewMenu.add(headerMaxItem);
			headerMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//��������
					leftSplitPane.setTopComponent(headPane);
					leftSplitPane.setBottomComponent(null);
					leftSplitPane.setDividerSize(0);
					CustomBaseFrame.this.pack();

				}
			});

			bodyMaxItem=new JMenuItem();
			bodyMaxItem.setText("�������");
			bodyMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//��������
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(null);
					middlelowerPane.setTopComponent(bodyPane);
					leftSplitPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(bodyMaxItem);


			tailMaxItem=new JMenuItem();
			tailMaxItem.setText("��β���");
			tailMaxItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//��������
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(null);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(null);
					leftSplitPane.setDividerSize(0);
					middlelowerPane.setDividerSize(0);
				}
			});

			viewMenu.add(tailMaxItem);

			defaultItem=new JMenuItem();
			defaultItem.setText("Ĭ��");
			defaultItem.addActionListener(new  AbstractAction(){
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//��������
					leftSplitPane.setBottomComponent(middlelowerPane);
					leftSplitPane.setTopComponent(headPane);
					middlelowerPane.setBottomComponent(tailPane);
					middlelowerPane.setTopComponent(bodyPane);
					leftSplitPane.setDividerLocation(150);
					//				rightPanel.setDividerLocation(300);
					middlelowerPane.setDividerLocation(200);
					leftSplitPane.setDividerSize(10);
					middlelowerPane.setDividerSize(10);
					CustomBaseFrame.this.pack();
				}
			});
			viewButton=new JButton("��ʾ");
			viewButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					viewMenu.show(viewButton, 0, viewButton.getHeight());
				}
			});
			viewMenu.add(defaultItem);
			mainToolBar.add(viewButton);
			viewTypeButton=new JButton("��Ƭ��ʾ");
			viewTypeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if(viewTypeButton.getText().equals("�б���ʾ")){
						changeViewType(LIST_VIEW);
					}else{
						changeViewType(CARD_VIEW);
					}
				}
			});
			this.mainToolBar.add(viewTypeButton);

		}catch(Exception e){
			e.printStackTrace();
		}
		pack();
	}

	//��ʼ������
	public CustomBaseFrame(Object templateId){
		this();
		this.templateId=templateId;
	}



	@Override
	protected Object prepareData() throws Exception {
		return super.prepareData();
	}
	@Override
	protected void prepared(Object arg0) throws Exception {
		super.prepared(arg0);
		//��ʼ������
		SysTemplate sysTemplate=(SysTemplate)(new RMIProxy(Consumer.getDefaultConsumer().getSession())).newStub(SysTemplate.class);
		VariantHolder<String> errMsg=new VariantHolder<String>();
		VariantHolder<Object> data=new VariantHolder<Object>();
		data.value=new RecordSet[]{new RecordSet(),new RecordSet(),new RecordSet(),new RecordSet()};
		sysTemplate.get(this.templateId,data , errMsg);
		RecordSet[] datas=(RecordSet[])data.value;
//		this.title=datas[0].getRecord(0).getField("TEMPLATE_NAME").getString();
		this.setTitle(datas[0].getRecord(0).getField("TEMPLATE_NAME").getString());
		//���ɶ�Ӧ��ҳǩ
		if(datas[2]!=null&&datas[2].recordCount()>0){
			for(int i=0;i<datas[2].recordCount();i++){
				CodeValue codeVaalue=new CodeValue();
				codeVaalue.code=datas[2].getRecord(i).getField("TABS_ID").getString();
				codeVaalue.value=datas[2].getRecord(i).getField("TABS_NAME").getString();
				StorageDataSetPanel panle=createPagePanel(codeVaalue,datas[2].getRecord(i).getField("POSITION").getString(),datas[2].getRecord(i).getField("TABS_TYPE").getString(),datas[2].getRecord(i).getField("SEQUENCE").getNumber().intValue());
				if(!datas[2].getRecord(i).getField("COL_NUM").isNull()){
					panle.setColNum(datas[2].getRecord(i).getField("COL_NUM").getNumber().intValue());
				}
			}
		}
		//����ҳǩ����
		headPane.setSelectedIndex(0);
		bodyPane.setSelectedIndex(0);
		tailPane.setSelectedIndex(0);
		//�����ֶ�ѡ��
		if(datas[3]!=null&&datas[3].recordCount()>0){
			FieldRecord[] records=FieldRecord.getFields(datas[3],FieldRecord.TABLE_TYPE);
			for(int i=0;i<records.length;i++){
				this.loadItem(pageMap.get(datas[3].getRecord(i).getField("TABS_ID").getString()),records[i]);
			}
		}
		pack();
		linkDetailDataSets();
		HashMap localObject1 = new HashMap();
		localObject1.put(this.masterDataSet, this.masterComponents);
		DataSetHelper.listDataAwareComponents(this.getContentPane(), localObject1);
		DataSetHelper.enableDataAwareComponents(this.masterComponents, false);
		headPane.setViewType(UITabPanel.OTHER);
		headPane.updateUI();
	}
	private StorageDataSetPanel createPagePanel(CodeValue codevalue, String pos,String type,int index){
		//����Ǳ�ͷ���߱�β����ֱ����masterDataSet,�����ϸ���һ����ֱ�Ӵ�detailDataSet����new����
		if(pageMap.containsKey(codevalue.code)){
			return null;
		}
		StorageDataSetPanel panel=null;
		if(pos.equals(DesignFrame.HEAD)){
			panel=new StorageDataSetPanel(codevalue,type,null,pos,masterDataSet,false);
			headPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
		}else if(pos.equals(DesignFrame.BODY)){
			if(index==0){
				//�����ϸ���һ���ǣ�ֱ���ø����detailDataSet
				panel=new StorageDataSetPanel(codevalue,type,pos,this.detailDataSet,true);
			}else{
				panel=new StorageDataSetPanel(codevalue,type,pos,new StorageDataSet(),true);
			}
			bodyPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
		}else if(pos.equals(DesignFrame.TAIL)){
			panel=new StorageDataSetPanel(codevalue,type,null,pos,masterDataSet,false);
			tailPane.addTab(codevalue.code.toString(), codevalue.value.toString(), panel);
			index=tailPane.getComponentCount();
		}
		pageMap.put((String)codevalue.code, panel);
		return panel;
	}
	private UIComponent loadItem(StorageDataSetPanel panel, FieldRecord record) throws Exception{
		if(panel==null||record.getField("COLUMN_ID")==null){
			System.out.println("�п�ֵ��");
		}
		if(atrrMap.containsKey(panel.getPageId()+"#"+record.getField("COLUMN_ID").getString())){
			return null;
		}
		UIComponent uicom=panel.add(record);
		this.atrrMap.put(record.getField("TABS_ID").getString()+"#"+record.getField("COLUMN_ID").getString(),uicom );
		return uicom;
	}
	private void addItem(StorageDataSetPanel panel, FieldRecord[] records) throws Exception{
		for(int i=0;i<records.length;i++){
			FieldRecord record=records[i];
			if(atrrMap.containsKey(panel.getPageId()+"#"+record.getField("COLUMN_ID").getString())){
				break ;
			}
			UIComponent uicom=panel.add(record);
		}
	}
	@Override
	public void pack(){
		super.pack();
		if(rightPanel.getComponentCount()==0){
			this.rightPanel.setVisible(false);
			this.mainPane.setBottomComponent(null);
			this.mainPane.setDividerSize(0);
		}
		if(tailPane.getUseComponentCount()==0){
			this.tailPane.setVisible(false);
			this.middlelowerPane.setBottomComponent(null);
			this.middlelowerPane.setDividerSize(0);
		}else{
			this.tailPane.setVisible(true);
			this.middlelowerPane.setBottomComponent(tailPane);;
			this.middlelowerPane.setDividerSize(1);
		}
	}

	@Override
	protected void linkDetailDataSets() {
		super.linkDetailDataSets();
		//�õ�������ϸ��map

		for(StorageDataSetPanel panel:pageMap.values()){
			if(panel.getStorageDataSet()==masterDataSet){
				continue;
			}
			if(detailDataSet!=panel.getStorageDataSet()){
				detailDataSets.add(panel.getStorageDataSet());
				this.detailLoadings.put(panel.getStorageDataSet(), false);
				this.detailEditables.put(panel.getStorageDataSet(), true);
//				HashMap localObject1 = new HashMap();
//				localObject1.put(panel.getStorageDataSet(), this.detailComponents);
//				DataSetHelper.listDataAwareComponents(this.getContentPane(), localObject1);
////				DataSetHelper.listDataAwareComponents(this.getContentPane(), (HashMap)localObject1);
				HashMap localObject1 = new HashMap();

				HashMap localHashMap = (HashMap)this.detailComponents.get(panel.getStorageDataSet());
				if (localHashMap == null)
				{
					localHashMap = new HashMap();
					this.detailComponents.put(panel.getStorageDataSet(), localHashMap);
				}
				localObject1.put(panel.getStorageDataSet(), localHashMap);
				DataSetHelper.listDataAwareComponents(this.getContentPane(),localObject1);
			}
			detailNewActions.put(panel.getStorageDataSet(), panel.getNewAction());
			detailDeleteActions.put(panel.getStorageDataSet(), panel.getDeleteAction());
			detailClearActions.put(panel.getStorageDataSet(), panel.getClearAction());
			if(panel.getBatchAction()!=null){
				detailBatchActions.put(panel.getStorageDataSet(), panel.getBatchAction());
			}
		}
	}
	protected void changeViewType(String type){
		if(CARD_VIEW.equals(type)){
			viewTypeButton.setText("�б���ʾ");
			headPane.setViewType(UITabPanel.ALL_PANEL);
		}else{
			viewTypeButton.setText("��Ƭ��ʾ");
			headPane.setViewType(UITabPanel.OTHER);
		}
		headPane.updateUI();
	}

	@Override
	protected void showStatus() {
		super.showStatus();
		this.viewTypeButton.setEnabled(!this.cancelButton.isEnabled());
		if(this.cancelButton.isEnabled()){
			changeViewType(CARD_VIEW);
		}
	}


}
