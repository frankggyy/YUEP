/*
 * $Id: UserCustomChangeListener.java, 2011-3-29 上午11:18:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.def;

import com.yuep.nms.core.common.smcore.model.UserCustom;

/**
 * <p>
 * Title: UserCustomChangeListener
 * </p>
 * <p>
 * Description:用户个性化信息变化的监听器
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 上午11:18:47
 * modified [who date description]
 * check [who date description]
 */
public interface UserCustomChangeListener {

    /**
     * 当前用户的个性化信息变化了
     * @param updatedUserCustom 更新后的个性化信息
     */
    public void update(UserCustom updatedUserCustom);
    
    /**
     * 删除用户的个性化信息
     */
    public void clearAll();
    
}
