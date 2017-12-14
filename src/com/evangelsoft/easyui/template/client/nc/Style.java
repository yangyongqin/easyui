//package com.evangelsoft.easyui.template.client.nc;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Insets;
//import java.util.Hashtable;
//
//import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
//import javax.swing.InputMap;
//import javax.swing.JPopupMenu;
//import javax.swing.ToolTipManager;
//import javax.swing.UIDefaults;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.border.BevelBorder;
//import javax.swing.border.Border;
//import javax.swing.border.LineBorder;
//import javax.swing.plaf.ColorUIResource;
//import javax.swing.plaf.InsetsUIResource;
//
//import sun.swing.SwingLazyValue;
//
///**
// * 界面样式风格 </br>
// *
// * 作者：孙润华</br> 修改：张 扬</br> 创建日期：(00-5-31 13:20:38)
// */
//public abstract class Style {
//
//	protected Hashtable<String, Object> m_hash = new Hashtable<String, Object>();
//
//	private static Style m_styleCur = null;
//
//	// zsb+基本组件的背景色
//	final public static Color clrBackground = new Color(0Xecf4fb);// D8D8D8);//C4C4C4);
//
//	// //0XE9E9E9
//
//	// 周善保2005-4-8:定义选中颜色
//	final static Color clrSelect = new Color(0X2E478B);
//
//	// 定义非空项颜色
//	final static Color clrNotnull = new Color(0X3333CC);
//
//	// 定义焦点颜色
//	public final static Color clrFocus = new Color(0Xfdf2c5);
//
//	// 定义控件可用颜色
//	public final static Color enableColor = new Color(0Xffffff);
//
//	// 定义控件不可用颜色
//	public final static Color unenableColor = new Color(0Xf6f9fd);// new
//																	// Color(0XEBEBE4);
//
//	// 定义控件焦点颜色
//	public final static Color focusColor = new Color(0Xfefedd);
//
//	// NC控件可用边框颜色
//	public final static Color NCborderColor = new Color(0X898989);// new
//																	// Color(0X7f9db9);
//
//	// NC控件不可用边框颜色
//	public final static Color NCborderDisableColor = new Color(0Xbcbcbc);
//
//	// NC控件焦点边框颜色
//	public final static Color NCborderFocusColor = new Color(0Xcd6411);
//
//	// Tree selection color
//	public final static Color TreeSelectionBackground = new Color(0X356799);
//
//	public final static Color TreeSelectionNoFocusBackground = new Color(0xb7b7b7);
//
//	// NC控件边框
//	public final static Border ncLineBorder = BorderFactory.createLineBorder(Style.NCborderColor);
//
//	Object focusCellHighlightBorder = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", null, new Object[] { new ColorUIResource(new Color(0xf9cd40)) });
//
//	/**
//	 * 此处插入方法说明。 创建日期：(2001-6-25 16:20:59)
//	 */
//	public Style() {
//		super();
//		initSystemStyle();
//		initDefaultStyle();
//		initLookAndFeel();
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:27:18)
//	 *
//	 * @return java.lang.Object
//	 * @param key
//	 *            java.lang.String
//	 */
//	public static Object get(String key) {
//		Object result = getCurrentStyle().m_hash.get(key);
//		return result;
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:32:16)
//	 *
//	 * @return com.sun.java.swing.border.Border
//	 * @param key
//	 *            java.lang.String
//	 */
//	public static Border getBorder(String key) {
//		Border result = null;
//		Object o = get(key);
//		if (o instanceof Border) {
//			result = (Border) o;
//		}
//		return result;
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:23:13)
//	 *
//	 * @return java.awt.Color
//	 * @param key
//	 *            java.lang.String
//	 */
//	public static java.awt.Color getColor(String key) {
//		Color result = null;
//		Object o = get(key);
//		if (o instanceof Color) {
//			result = (Color) o;
//		}
//		return result;
//	}
//
//	public static void refreshStyle() {
//		m_styleCur = null;
//		getCurrentStyle();
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:18:27)
//	 *
//	 * @return Style
//	 */
//	public static Style getCurrentStyle() {
//		/** 每次刷新都重新载入 */
//		if (m_styleCur == null) {
//			m_styleCur = new DefaultStyle();
//		}
//		return m_styleCur;
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 16:10:02)
//	 *
//	 * @return java.awt.Font
//	 * @param key
//	 *            java.lang.String
//	 */
//	public static Font getFont(String key) {
//		Font result = null;
//		Object o = get(key);
//		if (o instanceof Font) {
//			result = (Font) o;
//		}
//		return result;
//	}
//
//	/**
//	 * 根据键名获取图片文件。
//	 *
//	 * @param key
//	 *            java.lang.String
//	 */
//	public static ImageIcon getImage(String key) {
//		ImageIcon result = null;
//		try {
//			Object o = get(key);
//			if (o instanceof ImageIcon) {
//				result = (ImageIcon) o;
//			} else if (o instanceof String) {
//				String path = (String) o;
//
//				result = new ImageIcon(getCurrentStyle().getClass().getResource("/" + path));
//
//				if (result != null) {
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = null;
//		}
//		return result;
//	}
//
//	/**
//	 * 取得用友Logo图标， 由各用户组统一调用该方法得到用友图标
//	 *
//	 * @author 周善保2005-2-28
//	 * @return Image
//	 */
//	public static ImageIcon getUFLogoIcon() {
//		return getImage("主界面.用友标志");
//	}
//
//	/**
//	 * 取得标准的选中颜色
//	 *
//	 * @return Color
//	 */
//	public static Color getSelectColor() {
//		return clrSelect;
//	}
//
//	/**
//	 * 取得标准的非空颜色
//	 *
//	 * @return Color
//	 */
//	public static Color getNotnullColor() {
//		return clrNotnull;
//	}
//
//	/**
//	 * 取得标准的非空颜色
//	 *
//	 * @return Color
//	 */
//	public static Color getTableIntervalColor() {
//		return new Color(236, 244, 244);
//	}
//
//	public static String getFontname() {
//		String fontName = "Dialog";
////		Language lang = NCLangRes.getInstance().getCurrLanguage();
////		if (lang != null) {
////			String fn = lang.getFontName();
////			if (fn != null && fn.trim().length() > 0)
////				fontName = fn;
////		}
//		return fontName;
//
//	}
//
//	/**
//	 * 初始化默认风格数据。
//	 */
//	private void initDefaultStyle() {
//
//		/** 初始化图标 */
//		m_hash.put("登录.关于", "images/loginicon/about.jpg");
//
//		m_hash.put("业务流程.上级流程", "images/desktop30/previous.gif");
//		m_hash.put("业务流程.上级流程.彩", "images/desktop30/previous_c.gif");
//		m_hash.put("业务流程.顶级流程", "images/desktop30/top.gif");
//		m_hash.put("业务流程.顶级流程.彩", "images/desktop30/top_c.gif");
//		m_hash.put("业务界面.辅助功能背景", "images/desktop30/toolbg.gif");
//
//		//
//		m_hash.put("主界面.背景", "images/v5control/shading.jpg");
//		m_hash.put("消息.错误", "images/v5control/error.gif");
//		m_hash.put("消息.小错误", "images/v5control/error_mini.gif");
//		m_hash.put("消息.提示", "images/v5control/hint.gif");
//		m_hash.put("消息.小提示", "images/v5control/hint_mini.gif");
//		m_hash.put("消息.警告", "images/v5control/warning.gif");
//		m_hash.put("消息.小警告", "images/v5control/warning_mini.gif");
//		m_hash.put("消息.问题", "images/v5control/question.gif");
//
//		m_hash.put("参照.参照-ENABLED", "images/v5control/ref.gif");
//		m_hash.put("参照.参照-DISENABLED", "images/v5control/ref_unable.gif");
//		m_hash.put("参照.参照-PRESSED", "images/v5control/ref_down.gif");
//		m_hash.put("参照.参照-ROLLOVER", "images/v5control/ref_across.gif");
//
//		m_hash.put("参照.日历-ENABLED", "images/v5control/date.gif");
//		m_hash.put("参照.日历-DISENABLED", "images/v5control/date_unable.gif");
//		m_hash.put("参照.日历-PRESSED", "images/v5control/date_down.gif");
//		m_hash.put("参照.日历-ROLLOVER", "images/v5control/date_across.gif");
//
//		m_hash.put("参照.计算器-ENABLED", "images/v5control/calculator.gif");
//		m_hash.put("参照.计算器-DISENABLED", "images/v5control/calculator_unable.gif");
//		m_hash.put("参照.计算器-PRESSED", "images/v5control/calculator_down.gif");
//		m_hash.put("参照.计算器-ROLLOVER", "images/v5control/calculator_across.gif");
//
//		m_hash.put("参照.颜色-ENABLED", "images/v5control/colour.gif");
//		m_hash.put("参照.颜色-DISENABLED", "images/v5control/colour_unable.gif");
//		m_hash.put("参照.颜色-PRESSED", "images/v5control/colour_down.gif");
//		m_hash.put("参照.颜色-ROLLOVER", "images/v5control/colour_across.gif");
//
//		m_hash.put("参照.文件-ENABLED", "images/v5control/file.gif");
//		m_hash.put("参照.文件-DISENABLED", "images/v5control/file_unable.gif");
//		m_hash.put("参照.文件-PRESSED", "images/v5control/file_down.gif");
//		m_hash.put("参照.文件-ROLLOVER", "images/v5control/file_across.gif");
//
//		m_hash.put("参照.栏目", "images/v5control/refcolumn.gif");
//		m_hash.put("参照.刷新", "images/v5control/refresh.gif");
//		m_hash.put("参照.维护", "images/v5control/modify.gif");
//		m_hash.put("参照.查询", "images/v5control/refquery.gif");
//		m_hash.put("参照.高级查询", "images/v5control/refadquery.gif");
//		m_hash.put("参照.树", "images/v5control/reftree.gif");
//		m_hash.put("参照.表", "images/v5control/refgrid.gif");
//		m_hash.put("参照.增加", "images/v5control/refadd.gif");
//		m_hash.put("参照.删除", "images/v5control/refdelete.gif");
//		m_hash.put("参照.树非叶子节点", "images/v5control/reffolder.gif");
//		m_hash.put("参照.树叶子节点", "images/v5control/refleaf.gif");
//		m_hash.put("参照.加入常用", "images/v5control/addCommondata.gif");
//		m_hash.put("参照.下一页", "images/v5control/next.gif");
//		m_hash.put("参照.上一页", "images/v5control/previous.gif");
//
//		m_hash.put("参照.展开级次-ENABLED", "images/v5control/reflevel-enable.gif");
//		m_hash.put("参照.展开级次-DISENABLED", "images/v5control/reflevel-disable.gif");
//		m_hash.put("参照.展开级次-PRESSED", "images/v5control/reflevel-pressed.gif");
//		m_hash.put("参照.展开级次-ROLLOVER", "images/v5control/reflevel-rollover.gif");
//
//		m_hash.put("树.公司目录", "images/treeImages/corp.gif");
//
//		// 周善保+2005-3-16:banner图标
//		m_hash.put("waiting.banner", "images/v5control/banner.gif");
//
//		// 日历图片
//		m_hash.put("calendar.premonth", "images/v5control/left.gif");
//		m_hash.put("calendar.nextmonth", "images/v5control/right.gif");
//
//		// 增行删行图标
//		m_hash.put("uitable.addline", "images/toolbar/icon/addline.gif");
//		m_hash.put("uitable.insertline", "images/toolbar/icon/insertline.gif");
//		m_hash.put("uitable.deleteline", "images/toolbar/icon/deleteline.gif");
//		m_hash.put("uitable.deleteall", "images/toolbar/icon/delete.gif");
//
//		// spinner
//		m_hash.put("spinner.down", "images/v5control/spinner_down.gif");
//		m_hash.put("spinner.downaccross", "images/v5control/down_across.gif");
//		m_hash.put("spinner.downdown", "images/v5control/down_down.gif");
//		m_hash.put("spinner.downunable", "images/v5control/down_unable.gif");
//
//		m_hash.put("spinner.up", "images/v5control/spinner_up.gif");
//		m_hash.put("spinner.upaccross", "images/v5control/up_across.gif");
//		m_hash.put("spinner.updown", "images/v5control/up_down.gif");
//		m_hash.put("spinner.upunable", "images/v5control/up_unable.gif");
//
//		/** 初始化颜色 */
//		m_hash.put("错误", Color.red);
//		// 状态条提示颜色改为黑色
//		m_hash.put("提示", Color.black);
//		m_hash.put("警告", Color.yellow);
//		m_hash.put("阴影", new Color(191, 191, 191));
//		/** 初始化控件部分属性 */
//		m_hash.put("菜单.背景", new java.awt.Color(191, 184, 144));
//		m_hash.put("菜单.前景", Color.white);
//		m_hash.put("标签.背景", Color.white);
//		m_hash.put("标签.前景", new Color(0, 0, 200));
//		m_hash.put("状态条.前景", Color.black);
//		m_hash.put("状态条.背景", Color.lightGray);//
//		m_hash.put("表格.背景", Color.white);
//		m_hash.put("表格.单元", Color.white);
//		m_hash.put("表格.表头", new Color(240, 240, 240));
//		// zsb:update
//		m_hash.put("系统按钮条.背景", new Color(0XB6CADF));// new
//		m_hash.put("系统按钮条.前景", Color.white);
//		m_hash.put("按钮条.背景", new Color(0XB6CADF));//
//		m_hash.put("按钮条.前景", Color.black);
//		m_hash.put("按钮条.焦点", Color.white);
//		m_hash.put("标题.背景", new Color(210, 224, 236));
//		m_hash.put("标题.前景", new Color(0, 0, 0));//
//
//		m_hash.put("业务界面.背景", clrBackground);//
//		m_hash.put("对话框.背景", clrBackground);//
//
//		/** *初始化字体 */
//
//		/** 初始化边界 */
//
//	}
//
//	/**
//	 * 初始化界面样式－－LookAndFeel。
//	 */
//	private void initLookAndFeel() {
//
//		// lj+
//		UIManager.getDefaults().remove("MenuBar.windowBindings");
//		//
//		try {
//			InputMap im = (InputMap) javax.swing.UIManager.getDefaults().get("Table.ancestorInputMap");
//			im.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.KeyEvent.CTRL_MASK));
//			im.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.KeyEvent.CTRL_MASK));
//
//			InputMap im1 = (InputMap) javax.swing.UIManager.getDefaults().get("TabbedPane.ancestorInputMap");
//			im1.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.KeyEvent.CTRL_MASK));
//			im1.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.KeyEvent.CTRL_MASK));
//
//			InputMap im2 = (InputMap) javax.swing.UIManager.getDefaults().get("SplitPane.ancestorInputMap");
//			im2.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
//			im2.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
//			InputMap im3 = (InputMap) javax.swing.UIManager.getDefaults().get("ScrollBar.ancestorInputMap");
//			im3.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.KeyEvent.CTRL_MASK));
//			im3.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.KeyEvent.CTRL_MASK));
//
//			InputMap im4 = (InputMap) javax.swing.UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
//			im4.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.KeyEvent.CTRL_MASK));
//			im4.remove(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.KeyEvent.CTRL_MASK));
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//	/**
//	 * 初始化用户风格。
//	 *
//	 * @param pk_user
//	 *            java.lang.String
//	 */
//	protected abstract void initStyle(String pk_user);
//
//	/**
//	 * 根据图片路径得到ICON对象,放置出现异常
//	 *
//	 * @param imagepath
//	 * @return
//	 */
//	public ImageIcon getImageByUrl(String imagepath) {
//		try {
//			return new ImageIcon(this.getClass().getResource("/" + imagepath));
//
//		} catch (Exception e) {
////			Logger.error("Can't get ImageIcon,Url=" + imagepath);
//			return null;
//		}
//	}
//
//	/**
//	 * 返回默认的字体
//	 *
//	 * @return
//	 */
//	public static Font getDefaultFont() {
//		String sFontname = getFontname();
//		return new Font(sFontname, Font.PLAIN, 12);
//	}
//
//	/**
//	 * 初始化系统风格。 直接从程序载入NC系统默认风格
//	 */
//	private void initSystemStyle() {
//
//		/** *控件整体风格 */
//		ToolTipManager.sharedInstance().setInitialDelay(300);
//
//		String sFontname = getFontname();
//
//		Font ncfont = new Font(sFontname, Font.PLAIN, 12);
//		Object[][] styles = {
//
//		// *** Buttons
//				// zsb+:
//				{ "FileChooserUI", "nc.ui.pub.beans.UIFileChooserUI" }, { "ButtonUI", "nc.ui.plaf.basic.UIButtonUI" }, { "Button.font", ncfont }, { "Button.border", LineBorder.createGrayLineBorder() },// new
//				{ "Button.focus", clrFocus }, { "Button.background", new Color(0XC4C4C4) }, { "Button.foreground", Color.black }, { "Button.select", new Color(0XC4C4C4) }, { "Button.textIconGap", new Integer(2) }, { "Button.disabledText", new Color(0X9a9d9f) },// new
//				{ "Button.focusInputMap", new UIDefaults.LazyInputMap(new Object[] { "SPACE", "pressed", "released SPACE", "released", "ENTER", "pressed", "released ENTER", "released" }) }, { "ToggleButton.font", ncfont },
//
//				{ "RadioButton.font", ncfont }, { "RadioButton.background", clrBackground },//
//				{ "RadioButton.foreground", Color.black },
//
//				{ "CheckBox.focus", clrFocus }, { "CheckBox.font", ncfont }, { "CheckBox.background", clrBackground },//
//				{ "CheckBox.foreground", Color.black }, { "CheckBox.border", BorderFactory.createLineBorder(NCborderColor, 1) },
//
//				// *** ColorChooser
//				{ "ColorChooser.font", ncfont },
//
//				// *** ComboBox
//				{ "ComboBoxUI", "nc.ui.plaf.basic.UIComboBoxUI" }, { "SpinnerUI", "nc.ui.plaf.basic.UISpinnerUI" }, { "ComboBox.font", ncfont }, { "ComboBox.background", Color.white },//
//				{ "ComboBox.foreground", Color.black }, { "ComboBox.selectionForeground", Color.white }, { "ComboBox.selectionBackground", clrSelect },// 356799)},
//				{ "ComboBox.buttonBackground", Color.white },// clrBackground},
//				{ "ComboBox.inactivebuttonBackground", unenableColor },// clrBackground},
//				{ "ComboBox.disabledBackground", unenableColor },// new
//				{ "ComboBox.disabledForeground", Color.black },// B2B2B2)},
//				{ "ComboBox.buttonBorderColor", NCborderColor },// B2B2B2)},
////				{ "ComboBox.border", new ThreeColorBorder(NCborderColor, NCborderFocusColor, NCborderDisableColor) },// new
//
//				// *** InternalFrame
//				{ "InternalFrame.titleFont", new Font(sFontname, Font.BOLD, 14) }, { "InternalFrame.border", LineBorder.createBlackLineBorder() }, { "InternalFrame.activeTitleBackground", new java.awt.Color(52, 102, 153) }, { "InternalFrame.activeTitleForeground", Color.white }, { "InternalFrame.inactiveTitleBackground", new java.awt.Color(32, 82, 133) }, { "InternalFrame.inactiveTitleForeground", Color.gray },
//
//				{ "Desktop.background", clrBackground },
//				// Label
//
//				// *** Label
//				{ "Label.font", ncfont }, { "Label.foreground", Color.black }, { "Label.disabledForeground", new Color(0X878787) }, { "Label.notnullforeground", new Color(52, 102, 153) },
//
//				// *** List
//				{ "List.font", ncfont }, { "List.selectionForeground", Color.black },//
//				{ "List.selectionBackground", new Color(0Xfdf2c5) },// new
//				// Color(0XBCD5E7)
//				{ "List.focusCellHighlightBorder", focusCellHighlightBorder },
//				// *** Menus
//				{ "MenuBar.font", ncfont },
//				// zsb update:
//				{ "MenuItem.font", new Font(sFontname, 0, 12) }, { "MenuItem.background", new Color(0XCEE0EC) }, { "MenuItem.foreground", Color.black }, { "MenuItem.selectionForeground", Color.black },//
//				{ "MenuItem.selectionBackground", new Color(0X7D9BB8) },//
//				{ "MenuItem.disabledForeground", new Color(0X848484) },//
//				{ "MenuItem.acceleratorForeground", Color.black }, { "MenuItem.acceleratorSelectionForeground", Color.black }, { "MenuItem.acceleratorDelimiter", "+" },
//
//				{ "MessageDialog.bgcolor", new Color(0XEFF6FC) }, { "MessageDialog.linecolor", new Color(0X7F9DB9) },
//
//				// zsb update:
//				{ "RadioButtonMenuItem.background", new Color(0XCEE0EC) }, { "RadioButtonMenuItem.foreground", Color.black }, { "RadioButtonMenuItem.selectionForeground", Color.black }, { "RadioButtonMenuItem.selectionBackground", new Color(0X7D9BB8) },// new
//				{ "RadioButtonMenuItem.font", new Font(sFontname, 0, 12) }, { "RadioButtonMenuItem.disabledForeground", new Color(0X848484) }, { "RadioButtonMenuItem.acceleratorForeground", Color.black }, { "RadioButtonMenuItem.acceleratorSelectionForeground", Color.black }, { "RadioButtonMenuItem.acceleratorDelimiter", "+" },
//
//				// zsb update:
//				{ "CheckBoxMenuItem.background", new Color(0XCEE0EC) }, { "CheckBoxMenuItem.foreground", Color.black }, { "CheckBoxMenuItem.selectionForeground", Color.black },// new
//				{ "CheckBoxMenuItem.selectionBackground", new Color(0X7D9BB8) },// new
//				{ "CheckBoxMenuItem.font", new Font(sFontname, 0, 12) }, { "CheckBoxMenuItem.disabledForeground", new Color(0X848484) }, { "CheckBoxMenuItem.acceleratorForeground", Color.black }, { "CheckBoxMenuItem.acceleratorSelectionForeground", Color.black }, { "CheckBoxMenuItem.acceleratorDelimiter", "+" },
//
//				{ "Menu.font", new Font(sFontname, 0, 12) },
//
//				{ "PopupMenu.font", new Font(sFontname, 0, 12) }, { "PopupMenu.border", BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(0X2C5278), new Color(0XCEE0EC), Color.white, new Color(0X2C5278)) },
//
//				// *** OptionPane
//				{ "OptionPane.font", new Font(sFontname, 0, 12) }, { "OptionPane.background", clrBackground }, { "OptionPane.errorIcon", getImageByUrl("images/v5control/error.gif") }, { "OptionPane.informationIcon", getImageByUrl("images/v5control/hint.gif") }, { "OptionPane.warningIcon", getImageByUrl("images/v5control/warning.gif") }, { "OptionPane.questionIcon", getImageByUrl("images/v5control/question.gif") }, { "OptionPane.messageAreaBorder", BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black) }, { "OptionPane.buttonAreaBorder", BorderFactory.createMatteBorder(1, 0, 0, 0, Color.white) },
//
//				// *** Panel
//				// zsb update
//				{ "Panel.background", clrBackground },//
//				{ "Panel.fromColor", new Color(0xFFFFFF) }, { "Panel.toColor", new Color(0xF3F2F2) },
//
//				// *** ProgressBar
//				{ "ProgressBar.font", new Font(sFontname, 0, 12) }, { "ProgressBar.foreground", new Color(0X7C8CD3) }, { "ProgressBar.background", Color.WHITE },
//
//				{ "Separator.background", new Color(0XCCD3DB) },//
//				{ "Separator.foreground", new Color(0X6D7B8A) },//
//
//				// *** ScrollBar/ScrollPane/Viewport
//				// zsb+:修改滚动条的颜色控制
//				{ "ScrollBar.background", new Color(0XDCDFE3) },//
//				{ "ScrollBar.track", new Color(0XDCDFE3) },//
//				{ "control", new Color(0XC4CCD3) },// 解决上下箭头按钮的背景色
//				{ "controlShadow", new Color(0XC4CCD3) },// 解决上下箭头按钮的背景色
//				{ "controlLtHighlight", new Color(0XC4CCD3) },// 解决上下箭头按钮的背景色
//
//				{ "ScrollBar.thumb", new Color(0XC4CCD3) },//
//				{ "ScrollBar.thumbHighlight", Color.white },//
//				{ "ScrollBar.thumbDarkShadow", new Color(0X6C6C6C) },//
//				{ "ScrollBar.thumbLightShadow", new Color(0XC4CCD3) },//
////				{ "ScrollBarUI",  UIScrollBarUI.class.getName()},//"com.sun.java.swing.plaf.windows.WindowsScrollBarUI" },
//
//				{ "ScrollBar.border", BorderFactory.createMatteBorder(1, 1, 0, 0, Color.white) },
//
//				{ "ScrollPane.background", clrBackground },//
//				{ "ScrollPane.border", null }, { "ScrollPane.viewportBorder", null },
//
//				{ "Spinner.border", ncLineBorder }, { "Spinner.arrowButtonBorder", ncLineBorder }, { "Spinner.arrowButtonInsets", new Insets(1, 0, 1, 1) }, { "Spinner.arrowButtonSize", new Dimension(17, 8) }, { "Spinner.background", clrBackground },
//
//				{ "Viewport.background", Color.white },// EEEEEEnew
//
//				{ "SplitPane.dividerSize", new Integer(2) },
//
//				// *** TabbedPane
//				{ "TabbedPane.font", ncfont }, { "TabbedPane.selectedTabPadInsets", new InsetsUIResource(1, 2, 2, 1) }, { "TabbedPane.selected", new Color(0XF4F3F3) }, { "TabbedPane.background", new Color(0XB6B6B6) },//
//				// zsb update
//				{ "Table.font", ncfont }, { "Table.selectionForeground", Color.black }, { "Table.selectionBackground", new Color(0Xfdf2c5) },// new
//				{ "Table.background", Color.WHITE },//
//				{ "Table.gridColor", new Color(0Xd0d7e5) },// new
//				// Color(0XB7B8B9)
//				{ "Table.borderColor", new Color(0X9eb6ce) },// 表体边框颜色 new
//				// Color(0XB7B8B9)
//				{ "TableHeader.lineColor", new Color(0X9eb6ce) },// 表头竖线颜色new
//				// Color(0XB7B8B9)
//
//				{ "Table.scrollPaneBorder", BorderFactory.createLineBorder(NCborderColor, 1) }, // CCH
//				{ "Table.focusCellHighlightBorder", BorderFactory.createLineBorder(Color.black) }, { "TableHeader.font", ncfont }, { "TableHeader.background", new Color(0XE2E2E2) },//
////				{ "TableHeader.cellBorder", new TableHeaderBorder() },
//
//				// *** Text
////				{"TextFieldUI","nc.ui.plaf.basic.UITextFieldUI"},
//				{ "TextField.font", new Font(sFontname, 0, 12) }, { "TextField.background", Color.white },//
//				{ "TextField.foreground", Color.black }, { "TextField.selectionBackground", clrSelect },//
//				{ "TextField.selectionForeground", Color.white }, { "TextField.inactiveForeground", Color.black },//
//				{ "TextField.inactiveBackground", unenableColor },//
////				{ "TextField.border", new ThreeColorBorder(NCborderColor, NCborderFocusColor, NCborderDisableColor) },// new
//
//				{ "PasswordField.font", new Font(sFontname, 0, 12) }, { "PasswordField.background", Color.WHITE },//
//				{ "PasswordField.foreground", Color.black }, { "PasswordField.selectionBackground", clrSelect },//
//				{ "PasswordField.selectionForeground", Color.white }, { "PasswordField.inactiveForeground", new Color(0XA1A192) },// new
//				{ "PasswordField.inactiveBackground", unenableColor },// new
////				{ "PasswordField.border", new ThreeColorBorder(NCborderColor, NCborderFocusColor, NCborderDisableColor)  },// new
//
//				{ "TextArea.font", ncfont }, { "TextArea.background", Color.white },//
//				{ "TextArea.foreground", Color.black }, { "TextArea.selectionBackground", clrSelect },//
//				{ "TextArea.selectionForeground", Color.white }, { "TextArea.inactiveForeground", new Color(0XA1A192) },//
//				{ "TextArea.inactiveBackground", unenableColor },//
//				{ "TextArea.border", BorderFactory.createLineBorder(NCborderColor, 1) },
//
//				// *** TitledBorder
//				{ "TitledBorder.font", ncfont },
//
//				// *** ToolBar
//				{ "ToolBar.background", clrBackground },
//
//				{ "ClassLoader", this.getClass().getClassLoader() },
//
//				// *** ToolTips
//				{ "ToolTip.font", new Font(sFontname, 0, 12) },//
//				{ "ToolTip.background", new Color(0XFFFFE1) },//
//				{ "ToolTip.foreground", Color.black },//
//				{ "ToolTip.border", BorderFactory.createLineBorder(new Color(0X7A7A7A)) },//
//
//				// *** Tree原来都没有
//				{ "Tree.font", new Font(sFontname, 0, 12) },// dialogPlain12,
//				{ "Tree.background", Color.white },//
//				{ "Tree.line", new Color(0XC7C7C7) }, { "Tree.hash", new Color(0XC7C7C7) }, { "Tree.textForeground", Color.black },//
//				{ "Tree.textBackground", Color.white },// ,
//				{ "Tree.selectionForeground", Color.white },// ,
//				{ "Tree.selectionBackground", new Color(0X356799) },// ,
//				{ "Tree.selectionBorderColor", new Color(0X356799) },// black,
//				{ "Tree.leftChildIndent", new Integer(5) }, { "Tree.rightChildIndent", new Integer(10) }, { "Tree.openIcon", getImageByUrl("images/treeImages/Opened.gif") },//
//				{ "Tree.closedIcon", getImageByUrl("images/treeImages/Closed.gif") },//
//				{ "Tree.leafIcon", getImageByUrl("images/treeImages/Leaf.gif") }, { "Tree.expandedIcon", getImageByUrl("images/treeImages/treeexpand.gif") },//
//				{ "Tree.collapsedIcon", getImageByUrl("images/treeImages/treecollapse.gif") },//
//				{ "Tree.drawDashedFocusIndicator", Boolean.TRUE }, { "Tree.drawsFocusBorderAroundIcon", Boolean.FALSE },
//
//				{ "StandardUIDialog.error_miniIcon", getImageByUrl("images/v5control/error_mini.gif") }, { "StandardUIDialog.warning_miniIcon", getImageByUrl("images/v5control/warning_mini.gif") },
//
//		};
//
//		for (int i = 0; i < styles.length; i++) {
//			UIManager.put(styles[i][0], styles[i][1]);
//		}
////		UIManager.put(new JPopupMenu().getUIClassID(), UIPopupMenuUI.class.getName());
//
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:48:25)
//	 *
//	 * @param key
//	 *            java.lang.String
//	 * @param val
//	 *            java.lang.Object
//	 */
//	public static void put(String key, Object val) {
//		getCurrentStyle().m_hash.put(key, val);
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:35:00)
//	 *
//	 * @param style
//	 *            Style
//	 */
//	public static void setCurrentStyle(Style style) {
//		m_styleCur = style;
//	}
//
//	/**
//	 * Style 构造子注解。
//	 */
//	public Style(String pk_user) {
//		super();
//		initSystemStyle();
//		initDefaultStyle();
//		initStyle(pk_user);
//		initLookAndFeel();
//	}
//
//	// zsb+:改变以前每次getCurrentStyle都重新装载，现在重新装载时改为需要调用此方法
//	public static void refreshStyle(String pk_user) {
//		m_styleCur = null;
//		getCurrentStyle(pk_user);
//	}
//
//	/**
//	 * 在此处插入方法说明。 创建日期：(2000-10-24 15:18:27)
//	 *
//	 * @return Style
//	 */
//	public static Style getCurrentStyle(String pk_user) {
//		/** 每次刷新都重新载入 */
//		// zsb update:
//		if (m_styleCur == null) {// if(m_styleCur == null){
//			m_styleCur = new DefaultStyle(pk_user);
//		}// }
//		return m_styleCur;
//	}
//
//	/**
//	 * 初始化颜色常量。 以下是java定义的颜色常量 control－－－控件背景 controlText－－－控件前景(文字)
//	 * controlShadow－－－ controlDkShadow－－－ controlLtHighlight－－－
//	 *
//	 * text－－－ textText－－－ textHighlight－－－ textHighlightText－－－
//	 * textInactiveText－－－
//	 *
//	 * activeCaption－－－活动标题背景 activeCaptionText－－－活动标题前景(文字)
//	 * inactiveCaption－－－非活动标题背景 inactiveCaptionText－－－非活动标题前景(文字)
//	 *
//	 * desktop－－－桌面背景
//	 * window－－－窗口背景(TextField、TextArea、PasswordField、Table焦点单元格、Tree背景)
//	 *
//	 * menu－－－菜单背景 menuText－－菜单前景(文字)
//	 *
//	 * scrollBarTrack－－－ scrollbar－－－
//	 *
//	 * info－－－信息背景(Tooltip) infoText－－－信息前景(文字)
//	 */
//	public static void main(String[] args) {
//		// System.out.println("look="+UIManager.getLookAndFeel());
//		// System.out.println("treeline="+UIManager.get("Tree.line"));
//		// nc.ui.pub.cquery.CorpRefPanel.main(null);
//		// new DefaultStyle();
//		// javax.swing.JFrame frm = new javax.swing.JFrame();
//		// frm.setDefaultCloseOperation(3);
//		// frm.setLocation(300,300);
//		// frm.setSize(300, 400);
//		// frm.getContentPane().setLayout(new FlowLayout());
//		// JTextField tfUse = new nc.ui.pub.beans.UITextField();
//		// tfUse.setText("11222333");
//		// frm.getContentPane().add(tfUse);
//		// JTextField tfNotEditable = new nc.ui.pub.beans.UITextField();
//		// tfNotEditable.setEditable(false);
//		// tfNotEditable.setText("6666677783344566消息中心");
//		// frm.getContentPane().add(tfNotEditable);
//		// JTextField tfDisable = new nc.ui.pub.beans.UITextField();
//		// tfDisable.setText("tesxttt消息中心");
//		// tfDisable.setEnabled(false);
//		// frm.getContentPane().add(tfDisable);
//		//
//		// frm.getContentPane().add(new nc.ui.pub.beans.UICheckBox("消息中心"));
//		// JCheckBox ckEnable = new
//		// nc.ui.pub.beans.UICheckBox("ebaJCheckBoxddddddd");
//		// ckEnable.setSelected(true);
//		// ckEnable.setEnabled(false);
//		// frm.getContentPane().add(ckEnable);
//		// JCheckBox ckDisable = new
//		// nc.ui.pub.beans.UICheckBox("JCheckBoxddddddd");
//		// ckDisable.setEnabled(false);
//		// frm.getContentPane().add(ckDisable);
//		//
//		// JButton btn = new JButton("禁用按钮");
//		// btn.setEnabled(false);
//		// frm.getContentPane().add(btn);
//		// JButton btn1 = new JButton("可用按钮");
//		// frm.getContentPane().add(btn1);
//		// JComboBox combo = new JComboBox(new Object[]{"222","3334"});
//		// combo.setEnabled(false);
//		// frm.getContentPane().add(combo);
//		// frm.getContentPane().add( new JComboBox(new Object[]{"ddd","aaaa"}));
//		// JScrollPane scrollpan = new JScrollPane(new JTree());
//		// scrollpan.setPreferredSize(new Dimension(100,100));
//		// frm.getContentPane().add(scrollpan);
//		// JTextArea textarea = new JTextArea("dsdsd\nfdfdf\ngfgfd");
//		// JScrollPane scrollpanArea = new JScrollPane(textarea);
//		// scrollpanArea.setPreferredSize(new Dimension(100,100));
//		// frm.getContentPane().add(scrollpanArea);
//		// frm.setVisible(true);
//		// System.out.println(UIManager.get("Tree.rowHeight"));
//		// JOptionPane.showMessageDialog(frm,"dssdds");
//		// System.out.println("textareaui="+textarea.getUI());
//		//
//		// nc.ui.pub.beans.MessageDialog.showInputDlg(frm,"hint","请输入下拉数字","1234567788",50);
//		// nc.ui.pub.beans.MessageDialog.showInputDlg(frm,"hint","dsfdsf\nddddddddf\nffddddddfffffddsfs\nfdrfewrerrrrrrrrrryyrt","ndsdsdddddddddd",50);
//		// nc.ui.pub.beans.MessageDialog.showInputDlg(frm,"hint","dsfdsf\nddddddddf\nffddddddfffffddsfs\nfdrfewrerrrrrrrrrryyrt\netretesteettttttttttttt\nttttttttyesfffffffff\nfffgrtrttttttttttttttrtr","ndsdsdddddddddd",50);
//		// nc.ui.pub.beans.MessageDialog.showHintDlg(frm,"hint","hint\nddssds\ndsdsd\ndsdsd\nhint\nddssds\ndsdsd\ndsdsd");
//		// nc.ui.pub.beans.MessageDialog.showErrorDlg(frm,"error","hint\nddssds\ndsdsd\ndsdsd");
//		// nc.ui.pub.beans.MessageDialog.showWarningDlg(frm,"warning","hint\nddssds\ndsdsd\ndsdsd");
//		// nc.ui.pub.beans.MessageDialog.showYesNoDlg(frm,"question","hint\nddssds\ndsdsd\ndsdsd");
//	}
//
//}