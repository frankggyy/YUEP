/*
 * $Id: TopoMainFrame.java, 2011-3-14 ÏÂÎç03:47:54 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twaver.Element;
import twaver.Group;
import twaver.Node;
import twaver.SubNetwork;
import twaver.TDataBox;
import twaver.network.TNetwork;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.main.mainframe.MainFrame;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.biz.client.topo.menu.DomainTopoPopupMenuFactory;
import com.yuep.nms.core.client.mocore.tree.ClientMoTree;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoTypeConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;

/**
 * <p>
 * Title: TopoMainFrame
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2011-3-14 ÏÂÎç03:47:54
 * modified [who date description]
 * check [who date description]
 */
public class TopoMainFrame extends MainFrame implements MessageReceiver {

    private static final long serialVersionUID = 1158669104736610455L;

    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
    private TDataBox dataBox;

    private JSplitPane mainJSplitPane;

    private ClientMoTree moTree;

    public void initListener() {
        ClientCoreContext.subscribeLocal(MoMessage.MO_CREATE, this);
        ClientCoreContext.subscribeLocal(MoMessage.MO_DELETE, this);
        ClientCoreContext.subscribeLocal(MoMessage.MO_STATE_CHANGED, this);
    }

    private MoCore moCore = ClientCoreContext.getLocalService("moCore", MoCore.class);

//    private TSubNetwork rootNetwork;

    public void initData() {
        List<Mo> allMos = moCore.getAllMos();
        for (Mo mo : allMos) {
            addMo2DataBox(mo);
        }
    }

    /**
     * @param mo
     */
    private void addMo2DataBox(Mo mo) {
        MoNaming parent = mo.getParent();
        String type = mo.getType();
        Element element = null;
        if(MoTypeConstants.DOMAIN.equals(type)){
            element = new SubNetwork(mo.getMoNaming());
        }else if(MoTypeConstants.GROUP.equals(type)){
            element = new Group(mo.getMoNaming());
        }else if(MoTypeConstants.NE.equals(type)){
            element = new Node(mo.getMoNaming());
        }else if(MoTypeConstants.NM.equals(type)){
            element = new SubNetwork(mo.getMoNaming());
        }
        if(element == null)
            return;
        element.setName(mo.getDisplayName());
        element.setUserObject(mo);
        if (parent == null) {
            element.setParent(null);
        } else {
            Element parentElement = dataBox.getElementByID(parent);
            element.setParent(parentElement);
        }
        dataBox.addElement(element);
    }

    @Override
    protected JComponent getTopoView() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainJSplitPane = swingFactory.getSplitPane();
        mainJSplitPane.setBorder(swingFactory.getEmptyBorder(0, 0, 0, 0));
        mainJSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        mainJSplitPane.setDividerSize(3);
        mainJSplitPane.setDividerLocation(screenSize.width * 1 / 5);
        mainJSplitPane.setLeftComponent(getTreeView());
        mainJSplitPane.setRightComponent(getMapView());
        return mainJSplitPane;
    }

    private JComponent getTreeView() {
        JPanel treeView = swingFactory.getPanel(new BorderLayout());
        moTree = new ClientMoTree();
        treeView.add(swingFactory.getScrollPane(moTree), BorderLayout.CENTER);
        return treeView;
    }

    private JComponent getMapView() {
        JPanel mapView = swingFactory.getPanel(new BorderLayout());
        TNetwork network = new TNetwork();
        mapView.add(network, BorderLayout.CENTER);
        network.setPopupMenuFactory(new DomainTopoPopupMenuFactory(network));
        dataBox = new TDataBox();
        network.setDataBox(dataBox);
//        rootNetwork = new SubNetwork("root");
//        dataBox.addElement(rootNetwork);
//        network.setCurrentSubNetwork(rootNetwork);
        return mapView;
    }

    /**
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject,
     *      java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, final Serializable msg) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                MoMessage moMessage = (MoMessage) msg;
                String messageType = moMessage.getMessageType();
                Mo mo = (Mo) moMessage.getMessageBody();
                if (MoMessage.MO_CREATE.equals(messageType)) {
                    addMo2DataBox(mo);
                } else if (MoMessage.MO_DELETE.equals(messageType)) {
                    dataBox.removeElementByID(mo.getMoNaming());
                } else if (MoMessage.MO_STATE_CHANGED.equals(messageType)) {
                    dataBox.removeElementByID(mo.getMoNaming());
                    addMo2DataBox(mo);
                }
            }
            
        });
    }
}
