package com.whnec.yuep.cases.module;

import com.whnec.yuep.cases.facade.FacadeProcessorImpl;
import com.whnec.yuep.cases.message.TimeNotifyMessageReceiver;
import com.whnec.yuep.cases.proxy.ProxyProcessorImpl;
import com.whnec.yuep.cases.service.TimeService;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.interaction.def.facade.FacadeManager;
import com.yuep.core.interaction.def.facade.FacadeProcessor;
import com.yuep.core.interaction.def.proxy.ProxyManager;
import com.yuep.core.interaction.def.proxy.ProxyProcessor;
import com.yuep.core.module.def.DefaultModule;

public class ModuleE extends DefaultModule {

	private FacadeProcessor facadeProcessor;
	private ProxyProcessor proxyProcessor;

	@Override
	public void start() {
//		facadeProcessor = new FacadeProcessorImpl();
//		CoreContext.getInstance().local()
//				.getService("facadeManager", FacadeManager.class)
//				.setFacadeProcessor(facadeProcessor);

		proxyProcessor = new ProxyProcessorImpl();
		getLogger().info("Module E started.");

		RemoteCommunicateObject rco = CoreContext.getInstance().remote(
				"172.28.72.200", 9000);
//		CoreContext.getInstance().local()
//				.getService("proxyManager", ProxyManager.class)
//				.setProxyProcessor(rco, proxyProcessor);

		rco.getService(TimeService.SERVICE_NAME, TimeService.class)
				.getCurrentTime();
		rco.subscribe(TimeService.MSG_TIMENOTIFY,
				new TimeNotifyMessageReceiver());
	}

	@Override
	public void stop() {
		getLogger().info("Module E stopped.");
	}

}
