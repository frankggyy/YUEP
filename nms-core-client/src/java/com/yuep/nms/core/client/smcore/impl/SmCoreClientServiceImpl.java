/*
 * $Id: SmCoreClientServiceImpl.java, 2011-3-29 ����11:17:22 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.mainframe.action.system.SystemExitAction;
import com.yuep.core.client.permit.AccessCheck;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.client.smcore.def.UserCustomChangeListener;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingGetter;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.model.UserCustom;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: SmCoreClientServiceImpl
 * </p>
 * <p>
 * Description: sm core clientģ������ṩ�ı��ط���ʵ��
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����11:17:22
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreClientServiceImpl implements SmCoreClientService {

    private Map<MoNaming,Set<String>> permCache=new ConcurrentHashMap<MoNaming, Set<String>>();
    private Map<String,String> action2Permissions=new ConcurrentHashMap<String, String>();
    private UserCustom currentUserCustom;
    private List<UserCustomChangeListener> listeners=new ArrayList<UserCustomChangeListener>();
    private String loginedUser;
    private Long currentSessionId;
    private MoNaming[] rootMoNamings=new MoNaming[1];

    @Override
    public void initAfterLogin(String userName){
        this.loginedUser=userName;
        this.currentSessionId=ClientCoreContext.getSessionId();
        
        MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        rootMoNamings[0] = moCore.getRootMo().getMoNaming();
        
        // ��ȡ��ǰ�û���Ȩ����
        SmManagerService smManager=ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        Map<MoNaming, Set<String>> moPermTree = smManager.getPermissionTree(this.loginedUser);
        if(moPermTree!=null)
            permCache.putAll(moPermTree);
        
        // ��ȡactionid��Ӧ��Ȩ�޹�ϵ��
        Map<String, String> actionOrFns = smManager.getAllActionOrFunctions();
        if(actionOrFns!=null)
            action2Permissions.putAll(actionOrFns);
        
        // ��ȡ��ǰ�û��ĸ��Ի���Ϣ
        currentUserCustom=smManager.getUserCustom(this.loginedUser);
        
        // ע��У��ӿڵ�client core
        AccessCheck accessCheck=new AccessCheck(){
            @Override
            public boolean check(String actionId, Object... objectIds) {
                if(StringUtils.isEmpty(actionId))
                    return true;
                if(objectIds instanceof MoNaming[]){
                    return SmCoreClientServiceImpl.this.check(actionId, (MoNaming[])objectIds);
                }else if(objectIds!=null && objectIds.length>0){
                    if(objectIds[0] instanceof MoNaming){
                        MoNaming[] namings=new MoNaming[objectIds.length];
                        int i=0;
                        for(Object obj: objectIds){
                            namings[i]=(MoNaming)obj;
                            i++;
                        }
                        return SmCoreClientServiceImpl.this.check(actionId, namings);
                    }else if(objectIds[0] instanceof MoNamingGetter){
                        MoNaming[] namings=new MoNaming[objectIds.length];
                        int i=0;
                        for(Object obj: objectIds){
                            namings[i]=((MoNamingGetter)obj).getMoNaming();
                            i++;
                        }
                        return SmCoreClientServiceImpl.this.check(actionId, namings);
                    }
                    return false;
                }
                // �������:��action�����κ�mo����,��Ϊ��EMS Server��MO
                return SmCoreClientServiceImpl.this.check(actionId, rootMoNamings);
            }  
        };
        ClientCoreContext.setAccessCheck(accessCheck);
        
        //����MO Message
        addMoChangedListener();
        
        //����SM Message
        addSmMessageListener();
    }
    
    @Override
    public String getCurrentUser() {
        return this.loginedUser;
    }

    @Override
    public boolean check(String actionId, MoNaming... moNamings) {
        boolean accessable = checkPermission(actionId, moNamings);
        if(!accessable && "admin".equals(loginedUser)){
            // only for debug
            System.out.println("SmCoreClientServiceImpl:admin have no permission,actionId="+actionId);
        }
        return accessable;
    }
    
    private boolean checkPermission(String actionId, MoNaming... moNamings){
        String perm = action2Permissions.get(actionId);
        if(perm==null)
            return false;
        for(MoNaming moNaming : moNamings){
            if(moNaming==null)
                continue;
            Set<String> permMap = permCache.get(moNaming);
            if(permMap==null)
                return false;
            if(!permMap.contains(perm))
                return false;
        }
        return true;
    }

    @Override
    public String getUserCustom(String propKey) {
        Map<String, String> customInfo = currentUserCustom.getCustomInfo();
        if(customInfo==null)
            return null;
        return customInfo.get(propKey);
    }

    @Override
    public void registerUserCustomChangeListener(UserCustomChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void setUserCustom(String propKey, String value) {
        // ���µ�server
        SmManagerService smManager=ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        smManager.setUserCustom(this.loginedUser, propKey, value);
        
        // ����client cache
        if(this.loginedUser.equals(currentUserCustom.getUserName())){
            Map<String, String> customInfo = currentUserCustom.getCustomInfo();
            if(customInfo==null){
                customInfo=new HashMap<String, String>();
                currentUserCustom.setCustomInfo(customInfo);
            }
            customInfo.put(propKey, value);
        }
    }

    @Override
    public void unregisterUserCustomChangeListener(UserCustomChangeListener listener) {
        listeners.remove(listener);
        // TODO��Ҫ�Ż�
    }
    
    /**
     * ����mo�仯����Ϣ,mo�����ӣ�ɾ��ʱҲ��Ҫ��user��Ȩ��cache���б仯
     */
    private void addMoChangedListener(){
        ClientCoreContext.subscribeLocal(MoMessage.MO_CREATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreClientModule.isUnmanagedMo(moNaming)) //�忨,�˿ڵȲ��ɹ����moֱ�ӹ��˵�
                    return;
                
                // ��ǰmo�븸��mo��Ȩ��һ��
                MoNaming parent = mo.getParent();
                Set<String> parentPerms = permCache.get(parent);
                if(parentPerms!=null)
                    permCache.put(moNaming, parentPerms);
            }
        });
        
        ClientCoreContext.subscribeLocal(MoMessage.MO_DELETE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreClientModule.isUnmanagedMo(moNaming)) //�忨,�˿ڵȲ��ɹ����moֱ�ӹ��˵�
                    return;
                permCache.remove(moNaming);
            }
        });
    }
    
    private void addSmMessageListener(){
        ClientCoreContext.subscribeLocal(SmMessage.NAME, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                SmMessage smMsg=(SmMessage)msg;
                Serializable body = smMsg.getMessageBody();
                if(SmMessage.TYPE_CUSTOM_CHANGED.equals(smMsg.getMessageType())){
                    UserCustom custom=(UserCustom)body;
                    if(!loginedUser.equals(custom.getUserName())) //���ǵ�ǰ�û�����Ϣ��ignored
                        return;
                    currentUserCustom=custom;
                    for(UserCustomChangeListener listener : listeners)
                        listener.update(custom);
                }else if(SmMessage.TYPE_CUSTOM_DELETED.equals(smMsg.getMessageType())){
                    String msgUserName=(String)body;
                    if(!loginedUser.equals(msgUserName))
                        return;
                    currentUserCustom=null;
                    for(UserCustomChangeListener listener : listeners)
                        listener.clearAll();
                }else if(SmMessage.TYPE_USER_PERMISSION_CHANGED.equals(smMsg.getMessageType())){
                    // Ȩ�ޱ���
                    String msgUserName=(String)body;
                    if(!loginedUser.equals(msgUserName))
                        return;
                    ClientCoreContext.getOutputManager().addSystemMessage(ClientCoreContext.getString(SmMessage.TYPE_USER_PERMISSION_CHANGED));
                }else if(SmMessage.TYPE_USER_KICK_OUT.equals(smMsg.getMessageType())){
                    if(body instanceof Long){
                        Long msgSessionId=(Long)body;
                        if(msgSessionId.equals(currentSessionId)){
                            // �ѱ��߳�
                            kickOff();
                        }
                    }else if(body instanceof String){
                        String msgUserName=(String)body;
                        if(loginedUser.equals(msgUserName)){
                            // �ѱ��߳�
                            kickOff();
                        }
                    }
                }
            }
        });
    }
    
    private void kickOff(){
        SystemExitAction systemExitAction = new SystemExitAction(SystemExitAction.class.getSimpleName());
        systemExitAction.actionPerformed(null);
    }
    
    @Override
    public Long getCurrentSessionId() {
        return currentSessionId;
    }
    
}
