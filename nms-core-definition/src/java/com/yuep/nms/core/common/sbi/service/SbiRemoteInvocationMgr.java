package com.yuep.nms.core.common.sbi.service;

/**
 * <p>
 * Title: SbiRemoteInvocationMgr
 * </p>
 * <p>
 * SBI侧统一远程调用接口
 * </p>
 * 
 * @author pl
 * created 2011-4-14 下午05:18:24
 * modified [who date description]
 * check [who date description]
 */
public interface SbiRemoteInvocationMgr {
    /**
     * 远程调用接口
     * @param deviceType 设备类型
     * @param Version 设备版本
     * @param serviceTypeName service名称
     * @param methodName 调用方法名
     * @param paramType 调用方法参数类型
     * @param args 调用方法参数
     * @return
     */
    public Object remoteInvoke(String deviceType, String Version, String serviceTypeName, String methodName,
            Class<?>[] paramType, Object[] args);
}
