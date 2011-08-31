/*
 * $Id: TestStudent.java, 2010-11-11 ÏÂÎç07:37:57 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml.test;

import java.io.Serializable;

/**
 * <p>
 * Title: TestStudent
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2010-11-11 ÏÂÎç07:37:57
 * modified [who date description]
 * check [who date description]
 */
public class TestStudent implements Serializable{
    private static final long serialVersionUID = -2322310859970531098L;
    
    private String name;
    private int old;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOld() {
        return old;
    }
    public void setOld(int old) {
        this.old = old;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestStudent other = (TestStudent) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    

}
