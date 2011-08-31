package com.whnec.yuep.cases.module;

import com.whnec.yuep.cases.message.SessionMessageReceiver;
import com.whnec.yuep.cases.service.TimeService;
import com.whnec.yuep.cases.service.impl.TimeServiceImpl;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreUtils;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.SessionMessage;

public class ModuleA extends DefaultModule {

	private TimeService timeService;
	private MessageReceiver receiver;

	@Override
	public void start() {
		CoreUtils.initModuleLogger(this);

		timeService = new TimeServiceImpl();
		CoreContext.getInstance().setLocalService(TimeService.SERVICE_NAME,
				TimeService.class, timeService);
		CoreContext.getInstance().setRemoteService(TimeService.SERVICE_NAME,
				TimeService.class, timeService);

		receiver = new SessionMessageReceiver();
		CoreContext.getInstance().local()
				.subscribe(SessionMessage.NAME, receiver);

		getLogger().info("Module A started.");
		timeService.startTimeNotify(5);
	}

	@Override
	public void stop() {
		timeService.stopTimeNotify();
		timeService = null;
		
		CoreContext.getInstance().local().unregisterService(TimeService.SERVICE_NAME, TimeService.class);
		
		CoreContext.getInstance().local()
				.unsubscribe(SessionMessage.NAME, receiver);
		receiver = null;
		
		getLogger().info("Module A stopped.");
	}

}
