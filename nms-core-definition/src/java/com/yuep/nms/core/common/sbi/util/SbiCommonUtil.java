package com.yuep.nms.core.common.sbi.util;

import java.util.Map;

import com.yuep.base.util.net.snmp.model.SnmpProtocalLoginParam;
import com.yuep.base.util.net.snmp.model.YuepSnmpConst;
import com.yuep.nms.core.common.sbi.model.NeLoginParameter;

/**
 * <p>
 * Title: SbiCommonUtil
 * </p>
 * <p>
 * SBI公用工具类
 * </p>
 * 
 * @author pl
 * created 2011-4-18 上午11:41:58
 * modified [who date description]
 * check [who date description]
 */
public class SbiCommonUtil {
    
    /**
     * 根据设备型号、版本、适配器名得到adaptor本地服务名
     * @param deviceType
     * @param version
     * @param adaptorName
     * @return
     */
    public static String getAdaptorServiceName(String deviceType,String version,String adaptorName){
       return  deviceType + "." + version + "." + adaptorName;
    }
    
    /**
     * 根据NE的通信参数构建一个SNMP Agent的通信参数
     * @param ne
     * @return
     */
    public static SnmpProtocalLoginParam getSnmpProtocalLoginParam(NeLoginParameter ne) {
        SnmpProtocalLoginParam loginParam = new SnmpProtocalLoginParam();
        loginParam.setIp(ne.getIp());
        Map<String, Object> loginMap = ne.getLoginMap();
        loginParam.setReadCommunity(loginMap.get(YuepSnmpConst.SNMP_READ_COMMUNITY).toString());
        loginParam.setWriteCommunity(loginMap.get(YuepSnmpConst.SNMP_WRITE_COMMUNITY).toString());
        loginParam.setIp(loginMap.get(YuepSnmpConst.SNMP_IP).toString());
        loginParam.setSnmpPort(Integer.parseInt(loginMap.get(YuepSnmpConst.SNMP_PORT).toString()));
        loginParam.setSnmpVer(Integer.parseInt(loginMap.get(YuepSnmpConst.SNMP_VERSION).toString()));
        loginParam.setReties(Integer.parseInt(loginMap.get(YuepSnmpConst.SNMP_RETRIES).toString()));
        loginParam.setTimeout(Integer.parseInt(loginMap.get(YuepSnmpConst.SNMP_TIMEOUT).toString()));
        return loginParam;
    }
    
}
