/*
 * $Id: MoManager.java, 2011-3-28 ����11:47:09 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.momanager.service;

import java.util.List;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoFilter;

/**
 * <p>
 * Title: MoManager
 * </p>
 * <p>
 * Description:
 * Mo����ӿڣ��ϲ�Ӧ����Ҫʵ��
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:47:09
 * modified [who date description]
 * check [who date description]
 */
public interface MoManager {
    /**
     * ��ѯ���й������
     * @return
     *       ��������б�
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getAllMos();
    /**
     * ��ѯ�������
     * @param moNaming
     *        ��������ʶ
     * @return
     *        �������
     */
    Mo getMo(MoNaming moNaming);
    /**
     * ��ѯ�������
     * @param moFilter
     *        ����������
     * @return
     *         ��������б�
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getMos(MoFilter moFilter);
    /**
     * ��ѯ�ӹ������
     * @param moFilter
     *          ����������
     * @param parents
     *          ������ڵ�
     * @return
     *          ��������б�
     */
    @FacadeMethod(scopeFilter=true)
    List<Mo> getChildrenMos(MoFilter moFilter,MoNaming...parents);
    /**
     * ���¹���ڵ���������
     * @param managedNodeProperty
     *          ����ڵ���������
     *         
     */
    void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);
    /**
     * ��ѯ����ڵ���������
     * @param nm
     *        ����ڵ��ʶ
     * @return
     *      ����ڵ���������
     */
    ManagedNodeProperty getManagedNodeProperty (MoNaming nm);
    /**
     * ��������ڵ���������
     * @param parent
     *        ������ڵ�
     * @param managedNodeProperty
     *        ����ڵ���������
     * @param type
     *        ����ڵ�����
     * @return
     *       ����ڵ�Mo
     */
    @FacadeMethod(scopeFilter=true)
    Mo  createManagedNode(MoNaming parent,ManagedNodeProperty managedNodeProperty,String type);
    /**
     * ɾ������ڵ�
     * @param nm
     *        ����ڵ��ʶ
     */
    void deleteManagedNode(MoNaming nm);
    /**
     * �����������
     * @param parent
     *        ������ڵ�
     * @param domainName
     *        ������������
     * @param type
     *        ������������
     * @return
     *        �������Mo
     */
    @FacadeMethod(scopeFilter=true)
    Mo createManagedDomain(MoNaming parent,String domainName,String type);
    /**
     * ɾ���������
     * @param domainNaming
     *        Mo��ʶ
     */
    void deleteManagedDomain (MoNaming domainNaming);

}
