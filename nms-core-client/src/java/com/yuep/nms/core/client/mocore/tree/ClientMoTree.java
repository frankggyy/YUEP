/*
 * $Id: ClientMoTree.java, 2011-4-15 ����09:48:33 aaron Exp $
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
 * MO Tree�ṩ�ķ������(�̳пͻ��� Tree) <br>
 * 1) ���ֹ��췽ʽ��֧��Ĭ�ϡ�MO Type���Զ��幹��������� <br>
 * 2) ͳһ��ͼƬչʾ������MO �ľ�̬��Ϣ��ȡͳһͼƬ�� <br>
 * 3) ����MoCore��Ϣ��̬���£���ӡ��޸ġ�ɾ���ڵ㣩 <br>
 * 4) ��Ϣ��������
 * </p>
 * 
 * @author aaron
 * created 2011-4-15 ����09:48:33
 * modified [who date description]
 * check [who date description]
 */
public class ClientMoTree extends XTree implements MessageReceiver {

    private static final long serialVersionUID = 6418927575921007546L;

    /**
     * ���ڵ����ͣ��ο�Mo������
     */
    private String rootMoType;

    /**
     * Ҷ�ӽڵ�����ͣ��ο�Mo������
     */
    private String leafMoType;

    private MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);

    public ClientMoTree() {
        this(null, null);
    }

    /**
     * ���췽�����趨Ҷ�ӽڵ㵽��һ��
     * 
     * @param leafMoType
     *            Ҷ�ӽڵ������
     */
    public ClientMoTree(String leafMoType) {
        this(null, leafMoType);
    }

    /**
     * ���췽�����趨���ڵ��Ҷ�ӽڵ㵽��һ��
     * 
     * @param rootMoType
     *            ���ڵ�����
     * @param leafMoType
     *            Ҷ�ӽڵ�����
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
     * ����Mo��
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
     * ���������ӽڵ�
     * 
     * @param parentNaming
     *            ���ڵ��Mo Naming
     * @param parentNode
     *            ���ڵ�
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
     * ���ý���MoCore��Ϣ�Ŀ���
     * 
     * @param enable
     *            ���Ϊtrue��ע��MoCore��Ϣ�����ߣ�����ȡ��ע��
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
     * ˢ��Mo��
     */
    public void refresh() {
        constructTree();
    }

    /**
     * ���ý���MoCore��mo״̬�仯��Ϣ�Ŀ���
     * 
     * @param enable
     *            ���Ϊtree��ע��Ϊ״̬�仯��Ϣ�ļ����ߣ�����ȡ��ע��
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
     * ����XNode
     * 
     * @param moMessage
     *            Mo��Ϣ
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
