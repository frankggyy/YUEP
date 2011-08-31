package com.yuep.nms.biz.server.eqm;

import java.util.List;

import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.nms.biz.common.eqm.EqmService;
import com.yuep.nms.biz.common.sbi.EqmAdaptor;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;
import com.yuep.nms.core.common.sbi.model.NeLoginParameter;
import com.yuep.nms.core.common.sbi.service.SbiAdaptorMgr;
import com.yuep.nms.core.common.sbi.service.SbiSysMgr;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.module.constants.SubSystemProxyCoreDefinationModuleConstants;

public class EqmServiceImpl implements EqmService {

    @Override
    public String getNeUserLabel(MoNaming neNaming) {
        SubSystemProxyCore subSystemProxyCore = CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        SubSystemProxy sbiProxy = subSystemProxyCore.getBindSubSystemProxy(neNaming);
        RemoteCommunicateObject remote = CoreContext.getInstance().remote(sbiProxy.getSubSystemProxyProperty().getIp(), sbiProxy.getSubSystemProxyProperty().getPort());
        SbiSysMgr sysMgr = remote.getService("SbiSysMgr",SbiSysMgr.class);
        sysMgr.addNe(new NeLoginParameter());
        SbiAdaptorMgr adaptorMgr = remote.getService("SbiAdaptorMgr",SbiAdaptorMgr.class);
        List<DeviceAdaptor> adaptors = adaptorMgr.getAllAdaptors();
        System.out.println(adaptors);
        EqmAdaptor eqmAdaptor = sbiProxy.getService(neNaming, "eqm", EqmAdaptor.class);
        return eqmAdaptor.getNeUserLabel(neNaming);
    }

    @Override
    public void updateNeUserLabel(MoNaming neNaming, String userLabel) {
        SubSystemProxyCore subSystemProxyCore = CoreContext.getInstance().local().getService(SubSystemProxyCoreDefinationModuleConstants.SUBSYSTEMPROXYCORE_LOCAL_SERVICE, SubSystemProxyCore.class);
        SubSystemProxy sbiProxy = subSystemProxyCore.getBindSubSystemProxy(neNaming);
        EqmAdaptor eqmAdaptor = sbiProxy.getService(neNaming, "eqm", EqmAdaptor.class);
        eqmAdaptor.updateNeUserLabel(neNaming, userLabel);
    }

}
