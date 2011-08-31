/*
 * $Id: MoManagerImpl.java, 2011-3-29 上午11:07:11 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.service;

import java.util.List;

import com.yuep.base.exception.YuepException;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.model.MoStaticInfo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManager;
import com.yuep.nms.core.common.momanager.exception.MoManagerException;
import com.yuep.nms.core.common.momanager.service.MoManager;

/**
 * <p>
 * Title: MoManagerImpl
 * </p>
 * <p>
 * Description:
 * MoManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 上午11:07:11
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerImpl implements MoManager {

    private MoCore moCore;
    private MoStaticInfoManager moStaticInfoManager;
    private ObjectIDGenerateService objectIDGenerateService;
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    public void setMoStaticInfoManager(MoStaticInfoManager moStaticInfoManager){
        this.moStaticInfoManager=moStaticInfoManager;
    }
    
    public void setObjectIDGenerateService(ObjectIDGenerateService objectIDGenerateService){
        this.objectIDGenerateService=objectIDGenerateService;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.String)
     */
    @Override
    public Mo createManagedDomain(MoNaming parent, String managedDomainName, String type) {
        MoStaticInfo moStaticInfo=  moStaticInfoManager.getMoStaticInfo(type);
        //没有发现静态配置文件
        if(moStaticInfo==null)
            throw new MoManagerException(MoManagerException.STATIC_CONFIG_NOTFOUND,type);
        //是否能够创建
        if(parent!=null&&!accept(parent,type))
            throw new MoManagerException(MoManagerException.PARENT_NOTACCEPT_CHILD,type);
        String moType=moStaticInfo.getKind();
        Long id=objectIDGenerateService.generateObjectId(moType);
        
        MoNaming moNaming=MoNamingFactory.createMoNaming(parent, moType,id.intValue());
        Mo mo=new Mo();
        mo.setMoNaming(moNaming);
        mo.setType(type);
        mo.setActualtype(type);
        mo.setDisplayName(managedDomainName);
        mo.setParent(parent);
        
        moCore.createMos(YuepObjectUtils.newArrayList(mo));
        
        return mo;
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#createManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.model.ManagedNodeProperty, java.lang.String)
     */
    @Override
    public Mo createManagedNode(MoNaming parent,ManagedNodeProperty managedNodeProperty, String type) {
        
        MoStaticInfo moStaticInfo=  moStaticInfoManager.getMoStaticInfo(type);
        //没有发现静态配置文件
        if(moStaticInfo==null)
            throw new MoManagerException(MoManagerException.STATIC_CONFIG_NOTFOUND,type);
        //是否能够创建
        if(parent!=null&&!accept(parent,type))
            throw new MoManagerException(MoManagerException.PARENT_NOTACCEPT_CHILD,type);
        String moType=moStaticInfo.getKind();
        Long id=objectIDGenerateService.generateObjectId(moType);
        
      
        Mo mo=new Mo();
        MoNaming moNaming=MoNamingFactory.createMoNaming(parent, moType,id.intValue());
        mo.setMoNaming(moNaming);
        mo.setType(moType);
        if(managedNodeProperty!=null)
            mo.setDisplayName(managedNodeProperty.getIp());
        mo.setParent(parent);
        
        moCore.createMos(YuepObjectUtils.newArrayList(mo));
        if(managedNodeProperty==null)
            return mo;
        managedNodeProperty.setManagedNode(mo.getMoNaming());
        try{
            moCore.updateManagedNodeProperty(managedNodeProperty);
        }catch(YuepException ex){
            moCore.deleteMos(YuepObjectUtils.newArrayList(mo)); 
            throw ex;
        }
        
        return mo;
    }

    
    private boolean accept(MoNaming parent,String type){
        Mo parentMo=moCore.getMo(parent);
        String parentType=parentMo.getType();
        return moStaticInfoManager.accept(parentType, type);
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedDomain(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedDomain(MoNaming domainNaming) {
        Mo mo=moCore.getMo(domainNaming);
        if(mo==null)
            return;
         moCore.deleteMos(YuepObjectUtils.newArrayList(mo));
     }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#deleteManagedNode(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagedNode(MoNaming nm) {
       Mo mo=moCore.getMo(nm);
       if(mo==null)
           return;
        moCore.deleteMos(YuepObjectUtils.newArrayList(mo));
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getAllMos()
     */
    @Override
    public List<Mo> getAllMos() {
        return moCore.getAllMos();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getChildrenMos(com.yuep.nms.core.common.mocore.service.MoFilter, com.yuep.nms.core.common.mocore.naming.MoNaming[])
     */
    @Override
    public List<Mo> getChildrenMos(MoFilter moFilter, MoNaming... parents) {
        return moCore.getChildrenMos(moFilter, parents);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
        return moCore.getManagedNodeProperty(nm);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMo(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public Mo getMo(MoNaming moNaming) {
        return moCore.getMo(moNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#getMos(com.yuep.nms.core.common.mocore.service.MoFilter)
     */
    @Override
    public List<Mo> getMos(MoFilter moFilter) {
        return moCore.getMos(moFilter);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.momanager.service.MoManager#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        moCore.updateManagedNodeProperty(managedNodeProperty);
    }

}
