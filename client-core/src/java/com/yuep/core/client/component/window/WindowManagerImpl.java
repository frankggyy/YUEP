/*
 * $Id: WindowManagerImpl.java, 2009-3-2 上午10:47:12 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;

import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.bootstrap.def.module.Module;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.navigator.AbstractNavigationView;
import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.core.client.module.ClientModule;
import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.ClientView;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: WindowManagerImpl
 * </p>
 * <p>
 * Description: 窗口管理器实现
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-2 上午10:47:12
 * modified [who date description]
 * check [who date description]
 */
public class WindowManagerImpl implements WindowManager {

    private Map<AbstractClientView, Window> currentOpenCache = new ConcurrentHashMap<AbstractClientView, Window>();

    private Image windowImage = ClientCoreContext.getResourceFactory().getImage("logo.gif");

    private Map<ClientView<? extends Object>, ClientController<? extends Object, ? extends AbstractClientView<?>, ? extends AbstractClientModel<?>>> controllerMap = new ConcurrentHashMap<ClientView<? extends Object>, ClientController<? extends Object, ? extends AbstractClientView<?>, ? extends AbstractClientModel<?>>>();

    private ClientController<? extends Object, ? extends AbstractClientView<?>, ? extends AbstractClientModel<?>> controller;

    private class YuepWindowLinstener extends WindowAdapter {

        private ClientView<? extends Object> clientView;

        public YuepWindowLinstener(ClientView<? extends Object> clientView) {
            this.clientView = clientView;
        }

        /**
         * (non-Javadoc)
         * 
         * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
         */
        @Override
        public void windowClosed(WindowEvent e) {
            if (controller != null) {
                controller.clearData();
                List<AbstractTabController<?, ?, ?>> tabControllerList = getTabControllerList(controller);
                if (!CollectionUtils.isEmpty(tabControllerList)) {
                    for (AbstractTabController<?, ?, ?> abstractTabController : tabControllerList) {
                        abstractTabController.clearData();
                    }
                }
            }
            currentOpenCache.remove(clientView);
            controller = null;
            ClientController<? extends Object, ? extends AbstractClientView<?>, ? extends AbstractClientModel<?>> clientController = controllerMap
                    .get(clientView);
            if (clientController != null) {
                if (!ClientCoreContext.isMvcCache()) {
                    Map<Class<? extends Module>, Module> modules = ModuleContext.getInstance().getModules();
                    Set<Entry<Class<? extends Module>, Module>> entrySet = modules.entrySet();
                    for (Entry<Class<? extends Module>, Module> entry : entrySet) {
                        Module module = entry.getValue();
                        if(module instanceof ClientModule){
                            ClientModule clientModule = (ClientModule) module;
                            clientModule.removeCache(clientController);
                            List<AbstractTabController<?, ?, ?>> tabControllerList = getTabControllerList(clientController);
                            if (!CollectionUtils.isEmpty(tabControllerList)) {
                                for (AbstractTabController<?, ?, ?> abstractTabController : tabControllerList) {
                                    clientModule.removeCache(abstractTabController);
                                }
                            }
                        }
                    }
                }
                controllerMap.remove(clientView);
            }
            super.windowClosed(e);
        }

        /**
         * 
         */
        private List<AbstractTabController<?, ?, ?>> getTabControllerList(
                ClientController<? extends Object, ? extends AbstractClientView<?>, ? extends AbstractClientModel<?>> clientController) {
            List<AbstractTabController<?, ?, ?>> tabControllerList = new ArrayList<AbstractTabController<?, ?, ?>>();
            AbstractClientView<?> clientView2 = clientController.getClientView();
            if (clientView2 instanceof AbstractNavigationView) {
                AbstractNavigationView view = (AbstractNavigationView) clientView2;
                Map<String, List<AbstractTabController<?, ?, ?>>> tabMap = view.getTabMap();
                Set<Entry<String, List<AbstractTabController<?, ?, ?>>>> entrySet = tabMap.entrySet();
                for (Entry<String, List<AbstractTabController<?, ?, ?>>> entry : entrySet) {
                    List<AbstractTabController<?, ?, ?>> list = entry.getValue();
                    tabControllerList.addAll(list);
                }
            }
            return tabControllerList;
        }
    }

    private void setWindowVisible(final Window window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setVisible(true);
            }
        });
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.window.WindowManager#getCurrentOpenWindow(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Window getCurrentOpenWindow(String id) {
        for(Map.Entry<AbstractClientView, Window> entry: currentOpenCache.entrySet()){
            AbstractClientView view = entry.getKey();
            if(id.equals(view.getTitle())){
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.window.WindowManager#openAsDialog(com.ycignp.veapo.client.framework.module.mvc.ClientController,
     *      java.lang.String)
     */
    @Override
    public <T> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,
            Object... titles) {
        openAsDialog(controller, true, titles);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.window.WindowManager#openAsDialog(com.ycignp.veapo.client.framework.module.mvc.ClientController,
     *      java.lang.String)
     */
    @Override
    public <T> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,
            boolean resizable, Object... titles) {
        if (controller == null) {
            DialogUtils.showWarnDialog(ClientCoreContext.getMainFrame(), "Controller is not null", null);
            return;
        }
        this.controller = controller;
        controllerMap.put(controller.getClientView(), controller);
        final AbstractClientView<T> clientView = controller.getClientView();
        if (clientView == null) {
            DialogUtils.showWarnDialog(ClientCoreContext.getMainFrame(), "HelpId is not null!", null);
            return;
        }
        Window window = currentOpenCache.get(clientView);
        if (window != null) {
            setWindowVisible(window);
        } else {
            JDialog dialog = new JDialog();
            dialog.setResizable(resizable);
            dialog.setModal(true);
            XGuiUtils.addKeyStrokeAction_Esc(dialog);
            dialog.getContentPane().add(clientView);
            dialog.addWindowListener(new YuepWindowLinstener(clientView));
            if (titles == null)
                dialog.setTitle(ClientCoreContext.getString(clientView.getTitle()));
            else {
                String string = ClientCoreContext.getString(clientView.getTitle(), titles);
                dialog.setTitle(string);
            }
            dialog.setIconImage(windowImage);
            dialog.pack();
            JComponent defaultFoucs = clientView.getDefaultFocus();
            if (defaultFoucs != null)
                XGuiUtils.addDefaultFocus(dialog, defaultFoucs);
            XGuiUtils.centerWindow(dialog);
            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// (JFrame.DISPOSE_ON_CLOSE);
            JButton defaultButton = clientView.getDefaultButton();
            if (defaultButton != null)
                XGuiUtils.setDefaultButton(defaultButton);
            setWindowVisible(dialog);
            currentOpenCache.put(clientView, dialog);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.window.WindowManager#openAsFrame(com.ycignp.veapo.client.framework.module.mvc.ClientController,
     *      java.lang.String)
     */
    @Override
    public <T> void openAsFrame(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,
            Object... titles) {
        if (controller == null) {
            DialogUtils.showWarnDialog(ClientCoreContext.getMainFrame(), "Controller is not null", null);
            return;
        }
        this.controller = controller;
        controllerMap.put(controller.getClientView(), controller);
        AbstractClientView<T> clientView = controller.getClientView();
        if (clientView == null) {
            DialogUtils.showWarnDialog(ClientCoreContext.getMainFrame(), "HelpId is not null!", null);
            return;
        }
        
        Window window = currentOpenCache.get(clientView);
        if (window != null) {
            setWindowVisible(window);
        } else {
            JFrame frame = new JFrame();
            XGuiUtils.addKeyStrokeAction_Esc(frame);
            frame.getContentPane().add(clientView);
            frame.addWindowListener(new YuepWindowLinstener(clientView));
            if (titles != null)
                frame.setTitle(ClientCoreContext.getString(clientView.getTitle()));
            else {
                String string = ClientCoreContext.getString(clientView.getTitle());
                frame.setTitle(string);
            }
            frame.setIconImage(windowImage);
            frame.pack();
            JComponent defaultFoucs = clientView.getDefaultFocus();
            if (defaultFoucs != null)
                XGuiUtils.addDefaultFocus(frame, defaultFoucs);
            XGuiUtils.centerWindow(frame);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// (JFrame.DISPOSE_ON_CLOSE);
            JButton defaultButton = clientView.getDefaultButton();
            if (defaultButton != null)
                XGuiUtils.setDefaultButton(defaultButton);
            setWindowVisible(frame);
            currentOpenCache.put(clientView, frame);
        }
    }
}
