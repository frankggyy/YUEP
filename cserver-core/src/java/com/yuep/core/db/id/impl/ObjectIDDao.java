/*
 * $Id: ObjectIDDao.java, 2010-7-13 ����01:46:29 yangtao Exp $
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
 * ��������ΨһID
 * </p>
 * 
 * @author yangtao
 * created 2010-7-13 ����01:46:29
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectIDDao {
    /**
     * ����objectType����һ��Ψһ��ID
     * @param objectType
     *        ��������
     * @return
     *        Id
     */

    public Long getObjectId(String objectType);
}
