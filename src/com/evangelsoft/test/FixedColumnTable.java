package com.evangelsoft.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;


/**
 * 锁定列
 * 使用两个表来实现锁定列的功能
 * @author wuqi
 *
 */
public class FixedColumnTable extends JPanel {

	private static final long serialVersionUID = -7028815661978466642L;
	private Object[][] data;   //数据
	private Object[] column;   //列标题
	private JTable fixedTable,table; 
	private int columnFixedIndex; //冻结列的索引
	private boolean isNeedFixed=true; //是否需要冻结
	private JScrollPane scroll;  
	private JViewport viewport;

	public FixedColumnTable(Object[][] _data,Object[] _column,int _columnFixedIndex){
		super();
		this.data=_data;
		this.column=_column;
		//根据冻结列判断是否需要冻结表格
		if(_columnFixedIndex<=0){
			columnFixedIndex=0;
			isNeedFixed=false;
		}else if(_columnFixedIndex>=_column.length){
			columnFixedIndex=0;
			isNeedFixed=false;
		}else{
			columnFixedIndex=_columnFixedIndex;
		}
		init();



	}


	private void init(){
		fixedTable=new JTable(fixedModel){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				super.valueChanged(e);
				checkSelection(true);
			}
		};
		table=new JTable(model){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				super.valueChanged(e);
				checkSelection(false);
			}
		};
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane();
		if(isNeedFixed){
			fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			viewport = new JViewport();
			viewport.setView(fixedTable);
			viewport.setPreferredSize(fixedTable.getPreferredSize());
			scroll.setRowHeaderView(viewport);
			scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER,fixedTable.getTableHeader());
			scroll.setViewportView(table);
		}else{
			//有滚动条
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			scroll.setViewportView(table);
		}
		/*this.setLayout(new MigLayout("inset 2","[grow]","[grow]"));
	    this.add(scroll,"growx,growy");*/
		this.add(scroll);
		this.setVisible(true);

	}

	/**
	 * 检查表格的选择事件
	 * 在冻结表格中选中冻结表格中某行时，非冻结部分表格也选中相同行
	 * 在非冻结表格中选中某行时，如果与冻结表格选中索引不一致时，去掉冻结表格中的选中
	 * @param isFixedTable：方法调用是否来自固定表格
	 */
	private void checkSelection(boolean isFixedTable){
		int fixedSelectedIndex = fixedTable.getSelectedRow();
		int      selectedIndex = table.getSelectedRow();
		if(isFixedTable){
			if(fixedSelectedIndex>=0){
				table.setRowSelectionInterval(fixedSelectedIndex,fixedSelectedIndex);
			}
		}else{
			if(selectedIndex!=fixedSelectedIndex){
				fixedTable.clearSelection();
			}
		}

	} 
	AbstractTableModel fixedModel=new AbstractTableModel(){
		@Override
		public String getColumnName(int _column) {
			// TODO Auto-generated method stub
			return (String)column[_column];
		}
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnFixedIndex;
		}
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return data[rowIndex][columnIndex];
		}
	};
	AbstractTableModel model=new AbstractTableModel(){
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			data[rowIndex][columnIndex+columnFixedIndex]=aValue;
		}
		@Override
		public String getColumnName(int _column) {
			// TODO Auto-generated method stub
			return (String)column[_column+columnFixedIndex];
		}
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return column.length-columnFixedIndex;
		}
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return data[rowIndex][columnIndex+columnFixedIndex];
		}
	};
	public static void main(String[] args) {
		JFrame frame=new JFrame();
//		frame.getContentPane().setLayout();  

		Object[][] data =  new Object[][]{  
				{"1","11","A","","","","","","","",""},  
				{"2","22","","B","","","","","","",""},  
				{"3","33","","","C","","","","","",""},  
				{"4","44","","","","D","","","","","I"},  
				{"5","55","","","","","E","","","H",""},  
				{"6","66","","","","","","F","G","",""},  
				{"7","66","","","","","","F","G","",""},  
				{"8","66","","","","","","F","G","",""},  
				{"9","66","","","","","","F","G","",""},  
				{"10","66","","","","","","F","G","",""},  
				{"11","66","","","","","","F","G","",""},  
				{"12","66","","","","","","F","G","",""},  
				{"13","66","","","","","","F","G","",""},  
				{"14","66","","","","","","F","G","",""},  
				{"15","66","","","","","","F","G","",""}};  
		Object[] column = new Object[]{"fixed 1","fixed 2","a","b","c","d","e","f","g","h","i"};  

		FixedColumnTable test=new FixedColumnTable(data,column,2);  
		frame.getContentPane().add(test);
		frame.setVisible(true);
	}
}
