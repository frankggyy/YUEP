package com.whnec.yuep.cases.message;

import java.io.Serializable;

import com.yuep.core.bootstrap.def.logger.Logger;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.logger.def.YuepLoggerFactory;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionMessage;

public class SessionMessageReceiver implements MessageReceiver {
	private Logger logger;

	private String moduleName;
	
	public SessionMessageReceiver(){
	    ClassLoader cls = Thread.currentThread().getContextClassLoader();
	    moduleName=cls.toString();
	}
	
	@Override
	public void receive(CommunicateObject co, String name, Serializable msg) {
		StringBuilder sb = new StringBuilder("Message Received from [");
		sb.append(moduleName).append("]");
		if (co instanceof RemoteCommunicateObject) {
			RemoteCommunicateObject rco = (RemoteCommunicateObject) co;
			sb.append(rco.getRemoteIp()).append(':')
					.append(rco.getRemoteServerPort());
		} else {
			sb.append("localhost");
		}
		sb.append('/').append(name).append(" - ");
		if (msg instanceof SessionMessage) {
			SessionMessage sessionMsg = (SessionMessage) msg;
			Session session = sessionMsg.getSession();
			sb.append("Session[").append(session.getIp()).append('/')
					.append(session.getSessionId()).append("] ")
					.append(session.getSessionState().name());
		} else {
			sb.append(msg);
		}

		getLogger().info(sb.toString());
	}

	private final Logger getLogger() {
		if (logger == null) {
			YuepLoggerFactory factory = CoreContext.getInstance().local()
					.getService("yuepLoggerFactory", YuepLoggerFactory.class);
			logger = factory.getLogger("SessionMessageReceiver");
		}
		return logger;
	}
}
