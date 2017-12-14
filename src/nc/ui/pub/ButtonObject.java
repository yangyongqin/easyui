package nc.ui.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 按钮对象，提供对业务界面命令按钮的一个抽象包装，方便集中控制。一个按钮对象可包含子按钮。如果是组合按钮，则可执行多个按钮的功能。
 * 抽象按钮对象相应的可以是按钮，也可以是选择框，还可以是分界符。
 * 
 * @author 孙润华 2000-5-31
 * @author 陈新宇 2001-5-25
 * @since 1.0
 * @modifier zsb 2004-12-30 增加{@link #ButtonObject(String, String, String)}构造函数。
 */
public class ButtonObject {
	/* 显示名称。 */
	private String m_strName = null;

	/* 提示信息。 */
	private String m_strHint = null;

	/* 权限级别。 */
	private int m_nPower = 0;

	/* 父按钮。 */
	private ButtonObject m_boParent = null;

	/* 子按钮的集合。 */
	private Vector m_vecChildren = null;

	/* 按钮是否可用，不可用时，在界面上一般会变灰。 */
	private boolean m_bEnabled = true;

	/* 按钮是否显示。 */
	private boolean m_bVisible = true;

	/* 是否有权限。没有权限的按钮不显示。 */
	private boolean m_bPower = true;

	/* 附带的数据对象。 */
	private Object m_data = null;

	/* 一个内部标签，用来记录<code>ButtonObject</code>与实际按钮的对应关系。 */
	private String m_internalTag = null;

	/* 按钮修饰符 */
	private int m_intModifiers = 0; // -1;

	/* 此按钮的子按钮组是否是选择按钮。 */
	private boolean m_isCheckboxGroup = false;

	/* 此按钮的子按钮的选择模式：互斥还是多选。 */
	private boolean m_isExclusiveMode = true;

	/*
	 * licp+ 2004-6-1 是否受按钮权限控制，如果为true,就表示该按钮受按钮权限参数设置的控制，否则就不受按钮权限参数的控制。
	 */
	private boolean m_isPowerContrl = true;

	/* 按钮是否被选中。 */
	private boolean m_isSelected = false;

	/* 是否为分隔符结点。 */
	private boolean m_isSeperator = false;

	/* 按钮编码 */
	private String m_strCode = null;

	/* 加速键显示名 */
	private String m_strDisplayHotkey = null;

	/*
	 * zhangyang+ 加速键名
	 */
	private String m_strHotKey = null;

	/* 内部标识信息。 */
	private String m_strTag = null;

	/* zsb+：是否组合功能键，<code>true</code>表示多个则多个<code>ButtonObject</code>的功能组成。 */
	private boolean bUnionFunc = false;

	/* 组合功能键的子按钮。 */
	private ArrayList alUnionFuncbtn;

	/**
	 * 指定按钮文字构造按钮对象。按钮提示信息将与按钮文字一致。
	 * 
	 * @param strName 按钮的多语名称
	 * @deprecated 没有给code赋值的错误构造方法
	 */
	public ButtonObject(String strName) {
		this(strName, strName);
	}

	/**
	 * 创建一个新的按钮，包含相关的子按钮。
	 * 
	 * @param name 按钮文字
	 * @param children 子按钮数组
	 * @deprecated 没有给code赋值的错误构造方法
	 */
	public ButtonObject(String name, ButtonObject[] children) {
		this(name);
		setChildButtonGroup(children);
	}

	/**
	 * 指定按钮文字与提示信息构造按钮对象。权限级别默认为<code>0</code>。
	 * 
	 * @param name 按钮文字
	 * @param hint 提示名信息
	 * @deprecated 没有给code赋值的错误构造方法
	 */
	public ButtonObject(String name, String hint) {
		this(name, hint, 0);
	}

	/**
	 * 指定按钮文字，提示信息，权限级别构造按钮对象。
	 * 
	 * @param name 按钮文字
	 * @param hint 提示信息
	 * @param power 权限级别
	 * @deprecated 没有给code赋值的错误构造方法
	 */
	public ButtonObject(String name, String hint, int power) {
		m_strName = name;
		m_strHint = hint;
		m_nPower = power;
		m_strCode = name;
		m_internalTag = name + String.valueOf(Math.random());
	}

	/**
	 * 指定按钮文字，提示信息，按钮编码构造按钮对象。
	 * <p>
	 * 周善保 创建日期：(2004-12-30)
	 * 
	 * @param name 按钮文字
	 * @param hint 提示信息
	 * @param code 按钮编码
	 */
	public ButtonObject(String name, String hint, String code) {
		this(name, hint, 0, code);
	}

	/**
	 * 指定按钮文字，提示信息，权限级别，按钮编码构造按钮对象。
	 * 
	 * @param name 按钮文字
	 * @param hint 提示信息
	 * @param power 权限级别
	 * @param code 按钮编码
	 */
	public ButtonObject(String name, String hint, int power, String code) {
		m_strName = name;
		m_strHint = hint;
		m_nPower = power;
		m_strCode = code;
		m_internalTag = name + String.valueOf(Math.random());
	}

	/**
	 * 增加一个子按钮。
	 * 
	 * @param bo 子按钮对象
	 */
	public void addChildButton(ButtonObject bo) {
		bo.setParent(this);
		getChildren().addElement(bo);
	}

	/**
	 * 获得子按钮对象数组。
	 * <p>
	 * 创建日期：(2000-11-23 15:54:21)
	 * 
	 * @return 子按钮对象数组
	 */
	public ButtonObject[] getChildButtonGroup() {
		ButtonObject[] bos = new ButtonObject[getChildren().size()];
		return (ButtonObject[]) getChildren().toArray(bos);
	}

	/**
	 * 获得所有子按钮的向量。
	 * 
	 * @return 没有子按钮则返回长度为0的向量。返回值不为空。
	 */
	public Vector getChildren() {
		if (m_vecChildren == null) {
			try {
				m_vecChildren = new Vector();
			} catch (Throwable e) {
				handleException(e);
			}
		}
		return m_vecChildren;
	}

	/**
	 * 获得提示信息。
	 * 
	 * @return 提示信息
	 */
	public String getHint() {
		return m_strHint;
	}

	/**
	 * 获得按钮文字。
	 * 
	 * @return 按钮文字
	 */
	public String getName() {
		return m_strName;
	}

	/**
	 * 获得父按钮。
	 * 
	 * @return 父按钮对象
	 */
	public ButtonObject getParent() {
		return m_boParent;
	}

	/**
	 * 获得权限级别。
	 * 
	 * @return 限级别
	 */
	public int getPower() {
		return m_nPower;
	}

	/**
	 * 打印异常栈。
	 * 
	 * @param exception
	 *            异常
	 */
	private static void handleException(Throwable exception) {
		exception.printStackTrace(System.err);
	}

	/**
	 * 按钮是否可用。
	 * <p>
	 * 创建日期：(1999-7-28 11:14:34)
	 * 
	 * @return 是否可用
	 */
	public boolean isEnabled() {
		return m_bEnabled;
	}

	/**
	 * 按钮是否显示。
	 * <p>
	 * 创建日期：(2000-11-10 11:02:56)
	 * 
	 * @return 是否显示
	 */
	public boolean isVisible() {
		return m_bVisible;
	}

	/**
	 * 删除所有的子按钮。
	 * <p>
	 * 创建日期：(2000-11-23 15:48:51)
	 */
	public void removeAllChildren() {
		getChildren().removeAllElements();
	}

	/**
	 * 删除指定子按钮。
	 * 
	 * @param bo
	 *            需删除的子按钮对象
	 */
	public void removeChildButton(ButtonObject bo) {
		getChildren().remove(bo);
	}

	/**
	 * 设置子按钮组 
	 * 
	 * @param bos 子按钮对象数组
	 */
	public void setChildButtonGroup(ButtonObject[] bos) {
		if (bos != null) {
			Vector children = getChildren();
			children.removeAllElements();
			for (int i = 0; i < bos.length; i++) {
				bos[i].setParent(this);
				children.addElement(bos[i]);
			}
		}
	}

	/**
	 * 设置按钮是否可用。
	 * <p>
	 * 创建日期：(1999-7-28 11:13:57)
	 * 
	 * @param b
	 *            按钮是否可用
	 */
	public void setEnabled(boolean b) {
		m_bEnabled = b;
	}

	/**
	 * 设置按钮提示信息。
	 * 
	 * @param strHint
	 *            按钮提示
	 */
	public void setHint(String strHint) {
		m_strHint = strHint;
	}

	/**
	 * 设置按钮文字。
	 * 
	 * @param newvalue
	 *            按钮文字
	 */
	public void setName(String newvalue) {
		m_strName = newvalue;
	}

	/**
	 * 设置父按钮对象。
	 * 
	 * @param newvalue
	 *            父按钮对象
	 */
	public void setParent(ButtonObject newvalue) {
		m_boParent = newvalue;
	}

	/**
	 * 设置权限级别。
	 * 
	 * @param nPower
	 *            权限级别
	 */
	void setPower(int nPower) {
		m_nPower = nPower;
	}

	/**
	 * 设置按钮的可见性。
	 * <p>
	 * 创建日期：(2000-11-10 11:02:27)
	 * 
	 * @param visi
	 *            按钮的可见性
	 */
	public void setVisible(boolean visi) {
		m_bVisible = visi;
	}

	/**
	 * 添加一组子按钮。
	 * 
	 * @param aryBtns 子按钮对象数组
	 * @deprecated since 5.0. please use
	 *             {@link #setChildButtonGroup(ButtonObject[])} instead.
	 */
	public void addChileButtons(ButtonObject[] aryBtns) {
		// if (aryBtns != null) {
		// getChildren().removeAllElements();
		// for (int i = 0; i < aryBtns.length; i++) {
		// addChildButton(aryBtns[i]);
		// }
		// }
		setChildButtonGroup(aryBtns);
	}

	/**
	 * 返回子按钮的个数。
	 * <p>
	 * 创建日期：(2001-6-3 10:42:30)
	 * 
	 * @return 子按钮的个数
	 */
	public int getChildCount() {
		if (m_vecChildren == null)
			return 0;
		else
			return getChildren().size();
	}

	/**
	 * 返回按钮编码。
	 * <P>
	 * 创建日期：(2002-4-18 12:50:43)
	 * 
	 * @return 按钮编码
	 */
	public String getCode() {
		return m_strCode;
	}

	/**
	 * 返回附带的数据对象。
	 * <p>
	 * 创建日期：(2002-6-3 10:14:59)
	 * 
	 * @return java.lang.Object
	 */
	public Object getData() {
		return m_data;
	}

	/**
	 * 返回加速键的显示名。
	 * <p>
	 * 创建日期：(2002-4-15 18:51:36)
	 * 
	 * @return boolean
	 */
	public String getDisplayHotkey() {
		return m_strDisplayHotkey;
	}

	/**
	 * 返回加速键名称。
	 * <p>
	 * 创建日期：(2002-4-15 17:21:59)
	 * 
	 * @return java.lang.String
	 */
	public String getHotKey() {
		return m_strHotKey;
	}

	/**
	 * 返回内部标识。
	 * <p>
	 * 创建日期：(2001-6-3 9:55:04)
	 * 
	 * @return 内部标识
	 */
	public String getInternalTag() {
		return m_internalTag;
	}

	/**
	 * 返回按钮修饰符。
	 * <p>
	 * 创建日期：(2003-11-10 14:02:10)
	 * 
	 * @return 按钮修饰符
	 */
	public int getModifiers() {
		return m_intModifiers;
	}

	/**
	 * 如果子按钮组是选择框按钮，则返回被选中的子按钮，否则为空。
	 * <p>
	 * 创建日期：(2001-6-23 18:22:33)
	 * 
	 * @return 被选中的子按钮数组
	 */
	public ButtonObject[] getSelectedChildButton() {
		ButtonObject[] bo = null;
		if (isCheckboxGroup() && getChildCount() > 0) {
			List list = new ArrayList();
			ButtonObject[] ary = getChildButtonGroup();
			for (int i = 0; i < ary.length; i++) {
				if (ary[i].isSelected()) {
					list.add(ary[i]);
				}
			}
			if (list.size() > 0) {
				bo = new ButtonObject[list.size()];
				list.toArray(bo);
			}
		}
		return bo;
	}

	/**
	 * 返回内部标识信息。
	 * <p>
	 * 创建日期：(2001-5-25 10:31:01)
	 * 
	 * @return 内部标识信息
	 */
	public String getTag() {
		return m_strTag;
	}

	/**
	 * 子按钮是否是选择框组。
	 * <p>
	 * 创建日期：(2001-5-25 10:04:53)
	 * 
	 * @return 是否是选择框组
	 */
	public boolean isCheckboxGroup() {
		return m_isCheckboxGroup;
	}

	/**
	 * 子按钮组是否为互斥模式。在选择框模式下起作用。
	 * <p>
	 * 创建日期：(2001-5-25 10:07:10)
	 * 
	 * @return 是否为互斥模式
	 */
	public boolean isExclusiveMode() {
		return m_isExclusiveMode;
	}

	/**
	 * 是否有权限。
	 * <p>
	 * 创建日期：(2002-7-19 14:00:23)
	 * 
	 * @return 是否有权限
	 */
	public boolean isPower() {
		return m_bPower;
	}

	/**
	 * 是否进行权限控制。
	 * 
	 * @return 是否进行权限控制
	 */
	public boolean isPowerContrl() {
		return m_isPowerContrl;
	}

	/**
	 * 按钮是否被选中。在选择框模式下起作用。
	 * <p>
	 * 创建日期：(2001-5-25 10:26:46)
	 * 
	 * @return 是否被选中
	 */
	public boolean isSelected() {
		return m_isSelected;
	}

	/**
	 * 返回是否为分隔符。
	 * <p>
	 * 创建日期：(2001-6-12 18:19:33)
	 * 
	 * @return 是否为分隔符
	 */
	public boolean isSeperator() {
		return m_isSeperator;
	}

	/**
	 * 设置子按钮组是否是选择框子按钮。
	 * <p>
	 * 创建日期：(2001-5-25 10:03:59)
	 * 
	 * @param isCheckBoxGroup
	 *            是否是选择框子按钮
	 */
	public void setCheckboxGroup(boolean isCheckBoxGroup) {
		m_isCheckboxGroup = isCheckBoxGroup;
	}

	/**
	 * 设置按钮编码。
	 * <p>
	 * 创建日期：(2002-4-18 12:50:43)
	 * 
	 * @param newCode
	 *            按钮编码
	 */
	public void setCode(String newCode) {
		m_strCode = newCode;
	}

	/**
	 * 设置按钮所附带的数据对象。
	 * 
	 * @param newData
	 *            按钮所附带的数据对象
	 */
	public void setData(java.lang.Object newData) {
		m_data = newData;
	}

	/**
	 * 设置加速键显示名称。
	 * <p>
	 * 创建日期：(2002-4-15 18:51:36)
	 * 
	 * @param newDisplayHotkey
	 *            加速键显示名称
	 */
	public void setDisplayHotkey(String newDisplayHotkey) {
		m_strDisplayHotkey = newDisplayHotkey;
	}

	/**
	 * 设置选择的模式。在选择框模式下起作用。
	 * <p>
	 * 创建日期：(2001-5-25 10:05:46)
	 * 
	 * @param mode
	 *            选择模式 <code>false</code> - 共选模式；<br>
	 *            <code>true</code> - 互斥模式。
	 */
	public void setExclusiveMode(boolean mode) {
		m_isExclusiveMode = mode;
	}

	/**
	 * 设置加速键名称。
	 * 
	 * @param newHotKey
	 *            加速键名称
	 */

	public void setHotKey(String newHotKey) {
		m_strHotKey = newHotKey;
	}

	/**
	 * 设置修饰符。
	 * <p>
	 * 创建日期：(2003-11-10 14:02:10)
	 * 
	 * @param newModifiers
	 *            修饰符
	 */
	public void setModifiers(int newModifiers) {
		m_intModifiers = newModifiers;
	}

	/**
	 * 设置是否有权限。
	 * <p>
	 * 创建日期：(2002-7-19 14:00:23)
	 * 
	 * @param newPower
	 *            是否有权限
	 */
	public void setPower(boolean newPower) {
		m_bPower = newPower;
	}

	/**
	 * 设置是否进行权限控制。
	 * 
	 * @param isPowercontrl
	 *            是否进行权限控制
	 */
	public void setPowerContrl(boolean isPowercontrl) {
		m_isPowerContrl = isPowercontrl;
	}

	/**
	 * 设置按钮是否被选中。在选择框模式下起作用。
	 * <p>
	 * 创建日期：(2001-5-25 10:25:45)
	 * 
	 * @param isSelected
	 *            是否被选中
	 */
	public void setSelected(boolean isSelected) {
		m_isSelected = isSelected;
		if (getParent() != null && getParent().isCheckboxGroup() && getParent().isExclusiveMode()
				&& isSelected) {
			ButtonObject[] bos = getParent().getChildButtonGroup();
			int count = bos == null ? 0 : bos.length;
			for (int i = 0; i < count; i++) {
				ButtonObject child = bos[i];
				if (child.isSelected() && !this.equals(child)) {
					child.setSelected(false);
				}
			}
		}
	}

	/**
	 * 设置是否为分隔符。
	 * <p>
	 * 创建日期：(2001-6-12 18:18:34)
	 * 
	 * @param isSeperator
	 *            是否为分隔符
	 */
	public void setSeperator(boolean isSeperator) {
		m_isSeperator = isSeperator;
	}

	/**
	 * 设置内部标识信息。
	 * <p>
	 * 创建日期：(2001-5-25 10:32:00)
	 * 
	 * @param tag
	 *            标识信息
	 */
	public void setTag(String tag) {
		m_strTag = tag;
	}

	/**
	 * 设置是否为组合功能按钮。zsb+
	 * 
	 * @param bFlag
	 */
	public void setUnionFuncFlag(boolean bFlag) {
		bUnionFunc = bFlag;
	}

	/**
	 * 是否为组合功能按钮。zsb+
	 * 
	 * @return 是否为组合功能按钮
	 */
	public boolean isUnionFunc() {
		return bUnionFunc;
	}

	/**
	 * 增加组合功能按钮对象。zsb+
	 * 
	 * @param btnobj
	 *            组合功能按钮对象
	 * @deprecated since 5.0. please use {@link #addUnionFuncBtn(ButtonObject)}
	 *             instead.
	 */
	public void addUnionfuncbtn(ButtonObject btnobj) {
		addUnionFuncBtn(btnobj);
	}

	/**
	 * 增加组合功能按钮对象。zsb+
	 * 
	 * @param btnobj
	 *            组合功能按钮对象
	 */
	public void addUnionFuncBtn(ButtonObject btnobj) {
		if (alUnionFuncbtn == null) {
			alUnionFuncbtn = new ArrayList();
		}
		alUnionFuncbtn.add(btnobj);
	}

	/**
	 * 获得所有与此按钮相关联的所有组合功能按钮。
	 * 
	 * @return 组合功能按钮列表
	 * @deprecated since 5.0. please use {@link #getUnionFuncBtns()} instead.
	 */
	public ArrayList getUnionfuncbtn() {
		return alUnionFuncbtn;
	}

	/**
	 * 获得所有与此按钮相关联的所有组合功能按钮。
	 * 
	 * @return 组合功能按钮列表
	 */
	public ArrayList getUnionFuncBtns() {
		return alUnionFuncbtn;
	}

	/**
	 * 显示按钮文字。
	 */
	public String toString() {
		return m_strName;
	}
}