/*
 * $Id: CacheObject.java, 2010-10-28 下午03:35:58 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.test;

import java.io.Serializable;

/**
 * <p>
 * Title: CacheObject
 * </p>
 * <p>
 * Description:
 * 缓存对象
 * </p>
 * 
 * @author yangtao
 * created 2010-10-28 下午03:35:58
 * modified [who date description]
 * check [who date description]
 */
public class CacheObject implements Serializable {
    private static final long serialVersionUID = -6992773395817199062L;
    private String name;
    private int value;
    
    public CacheObject(String name,int value){
        this.name=name;
        this.value=value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    
    public String toString(){
        return name+"="+value;
    }
    
}
