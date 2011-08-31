/*
 * $Id: SyncMessageAdapter.java, 2011-5-17 下午08:37:07 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.syncmanager.adapter;

import java.io.Serializable;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.synccore.message.SyncMessage;

/**
 * <p>
 * Title: SyncMessageAdapter
 * </p>
 * <p>
 * Description:
 * 下级网管同步消息适配器
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午08:37:07
 * modified [who date description]
 * check [who date description]
 */
public class SyncMessageAdapter implements MessageReceiver {

    private CoreContext coreContext;
    private MoCore moCore;
    
    
    public SyncMessageAdapter(){
        
    }
   
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
        if(this.coreContext!=null)
            System.out.println("setCoreContext");
    }
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
        if(this.moCore!=null)
            System.out.println("setMoCore");
    }
    
    public void init(){
        
    }
    

    public void destory(){
    }

    private void subscribe(SubSystemProxy subSystemProxy,SyncMessageReceiver syncMessageReceiver){
        subSystemProxy.subscribe(SyncMessage.SYNC_MESSAGE, syncMessageReceiver);
    }
    
    private void unsubscribe(SubSystemProxy subSystemProxy,SyncMessageReceiver syncMessageReceiver){
        subSystemProxy.unsubscribe(SyncMessage.SYNC_MESSAGE, syncMessageReceiver);
    }
    
    
    /**
     * (non-Javadoc)
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, Serializable msg) {
    }

    
    private static class SyncMessageReceiver implements MessageReceiver{
        
        private MoNaming nm;
        public SyncMessageReceiver(MoNaming nm){
               this.nm=nm;
        }
        /**
         * 
         * (non-Javadoc)
         * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
         */
        @Override
        public void receive(CommunicateObject co, String name, Serializable msg) {
            System.out.println(nm);
        }
        
    }

}
