/*
 * $Id: Sync.java, 2009-3-6 下午03:43:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: Sync
 * </p>
 * <p>
 * Description: <br>
 * 业务同步接口，所有业务同步类都要实现该接口
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 下午03:43:58
 * modified [who date description]
 * check [who date description]
 */
public interface Sync {
    /**
     * 同步方法
     * 
     * @param target
     *            target有可能是当前同步节点的父节点，也有可能就是当前节点
     * @param listeners
     *            同步进程监听器集合
     */
    public void sync(MoNaming target, List<SyncListener> listeners);

}
