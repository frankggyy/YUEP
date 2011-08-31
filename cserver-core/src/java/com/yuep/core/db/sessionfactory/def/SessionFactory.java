/*
 * $Id: SessionFactory.java, 2011-3-21 ����09:32:33 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.def;

import com.yuep.core.db.session.def.Session;
/**
 * <p>
 * Title: SessionFactory
 * </p>
 * <p>
 * Description:
 * ���ݿ���ʻỰSession����
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 ����09:32:33
 * modified [who date description]
 * check [who date description]
 */
public interface SessionFactory {

    /**
     * ��һ��Session
     * @return
     *       ���ر��򿪵�Session
     */
    public abstract Session openSession();

    /**
     * �ر�SessionFactory
     */
    public abstract void close();
    /**
     * SessionFactory�Ƿ񱻹ر�
     * @return
     *     false:û�йر�
     *     true:�ر�
     */
    public abstract boolean isClosed();


}
