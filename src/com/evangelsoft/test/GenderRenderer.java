package com.evangelsoft.test;
import java.awt.Component;  
import javax.swing.JComboBox;  
import javax.swing.JTable;  
import javax.swing.table.TableCellRenderer;  
   
public class GenderRenderer extends JComboBox implements TableCellRenderer{  
         private static final long serialVersionUID = -8624401777277852691L;  

	public GenderRenderer(){  
                   super();  
                   addItem("ÄÐ");  
                   addItem("Å®");  
         }  
         public Component getTableCellRendererComponent(JTable table, Object value,  
                            boolean isSelected, boolean hasFocus, int row, int column) {  
                   if(isSelected){  
                            setForeground(table.getForeground());  
                            super.setBackground(table.getBackground());  
                   }else{  
                            setForeground(table.getForeground());  
                            setBackground(table.getBackground());  
                   }  
                   boolean isMale =value==null?false: ((Boolean)value).booleanValue();  
                   setSelectedIndex(isMale? 0 : 1);  
                   return this;  
         }  
   
}  