/*
 * $Id: DefaultTransaction.java, 2011-3-21 上午11:08:34 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.transaction.impl;

import com.yuep.core.db.transaction.def.Transaction;

/**
 * <p>
 * Title: DefaultTransaction
 * </p>
 * <p>
 * Description:
 * 默认事务管理器实现
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午11:08:34
 * modified [who date description]
 * check [who date description]
 */
public class TransactionImpl implements Transaction {
    
    private org.hibernate.Transaction hibernateTransaction;
    
    public TransactionImpl(org.hibernate.Transaction hibernateTransaction){
        this.hibernateTransaction=hibernateTransaction;
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#begin()
     */
    @Override
    public void begin() {
        hibernateTransaction.begin();
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#commit()
     */
    @Override
    public void commit() {
        hibernateTransaction.commit();
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#isActive()
     */
    @Override
    public boolean isActive() {
        return hibernateTransaction.isActive();
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#rollback()
     */
    @Override
    public void rollback() {
        hibernateTransaction.rollback();
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#setTimeout(int)
     */
    @Override
    public void setTimeout(int i) {
        hibernateTransaction.setTimeout(i);
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#wasCommitted()
     */
    @Override
    public boolean wasCommitted() {
       return  hibernateTransaction.wasCommitted();
    }
    /**
     * 
     * @see com.yuep.core.db.transaction.def.Transaction#wasRolledBack()
     */
    @Override
    public boolean wasRolledBack() {
        return hibernateTransaction.wasRolledBack();
    }

}
