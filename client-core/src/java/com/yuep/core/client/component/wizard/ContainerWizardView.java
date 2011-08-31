/*
 * $Id: ContainerWizardView.java, 2009-6-4 上午10:52:57 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingWorker;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: ContainerWizardView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 上午10:52:57
 * modified [who date description]
 * check [who date description]
 */
public abstract class ContainerWizardView<T extends Object> extends AbstractClientView<T> implements
        PropertyChangeListener {
    private static final long serialVersionUID = -916424050336219453L;

    /**
     * 当前操作步骤界面的Controller
     */
    private BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> currentController;

    /*
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#addListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    public <V,M> void addListener(ClientController<T,V,M> controller) {
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        if (currentController != null) {
            this.removeAll();
            setLayout(ClientCoreContext.getSwingFactory().getBorderLayout());
            BasicWizardView<?> clientView = currentController.getClientView();
            clientView.removeAll();
            clientView.constructUi();
            add(clientView, BorderLayout.CENTER);
            updateUI();
            clientView.initMessagePaneListener();
        } else {
            setPreferredSize(getWizardPreferredSize());
        }
    }

    /**
     * 返回向导界面的大小
     * @return Dimension 界面大小
     */
    protected Dimension getWizardPreferredSize() {
        return new Dimension(800, 600);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();
        BasicWizardController wizardController = null;
        if (propertyName.equals(BasicWizardView.PREVIOUS)) {
            wizardController = currentController.getPreviousBasicWizardController();
        } else if (propertyName.equals(BasicWizardView.NEXT)) {
            wizardController = currentController.getNextBasicWizardController();
        }
        if (wizardController != null) {
            setCurrentController(wizardController, newValue);
        }
    }

    /**
     * 设置当前操作步骤界面的Controller
     * @param controller
     *            the currentController to set
     */
    public void setCurrentController(BasicWizardController controller, final Object newValue) {
        BasicWizardController previousBasicWizardController = controller.getPreviousBasicWizardController();
        if (previousBasicWizardController != null) {
            previousBasicWizardController.getClientView().removePropertyChangeListener(
                    BasicWizardView.PREVIOUS, this);
            previousBasicWizardController.getClientView().removePropertyChangeListener(BasicWizardView.NEXT,
                    this);
        }
        BasicWizardController nextBasicWizardController = controller.getNextBasicWizardController();
        if (nextBasicWizardController != null) {
            nextBasicWizardController.getClientView().removePropertyChangeListener(BasicWizardView.PREVIOUS,
                    this);
            nextBasicWizardController.getClientView()
                    .removePropertyChangeListener(BasicWizardView.NEXT, this);
        }
        controller.getClientView().removePropertyChangeListener(BasicWizardView.PREVIOUS, this);
        controller.getClientView().removePropertyChangeListener(BasicWizardView.NEXT, this);
        controller.getClientView().addPropertyChangeListener(BasicWizardView.PREVIOUS, this);
        controller.getClientView().addPropertyChangeListener(BasicWizardView.NEXT, this);

        this.currentController = controller;
        constructUi();
        if (newValue != null) {
            final WaitingDialog waitingDialog = DialogUtils
                    .showWaitingDialog(false, false, null, getWindow());

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        currentController.getClientModel().init(newValue);
                    } catch (Exception e) {
                        DialogUtils.showErrorExpandDialog(getWindow(), e);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                    try {
                        List<T> model = currentController.getClientModel().getModelDatas();
                        currentController.getClientView().initData(model);
                    } catch (Exception e) {
                        DialogUtils.showErrorExpandDialog(getWindow(), e);
                    } finally {
                        waitingDialog.closeWaitingDialog();
                    }
                }
            }.execute();
        }
    }

    /**
     * 删除前一步和下一步的Listener
     */
    public void removeListener() {
        currentController.getClientView().removePropertyChangeListener(BasicWizardView.PREVIOUS, this);
        currentController.getClientView().removePropertyChangeListener(BasicWizardView.NEXT, this);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<T> collectData() {
        return null;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public abstract String getTitle();

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#initData(java.util.List)
     */
    @Override
    public void initData(List<T> boList) {
    }

    /**
     * 返回返回当前正在操作步骤界面的Controller
     * @return BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> 当前操作步骤界面的Controller
     */
    public BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> getCurrentController() {
        return currentController;
    }

}
