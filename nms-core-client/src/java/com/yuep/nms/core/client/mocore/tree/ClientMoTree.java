/*
 * $Id: ClientMoTree.java, 2011-4-15 上午09:48:33 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

import twaver.Element;
import twaver.Group;
import twaver.Node;
import twaver.SubNetwork;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;

/**
 * <p>
 * Title: ClientMoTree
 * </p>
 * <p>
 * Description: <br>
 * MO Tree提供的服务或功能(继承客户端 Tree) <br>
 * 1) 多种构造方式（支持默认、MO Type、自定义构造过滤器） <br>
 * 2) 统一的图片展示（根据MO 的静态信息获取统一图片） <br>
 * 3) 根据MoCore消息动态更新（添加、修改、删除节点） <br>
 * 4) 消息监听开关
 * </p>
 * 
 * @author aaron
 * created 2011-4-15 上午09:48:33
 * modified [who date description]
 * check [who date description]
 */
public class ClientMoTree extends XTree implements MessageReceiver {

    private static final long serialVersionUID = 6418927575921007546L;

    /**
     * 根节点类型，参考Mo的类型
     */
    private String rootMoType;

    /**
     * 叶子节点的类型，参考Mo的类型
     */
    private String leafMoType;

    private MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);

    public ClientMoTree() {
        this(null, null);
    }

    /**
     * 构造方法，设定叶子节点到哪一级
     * 
     * @param leafMoType
     *            叶子节点的类型
     */
    public ClientMoTree(String leafMoType) {
        this(null, leafMoType);
    }

    /**
     * 构造方法，设定根节点和叶子节点到哪一级
     * 
     * @param rootMoType
     *            根节点类型
     * @param leafMoType
     *            叶子节点类型
     */
    public ClientMoTree(String rootMoType, String leafMoType) {
        super();
        this.rootMoType = rootMoType;
        this.leafMoType = leafMoType;
        constructTree();
        setMoMsgEnabled(true);
        setMoStateMsgEnabled(true);
        setRootVisible(false);
    }

    /**
     * 构造Mo树
     */
    private void constructTree() {
        List<Mo> rootMos = new ArrayList<Mo>();
        if (StringUtils.isEmpty(rootMoType)) {
            List<Mo> allMos = moCore.getAllMos();
            for (Mo mo : allMos) {
                MoNaming parent = mo.getParent();
                if (parent == null) {
                    rootMos.add(mo);
                }
            }
        } else {
            rootMos = moCore.getMos(new MoFilter() {
                @Override
                public boolean accept(Mo mo) {
                    if (mo.getType().equals(rootMoType)) {
                        return true;
                    }
                    return false;
                }
            });
        }
        for (Mo mo : rootMos) {
            MoNaming moNaming = mo.getMoNaming();
            Element node = constructXNode(mo);
            addNode(node);
            constructChildren(moNaming);
        }
    }

    /**
     * 构造树的子节点
     * 
     * @param parentNaming
     *            父节点的Mo Naming
     * @param parentNode
     *            父节点
     */
    private void constructChildren(MoNaming parentNaming) {
        List<Mo> childrenMos = moCore.getChildrenMos(null, parentNaming);
        for (Mo mo : childrenMos) {
            if(this.getDataBox().containsByID(mo.getMoNaming()))
                continue;
            String type = mo.getType();
            if (!StringUtils.isEmpty(leafMoType) && type.equals(leafMoType)) {
                Element node = constructXNode(mo);
                addNode(node);
            } else {
                MoNaming moNaming = mo.getMoNaming();
                Element node = constructXNode(mo);
                addNode(node);
                constructChildren(moNaming);
            }
        }
    }

    /**
     * 设置接收MoCore消息的开关
     * 
     * @param enable
     *            如果为true，注册MoCore消息监听者，否则取消注册
     */
    public void setMoMsgEnabled(boolean enable) {
        if (!enable) {
            ClientCoreContext.unsubscribeLocal(MoMessage.MO_CREATE, this);
            ClientCoreContext.unsubscribeLocal(MoMessage.MO_DELETE, this);
        } else {
            ClientCoreContext.subscribeLocal(MoMessage.MO_CREATE, this);
            ClientCoreContext.subscribeLocal(MoMessage.MO_DELETE, this);
        }
    }

    /**
     * 刷新Mo树
     */
    public void refresh() {
        constructTree();
    }

    /**
     * 设置接收MoCore的mo状态变化消息的开关
     * 
     * @param enable
     *            如果为tree，注册为状态变化消息的监听者，否则取消注册
     */
    public void setMoStateMsgEnabled(boolean enable) {
        if (!enable) {
            ClientCoreContext.unsubscribeLocal(MoMessage.MO_STATE_CHANGED, this);
        } else {
            ClientCoreContext.subscribeLocal(MoMessage.MO_STATE_CHANGED, this);
        }
    }

    /**
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject,
     *      java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, final Serializable msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MoMessage moMessage = (MoMessage) msg;
                String messageType = moMessage.getMessageType();
                Mo mo = (Mo) moMessage.getMessageBody();
                if (MoMessage.MO_CREATE.equals(messageType)) {
                    Element node = constructXNode(mo);
                    addNode(node);
                } else if (MoMessage.MO_DELETE.equals(messageType)) {
                    Element node = constructXNode(mo);
                    deleteNode(node);
                } else if (MoMessage.MO_STATE_CHANGED.equals(messageType)) {
                    Element node = constructXNode(mo);
                    updateNode(node);
                }
            }

        });

    }

    /**
     * 创建XNode
     * 
     * @param moMessage
     *            Mo消息
     * @return XNode
     */
    private Element constructXNode(Mo mo) {
        String type = mo.getType();
        Element node = null;
        if ("domain".equals(type)) {
            node = new SubNetwork(mo.getMoNaming());
        } else if ("group".equals(type)) {
            node = new Group(mo.getMoNaming());
        } else if ("ne".equals(type)) {
            node = new Node(mo.getMoNaming());
        } else if ("nm".equals(type)) {
            node = new SubNetwork(mo.getMoNaming());
        }
        if (node == null)
            return null;
        node.setName(mo.getDisplayName());
        node.setUserObject(mo);
        Element parentNode = getXNode(mo.getParent());
        node.setParent(parentNode);
        return node;
    }
}
