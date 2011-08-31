/*
 * $Id: ItemFilter.java, 2009-6-11 上午10:43:46 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;
/**
 * <p>
 * Title: ItemFilter
 * </p>
 * <p>
 * Description:枚举型自动校验控件的过滤器接口
 * </p>
 * 
 * @author Yao
 * created 2009-6-11 上午10:43:46
 * modified [who date description]
 * check [who date description]
 */
public interface ItemFilter {
    /**
     * 发回要过滤的枚举值
     * @return 枚举值的数组
     */
    Enum<?>[] filterEnums();
}
