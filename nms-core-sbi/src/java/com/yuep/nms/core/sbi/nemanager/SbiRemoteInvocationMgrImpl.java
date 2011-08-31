package com.yuep.nms.core.sbi.nemanager;

import java.lang.reflect.Method;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.sbi.service.SbiRemoteInvocationMgr;
import com.yuep.nms.core.common.sbi.util.SbiCommonUtil;
import com.yuep.nms.core.sbi.sbicore.util.SbiAdaptorUtil;

/**
 * <p>
 * Title: SbiRemoteInvocationMgrImpl
 * </p>
 * <p>
 * SBI侧统一远程调用接口实现类
 * </p>
 * 
 * @author pl
 * created 2011-4-14 下午05:17:03
 * modified [who date description]
 * check [who date description]
 */
public class SbiRemoteInvocationMgrImpl implements SbiRemoteInvocationMgr {
    
    @SuppressWarnings("unchecked")
    public Object remoteInvoke(String deviceType, String version, String serviceTypeName, String methodName,
            Class<?>[] paramType, Object[] args) {
        // 得到服务名
        String serviceName = SbiCommonUtil.getAdaptorServiceName(deviceType, version, serviceTypeName);
        SbiAdaptorUtil util=new SbiAdaptorUtil();
        // 得到adaptor接口类
        Class intfClass = util.getServiceClassByName(serviceName);
        // 获取本地服务
        Object serviceObject = CoreContext.getInstance().local().getService(serviceName, intfClass);
        try {
            // 调用adaptor的方法
            Method method = intfClass.getMethod(methodName, paramType);
            return method.invoke(serviceObject, args);
        } catch (Exception ex) {
            throw new YuepException(ex);
        }
    }
    
}
