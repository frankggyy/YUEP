/*
 * $Id: ModifyPermissionBundleView.java, 2011-4-14 下午01:14:31 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.ApplyModifyPermissionBundleAction;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smmanager.model.ModulePermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: ModifyPermissionBundleView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 下午01:14:31
 * modified [who date description]
 * check [who date description]
 */
public class ModifyPermissionBundleView extends AbstractValidateView<PermissionGroup> {
    private static final long serialVersionUID = 5012138880428983266L;
    private PermissionBundlePanel mainPanel;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<PermissionGroup, V, M> controller) {
        ApplyModifyPermissionBundleAction action = new ApplyModifyPermissionBundleAction(false,"",controller);
        okButton.addActionListener(action);
        
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        mainPanel=new PermissionBundlePanel();
        mainPanel.initPanel();
        return mainPanel;
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "smmanager.ModifyPermissionBundleView.description";
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<PermissionGroup> collectData() {
     // 得到设置的权限集的信息
        PermissionGroup permGroup = mainPanel.collectData();
        List<PermissionGroup> list=new ArrayList<PermissionGroup>();
        list.add(permGroup);
        return list;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return "smmanager.ModifyPermissionBundleView.title";
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<PermissionGroup> data) {
        PermissionGroup currentPermGroup=null;
        if(CollectionUtils.isEmpty(data))
            currentPermGroup=null;
        else
            currentPermGroup=data.get(0);
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        List<ModulePermission> modulePermissionList = smManagerService.getAllModulePermission();
        mainPanel.update(modulePermissionList, currentPermGroup);
    }

}
