/*
 * $Id: SmCoreClientService.java, 2011-3-29 ����11:16:37 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.def;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SmCoreClientService
 * </p>
 * <p>
 * Description: sm core�ͻ��˶�����ģ���ṩ�ı��ؽӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����11:16:37
 * modified [who date description]
 * check [who date description]
 */
public interface SmCoreClientService {

    /**
     * client��½���ʼ��SM Core Client,�������Ȩ�޵�
     * @param currentUserName
     */
    public void initAfterLogin(String currentUserName);
    
    /**
     * �õ���ǰ��¼���û�
     * @return
     */
    public String getCurrentUser();
    
    /**
     * ��ǰ��¼�ͻ��˵�session id
     * @return
     */
    public Long getCurrentSessionId();
    
    /**
     * ��ǰ��¼�û���moNamings�Ƿ���actionid��Ȩ��
     * @param actionId
     * @param moNamings
     * @return
     */
    public boolean check(String actionId,MoNaming ... moNamings);
    
    /**
     * �õ���ǰ��¼�û��ĸ��Ի���Ϣ
     * @param propKey
     * @return
     */
    public String getUserCustom(String propKey);
    
    /**
     * ���õ�ǰ��¼�û��ĸ��Ի���Ϣ
     * @param propKey
     * @param value
     */
    public void setUserCustom(String propKey,String value);
    
    /**
     * ע��һ�������û����Ի���Ϣ��listener
     * @param listener
     */
    public void registerUserCustomChangeListener(UserCustomChangeListener listener);
    
    /**
     * ע��һ�������û����Ի���Ϣ��listener
     * @param listener
     */
    public void unregisterUserCustomChangeListener(UserCustomChangeListener listener);

}
