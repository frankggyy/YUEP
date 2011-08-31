/*
 * $Id: SimpleExpress.java, 2011-3-25 ����03:49:49 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.criteria;

/**
 * <p>
 * Title: SimpleExpress
 * </p>
 * <p>
 * Description:��򻯵��������ʽ�����ʽ����name='yuep'
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 ����03:49:49
 * modified [who date description]
 * check [who date description]
 */
public class SimpleExpress implements Criteria {

    /**
     * ����
     */
    private String prop;
    /**
     * ������������=,>,<��
     */
    private String operator;
    /**
     * ֵ
     */
    private Object value;
    
    public SimpleExpress(){}
    
    public SimpleExpress(String prop, String operator, Object value) {
        this.prop = prop;
        this.operator = operator;
        this.value = value;
    }
    
    public String getProp() {
        return prop;
    }
    
    public void setProp(String prop) {
        this.prop = prop;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String getValueExpress(){
        if(value==null)
            return "";
        if(value instanceof String)
            return "'"+value+"'";
        else
            return value.toString();
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    public String toSql() {
        StringBuilder sb=new StringBuilder();
        sb.append(prop).append(operator).append(getValueExpress());
        return sb.toString();
    }
    
}
