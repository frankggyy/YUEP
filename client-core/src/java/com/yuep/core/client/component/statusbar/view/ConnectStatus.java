/*
 * $Id: ConnectStatus.java, 2009-3-5 上午10:26:35 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar.view;

import javax.swing.Icon;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: ConnectStatus
 * </p>
 * <p>
 * Description:连接状态区域
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午10:26:35
 * modified [who date description]
 * check [who date description]
 */
public enum ConnectStatus {
    /**已连接*/
    CONNECTED(ClientCoreContext.getString("common.mainframe.status.link"), ClientCoreContext.getResourceFactory().getIcon("link.gif")),
    /**断连*/
    DISCONNECT(ClientCoreContext.getString("common.mainframe.status.unlink"), ClientCoreContext.getResourceFactory().getIcon("unlink.gif"));
    /**描述*/
    private String desc;
    /**连接icon*/
    private Icon   icon;
    ConnectStatus(String desc, Icon icon){
        this.desc = desc;
        this.icon = icon;
    }

    public String toString(){
        return desc;
    }

    public Icon getIcon(){
        
        return icon;
    }
}
