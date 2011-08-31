package com.yuep.nms.biz.sbi.eqm;

import java.util.List;

import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;
import com.yuep.nms.core.sbi.sbicore.SbiContext;
import com.yuep.nms.core.sbi.sbicore.util.SbiAdaptorUtil;

/**
 * sbi测设备管理模块
 * <p>
 * Title: SbiEqmModule
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author pl
 */
public class SbiEqmModule extends DefaultModule {

    @Override
    public void start() {
        // 解析Adaptor配置并发布为本地服务
        String filePath = getModulePath() + "conf\\appctx-sbiadaptor.xml";
        SbiAdaptorUtil util = new SbiAdaptorUtil();
        List<DeviceAdaptor> adaptorList = util.parseAdaptorXml(filePath);
        SbiContext.deviceAdaptorList.addAll(adaptorList);
        util.rigisterServiceByAdaptor(adaptorList);
    }

    @Override
    public void stop() {
    }

}