/*
 * $Id: ListElementUserType.java, 2009-2-25 上午11:11:25 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.usertype;

/**
 * <p>
 * Title: ListElementUserType
 * </p>
 * <p>
 * Description: 
 *        集合元素的用户类型;比如List中存放枚举类型.
 * </p>
 * 
 * @author yangtao
 * created 2009-2-25 上午11:11:25
 * modified [who date description]
 * check [who date description]
 */
public interface ListElementUserType<T> {
    /**
     * 将当前对象转化为字符串映射
     * 
     * @return
     */
    public String toString(T t, Class<T> type);

    /**
     * 将当前字符串转化为对象实例
     * 
     * @param str
     * @return
     */
    public T toObject(String str, Class<T> type);

}
