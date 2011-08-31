/*
 * $Id: ObjectIDGenerateServiceImpl.java, 2011-3-29 下午12:39:22 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.impl;

import com.yuep.core.db.id.def.ObjectIDGenerateService;

/**
 * <p>
 * Title: ObjectIDGenerateServiceImpl
 * </p>
 * <p>
 * Description:
 * ObjectStorageIDService接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午12:39:22
 * modified [who date description]
 * check [who date description]
 */
public class ObjectIDGenerateServiceImpl implements ObjectIDGenerateService {

    private ObjectIDDao objectIDDao;
    
    public void setObjectIDDao(ObjectIDDao objectIDDao){
        this.objectIDDao=objectIDDao;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.id.def.ObjectIDGenerateService#generateObjectId(java.lang.String, java.lang.String)
     */
    @Override
    public Long generateObjectId(String objectType) {
        return objectIDDao.getObjectId(objectType);
    }
    
    

}
