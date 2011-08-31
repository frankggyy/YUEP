/*
 * $Id: NmsMoMessageProcess.java, 2011-4-15 上午10:50:34 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsmanager;

import java.io.Serializable;
import java.util.List;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;

/**
 * <p>
 * Title: NmsMoMessageProcess
 * </p>
 * <p>
 * Description:
 * NmsProxy模块接收Mo模块的网管对象创建、删除、修改等消息，根据这些消息创建、删除、修改NmsProxy。
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 上午10:50:34
 * modified [who date description]
 * check [who date description]
 */
public class NmsManager implements MessageReceiver{
    
    private CoreContext coreContext;
    private SubSystemProxyManager subSystemProxyService;
    private MoCore moCore;
    
    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    public void setSubSystemProxyService(SubSystemProxyManager subSystemProxyService){
        this.subSystemProxyService=subSystemProxyService;
    }
    
    public void init(){
        coreContext.local().subscribe(MoMessage.MO_DELETE, this);
        coreContext.local().subscribe(MoMessage.MO_CREATE, this);
        coreContext.local().subscribe(MoMessage.MANAGEDNODEPROPERTY_UPDATE, this);
    }
    
    
    public void destory(){
        coreContext.local().unsubscribe(MoMessage.MO_DELETE, this);
        coreContext.local().unsubscribe(MoMessage.MO_CREATE, this);
        coreContext.local().unsubscribe(MoMessage.MANAGEDNODEPROPERTY_UPDATE, this);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, Serializable msg) {
        if(MoMessage.MO_DELETE.equals(name)){
            MoMessage moMessage=(MoMessage)msg;
            Mo mo=(Mo)moMessage.getMessageBody();
            if(!mo.getMoNaming().getMoType().equals(MoTypeConstants.NM))
                return;
            deleteSubSystemProxy(mo.getMoNaming());
            deleteChildren(mo.getMoNaming());
        }else if(MoMessage.MO_CREATE.equals(name)){
            MoMessage moMessage=(MoMessage)msg;
            Mo mo=(Mo)moMessage.getMessageBody();
            if(!mo.getMoNaming().getMoType().equals(MoTypeConstants.NM))
                return;
            setSubSystemProxy(mo.getMoNaming());
        }else if(MoMessage.MANAGEDNODEPROPERTY_UPDATE.equals(name)){
            MoMessage moMessage=(MoMessage)msg;
            ManagedNodeProperty managedNodeProperty=(ManagedNodeProperty)moMessage.getMessageBody();
            MoNaming managedNode=managedNodeProperty.getManagedNode();
            if(!managedNode.getMoType().equals(MoTypeConstants.NM))
                return;
            SubSystemProxy subSystemProxy=subSystemProxyService.getSubSystemProxyBySubSystemId(managedNode);
            //和Mo关联的子系统不存在,或者关联的子系统Id和当前管理节点属性不相等,则创建子系统代理
            if(subSystemProxy==null||!subSystemProxy.getSubSystemProxyProperty().getSubSystemId().equals(managedNode)){
                subSystemProxyService.createSubSystemProxy(toSubSystemProperty(managedNodeProperty));
                subSystemProxyService.setSubSystemProxy(managedNode, managedNode);//nmsproxy需要绑定自身
            }else{
                subSystemProxyService.updateSubSystemProxy(toSubSystemProperty(managedNodeProperty));
            }
        }
    }
    
    private void setSubSystemProxy(MoNaming nm){
        MoNaming parentNm=nm.getParentByMoType(MoTypeConstants.NM);
        if(parentNm==null)
            return;
        SubSystemProxy subSystemProxy=subSystemProxyService.getBindSubSystemProxy(parentNm);
        subSystemProxyService.setSubSystemProxy(subSystemProxy.getSubSystemProxyProperty().getSubSystemId(), nm);
    }
    
    private void deleteSubSystemProxy(MoNaming nm){
        subSystemProxyService.deleteSubSystemProxy(nm);
    }
    
    private void deleteChildren(MoNaming nm){
        List<Mo> children=moCore.getChildrenMos(null, nm);
        moCore.deleteMos(children);
    }

    private SubSystemProxyProperty toSubSystemProperty(ManagedNodeProperty managedNodeProperty){
        SubSystemProxyProperty subSystemProperty=new SubSystemProxyProperty();
        subSystemProperty.setIp(managedNodeProperty.getIp());
        subSystemProperty.setPassword(managedNodeProperty.getPassword());
        subSystemProperty.setPort(managedNodeProperty.getPort());
        subSystemProperty.setUserName(managedNodeProperty.getUseName());
        subSystemProperty.setSubSystemId(managedNodeProperty.getManagedNode());
        subSystemProperty.setSubSystemType(MoTypeConstants.NM);
        return subSystemProperty;
    }

}
