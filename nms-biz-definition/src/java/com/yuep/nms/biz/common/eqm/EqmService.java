package com.yuep.nms.biz.common.eqm;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

public interface EqmService {
    public String getNeUserLabel(MoNaming neNaming);

    public void updateNeUserLabel(MoNaming neNaming, String userLabel);
}
