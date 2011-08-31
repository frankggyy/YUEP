/*
 * $Id: UserPermissionTree.java, 2011-4-18 ����05:19:37 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.cache.MoNode;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.mgmtscope.MgmtScopeManager;

/**
 * <p>
 * Title: UserPermissionTree
 * </p>
 * <p>
 * Description: �����û�moȨ�ޣ�����һ��user-mo-permission��tree cache�����ı仯��Ҫ���µ����cache��
 * </p>
 * 
 * @author sufeng
 * created 2011-4-18 ����05:19:37
 * modified [who date description]
 * check [who date description]
 */
public class UserPermissionTree {

    private MgmtScopeManager mgmtScopeManager;
    //private UserManager userManager;
    private PermGroupManager permGroupManager;
    private MoPermGroupManager moPermGroupManager;
    private MoCore moCore;
    
    /**
     * ���������߳�ͬ��
     */
    private Object monitor=new Object();
    
    /**
     * �Ƿ��ʼ����
     */
    private boolean hasInited=false;
    
    /**
     * ÿ��user����ÿ��mo��Ȩ����
     */
    private Map<String,Map<MoNaming,Set<String>>> userMoPerms=new ConcurrentHashMap<String, Map<MoNaming,Set<String>>>();
    
    public void setMgmtScopeManager(MgmtScopeManager mgmtScopeManager) {
        this.mgmtScopeManager = mgmtScopeManager;
    }
    //public void setUserManager(UserManager userManager) {
    //    this.userManager = userManager;
    //}
    public void setPermGroupManager(PermGroupManager permGroupManager) {
        this.permGroupManager = permGroupManager;
    }
    public void setMoPermGroupManager(MoPermGroupManager moPermGroupManager) {
        this.moPermGroupManager = moPermGroupManager;
    }

    /**
     * �û���¼/user��rolelist�仯/role��permissiongroup�仯ʱ����user-mo-perm��ʼ��
     */
    public void buildUserPermissionTree(User user){
        synchronized (monitor) {
            if(!hasInited){
                moCore=SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
                addMoChangedListener();
                hasInited=true;
            }
        }

        // 1.user������role list
        if(user.getRoles()==null)
            return;
        
        // 2,ÿ��Ȩ�޼�������permission item : <permissionGroup,PermissionID list>
        Map<String,Set<String>> allPermItems=new HashMap<String,Set<String>>();
        List<PermissionGroup> allPermGroups = permGroupManager.getAllPermGroups();
        for(PermissionGroup permGroup : allPermGroups){
            Set<String> permItems=new HashSet<String>();
            permItems.addAll(permGroup.getPermissions());
            allPermItems.put(permGroup.getGroupName(), permItems);
        }
        
        // 3 ÿ��role��ӵ�еģ���ÿ��mo��Ȩ�޼��������Ѿ��������ı�
        // <rolename,role�����õ�moȨ�޼�>
        Map<String,Map<MoNaming,String>> roleMoPermGroups=new HashMap<String, Map<MoNaming,String>>();
        for(String roleName : user.getRoles()){
            Map<MoNaming,String> moPermGroups=new HashMap<MoNaming, String>();
            roleMoPermGroups.put(roleName,moPermGroups);
            List<MoPermGroup> moPermGroupList = getMoPermGroupByRole(roleName); //��ȡ��role��ÿ��mo��Ȩ�޼�
            for(MoPermGroup group : moPermGroupList){
                moPermGroups.put(group.getMoName(), group.getGroupName());
            }
        }
        
        // 4 user�ɹ����MO
        Set<MoNaming> userMgmtScope=mgmtScopeManager.getUserMgmtScope(user.getUserName());
        
        // 5 �㷨����
        // ���mo���ڹ���Χ����drop
        // ��ÿ��user��ÿ��role��ÿ��mo�ϵ�Ȩ�޼�����
        // ���ĳ��mo�Լ�û������Ȩ�޼�����ʹ�ø�����ү��Ȩ�޼�
        Map<MoNaming,Set<String>> moPermIds=new HashMap<MoNaming, Set<String>>();
        for(MoNaming moNaming : userMgmtScope){
            Set<String> permIds=new HashSet<String>();
            moPermIds.put(moNaming,permIds);
            for(Map.Entry<String,Map<MoNaming,String>> entry : roleMoPermGroups.entrySet()){
                Map<MoNaming, String> moPermGroups = entry.getValue();//ÿ��role��perm group
                if(moPermGroups!=null){
                    String permGroup = getMoPermissionGroup(moNaming, moPermGroups);
                    Set<String> tmpPermIds = allPermItems.get(permGroup);
                    if(tmpPermIds!=null)
                        permIds.addAll(tmpPermIds);
                }
            }
        }
        userMoPerms.put(user.getUserName(),moPermIds);
    }
    
    /**
     * ����mo�仯����Ϣ,mo�����ӣ�ɾ��ʱҲ��Ҫ��user��Ȩ��cache���б仯
     */
    private void addMoChangedListener(){
        SmCoreContext.subscribeLocalMessage(MoMessage.MO_CREATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreContext.isUnmanagedMo(moNaming)) //�忨,�˿ڵȲ��ɹ����moֱ�ӹ��˵�
                    return;
                MoNaming parent = mo.getParent();
                // ����ÿ���û���Ȩ����
                for(Map.Entry<String,Map<MoNaming,Set<String>>> entry : userMoPerms.entrySet()){
                    Map<MoNaming, Set<String>> moPerms = entry.getValue();
                    if(moPerms!=null){
                        // ��ǰmo�븸��mo��Ȩ��һ��
                        Set<String> parentPerms = moPerms.get(parent);
                        moPerms.put(moNaming, parentPerms);
                    }
                }
            }
        });
        
        SmCoreContext.subscribeLocalMessage(MoMessage.MO_DELETE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreContext.isUnmanagedMo(moNaming)) //�忨,�˿ڵȲ��ɹ����moֱ�ӹ��˵�
                    return;
                // ����ÿ���û���Ȩ����
                for(Map.Entry<String,Map<MoNaming,Set<String>>> entry : userMoPerms.entrySet()){
                    Map<MoNaming, Set<String>> moPerms = entry.getValue();
                    if(moPerms!=null)
                        moPerms.remove(moNaming);
                }
            }
        });
    }
    
    private List<MoPermGroup> getMoPermGroupByRole(String roleName){
        List<MoPermGroup> groups = moPermGroupManager.getMoPermGroupByRole(roleName);
        return groups;
    }
    
    /**
     * ��ȡָ���û���moȨ��
     * @param userName
     * @return
     */
    public Map<MoNaming,Set<String>> getUserMoPermission(String userName){
        return userMoPerms.get(userName);
    }
    
    /**
     * ���user��moȨ��cache
     * @param userName
     */
    public void removeUserMoPermission(String userName){
        userMoPerms.remove(userName);
    }
    
    /**
     * �ݹ��ȡmo��Ȩ�޼���
     * @param mo
     * @param moPermGroups �ض���ɫ��mo��Ȩ�޼�
     * @return
     */
    private String getMoPermissionGroup(MoNaming mo,Map<MoNaming,String> moPermGroups){
        String permGroup = moPermGroups.get(mo);
        if(permGroup!=null)
            return permGroup;
        MoNode node = moCore.getMoNode(mo);
        MoNode parent = node.getParent();
        if(parent==null)
            return null;
        return getMoPermissionGroup(parent.getMo().getMoNaming(), moPermGroups);
    }
    
    /**
     * ���û���role list��permission group�ȷ����仯ʱ,��Ҫ�ؽ����user-mo-permission��
     * @param updatedUser
     */
    public void userRolePermissionChanged(User updatedUser){
        if(!userMoPerms.containsKey(updatedUser.getUserName()))
            return;
        buildUserPermissionTree(updatedUser);
        
        // ����Ϣ��client
        SmMessage msg = new SmMessage();
        msg.setMessageBody(updatedUser.getUserName());
        msg.setMessageType(SmMessage.TYPE_USER_PERMISSION_CHANGED);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }
    
    
}
