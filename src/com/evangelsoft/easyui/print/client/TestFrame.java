package com.evangelsoft.easyui.print.client;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class TestFrame {
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setUndecorated(true);

//		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		frame.setLocation(0,0);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

//		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
	}
}
