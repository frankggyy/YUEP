/*
 * $Id: UnlockClientView.java, 2011-4-13 下午05:59:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;

import twaver.swing.TableLayout;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XPasswordFieldEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.client.smmanager.action.UnlockClientApplyAction;

/**
 * <p>
 * Title: UnlockClientView
 * </p>
 * <p>
 * Description: 锁定客户端，弹出user,password解锁界面
 * </p>
 * 
 * @author sufeng created 2011-4-27 下午05:59:55 modified [who date description]
 *         check [who date description]
 */
public class UnlockClientView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = 2791625154285806466L;
    private DefaultXEditor<StringValidator> userField;
    private XPasswordFieldEditor pwdTextField;

    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        okButton.setEnabled(true);
        okButton.setText(ClientCoreContext.getString("smmanager.unlock.button"));

        UnlockClientApplyAction applyAction = new UnlockClientApplyAction(UnlockClientApplyAction.class.getSimpleName());
        okButton.addActionListener(applyAction);
    }

    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();

        JLabel userLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.unlock.user")));
        JLabel pwdLabel = swingFactory.getLabel(new LabelDecorator(ClientCoreContext
                .getString("smmanager.unlock.password")));

        userField = swingFactory.getXEditor(new StringEditorDecorator("user"));
        userField.setEditable(false);
        pwdTextField = swingFactory.getXEditor(new PasswordEditorDecorator("password"));

        double[][] ds = new double[][] { { 5, 100, 5, TableLayout.FILL, 5 },
                swingFactory.getTableLayoutRowParam(2, 1, 1) };
        JPanel pane = swingFactory.getPanel(new TableLayout(ds));
        pane.add(userLabel, "1,1,f,c");
        pane.add(userField, "3,1,f,c");
        pane.add(pwdLabel, "1,3,f,c");
        pane.add(pwdTextField, "3,3,f,c");

        // init data : current user
        SmCoreClientService smCoreClientService = ClientCoreContext.getLocalService("smCoreClientService",
                SmCoreClientService.class);
        userField.setText(smCoreClientService.getCurrentUser());
        return pane;
    }

    @Override
    protected String getDescription() {
        return "smmanager.unlock.description";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> collectData() {
        // 读取用户名，密码
        String pwd = new String(pwdTextField.getPassword());
        String user = userField.getText();
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(user)) {
            DialogUtils.showErrorDialog(this.getWindow(), "smmanager.modifyselfpassword.input is null");
            return null;
        }

        List datas = new ArrayList();
        datas.add(user);
        // 加密传输
        String encryptPwd = EncryptUtils.setEncrypt(pwd, EncryptUtils.MAGIC_KEY_CLIENT);
        datas.add(encryptPwd);
        return datas;
    }

    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    @Override
    public String getHelpId() {
        return "smmanager.unlock";
    }

    @Override
    public String getTitle() {
        return "smmanager.unlock.title";
    }

    @Override
    protected JButton[] getSensitiveButtons() {
        return new JButton[0];
    }

    @Override
    public JButton[] getButtons() {
        return new JButton[] { okButton };
    }

    @Override
    protected Dimension getValidateViewPreferredSize() {
        return new Dimension(400, 200);
    }

    @Override
    protected void initializeData(List<Object> data) {
        super.initializeData(data);
        ((JDialog) getWindow()).setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

}
