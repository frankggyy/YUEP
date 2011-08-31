/*
 * $Id: StatusBarManager.java, 2009-3-5 上午10:21:55 aaron lee Exp $
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
 * Title: StatusBarManager
 * </p>
 * <p>
 * Description:状态栏管理器
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午10:21:55
 * modified [who date description]
 * check [who date description]
 */
public interface StatusBarManager {
    
    /**
     * 获取状态条
     * @return StatusBarView 状态条
     */
    StatusBarView getStatusBarView();
    
}
