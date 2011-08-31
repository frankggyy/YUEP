/*
 * $Id: RemoteInvocationFacade.java, 2009-11-5 ����10:41:30 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.facade.def;



/**
 * <p>
 * Title: RemoteInvocationFacade
 * </p>
 * <p>
 * Description: Զ�̵���ͳһ��ת������
 * </p>
 * 
 * @author yangtao
 * created 2009-11-5 ����10:41:30
 * modified [who date description]
 * check [who date description]
 */
public interface RemoteInvocationFacade {
    
    /**
     * ת�����õĽӿ�
     * @param clz Զ�̽ӿ���
     * @param methodName ������
     * @param paramType �����������
     * @param args      �����б�
     * @param sessionId �Ựid
     * @return
     * @throws
     */
    public Object remoteInvoke(Class<?> clz, String methodName, Class<?>[] paramType,
            Object[] args, Long sessionId) throws Throwable ;
    
}
