/*
 * $Id: SbiPropertyView.java, 2011-4-20 …œŒÁ10:16:49 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.view;

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
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.sbimanager.action.AddSbiPropertyAction;
import com.yuep.nms.core.client.sbimanager.controller.SbiPropertyController;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;

/**
 * <p>
 * Title: SbiPropertyView
 * </p>
 * <p>
 * Description:sbi Ù–‘
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 …œŒÁ10:16:49
 * modified [who date description]
 * check [who date description]
 */
public class SbiPropertyView extends AbstractValidateView<SbiProperty> {
    private static final long serialVersionUID = 2546868602149867678L;

    private JTextField ipTextField;
    private JTextField portTextField;
    private JTextField sbiNameTextField;
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener( ClientController<SbiProperty, V, M> controller) {
        this.okButton.addActionListener(new AddSbiPropertyAction("",(SbiPropertyController)controller));
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory=ClientCoreContext.getSwingFactory();
        JPanel contentPane=swingFactory.getPanel();
        double[][] ds=new double[][]{{10,120,TableLayout.FILL,10},swingFactory.getTableLayoutRowParam(3, 5, 5)};
        contentPane.setLayout(swingFactory.getTableLayout(ds));
        
        JLabel sbiNameLabel=swingFactory.getLabel(new Decorator<JLabel>(){

            @Override
            public JLabel contructEditor() {
                return new JLabel();
            }

            @Override
            public void decorate(JLabel t) {
                t.setText(ClientCoreContext.getString("sbimanager.sbiName"));
            }
            
        });
        contentPane.add(sbiNameLabel,"1,1,f,c");
        
        sbiNameTextField=swingFactory.getXEditor(new StringEditorDecorator("sbimanager.sbiname", true, 1, 64));
        contentPane.add(sbiNameTextField,"2,1,f,c");
        
        JLabel ipLabel=swingFactory.getLabel(new Decorator<JLabel>(){
            @Override
            public JLabel contructEditor() {
                return new JLabel();
            }

            @Override
            public void decorate(JLabel label) {
                label.setText(ClientCoreContext.getString("sbimanager.ip"));
            }
        });
        contentPane.add(ipLabel,"1,3,f,c");
        
        ipTextField=swingFactory.getXEditor(new IpAddressEditorDecorator("sbimanager.ip"));
        contentPane.add(ipTextField,"2,3,f,c");
        
        JLabel portLabel=swingFactory.getLabel(new Decorator<JLabel>(){
            @Override
            public JLabel contructEditor() {
                return new JLabel();
            }

            @Override
            public void decorate(JLabel t) {
                t.setText(ClientCoreContext.getString("sbimanager.port"));
            }
        });
        contentPane.add(portLabel,"1,5,f,c");
        portTextField=swingFactory.getXEditor(new NumberEditorDecorator("sbimanager.port",true,1,65535));
        contentPane.add(portTextField,"2,5,f,c");
        
        return contentPane;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        return "sbimanager.sbiproperty.des";
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<SbiProperty> collectData() {
        SbiProperty sbiProperty=new SbiProperty();
        sbiProperty.setSbiName(sbiNameTextField.getText());
        sbiProperty.setIp(ipTextField.getText());
        sbiProperty.setPort(Integer.parseInt(portTextField.getText()));
        return YuepObjectUtils.newArrayList(sbiProperty);
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
        return "sbimanager.sbiproperty.tile";
    }

}
