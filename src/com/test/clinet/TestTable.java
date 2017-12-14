package com.test.clinet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TestTable extends JFrame {

    private static final long serialVersionUID = -3606700961317828681L;

    private JTable rowHeaderTable = null;

    private DefaultTableModel rowHeaderTableModel = null;

    private JTable table = null;

    private DefaultTableModel tableModel = null;

    public TestTable() {
        super("Test");
        init();
    }

    private void init() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        this.getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel getSouthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addCaseRow();
            }
        });
        JButton delBtn = new JButton("Delete");
        delBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeCaseRows(table.getSelectedRows());
            }
        });
        panel.add(addBtn);
        panel.add(delBtn);
        return panel;
    }

    private JScrollPane getCenterPanel() {
        rowHeaderTable  = new JTable(getRowHeaderTableModel(), getRowHeaderTableColumnModel());
//        rowHeaderTable.getTableHeader().setReorderingAllowed(false);
//        rowHeaderTable.setColumnSelectionAllowed(false);
//        rowHeaderTable.getTableHeader().setReorderingAllowed(false);
//        rowHeaderTable.getTableHeader().setResizingAllowed(false);
//        rowHeaderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        rowHeaderTable.setEnabled(false);
        JViewport jv = new JViewport();
        jv.setView(rowHeaderTable);
        int height = new Double(rowHeaderTable.getMaximumSize().getHeight()).intValue();
        jv.setPreferredSize(new Dimension(70, height));
        JScrollPane scrollPanel_main = new JScrollPane();
        table = new JTable(getTableModel(), getTableColumnModel());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPanel_main.getViewport().add(table);
        scrollPanel_main.setRowHeader(jv);
        scrollPanel_main.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderTable.getTableHeader());
        return scrollPanel_main;
    }

    private DefaultTableModel getRowHeaderTableModel() {
        rowHeaderTableModel = new DefaultTableModel(0, 3) {

            private static final long serialVersionUID = 1986093475481012619L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return rowHeaderTableModel;
    }

    private DefaultTableModel getTableModel() {
        tableModel = new DefaultTableModel(0, 2) {

            private static final long serialVersionUID = 3325101167983738710L;

            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        return tableModel;
    }

    private TableColumnModel getTableColumnModel() {
        TableColumnModel columnModel = new DefaultTableColumnModel();
        for (int i = 0; i < 3; i++) {
            columnModel.addColumn(getTableColumns()[i]);

        }
        return columnModel;
    }

    private TableColumn[] getTableColumns() {
        TableColumn[] tableColumns = new TableColumn[3];

        tableColumns[0] = new TableColumn(0, 200, null, null);
        tableColumns[0].setHeaderValue("Test1");

        tableColumns[1] = new TableColumn(1, 200, null, null);
        tableColumns[1].setHeaderValue("Test2");

        tableColumns[2] = new TableColumn(1, 200, null, null);
        tableColumns[2].setHeaderValue("Test3");

        return tableColumns;
    }

    private TableColumnModel getRowHeaderTableColumnModel() {
        DefaultTableColumnModel rowHeaderColumnModel = new DefaultTableColumnModel();
        rowHeaderColumnModel.addColumn(getRowNumberColumn());

       TableColumn rowNumberColumn = new TableColumn(0, 20, null, null);
        rowNumberColumn.setHeaderValue("ID2");
        rowHeaderColumnModel.addColumn(rowNumberColumn);

        TableColumn rowNumberColumn3 = new TableColumn(0, 30, null, null);
        rowNumberColumn3.setHeaderValue("ID3");
        rowHeaderColumnModel.addColumn(rowNumberColumn3);

        return rowHeaderColumnModel;
    }

    private TableColumn getRowNumberColumn() {
        TableColumn rowNumberColumn = new TableColumn(0, 20, null, null);
        rowNumberColumn.setHeaderValue(" ");
        return rowNumberColumn;
    }

    private void addCaseRow() {
        int rowId = tableModel.getRowCount() + 1;
        Vector<String> headerVC = new Vector<String>();
        headerVC.add(String.valueOf(rowId));
        headerVC.add("2");
        headerVC.add("3");
        Vector<String> dataVC = new Vector<String>();
        dataVC.add("Test1:" + String.valueOf(rowId));
        dataVC.add("Test2:" + String.valueOf(rowId));
        rowHeaderTableModel.addRow(headerVC);
        tableModel.addRow(dataVC);
    }

    private void removeCaseRows(int[] rowIndex) {
        if (rowIndex.length > 0) {
            Arrays.sort(rowIndex);
            for (int i = rowIndex.length - 1; i >= 0; i--) {
                tableModel.removeRow(rowIndex[i]);
                rowHeaderTableModel.removeRow(rowIndex[i]);
            }
        }
        updateRowHeader();
    }

    private void updateRowHeader() {
        for (int i = 0; i < rowHeaderTableModel.getRowCount(); i++) {
            rowHeaderTableModel.setValueAt(i + 1, i, 0);
        }
    }

    public static void main(String[] args) {
        new TestTable();
    }

}