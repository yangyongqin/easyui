package com.evangelsoft.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class AddRemoveCells implements ActionListener {

	JTable table = null;

	DefaultTableModel defaultModel = null;

	public AddRemoveCells() {
		JFrame f = new JFrame();
		String[] name = { "�ֶ� 1", "�ֶ� 2", "�ֶ� 3", "�ֶ� 4", "�ֶ� 5" };
		String[][] data = new String[5][5];

		defaultModel = new DefaultTableModel(data, name);
		table = new JTable(defaultModel);
		table.setPreferredScrollableViewportSize(new Dimension(400, 80));
		JScrollPane s = new JScrollPane(table);

		JPanel panel = new JPanel();
		JButton b = new JButton("������");
		panel.add(b);
		b.addActionListener(this);
		b = new JButton("������");
		panel.add(b);
		b.addActionListener(this);
		b = new JButton("ɾ����");
		panel.add(b);
		b.addActionListener(this);
		b = new JButton("ɾ����");
		panel.add(b);
		b.addActionListener(this);

		Container contentPane = f.getContentPane();
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(s, BorderLayout.CENTER);

		f.setTitle("AddRemoveCells");
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("������")) {
			defaultModel.addColumn("������");
			TableColumnModel tcm = table.getColumnModel();
			TableColumn tc = tcm.getColumn(tcm.getColumnCount() - 1);
			tc.setCellRenderer(new GenderRenderer());
			tc.setCellEditor(new GenderEditor());
		}
		if (e.getActionCommand().equals("������"))
			defaultModel.addRow(new Vector());
		if (e.getActionCommand().equals("ɾ����")) {
			int columncount = defaultModel.getColumnCount() - 1;
			Component com = table.getComponentAt(0, columncount);
			if (columncount >= 0) { // ��columncount<0�����Ѿ�û���κ����ˡ�
				TableColumnModel columnModel = table.getColumnModel();
				TableColumn tableColumn = columnModel.getColumn(columncount);
				tableColumn.getCellRenderer();
				// tableColumn.setCellRenderer(cellRenderer);
				columnModel.removeColumn(tableColumn);
				defaultModel.setColumnCount(columncount);
			}
		}
		if (e.getActionCommand().equals("ɾ����")) {
			int rowcount = defaultModel.getRowCount() - 1;// getRowCount����������rowcount<0�����Ѿ�û���κ����ˡ�
			if (rowcount >= 0) {
				defaultModel.removeRow(rowcount);
				defaultModel.setRowCount(rowcount);
			}
		}
		table.revalidate();
	}

	public static void main(String args[]) {
		new AddRemoveCells();
	}
}