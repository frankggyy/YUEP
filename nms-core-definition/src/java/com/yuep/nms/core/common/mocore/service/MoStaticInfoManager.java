/*
 * $Id: MoStaticInfoManager.java, 2011-3-29 上午11:17:56 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import com.yuep.nms.core.common.mocore.model.MoStaticInfo;

/**
 * <p>
 * Title: MoStaticInfoManager
 * </p>
 * <p>
 * Description:
 * Mo静态信息管理接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 上午11:17:56
 * modified [who date description]
 * check [who date description]
 */
public interface MoStaticInfoManager {
    /**
     * 根据Mo详细类型获取Mo静态信息
     * @param type
     * @return
     *        Mo静态信息
     */
    MoStaticInfo getMoStaticInfo (String type);
    
    /**
     * 在父类型下是否能接受指定的子类型
     * @param parentType
     *        父类型
     * @param childType
     *        子类型
     * @return
     *        true:可以接受,false:不可以接受
     */
    boolean accept(String parentType,String childType);
    
    
    /**
     * 类型type是否包含在subKinds中
     * @param subKinds
     *        管理对象子类型
     * @param type
     *        管理对象详细类型
     * @return
     *        true:包含,false:不包含
     */
    boolean contain(String type,String... subKinds);
}
