package com.evangelsoft.workbench.jxclientutil.waiter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.evangelsoft.econnect.condutil.ConditionItem;
import com.evangelsoft.econnect.condutil.ConditionJointNode;
import com.evangelsoft.econnect.condutil.ConditionLeafNode;
import com.evangelsoft.econnect.condutil.ConditionTree;
import com.evangelsoft.econnect.dataformat.DataState;
import com.evangelsoft.econnect.dataformat.DeltaRecordSet;
import com.evangelsoft.econnect.dataformat.Record;
import com.evangelsoft.econnect.dataformat.RecordSet;
import com.evangelsoft.econnect.dataformat.VariantHolder;
import com.evangelsoft.econnect.plant.TxUnit;
import com.evangelsoft.econnect.plant.WaiterFactory;
import com.evangelsoft.econnect.util.Encrypter;
import com.evangelsoft.econnect.util.ExceptionFormat;
import com.evangelsoft.econnect.util.ResourceLocater;
import com.evangelsoft.workbench.clientutil.HelpManager;
import com.evangelsoft.workbench.desktop.MachineCkcGenerator;
import com.evangelsoft.workbench.desktop.MachineCkcGeneratorFrame;
import com.evangelsoft.workbench.jxclientutil.homeintf.MachineCommisionHome;
import com.evangelsoft.workbench.monitor.homeintf.SysAuditHome;
import com.evangelsoft.workbench.security.homeintf.SysMachineHome;

public class MachineCommisionWaiter implements MachineCommisionHome{


	public boolean setMachineCkc(VariantHolder<Object> machIds,
			VariantHolder<Object> databack, VariantHolder<String> errMsg) {
		try {
			ArrayList<String> machIDArrayList = (ArrayList<String>)machIds.value;
			//载入证书文件
			Properties p = MachineCkcGenerator.loadConfig();
			String licenceStr = "";
			String kdc = "";
			if (p.size() == 1)
			{
				String str = (String)p.keys().nextElement();
				if (str != null)
				{
					licenceStr = str;
					kdc = p.getProperty(str);
				}
			}
			//获取机器信息
			SysMachineHome sysMachineHome = (SysMachineHome)WaiterFactory.getWaiter(SysMachineHome.class);
			VariantHolder<Object> data = new VariantHolder<Object>();
			data.value = new RecordSet();
			VariantHolder<String> mcErrMsg = new VariantHolder<String>();
			//构造过滤条件树
			HashMap filter = new HashMap();
			ConditionTree filterTree = new ConditionTree();
			ConditionJointNode root = new ConditionJointNode(ConditionJointNode.JOIN_TYPE_OR);
			filterTree.setRoot(root);
			for(int i=0;i<machIDArrayList.size();i++) {
				ConditionLeafNode node = new ConditionLeafNode("MACH_ID",
						ConditionItem.DATA_TYPE_STRING,
						ConditionItem.OPERATOR_EQUAL);
				node.setString(machIDArrayList.get(i));
				filterTree.addChildLast(root, node);
			}
			filter.put("filter", filterTree);
			//获取
			if(!sysMachineHome.list(filter, data, mcErrMsg)) {
				throw new Exception(mcErrMsg.value);
			}
			//生成授权码，构造deltarecordset
			RecordSet mcRs = (RecordSet)data.value;
			DeltaRecordSet mcDeltaRecordSet = new DeltaRecordSet(mcRs.getFormat());
			for(int i=0;i<mcRs.recordCount();i++) {
				mcDeltaRecordSet.append(i, DataState.MODIFIED, (Record)mcRs.getRecord(i).clone(), (Record)mcRs.getRecord(i).clone());
				mcDeltaRecordSet.getNewRecord(i).getField("MACH_STATUS").setString("A");
				String ckc = Encrypter.generateCkc(licenceStr,mcDeltaRecordSet.getOldRecord(i).getField("MACH_STR").getString());
				mcDeltaRecordSet.getNewRecord(i).getField("MACH_CKC").setString(ckc);
			}
			mcDeltaRecordSet.setTrace(true);
			SysAuditHome sysAuditHome = (SysAuditHome)WaiterFactory.getWaiter(SysAuditHome.class);
			for(int i=0;i<mcDeltaRecordSet.recordCount();i++) {
				sysAuditHome.audit(null, "PERMIT_AUDIT", mcDeltaRecordSet.getNewRecord(i).getField("MACH_ID").getString());
			}
			VariantHolder<Object> dataBackfill = new VariantHolder<Object>();
			dataBackfill.value = new DeltaRecordSet();
			if(!sysMachineHome.flush(mcDeltaRecordSet, dataBackfill, mcErrMsg)) {
				throw new Exception(mcErrMsg.value);
			}
			databack.value = mcDeltaRecordSet;
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			ExceptionFormat.format(e, errMsg);
			TxUnit.setRollback();
			return false;
		}
	}
	public static void main(String[] args) {
		try{
			Properties p = MachineCkcGenerator.loadConfig();
			String licenceStr="";
			String kdc = "";
			if (p.size() == 1)
			{
				String str = (String)p.keys().nextElement();
				if (str != null)
				{
					licenceStr = str;
					kdc = p.getProperty(str);
				}
			}
			System.out.println(licenceStr);
			String ckc = Encrypter.generateCkc(licenceStr,"59AB342DDEAE827E8449E6CA9A634E658C09046D56D8C4E7743D12D710CA6F1C6D6E71D0369EABCD81464F92561459F9CBAA5B04D25A3FA7AF4BE1FDBAA06C628485CCB96EE1E99386FE5685B78B6F6CCC19620387786859");
			System.out.println(ckc);
		}catch(Exception e){
			e.printStackTrace();
		}


		System.out.println(MachineCkcGenerator.class.getSimpleName());
		try
		{
			Properties localProperties = new Properties();
			InputStream localInputStream = ResourceLocater.loadStream(MachineCkcGenerator.class.getSimpleName() + ".properties");
			if (localInputStream != null)
				localProperties.load(localInputStream);
			String str = localProperties.getProperty("help_system");
			if ((str == null) || (str.length() == 0))
				str = MachineCkcGenerator.class.getSimpleName();
			HelpManager.load(str);
			MachineCkcGeneratorFrame localMachineCkcGeneratorFrame = new MachineCkcGeneratorFrame();
			localMachineCkcGeneratorFrame.setVisible(true);
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
	}

}
