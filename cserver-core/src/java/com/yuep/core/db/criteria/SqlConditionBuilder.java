/*
 * $Id: SqlExpressBuilder.java, 2011-3-25 下午03:46:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: SqlExpressBuilder
 * </p>
 * <p>
 * Description:把criteria转换为sql
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 下午03:46:47
 * modified [who date description]
 * check [who date description]
 */
public class SqlConditionBuilder {

    /**
     * 多个条件
     */
    private List<Criteria> conditions=new ArrayList<Criteria>();
    
    /**
     * 增加一个条件
     * @param creteria
     */
    public void add(Criteria creteria){
        conditions.add(creteria);
    }
    
    /**
     * 转换为sql的where的条件部分,用and来连接
     * @return 比如 name='yuep' and age=16
     */
    public String toSql(){
        StringBuilder sb=new StringBuilder();
        if(conditions.size()==0)
            return "";
        if(conditions.size()==1)
            return conditions.get(0).toSql();
        
        int i=0;
        for(Criteria creteria : conditions){
            if(i>0)
                sb.append(" and ");
            sb.append(creteria.toSql());
            i++;
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SqlConditionBuilder sb=new SqlConditionBuilder();
        sb.add(Restrictions.eq("name", "yuep"));
        sb.add(Restrictions.eq("age", 15));
        sb.add(Restrictions.or(Restrictions.eq("obj", "ne=1"),Restrictions.and(Restrictions.eq("ip", "192.168.1.1"),Restrictions.eq("mask", "255.255.255.0"))));
        sb.add(Restrictions.and(Restrictions.eq("ip", "192.168.1.1"),Restrictions.eq("ip", "192.168.1.2")));
        System.out.println(sb.toSql());
    }
    
}
