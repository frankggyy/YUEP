/*
 * $Id: StatusBarManagerImpl.java, 2009-3-5 ����10:22:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar.view;

/**
 * <p>
 * Title: StatusBarManagerImpl
 * </p>
 * <p>
 * Description:״̬����������ʵ����
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 ����10:22:59
 * modified [who date description]
 * check [who date description]
 */
public class StatusBarManagerImpl implements StatusBarManager {


    /**
     * ״̬��
     */
    private StatusBarView statusBarView;
    
    /**
     * Constructor
     */
    public StatusBarManagerImpl() {
        init();
    }

    /**
     * ��ʼ��״̬��
     */
    private void init() {
        if(statusBarView==null){
            statusBarView = new DefaultStatusBarView();
        }
    }

     
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.statusbar.view.StatusBarManager#getStatusBarView()
     */
    public StatusBarView getStatusBarView() {
    
        return statusBarView;
    }
}
