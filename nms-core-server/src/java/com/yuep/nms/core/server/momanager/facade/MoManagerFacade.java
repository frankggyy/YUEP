/*
 * $Id: MoManagerFacade.java, 2011-4-20 下午08:33:27 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.facade;

import java.util.List;

import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;

/**
 * <p>
 * Title: MoManagerFacade
 * </p>
 * <p>
 * Description:面向用户的MoManager实现类，主要实现根据 MoNaming的服务转发
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 下午08:33:27
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerFacade implements MoManager {

    private SubSystemProxyCore subSystemProxyCore;
    private MoCore moCore;
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.String)
     */
    @Override
    public Mo createManagedDomain(MoNaming parent, String domainName,String type) {
        MoNaming nm=null;
        if(parent!=null)
          nm=parent.getMoNamingByMoType(MoTypeConstants.NM);
        else
          nm=moCore.getRootMo().getMoNaming();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        MoManager moManager=subSystemProxy.getService(nm, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.createManagedDomain(parent, domainName, type);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.model.ManagedNodeProperty, java.lang.String)
     */
    @Override
    public Mo createManagedNode(MoNaming parent,ManagedNodeProperty managedNodeProperty, String type) {
        MoNaming nm=null;
        if(parent!=null)
          nm=parent.getMoNamingByMoType(MoTypeConstants.NM);
        else
          nm=moCore.getRootMo().getMoNaming();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        MoManager moManager=subSystemProxy.getService(nm, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.createManagedNode(parent, managedNodeProperty, type);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedDomain(MoNaming domainNaming) {
        MoNaming nm=domainNaming.getParentByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        MoManager moManager=subSystemProxy.getService(nm, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        moManager.deleteManagedDomain(domainNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedNode(MoNaming nm) {
        MoNaming subSystemMoNaming=nm.getParentByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(subSystemMoNaming);
        MoManager moManager=subSystemProxy.getService(subSystemMoNaming, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        moManager.deleteManagedNode(nm);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getAllMos()
     */
    @Override
    public List<Mo> getAllMos() {
        Mo rootMo=moCore.getRootMo();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(rootMo.getMoNaming());
        MoManager moManager=subSystemProxy.getService(rootMo.getMoNaming(), MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.getAllMos();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getChildrenMos(com.yuep.nms.core.common.mocore.service.MoFilter, com.yuep.nms.core.common.mocore.naming.MoNaming[])
     */
    @Override
    public List<Mo> getChildrenMos(MoFilter moFilter, MoNaming... parents) {
        Mo rootMo=moCore.getRootMo();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(rootMo.getMoNaming());
        MoManager moManager=subSystemProxy.getService(rootMo.getMoNaming(), MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.getChildrenMos(moFilter, parents);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
        MoNaming subSystemMoNaming=nm.getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(subSystemMoNaming);
        MoManager moManager=subSystemProxy.getService(subSystemMoNaming, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.getManagedNodeProperty(nm);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMo(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public Mo getMo(MoNaming moNaming) {
        Mo rootMo=moCore.getRootMo();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(rootMo.getMoNaming());
        MoManager moManager=subSystemProxy.getService(rootMo.getMoNaming(), MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.getMo(moNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMos(com.yuep.nms.core.common.mocore.service.MoFilter)
     */
    @Override
    public List<Mo> getMos(MoFilter moFilter) {
        Mo rootMo=moCore.getRootMo();
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(rootMo.getMoNaming());
        MoManager moManager=subSystemProxy.getService(rootMo.getMoNaming(), MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        return moManager.getMos(moFilter);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        MoNaming subSystemMoNaming=managedNodeProperty.getManagedNode().getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(subSystemMoNaming);
        MoManager moManager=subSystemProxy.getService(subSystemMoNaming, MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
        moManager.updateManagedNodeProperty(managedNodeProperty);
    }

}
