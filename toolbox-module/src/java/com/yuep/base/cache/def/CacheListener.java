/*
 * $Id: CacheListener.java, 2010-5-27 ����05:01:53 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.def;

/**
 * <p>
 * Title: CacheListener
 * </p>
 * <p>
 * Description:
 *      ��������������ڼ�����
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 ����05:01:53
 * modified [who date description]
 * check [who date description]
 */
public interface CacheListener<T> {
    /**
     * ������󱻴���
     * @param object
     *          �������
     */
    public void create(T object);
    /**
     * �������ɾ��
     * @param object
     *          �������
     */
    public void remove(T object);
    /**
     * ��������޸�
     * @param object
     *          �������
     */
    public void update(T object);

}
