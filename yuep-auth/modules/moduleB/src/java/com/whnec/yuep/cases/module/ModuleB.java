package com.whnec.yuep.cases.module;

import java.io.Serializable;

import com.whnec.yuep.cases.message.SessionMessageReceiver;
import com.whnec.yuep.cases.service.TimeService;
import com.yuep.base.exception.YuepException;
import com.yuep.base.util.format.DateFormatter;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.DefaultModule;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.CoreUtils;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

public class ModuleB extends DefaultModule {

	MessageReceiver receiver;
	MessageReceiver sessionMsgReceiver;
	CommunicateObject co;
	RemoteCommunicateObject rco;

	@Override
	public void start() {
	    CoreUtils.initModuleLogger(this);

		getLogger().info("Module B started.");

		receiver = new TimeNotifyMessageReceiver();
		sessionMsgReceiver = new SessionMessageReceiver();

		// local cases
		// co = CoreContext.getInstance().local();
		// invoke(co, receiver);

		// remote(localhost) cases
		String remoteIp = moduleParams.get("remote.ip");
        if(remoteIp==null)
            remoteIp="localhost";
        
        String remotePort = moduleParams.get("remote.port");
        if(remotePort==null)
            remotePort=ModuleContext.getInstance().getSystemParam("rmi.port");
        if(remotePort==null)
            remotePort="9000";
		rco = CoreContext.getInstance().remote(remoteIp, Integer.valueOf(remotePort));
		invoke(rco, receiver);

	}

	void invoke(CommunicateObject co, MessageReceiver receiver) {
		String location = (co instanceof RemoteCommunicateObject) ? "remote["
				+ ((RemoteCommunicateObject) co).getRemoteIp() + "]" : "local";
		// get services
		TimeService timeService = co.getService(TimeService.SERVICE_NAME,
				TimeService.class);

		// invoke service
		// getLogger().info(
		// "Current Time from TimeService("
		// + location
		// + "): "
		// + DateFormatter.getLongDate(timeService
		// .getCurrentTime()));

		// catch YuepException
		try {
			timeService.getYuepException();
			throw new RuntimeException("Cannot catch YupeExcpetion.");
		} catch (Exception e) {
			if (e instanceof YuepException) {
				YuepException exception = (YuepException) e;
				getLogger().info(
						"Success to catch YuepException from " + location
								+ ": errorCode=" + exception.getErrorCode());
			} else {
				getLogger().error("Cannot catch YuepExcpetion.", e);
			}
		}
		// // catch UserDefineExcpetion
		// try {
		// timeService.getUserDefineException();
		// throw new RuntimeException("Cannot catch UserDefineException.");
		// } catch (UserDefineException e) {
		// getLogger().info(
		// "Success to catch UserDefineException from " + location
		// + ", root cause: " + e.getCause().getMessage());
		// } catch (Throwable e) {
		// throw new RuntimeException("Cannot catch UserDefineException.", e);
		// }

		// receive message
		// co.subscribe(TimeService.MSG_TIMENOTIFY, receiver);
		// co.subscribe(SessionMessage.NAME, sessionMsgReceiver);
	}

	@Override
	public void stop() {
		if (null != receiver) {
			cleanup(co);
			cleanup(rco);
			receiver = null;
		}
		getLogger().info("Module B stopped.");
	}

	private final void cleanup(CommunicateObject co) {
		if (co != null) {
			co.unsubscribe(TimeService.MSG_TIMENOTIFY, receiver);
			if (co instanceof RemoteCommunicateObject) {
				((RemoteCommunicateObject) co).cleanup();
			}
			co = null;
		}
	}

	/**
	 * Message Receiver for local or remote
	 */
	private final class TimeNotifyMessageReceiver implements MessageReceiver {

		@Override
		public void receive(CommunicateObject co, String name, Serializable msg) {
			StringBuilder location = new StringBuilder();
			if (co instanceof RemoteCommunicateObject) {
				// remote message
				RemoteCommunicateObject rco = (RemoteCommunicateObject) co;
				String ip = rco.getRemoteIp();
				location.append("Remote[").append(ip).append(":")
						.append(rco.getRemoteServerPort()).append("]");
			} else {
				// local message
				location.append("Local");
			}
			getLogger()
					.info("Received Message from " + location.toString() + ": "
							+ msg);
			// invoke service of message source communication object
			TimeService timeService = co.getService(TimeService.SERVICE_NAME,
					TimeService.class);
			getLogger().info(
					"Current Time from "
							+ location.toString()
							+ ": "
							+ DateFormatter.getLongDate(timeService
									.getCurrentTime()));
		}
	}
}
