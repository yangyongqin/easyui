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
 * չʾʹ����Ⱦ���ñ����ż���е���ɫΪ��ɫ�������в���
 * 
 * @author Administrator
 * 
 */
public class Test11 extends JFrame {

    Object[][] p = { { "����", "91", "100", "191" },
            { "��ѧ��", "82", "100", "182" }, { "÷��", "47", "100", "147" },
            { "����", "61", "100", "161" }, { "���", "90", "100", "190" }, };
    String[] n = { "����", "����", "��ѧ", "�ܷ�" };

    // �������----------------
    // �еײ�������
    private JScrollPane scroPanel = new JScrollPane();
    // �б�Ĭ��TableModel
    private DefaultTableModel model;
    private JTable table;

    public Test11() {
        config();
        addListener();
        confcolor();
    }

    /**
     * ���������湹��
     */
    private void config() {
        table = new JTable(model = new DefaultTableModel(p, n));
        DefaultTableCellRenderer ter = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {

                // ����ż������ɫ
                if (row % 2 == 0) {
                    setBackground(Color.white);
                }
                // ������������ɫ
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
        // ������沼��----------------------
        getContentPane().add(scroPanel, BorderLayout.CENTER);

    }

    /**
     * ������������ʾ
     */
    private void confcolor() {
        setTitle("�༭���Ĳ���");
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
     * ����¼�����addListener()
     */
    private void addListener() {
        this.addWindowListener(new WindowAdapter() {
            // ��Ӵ��ڹر��¼�
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    /**
     * �������main()
     * 
     * @param args
     */
    public static void main(String[] args) {
        new Test11();
    }

}