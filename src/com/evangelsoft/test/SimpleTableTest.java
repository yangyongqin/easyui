package com.evangelsoft.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class SimpleTableTest extends JFrame {

	private static final long serialVersionUID = -4172876583187222326L;

	protected JTable table;

	public SimpleTableTest() {
		setTitle("FromCannel_2020's blog(CSDN)");
		setLayout(new BorderLayout());
		final TableValues tv = new TableValues();
		table = new JTable(tv);
		// 设置行宽
		table.setRowHeight(30);

		TableColumnModel tcm = table.getColumnModel();
		TableColumn tc = tcm.getColumn(TableValues.GENDER);
		// 设置“性别”列的单元格渲染器（renderer）
		tc.setCellRenderer(new GenderRenderer());
		tc.setCellEditor(new GenderEditor());

		// 必须把table放入JScrollPane才会有列名出现
		JScrollPane jsp = new JScrollPane(table);
		add(jsp, BorderLayout.CENTER);
		JButton btn = new JButton("增加列");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				TableValues tv = new TableValues();
				tv.addColumn();
				table.setModel(tv);
				table.validate();
				table.repaint();
			}
		});
		this.add(btn,BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		SimpleTableTest t = new SimpleTableTest();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setSize(400, 200);
		t.setVisible(true);
	}
}