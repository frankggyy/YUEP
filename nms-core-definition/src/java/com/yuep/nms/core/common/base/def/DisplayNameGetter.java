/*
 * $Id: DisplayNameGetter.java, 2011-4-1 上午11:03:28 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

/**
 * <p>
 * Title: DisplayNameGetter
 * </p>
 * <p>
 * Description: 获取显示名
 * </p>
 * 
 * @author sufeng
 * created 2011-4-1 上午11:03:28
 * modified [who date description]
 * check [who date description]
 */
public interface DisplayNameGetter {

    /**
     * 得到对象的显示名,常用在操作日志展示,客户端展示等处
     * @return
     */
    public String getDisplayName();
    
}
