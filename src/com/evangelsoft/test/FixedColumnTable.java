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
 * ������
 * ʹ����������ʵ�������еĹ���
 * @author wuqi
 *
 */
public class FixedColumnTable extends JPanel {

	private static final long serialVersionUID = -7028815661978466642L;
	private Object[][] data;   //����
	private Object[] column;   //�б���
	private JTable fixedTable,table; 
	private int columnFixedIndex; //�����е�����
	private boolean isNeedFixed=true; //�Ƿ���Ҫ����
	private JScrollPane scroll;  
	private JViewport viewport;

	public FixedColumnTable(Object[][] _data,Object[] _column,int _columnFixedIndex){
		super();
		this.data=_data;
		this.column=_column;
		//���ݶ������ж��Ƿ���Ҫ������
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
			//�й�����
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			scroll.setViewportView(table);
		}
		/*this.setLayout(new MigLayout("inset 2","[grow]","[grow]"));
	    this.add(scroll,"growx,growy");*/
		this.add(scroll);
		this.setVisible(true);

	}

	/**
	 * ������ѡ���¼�
	 * �ڶ�������ѡ�ж�������ĳ��ʱ���Ƕ��Ჿ�ֱ��Ҳѡ����ͬ��
	 * �ڷǶ�������ѡ��ĳ��ʱ������붳����ѡ��������һ��ʱ��ȥ���������е�ѡ��
	 * @param isFixedTable�����������Ƿ����Թ̶����
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
