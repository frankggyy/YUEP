/*
 * $Id: ValueCompareObjectMapGetter.java, 2011-3-30 下午03:36:51 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.util.Map;

/**
 * <p>
 * Title: ValueCompareObjectMapGetter
 * </p>
 * <p>
 * Description:获取一个对象的哪些属性发生了变化
 * </p>
 * 
 * @author sufeng
 * created 2011-3-30 下午03:36:51
 * modified [who date description]
 * check [who date description]
 */
public interface ValueCompareObjectMapGetter<T> {

    /**
     * 比较新旧对象变化的属性，状态值（只需要返回重要的属性和状态）
     * @param otherObject this与该对象进行对比
     * @return
     */
    public Map<String,ValueCompareObject> getValueCompareObjectMap(T otherObject);
    
}
