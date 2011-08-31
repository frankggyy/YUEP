/*
 * $Id: Transaction.java, 2011-3-21 ����09:33:39 Administrator Exp $
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
 * ���ݿ��������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 ����09:33:39
 * modified [who date description]
 * check [who date description]
 */
public interface Transaction {

    /**
     * ��ʼ����
     */
    public abstract void begin();

    /**
     * �ύ����
     */
    public abstract void commit();

    /**
     * �ع�
     */
    public abstract void rollback();

    /**
     * �ѻع�����
     * @return
     */
    public abstract boolean wasRolledBack();

    /**
     * ���ύ����
     * @return
     */
    public abstract boolean wasCommitted();

    /**
     * �ǻ�Ծ��
     * @return
     */
    public abstract boolean isActive();

    /**
     * ���� timeout 
     * @param i ��
     */
    public abstract void setTimeout(int i);

}
