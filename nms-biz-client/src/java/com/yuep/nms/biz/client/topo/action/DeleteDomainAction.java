
package com.yuep.nms.biz.client.topo.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import twaver.Element;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.momanager.module.constants.MoManagerModuleConstants;
import com.yuep.nms.core.common.momanager.service.MoManager;

public class DeleteDomainAction extends DefaultPopupMenuAction {

	private static final long serialVersionUID = -8016540015371211357L;

	public DeleteDomainAction(Boolean isMultiple, String actionId,
			Object[] selectedObjects, Object paramObj, String paramClazz) {
		super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (selectedObjects == null)
            return;
		int result=DialogUtils.showDeleteConfirmDialog(ClientCoreContext.getMainFrame());
		if(result!=JOptionPane.OK_OPTION)
			return;
        Object object = selectedObjects[0];
        Element element=(Element)object;
        MoNaming domain=(MoNaming)element.getID();
		MoManager moManager=ClientCoreContext.getRemoteService(MoManagerModuleConstants.MOMANAGER_REMOTE_SERVICE, MoManager.class);
		moManager.deleteManagedDomain(domain);
	}

}
