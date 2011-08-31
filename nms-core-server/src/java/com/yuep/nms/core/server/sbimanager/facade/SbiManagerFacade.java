/*
 * $Id: SbiManagerFacade.java, 2011-4-22 上午09:21:02 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.facade;

import java.util.List;

import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;

/**
 * <p>
 * Title: SbiManagerFacade
 * </p>
 * <p>
 * Description:
 * SbiManagerFacade用于实现本级网管和下级网管服务的派发
 * </p>
 * 
 * @author yangtao
 * created 2011-4-22 上午09:21:02
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerFacade implements SbiManager { 
    private SubSystemProxyCore subSystemProxyCore;
    
    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#createSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public SbiProperty createSbi(MoNaming nm, SbiProperty sbiProperty) {
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        return sbiManager.createSbi(nm, sbiProperty);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#deleteSbi(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbi(MoNaming sbiNaming) {
        MoNaming nm=sbiNaming.getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        sbiManager.deleteSbi(sbiNaming);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getAllSbiProperties(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public List<SbiProperty> getAllSbiProperties(MoNaming nm) {
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        return sbiManager.getAllSbiProperties(nm);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getSbiProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SbiProperty getSbiProperty(MoNaming sbiNaming) {
        MoNaming nm=sbiNaming.getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        return sbiManager.getSbiProperty(sbiNaming);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setNeToSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void setNeToSbi(MoNaming sbiNaming, MoNaming ne) {
        MoNaming nm=sbiNaming.getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        sbiManager.setNeToSbi(sbiNaming, ne);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setSbiProperty(com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public void setSbiProperty(SbiProperty sbiProperty) {
        MoNaming nm=sbiProperty.getSbiNaming().getMoNamingByMoType(MoTypeConstants.NM);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        SbiManager sbiManager=subSystemProxy.getService(nm, SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
        sbiManager.setSbiProperty(sbiProperty);
    }
  
    
       
     
}
