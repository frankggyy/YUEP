package com.whnec.yuep.cases.action;

import java.awt.event.ActionEvent;

import com.whnec.yuep.cases.service.TimeService;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.DialogUtils;

public class UserDefineExceptionAction extends AbstractXAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6898699033573298247L;

	public UserDefineExceptionAction(String actionId) {
		super(actionId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TimeService timeService = ClientCoreContext.getRemoteService(
				TimeService.SERVICE_NAME, TimeService.class);
		try {
			timeService.getUserDefineException();
		} catch (Throwable th) {
			DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(),
					th);
		}
	}
}
