/*
 * $Id: ServerMoCore.java, 2011-3-28 上午11:58:26 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.base.def.ValueCompareObject;
import com.yuep.nms.core.common.mocore.cache.MoCache;
import com.yuep.nms.core.common.mocore.cache.MoNode;
import com.yuep.nms.core.common.mocore.exception.MoException;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao;
import com.yuep.nms.core.server.mocore.dao.MoDao;

/**
 * <p>
 * Title: ServerMoCore
 * </p>
 * <p>
 * Description:
 * 服务端MoCore实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:58:26
 * modified [who date description]
 * check [who date description]
 */
public class ServerMoCore implements MoCore {

    private MoDao moDao;
    private ManagedNodePropertyDao managedNodePropertyDao;
    private CoreContext coreContext;
    private final MoCache moCache=new MoCache();
    
    public ServerMoCore(){
        
    }
    
    public void init(){
       List<Mo> allMos=moDao.getAllMos();
       moCache.addMos(allMos);
    }
    
    
    public void setMoDao(MoDao moDao){
        this.moDao=moDao;
    }
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    
    public void setManagedNodePropertyDao(ManagedNodePropertyDao managedNodePropertyDao){
        this.managedNodePropertyDao=managedNodePropertyDao;
    }
    
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#createMos(java.util.List)
     */
    @Override
    public void createMos(List<Mo> mos) {
        moDao.createMos(mos);
        moCache.addMos(mos);
        sendMoMessage(MoMessage.MO_CREATE,null,mos.toArray(new Mo[0]));
     
    }

   
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#deleteMos(java.util.List)
     */
    @Override
    public void deleteMos(List<Mo> mos) {
        moDao.deleteMos(mos);
        moCache.deleteMos(mos);
        sendMoMessage(MoMessage.MO_DELETE,null,mos.toArray(new Mo[0]));
        for(Mo mo:mos)//删除管理节点连接属性
            managedNodePropertyDao.deleteManagerNodeProperty(mo.getMoNaming());
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getAllMos()
     */
    @Override
    public List<Mo> getAllMos() {
        return moCache.getAllMos();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getChildrenMos(com.yuep.nms.core.common.mocore.service.MoFilter, com.yuep.nms.core.common.mocore.naming.MoNaming[])
     */
    @Override
    public List<Mo> getChildrenMos(MoFilter moFilter, MoNaming... parents) {
        List<Mo> mos=moCache.getChildren(parents);
        if(moFilter==null)
            return mos;
        List<Mo> results=new ArrayList<Mo>();
        for(Mo mo:mos){
            if(moFilter.accept(mo))
                results.add(mo);
        }
        return results;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
        return managedNodePropertyDao.getManagedNodeProperty(nm);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getMo(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public Mo getMo(MoNaming moNaming) {
        return moCache.getMo(moNaming);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getMos(com.yuep.nms.core.common.mocore.service.MoFilter)
     */
    @Override
    public List<Mo> getMos(MoFilter moFilter) {
        List<Mo> mos=moCache.getAllMos();
        List<Mo> results=new ArrayList<Mo>();
        for(Mo mo:mos){
            if(moFilter.accept(mo))
                results.add(mo);
        }
        return results;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        ManagedNodeProperty dbManagedNodeProperty=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getManagedNode());
        ManagedNodeProperty temp=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getIp());
        if(dbManagedNodeProperty!=null){
          if(temp!=null&&!temp.getManagedNode().equals(dbManagedNodeProperty.getManagedNode()))
               throw new MoException(MoException.IP_EXIST,managedNodeProperty.getIp());
            managedNodePropertyDao.updateManagedNodeProperty(managedNodeProperty);
        }
        else{
          if(temp!=null)
             throw new MoException(MoException.IP_EXIST,managedNodeProperty.getIp());
           managedNodePropertyDao.createManagerNodeProperty(managedNodeProperty);
        }
        sendMoMessage(MoMessage.MANAGEDNODEPROPERTY_UPDATE,null,managedNodeProperty);
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#updateMos(java.util.List)
     */
    @Override
    public void updateMos(List<Mo> mos) {
        Map<MoNaming,Map<String,Serializable>> moAttrAdditions=new HashMap<MoNaming,Map<String,Serializable>>();
        Map<MoNaming,Map<String,Serializable>> moStateAdditions=new HashMap<MoNaming,Map<String,Serializable>>();
        for(Mo mo:mos){
            Mo rawMo=this.getMo(mo.getMoNaming());
            Map<String, List<Object>> differentProperties=YuepObjectUtils.getDifferentProperities(rawMo, mo);
            for(Entry<String,List<Object>> entry:differentProperties.entrySet()){
                String name=entry.getKey();
                List<Object> objects=entry.getValue();
                ValueCompareObject valueCompareObject=new ValueCompareObject();
                valueCompareObject.setName(name);
                valueCompareObject.setOldValue(objects.get(0));
                valueCompareObject.setNewValue(objects.get(1));
                if(ObjectUtils.equals("syncstate", name)||ObjectUtils.equals("linkstate", name)){
                   if(!moStateAdditions.containsKey(rawMo.getMoNaming()))
                       moStateAdditions.put(rawMo.getMoNaming(), new HashMap<String,Serializable>());
                   moStateAdditions.get(rawMo.getMoNaming()).put(name, valueCompareObject);
                }else{
                    if(!moAttrAdditions.containsKey(rawMo.getMoNaming()))
                        moAttrAdditions.put(rawMo.getMoNaming(), new HashMap<String,Serializable>());
                    moAttrAdditions.get(rawMo.getMoNaming()).put(name, valueCompareObject);
                }
            }
        }
        moDao.updateMos(mos);
        moCache.updateMos(mos);
        for(Mo mo:mos){
            if(moAttrAdditions.containsKey(mo.getMoNaming())){
                sendMoMessage(MoMessage.MO_ATTR_CHANGED,moAttrAdditions.get(mo.getMoNaming()),mo);
            }
            
            if(moStateAdditions.containsKey(mo.getMoNaming())){
                sendMoMessage(MoMessage.MO_STATE_CHANGED,moStateAdditions.get(mo.getMoNaming()),mo);
            }
        }
    }
    
    
    private void sendMoMessage(String moMessageType,Map<String,Serializable> additions,Serializable... objs){
        for(Serializable obj:objs){
            MoMessage moMessage=new MoMessage(obj,moMessageType,additions);
            coreContext.publish(moMessageType, moMessage);
        }
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getMoNode(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public MoNode getMoNode(MoNaming mo) {
        return moCache.getMoNode(mo);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#getRootMo()
     */
    @Override
    public Mo getRootMo() {
       return moCache.getRootMo();
    }

}
