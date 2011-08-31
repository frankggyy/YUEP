/*
 * $Id: PermGroupManager.java, 2011-4-15 ����01:42:06 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.util.List;
import java.util.Set;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.server.smcore.SmCoreContext;

/**
 * <p>
 * Title: PermGroupManager
 * </p>
 * <p>
 * Description:Ȩ�޼�������
 * </p>
 * 
 * @author WangRui
 * created 2011-4-15 ����01:42:06
 * modified [who date description]
 * check [who date description]
 */
public class PermGroupManager {
    
    private BaseDao<PermissionGroup> permGroupDao;
    private MoPermGroupManager moPermGroupManager;

    public void setMoPermGroupManager(MoPermGroupManager moPermGroupManager) {
        this.moPermGroupManager = moPermGroupManager;
    }
    public void setPermGroupDao(BaseDao<PermissionGroup> permGroupDao) {
        this.permGroupDao = permGroupDao;
    }

    /**
     * ����Ȩ�޼�
     * @param permGroup
     */
    public void addPermGroup(PermissionGroup permGroup) {
        permGroupDao.saveEntity(permGroup);
        // ������Ϣ
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_ROLE_ADD);
        msg.setMessageBody(permGroup.getGroupName());
        SmCoreContext.publishMessage(msg.getName(), msg);
    }
    
    /**
     * ����Ȩ�޼�
     * @param newPermGroup
     */
    public void updatePermGroup(PermissionGroup newPermGroup) {
        PermissionGroup permGroupInDb = permGroupDao.getUniqueEntityByOneProperty("groupName", newPermGroup
                .getGroupName());
        final boolean existed = (permGroupInDb != null);
        if (!existed) {
            permGroupDao.saveEntity(newPermGroup);
            return;
        }

        permGroupDao.updateEntity(newPermGroup);

        // Ȩ�޼��仯�ˣ�����Ҫ�������ø�Ȩ�޼���user��Ȩ��cache
        if (!YuepObjectUtils.collectionEquals(permGroupInDb.getPermissions(), newPermGroup.getPermissions())) {
            Set<String> roles = moPermGroupManager.getAppliedRolesByPermissionGroup(newPermGroup.getGroupName());
            for(String role : roles)
                moPermGroupManager.rolePermissionChanged(role);
        }
    }

    /**
     * ɾ��Ȩ�޼�
     * @param groupName
     * @return
     */
    public Boolean deletePermGroup(String groupName) {
        // �����Ȩ�޼��ѱ�Role����,���ܱ�ɾ��
        Boolean flag = true;
        List<MoPermGroup> groupList = moPermGroupManager.getAllMoPermGroup();
        for (MoPermGroup moPermGroup : groupList) {
            if (moPermGroup.getGroupName().equalsIgnoreCase(groupName)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            permGroupDao.deleteEntityByProperty("groupName", groupName);
            // ������Ϣ
            SmMessage msg = new SmMessage();
            msg.setMessageType(SmMessage.TYPE_ROLE_DELETE);
            msg.setMessageBody(groupName);
            SmCoreContext.publishMessage(msg.getName(), msg);
        }
        return flag;
    }

    /**
     * db�е�����Ȩ�޼�
     * @return
     */
    public List<PermissionGroup> getAllPermGroups() {
        return permGroupDao.getAllEntities();
    }

    /**
     * ����Ȩ�޼����õ�Ȩ�޼�    
     * @param groupName
     * @return
     */
    public PermissionGroup getPermGroupByName(String groupName){
        return permGroupDao.getUniqueEntityByOneProperty("groupName", groupName);
    }
    
}
