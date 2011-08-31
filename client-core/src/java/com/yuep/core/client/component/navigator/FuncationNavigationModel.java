/*
 * $Id: FuncationNavigationModel.java, 2009-3-16 上午10:50:38 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import twaver.Element;
import twaver.Node;

import com.yuep.base.xml.XmlParseUtil;

/**
 * <p>
 * Title: FuncationNavigationModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author Victor
 * created 2009-3-16 上午10:50:38
 * modified [who date description]
 * check [who date description]
 */
public abstract class FuncationNavigationModel<T extends Object> extends AbstractNavigationModel<T> {
    /**
     * 从配置文件中获取导航树节点
     * 
     * <code> 
     *  <element name="test1" action="com.yotc.test1" visible="true">
     *      <element name="test2" action="com.yotc.test2" visible="false"/>
     *      <element name="test3" action="com.yotc.test3" visible="true"/>
     *  </element>
     * </code>
     * 
     * @param filename
     *            配置文件名
     * @return 树节点集合
     */
    protected List<Element> getNodesFromXml(URL filename) {
        List<Element> datas = new ArrayList<Element>();
        XmlParseUtil xml = new XmlParseUtil(filename);
        parseElement(datas, null, xml.getDocumentElement());
        return datas;
    }

    @SuppressWarnings("unchecked")
    private void parseElement(List<Element> datas, Node parent, org.jdom.Element element) {
        String visible = element.getAttributeValue("visible", "true");
        if (visible.equals("true") && element.getAttribute("action") != null) {
            parent = createNode(datas, parent, element);
        }
        List<org.jdom.Element> children = element.getChildren();
        for (org.jdom.Element child : children) {
            parseElement(datas, parent, child);
        }
    }

    private Node createNode(List<Element> datas, Node parentNode, org.jdom.Element element) {
        Node node = new Node(element.getAttributeValue("name"));
        node.setName(element.getAttributeValue("name"));
        node.setParent(parentNode);
        String action = element.getAttributeValue("action");
        if (action != null) {
            try {
                node.setUserObject(Class.forName(action));
            } catch (ClassNotFoundException e) {
                System.err.println("Class " + action + " not found!");
            }
        }
        datas.add(node);
        return node;
    }
}
