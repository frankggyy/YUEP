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
 * SBI��ͳһԶ�̵��ýӿ�ʵ����
 * </p>
 * 
 * @author pl
 * created 2011-4-14 ����05:17:03
 * modified [who date description]
 * check [who date description]
 */
public class SbiRemoteInvocationMgrImpl implements SbiRemoteInvocationMgr {
    
    @SuppressWarnings("unchecked")
    public Object remoteInvoke(String deviceType, String version, String serviceTypeName, String methodName,
            Class<?>[] paramType, Object[] args) {
        // �õ�������
        String serviceName = SbiCommonUtil.getAdaptorServiceName(deviceType, version, serviceTypeName);
        SbiAdaptorUtil util=new SbiAdaptorUtil();
        // �õ�adaptor�ӿ���
        Class intfClass = util.getServiceClassByName(serviceName);
        // ��ȡ���ط���
        Object serviceObject = CoreContext.getInstance().local().getService(serviceName, intfClass);
        try {
            // ����adaptor�ķ���
            Method method = intfClass.getMethod(methodName, paramType);
            return method.invoke(serviceObject, args);
        } catch (Exception ex) {
            throw new YuepException(ex);
        }
    }
    
}
