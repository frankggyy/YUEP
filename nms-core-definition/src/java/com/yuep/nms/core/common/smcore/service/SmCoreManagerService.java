/*
 * $Id: SmCoreManagerService.java, 2011-3-24 ����11:18:38 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.base.def.annotation.LogType;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;

/**
 * <p>
 * Title: SmCoreManagerService
 * </p>
 * <p>
 * Description: �û�/��ɫ/Ȩ�޹���ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:18:38
 * modified [who date description]
 * check [who date description]
 */
public interface SmCoreManagerService {

    /**
     * ��ȡ�û�
     * @param userName
     * @return
     */
    public User getUser(String userName);
    
    /**
     * ��ȡ�����û�
     * @return
     */
    public List<User> getAllUsers();
    
    /**
     * �����û��Ŀɵ�½��IP��Χ
     * @return
     */
    public List<IpRange> getAllUsersIpRange();
    
    /**
     * �����û�
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addUser(User user);
    
    /**
     * ɾ���û�
     * @param userName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteUser(User user);
    
    /**
     * �����û�
     * @param updatedUser
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateUser(User updatedUser);
    
  
    /**
     * �����û�
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void activeUser(String userName);
    
    /**
     * ȥ�����û�
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deactiveUser(String userName);
    /**
     * ���ݽ�ɫ����ȡ��ɫ
     * @param roleName
     * @return
     */
    public Role getRoleByName(String roleName);
    
    /**
     * ��ȡ���н�ɫ
     * @return
     */
    public List<Role> getAllRoles();
    
    /**
     * ������ɫ
     * @param role
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addRole(Role role);
    
    /**
     * ɾ����ɫ
     * @param roleName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteRole(String... roleNames);
    
    /**
     * ���½�ɫ
     * @param updatedRole
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateRole(Role updatedRole);
    
    /**
     * ��ȡ����Ȩ�޼�
     * @return
     */
    public List<PermissionGroup> getAllPermissionGroups();
    
    /**
     * ��ȡȨ�޼�
     * @param permGroupName
     * @return
     */
    @FacadeMethod
    public PermissionGroup getPermissionGroup(String permGroupName);
    
    /**
     * ���Ȩ�޼�
     * @param permGroup
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addPermissionGroup(PermissionGroup permGroup);
    
    /**
     * ɾ��Ȩ�޼�
     * @param permGroupName
     * @return
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public Boolean deletePermissionGroup(String permGroupName);
    
    /**
     * ����Ȩ�޼�
     * @param updatedPermGroup
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updatePermissionGroup(PermissionGroup updatedPermGroup);
    
    /**
     * ����Ȩ���� ��Ŀǰ��ʵ�֣������ô�XML�ж�ȡ��
     * @param perm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addPermission(Permission perm);
    
    /**
     * ɾ��Ȩ���Ŀǰ��ʵ�֣�
     * @param permName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deletePermission(String permName);
    
    /**
     * ����Ȩ���Ŀǰ��ʵ�֣�
     * @param updatedPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updatePermission(Permission updatedPerm);
    
    /**
     * ��ȡָ���û���moȨ����
     * @param userName
     * @return
     */
    public Map<MoNaming, Set<String>> getPermissionTree(String userName);
    
    /**
     * �õ�����Ȩ����Ĵ���
     * @return
     */
    public List<String> getAllPermissionCategories();
    
    /**
     * ��ȡȨ��������е�����Ȩ����
     * @param category
     * @return
     */
    public List<Permission> getPermissions(String category);
    
    /**
     * ����ActionID��FunctionName����ȡȨ������Ϣ
     * @param actionOrFn
     * @return
     */
    public Permission getPermission(String actionOrFn);
    
    /**
     * ���һ����ɫ���mo�µ�Ȩ�޼�
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addMoPerm(MoPermGroup... moPerms);
    
    /**
     * ����moName,roleName��ѯĳ����ɫ���һ��mo�µ�Ȩ�޼�
     * @param moName
     * @param roleName
     * @return
     */
    public MoPermGroup getMoPerm(MoNaming moName,String roleName);

    /**
     * �޸�һ����ɫ���mo�µ�Ȩ�޼�
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateMoPerm(String roleName,MoPermGroup... moPerms);
    
    /**
     * ɾ��һ����ɫ���mo�µ�Ȩ�޼�
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteMoPerm(MoPermGroup... moPerms);

}
