/*
 * $Id: ClientMoCore.java, 2011-3-29 下午03:17:15 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.base.def.SystemMessage;
import com.yuep.nms.core.common.mocore.cache.MoCache;
import com.yuep.nms.core.common.mocore.cache.MoNode;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;

/**
 * <p>
 * Title: ClientMoCore
 * </p>
 * <p>
 * Description:
 * MoCore客户端实现类（做了mo的cache）
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午03:17:15
 * modified [who date description]
 * check [who date description]
 */
public class ClientMoCore implements MoCore, MessageReceiver{

    private CoreContext coreContext;
    private MoManager moManager;
    
    /**
     * mo cache
     */
    private final MoCache moCache=new MoCache();
    
    public ClientMoCore(){
        ClientCoreContext.subscribeLocal(SystemMessage.NAME, this);
    }
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    
    public void init(){
        initMoCache();
        initMoMessageMoMesssageReceiver();
    }
    
    /**
     * 初始化MoCore
     * 
     */
    private void initMoCache(){
        List<Mo> mos=moManager.getAllMos();
        moCache.addMos(mos);
    }
    
    /**
     * 初始化客户端Mo消息接收器
     */
    private void initMoMessageMoMesssageReceiver(){
        MoMesssageReceiver moMesssageReceiver=new MoMesssageReceiver();
        ClientCoreContext.subscribeRemote(MoMessage.MO_STATE_CHANGED, moMesssageReceiver);
        ClientCoreContext.subscribeRemote(MoMessage.MO_CREATE, moMesssageReceiver);
        ClientCoreContext.subscribeRemote(MoMessage.MO_ATTR_CHANGED, moMesssageReceiver);
        ClientCoreContext.subscribeRemote(MoMessage.MO_DELETE, moMesssageReceiver);
    }
    
    /**
     * 接收远端的MO消息
     * <p>
     * Title: MoMesssageReceiver
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author sufeng
     */
    private class MoMesssageReceiver implements MessageReceiver{

        @Override
        public void receive(CommunicateObject co, String name, Serializable msg) {
            MoMessage moMessage=(MoMessage)msg;
            Mo mo=(Mo)moMessage.getMessageBody();
            List<Mo> mos=new ArrayList<Mo>();
            mos.add(mo);
            // 做更新cache的操作
            if(ObjectUtils.equals(name, MoMessage.MO_STATE_CHANGED)){
                updateMos(mos);
            }else if(ObjectUtils.equals(name, MoMessage.MO_CREATE)){
                createMos(mos);
            }else if(ObjectUtils.equals(name, MoMessage.MO_ATTR_CHANGED)){
               updateMos(mos);
            }else if(ObjectUtils.equals(name, MoMessage.MO_DELETE)){
                deleteMos(mos);
            }
            coreContext.publish(name, moMessage);
        }
        
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#createMos(java.util.List)
     */
    @Override
    public void createMos(List<Mo> mos) {
        moCache.addMos(mos);
        
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#deleteMos(java.util.List)
     */
    @Override
    public void deleteMos(List<Mo> mos) {
        moCache.deleteMos(mos);
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
        throw new UnsupportedOperationException();
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
     * @see com.yuep.nms.core.common.mocore.service.MoCore#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        throw new UnsupportedOperationException();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoCore#updateMos(java.util.List)
     */
    @Override
    public void updateMos(List<Mo> mos) {
        moCache.updateMos(mos);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, Serializable msg) {
        if(msg instanceof SystemMessage){
            SystemMessage sysMsg=(SystemMessage)msg;
            if(SystemMessage.TYPE_LOGIN.equals(sysMsg.getMessageType())){
                moManager=ClientCoreContext.getRemoteService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
                this.init();
            }
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
