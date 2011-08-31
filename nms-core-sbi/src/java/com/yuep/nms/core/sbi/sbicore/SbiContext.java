package com.yuep.nms.core.sbi.sbicore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;
import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.model.SbiInitialParameter;

/**
 * <p>
 * Title: SbiContext
 * </p>
 * <p>
 * SBIȫ������������
 * </p>
 * 
 * @author pl
 * created 2011-4-14 ����06:12:25
 * modified [who date description]
 * check [who date description]
 */
public class SbiContext {
    
    /**
     * ���е�Adaptor��Ϣ
     */
    public final static List<DeviceAdaptor> deviceAdaptorList=new ArrayList<DeviceAdaptor>();
    
    /**
     * ����˴��ݹ�����ne��Ϣ
     */
    public static SbiInitialParameter sbiInitialParameter;
    
    /**
     * ����SbiInitialParameter
     * @param sbiParam
     */
    public static void setSbiInitialParameter(SbiInitialParameter sbiParam){
        sbiInitialParameter=sbiParam;
    }
    
    /**
     * ��map�洢��NE��Ϣ
     */
    public final static Map<String, List<NeLoginParameter>> nmIpNeMap = new HashMap<String, List<NeLoginParameter>>();
    
    /**
     * �������SBI���Ƿ���ͨ����ʼ�����
     */
    public static boolean isPingOk = false;
    
    /**
     * ����isPingOk
     * @param pingOk
     */
    public static void setPingOk(boolean pingOk){
        isPingOk=pingOk;
    }

    /**
     * ����ip�õ���Ԫͨ�Ų���
     * @param ip
     * @param neNaming
     * @return
     */
    public static NeLoginParameter getNeloginParameterByNeNaming(String ip,MoNaming neNaming) {
        List<NeLoginParameter> neList = nmIpNeMap.get(ip);
        if (neList != null) {
            for (NeLoginParameter ne : neList) {
                if (ne.getNeNaming().equals(neNaming))
                    return ne;
            }
        }
        return null;
    }
    
}
