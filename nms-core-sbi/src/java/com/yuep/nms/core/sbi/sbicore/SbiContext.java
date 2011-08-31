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
 * SBI全局上下文属性
 * </p>
 * 
 * @author pl
 * created 2011-4-14 下午06:12:25
 * modified [who date description]
 * check [who date description]
 */
public class SbiContext {
    
    /**
     * 所有的Adaptor信息
     */
    public final static List<DeviceAdaptor> deviceAdaptorList=new ArrayList<DeviceAdaptor>();
    
    /**
     * 服务端传递过来的ne信息
     */
    public static SbiInitialParameter sbiInitialParameter;
    
    /**
     * 设置SbiInitialParameter
     * @param sbiParam
     */
    public static void setSbiInitialParameter(SbiInitialParameter sbiParam){
        sbiInitialParameter=sbiParam;
    }
    
    /**
     * 以map存储的NE信息
     */
    public final static Map<String, List<NeLoginParameter>> nmIpNeMap = new HashMap<String, List<NeLoginParameter>>();
    
    /**
     * 服务端与SBI端是否连通并初始化完毕
     */
    public static boolean isPingOk = false;
    
    /**
     * 设置isPingOk
     * @param pingOk
     */
    public static void setPingOk(boolean pingOk){
        isPingOk=pingOk;
    }

    /**
     * 根据ip得到网元通信参数
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
