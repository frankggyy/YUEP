/*
 * $Id: SubSystemProxyManagerImpl.java, 2011-4-26 下午03:17:13 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.service;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemBind;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.server.subsystemproxycore.SubSystemProxyImpl;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemBindDao;
import com.yuep.nms.core.server.subsystemproxymanager.property.dao.SubSystemProxyPropertyDao;

/**
 * <p>
 * Title: SubSystemProxyManagerImpl
 * </p>
 * <p>
 * Description:
 * 子系统代理服务接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-26 下午03:17:13
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyManagerImpl implements SubSystemProxyManager {
    private SubSystemProxyCore subSystemProxyCore;
    private CoreContext coreContext;
    private SubSystemProxyPropertyDao subSystemProxyPropertyDao;
    private SubSystemBindDao subSystemBindsDao;
    
    
    public void init(){
       List<SubSystemProxyProperty> systemProxyProperties=subSystemProxyPropertyDao.getAllSubSystemProxyProperties();
       if(CollectionUtils.isEmpty(systemProxyProperties))
           return;
       for(SubSystemProxyProperty subSystemProxyProperty:systemProxyProperties)
           newSubSystemProxy(subSystemProxyProperty);
       
       List<SubSystemBind> subSystemBinds=subSystemBindsDao.getAllSubSystemBinds();
       for(SubSystemBind subSystemBind:subSystemBinds)
           subSystemProxyCore.bindSubSystemProxy(subSystemBind.getManagedNode(), subSystemBind.getSubSystemId());
    }
    
    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    
    public void setSubSystemProxyPropertyDao(SubSystemProxyPropertyDao subSystemProxyPropertyDao){
        this.subSystemProxyPropertyDao=subSystemProxyPropertyDao;
    }
    
    public void setSubSystemBindsDao(SubSystemBindDao subSystemBindsDao){
        this.subSystemBindsDao=subSystemBindsDao;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#createSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty, java.lang.Class)
     */
    @Override
    public void createSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty) {
       try {
           subSystemProxyPropertyDao.createSubSystemProxyProperty(subSystemProxyProperty);
           newSubSystemProxy(subSystemProxyProperty);
       } catch (Exception ex) {
            throw new YuepException(ex);
        } 
    }
    
    private SubSystemProxy newSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty){
        SubSystemProxyImpl subSystemProxy=new SubSystemProxyImpl();
        subSystemProxy.setCoreContext(coreContext);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        subSystemProxy.init(subSystemProxyProperty);
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        return subSystemProxy;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#deleteSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSubSystemProxy(MoNaming subSystemId) {
        subSystemProxyCore.deleteSubSystemProxy(subSystemId);
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#setSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void setSubSystemProxy(MoNaming subSystemId, MoNaming mo) {
        if(subSystemId!=null){
            subSystemBindsDao.setSubSystemBind(subSystemId, mo);
        }else{
            subSystemBindsDao.deleteSubSystemBind(null, mo);
        }
        subSystemProxyCore.bindSubSystemProxy(mo, subSystemId);
       
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#updateSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty)
     */
    @Override
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty) {
        subSystemProxyPropertyDao.updateSubSystemProxyProperty(subSystemProxyProperty);
        subSystemProxyCore.updateSubSystemProxy(subSystemProxyProperty);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#getBindSubSystemProxy(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo) {
        return subSystemProxyCore.getBindSubSystemProxy(mo);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager#getSubSystemProxyBySubSystemId(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId) {
        return subSystemProxyCore.getSubSystemProxyBySubSystemId(subSystemId);
    }


}
