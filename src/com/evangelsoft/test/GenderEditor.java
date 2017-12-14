package com.evangelsoft.test;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

public class GenderEditor extends JComboBox implements TableCellEditor {

	private static final long serialVersionUID = 5860619160549087886L;

	// EventListenerList:����EventListener �б���ࡣ
	private EventListenerList listenerList = new EventListenerList();

	// ChangeEvent����֪ͨ����Ȥ�Ĳ������¼�Դ�е�״̬�ѷ������ġ�
	private ChangeEvent changeEvent = new ChangeEvent(this);

	public GenderEditor() {
		super();
		addItem("��");
		addItem("Ů");
		// ������ֹ�༭�������԰�����Ԫ���JTable�յ���Ҳ���Դӱ༭����������������JComboBox�����
		/*
		 * addActionListener(newActionListener(){ public void
		 * actionPerformed(ActionEvent e) {
		 * System.out.println("ActionListener");
		 * //��ͬstopCellEditing�����ǵ���fireEditingStopped()���� fireEditingStopped();
		 * }
		 * 
		 * });
		 */
	}

	public void addCellEditorListener(CellEditorListener l) {
		listenerList.add(CellEditorListener.class, l);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		listenerList.remove(CellEditorListener.class, l);
	}

	private void fireEditingStopped() {
		CellEditorListener listener;
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == CellEditorListener.class) {
				// ֮������i+1������Ϊһ��ΪCellEditorListener.class��Class���󣩣�
				// ���ŵ���һ��CellEditorListener��ʵ��
				listener = (CellEditorListener) listeners[i + 1];
				// ��changeEventȥ֪ͨ�༭���Ѿ������༭
				// ��editingStopped�����У�JTable����getCellEditorValue()ȡ�ص�Ԫ���ֵ��
				// ���Ұ����ֵ���ݸ�TableValues(TableModel)��setValueAt()
				listener.editingStopped(changeEvent);
			}
		}
	}

	public void cancelCellEditing() {
	}

	/** 
	 * �༭����һ����Ԫ���ٵ����һ����Ԫ��ʱ�����á�-------------���������� 
	 */
	public boolean stopCellEditing() {
		// ����ע�͵������fireEditingStopped();��Ȼ����GenderEditor�Ĺ��캯���а�
		// addActionListener()��ע��ȥ������ʱ������ֹ�༭������JComboBox��ã���
		System.out.println("�༭����һ����Ԫ���ٵ����һ����Ԫ��ʱ�����á�");
		fireEditingStopped();// ������ֹ�༭������JTable���
		return true;
	}

	/** 
	 * Ϊһ����Ԫ���ʼ���༭ʱ��getTableCellEditorComponent������ 
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		boolean isMale =value==null?false: ((Boolean) value).booleanValue();
		setSelectedIndex(isMale ? 0 : 1);
		return this;
	}

	/** 
	 * ѯ�ʱ༭�����Ƿ����ʹ�� anEvent ��ʼ���б༭�� 
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	/** 
	 * ���Ӧ��ѡ�����༭�ĵ�Ԫ���򷵻�true�����򷵻� false�� 
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/** 
	 * ����ֵ���ݸ�TableValue��TableModel���е�setValueAt()���� 
	 */
	public Object getCellEditorValue() {
		return new Boolean(getSelectedIndex() == 0 ? true : false);
	}
}