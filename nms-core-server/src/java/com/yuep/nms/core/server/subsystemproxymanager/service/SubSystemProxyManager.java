/*
 * $Id: SubSystemProxyManager.java, 2011-4-26 ����02:45:52 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;

/**
 * <p>
 * Title: SubSystemProxyManager
 * </p>
 * <p>
 * Description:
 * ��ϵͳ����ģ�����,���ڴ�����ɾ�����޸���ϵͳ����
 * </p>
 * 
 * @author yangtao
 * created 2011-4-26 ����02:45:52
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyManager {

    /**
     * ������ϵͳ����
     * @param subSystemProperty
     *        ��ϵͳ��������
     */
    public void createSubSystemProxy(SubSystemProxyProperty subSystemProperty);
    
    /**
     * ɾ����ϵͳ����
     * @param subSystemId
     *         ��ϵͳ��ʶ
     */
    public void deleteSubSystemProxy(MoNaming subSystemId);
    
    /**
     * ������ϵͳ����
     * @param subSystemProperty
     *          ��ϵͳ��������
     */
    public void updateSubSystemProxy(SubSystemProxyProperty subSystemProperty);
    
    /**
     * ����ϵͳ����subSystemId���õ�Mo��
     * @param subSystemId
     *         ��ϵͳ��ʶ
     * @param mo
     *         ��Ҫ��������ϵͳ��Mo
     */
    public void setSubSystemProxy(MoNaming subSystemId,MoNaming mo);
    
    /**
     * ����mo��ȡ���ڸ�mo�ϵ�SubSystemProxy
     * @param mo
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getBindSubSystemProxy(MoNaming mo);

    /**
     * ��ȡ������ϵͳId��ȡ��ϵͳ����
     * @param subSystemId
     *        ��ϵͳId
     * @return
     *        SubSystemProxy
     */
    public SubSystemProxy getSubSystemProxyBySubSystemId(MoNaming subSystemId);

}
