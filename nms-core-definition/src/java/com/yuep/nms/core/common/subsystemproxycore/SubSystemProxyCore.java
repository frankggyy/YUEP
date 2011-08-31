/*
 * $Id: SubSystemProxyCore.java, 2011-5-24 ����02:51:33 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemProxyCore
 * </p>
 * <p>
 * Description:
 *  SubSystemProxy����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 ����02:51:33
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyCore {
    /**
     * ������ϵͳ����
     * @param subSystemProxy
     */
    public void createSubSystemProxy(SubSystemProxy subSystemProxy);
    /**
     * ������ϵͳ����
     * @param subSystemProxyProperty
     */
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * ɾ����ϵͳ����ϵͳ
     * @param subSystemId
     */
    public void deleteSubSystemProxy(MoNaming subSystemId);
    /**
     * ��ȡ����Mo�ϵ���ϵͳ����
     * @return
     *       ��ϵͳ����
     */
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo);
    
    /**
     * ������ϵͳId��ȡ��ϵͳ����
     * @param subSystemId
     *        ��ϵͳId
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId);
    /**
     * ����ϵͳ����
     * @param mo
     *        ���󶨵�Mo
     * @param subSystemId
     *        ��ϵͳId
     */
    public void bindSubSystemProxy(MoNaming mo,MoNaming subSystemId);
    /**
     * ������ϵͳ���ͻ�ȡ
     * @param subSystemType
     *        ��ϵͳ����
     * @return 
     *        ��ϵͳ������
     */
    public List<SubSystemProxy> getSubSystemProxies(String subSystemType);
    /**
     * ע����ϵͳ����������
     * @param subSystemType
     * @param subSystemRequestIntecepter
     * 
     */
    public void registerSubSystemRequestIntecepter(String subSystemType,SubSystemRequestIntercepter subSystemRequestIntecepter);
   
    /**
     * ������ϵͳ���ͻ�ȡ��ϵͳ����������
     * @param subSystemType
     *         ��ϵͳ����
     * @param SubSystemRequestIntercepter
     *         ��ϵͳ��������������
     */
    public SubSystemRequestIntercepter getSubSystemRequestIntecepter(String subSystemType);
}
