package com.evangelsoft.easyui.template.waiter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.evangelsoft.easyui.tool.VariableParser;

public class SqlTool {
	private static Pattern G = Pattern.compile("@[a-zA-Z_][0-9a-zA-Z_$]*");
	public static HashMap<String, Integer> prepare(String paramString)
	throws SQLException
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		StringBuffer localStringBuffer = new StringBuffer();
		int i = 0;
		int j = 0;
		int k = 0;
		int m = 0;
		int n = 0;
		String str1 = "";
		while (k < paramString.length())
		{
			int i1 = paramString.charAt(k);
			if (m != 0)
			{
				if (i1 == n)
				{
					str1 = paramString.substring(j, k + 1);
					j = k + 1;
				}
			}
			else if ((i1 == 39) || (i1 == 34) || (k == paramString.length() - 1))
			{
				n = i1;
				str1 = paramString.substring(j, k == paramString.length() - 1 ? k + 1 : k);
				j = k;
			}
			if (str1.length() != 0)
			{
				if (m == 0)
				{
					Matcher localMatcher = G.matcher(str1);
					while (localMatcher.find())
					{
						String str2 = localMatcher.group().substring(1);
						map.put(str2, Integer.valueOf(++i));
					}
					localStringBuffer.append(localMatcher.replaceAll("?"));
				}
				else
				{
					localStringBuffer.append(str1);
				}
				str1 = "";
				m = m != 0 ? 0 : 1;
			}
			k++;
		}
		return map;
	}
	public static String sqlHandle(String sql) throws SQLException{
		HashMap<String, Integer> map = prepare(sql);
		for(String key:map.keySet()){
			String value=VariableParser.getString(key,null);
			if(value!=null){
				sql=sql.replaceAll("@"+key, value.toString());
			}
		}
		return sql;
	}

	public static void main(String[] args) {
		try{
			System.out.println(SqlTool.sqlHandle("select * from sys_table where id= @OWNER_ID and xx= @OWNER_ID"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
