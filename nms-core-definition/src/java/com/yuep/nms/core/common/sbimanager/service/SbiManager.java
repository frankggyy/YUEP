/*
 * $Id: SbiManager.java, 2011-4-15 下午12:29:11 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.sbimanager.service;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;

/**
 * <p>
 * Title: SbiManager
 * </p>
 * <p>
 * Description:
 * SBI管理接口
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午12:29:11
 * modified [who date description]
 * check [who date description]
 */
public interface SbiManager {
    /**
     * 创建SBI
     * @param sbiProperty
     *        sbi基本属性
     * @param nm
     *        网管对象
     */
    public SbiProperty createSbi(MoNaming nm,SbiProperty sbiProperty);
    
    /**
     * 删除SBI
     * @param sbiNaming
     *        sbi唯一标示
     */
    public void deleteSbi(MoNaming sbiNaming);
    
    /**
     * 更新SBI
     * @param sbiProperty
     */
    public void setSbiProperty(SbiProperty sbiProperty);
    
    /**
     * 获取所有的SBI
     * @param nm
     *        网管唯一标示
     * @return 
     */
    public List<SbiProperty> getAllSbiProperties(MoNaming nm);
    
    /**
     * 获取SBI
     * @param sbiNaming
     * @return
     */
    public SbiProperty getSbiProperty(MoNaming sbiNaming);
    
    /**
     * 设置Ne到SBI
     * @param sbiName
     * @param ne
     */
    public void setNeToSbi(MoNaming sbiNaming,MoNaming ne);

}
