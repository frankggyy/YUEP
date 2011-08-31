package com.yuep.nms.biz.common.sbi;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

public interface EqmAdaptor {
    public String getNeUserLabel(MoNaming neNaming);

    public void updateNeUserLabel(MoNaming neNaming, String userLabel);
}
