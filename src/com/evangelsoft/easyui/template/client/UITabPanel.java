package com.evangelsoft.easyui.template.client;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/** * @author  杨永钦
 E-mail:
@date ：2016-3-20 下午03:06:36
@version 1.0   * @since    */
public class UITabPanel extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//存放面板SortedMap，有序
	private LinkedHashMap<String, PagePanel> panelMap=new LinkedHashMap<String, PagePanel>();
	private JTabbedPane tabPane;//选项卡
	public static  int DEFAULT=0;
	public static  int ALL_PANEL=1;
	public static  int FIRST_PANEL=2;
	public static  int LAST_PANEL=3;
	public static int OTHER=9;
	private int viewType=DEFAULT;
	private JComponent component;
	public  UITabPanel(){
		super();
//		panelMap=new LinkedHashMap<String, UIPanel>();
		this.setLayout(new BorderLayout());
//		this.add(getTabPane());
	}
	public LinkedHashMap<String, PagePanel> getPanelMap() {
		return panelMap;
	}
	public void setPanelMap(LinkedHashMap<String, PagePanel> panelMap) {
		this.panelMap = panelMap;
	}
	public JTabbedPane getTabPane() {
		if(tabPane==null){
			tabPane=new JTabbedPane();
		}
		return tabPane;
	}
	public PagePanel getComByIndex(int index){
		int i=0;
		for(String key:panelMap.keySet()){
			if(i==index){
				return panelMap.get(key);
			}
			i++;
		}
		return null;
	}
	public void setSelectedIndex(int index){
		if(tabPane!=null&&tabPane.getComponentCount()>0){
			this.tabPane.setSelectedIndex(index);
		}
	}
	public int getSelectedIndex(){
		if(tabPane!=null&&tabPane.getComponentCount()>0){
			return this.tabPane.getSelectedIndex();
		}
		return 0;
	}

	public void addTab(String key, String title, PagePanel uiPanel) {
		this.addTab(key,title, null, uiPanel, null);
	}
	public void addTab(String key,String title,Icon icon, PagePanel uiPanel) {
		this.addTab( key,title, icon, uiPanel, null);
	}
	public void addTab(String key,String title, Icon icon, PagePanel uiPanel, String tip) {
		if(uiPanel==null){
			return;
		}
		if(uiPanel.getPageId()==null){
			uiPanel.setPageId(key);
		}
		if(uiPanel.getPageName()==null){
			uiPanel.setPageName(title);
		}
		if(uiPanel.getIcon()==null){
			uiPanel.setIcon(icon);
		}
		if(uiPanel.getTip()==null){
			uiPanel.setTip(tip);
		}
		getTabPane().addTab(title, icon, uiPanel, tip);
		panelMap.put(key, uiPanel);
		updateUI();
	}
	public void remove(String key) {
		if(panelMap.size()>1){
			getTabPane().remove(getIndex(key));
		}else{
			this.remove(0);
		}
		panelMap.remove(key);
	}
	public int getIndex(String key){
		int index=0;
		if(key!=null&&key.trim().length()>0){
			for(String k:panelMap.keySet()){
				if(key.equals(k)){
					return index;
				}
				index++;
			}
		}
		return -1;
	}
//	@Override

	public void updateUI() {
		int index=this.getSelectedIndex();
		if(panelMap!=null){
			super.removeAll();//清除所有

			if(this.viewType==DEFAULT){
				if(panelMap.size()>1){
					getTabPane().add(this.getComByIndex(0), 0);
					getTabPane().setTitleAt(0, this.getComByIndex(0).getPageName());
					getTabPane().setToolTipTextAt(0, this.getComByIndex(0).getTip());
					getTabPane().setIconAt(0, this.getComByIndex(0).getIcon());
					this.add(getTabPane());
				}else if(panelMap.size()==1){
					//如果只有一个则取第一个
					this.add(this.getComByIndex(0));
				}
			}else if(this.viewType==ALL_PANEL){
				if(panelMap.size()>1){
					getTabPane().add(this.getComByIndex(0), 0);
					getTabPane().setTitleAt(0, this.getComByIndex(0).getPageName());
					getTabPane().setToolTipTextAt(0, this.getComByIndex(0).getTip());
					getTabPane().setIconAt(0, this.getComByIndex(0).getIcon());
					this.add(getTabPane());
				}else{
					//如果只有一个则取第一个
					this.add(this.getComByIndex(0));
				}
			}else if(this.viewType==FIRST_PANEL){
				this.add(this.getComByIndex(0));
			}else if(this.viewType==LAST_PANEL){
				this.add(this.getComByIndex(panelMap.size()-1));
			}else{
				this.add(component);
			}
		}
		this.setSelectedIndex(index);
		super.updateUI();
	}
	public synchronized void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		getTabPane().addMouseListener(l);
	}
	public JComponent getComponent() {
		return component;
	}
	public void setComponent(JComponent component) {
		this.component = component;
	}
	public int getViewType() {
		return viewType;
	}
	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public	int getUseComponentCount(){
		if(panelMap.size()>1){
			return panelMap.size();
		}else if(panelMap.size()==1){
			return this.getComByIndex(0).getComponentMap().size();
		}
		return 0;
	}
}
