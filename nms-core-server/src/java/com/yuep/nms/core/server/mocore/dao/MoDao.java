/*
 * $Id: MoDao.java, 2011-3-28 ����11:59:14 yangtao Exp $
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
 * Mo���ݷ��ʽӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:59:14
 * modified [who date description]
 * check [who date description]
 */
public interface MoDao {
    /**
     * ����Mo
     * @param mos
     *        Mo�б�
     */
    public void createMos(List<Mo> mos);
    /**
     * ɾ��Mo
     * @param mos
     *         Mo�б�
     */
    public void deleteMos(List<Mo> mos);
    
    /**
     * ����Mo
     * @param mos
     */
    public void updateMos(List<Mo> mos);
    /**
     * ��ȡ����Mo
     * @return
     */
    public List<Mo> getAllMos();

}
