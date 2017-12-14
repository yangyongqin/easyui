package com.evangelsoft.easyui.template.intf;

import java.util.Map;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;

public interface BaseSingleDataSet {
	/**
	 *
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param data �����ݵļ���
	 * @param errMsg ��ʾ��Ϣ�����ؽ��ΪtrueʱΪ��
	 * ��ѯ�ṹ���������������ù���
	 */
	public boolean getStructure(Map<String, Object> map, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException ;
	/**
	 *
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param key ��������
	 * @param data ���ڷ��ز�ѯ������
	 * @param errMsg ��ʾ��Ϣ�����ؽ��ΪtrueʱΪ��
	 * @return boolean ���ز���ִ�н��
	 * @throws RemoteException
	 * ����������ѯ��Ӧ�ĵ�����Ϣ
	 */
	public boolean get(Map<String, Object> map,Object key, VariantHolder<Object> data,
			VariantHolder<String> errMsg) throws RemoteException;
	/**
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param filter ��ѯ����
	 * @param list ���������б�
	 * @param errMsg ������ʾ
	 * @return boolean ���ز���ִ�н��
	 */
	public boolean list(Map<String, Object> map,Object filter, VariantHolder<Object> list,
			VariantHolder<String> errMsg)throws RemoteException  ;
	
	/**
	 * @param paramObject ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param data ����
	 * @param errMsg ��ʾ��Ϣ
	 * @return
	 * @throws RemoteException
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	  public  boolean flush(Map<String, Object> map,Object data, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg)
	    throws RemoteException;
}
