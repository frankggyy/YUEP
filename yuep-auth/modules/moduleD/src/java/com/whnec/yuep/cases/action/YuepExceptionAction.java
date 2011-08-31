package com.whnec.yuep.cases.action;

import java.awt.event.ActionEvent;

import com.whnec.yuep.cases.service.TimeService;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.DialogUtils;

public class YuepExceptionAction extends AbstractXAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5544025164155037675L;

	public YuepExceptionAction(String actionId) {
		super(actionId);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TimeService timeService = ClientCoreContext.getRemoteService(
				TimeService.SERVICE_NAME, TimeService.class);
		try {
			timeService.getYuepException();
			throw new RuntimeException("Cannot catch YuepException.");
		} catch (Exception ex) {
			DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(),
					ex);
		}
	}
}
