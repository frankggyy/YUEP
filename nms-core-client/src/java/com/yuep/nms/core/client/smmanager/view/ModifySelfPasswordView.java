/*
 * $Id: ModifySelfPasswordView.java, 2011-4-13 下午05:59:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;

import twaver.swing.TableLayout;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.PasswordEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.XPasswordFieldEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.nms.core.client.smmanager.action.ModifySelfPasswordApplyAction;

/**
 * <p>
 * Title: ModifySelfPasswordView
 * </p>
 * <p>
 * Description: 修改自己的密码
 * </p>
 * 
 * @author sufeng
 * created 2011-4-27 下午05:59:55
 * modified [who date description]
 * check [who date description]
 */
public class ModifySelfPasswordView extends AbstractValidateView<Object> {

    private static final long serialVersionUID = 3266224239698895078L;
    private XPasswordFieldEditor oldPwdTextField ;
    private XPasswordFieldEditor pwdTextField;
    private XPasswordFieldEditor confirmPwdTextField;

    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        ModifySelfPasswordApplyAction applyAction=new ModifySelfPasswordApplyAction(ModifySelfPasswordApplyAction.class.getSimpleName());
        okButton.addActionListener(applyAction);
        okButton.setEnabled(true);
    }

    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        
        JLabel oldPwdLabel=swingFactory.getLabel(new LabelDecorator(ClientCoreContext.getString("smmanager.modifyselfpassword.oldpassword")));
        JLabel pwdLabel=swingFactory.getLabel(new LabelDecorator(ClientCoreContext.getString("smmanager.modifyselfpassword.password")));
        JLabel confirmPwdLabel=swingFactory.getLabel(new LabelDecorator(ClientCoreContext.getString("smmanager.modifyselfpassword.confirmpassword")));
        
        oldPwdTextField = swingFactory.getXEditor(new PasswordEditorDecorator("oldpassword"));
        pwdTextField = swingFactory.getXEditor(new PasswordEditorDecorator("password"));
        confirmPwdTextField = swingFactory.getXEditor(new PasswordEditorDecorator("confirmpassword"));
        
        double[][] ds=new double[][]{{5,100,5,TableLayout.FILL,5},swingFactory.getTableLayoutRowParam(3, 1, 1)};
        JPanel pane=swingFactory.getPanel(new TableLayout(ds));
        pane.add(oldPwdLabel,"1,1,f,c");
        pane.add(oldPwdTextField,"3,1,f,c");
        pane.add(pwdLabel,"1,3,f,c");
        pane.add(pwdTextField,"3,3,f,c");
        pane.add(confirmPwdLabel,"1,5,f,c");
        pane.add(confirmPwdTextField,"3,5,f,c");
        return pane;
    }

    @Override
    protected String getDescription() {
        return "smmanager.modifyselfpassword.description";
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public List<Object> collectData() {
        String newPwd = new String(pwdTextField.getPassword());
        String confirmPwd = new String(confirmPwdTextField.getPassword());
        String oldPwd = new String (oldPwdTextField.getPassword());
        if(StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(confirmPwd) 
                || StringUtils.isEmpty(oldPwd)){
            DialogUtils.showErrorDialog(this.getWindow(), "smmanager.modifyselfpassword.input is null");
            return null;
        }
        if(!newPwd.equals(confirmPwd)){
            DialogUtils.showErrorDialog(this.getWindow(), "smmanager.modifyselfpassword.confirmpassword.unmatch");
            return null;
        }
        
        List datas=new ArrayList();
        String clientEncryptPwd_old = EncryptUtils.setEncrypt(oldPwd, EncryptUtils.MAGIC_KEY_CLIENT);
        String clientEncryptPwd_new = EncryptUtils.setEncrypt(newPwd, EncryptUtils.MAGIC_KEY_CLIENT);
        datas.add(clientEncryptPwd_old);
        datas.add(clientEncryptPwd_new);
        return datas;
    }

    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    @Override
    public String getHelpId() {
        return "smmanager.modifyselfpassword";
    }

    @Override
    public String getTitle() {
        return "smmanager.modifyselfpassword.title";
    }
    
    @Override
    protected JButton[] getSensitiveButtons() {
        return new JButton[0];
    }

}
