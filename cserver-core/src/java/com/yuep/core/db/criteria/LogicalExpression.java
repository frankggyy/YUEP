/*
 * $Id: LogicalExpression.java, 2011-3-25 下午04:34:56 sufeng Exp $
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
 * Title: LogicalExpression
 * </p>
 * <p>
 * Description:sql中的where条件表达式(对2个条件进行叠加a=1 or b=2)
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 下午04:34:56
 * modified [who date description]
 * check [who date description]
 */
public class LogicalExpression implements Criteria{

    /**
     * 条件1
     */
    private Criteria cond1;
    
    /**
     * 条件2
     */
    private Criteria cond2;
    
    /**
     * 条件间的组合符，比如or,and
     */
    private String operator;
    
    public LogicalExpression(Criteria cond1,String operator,Criteria cond2){
        this.cond1=cond1;
        this.cond2=cond2;
        this.operator=operator;
    }
    
    @Override
    public String toSql() {
        StringBuilder sb=new StringBuilder();
        sb.append("(").append(cond1.toSql()).append(operator).append(cond2.toSql()).append(")");
        return sb.toString();
    }
    
}
