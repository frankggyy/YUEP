/*
 * $Id: ObjectIDDao.java, 2010-7-13 下午01:46:29 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.impl;


/**
 * <p>
 * Title: ObjectStorageIDDao
 * </p>
 * <p>
 * Description:
 * 用于生成唯一ID
 * </p>
 * 
 * @author yangtao
 * created 2010-7-13 下午01:46:29
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectIDDao {
    /**
     * 根据objectType生成一个唯一的ID
     * @param objectType
     *        对象类型
     * @return
     *        Id
     */

    public Long getObjectId(String objectType);
}
