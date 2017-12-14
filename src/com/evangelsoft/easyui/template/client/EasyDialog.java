package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class  EasyDialog extends JInternalFrame
{
	private static final long serialVersionUID = -5057014492015629504L;

	public EasyDialog()
	{
		this("", false, false, false, false);
	}

	public EasyDialog(String paramString)
	{
		this(paramString, false, false, false, false);
	}

	public EasyDialog(String paramString, boolean paramBoolean)
	{
		this(paramString, paramBoolean, false, false, false);
	}

	public EasyDialog(String paramString, boolean paramBoolean1, boolean paramBoolean2)
	{
		this(paramString, paramBoolean1, paramBoolean2, false, false);
	}

	public EasyDialog(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
	{
		this(paramString, paramBoolean1, paramBoolean2, paramBoolean3, false);
	}

	public EasyDialog(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
	{
		super(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
	}

	public void showModal()
	{
		showModal(null);
	}

	public JDialog showModal(JInternalFrame paramJInternalFrame)
	{
		return showAsDialog(null, this);
	}

	public static JDialog showAsDialog(final JDialog localJDialog, JInternalFrame paramJInternalFrame)
	{
	 	return showAsDialog(localJDialog, paramJInternalFrame, true);
	}

	public static JDialog showAsDialog(final JDialog localJDialog, final JInternalFrame paramJInternalFrame, boolean paramBoolean)
	{
		localJDialog.setDefaultCloseOperation(0);
		((BasicInternalFrameUI)paramJInternalFrame.getUI()).setNorthPane(null);
		paramJInternalFrame.setBorder(null);
		InternalFrameAdapter local1 = new InternalFrameAdapter()
		{
			public void internalFrameClosed(InternalFrameEvent paramAnonymousInternalFrameEvent)
			{
				localJDialog.dispose();
				paramJInternalFrame.removeInternalFrameListener(this);
			}
		};
		paramJInternalFrame.addInternalFrameListener(local1);
		paramJInternalFrame.setVisible(true);
		localJDialog.setTitle(paramJInternalFrame.getTitle());
		localJDialog.getContentPane().setLayout(new BorderLayout());
		localJDialog.getContentPane().add(paramJInternalFrame, "Center");
		localJDialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent paramAnonymousWindowEvent)
			{
				paramJInternalFrame.doDefaultCloseAction();
				if (!localJDialog.isVisible())
					localJDialog.dispose();
			}
		});
		paramJInternalFrame.addInternalFrameListener(local1);
		localJDialog.setModal(paramBoolean);
		localJDialog.pack();
//		localJDialog.setLocationRelativeTo(paramComponent);
		localJDialog.setVisible(true);
		return localJDialog;
	}

	//  public static void showAsDialog(Component paramComponent,final JInternalFrame paramJInternalFrame, boolean paramBoolean)
	//	{
	//		Container localContainer = null;
	//		if (paramComponent != null)
	//			for (localContainer = paramComponent.getParent(); localContainer != null; localContainer = localContainer.getParent())
	//				if (((localContainer instanceof JDialog)) || ((localContainer instanceof JFrame)))
	//					break;
	//		final JDialog localJDialog;
	//		if (localContainer == null)
	//			localJDialog = new JDialog();
	//		else if ((localContainer instanceof JDialog))
	//			localJDialog = new JDialog((JDialog)localContainer);
	//		else if ((localContainer instanceof JFrame))
	//			localJDialog = new JDialog((JFrame)localContainer);
	//		else
	//			localJDialog = new JDialog();
	//		localJDialog.setDefaultCloseOperation(0);
	//		((BasicInternalFrameUI)paramJInternalFrame.getUI()).setNorthPane(null);
	//		paramJInternalFrame.setBorder(null);
	//		localJDialog.addWindowListener(new WindowAdapter(){
	//			public void windowClosing(WindowEvent paramAnonymousWindowEvent)
	//			{
	//				paramJInternalFrame.doDefaultCloseAction();
	//				if (!localJDialog.isVisible())
	//					localJDialog.dispose();
	//			}
	//		});
	//		paramJInternalFrame.setVisible(true);
	//		localJDialog.setTitle(paramJInternalFrame.getTitle());
	//		localJDialog.getContentPane().setLayout(new BorderLayout());
	//		localJDialog.getContentPane().add(paramJInternalFrame, "Center");
	//		InternalFrameAdapter local1 = new InternalFrameAdapter()
	//		{
	//			public void internalFrameClosed(InternalFrameEvent paramAnonymousInternalFrameEvent)
	//			{
	//				localJDialog.dispose();
	//				paramJInternalFrame.removeInternalFrameListener(this);
	//			}
	//		};
	//		paramJInternalFrame.addInternalFrameListener(local1);
	//		localJDialog.setModal(paramBoolean);
	//		localJDialog.pack();
	//		localJDialog.setLocationRelativeTo(paramComponent);
	//		localJDialog.setVisible(true);
	//	}
	public static void showAsDialog(Component paramComponent,final JPanel paramJInternalFrame,String title,boolean paramBoolean)
	{
		Container localContainer = null;
		if (paramComponent != null)
			for (localContainer = paramComponent.getParent(); localContainer != null; localContainer = localContainer.getParent())
				if (((localContainer instanceof JDialog)) || ((localContainer instanceof JFrame)))
					break;
		final JDialog localJDialog;
		if (localContainer == null)
			localJDialog = new JDialog();
		else if ((localContainer instanceof JDialog))
			localJDialog = new JDialog((JDialog)localContainer);
		else if ((localContainer instanceof JFrame))
			localJDialog = new JDialog((JFrame)localContainer);
		else
			localJDialog = new JDialog();
		localJDialog.setDefaultCloseOperation(0);
		paramJInternalFrame.setVisible(true);
		localJDialog.setTitle(title);
		localJDialog.getContentPane().setLayout(new BorderLayout());
		localJDialog.getContentPane().add(paramJInternalFrame, "Center");
		localJDialog.setModal(paramBoolean);
		localJDialog.pack();
		localJDialog.setLocationRelativeTo(paramComponent);
		localJDialog.setVisible(true);
	}

}