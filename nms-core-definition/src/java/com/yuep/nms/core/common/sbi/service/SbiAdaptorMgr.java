package com.yuep.nms.core.common.sbi.service;

import java.util.List;

import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;

/**
 * <p>
 * Title: SbiAdaptorMgr
 * </p>
 * <p>
 * SBI�����ṩAdaptor��ѯ�Ľӿ�
 * </p>
 * 
 * @author pl
 * created 2011-4-16 ����03:16:28
 * modified [who date description]
 * check [who date description]
 */
public interface SbiAdaptorMgr {
    /**
     * �õ�����Adaptor
     * @return
     */
    public List<DeviceAdaptor> getAllAdaptors();
}
