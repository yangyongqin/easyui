//package com.evangelsoft.easyui.template.client;
//
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.Window;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.beans.Beans;
//import java.lang.reflect.Method;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.Properties;
//
//import javax.swing.AbstractAction;
//import javax.swing.Action;
//import javax.swing.ActionMap;
//import javax.swing.ButtonGroup;
//import javax.swing.ImageIcon;
//import javax.swing.InputMap;
//import javax.swing.JButton;
//import javax.swing.JCheckBoxMenuItem;
//import javax.swing.JDesktopPane;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.JRadioButtonMenuItem;
//import javax.swing.JSpinner;
//import javax.swing.JSplitPane;
//import javax.swing.JTabbedPane;
//import javax.swing.JToggleButton;
//import javax.swing.JToolBar;
//import javax.swing.KeyStroke;
//import javax.swing.SwingUtilities;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.TitledBorder;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.InternalFrameAdapter;
//import javax.swing.event.InternalFrameEvent;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import com.borland.dbswing.JdbTable;
//import com.borland.dbswing.TableScrollPane;
//import com.borland.dx.dataset.DataChangeAdapter;
//import com.borland.dx.dataset.DataChangeEvent;
//import com.borland.dx.dataset.DataRow;
//import com.borland.dx.dataset.DataSet;
//import com.borland.dx.dataset.DataSetAware;
//import com.borland.dx.dataset.DataSetException;
//import com.borland.dx.dataset.DataSetView;
//import com.borland.dx.dataset.EditAdapter;
//import com.borland.dx.dataset.ExceptionEvent;
//import com.borland.dx.dataset.NavigationEvent;
//import com.borland.dx.dataset.NavigationListener;
//import com.borland.dx.dataset.ReadRow;
//import com.borland.dx.dataset.ReadWriteRow;
//import com.borland.dx.dataset.RowStatus;
//import com.borland.dx.dataset.SortDescriptor;
//import com.borland.dx.dataset.StorageDataSet;
//import com.borland.dx.dataset.TdDataSet;
//import com.borland.dx.dataset.Variant;
//import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
//import com.evangelsoft.econnect.DataModel;
//import com.evangelsoft.econnect.client.Consumer;
//import com.evangelsoft.econnect.client.WireWorker;
//import com.evangelsoft.econnect.condutil.ConditionItem;
//import com.evangelsoft.econnect.condutil.ConditionTree;
//import com.evangelsoft.econnect.condutil.SortItem;
//import com.evangelsoft.econnect.condutil.SortList;
//import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
//import com.evangelsoft.econnect.dataformat.Record;
//import com.evangelsoft.econnect.dataformat.RecordFormat;
//import com.evangelsoft.econnect.dataformat.RecordSet;
//import com.evangelsoft.econnect.dataformat.TransientRecordSet;
//import com.evangelsoft.econnect.dataformat.VariantHolder;
//import com.evangelsoft.econnect.rmi.RMIProxy;
//import com.evangelsoft.econnect.session.RemoteException;
//import com.evangelsoft.econnect.util.ExceptionFormat;
//import com.evangelsoft.econnect.util.LaunchPath;
//import com.evangelsoft.workbench.clientdataset.DataSetExceptionAdapter;
//import com.evangelsoft.workbench.clientdataset.DataSetHelper;
//import com.evangelsoft.workbench.clientutil.ConditionItemsHelper;
//import com.evangelsoft.workbench.clientutil.ContainerHelper;
//import com.evangelsoft.workbench.clientutil.SortItemsHelper;
//import com.evangelsoft.workbench.clientutil.VisibleWireWorker;
//import com.evangelsoft.workbench.framebase.FunctionFrame;
//import com.evangelsoft.workbench.panelbase.ConditionTreePanel;
//import com.evangelsoft.workbench.panelbase.SortListPanel;
//import com.evangelsoft.workbench.swing.JInternalDialog;
//
//public class MasterDetailFrame extends FunctionFrame
//{
//	public static int DEFAULT_MAX_ROWS = 1000;
//	protected SelectAction selectAction = new SelectAction();
//	protected CloseAction closeAction = new CloseAction();
//	protected FilterAction filterAction = new FilterAction();
//	protected RefreshAction refreshAction = new RefreshAction();
//	protected SetKeyAction setKeyAction = new SetKeyAction();
//	protected SearchAction searchAction = new SearchAction();
//	protected NewAction newAction = new NewAction();
//	protected SaveAction saveAction = new SaveAction();
//	protected CancelAction cancelAction = new CancelAction();
//	protected DeleteAction deleteAction = new DeleteAction();
//	protected FirstAction firstAction = new FirstAction();
//	protected PriorAction priorAction = new PriorAction();
//	protected NextAction nextAction = new NextAction();
//	protected LastAction lastAction = new LastAction();
//	protected DetailNewAction detailNewAction = new DetailNewAction();
//	protected DetailBatchAction detailBatchAction = new DetailBatchAction();
//	protected DetailDeleteAction detailDeleteAction = new DetailDeleteAction();
//	protected DetailClearAction detailClearAction = new DetailClearAction();
//	protected ListInletAction listInletAction = new ListInletAction();
//	protected FlushInletAction flushInletAction = new FlushInletAction();
//	protected StorageDataSet masterDataSet;
//	protected StorageDataSet detailDataSet;
//	protected JPanel headerPanel;
//	protected JPanel searchPanel;
//	protected JPanel titlePanel;
//	protected JPanel footerExtendedPanel;
//	protected JLabel listRowCountLabel;
//	protected JLabel listRowCountPromptLabel;
//	protected JPanel footerFixedPanel;
//	protected JPanel listFooterPanel;
//	protected JdbTable listTable;
//	protected TableScrollPane listTablePane;
//	protected JPanel formPanel;
//	protected JPanel formFooterPanel;
//	protected JPanel footerPanel;
//	protected JPanel progressPanel;
//	protected JPanel filterSimplePanel;
//	protected JLabel detailRowCountLabel;
//	protected JLabel detailRowCountPromptLabel;
//	protected JPanel detailSummaryExtendedPanel;
//	protected JPanel detailSummaryFixedPanel;
//	protected JButton detailNewButton;
//	protected JButton detailBatchButton;
//	protected JButton detailDeleteButton;
//	protected JButton detailClearButton;
//	protected JPanel detailFooterPanel;
//	protected JdbTable detailTable;
//	protected TableScrollPane detailTablePane;
//	protected JToolBar detailToolBar;
//	protected JPanel detailPanel;
//	protected JTabbedPane detailPane;
//	protected JPanel masterPanel;
//	protected JPanel listPanel;
//	protected JPanel filterPanel;
//	protected JPanel filterConditionsPanel;
//	protected SortListPanel sortListPanel;
//	protected ConditionTreePanel conditionTreePanel;
//	protected JSplitPane filterAdvancedPane;
//	protected JPanel filterModePanel;
//	protected JSplitPane formPane;
//	protected JSplitPane listPane;
//	protected JPanel inletPanel;
//	protected JSplitPane contentPane;
//	protected JSplitPane mainPane;
//	protected JToolBar mainToolBar;
//	protected JButton selectButton;
//	protected JButton closeButton;
//	protected JButton filterButton;
//	protected JButton refreshButton;
//	protected JToggleButton setKeyButton;
//	protected JButton searchButton;
//	protected JButton newButton;
//	protected JButton saveButton;
//	protected JButton cancelButton;
//	protected JButton deleteButton;
//	protected JButton firstButton;
//	protected JButton priorButton;
//	protected JButton nextButton;
//	protected JButton lastButton;
//	protected JPanel mainToolBarControlPanel;
//	protected JPanel mainToolBarControlExtPanel;
//	protected JLabel maxRowsLabel;
//	protected JSpinner maxRowsSpinner;
//	protected JPanel mainToolBarControlPaddingPanel;
//	protected ButtonGroup filterModeGroup = new ButtonGroup();
//	protected JRadioButtonMenuItem filterAdvancedMenuItem;
//	protected JRadioButtonMenuItem filterSimpleMenuItem;
//	protected JCheckBoxMenuItem filterAutoHiddenMenuItem;
//	protected JCheckBoxMenuItem filterMenuItem;
//	protected JPopupMenu filterMenu;
//	protected VisibleWireWorker worker = new VisibleWireWorker();
//	protected Circulator circulator = new Circulator();
//	protected HashMap<DataSetAware, Boolean> masterComponents = new HashMap();
//	protected HashMap<JToolBar, Container> toolBars = new HashMap();
//	protected Class<?> entityClass;
//	protected Object foreignKey = null;
//	protected String[] keyColumns;
//	protected String[] uniqueColumns;
//	protected boolean duplicateChecking = true;
//	protected HashMap<StorageDataSet, String[]> detailKeyColumns = new HashMap();
//	protected HashMap<StorageDataSet, String[]> detailUniqueColumns = new HashMap();
//	protected HashMap<StorageDataSet, Boolean> detailDuplicateChecking = new HashMap();
//	protected boolean canView = true;
//	protected boolean canInsert = true;
//	protected boolean canModify = true;
//	protected boolean canDelete = true;
//	protected ArrayList<StorageDataSet> detailDataSets = new ArrayList();
//	protected HashMap<StorageDataSet, TdDataSet> detailTdDataSets = new HashMap();
//	protected HashMap<StorageDataSet, HashMap<DataSetAware, Boolean>> detailComponents = new HashMap();
//	protected HashMap<StorageDataSet, AbstractAction> detailNewActions = new HashMap();
//	protected HashMap<StorageDataSet, AbstractAction> detailBatchActions = new HashMap();
//	protected HashMap<StorageDataSet, AbstractAction> detailDeleteActions = new HashMap();
//	protected HashMap<StorageDataSet, AbstractAction> detailClearActions = new HashMap();
//	protected HashMap<StorageDataSet, JLabel> detailRowCountLabels = new HashMap();
//	protected boolean masterLoading = false;
//	protected HashMap<StorageDataSet, Boolean> detailLoadings = new HashMap();
//	protected HashMap<StorageDataSet, Boolean> detailEditables = new HashMap();
//	protected boolean inletEnabled = true;
//	protected ConditionTree boundTree = new ConditionTree();
//	protected ConditionTree filterTree = new ConditionTree();
//	protected SortList sortList = new SortList();
//	protected boolean advancedFilterModeEnabled = true;
//	protected boolean maxRowsControlEnabled = true;
//	protected boolean inletVisibleWhenOpened = false;
//	protected ArrayList<ConditionItem> conditionItems = new ArrayList();
//	protected String[] omitConditionItems = null;
//	protected ArrayList<SortItem> sortItems = new ArrayList();
//	protected String[] omitSortItems = null;
//	private boolean O = true;
//	private boolean K = false;
//	protected Object I = null;
//	private boolean H = false;
//	protected RecordSet selections = null;
//	protected boolean refreshWhenOpened = false;
//	private boolean modified = false;
//	private boolean V = false;
//	private int S = 0;
//	private long Q = -1L;
//	private boolean U = true;
//	private Border X;
//	private boolean R = true;
//	private boolean M = true;
//	private boolean N = true;
//	private boolean J = true;
//	private HashMap<String, Properties> L = new HashMap();
//	private HashMap<String, AbstractAction> T = new HashMap();
//	private HashMap<String, Boolean> W = new HashMap();
//	/**
//	 *参数map，用于模板，审批流和功能插件等等
//	 */
//	protected HashMap<String,Object> pranMap=new HashMap<String, Object>();
//
//
//	public MasterDetailFrame()
//	{
//		setBounds(0, 0, 600, 400);
//		D();
//		if (!Beans.isDesignTime())
//			this.searchPanel.setVisible(false);
//		if (Beans.isDesignTime())
//			return;
//		this.maxRowsSpinner.setValue(Integer.valueOf(DEFAULT_MAX_ROWS));
//		InputMap localInputMap = getInputMap(1);
//		localInputMap.put(KeyStroke.getKeyStroke(120, 0), "SELECT");
//		localInputMap.put(KeyStroke.getKeyStroke(82, 2), "REFRESH");
//		localInputMap.put(KeyStroke.getKeyStroke(78, 2), "NEW");
//		localInputMap.put(KeyStroke.getKeyStroke(83, 2), "SAVE");
//		localInputMap.put(KeyStroke.getKeyStroke(90, 2), "CANCEL");
//		localInputMap.put(KeyStroke.getKeyStroke(68, 2), "DELETE");
//		localInputMap.put(KeyStroke.getKeyStroke(36, 2), "FIRST");
//		localInputMap.put(KeyStroke.getKeyStroke(38, 2), "PRIOR");
//		localInputMap.put(KeyStroke.getKeyStroke(40, 2), "NEXT");
//		localInputMap.put(KeyStroke.getKeyStroke(35, 2), "LAST");
//		ActionMap localActionMap = getActionMap();
//		localActionMap.put("SELECT", this.selectAction);
//		localActionMap.put("REFRESH", this.refreshAction);
//		localActionMap.put("NEW", this.newAction);
//		localActionMap.put("SAVE", this.saveAction);
//		localActionMap.put("CANCEL", this.cancelAction);
//		localActionMap.put("DELETE", this.deleteAction);
//		localActionMap.put("FIRST", this.firstAction);
//		localActionMap.put("PRIOR", this.priorAction);
//		localActionMap.put("NEXT", this.nextAction);
//		localActionMap.put("LAST", this.lastAction);
//		this.masterDataSet.setPostUnmodifiedRow(true);
//		this.masterDataSet.addEditListener(new EditAdapter()
//		{
//			public void modifying(DataSet paramDataSet)
//			{
//				if (MasterDetailFrame.this.masterLoading)
//					return;
//				MasterDetailFrame.this.setModified(true);
//			}
//
//			public void adding(DataSet paramDataSet, ReadWriteRow paramReadWriteRow)
//			throws Exception
//			{
//				if (MasterDetailFrame.this.masterLoading)
//					return;
//				MasterDetailFrame.this.validateMaster(paramReadWriteRow, null);
//			}
//
//			public void updating(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
//			throws Exception
//			{
//				if (MasterDetailFrame.this.masterLoading)
//					return;
//				MasterDetailFrame.this.validateMaster(paramReadWriteRow, paramReadRow);
//			}
//		});
//		masterDataSet.addNavigationListener(new NavigationListener() {
//			public void navigated(NavigationEvent event) {
//				if (masterLoading) {
//					return;
//				}
//
//				if ((masterDataSet.getStatus() & RowStatus.INSERTED) != 0) {
//					// When a new row just inserted, getInternalRow returns
//					// the previous row id, so it can't be adopted. This may be
//					// a bug. If data set is sorted, posting may trigger
//					// navigated event. Event under such situation should be
//					// ignored.
//					if (masterDataSet.isEditingNewRow()) {
//						Q = Long.MAX_VALUE;
//
//					} else {
//						Q = masterDataSet.getInternalRow();
//						return;
//					}
//				} else {
//					// Navigated event trigger by sorting should be ignored.
//					long currentRowId = masterDataSet.getInternalRow();
//					if (Q == currentRowId) {
//						return;
//					}
//					Q = currentRowId;
//				}
//
//				if (masterDataSet.isEmpty()
//						|| ((masterDataSet.getStatus() & RowStatus.INSERTED) != 0)) {
//					for (int i = 0; i < detailDataSets.size(); i++) {
//						detailDataSets.get(i).empty();
//						detailDataSets.get(i).reset();
//					}
//					buildDetailUniqueIndexes();
//
//					clearInlet();
//					if ((masterDataSet.getStatus() & RowStatus.INSERTED) == 0) {
//						showStatus();
//					}
//				} else {
//					boolean found = false;
//					try {
//						MasterDetailFrame.this.getClass().getDeclaredMethod(
//								"listInlet", new Class[0]);
//						found = true;
//					} catch (Exception e) {
//					}
//					final boolean hasInlet = found;
//
//					if (detailDataSets.size() > 0) {
//						class Worker implements WireWorker.Worker {
//							private Object key;
//
//							public Worker(Object key) {
//								super();
//								this.key = key;
//							}
//
//							public Object work() throws Throwable {
//								return fetchEntity(key);
//							}
//						}
//
//						class Hook implements WireWorker.Hook {
//							public void hook(Object value) {
//								worker.setHook(null);
//								worker.setCorrector(null);
//								worker.setResumer(null);
//
////								loadEntity(value);
//
//								if (hasInlet) {
//									listInletAction
//									.actionPerformed(new ActionEvent(
//											listInletAction,
//											0,
//											(String) listInletAction
//											.getValue(Action.ACTION_COMMAND_KEY)));
//								} else {
//									showStatus();
//								}
//							}
//						}
//
//						class Corrector implements WireWorker.Corrector {
//							public void correct(Throwable e) {
//								worker.setHook(null);
//								worker.setCorrector(null);
//								worker.setResumer(null);
//
//								showStatus();
//							}
//						}
//
//						class Resumer implements WireWorker.Resumer {
//							public void resume() {
//								worker.setHook(null);
//								worker.setCorrector(null);
//								worker.setResumer(null);
//
//								showStatus();
//							}
//						}
//
//						worker.setWorker(new Worker(buildKey()));
//						worker.setHook(new Hook());
//						worker.setCorrector(new Corrector());
//						worker.setResumer(new Resumer());
//						worker.start();
//					} else {
//						if (hasInlet) {
//							listInletAction
//							.actionPerformed(new ActionEvent(
//									listInletAction,
//									0,
//									(String) listInletAction
//									.getValue(Action.ACTION_COMMAND_KEY)));
//						} else {
//							showStatus();
//						}
//					}
//				}
//			}
//		});
//		this.listTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
//		{
//			public void valueChanged(ListSelectionEvent paramListSelectionEvent)
//			{
//				int[] arrayOfInt = MasterDetailFrame.this.listTable.getSelectedRows();
//				int i = arrayOfInt.length;
//				if ((i > 0) && (arrayOfInt[(i - 1)] >= MasterDetailFrame.this.listTable.getDataSet().rowCount()))
//					return;
//				if ((i > 1) || ((i == 1) && (MasterDetailFrame.this.listTable.getSelectedRow() == paramListSelectionEvent.getFirstIndex()) && (MasterDetailFrame.E(MasterDetailFrame.this) > 1) && (paramListSelectionEvent.getLastIndex() - paramListSelectionEvent.getFirstIndex() + 1 == MasterDetailFrame.E(MasterDetailFrame.this))))
//					MasterDetailFrame.this.showStatus();
//				MasterDetailFrame.B(MasterDetailFrame.this, i);
//			}
//		});
//		addInternalFrameListener(new InternalFrameAdapter()
//		{
//			public void internalFrameOpened(InternalFrameEvent paramInternalFrameEvent)
//			{
//				if (!MasterDetailFrame.D(MasterDetailFrame.this))
//					return;
//				DataSetExceptionAdapter.getDefaultAdapter().registerDataSets(new DataSet[] { MasterDetailFrame.this.masterDataSet }, MasterDetailFrame.this);
//				MasterDetailFrame.this.worker.attachDesktop(MasterDetailFrame.this.getTitle(), 3, MasterDetailFrame.this.progressPanel, new Component[] { MasterDetailFrame.this.mainToolBar, MasterDetailFrame.this.mainPane });
//				MasterDetailFrame.this.linkDetailDataSets();
//				DataSetExceptionAdapter.getDefaultAdapter().registerDataSets((DataSet[])MasterDetailFrame.this.detailDataSets.toArray(new StorageDataSet[0]), MasterDetailFrame.this);
//				Iterator<StorageDataSet> localObject2 = MasterDetailFrame.this.detailDataSets.iterator();
//				while (((Iterator)localObject2).hasNext())
//				{
//					final	StorageDataSet	localObject1 = (StorageDataSet)((Iterator)localObject2).next();
//					((StorageDataSet)localObject1).addEditListener(new EditAdapter()
//					{
//						public void modifying(DataSet paramDataSet)
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							if (MasterDetailFrame.this.masterDataSet.isEditing())
//								MasterDetailFrame.this.masterDataSet.post();
//							if (!MasterDetailFrame.this.isModified())
//								MasterDetailFrame.this.setModified(true);
//							else
//								MasterDetailFrame.this.showDetailStatus();
//						}
//
//						public void adding(DataSet paramDataSet, ReadWriteRow paramReadWriteRow)
//						throws Exception
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							MasterDetailFrame.this.validateDetail(paramDataSet, paramReadWriteRow, null);
//						}
//
//						public void deleting(DataSet paramDataSet)
//						throws Exception
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							if (MasterDetailFrame.this.masterDataSet.isEditing())
//								MasterDetailFrame.this.masterDataSet.post();
//						}
//
//						public void inserting(DataSet paramDataSet)
//						throws Exception
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							if (MasterDetailFrame.this.masterDataSet.isEditing())
//								MasterDetailFrame.this.masterDataSet.post();
//						}
//
//						public void updating(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
//						throws Exception
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							MasterDetailFrame.this.validateDetail(paramDataSet, paramReadWriteRow, paramReadRow);
//						}
//					});
//					((StorageDataSet)localObject1).addDataChangeListener(new DataChangeAdapter()
//					{
//						public void dataChanged(DataChangeEvent paramDataChangeEvent)
//						{
//							if (((Boolean)MasterDetailFrame.this.detailLoadings.get(localObject1)).booleanValue())
//								return;
//							if (!MasterDetailFrame.this.isModified())
//								MasterDetailFrame.this.setModified(true);
//							else
//								MasterDetailFrame.this.showDetailStatus();
//						}
//					});
//				}
//				Object localObject1 = new HashMap();
//				((HashMap)localObject1).put(MasterDetailFrame.this.masterDataSet, MasterDetailFrame.this.masterComponents);
//				Iterator localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//				while (localIterator.hasNext())
//				{
//					StorageDataSet localObject = (StorageDataSet)localIterator.next();
//					HashMap localHashMap = (HashMap)MasterDetailFrame.this.detailComponents.get(localObject2);
//					if (localHashMap == null)
//					{
//						localHashMap = new HashMap();
//						MasterDetailFrame.this.detailComponents.put(localObject, localHashMap);
//					}
//					((HashMap)localObject1).put(localObject2, localHashMap);
//				}
//				DataSetHelper.listDataAwareComponents(MasterDetailFrame.this.getContentPane(), (HashMap)localObject1);
//				MasterDetailFrame.this.masterLoading = false;
//				localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//				while (localIterator.hasNext())
//				{
//					StorageDataSet	localObject = (StorageDataSet)localIterator.next();
//					MasterDetailFrame.this.detailLoadings.put(localObject, Boolean.valueOf(false));
//					MasterDetailFrame.this.detailEditables.put(localObject, Boolean.valueOf(true));
//				}
//				MasterDetailFrame.this.worker.setHook(new WireWorker.Hook()
//				{
//					public void hook(Object paramObject)
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//						MasterDetailFrame.A(MasterDetailFrame.this, false);
//						int i = 1;
//						try
//						{
//							MasterDetailFrame.this.prepared(paramObject);
//						}
//						catch (Throwable localThrowable)
//						{
//							i = 0;
//							JOptionPane.showMessageDialog(MasterDetailFrame.this, ExceptionFormat.format(localThrowable), MasterDetailFrame.this.getTitle(), 0);
//						}
//						if ((!MasterDetailFrame.this.canView) || (i == 0))
//						{
//							MasterDetailFrame.A(MasterDetailFrame.this, 25550);
//							return;
//						}
//						MasterDetailFrame.A(MasterDetailFrame.this);
//						MasterDetailFrame.this.buildMasterUniqueIndex();
//						MasterDetailFrame.this.buildDetailUniqueIndexes();
//						if (MasterDetailFrame.this.advancedFilterModeEnabled)
//						{
//							ConditionItemsHelper.load(MasterDetailFrame.this.conditionItems, MasterDetailFrame.this.masterDataSet, MasterDetailFrame.this.omitConditionItems);
//							SortItemsHelper.load(MasterDetailFrame.this.sortItems, MasterDetailFrame.this.masterDataSet, MasterDetailFrame.this.omitSortItems);
//							MasterDetailFrame.this.prepareConditions();
//							MasterDetailFrame.this.conditionTreePanel.setItems(MasterDetailFrame.this.conditionItems);
//							MasterDetailFrame.this.sortListPanel.setItems(MasterDetailFrame.this.sortItems);
//						}
//						if (!Beans.isDesignTime())
//						{
//							if (MasterDetailFrame.this.filterSimplePanel.getComponentCount() == 0)
//								MasterDetailFrame.this.filterSimpleMenuItem.setEnabled(false);
//							if (!MasterDetailFrame.this.advancedFilterModeEnabled)
//							{
//								MasterDetailFrame.this.filterAdvancedMenuItem.setEnabled(false);
//							}
//							else
//							{
//								if (MasterDetailFrame.this.conditionItems.size() == 0)
//								{
//									MasterDetailFrame.this.conditionTreePanel.setVisible(false);
//									MasterDetailFrame.this.filterAdvancedPane.setDividerSize(0);
//									MasterDetailFrame.this.filterAdvancedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
//								}
//								if (MasterDetailFrame.this.sortItems.size() == 0)
//								{
//									MasterDetailFrame.this.sortListPanel.setVisible(false);
//									MasterDetailFrame.this.filterAdvancedPane.setDividerSize(0);
//									MasterDetailFrame.this.filterAdvancedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
//								}
//							}
//							if (!MasterDetailFrame.this.filterConditionsPanel.isVisible())
//								if (MasterDetailFrame.this.filterSimpleMenuItem.isEnabled())
//									MasterDetailFrame.this.filterSimpleMenuItem.setSelected(true);
//								else
//									MasterDetailFrame.this.filterAdvancedMenuItem.setSelected(true);
//							if ((!MasterDetailFrame.this.filterModePanel.isVisible()) && (!MasterDetailFrame.this.filterSimpleMenuItem.isSelected()) && (MasterDetailFrame.this.filterMenuItem.isSelected()))
//								MasterDetailFrame.this.filterMenuItem.doClick();
//						}
//						if (MasterDetailFrame.this.filterTree != null)
//						{
//							MasterDetailFrame.this.conditionTreePanel.setTree(MasterDetailFrame.this.filterTree);
//							MasterDetailFrame.this.filterAdvancedPane.setDividerLocation(1.0D);
//						}
//						MasterDetailFrame.this.showStatus();
//						if (MasterDetailFrame.I(MasterDetailFrame.this))
//						{
//							if (MasterDetailFrame.H(MasterDetailFrame.this) != null)
//								SwingUtilities.invokeLater(new Runnable()
//								{
//									public void run()
//									{
//										MasterDetailFrame.this.searchAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.searchAction, 0, (String)MasterDetailFrame.this.searchAction.getValue("ActionCommandKey")));
//										MasterDetailFrame.A(MasterDetailFrame.this, null);
//									}
//								});
//						}
//						else if (MasterDetailFrame.this.refreshWhenOpened)
//							SwingUtilities.invokeLater(new Runnable()
//							{
//								public void run()
//								{
//									MasterDetailFrame.this.refreshAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.refreshAction, 0, (String)MasterDetailFrame.this.refreshAction.getValue("ActionCommandKey")));
//								}
//							});
//					}
//				});
//				MasterDetailFrame.this.worker.setCorrector(new WireWorker.Corrector()
//				{
//					public void correct(Throwable paramThrowable)
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//						MasterDetailFrame.A(MasterDetailFrame.this, 25550);
//					}
//				});
//				MasterDetailFrame.this.worker.setResumer(new WireWorker.Resumer()
//				{
//					public void resume()
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//						MasterDetailFrame.A(MasterDetailFrame.this, 25550);
//					}
//				});
//				MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//				{
//					public Object work()
//					throws Throwable
//					{
//						Object localObject = MasterDetailFrame.this.prepareData();
//						MasterDetailFrame.G(MasterDetailFrame.this);
//						MasterDetailFrame.this.checkPrivileges();
//						MasterDetailFrame.B(MasterDetailFrame.this);
//						return localObject;
//					}
//				});
//				MasterDetailFrame.this.worker.start();
//			}
//
//			public void internalFrameClosing(InternalFrameEvent paramInternalFrameEvent)
//			{
//				if (paramInternalFrameEvent.getSource() == null)
//					return;
//
//				if ((MasterDetailFrame.this.isModified()) || (MasterDetailFrame.this.isInletModified())){
//					switch (JOptionPane.showConfirmDialog(MasterDetailFrame.this, DataModel.getDefault().getSentence("MSG_EXIT_WITHOUT_SAVING_PROMPT"), MasterDetailFrame.this.getTitle(), 1, 3))
//					{
//					case 0:
//						MasterDetailFrame.this.worker.setCleaner(new WireWorker.Cleaner()
//						{
//							public void clean()
//							{
//								MasterDetailFrame.this.worker.setCleaner(null);
//								if ((!MasterDetailFrame.this.isModified()) && (!MasterDetailFrame.this.isInletModified()))
//									MasterDetailFrame.this.dispose();
//							}
//						});
//						if (MasterDetailFrame.this.isModified())
//							MasterDetailFrame.this.saveAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.saveAction, 0, (String)MasterDetailFrame.this.saveAction.getValue("ActionCommandKey")));
//						else
//							MasterDetailFrame.this.flushInletAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.flushInletAction, 0, (String)MasterDetailFrame.this.flushInletAction.getValue("ActionCommandKey")));
//						break;
//					case 1:
//						MasterDetailFrame.this.dispose();
//						break;
//					case 2:
//						break;
//					default:
//						MasterDetailFrame.this.dispose();
//					break;
//					}
//				}else{
//					dispose();
//				}
//			}
//
//			public void internalFrameClosed(InternalFrameEvent paramInternalFrameEvent)
//			{
//				Iterator localIterator = MasterDetailFrame.this.toolBars.keySet().iterator();
//				while (localIterator.hasNext())
//				{
//					JToolBar localJToolBar = (JToolBar)localIterator.next();
//					if (localJToolBar.getParent() == MasterDetailFrame.this.toolBars.get(localJToolBar))
//						continue;
//					Container localContainer = localJToolBar.getTopLevelAncestor();
//					if (!(localContainer instanceof Window))
//						continue;
//					((Window)localContainer).dispose();
//				}
//				DataSetExceptionAdapter.getDefaultAdapter().deregisterDataSets(new DataSet[] { MasterDetailFrame.this.masterDataSet });
//				DataSetExceptionAdapter.getDefaultAdapter().deregisterDataSets((DataSet[])MasterDetailFrame.this.detailDataSets.toArray(new DataSet[0]));
//			}
//		});
//	}
//
//	public void pack()
//	{
//		if (Beans.isDesignTime())
//			return;
//		ArrayList localArrayList = new ArrayList();
//		ContainerHelper.listComponents(getContentPane(), localArrayList, JToolBar.class);
//		Iterator localIterator = localArrayList.iterator();
//		while (localIterator.hasNext())
//		{
//			Component localComponent = (Component)localIterator.next();
//			this.toolBars.put((JToolBar)localComponent, localComponent.getParent());
//		}
//		if (this.U)
//		{
//			this.R = this.filterButton.isVisible();
//			this.M = this.refreshButton.isVisible();
//			this.N = this.setKeyButton.isVisible();
//			this.J = this.searchButton.isVisible();
//			if (this.mainToolBar.getComponentIndex(this.mainToolBarControlPanel) != this.mainToolBar.getComponentCount() - 1)
//			{
//				this.mainToolBar.remove(this.mainToolBarControlPanel);
//				this.mainToolBar.add(this.mainToolBarControlPanel);
//			}
//			this.U = false;
//		}
//		if (this.O)
//		{
//			this.selectButton.setVisible(this.H);
//			this.mainToolBar.getComponent(this.mainToolBar.getComponentIndex(this.selectButton) + 1).setVisible(this.H);
//			this.filterButton.setVisible((this.R) && (!this.K) && ((this.filterSimplePanel.getComponentCount() > 0) || (this.advancedFilterModeEnabled)));
//			this.refreshButton.setVisible((this.M) && (!this.K));
//			this.setKeyButton.setVisible((this.N) && (this.K) && (this.searchPanel.getComponentCount() > 0));
//			if (this.setKeyButton.isVisible())
//				this.setKeyButton.setSelected(true);
//			this.searchButton.setVisible((this.J) && (this.K) && (this.searchPanel.getComponentCount() > 0));
//			if ((!this.filterButton.isVisible()) && (!this.refreshButton.isVisible()) && (!this.setKeyButton.isVisible()) && (!this.searchButton.isVisible()))
//				this.mainToolBar.getComponent(this.mainToolBar.getComponentIndex(this.searchButton) + 1).setVisible(false);
//			if ((this.maxRowsControlEnabled) && (!this.K))
//				this.mainToolBarControlPanel.setVisible(true);
//			else
//				this.mainToolBarControlPanel.setVisible(false);
//			if (this.filterModePanel.getComponentCount() == 0)
//				this.filterModePanel.setVisible(false);
//			if ((this.filterSimplePanel.getComponentCount() == 0) && (!this.advancedFilterModeEnabled))
//				this.filterConditionsPanel.setVisible(false);
//			if ((this.filterModePanel.isVisible()) && (!this.filterConditionsPanel.isVisible()))
//			{
//				if (((this.filterPanel.getLayout() instanceof BorderLayout)) && (((BorderLayout)this.filterPanel.getLayout()).getConstraints(this.filterConditionsPanel).equals("Center")))
//				{
//					this.filterPanel.remove(this.filterModePanel);
//					this.filterPanel.remove(this.filterConditionsPanel);
//					this.filterPanel.add(this.filterModePanel, "Center");
//					this.filterPanel.add(this.filterConditionsPanel, "West");
//				}
//			}
//			else if ((!this.filterModePanel.isVisible()) && (!this.filterConditionsPanel.isVisible()))
//			{
//				this.filterPanel.setVisible(false);
//				this.filterButton.setVisible(false);
//				this.listPane.setDividerSize(0);
//				this.listPane.setBorder(new EmptyBorder(0, 0, 0, 0));
//			}
//			if (this.formFooterPanel.getComponentCount() == 0)
//				this.formFooterPanel.setVisible(false);
//			try
//			{
//				getClass().getDeclaredMethod("detailBatch", new Class[0]);
//			}
//			catch (Throwable localThrowable)
//			{
//				this.detailBatchButton.setVisible(false);
//			}
//			if ((this.inletPanel.getComponentCount() == 0) || (!this.inletPanel.isVisible()) || ((!this.inletVisibleWhenOpened) && (this.O)))
//			{
//				if (this.inletPanel.isVisible())
//					this.inletPanel.setVisible(false);
//				this.contentPane.setRightComponent(null);
//				if (this.inletPanel.getComponentCount() == 0)
//					this.contentPane.setDividerSize(0);
//			}
//		}
//		super.pack();
//		if ((getTopLevelAncestor() != null) && ((getTopLevelAncestor() instanceof JDialog)))
//			((JDialog)getTopLevelAncestor()).pack();
//	}
//
//	private void B()
//	{
//		String str1 = getClass().getPackage().getName() + "." + (getClass().getSimpleName().endsWith("Frame") ? getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 5) : getClass().getSimpleName()) + ".PlugIn.";
//		String str2 = str1.replace('.', System.getProperty("file.separator").charAt(0));
//		String[] arrayOfString1 = LaunchPath.listFileNames(str2, ".properties");
//		if (arrayOfString1 != null)
//			for (String str3 : arrayOfString1)
//			{
//				String str4 = str3.substring(0, str3.length() - ".properties".length());
//				Properties localProperties = new Properties();
//				try
//				{
//					localProperties.load(getClass().getClassLoader().getResourceAsStream(str3));
//					this.L.put(str4, localProperties);
//				}
//				catch (Exception localException)
//				{
//					localException.printStackTrace();
//				}
//			}
//		Iterator localIterator = this.L.keySet().iterator();
//		while (localIterator.hasNext())
//		{
//			String str3 = (String)localIterator.next();
//			this.W.put(str3, Boolean.valueOf(true));
//		}
//	}
//
//	private void H()
//	{
//		if (this.L.size() == 0)
//			return;
//		int i = this.mainToolBar.getComponentIndex(this.mainToolBarControlPanel);
//		JToolBar.Separator localSeparator = new JToolBar.Separator();
//		this.mainToolBar.add(localSeparator, i);
//		i++;
//		Iterator localIterator = this.L.keySet().iterator();
//		while (localIterator.hasNext())
//		{
//			String str1 = (String)localIterator.next();
//			Properties localProperties = (Properties)this.L.get(str1);
//			JButton localJButton = new JButton();
//			localJButton.setName(str1);
//			String str2 = localProperties.getProperty("name");
//			String str3 = localProperties.getProperty("icon");
//			String str4 = localProperties.getProperty("shortDescription");
//			String str5 = localProperties.getProperty("longDescription");
//			PlugInAction localPlugInAction = new PlugInAction(str2);
//			if ((str3 != null) && (str3.length() > 0))
//				try
//			{
//					localPlugInAction.putValue("SmallIcon", new ImageIcon(getClass().getResource(str3)));
//			}
//			catch (Exception localException)
//			{
//				localException.printStackTrace();
//			}
//			localPlugInAction.putValue("ShortDescription", str4);
//			localPlugInAction.putValue("LongDescription", str5);
//			this.T.put(str1, localPlugInAction);
//			localJButton.setAction(localPlugInAction);
//			this.mainToolBar.add(localJButton, i);
//			i++;
//		}
//	}
//
//	private void G()
//	{
//		Iterator localIterator = this.L.keySet().iterator();
//		while (localIterator.hasNext())
//		{
//			String str1 = (String)localIterator.next();
//			String str2 = ((Properties)this.L.get(str1)).getProperty("privilegeId");
//			if ((str2 == null) || (str2.length() <= 0))
//				continue;
//			this.W.put(str1, Boolean.valueOf(privilegeChecker.validate(str2)));
//		}
//	}
//
//	private void D()
//	{
//		this.masterDataSet = new StorageDataSet();
//		this.detailDataSet = new StorageDataSet();
//		setTitle(MasterDetailFrame.class.getSimpleName());
//		setDefaultCloseOperation(0);
//		setResizable(true);
//		setIconifiable(true);
//		setMaximizable(true);
//		setClosable(true);
//		this.mainPane = new JSplitPane();
//		this.mainPane.setOneTouchExpandable(true);
//		this.mainPane.setResizeWeight(1.0D);
//		getContentPane().add(this.mainPane, "Center");
//		this.listPane = new JSplitPane();
//		this.listPane.setOrientation(0);
//		this.listPane.setResizeWeight(0.0D);
//		this.listPane.setOneTouchExpandable(true);
//		this.mainPane.setLeftComponent(this.listPane);
//		this.filterPanel = new JPanel();
//		this.filterPanel.setLayout(new BorderLayout());
//		this.listPane.setTopComponent(this.filterPanel);
//		this.filterModePanel = new JPanel();
//		this.filterModePanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("FILTER_MODE"), 0, 0, null, null));
//		this.filterPanel.add(this.filterModePanel, "West");
//		this.filterConditionsPanel = new JPanel();
//		this.filterConditionsPanel.setLayout(new GridBagLayout());
//		this.filterConditionsPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("FILTER_CONDITIONS"), 0, 0, null, null));
//		this.filterPanel.add(this.filterConditionsPanel);
//		this.filterSimplePanel = new JPanel();
//		this.filterConditionsPanel.add(this.filterSimplePanel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
//		this.filterAdvancedPane = new JSplitPane();
//		this.filterAdvancedPane.setVisible(false);
//		this.filterAdvancedPane.setOneTouchExpandable(true);
//		this.filterAdvancedPane.setResizeWeight(0.8D);
//		this.filterConditionsPanel.add(this.filterAdvancedPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
//		this.conditionTreePanel = new ConditionTreePanel();
//		this.filterAdvancedPane.setLeftComponent(this.conditionTreePanel);
//		this.sortListPanel = new SortListPanel();
//		this.filterAdvancedPane.setRightComponent(this.sortListPanel);
//		this.listPanel = new JPanel();
//		this.listPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("LIST"), 0, 0, null, null));
//		this.listPanel.setLayout(new BorderLayout());
//		this.listPane.setRightComponent(this.listPanel);
//		this.listTablePane = new TableScrollPane();
//		this.listPanel.add(this.listTablePane, "Center");
//		this.listTable = new JdbTable();
//		this.listTable.setName("listTable");
//		this.listTable.addMouseListener(new ListTableMouseListener());
//		this.listTable.setEditable(false);
//		this.listTable.setDataSet(this.masterDataSet);
//		this.listTablePane.setViewportView(this.listTable);
//		this.listFooterPanel = new JPanel();
//		this.listFooterPanel.setLayout(new BorderLayout());
//		this.listPanel.add(this.listFooterPanel, "South");
//		this.contentPane = new JSplitPane();
//		this.contentPane.setOneTouchExpandable(true);
//		this.contentPane.setResizeWeight(0.0D);
//		this.mainPane.setRightComponent(this.contentPane);
//		this.formPanel = new JPanel();
//		this.contentPane.setLeftComponent(this.formPanel);
//		this.formPanel.setLayout(new BorderLayout());
//		this.formPane = new JSplitPane();
//		this.formPanel.add(this.formPane);
//		this.formPane.setOneTouchExpandable(true);
//		this.formPane.setResizeWeight(0.0D);
//		this.formPane.setOrientation(0);
//		this.masterPanel = new JPanel();
//		this.masterPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("CONTENT"), 0, 0, null, null));
//		this.formPane.setLeftComponent(this.masterPanel);
//		this.detailPane = new JTabbedPane();
//		this.formPane.setRightComponent(this.detailPane);
//		this.detailPanel = new JPanel();
//		this.detailPanel.setLayout(new BorderLayout());
//		this.detailPane.addTab(DataModel.getDefault().getCaption("DETAIL_INFO"), null, this.detailPanel, null);
//		this.detailToolBar = new JToolBar(MessageFormat.format(DataModel.getDefault().getCaption("TOOL_BAR_OF"), new Object[] { DataModel.getDefault().getCaption("DETAIL") }));
//		this.detailPanel.add(this.detailToolBar, "North");
//		this.detailNewButton = new JButton();
//		this.detailNewButton.setAction(this.detailNewAction);
//		this.detailNewButton.setText("");
//		this.detailToolBar.add(this.detailNewButton);
//		this.detailBatchButton = new JButton();
//		this.detailBatchButton.setAction(this.detailBatchAction);
//		this.detailBatchButton.setText("");
//		this.detailToolBar.add(this.detailBatchButton);
//		this.detailDeleteButton = new JButton();
//		this.detailDeleteButton.setAction(this.detailDeleteAction);
//		this.detailDeleteButton.setText("");
//		this.detailToolBar.add(this.detailDeleteButton);
//		this.detailClearButton = new JButton();
//		this.detailClearButton.setAction(this.detailClearAction);
//		this.detailClearButton.setText("");
//		this.detailToolBar.add(this.detailClearButton);
//		this.detailFooterPanel = new JPanel();
//		this.detailFooterPanel.setLayout(new BorderLayout());
//		this.detailPanel.add(this.detailFooterPanel, "South");
//		this.detailSummaryFixedPanel = new JPanel();
//		GridBagLayout localGridBagLayout1 = new GridBagLayout();
//		localGridBagLayout1.rowHeights = new int[1];
//		localGridBagLayout1.columnWidths = new int[2];
//		this.detailSummaryFixedPanel.setLayout(localGridBagLayout1);
//		this.detailFooterPanel.add(this.detailSummaryFixedPanel, "East");
//		this.detailRowCountPromptLabel = new JLabel();
//		this.detailRowCountPromptLabel.setText(DataModel.getDefault().getLabel("LINE_COUNT"));
//		this.detailSummaryFixedPanel.add(this.detailRowCountPromptLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 0), 0, 0));
//		this.detailRowCountLabel = new JLabel();
//		this.detailRowCountLabel.setText("0");
//		this.detailSummaryFixedPanel.add(this.detailRowCountLabel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 5, 5), 0, 0));
//		this.detailSummaryExtendedPanel = new JPanel();
//		this.detailFooterPanel.add(this.detailSummaryExtendedPanel, "Center");
//		this.detailTablePane = new TableScrollPane();
//		this.detailPanel.add(this.detailTablePane);
//		this.detailTable = new JdbTable();
//		this.detailTable.setName("detailTable");
//		this.detailTable.setDataSet(this.detailDataSet);
//		this.detailTablePane.setViewportView(this.detailTable);
//		this.formFooterPanel = new JPanel();
//		this.formFooterPanel.setLayout(new BorderLayout());
//		this.formPanel.add(this.formFooterPanel, "South");
//		this.inletPanel = new JPanel();
//		this.inletPanel.setLayout(new BorderLayout());
//		this.contentPane.setRightComponent(this.inletPanel);
//		this.footerPanel = new JPanel();
//		this.footerPanel.setLayout(new BorderLayout());
//		getContentPane().add(this.footerPanel, "South");
//		this.progressPanel = new JPanel();
//		this.progressPanel.setLayout(new BorderLayout());
//		this.footerPanel.add(this.progressPanel, "West");
//		this.footerFixedPanel = new JPanel();
//		GridBagLayout localGridBagLayout2 = new GridBagLayout();
//		localGridBagLayout2.columnWidths = new int[2];
//		localGridBagLayout2.rowHeights = new int[1];
//		this.footerFixedPanel.setLayout(localGridBagLayout2);
//		this.footerPanel.add(this.footerFixedPanel, "East");
//		this.listRowCountPromptLabel = new JLabel();
//		this.listRowCountPromptLabel.setText(DataModel.getDefault().getLabel("ROW_COUNT"));
//		this.footerFixedPanel.add(this.listRowCountPromptLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 0), 0, 0));
//		this.listRowCountLabel = new JLabel();
//		this.listRowCountLabel.setText("0");
//		this.footerFixedPanel.add(this.listRowCountLabel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 5, 5), 0, 0));
//		this.footerExtendedPanel = new JPanel();
//		this.footerExtendedPanel.setLayout(new BorderLayout());
//		this.footerPanel.add(this.footerExtendedPanel);
//		this.headerPanel = new JPanel();
//		this.headerPanel.setLayout(new BorderLayout());
//		getContentPane().add(this.headerPanel, "North");
//		this.mainToolBar = new JToolBar(DataModel.getDefault().getCaption("TOOL_BAR"));
//		this.headerPanel.add(this.mainToolBar, "North");
//		this.selectButton = new JButton();
//		this.selectButton.setAction(this.selectAction);
//		this.selectButton.setHorizontalTextPosition(0);
//		this.selectButton.setVerticalTextPosition(3);
//		this.selectButton.setText("");
//		this.mainToolBar.add(this.selectButton);
//		this.mainToolBar.addSeparator();
//		this.closeButton = new JButton();
//		this.closeButton.setHorizontalTextPosition(0);
//		this.closeButton.setVerticalTextPosition(3);
//		this.closeButton.setAction(this.closeAction);
//		this.closeButton.setText("");
//		this.mainToolBar.add(this.closeButton);
//		this.mainToolBar.addSeparator();
//		this.filterButton = new JButton();
//		this.filterButton.setSelected(true);
//		this.filterButton.setHorizontalTextPosition(0);
//		this.filterButton.setVerticalTextPosition(3);
//		this.filterButton.setAction(this.filterAction);
//		this.filterButton.setText("");
//		this.mainToolBar.add(this.filterButton);
//		this.filterMenu = new JPopupMenu();
//		B(this.filterButton, this.filterMenu);
//		this.filterMenuItem = new JCheckBoxMenuItem();
//		this.filterMenuItem.addActionListener(new FilterMenuItemActionListener());
//		this.filterMenuItem.setSelected(true);
//		this.filterMenuItem.setText(DataModel.getDefault().getCaption("FILTER_OPTION"));
//		this.filterMenu.add(this.filterMenuItem);
//		this.filterAutoHiddenMenuItem = new JCheckBoxMenuItem();
//		this.filterAutoHiddenMenuItem.setText(DataModel.getDefault().getCaption("AUTO_HIDDEN"));
//		this.filterMenu.add(this.filterAutoHiddenMenuItem);
//		this.filterMenu.addSeparator();
//		this.filterSimpleMenuItem = new JRadioButtonMenuItem();
//		this.filterModeGroup.add(this.filterSimpleMenuItem);
//		this.filterSimpleMenuItem.addActionListener(new FilterSimpleMenuItemActionListener());
//		this.filterSimpleMenuItem.setSelected(true);
//		this.filterSimpleMenuItem.setText(DataModel.getDefault().getCaption("SIMPLE_MODE"));
//		this.filterMenu.add(this.filterSimpleMenuItem);
//		this.filterAdvancedMenuItem = new JRadioButtonMenuItem();
//		this.filterModeGroup.add(this.filterAdvancedMenuItem);
//		this.filterAdvancedMenuItem.addActionListener(new FilterAdvancedMenuItemActionListener());
//		this.filterAdvancedMenuItem.setText(DataModel.getDefault().getCaption("ADVANCED_MODE"));
//		this.filterMenu.add(this.filterAdvancedMenuItem);
//		this.refreshButton = new JButton();
//		this.refreshButton.setVerticalTextPosition(3);
//		this.refreshButton.setHorizontalTextPosition(0);
//		this.refreshButton.setAction(this.refreshAction);
//		this.refreshButton.setText("");
//		this.mainToolBar.add(this.refreshButton);
//		this.setKeyButton = new JToggleButton();
//		this.setKeyButton.addChangeListener(new SetKeyButtonChangeListener());
//		this.setKeyButton.setVerticalTextPosition(3);
//		this.setKeyButton.setHorizontalTextPosition(0);
//		this.setKeyButton.setAction(this.setKeyAction);
//		this.setKeyButton.setText("");
//		this.mainToolBar.add(this.setKeyButton);
//		this.searchButton = new JButton();
//		this.searchButton.setVerticalTextPosition(3);
//		this.searchButton.setHorizontalTextPosition(0);
//		this.searchButton.setAction(this.searchAction);
//		this.searchButton.setText("");
//		this.mainToolBar.add(this.searchButton);
//		this.mainToolBar.addSeparator();
//		this.newButton = new JButton();
//		this.newButton.setVerticalTextPosition(3);
//		this.newButton.setHorizontalTextPosition(0);
//		this.newButton.setAction(this.newAction);
//		this.newButton.setText("");
//		this.mainToolBar.add(this.newButton);
//		this.saveButton = new JButton();
//		this.saveButton.setHorizontalTextPosition(0);
//		this.saveButton.setVerticalTextPosition(3);
//		this.saveButton.setAction(this.saveAction);
//		this.saveButton.setText("");
//		this.mainToolBar.add(this.saveButton);
//		this.cancelButton = new JButton();
//		this.cancelButton.setHorizontalTextPosition(0);
//		this.cancelButton.setVerticalTextPosition(3);
//		this.cancelButton.setAction(this.cancelAction);
//		this.cancelButton.setText("");
//		this.mainToolBar.add(this.cancelButton);
//		this.mainToolBar.addSeparator();
//		this.firstButton = new JButton();
//		this.firstButton.setVerticalTextPosition(3);
//		this.firstButton.setHorizontalTextPosition(0);
//		this.firstButton.setAction(this.firstAction);
//		this.firstButton.setText("");
//		this.mainToolBar.add(this.firstButton);
//		this.priorButton = new JButton();
//		this.priorButton.setHorizontalTextPosition(0);
//		this.priorButton.setVerticalTextPosition(3);
//		this.priorButton.setAction(this.priorAction);
//		this.priorButton.setText("");
//		this.mainToolBar.add(this.priorButton);
//		this.nextButton = new JButton();
//		this.nextButton.setVerticalTextPosition(3);
//		this.nextButton.setHorizontalTextPosition(0);
//		this.nextButton.setAction(this.nextAction);
//		this.nextButton.setText("");
//		this.mainToolBar.add(this.nextButton);
//		this.lastButton = new JButton();
//		this.lastButton.setHorizontalTextPosition(0);
//		this.lastButton.setVerticalTextPosition(3);
//		this.lastButton.setAction(this.lastAction);
//		this.lastButton.setText("");
//		this.mainToolBar.add(this.lastButton);
//		this.mainToolBar.addSeparator();
//		this.deleteButton = new JButton();
//		this.deleteButton.setHorizontalTextPosition(0);
//		this.deleteButton.setVerticalTextPosition(3);
//		this.deleteButton.setAction(this.deleteAction);
//		this.deleteButton.setText("");
//		this.mainToolBar.add(this.deleteButton);
//		this.mainToolBarControlPanel = new JPanel();
//		GridBagLayout localGridBagLayout3 = new GridBagLayout();
//		localGridBagLayout3.columnWidths = new int[] { 5 };
//		this.mainToolBarControlPanel.setLayout(localGridBagLayout3);
//		this.mainToolBarControlPanel.setVisible(false);
//		this.mainToolBar.add(this.mainToolBarControlPanel);
//		this.mainToolBarControlPaddingPanel = new JPanel();
//		this.mainToolBarControlPanel.add(this.mainToolBarControlPaddingPanel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
//		this.mainToolBarControlExtPanel = new JPanel();
//		this.mainToolBarControlPanel.add(this.mainToolBarControlExtPanel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
//		this.mainToolBarControlExtPanel.setLayout(new BorderLayout(0, 0));
//		this.maxRowsLabel = new JLabel();
//		this.maxRowsLabel.setText(DataModel.getDefault().getLabel("MAX_ROWS"));
//		this.mainToolBarControlPanel.add(this.maxRowsLabel, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
//		this.maxRowsSpinner = new JSpinner();
//		((JSpinner.DefaultEditor)this.maxRowsSpinner.getEditor()).getTextField().setColumns(7);
//		this.mainToolBarControlPanel.add(this.maxRowsSpinner, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 5), 0, 0));
//		this.searchPanel = new JPanel();
//		this.searchPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("SEARCH_CONDITIONS"), 0, 0, null, null));
//		this.searchPanel.setLayout(new BorderLayout());
//		this.headerPanel.add(this.searchPanel, "South");
//		this.titlePanel = new JPanel();
//		this.titlePanel.setLayout(new BorderLayout());
//		this.headerPanel.add(this.titlePanel);
//	}
//
//	public void show(Component paramComponent, ConditionTree paramConditionTree)
//	{
//		if (paramConditionTree != null)
//			this.boundTree.unmarshal(paramConditionTree.marshal());
//		this.refreshWhenOpened = true;
//		if (paramComponent == null)
//			setVisible(true);
//		else
//			JInternalDialog.showAsDialog(paramComponent, this);
//	}
//
//	protected void A(boolean paramBoolean)
//	{
//		if (this.K == paramBoolean)
//			return;
//		this.K = paramBoolean;
//		if (Beans.isDesignTime())
//			return;
//		this.searchPanel.setVisible((this.searchPanel.isVisible()) && (this.searchPanel.getComponentCount() > 0) && (paramBoolean));
//		if (paramBoolean)
//		{
//			this.mainPane.setLeftComponent(null);
//			this.X = this.mainPane.getBorder();
//			this.mainPane.setBorder(new EmptyBorder(0, 0, 0, 0));
//			this.mainPane.setDividerSize(0);
//			if ((this.footerFixedPanel.getComponentCount() == 2) && (this.listRowCountPromptLabel.getParent() == this.footerFixedPanel) && (this.listRowCountLabel.getParent() == this.footerFixedPanel))
//				this.footerFixedPanel.setVisible(false);
//			this.footerExtendedPanel.setVisible(false);
//		}
//		else
//		{
//			this.mainPane.setLeftComponent(this.listPane);
//			this.mainPane.setDividerSize(new JSplitPane().getDividerSize());
//			if (this.X != null)
//				this.mainPane.setBorder(this.X);
//			this.footerExtendedPanel.setVisible(true);
//			this.footerFixedPanel.setVisible(true);
//		}
//		pack();
//	}
//
//	public void showByKey(Component paramComponent, Object paramObject)
//	{
//
//		A(true);
//		this.masterLoading=true;
//		this.I = paramObject;
//		if (paramComponent == null)
//		{
//			setVisible(true);
//		}
//		else if ((paramComponent instanceof JDesktopPane))
//		{
//			if (getParent() != null)
//				getParent().remove(this);
//			((JDesktopPane)paramComponent).add(this);
//			setVisible(true);
//		}
//		else
//		{
//			JInternalDialog.showAsDialog(paramComponent, this);
//		}
//	}
//
//	public void showByKeyString(Component paramComponent, String paramString)
//	{
//		showByKey(paramComponent, buildSearchKeyFromString(paramString));
//	}
//
//	public void showByKeyString(String paramString)
//	{
//		showByKeyString(null, paramString);
//	}
//
//	public void show(Component paramComponent, boolean paramBoolean)
//	{
//		A(paramBoolean);
//		if (paramComponent == null)
//		{
//			setVisible(true);
//		}
//		else if ((paramComponent instanceof JDesktopPane))
//		{
//			if (getParent() != null)
//				getParent().remove(this);
//			((JDesktopPane)paramComponent).add(this);
//			setVisible(true);
//		}
//		else
//		{
//			JInternalDialog.showAsDialog(paramComponent, this);
//		}
//	}
//
//	public void showByFilterString(Component paramComponent, String paramString)
//	{
//		if ((paramString != null) && (paramString.length() > 0))
//		{
//			this.filterTree.unmarshal(paramString.getBytes());
//			if (!this.filterAdvancedMenuItem.isSelected())
//				this.filterAdvancedMenuItem.doClick();
//		}
//		this.refreshWhenOpened = true;
//		if (paramComponent == null)
//			setVisible(true);
//		else
//			JInternalDialog.showAsDialog(paramComponent, this);
//	}
//
//	public void showByFilterString(String paramString)
//	{
//		showByFilterString(null, paramString);
//	}
//
//	public RecordSet select(Component paramComponent, ConditionTree paramConditionTree, boolean paramBoolean1, boolean paramBoolean2)
//	{
//		if (paramConditionTree != null)
//			this.boundTree.unmarshal(paramConditionTree.marshal());
//		this.H = true;
//		if (paramBoolean1)
//			this.listTable.setSelectionMode(2);
//		else
//			this.listTable.setSelectionMode(0);
//		boolean bool = this.refreshWhenOpened;
//		this.refreshWhenOpened = paramBoolean2;
//		this.selections = null;
//		pack();
//		SwingUtilities.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				MasterDetailFrame.this.mainPane.setDividerLocation(1.0D);
//			}
//		});
//		JInternalDialog.showAsDialog(paramComponent, this);
//		this.refreshWhenOpened = bool;
//		return this.selections;
//	}
//
//	public RecordSet select(Component paramComponent, ConditionTree paramConditionTree, boolean paramBoolean)
//	{
//		return select(paramComponent, paramConditionTree, paramBoolean, true);
//	}
//
//	protected void showNavigator()
//	{
//		boolean bool1 = false;
//		boolean bool2 = false;
//		boolean bool3 = false;
//		boolean bool4 = false;
//		if ((this.masterDataSet.isOpen()) && (!isModified()) && (!isInletModified()))
//		{
//			bool1 = !this.masterDataSet.atFirst();
//			bool2 = !this.masterDataSet.atFirst();
//			bool3 = !this.masterDataSet.atLast();
//			bool4 = !this.masterDataSet.atLast();
//		}
//		if (this.firstAction.isEnabled() != bool1)
//		{
//			this.firstAction.setEnabled(!bool1);
//			this.firstAction.setEnabled(bool1);
//		}
//		if (this.priorAction.isEnabled() != bool2)
//		{
//			this.priorAction.setEnabled(!bool2);
//			this.priorAction.setEnabled(bool2);
//		}
//		if (this.nextAction.isEnabled() != bool3)
//		{
//			this.nextAction.setEnabled(!bool3);
//			this.nextAction.setEnabled(bool3);
//		}
//		if (this.lastAction.isEnabled() != bool4)
//		{
//			this.lastAction.setEnabled(!bool4);
//			this.lastAction.setEnabled(bool4);
//		}
//	}
//
//	protected boolean canModifyRow()
//	{
//		return true;
//	}
//
//	protected boolean canDeleteRow()
//	{
//		return true;
//	}
//
//	protected void showRowStatus()
//	{
//	}
//
//	protected void showStatus()
//	{
//		if (!SwingUtilities.isEventDispatchThread())
//			return;
//		boolean bool1 = this.masterDataSet.isOpen();
//		boolean bool2 = isModified();
//		boolean bool3 = isInletModified();
//		boolean bool4 = (!this.K) && (!bool2) && (!bool3);
//		boolean bool5 = (!this.K) && (!bool2) && (!bool3);
//		boolean bool6 = (this.K) && (!bool2) && (!bool3);
//		boolean bool7 = (this.K) && (!bool2) && (!bool3);
//		if (this.filterAction.isEnabled() != bool4)
//		{
//			this.filterAction.setEnabled(!bool4);
//			this.filterAction.setEnabled(bool4);
//		}
//		if (this.refreshAction.isEnabled() != bool5)
//		{
//			this.refreshAction.setEnabled(!bool5);
//			this.refreshAction.setEnabled(bool5);
//		}
//		if (this.setKeyAction.isEnabled() != bool6)
//		{
//			this.setKeyAction.setEnabled(!bool6);
//			this.setKeyAction.setEnabled(bool6);
//		}
//		if (this.searchAction.isEnabled() != bool7)
//		{
//			this.searchAction.setEnabled(!bool7);
//			this.searchAction.setEnabled(bool7);
//		}
//		boolean bool12;
//		if (bool1)
//		{
//			this.masterDataSet.setEnableInsert((this.canInsert) && (!bool3));
//			this.masterDataSet.setEnableUpdate(((this.canModify) && (canModifyRow())) || (((this.masterDataSet.getStatus() & 0x4) != 0) && (!bool3)));
//			this.masterDataSet.setEnableDelete(((this.canDelete) && (canDeleteRow())) || (((this.masterDataSet.getStatus() & 0x4) != 0) && (!bool3)));
//			boolean	bool8 = (this.H) && (((this.listTable.getSelectionModel().getSelectionMode() == 0) && (this.masterDataSet.inBounds())) || ((this.listTable.getSelectedRowCount() > 0) && (!bool2) && (!bool3)));
//			boolean bool9 = (!bool2) && (this.canInsert) && (!bool3);
//			boolean bool10 = bool2;
//			boolean bool11 = bool2;
//			if ((this.listTable.getSelectedRowCount() > 1) && (this.masterDataSet.isEnableDelete()))
//			{
//				boolean bool13 = this.masterLoading;
//				this.masterLoading = true;
//				boolean bool14 = this.masterDataSet.isEnableDataSetEvents();
//				if (bool14)
//					this.masterDataSet.enableDataSetEvents(false);
//				int i = this.masterDataSet.getRow();
//				try
//				{
//					int[] arrayOfInt1 = this.listTable.getSelectedRows();
//					bool12 = true;
//					for (int j : arrayOfInt1)
//					{
//						this.masterDataSet.goToRow(j);
//						if (canDeleteRow())
//							continue;
//						bool12 = false;
//						break;
//					}
//				}
//				finally
//				{
//					this.masterDataSet.goToRow(i);
//					if (bool14)
//						this.masterDataSet.enableDataSetEvents(true);
//					this.masterLoading = bool13;
//				}
//			}
//			else
//			{
//				bool12 = (this.masterDataSet.isEnableDelete()) && (this.masterDataSet.inBounds());
//			}
//			if (this.selectAction.isEnabled() != bool8)
//			{
//				this.selectAction.setEnabled(!bool8);
//				this.selectAction.setEnabled(bool8);
//			}
//			if (this.newAction.isEnabled() != bool9)
//			{
//				this.newAction.setEnabled(!bool9);
//				this.newAction.setEnabled(bool9);
//			}
//			if (this.saveAction.isEnabled() != bool10)
//			{
//				this.saveAction.setEnabled(!bool10);
//				this.saveAction.setEnabled(bool10);
//			}
//			if (this.cancelAction.isEnabled() != bool11)
//			{
//				this.cancelAction.setEnabled(!bool11);
//				this.cancelAction.setEnabled(bool11);
//			}
//			if (this.deleteAction.isEnabled() != bool12)
//			{
//				this.deleteAction.setEnabled(!bool12);
//				this.deleteAction.setEnabled(bool12);
//			}
//			DataSetHelper.enableDataAwareComponents(this.masterComponents, ((!this.masterDataSet.isEmpty()) && (this.masterDataSet.isEnableUpdate())) || (this.masterDataSet.isEditingNewRow()));
//		}
//		else
//		{
//			if (this.selectAction.isEnabled())
//			{
//				this.selectAction.setEnabled(true);
//				this.selectAction.setEnabled(false);
//			}
//			if (this.newAction.isEnabled())
//			{
//				this.newAction.setEnabled(true);
//				this.newAction.setEnabled(false);
//			}
//			if (this.saveAction.isEnabled())
//			{
//				this.saveAction.setEnabled(true);
//				this.saveAction.setEnabled(false);
//			}
//			if (this.cancelAction.isEnabled())
//			{
//				this.cancelAction.setEnabled(true);
//				this.cancelAction.setEnabled(false);
//			}
//			DataSetHelper.enableDataAwareComponents(this.masterComponents, false);
//		}
//		showNavigator();
//		if ((this.masterDataSet.isEditingNewRow()) || (!this.masterDataSet.isEmpty()))
//			showRowStatus();
//		if (this.inletPanel.isVisible())
//			showInletStatus();
//		this.listPane.setEnabled((!isModified()) && (!isInletModified()));
//		this.listTable.setEnabled((!isModified()) && (!isInletModified()));
//		if (bool1)
//			this.listRowCountLabel.setText(Integer.toString(this.masterDataSet.getRowCount()));
//		else
//			this.listRowCountLabel.setText("0");
//		boolean bool8 = (!this.masterDataSet.isEmpty()) && (!isModified());
//		Iterator localIterator = this.T.keySet().iterator();
//		while (localIterator.hasNext())
//		{
//			String str = (String)localIterator.next();
//			PlugInAction localPlugInAction = (PlugInAction)this.T.get(str);
//			bool12 = ((Boolean)this.W.get(str)).booleanValue();
//			localPlugInAction.setEnabled((!bool8) || (!bool12));
//			localPlugInAction.setEnabled((bool8) && (bool12));
//		}
//		showDetailStatus();
//	}
//
//	protected void showDetailStatus()
//	{
//		boolean bool1 = this.masterDataSet.isOpen();
//		StorageDataSet localStorageDataSet;
//		Object localObject2;
//		boolean bool3;
//		Object localObject1;
//		Iterator<StorageDataSet> localIterator;
//		if (bool1)
//		{
//			localIterator = this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				localStorageDataSet = (StorageDataSet)localIterator.next();
//				boolean bool2 = false;
//				if (localStorageDataSet.isOpen())
//				{
//					bool2 = (((Boolean)this.detailEditables.get(localStorageDataSet)).booleanValue()) && (((localStorageDataSet.getStatus() & 0x6) != 0) || ((!this.masterDataSet.isEmpty()) && (this.masterDataSet.isEnableUpdate())) || (this.masterDataSet.isEditingNewRow()));
//					DataSetHelper.enableDataAwareComponents((HashMap)this.detailComponents.get(localStorageDataSet), bool2);
//				}
//				localObject2 = (AbstractAction)this.detailNewActions.get(localStorageDataSet);
//				if (localObject2 != null)
//				{
//					bool3 = bool2;
//					if (((AbstractAction)localObject2).isEnabled() != bool3)
//					{
//						((AbstractAction)localObject2).setEnabled(!bool3);
//						((AbstractAction)localObject2).setEnabled(bool3);
//					}
//				}
//				localObject2 = (AbstractAction)this.detailBatchActions.get(localStorageDataSet);
//				if (localObject2 != null)
//				{
//					bool3 = bool2;
//					if (((AbstractAction)localObject2).isEnabled() != bool3)
//					{
//						((AbstractAction)localObject2).setEnabled(!bool3);
//						((AbstractAction)localObject2).setEnabled(bool3);
//					}
//				}
//				localObject2 = (AbstractAction)this.detailDeleteActions.get(localStorageDataSet);
//				if (localObject2 != null)
//				{
//					bool3 = (bool2) && ((!localStorageDataSet.isEmpty()) || (localStorageDataSet.isEditingNewRow()));
//					if (((AbstractAction)localObject2).isEnabled() != bool3)
//					{
//						((AbstractAction)localObject2).setEnabled(!bool3);
//						((AbstractAction)localObject2).setEnabled(bool3);
//					}
//				}
//				localObject2 = (AbstractAction)this.detailClearActions.get(localStorageDataSet);
//				if (localObject2 == null)
//					continue;
//				bool3 = (bool2) && ((!localStorageDataSet.isEmpty()) || (localStorageDataSet.isEditingNewRow()));
//				if (((AbstractAction)localObject2).isEnabled() == bool3)
//					continue;
//				((AbstractAction)localObject2).setEnabled(!bool3);
//				((AbstractAction)localObject2).setEnabled(bool3);
//			}
//		}
//		else
//		{
//			localIterator = this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				localStorageDataSet = (StorageDataSet)localIterator.next();
//				if (localStorageDataSet.isOpen())
//					localStorageDataSet.close();
//				DataSetHelper.enableDataAwareComponents((HashMap)this.detailComponents.get(localStorageDataSet), false);
//				localObject1 = (AbstractAction)this.detailNewActions.get(localStorageDataSet);
//				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
//				{
//					((AbstractAction)localObject1).setEnabled(true);
//					((AbstractAction)localObject1).setEnabled(false);
//				}
//				localObject1 = (AbstractAction)this.detailBatchActions.get(localStorageDataSet);
//				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
//				{
//					((AbstractAction)localObject1).setEnabled(true);
//					((AbstractAction)localObject1).setEnabled(false);
//				}
//				localObject1 = (AbstractAction)this.detailDeleteActions.get(localStorageDataSet);
//				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
//				{
//					((AbstractAction)localObject1).setEnabled(true);
//					((AbstractAction)localObject1).setEnabled(false);
//				}
//				localObject1 = (AbstractAction)this.detailClearActions.get(localStorageDataSet);
//				if ((localObject1 == null) || (!((AbstractAction)localObject1).isEnabled()))
//					continue;
//				((AbstractAction)localObject1).setEnabled(true);
//				((AbstractAction)localObject1).setEnabled(false);
//			}
//		}
//		localIterator = this.detailDataSets.iterator();
//		while (localIterator.hasNext())
//		{
//			localStorageDataSet = (StorageDataSet)localIterator.next();
//			localObject1 = (JLabel)this.detailRowCountLabels.get(localStorageDataSet);
//			if (localObject1 == null)
//				continue;
//			localObject2 = localStorageDataSet;
//			if (this.detailTdDataSets.get(localStorageDataSet) != null)
//				localObject2 = (DataSet)this.detailTdDataSets.get(localStorageDataSet);
//			if (((DataSet)localObject2).isOpen())
//			{
//				bool3 = false;
//				int i=0;
//				if ((localStorageDataSet != localObject2) && ((localObject2 instanceof TdDataSet)))
//					if (((TdDataSet)localObject2).isInserting())
//						bool3 = true;
//					else if (((TdDataSet)localObject2).isDeleting())
//						i = -1;
//				((JLabel)localObject1).setText(Integer.toString(((DataSet)localObject2).getRowCount() + i));
//			}
//			else
//			{
//				((JLabel)localObject1).setText("0");
//			}
//		}
//	}
//
//	protected void linkDetailDataSets()
//	{
//		this.detailDataSets.add(this.detailDataSet);
//		this.detailNewActions.put(this.detailDataSet, this.detailNewAction);
//		this.detailBatchActions.put(this.detailDataSet, this.detailBatchAction);
//		this.detailDeleteActions.put(this.detailDataSet, this.detailDeleteAction);
//		this.detailClearActions.put(this.detailDataSet, this.detailClearAction);
//		this.detailRowCountLabels.put(this.detailDataSet, this.detailRowCountLabel);
//	}
//
//	protected void buildMasterUniqueIndex()
//	{
//		if (!this.duplicateChecking)
//			return;
//		boolean bool = this.masterLoading;
//		this.masterLoading = true;
//		try
//		{
//			String[] arrayOfString = (String[])null;
//			if ((this.uniqueColumns != null) && (this.uniqueColumns.length > 0))
//				arrayOfString = this.uniqueColumns;
//			if (arrayOfString != null)
//			{
//				boolean[] arrayOfBoolean = new boolean[arrayOfString.length];
//				for (int i = 0; i < arrayOfBoolean.length; i++)
//					arrayOfBoolean[i] = false;
//				SortDescriptor localSortDescriptor = new SortDescriptor("UIDX$", arrayOfString, arrayOfBoolean, Locale.getDefault().toString(), 3);
//				this.masterDataSet.dropIndex(localSortDescriptor, null);
//				this.masterDataSet.addIndex(localSortDescriptor, null);
//			}
//		}
//		finally
//		{
//			this.masterLoading = bool;
//		}
//	}
//
//	protected void buildDetailUniqueIndexes()
//	{
//		Iterator localIterator = this.detailDataSets.iterator();
//		while (localIterator.hasNext())
//		{
//			StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//			boolean bool1 = false;
//			Object localObject1 = this.detailLoadings.get(localStorageDataSet);
//			if (localObject1 != null)
//				bool1 = ((Boolean)localObject1).booleanValue();
//			this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(true));
//			try
//			{
//				boolean bool2 = true;
//				if (this.detailDuplicateChecking.containsKey(localStorageDataSet))
//					bool2 = ((Boolean)this.detailDuplicateChecking.get(localStorageDataSet)).booleanValue();
//				String[] arrayOfString = (String[])null;
//				if (this.detailUniqueColumns.containsKey(localStorageDataSet))
//					arrayOfString = (String[])this.detailUniqueColumns.get(localStorageDataSet);
//				if ((arrayOfString == null) || (arrayOfString.length == 0))
//					arrayOfString = (String[])this.detailKeyColumns.get(localStorageDataSet);
//				if ((bool2) && (arrayOfString != null) && (arrayOfString.length > 0))
//				{
//					boolean[] arrayOfBoolean = new boolean[arrayOfString.length];
//					for (int i = 0; i < arrayOfBoolean.length; i++)
//						arrayOfBoolean[i] = false;
//					SortDescriptor localSortDescriptor = new SortDescriptor("UIDX$", arrayOfString, arrayOfBoolean, Locale.getDefault().toString(), 3);
//					localStorageDataSet.dropIndex(localSortDescriptor, null);
//					localStorageDataSet.addIndex(localSortDescriptor, null);
//				}
//			}
//			finally
//			{
//				this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(bool1));
//			}
//		}
//	}
//
//	protected Object prepareData()
//	throws Exception
//	{
//		return null;
//	}
//
//	protected void prepared(Object paramObject)
//	throws Exception
//	{
//	}
//
//	protected void prepareConditions()
//	{
//	}
//
//	protected boolean beforeRefresh()
//	{
//		return true;
//	}
//
//	protected void afterRefresh()
//	{
//	}
//
//	protected void afterNavigate()
//	{
//	}
//
//	protected boolean beforeInsert()
//	{
//		return true;
//	}
//
//	protected void afterInsert()
//	{
//	}
//
//	protected boolean beforeCancel()
//	{
//		return true;
//	}
//
//	protected void afterCancel()
//	{
//	}
//
//	protected boolean beforeSave()
//	{
//		return true;
//	}
//
//	protected void afterSave()
//	{
//	}
//
//	protected boolean beforeDelete()
//	{
//		return true;
//	}
//
//	protected void afterDelete()
//	{
//	}
//
//	protected void checkPrivileges()
//	throws Exception
//	{
//	}
//
//	protected void buildFilter()
//	throws Exception
//	{
//	}
//
//	protected Object buildSearchKey()
//	throws Exception
//	{
//		return null;
//	}
//
//	protected Object buildSearchKeyFromString(String paramString)
//	{
//		String[] arrayOfString1 = paramString.split(";");
//		String[] arrayOfString2 = this.keyColumns;
//		if (arrayOfString2 == null)
//		{
//			arrayOfString2 = new String[arrayOfString1.length];
//			for (int i = 0; i < arrayOfString1.length; i++)
//			{
//				String str1 = arrayOfString1[i];
//				int k = str1.indexOf("=");
//				if (k < 0)
//					k = str1.length();
//				arrayOfString2[i] = str1.substring(0, k);
//			}
//		}
//		Object[] arrayOfObject = new Object[arrayOfString2.length];
//		for (int j = 0; j < arrayOfObject.length; j++)
//		{
//			String str2 = arrayOfString2[j];
//			String str3 = null;
//			for (int m = 0; m < arrayOfString1.length; m++)
//			{
//				String localObject = arrayOfString1[m];
//				int n = ((String)localObject).indexOf("=");
//				if ((n <= 0) || (!((String)localObject).substring(0, n).equalsIgnoreCase(str2)) || (n + 1 >= ((String)localObject).length()))
//					continue;
//				str3 = ((String)localObject).substring(n + 1);
//			}
//			if ((str3 == null) || (str3.length() <= 0))
//				continue;
//			int m = this.masterDataSet.getColumn(str2).getDataType();
//			Object localObject = new Variant();
//			((Variant)localObject).setFromString(m, str3);
//			arrayOfObject[j] = ((Variant)localObject).getAsObject();
//		}
//		return arrayOfObject.length == 1 ? arrayOfObject[0] : arrayOfObject;
//	}
//
//	protected void validateSearch(Object paramObject)
//	throws Exception
//	{
//	}
//
//	protected void afterSearch()
//	{
//	}
//
//	private boolean E()
//	{
//		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
//	}
//
//	private boolean F()
//	{
//		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
//	}
//
//	private boolean C()
//	{
//		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
//	}
//
//	protected Object buildOldKey()
//	{
//		if (this.masterDataSet.getUpdatedRowCount() == 0)
//			return buildKey();
//		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
//			return null;
//		DataSetView localDataSetView = new DataSetView();
//		this.masterDataSet.getUpdatedRows(localDataSetView);
//		DataRow localDataRow = new DataRow(this.masterDataSet);
//		localDataSetView.first();
//		Object[] arrayOfObject1 = new Object[this.keyColumns.length];
//		Variant localVariant = new Variant();
//		for (int i = 0; i < this.keyColumns.length; i++)
//		{
//			this.masterDataSet.getOriginalRow(localDataSetView, localDataRow);
//			localDataRow.getVariant(this.keyColumns[i], localVariant);
//			arrayOfObject1[i] = localVariant.getAsObject();
//		}
//		if (this.foreignKey == null)
//		{
//			if (this.keyColumns.length > 1)
//				return arrayOfObject1;
//			return arrayOfObject1[0];
//		}
//		Object[] arrayOfObject2;
//		if (this.foreignKey.getClass().isArray())
//		{
//			int j = ((Object[])this.foreignKey).length;
//			arrayOfObject2 = new Object[arrayOfObject1.length + j];
//			System.arraycopy(this.foreignKey, 0, arrayOfObject2, 0, j);
//		}
//		else
//		{
//			arrayOfObject2 = new Object[arrayOfObject1.length + 1];
//			arrayOfObject2[0] = this.foreignKey;
//		}
//		System.arraycopy(arrayOfObject1, 0, arrayOfObject2, arrayOfObject2.length - arrayOfObject1.length, arrayOfObject1.length);
//		return arrayOfObject2;
//	}
//
//	protected Object buildKey(long paramLong)
//	{
//		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
//			return null;
//		Object[] arrayOfObject1 = new Object[this.keyColumns.length];
//		Variant localVariant = new Variant();
//		for (int i = 0; i < this.keyColumns.length; i++)
//		{
//			if (paramLong >= 0L)
//				this.masterDataSet.getVariant(this.keyColumns[i], paramLong, localVariant);
//			else
//				this.masterDataSet.getVariant(this.keyColumns[i], localVariant);
//			arrayOfObject1[i] = localVariant.getAsObject();
//		}
//		if (this.foreignKey == null)
//		{
//			if (this.keyColumns.length > 1)
//				return arrayOfObject1;
//			return arrayOfObject1[0];
//		}
//		Object[] arrayOfObject2;
//		if (this.foreignKey.getClass().isArray())
//		{
//			int j = ((Object[])this.foreignKey).length;
//			arrayOfObject2 = new Object[arrayOfObject1.length + j];
//			System.arraycopy(this.foreignKey, 0, arrayOfObject2, 0, j);
//		}
//		else
//		{
//			arrayOfObject2 = new Object[arrayOfObject1.length + 1];
//			arrayOfObject2[0] = this.foreignKey;
//		}
//		System.arraycopy(arrayOfObject1, 0, arrayOfObject2, arrayOfObject2.length - arrayOfObject1.length, arrayOfObject1.length);
//		return arrayOfObject2;
//	}
//
//	protected Object buildKey()
//	{
//		return buildKey(-1L);
//	}
//
//	protected void setKey(Object paramObject)
//	{
//		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
//			return;
//		Variant localVariant = new Variant();
//		for (int i = 0; i < this.keyColumns.length; i++)
//		{
//			Object localObject1;
//			if (this.foreignKey == null)
//			{
//				if (this.keyColumns.length > 1)
//					localObject1 = ((Object[])paramObject)[i];
//				else
//					localObject1 = paramObject;
//			}
//			else
//			{
//				int j;
//				if (this.foreignKey.getClass().isArray())
//					j = ((Object[])this.foreignKey).length;
//				else
//					j = 1;
//				localObject1 = ((Object[])paramObject)[(i + j)];
//			}
//			this.masterDataSet.getVariant(this.keyColumns[i], localVariant);
//			Object localObject2 = localVariant.getAsObject();
//			if ((localObject2 != null) && (localVariant.getAsObject().equals(localObject1)))
//				continue;
//			if ((this.masterDataSet.getStatus() & 0x4 & 0x2) == 0)
//				this.masterDataSet.editRow();
//			localVariant.setAsObject(localObject1, this.masterDataSet.getColumn(this.keyColumns[i]).getDataType());
//			this.masterDataSet.loadValue(this.keyColumns[i], localVariant);
//		}
//		if (this.masterDataSet.isEditing())
//			this.masterDataSet.post();
//	}
//
//	protected boolean isMonoMode()
//	{
//		return this.K;
//	}
//
//	protected boolean isSelecting()
//	{
//		return this.H;
//	}
//
//	protected boolean isModified()
//	{
//		return this.modified;
//	}
//
//	protected void setModified(boolean paramBoolean)
//	{
//		if (this.modified == paramBoolean)
//			return;
//		if (!paramBoolean)
//		{
//			if (this.masterDataSet.changesPending())
//				this.masterDataSet.mergeChanges(true);
//			Iterator localIterator = this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//				if (!localStorageDataSet.changesPending())
//					continue;
//				localStorageDataSet.mergeChanges(true);
//			}
//		}
//		this.modified = paramBoolean;
//		showStatus();
//	}
//
//	protected boolean isInletModified()
//	{
//		return this.V;
//	}
//
//	protected void setInletModified(boolean paramBoolean)
//	{
//		if (this.V == paramBoolean)
//			return;
//		this.V = paramBoolean;
//		showStatus();
//	}
//
//	protected Object listEntity(Object paramObject)
//	throws Exception
//	{
//		if (!E())
//			throw new Exception("Method listEntity should be overridden.");
//		VariantHolder localVariantHolder1 = new VariantHolder();
//		localVariantHolder1.value = new TransientRecordSet();
//		VariantHolder localVariantHolder2 = new VariantHolder();
//		BaseMasterDetail localEntityListable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
//		if (!localEntityListable.list(pranMap, paramObject, localVariantHolder1, localVariantHolder2))
//			throw new RemoteException((String)localVariantHolder2.value);
//		return localVariantHolder1.value;
//	}
//
//	protected Object buildDataStructure()
//	{
//		if (this.detailDataSets.size() == 0)
//			return new TransientRecordSet();
//		TransientRecordSet[] arrayOfTransientRecordSet = new TransientRecordSet[this.detailDataSets.size() + 1];
//		for (int i = 0; i < this.detailDataSets.size() + 1; i++)
//			arrayOfTransientRecordSet[i] = new TransientRecordSet();
//		return arrayOfTransientRecordSet;
//	}
//
//	protected Object fetchEntity(Object paramObject)
//	throws Exception
//	{
//		if (!F())
//			throw new Exception("Method fetchEntity should be overridden.");
//		VariantHolder localVariantHolder1 = new VariantHolder();
//		localVariantHolder1.value = buildDataStructure();
//		VariantHolder localVariantHolder2 = new VariantHolder();
//		BaseMasterDetail localEntityMonoReadable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
//		if (!localEntityMonoReadable.get(pranMap,paramObject, localVariantHolder1, localVariantHolder2))
//			throw new RemoteException((String)localVariantHolder2.value);
//		return localVariantHolder1.value;
//	}
//
//	protected void loadEntity(Object paramObject)
//	{
//		RecordSet localRecordSet1 = null;
//		if (this.detailDataSets.size() == 0)
//			localRecordSet1 = (RecordSet)paramObject;
//		else
//			localRecordSet1 = ((RecordSet[])paramObject)[0];
//		int i = 0;
//		if (localRecordSet1 != null)
//		{
//			if (this.masterDataSet.isEmpty())
//			{
//				DataSetHelper.loadFromRecordSet(this.masterDataSet, localRecordSet1, true, null, 1);
//			}
//			else
//			{
//				if ((this.keyColumns != null) && (this.keyColumns.length > 0))
//				{
//					Object localObject = buildKey();
//					if (this.keyColumns.length == 1)
//						i = localObject.equals(localRecordSet1.getRecord(0).getField(this.keyColumns[0]).getAsObject()) ? 0 : 1;
//					else
//						for (int k = 0; k < this.keyColumns.length; k++)
//						{
//							i = ((Object[])localObject)[k].equals(localRecordSet1.getRecord(0).getField(this.keyColumns[k]).getAsObject()) ? 0 : 1;
//							if (i != 0)
//								break;
//						}
//				}
//				if (i == 0)
//				{
//					DataSetHelper.loadRowFromRecord(this.masterDataSet, localRecordSet1.getRecord(0), true);
//					this.masterDataSet.mergeChanges(true);
//				}
//			}
//			this.masterDataSet.goToRow(this.masterDataSet.getLongRow());
//		}
//		if (i == 0)
//		{
//			for (int j = 0; j < this.detailDataSets.size(); j++)
//			{
//				RecordSet localRecordSet2 = ((RecordSet[])paramObject)[(j + 1)];
//				if (localRecordSet2 == null)
//					continue;
//				((StorageDataSet)this.detailDataSets.get(j)).empty();
//				((StorageDataSet)this.detailDataSets.get(j)).reset();
//				DataSetHelper.loadFromRecordSet((StorageDataSet)this.detailDataSets.get(j), localRecordSet2);
//			}
//			buildDetailUniqueIndexes();
//			for (int j = 0; j < this.detailDataSets.size(); j++)
//				((StorageDataSet)this.detailDataSets.get(j)).goToRow(((StorageDataSet)this.detailDataSets.get(j)).getLongRow());
//		}
//		this.modified = false;
//	}
//
//	protected void validateMaster(ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
//	throws Exception
//	{
//	}
//
//	protected void validateDetail(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
//	throws Exception
//	{
//	}
//
//	protected Object addEntity(Object paramObject1, Object paramObject2)
//	throws Exception
//	{
//		if (!C())
//			throw new Exception("Method addEntity should be overridden.");
//		VariantHolder localVariantHolder1 = new VariantHolder();
//		VariantHolder localVariantHolder2 = new VariantHolder();
//		VariantHolder localVariantHolder3 = new VariantHolder();
//		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
//		if (!localEntityMonoEditable.add(pranMap,paramObject1, paramObject2, localVariantHolder1, localVariantHolder2, localVariantHolder3))
//			throw new RemoteException((String)localVariantHolder3.value);
//		return new Object[] { localVariantHolder1.value, localVariantHolder2.value };
//	}
//
//	protected Object modifyEntity(Object paramObject1, Object paramObject2, Object paramObject3)
//	throws Exception
//	{
//		if (!C())
//			throw new Exception("Method modifyEntity should be overridden.");
//		VariantHolder localVariantHolder1 = new VariantHolder();
//		localVariantHolder1.value = paramObject3;
//		VariantHolder localVariantHolder2 = new VariantHolder();
//		VariantHolder localVariantHolder3 = new VariantHolder();
//		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
//		if (!localEntityMonoEditable.modify(pranMap,paramObject1, paramObject2, localVariantHolder1, localVariantHolder2, localVariantHolder3))
//			throw new RemoteException((String)localVariantHolder3.value);
//		return new Object[] { localVariantHolder1.value, localVariantHolder2.value };
//	}
//
//	protected void removeEntity(Object paramObject)
//	throws Exception
//	{
//		if (!C())
//			throw new Exception("Method removeEntity should be overridden.");
//		VariantHolder localVariantHolder1 = new VariantHolder();
//		localVariantHolder1.value = new TransientRecordSet();
//		VariantHolder localVariantHolder2 = new VariantHolder();
//		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
//		if (!localEntityMonoEditable.remove(pranMap,paramObject, localVariantHolder2))
//			throw new RemoteException((String)localVariantHolder2.value);
//	}
//
//	protected void detailBatch()
//	{
//	}
//
//	protected boolean listInlet()
//	{
//		return true;
//	}
//
//	protected void clearInlet()
//	{
//	}
//
//	protected boolean flushInlet()
//	{
//		return true;
//	}
//
//	protected void showInletStatus()
//	{
//	}
//
//	private static void B(Component paramComponent, JPopupMenu paramJPopupMenu)
//	{
//	}
//
//	protected class CancelAction extends AbstractAction
//	{
//		public CancelAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/cancel.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("CANCEL"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (!MasterDetailFrame.this.isModified()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			if (!MasterDetailFrame.this.beforeCancel())
//				return;
//			Iterator localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//				MasterDetailFrame.this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(true));
//				try
//				{
//					localStorageDataSet.cancel();
//					localStorageDataSet.reset();
//					TdDataSet localTdDataSet = (TdDataSet)MasterDetailFrame.this.detailTdDataSets.get(localStorageDataSet);
//					if (localTdDataSet != null)
//						localTdDataSet.build();
//				}
//				finally
//				{
//					MasterDetailFrame.this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(false));
//				}
//				if (localStorageDataSet.getLongRow() >= localStorageDataSet.getRowCount())
//					localStorageDataSet.goToRow(localStorageDataSet.getRowCount() - 1);
//				else
//					localStorageDataSet.goToRow(localStorageDataSet.getLongRow());
//			}
//			MasterDetailFrame.this.masterLoading = true;
//			try
//			{
//				MasterDetailFrame.this.masterDataSet.cancel();
//				MasterDetailFrame.this.masterDataSet.reset();
//			}
//			finally
//			{
//				MasterDetailFrame.this.masterLoading = false;
//			}
//			MasterDetailFrame.this.afterCancel();
//			if (MasterDetailFrame.this.masterDataSet.isEmpty())
//				MasterDetailFrame.this.clearInlet();
//			else if (MasterDetailFrame.this.masterDataSet.getLongRow() >= MasterDetailFrame.this.masterDataSet.getRowCount())
//				MasterDetailFrame.this.masterDataSet.goToRow(MasterDetailFrame.this.masterDataSet.getRowCount() - 1);
//			else if (MasterDetailFrame.F(MasterDetailFrame.this) == 9223372036854775807L)
//				MasterDetailFrame.this.masterDataSet.goToRow(MasterDetailFrame.this.masterDataSet.getLongRow() - 1L);
//			else
//				MasterDetailFrame.this.masterDataSet.goToRow(MasterDetailFrame.this.masterDataSet.getLongRow());
//			MasterDetailFrame.this.setModified(false);
//		}
//	}
//
//	protected class Circulator
//	{
//
//
//
//		private Runnable B = null;
//
//		public Circulator()
//		{
//			this(null);
//		}
//
//		public Circulator(Runnable arg2)
//		{
//			this.B = arg2;
//		}
//
//		public void setEngine(Runnable paramRunnable)
//		{
//			this.B = paramRunnable;
//		}
//
//		public void start()
//		{
//			int[] arrayOfInt1 = MasterDetailFrame.this.listTable.getSelectedRows();
//			if ((arrayOfInt1 == null) || (arrayOfInt1.length == 0))
//				if (MasterDetailFrame.this.masterDataSet.getRow() >= 0)
//					arrayOfInt1 = new int[] { MasterDetailFrame.this.masterDataSet.getRow() };
//				else
//					return;
//			final int[] arrayOfInt2 = arrayOfInt1;
//			int i = MasterDetailFrame.this.detailDataSets.size() > 0 ? 1 : 0;
//			final	boolean bool = MasterDetailFrame.this.inletEnabled;
//			final VariantHolder localVariantHolder = new VariantHolder();
//			localVariantHolder.value = Integer.valueOf(0);
//			if ((arrayOfInt2.length > 1) && (i != 0))
//				MasterDetailFrame.this.worker.addMonitor(new WireWorker.Monitor()
//				{
//					public void notify(int paramInt)
//					{
//						int i = 0;
//						Object localObject = null;
//						if (paramInt == 0)
//						{
//							if (((Integer)this.A.value).intValue() < this.B.length)
//								try
//							{
//									MasterDetailFrame.Circulator.this.B.run();
//							}
//							catch (RuntimeException localRuntimeException)
//							{
//								i = 1;
//								localObject = localRuntimeException;
//							}
//							if (i == 0)
//							{
//								this.A.value = Integer.valueOf(((Integer)this.A.value).intValue() + 1);
//								if (((Integer)this.A.value).intValue() < this.B.length)
//									MasterDetailFrame.this.masterDataSet.goToRow(this.B[((Integer)this.A.value).intValue()]);
//								else if (((Integer)this.A.value).intValue() == this.B.length)
//									MasterDetailFrame.this.masterDataSet.goToRow(this.B[0]);
//								else
//									i = 1;
//							}
//						}
//						else
//						{
//							i = 1;
//						}
//						if (i != 0)
//						{
//							MasterDetailFrame.this.inletEnabled = this.C;
//							MasterDetailFrame.this.worker.removeMonitor(this);
//							Arrays.sort(this.B);
//							for (int j : this.B)
//								MasterDetailFrame.this.listTable.getSelectionModel().addSelectionInterval(j, j);
//						}
////						if (localObject != null)
////						throw localObject;
//					}
//
//					final Circulator D;
//					private final VariantHolder A;
//					private final int B[];
//					private final boolean C;
//
//
//					{
//						D = Circulator.this;
//						A = localVariantHolder;
//						B = arrayOfInt2;
//						C = bool;
//					}
//
//				});
//			this.B.run();
//			if (i != 0)
//			{
//				localVariantHolder.value = Integer.valueOf(((Integer)localVariantHolder.value).intValue() + 1);
//				if (((Integer)localVariantHolder.value).intValue() < arrayOfInt2.length)
//				{
//					MasterDetailFrame.this.inletEnabled = false;
//					MasterDetailFrame.this.masterDataSet.goToRow(arrayOfInt2[((Integer)localVariantHolder.value).intValue()]);
//				}
//			}
//			else
//			{
//				try
//				{
//					MasterDetailFrame.this.inletEnabled = false;
//					while (((Integer)localVariantHolder.value).intValue() < arrayOfInt2.length)
//					{
//						localVariantHolder.value = Integer.valueOf(((Integer)localVariantHolder.value).intValue() + 1);
//						MasterDetailFrame.this.masterDataSet.goToRow(arrayOfInt2[((Integer)localVariantHolder.value).intValue()]);
//						this.B.run();
//					}
//				}
//				finally
//				{
//					MasterDetailFrame.this.inletEnabled = bool;
//					MasterDetailFrame.this.masterDataSet.goToRow(arrayOfInt2[0]);
//					Arrays.sort(arrayOfInt2);
//					for (int j : arrayOfInt2)
//						MasterDetailFrame.this.listTable.getSelectionModel().addSelectionInterval(j, j);
//				}
//			}
//		}
//	}
//
//	protected class CloseAction extends AbstractAction
//	{
//		public CloseAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/close.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("CLOSE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			MasterDetailFrame.A(MasterDetailFrame.this, 25550);
//		}
//	}
//
//	protected class DeleteAction extends AbstractAction
//	{
//		public DeleteAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/delete.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("DELETE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || ((MasterDetailFrame.this.masterDataSet.isEmpty()) && (!MasterDetailFrame.this.masterDataSet.isEditingNewRow())) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			if ((MasterDetailFrame.this.masterDataSet.getStatus() & 0x4) != 0)
//			{
//				MasterDetailFrame.this.cancelAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.cancelAction, 0, (String)MasterDetailFrame.this.cancelAction.getValue("ActionCommandKey")));
//			}
//			else
//			{
//				if (JOptionPane.showConfirmDialog(MasterDetailFrame.this, DataModel.getDefault().getSentence("MSG_DELETE_PROMPT"), MasterDetailFrame.this.getTitle(), 0, 3) != 0)
//					return;
//				if (!MasterDetailFrame.this.beforeDelete())
//					return;
//				int[] arrayOfInt = MasterDetailFrame.this.listTable.getSelectedRows();
//				final long[] arrayOfLong = arrayOfInt.length > 1 ? new long[arrayOfInt.length] : null;
//				if (arrayOfLong != null)
//				{
//					boolean bool = MasterDetailFrame.this.masterLoading;
//					MasterDetailFrame.this.masterLoading = true;
//					try
//					{
//						for (int i = 0; i < arrayOfLong.length; i++)
//						{
//							MasterDetailFrame.this.masterDataSet.goToRow(arrayOfInt[i]);
//							arrayOfLong[i] = MasterDetailFrame.this.masterDataSet.getInternalRow();
//						}
//					}
//					finally
//					{
//						MasterDetailFrame.this.masterLoading = bool;
//					}
//				}
//				MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//				{
//					public Object work()
//					throws Throwable
//					{
//						if (arrayOfLong != null)
//						{
//							boolean bool = MasterDetailFrame.this.masterLoading;
//							MasterDetailFrame.this.masterLoading = true;
//							try
//							{
//								for (long l : arrayOfLong)
//								{
//									MasterDetailFrame.this.masterDataSet.goToInternalRow(l);
//									MasterDetailFrame.this.removeEntity(MasterDetailFrame.this.buildKey());
//								}
//							}
//							finally
//							{
//								MasterDetailFrame.this.masterLoading = bool;
//							}
//						}
//						else
//						{
//							MasterDetailFrame.this.removeEntity(MasterDetailFrame.this.buildKey());
//						}
//						return null;
//					}
//				});
//				MasterDetailFrame.this.worker.setHook(new WireWorker.Hook()
//				{
//					public void hook(Object paramObject)
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//						for (int i = 0; i < MasterDetailFrame.this.detailDataSets.size(); i++)
//						{
//							((StorageDataSet)MasterDetailFrame.this.detailDataSets.get(i)).empty();
//							((StorageDataSet)MasterDetailFrame.this.detailDataSets.get(i)).reset();
//						}
//						if (arrayOfLong!= null)
//						{
//							boolean bool = MasterDetailFrame.this.masterLoading;
//							MasterDetailFrame.this.masterLoading = true;
//							try
//							{
//								for (long l : arrayOfLong)
//								{
//									MasterDetailFrame.this.masterDataSet.goToInternalRow(l >= 0L ? l : -l);
//									MasterDetailFrame.this.masterDataSet.deleteRow();
//								}
//							}
//							finally
//							{
//								MasterDetailFrame.this.masterLoading = bool;
//							}
//							if (!MasterDetailFrame.this.masterDataSet.isEmpty())
//								MasterDetailFrame.this.masterDataSet.goToRow(MasterDetailFrame.this.masterDataSet.getRow());
//						}
//						else
//						{
//							MasterDetailFrame.this.masterDataSet.deleteRow();
//						}
//						MasterDetailFrame.this.masterDataSet.mergeChanges(false);
//						if (MasterDetailFrame.this.masterDataSet.isEmpty())
//							MasterDetailFrame.this.clearInlet();
//						MasterDetailFrame.this.modified=false;
//						MasterDetailFrame.this.showStatus();
//						MasterDetailFrame.this.afterDelete();
//					}
//				});
//				MasterDetailFrame.this.worker.setCorrector(new WireWorker.Corrector()
//				{
//					public void correct(Throwable paramThrowable)
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//					}
//				});
//				MasterDetailFrame.this.worker.setResumer(new WireWorker.Resumer()
//				{
//					public void resume()
//					{
//						MasterDetailFrame.this.worker.setHook(null);
//						MasterDetailFrame.this.worker.setCorrector(null);
//						MasterDetailFrame.this.worker.setResumer(null);
//					}
//				});
//				MasterDetailFrame.this.worker.start();
//			}
//		}
//	}
//
//	protected class DetailBatchAction extends AbstractAction
//	{
//		public DetailBatchAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/batchDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW_LINES"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			Object localObject = MasterDetailFrame.this.detailTable.getDataSet();
//			if (localObject == null)
//				localObject = MasterDetailFrame.this.detailDataSet;
//			if (!((DataSet)localObject).isOpen())
//				return;
//			MasterDetailFrame.this.detailDataSet.last();
//			try
//			{
//				MasterDetailFrame.this.detailBatch();
//			}
//			catch (DataSetException localDataSetException)
//			{
//				if (DataSetException.getExceptionListeners() != null)
//				{
//					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(MasterDetailFrame.this.detailDataSet, MasterDetailFrame.this.detailTable, localDataSetException));
//					return;
//				}
//				throw localDataSetException;
//			}
//		}
//	}
//
//	protected class DetailClearAction extends AbstractAction
//	{
//		public DetailClearAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("CLEAR_LINES"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			Object localObject = MasterDetailFrame.this.detailTable.getDataSet();
//			if (localObject == null)
//				localObject = MasterDetailFrame.this.detailDataSet;
//			if ((!((DataSet)localObject).isOpen()) || ((((DataSet)localObject).isEmpty()) && (!((DataSet)localObject).isEditingNewRow())))
//				return;
//			((DataSet)localObject).deleteAllRows();
//		}
//	}
//
//	protected class DetailDeleteAction extends AbstractAction
//	{
//		public DetailDeleteAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("DELETE_LINE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			Object localObject = MasterDetailFrame.this.detailTable.getDataSet();
//			if (localObject == null)
//				localObject = MasterDetailFrame.this.detailDataSet;
//			if ((!((DataSet)localObject).isOpen()) || ((((DataSet)localObject).isEmpty()) && (!((DataSet)localObject).isEditingNewRow())))
//				return;
//			if (MasterDetailFrame.this.detailTable.getSelectedRowCount() > 1)
//			{
//				int[] arrayOfInt = MasterDetailFrame.this.detailTable.getSelectedRows();
//				long[] arrayOfLong1 = new long[arrayOfInt.length];
//				for (int i = 0; i < arrayOfInt.length; i++)
//				{
//					((DataSet)localObject).goToRow(arrayOfInt[i]);
//					arrayOfLong1[i] = ((DataSet)localObject).getInternalRow();
//				}
//				for (long l : arrayOfLong1)
//				{
//					((DataSet)localObject).goToInternalRow(l);
//					((DataSet)localObject).deleteRow();
//				}
//			}
//			else
//			{
//				((DataSet)localObject).deleteRow();
//			}
//		}
//	}
//
//	protected class DetailNewAction extends AbstractAction
//	{
//		public DetailNewAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/addDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW_LINE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.detailDataSet.isOpen()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			try
//			{
//				Object localObject = MasterDetailFrame.this.detailTable.getDataSet();
//				if (localObject == null)
//					localObject = MasterDetailFrame.this.detailDataSet;
//				((DataSet)localObject).last();
//				((DataSet)localObject).insertRow(false);
//			}
//			catch (DataSetException localDataSetException)
//			{
//				if (DataSetException.getExceptionListeners() != null)
//				{
//					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(MasterDetailFrame.this.detailDataSet, MasterDetailFrame.this.detailTable, localDataSetException));
//					return;
//				}
//				throw localDataSetException;
//			}
//		}
//	}
//
//	protected class FilterAction extends AbstractAction
//	{
//		public FilterAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/filter.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("FILTER"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.filterMenu.show(MasterDetailFrame.this.filterButton, 0, MasterDetailFrame.this.filterButton.getHeight());
//		}
//	}
//
//	private class FilterAdvancedMenuItemActionListener
//	implements ActionListener
//	{
//		private FilterAdvancedMenuItemActionListener()
//		{
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.filterAdvancedPane.setVisible(true);
//			MasterDetailFrame.this.filterSimplePanel.setVisible(false);
//			MasterDetailFrame.this.listPane.setResizeWeight(0.5D);
//			MasterDetailFrame.this.listPane.resetToPreferredSizes();
//		}
//	}
//
//	private class FilterMenuItemActionListener
//	implements ActionListener
//	{
//		private FilterMenuItemActionListener()
//		{
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.filterPanel.setVisible(MasterDetailFrame.this.filterMenuItem.isSelected());
//			MasterDetailFrame.this.listPane.resetToPreferredSizes();
//		}
//	}
//
//	private class FilterSimpleMenuItemActionListener
//	implements ActionListener
//	{
//		private FilterSimpleMenuItemActionListener()
//		{
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.filterSimplePanel.setVisible(true);
//			MasterDetailFrame.this.filterAdvancedPane.setVisible(false);
//			MasterDetailFrame.this.listPane.setResizeWeight(0.0D);
//			MasterDetailFrame.this.listPane.resetToPreferredSizes();
//		}
//	}
//
//	protected class FirstAction extends AbstractAction
//	{
//		public FirstAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/first.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("FIRST"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (MasterDetailFrame.this.isModified()) || (MasterDetailFrame.this.masterDataSet.atFirst()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			MasterDetailFrame.this.masterDataSet.first();
//		}
//	}
//
//	protected class FlushInletAction extends AbstractAction
//	{
//		public FlushInletAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/saveDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("SAVE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			if (MasterDetailFrame.this.flushInlet())
//				MasterDetailFrame.this.showInletStatus();
//		}
//	}
//
//	protected class LastAction extends AbstractAction
//	{
//		public LastAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/last.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("LAST"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (MasterDetailFrame.this.isModified()) || (MasterDetailFrame.this.masterDataSet.atLast()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			MasterDetailFrame.this.masterDataSet.last();
//		}
//	}
//
//	protected class ListInletAction extends AbstractAction
//	{
//		public ListInletAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/refreshDetail.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("REFRESH"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			if (MasterDetailFrame.this.listInlet())
//				MasterDetailFrame.this.showInletStatus();
//		}
//	}
//
//	private class ListTableMouseListener extends MouseAdapter
//	{
//		private ListTableMouseListener()
//		{
//		}
//
//		public void mouseClicked(MouseEvent paramMouseEvent)
//		{
//			if ((paramMouseEvent.getClickCount() >= 2) && (MasterDetailFrame.this.selectButton.isShowing()) && (MasterDetailFrame.this.selectButton.isEnabled()))
//				MasterDetailFrame.this.selectButton.doClick();
//		}
//	}
//
//	protected class NewAction extends AbstractAction
//	{
//		public NewAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/add.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (MasterDetailFrame.this.isModified()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			MasterDetailFrame.this.mainToolBar.requestFocusInWindow();
//			if (!MasterDetailFrame.this.beforeInsert())
//				return;
//			MasterDetailFrame.this.masterLoading = true;
//			try
//			{
//				if (!MasterDetailFrame.this.masterDataSet.atLast())
//					MasterDetailFrame.this.masterDataSet.last();
//			}
//			finally
//			{
//				MasterDetailFrame.this.masterLoading = false;
//			}
//			MasterDetailFrame.this.masterDataSet.insertRow(false);
//			MasterDetailFrame.this.afterInsert();
//			if (MasterDetailFrame.this.isModified())
//				MasterDetailFrame.this.showStatus();
//			else
//				MasterDetailFrame.this.setModified(true);
//		}
//	}
//
//	protected class NextAction extends AbstractAction
//	{
//		public NextAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/next.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("NEXT"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (MasterDetailFrame.this.isModified()) || (MasterDetailFrame.this.masterDataSet.atLast()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			MasterDetailFrame.this.masterDataSet.next();
//		}
//	}
//
//	private class PlugInAction extends AbstractAction
//	{
//		PlugInAction(String arg2)
//		{
//			super(null);
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((MasterDetailFrame.this.masterDataSet.isEmpty()) || (MasterDetailFrame.this.masterDataSet.isEditingNewRow()))
//				return;
//			JButton localJButton;
//			if ((paramActionEvent.getSource() instanceof JButton))
//				localJButton = (JButton)paramActionEvent.getSource();
//			else
//				return;
//			String str1 = localJButton.getName();
//			if ((str1 != null) && (str1.length() != 0))
//			{
//				Properties localProperties = (Properties)MasterDetailFrame.C(MasterDetailFrame.this).get(str1);
//				String str2 = localProperties.getProperty("class");
//				String str3 = localProperties.getProperty("method");
//				String str4 = localProperties.getProperty("foreignKey");
//				try
//				{
//					Class localClass = Class.forName(str2);
//					Method localMethod = localClass.getMethod(str3, new Class[] { Component.class, Object.class });
//					Object localObject = MasterDetailFrame.this.buildKey();
//					if ((str4 != null) && (str4.length() > 0))
//					{
//						Object[] arrayOfObject;
//						if (localObject.getClass().isArray())
//						{
//							arrayOfObject = new Object[((Object[])localObject).length + 1];
//							arrayOfObject[0] = str4;
//							System.arraycopy(localObject, 0, arrayOfObject, 1, arrayOfObject.length - 1);
//						}
//						else
//						{
//							arrayOfObject = new Object[] { str4, localObject };
//						}
//						localObject = arrayOfObject;
//					}
//					localMethod.invoke(null, new Object[] { MasterDetailFrame.this, localObject });
//				}
//				catch (Exception localException)
//				{
//					localException.printStackTrace();
//				}
//			}
//		}
//	}
//
//	protected class PriorAction extends AbstractAction
//	{
//		public PriorAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/previous.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("PRIOR"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (MasterDetailFrame.this.isModified()) || (MasterDetailFrame.this.masterDataSet.atFirst()) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			MasterDetailFrame.this.masterDataSet.prior();
//		}
//	}
//
//	protected class RefreshAction extends AbstractAction
//	{
//		public RefreshAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/refresh.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("REFRESH"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.masterDataSet.empty();
//			MasterDetailFrame.this.masterDataSet.reset();
//			Iterator localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//				localStorageDataSet.empty();
//				localStorageDataSet.reset();
//			}
//			MasterDetailFrame.this.clearInlet();
//			MasterDetailFrame.A(MasterDetailFrame.this, -1L);
//			MasterDetailFrame.this.filterTree.clear();
//			MasterDetailFrame.this.sortList.clear();
//			try
//			{
//				if (MasterDetailFrame.this.filterSimpleMenuItem.isSelected())
//				{
//					MasterDetailFrame.this.buildFilter();
//				}
//				else
//				{
//					MasterDetailFrame.this.filterTree = MasterDetailFrame.this.conditionTreePanel.getTree();
//					MasterDetailFrame.this.sortList = MasterDetailFrame.this.sortListPanel.getList();
//				}
//			}
//			catch (Throwable localThrowable)
//			{
//				JOptionPane.showMessageDialog(MasterDetailFrame.this, ExceptionFormat.format(localThrowable), MasterDetailFrame.this.getTitle(), 0);
//				return;
//			}
//			final	HashMap localHashMap = new HashMap();
//			localHashMap.put("bound", MasterDetailFrame.this.boundTree);
//			localHashMap.put("filter", MasterDetailFrame.this.filterTree);
//			localHashMap.put("sort", MasterDetailFrame.this.sortList);
//			localHashMap.put("maxRows", (Integer)MasterDetailFrame.this.maxRowsSpinner.getValue());
//			if (!MasterDetailFrame.this.beforeRefresh())
//				return;
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//			{
////				private Object A;
//
//				public Object work()
//				throws Throwable
//				{
//					return MasterDetailFrame.this.listEntity(localHashMap);
//				}
//			});
//			MasterDetailFrame.this.worker.setHook(new WireWorker.Hook()
//			{
//				public void hook(Object paramObject)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					DataSetHelper.loadFromRecordSet(MasterDetailFrame.this.masterDataSet, (RecordSet)paramObject, true, null, ((Integer)MasterDetailFrame.this.maxRowsSpinner.getValue()).intValue());
//					MasterDetailFrame.this.modified=false;
//					if (MasterDetailFrame.this.masterDataSet.getRowCount() <= 1000)
//						MasterDetailFrame.this.buildMasterUniqueIndex();
//					if (MasterDetailFrame.this.filterAutoHiddenMenuItem.isSelected())
//					{
//						MasterDetailFrame.this.filterMenuItem.setSelected(false);
//						MasterDetailFrame.this.filterPanel.setVisible(false);
//						MasterDetailFrame.this.listPane.resetToPreferredSizes();
//					}
//					if (!MasterDetailFrame.this.masterDataSet.isEmpty())
//					{
//						SwingUtilities.invokeLater(new Runnable()
//						{
//							public void run()
//							{
//								MasterDetailFrame.A(MasterDetailFrame.this, -1L);
//								MasterDetailFrame.this.masterDataSet.first();
//								MasterDetailFrame.this.showStatus();
//							}
//						});
//					}
//					else
//					{
//						MasterDetailFrame.A(MasterDetailFrame.this, -1L);
//						MasterDetailFrame.this.clearInlet();
//						MasterDetailFrame.this.showStatus();
//					}
//					MasterDetailFrame.this.afterRefresh();
//				}
//			});
//			MasterDetailFrame.this.worker.setCorrector(new WireWorker.Corrector()
//			{
//				public void correct(Throwable paramThrowable)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					MasterDetailFrame.this.showStatus();
//				}
//			});
//			MasterDetailFrame.this.worker.setResumer(new WireWorker.Resumer()
//			{
//				public void resume()
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					MasterDetailFrame.this.showStatus();
//				}
//			});
//			MasterDetailFrame.this.worker.start();
//		}
//	}
//
//	protected class SaveAction extends AbstractAction
//	{
//		public SaveAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/save.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("SAVE"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((!MasterDetailFrame.this.masterDataSet.isOpen()) || (!MasterDetailFrame.this.isModified()) || ((MasterDetailFrame.this.masterDataSet.isEmpty()) && (!MasterDetailFrame.this.masterDataSet.isEditingNewRow())) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			try
//			{
//				MasterDetailFrame.this.masterDataSet.post();
//			}
//			catch (DataSetException localDataSetException1)
//			{
//				if (DataSetException.getExceptionListeners() != null)
//				{
//					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(MasterDetailFrame.this.masterDataSet, MasterDetailFrame.this.listTable, localDataSetException1));
//					return;
//				}
//				throw localDataSetException1;
//			}
//			Iterator localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//				try
//				{
//					if (MasterDetailFrame.this.detailTdDataSets.get(localStorageDataSet) != null)
//						((TdDataSet)MasterDetailFrame.this.detailTdDataSets.get(localStorageDataSet)).post();
//					localStorageDataSet.post();
//				}
//				catch (DataSetException localDataSetException2)
//				{
//					if (DataSetException.getExceptionListeners() != null)
//					{
//						DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(localStorageDataSet, MasterDetailFrame.this.listTable, localDataSetException2));
//						return;
//					}
//					throw localDataSetException2;
//				}
//			}
//			if (!MasterDetailFrame.this.beforeSave())
//				return;
//			if (MasterDetailFrame.this.masterDataSet.isEditing())
//			{
//				MasterDetailFrame.this.masterLoading = true;
//				try
//				{
//					MasterDetailFrame.this.masterDataSet.post();
//				}
//				finally
//				{
//					MasterDetailFrame.this.masterLoading = false;
//				}
//			}
//			localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet	localObject2 = (StorageDataSet)localIterator.next();
//				MasterDetailFrame.this.detailLoadings.put(localObject2, Boolean.valueOf(true));
//				try
//				{
//					if (MasterDetailFrame.this.detailTdDataSets.get(localObject2) != null)
//						((TdDataSet)MasterDetailFrame.this.detailTdDataSets.get(localObject2)).post();
//					((StorageDataSet)localObject2).post();
//				}
//				finally
//				{
//					MasterDetailFrame.this.detailLoadings.put(localObject2, Boolean.valueOf(false));
//				}
//			}
//			DeltaRecordSet[] localObject2 = new DeltaRecordSet[MasterDetailFrame.this.detailDataSets.size() + 1];
//			for (int i = 0; i < localObject2.length; i++)
//				localObject2[i] = new DeltaRecordSet();
//			DataSetHelper.saveToDeltaRecordSet(MasterDetailFrame.this.masterDataSet, localObject2[0], false, false);
//			if (localObject2[0].recordCount() == 0)
//			{
//				localObject2[0].setTrace(false);
//				Record localRecord = new Record(localObject2[0].getFormat());
//				DataRow localDataRow = new DataRow(MasterDetailFrame.this.masterDataSet);
//				MasterDetailFrame.this.masterDataSet.getDataRow(localDataRow);
//				DataSetHelper.saveRowToRecord(localDataRow, localRecord);
//				localObject2[0].append((int)MasterDetailFrame.this.masterDataSet.getInternalRow(), 2, localRecord, (Record)localRecord.clone());
//				localObject2[0].setTrace(true);
//			}
//			for (int j = 0; j < MasterDetailFrame.this.detailDataSets.size(); j++)
//				DataSetHelper.saveToDeltaRecordSet((DataSet)MasterDetailFrame.this.detailDataSets.get(j), localObject2[(j + 1)], false, false);
//			final	Object localObject3 ;
//			if (localObject2.length == 1)
//				localObject3 = localObject2[0];
//			else
//				localObject3 = localObject2;
//
//
//			if ((MasterDetailFrame.this.masterDataSet.getStatus() & 0x4) != 0)
//				MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//				{
//
//					public Object work()
//					throws Throwable
//					{
//						return MasterDetailFrame.this.addEntity(MasterDetailFrame.this.buildKey(),localObject3 );
//					}
//				});
//			else
//				MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//				{
//					private Object A;
//					private Object B;
//					private Object C;
//
//					public Object work()
//					throws Throwable
//					{
//						return MasterDetailFrame.this.modifyEntity(MasterDetailFrame.this.buildOldKey(), localObject3, MasterDetailFrame.this.buildKey());
//					}
//				});
//			MasterDetailFrame.this.worker.setHook(new WireWorker.Hook()
//			{
//				public void hook(Object paramObject)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					if (((Object[])paramObject)[1] != null)
//					{
//						DeltaRecordSet localDeltaRecordSet1;
//						if (MasterDetailFrame.this.detailDataSets.size() > 0)
//							localDeltaRecordSet1 = ((DeltaRecordSet[])((Object[])paramObject)[1])[0];
//						else
//							localDeltaRecordSet1 = (DeltaRecordSet)((Object[])paramObject)[1];
//						if (localDeltaRecordSet1 != null)
//							DataSetHelper.backfillFromDeltaRecordSet(MasterDetailFrame.this.masterDataSet, localDeltaRecordSet1);
//						for (int j = 0; j < MasterDetailFrame.this.detailDataSets.size(); j++)
//						{
//							DeltaRecordSet localDeltaRecordSet2 = ((DeltaRecordSet[])((Object[])paramObject)[1])[(j + 1)];
//							if (localDeltaRecordSet2 == null)
//								continue;
//							DataSetHelper.backfillFromDeltaRecordSet((DataSet)MasterDetailFrame.this.detailDataSets.get(j), localDeltaRecordSet2);
//						}
//					}
//					MasterDetailFrame.this.setKey(((Object[])paramObject)[0]);
//					MasterDetailFrame.this.masterDataSet.mergeChanges(false);
//					MasterDetailFrame.A(MasterDetailFrame.this, MasterDetailFrame.this.masterDataSet.getInternalRow());
//					MasterDetailFrame.this.masterDataSet.goToRow(MasterDetailFrame.this.masterDataSet.getLongRow());
//					for (int i = 0; i < MasterDetailFrame.this.detailDataSets.size(); i++)
//					{
//						((StorageDataSet)MasterDetailFrame.this.detailDataSets.get(i)).mergeChanges(false);
//						((StorageDataSet)MasterDetailFrame.this.detailDataSets.get(i)).goToRow(((StorageDataSet)MasterDetailFrame.this.detailDataSets.get(i)).getLongRow());
//					}
//					MasterDetailFrame.this.modified=false;
//					MasterDetailFrame.this.showStatus();
//					MasterDetailFrame.this.afterSave();
//				}
//			});
//			MasterDetailFrame.this.worker.setCorrector(new WireWorker.Corrector()
//			{
//				public void correct(Throwable paramThrowable)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					for (int i = 0; i < MasterDetailFrame.this.detailDataSets.size(); i++);
//				}
//			});
//			MasterDetailFrame.this.worker.setResumer(new WireWorker.Resumer()
//			{
//				public void resume()
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					for (int i = 0; i < MasterDetailFrame.this.detailDataSets.size(); i++);
//				}
//			});
//			MasterDetailFrame.this.worker.start();
//		}
//	}
//
//	protected class SearchAction extends AbstractAction
//	{
//		public SearchAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/find.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("SEARCH"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if (!MasterDetailFrame.this.worker.isIdle())
//				return;
//			Object localObject;
//			if (MasterDetailFrame.H(MasterDetailFrame.this) != null)
//				localObject = MasterDetailFrame.H(MasterDetailFrame.this);
//			else
//				try
//			{
//					localObject = MasterDetailFrame.this.buildSearchKey();
//			}
//			catch (Throwable localThrowable)
//			{
//				JOptionPane.showMessageDialog(MasterDetailFrame.this, ExceptionFormat.format(localThrowable), MasterDetailFrame.this.getTitle(), 0);
//				return;
//			}
//			if (localObject == null)
//				return;
//			MasterDetailFrame.this.masterDataSet.empty();
//			MasterDetailFrame.this.masterDataSet.reset();
//			Iterator localIterator = MasterDetailFrame.this.detailDataSets.iterator();
//			while (localIterator.hasNext())
//			{
//				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
//				localStorageDataSet.empty();
//				localStorageDataSet.reset();
//			}
//			MasterDetailFrame.this.clearInlet();
//			MasterDetailFrame.A(MasterDetailFrame.this, -1L);
//			final Object obj=localObject;
//			MasterDetailFrame.this.worker.setWorker(new WireWorker.Worker()
//			{
//				private Object A;
//
//				public Object work()
//				throws Throwable
//				{
//					Object localObject = MasterDetailFrame.this.fetchEntity(obj);
//					MasterDetailFrame.this.validateSearch(localObject);
//					return localObject;
//				}
//			});
//			MasterDetailFrame.this.worker.setHook(new WireWorker.Hook()
//			{
//				public void hook(Object paramObject)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					MasterDetailFrame.this.loadEntity(paramObject);
//					MasterDetailFrame.this.showStatus();
//					int i = 0;
//					if (MasterDetailFrame.this.inletEnabled)
//						try
//					{
//							MasterDetailFrame.this.getClass().getDeclaredMethod("listInlet", new Class[0]);
//							i = 1;
//					}
//					catch (Throwable localThrowable)
//					{
//					}
//					if (i != 0)
//						SwingUtilities.invokeLater(new Runnable()
//						{
//							public void run()
//							{
//								MasterDetailFrame.this.listInletAction.actionPerformed(new ActionEvent(MasterDetailFrame.this.listInletAction, 0, (String)MasterDetailFrame.this.listInletAction.getValue("ActionCommandKey")));
//							}
//						});
//					MasterDetailFrame.this.afterSearch();
//				}
//			});
//			MasterDetailFrame.this.worker.setCorrector(new WireWorker.Corrector()
//			{
//				public void correct(Throwable paramThrowable)
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					MasterDetailFrame.this.showStatus();
//				}
//			});
//			MasterDetailFrame.this.worker.setResumer(new WireWorker.Resumer()
//			{
//				public void resume()
//				{
//					MasterDetailFrame.this.worker.setHook(null);
//					MasterDetailFrame.this.worker.setCorrector(null);
//					MasterDetailFrame.this.worker.setResumer(null);
//					MasterDetailFrame.this.showStatus();
//				}
//			});
//			MasterDetailFrame.this.worker.start();
//		}
//	}
//
//	protected class SelectAction extends AbstractAction
//	{
//		public SelectAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/select.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("SELECT"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//			if ((MasterDetailFrame.this.listTable.getSelectedRow() < 0) || (!MasterDetailFrame.this.worker.isIdle()))
//				return;
//			RecordFormat localRecordFormat = new RecordFormat("@");
//			DataSetHelper.saveMetaToRecordFormat(MasterDetailFrame.this.masterDataSet, localRecordFormat);
//			MasterDetailFrame.this.selections = new RecordSet(localRecordFormat);
//			MasterDetailFrame.this.selections.setTrace(false);
//			DataRow localDataRow = new DataRow(MasterDetailFrame.this.masterDataSet);
//			int[] arrayOfInt1 = MasterDetailFrame.this.listTable.getSelectedRows();
//			for (int i : arrayOfInt1)
//			{
//				MasterDetailFrame.this.listTable.getDataSet().getDataRow(i, localDataRow);
//				Record localRecord = new Record(localRecordFormat);
//				DataSetHelper.saveRowToRecord(localDataRow, localRecord);
//				MasterDetailFrame.this.selections.append(localRecord);
//			}
//			MasterDetailFrame.A(MasterDetailFrame.this, 25550);
//		}
//	}
//
//	protected class SetKeyAction extends AbstractAction
//	{
//		public SetKeyAction()
//		{
//			super(null);
//			if (!Beans.isDesignTime())
//				putValue("SmallIcon", new ImageIcon(MasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/filter.png")));
//			putValue("ShortDescription", DataModel.getDefault().getCaption("SET_KEY"));
//		}
//
//		public void actionPerformed(ActionEvent paramActionEvent)
//		{
//		}
//	}
//
//	private class SetKeyButtonChangeListener
//	implements ChangeListener
//	{
//		private SetKeyButtonChangeListener()
//		{
//		}
//
//		public void stateChanged(ChangeEvent paramChangeEvent)
//		{
//			if (MasterDetailFrame.this.searchPanel != null)
//				MasterDetailFrame.this.searchPanel.setVisible(MasterDetailFrame.this.setKeyButton.isSelected());
//		}
//	}
//	static void A(MasterDetailFrame masterdetailframe, int i)
//	{
//		masterdetailframe.fireInternalFrameEvent(i);
//	}
//
//	static void A(MasterDetailFrame masterdetailframe, long l)
//	{
//		masterdetailframe.Q = l;
//	}
//
//
//	static Object H(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.I;
//	}
//
//	static long F(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.Q;
//	}
//
//	static HashMap C(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.L;
//	}
//
//	static int E(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.S;
//	}
//
//	static void B(MasterDetailFrame masterdetailframe, int i)
//	{
//		masterdetailframe.S = i;
//	}
//
//	static boolean D(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.O;
//	}
//
//	static void A(MasterDetailFrame masterdetailframe, boolean flag)
//	{
//		masterdetailframe.O = flag;
//	}
//
//	static void A(MasterDetailFrame masterdetailframe)
//	{
//		masterdetailframe.H();
//	}
//
//	static boolean I(MasterDetailFrame masterdetailframe)
//	{
//		return masterdetailframe.K;
//	}
//
//	static void A(MasterDetailFrame masterdetailframe, Object obj)
//	{
//		masterdetailframe.I = obj;
//	}
//
//	static void G(MasterDetailFrame masterdetailframe)
//	{
//		masterdetailframe.B();
//	}
//
//	static void B(MasterDetailFrame masterdetailframe)
//	{
//		masterdetailframe.G();
//	}
//}