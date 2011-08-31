/*
 * $Id: UserInfoView.java, 2011-4-25 上午11:39:06 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.calendar.JCalendar;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.TextAreaEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.table.TableActionHelper;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.action.DeleteIpRangeAction;
import com.yuep.nms.core.client.smmanager.action.DeleteSelectRoleAction;
import com.yuep.nms.core.client.smmanager.action.OpenAddIpRangeAction;
import com.yuep.nms.core.client.smmanager.action.OpenSelectRoleAction;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;
/**
 * <p>
 * Title: UserInfoView
 * </p>
 * <p>
 * Description:展示用户基本信息的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 上午11:39:06
 * modified [who date description]
 * check [who date description]
 */
public class UserInfoView extends AbstractTabView<Object> {

    private static final long serialVersionUID = 1L;

    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();

    private JTextField nameField;
    private JTextField fullNameField;
    private JPasswordField pwdField;
    private JPasswordField pwdConfirmField;

    private JCalendar userExpireTime;
    private JCalendar pwdExpireTime;

    private XTable<Role> userRoleTable;
    private JButton addRoleBtn;
    private JButton delRoleBtn;

    private XTable<IpRange> ipRangeTable;
    private JButton addIpRangeBtn;
    private JButton delIpRangeBtn;

    private JTextField deparmentField;
    private JTextField emailField;
    private JTextField cellPhoneField;
    private JTextField telephoneField;
    private JTextArea descriptionField;

    protected TableActionHelper roleTableActionHelper;
    protected TableActionHelper ipRangeTableActionHelper;

    private Boolean isView;
    private Boolean isModify;

    private JComponent getUserBasicInfoPanel() {
        JPanel userBasicInfoSep = (JPanel) swingFactory.getXTitleSeparator("UserInfo.userBasicInfo");
        JLabel nameLabel = swingFactory.getLabel(new LabelDecorator("User.userName"));
        nameField = swingFactory.getXEditor(new StringEditorDecorator("User.userName", true, 0, 255));
        JLabel fullNameLabel = swingFactory.getLabel(new LabelDecorator("User.fullName"));
        fullNameField = swingFactory.getXEditor(new StringEditorDecorator("User.fullName"));
        JLabel pwdLabel = swingFactory.getLabel(new LabelDecorator("User.password"));
        pwdField = swingFactory.getXEditor(new PasswordEditorDecorator("User.password"));
        JLabel pwdConfirmLabel = swingFactory.getLabel(new LabelDecorator("User.pwdConfirm"));
        pwdConfirmField = swingFactory.getXEditor(new PasswordEditorDecorator("User.pwdConfirm"));
        JLabel userExpireLabel = swingFactory.getLabel(new LabelDecorator("User.expiredTime"));
        userExpireTime = new JCalendar();
        userExpireTime.setFormatterData(true);
        JLabel pwdExpireLabel = swingFactory.getLabel(new LabelDecorator("User.passwordExpiredTime"));
        pwdExpireTime = new JCalendar();
        pwdExpireTime.setFormatterData(true);

        double[][] ds = new double[][] { { 10, 100, TableLayout.FILL, 10, 100, TableLayout.FILL },
                swingFactory.getTableLayoutRowParam(4, 0, 0) };
        XTableLayout tableLayout = swingFactory.getTableLayout(ds);
        JPanel panel = swingFactory.getPanel(tableLayout);
        panel.add(userBasicInfoSep, "0,1,5,c");
        panel.add(nameLabel, "1,3,f,c");
        panel.add(nameField, "2,3,f,c");
        panel.add(fullNameLabel, "4,3,f,c");
        panel.add(fullNameField, "5,3,f,c");
        panel.add(pwdLabel, "1,5,f,c");
        panel.add(pwdField, "2,5,f,c");
        panel.add(pwdConfirmLabel, "4,5,f,c");
        panel.add(pwdConfirmField, "5,5,f,c");
        panel.add(userExpireLabel, "1,7,f,c");
        panel.add(userExpireTime, "2,7,f,c");
        panel.add(pwdExpireLabel, "4,7,f,c");
        panel.add(pwdExpireTime, "5,7,f,c");

        return panel;

    }

    private JComponent getUserRolePanel() {
        JPanel panel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] { { 10, TableLayout.FILL },
                { 0, 18, TableLayout.FILL, 10 } }));
        JPanel roleSeperator = (JPanel) swingFactory.getXTitleSeparator("UserInfo.userRole");
        JPanel borderPanel = swingFactory.getPanel(new BorderLayout());
        userRoleTable = swingFactory.getXTableEditor("", Role.class, Role.class, "roleName", new String[] { "roleName",
                "description" });
        userRoleTable.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        roleTableActionHelper = new TableActionHelper(userRoleTable);
        userRoleTable.addTableListener(roleTableActionHelper);
        JPanel btnPanel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] {
                { 5, TableLayout.FILL, 0 }, swingFactory.getTableLayoutRowParam(3, 0, 0) }));
        addRoleBtn = swingFactory.getButton(new ButtonDecorator("Button.add", 'A'));
        delRoleBtn = swingFactory.getButton(new ButtonDecorator("Button.delete", 'D'));
        btnPanel.add(addRoleBtn, "1,1,f,c");
        btnPanel.add(delRoleBtn, "1,3,f,c");
        borderPanel.add(swingFactory.getScrollPane(userRoleTable), BorderLayout.CENTER);
        borderPanel.add(btnPanel, BorderLayout.EAST);
        panel.add(roleSeperator, "0,1,1,c");
        panel.add(borderPanel, "1,2,f,c");
        return panel;
    }

    private JComponent getIpRangePanel() {
        JPanel panel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] { { 10, TableLayout.FILL },
                { 0, 18, TableLayout.FILL } }));
        JPanel borderPanel = swingFactory.getPanel(new BorderLayout());
        JPanel ipSeperator = (JPanel) swingFactory.getXTitleSeparator("User.ipRanges");
        ipRangeTable = new XTable<IpRange>(IpRange.class, "startIpAddress", new String[] { "startIpAddress",
                "endIpAddress" });
        ipRangeTable.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        ipRangeTableActionHelper = new TableActionHelper(ipRangeTable);
        userRoleTable.addTableListener(ipRangeTableActionHelper);
        addIpRangeBtn = swingFactory.getButton(new ButtonDecorator("Button.add", 'A'));
        delIpRangeBtn = swingFactory.getButton(new ButtonDecorator("Button.delete", 'D'));
        JPanel btnPanel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] {
                { 5, TableLayout.FILL, 0 }, swingFactory.getTableLayoutRowParam(3, 0, 0) }));
        btnPanel.add(addIpRangeBtn, "1,1,f,c");
        btnPanel.add(delIpRangeBtn, "1,3,f,c");
        borderPanel.add(swingFactory.getScrollPane(ipRangeTable), BorderLayout.CENTER);
        borderPanel.add(btnPanel, BorderLayout.EAST);
        panel.add(ipSeperator, "0,1,1,c");
        panel.add(borderPanel, "1,2,f,c");
        return panel;

    }

    private JComponent getAddtionInfoPanel() {
        JPanel panel = swingFactory.getPanel();
        JPanel addtionSeperator = (JPanel) swingFactory.getXTitleSeparator("UserInfo.addtionInfo");
        JLabel departmentLabel = swingFactory.getLabel(new LabelDecorator("User.department"));
        deparmentField = swingFactory.getXEditor(new StringEditorDecorator("User.department", 0, 255));
        JLabel cellPhoneLabel = swingFactory.getLabel(new LabelDecorator("User.mobile"));
        cellPhoneField = swingFactory.getXEditor(new StringEditorDecorator("User.mobile", 0, 255));
        JLabel telephoneLabel = swingFactory.getLabel(new LabelDecorator("User.workPhone"));
        telephoneField = swingFactory.getXEditor(new StringEditorDecorator("User.workPhone", 0, 255));
        JLabel emailLabel = swingFactory.getLabel(new LabelDecorator("User.email"));
        emailField = swingFactory.getXEditor(new StringEditorDecorator("User.email", 0, 255));
        JLabel descriptionLabel = swingFactory.getLabel(new LabelDecorator("User.description"));
        descriptionField = swingFactory.getXEditor(new TextAreaEditorDecorator("User.description", false, 0, 255));
        panel.setLayout(swingFactory
                .getTableLayout(new double[][] { { 10, 100, TableLayout.FILL, 10, 100, TableLayout.FILL },
                        swingFactory.getTableLayoutRowParam(4, 5, 5) }));
        panel.add(addtionSeperator, "0,1,5,c");
        panel.add(departmentLabel, "1,3,f,c");
        panel.add(deparmentField, "2,3,f,c");
        panel.add(cellPhoneLabel, "4,3,f,c");
        panel.add(cellPhoneField, "5,3,f,c");
        panel.add(telephoneLabel, "1,5,f,c");
        panel.add(telephoneField, "2,5,f,c");
        panel.add(emailLabel, "4,5,f,c");
        panel.add(emailField, "5,5,f,c");
        panel.add(descriptionLabel, "1,7,f,c");
        panel.add(descriptionField, "2,7,5,c");
        return panel;
    }

    @Override
    protected String getDescription() {
        return "UserInfo.des";
    }

    @Override
    public List<Object> collectData() {
        User user = new User();
        user.setUserName(nameField.getText());
        user.setFullName(fullNameField.getText());
        String clientEncryptPwd = EncryptUtils.setEncrypt(new String(pwdField.getPassword()), EncryptUtils.MAGIC_KEY_CLIENT);
        user.setPassword(clientEncryptPwd );
        user.setExpiredTime(userExpireTime.getTime());
        user.setPasswordExpiredTime(pwdExpireTime.getTime());
        user.setMobile(cellPhoneField.getText());
        user.setWorkPhone(telephoneField.getText());
        user.setDepartment(deparmentField.getText());
        user.setEmail(emailField.getText());
        user.setDescription(descriptionField.getText());
        List<IpRange> ipList = new ArrayList<IpRange>();
        ipList.addAll(ipRangeTable.getAllDatas());
        user.setIpRanges(ipList);
        List<Role> roleList = new ArrayList<Role>();
        roleList.addAll(userRoleTable.getAllDatas());
        List<String> roleNames = new ArrayList<String>();
        for (Role role : roleList) {
            roleNames.add(role.getRoleName().toString());
        }
        user.setRoles(roleNames);
        List<Object> objects = new ArrayList<Object>();
        objects.add(user);
        objects.add(isView);
        objects.add(isModify);
        return objects;
    }

    public boolean checkValid() {
        // 此处为控件无法校验判断，主要是密码比较，时间值的设置等
        String strPwd = new String(pwdField.getPassword());
        String strConfigPwd = new String(pwdConfirmField.getPassword());
        if (strPwd == null || "".equals(strPwd)) {
            DialogUtils.showInfoDialog(this, "UserInfo.pwdempty");
            pwdField.requestFocus();
            return false;
        }
        if (strConfigPwd == null || "".equals(strConfigPwd)) {
            DialogUtils.showInfoDialog(this, "UserInfo.pwdconfigempty");
            pwdConfirmField.requestFocus();
            return false;
        }
        if (!strPwd.equals(strConfigPwd)) {
            DialogUtils.showInfoDialog(this, "UserInfo.pwdnotsame");
            pwdConfirmField.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "UserInfo.title";
    }

    public List<IpRange> getAllIpRange() {
        return ipRangeTable.getAllDatas();
    }

    public List<Role> getAllRoles() {
        return userRoleTable.getAllDatas();
    }

    public void updateIpRangeTable(List<IpRange> list) {
        ipRangeTable.getTableModel().clearRawData();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ipRangeTable.addRowDatas(list);

    }

    public void updateUserRoleTable(List<Role> roles) {
        userRoleTable.getTableModel().clearRawData();
        if (CollectionUtils.isEmpty(roles)) {
            return;
        }
        userRoleTable.addRowDatas(roles);

    }

    public List<Role> getSelectedRoles() {
        return userRoleTable.getSelectionDatas();
    }

    public List<IpRange> getSelectedIpRange() {
        return ipRangeTable.getSelectionDatas();
    }

    public void setData(User user) {
        SmManagerService smManagerService = ClientCoreContext.getRemoteService("smManagerService",
                SmManagerService.class);
        nameField.setText(user.getUserName());

        fullNameField.setText(user.getFullName());
        pwdField.setText(user.getPassword());
        pwdConfirmField.setText(user.getPassword());

        userExpireTime.setTime(user.getExpiredTime());

        pwdExpireTime.setTime(user.getPasswordExpiredTime());

        List<String> roleNames = user.getRoles();
        List<Role> roles = new ArrayList<Role>();
        if (roleNames != null) {
            for (String roleName : roleNames) {
                roles.add(smManagerService.getRoleByName(roleName));
            }
        }
        updateUserRoleTable(roles);
        updateIpRangeTable(user.getIpRanges());

        deparmentField.setText(user.getDepartment());
        emailField.setText(user.getEmail());
        cellPhoneField.setText(user.getMobile());
        telephoneField.setText(user.getWorkPhone());
        descriptionField.setText(user.getDescription());

    }

    protected void initializeData(List<Object> data) {
        if (data == null || data.size() < 1) {
            setData(new User());
            setEditabled(this, true, null);
            return;
        }
        User user = (User) data.get(0);
        isView = (Boolean) data.get(1);
        isModify = (Boolean) data.get(2);
        if (isView != null && isView == true) {
            setData(user);
            setEditabled(this, false, null);
            return;
        }
        if (isModify != null && isModify == true) {
            setData(user);
            setEditabled(this, true, null);
            return;

        }

    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        OpenAddIpRangeAction addIpRangeAction = new OpenAddIpRangeAction("");
        addIpRangeBtn.setAction(addIpRangeAction);
        OpenSelectRoleAction selectRoleAction = new OpenSelectRoleAction("");
        addRoleBtn.setAction(selectRoleAction);
        DeleteSelectRoleAction deleteRoleAction = new DeleteSelectRoleAction(true, "", controller);
        delRoleBtn.setAction(deleteRoleAction);
        roleTableActionHelper.addSensitiveAction(deleteRoleAction);
        DeleteIpRangeAction deleteIpAction = new DeleteIpRangeAction(true, "", controller);
        delIpRangeBtn.setAction(deleteIpAction);
        ipRangeTableActionHelper.addSensitiveAction(deleteIpAction);

    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        JPanel basicPanel = (JPanel) getUserBasicInfoPanel();
        JPanel rolePanel = (JPanel) getUserRolePanel();
        JPanel ipPanel = (JPanel) getIpRangePanel();
        JPanel addtionPanel = (JPanel) getAddtionInfoPanel();
        setLayout(swingFactory.getTableLayout(new double[][] { { 5, TableLayout.FILL, 5 },
                { 0, 120, 0, 120, 0, 120, 0, 120, 5 } }));
        add(basicPanel, "1,1,f,c");
        add(rolePanel, "1,3,f,c");
        add(ipPanel, "1,5,f,c");
        add(addtionPanel, "1,7,f,c");

    }

    /**
     * @see com.yuep.core.client.component.navigator.AbstractTabView#getSensitiveButtons()
     */
    @Override
    protected JButton[] getSensitiveButtons() {
        // TODO Auto-generated method stub
        return new JButton[]{};
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        // TODO Auto-generated method stub
        return null;
    }
}
