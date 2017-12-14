package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Beans;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.borland.dbswing.JdbTable;
import com.borland.dbswing.TableScrollPane;
import com.borland.dx.dataset.DataChangeAdapter;
import com.borland.dx.dataset.DataChangeEvent;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetAware;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.DataSetView;
import com.borland.dx.dataset.EditAdapter;
import com.borland.dx.dataset.ExceptionEvent;
import com.borland.dx.dataset.NavigationEvent;
import com.borland.dx.dataset.NavigationListener;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.RowStatus;
import com.borland.dx.dataset.SortDescriptor;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.TdDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.easyui.template.intf.BaseMasterDetail;
import com.evangelsoft.easyui.type.DefaultValue;
import com.evangelsoft.easyui.type.OperateCode;
import com.evangelsoft.econnect.DataModel;
import com.evangelsoft.econnect.client.Consumer;
import com.evangelsoft.econnect.client.WireWorker;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.condutil.SortItem;
import com.evangelsoft.econnect.condutil.SortList;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.TransientRecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.rmi.RMIProxy;
import com.evangelsoft.econnect.session.RemoteException;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.econnect.util.LaunchPath;
import com.evangelsoft.workbench.clientdataset.DataSetExceptionAdapter;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientutil.ContainerHelper;
import com.evangelsoft.workbench.clientutil.SortItemsHelper;
import com.evangelsoft.workbench.clientutil.VisibleWireWorker;
import com.evangelsoft.workbench.framebase.FunctionFrame;
import com.evangelsoft.workbench.panelbase.ConditionTreePanel;
import com.evangelsoft.workbench.panelbase.SortListPanel;
import com.evangelsoft.workbench.swing.JInternalDialog;

public class UMasterDetailFrame extends FunctionFrame
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static int DEFAULT_MAX_ROWS = 1000;
	protected SelectAction selectAction = new SelectAction();
	protected CloseAction closeAction = new CloseAction();
	protected FilterAction filterAction = new FilterAction();
	protected RefreshAction refreshAction = new RefreshAction();
	protected QueryAction queryAction = new QueryAction();

	protected SetKeyAction setKeyAction = new SetKeyAction();
	protected SearchAction searchAction = new SearchAction();
	protected NewAction newAction = new NewAction();
	protected SaveAction saveAction = new SaveAction();
	protected CancelAction cancelAction = new CancelAction();
	protected DeleteAction deleteAction = new DeleteAction();
	protected FirstAction firstAction = new FirstAction();
	protected PriorAction priorAction = new PriorAction();
	protected NextAction nextAction = new NextAction();
	protected LastAction lastAction = new LastAction();
	protected DetailNewAction detailNewAction = new DetailNewAction();
	protected DetailBatchAction detailBatchAction = new DetailBatchAction();
	protected DetailDeleteAction detailDeleteAction = new DetailDeleteAction();
	protected DetailClearAction detailClearAction = new DetailClearAction();
	protected ListInletAction listInletAction = new ListInletAction();
	protected FlushInletAction flushInletAction = new FlushInletAction();
	protected StorageDataSet masterDataSet;
	protected StorageDataSet detailDataSet;
	protected JPanel headerPanel;
	protected JPanel searchPanel;
	protected JPanel titlePanel;
	protected JPanel footerExtendedPanel;
	protected JLabel listRowCountLabel;
	protected JLabel listRowCountPromptLabel;
	protected JPanel footerFixedPanel;
	protected JPanel listFooterPanel;
	protected JdbTable listTable;
	protected TableScrollPane listTablePane;
	protected JPanel formPanel;
	protected JPanel formFooterPanel;
	protected JPanel footerPanel;
	protected JPanel progressPanel;
	protected JPanel filterSimplePanel;
	protected JLabel detailRowCountLabel;
	protected JLabel detailRowCountPromptLabel;
	protected JPanel detailSummaryExtendedPanel;
	protected JPanel detailSummaryFixedPanel;
	protected JButton detailNewButton;
	protected JButton detailBatchButton;
	protected JButton detailDeleteButton;
	protected JButton detailClearButton;
	protected JPanel detailFooterPanel;
	protected JdbTable detailTable;
	protected TableScrollPane detailTablePane;
	protected JToolBar detailToolBar;
	protected JPanel detailPanel;
	protected JTabbedPane detailPane;
	protected JPanel masterPanel;
	protected JPanel listPanel;
	protected JPanel filterPanel;
	protected JPanel filterConditionsPanel;
	protected SortListPanel sortListPanel;
	protected ConditionTreePanel conditionTreePanel;
	protected JSplitPane filterAdvancedPane;
	protected JPanel filterModePanel;
	protected JSplitPane formPane;
	protected JSplitPane listPane;
	protected JPanel inletPanel;
	protected JSplitPane contentPane;
	protected JSplitPane mainPane;
	protected JToolBar mainToolBar;
	protected JButton selectButton;
	protected JButton closeButton;
	protected JButton filterButton;
	protected JButton refreshButton;
	protected JToggleButton setKeyButton;
	protected JButton searchButton;
	protected JButton newButton;
	protected JButton saveButton;
	protected JButton cancelButton;
	protected JButton deleteButton;
	protected JButton firstButton;
	protected JButton priorButton;
	protected JButton nextButton;
	protected JButton lastButton;
	protected JPanel mainToolBarControlPanel;
	protected JPanel mainToolBarControlExtPanel;
	protected JLabel maxRowsLabel;
	protected JSpinner maxRowsSpinner;
	protected JPanel mainToolBarControlPaddingPanel;
	protected ButtonGroup filterModeGroup = new ButtonGroup();
	protected JRadioButtonMenuItem filterAdvancedMenuItem;
	protected JRadioButtonMenuItem filterSimpleMenuItem;
	protected JCheckBoxMenuItem filterAutoHiddenMenuItem;
	protected JCheckBoxMenuItem filterMenuItem;
	protected JPopupMenu filterMenu;
	protected VisibleWireWorker worker = new VisibleWireWorker();
	protected Circulator circulator = new Circulator();
	protected HashMap<DataSetAware, Boolean> masterComponents = new HashMap();
	protected HashMap<JToolBar, Container> toolBars = new HashMap();
	protected Class<?> entityClass;
	protected Object foreignKey = null;
	protected String[] keyColumns;
	/**存放主键的map
	 * 杨永钦
	 * */
	LinkedHashMap  <String,Object> keyMap=new LinkedHashMap<String, Object>();


	protected String[] uniqueColumns;
	protected boolean duplicateChecking = true;
	protected HashMap<StorageDataSet, String[]> detailKeyColumns = new HashMap();
	protected HashMap<StorageDataSet, String[]> detailUniqueColumns = new HashMap();
	protected HashMap<StorageDataSet, Boolean> detailDuplicateChecking = new HashMap();
	protected boolean canView = true;
	protected boolean canInsert = true;
	protected boolean canModify = true;
	protected boolean canDelete = true;
	protected ArrayList<StorageDataSet> detailDataSets = new ArrayList();
	protected HashMap<StorageDataSet, TdDataSet> detailTdDataSets = new HashMap();
	protected HashMap<StorageDataSet, HashMap<DataSetAware, Boolean>> detailComponents = new HashMap();
	protected HashMap<StorageDataSet, AbstractAction> detailNewActions = new HashMap();
	protected HashMap<StorageDataSet, AbstractAction> detailBatchActions = new HashMap();
	protected HashMap<StorageDataSet, AbstractAction> detailDeleteActions = new HashMap();
	protected HashMap<StorageDataSet, AbstractAction> detailClearActions = new HashMap();
	protected HashMap<StorageDataSet, JLabel> detailRowCountLabels = new HashMap();
	protected boolean masterLoading = false;
	protected HashMap<StorageDataSet, Boolean> detailLoadings = new HashMap();
	protected HashMap<StorageDataSet, Boolean> detailEditables = new HashMap();
	protected boolean inletEnabled = true;
	protected ConditionTree boundTree = new ConditionTree();
	protected ConditionTree filterTree = new ConditionTree();
	protected SortList sortList = new SortList();
	protected boolean advancedFilterModeEnabled = true;
	protected boolean maxRowsControlEnabled = true;
	protected boolean inletVisibleWhenOpened = false;
	protected ArrayList<ConditionItem> conditionItems = new ArrayList();
	protected String[] omitConditionItems = null;
	protected ArrayList<SortItem> sortItems = new ArrayList();
	protected String[] omitSortItems = null;
	private boolean O = true;
	private boolean K = false;
	//查询用的key
	protected Object I = null;
	private boolean H = false;
	protected RecordSet selections = null;
	protected boolean refreshWhenOpened = false;
	private boolean modified = false;
	private boolean V = false;
	private int S = 0;
	private long Q = -1L;
	private boolean U = true;
	private Border X;
	private boolean R = true;
	private boolean M = true;
	private boolean N = true;
	private boolean J = true;
	private HashMap<String, Properties> L = new HashMap();
	private HashMap<String, AbstractAction> T = new HashMap();
	private HashMap<String, Boolean> W = new HashMap();

	private JButton queryButton;
	/**
	 *参数map，用于模板，审批流和功能插件等等
	 */
	protected HashMap<String,Object> pranMap=new HashMap<String, Object>();
	/**
	 * 数据集MAP
	 */
	// 查询模板
	protected QueryDialog query;

	HashMap<String,StorageDataSetPanel> dataSetPanelMap=new HashMap<String, StorageDataSetPanel>();
	//键值对关系，能够通过数据集找到对应的key
	HashMap<StorageDataSet, String> dataSetKeyMap=new HashMap<StorageDataSet, String>();

	//	HashMap<String, DefaultValue> initValueMap=new HashMap<String, DefaultValue>();
	HashMap<String ,HashMap<String,DefaultValue>> initValueMap=new HashMap<String, HashMap<String,DefaultValue>>();
	/**
	 * 模板信息，便于其他地方取值
	 */
	public Record tempRecord;

	public UMasterDetailFrame()
	{
		setBounds(0, 0, 600, 400);
		D();
		if (!Beans.isDesignTime())
			this.searchPanel.setVisible(false);
		if (Beans.isDesignTime())
			return;
		this.maxRowsSpinner.setValue(Integer.valueOf(DEFAULT_MAX_ROWS));
		InputMap localInputMap = getInputMap(1);
		localInputMap.put(KeyStroke.getKeyStroke(120, 0), "SELECT");
		localInputMap.put(KeyStroke.getKeyStroke(82, 2), "REFRESH");
		localInputMap.put(KeyStroke.getKeyStroke(78, 2), "NEW");
		localInputMap.put(KeyStroke.getKeyStroke('Q', 2), "QUERY");
		localInputMap.put(KeyStroke.getKeyStroke(83, 2), "SAVE");
		localInputMap.put(KeyStroke.getKeyStroke(90, 2), "CANCEL");
		localInputMap.put(KeyStroke.getKeyStroke(68, 2), "DELETE");
		localInputMap.put(KeyStroke.getKeyStroke(36, 2), "FIRST");
		localInputMap.put(KeyStroke.getKeyStroke(38, 2), "PRIOR");
		localInputMap.put(KeyStroke.getKeyStroke(40, 2), "NEXT");
		localInputMap.put(KeyStroke.getKeyStroke(35, 2), "LAST");
		ActionMap localActionMap = getActionMap();
		localActionMap.put("SELECT", this.selectAction);
		localActionMap.put("REFRESH", this.refreshAction);
		localActionMap.put("QUERY", this.queryAction);
		localActionMap.put("NEW", this.newAction);
		localActionMap.put("SAVE", this.saveAction);
		localActionMap.put("CANCEL", this.cancelAction);
		localActionMap.put("DELETE", this.deleteAction);
		localActionMap.put("FIRST", this.firstAction);
		localActionMap.put("PRIOR", this.priorAction);
		localActionMap.put("NEXT", this.nextAction);
		localActionMap.put("LAST", this.lastAction);
		this.masterDataSet.setPostUnmodifiedRow(true);
		this.masterDataSet.addEditListener(new EditAdapter()
		{
			public void modifying(DataSet paramDataSet)
			{
				if (masterLoading)
					return;
				setModified(true);
			}

			public void adding(DataSet paramDataSet, ReadWriteRow paramReadWriteRow)
			throws Exception
			{
				if (masterLoading)
					return;
				validateMaster(paramReadWriteRow, null);
			}

			public void updating(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
			throws Exception
			{
				if (masterLoading)
					return;
				validateMaster(paramReadWriteRow, paramReadRow);
			}
		});
		masterDataSet.addNavigationListener(new NavigationListener() {
			public void navigated(NavigationEvent event) {
				if (masterLoading) {
					return;
				}

				if ((masterDataSet.getStatus() & RowStatus.INSERTED) != 0) {
					// When a new row just inserted, getInternalRow returns
					// the previous row id, so it can't be adopted. This may be
					// a bug. If data set is sorted, posting may trigger
					// navigated event. Event under such situation should be
					// ignored.
					if (masterDataSet.isEditingNewRow()) {
						Q = Long.MAX_VALUE;

					} else {
						Q = masterDataSet.getInternalRow();
						return;
					}
				} else {
					// Navigated event trigger by sorting should be ignored.
					long currentRowId = masterDataSet.getInternalRow();
					if (Q == currentRowId) {
						return;
					}
					Q = currentRowId;
				}

				if (masterDataSet.isEmpty()
						|| ((masterDataSet.getStatus() & RowStatus.INSERTED) != 0)) {
					for (int i = 0; i < detailDataSets.size(); i++) {
						detailDataSets.get(i).empty();
						detailDataSets.get(i).reset();
					}
					buildDetailUniqueIndexes();

					clearInlet();
					if ((masterDataSet.getStatus() & RowStatus.INSERTED) == 0) {
						showStatus();
					}
				} else {
					boolean found = false;
					try {
						getClass().getDeclaredMethod(
								"listInlet", new Class[0]);
						found = true;
					} catch (Exception e) {
					}
					final boolean hasInlet = found;

					if (detailDataSets.size() > 0) {
						class Worker implements WireWorker.Worker {
							private Object key;

							public Worker(Object key) {
								super();
								this.key = key;
							}

							public Object work() throws Throwable {
								return fetchEntity(key);
							}
						}

						class Hook implements WireWorker.Hook {
							public void hook(Object value) {
								worker.setHook(null);
								worker.setCorrector(null);
								worker.setResumer(null);

								loadEntity(value);

								if (hasInlet) {
									listInletAction
									.actionPerformed(new ActionEvent(
											listInletAction,
											0,
											(String) listInletAction
											.getValue(Action.ACTION_COMMAND_KEY)));
								} else {
									showStatus();
								}
							}
						}

						class Corrector implements WireWorker.Corrector {
							public void correct(Throwable e) {
								worker.setHook(null);
								worker.setCorrector(null);
								worker.setResumer(null);

								showStatus();
							}
						}

						class Resumer implements WireWorker.Resumer {
							public void resume() {
								worker.setHook(null);
								worker.setCorrector(null);
								worker.setResumer(null);

								showStatus();
							}
						}

						worker.setWorker(new Worker(buildKey()));
						worker.setHook(new Hook());
						worker.setCorrector(new Corrector());
						worker.setResumer(new Resumer());
						worker.start();
					} else {
						if (hasInlet) {
							listInletAction
							.actionPerformed(new ActionEvent(
									listInletAction,
									0,
									(String) listInletAction
									.getValue(Action.ACTION_COMMAND_KEY)));
						} else {
							showStatus();
						}
					}
				}
			}
		});
		this.listTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent paramListSelectionEvent)
			{
				int[] arrayOfInt = listTable.getSelectedRows();
				int i = arrayOfInt.length;
				if ((i > 0) && (arrayOfInt[(i - 1)] >= listTable.getDataSet().rowCount()))
					return;
				if ((i > 1) || ((i == 1) && (listTable.getSelectedRow() == paramListSelectionEvent.getFirstIndex()) && (UMasterDetailFrame.E(UMasterDetailFrame.this) > 1) && (paramListSelectionEvent.getLastIndex() - paramListSelectionEvent.getFirstIndex() + 1 == UMasterDetailFrame.E(UMasterDetailFrame.this))))
					showStatus();
				UMasterDetailFrame.B(UMasterDetailFrame.this, i);
			}
		});
		addInternalFrameListener(new InternalFrameAdapter()
		{
			public void internalFrameOpened(InternalFrameEvent paramInternalFrameEvent)
			{
				if (!UMasterDetailFrame.D(UMasterDetailFrame.this))
					return;
				DataSetExceptionAdapter.getDefaultAdapter().registerDataSets(new DataSet[] { masterDataSet }, UMasterDetailFrame.this);
				worker.attachDesktop(getTitle(), 3, progressPanel, new Component[] { mainToolBar, mainPane });
				linkDetailDataSets();
				DataSetExceptionAdapter.getDefaultAdapter().registerDataSets((DataSet[])detailDataSets.toArray(new StorageDataSet[0]), UMasterDetailFrame.this);
				Iterator<StorageDataSet> localObject2 = detailDataSets.iterator();
				while (((Iterator)localObject2).hasNext())
				{
					final	StorageDataSet	localObject1 = (StorageDataSet)((Iterator)localObject2).next();
					((StorageDataSet)localObject1).addEditListener(new EditAdapter()
					{
						public void modifying(DataSet paramDataSet)
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							if (masterDataSet.isEditing())
								masterDataSet.post();
							if (!isModified())
								setModified(true);
							else
								showDetailStatus();
						}

						public void adding(DataSet paramDataSet, ReadWriteRow paramReadWriteRow)
						throws Exception
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							validateDetail(paramDataSet, paramReadWriteRow, null);
						}

						public void deleting(DataSet paramDataSet)
						throws Exception
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							if (masterDataSet.isEditing())
								masterDataSet.post();
						}

						public void inserting(DataSet paramDataSet)
						throws Exception
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							if (masterDataSet.isEditing())
								masterDataSet.post();
						}

						public void updating(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
						throws Exception
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							validateDetail(paramDataSet, paramReadWriteRow, paramReadRow);
						}
					});
					((StorageDataSet)localObject1).addDataChangeListener(new DataChangeAdapter()
					{
						public void dataChanged(DataChangeEvent paramDataChangeEvent)
						{
							if (((Boolean)detailLoadings.get(localObject1)).booleanValue())
								return;
							if (!isModified())
								setModified(true);
							else
								showDetailStatus();
						}
					});
				}
				Object localObject1 = new HashMap();
				((HashMap)localObject1).put(masterDataSet, masterComponents);
				Iterator localIterator = detailDataSets.iterator();
				while (localIterator.hasNext())
				{
					StorageDataSet localObject = (StorageDataSet)localIterator.next();
					HashMap localHashMap = (HashMap)detailComponents.get(localObject2);
					if (localHashMap == null)
					{
						localHashMap = new HashMap();
						detailComponents.put(localObject, localHashMap);
					}
					((HashMap)localObject1).put(localObject2, localHashMap);
				}
				DataSetHelper.listDataAwareComponents(getContentPane(), (HashMap)localObject1);
				masterLoading = false;
				localIterator = detailDataSets.iterator();
				while (localIterator.hasNext())
				{
					StorageDataSet	localObject = (StorageDataSet)localIterator.next();
					detailLoadings.put(localObject, Boolean.valueOf(false));
					detailEditables.put(localObject, Boolean.valueOf(true));
				}
				worker.setHook(new WireWorker.Hook()
				{
					public void hook(Object paramObject)
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						UMasterDetailFrame.A(UMasterDetailFrame.this, false);
						int i = 1;
						try
						{
							prepared(paramObject);
						}
						catch (Throwable localThrowable)
						{
							i = 0;
							JOptionPane.showMessageDialog(UMasterDetailFrame.this, ExceptionFormat.format(localThrowable), getTitle(), 0);
						}
						if ((!canView) || (i == 0))
						{
							UMasterDetailFrame.A(UMasterDetailFrame.this, 25550);
							return;
						}
						UMasterDetailFrame.A(UMasterDetailFrame.this);
						buildMasterUniqueIndex();
						buildDetailUniqueIndexes();
						if (advancedFilterModeEnabled)
						{
							ConditionItemsHelper.load(conditionItems, masterDataSet, omitConditionItems);
							SortItemsHelper.load(sortItems, masterDataSet, omitSortItems);
							prepareConditions();
							conditionTreePanel.setItems(conditionItems);
							sortListPanel.setItems(sortItems);
						}
						if (!Beans.isDesignTime())
						{
							if (filterSimplePanel.getComponentCount() == 0)
								filterSimpleMenuItem.setEnabled(false);
							if (!advancedFilterModeEnabled)
							{
								filterAdvancedMenuItem.setEnabled(false);
							}
							else
							{
								if (conditionItems.size() == 0)
								{
									conditionTreePanel.setVisible(false);
									filterAdvancedPane.setDividerSize(0);
									filterAdvancedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
								}
								if (sortItems.size() == 0)
								{
									sortListPanel.setVisible(false);
									filterAdvancedPane.setDividerSize(0);
									filterAdvancedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
								}
							}
							if (!filterConditionsPanel.isVisible())
								if (filterSimpleMenuItem.isEnabled())
									filterSimpleMenuItem.setSelected(true);
								else
									filterAdvancedMenuItem.setSelected(true);
							if ((!filterModePanel.isVisible()) && (!filterSimpleMenuItem.isSelected()) && (filterMenuItem.isSelected()))
								filterMenuItem.doClick();
						}
						if (filterTree != null)
						{
							conditionTreePanel.setTree(filterTree);
							filterAdvancedPane.setDividerLocation(1.0D);
						}
						showStatus();
						if (UMasterDetailFrame.I(UMasterDetailFrame.this))
						{
							if ( I != null)
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										searchAction.actionPerformed(new ActionEvent(searchAction, 0, (String)searchAction.getValue("ActionCommandKey")));
										I=null;
									}
								});
						}
						else if (refreshWhenOpened)
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									refreshAction.actionPerformed(new ActionEvent(refreshAction, 0, (String)refreshAction.getValue("ActionCommandKey")));
								}
							});
					}
				});
				worker.setCorrector(new WireWorker.Corrector()
				{
					public void correct(Throwable paramThrowable)
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						UMasterDetailFrame.A(UMasterDetailFrame.this, 25550);
					}
				});
				worker.setResumer(new WireWorker.Resumer()
				{
					public void resume()
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						UMasterDetailFrame.A(UMasterDetailFrame.this, 25550);
					}
				});
				worker.setWorker(new WireWorker.Worker()
				{
					public Object work()
					throws Throwable
					{
						Object localObject = prepareData();
						UMasterDetailFrame.G(UMasterDetailFrame.this);
						checkPrivileges();
						UMasterDetailFrame.B(UMasterDetailFrame.this);
						return localObject;
					}
				});
				worker.start();
			}

			public void internalFrameClosing(InternalFrameEvent paramInternalFrameEvent)
			{
				if (paramInternalFrameEvent.getSource() == null)
					return;

				if ((isModified()) || (isInletModified())){
					switch (JOptionPane.showConfirmDialog(UMasterDetailFrame.this, DataModel.getDefault().getSentence("MSG_EXIT_WITHOUT_SAVING_PROMPT"), getTitle(), 1, 3))
					{
					case 0:
						worker.setCleaner(new WireWorker.Cleaner()
						{
							public void clean()
							{
								worker.setCleaner(null);
								if ((!isModified()) && (!isInletModified()))
									dispose();
							}
						});
						if (isModified())
							saveAction.actionPerformed(new ActionEvent(saveAction, 0, (String)saveAction.getValue("ActionCommandKey")));
						else
							flushInletAction.actionPerformed(new ActionEvent(flushInletAction, 0, (String)flushInletAction.getValue("ActionCommandKey")));
						break;
					case 1:
						dispose();
						break;
					case 2:
						break;
					default:
						dispose();
					break;
					}
				}else{
					dispose();
				}
			}

			public void internalFrameClosed(InternalFrameEvent paramInternalFrameEvent)
			{
				Iterator localIterator = toolBars.keySet().iterator();
				while (localIterator.hasNext())
				{
					JToolBar localJToolBar = (JToolBar)localIterator.next();
					if (localJToolBar.getParent() == toolBars.get(localJToolBar))
						continue;
					Container localContainer = localJToolBar.getTopLevelAncestor();
					if (!(localContainer instanceof Window))
						continue;
					((Window)localContainer).dispose();
				}
				DataSetExceptionAdapter.getDefaultAdapter().deregisterDataSets(new DataSet[] { masterDataSet });
				DataSetExceptionAdapter.getDefaultAdapter().deregisterDataSets((DataSet[])detailDataSets.toArray(new DataSet[0]));
			}
		});
	}

	public void pack()
	{
		if (Beans.isDesignTime())
			return;
		ArrayList localArrayList = new ArrayList();
		ContainerHelper.listComponents(getContentPane(), localArrayList, JToolBar.class);
		Iterator localIterator = localArrayList.iterator();
		while (localIterator.hasNext())
		{
			Component localComponent = (Component)localIterator.next();
			this.toolBars.put((JToolBar)localComponent, localComponent.getParent());
		}
		if (this.U)
		{
			this.R = this.filterButton.isVisible();
			this.M = this.refreshButton.isVisible();
			this.N = this.setKeyButton.isVisible();
			this.J = this.searchButton.isVisible();
			if (this.mainToolBar.getComponentIndex(this.mainToolBarControlPanel) != this.mainToolBar.getComponentCount() - 1)
			{
				this.mainToolBar.remove(this.mainToolBarControlPanel);
				this.mainToolBar.add(this.mainToolBarControlPanel);
			}
			this.U = false;
		}
		if (this.O)
		{
			this.selectButton.setVisible(this.H);
			this.mainToolBar.getComponent(this.mainToolBar.getComponentIndex(this.selectButton) + 1).setVisible(this.H);
			this.filterButton.setVisible((this.R) && (!this.K) && ((this.filterSimplePanel.getComponentCount() > 0) || (this.advancedFilterModeEnabled)));
			this.refreshButton.setVisible((this.M) && (!this.K));
			this.setKeyButton.setVisible((this.N) && (this.K) && (this.searchPanel.getComponentCount() > 0));
			if (this.setKeyButton.isVisible())
				this.setKeyButton.setSelected(true);
			this.searchButton.setVisible((this.J) && (this.K) && (this.searchPanel.getComponentCount() > 0));
			if ((!this.filterButton.isVisible()) && (!this.refreshButton.isVisible()) && (!this.setKeyButton.isVisible()) && (!this.searchButton.isVisible()))
				this.mainToolBar.getComponent(this.mainToolBar.getComponentIndex(this.searchButton) + 1).setVisible(false);
			if ((this.maxRowsControlEnabled) && (!this.K))
				this.mainToolBarControlPanel.setVisible(true);
			else
				this.mainToolBarControlPanel.setVisible(false);
			if (this.filterModePanel.getComponentCount() == 0)
				this.filterModePanel.setVisible(false);
			if ((this.filterSimplePanel.getComponentCount() == 0) && (!this.advancedFilterModeEnabled))
				this.filterConditionsPanel.setVisible(false);
			if ((this.filterModePanel.isVisible()) && (!this.filterConditionsPanel.isVisible()))
			{
				if (((this.filterPanel.getLayout() instanceof BorderLayout)) && (((BorderLayout)this.filterPanel.getLayout()).getConstraints(this.filterConditionsPanel).equals("Center")))
				{
					this.filterPanel.remove(this.filterModePanel);
					this.filterPanel.remove(this.filterConditionsPanel);
					this.filterPanel.add(this.filterModePanel, "Center");
					this.filterPanel.add(this.filterConditionsPanel, "West");
				}
			}
			else if ((!this.filterModePanel.isVisible()) && (!this.filterConditionsPanel.isVisible()))
			{
				this.filterPanel.setVisible(false);
				this.filterButton.setVisible(false);
				this.listPane.setDividerSize(0);
				this.listPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			}
			if (this.formFooterPanel.getComponentCount() == 0)
				this.formFooterPanel.setVisible(false);
			try
			{
				getClass().getDeclaredMethod("detailBatch", new Class[0]);
			}
			catch (Throwable localThrowable)
			{
				this.detailBatchButton.setVisible(false);
			}
			if ((this.inletPanel.getComponentCount() == 0) || (!this.inletPanel.isVisible()) || ((!this.inletVisibleWhenOpened) && (this.O)))
			{
				if (this.inletPanel.isVisible())
					this.inletPanel.setVisible(false);
				this.contentPane.setRightComponent(null);
				if (this.inletPanel.getComponentCount() == 0)
					this.contentPane.setDividerSize(0);
			}
		}
		super.pack();
		if ((getTopLevelAncestor() != null) && ((getTopLevelAncestor() instanceof JDialog)))
			((JDialog)getTopLevelAncestor()).pack();
	}

	private void B()
	{
		String str1 = getClass().getPackage().getName() + "." + (getClass().getSimpleName().endsWith("Frame") ? getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 5) : getClass().getSimpleName()) + ".PlugIn.";
		String str2 = str1.replace('.', System.getProperty("file.separator").charAt(0));
		String[] arrayOfString1 = LaunchPath.listFileNames(str2, ".properties");
		if (arrayOfString1 != null)
			for (String str3 : arrayOfString1)
			{
				String str4 = str3.substring(0, str3.length() - ".properties".length());
				Properties localProperties = new Properties();
				try
				{
					localProperties.load(getClass().getClassLoader().getResourceAsStream(str3));
					this.L.put(str4, localProperties);
				}
				catch (Exception localException)
				{
					localException.printStackTrace();
				}
			}
		Iterator localIterator = this.L.keySet().iterator();
		while (localIterator.hasNext())
		{
			String str3 = (String)localIterator.next();
			this.W.put(str3, Boolean.valueOf(true));
		}
	}

	private void H()
	{
		if (this.L.size() == 0)
			return;
		int i = this.mainToolBar.getComponentIndex(this.mainToolBarControlPanel);
		JToolBar.Separator localSeparator = new JToolBar.Separator();
		this.mainToolBar.add(localSeparator, i);
		i++;
		Iterator localIterator = this.L.keySet().iterator();
		while (localIterator.hasNext())
		{
			String str1 = (String)localIterator.next();
			Properties localProperties = (Properties)this.L.get(str1);
			JButton localJButton = new JButton();
			localJButton.setName(str1);
			String str2 = localProperties.getProperty("name");
			String str3 = localProperties.getProperty("icon");
			String str4 = localProperties.getProperty("shortDescription");
			String str5 = localProperties.getProperty("longDescription");
			PlugInAction localPlugInAction = new PlugInAction(str2);
			if ((str3 != null) && (str3.length() > 0))
				try
			{
					localPlugInAction.putValue("SmallIcon", new ImageIcon(getClass().getResource(str3)));
			}
			catch (Exception localException)
			{
				localException.printStackTrace();
			}
			localPlugInAction.putValue("ShortDescription", str4);
			localPlugInAction.putValue("LongDescription", str5);
			this.T.put(str1, localPlugInAction);
			localJButton.setAction(localPlugInAction);
			this.mainToolBar.add(localJButton, i);
			i++;
		}
	}

	private void G()
	{
		Iterator localIterator = this.L.keySet().iterator();
		while (localIterator.hasNext())
		{
			String str1 = (String)localIterator.next();
			String str2 = ((Properties)this.L.get(str1)).getProperty("privilegeId");
			if ((str2 == null) || (str2.length() <= 0))
				continue;
			this.W.put(str1, Boolean.valueOf(privilegeChecker.validate(str2)));
		}
	}

	private void D()
	{
		this.masterDataSet = new StorageDataSet();
		this.detailDataSet = new StorageDataSet();
		setTitle(UMasterDetailFrame.class.getSimpleName());
		setDefaultCloseOperation(0);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		this.mainPane = new JSplitPane();
		this.mainPane.setOneTouchExpandable(true);
		this.mainPane.setResizeWeight(1.0D);
		getContentPane().add(this.mainPane, "Center");
		this.listPane = new JSplitPane();
		this.listPane.setOrientation(0);
		this.listPane.setResizeWeight(0.0D);
		this.listPane.setOneTouchExpandable(true);
		this.mainPane.setLeftComponent(this.listPane);
		this.filterPanel = new JPanel();
		this.filterPanel.setLayout(new BorderLayout());
		this.listPane.setTopComponent(this.filterPanel);
		this.filterModePanel = new JPanel();
		this.filterModePanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("FILTER_MODE"), 0, 0, null, null));
		this.filterPanel.add(this.filterModePanel, "West");
		this.filterConditionsPanel = new JPanel();
		this.filterConditionsPanel.setLayout(new GridBagLayout());
		this.filterConditionsPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("FILTER_CONDITIONS"), 0, 0, null, null));
		this.filterPanel.add(this.filterConditionsPanel);
		this.filterSimplePanel = new JPanel();
		this.filterConditionsPanel.add(this.filterSimplePanel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
		this.filterAdvancedPane = new JSplitPane();
		this.filterAdvancedPane.setVisible(false);
		this.filterAdvancedPane.setOneTouchExpandable(true);
		this.filterAdvancedPane.setResizeWeight(0.8D);
		this.filterConditionsPanel.add(this.filterAdvancedPane, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
		this.conditionTreePanel = new ConditionTreePanel();
		this.filterAdvancedPane.setLeftComponent(this.conditionTreePanel);
		this.sortListPanel = new SortListPanel();
		this.filterAdvancedPane.setRightComponent(this.sortListPanel);
		this.listPanel = new JPanel();
		this.listPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("LIST"), 0, 0, null, null));
		this.listPanel.setLayout(new BorderLayout());
		this.listPane.setRightComponent(this.listPanel);
		this.listTablePane = new TableScrollPane();
		this.listPanel.add(this.listTablePane, "Center");
		this.listTable = new JdbTable();
		this.listTable.setName("listTable");
		this.listTable.addMouseListener(new ListTableMouseListener());
		this.listTable.setEditable(false);
		this.listTable.setDataSet(this.masterDataSet);
		this.listTablePane.setViewportView(this.listTable);
		this.listFooterPanel = new JPanel();
		this.listFooterPanel.setLayout(new BorderLayout());
		this.listPanel.add(this.listFooterPanel, "South");
		this.contentPane = new JSplitPane();
		this.contentPane.setOneTouchExpandable(true);
		this.contentPane.setResizeWeight(0.0D);
		this.mainPane.setRightComponent(this.contentPane);
		this.formPanel = new JPanel();
		this.contentPane.setLeftComponent(this.formPanel);
		this.formPanel.setLayout(new BorderLayout());
		this.formPane = new JSplitPane();
		this.formPanel.add(this.formPane);
		this.formPane.setOneTouchExpandable(true);
		this.formPane.setResizeWeight(0.0D);
		this.formPane.setOrientation(0);
		this.masterPanel = new JPanel();
		this.masterPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("CONTENT"), 0, 0, null, null));
		this.formPane.setLeftComponent(this.masterPanel);
		this.detailPane = new JTabbedPane();
		this.formPane.setRightComponent(this.detailPane);
		this.detailPanel = new JPanel();
		this.detailPanel.setLayout(new BorderLayout());
		this.detailPane.addTab(DataModel.getDefault().getCaption("DETAIL_INFO"), null, this.detailPanel, null);
		this.detailToolBar = new JToolBar(MessageFormat.format(DataModel.getDefault().getCaption("TOOL_BAR_OF"), new Object[] { DataModel.getDefault().getCaption("DETAIL") }));
		this.detailPanel.add(this.detailToolBar, "North");
		this.detailNewButton = new JButton();
		this.detailNewButton.setAction(this.detailNewAction);
		this.detailNewButton.setText("");
		this.detailToolBar.add(this.detailNewButton);
		this.detailBatchButton = new JButton();
		this.detailBatchButton.setAction(this.detailBatchAction);
		this.detailBatchButton.setText("");
		this.detailToolBar.add(this.detailBatchButton);
		this.detailDeleteButton = new JButton();
		this.detailDeleteButton.setAction(this.detailDeleteAction);
		this.detailDeleteButton.setText("");
		this.detailToolBar.add(this.detailDeleteButton);
		this.detailClearButton = new JButton();
		this.detailClearButton.setAction(this.detailClearAction);
		this.detailClearButton.setText("");
		this.detailToolBar.add(this.detailClearButton);
		this.detailFooterPanel = new JPanel();
		this.detailFooterPanel.setLayout(new BorderLayout());
		this.detailPanel.add(this.detailFooterPanel, "South");
		this.detailSummaryFixedPanel = new JPanel();
		GridBagLayout localGridBagLayout1 = new GridBagLayout();
		localGridBagLayout1.rowHeights = new int[1];
		localGridBagLayout1.columnWidths = new int[2];
		this.detailSummaryFixedPanel.setLayout(localGridBagLayout1);
		this.detailFooterPanel.add(this.detailSummaryFixedPanel, "East");
		this.detailRowCountPromptLabel = new JLabel();
		this.detailRowCountPromptLabel.setText(DataModel.getDefault().getLabel("LINE_COUNT"));
		this.detailSummaryFixedPanel.add(this.detailRowCountPromptLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 0), 0, 0));
		this.detailRowCountLabel = new JLabel();
		this.detailRowCountLabel.setText("0");
		this.detailSummaryFixedPanel.add(this.detailRowCountLabel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 5, 5), 0, 0));
		this.detailSummaryExtendedPanel = new JPanel();
		this.detailFooterPanel.add(this.detailSummaryExtendedPanel, "Center");
		this.detailTablePane = new TableScrollPane();
		this.detailPanel.add(this.detailTablePane);
		this.detailTable = new JdbTable();
		this.detailTable.setName("detailTable");
		this.detailTable.setDataSet(this.detailDataSet);
		this.detailTablePane.setViewportView(this.detailTable);
		this.formFooterPanel = new JPanel();
		this.formFooterPanel.setLayout(new BorderLayout());
		this.formPanel.add(this.formFooterPanel, "South");
		this.inletPanel = new JPanel();
		this.inletPanel.setLayout(new BorderLayout());
		this.contentPane.setRightComponent(this.inletPanel);
		this.footerPanel = new JPanel();
		this.footerPanel.setLayout(new BorderLayout());
		getContentPane().add(this.footerPanel, "South");
		this.progressPanel = new JPanel();
		this.progressPanel.setLayout(new BorderLayout());
		this.footerPanel.add(this.progressPanel, "West");
		this.footerFixedPanel = new JPanel();
		GridBagLayout localGridBagLayout2 = new GridBagLayout();
		localGridBagLayout2.columnWidths = new int[2];
		localGridBagLayout2.rowHeights = new int[1];
		this.footerFixedPanel.setLayout(localGridBagLayout2);
		this.footerPanel.add(this.footerFixedPanel, "East");
		this.listRowCountPromptLabel = new JLabel();
		this.listRowCountPromptLabel.setText(DataModel.getDefault().getLabel("ROW_COUNT"));
		this.footerFixedPanel.add(this.listRowCountPromptLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 0), 0, 0));
		this.listRowCountLabel = new JLabel();
		this.listRowCountLabel.setText("0");
		this.footerFixedPanel.add(this.listRowCountLabel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 5, 5), 0, 0));
		this.footerExtendedPanel = new JPanel();
		this.footerExtendedPanel.setLayout(new BorderLayout());
		this.footerPanel.add(this.footerExtendedPanel);
		this.headerPanel = new JPanel();
		this.headerPanel.setLayout(new BorderLayout());
		getContentPane().add(this.headerPanel, "North");
		this.mainToolBar = new JToolBar(DataModel.getDefault().getCaption("TOOL_BAR"));
		this.headerPanel.add(this.mainToolBar, "North");
		this.selectButton = new JButton();
		this.selectButton.setAction(this.selectAction);
		this.selectButton.setHorizontalTextPosition(0);
		this.selectButton.setVerticalTextPosition(3);
		this.selectButton.setText("");
		this.mainToolBar.add(this.selectButton);
		this.mainToolBar.addSeparator();
		this.closeButton = new JButton();
		this.closeButton.setHorizontalTextPosition(0);
		this.closeButton.setVerticalTextPosition(3);
		this.closeButton.setAction(this.closeAction);
		this.closeButton.setText("");
		this.mainToolBar.add(this.closeButton);
		this.mainToolBar.addSeparator();
		this.filterButton = new JButton();
		this.filterButton.setSelected(true);
		this.filterButton.setHorizontalTextPosition(0);
		this.filterButton.setVerticalTextPosition(3);
		this.filterButton.setAction(this.filterAction);
		this.filterButton.setText("");
		this.mainToolBar.add(this.filterButton);
		this.filterMenu = new JPopupMenu();
		B(this.filterButton, this.filterMenu);
		this.filterMenuItem = new JCheckBoxMenuItem();
		this.filterMenuItem.addActionListener(new FilterMenuItemActionListener());
		this.filterMenuItem.setSelected(true);
		this.filterMenuItem.setText(DataModel.getDefault().getCaption("FILTER_OPTION"));
		this.filterMenu.add(this.filterMenuItem);
		this.filterAutoHiddenMenuItem = new JCheckBoxMenuItem();
		this.filterAutoHiddenMenuItem.setText(DataModel.getDefault().getCaption("AUTO_HIDDEN"));
		this.filterMenu.add(this.filterAutoHiddenMenuItem);
		this.filterMenu.addSeparator();
		this.filterSimpleMenuItem = new JRadioButtonMenuItem();
		this.filterModeGroup.add(this.filterSimpleMenuItem);
		this.filterSimpleMenuItem.addActionListener(new FilterSimpleMenuItemActionListener());
		this.filterSimpleMenuItem.setSelected(true);
		this.filterSimpleMenuItem.setText(DataModel.getDefault().getCaption("SIMPLE_MODE"));
		this.filterMenu.add(this.filterSimpleMenuItem);
		this.filterAdvancedMenuItem = new JRadioButtonMenuItem();
		this.filterModeGroup.add(this.filterAdvancedMenuItem);
		this.filterAdvancedMenuItem.addActionListener(new FilterAdvancedMenuItemActionListener());
		this.filterAdvancedMenuItem.setText(DataModel.getDefault().getCaption("ADVANCED_MODE"));
		this.filterMenu.add(this.filterAdvancedMenuItem);
		this.refreshButton = new JButton();
		this.refreshButton.setVerticalTextPosition(3);
		this.refreshButton.setHorizontalTextPosition(0);
		this.refreshButton.setAction(this.refreshAction);
		this.refreshButton.setText("");
		this.mainToolBar.add(this.refreshButton);
		mainToolBar.addSeparator();
		//查询
		queryButton=new JButton();
		queryButton.setAction( queryAction);
		queryButton.setText("");
		mainToolBar.add(queryButton);
//		mainToolBar.addSeparator();

		this.setKeyButton = new JToggleButton();
		this.setKeyButton.addChangeListener(new SetKeyButtonChangeListener());
		this.setKeyButton.setVerticalTextPosition(3);
		this.setKeyButton.setHorizontalTextPosition(0);
		this.setKeyButton.setAction(this.setKeyAction);
		this.setKeyButton.setText("");
		this.mainToolBar.add(this.setKeyButton);
		this.searchButton = new JButton();
		this.searchButton.setVerticalTextPosition(3);
		this.searchButton.setHorizontalTextPosition(0);
		this.searchButton.setAction(this.searchAction);
		this.searchButton.setText("");
		this.mainToolBar.add(this.searchButton);
		this.mainToolBar.addSeparator();
		this.newButton = new JButton();
		this.newButton.setVerticalTextPosition(3);
		this.newButton.setHorizontalTextPosition(0);
		this.newButton.setAction(this.newAction);
		this.newButton.setText("");
		this.mainToolBar.add(this.newButton);
		this.saveButton = new JButton();
		this.saveButton.setHorizontalTextPosition(0);
		this.saveButton.setVerticalTextPosition(3);
		this.saveButton.setAction(this.saveAction);
		this.saveButton.setText("");
		this.mainToolBar.add(this.saveButton);
		this.cancelButton = new JButton();
		this.cancelButton.setHorizontalTextPosition(0);
		this.cancelButton.setVerticalTextPosition(3);
		this.cancelButton.setAction(this.cancelAction);
		this.cancelButton.setText("");
		this.mainToolBar.add(this.cancelButton);
		this.mainToolBar.addSeparator();
		this.firstButton = new JButton();
		this.firstButton.setVerticalTextPosition(3);
		this.firstButton.setHorizontalTextPosition(0);
		this.firstButton.setAction(this.firstAction);
		this.firstButton.setText("");
		this.mainToolBar.add(this.firstButton);
		this.priorButton = new JButton();
		this.priorButton.setHorizontalTextPosition(0);
		this.priorButton.setVerticalTextPosition(3);
		this.priorButton.setAction(this.priorAction);
		this.priorButton.setText("");
		this.mainToolBar.add(this.priorButton);
		this.nextButton = new JButton();
		this.nextButton.setVerticalTextPosition(3);
		this.nextButton.setHorizontalTextPosition(0);
		this.nextButton.setAction(this.nextAction);
		this.nextButton.setText("");
		this.mainToolBar.add(this.nextButton);
		this.lastButton = new JButton();
		this.lastButton.setHorizontalTextPosition(0);
		this.lastButton.setVerticalTextPosition(3);
		this.lastButton.setAction(this.lastAction);
		this.lastButton.setText("");
		this.mainToolBar.add(this.lastButton);
		this.mainToolBar.addSeparator();
		this.deleteButton = new JButton();
		this.deleteButton.setHorizontalTextPosition(0);
		this.deleteButton.setVerticalTextPosition(3);
		this.deleteButton.setAction(this.deleteAction);
		this.deleteButton.setText("");
		this.mainToolBar.add(this.deleteButton);
		this.mainToolBarControlPanel = new JPanel();
		GridBagLayout localGridBagLayout3 = new GridBagLayout();
		localGridBagLayout3.columnWidths = new int[] { 5 };
		this.mainToolBarControlPanel.setLayout(localGridBagLayout3);
		this.mainToolBarControlPanel.setVisible(false);
		this.mainToolBar.add(this.mainToolBarControlPanel);
		this.mainToolBarControlPaddingPanel = new JPanel();
		this.mainToolBarControlPanel.add(this.mainToolBarControlPaddingPanel, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.mainToolBarControlExtPanel = new JPanel();
		this.mainToolBarControlPanel.add(this.mainToolBarControlExtPanel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.mainToolBarControlExtPanel.setLayout(new BorderLayout(0, 0));
		this.maxRowsLabel = new JLabel();
		this.maxRowsLabel.setText(DataModel.getDefault().getLabel("MAX_ROWS"));
		this.mainToolBarControlPanel.add(this.maxRowsLabel, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
		this.maxRowsSpinner = new JSpinner();
		((JSpinner.DefaultEditor)this.maxRowsSpinner.getEditor()).getTextField().setColumns(7);
		this.mainToolBarControlPanel.add(this.maxRowsSpinner, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 5), 0, 0));
		this.searchPanel = new JPanel();
		this.searchPanel.setBorder(new TitledBorder(null, DataModel.getDefault().getCaption("SEARCH_CONDITIONS"), 0, 0, null, null));
		this.searchPanel.setLayout(new BorderLayout());
		this.headerPanel.add(this.searchPanel, "South");
		this.titlePanel = new JPanel();
		this.titlePanel.setLayout(new BorderLayout());
		this.headerPanel.add(this.titlePanel);
	}

	public void show(Component paramComponent, ConditionTree paramConditionTree)
	{
		if (paramConditionTree != null)
			this.boundTree.unmarshal(paramConditionTree.marshal());
		this.refreshWhenOpened = true;
		if (paramComponent == null)
			setVisible(true);
		else
			JInternalDialog.showAsDialog(paramComponent, this);
	}

	protected void A(boolean paramBoolean)
	{
		if (this.K == paramBoolean)
			return;
		this.K = paramBoolean;
		if (Beans.isDesignTime())
			return;
		this.searchPanel.setVisible((this.searchPanel.isVisible()) && (this.searchPanel.getComponentCount() > 0) && (paramBoolean));
		if (paramBoolean)
		{
			this.mainPane.setLeftComponent(null);
			this.X = this.mainPane.getBorder();
			this.mainPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			this.mainPane.setDividerSize(0);
			if ((this.footerFixedPanel.getComponentCount() == 2) && (this.listRowCountPromptLabel.getParent() == this.footerFixedPanel) && (this.listRowCountLabel.getParent() == this.footerFixedPanel))
				this.footerFixedPanel.setVisible(false);
			this.footerExtendedPanel.setVisible(false);
		}
		else
		{
			this.mainPane.setLeftComponent(this.listPane);
			this.mainPane.setDividerSize(new JSplitPane().getDividerSize());
			if (this.X != null)
				this.mainPane.setBorder(this.X);
			this.footerExtendedPanel.setVisible(true);
			this.footerFixedPanel.setVisible(true);
		}
		pack();
	}

	public void showByKey(Component paramComponent, Object paramObject)
	{

		A(true);
		this.masterLoading=true;
		this.I = paramObject;
		if (paramComponent == null)
		{
			setVisible(true);
		}
		else if ((paramComponent instanceof JDesktopPane))
		{
			if (getParent() != null)
				getParent().remove(this);
			((JDesktopPane)paramComponent).add(this);
			setVisible(true);
		}
		else
		{
			JInternalDialog.showAsDialog(paramComponent, this);
		}
	}

	public void showByKeyString(Component paramComponent, String paramString)
	{
		showByKey(paramComponent, buildSearchKeyFromString(paramString));
	}

	public void showByKeyString(String paramString)
	{
		showByKeyString(null, paramString);
	}

	public void show(Component paramComponent, boolean paramBoolean)
	{
		A(paramBoolean);
		if (paramComponent == null)
		{
			setVisible(true);
		}
		else if ((paramComponent instanceof JDesktopPane))
		{
			if (getParent() != null)
				getParent().remove(this);
			((JDesktopPane)paramComponent).add(this);
			setVisible(true);
		}
		else
		{
			JInternalDialog.showAsDialog(paramComponent, this);
		}
	}

	public void showByFilterString(Component paramComponent, String paramString)
	{
		if ((paramString != null) && (paramString.length() > 0))
		{
			this.filterTree.unmarshal(paramString.getBytes());
			if (!this.filterAdvancedMenuItem.isSelected())
				this.filterAdvancedMenuItem.doClick();
		}
		this.refreshWhenOpened = true;
		if (paramComponent == null)
			setVisible(true);
		else
			JInternalDialog.showAsDialog(paramComponent, this);
	}

	public void showByFilterString(String paramString)
	{
		showByFilterString(null, paramString);
	}

	public RecordSet select(Component paramComponent, ConditionTree paramConditionTree, boolean paramBoolean1, boolean paramBoolean2)
	{
		if (paramConditionTree != null)
			this.boundTree.unmarshal(paramConditionTree.marshal());
		this.H = true;
		if (paramBoolean1)
			this.listTable.setSelectionMode(2);
		else
			this.listTable.setSelectionMode(0);
		boolean bool = this.refreshWhenOpened;
		this.refreshWhenOpened = paramBoolean2;
		this.selections = null;
		pack();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				mainPane.setDividerLocation(1.0D);
			}
		});
		JInternalDialog.showAsDialog(paramComponent, this);
		this.refreshWhenOpened = bool;
		return this.selections;
	}

	public RecordSet select(Component paramComponent, ConditionTree paramConditionTree, boolean paramBoolean)
	{
		return select(paramComponent, paramConditionTree, paramBoolean, true);
	}

	protected void showNavigator()
	{
		boolean bool1 = false;
		boolean bool2 = false;
		boolean bool3 = false;
		boolean bool4 = false;
		if ((this.masterDataSet.isOpen()) && (!isModified()) && (!isInletModified()))
		{
			bool1 = !this.masterDataSet.atFirst();
			bool2 = !this.masterDataSet.atFirst();
			bool3 = !this.masterDataSet.atLast();
			bool4 = !this.masterDataSet.atLast();
		}
		if (this.firstAction.isEnabled() != bool1)
		{
			this.firstAction.setEnabled(!bool1);
			this.firstAction.setEnabled(bool1);
		}
		if (this.priorAction.isEnabled() != bool2)
		{
			this.priorAction.setEnabled(!bool2);
			this.priorAction.setEnabled(bool2);
		}
		if (this.nextAction.isEnabled() != bool3)
		{
			this.nextAction.setEnabled(!bool3);
			this.nextAction.setEnabled(bool3);
		}
		if (this.lastAction.isEnabled() != bool4)
		{
			this.lastAction.setEnabled(!bool4);
			this.lastAction.setEnabled(bool4);
		}
	}

	protected boolean canModifyRow()
	{
		return true;
	}

	protected boolean canDeleteRow()
	{
		return true;
	}

	protected void showRowStatus()
	{
	}

	protected void showStatus()
	{
		if (!SwingUtilities.isEventDispatchThread())
			return;
		boolean bool1 = this.masterDataSet.isOpen();
		boolean bool2 = isModified();
		boolean bool3 = isInletModified();
		boolean bool4 = (!this.K) && (!bool2) && (!bool3);
		boolean bool5 = (!this.K) && (!bool2) && (!bool3);
		boolean bool6 = (this.K) && (!bool2) && (!bool3);
		boolean bool7 = (this.K) && (!bool2) && (!bool3);
		if (this.filterAction.isEnabled() != bool4)
		{
			this.filterAction.setEnabled(!bool4);
			this.filterAction.setEnabled(bool4);
		}
		if (this.refreshAction.isEnabled() != bool5)
		{
			this.refreshAction.setEnabled(!bool5);
			this.refreshAction.setEnabled(bool5);
		}
		if (this.setKeyAction.isEnabled() != bool6)
		{
			this.setKeyAction.setEnabled(!bool6);
			this.setKeyAction.setEnabled(bool6);
		}
		if (this.searchAction.isEnabled() != bool7)
		{
			this.searchAction.setEnabled(!bool7);
			this.searchAction.setEnabled(bool7);
		}
		boolean bool12;
		if (bool1)
		{
			this.masterDataSet.setEnableInsert((this.canInsert) && (!bool3));
			this.masterDataSet.setEnableUpdate(((this.canModify) && (canModifyRow())) || (((this.masterDataSet.getStatus() & 0x4) != 0) && (!bool3)));
			this.masterDataSet.setEnableDelete(((this.canDelete) && (canDeleteRow())) || (((this.masterDataSet.getStatus() & 0x4) != 0) && (!bool3)));
			boolean	bool8 = (this.H) && (((this.listTable.getSelectionModel().getSelectionMode() == 0) && (this.masterDataSet.inBounds())) || ((this.listTable.getSelectedRowCount() > 0) && (!bool2) && (!bool3)));
			boolean bool9 = (!bool2) && (this.canInsert) && (!bool3);
			boolean bool10 = bool2;
			boolean bool11 = bool2;
			if ((this.listTable.getSelectedRowCount() > 1) && (this.masterDataSet.isEnableDelete()))
			{
				boolean bool13 = this.masterLoading;
				this.masterLoading = true;
				boolean bool14 = this.masterDataSet.isEnableDataSetEvents();
				if (bool14)
					this.masterDataSet.enableDataSetEvents(false);
				int i = this.masterDataSet.getRow();
				try
				{
					int[] arrayOfInt1 = this.listTable.getSelectedRows();
					bool12 = true;
					for (int j : arrayOfInt1)
					{
						this.masterDataSet.goToRow(j);
						if (canDeleteRow())
							continue;
						bool12 = false;
						break;
					}
				}
				finally
				{
					this.masterDataSet.goToRow(i);
					if (bool14)
						this.masterDataSet.enableDataSetEvents(true);
					this.masterLoading = bool13;
				}
			}
			else
			{
				bool12 = (this.masterDataSet.isEnableDelete()) && (this.masterDataSet.inBounds());
			}
			if (this.selectAction.isEnabled() != bool8)
			{
				this.selectAction.setEnabled(!bool8);
				this.selectAction.setEnabled(bool8);
			}
			if (this.newAction.isEnabled() != bool9)
			{
				this.newAction.setEnabled(!bool9);
				this.newAction.setEnabled(bool9);
			}
			if (this.saveAction.isEnabled() != bool10)
			{
				this.saveAction.setEnabled(!bool10);
				this.saveAction.setEnabled(bool10);
			}
			if (this.cancelAction.isEnabled() != bool11)
			{
				this.cancelAction.setEnabled(!bool11);
				this.cancelAction.setEnabled(bool11);
			}
			if (this.deleteAction.isEnabled() != bool12)
			{
				this.deleteAction.setEnabled(!bool12);
				this.deleteAction.setEnabled(bool12);
			}
			DataSetHelper.enableDataAwareComponents(this.masterComponents, ((!this.masterDataSet.isEmpty()) && (this.masterDataSet.isEnableUpdate())) || (this.masterDataSet.isEditingNewRow()));
		}
		else
		{
			if (this.selectAction.isEnabled())
			{
				this.selectAction.setEnabled(true);
				this.selectAction.setEnabled(false);
			}
			if (this.newAction.isEnabled())
			{
				this.newAction.setEnabled(true);
				this.newAction.setEnabled(false);
			}
			if (this.saveAction.isEnabled())
			{
				this.saveAction.setEnabled(true);
				this.saveAction.setEnabled(false);
			}
			if (this.cancelAction.isEnabled())
			{
				this.cancelAction.setEnabled(true);
				this.cancelAction.setEnabled(false);
			}
			DataSetHelper.enableDataAwareComponents(this.masterComponents, false);
		}
		showNavigator();
		if ((this.masterDataSet.isEditingNewRow()) || (!this.masterDataSet.isEmpty()))
			showRowStatus();
		if (this.inletPanel.isVisible())
			showInletStatus();
		this.listPane.setEnabled((!isModified()) && (!isInletModified()));
		this.listTable.setEnabled((!isModified()) && (!isInletModified()));
		if (bool1)
			this.listRowCountLabel.setText(Integer.toString(this.masterDataSet.getRowCount()));
		else
			this.listRowCountLabel.setText("0");
		boolean bool8 = (!this.masterDataSet.isEmpty()) && (!isModified());
		Iterator localIterator = this.T.keySet().iterator();
		while (localIterator.hasNext())
		{
			String str = (String)localIterator.next();
			PlugInAction localPlugInAction = (PlugInAction)this.T.get(str);
			bool12 = ((Boolean)this.W.get(str)).booleanValue();
			localPlugInAction.setEnabled((!bool8) || (!bool12));
			localPlugInAction.setEnabled((bool8) && (bool12));
		}
		showDetailStatus();
	}

	protected void showDetailStatus()
	{
		boolean bool1 = this.masterDataSet.isOpen();
		StorageDataSet localStorageDataSet;
		Object localObject2;
		boolean bool3;
		Object localObject1;
		Iterator<StorageDataSet> localIterator;
		if (bool1)
		{
			localIterator = this.detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				localStorageDataSet = (StorageDataSet)localIterator.next();
				boolean bool2 = false;
				if (localStorageDataSet.isOpen())
				{
					bool2 = (((Boolean)this.detailEditables.get(localStorageDataSet)).booleanValue()) && (((localStorageDataSet.getStatus() & 0x6) != 0) || ((!this.masterDataSet.isEmpty()) && (this.masterDataSet.isEnableUpdate())) || (this.masterDataSet.isEditingNewRow()));
					DataSetHelper.enableDataAwareComponents((HashMap)this.detailComponents.get(localStorageDataSet), bool2);
				}
				localObject2 = (AbstractAction)this.detailNewActions.get(localStorageDataSet);
				if (localObject2 != null)
				{
					bool3 = bool2;
					if (((AbstractAction)localObject2).isEnabled() != bool3)
					{
						((AbstractAction)localObject2).setEnabled(!bool3);
						((AbstractAction)localObject2).setEnabled(bool3);
					}
				}
				localObject2 = (AbstractAction)this.detailBatchActions.get(localStorageDataSet);
				if (localObject2 != null)
				{
					bool3 = bool2;
					if (((AbstractAction)localObject2).isEnabled() != bool3)
					{
						((AbstractAction)localObject2).setEnabled(!bool3);
						((AbstractAction)localObject2).setEnabled(bool3);
					}
				}
				localObject2 = (AbstractAction)this.detailDeleteActions.get(localStorageDataSet);
				if (localObject2 != null)
				{
					bool3 = (bool2) && ((!localStorageDataSet.isEmpty()) || (localStorageDataSet.isEditingNewRow()));
					if (((AbstractAction)localObject2).isEnabled() != bool3)
					{
						((AbstractAction)localObject2).setEnabled(!bool3);
						((AbstractAction)localObject2).setEnabled(bool3);
					}
				}
				localObject2 = (AbstractAction)this.detailClearActions.get(localStorageDataSet);
				if (localObject2 == null)
					continue;
				bool3 = (bool2) && ((!localStorageDataSet.isEmpty()) || (localStorageDataSet.isEditingNewRow()));
				if (((AbstractAction)localObject2).isEnabled() == bool3)
					continue;
				((AbstractAction)localObject2).setEnabled(!bool3);
				((AbstractAction)localObject2).setEnabled(bool3);
			}
		}
		else
		{
			localIterator = this.detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				localStorageDataSet = (StorageDataSet)localIterator.next();
				if (localStorageDataSet.isOpen())
					localStorageDataSet.close();
				DataSetHelper.enableDataAwareComponents((HashMap)this.detailComponents.get(localStorageDataSet), false);
				localObject1 = (AbstractAction)this.detailNewActions.get(localStorageDataSet);
				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
				{
					((AbstractAction)localObject1).setEnabled(true);
					((AbstractAction)localObject1).setEnabled(false);
				}
				localObject1 = (AbstractAction)this.detailBatchActions.get(localStorageDataSet);
				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
				{
					((AbstractAction)localObject1).setEnabled(true);
					((AbstractAction)localObject1).setEnabled(false);
				}
				localObject1 = (AbstractAction)this.detailDeleteActions.get(localStorageDataSet);
				if ((localObject1 != null) && (((AbstractAction)localObject1).isEnabled()))
				{
					((AbstractAction)localObject1).setEnabled(true);
					((AbstractAction)localObject1).setEnabled(false);
				}
				localObject1 = (AbstractAction)this.detailClearActions.get(localStorageDataSet);
				if ((localObject1 == null) || (!((AbstractAction)localObject1).isEnabled()))
					continue;
				((AbstractAction)localObject1).setEnabled(true);
				((AbstractAction)localObject1).setEnabled(false);
			}
		}
		localIterator = this.detailDataSets.iterator();
		while (localIterator.hasNext())
		{
			localStorageDataSet = (StorageDataSet)localIterator.next();
			localObject1 = (JLabel)this.detailRowCountLabels.get(localStorageDataSet);
			if (localObject1 == null)
				continue;
			localObject2 = localStorageDataSet;
			if (this.detailTdDataSets.get(localStorageDataSet) != null)
				localObject2 = (DataSet)this.detailTdDataSets.get(localStorageDataSet);
			if (((DataSet)localObject2).isOpen())
			{
				bool3 = false;
				int i=0;
				if ((localStorageDataSet != localObject2) && ((localObject2 instanceof TdDataSet)))
					if (((TdDataSet)localObject2).isInserting())
						bool3 = true;
					else if (((TdDataSet)localObject2).isDeleting())
						i = -1;
				((JLabel)localObject1).setText(Integer.toString(((DataSet)localObject2).getRowCount() + i));
			}
			else
			{
				((JLabel)localObject1).setText("0");
			}
		}
	}

	protected void linkDetailDataSets()
	{
		this.detailDataSets.add(this.detailDataSet);
		this.detailNewActions.put(this.detailDataSet, this.detailNewAction);
		this.detailBatchActions.put(this.detailDataSet, this.detailBatchAction);
		this.detailDeleteActions.put(this.detailDataSet, this.detailDeleteAction);
		this.detailClearActions.put(this.detailDataSet, this.detailClearAction);
		this.detailRowCountLabels.put(this.detailDataSet, this.detailRowCountLabel);
	}

	protected void buildMasterUniqueIndex()
	{
		if (!this.duplicateChecking)
			return;
		boolean bool = this.masterLoading;
		this.masterLoading = true;
		try
		{
			String[] arrayOfString = (String[])null;
			if ((this.uniqueColumns != null) && (this.uniqueColumns.length > 0))
				arrayOfString = this.uniqueColumns;
			if (arrayOfString != null)
			{
				boolean[] arrayOfBoolean = new boolean[arrayOfString.length];
				for (int i = 0; i < arrayOfBoolean.length; i++)
					arrayOfBoolean[i] = false;
				SortDescriptor localSortDescriptor = new SortDescriptor("UIDX$", arrayOfString, arrayOfBoolean, Locale.getDefault().toString(), 3);
				this.masterDataSet.dropIndex(localSortDescriptor, null);
				this.masterDataSet.addIndex(localSortDescriptor, null);
			}
		}
		finally
		{
			this.masterLoading = bool;
		}
	}

	protected void buildDetailUniqueIndexes()
	{
		Iterator localIterator = this.detailDataSets.iterator();
		while (localIterator.hasNext())
		{
			StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
			boolean bool1 = false;
			Object localObject1 = this.detailLoadings.get(localStorageDataSet);
			if (localObject1 != null)
				bool1 = ((Boolean)localObject1).booleanValue();
			this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(true));
			try
			{
				boolean bool2 = true;
				if (this.detailDuplicateChecking.containsKey(localStorageDataSet))
					bool2 = ((Boolean)this.detailDuplicateChecking.get(localStorageDataSet)).booleanValue();
				String[] arrayOfString = (String[])null;
				if (this.detailUniqueColumns.containsKey(localStorageDataSet))
					arrayOfString = (String[])this.detailUniqueColumns.get(localStorageDataSet);
				if ((arrayOfString == null) || (arrayOfString.length == 0))
					arrayOfString = (String[])this.detailKeyColumns.get(localStorageDataSet);
				if ((bool2) && (arrayOfString != null) && (arrayOfString.length > 0))
				{
					boolean[] arrayOfBoolean = new boolean[arrayOfString.length];
					for (int i = 0; i < arrayOfBoolean.length; i++)
						arrayOfBoolean[i] = false;
					SortDescriptor localSortDescriptor = new SortDescriptor("UIDX$", arrayOfString, arrayOfBoolean, Locale.getDefault().toString(), 3);
					localStorageDataSet.dropIndex(localSortDescriptor, null);
					localStorageDataSet.addIndex(localSortDescriptor, null);
				}
			}
			finally
			{
				this.detailLoadings.put(localStorageDataSet, Boolean.valueOf(bool1));
			}
		}
	}

	protected Object prepareData()
	throws Exception
	{
		return null;
	}

	protected void prepared(Object paramObject)
	throws Exception
	{
	}

	protected void prepareConditions()
	{
	}

	protected boolean beforeRefresh()
	{
		return true;
	}

	protected void afterRefresh()
	{
	}

	protected void afterNavigate()
	{
	}

	protected boolean beforeInsert()
	{
		return true;
	}

	protected void afterInsert()
	{
	}

	protected boolean beforeCancel()
	{
		return true;
	}

	protected void afterCancel()
	{
	}

	protected boolean beforeSave()
	{
		return true;
	}

	protected void afterSave()
	{
	}

	protected boolean beforeDelete()
	{
		return true;
	}

	protected void afterDelete()
	{
	}

	protected void checkPrivileges()
	throws Exception
	{
	}

	protected void buildFilter()
	throws Exception
	{
	}

	protected Object buildSearchKey()
	throws Exception
	{
		return null;
	}

	protected Object buildSearchKeyFromString(String paramString)
	{
		String[] arrayOfString1 = paramString.split(";");
		String[] arrayOfString2 = this.keyColumns;
		if (arrayOfString2 == null)
		{
			arrayOfString2 = new String[arrayOfString1.length];
			for (int i = 0; i < arrayOfString1.length; i++)
			{
				String str1 = arrayOfString1[i];
				int k = str1.indexOf("=");
				if (k < 0)
					k = str1.length();
				arrayOfString2[i] = str1.substring(0, k);
			}
		}
		Object[] arrayOfObject = new Object[arrayOfString2.length];
		for (int j = 0; j < arrayOfObject.length; j++)
		{
			String str2 = arrayOfString2[j];
			String str3 = null;
			for (int m = 0; m < arrayOfString1.length; m++)
			{
				String localObject = arrayOfString1[m];
				int n = ((String)localObject).indexOf("=");
				if ((n <= 0) || (!((String)localObject).substring(0, n).equalsIgnoreCase(str2)) || (n + 1 >= ((String)localObject).length()))
					continue;
				str3 = ((String)localObject).substring(n + 1);
			}
			if ((str3 == null) || (str3.length() <= 0))
				continue;
			int m = this.masterDataSet.getColumn(str2).getDataType();
			Object localObject = new Variant();
			((Variant)localObject).setFromString(m, str3);
			arrayOfObject[j] = ((Variant)localObject).getAsObject();
		}
		return arrayOfObject.length == 1 ? arrayOfObject[0] : arrayOfObject;
	}

	protected void validateSearch(Object paramObject)
	throws Exception
	{
	}

	protected void afterSearch()
	{
	}

	private boolean E()
	{
		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
	}

	private boolean F()
	{
		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
	}

	private boolean C()
	{
		return (this.entityClass != null) && (BaseMasterDetail.class.isAssignableFrom(this.entityClass));
	}

	protected Object buildOldKey()
	{
		if (this.masterDataSet.getUpdatedRowCount() == 0)
			return buildKey();
		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
			return null;
		DataSetView localDataSetView = new DataSetView();
		this.masterDataSet.getUpdatedRows(localDataSetView);
		DataRow localDataRow = new DataRow(this.masterDataSet);
		localDataSetView.first();
		Object[] arrayOfObject1 = new Object[this.keyColumns.length];
		Variant localVariant = new Variant();
		for (int i = 0; i < this.keyColumns.length; i++)
		{
			this.masterDataSet.getOriginalRow(localDataSetView, localDataRow);
			localDataRow.getVariant(this.keyColumns[i], localVariant);
			arrayOfObject1[i] = localVariant.getAsObject();
		}
		if (this.foreignKey == null)
		{
			if (this.keyColumns.length > 1)
				return arrayOfObject1;
			return arrayOfObject1[0];
		}
		Object[] arrayOfObject2;
		if (this.foreignKey.getClass().isArray())
		{
			int j = ((Object[])this.foreignKey).length;
			arrayOfObject2 = new Object[arrayOfObject1.length + j];
			System.arraycopy(this.foreignKey, 0, arrayOfObject2, 0, j);
		}
		else
		{
			arrayOfObject2 = new Object[arrayOfObject1.length + 1];
			arrayOfObject2[0] = this.foreignKey;
		}
		System.arraycopy(arrayOfObject1, 0, arrayOfObject2, arrayOfObject2.length - arrayOfObject1.length, arrayOfObject1.length);
		return arrayOfObject2;
	}



	protected Object buildKey(long paramLong)
	{
		//得要重写得到主键的方法，现在全部通通都要改成MAP集合传递到后台了
		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
			return null;
		Object[] arrayOfObject1 = new Object[this.keyColumns.length];
		Variant localVariant = new Variant();
		for (int i = 0; i < this.keyColumns.length; i++)
		{
			if (paramLong >= 0L){
				this.masterDataSet.getVariant(this.keyColumns[i], paramLong, localVariant);
			}
			else{
				this.masterDataSet.getVariant(this.keyColumns[i], localVariant);
			}
			arrayOfObject1[i] = localVariant.getAsObject();
			keyMap.put(keyColumns[i], localVariant.getAsObject());
		}
		if (this.foreignKey == null)
		{
			//			if (this.keyColumns.length > 1){
			//				return arrayOfObject1;
			//			}
			//			return arrayOfObject1[0];
			return keyMap;
		}
		Object[] arrayOfObject2;
		if (this.foreignKey.getClass().isArray())
		{
			int j = ((Object[])this.foreignKey).length;
			arrayOfObject2 = new Object[arrayOfObject1.length + j];
			System.arraycopy(this.foreignKey, 0, arrayOfObject2, 0, j);
		}
		else
		{
			arrayOfObject2 = new Object[arrayOfObject1.length + 1];
			arrayOfObject2[0] = this.foreignKey;
		}
		System.arraycopy(arrayOfObject1, 0, arrayOfObject2, arrayOfObject2.length - arrayOfObject1.length, arrayOfObject1.length);
		return arrayOfObject2;
	}

	protected Object buildKey()
	{
		return buildKey(-1L);
	}

	protected void setKey(Object paramObject)
	{
		if ((this.keyColumns == null) || (this.keyColumns.length == 0))
			return;
		Variant localVariant = new Variant();
		HashMap<String,Object> keyMap=(HashMap<String,Object> )paramObject;

		for (int i = 0; i < this.keyColumns.length; i++)
		{
			Object localObject1;
			if (this.foreignKey == null)
			{
				if (this.keyColumns.length > 1){
					//					localObject1 = ((Object[])paramObject)[i];
					localObject1=keyMap.get(keyColumns[i]);
				}
				else{
					//					localObject1 = paramObject;
					localObject1=keyMap.get(keyColumns[0]);
				}

			}
			else
			{
				int j;
				if (this.foreignKey.getClass().isArray())
					j = ((Object[])this.foreignKey).length;
				else
					j = 1;
				localObject1 = ((Object[])paramObject)[(i + j)];
			}
			this.masterDataSet.getVariant(this.keyColumns[i], localVariant);
			Object localObject2 = localVariant.getAsObject();
			if ((localObject2 != null) && (localVariant.getAsObject().equals(localObject1)))
				continue;
			if ((this.masterDataSet.getStatus() & 0x4 & 0x2) == 0)
				this.masterDataSet.editRow();
			localVariant.setAsObject(localObject1, this.masterDataSet.getColumn(this.keyColumns[i]).getDataType());
			this.masterDataSet.loadValue(this.keyColumns[i], localVariant);
		}
		if (this.masterDataSet.isEditing())
			this.masterDataSet.post();
	}

	protected boolean isMonoMode()
	{
		return this.K;
	}

	protected boolean isSelecting()
	{
		return this.H;
	}

	protected boolean isModified()
	{
		return this.modified;
	}

	protected void setModified(boolean paramBoolean)
	{
		if (this.modified == paramBoolean)
			return;
		if (!paramBoolean)
		{
			if (this.masterDataSet.changesPending())
				this.masterDataSet.mergeChanges(true);
			Iterator localIterator = this.detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
				if (!localStorageDataSet.changesPending())
					continue;
				localStorageDataSet.mergeChanges(true);
			}
		}
		this.modified = paramBoolean;
		showStatus();
	}

	protected boolean isInletModified()
	{
		return this.V;
	}

	protected void setInletModified(boolean paramBoolean)
	{
		if (this.V == paramBoolean)
			return;
		this.V = paramBoolean;
		showStatus();
	}

	protected Object listEntity(Object paramObject)
	throws Exception
	{
		if (!E())
			throw new Exception("Method listEntity should be overridden.");
		VariantHolder localVariantHolder1 = new VariantHolder();
		localVariantHolder1.value = new TransientRecordSet();
		VariantHolder localVariantHolder2 = new VariantHolder();
		BaseMasterDetail localEntityListable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
		if (!localEntityListable.list(pranMap, paramObject, localVariantHolder1, localVariantHolder2))
			throw new RemoteException((String)localVariantHolder2.value);
		return localVariantHolder1.value;
	}

	protected Object buildDataStructure()
	{
		if (this.detailDataSets.size() == 0)
			return new TransientRecordSet();
		TransientRecordSet[] arrayOfTransientRecordSet = new TransientRecordSet[this.detailDataSets.size() + 1];
		for (int i = 0; i < this.detailDataSets.size() + 1; i++)
			arrayOfTransientRecordSet[i] = new TransientRecordSet();
		return arrayOfTransientRecordSet;
	}

	protected Object fetchEntity(Object paramObject)
	throws Exception
	{
		if (!F())
			throw new Exception("Method fetchEntity should be overridden.");
		VariantHolder localVariantHolder1 = new VariantHolder();
		localVariantHolder1.value = buildDataStructure();
		VariantHolder localVariantHolder2 = new VariantHolder();
		BaseMasterDetail localEntityMonoReadable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
		//查询字段得值，默认值改成MAP
		if (!localEntityMonoReadable.get(pranMap,paramObject, localVariantHolder1, localVariantHolder2))
			throw new RemoteException((String)localVariantHolder2.value);
		return localVariantHolder1.value;
	}


	/**
	 * 重写将值加载到界面的方法
	 * 杨永钦
	 * @param paramObject（原值是Object数据，现改成HashMap<String,Object>数组）
	 */
	protected void loadEntity(Object value) {
		LinkedHashMap<String, RecordSet> dataMap=( LinkedHashMap<String, RecordSet>)value;

		Record rc = null;
		if (detailDataSets.size() == 0) {
			if (value != null) {
				rc = ((RecordSet) value).getRecord(0);
			}
		} else {
			rc=dataMap.get(tempRecord.getField("MAIN_TABLE_ID").getString()).getRecord(0);
			//			if (((RecordSet[]) value)[0] != null) {
			//			rc = (((RecordSet[]) value)[0]).getRecord(0);
			//			}
		}
		if (rc != null) {
			DataSetHelper.loadRowFromRecord(masterDataSet, rc, true);
			// DataSetHelper.resetStatus(masterDataSet);
			masterDataSet.mergeChanges(true);

			// Trigger event to display row selected in table control.
			masterDataSet.goToRow(masterDataSet.getLongRow());
		}

		for (int i = 0; i < detailDataSets.size(); i++) {
			RecordSet localRecordSet2 =dataMap.get(dataSetKeyMap.get(detailDataSets.get(i)));
			if (localRecordSet2 == null)
				continue;
			((StorageDataSet)this.detailDataSets.get(i)).empty();
			((StorageDataSet)this.detailDataSets.get(i)).reset();
			DataSetHelper.loadFromRecordSet((StorageDataSet)this.detailDataSets.get(i), localRecordSet2);


			//			RecordSet rs = ((RecordSet[]) value)[i + 1];
			//			if (rs != null) {
			//			detailDataSets.get(i).empty();
			//			detailDataSets.get(i).reset();
			//			// There may be a bug in dbSwing. If StorageDataSet isn't
			//			// editable before loading rows, status of dbSwing controls will
			//			// be uncontrollable.
			//			// detailDataSets.elementAt(i).setEditable(true);
			//			DataSetHelper.loadFromRecordSet(detailDataSets.get(i), rs);
			//			}
		}
		buildDetailUniqueIndexes();

		modified = false;
	}


	//	protected void loadEntity(Object paramObject)
	//	{
	//	//x
	//	RecordSet localRecordSet1 = null;
	//	if (this.detailDataSets.size() == 0)
	//	localRecordSet1 = (RecordSet)paramObject;
	//	else{
	//	//			localRecordSet1=(Map<>)
	//	LinkedHashMap<String, RecordSet> dataMap=( LinkedHashMap<String, RecordSet>)paramObject;
	//	localRecordSet1=dataMap.get(tempRecord.getField("MAIN_TABLE_ID").getString());
	//	}
	//	//		localRecordSet1 = ((RecordSet[])paramObject)[0];
	//	int i = 0;
	//	if (localRecordSet1 != null)
	//	{
	//	if (this.masterDataSet.isEmpty())
	//	{
	//	DataSetHelper.loadFromRecordSet(this.masterDataSet, localRecordSet1, true, null, 1);
	//	}
	//	else
	//	{
	//	if ((this.keyColumns != null) && (this.keyColumns.length > 0))
	//	{
	//	Object localObject = buildKey();
	//	if (this.keyColumns.length == 1){
	////	i = localObject.equals(localRecordSet1.getRecord(0).getField(this.keyColumns[0]).getAsObject()) ? 0 : 1;
	//	i =		keyMap.get(keyColumns[0]).equals(localRecordSet1.getRecord(0).getField(this.keyColumns[0]).getAsObject())? 0 : 1;
	//	}
	//	else
	//	for (int k = 0; k < this.keyColumns.length; k++)
	//	{
	////	i = ((Object[])localObject)[k].equals(localRecordSet1.getRecord(0).getField(this.keyColumns[k]).getAsObject()) ? 0 : 1;
	//	i =		keyMap.get(keyColumns[k]).equals(localRecordSet1.getRecord(0).getField(this.keyColumns[k]).getAsObject())? 0 : 1;
	//	if (i != 0)
	//	break;
	//	}
	//	}
	//	if (i == 0)
	//	{
	//	DataSetHelper.loadRowFromRecord(this.masterDataSet, localRecordSet1.getRecord(0), true);
	//	this.masterDataSet.mergeChanges(true);
	//	}
	//	}
	//	this.masterDataSet.goToRow(this.masterDataSet.getLongRow());
	//	}
	//	if (i == 0)
	//	{
	//	LinkedHashMap<String, RecordSet> dataMap=( LinkedHashMap<String, RecordSet>)paramObject;

	//	for (int j = 0; j < this.detailDataSets.size(); j++)
	//	{
	//	//				RecordSet localRecordSet2 = ((RecordSet[])paramObject)[(j + 1)];
	//	//				String xxx = dataSetKeyMap.get(detailDataSets.get(i+1));
	//	RecordSet localRecordSet2 =dataMap.get(dataSetKeyMap.get(detailDataSets.get(j)));
	//	if (localRecordSet2 == null)
	//	continue;
	//	((StorageDataSet)this.detailDataSets.get(j)).empty();
	//	((StorageDataSet)this.detailDataSets.get(j)).reset();
	//	DataSetHelper.loadFromRecordSet((StorageDataSet)this.detailDataSets.get(j), localRecordSet2);
	//	}
	//	buildDetailUniqueIndexes();
	//	for (int j = 0; j < this.detailDataSets.size(); j++)
	//	((StorageDataSet)this.detailDataSets.get(j)).goToRow(((StorageDataSet)this.detailDataSets.get(j)).getLongRow());
	//	}
	//	this.modified = false;
	//	}

	protected void validateMaster(ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
	throws Exception
	{
	}

	protected void validateDetail(DataSet paramDataSet, ReadWriteRow paramReadWriteRow, ReadRow paramReadRow)
	throws Exception
	{
	}

	protected Object addEntity(Object paramObject1, Object paramObject2)
	throws Exception
	{
		if (!C())
			throw new Exception("Method addEntity should be overridden.");
		VariantHolder localVariantHolder1 = new VariantHolder();
		VariantHolder localVariantHolder2 = new VariantHolder();
		VariantHolder localVariantHolder3 = new VariantHolder();
		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
		pranMap.put(BaseMasterDetail.OPERATE_CODE, OperateCode.ADD);
		if (!localEntityMonoEditable.add(pranMap,paramObject1, paramObject2, localVariantHolder1, localVariantHolder2, localVariantHolder3))
			throw new RemoteException((String)localVariantHolder3.value);
		return new Object[] { localVariantHolder1.value, localVariantHolder2.value };
	}

	protected Object modifyEntity(Object paramObject1, Object paramObject2, Object paramObject3)
	throws Exception
	{
		if (!C())
			throw new Exception("Method modifyEntity should be overridden.");
		VariantHolder localVariantHolder1 = new VariantHolder();
		localVariantHolder1.value = paramObject3;
		VariantHolder localVariantHolder2 = new VariantHolder();
		VariantHolder localVariantHolder3 = new VariantHolder();
		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
		pranMap.put(BaseMasterDetail.OPERATE_CODE, OperateCode.MODIFY);
		if (!localEntityMonoEditable.modify(pranMap,paramObject1, paramObject2, localVariantHolder1, localVariantHolder2, localVariantHolder3))
			throw new RemoteException((String)localVariantHolder3.value);
		return new Object[] { localVariantHolder1.value, localVariantHolder2.value };
	}

	protected void removeEntity(Object paramObject)
	throws Exception
	{
		if (!C())
			throw new Exception("Method removeEntity should be overridden.");
		VariantHolder localVariantHolder1 = new VariantHolder();
		localVariantHolder1.value = new TransientRecordSet();
		VariantHolder localVariantHolder2 = new VariantHolder();
		BaseMasterDetail localEntityMonoEditable = (BaseMasterDetail)new RMIProxy(Consumer.getDefaultConsumer().getSession()).newStub(this.entityClass);
		pranMap.put(BaseMasterDetail.OPERATE_CODE, OperateCode.DELETE);
		if (!localEntityMonoEditable.remove(pranMap,paramObject, localVariantHolder2))
			throw new RemoteException((String)localVariantHolder2.value);
	}

	protected void detailBatch()
	{
	}

	protected boolean listInlet()
	{
		return true;
	}

	protected void clearInlet()
	{
	}

	protected boolean flushInlet()
	{
		return true;
	}

	protected void showInletStatus()
	{
	}

	private static void B(Component paramComponent, JPopupMenu paramJPopupMenu)
	{
	}

	protected class CancelAction extends AbstractAction
	{
		public CancelAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/cancel.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("CANCEL"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (!isModified()) || (!worker.isIdle()))
				return;
			if (!beforeCancel())
				return;
			Iterator localIterator = detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
				detailLoadings.put(localStorageDataSet, Boolean.valueOf(true));
				try
				{
					localStorageDataSet.cancel();
					localStorageDataSet.reset();
					TdDataSet localTdDataSet = (TdDataSet)detailTdDataSets.get(localStorageDataSet);
					if (localTdDataSet != null)
						localTdDataSet.build();
				}
				finally
				{
					detailLoadings.put(localStorageDataSet, Boolean.valueOf(false));
				}
				if (localStorageDataSet.getLongRow() >= localStorageDataSet.getRowCount())
					localStorageDataSet.goToRow(localStorageDataSet.getRowCount() - 1);
				else
					localStorageDataSet.goToRow(localStorageDataSet.getLongRow());
			}
			masterLoading = true;
			try
			{
				masterDataSet.cancel();
				masterDataSet.reset();
			}
			finally
			{
				masterLoading = false;
			}
			afterCancel();
			if (masterDataSet.isEmpty())
				clearInlet();
			else if (masterDataSet.getLongRow() >= masterDataSet.getRowCount())
				masterDataSet.goToRow(masterDataSet.getRowCount() - 1);
			else if (UMasterDetailFrame.F(UMasterDetailFrame.this) == 9223372036854775807L)
				masterDataSet.goToRow(masterDataSet.getLongRow() - 1L);
			else
				masterDataSet.goToRow(masterDataSet.getLongRow());
			setModified(false);
		}
	}

	protected class Circulator
	{



		private Runnable B = null;

		public Circulator()
		{
			this(null);
		}

		public Circulator(Runnable arg2)
		{
			this.B = arg2;
		}

		public void setEngine(Runnable paramRunnable)
		{
			this.B = paramRunnable;
		}

		public void start()
		{
			int[] arrayOfInt1 = listTable.getSelectedRows();
			if ((arrayOfInt1 == null) || (arrayOfInt1.length == 0))
				if (masterDataSet.getRow() >= 0)
					arrayOfInt1 = new int[] { masterDataSet.getRow() };
				else
					return;
			final int[] arrayOfInt2 = arrayOfInt1;
			int i = detailDataSets.size() > 0 ? 1 : 0;
			final	boolean bool = inletEnabled;
			final VariantHolder localVariantHolder = new VariantHolder();
			localVariantHolder.value = Integer.valueOf(0);
			if ((arrayOfInt2.length > 1) && (i != 0))
				worker.addMonitor(new WireWorker.Monitor()
				{
					public void notify(int paramInt)
					{
						int i = 0;
						Object localObject = null;
						if (paramInt == 0)
						{
							if (((Integer)this.A.value).intValue() < this.B.length)
								try
							{
									UMasterDetailFrame.Circulator.this.B.run();
							}
							catch (RuntimeException localRuntimeException)
							{
								i = 1;
								localObject = localRuntimeException;
							}
							if (i == 0)
							{
								this.A.value = Integer.valueOf(((Integer)this.A.value).intValue() + 1);
								if (((Integer)this.A.value).intValue() < this.B.length)
									masterDataSet.goToRow(this.B[((Integer)this.A.value).intValue()]);
								else if (((Integer)this.A.value).intValue() == this.B.length)
									masterDataSet.goToRow(this.B[0]);
								else
									i = 1;
							}
						}
						else
						{
							i = 1;
						}
						if (i != 0)
						{
							inletEnabled = this.C;
							worker.removeMonitor(this);
							Arrays.sort(this.B);
							for (int j : this.B)
								listTable.getSelectionModel().addSelectionInterval(j, j);
						}
						//						if (localObject != null)
						//						throw localObject;
					}

					final Circulator D;
					private final VariantHolder A;
					private final int B[];
					private final boolean C;


					{
						D = Circulator.this;
						A = localVariantHolder;
						B = arrayOfInt2;
						C = bool;
					}

				});
			this.B.run();
			if (i != 0)
			{
				localVariantHolder.value = Integer.valueOf(((Integer)localVariantHolder.value).intValue() + 1);
				if (((Integer)localVariantHolder.value).intValue() < arrayOfInt2.length)
				{
					inletEnabled = false;
					masterDataSet.goToRow(arrayOfInt2[((Integer)localVariantHolder.value).intValue()]);
				}
			}
			else
			{
				try
				{
					inletEnabled = false;
					while (((Integer)localVariantHolder.value).intValue() < arrayOfInt2.length)
					{
						localVariantHolder.value = Integer.valueOf(((Integer)localVariantHolder.value).intValue() + 1);
						masterDataSet.goToRow(arrayOfInt2[((Integer)localVariantHolder.value).intValue()]);
						this.B.run();
					}
				}
				finally
				{
					inletEnabled = bool;
					masterDataSet.goToRow(arrayOfInt2[0]);
					Arrays.sort(arrayOfInt2);
					for (int j : arrayOfInt2)
						listTable.getSelectionModel().addSelectionInterval(j, j);
				}
			}
		}
	}

	protected class CloseAction extends AbstractAction
	{
		public CloseAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/close.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("CLOSE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			UMasterDetailFrame.A(UMasterDetailFrame.this, 25550);
		}
	}

	protected class DeleteAction extends AbstractAction
	{
		public DeleteAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/delete.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("DELETE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || ((masterDataSet.isEmpty()) && (!masterDataSet.isEditingNewRow())) || (!worker.isIdle()))
				return;
			if ((masterDataSet.getStatus() & 0x4) != 0)
			{
				cancelAction.actionPerformed(new ActionEvent(cancelAction, 0, (String)cancelAction.getValue("ActionCommandKey")));
			}
			else
			{
				if (JOptionPane.showConfirmDialog(UMasterDetailFrame.this, DataModel.getDefault().getSentence("MSG_DELETE_PROMPT"), getTitle(), 0, 3) != 0)
					return;
				if (!beforeDelete())
					return;
				int[] arrayOfInt = listTable.getSelectedRows();
				final long[] arrayOfLong = arrayOfInt.length > 1 ? new long[arrayOfInt.length] : null;
				if (arrayOfLong != null)
				{
					boolean bool = masterLoading;
					masterLoading = true;
					try
					{
						for (int i = 0; i < arrayOfLong.length; i++)
						{
							masterDataSet.goToRow(arrayOfInt[i]);
							arrayOfLong[i] = masterDataSet.getInternalRow();
						}
					}
					finally
					{
						masterLoading = bool;
					}
				}
				worker.setWorker(new WireWorker.Worker()
				{
					public Object work()
					throws Throwable
					{
						if (arrayOfLong != null)
						{
							boolean bool = masterLoading;
							masterLoading = true;
							try
							{
								for (long l : arrayOfLong)
								{
									masterDataSet.goToInternalRow(l);
									removeEntity(buildKey());
								}
							}
							finally
							{
								masterLoading = bool;
							}
						}
						else
						{
							removeEntity(buildKey());
						}
						return null;
					}
				});
				worker.setHook(new WireWorker.Hook()
				{
					public void hook(Object paramObject)
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
						for (int i = 0; i < detailDataSets.size(); i++)
						{
							((StorageDataSet)detailDataSets.get(i)).empty();
							((StorageDataSet)detailDataSets.get(i)).reset();
						}
						if (arrayOfLong!= null)
						{
							boolean bool = masterLoading;
							masterLoading = true;
							try
							{
								for (long l : arrayOfLong)
								{
									masterDataSet.goToInternalRow(l >= 0L ? l : -l);
									masterDataSet.deleteRow();
								}
							}
							finally
							{
								masterLoading = bool;
							}
							if (!masterDataSet.isEmpty())
								masterDataSet.goToRow(masterDataSet.getRow());
						}
						else
						{
							masterDataSet.deleteRow();
						}
						masterDataSet.mergeChanges(false);
						if (masterDataSet.isEmpty())
							clearInlet();
						modified=false;
						showStatus();
						afterDelete();
					}
				});
				worker.setCorrector(new WireWorker.Corrector()
				{
					public void correct(Throwable paramThrowable)
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
					}
				});
				worker.setResumer(new WireWorker.Resumer()
				{
					public void resume()
					{
						worker.setHook(null);
						worker.setCorrector(null);
						worker.setResumer(null);
					}
				});
				worker.start();
			}
		}
	}

	protected class DetailBatchAction extends AbstractAction
	{
		public DetailBatchAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/batchDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW_LINES"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			Object localObject = detailTable.getDataSet();
			if (localObject == null)
				localObject = detailDataSet;
			if (!((DataSet)localObject).isOpen())
				return;
			detailDataSet.last();
			try
			{
				detailBatch();
			}
			catch (DataSetException localDataSetException)
			{
				if (DataSetException.getExceptionListeners() != null)
				{
					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(detailDataSet, detailTable, localDataSetException));
					return;
				}
				throw localDataSetException;
			}
		}
	}

	protected class DetailClearAction extends AbstractAction
	{
		public DetailClearAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/clearDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("CLEAR_LINES"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			Object localObject = detailTable.getDataSet();
			if (localObject == null)
				localObject = detailDataSet;
			if ((!((DataSet)localObject).isOpen()) || ((((DataSet)localObject).isEmpty()) && (!((DataSet)localObject).isEditingNewRow())))
				return;
			((DataSet)localObject).deleteAllRows();
		}
	}

	protected class DetailDeleteAction extends AbstractAction
	{
		public DetailDeleteAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/deleteDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("DELETE_LINE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			Object localObject = detailTable.getDataSet();
			if (localObject == null)
				localObject = detailDataSet;
			if ((!((DataSet)localObject).isOpen()) || ((((DataSet)localObject).isEmpty()) && (!((DataSet)localObject).isEditingNewRow())))
				return;
			if (detailTable.getSelectedRowCount() > 1)
			{
				int[] arrayOfInt = detailTable.getSelectedRows();
				long[] arrayOfLong1 = new long[arrayOfInt.length];
				for (int i = 0; i < arrayOfInt.length; i++)
				{
					((DataSet)localObject).goToRow(arrayOfInt[i]);
					arrayOfLong1[i] = ((DataSet)localObject).getInternalRow();
				}
				for (long l : arrayOfLong1)
				{
					((DataSet)localObject).goToInternalRow(l);
					((DataSet)localObject).deleteRow();
				}
			}
			else
			{
				((DataSet)localObject).deleteRow();
			}
		}
	}

	protected class DetailNewAction extends AbstractAction
	{
		public DetailNewAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/addDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW_LINE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!detailDataSet.isOpen()) || (!worker.isIdle()))
				return;
			try
			{
				Object localObject = detailTable.getDataSet();
				if (localObject == null)
					localObject = detailDataSet;
				((DataSet)localObject).last();
				((DataSet)localObject).insertRow(false);
			}
			catch (DataSetException localDataSetException)
			{
				if (DataSetException.getExceptionListeners() != null)
				{
					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(detailDataSet, detailTable, localDataSetException));
					return;
				}
				throw localDataSetException;
			}
		}
	}

	protected class FilterAction extends AbstractAction
	{
		public FilterAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/filter.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("FILTER"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			filterMenu.show(filterButton, 0, filterButton.getHeight());
		}
	}

	private class FilterAdvancedMenuItemActionListener
	implements ActionListener
	{
		private FilterAdvancedMenuItemActionListener()
		{
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			filterAdvancedPane.setVisible(true);
			filterSimplePanel.setVisible(false);
			listPane.setResizeWeight(0.5D);
			listPane.resetToPreferredSizes();
		}
	}

	private class FilterMenuItemActionListener
	implements ActionListener
	{
		private FilterMenuItemActionListener()
		{
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			filterPanel.setVisible(filterMenuItem.isSelected());
			listPane.resetToPreferredSizes();
		}
	}

	private class FilterSimpleMenuItemActionListener
	implements ActionListener
	{
		private FilterSimpleMenuItemActionListener()
		{
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			filterSimplePanel.setVisible(true);
			filterAdvancedPane.setVisible(false);
			listPane.setResizeWeight(0.0D);
			listPane.resetToPreferredSizes();
		}
	}

	protected class FirstAction extends AbstractAction
	{
		public FirstAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/first.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("FIRST"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (isModified()) || (masterDataSet.atFirst()) || (!worker.isIdle()))
				return;
			masterDataSet.first();
		}
	}

	protected class FlushInletAction extends AbstractAction
	{
		public FlushInletAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/saveDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("SAVE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			if (flushInlet())
				showInletStatus();
		}
	}

	protected class LastAction extends AbstractAction
	{
		public LastAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/last.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("LAST"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (isModified()) || (masterDataSet.atLast()) || (!worker.isIdle()))
				return;
			masterDataSet.last();
		}
	}

	protected class ListInletAction extends AbstractAction
	{
		public ListInletAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/refreshDetail.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("REFRESH"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			if (listInlet())
				showInletStatus();
		}
	}

	private class ListTableMouseListener extends MouseAdapter
	{
		private ListTableMouseListener()
		{
		}

		public void mouseClicked(MouseEvent paramMouseEvent)
		{
			if ((paramMouseEvent.getClickCount() >= 2) && (selectButton.isShowing()) && (selectButton.isEnabled()))
				selectButton.doClick();
		}
	}

	protected class NewAction extends AbstractAction
	{
		public NewAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/add.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("NEW"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (isModified()) || (!worker.isIdle()))
				return;
			mainToolBar.requestFocusInWindow();
			if (!beforeInsert())
				return;
			masterLoading = true;
			try
			{
				if (!masterDataSet.atLast())
					masterDataSet.last();
			}
			finally
			{
				masterLoading = false;
			}
			masterDataSet.insertRow(false);
			afterInsert();
			if (isModified())
				showStatus();
			else
				setModified(true);
		}
	}

	protected class NextAction extends AbstractAction
	{
		public NextAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/next.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("NEXT"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (isModified()) || (masterDataSet.atLast()) || (!worker.isIdle()))
				return;
			masterDataSet.next();
		}
	}

	private class PlugInAction extends AbstractAction
	{
		PlugInAction(String arg2)
		{
			super(null);
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((masterDataSet.isEmpty()) || (masterDataSet.isEditingNewRow()))
				return;
			JButton localJButton;
			if ((paramActionEvent.getSource() instanceof JButton))
				localJButton = (JButton)paramActionEvent.getSource();
			else
				return;
			String str1 = localJButton.getName();
			if ((str1 != null) && (str1.length() != 0))
			{
				Properties localProperties = (Properties)UMasterDetailFrame.C(UMasterDetailFrame.this).get(str1);
				String str2 = localProperties.getProperty("class");
				String str3 = localProperties.getProperty("method");
				String str4 = localProperties.getProperty("foreignKey");
				try
				{
					Class localClass = Class.forName(str2);
					Method localMethod = localClass.getMethod(str3, new Class[] { Component.class, Object.class });
					Object localObject = buildKey();
					if ((str4 != null) && (str4.length() > 0))
					{
						Object[] arrayOfObject;
						if (localObject.getClass().isArray())
						{
							arrayOfObject = new Object[((Object[])localObject).length + 1];
							arrayOfObject[0] = str4;
							System.arraycopy(localObject, 0, arrayOfObject, 1, arrayOfObject.length - 1);
						}
						else
						{
							arrayOfObject = new Object[] { str4, localObject };
						}
						localObject = arrayOfObject;
					}
					localMethod.invoke(null, new Object[] { UMasterDetailFrame.this, localObject });
				}
				catch (Exception localException)
				{
					localException.printStackTrace();
				}
			}
		}
	}

	protected class PriorAction extends AbstractAction
	{
		public PriorAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/previous.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("PRIOR"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (isModified()) || (masterDataSet.atFirst()) || (!worker.isIdle()))
				return;
			masterDataSet.prior();
		}
	}

	protected class RefreshAction extends AbstractAction
	{
		public RefreshAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/refresh.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("REFRESH"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			masterDataSet.empty();
			masterDataSet.reset();
			Iterator localIterator = detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
				localStorageDataSet.empty();
				localStorageDataSet.reset();
			}
			clearInlet();
			UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
			filterTree.clear();
			sortList.clear();
			try
			{
				if (filterSimpleMenuItem.isSelected())
				{
					buildFilter();
				}
				else
				{
					filterTree = conditionTreePanel.getTree();
					sortList = sortListPanel.getList();
				}
			}
			catch (Throwable localThrowable)
			{
				JOptionPane.showMessageDialog(UMasterDetailFrame.this, ExceptionFormat.format(localThrowable), getTitle(), 0);
				return;
			}
			final	HashMap localHashMap = new HashMap();
			localHashMap.put("bound", boundTree);
			localHashMap.put("filter", filterTree);
			localHashMap.put("sort", sortList);
			localHashMap.put("maxRows", (Integer)maxRowsSpinner.getValue());
			if (!beforeRefresh())
				return;
			if (!worker.isIdle())
				return;
			worker.setWorker(new WireWorker.Worker()
			{
				//				private Object A;

				public Object work()
				throws Throwable
				{
					return listEntity(localHashMap);
				}
			});
			worker.setHook(new WireWorker.Hook()
			{
				public void hook(Object paramObject)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					DataSetHelper.loadFromRecordSet(masterDataSet, (RecordSet)paramObject, true, null, ((Integer)maxRowsSpinner.getValue()).intValue());
					modified=false;
					if (masterDataSet.getRowCount() <= 1000)
						buildMasterUniqueIndex();
					if (filterAutoHiddenMenuItem.isSelected())
					{
						filterMenuItem.setSelected(false);
						filterPanel.setVisible(false);
						listPane.resetToPreferredSizes();
					}
					if (!masterDataSet.isEmpty())
					{
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
								masterDataSet.first();
								showStatus();
							}
						});
					}
					else
					{
						UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
						clearInlet();
						showStatus();
					}
					afterRefresh();
				}
			});
			worker.setCorrector(new WireWorker.Corrector()
			{
				public void correct(Throwable paramThrowable)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.setResumer(new WireWorker.Resumer()
			{
				public void resume()
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.start();
		}
	}

	protected class QueryAction extends AbstractAction
	{
		public QueryAction()
		{
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/easyui/resources/buttons/query.png")));
			putValue("ShortDescription", "查询");
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{

			HashMap map=null;
			try{
				map=query.getFilterMap();
				if(map==null){
					return ;
				}
				map.put("bound", boundTree);
				//						map.put("filter", filterTree);
				//						map.put("sort", sortList);
				map.put("maxRows", (Integer)maxRowsSpinner.getValue());
			}catch(Exception e){
				e.printStackTrace();
			}
			if (!worker.isIdle())
				return;
			masterDataSet.empty();
			masterDataSet.reset();
			final HashMap workerMap=map;
			//执行查询
			worker.setWorker(new WireWorker.Worker() {
				public Object work() throws Throwable {
					Iterator localIterator = detailDataSets.iterator();
					while (localIterator.hasNext())
					{
						StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
						localStorageDataSet.empty();
						localStorageDataSet.reset();
					}
					Object obj=	listEntity(workerMap);
					return obj;
				}
			});
			//查询之后处理
			worker.setHook(new WireWorker.Hook() {
				public void hook(Object paramObject) {
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					DataSetHelper.loadFromRecordSet(masterDataSet, (RecordSet)paramObject, true, null, ((Integer)maxRowsSpinner.getValue()).intValue());
					modified=false;
					if (masterDataSet.getRowCount() <= 1000)
						buildMasterUniqueIndex();
					if (filterAutoHiddenMenuItem.isSelected())
					{
						filterMenuItem.setSelected(false);
						filterPanel.setVisible(false);
						listPane.resetToPreferredSizes();
					}
					if (!masterDataSet.isEmpty())
					{
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
								masterDataSet.first();
								showStatus();
							}
						});
					}
					else
					{
						UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
						clearInlet();
						showStatus();
					}
					afterRefresh();
				}
			});
			worker.setCorrector(new WireWorker.Corrector()
			{
				public void correct(Throwable paramThrowable)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.setResumer(new WireWorker.Resumer()
			{
				public void resume()
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.start();
		}
	}



	HashMap<String,DeltaRecordSet> deltaMap=new HashMap<String, DeltaRecordSet>();

	protected class SaveAction extends AbstractAction
	{
		public SaveAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/save.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("SAVE"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((!masterDataSet.isOpen()) || (!isModified()) || ((masterDataSet.isEmpty()) && (!masterDataSet.isEditingNewRow())) || (!worker.isIdle()))
				return;
			try
			{
				masterDataSet.post();
			}
			catch (DataSetException localDataSetException1)
			{
				if (DataSetException.getExceptionListeners() != null)
				{
					DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(masterDataSet, listTable, localDataSetException1));
					return;
				}
				throw localDataSetException1;
			}
			Iterator localIterator = detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
				try
				{
					if (detailTdDataSets.get(localStorageDataSet) != null)
						((TdDataSet)detailTdDataSets.get(localStorageDataSet)).post();
					localStorageDataSet.post();
				}
				catch (DataSetException localDataSetException2)
				{
					if (DataSetException.getExceptionListeners() != null)
					{
						DataSetException.getExceptionListeners().dispatch(new ExceptionEvent(localStorageDataSet, listTable, localDataSetException2));
						return;
					}
					throw localDataSetException2;
				}
			}
			if (!beforeSave())
				return;
			if (masterDataSet.isEditing())
			{
				masterLoading = true;
				try
				{
					masterDataSet.post();
				}
				finally
				{
					masterLoading = false;
				}
			}
			localIterator = detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet	localObject2 = (StorageDataSet)localIterator.next();
				detailLoadings.put(localObject2, Boolean.valueOf(true));
				try
				{
					if (detailTdDataSets.get(localObject2) != null)
						((TdDataSet)detailTdDataSets.get(localObject2)).post();
					((StorageDataSet)localObject2).post();
				}
				finally
				{
					detailLoadings.put(localObject2, Boolean.valueOf(false));
				}
			}
			DeltaRecordSet[] localObject2 = new DeltaRecordSet[detailDataSets.size() + 1];
			for (int i = 0; i < localObject2.length; i++){
				localObject2[i] = new DeltaRecordSet();
			}
			DataSetHelper.saveToDeltaRecordSet(masterDataSet, localObject2[0], false, false);
			if (localObject2[0].recordCount() == 0)
			{
				localObject2[0].setTrace(false);
				Record localRecord = new Record(localObject2[0].getFormat());
				DataRow localDataRow = new DataRow(masterDataSet);
				masterDataSet.getDataRow(localDataRow);
				DataSetHelper.saveRowToRecord(localDataRow, localRecord);
				localObject2[0].append((int)masterDataSet.getInternalRow(), 2, localRecord, (Record)localRecord.clone());
				localObject2[0].setTrace(true);
			}
			//数据集清空
			deltaMap.clear();
			deltaMap.put(dataSetKeyMap.get(masterDataSet), localObject2[0]);

			for (int j = 0; j < detailDataSets.size(); j++){
				DataSetHelper.saveToDeltaRecordSet((DataSet)detailDataSets.get(j), localObject2[(j + 1)], false, false);
				deltaMap.put(dataSetKeyMap.get(detailDataSets.get(j)), localObject2[(j + 1)]);
			}
			final	Object localObject3 ;
			if (localObject2.length == 1)
				localObject3 = localObject2[0];
			else
				localObject3 = localObject2;


			if ((masterDataSet.getStatus() & 0x4) != 0)
				worker.setWorker(new WireWorker.Worker()
				{

					public Object work()
					throws Throwable
					{
						return addEntity(buildKey(),deltaMap );
					}
				});
			else
				worker.setWorker(new WireWorker.Worker()
				{
					private Object A;
					private Object B;
					private Object C;

					public Object work()
					throws Throwable
					{
						return modifyEntity(buildOldKey(), deltaMap, buildKey());
					}
				});
			worker.setHook(new WireWorker.Hook()
			{
				public void hook(Object paramObject)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					if (((Object[])paramObject)[1] != null)
					{
						DeltaRecordSet localDeltaRecordSet1;
						if (detailDataSets.size() > 0)
							localDeltaRecordSet1 = ((DeltaRecordSet[])((Object[])paramObject)[1])[0];
						else
							localDeltaRecordSet1 = (DeltaRecordSet)((Object[])paramObject)[1];
						if (localDeltaRecordSet1 != null)
							DataSetHelper.backfillFromDeltaRecordSet(masterDataSet, localDeltaRecordSet1);
						for (int j = 0; j < detailDataSets.size(); j++)
						{
							DeltaRecordSet localDeltaRecordSet2 = ((DeltaRecordSet[])((Object[])paramObject)[1])[(j + 1)];
							if (localDeltaRecordSet2 == null)
								continue;
							DataSetHelper.backfillFromDeltaRecordSet((DataSet)detailDataSets.get(j), localDeltaRecordSet2);
						}
					}
					setKey(((Object[])paramObject)[0]);
					masterDataSet.mergeChanges(false);
					UMasterDetailFrame.A(UMasterDetailFrame.this, masterDataSet.getInternalRow());
					masterDataSet.goToRow(masterDataSet.getLongRow());
					for (int i = 0; i < detailDataSets.size(); i++)
					{
						((StorageDataSet)detailDataSets.get(i)).mergeChanges(false);
						((StorageDataSet)detailDataSets.get(i)).goToRow(((StorageDataSet)detailDataSets.get(i)).getLongRow());
					}
					modified=false;
					showStatus();
					afterSave();
				}
			});
			worker.setCorrector(new WireWorker.Corrector()
			{
				public void correct(Throwable paramThrowable)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					for (int i = 0; i < detailDataSets.size(); i++);
				}
			});
			worker.setResumer(new WireWorker.Resumer()
			{
				public void resume()
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					for (int i = 0; i < detailDataSets.size(); i++);
				}
			});
			worker.start();
		}
	}

	protected class SearchAction extends AbstractAction
	{
		public SearchAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/find.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("SEARCH"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if (!worker.isIdle())
				return;
			Object localObject;
			if ( I != null)

				localObject = I;
			else
				try
			{
					localObject = buildSearchKey();
			}
			catch (Throwable localThrowable)
			{
				JOptionPane.showMessageDialog(UMasterDetailFrame.this, ExceptionFormat.format(localThrowable), getTitle(), 0);
				return;
			}
			if (localObject == null)
				return;
			masterDataSet.empty();
			masterDataSet.reset();
			Iterator localIterator = detailDataSets.iterator();
			while (localIterator.hasNext())
			{
				StorageDataSet localStorageDataSet = (StorageDataSet)localIterator.next();
				localStorageDataSet.empty();
				localStorageDataSet.reset();
			}
			clearInlet();
			UMasterDetailFrame.A(UMasterDetailFrame.this, -1L);
			final Object obj=localObject;
			worker.setWorker(new WireWorker.Worker()
			{
				private Object A;

				public Object work()
				throws Throwable
				{
					Object localObject = fetchEntity(obj);
					validateSearch(localObject);
					return localObject;
				}
			});
			worker.setHook(new WireWorker.Hook()
			{
				public void hook(Object paramObject)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					loadEntity(paramObject);
					showStatus();
					int i = 0;
					if (inletEnabled)
						try
					{
							getClass().getDeclaredMethod("listInlet", new Class[0]);
							i = 1;
					}
					catch (Throwable localThrowable)
					{
					}
					if (i != 0)
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								listInletAction.actionPerformed(new ActionEvent(listInletAction, 0, (String)listInletAction.getValue("ActionCommandKey")));
							}
						});
					afterSearch();
				}
			});
			worker.setCorrector(new WireWorker.Corrector()
			{
				public void correct(Throwable paramThrowable)
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.setResumer(new WireWorker.Resumer()
			{
				public void resume()
				{
					worker.setHook(null);
					worker.setCorrector(null);
					worker.setResumer(null);
					showStatus();
				}
			});
			worker.start();
		}
	}

	protected class SelectAction extends AbstractAction
	{
		public SelectAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/select.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("SELECT"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
			if ((listTable.getSelectedRow() < 0) || (!worker.isIdle()))
				return;
			RecordFormat localRecordFormat = new RecordFormat("@");
			DataSetHelper.saveMetaToRecordFormat(masterDataSet, localRecordFormat);
			selections = new RecordSet(localRecordFormat);
			selections.setTrace(false);
			DataRow localDataRow = new DataRow(masterDataSet);
			int[] arrayOfInt1 = listTable.getSelectedRows();
			for (int i : arrayOfInt1)
			{
				listTable.getDataSet().getDataRow(i, localDataRow);
				Record localRecord = new Record(localRecordFormat);
				DataSetHelper.saveRowToRecord(localDataRow, localRecord);
				selections.append(localRecord);
			}
			UMasterDetailFrame.A(UMasterDetailFrame.this, 25550);
		}
	}

	protected class SetKeyAction extends AbstractAction
	{
		public SetKeyAction()
		{
			super(null);
			if (!Beans.isDesignTime())
				putValue("SmallIcon", new ImageIcon(UMasterDetailFrame.class.getClassLoader().getResource("com/evangelsoft/workbench/resources/buttons/filter.png")));
			putValue("ShortDescription", DataModel.getDefault().getCaption("SET_KEY"));
		}

		public void actionPerformed(ActionEvent paramActionEvent)
		{
		}
	}

	private class SetKeyButtonChangeListener
	implements ChangeListener
	{
		private SetKeyButtonChangeListener()
		{
		}

		public void stateChanged(ChangeEvent paramChangeEvent)
		{
			if (searchPanel != null)
				searchPanel.setVisible(setKeyButton.isSelected());
		}
	}
	static void A(UMasterDetailFrame masterdetailframe, int i)
	{
		masterdetailframe.fireInternalFrameEvent(i);
	}

	static void A(UMasterDetailFrame masterdetailframe, long l)
	{
		masterdetailframe.Q = l;
	}

	static long F(UMasterDetailFrame masterdetailframe)
	{
		return masterdetailframe.Q;
	}

	static HashMap C(UMasterDetailFrame masterdetailframe)
	{
		return masterdetailframe.L;
	}

	static int E(UMasterDetailFrame masterdetailframe)
	{
		return masterdetailframe.S;
	}

	static void B(UMasterDetailFrame masterdetailframe, int i)
	{
		masterdetailframe.S = i;
	}

	static boolean D(UMasterDetailFrame masterdetailframe)
	{
		return masterdetailframe.O;
	}

	static void A(UMasterDetailFrame masterdetailframe, boolean flag)
	{
		masterdetailframe.O = flag;
	}

	static void A(UMasterDetailFrame masterdetailframe)
	{
		masterdetailframe.H();
	}

	static boolean I(UMasterDetailFrame masterdetailframe)
	{
		return masterdetailframe.K;
	}

	//	static void A(UMasterDetailFrame masterdetailframe, Object obj)
	//	{
	//		masterdetailframe.I = obj;
	//	}

	static void G(UMasterDetailFrame masterdetailframe)
	{
		masterdetailframe.B();
	}

	static void B(UMasterDetailFrame masterdetailframe)
	{
		masterdetailframe.G();
	}
}