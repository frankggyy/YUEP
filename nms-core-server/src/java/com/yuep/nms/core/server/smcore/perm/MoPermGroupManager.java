/*
 * $Id: MoPermGroupManager.java, 2011-4-18 ����05:30:47 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.role.RoleManager;

/**
 * <p>
 * Title: MoPermGroupManager
 * </p>
 * <p>
 * Description:��ɫ��Ӧmo�µ�Ȩ�޼�������
 * </p>
 * 
 * @author WangRui
 * created 2011-4-18 ����05:30:47
 * modified [who date description]
 * check [who date description]
 */
public class MoPermGroupManager {
    
    private BaseDao<MoPermGroup> moPermDao;
    public void setMoPermDao(BaseDao<MoPermGroup> moPermDao) {
        this.moPermDao = moPermDao;
    }

    /**
     * ��mo��Ȩ
     * @param moPerms
     */
    public void addMoPerm(MoPermGroup... moPerms){
        if(moPerms==null || moPerms.length==0)
            return;
        moPermDao.saveBatch(moPerms);
    }

    /**
     * �õ���ɫ��ĳ��mo�ϵ�Ȩ��������Ϣ
     * @param moName
     * @param roleName
     * @return
     */
    public MoPermGroup getMoPerm(MoNaming moName,String roleName){
        MoPermGroup moPerm = moPermDao.getUniqueEntityByPropNames(new String[] { "moName", "roleName" },
                new Object[] { moName, roleName });
        return moPerm;
    }
    
    /**
     * Ϊ��ɫ����ÿ��mo��Ȩ�޼�
     * @param roleName
     * @param moPerms
     */
    public void updateMoPerm(String roleName,MoPermGroup... moPerms){
        List<MoPermGroup> moPermsInDb = getMoPermGroupByRole(roleName);
        List<MoPermGroup> moPermsUpdated = YuepObjectUtils.newArrayList(moPerms);
        boolean isSame=YuepObjectUtils.collectionEquals(moPermsInDb, moPermsUpdated);
        if(isSame)
            return;
        
        moPermDao.updateBatch(moPerms);
        // ���role,ɾ��role�����ᴥ���û�Ȩ��cache�ı仯
        // ������roleʱ,�п��ܻᴥ���û�Ȩ��cache�ı仯
        // �޸�roleʱ,�޸���role��moȨ�޼�,����Ҫ�������ø�role��user��Ȩ��cache
        rolePermissionChanged(roleName);
    }
    
    /**
     * role��Ȩ�ޱ仯��,��Ҫ����cache
     * @param roleName
     */
    public void rolePermissionChanged(String roleName){
        RoleManager roleManager=SmCoreContext.getBean("roleManager",RoleManager.class);
        List<User> users = roleManager.getUsersByRole(roleName);
        UserPermissionTree userPermissionTree=SmCoreContext.getBean("userPermissionTree", UserPermissionTree.class);
        for(User user : users)
            userPermissionTree.userRolePermissionChanged(user);
    }
    
    /**
     * ɾ��mo��permisssion
     * @param moPerms
     */
    public void deleteMoPerm(MoPermGroup... moPerms){
        moPermDao.deleteAllEntities(moPerms);
    }
    
    /**
     * �õ�ָ��role��moȨ�޼�
     * @param roleName
     * @return
     */
    public List<MoPermGroup> getMoPermGroupByRole(String roleName){
        List<MoPermGroup> entities = moPermDao.getEntitiesByOneProperty("roleName", roleName);
        return entities;
    }
    
    /**
     * �õ�permission group����Щrole������
     * @param roleName
     * @return
     */
    public Set<String> getAppliedRolesByPermissionGroup(String permissionGroup){
        List<MoPermGroup> entities = moPermDao.getEntitiesByOneProperty("groupName", permissionGroup);
        Set<String> roles=new HashSet<String>();
        for(MoPermGroup group : entities){
            if(group.getRoleName()!=null)
                roles.add(group.getRoleName());
        }
        return roles;
    }
    
    /**
     * ����mo��Ȩ��������Ϣ
     * @return
     */
    public List<MoPermGroup> getAllMoPermGroup(){
        return moPermDao.getAllEntities();
    }

}
