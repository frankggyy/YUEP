/*
 * $Id: MoManagerAdapter.java, 2011-4-18 上午11:29:44 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.server.nmsproxy.NmsServiceAdapter;

/**
 * <p>
 * Title: MoManagerAdapter
 * </p>
 * <p>
 * Description:
 * 下级网管MoManager服务适配器，将服务参数中的MoNaming转为下级网管MoNaming
 * </p>
 * 
 * @author yangtao
 * created 2011-4-18 上午11:29:44
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerAdapter extends NmsServiceAdapter<MoManager> implements MoManager {
    
    public MoManagerAdapter(MoNaming subSystemId,MoManager moManager){
         super(subSystemId,moManager);
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.String)
     */
    @Override
    public Mo createManagedDomain(MoNaming parent, String managedDomainName,String type) {
        MoNaming remoteParent=MoNamingFactory.toRemoteMoNaming(nm, parent);
        Mo mo=nmsRemoteService.createManagedDomain(remoteParent, managedDomainName, type);
        mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
        mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
        return mo;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.model.ManagedNodeProperty, java.lang.String)
     */
    @Override
    public Mo createManagedNode(MoNaming parent,ManagedNodeProperty managedNodeProperty, String type) {
        MoNaming remoteParent=MoNamingFactory.toRemoteMoNaming(nm, parent);
        Mo mo=nmsRemoteService.createManagedNode(remoteParent, managedNodeProperty, type);
        mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
        mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
        return mo;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedDomain(MoNaming domainNaming) {
        MoNaming remoteMoNaming=MoNamingFactory.toRemoteMoNaming(nm, domainNaming);
        nmsRemoteService.deleteManagedDomain(remoteMoNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedNode(MoNaming managedNode) {
        MoNaming remoteMoNaming=MoNamingFactory.toRemoteMoNaming(nm, managedNode);
        nmsRemoteService.deleteManagedDomain(remoteMoNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getAllMos()
     */
    @Override
    public List<Mo> getAllMos() {
        List<Mo> mos=nmsRemoteService.getAllMos();
        List<Mo> newMos=new ArrayList<Mo>();
        for(Mo mo:mos){
            mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
            mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
            newMos.add(mo);
        }
        return newMos;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getChildrenMos(com.yuep.nms.core.common.mocore.service.MoFilter, com.yuep.nms.core.common.mocore.naming.MoNaming[])
     */
    @Override
    public List<Mo> getChildrenMos(MoFilter moFilter, MoNaming... parents) {
        MoNaming[] newParents=new MoNaming[parents.length];
        for(int i=0;i<parents.length;i++){
            newParents[i]=MoNamingFactory.toRemoteMoNaming(nm, parents[i]);
        }
        List<Mo> mos=nmsRemoteService.getChildrenMos(moFilter, newParents);
        List<Mo> newMos=new ArrayList<Mo>();
        for(Mo mo:mos){
            mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
            mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
            newMos.add(mo);
        }
        return newMos;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
        MoNaming newNm=MoNamingFactory.toRemoteMoNaming(this.nm, nm);
        ManagedNodeProperty managedNodeProperty=nmsRemoteService.getManagedNodeProperty(newNm);
        managedNodeProperty.setManagedNode(MoNamingFactory.toLocalMoNaming(this.nm, managedNodeProperty.getManagedNode()));
        return managedNodeProperty;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMo(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public Mo getMo(MoNaming moNaming) {
        MoNaming newMoNaming=MoNamingFactory.toRemoteMoNaming(nm, moNaming);
        Mo mo=nmsRemoteService.getMo(newMoNaming);
        mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
        mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
        return mo;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMos(com.yuep.nms.core.common.mocore.service.MoFilter)
     */
    @Override
    public List<Mo> getMos(MoFilter moFilter) {
        List<Mo> mos=nmsRemoteService.getMos(moFilter);
        List<Mo> newMos=new ArrayList<Mo>();
        for(Mo mo:mos){
            mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm, mo.getMoNaming()));
            mo.setParent(MoNamingFactory.toLocalMoNaming(nm, mo.getParent()));
            newMos.add(mo);
        }
        return newMos;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        MoNaming newNm=MoNamingFactory.toRemoteMoNaming(nm, managedNodeProperty.getManagedNode());
        managedNodeProperty.setManagedNode(newNm);
        nmsRemoteService.updateManagedNodeProperty(managedNodeProperty);
    }

}
