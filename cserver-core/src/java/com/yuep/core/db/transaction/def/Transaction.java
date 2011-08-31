/*
 * $Id: Transaction.java, 2011-3-21 上午09:33:39 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.transaction.def;

/**
 * <p>
 * Title: Transaction
 * </p>
 * <p>
 * Description:
 * 数据库访问事务接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午09:33:39
 * modified [who date description]
 * check [who date description]
 */
public interface Transaction {

    /**
     * 开始事务
     */
    public abstract void begin();

    /**
     * 提交事务
     */
    public abstract void commit();

    /**
     * 回滚
     */
    public abstract void rollback();

    /**
     * 已回滚了吗
     * @return
     */
    public abstract boolean wasRolledBack();

    /**
     * 已提交了吗
     * @return
     */
    public abstract boolean wasCommitted();

    /**
     * 是活跃的
     * @return
     */
    public abstract boolean isActive();

    /**
     * 设置 timeout 
     * @param i 秒
     */
    public abstract void setTimeout(int i);

}
