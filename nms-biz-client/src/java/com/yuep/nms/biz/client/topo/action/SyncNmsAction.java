/**
 * 
 */
package com.yuep.nms.biz.client.topo.action;

import java.awt.event.ActionEvent;

import twaver.Element;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.DefaultPopupMenuAction;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.syncmanager.module.constants.SyncManagerDefModuleConstants;
import com.yuep.nms.core.common.syncmanager.service.SyncManager;

/**
 * @author yangtao
 *
 */
public class SyncNmsAction extends DefaultPopupMenuAction {

	private static final long serialVersionUID = -2396671502719621912L;
	
	public SyncNmsAction(Boolean isMultiple, String actionId,
			Object[] selectedObjects, Object paramObj, String paramClazz) {
		super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	        Element selectedElement=(Element)selectedObjects[0];
	        MoNaming nms = (MoNaming)selectedElement.getID();
	        SyncManager syncManager=ClientCoreContext.getRemoteService(SyncManagerDefModuleConstants.SYNCMANAGER_REMOTE_SERVICE, SyncManager.class);
	        try{
	        	syncManager.sync(nms);
	        }catch(Exception ex){
	        	DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), ex);
	        }
	}

}
