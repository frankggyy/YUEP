/*
 * $Id: ModulePermission.java, 2011-4-14 ����02:25:30 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smmanager.model;

import java.io.Serializable;
import java.util.List;

import com.yuep.nms.core.common.smcore.model.Permission;

/**
 * <p>
 * Title: ModulePermission
 * </p>
 * <p>
 * Description:ģ��Ȩ�޼�,��Ӧ��Ȩ�޼��������еĽڵ�
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 ����02:25:30
 * modified [who date description]
 * check [who date description]
 */
public class ModulePermission  implements Serializable{
    private static final long serialVersionUID = 8664754771417402405L;
    /**ģ����*/
    private String moduleName;
    /** �˽ڵ��������Ϣ*/
    private String description;
    /** ģ��Ȩ�޼���list,�����˽ڵ���Ȩ�������ӽڵ�*/
    private List<ModulePermission> children;
    /** ģ���°�����Ȩ���� */
    private List<Permission> permissions;
    
    /**
     * @param moduleName
     * @param description
     */
    public ModulePermission(String moduleName, String description) {
        super();
        this.moduleName = moduleName;
        this.description = description;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the children
     */
    public List<ModulePermission> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<ModulePermission> children) {
        this.children = children;
    }

    /**
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    
}
