package com.evangelsoft.easyui.template.client;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionValuePickable;
import com.evangelsoft.econnect.dataformat.DataType;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientutil.CodeTable;
import com.evangelsoft.workbench.clientutil.ParameterValueCoder;
import com.evangelsoft.workbench.clientutil.ParameterValuePicker;

public class ConditionItemsHelper
{
	public static void load(ArrayList<ConditionItem> paramArrayList,final Column paramColumn)
	{
		if ((paramColumn.getCalcType() != 0) && (paramColumn.getCalcType() != 3))
			return;
		Column localColumn1 = paramColumn;
		Column localColumn2 = null;
		if (paramColumn.getCalcType() == 3)
		{
			if ((paramColumn.getPickList().getDestinationColumns().length != 1) && (paramColumn.getPickList().getPickListColumns().length != 1))
				return;
			localColumn1 = paramColumn.getDataSet().getColumn(paramColumn.getPickList().getDestinationColumns()[0]);
			localColumn2 = paramColumn;
		}
		RecordFieldFormat localRecordFieldFormat = DataSetHelper.getRecordFieldFormat(localColumn1);
		if (localRecordFieldFormat == null)
			return;
		int i;
		switch (localRecordFieldFormat.getType())
		{
		case 21:
		case 22:
			i = 22;
			break;
		case 1:
			i = 1;
			break;
		case 11:
			i = 11;
			break;
		case 12:
			i = 12;
			break;
		case 13:
			i = 13;
			break;
		default:
			return;
		}
		com.evangelsoft.econnect.dataformat.CharacterCase localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.normal;
		if (i == 22)
			if (paramColumn.getCharacterCase() == com.borland.dx.dataset.CharacterCase.upperCase)
				localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.upperCase;
			else if (paramColumn.getCharacterCase() == com.borland.dx.dataset.CharacterCase.lowerCase)
				localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.lowerCase;
		ConditionItem localConditionItem = new ConditionItem(localColumn1.getColumnName(), paramColumn.getCaption(), i, localColumn1.getPrecision(), localColumn1.getScale(), localColumn1.getWidth(), localCharacterCase);
		Object localObject1;
		if (localColumn2 != null)
		{
			localObject1 = localColumn2.getPickList().getPickListDataSet();
			String str1 = localColumn2.getPickList().getLookupDisplayColumn();
			String str2 = localColumn2.getPickList().getPickListColumns()[0];
			HashMap<Object, String> localHashMap = new HashMap<Object, String>();
			((DataSet)localObject1).first();
			while (((DataSet)localObject1).inBounds())
			{
				Object localObject2;
				if (localConditionItem.dataType == 22)
					localObject2 = ((DataSet)localObject1).getString(str2);
				else if (localConditionItem.dataType == 1)
					localObject2 = ((DataSet)localObject1).getBigDecimal(str2);
				else if (localConditionItem.dataType == 11)
					localObject2 = ((DataSet)localObject1).getBigDecimal(str2);
				else if (localConditionItem.dataType == 12)
					localObject2 = ((DataSet)localObject1).getBigDecimal(str2);
				else if (localConditionItem.dataType == 13)
					localObject2 = ((DataSet)localObject1).getBigDecimal(str2);
				else
					localObject2 = null;
				if (localObject2 != null)
					localHashMap.put(localObject2, ((DataSet)localObject1).getString(str1));
				((DataSet)localObject1).next();
			}
			localConditionItem.codeTable = localHashMap;
		}
		if (paramColumn.getColumnCustomEditListener() != null)
		{
			localConditionItem.picker = new ConditionValuePickable()
			{
				public boolean pick(ConditionItem paramConditionItem, VariantHolder<Object> paramVariantHolder)
				{
					Variant localVariant = paramColumn.getColumnCustomEditListener().customEdit(paramColumn.getDataSet(), paramColumn);
					if (localVariant != null)
					{
						paramVariantHolder.value = localVariant.getAsObject();
						return true;
					}
					return false;
				}
			};
		}
		paramArrayList.add(localConditionItem);
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, DataSet paramDataSet)
	{
		load(paramArrayList, paramDataSet, null);
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, DataSet paramDataSet, String[] paramArrayOfString)
	{
		for (Column localColumn : paramDataSet.getColumns())
		{
			if ((localColumn.getVisible() == 0) || ((localColumn.getCalcType() != 0) && (localColumn.getCalcType() != 3)))
				continue;
			if (paramArrayOfString != null)
			{
				int k = 0;
				for (String str : paramArrayOfString)
				{
					if (!str.equals(localColumn.getColumnName()))
						continue;
					k = 1;
					break;
				}
				if (k != 0)
					continue;
			}
			load(paramArrayList, localColumn);
		}
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, Record paramRecord, Container paramContainer)
	{
		int i;
		switch (DataType.getType(paramRecord.getField("DATA_TYPE").getString()))
		{
		case 21:
		case 22:
			i = 22;
			break;
		case 1:
			i = 1;
			break;
		case 11:
			i = 11;
			break;
		case 12:
			i = 12;
			break;
		case 13:
			i = 13;
			break;
		default:
			return;
		}
		com.evangelsoft.econnect.dataformat.CharacterCase localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.normal;
		String str1;
		if (i == 22)
		{
			str1 = paramRecord.getField("CHAR_CASE").getString();
			if (str1.equals("U"))
				localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.upperCase;
			else if (str1.equals("L"))
				localCharacterCase = com.evangelsoft.econnect.dataformat.CharacterCase.lowerCase;
		}
		if (paramRecord.getField("ENT_ID") != null)
			str1 = paramRecord.getField("ENT_ID").getString() + "." + paramRecord.getField("FLD_ID").getString();
		else if (paramRecord.getField("EXPRESSION") != null)
			str1 = paramRecord.getField("EXPRESSION").getString();
		else
			return;
		ConditionItem localConditionItem = new ConditionItem(str1, paramRecord.getField("FLD_NAME").getString(), i, paramRecord.getField("DATA_LEN").getInt(), paramRecord.getField("DATA_DEC").getInt(), paramRecord.getField("WIDTH").getInt(), localCharacterCase);
		String str2;
		if (paramRecord.getField("CODE_TBL") != null)
		{
			str2 = paramRecord.getField("CODE_TBL").getString();
			if (str2.length() > 0)
				bindCodeTable(localConditionItem, str2);
		}
		if (paramRecord.getField("EDT_FML") != null)
		{
			str2 = paramRecord.getField("EDT_FML").getString();
			if (str2.length() > 0)
				bindEditFormula(localConditionItem, str2, paramContainer);
		}
		paramArrayList.add(localConditionItem);
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, RecordSet paramRecordSet, Container paramContainer)
	{
		for (int i = 0; i < paramRecordSet.recordCount(); i++)
			load(paramArrayList, paramRecordSet.getRecord(i), paramContainer);
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, String paramString, Container paramContainer)
	{
		load(paramArrayList, paramString, paramContainer);
	}

	public static void load(ArrayList<ConditionItem> paramArrayList, String paramString1, Container paramContainer, String paramString2)
	{
		paramArrayList.clear();
		String[] arrayOfString1 = paramString1.split(";");
		for (String str1 : arrayOfString1)
		{
			str1 = str1.trim();
			if (str1.length() == 0)
				continue;
			String[] arrayOfString3 = str1.split(":");
			String str2 = "";
			String str3 = "";
			for (int k = 0; (k < arrayOfString3.length) && (k < 2); k++)
				if (k == 0)
				{
					str2 = arrayOfString3[k].trim();
				}
				else
				{
					if (k != 1)
						continue;
					str3 = arrayOfString3[k].trim();
				}
			if (str2.length() == 0)
				continue;
			if ((str2.indexOf('.') < 0) && (paramString2 != null))
				str2 = paramString2 + '.' + str2;
			try
			{
				load(paramArrayList, new Column(str2));
				ConditionItem localConditionItem = (ConditionItem)paramArrayList.get(paramArrayList.size() - 1);
				if (str3.length() <= 0)
					continue;
				bindEditFormula(localConditionItem, str3, paramContainer);
			}
			catch (Exception localException)
			{
				localException.printStackTrace();
			}
		}
	}

	public static void bindCodeTable(ConditionItem paramConditionItem, String paramString)
	{
		HashMap<Object, String> localHashMap = new HashMap<Object, String>();
		RecordSet localRecordSet = CodeTable.defaultCodeTable.getRecordSet(paramString);
		for (int i = 0; i < localRecordSet.recordCount(); i++)
			localHashMap.put(localRecordSet.getRecord(i).getField("CODE").getAsObject(), localRecordSet.getRecord(i).getField("DESCRIPTION").getString());
		paramConditionItem.codeTable = localHashMap;
	}

	public static void bindEditFormula(ConditionItem paramConditionItem, String paramString,final Container paramContainer)
	{
		Object localObject1 = "S";
		String str1 = "";
		int i = 0;
		String str2 = "";
		String[] localObject2=null;
		if (paramString.length() > 0)
		{
			localObject2 = paramString.split("&");
			for (int k = 0; k < localObject2.length; k++)
			{
				if (localObject2[k].length() == 0)
					continue;
				String str3 = localObject2[k].substring(0, 1).toUpperCase();
				if (str3.equals("L"))
				{
					localObject1 = str3;
					str1 = localObject2[k].substring(1);
				}
				else if (str3.equals("C"))
				{
					localObject1 = str3;
					str1 = localObject2[k].substring(1);
				}
				else
				{
					if (!str3.equals("P"))
						continue;
					i = 1;
					str2 = localObject2[k].substring(1);
				}
			}
		}
		String[] localObject3;
		Object[][] localObjectx;
		String[] arrayOfString1;
		if ((!((String)localObject1).equals("L")) && (((String)localObject1).equals("C")) && (((str1.length() > 1) && (str1.charAt(0) == '@')) || ((str1.length() > 2) && (str1.charAt(0) == '(') && (str1.charAt(str1.length() - 1) == ')'))))
		{
			if (str1.charAt(0) == '@')
			{
				localObjectx = ParameterValueCoder.defaultCoder.get(str1.substring(1));
			}
			else
			{
				localObject3 = str1.substring(1, str1.length() - 1).split(",");
				localObjectx = new Object[localObject3.length][2];
				for (int m = 0; m < localObject3.length; m++)
				{
					arrayOfString1 = localObject3[m].split("\\|");
					localObjectx[m][0] = arrayOfString1[0];
					localObjectx[m][1] = (arrayOfString1.length > 1 ? arrayOfString1[1] : arrayOfString1[0]);
				}
			}
			HashMap<Object, String>   localObjecty = new HashMap<Object, String>();
			for (int m = 0; m < localObject2.length; m++)
				((HashMap)localObjecty).put(localObjectx[m][0], localObjectx[m][1].toString());
			paramConditionItem.codeTable = ((HashMap)localObjecty);
		}
		if ((i != 0) && (str2.length() >= 2) && (str2.charAt(0) == '@'))
			try
		{
				int j = str2.indexOf('?');
				final  String  localObjectz = j > 1 ? str2.substring(1, j) : str2.substring(1);
				final  HashMap localHashMap = new HashMap();
				if ((j > 1) && (j < str2.length() - 1))
				{
					arrayOfString1 = str2.substring(j + 1).split("\\|");
					for (String str4 : arrayOfString1)
					{
						String[] arrayOfString3 = str4.split("=");
						if (arrayOfString3.length < 2)
							continue;
						localHashMap.put(arrayOfString3[0], arrayOfString3[1]);
					}
				}
				paramConditionItem.picker = new ConditionValuePickable()
				{
					public boolean pick(ConditionItem paramConditionItem, VariantHolder<Object> paramVariantHolder)
					{
						return ParameterValuePicker.defaultPicker.pick(paramContainer, localObjectz, localHashMap, true, paramVariantHolder);
					}
				};
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
	}

	public static void bindPicker(ArrayList<ConditionItem> paramArrayList, String[] paramArrayOfString, ConditionValuePickable paramConditionValuePickable)
	{
		Iterator localIterator = paramArrayList.iterator();
		while (localIterator.hasNext())
		{
			ConditionItem localConditionItem = (ConditionItem)localIterator.next();
			int i = 0;
			for (String str : paramArrayOfString)
			{
				if (!str.equals(localConditionItem.name))
					continue;
				i = 1;
				break;
			}
			if (i == 0)
				continue;
			localConditionItem.picker = paramConditionValuePickable;
		}
	}
}