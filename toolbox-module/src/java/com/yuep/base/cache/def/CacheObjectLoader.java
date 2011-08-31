/*
 * $Id: CacheObjectLoader.java, 2010-5-27 ����04:59:43 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.def;

import java.util.Map;

/**
 * <p>
 * Title: CacheObjectLoader
 * </p>
 * <p>
 * Description:
 * Cache��ʼ���ӿڣ����ڳ�ʼ��Cache
 * </p>
 * 
 * @author yangtao
 * @param <K> ��
 * @param <T> ֵ
 */
public interface CacheObjectLoader<K,T> {
    
    /**
     * ���ػ������
     * @param K ��
     * @param T ֵ
     * @param loadParams
     *        ��ʼ������
     * @return
     *        Map,key=�������Ψһ��ʾ value=�������
     */
    public Map<K,T> loadCacheObjects(Object...loadParams);

}
