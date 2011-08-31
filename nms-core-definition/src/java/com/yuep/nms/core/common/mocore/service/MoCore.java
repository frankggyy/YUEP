/*
 * $Id: MoCore.java, 2011-3-28 ����11:34:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import java.util.List;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.cache.MoNode;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoCore
 * </p>
 * <p>
 * Description:
 * Mo���ķ���,��Ҫ���ڴ�����ɾ�����޸�Mo
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:34:58
 * modified [who date description]
 * check [who date description]
 */
public interface MoCore {
   
    /**
     * �����������
     * @param mos
     *        ��������б�
     */
    void createMos(List<Mo> mos);
    /**
     * ���¹������
     * @param mos
     *        ��������б�
     */
    void updateMos(List<Mo> mos);
    /**
     * ɾ���������
     * @param mos
     *       ��������б�
     */
    void deleteMos(List<Mo> mos);
    /**
     * ��ѯ�������
     * @param moNaming
     *        ��������ʾ
     * @return
     *        �������
     */
    Mo getMo(MoNaming moNaming);
    /**
     * ��ѯ�������
     * @param moFilter
     *        ���������˽ӿ�
     * @return
     *        ��������б�
     */
    List<Mo> getMos(MoFilter moFilter);
    /**
     * ���ݸ��ڵ��ѯ�ӹ������
     * @param moFilter
     *          ���������˽ӿ�
     * @param parents
     *          ���������
     * @return
     *        ��������б�
     */
    List<Mo> getChildrenMos(MoFilter moFilter,MoNaming...parents);
    
    /**
     * ��ȡ���еĹ������
     * @return
     *       ���й������
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getAllMos();
    
    /**
     * ��ȡ�Ե�ǰ����Ϊ�ڵ����ṹ
     * @param mo
     * @return
     */
    MoNode getMoNode(MoNaming mo);
    
    /**
     * ��ȡMo���ڵ�
     * @return
     */
    public Mo getRootMo();
    /**
     * ��ѯ����ڵ���������
     * @param nm
     *        ����ڵ��ʶ
     * @return
     *        ���������������
     */
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm);
    /**
     * ���¹��������������
     * @param managedNodeProperty
     *        ���������������
     */
    public void  updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);


}
