/*
 * $Id: ScopeFilter.java, 2011-3-29 上午09:24:50 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.util.Set;

import com.yuep.nms.core.common.mocore.naming.MoNaming;


/**
 * <p>
 * Title: ScopeFilter
 * </p>
 * <p>
 * Description: 管理范围过滤
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 上午09:24:50
 * modified [who date description]
 * check [who date description]
 */
public interface ScopeFilter {

    /**
     * 对管理范围进行过滤
     * @param moNamings
     * @return 是否在管理范围内 true在管理范围内 false不在管理范围内
     */
    public boolean isInScope(Set<MoNaming> moNamings);
    
    /**
     * 是否对象自己进行过滤，否则使用工具类（比如集合中）进行过滤
     * @return true自己过滤 false依附的类进行过滤
     */
    public boolean isSelfFilter();
    
}
