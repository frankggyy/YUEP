/*
 * $Id: LoginView.java, 2010-4-27 下午02:28:15 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import twaver.TWaverConst;
import twaver.TWaverUtil;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.main.login.action.LoginAction;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.login.model.StartObj;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: LoginView
 * </p>
 * <p>
 * Description:登录界面的View
 * </p>
 * 
 * @author aaron lee created 2010-4-27 下午02:28:15 modified [who date
 *         description] check [who date description]
 */
public class LoginView extends AbstractClientView<StartObj> {
    private static final long serialVersionUID = -8358828157872369403L;

    private static String localeItemValue_zh = ClientCoreContext.getString("common.loginframe.chinese");

    private static String localeItemValue_en = ClientCoreContext.getString("common.loginframe.english");

    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField serverField;
    private XNumberEditor<NumberValidator> portField;

    private JButton loginButton;
    private JButton quitButton;

    private JLabel logoLabel;

    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel serverLabel;
    private JLabel localeLabel;
    private JLabel portLabel;
    private String title = "common.loginframe.title";

    private JComboBox localeComboBox;

    private StartObj startObj;

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        setLayout(ClientCoreContext.getSwingFactory().getBorderLayout());
        add(createMessagePane(), BorderLayout.NORTH);
        add(createExpandPane(), BorderLayout.CENTER);
        add(createActionPane(), BorderLayout.SOUTH);
    }

    /**
     * 创建登录界面的action panel
     * 
     * @return action panel
     */
    private JComponent createActionPane() {
        FlowLayout flowLayout = ClientCoreContext.getSwingFactory().getFlowLayout(FlowLayout.RIGHT);
        JPanel pane = ClientCoreContext.getSwingFactory().getPanel(flowLayout);
        pane.setBorder(ClientCoreContext.getSwingFactory().getEmptyBorder(3, 0, 6, 13));

        loginButton = ClientCoreContext.getSwingFactory().getButton(
                new ButtonDecorator("common.loginframe.login.button", 'l'));
        quitButton = ClientCoreContext.getSwingFactory().getButton(
                new ButtonDecorator("common.loginframe.quit.button", 'q'));

        pane.add(loginButton);
        pane.add(quitButton);
        return pane;
    }

    private JLabel getLabel(String text) {
        JLabel label = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator(text));
        label.setPreferredSize(new Dimension(50, 20));
        return label;
    }

    /**
     * 创建登录界面的中央区域的panel，用来输入登录信息的区域，包括用户名、密码和高级信息服务器、语言信息
     * 
     * @return 包括高级信息的中央区域panel
     */
    private JComponent createExpandPane() {
        LayoutManager tableLayout = ClientCoreContext.getSwingFactory().getTableLayout(
                new double[][] { { 3, 15, .2, 5, .8, 18 }, { 10, 20, 3, 20, 3, 20, 3, 20, 3 } });
        JPanel pane = ClientCoreContext.getSwingFactory().getPanel(tableLayout);
        pane.setBorder(ClientCoreContext.getSwingFactory().getEmptyBorder(0, 0, 3, 0));
        userLabel = getLabel("common.loginframe.user");
        userField = ClientCoreContext.getSwingFactory().getXEditor(
                new StringEditorDecorator("common.loginframe.user", true, 4, 64));
        passwordLabel = getLabel("common.loginframe.password");
        passwordField = ClientCoreContext.getSwingFactory().getXEditor(
                new PasswordEditorDecorator("common.loginframe.password"));
        serverLabel = getLabel("common.loginframe.server");
        serverField = ClientCoreContext.getSwingFactory().getXEditor(
                new StringEditorDecorator("common.loginframe.server", true, 4, 64));
        localeLabel = getLabel("common.loginframe.language");
        portField = ClientCoreContext.getSwingFactory().getXEditor(
                new NumberEditorDecorator("common.loginframe.port", 4, 4));
        localeComboBox = getLocaleComboBox();
        JPanel serverPane = ClientCoreContext.getSwingFactory().getPanel(
                ClientCoreContext.getSwingFactory().getBorderLayout());
        serverPane.add(serverField, BorderLayout.CENTER);
        portLabel = getLabel("common.loginframe.port");
        LayoutManager layout = ClientCoreContext.getSwingFactory().getTableLayout(
                new double[][] { { 15, .5, .5 }, { 0, 20, 0 } });
        JPanel portPane = ClientCoreContext.getSwingFactory().getPanel(layout);
        portLabel.setPreferredSize(new Dimension(40, 20));
        portField.setPreferredSize(new Dimension(40, 20));
        portPane.add(portLabel, "1,1,f,c");
        portPane.add(portField, "2,1,f,c");
        serverPane.add(portPane, BorderLayout.EAST);
        pane.add(userLabel, "2,1,f,c");
        pane.add(userField, "4,1,f,c");
        pane.add(passwordLabel, "2,3,f,c");
        pane.add(passwordField, "4,3,f,c");
        pane.add(serverLabel, "2,5,f,c");
        pane.add(serverPane, "4,5,f,c");
        pane.add(localeLabel, "2,7,f,c");
        pane.add(localeComboBox, "4,7,f,c");

        return pane;
    }

    /**
     * 切换语言时更新界面显示
     */
    private void switchLocale() {
        ClientCoreContext.getResourceFactory().clearResourceCache();
        userLabel.setText(ClientCoreContext.getString("common.loginframe.user"));
        passwordLabel.setText(ClientCoreContext.getString("common.loginframe.password"));
        serverLabel.setText(ClientCoreContext.getString("common.loginframe.server"));
        localeLabel.setText(ClientCoreContext.getString("common.loginframe.language"));
        loginButton.setText(ClientCoreContext.getString("common.loginframe.login.button"));
        quitButton.setText(ClientCoreContext.getString("common.loginframe.quit.button"));
        try {
            JFrame frame = (JFrame) getWindow();
            frame.setTitle(ClientCoreContext.getString(title));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        Icon icon = ClientCoreContext.getResourceFactory().getIcon("loginlogo.gif");
        logoLabel.setIcon(icon);
        portLabel.setText(ClientCoreContext.getString("common.loginframe.port"));
    }

    /**
     * 创建登录界面上部的panel，显示公司logo的图片
     * 
     * @return 上部界面的panel
     */
    private JComponent createMessagePane() {
        Icon icon = ClientCoreContext.getResourceFactory().getIcon("loginlogo.gif");
        BorderLayout borderLayout = ClientCoreContext.getSwingFactory().getBorderLayout();
        JPanel pane = ClientCoreContext.getSwingFactory().getPanel(borderLayout);
        logoLabel = getLabel("");
        int iconHeight = icon.getIconHeight();
        int iconWidth = icon.getIconWidth();
        logoLabel.setPreferredSize(new Dimension(iconWidth - 2, iconHeight));
        logoLabel.setIcon(icon);
        pane.add(logoLabel);
        return pane;
    }

    /**
     * 获取语言选择控件
     * 
     * @return 语言选择控件
     */
    private JComboBox getLocaleComboBox() {
        JComboBox localeComboBox = ClientCoreContext.getSwingFactory().getXEditor(
                new ComboBoxEditorDecorator("Language"));
        localeComboBox.addItem(localeItemValue_zh);
        localeComboBox.addItem(localeItemValue_en);
        localeComboBox.setSelectedItem(localeItemValue_zh);
        return localeComboBox;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.AbstractClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Button ActionEvent Handling
     */
    public static class ButtonHandle implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("quit")) {
                ContainerConst.exitSystem(0);
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return loginButton;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return "login frame";
    }

    public String getUserName() {
        return userField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());

    }

    public String getServer() {

        return serverField.getText().trim();
    }

    public boolean validateUser() {
        String userName = getUserName();
        String message = "common.loginframe.check.user";
        if (StringUtils.isEmpty(userName)) {
            DialogUtils.showWarnDialog(this, message);
            return false;
        } else {
            return true;
        }
    }

    public String getServerPort() {
        return portField.getText();
    }

    /**
     * @return
     */
    public boolean validateServerPort() {

        String port = portField.getText();
        if (StringUtils.isEmpty(port)) {
            String message = "common.loginframe.check.port";
            DialogUtils.showWarnDialog(this, message);
            return false;
        } else {
            return true;
        }

    }

    /**
     * @return
     */
    public boolean validatePassword() {

        String password = getPassword();
        if (StringUtils.isEmpty(password)) {
            String message = "common.loginframe.check.password";
            DialogUtils.showWarnDialog(this, message);
            return false;
        } else {
            return true;
        }

    }

    /**
     * @return
     */
    public boolean validateServerIp() {
        String server = getServer();
        String message = "common.loginframe.check.server";
        if (StringUtils.isEmpty(server)) {
            DialogUtils.showWarnDialog(this, message);
            return false;
        } else {
            return true;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.component.module.ClientView#collectData()
     */
    @Override
    public List<StartObj> collectData() {
        LoginInfo loginInfo = new LoginInfo();
        if (!validateUser())
            return null;
        loginInfo.setUser(getUserName());
        if (!validatePassword())
            return null;
        String clientEncryptPwd = EncryptUtils.setEncrypt(getPassword(), EncryptUtils.MAGIC_KEY_CLIENT);
        loginInfo.setPassword(clientEncryptPwd);
        if (!validateServerIp())
            return null;
        loginInfo.setServer(getServer());
        if (!validateServerPort())
            return null;
        loginInfo.setPort(getServerPort());
        List<StartObj> list = new ArrayList<StartObj>();
        startObj.setLoginInfo(loginInfo);
        list.add(startObj);
        return list;
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
     * @see com.yotc.opview.framework.client.component.module.ClientView#initData(java.util.List)
     */
    @Override
    public void initData(List<StartObj> boList) {
        if (CollectionUtils.isEmpty(boList))
            return;
        startObj = boList.get(0);
        LoginInfo loginInfo = startObj.getLoginInfo();
        userField.setText(loginInfo.getUser());
        serverField.setText(loginInfo.getServer());
        portField.setText(loginInfo.getPort());
        if (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
            passwordField.setText("admin");
        }
        loginButton.addActionListener(new LoginAction(startObj));
    }

    @Override
    public JComponent getDefaultFocus() {
        return passwordField;
    }

    @Override
    public <V, M> void addListener(ClientController<StartObj, V, M> controller) {
        localeComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent itemevent) {
                String selectedItem = (String) localeComboBox.getSelectedItem();
                if (selectedItem.equals(localeItemValue_zh)) {
                    ClientCoreContext.setLocale(Locale.SIMPLIFIED_CHINESE);
                    switchLocale();
                    TWaverUtil.init(TWaverConst.ZH_CN, null);
                } else if (selectedItem.equals(localeItemValue_en)) {
                    ClientCoreContext.setLocale(Locale.ENGLISH);
                    switchLocale();
                    TWaverUtil.init(TWaverConst.EN_US, null);
                }
            }

        });

        ActionListener buttonHandle = new ButtonHandle();
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(buttonHandle);
    }

    /**
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientView#dispose()
     */
    @Override
    public void dispose() {
        getWindow().setVisible(false);
    }
}
