package com.whnec.yuep.cases.action;

import com.whnec.yuep.cases.service.TimeService;
import com.yuep.base.util.format.DateFormatter;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.DialogUtils;

import java.awt.event.ActionEvent;

public class TimeAction extends AbstractXAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4910180647662973528L;

	public TimeAction(String actionId) {
		super(actionId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TimeService timeService = ClientCoreContext.getRemoteService(
				TimeService.SERVICE_NAME, TimeService.class);
		DialogUtils.showInfoDialog(ClientCoreContext.getMainFrame(),
				ClientCoreContext.getString("a.time",
						new Object[] { DateFormatter.getLongDate(timeService
								.getCurrentTime()) }));
	}
}
