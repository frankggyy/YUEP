/*
 * $Id: DbServerManager.java, 2011-3-21 上午09:36:08 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.server.manager.def;

import java.util.Map;

/**
 * <p>
 * Title: DbServerManager
 * </p>
 * <p>
 * Description:
 * 数据库管理接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午09:36:08
 * modified [who date description]
 * check [who date description]
 */
public interface DbServerManager {
    
    /**
     * 启动数据库服务器
     * @param dbRunningParams
     *        数据库服务器参数
     */
    public void start(Map<String,String> dbParams);
    
    /**
     * 关闭数据库服务器
     */
    public void close();

}
