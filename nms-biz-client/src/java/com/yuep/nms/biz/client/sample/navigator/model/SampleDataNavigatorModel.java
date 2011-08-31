/*
 * $Id: SampleDataNavigatorModel.java, 2010-3-30 ����11:03:16 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.navigator.model;

import java.util.ArrayList;
import java.util.List;

import twaver.Element;
import twaver.Node;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.core.client.component.navigator.DataNavigationModel;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.navigator.controller.SampleMultipleTabNavigatorController1;
import com.yuep.nms.biz.client.sample.navigator.controller.SampleMultipleTabNavigatorController2;
import com.yuep.nms.biz.client.sample.navigator.controller.SampleSingleTabNavigatorController;
import com.yuep.nms.biz.client.sample.navigator.view.SampleMultipleTabNavigatorView1;
import com.yuep.nms.biz.client.sample.navigator.view.SampleMultipleTabNavigatorView2;
import com.yuep.nms.biz.client.sample.navigator.view.SampleSingleTabNavigatorView;

/**
 * <p>
 * Title: SampleDataNavigatorModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-30 ����11:03:16
 * modified [who date description]
 * check [who date description]
 */
public class SampleDataNavigatorModel extends DataNavigationModel<Object> {

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.navigator.AbstractNavigationModel#getNavigationNodes()
     */
    @Override
    protected List<Element> getNavigationNodes() {
        // ���쵼������������Ϣ
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        Node parentNode = new Node("��������");
        parentNode.setName("��������");
        parentNode.setBusinessObject("��������");
        Node node = new Node("��Tabҳ");
        node.setParent(parentNode);
        node.setName("��Tabҳ");
        node.setUserObject("��Tabҳ");
        node.setBusinessObject("��Tabҳ");
        SampleSingleTabNavigatorController sampleSingleTabNavigatorController = module.getController(
                SampleSingleTabNavigatorModel.class, SampleSingleTabNavigatorView.class,
                SampleSingleTabNavigatorController.class);
        List<AbstractTabController<?, ?, ?>> list = new ArrayList<AbstractTabController<?, ?, ?>>();
        list.add(sampleSingleTabNavigatorController);
        getTabMap().put("��Tabҳ", list);
        Node node1 = new Node("��Tabҳ");
        node1.setParent(parentNode);
        node1.setName("��Tabҳ");
        node1.setUserObject("��Tabҳ");
        node1.setBusinessObject("��Tabҳ");
        SampleMultipleTabNavigatorController1 exampleMultipleTabNavigatorController1 = module.getController(
                SampleMultipleTabNavigatorModel1.class, SampleMultipleTabNavigatorView1.class,
                SampleMultipleTabNavigatorController1.class);
        SampleMultipleTabNavigatorController2 exampleMultipleTabNavigatorController2 = module.getController(
                SampleMultipleTabNavigatorModel2.class, SampleMultipleTabNavigatorView2.class,
                SampleMultipleTabNavigatorController2.class);
        List<AbstractTabController<?, ?, ?>> multipleList = new ArrayList<AbstractTabController<?, ?, ?>>();
        multipleList.add(exampleMultipleTabNavigatorController1);
        multipleList.add(exampleMultipleTabNavigatorController2);
        getTabMap().put("��Tabҳ", multipleList);
        navigationNodes.add(parentNode);
        navigationNodes.add(node);
        navigationNodes.add(node1);
        return navigationNodes;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        // TODO ���ݴ���Ĳ�����ʼ������
    }

}
