package com.evangelsoft.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * 展示使用渲染器让表格中偶数行的颜色为蓝色，奇数行不变
 * 
 * @author Administrator
 * 
 */
public class Test11 extends JFrame {

    Object[][] p = { { "王鹏", "91", "100", "191" },
            { "朱学莲", "82", "100", "182" }, { "梅婷", "47", "100", "147" },
            { "赵龙", "61", "100", "161" }, { "李兵", "90", "100", "190" }, };
    String[] n = { "姓名", "语文", "数学", "总分" };

    // 界面组件----------------
    // 中底层滚动面板
    private JScrollPane scroPanel = new JScrollPane();
    // 列表默认TableModel
    private DefaultTableModel model;
    private JTable table;

    public Test11() {
        config();
        addListener();
        confcolor();
    }

    /**
     * 方法：界面构建
     */
    private void config() {
        table = new JTable(model = new DefaultTableModel(p, n));
        DefaultTableCellRenderer ter = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {

                // 设置偶数行颜色
                if (row % 2 == 0) {
                    setBackground(Color.white);
                }
                // 设置奇数行颜色
                else if (row % 2 == 1) {
                    setBackground(new Color(206, 231, 255));
                }
                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
            }
        };
        for (int i = 0; i <= 3; i++) {
            table.getColumn(n[i]).setCellRenderer(ter);
        }
        scroPanel.getViewport().setBackground(Color.WHITE);
        scroPanel.getViewport().add(table);
        // 总体界面布局----------------------
        getContentPane().add(scroPanel, BorderLayout.CENTER);

    }

    /**
     * 方法：界面显示
     */
    private void confcolor() {
        setTitle("编辑器的测试");
        setSize(500, 400);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension frameSize = this.getPreferredSize();
        setLocation(screenSize.width / 2 - (frameSize.width) / 2,
                screenSize.height / 2 - (frameSize.height / 2));
        setResizable(false);
        setVisible(true);

    }

    /**
     * 添加事件监听addListener()
     */
    private void addListener() {
        this.addWindowListener(new WindowAdapter() {
            // 添加窗口关闭事件
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    /**
     * 程序入口main()
     * 
     * @param args
     */
    public static void main(String[] args) {
        new Test11();
    }

}