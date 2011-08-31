package com.yuep.nms.core.common.sbi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: SbiInitialParameter
 * </p>
 * <p>
 * SBI初始化参数,包括该SBI可管理的网元通信信息等
 * </p>
 * 
 * @author pl
 * created 2011-4-16 下午04:11:07
 * modified [who date description]
 * check [who date description]
 */
public class SbiInitialParameter implements Serializable{
    
    private static final long serialVersionUID = 7869204554698644052L;
   
    /**
     * ServerIp
     */
    private String serverIp = "127.0.0.1";
    
    /**
     * 网元列表：网元的通信参数
     */
    private List<NeLoginParameter> neList = new ArrayList<NeLoginParameter>();;
    
    /**
     * 附加属性，供扩展
     */
    private Map<String, Object> memoMap = new HashMap<String, Object>();

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public List<NeLoginParameter> getNeList() {
        return neList;
    }

    public void setNeList(List<NeLoginParameter> neList) {
        this.neList = neList;
    }

    public Map<String, Object> getMemoMap() {
        return memoMap;
    }

    public void setMemoMap(Map<String, Object> memoMap) {
        this.memoMap = memoMap;
    }

}
