package com.yuep.nms.core.common.sbi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: NeLoginParamater
 * </p>
 * <p>
 * 网元登陆参数
 * </p>
 * 
 * @author pl
 * created 2011-4-18 上午11:15:38
 * modified [who date description]
 * check [who date description]
 */
public class NeLoginParameter implements Serializable{
    private static final long serialVersionUID = -5884794247121435545L;
    /**
     * 网元IP
     */
    private String ip = "127.0.0.1";
    /**
     * 网元Naming
     */
    private MoNaming neNaming;
    /**
     * 网元登陆参数，例如端口号、共同体等，可以扩展
     */
    private Map<String, Object> loginMap = new HashMap<String, Object>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public MoNaming getNeNaming() {
        return neNaming;
    }

    public void setNeNaming(MoNaming neNaming) {
        this.neNaming = neNaming;
    }

    public Map<String, Object> getLoginMap() {
        return loginMap;
    }

    public void setLoginMap(Map<String, Object> loginMap) {
        this.loginMap = loginMap;
    }
}
