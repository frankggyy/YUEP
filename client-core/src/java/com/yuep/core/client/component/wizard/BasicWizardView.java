/*
 * $Id: BasicWizardView.java, 2009-6-4 ����10:55:40 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.wizard;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.button.HelpButtonDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;

/**
 * <p>
 * Title: BasicWizardView
 * </p>
 * <p>
 * Description:�򵼿ؼ��е��������View
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 ����10:55:40
 * modified [who date description]
 * check [who date description]
 */
public abstract class BasicWizardView<T extends Object> extends AbstractValidateView<T> {
    private static final long serialVersionUID = 2768050808279661608L;
    protected PropertyChangeSupport propertyChangeSupport;
    public final static String PREVIOUS = "previous";
    public final static String NEXT = "next";
    /**
     * ǰһ����ť
     */
    private JButton previousButton;
    /**
     * ��һ����ť
     */
    private JButton nextButton;
    /**
     * ��ɰ�ť
     */
    private JButton finishButton;
    /**
     * ����Ľ�������
     */
    protected T cacheData;
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#addButtonListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    protected <V,M> void addButtonListener(ClientController<T,V,M> controller) {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * ��ʼ��ҳ���Զ�У��ؼ���PropertyChangeListener
     */
    protected void initMessagePaneListener() {
        messagePane.initPropertyChangeListener();
    }

    /**
     * PropertyChangeListener�Ľ����¼��ķ���
     * @param propertyName�仯����������
     * @param newValue ��ֵ
     */
    public void propertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
    }

    /**
     * PropertyChangeListener�Ľ����¼��ķ���
     * @param propertyName �仯����������
     * @param oldValue ��ֵ
     * @param newValue ��ֵ
     */
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * (non-Javadoc)
     * @see java.awt.Container#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * (non-Javadoc)
     * @see java.awt.Component#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getMessageLogoName()
     */
    @Override
    protected String getMessageLogoName() {
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
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return null;
    }

    /**
     * �Ƿ��ǿ�ʼҳ
     * @return ����Ƿ���<code>true</code>�����򷵻�<code>false</code>
     */
    protected abstract boolean isStartWizard();

    /**
     * �Ƿ��ǽ���ҳ
     * @return ����Ƿ���<code>true</code>�����򷵻�<code>false</code>
     */
    protected abstract boolean isEndWizard();

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#createActionPane()
     */
    @Override
    protected JComponent createActionPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        helpBtn = swingFactory.getButton(new HelpButtonDecorator(null));
        helpBtn.setVisible(true);
        // for buttons
        previousButton = swingFactory.getButton(new ButtonDecorator("common.button.previous",'p'));
        nextButton = swingFactory.getButton(new ButtonDecorator("common.button.next",'n'));
        finishButton = swingFactory.getButton(new ButtonDecorator("common.button.finish",'f'));
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<T> collectData = collectData();
                propertyChange(PREVIOUS, collectData);
            }

        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isContinue = validateData();
                if (isContinue) {
                    List<T> collectData = collectData();
                    propertyChange(NEXT, collectData);
                }
            }

        });
        finishButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isContinue = validateData();
                if (isContinue) {
                    List<T> collectData = collectData();
                    finish(collectData);
                }
            }

        });
        cancelButton = swingFactory.getButton(new ButtonDecorator("common.button.cancel",'c'));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton[] btns = new JButton[] { previousButton, nextButton, cancelButton };
        int index = 0;
        double[] d = new double[btns.length * 2 + 3];
        d[index++] = 10;
        d[index++] = 20;
        d[index++] = XTableLayout.FILL;
        for (JButton btn : btns) {
            d[index++] = btn.getPreferredSize().getWidth();
            d[index++] = 5;
        }
        index--;
        double[][] table = new double[][] { d, { 4, 5, XTableLayout.FILL, 5 } };
        JPanel btnPanel = swingFactory.getPanel(swingFactory.getTableLayout(table));
        btnPanel.add(swingFactory.getSeparator(), "0,0," + index + ",b");
        btnPanel.add(helpBtn, "1,2,r,c");
        if (!isStartWizard())
            btnPanel.add(previousButton, "3,2,r,c");
        if (!isEndWizard())
            btnPanel.add(nextButton, "5,2,r,c");
        else
            btnPanel.add(finishButton, "5,2,r,c");
        btnPanel.add(cancelButton, "7,2,r,c");
        nextButton.setEnabled(false);
        return btnPanel;
    }

    @Override
    protected abstract JComponent createContentPane();

    @Override
    public JButton getDefaultButton() {
        if (isEndWizard())
            return finishButton;
        return nextButton;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getDescription()
     */
    @Override
    protected abstract String getDescription();

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getHeader()
     */
    @Override
    protected abstract String getHeader();

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public abstract List<T> collectData();

    /**
     * (     * ��ʼ��������Ϣ����ʼ���Ƚ��ý���Ļ������ݸ���
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<T> data) {
        if(!CollectionUtils.isEmpty(data))
            cacheData = data.get(0);
        else
            cacheData = null;
        initBasicWizardData(data);
    }
    
    /**
     * ��ʼ������ĳһҳ������,�����ڷ���ʵ����ֱ��ͨ��cacheData���г�ʼ����Ҳ����ͨ������Ĳ������г�ʼ��
     * @param data Ҫ��ʼ����������Ϣ
     */
    protected abstract void initBasicWizardData(List<T> data);
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.AbstractClientView#dispose()
     */
    @Override
    public void dispose() {
        Container parent = getParent();
        if (parent instanceof ContainerWizardView) {
            ContainerWizardView wizardView = (ContainerWizardView) parent;
            wizardView.removeListener();
        }
        super.dispose();
    }

    /**
     * ���ݽ�����Ҫ��������У��
     * 
     * @return
     */
    protected abstract boolean validateData();

    /**
     * ���һ����Ҫʵ�ָ÷���
     */
    protected void finish(List<T> list) {
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
     * @see com.yuep.core.client.mvc.validate.AbstractValidateView#getSensitiveButtons()
     */
    @Override
    protected JButton[] getSensitiveButtons() {
        if (isEndWizard())
            return new JButton[] { finishButton };
        return new JButton[] { nextButton };
    }
}
