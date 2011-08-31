/*
 * $Id: MoMessageAdapter.java, 2011-4-19 上午09:58:33 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.message.SystemProxyMessage;

/**
 * <p>
 * Title: MoMessageAdapter
 * </p>
 * <p>
 * Description:
 * 下级网管Mo消息适配器
 * </p>
 * 
 * @author yangtao
 * created 2011-4-19 上午09:58:33
 * modified [who date description]
 * check [who date description]
 */
public class MoMessageAdapter implements MessageReceiver {

    private CoreContext coreContext;
    private MoCore moCore;
    private SubSystemProxyCore subSystemProxyCore;
    
    public void setCoreContext(CoreContext coreContext){
           this.coreContext=coreContext;
    }

    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
        this.subSystemProxyCore=subSystemProxyCore;
    }
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    public void init(){
        List<SubSystemProxy> subSystemProxies=subSystemProxyCore.getSubSystemProxies(MoTypeConstants.NM);
        for(SubSystemProxy subSystemProxy:subSystemProxies){
            if(subSystemProxy.getSubSystemProxyProperty().getSubSystemId().equals(moCore.getRootMo().getMoNaming()))
                continue;
            subscribe(subSystemProxy,new NmMessageReceiver(subSystemProxy.getSubSystemProxyProperty().getSubSystemId()));
        }
        coreContext.local().subscribe(SystemProxyMessage.CREATE_SUBSYSTEMPROXY, this);
        coreContext.local().subscribe(SystemProxyMessage.DELETE_SUBSYSTEMPEOXY, this);
    }
    
    
    
    public void destroy(){
        coreContext.local().unsubscribe(SystemProxyMessage.CREATE_SUBSYSTEMPROXY, this);
        coreContext.local().unsubscribe(SystemProxyMessage.DELETE_SUBSYSTEMPEOXY, this); 
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, Serializable msg) {
        if(ObjectUtils.equals(name, SystemProxyMessage.CREATE_SUBSYSTEMPROXY)){
            SystemProxyMessage systemProxyMessage=(SystemProxyMessage)msg;
            SubSystemProxy subSystemProxy=(SubSystemProxy)systemProxyMessage.getMessageSource();
            if(subSystemProxy.getSubSystemProxyProperty().getSubSystemId().equals(moCore.getRootMo().getMoNaming()))
                return ;
            SubSystemProxyProperty subSystemProxyProperty=(SubSystemProxyProperty)subSystemProxy.getSubSystemProxyProperty();
            if(!subSystemProxyProperty.getSubSystemType().equals(MoTypeConstants.NM))
                return;
            NmMessageReceiver nmMessageReceiver=new NmMessageReceiver(subSystemProxyProperty.getSubSystemId());
            subscribe(subSystemProxy,nmMessageReceiver);
        }
    }

    
    private void subscribe(SubSystemProxy subSystemProxy,NmMessageReceiver nmMessageReceiver){
        subSystemProxy.subscribe(MoMessage.MO_CREATE, nmMessageReceiver);
        subSystemProxy.subscribe(MoMessage.MO_DELETE, nmMessageReceiver);
        subSystemProxy.subscribe(MoMessage.MO_ATTR_CHANGED, nmMessageReceiver);
        subSystemProxy.subscribe(MoMessage.MO_STATE_CHANGED, nmMessageReceiver);
    }
    
  
    /**
     * 
     * <p>
     * Title: NmMessageReceiver
     * </p>
     * <p>
     * Description:下级网管Mo消息接收
     * </p>
     * 
     * @author yangtao
     * @created 2011-8-2 上午10:11:53
     * @modified [who date description]
     * @check [who date description]
     */
    private  class NmMessageReceiver implements MessageReceiver{
        
        private MoNaming nm;
        
        public NmMessageReceiver(MoNaming nm){
               this.nm=nm;
        }
        
        @Override
        public void receive(CommunicateObject co, String name, Serializable msg) {
            MoMessage moMessage=(MoMessage)msg;
            Mo mo=(Mo)moMessage.getMessageBody();
            mo.setMoNaming(MoNamingFactory.toLocalMoNaming(nm,mo.getMoNaming()));
            mo.setParent(MoNamingFactory.toLocalMoNaming(nm,mo.getParent()));
            List<Mo> mos=new ArrayList<Mo>();
            mos.add(mo);
            if(ObjectUtils.equals(name, MoMessage.MO_CREATE)){
                moCore.createMos(mos);
            }else if(ObjectUtils.equals(name, MoMessage.MO_DELETE)){
                moCore.deleteMos(mos);
            }else if(ObjectUtils.equals(name, MoMessage.MO_STATE_CHANGED)||ObjectUtils.equals(name, MoMessage.MO_ATTR_CHANGED)){
                moCore.updateMos(mos);
            }
           
        }
    }
}
