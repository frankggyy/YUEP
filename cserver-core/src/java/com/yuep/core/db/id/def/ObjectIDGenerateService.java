/*
 * $Id: ObjectIDGenerateService.java, 2011-3-29 下午12:31:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.def;

/**
 * <p>
 * Title: ObjectIDGenerateService
 * </p>
 * <p>
 * Description:
 * 对象唯一ID产服务
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午12:31:58
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectIDGenerateService {
    /**
     * 根据objectType生成一个唯一的ID
     * @param objectType
     *        
     * @return
     */
    public Long generateObjectId(String objectType);
}
