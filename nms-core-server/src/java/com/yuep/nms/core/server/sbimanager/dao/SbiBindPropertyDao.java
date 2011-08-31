/*
 * $Id: SbiBindPropertyDao.java, 2011-4-15 ����01:16:33 yangtao Exp $
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
 * SbiBindProperty���ݷ��ʲ�ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 ����01:16:33
 * modified [who date description]
 * check [who date description]
 */
public interface SbiBindPropertyDao {
    /**
     * ����SBI����Ϣ
     * @param sbiBindProperty
     */
    public void createSbiBindProperty(SbiBindProperty sbiBindProperty);
    
    /**
     * ����SBI����Ϣ
     * @param sbiBindProperty
     */
    public void setSbiBindProperty(SbiBindProperty sbiBindProperty); 
    /**
     * ������ԪMoNaming,��ȡSbiBindProperty
     * @param ne
     *        ��ԪMoNaming
     * @return
     *        SbiBindProperty
     */
    public SbiBindProperty getSbiBindProperty(MoNaming ne);
    /**
     * ����sbiNaming,��ѯsbi����Ϣ
     */
    public List<SbiBindProperty> getSbiBindProperties(MoNaming sbiNaming);
    /**
     * ������ԪMoNaming,ɾ��SBI����Ϣ
     * @param ne
     */
    public void deleteSbiBindPropertyByNe(MoNaming ne);
    
    /**
     * ����sbiNaming,ɾ��SBI����Ϣ
     * @param sbiNaming
     */
    public void deleteSbiBindPropertyBySbiNaming(MoNaming sbiNaming);

}
