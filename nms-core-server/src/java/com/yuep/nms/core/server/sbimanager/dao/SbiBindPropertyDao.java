/*
 * $Id: SbiBindPropertyDao.java, 2011-4-15 下午01:16:33 yangtao Exp $
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
import com.yuep.nms.core.common.sbimanager.model.SbiBindProperty;

/**
 * <p>
 * Title: SbiBindPropertyDao
 * </p>
 * <p>
 * Description:
 * SbiBindProperty数据访问层接口
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午01:16:33
 * modified [who date description]
 * check [who date description]
 */
public interface SbiBindPropertyDao {
    /**
     * 创建SBI绑定信息
     * @param sbiBindProperty
     */
    public void createSbiBindProperty(SbiBindProperty sbiBindProperty);
    
    /**
     * 设置SBI绑定信息
     * @param sbiBindProperty
     */
    public void setSbiBindProperty(SbiBindProperty sbiBindProperty); 
    /**
     * 根据网元MoNaming,获取SbiBindProperty
     * @param ne
     *        网元MoNaming
     * @return
     *        SbiBindProperty
     */
    public SbiBindProperty getSbiBindProperty(MoNaming ne);
    /**
     * 根据sbiNaming,查询sbi绑定信息
     */
    public List<SbiBindProperty> getSbiBindProperties(MoNaming sbiNaming);
    /**
     * 根据网元MoNaming,删除SBI绑定信息
     * @param ne
     */
    public void deleteSbiBindPropertyByNe(MoNaming ne);
    
    /**
     * 根据sbiNaming,删除SBI绑定信息
     * @param sbiNaming
     */
    public void deleteSbiBindPropertyBySbiNaming(MoNaming sbiNaming);

}
