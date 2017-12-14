package com.evangelsoft.easyui.template.intf;

import java.util.Map;

import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.TxMode;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.session.RemoteException;

public interface BaseMasterDetail {
	/**
	 * ģ��ID
	 */
	String TEMPLATE_ID="TEMPLATE_ID";
	/**
	 * ģ����
	 */
	String TEMPLATE_NUM="TEMPLATE_NUM";
	/**
	 * ������
	 */
	String OPERATE_CODE="OPERATE_CODE";
	/**
	 * ����ID
	 */
	String FUNCTION_ID="FUNCTION_ID";
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
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param key ��������
	 * @param data ��ӵ����ݼ����б�
	 * @param newKey ���ص�����������
	 * @param dataBackfill ��̨�޸ĵ����ݣ����ǰ̨
	 * @param errMsg ��ʾ��Ϣ�����ؽ��ΪtrueʱΪ��
	 * @return boolean ���ز���ִ�н��
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean add(Map<String, Object> map,Object key, Object data, VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg)throws RemoteException ;
	/**
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param key ��������
	 * @param data  �޸����ݼ����б�
	 * @param newKey �µ�����
	 * @param dataBackfill ��̨�޸����ݷ����ȥ������
	 * @param errMsg ��ʾ��Ϣ�����ؽ��ΪtrueʱΪ��
	 * @return boolean ���ز���ִ�н��
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean modify(Map<String, Object> map,Object key,Object data, VariantHolder<Object> newKey, VariantHolder<Object> dataBackfill, VariantHolder<String> errMsg) throws RemoteException ;

	/**
	 * @param map  ���ϣ�ģ��ID�����Ժ���������ҵ���������õ���N�������������ݲ���������map��ʽ
	 * @param key ��������
	 * @param errMsg ��Ϣ��ʾ���������true��Ϊ��
	 * @return ���ز���ִ�н��
	 * @throws RemoteException
	 */
	@TxMode(TxUnit.TX_REQUIRED)
	public boolean remove(Map<String, Object> map,Object key, VariantHolder<String> errMsg)throws RemoteException;
}
