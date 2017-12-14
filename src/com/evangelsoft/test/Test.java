package com.evangelsoft.test;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * ClassName: Test 
 * @Description: java文本框自动补全示例
 * 数据源来自于当前系统字体，用当前系统字体测试
 */
 
public class Test {                                                                                                                                                                 

	public static void main(String[] args) throws Exception {                                                                                                                       
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());                                                                                                        
		JFrame frame = new JFrame();                                                                                                                                                
		frame.setTitle("Auto Completion Test");                                                                                                                                     
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                                                                                       
		frame.setBounds(200, 200, 500, 400);                                                                                                                                        

		//获取系统中可用的字体的名字
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontName = e.getAvailableFontFamilyNames();
		for (int i = 0; i < fontName.length; i++) {                                                                                                                                  
			String item = fontName[i];                                                                                                                 
			items.add(item);                                                                                                                                                        
		}
		txtInput = new JTextField();                                                                                                                                     
		setupAutoComplete( );                                                                                                                                         
		txtInput.setColumns(30);                                                                                                                                                    
		frame.getContentPane().setLayout(new FlowLayout());                                                                                                                         
		frame.getContentPane().add(txtInput, BorderLayout.NORTH);                                                                                                                   
		frame.setVisible(true);                                                                                                                                                     
	}                                                                                                                                                                               

	private static boolean isAdjusting(JComboBox cbInput) {                                                                                                                         
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {                                                                                                         
			return (Boolean) cbInput.getClientProperty("is_adjusting");                                                                                                             
		}                                                                                                                                                                           
		return false;                                                                                                                                                               
	}                                                                                                                                                                               

	private static void setAdjusting(JComboBox cbInput, boolean adjusting) {                                                                                                        
		cbInput.putClientProperty("is_adjusting", adjusting);                                                                                                                       
	}                                                                                                                                                                               
	static JComboBox cbInput=null;   
	static JTextField txtInput=null;
	static ArrayList<String> items = new ArrayList<String>(); 
	public static void setupAutoComplete() {                                                                                
		final DefaultComboBoxModel model = new DefaultComboBoxModel();                                                                                                              
		cbInput = new JComboBox(model) {                                                                                                                            
			public Dimension getPreferredSize() {                                                                                                                                   
				return new Dimension(super.getPreferredSize().width, 0);                                                                                                            
			}                                                                                                                                                                       
		};                                                                                                                                                                          
		setAdjusting(cbInput, false);                                                                                                                                               
		for (String item : items) {                                                                                                                                                 
			model.addElement(item);                                                                                                                                                 
		}                                                                                                                                                                           
		cbInput.setSelectedItem(null);                                                                                                                                              
		cbInput.addActionListener(new ActionListener() {                                                                                                                            
			@Override                                                                                                                                                               
			public void actionPerformed(ActionEvent e) {                                                                                                                            
				if (!isAdjusting(cbInput)) {                                                                                                                                        
					if (cbInput.getSelectedItem() != null) {                                                                                                                        
						txtInput.setText(cbInput.getSelectedItem().toString());                                                                                                     
					}                                                                                                                                                               
				}                                                                                                                                                                   
			}                                                                                                                                                                       
		});                                                                                                                                                                         

		txtInput.addKeyListener(new KeyAdapter() {                                                                                                                                  

			@Override                                                                                                                                                               
			public void keyPressed(KeyEvent e) {                                                                                                                                    
				setAdjusting(cbInput, true);                                                                                                                                        
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {                                                                                                                          
					if (cbInput.isPopupVisible()) {                                                                                                                                 
						e.setKeyCode(KeyEvent.VK_ENTER);                                                                                                                            
					}                                                                                                                                                               
				}                                                                                                                                                                   
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {                                                
					e.setSource(cbInput);                                                                                                                                           
					cbInput.dispatchEvent(e);                                                                                                                                       
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
						if(cbInput.getSelectedItem()!=null){
							txtInput.setText(cbInput.getSelectedItem().toString());                                                                                                     
							cbInput.setPopupVisible(false);     
						}
					}                                                                                                                                                               
				}                                                                                                                                                                   
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {                                                                                                                         
					cbInput.setPopupVisible(false);                                                                                                                                 
				}                                                                                                                                                                   
				setAdjusting(cbInput, false);                                                                                                                                       
			}                                                                                                                                                                       
		});                                                                                                                                                                         
		txtInput.getDocument().addDocumentListener(new DocumentListener() {                                                                                                         
			public void insertUpdate(DocumentEvent e) {                                                                                                                             
				updateList();                                                                                                                                                       
			}                                                                                                                                                                       

			public void removeUpdate(DocumentEvent e) {                                                                                                                             
				updateList();                                                                                                                                                       
			}                                                                                                                                                                       

			public void changedUpdate(DocumentEvent e) {                                                                                                                            
				updateList();                                                                                                                                                       
			}                                                                                                                                                                       

			private void updateList() {                                                                                                                                             
				setAdjusting(cbInput, true);                                                                                                                                        
				model.removeAllElements();                                                                                                                                          
				String input = txtInput.getText();                                                                                                                                  
				if (!input.isEmpty()) {                                                                                                                                             
					for (String item : items) {                                                                                                                                     
						if (item.toLowerCase().startsWith(input.toLowerCase())) {                                                                                                   
							model.addElement(item);                                                                                                                                 
						}                                                                                                                                                           
					}                                                                                                                                                               
				}                                                                                                                                                                   
				cbInput.setPopupVisible(model.getSize() > 0);                                                                                                                       
				setAdjusting(cbInput, false);                                                                                                                                       
			}                                                                                                                                                                       
		});                                                                                                                                                                         
		txtInput.setLayout(new BorderLayout());                                                                                                                                     
		txtInput.add(cbInput, BorderLayout.SOUTH);                                                                                                                                  
	}                                                                                                                                                                               
}                                                                                                                                                                                   