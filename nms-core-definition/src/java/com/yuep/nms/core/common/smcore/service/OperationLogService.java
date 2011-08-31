/*
 * $Id: OperationLogService.java, 2011-3-24 上午11:19:06 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import java.util.List;

import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;

/**
 * <p>
 * Title: OperationLogService
 * </p>
 * <p>
 * Description:操作日志接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:19:06
 * modified [who date description]
 * check [who date description]
 */
public interface OperationLogService {

    /**
     * 添加1个或多个操作日志
     * @param logs
     */
    public void addOperationLog(OperationLog ... logs);
    
    /**
     * 按条件查询日志记录
     * @param cond 查询条件
     * @return 日志list
     */
    public List<OperationLog> getOperationLog(OperationLogCondition cond);
    
    /**
     * 得到当前网管系统中所有的操作对象
     * @return 系统中所有的操作对象
     */
    public List<String> getAllOperationObjects();

    /**
     * 得到当前网管系统中所有的操作动作
     * @return 系统中所有的操作动作
     */
    public List<String> getAllOperationNames();
    
    /**
     * 得到当前网管系统中所有的操作员
     * @return
     */
    public List<String> getAllOperationUserNames();
    
}
