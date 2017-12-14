package com.evangelsoft.easyui.config.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.evangelsoft.workbench.desktop.Workbench;
import com.evangelsoft.workbench.swing.JInternalDialog;

/** * @author  杨永钦
 E-mail:
@date ：2016-9-9 下午09:53:27
@version 1.0   * @since    */
public class FontManage extends JInternalFrame{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -1832375885906752174L;
	public JButton button;
	public FontManage(){
		init();
		this.setClosable(true);
		this.setMaximizable(true);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		//		this.setm
	}
	private JLabel fontNameLabel;//字体名称
	private JComboBox<String> fontNameBox;
	private JLabel fontSizeLabel;//字体大小
	private JSpinner fontSizeSpinner;
	private JButton changeButton;//改变
	JPanel toppanel=new JPanel();

	private JPanel previewPanel;//预览面包
	private JLabel previewLabel;//预览标签
	private JTextField previewField;//预览文本
	private JButton previewButton;//预览按钮
	private JTextArea previewArea;//预览文本域


	private Font font;

	public void init(){
		GridBagLayout localGridBagLayout1 = new GridBagLayout();
		localGridBagLayout1.columnWidths=new int[]{5,5,5,5,5,5,5,5};
		localGridBagLayout1.rowHeights=new int[]{5,5,5};
		toppanel.setLayout(localGridBagLayout1);
		this.setTitle("字体管理");
		fontNameLabel=new JLabel("字体：");
		fontNameBox=new JComboBox<String>();
		fontSizeLabel=new JLabel("字体大小：");
		fontSizeSpinner= new JSpinner(new SpinnerNumberModel(12, 1, 100, 1));
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				fontChenge();
			}
		});
		//		fontSizeSpinner.getEditor().addc
		//		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(fontSizeSpinner, "0");
		//		fontSizeSpinner.setEditor(editor);
		JFormattedTextField textField = ((JSpinner.NumberEditor) fontSizeSpinner.getEditor())

				.getTextField();
		//		textField.setDocument(new NumberDocument());
		//		fontSizeSpinner.getModel().addChangeListener(new ChangeListener() {
		//
		//			@Override
		//			public void stateChanged(ChangeEvent e) {
		//				System.out.println("AAcccccccccccccc");
		//			}
		//		});

		textField.setEditable(true);
		DefaultFormatterFactory factory = (DefaultFormatterFactory) textField
				.getFormatterFactory();
		NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
		formatter.setAllowsInvalid(false);
		//获取系统中可用的字体的名字
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();

		String fontNameStr= fontNameBox.getFont().getFontName();

		int index=0;
		for(int i = 0; i<fontName.length ; i++)
		{
			if(fontNameStr.equals(fontName[i])||(fontNameStr.startsWith("Dialog")&&fontNameStr.startsWith(fontName[i]))){
				index=i;
			}
			fontNameBox.addItem(fontName[i]);
		}
		fontNameBox.setSelectedIndex(index);
		fontNameBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					fontChenge();
//					font=new Font(fontNameBox.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(fontSizeSpinner.getValue().toString()));
				}
			}
		});


		changeButton=new JButton("变变变");
		changeButton.addActionListener(new FontChangeActionListener());
		toppanel.add(fontNameLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		toppanel.add(fontNameBox,
				new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		toppanel.add(fontSizeLabel, new GridBagConstraints(4, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		toppanel.add(fontSizeSpinner,
				new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));
		toppanel.add(changeButton, new GridBagConstraints(7, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(toppanel,BorderLayout.NORTH);


		GridBagLayout bodyLayout = new GridBagLayout();
		bodyLayout.columnWidths=new int[]{5,5,5,5,5,5};
		bodyLayout.rowHeights=new int[]{5,5,5};

		previewPanel=new  JPanel();
		previewPanel.setLayout(bodyLayout);
		previewLabel=new JLabel("标签预览");

		previewPanel.add(previewLabel, new GridBagConstraints(1, 1, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		previewPanel.add(previewField=new JTextField("预览文本框"),
				new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,0), 0, 0));


		previewPanel.add(previewButton=new JButton("按钮预览"),
				new GridBagConstraints(5, 1, 1, 1,
						0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));


		previewArea=new JTextArea();
		previewArea.setText("《春晓》\n 孟浩然\n春眠不觉晓，处处闻啼鸟。\n夜来风雨声，花落知多少。");

		previewPanel.add(previewArea,
				new GridBagConstraints(1, 3, 5, 1, 1.0, 1,GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0,0), 0, 0));

		this.getContentPane().add(previewPanel,BorderLayout.CENTER);
		pack();
	}
	public class FontChangeActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(fontSizeSpinner.getValue()!=null&&fontSizeSpinner.getValue().toString().length()>0){
				Font vFont = new Font(fontNameBox.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(fontSizeSpinner.getValue().toString()));
				UIManager.put("CheckBox.font", vFont);
				UIManager.put("Tree.font", vFont);
				UIManager.put("Viewport.font", vFont);
				UIManager.put("ProgressBar.font", vFont);
				UIManager.put("RadioButtonMenuItem.font", vFont);
				UIManager.put("FormattedTextField.font", vFont);
				UIManager.put("ToolBar.font", vFont);
				UIManager.put("ColorChooser.font", vFont);
				UIManager.put("ToggleButton.font", vFont);
				UIManager.put("Panel.font", vFont);
				UIManager.put("TextArea.font", vFont);
				UIManager.put("Menu.font", vFont);
				UIManager.put("RadioButtonMenuItem.acceleratorFont", vFont);
				UIManager.put("Spinner.font", vFont);
				UIManager.put("Menu.acceleratorFont", vFont);
				UIManager.put("CheckBoxMenuItem.acceleratorFont", vFont);
				UIManager.put("TableHeader.font", vFont);
				UIManager.put("TextField.font", vFont);
				UIManager.put("OptionPane.font", vFont);
				UIManager.put("MenuBar.font", vFont);
				UIManager.put("Button.font", vFont);
				UIManager.put("Label.font", vFont);
				UIManager.put("PasswordField.font", vFont);
				UIManager.put("InternalF8 rame.titleFont", vFont);
				UIManager.put("OptionPane.buttonFont", vFont);
				UIManager.put("ScrollPane.font", vFont);
				UIManager.put("MenuItem.font", vFont);
				UIManager.put("ToolTip.font", vFont);
				UIManager.put("List.font", vFont);
				UIManager.put("OptionPane.messageFont", vFont);
				UIManager.put("EditorPane.font", vFont);
				UIManager.put("Table.font", vFont);
				UIManager.put("TabbedPane.font", vFont);
				UIManager.put("RadioButton.font", vFont);
				UIManager.put("CheckBoxMenuItem.font", vFont);
				UIManager.put("TextPane.font", vFont);
				UIManager.put("PopupMenu.font", vFont);
				UIManager.put("TitledBorder.font", vFont);
				UIManager.put("ComboBox.font", vFont);

				SwingUtilities.updateComponentTreeUI(Workbench.desktopFrame);
			}

		/*	try {
				UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");
				SwingUtilities.updateComponentTreeUI(Workbench.desktopFrame);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}*/
			pack();
		}
	}
	public void fontChenge(){
		if(fontSizeSpinner.getValue()!=null&&fontSizeSpinner.getValue().toString().length()>0){
			font=new Font(fontNameBox.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(fontSizeSpinner.getValue().toString()));
		}
		if(font!=null){
			//当前显示 组件修改字体
//			private JLabel previewLabel;//预览标签
//			private JTextField previewField;//预览文本
//			private JButton previewButton;//预览按钮
//			private JTextArea previewArea;//预览文本域
			previewLabel.setFont(font);
			previewField.setFont(font);
			previewButton.setFont(font);
			previewArea.setFont(font);
		}
	}
	public static void main(String[] args) {
		FontManage font=new FontManage();
		JInternalDialog.showAsDialog(null, font);
	}
}
