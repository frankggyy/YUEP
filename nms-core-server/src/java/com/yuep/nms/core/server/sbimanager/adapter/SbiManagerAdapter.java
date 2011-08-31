/*
 * $Id: SbiManagerAdapter.java, 2011-4-22 上午10:33:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.adapter;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapter;

/**
 * <p>
 * Title: SbiManagerAdapter
 * </p>
 * <p>
 * Description:
 * SbiManager服务下级网管适配器
 * </p>
 * 
 * @author yangtao
 * created 2011-4-22 上午10:33:31
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerAdapter extends NmsServiceAdapter<SbiManager> implements SbiManager {
    
    public SbiManagerAdapter(MoNaming subSystemId,SbiManager sbiManager){
        super(subSystemId,sbiManager);
    }
 
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#createSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public SbiProperty createSbi(MoNaming nm, SbiProperty sbiProperty) {
        MoNaming remoteNm=MoNamingFactory.toRemoteMoNaming(this.nm, nm);
        SbiProperty newSbiProperty=nmsRemoteService.createSbi(remoteNm, sbiProperty);
        sbiProperty.setSbiNaming(MoNamingFactory.toLocalMoNaming(this.nm, newSbiProperty.getSbiNaming()));
        return newSbiProperty;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#deleteSbi(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbi(MoNaming sbiNaming) {
        MoNaming remoteSbiNaming=MoNamingFactory.toRemoteMoNaming(this.nm, sbiNaming);
        nmsRemoteService.deleteSbi(remoteSbiNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getAllSbiProperties(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public List<SbiProperty> getAllSbiProperties(MoNaming nm) {
        MoNaming remoteNm=MoNamingFactory.toRemoteMoNaming(this.nm, nm);
        List<SbiProperty> sbiProperties=nmsRemoteService.getAllSbiProperties(remoteNm);
        for(SbiProperty sbiProperty:sbiProperties){
            sbiProperty.setSbiNaming(MoNamingFactory.toLocalMoNaming(this.nm, sbiProperty.getSbiNaming()));
        }
        return sbiProperties;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getSbiProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SbiProperty getSbiProperty(MoNaming sbiNaming) {
        MoNaming remoteSbiNaming=MoNamingFactory.toRemoteMoNaming(this.nm, sbiNaming);
        SbiProperty sbiProperty=nmsRemoteService.getSbiProperty(remoteSbiNaming);
        sbiProperty.setSbiNaming(MoNamingFactory.toLocalMoNaming(this.nm, sbiProperty.getSbiNaming()));
        return sbiProperty;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setNeToSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void setNeToSbi(MoNaming sbiNaming, MoNaming ne) {
        MoNaming remoteSbiNaming=MoNamingFactory.toRemoteMoNaming(this.nm, sbiNaming);
        MoNaming remoteNe=MoNamingFactory.toRemoteMoNaming(this.nm, ne);
        nmsRemoteService.setNeToSbi(remoteSbiNaming, remoteNe);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setSbiProperty(com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public void setSbiProperty(SbiProperty sbiProperty) {
        sbiProperty.setSbiNaming(MoNamingFactory.toRemoteMoNaming(this.nm, sbiProperty.getSbiNaming()));
        nmsRemoteService.setSbiProperty(sbiProperty);
    }

}
