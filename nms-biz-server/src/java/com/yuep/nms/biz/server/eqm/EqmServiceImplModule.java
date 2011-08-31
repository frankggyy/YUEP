package com.yuep.nms.biz.server.eqm;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.biz.common.eqm.EqmService;

public class EqmServiceImplModule extends DefaultModule {

    @Override
    public void start() {
        EqmService eqmService = new EqmServiceImpl();
        CoreContext.getInstance().setRemoteService("eqmService", EqmService.class, eqmService);
    }

    @Override
    public void stop() {
    }

}