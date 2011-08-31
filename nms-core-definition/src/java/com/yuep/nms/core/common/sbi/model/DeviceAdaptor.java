package com.yuep.nms.core.common.sbi.model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.yuep.base.util.format.Pair;

/**
 * <p>
 * Title: DeviceAdaptor
 * </p>
 * <p>
 * �豸������
 * </p>
 * 
 * @author pl
 * created 2011-4-14 ����02:53:28
 * modified [who date description]
 * check [who date description]
 */
public class DeviceAdaptor implements Serializable{
    private static final long serialVersionUID = 6664722222102358426L;
    
    /**
     * �豸���ͣ�����C8000�����
     */
    private String deviceType;
    
    /**
     * �豸�汾������V1�����
     */
    private String version;
    
    /**
     * KeyΪadaptorName������eqm����valueΪ��Ӧ�Ľӿ����ʵ����ȫ������
     */
    private Map<String, Pair<String, String>> seviceMap = new HashMap<String, Pair<String, String>>();

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Pair<String, String>> getSeviceMap() {
        return seviceMap;
    }

    public void setSeviceMap(Map<String, Pair<String, String>> seviceMap) {
        this.seviceMap = seviceMap;
    }
    
    @Override
    public String toString() {
        return "deviceType="+deviceType+",version="+version;
    }

}
