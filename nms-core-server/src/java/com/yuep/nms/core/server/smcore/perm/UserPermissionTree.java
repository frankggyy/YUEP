/*
 * $Id: UserPermissionTree.java, 2011-4-18 下午05:19:37 sufeng Exp $
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
 * Description: 处理用户mo权限，这是一个user-mo-permission的tree cache，外界的变化需要更新到这个cache中
 * </p>
 * 
 * @author sufeng
 * created 2011-4-18 下午05:19:37
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
     * 用来做多线程同步
     */
    private Object monitor=new Object();
    
    /**
     * 是否初始化过
     */
    private boolean hasInited=false;
    
    /**
     * 每个user，对每个mo的权限项
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
     * 用户登录/user的rolelist变化/role的permissiongroup变化时进行user-mo-perm初始化
     */
    public void buildUserPermissionTree(User user){
        synchronized (monitor) {
            if(!hasInited){
                moCore=SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
                addMoChangedListener();
                hasInited=true;
            }
        }

        // 1.user包含的role list
        if(user.getRoles()==null)
            return;
        
        // 2,每个权限集包含的permission item : <permissionGroup,PermissionID list>
        Map<String,Set<String>> allPermItems=new HashMap<String,Set<String>>();
        List<PermissionGroup> allPermGroups = permGroupManager.getAllPermGroups();
        for(PermissionGroup permGroup : allPermGroups){
            Set<String> permItems=new HashSet<String>();
            permItems.addAll(permGroup.getPermissions());
            allPermItems.put(permGroup.getGroupName(), permItems);
        }
        
        // 3 每个role所拥有的，对每个mo的权限集，这是已经分析过的表
        // <rolename,role所设置的mo权限集>
        Map<String,Map<MoNaming,String>> roleMoPermGroups=new HashMap<String, Map<MoNaming,String>>();
        for(String roleName : user.getRoles()){
            Map<MoNaming,String> moPermGroups=new HashMap<MoNaming, String>();
            roleMoPermGroups.put(roleName,moPermGroups);
            List<MoPermGroup> moPermGroupList = getMoPermGroupByRole(roleName); //获取该role的每个mo的权限集
            for(MoPermGroup group : moPermGroupList){
                moPermGroups.put(group.getMoName(), group.getGroupName());
            }
        }
        
        // 4 user可管理的MO
        Set<MoNaming> userMgmtScope=mgmtScopeManager.getUserMgmtScope(user.getUserName());
        
        // 5 算法过程
        // 如果mo不在管理范围内则drop
        // 对每个user找每个role对每个mo上的权限集并集
        // 如果某个mo自己没有设置权限集，则使用父或者爷的权限集
        Map<MoNaming,Set<String>> moPermIds=new HashMap<MoNaming, Set<String>>();
        for(MoNaming moNaming : userMgmtScope){
            Set<String> permIds=new HashSet<String>();
            moPermIds.put(moNaming,permIds);
            for(Map.Entry<String,Map<MoNaming,String>> entry : roleMoPermGroups.entrySet()){
                Map<MoNaming, String> moPermGroups = entry.getValue();//每个role的perm group
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
     * 监听mo变化的消息,mo的增加，删除时也需要把user的权限cache进行变化
     */
    private void addMoChangedListener(){
        SmCoreContext.subscribeLocalMessage(MoMessage.MO_CREATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreContext.isUnmanagedMo(moNaming)) //板卡,端口等不可管理的mo直接过滤掉
                    return;
                MoNaming parent = mo.getParent();
                // 更新每个用户的权限树
                for(Map.Entry<String,Map<MoNaming,Set<String>>> entry : userMoPerms.entrySet()){
                    Map<MoNaming, Set<String>> moPerms = entry.getValue();
                    if(moPerms!=null){
                        // 当前mo与父亲mo的权限一样
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
                if(SmCoreContext.isUnmanagedMo(moNaming)) //板卡,端口等不可管理的mo直接过滤掉
                    return;
                // 更新每个用户的权限树
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
     * 获取指定用户的mo权限
     * @param userName
     * @return
     */
    public Map<MoNaming,Set<String>> getUserMoPermission(String userName){
        return userMoPerms.get(userName);
    }
    
    /**
     * 清除user的mo权限cache
     * @param userName
     */
    public void removeUserMoPermission(String userName){
        userMoPerms.remove(userName);
    }
    
    /**
     * 递归获取mo的权限集名
     * @param mo
     * @param moPermGroups 特定角色的mo的权限集
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
     * 当用户的role list或permission group等发生变化时,需要重建这棵user-mo-permission树
     * @param updatedUser
     */
    public void userRolePermissionChanged(User updatedUser){
        if(!userMoPerms.containsKey(updatedUser.getUserName()))
            return;
        buildUserPermissionTree(updatedUser);
        
        // 发消息给client
        SmMessage msg = new SmMessage();
        msg.setMessageBody(updatedUser.getUserName());
        msg.setMessageType(SmMessage.TYPE_USER_PERMISSION_CHANGED);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }
    
    
}
