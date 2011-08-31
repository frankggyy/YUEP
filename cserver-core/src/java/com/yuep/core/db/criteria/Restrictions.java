/*
 * $Id: Restrictions.java, 2011-3-25 ����03:43:19 sufeng Exp $
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
 * Title: Restrictions
 * </p>
 * <p>
 * Description: ƴװ��ѯ��where������Ŀǰ����֧��in 
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 ����03:43:19
 * modified [who date description]
 * check [who date description]
 */
public class Restrictions {

    /**
     * ����
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress eq(String prop,Object value){
        return new SimpleExpress(prop,"=",value);
    }
    
    /**
     * ������
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress ne(String prop,Object value){
        return new SimpleExpress(prop,"<>",value);
    }
    
    /**
     * С��
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress lt(String prop,Object value){
        return new SimpleExpress(prop,"<",value);
    }
    
    /**
     * С�ڵ���
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress le(String prop,Object value){
        return new SimpleExpress(prop,"<=",value);
    }

    /**
     * ����
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress gt(String prop,Object value){
        return new SimpleExpress(prop,">",value);
    }
    
    /**
     * ���ڵ���
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress ge(String prop,Object value){
        return new SimpleExpress(prop,">=",value);
    }
    
    /**
     * like
     * @param prop
     * @param value
     * @return
     */
    public static SimpleExpress like(String prop,String value){
        return new SimpleExpress(prop," like ",value);
    }
    
    /**
     * �ǿ���
     * @param prop
     * @return
     */
    public static SimpleExpress isNull(String prop){
        return new SimpleExpress(prop," is null",null);
    }
    
    /**
     * �ǿ���
     * @param prop
     * @return
     */
    public static SimpleExpress isNotNull(String prop){
        return new SimpleExpress(prop," is not null",null);
    }
    
    /**
     * ��
     * @param cond1
     * @param cond2
     * @return
     */
    public static LogicalExpression or(Criteria cond1,Criteria cond2){
        return new LogicalExpression(cond1," or ",cond2);
    }
    
    /**
     * ��
     * @param cond1
     * @param cond2
     * @return
     */
    public static LogicalExpression and(Criteria cond1,Criteria cond2){
        return new LogicalExpression(cond1," and ",cond2);
    }

}
