package com.evangelsoft.easyui.template.client.nc;

/**
 * 函数型公式ITEM
 * @author cch (cch@ufida.com.cn)
 * 2006-2-9-10:15:50
 * 
 */
public class FunctionFormulaItem extends FormulaItem
{

	public FunctionFormulaItem(String displayName, String inputSig, String hintMsg)
	{
		super(displayName, inputSig, hintMsg);
	}
	
    public String[] getKeyWord()
    {
        int npos = getInputSig().indexOf('(');
        if(npos>0)
        {
        	return  new String[]{getInputSig().substring(0,npos)};
        }
        return super.getKeyWord();
    }

}
