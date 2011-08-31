/*
 * $Id: AbstractXAction.java, 2009-2-9 ����03:51:53 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

import javax.swing.JMenuItem;

import org.apache.commons.lang.StringUtils;

import twaver.Node;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: AbstractXAction
 * </p>
 * <p>
 * Description:action�������
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:51:53
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractXAction extends XActionBase implements SensitiveAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7409903220213826647L;

    /**
     * ѡ������ʹ��״̬ for <code>selectedEnabled</code>
     */
    private boolean selectedEnabled;

    /**
     * ��־���Action�Ƿ�֧�ֶ�ѡ���� for <code>isMultiple</code>
     */
    private boolean isMultiple;

    /**
     * ����ʵ�����ṩ�� for <code>instanceProvider</code>
     */
    protected InstanceProvider instanceProvider;

    /**
     * ����actionʱ��ѡ�еĶ��� for <code>objects</code>
     */
    protected Object[] selectedObjects;

    protected JMenuItem menuItem;

    public AbstractXAction(boolean isMultiple) {
        this(isMultiple, "");
    }

    /**
     * constructor.
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ����
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public AbstractXAction(boolean isMultiple, String actionId) {
        this(actionId);
        this.isMultiple = isMultiple;
        setSelectedEnabled(false);
    }

    /**
     * Constructor.����action���ṩ��ѡ���������Ϣ��ʹ�ø���Ϣ�ж�action������״̬��
     * 
     * @param isMultiple
     *            �Ƿ�֧�ֶ�ѡ����
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ���������Ϣ
     */
    public AbstractXAction(Boolean isMultiple, String actionId, Object[] selectedObjects) {
        this(actionId);
        this.isMultiple = isMultiple;
        this.selectedObjects = selectedObjects;
        initActionEnabled();
    }

    /**
     * Constructor.
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     */
    public AbstractXAction(String actionId) {
        super(actionId);
    }
    
    public AbstractXAction(String actionId,String textKey,char mnemonic) {
        super(actionId);
        if(StringUtils.isNotEmpty(textKey)){
            String text = ClientCoreContext.getString(textKey);
            int vk = XGuiUtils.getMnemonicKey(mnemonic);
            if (text.toLowerCase().indexOf(String.valueOf(mnemonic).toLowerCase()) == -1) {
                text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
            }
            putValue(NAME, text);
            putValue(MNEMONIC_KEY, vk);
        }
    }

    /**
     * Constructor.����action���ṩ��ѡ���������Ϣ��ʹ�ø���Ϣ�ж�action������״̬��
     * 
     * @param actionId
     *            У��Ȩ�޵�actionId
     * @param selectedObjects
     *            ��ѡ���������Ϣ
     */
    public AbstractXAction(String actionId, Object[] selectedObjects) {
        this(false, actionId, selectedObjects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tekview.ocean.client.common.action.BussinessAllowable#bussinessAllowabled
     * (com.tekview.ocean.client.common.action.InstanceProvider)
     */
    @Override
    public boolean bussinessAllowabled(InstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tekview.ocean.client.common.action.BussinessAllowable#bussinessAllowabled (java.lang.Object[])
     */
    @Override
    public boolean bussinessAllowabled(Object[] selectedObjects) {
        return true;
    }

    /**
     * ��ʼ��action��Ȩ��ʹ��״̬
     * 
     * @param actionId
     */
    protected void initPermission(String actionId) {
        boolean accessable = true; // �ж��Ƿ���Ȩ�޷���
        if (StringUtils.isEmpty(actionId)) {
            accessable = true;
        } else {
            if (selectedObjects != null) {
                // ����ͼ���Ҽ��˵�
                for (Object obj : selectedObjects) {
                    if (obj instanceof Node) {
                        Object userObject = ((Node) obj).getUserObject();
                        String methodName = "getMoNaming";

                        Object id;
                        try {
                            Method method = userObject.getClass().getMethod(methodName);
                            id = method.invoke(userObject);
                            accessable = ClientCoreContext.getAccessCheck().check(actionId,id);
                            if(accessable)
                                continue;
                            else
                                break;
                        } catch (Exception e) {
                            ClientCoreContext.getLogger().error("", e);
                        }
                    }
                }
            } else {
                // û��ѡ���κ�element��Ӧ�������˵�
                accessable = ClientCoreContext.getAccessCheck().check(actionId);
            }
        }

        // ����û��Ȩ�޵Ĳ˵�disable��
        setPermissionEnabled(accessable);
    }

    /**
     * �������ݵ������ֱ�ӳ�ʼ��action��ʹ��״̬��
     */
    private void initActionEnabled() {
        int selectedCount = 0;
        if (selectedObjects == null) {
            selectedCount = 1;
        } else {
            selectedCount = selectedObjects.length;
        }
        boolean bussinessAllowabled = bussinessAllowabled(selectedObjects);
        setActionEnabaled(bussinessAllowabled, selectedCount);
    }

    /**
     * ����ѡ��״̬�ж�action��ʹ��״̬
     * 
     * @return boolean
     */
    protected boolean isSelectedEnabled() {
        return selectedEnabled;
    }

    /**
     * ����ѡ�����ı仯������action��ʹ��״̬��
     * 
     * @see com.tekview.ocean.client.common.action.SensitiveAction#selectionChanged(com.tekview.ocean.client.common.action.InstanceProvider)
     */
    public void selectionChanged(InstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
        int selectedCount = instanceProvider.getSelectedCount();
        boolean bussinessAllowabled = bussinessAllowabled(instanceProvider);
        setActionEnabaled(bussinessAllowabled, selectedCount);
    }

    /**
     * ����action��ʹ��״̬
     * 
     * @param bussinessAllowabled
     *            ҵ�����Ƿ�����actionʹ��
     * @param selectedCount
     *            ѡ�����ݵ�����
     */
    private void setActionEnabaled(boolean bussinessAllowabled, int selectedCount) {
        if (selectedCount == 0) {
            setSelectedEnabled(false);
        } else if (isMultiple) {
            setSelectedEnabled(true);
            setEnabled(bussinessAllowabled);
        } else {
            if (selectedCount > 1) {
                setSelectedEnabled(false);
            } else {
                setSelectedEnabled(true);
                setEnabled(bussinessAllowabled);
            }
        }
    }

    public void setMenuItem(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    protected void setMenuItemSelected() {
        menuItem.setSelected(true);
    }

    /**
     * ���ݶԶ����ѡ������action��ʹ��״̬����������Ҫ���жϸ�actionȨ�޵�ʹ��״̬
     * 
     * @param selectedEnabled
     */
    protected void setSelectedEnabled(boolean selectedEnabled) {
        if (isPermissionEnabled()) {
            this.selectedEnabled = selectedEnabled;
            super.setEnabled(selectedEnabled);
        } else {
            this.selectedEnabled = false;
        }
    }
}
