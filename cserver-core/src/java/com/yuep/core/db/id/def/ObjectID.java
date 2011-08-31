/*
 * $Id: ObjectStorageID.java, 2011-3-29 下午12:33:30 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.def;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>
 * Title: ObjectStorageID
 * </p>
 * <p>
 * Description:
 * 保存对象ID
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午12:33:30
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class ObjectID implements Serializable {
    private static final long serialVersionUID = -6428582628123146245L;
    
    /**
     * 自增1的ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 对象类别
     */
    private String objectType;

    /**
     * 对象标识ID
     */
    private long objectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }



}
