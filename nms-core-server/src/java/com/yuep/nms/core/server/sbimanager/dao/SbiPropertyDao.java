/*
 * $Id: SbiPropertyDao.java, 2011-4-15 下午12:40:39 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.dao;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;

/**
 * <p>
 * Title: SbiPropertyDao
 * </p>
 * <p>
 * Description:
 * SbiProperty Dao数据访问层接口
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午12:40:39
 * modified [who date description]
 * check [who date description]
 */
public interface SbiPropertyDao {
    /**
     * 创建SbiProperty
     * @param sbiProperty
     */
    public void createSbiProperty(SbiProperty sbiProperty);
    /**
     * 删除SbiProperty
     * @param sbiNaming
     *         sbi标识
     */
    public void deleteSbiProperty(MoNaming sbiNaming);
    /**
     * 更新SbiProperty
     * @param 
     *      sbiProperty  
     */
    public void updateSbiProperty(SbiProperty sbiProperty);
    /**
     * 获取所有SbiProperty
     * @return
     *         所有SbiProperty
     */
    public List<SbiProperty> getAllSbiPropertys();
    
    /**
     * 根据SBI名称获取SbiProperty
     * @param sbiNaming
     *        sbi标识
     * @return
     *        SbiProperty
     */
    public SbiProperty getSbiProperty(MoNaming sbiNaming);
    
    /**
     * 根据IP和端口获取SbiProperty
     * @param ip
     *        sbi的ip地址
     *        
     * @param port
     *        sbi端口
     * @return
     *       SbiProperty
     */
    public SbiProperty getSbiPropertyByIpPort(String ip,int port);
    
    /**
     * 根据Sbi名称获取SbiProperty
     * @param sbiName
     *        Sbi名称
     * @return
     *        SbiProperty
     */
    public SbiProperty getSbiProperty(String sbiName);

}
