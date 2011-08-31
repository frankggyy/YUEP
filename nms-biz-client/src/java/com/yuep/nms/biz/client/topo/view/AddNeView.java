/*
 * $Id: AddNeView.java, 2011-4-19 ÏÂÎç03:49:59 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo.view;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.IpAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.NumberEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.menu.action.AbstractButtonAction;
import com.yuep.core.client.component.validate.editor.XComboBoxEditor;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;

/**
 * <p>
 * Title: AddNeView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-4-19 ÏÂÎç03:49:59
 * modified [who date description]
 * check [who date description]
 */
public class AddNeView extends AbstractValidateView<Object>{

    private static final long serialVersionUID = 5902950518144660818L;
    private XIpAddressEditor<IpAddressValidator> ipAdressEditor;
    private XNumberEditor<NumberValidator> portField;
    private XComboBoxEditor sbiCombo;
    private MoNaming parent;
    private List<SbiProperty> sbiList;

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {
        okButton.addActionListener(new AbstractButtonAction(""){
            private static final long serialVersionUID = -4625176666445968309L;
            @Override
            protected Object[] commitData(Object... objs) {
                String ip = ipAdressEditor.getText();
                String port = portField.getText();
                SbiProperty sbiProperty=null;
                if(sbiCombo.getSelectedItem()!=null)
                	sbiProperty=(SbiProperty)sbiCombo.getSelectedItem();
                ManagedNodeProperty property = new ManagedNodeProperty();
                property.setIp(ip);
                property.setPort(Integer.parseInt(port));
                MoManager moManager = ClientCoreContext.getRemoteService("moManager", MoManager.class);
                SbiManager sbiManager = ClientCoreContext.getRemoteService("sbiManager", SbiManager.class);
                Mo mo = moManager.createManagedNode(parent, property, "C8000");
                
                if(sbiProperty!=null)
                	sbiManager.setNeToSbi(sbiProperty.getSbiNaming(), mo.getMoNaming());
//                sbiManager.setNeToSbi(sbiName, mo.getMoNaming());
//                EqmService eqmService=ClientCoreContext.getRemoteService("eqmService", EqmService.class);
//                String neUserLabel = eqmService.getNeUserLabel(mo.getMoNaming());
//                DialogUtils.showInfoDialog(getWindow(), neUserLabel);
                return null;
            }
            @Override
            protected void updateUi(Object... objs) {
                dispose();
            }
            
        });
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createContentPane()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JPanel mainPanel = swingFactory.getPanel();
        double[][] ds = new double[][] {
                { 10, 120, TableLayout.FILL, 5, 80, 10 }, swingFactory.getTableLayoutRowParam(3, 10, 10) };
        mainPanel.setLayout(swingFactory.getTableLayout(ds));
        JLabel ipLabel = swingFactory.getLabel(new LabelDecorator("AddNeView.title"));
        ipAdressEditor = swingFactory.getXEditor(new IpAddressEditorDecorator("AddNeView.title", true));
        JLabel portLabel = swingFactory.getLabel(new LabelDecorator("AddNeView.port"));
        portField = ClientCoreContext.getSwingFactory().getXEditor(new NumberEditorDecorator("AddNeView.port",true, 1, 10000));
        JLabel sbiLabel = swingFactory.getLabel(new LabelDecorator("SBI£º"));
        sbiCombo=ClientCoreContext.getSwingFactory().getXEditor(new ComboBoxEditorDecorator(""));
        mainPanel.add(ipLabel, "1,1,f,c");
        mainPanel.add(ipAdressEditor, "2,1,f,c");
        mainPanel.add(portLabel, "1,3,f,c");
        mainPanel.add(portField, "2,3,f,c");
        mainPanel.add(sbiLabel, "1,5,f,c");
        mainPanel.add(sbiCombo, "2,5,f,c");
        return mainPanel;
    }
    
    private void initCombo(){
        sbiCombo.removeAllItems();
        for(SbiProperty property:sbiList){
            sbiCombo.addItem(property);
        }   
    }

    /**
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return "AddNeView.desc";
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<Object> collectData() {
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

    /**
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "AddNeView.desc";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initializeData(List<Object> data) {
        if(CollectionUtils.isEmpty(data))
            return;
        List<Object> objectList = (List<Object>)data.get(0);
        this.parent = (MoNaming) objectList.get(0);
        List<SbiProperty> sbiList=(List<SbiProperty>)objectList.get(1);
        this.sbiList=sbiList;
        initCombo();
    }

}
