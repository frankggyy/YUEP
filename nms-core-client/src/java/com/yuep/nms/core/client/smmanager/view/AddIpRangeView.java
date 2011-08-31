/*
 * $Id: AddIpRangeView.java, 2011-4-25 上午11:39:06 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import twaver.swing.TableLayout;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.IpAddressEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.editor.XComboBoxEditor;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.nms.core.client.smmanager.action.SaveAddIpRange;
import com.yuep.nms.core.common.smcore.model.IpRange;
/**
 * <p>
 * Title: AddIpRangeView
 * </p>
 * <p>
 * Description:为用户分配登陆IP范围的View
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 上午11:39:06
 * modified [who date description]
 * check [who date description]
 */
public class AddIpRangeView extends AbstractValidateView<IpRange> {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1635207639590021564L;
    private SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
    private XIpAddressEditor startIpEditor;
    private XIpAddressEditor endIpEditor;
    private XComboBoxEditor ipRangeCombox;
    private Map<String, IpRange> ipRangeMap = new HashMap<String, IpRange>();

    @Override
    protected void addButtonListener(ClientController controller) {
        SaveAddIpRange saveAction = new SaveAddIpRange(true, "", controller);
        okButton.addActionListener(saveAction);
        ipRangeCombox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IpRange ipRange = ipRangeMap.get(ipRangeCombox.getSelectedItem());
                startIpEditor.setText(ipRange.getStartIpAddress());
                endIpEditor.setText(ipRange.getEndIpAddress());

            }

        });

    }

    @Override
    protected JComponent createContentPane() {
        JPanel panel = swingFactory.getPanel(swingFactory.getTableLayout(new double[][] {
                { 5, 5, 100, TableLayout.FILL, 5 }, swingFactory.getTableLayoutRowParam(10, 0, 0) }));
        JPanel comboxSeperator = (JPanel) swingFactory.getXTitleSeparator("IpRange.choose");
        JLabel ipRangeLabel = swingFactory.getLabel(new LabelDecorator("User.ipRanges"));
        ipRangeCombox = new XComboBoxEditor();
        JPanel inputSeperator = (JPanel) swingFactory.getXTitleSeparator("IpRange.input");
        JLabel startIpLbl = swingFactory.getLabel(new LabelDecorator("IpRange.startIpAddress"));
        startIpEditor = swingFactory.getXEditor(new IpAddressEditorDecorator("IpRange.startIpAddress", true));
        JLabel endIpLbl = swingFactory.getLabel(new LabelDecorator("IpRange.endIpAddress"));
        endIpEditor = swingFactory.getXEditor(new IpAddressEditorDecorator("IpRange.endIpAddress", true));
        panel.add(comboxSeperator, "1,1,4,c");
        panel.add(ipRangeLabel, "2,3,f,c");
        panel.add(ipRangeCombox, "3,3,f,c");
        panel.add(inputSeperator,"1,5,4,c");
        panel.add(startIpLbl, "2,7,f,c");
        panel.add(startIpEditor, "3,7,f,c");
        panel.add(endIpLbl, "2,9,f,c");
        panel.add(endIpEditor, "3,9,f,c");
        return panel;
    }

    @Override
    protected String getDescription() {
        // TODO Auto-generated method stub
        return "IpRange.des";
    }

    @Override
    public List<IpRange> collectData() {
        IpRange ipRange = new IpRange();
        ipRange.setStartIpAddress(startIpEditor.getText());
        ipRange.setEndIpAddress(endIpEditor.getText());
        List<IpRange> list = new ArrayList<IpRange>();
        list.add(ipRange);
        return list;
    }

    @Override
    public JComponent getDefaultFocus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHelpId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return "IpRange.title";
    }

    @Override
    protected void initializeData(List<IpRange> data) {
        for (IpRange range : data) {
            String stringRange = range.getStartIpAddress() + "-" + range.getEndIpAddress();
            ComboBoxModel dataModel = ipRangeCombox.getModel();
            ipRangeMap.put(stringRange, range);
            ipRangeCombox.addItem(stringRange);

        }

    }

}
