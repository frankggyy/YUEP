/*
 * $Id: SyncState.java, 2011-3-28 上午11:06:40 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.enums;

/**
 * <p>
 * Title: SyncState
 * </p>
 * <p>
 * Description:
 * 管理同步状态
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:06:40
 * modified [who date description]
 * check [who date description]
 */
public enum SyncState {
    /**
     * 未知状态
     */
    UNKNOWN,
    /**
     * 同步状态
     */
    SYNC,
    /**
     * 不同步状态
     */
    NOTSYNC
}
