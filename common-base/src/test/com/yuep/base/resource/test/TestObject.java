/*
 * $Id: TestObject.java, 2010-11-11 上午10:45:51 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource.test;

import java.io.Serializable;

/**
 * <p>
 * Title: TestObject
 * </p>
 * <p>
 * Description:
 * 测试对象
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 上午10:45:51
 * modified [who date description]
 * check [who date description]
 */
public class TestObject implements Serializable {
    private static final long serialVersionUID = -4132224555986727792L;
    private String name;
    private int value;
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
    
    

}
