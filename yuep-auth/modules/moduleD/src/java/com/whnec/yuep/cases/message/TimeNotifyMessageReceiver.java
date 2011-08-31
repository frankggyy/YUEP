package com.whnec.yuep.cases.message;

import java.io.Serializable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;

public class TimeNotifyMessageReceiver implements MessageReceiver {
	@Override
	public void receive(CommunicateObject co, String name, Serializable msg) {
		StringBuilder location = new StringBuilder();
		if (co instanceof RemoteCommunicateObject) {
			// remote message
			RemoteCommunicateObject rco = (RemoteCommunicateObject) co;
			location.append("Remote[").append(rco.getRemoteIp()).append(':')
					.append(rco.getRemoteServerPort()).append("]");
		} else {
			// local message
			location.append("Local");
		}
		ClientCoreContext.getOutputManager().addSystemMessage(
				"Received Message(" + name + ") from " + location.toString()
						+ ": " + msg);
		// invoke service of message source communication object
		// TimeService timeService = co.getService(TimeService.SERVICE_NAME,
		// TimeService.class);
		// ClientCoreContext.getOutputManager().addOperationMessage(
		// "Current Time from "
		// + location.toString()
		// + ": "
		// + DateFormatter.getLongDate(timeService
		// .getCurrentTime()));
	}
}
