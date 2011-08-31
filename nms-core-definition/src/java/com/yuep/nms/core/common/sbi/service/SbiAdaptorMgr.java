package com.yuep.nms.core.common.sbi.service;

import java.util.List;

import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;

/**
 * <p>
 * Title: SbiAdaptorMgr
 * </p>
 * <p>
 * SBI对外提供Adaptor查询的接口
 * </p>
 * 
 * @author pl
 * created 2011-4-16 下午03:16:28
 * modified [who date description]
 * check [who date description]
 */
public interface SbiAdaptorMgr {
    /**
     * 得到所有Adaptor
     * @return
     */
    public List<DeviceAdaptor> getAllAdaptors();
}
