/*
 * Created on 2005-12-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.evangelsoft.easyui.template.client.nc;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;


/**
 * @author ljian
 */
public class HintMsgPanel extends JPanel {
    private JScrollPane hintMsgPanel = null;
    private JTextArea msgarea = null;

    public HintMsgPanel() {
        super();
        initialize();
    }

    private void initialize() {
        setName("StatusBar");
        setLayout(new BorderLayout());
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        setPreferredSize(new Dimension(300, 60));
        add(getHintMsgPanel(), BorderLayout.CENTER);
    }

    private JScrollPane getHintMsgPanel()
    {
        if (hintMsgPanel == null)
        {
            hintMsgPanel = new JScrollPane();
            msgarea = new JTextArea();
            msgarea.setLineWrap(true);
            msgarea.setEditable(false);
            hintMsgPanel.setViewportView(msgarea);
            hintMsgPanel.setPreferredSize(new java.awt.Dimension(100, 60));
            hintMsgPanel.setFont(new java.awt.Font("dialog", 0, 12));
//            hintMsgPanel.setMinimumSize(new java.awt.Dimension(32, 16));
        }
        return hintMsgPanel;
    }

    private JTextArea getMsgTextArea()
    {
    	if(msgarea==null)
    	{
    		msgarea = new JTextArea();
    	}
    	return msgarea;
    }


    public void setHintMessage(String msg)
    {
//    	getMsgTextArea().setForeground(Style.getColor("ÌבÊ¾"));
        setMessage(StringUtil.recoverWrapLineChar(msg));
    }

    private void setMessage(final String msg) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	getMsgTextArea().setText(msg);
            	getMsgTextArea().paintImmediately(getMsgTextArea().getBounds());
            }
        });
    }
}