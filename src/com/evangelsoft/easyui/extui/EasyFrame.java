package com.evangelsoft.easyui.extui;

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

import org.apache.commons.lang.StringUtils;

public class  EasyFrame extends JInternalFrame
{
	private static final long serialVersionUID = -5057014492015629504L;

	public EasyFrame()
	{
		this("", false, false, false, false);
	}

	public EasyFrame(String paramString)
	{
		this(paramString, false, false, false, false);
	}

	public EasyFrame(String paramString, boolean paramBoolean)
	{
		this(paramString, paramBoolean, false, false, false);
	}

	public EasyFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2)
	{
		this(paramString, paramBoolean1, paramBoolean2, false, false);
	}

	public EasyFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
	{
		this(paramString, paramBoolean1, paramBoolean2, paramBoolean3, false);
	}

	public EasyFrame(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
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

	public static JDialog showAsDialog(Component paramComponent, final JInternalFrame paramJInternalFrame,String title, boolean paramBoolean)
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
		if(StringUtils.isEmpty(title)){
			localJDialog.setTitle(paramJInternalFrame.getTitle());
		}else{
			localJDialog.setTitle(title);
		}
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


	public static JFrame showAsDialog(Component paramComponent,final JPanel paramJInternalFrame,String title,boolean paramBoolean)
	{
		Container localContainer = null;
		if (paramComponent != null)
			for (localContainer = paramComponent.getParent(); localContainer != null; localContainer = localContainer.getParent())
				if (((localContainer instanceof JDialog)) || ((localContainer instanceof JFrame)))
					break;
		final JFrame localJDialog;
		if (localContainer == null)
			localJDialog = new JFrame();
		else if ((localContainer instanceof JDialog))
			localJDialog = new JFrame();
		else if ((localContainer instanceof JFrame))
			localJDialog = new JFrame();
		else
			localJDialog = new JFrame();
		localJDialog.setDefaultCloseOperation(0);
		paramJInternalFrame.setVisible(true);
		localJDialog.setTitle(title);
		localJDialog.getContentPane().setLayout(new BorderLayout());
		localJDialog.getContentPane().add(paramJInternalFrame, "Center");
		localJDialog.pack();
		localJDialog.setLocationRelativeTo(paramComponent);
		localJDialog.setVisible(true);
		return localJDialog;
	}

}