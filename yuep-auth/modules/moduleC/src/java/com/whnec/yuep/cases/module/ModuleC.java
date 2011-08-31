package com.whnec.yuep.cases.module;

import com.whnec.yuep.cases.message.SessionMessageReceiver;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreUtils;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.SessionMessage;

public class ModuleC extends DefaultModule {

	RemoteCommunicateObject rco;
	MessageReceiver receiver;
	MessageReceiver sessionMsgReceiver;

	@Override
	public void start() {
	    CoreUtils.initModuleLogger(this);
		
		sessionMsgReceiver = new SessionMessageReceiver();
		CoreContext.getInstance().local()
				.subscribe(SessionMessage.NAME, sessionMsgReceiver);
		getLogger().info("Module C started.");

		// invoke service
		String remoteIp = moduleParams.get("remote.ip");
		if(remoteIp==null)
		    remoteIp="localhost";
		
        String remotePort = moduleParams.get("remote.port");
        if(remotePort==null)
            remotePort=ModuleContext.getInstance().getSystemParam("rmi.port");
        if(remotePort==null)
            remotePort="9000";
        
		rco = CoreContext.getInstance().remote(remoteIp, Integer.valueOf(remotePort));
	}

	@Override
	public void stop() {
		if (rco != null) {
			try {
				rco.cleanup();
			} catch (Exception e) {
				getLogger().error(
						"Exception on disconnect from " + rco.getRemoteIp()
								+ ":" + rco.getRemoteServerPort());
			}
			rco = null;
		}
		if (receiver != null) {
			try {
				CoreContext.getInstance().local()
						.unsubscribe(SessionMessage.NAME, sessionMsgReceiver);
			} catch (Exception e) {
				getLogger().error(
						"Exception on unsubscribe local message: "
								+ SessionMessage.NAME, e);
			}
			receiver = null;
		}
		getLogger().info("Module C stopped.");
	}
}
