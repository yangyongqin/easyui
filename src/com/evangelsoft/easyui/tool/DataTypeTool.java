package com.evangelsoft.easyui.tool;

import com.evangelsoft.econnect.dataformat.DataType;

/** * @author  —Ó”¿«’
 E-mail:
@date £∫2016-7-10 …œŒÁ10:08:49
@version 1.0   * @since    */
public class DataTypeTool {
	public static	int getType(String type){
		int i=DataType.getType(type);
		int j;
		switch (i)
		{
		case 1:
			j = 10;
			break;
		case 11:
			j = 13;
			break;
		case 12:
			j = 14;
			break;
		case 13:
			j = 15;
			break;
		case 21:
		case 22:
		case 23:
			j = 16;
			break;
		case 31:
		case 32:
		case 33:
			j = 12;
			break;
		default:
			j = 0;
		}
		return j;
	}
	public static void main(String[] args) {
		/*case 1:
	      return "NU";
	    case 11:
	      return "DT";
	    case 12:
	      return "TM";
	    case 13:
	      return "TS";
	    case 21:
	      return "CH";
	    case 22:
	      return "VC";
	    case 31:
	      return "BI";
	    case 32:
	      return "VB";
	    case 23:
	      return "CL";
	    case 33:
	      return "BL";
	    case 41:
	      return "OB";
	    case 101:
	      return "EX";*/
		System.out.println("NU"+getType("NU"));
		System.out.println("DT"+getType("DT"));

		System.out.println("TM"+getType("TM"));

		System.out.println("TS"+getType("TS"));
		System.out.println("CH"+getType("CH"));
		System.out.println("VC"+getType("VC"));
		System.out.println("BI"+getType("BI"));
		System.out.println("CL"+getType("CL"));
		System.out.println("BL"+getType("BL"));
		System.out.println("OB"+getType("OB"));
		System.out.println("EX"+getType("EX"));

	}

	public static String dataTypeToFieldTypeTo(int type)
	{
		String typeStr ="";
		switch (type)
		{
		case 10:
			typeStr="NU";
			break;
		case 13:
			typeStr="DT";
			break;
		case 14:
			typeStr="TM";
			break;
		case 15:
			typeStr="TS";
			break;
		case 16:
			typeStr="VC";
		case 12:
			typeStr="BI";
		case 0:
			typeStr="EX";
		default:
			typeStr = "VC";
		}
		return typeStr;
	}
}
