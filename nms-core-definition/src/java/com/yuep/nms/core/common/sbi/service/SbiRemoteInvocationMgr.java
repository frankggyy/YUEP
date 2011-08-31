package com.yuep.nms.core.common.sbi.service;

/**
 * <p>
 * Title: SbiRemoteInvocationMgr
 * </p>
 * <p>
 * SBI��ͳһԶ�̵��ýӿ�
 * </p>
 * 
 * @author pl
 * created 2011-4-14 ����05:18:24
 * modified [who date description]
 * check [who date description]
 */
public interface SbiRemoteInvocationMgr {
    /**
     * Զ�̵��ýӿ�
     * @param deviceType �豸����
     * @param Version �豸�汾
     * @param serviceTypeName service����
     * @param methodName ���÷�����
     * @param paramType ���÷�����������
     * @param args ���÷�������
     * @return
     */
    public Object remoteInvoke(String deviceType, String Version, String serviceTypeName, String methodName,
            Class<?>[] paramType, Object[] args);
}
