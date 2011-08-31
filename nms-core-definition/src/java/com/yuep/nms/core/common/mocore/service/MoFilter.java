/*
 * $Id: MoFilter.java, 2011-3-28 上午11:38:44 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import com.yuep.nms.core.common.mocore.model.Mo;


/**
 * <p>
 * Title: MoFilter
 * </p>
 * <p>
 * Description:
 * Mo过滤接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:38:44
 * modified [who date description]
 * check [who date description]
 */
public interface MoFilter{
    /**
     * 是否被过滤
     * @param mo
     *        管理对象
     * @return
     *        true:没有被过滤
     *        false:被过滤
     */
    public boolean accept(Mo mo);

}
