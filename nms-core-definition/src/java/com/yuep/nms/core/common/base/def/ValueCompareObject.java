/*
 * $Id: ValueCompareObject.java, 2011-3-29 上午09:41:38 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.io.Serializable;

/**
 * <p>
 * Title: ValueCompareObject
 * </p>
 * <p>
 * Description:
 * 值比较对象,包含属性名,旧值,新值
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 上午09:41:38
 * modified [who date description]
 * check [who date description]
 */
public class ValueCompareObject implements Serializable {
    
    private static final long serialVersionUID = -861428620597095630L;

    /**
     * 属性名
     */
    private String name;
    
    /**
     * 旧值
     */
    private Object oldValue;
    
    /**
     * 新值
     */
    private Object newValue;

    public ValueCompareObject(){
        
    }
    
    /**
     * 构造
     * @param name
     * @param oldValue
     * @param newValue
     */
    public ValueCompareObject(String name,Object oldValue,Object newValue){
        this.name=name;
        this.oldValue=oldValue;
        this.newValue=newValue;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getOldValue() {
        return oldValue;
    }
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    public Object getNewValue() {
        return newValue;
    }
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
    
    
}
