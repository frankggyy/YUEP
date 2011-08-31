/*
 * $Id: SubSystemProxyCoreImpl.java, 2011-5-24 下午03:58:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxycore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;
import com.yuep.nms.core.common.subsystemproxycore.message.SystemProxyMessage;

/**
 * <p>
 * Title: SubSystemProxyCoreImpl
 * </p>
 * <p>
 * Description:
 * SubSystemProxyCore接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 下午03:58:58
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyCoreImpl implements SubSystemProxyCore {
    
    private Map<MoNaming,SubSystemProxy> subSystemProxies=new ConcurrentHashMap<MoNaming,SubSystemProxy>();
    private Map<MoNaming,MoNaming> subSystemBinds=new ConcurrentHashMap<MoNaming,MoNaming>();
    private Map<String,SubSystemRequestIntercepter> subSystemRequestIntercepters=new ConcurrentHashMap<String, SubSystemRequestIntercepter>();
    private CoreContext coreContext;
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#createSubSystemProxy(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy)
     */
    @Override
    public void createSubSystemProxy(SubSystemProxy subSystemProxy) {
        subSystemProxies.put(subSystemProxy.getSubSystemProxyProperty().getSubSystemId(), subSystemProxy);
        SystemProxyMessage systemProxyMessage=new SystemProxyMessage(SystemProxyMessage.CREATE_SUBSYSTEMPROXY,subSystemProxy);
        coreContext.publish(SystemProxyMessage.CREATE_SUBSYSTEMPROXY, systemProxyMessage);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#deleteSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSubSystemProxy(MoNaming subSystemId) {
        SubSystemProxy subSystemProxy=subSystemProxies.remove(subSystemId);
        if(subSystemProxy==null)
            return;
        try{
            subSystemProxy.destory();
        }finally{
            SystemProxyMessage systemProxyMessage=new SystemProxyMessage(SystemProxyMessage.DELETE_SUBSYSTEMPEOXY,subSystemProxy);
            coreContext.publish(SystemProxyMessage.DELETE_SUBSYSTEMPEOXY, systemProxyMessage);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#getSubSystemProxies(java.lang.String)
     */
    @Override
    public List<SubSystemProxy> getSubSystemProxies(String subSystemType) {
        List<SubSystemProxy> results=new ArrayList<SubSystemProxy>();
        for(Entry<MoNaming,SubSystemProxy> entry:subSystemProxies.entrySet()){
            SubSystemProxyProperty subSystemProxyProperty=entry.getValue().getSubSystemProxyProperty();
            if(ObjectUtils.equals(subSystemProxyProperty.getSubSystemType(), subSystemType))
                results.add(entry.getValue());
        }
        return results;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#getBindSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo) {
        MoNaming subSystemId=subSystemBinds.get(mo);
        if(subSystemId==null)
            return null;
        return subSystemProxies.get(subSystemId);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#getSubSystemRequestIntecepter(java.lang.String)
     */
    @Override
    public SubSystemRequestIntercepter getSubSystemRequestIntecepter(String subSystemType) {
        return subSystemRequestIntercepters.get(subSystemType);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#registerSubSystemRequestIntecepter(java.lang.String, com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter)
     */
    @Override
    public void registerSubSystemRequestIntecepter(String subSystemType,SubSystemRequestIntercepter subSystemRequestIntecepter) {
        subSystemRequestIntercepters.put(subSystemType, subSystemRequestIntecepter);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#bindSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy)
     */
    @Override
    public void bindSubSystemProxy(MoNaming mo, MoNaming subSystemId) {
        if(subSystemId==null){
            subSystemBinds.remove(mo);
            return;
        }
        SubSystemProxy subSystemProxy=subSystemProxies.get(subSystemId);
        if(subSystemProxy==null)
            throw new IllegalArgumentException("subsystemproxy is null");
        subSystemBinds.put(mo, subSystemId);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#updateSubSystemProxy(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty)
     */
    @Override
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty) {
        SubSystemProxy subSystemProxy=subSystemProxies.get(subSystemProxyProperty.getSubSystemId());
        subSystemProxy.init(subSystemProxyProperty);
        SystemProxyMessage systemProxyMessage=new SystemProxyMessage(SystemProxyMessage.UPDATE_SUBSYSTEMPROXY,subSystemProxy);
        coreContext.publish(SystemProxyMessage.UPDATE_SUBSYSTEMPROXY, systemProxyMessage);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore#getSubSystemProxyBySubSystemId(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId) {
        return subSystemProxies.get(subSystemId);
    }

}
