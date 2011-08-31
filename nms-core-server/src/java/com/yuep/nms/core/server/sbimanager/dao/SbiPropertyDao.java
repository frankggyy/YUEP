/*
 * $Id: SbiPropertyDao.java, 2011-4-15 ����12:40:39 yangtao Exp $
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
 * SbiProperty Dao���ݷ��ʲ�ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 ����12:40:39
 * modified [who date description]
 * check [who date description]
 */
public interface SbiPropertyDao {
    /**
     * ����SbiProperty
     * @param sbiProperty
     */
    public void createSbiProperty(SbiProperty sbiProperty);
    /**
     * ɾ��SbiProperty
     * @param sbiNaming
     *         sbi��ʶ
     */
    public void deleteSbiProperty(MoNaming sbiNaming);
    /**
     * ����SbiProperty
     * @param 
     *      sbiProperty  
     */
    public void updateSbiProperty(SbiProperty sbiProperty);
    /**
     * ��ȡ����SbiProperty
     * @return
     *         ����SbiProperty
     */
    public List<SbiProperty> getAllSbiPropertys();
    
    /**
     * ����SBI���ƻ�ȡSbiProperty
     * @param sbiNaming
     *        sbi��ʶ
     * @return
     *        SbiProperty
     */
    public SbiProperty getSbiProperty(MoNaming sbiNaming);
    
    /**
     * ����IP�Ͷ˿ڻ�ȡSbiProperty
     * @param ip
     *        sbi��ip��ַ
     *        
     * @param port
     *        sbi�˿�
     * @return
     *       SbiProperty
     */
    public SbiProperty getSbiPropertyByIpPort(String ip,int port);
    
    /**
     * ����Sbi���ƻ�ȡSbiProperty
     * @param sbiName
     *        Sbi����
     * @return
     *        SbiProperty
     */
    public SbiProperty getSbiProperty(String sbiName);

}
