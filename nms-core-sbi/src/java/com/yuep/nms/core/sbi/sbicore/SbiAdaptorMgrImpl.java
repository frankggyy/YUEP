package com.yuep.nms.core.sbi.sbicore;

import java.util.List;

import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;
import com.yuep.nms.core.common.sbi.service.SbiAdaptorMgr;

/**
 * <p>
 * Title: SbiAdaptorMgrImpl
 * </p>
 * <p>
 * SBI�����ṩAdaptor��ѯ�Ľӿ�ʵ����
 * </p>
 * 
 * @author pl
 * created 2011-4-16 ����04:03:06
 * modified [who date description]
 * check [who date description]
 */
public class SbiAdaptorMgrImpl implements SbiAdaptorMgr {

    @Override
    public List<DeviceAdaptor> getAllAdaptors() {
        return SbiContext.deviceAdaptorList;
    }

}
