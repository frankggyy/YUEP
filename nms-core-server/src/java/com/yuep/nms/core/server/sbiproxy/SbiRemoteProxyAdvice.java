package com.yuep.nms.core.server.sbiproxy;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.proxy.impl.RemoteProxyAdvice;
import com.yuep.nms.core.common.sbi.service.SbiRemoteInvocationMgr;

/**
 * <p>
 * Title: SbiRemoteProxyAdvice
 * </p>
 * <p>
 * 封装SBI提供的远程调用
 * </p>
 * 
 * @author pl
 * created 2011-4-20 上午10:26:24
 * modified [who date description]
 * check [who date description]
 */
public class SbiRemoteProxyAdvice extends RemoteProxyAdvice {
    private String deviceType;
    private String version;
    private String serviceType;

    public SbiRemoteProxyAdvice(RemoteCommunicateObject remoteCommunicateObject, String deviceType, String version,
            String serviceType) {
        super(remoteCommunicateObject);
        this.deviceType = deviceType;
        this.version = version;
        this.serviceType = serviceType;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.proxy.impl.RemoteProxyAdvice#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();
        String methodName = method.getName();
        SbiRemoteInvocationMgr sbiRemoteInvocationMgr = getRemoteCommunicateObject().getService(
                "SbiRemoteInvocationMgr", SbiRemoteInvocationMgr.class);
        Object ret = null;
        try {
            ret = sbiRemoteInvocationMgr.remoteInvoke(deviceType, version, serviceType, methodName, method
                    .getParameterTypes(), arguments);
        } catch (Throwable ex) {
            Throwable actualEx = ExceptionUtils.getRawThrowable(ex);
            throw actualEx;
        }
        return ret;

    }

}
