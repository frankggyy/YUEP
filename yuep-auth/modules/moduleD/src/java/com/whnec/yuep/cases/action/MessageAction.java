package com.whnec.yuep.cases.action;

import com.whnec.yuep.cases.message.TimeNotifyMessageReceiver;
import com.whnec.yuep.cases.service.TimeService;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MessageAction extends AbstractXAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5349204407036152894L;

	public MessageAction(String actionId) {
		super(actionId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem menuItem = (JMenuItem) e.getSource();
			menuItem.setEnabled(false);
		}
		ClientCoreContext.getOutputManager().setAutoScroll(true);
		ClientCoreContext.subscribeRemote(TimeService.MSG_TIMENOTIFY,
				new TimeNotifyMessageReceiver());
	}
}
