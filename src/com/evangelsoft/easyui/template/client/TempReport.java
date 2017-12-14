package com.evangelsoft.easyui.template.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.borland.dx.dataset.DataSet;
import com.evangelsoft.easyui.type.PrintAction;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.workbench.clientutil.JRDataSetDataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class TempReport {

	private HashMap<String, Object> map;
	private DataSet dataset;


	 
	public static ActionListener buildReportAction(RecordSet set){
		if(set!=null&&set.recordCount()>0){
			JPopupMenu menu=new JPopupMenu();
			for(int i=0;i<set.recordCount();i++){
				JMenuItem item=	new JMenuItem();
				final Record record=set.getRecord(i);

				item.setText(record.getField("PRINT_NAME").getString());
			}
		}
		return null;
	}
	public class MenuItemAction extends AbstractAction{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				PrintMenuItem item=(PrintMenuItem)e.getSource();
				ByteArrayInputStream inStream = new ByteArrayInputStream(item.getRecord().getField("XML").getBytes());
				//			JRDataSource dataSource = new JRBeanCollectionDataSource(dataset);
				JasperPrint jp=	JasperFillManager.fillReport(inStream, map, new JRDataSetDataSource(dataset));
				//			jp = JasperFillManager.fillReport(jr, map, dataSource);
				//´òÓ¡Ô¤ÀÀ
				if(PrintAction.PRINT.equals( item.getRecord().getField("ACTION"))){
					JasperPrintManager.printReport(jp, false);
				}else{
					JasperViewer.viewReport(jp);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	MenuItemAction menuItemAction=new MenuItemAction();
	public class PrintMenuItem extends JMenuItem{
		Record record;
		PrintMenuItem(Record record){
			this.record=record;
			this.setAction(menuItemAction);
		}
		public Record getRecord() {
			return record;
		}
		public void setRecord(Record record) {
			this.record = record;
		}
	}
	//	@Override
	//	public void actionPerformed(ActionEvent e) {
	//		
	//	}

	//	public static ParameterParser defaultParameterParser = null;
	//
	//
	//	public TempReport(String arg0, HashMap<String, String> arg1, HashMap<String, Object> arg2, RecordSet arg3) {
	//		super(arg0, arg1, arg2, arg3);
	//	}
	//	public static ActionListener buildReportAction(final JButton paramJButton, String paramString, String[] paramArrayOfString, final Builder paramBuilder, final Controller paramController, final Accumulator paramAccumulator)
	//	{
	//		ArrayList localArrayList1 = new ArrayList();
	//		ArrayList localArrayList2 = new ArrayList();
	//		Object localObject2;
	//		Object localObject4;
	//		try
	//		{
	//			String str1 = Locale.getDefault().toString();
	//			localObject2 = paramString + ".rpl";
	//			String str2 = paramString + "_" + str1 + ".rpl";
	//			localObject4 = Report.class.getClassLoader().getResourceAsStream(str2);
	//			if (localObject4 == null)
	//				localObject4 = Report.class.getClassLoader().getResourceAsStream((String)localObject2);
	//			if (localObject4 == null)
	//				return null;
	//			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader((InputStream)localObject4));
	//			String str3;
	//			while ((str3 = localBufferedReader.readLine()) != null)
	//			{
	//				final HashMap localHashMap = new HashMap();
	//				String str8;
	//				for (String str4 : str3.split(";"))
	//				{
	//					str4 = str4.trim();
	//					if (str4.length() != 0)
	//					{
	//						int m = str4.indexOf('=');
	//						if (m > 0)
	//						{
	//							String str7 = str4.substring(0, m).trim();
	//							str8 = str4.substring(m + 1).trim();
	//							if ((str8.length() > 3) && (str8.substring(0, 3).equalsIgnoreCase("$P{")) && (str8.charAt(str8.length() - 1) == '}') && (defaultParameterParser != null))
	//								str8 = defaultParameterParser.get(str8.substring(3, str8.length() - 1));
	//							localHashMap.put(str7, str8);
	//						}
	//					}
	//				}
	//				final String str4 = (String)localHashMap.get("name");
	//				String str5 = (String)localHashMap.get("caption");
	//				str5 = (str5 == null) || (str5.length() == 0) ? str4 : str5;
	//				String str6 = (String)localHashMap.get("reportFile");
	//				String xxx = (String)localHashMap.get("action");
	//				boolean bool = BoolStr.getBoolean((String)localHashMap.get("accumulative"));
	//				int n = (str4 != null) && (str4.length() > 0) && (str6 != null) && (str6.length() > 0) && (xxx!= null) && (((String)xxx).length() > 0) ? 1 : 0;
	//				if ((n != 0) && (paramArrayOfString != null))
	//				{
	//					n = 0;
	//					for (String str8x : paramArrayOfString)
	//						if (str8x.equalsIgnoreCase(str4))
	//						{
	//							n = 1;
	//							break;
	//						}
	//				}
	//				if (n != 0)
	//				{
	//					localArrayList1.add(str5);
	//					localArrayList2.add(new ActionListener()
	//					{
	//						@Override
	//						public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	//						{
	//							Object localObject;
	//							if ((!this.C) && (paramBuilder != null))
	//							{
	//								localObject = new Report.Executor()
	//								{
	//									public int execute()
	//									{
	//										Report localReport = Report.buildReport(this.D, this.E);
	//										if (localReport == null)
	//											throw new RuntimeException(Report.A().getString("MSG_NO_REPORT_BUILT"));
	//										localReport.run(this.E);
	//										return 0;
	//									}
	//								};
	//								if (paramController == null)
	//									((Report.Executor)localObject).execute();
	//								else
	//									paramController.execute((Report.Executor)localObject);
	//							}
	//							else if ((this.C) && (paramAccumulator != null))
	//							{
	//								localObject = new Report.Executor()
	//								{
	//									public int execute()
	//									{
	//										Report localReport = Report.buildReport(this.B, this.A);
	//										if (localReport == null)
	//											throw new RuntimeException(Report.A().getString("MSG_NO_REPORT_BUILT"));
	//										localReport.run(this.A);
	//										return 0;
	//									}
	//								};
	//								paramAccumulator.execute(str4, (Report.Executor)localObject);
	//							}
	//						}
	//
	//					});
	//				}
	//			}
	//		}
	//		catch (Throwable localThrowable)
	//		{
	//			localThrowable.printStackTrace();
	//		}
	//		if (localArrayList2.size() == 0)
	//			return null;
	//		Object localObject1;
	//		if (localArrayList2.size() == 1)
	//		{
	//			localObject1 = new ActionListener()
	//			{
	//				public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	//				{
	//					((ActionListener)Report.this.get(0)).actionPerformed(null);
	//				}
	//			};
	//		}
	//		else
	//		{
	//			localObject2 = new JPopupMenu();
	//			for (int i = 0; i < localArrayList2.size(); i++)
	//			{
	//				localObject4 = new JMenuItem();
	//				((JMenuItem)localObject4).setText((String)localArrayList1.get(i));
	//				((JMenuItem)localObject4).addActionListener((ActionListener)localArrayList2.get(i));
	//				((JPopupMenu)localObject2).add((JMenuItem)localObject4);
	//			}
	//			localObject1 = new ActionListener()
	//			{
	//				public void actionPerformed(ActionEvent paramAnonymousActionEvent)
	//				{
	//					Report.this.show(paramJButton, 0, paramJButton.getHeight());
	//				}
	//			};
	//		}
	//		if (paramJButton != null)
	//			if (paramJButton.getAction() != null)
	//			{
	//				localObject2 = null;
	//				for (Object localObject3 = paramJButton; localObject3 != null; localObject3 = ((Container)localObject3).getParent())
	//					if (((localObject3 instanceof Window)) || ((localObject3 instanceof Applet)) || ((localObject3 instanceof JInternalFrame)))
	//					{
	//						localObject2 = localObject3;
	//						break;
	//					}
	//				if ((localObject2 != null) && ((localObject2 instanceof JComponent)))
	//				{
	//					localObject3 = ((JComponent)localObject2).getInputMap(1);
	//					((InputMap)localObject3).put(KeyStroke.getKeyStroke(80, 2), "PRINT");
	//					localObject4 = ((JComponent)localObject2).getActionMap();
	//					((ActionMap)localObject4).put("PRINT", paramJButton.getAction());
	//				}
	//			}
	//			else
	//			{
	//				paramJButton.addActionListener((ActionListener)localObject1);
	//			}
	//		return localObject1;
	//	}
	//	public static abstract interface Accumulator extends Report.Builder
	//	{
	//		public abstract void execute(String paramString, Report.Executor paramExecutor);
	//	}
}
