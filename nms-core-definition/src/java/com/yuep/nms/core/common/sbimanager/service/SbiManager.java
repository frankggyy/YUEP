/*
 * $Id: SbiManager.java, 2011-4-15 ����12:29:11 yangtao Exp $
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
 * SBI����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 ����12:29:11
 * modified [who date description]
 * check [who date description]
 */
public interface SbiManager {
    /**
     * ����SBI
     * @param sbiProperty
     *        sbi��������
     * @param nm
     *        ���ܶ���
     */
    public SbiProperty createSbi(MoNaming nm,SbiProperty sbiProperty);
    
    /**
     * ɾ��SBI
     * @param sbiNaming
     *        sbiΨһ��ʾ
     */
    public void deleteSbi(MoNaming sbiNaming);
    
    /**
     * ����SBI
     * @param sbiProperty
     */
    public void setSbiProperty(SbiProperty sbiProperty);
    
    /**
     * ��ȡ���е�SBI
     * @param nm
     *        ����Ψһ��ʾ
     * @return 
     */
    public List<SbiProperty> getAllSbiProperties(MoNaming nm);
    
    /**
     * ��ȡSBI
     * @param sbiNaming
     * @return
     */
    public SbiProperty getSbiProperty(MoNaming sbiNaming);
    
    /**
     * ����Ne��SBI
     * @param sbiName
     * @param ne
     */
    public void setNeToSbi(MoNaming sbiNaming,MoNaming ne);

}
