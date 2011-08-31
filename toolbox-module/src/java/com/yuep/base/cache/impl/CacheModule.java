/*
 * $Id: CacheModule.java, 2011-3-7 ÉÏÎç10:16:09 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.impl;

import com.yuep.base.cache.def.CacheManager;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;

/**
 * <p>
 * Title: CacheModule
 * </p>
 * <p>
 * Description:»º´æÄ£¿é
 * </p>
 * 
 * @author sufeng
 * created 2011-3-7 ÉÏÎç10:16:09
 * modified [who date description]
 * check [who date description]
 */
public class CacheModule extends DefaultModule {

    @Override
    public void start() {
        DefaultCacheManager defaultCacheManager=new DefaultCacheManager();
        CoreContext.getInstance().setLocalService("cacheManager", CacheManager.class, defaultCacheManager);
    }

    @Override
    public void stop() {
        
    }

}
