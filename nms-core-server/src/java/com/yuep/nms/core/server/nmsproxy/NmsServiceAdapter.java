/*
 * $Id: NmsServiceAdapter.java, 2011-5-19 上午10:42:37 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: NmsServiceAdapter
 * </p>
 * <p>
 * Description:
 * 下级网管服务适配器抽象类，所有下级网管服务适配类都必须继承该类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-19 上午10:42:37
 * modified [who date description]
 * check [who date description]
 */
public abstract class NmsServiceAdapter<T> {

    protected MoNaming nm;
    protected T nmsRemoteService;
    
    public NmsServiceAdapter(MoNaming nm,T nmsRemoteService){
        this.nm=nm;
        this.nmsRemoteService=nmsRemoteService;
    }
    
    
}
