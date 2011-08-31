package com.whnec.yuep.cases.module;

import com.whnec.yuep.cases.message.SessionMessageReceiver;
import com.yuep.core.client.module.ClientModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreUtils;
import com.yuep.core.session.def.SessionMessage;

public class ModuleD extends ClientModule {

	private SessionMessageReceiver sessionMsgReceiver;

	@Override
	public void start() {

	    CoreUtils.initModuleLogger(this);

		sessionMsgReceiver = new SessionMessageReceiver();
		CoreContext.getInstance().local()
				.subscribe(SessionMessage.NAME, sessionMsgReceiver);

		getLogger().info(getModuleName() + " started.");
	}

	@Override
	public void stop() {

		if (sessionMsgReceiver != null) {
			CoreContext.getInstance().local()
					.unsubscribe(SessionMessage.NAME, sessionMsgReceiver);
			sessionMsgReceiver = null;
		}

		getLogger().info(getModuleName() + " stopped.");
	}
}
