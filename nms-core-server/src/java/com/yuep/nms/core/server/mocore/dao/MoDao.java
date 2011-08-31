/*
 * $Id: MoDao.java, 2011-3-28 上午11:59:14 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao;

import java.util.List;

import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: MoDao
 * </p>
 * <p>
 * Description:
 * Mo数据访问接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:59:14
 * modified [who date description]
 * check [who date description]
 */
public interface MoDao {
    /**
     * 创建Mo
     * @param mos
     *        Mo列表
     */
    public void createMos(List<Mo> mos);
    /**
     * 删除Mo
     * @param mos
     *         Mo列表
     */
    public void deleteMos(List<Mo> mos);
    
    /**
     * 更新Mo
     * @param mos
     */
    public void updateMos(List<Mo> mos);
    /**
     * 获取所有Mo
     * @return
     */
    public List<Mo> getAllMos();

}
