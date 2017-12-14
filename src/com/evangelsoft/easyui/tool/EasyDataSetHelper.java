// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 2016/1/11 14:39:51
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3)

package com.evangelsoft.easyui.tool;

import java.awt.Component;
import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import com.borland.dbswing.JdbComboBox;
import com.borland.dbswing.JdbTable;
import com.borland.dx.dataset.AggDescriptor;
import com.borland.dx.dataset.AvgAggOperator;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.CountAggOperator;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.DataSetAware;
import com.borland.dx.dataset.DataSetView;
import com.borland.dx.dataset.MaxAggOperator;
import com.borland.dx.dataset.MinAggOperator;
import com.borland.dx.dataset.PickListDescriptor;
import com.borland.dx.dataset.ProviderHelp;
import com.borland.dx.dataset.ReadRow;
import com.borland.dx.dataset.ReadWriteRow;
import com.borland.dx.dataset.RowCountAggOperator;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.SumAggOperator;
import com.borland.dx.dataset.TdDataSet;
import com.borland.dx.dataset.Variant;
import com.evangelsoft.econnect.dataformat.CharacterCase;
import com.evangelsoft.econnect.dataformat.DataType;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordField;
import com.evangelsoft.econnect.dataformat.RecordFieldFormat;
import com.evangelsoft.econnect.dataformat.RecordFormat;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.swing.Editable;
import com.evangelsoft.workbench.clientdataset.DataSetHelper;
import com.evangelsoft.workbench.clientdataset.LoadCanceler;
import com.evangelsoft.workbench.clientutil.CodeTable;

// Referenced classes of package com.evangelsoft.workbench.clientdataset:
//            LoadCanceler

public class EasyDataSetHelper
{

	private static ResourceBundle A = ResourceBundle.getBundle((new StringBuilder(String.valueOf(DataSetHelper.class.getPackage().getName()))).append(".Res").toString());

	public EasyDataSetHelper()
	{
	}

	public static int fieldTypeToDataType(int i)
	{
		byte byte0;
		switch(i)
		{
		case 1: // '\001'
			byte0 = 10;
			break;

		case 11: // '\013'
			byte0 = 13;
			break;

		case 12: // '\f'
			byte0 = 14;
			break;

		case 13: // '\r'
			byte0 = 15;
			break;

		case 21: // '\025'
		case 22: // '\026'
		case 23: // '\027'
			byte0 = 16;
			break;

		case 31: // '\037'
		case 32: // ' '
		case 33: // '!'
			byte0 = 12;
			break;

		case 41: // ')'
			byte0 = 17;
			break;

		default:
			byte0 = 0;
			break;
		}
		return byte0;
	}

	public static com.borland.dx.dataset.CharacterCase characterCaseFieldToColumn(CharacterCase charactercase)
	{
		if(charactercase == CharacterCase.upperCase)
			return com.borland.dx.dataset.CharacterCase.upperCase;
		if(charactercase == CharacterCase.lowerCase)
			return com.borland.dx.dataset.CharacterCase.lowerCase;
		else
			return com.borland.dx.dataset.CharacterCase.normal;
	}

	public static int[] loadMetaFromRecordFormat(StorageDataSet storagedataset, RecordFormat recordformat)
	{
		boolean flag = storagedataset.getColumnCount() > 0;
		ArrayList arraylist = new ArrayList();
		ArrayList arraylist1 = recordformat.getFields();
		for(int i = 0; i < arraylist1.size(); i++)
		{
			int j = fieldTypeToDataType(((RecordFieldFormat)arraylist1.get(i)).getType());
			if(j != 0)
			{
				Column column = storagedataset.hasColumn(((RecordFieldFormat)arraylist1.get(i)).getName());
				String s = ((RecordFieldFormat)arraylist1.get(i)).getName();
				String s1 = ((RecordFieldFormat)arraylist1.get(i)).getLabel();
				if(s1 == null || s1.length() == 0)
					s1 = s;
				Column column1 = new Column(s, s1, j);
				column1.setCharacterCase(characterCaseFieldToColumn(((RecordFieldFormat)arraylist1.get(i)).getCharacterCase()));
				if(column == null)
				{
					column1.setPrecision(((RecordFieldFormat)arraylist1.get(i)).getLength());
					column1.setScale(((RecordFieldFormat)arraylist1.get(i)).getDecimal());
					column1.setWidth(((RecordFieldFormat)arraylist1.get(i)).getWidth());
				} else
				{
					column1.setPrecision(Math.min(((RecordFieldFormat)arraylist1.get(i)).getLength(), column.getPrecision()));
					column1.setScale(Math.min(((RecordFieldFormat)arraylist1.get(i)).getDecimal(), column.getScale()));
					column1.setWidth(column.getWidth());
				}
				if(column == null && flag)
					column1.setVisible(0);
				arraylist.add(column1);
			}
		}

		return ProviderHelp.initData(storagedataset, (Column[])arraylist.toArray(new Column[0]), true, true);
	}

	public static void loadFromRecordSet(StorageDataSet storagedataset, RecordSet recordset, boolean flag, LoadCanceler loadcanceler)
	{
		loadFromRecordSet(storagedataset, recordset, flag, loadcanceler, 0);
	}

	public static void loadFromRecordSet(StorageDataSet paramStorageDataSet, RecordSet paramRecordSet, boolean paramBoolean, LoadCanceler paramLoadCanceler, int paramInt)
	{
		if (paramLoadCanceler == null)
			paramLoadCanceler = new LoadCanceler()
		{
			boolean F = false;

			public void clearCancelled()
			{
				this.F = false;
			}

			public boolean isCancelled()
			{
				return this.F;
			}

			public void cancelLoad()
			{
				this.F = true;
			}
		};
		paramLoadCanceler.clearCancelled();
		int[] arrayOfInt1;


		if (paramBoolean)
		{
			arrayOfInt1 = loadMetaFromRecordFormat(paramStorageDataSet, paramRecordSet.getFormat());
		}
		else
		{
			arrayOfInt1 = new int[paramRecordSet.getFormat().fieldCount()];
			Object localObject1 = paramRecordSet.getFormat().getFields();
			for (int i = 0; i < ((ArrayList)localObject1).size(); i++)
			{
				Object localObject2= paramStorageDataSet.hasColumn(((RecordFieldFormat)((ArrayList)localObject1).get(i)).getName());
				if (localObject2 != null)
					arrayOfInt1[i] = ((Column)localObject2).getOrdinal();
				else
					arrayOfInt1[i] = -1;
			}
		}
		label395:
			try
		{
				Variant[] localObject1 = paramStorageDataSet.startLoading(paramLoadCanceler, 8, false);
				int[] arrayOfInt2 = new int[paramStorageDataSet.getColumnCount()];
				int[]   localObject2 = new int[paramStorageDataSet.getColumnCount()];
				int[] arrayOfInt3 = new int[paramStorageDataSet.getColumnCount()];
				for (int j = 0; j < paramStorageDataSet.getColumnCount(); j++)
				{
					arrayOfInt2[j] = paramStorageDataSet.getColumn(j).getDataType();
					localObject2[j] = paramStorageDataSet.getColumn(j).getPrecision();
					arrayOfInt3[j] = paramStorageDataSet.getColumn(j).getScale();
				}
				int	j = paramRecordSet.recordCount();
				if (paramInt > 0)
					j = Math.min(j, paramInt);
				for (int k = 0; k < j; k++)
				{
					for (int m = 0; m < arrayOfInt1.length; m++)
						if (arrayOfInt1[m] >= 0)
						{
							int n = arrayOfInt1[m];
							RecordField localRecordField = (RecordField)paramRecordSet.getRecord(k).getFields().get(m);
							loadValueFromField(arrayOfInt2[n], localObject2[n], arrayOfInt3[n], localObject1[n], localRecordField);
						}
					paramStorageDataSet.loadRow();
					if ((k % 100 == 0) && ((paramLoadCanceler.isCancelled()) || (Thread.interrupted())))
					{
						if (paramLoadCanceler.isCancelled())
							break;
						paramLoadCanceler.cancelLoad();
						break label395;
					}
				}
		}
		finally
		{
			paramStorageDataSet.endLoading();
			if (!paramStorageDataSet.isOpen())
				paramStorageDataSet.open();
		}
	}


	public static void loadFromRecordSet(StorageDataSet storagedataset, RecordSet recordset)
	{
		loadFromRecordSet(storagedataset, recordset, true, null);
	}

	public static void loadValueFromField(int i, Variant variant, RecordField recordfield)
	{
		loadValueFromField(i, 0x7fffffff, 0x7fffffff, variant, recordfield);
	}

	public static void loadValueFromField(int i, int j, int k, Variant variant, RecordField recordfield)
	{
		if(recordfield.isNull())
		{
			variant.setAssignedNull();
			return;
		}
		if(i == 0)
		{
			i = variant.getType();
			if(i == 0)
				switch(recordfield.getFormat().getType())
				{
				case 1: // '\001'
					i = 10;
					break;

				case 11: // '\013'
					i = 13;
					break;

				case 12: // '\f'
					i = 14;
					break;

				case 13: // '\r'
					i = 15;
					break;

				case 21: // '\025'
				case 22: // '\026'
				case 23: // '\027'
					i = 16;
					break;

				case 31: // '\037'
				case 32: // ' '
				case 33: // '!'
					i = 12;
					break;

				case 41: // ')'
					i = 17;
					break;
				}
		}
		if(j == 0x7fffffff)
			j = recordfield.getFormat().getLength();
		if(k == 0x7fffffff)
			k = recordfield.getFormat().getDecimal();
		switch(i)
		{
		case 8: // '\b'
		case 9: // '\t'
		case 11: // '\013'
		default:
			break;

		case 2: // '\002'
			variant.setByte(recordfield.getNumber().byteValue());
			break;

		case 3: // '\003'
			variant.setShort(recordfield.getNumber().shortValue());
			break;

		case 4: // '\004'
			variant.setInt(recordfield.getNumber().intValue());
			break;

		case 5: // '\005'
			variant.setLong(recordfield.getNumber().longValue());
			break;

		case 6: // '\006'
			variant.setFloat(recordfield.getNumber().floatValue());
			break;

		case 7: // '\007'
			variant.setDouble(recordfield.getNumber().doubleValue());
			break;

		case 10: // '\n'
			if(k == recordfield.getFormat().getDecimal())
				variant.setBigDecimal(recordfield.getNumber());
			else
				variant.setBigDecimal(recordfield.getNumber().setScale(k, RoundingMode.HALF_UP));
			break;

		case 13: // '\r'
			variant.setDate(new Date(recordfield.getDate().getTime()));
			break;

		case 14: // '\016'
			variant.setTime(new Time(recordfield.getTime().getTime()));
			break;

		case 15: // '\017'
			variant.setTimestamp(new Timestamp(recordfield.getTimestamp().getTime()));
			break;

		case 16: // '\020'
			variant.setString(recordfield.getString());
			break;

		case 12: // '\f'
			variant.setInputStream(new ByteArrayInputStream(recordfield.getBinary()));
			break;

		case 17: // '\021'
			variant.setObject(recordfield.getObject());
			break;
		}
	}

	public static Variant getValueFromField(int i, RecordField recordfield)
	{
		Variant variant = new Variant();
		loadValueFromField(i, variant, recordfield);
		return variant;
	}
	public static void loadRowFromRecord(ReadWriteRow paramReadWriteRow, Record paramRecord, boolean paramBoolean, HashMap<String, String> paramHashMap){

	}

	/**
	 * @param paramReadWriteRow 数据仓库
	 * @param paramRecord  数据记录
	 * @param paramBoolean
	 * @param paramHashMap
	 * @param ignoreSet
	 */
	public static void loadRowFromRecord(ReadWriteRow paramReadWriteRow, Record paramRecord, boolean paramBoolean, HashMap<String, String> paramHashMap,List<String> ignoreList)
	{
		boolean bool1 = true;
		boolean bool2 = true;
		boolean bool3 = true;
		boolean bool4 = false;
		Object localObject1;
		if ((paramReadWriteRow instanceof DataSet))
		{
			localObject1 = (DataSet)paramReadWriteRow;
			bool4 = ((DataSet)localObject1).isEditing();
			bool2 = ((DataSet)localObject1).isEnableInsert();
			bool1 = ((DataSet)localObject1).isEditable();
			bool3 = ((DataSet)localObject1).isEnableUpdate();
			((DataSet)localObject1).setEditable(true);
			((DataSet)localObject1).setEnableInsert(true);
			((DataSet)localObject1).setEnableUpdate(true);
		}
		DataSet localDataSet;
		try
		{
			localObject1 = new Variant();
			for (Column localObject2 : paramReadWriteRow.getColumns())
			{
				String str1 = ((Column)localObject2).getColumnName();
				//
				if(ignoreList != null&&ignoreList.contains(str1)){
					continue;
				}
				String str2 = str1;
				if ((paramHashMap != null) && (paramHashMap.containsKey(str1)))
					str2 = (String)paramHashMap.get(str1);
				if ((str2 != null) && (paramRecord.getFormat().getField(str2) != null))
				{
					paramReadWriteRow.getVariant(str1, (Variant)localObject1);
					Variant localVariant = getValueFromField(((Column)localObject2).getDataType(), paramRecord.getField(str2));
					if ((((Variant)localObject1).getType() != localVariant.getType()) || (!((Variant)localObject1).equals(localVariant)))
						if (paramBoolean)
							paramReadWriteRow.loadValue(str1, localVariant);
						else
							paramReadWriteRow.setVariant(str1, localVariant);
				}
			}
			if ((paramReadWriteRow instanceof DataSet))
			{
				DataSet localObject2 = (DataSet)paramReadWriteRow;
				((DataSet)localObject2).setEditable(true);
				((DataSet)localObject2).setEnableUpdate(true);
				((DataSet)localObject2).setEnableInsert(true);
				if (!bool4)
					((DataSet)localObject2).post(paramBoolean);
			}
		}
		finally
		{
			if ((paramReadWriteRow instanceof DataSet))
			{
				localDataSet = (DataSet)paramReadWriteRow;
				localDataSet.setEditable(bool1);
				localDataSet.setEnableUpdate(bool3);
				localDataSet.setEnableInsert(bool2);
			}
		}
	}

	public static RecordFieldFormat getRecordFieldFormat(Column column)
	{
		RecordFieldFormat recordfieldformat = null;
		String s = column.getColumnName();
		String s1 = column.getCaption();
		switch(column.getDataType())
		{
		case 2: // '\002'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 3, 0);
			break;

		case 3: // '\003'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 5, 0);
			break;

		case 4: // '\004'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 10, 0);
			break;

		case 5: // '\005'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 20, 0);
			break;

		case 6: // '\006'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 15, 4);
			break;

		case 7: // '\007'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, 20, 6);
			break;

		case 10: // '\n'
			recordfieldformat = new RecordFieldFormat(s, s1, 1, column.getPrecision(), column.getScale());
			break;

		case 15: // '\017'
			recordfieldformat = new RecordFieldFormat(s, s1, 13, 0, 0);
			break;

		case 13: // '\r'
			recordfieldformat = new RecordFieldFormat(s, s1, 11, 0, 0);
			break;

		case 14: // '\016'
			recordfieldformat = new RecordFieldFormat(s, s1, 12, 0, 0);
			break;

		case 16: // '\020'
			if(column.getPrecision() > 0)
				recordfieldformat = new RecordFieldFormat(s, s1, column.isFixedPrecision() ? 21 : 22, column.getPrecision(), 0);
			else
				recordfieldformat = new RecordFieldFormat(s, s1, 23, 0, 0);
			break;

		case 12: // '\f'
			if(column.getPrecision() > 0)
				recordfieldformat = new RecordFieldFormat(s, s1, column.isFixedPrecision() ? 31 : 32, column.getPrecision(), 0);
			else
				recordfieldformat = new RecordFieldFormat(s, s1, 33, 0, 0);
			break;

		case 17: // '\021'
			recordfieldformat = new RecordFieldFormat(s, s1, 41, 0, 0);
			break;

		case 8: // '\b'
		case 9: // '\t'
		case 11: // '\013'
		default:
			recordfieldformat = null;
			break;
		}
		return recordfieldformat;
	}

	public static void loadRowFromRecord(ReadWriteRow readwriterow, Record record, boolean flag)
	{
		loadRowFromRecord(readwriterow, record, flag, null);
	}

	public static void loadRowFromRecord(ReadWriteRow readwriterow, Record record)
	{
		loadRowFromRecord(readwriterow, record, false);
	}

	public static int[] saveMetaToRecordFormat(DataSet dataset, RecordFormat recordformat, boolean flag)
	{
		int ai[] = new int[dataset.getColumnCount()];
		recordformat.clearFields();
		for(int i = 0; i < ai.length; i++)
		{
			Column column = dataset.getColumn(i);
			RecordFieldFormat recordfieldformat;
			if(flag && !column.isResolvable())
				recordfieldformat = null;
			else
				recordfieldformat = getRecordFieldFormat(column);
			if(recordfieldformat != null)
			{
				recordformat.appendField(recordfieldformat);
				ai[i] = recordformat.fieldCount() - 1;
			} else
			{
				ai[i] = -1;
			}
		}

		return ai;
	}

	public static int[] saveMetaToRecordFormat(DataSet dataset, RecordFormat recordformat)
	{
		return saveMetaToRecordFormat(dataset, recordformat, false);
	}

	public static void saveToRecordSet(StorageDataSet paramStorageDataSet, RecordSet paramRecordSet, boolean paramBoolean, LoadCanceler paramLoadCanceler)
	{
		if (paramLoadCanceler == null)
			paramLoadCanceler = new LoadCanceler()
		{
			boolean E = false;

			public void clearCancelled()
			{
				this.E = false;
			}

			public boolean isCancelled()
			{
				return this.E;
			}

			public void cancelLoad()
			{
				this.E = true;
			}
		};
		paramLoadCanceler.clearCancelled();
		boolean bool = paramRecordSet.getTrace();
		paramRecordSet.setTrace(false);
		try
		{
			paramRecordSet.clear();
			int[] arrayOfInt;
			Object localObject1;
			if (paramBoolean)
			{
				arrayOfInt = saveMetaToRecordFormat(paramStorageDataSet, paramRecordSet.getFormat());
			}
			else
			{
				arrayOfInt = new int[paramStorageDataSet.getColumnCount()];
				for (int i = 0; i < arrayOfInt.length; i++)
				{
					localObject1 = paramRecordSet.getFormat().getField(paramStorageDataSet.getColumn(i).getColumnName());
					if (localObject1 != null)
						arrayOfInt[i] = ((RecordFieldFormat)localObject1).getOrdinal();
					else
						arrayOfInt[i] = -1;
				}
			}
			for (int i = 0; i < paramStorageDataSet.getRowCount(); i++)
			{
				localObject1 = paramRecordSet.append();
				for (int j = 0; j < arrayOfInt.length; j++)
					if (arrayOfInt[j] >= 0)
					{
						Variant localVariant = new Variant();
						paramStorageDataSet.getVariant(j, i, localVariant);
						saveValueToField(localVariant, (RecordField)((Record)localObject1).getFields().get(arrayOfInt[j]));
					}
				if (paramLoadCanceler.isCancelled())
					break;
			}
		}
		finally
		{
			paramRecordSet.setTrace(bool);
		}
	}

	public static void saveValueToField(Variant variant, RecordField recordfield)
	{
		if(variant.isNull())
		{
			recordfield.clear();
			return;
		}
		switch(recordfield.getFormat().getType())
		{
		default:
			break;

		case 1: // '\001'
			switch(variant.getType())
			{
			case 10: // '\n'
				recordfield.setNumber(variant.getBigDecimal());
				break;

			case 7: // '\007'
				recordfield.setDouble(variant.getDouble());
				break;

			case 6: // '\006'
				recordfield.setFloat(variant.getFloat());
				break;

			case 4: // '\004'
				recordfield.setInt(variant.getInt());
				break;

			case 5: // '\005'
				recordfield.setLong(variant.getLong());
				break;

			case 3: // '\003'
				recordfield.setShort(variant.getShort());
				break;
			}
			break;

		case 11: // '\013'
			recordfield.setDate(variant.getDate());
			break;

		case 12: // '\f'
			recordfield.setTime(variant.getTime());
			break;

		case 13: // '\r'
			recordfield.setTimestamp(variant.getTimestamp());
			break;

		case 21: // '\025'
		case 22: // '\026'
		case 23: // '\027'
			recordfield.setString(variant.getString());
			break;

		case 31: // '\037'
		case 32: // ' '
		case 33: // '!'
			recordfield.setBinary(variant.getByteArray());
			break;

		case 41: // ')'
			recordfield.setObject(variant.getObject());
			break;
		}
	}

	public static void saveRowToRecord(ReadWriteRow readwriterow, Record record)
	{
		Variant variant = new Variant();
		Column acolumn[];
		int j = (acolumn = readwriterow.getColumns()).length;
		for(int i = 0; i < j; i++)
		{
			Column column = acolumn[i];
			if(record.getFormat().getField(column.getColumnName()) != null)
			{
				readwriterow.getVariant(column.getColumnName(), variant);
				saveValueToField(variant, record.getField(column.getColumnName()));
			}
		}

	}

	public static void saveToDeltaRecordSet(DataSet paramDataSet, DeltaRecordSet paramDeltaRecordSet, boolean paramBoolean1, boolean paramBoolean2)
	{
		RecordFormat localRecordFormat = new RecordFormat("@");
		saveMetaToRecordFormat(paramDataSet, localRecordFormat, true);
		paramDeltaRecordSet.clear(localRecordFormat);
		StorageDataSet localStorageDataSet = paramDataSet.getStorageDataSet();
		if (paramBoolean1)
			ProviderHelp.startResolution(localStorageDataSet, true);
		if (!localStorageDataSet.changesPending())
		{
			if ((paramBoolean1) && (paramBoolean2))
				ProviderHelp.endResolution(localStorageDataSet);
			return;
		}
		boolean bool1 = paramDeltaRecordSet.getTrace();
		paramDeltaRecordSet.setTrace(false);
		boolean bool2 = false;
		try
		{
			DataSetView localDataSetView1 = new DataSetView();
			DataSetView localDataSetView2 = new DataSetView();
			DataSetView localDataSetView3 = new DataSetView();
			localStorageDataSet.getDeletedRows(localDataSetView1);
			localStorageDataSet.getUpdatedRows(localDataSetView2);
			localStorageDataSet.getInsertedRows(localDataSetView3);
			DataRow localDataRow;
			int i;
			Object localObject1;
			if (localDataSetView1.getRowCount() > 0)
			{
				localDataRow = new DataRow(localDataSetView1);
				localDataSetView1.first();
				do
				{
					i = localDataSetView1.getStatus();
					if ((i & 0x4) == 0)
					{
						localObject1 = new Record(paramDeltaRecordSet.getFormat());
						localDataSetView1.getDataRow(localDataRow);
						saveRowToRecord(localDataRow, (Record)localObject1);
						paramDeltaRecordSet.append((int)localDataSetView1.getInternalRow(), 3, (Record)localObject1, null);
						if (paramBoolean1)
							ProviderHelp.markPendingStatus(localDataSetView1, true);
					}
				}
				while (localDataSetView1.next());
			}
			if (localDataSetView2.getRowCount() > 0)
			{
				localDataRow = new DataRow(localDataSetView2);
				localObject1 = new DataRow(localDataSetView2);
				localDataSetView2.first();
				do
				{
					i = localDataSetView2.getStatus();
					if ((i & 0x1) == 0)
					{
						Record localRecord1 = new Record(paramDeltaRecordSet.getFormat());
						Record localRecord2 = new Record(paramDeltaRecordSet.getFormat());
						localDataSetView2.getDataRow(localDataRow);
						saveRowToRecord(localDataRow, localRecord1);
						localDataSetView2.getStorageDataSet().getOriginalRow(localDataSetView2, (ReadWriteRow)localObject1);
						saveRowToRecord((ReadWriteRow)localObject1, localRecord2);
						paramDeltaRecordSet.append((int)localDataSetView2.getInternalRow(), 2, localRecord1, localRecord2);
						if (paramBoolean1)
							ProviderHelp.markPendingStatus(localDataSetView2, true);
					}
				}
				while (localDataSetView2.next());
			}
			if (localDataSetView3.getRowCount() > 0)
			{
				localDataRow = new DataRow(localDataSetView3);
				localDataSetView3.first();
				do
				{
					i = localDataSetView3.getStatus();
					if ((i & 0x1) == 0)
					{
						localObject1 = new Record(paramDeltaRecordSet.getFormat());
						localDataSetView3.getDataRow(localDataRow);
						saveRowToRecord(localDataRow, (Record)localObject1);
						paramDeltaRecordSet.append((int)localDataSetView3.getInternalRow(), 1, (Record)localObject1, null);
						if (paramBoolean1)
							ProviderHelp.markPendingStatus(localDataSetView3, true);
					}
				}
				while (localDataSetView3.next());
			}
			bool2 = true;
		}
		finally
		{
			paramDeltaRecordSet.setTrace(bool1);
			if ((paramBoolean1) && ((paramBoolean2) || (!bool2)))
			{
				paramDataSet.resetPendingStatus(bool2);
				ProviderHelp.endResolution(localStorageDataSet);
			}
		}
	}

	public static void saveToDeltaRecordSet(DataSet dataset, DeltaRecordSet deltarecordset)
	{
		saveToDeltaRecordSet(dataset, deltarecordset, true, true);
	}

	public static void mergeTdDeltaToDataSet(TdDataSet tddataset, String as[], String as1[], DataSet dataset, String as2[], String as3[], String as4[], HashMap hashmap)
	{
		if(as.length * as1.length != as4.length)
			throw new IllegalArgumentException();
		HashMap hashmap1 = null;
		if(hashmap != null)
		{
			hashmap1 = new HashMap();
			for(Iterator iterator = hashmap.keySet().iterator(); iterator.hasNext();)
			{
				String s = (String)iterator.next();
				String s1 = (String)hashmap.get(s);
				if(!s.equals(s1))
				{
					if(s1 != null)
						hashmap1.put(s1, s);
					if(!hashmap1.containsKey(s))
						hashmap1.put(s, null);
				}
			}

		}
		String as5[] = tddataset.getRemainedKeyDataColumns();
		Column column = tddataset.getDataSet().getColumn(tddataset.getExpandedKeyName());
		String as6[];
		if(column.getCalcType() == 3)
		{
			String as7[] = column.getPickList().getDestinationColumns();
			as6 = new String[as5.length + as7.length];
			for(int i = 0; i < as7.length; i++)
				as6[as5.length + i] = as7[i];

		} else
		{
			as6 = new String[as5.length + 1];
			as6[as6.length - 1] = column.getColumnName();
		}
		System.arraycopy(as5, 0, as6, 0, as5.length);
		ArrayList arraylist = new ArrayList();
		String as9[];
		int k = (as9 = as2).length;
		for(int j = 0; j < k; j++)
		{
			String s2 = as9[j];
			if(hashmap1 != null && hashmap1.containsKey(s2))
			{
				s2 = (String)hashmap1.get(s2);
				if(s2 == null)
					continue;
			}
			if(tddataset.hasColumn(s2) != null)
				arraylist.add(s2);
		}

		String as8[] = (String[])arraylist.toArray(new String[0]);
		BigDecimal abigdecimal[] = new BigDecimal[as4.length];
		BigDecimal abigdecimal1[] = new BigDecimal[as4.length];
		DeltaRecordSet deltarecordset = new DeltaRecordSet();
		saveToDeltaRecordSet(tddataset, deltarecordset, false, false);
		DataRow datarow = new DataRow(dataset, as2);
		DataRow datarow1 = new DataRow(tddataset.getDataSet(), as6);
		ArrayList arraylist1 = new ArrayList();
		ArrayList arraylist2 = new ArrayList();
		Variant variant = new Variant();
		for(int l = 0; l < deltarecordset.recordCount(); l++)
		{
			int i1 = deltarecordset.getState(l);
			Record record = null;
			Record record1 = null;
			Record record2 = null;
			if(i1 == 1)
				record = deltarecordset.getNewRecord(l);
			else
				if(i1 == 2)
				{
					boolean flag = false;
					String as12[];
					int l2 = (as12 = as8).length;
					for(int j2 = 0; j2 < l2; j2++)
					{
						String s4 = as12[j2];
						if(deltarecordset.getOldRecord(l).getField(s4).isSameAs(deltarecordset.getNewRecord(l).getField(s4)))
							continue;
						flag = true;
						break;
					}

					record = deltarecordset.getNewRecord(l);
					if(flag)
						record2 = deltarecordset.getOldRecord(l);
					else
						record1 = deltarecordset.getOldRecord(l);
				} else
					if(i1 == 3)
						record2 = deltarecordset.getNewRecord(l);
			for(int i2 = 0; i2 < 2; i2++)
			{
				Record record3 = i2 != 0 ? record : record2;
				if(record3 != null)
				{
					arraylist1.clear();
					String as13[];
					int i4 = (as13 = as).length;
					for(int i3 = 0; i3 < i4; i3++)
					{
						String s5 = as13[i3];
						for(int j6 = 0; j6 < tddataset.getExpandedKeyValues().size(); j6++)
							if((!record3.getField((new StringBuilder(String.valueOf(s5))).append("$").append(j6).toString()).isNull() || record1 != null && !record1.getField((new StringBuilder(String.valueOf(s5))).append("$").append(j6).toString()).isNull()) && arraylist1.indexOf(Integer.valueOf(j6)) < 0)
								arraylist1.add(Integer.valueOf(j6));

					}

					for(int k2 = 0; k2 < arraylist1.size(); k2++)
					{
						String as14[];
						int j5 = (as14 = as2).length;
						for(int j4 = 0; j4 < j5; j4++)
						{
							String s7 = as14[j4];
							String s12 = s7;
							if(hashmap1 != null)
							{
								String s13 = (String)hashmap1.get(s7);
								if(s13 != null)
									s12 = s13;
							}
							if(record3.getField(s12) != null)
							{
								loadValueFromField(datarow.getColumn(s7).getDataType(), variant, record3.getField(s12));
								datarow.setVariant(s7, variant);
							} else
							{
								String as16[] = tddataset.getExpandedKeyDataColumns();
								HashMap hashmap2 = tddataset.getExpandedKeyFilter();
								HashMap hashmap3 = null;
								if(hashmap2.size() > 0)
								{
									hashmap3 = new HashMap();
									String s16;
									Variant variant2;
									for(Iterator iterator2 = hashmap2.keySet().iterator(); iterator2.hasNext(); hashmap3.put(s16, variant2))
									{
										s16 = (String)iterator2.next();
										variant2 = new Variant();
										loadValueFromField(tddataset.getColumn(s16).getDataType(), variant2, record3.getField(s16));
									}

								}
								Variant avariant[] = tddataset.getExpandedKeyDataValues(((Integer)arraylist1.get(k2)).intValue(), hashmap3);
								for(int k8 = 0; k8 < as16.length; k8++)
								{
									String s17 = as16[k8];
									if(s12.equals(s17))
										datarow.setVariant(s7, avariant[k8]);
								}

							}
						}

						for(int j3 = 0; j3 < as1.length; j3++)
						{
							for(int k4 = 0; k4 < as.length; k4++)
							{
								BigDecimal bigdecimal = record3.getField((new StringBuilder(String.valueOf(as[k4]))).append("$").append(arraylist1.get(k2)).toString()).getNumber().multiply(as1[j3] == null ? BigDecimal.ONE : record3.getField(as1[j3]).getNumber());
								if(record1 != null)
									bigdecimal = bigdecimal.subtract(record1.getField((new StringBuilder(String.valueOf(as[k4]))).append("$").append(arraylist1.get(k2)).toString()).getNumber().multiply(as1[j3] == null ? BigDecimal.ONE : record1.getField(as1[j3]).getNumber()));
								abigdecimal1[k4 + as.length * j3] = bigdecimal;
							}

						}

						if(dataset.locate(datarow, 32))
						{
							for(int k3 = 0; k3 < abigdecimal.length; k3++)
								abigdecimal[k3] = dataset.getBigDecimal(as4[k3]);

							dataset.editRow();
							loadRowFromRecord(dataset, record3, true, hashmap1);
							for(int l3 = 0; l3 < abigdecimal.length; l3++)
								dataset.setBigDecimal(as4[l3], abigdecimal[l3].add(record3 != record ? abigdecimal1[l3].negate() : abigdecimal1[l3]));

						} else
						{
							boolean flag2 = true;
							for(int l4 = 0; l4 < abigdecimal1.length; l4++)
							{
								if(abigdecimal1[l4].compareTo(BigDecimal.ZERO) == 0)
									continue;
								flag2 = false;
								break;
							}

							if(flag2)
								continue;
							dataset.insertRow(false);
							DataRow datarow3 = new DataRow(tddataset.getDataSet());
							String as17[];
							int k7 = (as17 = as6).length;
							for(int k6 = 0; k6 < k7; k6++)
							{
								String s9 = as17[k6];
								String s14 = null;
								if(hashmap != null)
									s14 = (String)hashmap.get(s9);
								if(s14 == null)
									s14 = s9;
								if(datarow.hasColumn(s14) != null)
								{
									datarow.getVariant(s14, variant);
									datarow1.setVariant(s9, variant);
								} else
									if(tddataset.hasColumn(s9) != null)
									{
										if(record3.getField(s9).isNull())
											variant.setUnassignedNull();
										else
											loadValueFromField(tddataset.getColumn(s9).getDataType(), variant, record3.getField(s9));
										datarow1.setVariant(s9, variant);
									}
							}

							if(as3 != null && as3.length > 0 && tddataset.getDataSet().lookup(datarow1, datarow3, 32))
							{
								String as18[];
								int i8 = (as18 = as3).length;
								for(int l6 = 0; l6 < i8; l6++)
								{
									String s10 = as18[l6];
									String s15 = s10;
									if(hashmap1 != null && hashmap1.containsKey(s10))
										s15 = (String)hashmap1.get(s10);
									if(s15 == null)
										s15 = s10;
									datarow3.getVariant(s15, variant);
									dataset.setVariant(s10, variant);
								}

							}
							loadRowFromRecord(dataset, record3, true, hashmap1);
							for(int k5 = 0; k5 < abigdecimal1.length; k5++)
								dataset.setBigDecimal(as4[k5], record3 != record ? abigdecimal1[k5].negate() : abigdecimal1[k5]);

						}
						dataset.post();
						boolean flag3 = true;
						String as15[];
						int i7 = (as15 = as4).length;
						for(int i6 = 0; i6 < i7; i6++)
						{
							String s8 = as15[i6];
							if(dataset.isNull(s8) || dataset.getBigDecimal(s8).compareTo(BigDecimal.ZERO) == 0)
								continue;
							flag3 = false;
							break;
						}

						long l5 = dataset.getInternalRow();
						if(flag3)
						{
							if(arraylist2.indexOf(Long.valueOf(l5)) < 0)
								arraylist2.add(Long.valueOf(l5));
						} else
						{
							long l7 = arraylist2.indexOf(Long.valueOf(l5));
							if(l7 >= 0L)
								arraylist2.remove(Long.valueOf(l7));
						}
					}

				}
			}

		}

		if(arraylist2.size() > 0)
		{
			DataSetView datasetview = tddataset.cloneDataSetView();
			arraylist.clear();
			String as11[];
			int k1 = (as11 = as5).length;
			for(int j1 = 0; j1 < k1; j1++)
			{
				String s3 = as11[j1];
				if(dataset.hasColumn(s3) != null)
					arraylist.add(s3);
			}

			String as10[] = (String[])arraylist.toArray(new String[0]);
			DataRow datarow2 = new DataRow(datasetview, as10);
			for(Iterator iterator1 = arraylist2.iterator(); iterator1.hasNext();)
			{
				long l1 = ((Long)iterator1.next()).longValue();
				dataset.goToInternalRow(l1);
				ReadRow.copyTo(as10, dataset, as10, datarow2);
				if(!tddataset.locate(datarow2, 32))
				{
					dataset.deleteRow();
				} else
				{
					boolean flag1 = false;
					do
					{
						String s6 = tddataset.getExpandedKeyName();
						Variant variant1 = new Variant();
						dataset.getVariant(s6, variant1);
						int i5 = tddataset.getExpandedKeyValues().indexOf(variant1);
						String as19[];
						int j8 = (as19 = as).length;
						for(int j7 = 0; j7 < j8; j7++)
						{
							String s11 = as19[j7];
							if(tddataset.isNull((new StringBuilder(String.valueOf(s11))).append("$").append(i5).toString()))
								continue;
							flag1 = true;
							break;
						}

					} while(!flag1 && tddataset.locate(datarow2, 2));
					if(!flag1)
						dataset.deleteRow();
				}
			}

			datasetview.close();
			datasetview = null;
		}
	}

	public static void mergeTdDeltaToDataSet(TdDataSet tddataset, String as[], String as1[], DataSet dataset, String as2[], String as3[], String as4[])
	{
		mergeTdDeltaToDataSet(tddataset, as, as1, dataset, as2, as3, as4, null);
	}

	public static void backfillFromDeltaRecordSet(DataSet paramDataSet, DeltaRecordSet paramDeltaRecordSet)
	{
		paramDataSet.enableDataSetEvents(false);
		try
		{
			long l = paramDataSet.getInternalRow();
			for (int i = 0; i < paramDeltaRecordSet.recordCount(); i++)
				switch (paramDeltaRecordSet.getState(i))
				{
				case 1:
					DataRow localDataRow = new DataRow(paramDataSet);
					loadRowFromRecord(localDataRow, paramDeltaRecordSet.getNewRecord(i), true);
					paramDataSet.addRow(localDataRow);
					break;
				case 2:
					if (paramDataSet.goToInternalRow(paramDeltaRecordSet.getId(i), true))
						loadRowFromRecord(paramDataSet, paramDeltaRecordSet.getNewRecord(i), true);
					break;
				case 3:
					if (paramDataSet.goToInternalRow(paramDeltaRecordSet.getId(i)))
						paramDataSet.deleteRow();
					break;
				}
			if (l > 0L)
				paramDataSet.goToInternalRow(l, true);
		}
		finally
		{
			paramDataSet.enableDataSetEvents(true);
		}
	}
	public static void resetStatus(DataSet paramDataSet)
	{
		StorageDataSet localStorageDataSet = paramDataSet.getStorageDataSet();
		if (!localStorageDataSet.changesPending())
		{
			localStorageDataSet.resetPendingStatus(true);
			return;
		}
		ProviderHelp.startResolution(localStorageDataSet, true);
		boolean bool = false;
		try
		{
			DataSetView localDataSetView1 = new DataSetView();
			DataSetView localDataSetView2 = new DataSetView();
			DataSetView localDataSetView3 = new DataSetView();
			localStorageDataSet.getDeletedRows(localDataSetView1);
			localStorageDataSet.getUpdatedRows(localDataSetView2);
			localStorageDataSet.getInsertedRows(localDataSetView3);
			if (localDataSetView1.getRowCount() > 0)
			{
				localDataSetView1.first();
				do
					ProviderHelp.markPendingStatus(localDataSetView1, true);
				while (localDataSetView1.next());
			}
			if (localDataSetView2.getRowCount() > 0)
			{
				localDataSetView2.first();
				do
					ProviderHelp.markPendingStatus(localDataSetView2, true);
				while (localDataSetView2.next());
			}
			if (localDataSetView3.getRowCount() > 0)
			{
				localDataSetView3.first();
				do
					ProviderHelp.markPendingStatus(localDataSetView3, true);
				while (localDataSetView3.next());
			}
			bool = true;
		}
		finally
		{
			localStorageDataSet.resetPendingStatus(bool);
			ProviderHelp.endResolution(localStorageDataSet);
		}
	}

	public static void enableDataAwareComponent(DataSetAware datasetaware, boolean flag)
	{
		if(datasetaware instanceof JTextComponent)
			((JTextComponent)datasetaware).setEditable(flag);
		else
			if(datasetaware instanceof JdbTable)
				((JdbTable)datasetaware).setEditable(flag);
			else
				if(datasetaware instanceof Editable)
					((Editable)datasetaware).setEditable(flag);
				else
					if(datasetaware instanceof JdbComboBox)
					{
						JdbComboBox jdbcombobox = (JdbComboBox)datasetaware;
						jdbcombobox.setEnabled(flag);
						if(jdbcombobox.isEditable())
						{
							Component component = ((JdbComboBox)datasetaware).getEditor().getEditorComponent();
							if(component instanceof JTextComponent)
								((JTextComponent)component).setEditable(flag);
						}
					} else
						if(!(datasetaware instanceof JLabel) && (datasetaware instanceof JComponent))
							((JComponent)datasetaware).setEnabled(flag);
	}

	public static void listDataAwareComponents(Component component, HashMap hashmap)
	{
		if(component instanceof DataSetAware)
		{
			HashMap hashmap1 = (HashMap)hashmap.get(((DataSetAware)component).getDataSet());
			if(hashmap1 == null)
				return;
			if(component instanceof JTextComponent)
				hashmap1.put((DataSetAware)component, Boolean.valueOf(((JTextComponent)component).isEditable()));
			else
				if(component instanceof JdbTable)
					hashmap1.put((DataSetAware)component, Boolean.valueOf(((JdbTable)component).isEditable() && component.isEnabled()));
				else
					hashmap1.put((DataSetAware)component, Boolean.valueOf(component.isEnabled()));
		} else
			if(component instanceof Container)
			{
				Component acomponent[];
				int j = (acomponent = ((Container)component).getComponents()).length;
				for(int i = 0; i < j; i++)
				{
					Component component1 = acomponent[i];
					listDataAwareComponents(component1, hashmap);
				}

			}
	}

	public static void enableDataAwareComponents(HashMap hashmap, boolean flag)
	{
		for(Iterator<?> iterator = hashmap.keySet().iterator(); iterator.hasNext();)
		{
			DataSetAware datasetaware = (DataSetAware)iterator.next();
			if(((Boolean)hashmap.get(datasetaware)).booleanValue())
				enableDataAwareComponent(datasetaware, flag);
		}

	}

	public static String describeRow(ReadWriteRow readwriterow, String as[], String s)
	{
		String s1 = "";
		String as1[];
		int j = (as1 = as).length;
		for(int i = 0; i < j; i++)
		{
			String s2 = as1[i];
			if(s1.length() == 0)
				s1 = (new StringBuilder(String.valueOf(s1))).append(s).toString();
			s1 = (new StringBuilder(String.valueOf(s1))).append(readwriterow.getString(s2)).toString();
		}

		return s1;
	}

	public static void buildMeta(StorageDataSet storagedataset, RecordSet recordset, RecordSet recordset1)
			throws Exception
	{
		RecordFormat recordformat = new RecordFormat("@");
		boolean flag = recordset.getFormat().getField("FLD_NAME") != null;
		boolean flag1 = recordset.getFormat().getField("WIDTH") != null;
		for(int i = 0; i < recordset.recordCount(); i++)
		{
			Record record = recordset.getRecord(i);
			String s = record.getField("FLD_ID").getString().replace('.', '$');
			String s3 = record.getField("FLD_RS_ID").getString();
			String s6 = flag ? record.getField("FLD_NAME").getString() : "";
			int i1;
			int j1;
			int k1;
			int i2;
			CharacterCase charactercase;
			try
			{
				if(s3.length() > 0)
				{
					RecordFieldFormat recordfieldformat = new RecordFieldFormat(s3);
					if(s6.length() == 0)
						s6 = recordfieldformat.getLabel();
					i1 = recordfieldformat.getType();
					j1 = recordfieldformat.getLength();
					k1 = recordfieldformat.getDecimal();
					i2 = recordfieldformat.getWidth();
					if(flag1)
						i2 = record.getField("WIDTH").isNull() ? i2 : Math.max(record.getField("WIDTH").getInt(), 0);
					charactercase = recordfieldformat.getCharacterCase();
				} else
				{
					if(s6.length() == 0)
						s6 = s;
					i1 = DataType.getType(record.getField("DATA_TYPE").getString());
					j1 = record.getField("DATA_LEN").getInt();
					k1 = record.getField("DATA_DEC").getInt();
					i2 = record.getField("WIDTH").isNull() ? 0 : Math.max(record.getField("WIDTH").getInt(), 0);
					String s13 = record.getField("CHAR_CASE").getString();
					charactercase = s13 != "U" ? s13 != "L" ? CharacterCase.normal : CharacterCase.lowerCase : CharacterCase.upperCase;
				}
			}
			catch(Exception exception)
			{
				throw new Exception(MessageFormat.format(A.getString("MSG_INVALID_FIELD_DEFINITION"), new Object[] {
					s, exception.getMessage()
				}));
			}
			RecordFieldFormat recordfieldformat1 = new RecordFieldFormat(s, s6, i1, j1, k1, i2, charactercase);
			recordformat.appendField(recordfieldformat1);
			String s14 = record.getField("CODE_TBL") == null ? "" : record.getField("CODE_TBL").getString();
			if(s14.length() > 0)
			{
				RecordFieldFormat recordfieldformat2 = new RecordFieldFormat((new StringBuilder(String.valueOf(s))).append("$DESC").toString(), recordfieldformat1.getLabel(), 22, 50, 0);
				recordfieldformat2.setWidth(recordfieldformat1.getWidth());
				recordformat.appendField(recordfieldformat2);
			}
		}

		storagedataset.empty();
		storagedataset.close();
		for(int j = storagedataset.getColumnCount() - 1; j >= 0; j--)
		{
			Column column = storagedataset.getColumn(j);
			storagedataset.dropColumn(column);
		}

		storagedataset.dropAllIndexes();
		storagedataset.dropIndex();
		storagedataset.setSort(null);
		loadMetaFromRecordFormat(storagedataset, recordformat);
		for(int k = 0; k < recordset.recordCount(); k++)
		{
			Record record1 = recordset.getRecord(k);
			String s1 = record1.getField("CODE_TBL") == null ? "" : record1.getField("CODE_TBL").getString();
			if(s1.length() != 0)
			{
				String s4 = record1.getField("FLD_ID").getString().replace('.', '$');
				String s7 = (new StringBuilder(String.valueOf(s4))).append("$DESC").toString();
				storagedataset.getColumn(s4).setVisible(0);
				storagedataset.getColumn(s7).setCalcType(3);
				StorageDataSet storagedataset1 = CodeTable.defaultCodeTable.getDataSet(s1);
				if(storagedataset1.hasColumn("CODE") != null && storagedataset1.hasColumn("DESCRIPTION") != null)
				{
					if(storagedataset1.getColumn("CODE").getDataType() != storagedataset.getColumn(s4).getDataType())
						throw new Exception(MessageFormat.format(A.getString("MSG_INVALID_FIELD_DEFINITION"), new Object[] {
							s4, A.getString("MSG_UNMATCHED_LOOKUP_DATA_TYPE")
						}));
					String s10 = "CODE";
					String s12 = "DESCRIPTION";
					storagedataset.getColumn(s7).setPickList(new PickListDescriptor(storagedataset1, new String[] {
							s10
					}, new String[] {
							s12
					}, new String[] {
							s4
					}, s12, true));
				}
			}
		}

		if(recordset1 != null)
		{
			for(int l = 0; l < recordset1.recordCount(); l++)
			{
				Record record2 = recordset1.getRecord(l);
				String s2 = record2.getField("FLD_ID").getString();
				String s5 = record2.getField("FLD_NAME").getString();
				String s8 = record2.getField("FLD_RS_ID").getString();
				String s9 = record2.getField("SUM_OPR").getString();
				String s11 = record2.getField("EXPRESSION").getString();
				s11 = s11.replace('.', '$');
				int l1;
				int j2;
				int k2;
				int l2;
				CharacterCase charactercase1;
				try
				{
					if(s8.length() > 0)
					{
						RecordFieldFormat recordfieldformat3 = new RecordFieldFormat(s8);
						if(s5.length() == 0)
							s5 = recordfieldformat3.getLabel();
						l1 = recordfieldformat3.getType();
						j2 = recordfieldformat3.getLength();
						k2 = recordfieldformat3.getDecimal();
						l2 = recordfieldformat3.getWidth();
						l2 = record2.getField("WIDTH").isNull() ? l2 : record2.getField("WIDTH").getInt();
						charactercase1 = recordfieldformat3.getCharacterCase();
					} else
					{
						l1 = DataType.getType(record2.getField("DATA_TYPE").getString());
						j2 = record2.getField("DATA_LEN").getInt();
						k2 = record2.getField("DATA_DEC").getInt();
						l2 = record2.getField("WIDTH").isNull() ? 0 : record2.getField("WIDTH").getInt();
						String s15 = record2.getField("CHAR_CASE").getString();
						charactercase1 = s15 != "U" ? s15 != "L" ? CharacterCase.normal : CharacterCase.lowerCase : CharacterCase.upperCase;
					}
				}
				catch(Exception exception1)
				{
					throw new Exception(MessageFormat.format(A.getString("MSG_INVALID_FIELD_DEFINITION"), new Object[] {
						s2, exception1.getMessage()
					}));
				}
				RecordFieldFormat recordfieldformat4 = new RecordFieldFormat(s2, s5, l1, j2, k2, l2, charactercase1);
				Column column1 = new Column(recordfieldformat4.getName(), recordfieldformat4.getLabel(), fieldTypeToDataType(recordfieldformat4.getType()));
				column1.setCharacterCase(characterCaseFieldToColumn(recordfieldformat4.getCharacterCase()));
				column1.setPrecision(recordfieldformat4.getLength());
				column1.setScale(recordfieldformat4.getDecimal());
				column1.setWidth(recordfieldformat4.getWidth());
				column1.setCalcType(2);
				Object obj;
				if(s9.equals("SUM"))
					obj = new SumAggOperator();
				else
					if(s9.equals("MAX"))
						obj = new MaxAggOperator();
					else
						if(s9.equals("MIN"))
							obj = new MinAggOperator();
						else
							if(s9.equals("AVG"))
								obj = new AvgAggOperator();
							else
								if(s9.equals("CNT"))
								{
									column1.setDataType(4);
									obj = new CountAggOperator();
								} else
									if(s9.equals("RWC"))
									{
										column1.setDataType(4);
										if(s11.length() == 0 && recordset.recordCount() > 0)
											s11 = recordset.getRecord(0).getField("FLD_ID").getString();
										obj = new RowCountAggOperator();
									} else
									{
										column1.setDataType(4);
										obj = new CountAggOperator();
									}
				AggDescriptor aggdescriptor = new AggDescriptor(null, s11, ((com.borland.dx.dataset.AggOperator) (obj)));
				column1.setAgg(aggdescriptor);
				storagedataset.addColumn(column1);
			}

		}
		storagedataset.open();
	}

	public static StorageDataSet cloneDataSet(StorageDataSet storagedataset)
	{
		StorageDataSet storagedataset1 = storagedataset.cloneDataSetStructure();
		for(int i = 0; i < storagedataset1.getColumnCount(); i++)
		{
			Column column = storagedataset1.getColumn(i);
			if(column.getColumnChangeListener() != null)
				column.removeColumnChangeListener(column.getColumnChangeListener());
		}

		storagedataset1.open();
		DataRow datarow = new DataRow(storagedataset);
		for(int j = 0; j < storagedataset.getRowCount(); j++)
		{
			storagedataset.getDataRow(j, datarow);
			storagedataset1.insertRow(false);
			datarow.copyTo(storagedataset1);
			storagedataset1.post();
		}

		return storagedataset1;
	}


}
