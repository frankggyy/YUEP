/*
 * $Id: MgmtScopeService.java, 2011-3-24 上午11:18:25 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MgmtScopeService
 * </p>
 * <p>
 * Description:管理范围过滤接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:18:25
 * modified [who date description]
 * check [who date description]
 */
public interface MgmtScopeService {
    
    /**
     * 检查MO是否在管理范围内
     * @param user 用户名
     * @param moNamings MO名数组
     * @return true 在管理范围内
     */
    public boolean isInMgmtScope(String user, MoNaming ... moNamings); 
    
}
