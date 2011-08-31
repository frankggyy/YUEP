/*
 * $Id: DefaultStatusBarView.java, 2009-3-5 上午10:23:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.statusbar.DefaultStatusModel;
import com.yuep.core.client.component.statusbar.Status;
import com.yuep.core.client.component.statusbar.XStatusBar;
import com.yuep.core.client.main.login.model.LoginInfo;

/**
 * <p>
 * Title: DefaultStatusBarView
 * </p>
 * <p>
 * Description:默认状态栏
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午10:23:47
 * modified [who date description]
 * check [who date description]
 */
public class DefaultStatusBarView implements StatusConstants, StatusBarView {
    
    /** model of status bar */
    protected DefaultStatusModel model;
    
    protected XStatusBar    statusBar;
    
    protected Map<String, Status> statusMap;
    
    protected LoginInfo loginInfo;
    
    /** Default constructor */
    public DefaultStatusBarView(){
        
        model = new DefaultStatusModel ();
        statusMap = new HashMap<String, Status>();
                    
        init();
        statusBar = new XStatusBar(model);
        
    }
    
    /* 
     * @see com.shjv.tdscdma.omc.client.platform.statusbar.OmcStatusBarIF#getStatusBar()
     */
    public XStatusBar getStatusBar() {

        return statusBar;
    }

    protected void init(){
                
        /* connect status */
        Status status = new Status (ConnectStatus.DISCONNECT, false, ConnectStatus.DISCONNECT.getIcon (), 150);
        model.addStatus (status);
        statusMap.put(CONNECT_INDEX, status);
        
        /* user name */
        String userInfo = ClientCoreContext.getString("common.mainframe.status.user.text");
        status = new Status (userInfo, false, ClientCoreContext.getResourceFactory().getIcon("user.gif"), 160);
        model.addStatus (status);
        statusMap.put(CURRENT_USER_INDEX, status);
        
        /* user model */
        String userModel = ClientCoreContext.getString("common.mainframe.status.user.model.text");
        status = new Status (userModel, false, ClientCoreContext.getResourceFactory().getIcon("user_mode.gif"), 180);
        model.addStatus (status);
        statusMap.put(CURRENT_USER_MODEL_INDEX, status);
        
        /* server ip */
        String serverInfo = ClientCoreContext.getString("common.mainframe.status.server.text");
        status = new Status (serverInfo, false, ClientCoreContext.getResourceFactory().getIcon("server.gif"), 180);
        model.addStatus (status);
        statusMap.put(SERVER_ADDRESS_INDEX, status);
        
        /* custom area */
        status = new Status ("", true, null);
        model.addStatus (status);
        statusMap.put(CUSTOM_INDEX, status);
        
        /* logo icon */
        status = new Status ("", false, ClientCoreContext.getResourceFactory().getIcon("logo_statusbar.gif"), 20);
        model.addStatus (status);
        statusMap.put(LOGO_INDEX, status);
        //status.setAction(new HmAboutAction());
    }
    
    /**
     * find index by status
     * @param statusKey
     * @return index
     */
    public int getStatusIndex(String statusKey){
        Status status = statusMap.get(statusKey);
        return model.indexOf(status);
    }
    
    /*
     * @see com.shjv.tdscdma.omc.client.platform.statusbar.StatusBarManagerIF#refreshStatus()
     */
    public void refreshStatus() {

        /* connect status */
        boolean connected = true;
        updateConnectionStatus(connected);

        // TODO 状态条信息处理逻辑已经注释
        /* server ip */
        int index = getStatusIndex(SERVER_ADDRESS_INDEX);
        String serverIP = loginInfo.getServer();
        
        String serverInfo = ClientCoreContext.getString("common.mainframe.status.server.text",serverIP);
        model.setStatusVaule (serverInfo, index);

        /* user name */
        index = getStatusIndex(CURRENT_USER_INDEX);
        String username = loginInfo.getUser();
        String userInfo = ClientCoreContext.getString("common.mainframe.status.user.text",username);
        model.setStatusVaule (userInfo, index);
        
        index = getStatusIndex(CURRENT_USER_MODEL_INDEX);
        String userModel = loginInfo.getUserModel();
        String displayUserModel=ClientCoreContext.getString(userModel);
        String userModelInfo = ClientCoreContext.getString("common.mainframe.status.user.model.text",displayUserModel);
        model.setStatusVaule (userModelInfo, index);
    }

    public void updateStatusIcon(String key, Icon icon) {

        model.setStatusIcon(icon, getStatusIndex(key));
        
    }

    public void updateStatusValue(String key, Object value) {

        model.setStatusVaule(value, getStatusIndex(key));
        
    }
    
    public void updateConnectionStatus(boolean connected){
        
        ConnectStatus connStatus = connected? ConnectStatus.CONNECTED
                    : ConnectStatus.DISCONNECT;

        int index = getStatusIndex(CONNECT_INDEX);
        
        model.setStatusIcon (connStatus.getIcon (), index);
        model.setStatusVaule (connStatus, index);
    }

    public void hideBubble(int index) {
        statusBar.hideBubble(index);
    }

    public void popupBubble(int index, Object bubbleMsg, int delaySeconds) {
        statusBar.showBubble(index, bubbleMsg.toString());
    }

    /**
     * @param loginInfo the loginInfo to set
     */
    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
