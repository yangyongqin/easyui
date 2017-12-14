package com.evangelsoft.easyui.template.client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.evangelsoft.easyui.template.client.nc.DefaultTableHeaderCellRenderer;
import com.evangelsoft.easyui.type.FieldRecord;
import com.evangelsoft.econnect.dataformat.CodeValue;
import com.evangelsoft.swing.JDatePicker;
import com.evangelsoft.swing.JDateTimePicker;
import com.evangelsoft.swing.JTimePicker;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-20 下午06:42:17
@version 1.0   * @since    */
public class PagePanel extends JScrollPane {
	/**
	 *
	 */
	public static String STRING_ID="PAGE_TYPE";
	public static String TABLE_TYPE="TABLE";
	public static String PANEL_TYPE="PANEL";
	protected JPanel panel=new JPanel();
	protected String type;
	protected UIScrollPane tableScrollPane;
	protected JTable table;
	//	StorageDataSet storageDataSet;//数据仓库、

	protected static final long serialVersionUID = -5660215293354264715L;
	public static int STATIC_COL=3;
	protected String pageId;
	protected String pageName;
	protected  Icon icon;
	protected String tip;
	protected String tableId;
	protected int colNum=STATIC_COL;//每行显示元素数量
	protected String pos;

	protected int index;

	GridBagLayout layout = new GridBagLayout();
	//	protected Map<String ,UIComponent> componentMap=new HashMap<String ,UIComponent>();
	//存放面板SortedMap，有序
	protected LinkedHashMap<String, UIComponent> componentMap=new LinkedHashMap<String, UIComponent>();
	//只允许之类使用的构造方法
	protected PagePanel(){
		super();
	}

	protected JLabel bothLabel=new JLabel();//填充JLabel，使其需要使用组件显示上面
	int lastCol;
	public PagePanel(CodeValue codevalue,String type,String tableId,String pos){
		this.tableId=tableId;

		this.type=type;
		if(codevalue!=null){
			pageId=codevalue.code.toString();
			pageName=codevalue.value.toString();
		}
		table=new JTable();
		//		storageDataSet=new StorageDataSet();//数据仓库
		tableScrollPane=new UIScrollPane(table,pageId);
		tableScrollPane.setPreferredSize(new Dimension(500,150));
		if(TABLE_TYPE.equals(this.getType())){

			//			table.addMouseListener(this);
			//			table.getColumnModel().addColumn(new TableColumn());
			//			table.removeMouseListener(table);
			this.setViewportView(tableScrollPane);
			//			this.removeMouseListener(table);

		}else{
			this.setViewportView(panel);
		}

	}

	//	return super.add(comp);
	//	}
	public UIComponent add(FieldRecord record) throws Exception{
		//		if(TABLE_TYPE.equals(this.type)){
		TableColumn column=	new TableColumn();
		column.setHeaderValue(record.getField("COLUMN_NAME").getString());
		DefaultTableHeaderCellRenderer cellRendere = new DefaultTableHeaderCellRenderer(record.getField("TABLE_ID").getString(),record.getField("COLUMN_ID").getString());
		column.setHeaderRenderer(cellRendere);
		column.setPreferredWidth(100);
		//			table.getColumnModel().addColumn();
		TableColumnModel model = table.getColumnModel();
		model.addColumn(column);
		this.table.setColumnModel(model);
		//		}
		if(componentMap.containsKey(record.getField("TABLE_ID").getString()+"#"+record.getField("COLUMN_ID").getString())){
			return null;
		}
		int lastCol=1;
		int allIndex=1;
		//		this.removeAll();//全部清空，重绘
		int width=1;
		for(UIComponent com:sortItem() ){
			width=com.getWidth();
			lastCol=lastCol+com.getWidth();
			allIndex=allIndex+com.getWidth();
			if(lastCol>colNum){
				allIndex=allIndex-(lastCol-colNum)+1;
				lastCol=1;
			}
		}
		int row=  (int) Math.ceil((allIndex+0.0)/colNum);
		int[] rowHeights=new int[row*2+1];

		for(int i=0;i<row*2+1;i++){
			rowHeights[i]=5;
		}
		int col=colNum*3+1;

		int[] columnWidths=new int[col];
		for(int i=0;i<col;i++){
			columnWidths[i]=5;
		}
		layout.rowHeights=rowHeights;
		layout.columnWidths=columnWidths;
		this.panel.setLayout(layout);
		ULabel label=new ULabel(this.pageId,record.getField("COLUMN_ID").getString());
		label.setText(record.getField("COLUMN_NAME").getString()+":");

		for(int i=0;i<colNum;i++){
			this.panel.add(new JLabel(),
					new GridBagConstraints((3*i)+2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		}
		width=record.getField("COL")==null ||record.getField("COL").getNumber()==null || record.getField("COL").getNumber().compareTo(BigDecimal.ZERO)==0?1:record.getField("COL").getNumber().intValue();
		//		JTextField field=new JTextField();
		JComponent field=null;

		String type=record.getField("VIEW_TYPE")==null||record.getField("VIEW_TYPE").getString()==null?UIComponent.TEXT:record.getField("VIEW_TYPE").getString();
		//判断显示方式，如果下拉
		if(UIComponent.BOOL.equals(type)){
			field=new JCheckBox();
		}else if(UIComponent.COMBO.equals(type)){
			//如果是下拉框
			field=new JComboBox<Object>();
		}else if(UIComponent.DATE.equals(type)){
			//如果是日期
			field =new JDatePicker();
		}
		else if(UIComponent.DATATIME.equals(type)){
			//时间戳
			field =new JDateTimePicker();
		}else if(UIComponent.IMAGE.equals(type)){
			//图片暂时没写，后面加后面加，记得记得
		}else if(UIComponent.OBJECT.equals(type)){
			//object暂时没写，后面加后面加，记得记得
		}else if(UIComponent.PASS_TEXT.equals(type)){
			field=new JPasswordField();
		}else if(UIComponent.REFERENCE.equals(type)){
			field=new ReferencePanel();
		}else if(UIComponent.TEXT.equals(type)){
			field=new JTextField();
		}else if(UIComponent.TEXTAREA.equals(type)){
			field=new JTextArea();
		}else if(UIComponent.TIME.equals(type)){
			field=new JTimePicker();
		}else{
			field=new JTextField();
		}



		this.panel.add(label, new GridBagConstraints(lastCol*3-2, row*2-1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		this.panel.add(field,
				new GridBagConstraints(lastCol*3-1, row*2-1, width*3-2, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));

		this.panel.add(bothLabel,
				new GridBagConstraints(lastCol*3-1, row*2, width*3-2, 1, 1.0, 1,GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));
		this.updateUI();
		UIComponent com=new UIComponent();
		com.setLable(label);
		com.setComponent(field);
		com.setSequence(getMaxSequence()+1);
		com.setWidth(width);
		componentMap.put(this.getTableId()+"#"+record.getField("COLUMN_ID").getString(), com);
		return com;
	}
	public UIComponent remove(String attrId) {

		TableColumnModel model = table.getColumnModel();
		TableColumn column=model.getColumn(getIndex(attrId));
		model.removeColumn(column);
		this.table.setColumnModel(model);
		UIComponent com=componentMap.remove(attrId);
		this.panel.remove(com.getLable());//删除组件
		this.panel.remove(com.getComponent());//删除组件
		//表格还要删除列


		this.updateUI();
		return com;
	}
	public void clear(){
		componentMap.clear();
		this.panel.removeAll();
		this.panel.updateUI();
	}

	protected int getMaxSequence(){
		int sequence=0;
		for(UIComponent com:componentMap.values() ){
			if(sequence<com.getSequence()){
				sequence=com.getSequence();
			}
		}
		return sequence;
	}
	protected UIComponent[] sortItem(){
		UIComponent[] coms=componentMap.values().toArray(new UIComponent[0]);
		for(int i=0;i<coms.length-1;i++){
			if(coms[i].getSequence()>coms[i+1].getSequence()){
				UIComponent temp=coms[i];
				coms[i]=coms[i+1];
				coms[i+1]=temp;
			}
		}
		return coms;
	}
	public void sort(){
		UIComponent[] coms=componentMap.values().toArray(new UIComponent[0]);
		for(int i=0;i<coms.length-1;i++){
			if(coms[i].getSequence()>coms[i+1].getSequence()){
				UIComponent temp=coms[i];
				coms[i]=coms[i+1];
				coms[i+1]=temp;
			}
		}
		this.panel.removeAll();
		int lastCol=1;
		int allIndex=1;

		for(int i=0;i<colNum;i++){
			this.panel.add(new JLabel(),
					new GridBagConstraints((3*i)+2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		}
		int row=1;
		for(int i=0;i<coms.length;i++){
			int width=coms[i].getWidth();
			lastCol=lastCol+coms[i].getWidth();
			allIndex=allIndex+coms[i].getWidth();
			if(lastCol>colNum){
				allIndex=allIndex-(lastCol-colNum)+1;
				lastCol=1;
			}
			lastCol=lastCol+coms[i].getWidth();
			row=  (int) Math.ceil((allIndex+0.0)/colNum);
			this.panel.add(coms[i].getLable(), new GridBagConstraints(lastCol*3-2, row*2-1, 1, 1,
					0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
			this.panel.add(coms[i].getComponent(),
					new GridBagConstraints(lastCol*3-1, row*2-1, width*3-2, 1, 1.0, 0.0,GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		}
		this.panel.add(bothLabel,
				new GridBagConstraints(lastCol*3-1, row*2, 1, 1, 1.0, 1,GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));
	}
	public synchronized void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		tableScrollPane.addMouseListener(l);
		this.table.getTableHeader().addMouseListener(l);
	}


	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	//	public Component getComponentl() {
	//	return componentl;
	//	}
	//	public void setComponentl(Component componentl) {
	//	this.componentl = componentl;
	//	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}


	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}



	public LinkedHashMap<String, UIComponent> getComponentMap() {
		return componentMap;
	}

	public void setComponentMap(LinkedHashMap<String, UIComponent> componentMap) {
		this.componentMap = componentMap;
	}

	public int getIndex(String key){
		int index=0;
		if(key!=null&&key.trim().length()>0){
			for(String k:componentMap.keySet()){
				if(key.equals(k)){
					return index;
				}
				index++;
			}
		}
		return -1;
	}

}
