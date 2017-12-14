package nc.ui.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * ��ť�����ṩ��ҵ��������ť��һ�������װ�����㼯�п��ơ�һ����ť����ɰ����Ӱ�ť���������ϰ�ť�����ִ�ж����ť�Ĺ��ܡ�
 * ����ť������Ӧ�Ŀ����ǰ�ť��Ҳ������ѡ��򣬻������Ƿֽ����
 * 
 * @author ���� 2000-5-31
 * @author ������ 2001-5-25
 * @since 1.0
 * @modifier zsb 2004-12-30 ����{@link #ButtonObject(String, String, String)}���캯����
 */
public class ButtonObject {
	/* ��ʾ���ơ� */
	private String m_strName = null;

	/* ��ʾ��Ϣ�� */
	private String m_strHint = null;

	/* Ȩ�޼��� */
	private int m_nPower = 0;

	/* ����ť�� */
	private ButtonObject m_boParent = null;

	/* �Ӱ�ť�ļ��ϡ� */
	private Vector m_vecChildren = null;

	/* ��ť�Ƿ���ã�������ʱ���ڽ�����һ����ҡ� */
	private boolean m_bEnabled = true;

	/* ��ť�Ƿ���ʾ�� */
	private boolean m_bVisible = true;

	/* �Ƿ���Ȩ�ޡ�û��Ȩ�޵İ�ť����ʾ�� */
	private boolean m_bPower = true;

	/* ���������ݶ��� */
	private Object m_data = null;

	/* һ���ڲ���ǩ��������¼<code>ButtonObject</code>��ʵ�ʰ�ť�Ķ�Ӧ��ϵ�� */
	private String m_internalTag = null;

	/* ��ť���η� */
	private int m_intModifiers = 0; // -1;

	/* �˰�ť���Ӱ�ť���Ƿ���ѡ��ť�� */
	private boolean m_isCheckboxGroup = false;

	/* �˰�ť���Ӱ�ť��ѡ��ģʽ�����⻹�Ƕ�ѡ�� */
	private boolean m_isExclusiveMode = true;

	/*
	 * licp+ 2004-6-1 �Ƿ��ܰ�ťȨ�޿��ƣ����Ϊtrue,�ͱ�ʾ�ð�ť�ܰ�ťȨ�޲������õĿ��ƣ�����Ͳ��ܰ�ťȨ�޲����Ŀ��ơ�
	 */
	private boolean m_isPowerContrl = true;

	/* ��ť�Ƿ�ѡ�С� */
	private boolean m_isSelected = false;

	/* �Ƿ�Ϊ�ָ�����㡣 */
	private boolean m_isSeperator = false;

	/* ��ť���� */
	private String m_strCode = null;

	/* ���ټ���ʾ�� */
	private String m_strDisplayHotkey = null;

	/*
	 * zhangyang+ ���ټ���
	 */
	private String m_strHotKey = null;

	/* �ڲ���ʶ��Ϣ�� */
	private String m_strTag = null;

	/* zsb+���Ƿ���Ϲ��ܼ���<code>true</code>��ʾ�������<code>ButtonObject</code>�Ĺ�����ɡ� */
	private boolean bUnionFunc = false;

	/* ��Ϲ��ܼ����Ӱ�ť�� */
	private ArrayList alUnionFuncbtn;

	/**
	 * ָ����ť���ֹ��찴ť���󡣰�ť��ʾ��Ϣ���밴ť����һ�¡�
	 * 
	 * @param strName ��ť�Ķ�������
	 * @deprecated û�и�code��ֵ�Ĵ����췽��
	 */
	public ButtonObject(String strName) {
		this(strName, strName);
	}

	/**
	 * ����һ���µİ�ť��������ص��Ӱ�ť��
	 * 
	 * @param name ��ť����
	 * @param children �Ӱ�ť����
	 * @deprecated û�и�code��ֵ�Ĵ����췽��
	 */
	public ButtonObject(String name, ButtonObject[] children) {
		this(name);
		setChildButtonGroup(children);
	}

	/**
	 * ָ����ť��������ʾ��Ϣ���찴ť����Ȩ�޼���Ĭ��Ϊ<code>0</code>��
	 * 
	 * @param name ��ť����
	 * @param hint ��ʾ����Ϣ
	 * @deprecated û�и�code��ֵ�Ĵ����췽��
	 */
	public ButtonObject(String name, String hint) {
		this(name, hint, 0);
	}

	/**
	 * ָ����ť���֣���ʾ��Ϣ��Ȩ�޼����찴ť����
	 * 
	 * @param name ��ť����
	 * @param hint ��ʾ��Ϣ
	 * @param power Ȩ�޼���
	 * @deprecated û�и�code��ֵ�Ĵ����췽��
	 */
	public ButtonObject(String name, String hint, int power) {
		m_strName = name;
		m_strHint = hint;
		m_nPower = power;
		m_strCode = name;
		m_internalTag = name + String.valueOf(Math.random());
	}

	/**
	 * ָ����ť���֣���ʾ��Ϣ����ť���빹�찴ť����
	 * <p>
	 * ���Ʊ� �������ڣ�(2004-12-30)
	 * 
	 * @param name ��ť����
	 * @param hint ��ʾ��Ϣ
	 * @param code ��ť����
	 */
	public ButtonObject(String name, String hint, String code) {
		this(name, hint, 0, code);
	}

	/**
	 * ָ����ť���֣���ʾ��Ϣ��Ȩ�޼��𣬰�ť���빹�찴ť����
	 * 
	 * @param name ��ť����
	 * @param hint ��ʾ��Ϣ
	 * @param power Ȩ�޼���
	 * @param code ��ť����
	 */
	public ButtonObject(String name, String hint, int power, String code) {
		m_strName = name;
		m_strHint = hint;
		m_nPower = power;
		m_strCode = code;
		m_internalTag = name + String.valueOf(Math.random());
	}

	/**
	 * ����һ���Ӱ�ť��
	 * 
	 * @param bo �Ӱ�ť����
	 */
	public void addChildButton(ButtonObject bo) {
		bo.setParent(this);
		getChildren().addElement(bo);
	}

	/**
	 * ����Ӱ�ť�������顣
	 * <p>
	 * �������ڣ�(2000-11-23 15:54:21)
	 * 
	 * @return �Ӱ�ť��������
	 */
	public ButtonObject[] getChildButtonGroup() {
		ButtonObject[] bos = new ButtonObject[getChildren().size()];
		return (ButtonObject[]) getChildren().toArray(bos);
	}

	/**
	 * ��������Ӱ�ť��������
	 * 
	 * @return û���Ӱ�ť�򷵻س���Ϊ0������������ֵ��Ϊ�ա�
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
	 * �����ʾ��Ϣ��
	 * 
	 * @return ��ʾ��Ϣ
	 */
	public String getHint() {
		return m_strHint;
	}

	/**
	 * ��ð�ť���֡�
	 * 
	 * @return ��ť����
	 */
	public String getName() {
		return m_strName;
	}

	/**
	 * ��ø���ť��
	 * 
	 * @return ����ť����
	 */
	public ButtonObject getParent() {
		return m_boParent;
	}

	/**
	 * ���Ȩ�޼���
	 * 
	 * @return �޼���
	 */
	public int getPower() {
		return m_nPower;
	}

	/**
	 * ��ӡ�쳣ջ��
	 * 
	 * @param exception
	 *            �쳣
	 */
	private static void handleException(Throwable exception) {
		exception.printStackTrace(System.err);
	}

	/**
	 * ��ť�Ƿ���á�
	 * <p>
	 * �������ڣ�(1999-7-28 11:14:34)
	 * 
	 * @return �Ƿ����
	 */
	public boolean isEnabled() {
		return m_bEnabled;
	}

	/**
	 * ��ť�Ƿ���ʾ��
	 * <p>
	 * �������ڣ�(2000-11-10 11:02:56)
	 * 
	 * @return �Ƿ���ʾ
	 */
	public boolean isVisible() {
		return m_bVisible;
	}

	/**
	 * ɾ�����е��Ӱ�ť��
	 * <p>
	 * �������ڣ�(2000-11-23 15:48:51)
	 */
	public void removeAllChildren() {
		getChildren().removeAllElements();
	}

	/**
	 * ɾ��ָ���Ӱ�ť��
	 * 
	 * @param bo
	 *            ��ɾ�����Ӱ�ť����
	 */
	public void removeChildButton(ButtonObject bo) {
		getChildren().remove(bo);
	}

	/**
	 * �����Ӱ�ť�� 
	 * 
	 * @param bos �Ӱ�ť��������
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
	 * ���ð�ť�Ƿ���á�
	 * <p>
	 * �������ڣ�(1999-7-28 11:13:57)
	 * 
	 * @param b
	 *            ��ť�Ƿ����
	 */
	public void setEnabled(boolean b) {
		m_bEnabled = b;
	}

	/**
	 * ���ð�ť��ʾ��Ϣ��
	 * 
	 * @param strHint
	 *            ��ť��ʾ
	 */
	public void setHint(String strHint) {
		m_strHint = strHint;
	}

	/**
	 * ���ð�ť���֡�
	 * 
	 * @param newvalue
	 *            ��ť����
	 */
	public void setName(String newvalue) {
		m_strName = newvalue;
	}

	/**
	 * ���ø���ť����
	 * 
	 * @param newvalue
	 *            ����ť����
	 */
	public void setParent(ButtonObject newvalue) {
		m_boParent = newvalue;
	}

	/**
	 * ����Ȩ�޼���
	 * 
	 * @param nPower
	 *            Ȩ�޼���
	 */
	void setPower(int nPower) {
		m_nPower = nPower;
	}

	/**
	 * ���ð�ť�Ŀɼ��ԡ�
	 * <p>
	 * �������ڣ�(2000-11-10 11:02:27)
	 * 
	 * @param visi
	 *            ��ť�Ŀɼ���
	 */
	public void setVisible(boolean visi) {
		m_bVisible = visi;
	}

	/**
	 * ���һ���Ӱ�ť��
	 * 
	 * @param aryBtns �Ӱ�ť��������
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
	 * �����Ӱ�ť�ĸ�����
	 * <p>
	 * �������ڣ�(2001-6-3 10:42:30)
	 * 
	 * @return �Ӱ�ť�ĸ���
	 */
	public int getChildCount() {
		if (m_vecChildren == null)
			return 0;
		else
			return getChildren().size();
	}

	/**
	 * ���ذ�ť���롣
	 * <P>
	 * �������ڣ�(2002-4-18 12:50:43)
	 * 
	 * @return ��ť����
	 */
	public String getCode() {
		return m_strCode;
	}

	/**
	 * ���ظ��������ݶ���
	 * <p>
	 * �������ڣ�(2002-6-3 10:14:59)
	 * 
	 * @return java.lang.Object
	 */
	public Object getData() {
		return m_data;
	}

	/**
	 * ���ؼ��ټ�����ʾ����
	 * <p>
	 * �������ڣ�(2002-4-15 18:51:36)
	 * 
	 * @return boolean
	 */
	public String getDisplayHotkey() {
		return m_strDisplayHotkey;
	}

	/**
	 * ���ؼ��ټ����ơ�
	 * <p>
	 * �������ڣ�(2002-4-15 17:21:59)
	 * 
	 * @return java.lang.String
	 */
	public String getHotKey() {
		return m_strHotKey;
	}

	/**
	 * �����ڲ���ʶ��
	 * <p>
	 * �������ڣ�(2001-6-3 9:55:04)
	 * 
	 * @return �ڲ���ʶ
	 */
	public String getInternalTag() {
		return m_internalTag;
	}

	/**
	 * ���ذ�ť���η���
	 * <p>
	 * �������ڣ�(2003-11-10 14:02:10)
	 * 
	 * @return ��ť���η�
	 */
	public int getModifiers() {
		return m_intModifiers;
	}

	/**
	 * ����Ӱ�ť����ѡ���ť���򷵻ر�ѡ�е��Ӱ�ť������Ϊ�ա�
	 * <p>
	 * �������ڣ�(2001-6-23 18:22:33)
	 * 
	 * @return ��ѡ�е��Ӱ�ť����
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
	 * �����ڲ���ʶ��Ϣ��
	 * <p>
	 * �������ڣ�(2001-5-25 10:31:01)
	 * 
	 * @return �ڲ���ʶ��Ϣ
	 */
	public String getTag() {
		return m_strTag;
	}

	/**
	 * �Ӱ�ť�Ƿ���ѡ����顣
	 * <p>
	 * �������ڣ�(2001-5-25 10:04:53)
	 * 
	 * @return �Ƿ���ѡ�����
	 */
	public boolean isCheckboxGroup() {
		return m_isCheckboxGroup;
	}

	/**
	 * �Ӱ�ť���Ƿ�Ϊ����ģʽ����ѡ���ģʽ�������á�
	 * <p>
	 * �������ڣ�(2001-5-25 10:07:10)
	 * 
	 * @return �Ƿ�Ϊ����ģʽ
	 */
	public boolean isExclusiveMode() {
		return m_isExclusiveMode;
	}

	/**
	 * �Ƿ���Ȩ�ޡ�
	 * <p>
	 * �������ڣ�(2002-7-19 14:00:23)
	 * 
	 * @return �Ƿ���Ȩ��
	 */
	public boolean isPower() {
		return m_bPower;
	}

	/**
	 * �Ƿ����Ȩ�޿��ơ�
	 * 
	 * @return �Ƿ����Ȩ�޿���
	 */
	public boolean isPowerContrl() {
		return m_isPowerContrl;
	}

	/**
	 * ��ť�Ƿ�ѡ�С���ѡ���ģʽ�������á�
	 * <p>
	 * �������ڣ�(2001-5-25 10:26:46)
	 * 
	 * @return �Ƿ�ѡ��
	 */
	public boolean isSelected() {
		return m_isSelected;
	}

	/**
	 * �����Ƿ�Ϊ�ָ�����
	 * <p>
	 * �������ڣ�(2001-6-12 18:19:33)
	 * 
	 * @return �Ƿ�Ϊ�ָ���
	 */
	public boolean isSeperator() {
		return m_isSeperator;
	}

	/**
	 * �����Ӱ�ť���Ƿ���ѡ����Ӱ�ť��
	 * <p>
	 * �������ڣ�(2001-5-25 10:03:59)
	 * 
	 * @param isCheckBoxGroup
	 *            �Ƿ���ѡ����Ӱ�ť
	 */
	public void setCheckboxGroup(boolean isCheckBoxGroup) {
		m_isCheckboxGroup = isCheckBoxGroup;
	}

	/**
	 * ���ð�ť���롣
	 * <p>
	 * �������ڣ�(2002-4-18 12:50:43)
	 * 
	 * @param newCode
	 *            ��ť����
	 */
	public void setCode(String newCode) {
		m_strCode = newCode;
	}

	/**
	 * ���ð�ť�����������ݶ���
	 * 
	 * @param newData
	 *            ��ť�����������ݶ���
	 */
	public void setData(java.lang.Object newData) {
		m_data = newData;
	}

	/**
	 * ���ü��ټ���ʾ���ơ�
	 * <p>
	 * �������ڣ�(2002-4-15 18:51:36)
	 * 
	 * @param newDisplayHotkey
	 *            ���ټ���ʾ����
	 */
	public void setDisplayHotkey(String newDisplayHotkey) {
		m_strDisplayHotkey = newDisplayHotkey;
	}

	/**
	 * ����ѡ���ģʽ����ѡ���ģʽ�������á�
	 * <p>
	 * �������ڣ�(2001-5-25 10:05:46)
	 * 
	 * @param mode
	 *            ѡ��ģʽ <code>false</code> - ��ѡģʽ��<br>
	 *            <code>true</code> - ����ģʽ��
	 */
	public void setExclusiveMode(boolean mode) {
		m_isExclusiveMode = mode;
	}

	/**
	 * ���ü��ټ����ơ�
	 * 
	 * @param newHotKey
	 *            ���ټ�����
	 */

	public void setHotKey(String newHotKey) {
		m_strHotKey = newHotKey;
	}

	/**
	 * �������η���
	 * <p>
	 * �������ڣ�(2003-11-10 14:02:10)
	 * 
	 * @param newModifiers
	 *            ���η�
	 */
	public void setModifiers(int newModifiers) {
		m_intModifiers = newModifiers;
	}

	/**
	 * �����Ƿ���Ȩ�ޡ�
	 * <p>
	 * �������ڣ�(2002-7-19 14:00:23)
	 * 
	 * @param newPower
	 *            �Ƿ���Ȩ��
	 */
	public void setPower(boolean newPower) {
		m_bPower = newPower;
	}

	/**
	 * �����Ƿ����Ȩ�޿��ơ�
	 * 
	 * @param isPowercontrl
	 *            �Ƿ����Ȩ�޿���
	 */
	public void setPowerContrl(boolean isPowercontrl) {
		m_isPowerContrl = isPowercontrl;
	}

	/**
	 * ���ð�ť�Ƿ�ѡ�С���ѡ���ģʽ�������á�
	 * <p>
	 * �������ڣ�(2001-5-25 10:25:45)
	 * 
	 * @param isSelected
	 *            �Ƿ�ѡ��
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
	 * �����Ƿ�Ϊ�ָ�����
	 * <p>
	 * �������ڣ�(2001-6-12 18:18:34)
	 * 
	 * @param isSeperator
	 *            �Ƿ�Ϊ�ָ���
	 */
	public void setSeperator(boolean isSeperator) {
		m_isSeperator = isSeperator;
	}

	/**
	 * �����ڲ���ʶ��Ϣ��
	 * <p>
	 * �������ڣ�(2001-5-25 10:32:00)
	 * 
	 * @param tag
	 *            ��ʶ��Ϣ
	 */
	public void setTag(String tag) {
		m_strTag = tag;
	}

	/**
	 * �����Ƿ�Ϊ��Ϲ��ܰ�ť��zsb+
	 * 
	 * @param bFlag
	 */
	public void setUnionFuncFlag(boolean bFlag) {
		bUnionFunc = bFlag;
	}

	/**
	 * �Ƿ�Ϊ��Ϲ��ܰ�ť��zsb+
	 * 
	 * @return �Ƿ�Ϊ��Ϲ��ܰ�ť
	 */
	public boolean isUnionFunc() {
		return bUnionFunc;
	}

	/**
	 * ������Ϲ��ܰ�ť����zsb+
	 * 
	 * @param btnobj
	 *            ��Ϲ��ܰ�ť����
	 * @deprecated since 5.0. please use {@link #addUnionFuncBtn(ButtonObject)}
	 *             instead.
	 */
	public void addUnionfuncbtn(ButtonObject btnobj) {
		addUnionFuncBtn(btnobj);
	}

	/**
	 * ������Ϲ��ܰ�ť����zsb+
	 * 
	 * @param btnobj
	 *            ��Ϲ��ܰ�ť����
	 */
	public void addUnionFuncBtn(ButtonObject btnobj) {
		if (alUnionFuncbtn == null) {
			alUnionFuncbtn = new ArrayList();
		}
		alUnionFuncbtn.add(btnobj);
	}

	/**
	 * ���������˰�ť�������������Ϲ��ܰ�ť��
	 * 
	 * @return ��Ϲ��ܰ�ť�б�
	 * @deprecated since 5.0. please use {@link #getUnionFuncBtns()} instead.
	 */
	public ArrayList getUnionfuncbtn() {
		return alUnionFuncbtn;
	}

	/**
	 * ���������˰�ť�������������Ϲ��ܰ�ť��
	 * 
	 * @return ��Ϲ��ܰ�ť�б�
	 */
	public ArrayList getUnionFuncBtns() {
		return alUnionFuncbtn;
	}

	/**
	 * ��ʾ��ť���֡�
	 */
	public String toString() {
		return m_strName;
	}
}