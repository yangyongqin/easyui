package com.evangelsoft.easyui.tool;

import com.borland.dx.dataset.Column;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: æ¸œË</p>
 * @author —Ó”¿«’
 * @version 1.0
 * @date 2017-1-14 …œŒÁ10:41:12
 */
public class ColumnsHelp {
	public static Column[] getColumns(String tabelName,Object[] columnName){
		Column[] columns=new Column[columnName.length];
		for(int i=0;i<columnName.length;i++){
			if(columnName[i] instanceof String) {
				String column=(String)columnName[i];
				columns[i]=new Column();
				if(column.indexOf(".")>0){
					columns[i].setModel(column);
				}else{
					columns[i].setModel(tabelName+"."+column);
				}
			}else if(columnName[i] instanceof String[]){
				String[] column=(String[])columnName[i];
				columns[i]=new Column();
				if(column[0].indexOf(".")>0){
					columns[i].setModel(column[0]);
				}else{
					columns[i].setModel(tabelName+"."+column[0]);
				}
				columns[i].setColumnName(column[1]);
				if(column.length>2){
					columns[i].setCaption(column[2]);
				}
				if(column.length>3){
					columns[i].setWidth(Integer.parseInt(column[3]));
				}
			}else if(columnName[i] instanceof Object[]) {
				Object[] obj=(Object[])columnName[i];
				columns[i]=new Column();
				columns[i].setColumnName((String)obj[0]);
				columns[i].setCaption((String)obj[1]);
				columns[i].setDataType((Integer)obj[2]);
				columns[i].setScale((Integer)obj[3]);
				columns[i].setPrecision(4);
				columns[i].setWidth(5);
			}
		}
		return columns;
	}
}
