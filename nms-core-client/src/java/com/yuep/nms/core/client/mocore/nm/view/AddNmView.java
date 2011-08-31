/*
 * $Id: AddNmView.java, 2011-4-19 下午02:30:38 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.mocore.nm.view;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twaver.swing.TableLayout;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.Decorator;
import com.yuep.core.client.component.factory.decorator.editor.IpAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.mocore.nm.action.AddNmAction;
import com.yuep.nms.core.client.mocore.nm.controller.AddNmController;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;

/**
 * <p>
 * Title: AddNmView
 * </p>
 * <p>
 * Description:
 * 添加下级网管
 * </p>
 * 
 * @author yangtao
 * created 2011-4-19 下午02:30:38
 * modified [who date description]
 * check [who date description]
 */
public class AddNmView extends AbstractValidateView<ManagedNodeProperty> {
    private static final long serialVersionUID = -5383644456983632257L;

    private JTextField ipTextField;
    private JTextField portTextField;
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<ManagedNodeProperty, V, M> controller) {
        this.okButton.addActionListener(new AddNmAction("nm.add",(AddNmController)controller));
    }

    
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory=ClientCoreContext.getSwingFactory();
        JPanel contentPane=swingFactory.getPanel();
        
        double[][] ds=new double[][]{{10,120,TableLayout.FILL,10},swingFactory.getTableLayoutRowParam(4, 5, 5)};
        contentPane.setLayout(swingFactory.getTableLayout(ds));
        
        JLabel ipLabel=swingFactory.getLabel(new Decorator<JLabel>(){
            @Override
            public JLabel contructEditor() {
                return new JLabel();
            }

            @Override
            public void decorate(JLabel label) {
                label.setText(ClientCoreContext.getString("nm.ip"));
            }
        });
        contentPane.add(ipLabel,"1,1,f,c");
        
        ipTextField=swingFactory.getXEditor(new IpAddressEditorDecorator(ClientCoreContext.getString("nm.ip")));
        contentPane.add(ipTextField,"2,1,f,c");
        
        JLabel portLabel=swingFactory.getLabel(new Decorator<JLabel>(){
            @Override
            public JLabel contructEditor() {
                return new JLabel();
            }

            @Override
            public void decorate(JLabel t) {
                t.setText(ClientCoreContext.getString("nm.port"));
            }
        });
        contentPane.add(portLabel,"1,3,f,c");
        portTextField=swingFactory.getXEditor(new NumberEditorDecorator(ClientCoreContext.getString("nm.port"),true,1,65535));
        contentPane.add(portTextField,"2,3,f,c");
        
        return contentPane;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return ClientCoreContext.getString("mo.nm.add.des");
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<ManagedNodeProperty> collectData() {
        ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setIp(ipTextField.getText());
        managedNodeProperty.setPort(Integer.parseInt(portTextField.getText()));
        return YuepObjectUtils.newArrayList(managedNodeProperty);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return this.okButton;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return ClientCoreContext.getString("mo.nm.add.title");
    }

}
