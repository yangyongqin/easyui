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
 * �¹�ʽ�༭���Ի���֧�ֹ�ʽ�ͱ�����ǩҳ����Ӻ�ɾ����
 *
 * @author ������
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
		//		super(parent, "��ʽ�༭");
		super(getFrame(parent), "��ʽ�༭");
		initialize();
	}

	FormulaEditorDialog(){
		initialize();
	}
	FormulaEditorDialog(String text){
		initialize();
		this.formulaEditor.setText(text);
	}

	String formulaDesc = null; //��ʽ����

	String formulaDescWithDummyMap = null; // ����ӳ��Ĺ�ʽ����

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

	private Timer timer; //��ʱ��ʾʹ�ü���
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
		//�����ʾ��Ŀ
		useTipList.add("���������ұ�˫�����Զ�ƥ�䵽��֮��Ӧ��������"/*"���������ұ�˫�����Զ�ƥ�䵽��֮��Ӧ��������"*/);
		useTipList.add("��ʽ��ĸ�ֵ����Ϊ->������=,�ж��Ƿ������=="/*"��ʽ��ĸ�ֵ����Ϊ->������=,�ж��Ƿ������=="*/);
		useTipList.add("�����ж�����iif����,ѡ����Ӧ�ĺ������Բ鿴��ϸ˵��"/*"�����ж�����iif����,ѡ����Ӧ�ĺ������Բ鿴��ϸ˵��"*/);

		//		setResizable(false);
		setName("DiaTest");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 520));

		setContentPane(getPnlContent());
		//����¼���⹫ʽ����ƥ��
		getFormulaEditor().addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==2 && e.getButton()==1) //left double click
				{
					selTextWithinBraces(); //ѡ�������ڵ�����
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

	/* �༭��� */
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
			//			hintlabel.setIcon(Style.getImage("��Ϣ.С��ʾ"));
			hintlabel.setToolTipText("����ͼ��򿪻��߹ر���ʾ��Ϣ"/*"����ͼ��򿪻��߹ر���ʾ��Ϣ"*/);
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
			btnValidate = new JButton("��ʽ��֤"/*@res "��ʽ��֤"*/);
			btnValidate.setMargin(new Insets(0, 0, 0, 0));
			btnValidate.setPreferredSize(new Dimension(60, 25));
			btnValidate.addActionListener(this);
		}
		return btnValidate;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("ȷ��"/*@res "ȷ��"*/);
			btnOk.getModel().setActionCommand("ACT_OK");
			btnOk.setPreferredSize(new Dimension(60, 25));
			btnOk.addActionListener(this);
		}
		return btnOk;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("ȡ��"/*@res "ȡ��"*/);
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
			// ��δ���ΪRichEditor����˴ʷ�������
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

	/* ��������������ʾ��� */
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

	/** �����ͱ������ */
	private JPanel getFormulaAndVariablePanel() {
		if (formulaAndVariablePanel == null) {
			formulaAndVariablePanel = new JPanel();
			formulaAndVariablePanel.setLayout(new GridLayout(1, 0, 15, 15));
			getFormulaAndVariablePanel().add(getFunctionTabbedPanel());
			getFormulaAndVariablePanel().add(getVariableTabbedPanel());
		}
		return formulaAndVariablePanel;
	}

	//	/** ������ҳǩ����� */
	private UITabbedPane getFunctionTabbedPanel() {
		if (formulaTabbedPanel == null) {
			formulaTabbedPanel = new UITabbedPane();
			formulaTabbedPanel.setBackground(SystemColor.RED);
			formulaTabbedPanel.setTabLayoutPolicy(UITabbedPane.SCROLL_TAB_LAYOUT);

			// ���ú�����ǩ���
			initFunDirector();
			funDirector.addBuilder(0, new CommonFunTabBuilder(
					getFormulaWordSorter()));
			addAllTab(formulaTabbedPanel, funDirector);
		}
		return formulaTabbedPanel;
	}

	//	/** ������ҳǩ����� */
	private UITabbedPane getVariableTabbedPanel() {
		if (variableTabbedPanel == null) {
			variableTabbedPanel = new UITabbedPane();
			variableTabbedPanel.setTabLayoutPolicy(UITabbedPane.SCROLL_TAB_LAYOUT);
			// ���ñ�����ǩ���
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
	 * ��ʽ�༭����м���һ���Զ���Ĺ�ʽ��ǩ�����߱�����ǩ���
	 *
	 * @param builder
	 *            �Զ����ǩ���Ĵ�����
	 * @param option
	 *            ��ʽ���Ǳ���
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
	 * ͬ�ϡ�
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
	 * �滻��ʽ�༭�����ָ��λ�õ�һ���Զ���Ĺ�ʽ��ǩ�����߱�����ǩ���.
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
	 * ɾ��һ���ǩ��塣
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

	/** ʹ�ô���ָ���� <code>director</code> ����ǩ��� <code>tabbedPane</code> ��ӱ�ǩ */
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
	 * ��ʽ��ǩ�����߱�����ǩ��嵱ǰ��ǩ����
	 *
	 * @param option
	 *            ָ���ĸ����
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
//				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "��ʽ��֤"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000056")/*@res "��ʽ��֤�ɹ�!"*/);
//				} else {
//				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "��ʽ��֤"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "��ʽ��֤ʧ��!"*/);
//				}
//				} catch (FormulaException e) {
//				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "��ʽ��֤"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "��ʽ��֤ʧ��!"*/);
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
	//	MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "��ʽ��֤"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "��ʽ��֤ʧ��!"*/);
	//	}
	//	} catch (FormulaException e) {
	//	MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000028")/*@res "��ʽ��֤"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("formulaparse","UPPformulaparse-000057")/*@res "��ʽ��֤ʧ��!"*/);
	//	}
	//	}

//		private void onBtnCancel() {
//		closeCancel();
//		}

//		/**
//		* ���ı��г�ȡ��ʽ����������������ͱ�����֮����滻��
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
		* �ı��滻������û�Ҫ���ڹ�ʽ��ʾʱ��ʾ���ĵ�����
		* @param strs
		*/
		private void textReplace(String[] strs) throws Exception {
		for (int i = 0; i < strs.length; i++) {
		strs[i] = StringUtil.removeCharFromString(strs[i], '\n');
		if (mapInputSig != null) {
		while (strs[i].indexOf("{") >= 0) {
		String dummyInputSig = substringBetween(strs[i], "{", "}");
		if (dummyInputSig == null || mapInputSig.get(dummyInputSig) == null) {
		throw new Exception("������������");
		}
		strs[i] = replaceFromTo(strs[i], "{", "}", (String)mapInputSig.get(dummyInputSig));
		}
		}
		}
		}

	/**
	 * �滻�ַ���source�д�strBegin��strEnd�����ݣ��滻������replaced�ַ�����
	 * ��һ������ĵط����ڣ�һ�����滻�ַ������ܽ�����ǰһ�������ַ�����
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
				throw new Exception("������������");
			}
		}
		return source.substring(0, index) + replaced + source.substring(index1 + strEnd.length());
	}

	/**
	 * ȡԭ�ַ����п�ʼ�����������֮����Ӵ�����������ʼ�ͽ�����Ǵ���
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
		if (indexBegin < 0) { // ��ʼ��Ǻͽ�����ǲ�ƥ��
			return null;
		}
		if (indexEnd < 0) { // ��ʼ��Ǻͽ�����ǲ�ƥ��
			return null;
		}
		if (indexBegin >=  indexEnd) {
			return null;
		}
		return source.substring(indexBegin + 1, indexEnd);
	}

	//	/**
	//	* ���ݹ�ʽ���JEPע��Ĺ�ʽ���ࡣ
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
			addFunTabBuilder(dbFuns, "���ݿ�"/*@res "���ݿ�"*/);
		}
		if (strFuns.size() > 0) {
			addFunTabBuilder(strFuns, "�ַ���"/*@res "�ַ���"*/);
		}
		if (dateFuns.size() > 0) {
			addFunTabBuilder(dateFuns, "����"/*@res "����"*/);
		}
		if (mathFuns.size() > 0) {
			addFunTabBuilder(mathFuns, "��ѧ"/*@res "��ѧ"*/);
		}
		if (glFuns.size() > 0) {
			addFunTabBuilder(glFuns, "����"/*@res "����"*/);
		}
		if (printFuns.size() > 0) {
			addFunTabBuilder(printFuns, "��ӡ"/*@res "��ӡ"*/);
		}
		if (customFuns.size() > 0) {
			addFunTabBuilder(customFuns, "�Զ���"/*@res "�Զ���"*/);
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
	 * �����ڹ�ʽ�༭���б༭��Ĺ�ʽ����û��setDummyInputSigMapʱ��getFormulaDescWithDummyMap()���صĽ����ͬ��
	 */
	public String getFormulaDesc() {
		return formulaDesc;
	}

	/**
	 * ������Ҫ���ּ�ӳ��֮��Ĺ�ʽ������
	 */
	public String getFormulaDescWithDummyMap() {
		return formulaDescWithDummyMap;
	}

	/**
	 * �������Ϲ�ʽ�����༭�Ի���
	 */
	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
		String formulaDisplayDesc = textRevertReplace(formulaDesc);
		getFormulaEditor().setText(formulaDisplayDesc);
	}

	/**
	 * �����滻��ʽ�ı��������ַ�����ʽ�ı����滻����������
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
	 * �滻ԭ�ַ���source��ָ���Ӵ���
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
	 * �ж�tab��û���Ѿ����ڡ�
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
	 * ����tab��λ��,������,�򷵻�-1
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
	 * ��ʼ����ʱ��
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
	 * �ڹ���tip���������Զ������Ϣ
	 * @param hintTip
	 */
	public void addUseTipMessage(String hintTip)
	{
		useTipList.add(hintTip);
	}

		/**
		* ������������������ʵ������֮���ӳ�䡣
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