/*
 * $Id: DomainTopoPopupMenuFactory.java, 2011-4-20 上午09:27:49 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo.menu;

import java.awt.Point;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.apache.commons.collections.CollectionUtils;

import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.PopupMenuFactory;
import twaver.TSubNetwork;
import twaver.network.TNetwork;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.interpreter.MenuInterpreter;
import com.yuep.nms.biz.client.topo.TopoClientModule;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: DomainTopoPopupMenuFactory
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-4-20 上午09:27:49
 * modified [who date description]
 * check [who date description]
 */
public class DomainTopoPopupMenuFactory implements PopupMenuFactory {

    private JComponent container;

    private static final String MENU_TOPO_BLANK="topo.blank";
    private static final String MENU_TOPO_NMS="topo.nms";
    private static final String MENU_TOPO_DOMAIN="topo.domain";
    private static final String MENU_TOPO_NE="topo.ne";
    
    public DomainTopoPopupMenuFactory(JComponent container) {
        this.container = container;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public JPopupMenu getPopupMenu(DataBoxSelectionModel databoxselectionmodel, Point point) {
        TopoClientModule module = (TopoClientModule) ClientCoreContext.getModule(TopoClientModule.class);
        MenuInterpreter menuInterpreter = module.getMenuInterpreter();
        List selElements = databoxselectionmodel.getAllSelectedElement();

        // 1,空白处操作
        if (CollectionUtils.isEmpty(selElements)) {
            if (container instanceof TNetwork) {
                TNetwork network = (TNetwork) container;
                TSubNetwork subNetwork = network.getCurrentSubNetwork();
                if(subNetwork != null)
                    return menuInterpreter.getPopupMenu(MENU_TOPO_BLANK, null, new Object[] { subNetwork },null);
            }
        }else{
        	Element selectedElement=(Element)selElements.get(0);
        	MoNaming mo=(MoNaming)selectedElement.getID();
        	String moType=mo.getMoType();
        	if(moType.equals(MoTypeConstants.NE)){
        		return menuInterpreter.getPopupMenu(MENU_TOPO_NE, null, new Object[] { selectedElement },null);
        	}else if(moType.equals(MoTypeConstants.NM)){
        		return menuInterpreter.getPopupMenu(MENU_TOPO_NMS, null, new Object[] { selectedElement },null);
        	}else if(moType.equals(MoTypeConstants.DOMAIN)){
        		return menuInterpreter.getPopupMenu(MENU_TOPO_DOMAIN, null, new Object[] { selectedElement },null);
        	}
        }
        return null;
    }

}
