/*
 * $Id: SbiManagerImpl.java, 2011-4-15 下午12:39:05 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.sbimanager.exception.SbiManagerException;
import com.yuep.nms.core.common.sbimanager.model.SbiBindProperty;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao;
import com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;

/**
 * <p>
 * Title: SbiManagerImpl
 * </p>
 * <p>
 * Description:
 * SbiManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午12:39:05
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerImpl implements SbiManager {

    private SbiPropertyDao sbiPropertyDao;
    private SbiBindPropertyDao sbiBindPropertyDao;
    private SubSystemProxyManager subSystemProxyManager;
    private ObjectIDGenerateService objectIDGenerateService;
    
    public void setSbiPropertyDao(SbiPropertyDao sbiPropertyDao){
        this.sbiPropertyDao=sbiPropertyDao;
    }
    
    public void setSbiBindPropertyDao(SbiBindPropertyDao sbiBindPropertyDao){
        this.sbiBindPropertyDao=sbiBindPropertyDao;
    }
    
    public void setSubSystemProxyService(SubSystemProxyManager subSystemProxyService){
        this.subSystemProxyManager=subSystemProxyService;
    }
    
    public void setObjectIDGenerateService(ObjectIDGenerateService objectIDGenerateService){
        this.objectIDGenerateService=objectIDGenerateService;
    }
    
    public void init(){
    }
    
    
    public void destory(){
        
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setSbiProperty(com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public void setSbiProperty(SbiProperty sbiProperty) {
        sbiPropertyDao.updateSbiProperty(sbiProperty);
        SubSystemProxyProperty subSystemProperty=toSubSystemProperty(sbiProperty);
        subSystemProxyManager.updateSubSystemProxy(subSystemProperty);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#createSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.sbimanager.model.SbiProperty)
     */
    @Override
    public SbiProperty createSbi(MoNaming nm, SbiProperty sbiProperty) {
        SbiProperty dbSbiProperty=sbiPropertyDao.getSbiProperty(sbiProperty.getSbiName());
        if(dbSbiProperty!=null)
            throw new SbiManagerException(SbiManagerException.SBI_NAME_EXIST,sbiProperty.getSbiName());
       
        int instance=objectIDGenerateService.generateObjectId("sbi").intValue();
        MoNaming sbiNaming=MoNamingFactory.createMoNaming(nm, "sbi", instance);
        sbiProperty.setSbiNaming(sbiNaming);
        sbiPropertyDao.createSbiProperty(sbiProperty);
        
        SubSystemProxyProperty subSystemProperty=toSubSystemProperty(sbiProperty);
        subSystemProxyManager.createSubSystemProxy(subSystemProperty);
        return sbiProperty;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#deleteSbi(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbi(MoNaming sbiNaming) {
        SbiProperty sbiProperty=sbiPropertyDao.getSbiProperty(sbiNaming);
        List<SbiBindProperty> sbiBindProperties=sbiBindPropertyDao.getSbiBindProperties(sbiProperty.getSbiNaming());
        if(CollectionUtils.isNotEmpty(sbiBindProperties))
            throw new SbiManagerException(SbiManagerException.SBI_BIND_NE,sbiProperty.getSbiName());
      
        subSystemProxyManager.deleteSubSystemProxy(sbiProperty.getSbiNaming());
        sbiPropertyDao.deleteSbiProperty(sbiProperty.getSbiNaming());
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getAllSbiProperties(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public List<SbiProperty> getAllSbiProperties(MoNaming nm) {
        return sbiPropertyDao.getAllSbiPropertys();
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#getSbiProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SbiProperty getSbiProperty(MoNaming sbiNaming) {
        return sbiPropertyDao.getSbiProperty(sbiNaming);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.sbimanager.service.SbiManager#setNeToSbi(com.yuep.nms.core.common.mocore.naming.MoNaming, com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void setNeToSbi(MoNaming sbiNaming, MoNaming ne) {
        SbiBindProperty sbiBindProperty=sbiBindPropertyDao.getSbiBindProperty(ne);
        if(sbiBindProperty!=null){
            sbiBindProperty.setSbiNaming(sbiNaming);
            sbiBindPropertyDao.setSbiBindProperty(sbiBindProperty);
        }else{
            sbiBindProperty=new SbiBindProperty();
            sbiBindProperty.setNe(ne);
            sbiBindProperty.setSbiNaming(sbiNaming);
            sbiBindPropertyDao.createSbiBindProperty(sbiBindProperty);
        }
        subSystemProxyManager.setSubSystemProxy(sbiNaming, ne);
    
    }
    
    
    private SubSystemProxyProperty toSubSystemProperty(SbiProperty sbiProperty){
        SubSystemProxyProperty subSystemProperty=new SubSystemProxyProperty();
        subSystemProperty.setIp(sbiProperty.getIp());
        subSystemProperty.setPassword(sbiProperty.getPassword());
        subSystemProperty.setPort(sbiProperty.getPort());
        subSystemProperty.setUserName(sbiProperty.getUserName());
        subSystemProperty.setSubSystemType("sbi");
        subSystemProperty.setSubSystemId(sbiProperty.getSbiNaming());
        return subSystemProperty;
    }

}
