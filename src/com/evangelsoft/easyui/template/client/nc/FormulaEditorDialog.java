package com.evangelsoft.easyui.template.client.nc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;


/**
 * 新公式编辑器对话框。支持公式和变量标签页的添加和删除。
 *
 * @author 杨永钦
 */
public class FormulaEditorDialog extends JDialog implements ActionListener,FormulaListener
{

	/**
	 *
	 */
	private static final long serialVersionUID = -4422003721896416133L;
	private static JFrame sFrame = null;
	private static JFrame getFrame(Container parent) {
		if (parent instanceof JFrame) {
			return (JFrame) parent;
		}
		JFrame f = null;
		Boolean inNavigator = (Boolean)System.getProperties().get("_ISRUNINNAVIGATOR_");
		if(inNavigator == null){
			inNavigator = Boolean.TRUE;
		}
		if (System.getProperty("java.version").startsWith("1.6") && inNavigator) {
			if (sFrame == null) {
				try {
					sFrame = new JFrame("");
					sFrame.setSize(0, 0);
					//					sFrame.setResizable(false);
					sFrame.setName("DONTCLOSE");
					sFrame.setUndecorated(true);
					sFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			f = sFrame;
		} else {
			f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, parent);
		}
		// System.out.println("dlg parent is "+f);
		return f;
	}
	public FormulaEditorDialog(Container parent) {
		//		super(parent, "公式编辑");
		super(getFrame(parent), "公式编辑");
		initialize();
	}

	FormulaEditorDialog(){
		initialize();
	}
	FormulaEditorDialog(String text){
		initialize();
		this.formulaEditor.setText(text);
	}

	String formulaDesc = null; //公式描述

	String formulaDescWithDummyMap = null; // 带有映射的公式描述

	JPanel pnlContent = null;

	// north
	JPanel editPanel = null;

	JPanel pnlNorthWest = null;

	JPanel realEditPanel = null;

	JPanel validatePanel = null;

	JButton btnValidate = null;

	JTextArea hintTextArea = null;

	JButton btnOk = null;

	JButton btnCancel = null;

	JPanel calculateToolPanel = null;

	JPanel digitPanel = null;

	JPanel operatorPanel = null;


	JButton[] digitsBts = null;

	JButton[] caculateAndLogicBts = null;

	String[] digits = { "7", "8", "9", "0", "4", "5", "6", ".", "1", "2", "3",
	"00" };

	String[] caculateAndLogics = { "+", "-", "*", "/", "->", "==", "(", ")",
			"<", ">", "<=", ">=" };

	// center
	JPanel pnlCenter = null;

	JPanel formulaAndVariablePanel = null;

	UITabbedPane formulaTabbedPanel = null;

	UITabbedPane variableTabbedPanel = null;

	HintMsgPanel pnlHintMsg = null;

	public static final int FORMULA_FUNCTION = 0;

	public static final int FORMULA_VARIABLE = 1;


	HashMap mathFuns = new HashMap();

	HashMap strFuns = new HashMap();

	HashMap dbFuns = new HashMap();

	HashMap dateFuns = new HashMap();

	HashMap customFuns = new HashMap();

	HashMap glFuns = new HashMap();

	HashMap printFuns = new HashMap();

	private Timer timer; //定时提示使用技巧
	private boolean timeron = false;

	private List useTipList = new ArrayList();

	private Map mapInputSig;

	private Map mapRevertInputSig;
	private JTextArea formulaEditor = null;

	FormulaTabDirector funDirector = new FormulaTabDirector();

	FormulaTabDirector varDirector = new FormulaTabDirector();

	FormulaWordSorter fws = null;
	//	FormulaParseFather f = null;
	private void initialize()
	{
		//添加提示条目
		useTipList.add("在左括号右边双击可自动匹配到与之对应的右括号"/*"在左括号右边双击可自动匹配到与之对应的右括号"*/);
		useTipList.add("公式里的赋值符号为->而不是=,判断是否相等用=="/*"公式里的赋值符号为->而不是=,判断是否相等用=="*/);
		useTipList.add("条件判断请用iif函数,选择相应的函数可以查看详细说明"/*"条件判断请用iif函数,选择相应的函数可以查看详细说明"*/);

		//		setResizable(false);
		setName("DiaTest");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 520));

		setContentPane(getPnlContent());
		//鼠标事件检测公式括号匹配
		getFormulaEditor().addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2 && e.getButton()==1) //left double click
				{
					selTextWithinBraces(); //选中括号内的内容
				}
			}

			private void selTextWithinBraces()
			{
				int caretpos = getFormulaEditor().getCaretPosition();
				if(caretpos<=0) return;
				String strcontent = getFormulaEditor().getText();
				if(StringUtil.isEmpty(strcontent)) return;
				int preBracePos = findPreviousLeftBrace(strcontent.substring(0,caretpos-1));
				if(preBracePos!=-1)
				{
					int rightBracePos = findRightBracePosition(strcontent.substring(caretpos-1));
					if(rightBracePos==0) return;
					getFormulaEditor().setSelectionStart(preBracePos+1);
					getFormulaEditor().setSelectionEnd(rightBracePos+caretpos-1);
				}
			}

			private int findPreviousLeftBrace(String sourcestr)
			{
				if(StringUtil.isEmpty(sourcestr)) return 0;
				for (int i = sourcestr.length()-1; i >=0; i--)
				{
					if(sourcestr.charAt(i)=='(')
						return i;
					else if(sourcestr.charAt(i)==')')
						return -1;
				}
				return -1;
			}

			private int findRightBracePosition(String sourcestr)
			{
				if(StringUtil.isEmpty(sourcestr)) return 0;
				Stack bracestack = new Stack();
				for (int i = 0; i < sourcestr.length(); i++)
				{
					char curchar = sourcestr.charAt(i);
					if(curchar=='(')
					{
						bracestack.push(new String("("));
					}
					else if(curchar == ')')
					{
						if(bracestack.size()==0)
							return i;
						else
							bracestack.pop();
					}
				}
				return sourcestr.length();
			}
		});
		initTimer();
		pack();
	}

	private JPanel getPnlContent() {
		if (pnlContent == null) {
			pnlContent = new JPanel();
			pnlContent.setLayout(new BorderLayout());
			pnlContent.add(getEditPanel(), BorderLayout.NORTH);
			pnlContent.add(getPnlCenter(), BorderLayout.CENTER);
		}
		return pnlContent;
	}

	/* 编辑面板 */
	private JPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new JPanel();
			editPanel.setPreferredSize(new Dimension(0, 200));
			editPanel.setLayout(new BorderLayout());
			getEditPanel().add(getPnlNorthWest(), BorderLayout.CENTER);
			getEditPanel().add(getCalculateToolPanel(), BorderLayout.EAST);
		}
		return editPanel;
	}

	private JPanel getPnlNorthWest() {
		if (pnlNorthWest == null) {
			pnlNorthWest = new JPanel();
			pnlNorthWest.setPreferredSize(new Dimension(0, 250));
			pnlNorthWest.setLayout(new BorderLayout());
			pnlNorthWest.add(getRealEditPanel(), BorderLayout.CENTER);
			pnlNorthWest.add(getValidatePanel(), BorderLayout.SOUTH);
		}
		return pnlNorthWest;
	}

	private JPanel getRealEditPanel() {
		if (realEditPanel == null) {
			realEditPanel = new JPanel();
			realEditPanel.setPreferredSize(new Dimension(150, 250));
			realEditPanel.setLayout(new BorderLayout());
			realEditPanel.add(createUIScrollPane(getFormulaEditor()), "Center");
		}
		return realEditPanel;
	}

	private JPanel getValidatePanel() {
		if (validatePanel == null) {
			validatePanel = new JPanel();
			validatePanel.setBorder(new EtchedBorder());
			validatePanel.setPreferredSize(new Dimension(0,40));
			validatePanel.setLayout(null);
			JLabel hintlabel = new JLabel();
			hintlabel.setOpaque(false);
			hintlabel.setBounds(5,8,20,20);
			//			hintlabel.setIcon(Style.getImage("消息.小提示"));
			hintlabel.setToolTipText("单击图标打开或者关闭提示信息"/*"单击图标打开或者关闭提示信息"*/);
			hintlabel.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if(timeron)
					{
						//						stopTimer();
					}
					else
					{
						//						startTimer();
					}
				}
			});
			validatePanel.add(hintlabel);
			validatePanel.add(getHintUITextArea());
			JPanel btnpanel = new JPanel();
			btnpanel.setBounds(360,1,200,30);
			btnpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			btnpanel.add(getBtnValidate());
			btnpanel.add(getBtnOk());
			btnpanel.add(getBtnCancel());
			validatePanel.add(btnpanel);
		}
		return validatePanel;
	}

	private JTextArea getHintUITextArea()
	{
		if(hintTextArea==null)
		{
			hintTextArea = new JTextArea();
			hintTextArea.setMargin(new java.awt.Insets(1, 1, 1, 1));
			hintTextArea.setEditable(false);
			hintTextArea.setText((String)useTipList.get(0));
			hintTextArea.setToolTipText(hintTextArea.getText());
			//			hintTextArea.setBackground(Style.clrBackground);
			hintTextArea.setBounds(25,10,320,20);
			hintTextArea.updateUI();
			hintTextArea.setBorder(null);
			hintTextArea.setForeground(Color.black);
			hintTextArea.setRows(1);
			hintTextArea.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					if(timeron)
						timer.stop();
				}

				public void mouseExited(MouseEvent e)
				{
					if(timeron)
						timer.start();
				}

			});

		}
		return hintTextArea;
	}


	private JButton getBtnValidate() {
		if (btnValidate == null) {
			btnValidate = new JButton("公式验证"/*@res "公式验证"*/);
			btnValidate.setMargin(new Insets(0, 0, 0, 0));
			btnValidate.setPreferredSize(new Dimension(60, 25));
			btnValidate.addActionListener(this);
		}
		return btnValidate;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("确定"/*@res "确定"*/);
			btnOk.getModel().setActionCommand("ACT_OK");
			btnOk.setPreferredSize(new Dimension(60, 25));
			btnOk.addActionListener(this);
		}
		return btnOk;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消"/*@res "取消"*/);
			btnCancel.getModel().setActionCommand("ACT_CANCEL");
			btnCancel.setPreferredSize(new Dimension(60, 25));
			btnCancel.addActionListener(this);
		}
		return btnCancel;
	}

	private JTextArea getFormulaEditor() {
		if (formulaEditor == null) {
			formulaEditor = new JTextArea();
			formulaEditor.setLineWrap(true);
			formulaEditor.setWrapStyleWord(true);
			//			formulaEditor.setFont(new Font(Style.getFontname(), Font.PLAIN, 14));
			// 这段代码为RichEditor添加了词法分析器
			//			formulaEditor.setWordSorter(getFormulaWordSorter());
		}
		return formulaEditor;
	}

	private JPanel getCalculateToolPanel() {
		if (calculateToolPanel == null) {
			calculateToolPanel = new JPanel();
			calculateToolPanel.setPreferredSize(new Dimension(200, 0));
			calculateToolPanel.setBorder(new EtchedBorder());
			calculateToolPanel.setLayout(new GridLayout(0, 1, 5, 5));
			getCalculateToolPanel().add(getPanelDigit());
			getCalculateToolPanel().add(getPanelOprate());
		}
		return calculateToolPanel;
	}

	private JPanel getPanelDigit() {
		if (digitPanel == null) {
			digitPanel = new JPanel();
			digitPanel.setPreferredSize(new java.awt.Dimension(10, 200));
			digitPanel.setBorder(new LineBorder(digitPanel.getBackground(), 5));
			digitPanel.setLayout(new GridLayout(3, 4));
			JButton[] bts = getDigitButtons();
			if (bts != null) {
				for (int i = 0; i < bts.length; i++) {
					getPanelDigit().add(bts[i], bts[i].getName());
				}
			}
		}
		return digitPanel;
	}

	private JButton[] getDigitButtons() {
		if (digitsBts == null)
			digitsBts = createButtons(getDigitBtnProps());
		return digitsBts;
	}

	private String[][] getDigitBtnProps() {
		String[] digits = this.digits;
		String[][] textAndCmds = new String[digits.length][3];
		for (int i = 0; i < textAndCmds.length; i++) {
			textAndCmds[i][0] = textAndCmds[i][1] = textAndCmds[i][2] = digits[i];
		}
		return textAndCmds;
	}

	private JPanel getPanelOprate() {
		if (operatorPanel == null) {
			operatorPanel = new JPanel();
			operatorPanel.setName("UIPanelOprate");
			operatorPanel.setPreferredSize(new java.awt.Dimension(10, 200));
			operatorPanel.setBorder(new LineBorder(operatorPanel
					.getBackground(), 5));
			operatorPanel.setLayout(new java.awt.GridLayout(3, 4));
			operatorPanel.setBounds(6, 110, 186, 75);
			JButton[] bts = getCalculatAndLogicButtons();
			if (bts != null) {
				for (int i = 0; i < bts.length; i++) {
					getPanelOprate().add(bts[i], bts[i].getName());
				}
			}
		}
		return operatorPanel;
	}

	private JButton[] getCalculatAndLogicButtons() {
		if (caculateAndLogicBts == null)
			caculateAndLogicBts = createButtons(getCalculatAndLogicBtnProps());
		return caculateAndLogicBts;
	}

	private String[][] getCalculatAndLogicBtnProps() {
		String[] caculateAndLogics = this.caculateAndLogics;
		String[][] textAndCmds = new String[caculateAndLogics.length][3];
		for (int i = 0; i < textAndCmds.length; i++) {
			textAndCmds[i][0] = textAndCmds[i][1] = textAndCmds[i][2] = caculateAndLogics[i];
		}
		return textAndCmds;
	}

	private JButton[] createButtons(String[][] textAndCmds) {
		if (textAndCmds == null || textAndCmds.length == 0)
			return null;
		int size = textAndCmds.length;
		JButton[] bts = new JButton[size];
		for (int i = 0; i < size; i++) {
			bts[i] = new JButton();
			bts[i].setText(textAndCmds[i][0]);
			bts[i].setName(textAndCmds[i][1]);
			bts[i].setActionCommand(textAndCmds[i][2]);
			bts[i].setMargin(new Insets(0, 0, 0, 0));

			bts[i].addActionListener(this);
		}
		return bts;
	}

	/* 函数、变量和提示面板 */
	private JPanel getPnlCenter() {
		if (pnlCenter == null) {
			pnlCenter = new JPanel();
			//			pnlCenter.setBackground(SystemColor.RED);
			//			pnlCenter.setBorder( new LineBorder(Color.RED, 5) );
			pnlCenter.setLayout(new BorderLayout());
			getPnlCenter().add(getFormulaAndVariablePanel(),
					BorderLayout.CENTER);
			getPnlCenter().add(getPnlHintMsg(), BorderLayout.SOUTH);
		}
		return pnlCenter;
	}

	/** 函数和变量面板 */
	private JPanel getFormulaAndVariablePanel() {
		if (formulaAndVariablePanel == null) {
			formulaAndVariablePanel = new JPanel();
			formulaAndVariablePanel.setLayout(new GridLayout(1, 0, 15, 15));
			getFormulaAndVariablePanel().add(getFunctionTabbedPanel());
			getFormulaAndVariablePanel().add(getVariableTabbedPanel());
		}
		return formulaAndVariablePanel;
	}

	//	/** 函数多页签子面板 */
	private UITabbedPane getFunctionTabbedPanel() {
		if (formulaTabbedPanel == null) {
			formulaTabbedPanel = new UITabbedPane();
			formulaTabbedPanel.setBackground(SystemColor.RED);
			formulaTabbedPanel.setTabLayoutPolicy(UITabbedPane.SCROLL_TAB_LAYOUT);

			// 内置函数标签面板
			initFunDirector();
			funDirector.addBuilder(0, new CommonFunTabBuilder(
					getFormulaWordSorter()));
			addAllTab(formulaTabbedPanel, funDirector);
		}
		return formulaTabbedPanel;
	}

	//	/** 变量多页签子面板 */
	private UITabbedPane getVariableTabbedPanel() {
		if (variableTabbedPanel == null) {
			variableTabbedPanel = new UITabbedPane();
			variableTabbedPanel.setTabLayoutPolicy(UITabbedPane.SCROLL_TAB_LAYOUT);
			// 内置变量标签面板
			//			varDirector.addBuilder(new TableFieldTabBuilder(
			//			getFormulaWordSorter()));
			addAllTab(variableTabbedPanel, varDirector);
		}
		return variableTabbedPanel;
	}

	private JScrollPane createUIScrollPane(Component view) {
		JScrollPane srollPane = new JScrollPane();
		srollPane.setViewportView(view);
		srollPane.setAutoscrolls(true);
		return srollPane;
	}

	public void actionPerformed(java.awt.event.ActionEvent ev) {
		//		getPnlHintMsg().setHintMessage("");
		//		Object o = ev.getSource();
		//		if (o == getBtnValidate()) {
		//		onBtnValidate();
		//		return;
		//		} else if (o == getBtnOk()) {
		//		onBtnOk();
		//		return;
		//		} else if (o == getBtnCancel()) {
		//		onBtnCancel();
		//		return;
		//		}
		//		String st = ((nc.ui.pub.beans.JButton) ev.getSource()).getText()
		//		.trim();
		//		if (isDigit(st)) {
		//		updateTextArea(st);
		//		} else {
		//		updateTextArea(st);
	}

	//	private boolean isDigit(String cmd) {
	//	for (int i = 0; i < digits.length; i++)
	//	if (digits[i].equals(cmd))
	//	return true;
	//	return false;
	//	}

	public void itemSelected(FormulaEditEvent e) {
		String inputSig = e.getInputSig();
		if (mapInputSig != null
				&& mapInputSig.get(inputSig) != null) {
			inputSig = "{" + inputSig + "}";
		}
		updateTextArea(inputSig);
	}

	public void itemFocused(FormulaEditEvent e) {
		getPnlHintMsg().setHintMessage(e.getHintMsg());
	}

	private void updateTextArea(String st) {
		String sel = getFormulaEditor().getSelectedText();
		int pos = getFormulaEditor().getCaretPosition();
		if (sel != null) {
			getFormulaEditor().replaceSelection(st);
		} else {
			if (pos >= 0) {
				getFormulaEditor().insert(st, pos);
			} else {
				getFormulaEditor().append(st);
			}
		}
		if(!StringUtil.isEmpty(st))
		{
			int caretpos = st.indexOf('(');
			if(caretpos == -1)
				getFormulaEditor().setCaretPosition(pos+st.length());
			else
				getFormulaEditor().setCaretPosition(pos+caretpos+1);
		}
		getFormulaEditor().requestFocus();
	}

	/**
	 * 向公式编辑面板中加入一个自定义的公式标签面板或者变量标签面板
	 *
	 * @param builder
	 *            自定义标签面板的创建器
	 * @param option
	 *            公式还是变量
	 */
	public void addCustomTabBuilder(TabBuilder builder, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			funDirector.addBuilder(builder);
			getFunctionTabbedPanel().removeAll();
			addAllTab(getFunctionTabbedPanel(), funDirector);
			break;
		case FORMULA_VARIABLE:
			varDirector.addBuilder(builder);
			getVariableTabbedPanel().removeAll();
			addAllTab(getVariableTabbedPanel(), varDirector);
			break;
		default:
			break;
		}
	}

	/**
	 * 同上。
	 */
	public void addCustomTabBuilder(int index, TabBuilder builder, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			funDirector.addBuilder(index, builder);
			getFunctionTabbedPanel().removeAll();
			addAllTab(getFunctionTabbedPanel(), funDirector);
			break;
		case FORMULA_VARIABLE:
			varDirector.addBuilder(index, builder);
			getVariableTabbedPanel().removeAll();
			addAllTab(getVariableTabbedPanel(), varDirector);
			break;
		default:
			break;
		}
	}

	/**
	 * 替换公式编辑面板中指定位置的一个自定义的公式标签面板或者变量标签面板.
	 */
	public void setCustomTabBuilder(int index, TabBuilder builder, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			funDirector.setBuilder(index, builder);
			getFunctionTabbedPanel().removeAll();
			addAllTab(getFunctionTabbedPanel(), funDirector);
			break;
		case FORMULA_VARIABLE:
			varDirector.setBuilder(index, builder);
			getVariableTabbedPanel().removeAll();
			addAllTab(getVariableTabbedPanel(), varDirector);
			break;
		default:
			break;
		}
	}

	/**
	 * 删除一块标签面板。
	 *
	 * @param index
	 * @param option
	 */
	public void removeTabBuilder(int index, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			funDirector.removeBuilder(index);
			getFunctionTabbedPanel().removeAll();
			addAllTab(getFunctionTabbedPanel(), funDirector);
			break;
		case FORMULA_VARIABLE:
			varDirector.removeBuilder(index);
			getVariableTabbedPanel().removeAll();
			addAllTab(getVariableTabbedPanel(), varDirector);
			break;
		default:
			break;
		}
	}

	/** 使用创建指导器 <code>director</code> 往标签面板 <code>tabbedPane</code> 添加标签 */
	private void addAllTab(UITabbedPane tabbedPane, FormulaTabDirector director) {
		director.build();
		ArrayList builders = (ArrayList) director.getBuilders();
		for (int i = 0; i < builders.size(); i++) {
			TabBuilder builder = (TabBuilder) builders.get(i);
			FormulaSpecifiedPanel pane = (FormulaSpecifiedPanel) builder
					.getPnlTab();
			pane.addFormularListener(this);
			tabbedPane.addTab(builder.getTabName(), pane);
		}
	}

	/**
	 * 求公式标签面板或者变量标签面板当前标签数。
	 *
	 * @param option
	 *            指定哪个面板
	 */
	public int getTabNumber(int option) {
		int num = 0;
		switch (option) {
		case FORMULA_FUNCTION:
			num = funDirector.getBuilders().size();
			break;
		case FORMULA_VARIABLE:
			num = varDirector.getBuilders().size();
			break;
		default:
			break;
		}
		return num;
	}

	private void onBtnValidate() {
//				try {
//				String[] formulas = textToFormulaArrays();
//				if (f.checkExpressArray(formulas) == true) {
//				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "公式验证"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000056")/*@res "公式验证成功!"*/);
//				} else {
//				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "公式验证"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "公式验证失败!"*/);
//				}
//				} catch (FormulaException e) {
//				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "公式验证"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "公式验证失败!"*/);
//				}
	}

	//	private void onBtnOk() {
	//	try {
	//	String[] formulas = textToFormulaArrays();
	//	if (f.checkExpressArray(formulas) == true) {
	//	StringBuffer formulaDescBuf = new StringBuffer();
	//	for (int i = 0; i < formulas.length; i++) {
	//	formulaDescBuf.append(formulas[i] + ";");
	//	}
	//	formulaDescWithDummyMap = formulaDescBuf.toString();
	//	formulaDesc = getFormulaEditor().getText();
	//	closeOK();
	//	} else {
	//	MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "公式验证"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "公式验证失败!"*/);
	//	}
	//	} catch (FormulaException e) {
	//	MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "公式验证"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "公式验证失败!"*/);
	//	}
	//	}

//		private void onBtnCancel() {
//		closeCancel();
//		}

//		/**
//		* 从文本中抽取公式，并且完成中文名和变量名之间的替换。
//		* @return
//		* @throws FormulaException
//		*/
//		private String[] textToFormulaArrays() throws FormulaException {
//		String text = getFormulaEditor().getText();
//		String[] strs = FormulaUtils.splitFormulaStr(text);
//		textReplace(strs);
//		return strs;
//		}

		/**
		* 文本替换，针对用户要求在公式显示时显示中文的需求。
		* @param strs
		*/
		private void textReplace(String[] strs) throws Exception {
		for (int i = 0; i < strs.length; i++) {
		strs[i] = StringUtil.removeCharFromString(strs[i], '\n');
		if (mapInputSig != null) {
		while (strs[i].indexOf("{") >= 0) {
		String dummyInputSig = substringBetween(strs[i], "{", "}");
		if (dummyInputSig == null || mapInputSig.get(dummyInputSig) == null) {
		throw new Exception("变量定义有误！");
		}
		strs[i] = replaceFromTo(strs[i], "{", "}", (String)mapInputSig.get(dummyInputSig));
		}
		}
		}
		}

	/**
	 * 替换字符串source中从strBegin到strEnd的内容，替换内容是replaced字符串。
	 * 有一个特殊的地方在于，一个待替换字符串不能紧接着前一个待替字符串。
	 *
	 * @param source
	 * @param strBegin
	 * @param strEnd
	 * @param replaced
	 * @return
	 * @throws FormulaException
	 */
	public static String replaceFromTo(String source, String strBegin,
			String strEnd, String replaced) throws Exception {
		if (null == source)
			return null;
		int index = source.indexOf(strBegin);
		int index1 = source.indexOf(strEnd);
		if (source.length() > index1 + 1) {
			if (source.charAt(index1 + 1) == '{') {
				throw new Exception("变量定义有误！");
			}
		}
		return source.substring(0, index) + replaced + source.substring(index1 + strEnd.length());
	}

	/**
	 * 取原字符串中开始标记与结束标记之间的子串（不包括开始和结束标记串）
	 * @param source
	 * @param strBegin
	 * @param strEnd
	 * @return
	 */
	public String substringBetween(String source, String strBegin,
			String strEnd) {
		if (null == source)
			return null;
		int indexBegin = source.indexOf(strBegin);
		int indexEnd = source.indexOf(strEnd);
		if (indexBegin < 0) { // 开始标记和结束标记不匹配
			return null;
		}
		if (indexEnd < 0) { // 开始标记和结束标记不匹配
			return null;
		}
		if (indexBegin >=  indexEnd) {
			return null;
		}
		return source.substring(indexBegin + 1, indexEnd);
	}

	//	/**
	//	* 根据公式类别将JEP注册的公式分类。
	//	*/
	private void initFunFormulaItems() {
		Map funmap = InnerFunction.functionMap;
		for (Iterator iter = funmap.keySet().iterator(); iter.hasNext();) {
			String fundesc = (String) iter.next();
			InnerFunction element = (InnerFunction) funmap
					.get(fundesc);
			int type = element.getType();
			switch (type) {
			case IFormulaConstant.FUN_DATE:
				addEntry(dateFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_DB:
				addEntry(dbFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_GL:
				addEntry(glFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_MATH:
				addEntry(mathFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_PRINT:
				addEntry(printFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_CUSTOM:
				addEntry(customFuns, fundesc, element);
				break;
			case IFormulaConstant.FUN_STRING:
				addEntry(strFuns, fundesc, element);
			}
		}
	}

	private void initFunDirector() {
		initFunFormulaItems();
		if (dbFuns.size() > 0) {
			addFunTabBuilder(dbFuns, "数据库"/*@res "数据库"*/);
		}
		if (strFuns.size() > 0) {
			addFunTabBuilder(strFuns, "字符串"/*@res "字符串"*/);
		}
		if (dateFuns.size() > 0) {
			addFunTabBuilder(dateFuns, "日期"/*@res "日期"*/);
		}
		if (mathFuns.size() > 0) {
			addFunTabBuilder(mathFuns, "数学"/*@res "数学"*/);
		}
		if (glFuns.size() > 0) {
			addFunTabBuilder(glFuns, "财务"/*@res "财务"*/);
		}
		if (printFuns.size() > 0) {
			addFunTabBuilder(printFuns, "打印"/*@res "打印"*/);
		}
		if (customFuns.size() > 0) {
			addFunTabBuilder(customFuns, "自定义"/*@res "自定义"*/);
		}
	}

	private void addFunTabBuilder(final Map items, final String tabname) {
		funDirector.addBuilder(new AbstractTabBuilder(getFormulaWordSorter()) {
			public HashMap getHSFormulaItems() {
				return (HashMap) items;
			}
			public String getTabName() {
				return tabname;
			}
		});
	}

	private String getInputSig(String funName, int argc) {
		StringBuffer strBuffer = new StringBuffer(funName);
		if (argc == -1) {
			if (funName.equalsIgnoreCase("getColValue")
					|| funName.equalsIgnoreCase("getColNmV")
					|| funName.equalsIgnoreCase("cvs")
					|| funName.equalsIgnoreCase("cvn")
					|| funName.equalsIgnoreCase("getLangRes")) {
				strBuffer.append("( , , , ) ");
			} else if (funName.equalsIgnoreCase("getColValue2")
					|| funName.equalsIgnoreCase("getColNmv2")) {
				strBuffer.append("( , , , , , )");
			} else {
				//				default
				strBuffer.append("()");
			}

		} else {
			String param = null;
			if (argc > 0) {
				param = " ";
			} else {
				param = "";
			}
			while (argc > 1) {
				param = param + ", ";
				argc--;
			}
			strBuffer.append("(" + param + ")");
		}
		return strBuffer.toString();
	}

	private void addEntry(HashMap h, String fundesc, InnerFunction element) {
		String displayName = fundesc.toLowerCase();
		String inputSig = getInputSig(displayName, element
				.getType());
		String hintMsg = element.getDesc();
		FormulaItem item = new FunctionFormulaItem(displayName, inputSig,
				hintMsg);
		h.put(item.getDisplayName(), item);
	}

	private HintMsgPanel getPnlHintMsg() {
		if (pnlHintMsg == null) {
			pnlHintMsg = new HintMsgPanel();
		}
		return pnlHintMsg;
	}

	public FormulaWordSorter getFormulaWordSorter() {
		if (fws == null) {
			fws = new FormulaWordSorter();
		}
		return fws;
	}

	/**
	 * 返回在公式编辑器中编辑后的公式。在没有setDummyInputSigMap时与getFormulaDescWithDummyMap()返回的结果相同。
	 */
	public String getFormulaDesc() {
		return formulaDesc;
	}

	/**
	 * 返回需要名字间映射之后的公式描述。
	 */
	public String getFormulaDescWithDummyMap() {
		return formulaDescWithDummyMap;
	}

	/**
	 * 将已有老公式带进编辑对话框。
	 */
	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
		String formulaDisplayDesc = textRevertReplace(formulaDesc);
		getFormulaEditor().setText(formulaDisplayDesc);
	}

	/**
	 * 反向替换公式文本，即将字符串形式的变量替换成中文名。
	 * @param formulaDesc
	 * @return
	 */
	private String textRevertReplace(String formulaDesc) {
		if (mapRevertInputSig != null) {
			Set keys = mapRevertInputSig.keySet();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				String key = (String)iter.next();
				formulaDesc = replaceAllString(formulaDesc, key, (String)mapRevertInputSig.get(key));
			}
		}
		return formulaDesc;
	}

	/**
	 * 替换原字符串source上指定子串。
	 * @param source
	 * @param strReplace
	 * @param strReplaced
	 * @return
	 */
	public static String replaceAllString(String source, String strReplace,
			String strReplaced) {
		if (StringUtil.isEmpty(source) || StringUtil.isEmpty(strReplace) || strReplaced == null)
			return source;
		String regex = "\\b" + strReplace + "\\b";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source); // get a matcher object
		return m.replaceAll("{" + strReplaced + "}");
	}

	/**
	 * 判断tab有没有已经存在。
	 *
	 * @param tabname
	 * @return
	 */
	public boolean isBuilderExist(String tabname, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			return funDirector.isBuilderExist(tabname);
		case FORMULA_VARIABLE:
			return varDirector.isBuilderExist(tabname);
		default:
			return false;
		}
	}

	/**
	 * 返回tab的位置,不存在,则返回-1
	 * @param tabname
	 * @param option
	 * @return
	 */
	public int getBuilderIndex(String tabname, int option) {
		switch (option) {
		case FORMULA_FUNCTION:
			return funDirector.getBuilderIndex(tabname);
		case FORMULA_VARIABLE:
			return varDirector.getBuilderIndex(tabname);
		default:
			return -1;
		}
	}

	/**
	 * 初始化定时器
	 */
	private void initTimer()
	{
		timer = new Timer(2000, new ActionListener()
		{
			int ncurindex = 1;
			public void actionPerformed(ActionEvent evt)
			{
				getHintUITextArea().setText((String)useTipList.get(getIndex()));
				getHintUITextArea().setToolTipText(getHintUITextArea().getText());
			}
			private int getIndex()
			{
				ncurindex++;
				if(ncurindex==useTipList.size())
					ncurindex = 0;
				return ncurindex;
			}
		});
	}


	private void stopTimer()
	{
		timer.stop();
		timeron = false;
	}


	private void startTimer()
	{
		timer.start();
		timeron = true;
	}

	/**
	 * 在滚动tip区域增加自定义的消息
	 * @param hintTip
	 */
	public void addUseTipMessage(String hintTip)
	{
		useTipList.add(hintTip);
	}

		/**
		* 设置虚拟输入名与真实输入名之间的映射。
		* @param inputSigMap
		*/
	public void setDummyInputSigMap(Map mapInputSig) {
		this.mapInputSig = mapInputSig;
		this.mapRevertInputSig = new HashMap();
		Set keys = mapInputSig.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String)iter.next();
			String value = (String)mapInputSig.get(key);
			mapRevertInputSig.put(value, key);
		}
	}
	
	public static String formulaEditor(String text){
		FormulaEditorDialog dlg=new FormulaEditorDialog(text );
		dlg.setModal(true);
		dlg.setResizable(true);
		dlg.pack();
		dlg.setVisible(true);
		return dlg.formulaEditor.getText();
	}

	public static void main(String[] args) {
//		FormulaEditorDialog dlg=new FormulaEditorDialog( );
//		dlg.setResizable(true);
//		dlg.pack();
//		dlg.setVisible(true);
		System.out.println(formulaEditor("haha"));
	}
}