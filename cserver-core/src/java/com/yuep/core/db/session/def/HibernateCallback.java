/*
 * $Id: HibernateCallback.java, 2011-3-24 ����03:25:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.session.def;

/**
 * <p>
 * Title: HibernateCallback
 * </p>
 * <p>
 * Description:
 * �ص�ִ��hibernate Session
 * </p>
 * 
 * @author yangtao
 * created 2011-3-24 ����03:25:58
 * modified [who date description]
 * check [who date description]
 */
public interface HibernateCallback {
    /**
     * �ص�ִ��Hibernate Session
     * @param session
     * @return
     *       Hibernateִ�н��
     */
    public Object doInHibernate(org.hibernate.Session session);

}
