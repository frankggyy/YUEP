/*
 * $Id: AbstractValidateView1.java, 2009-4-10 ����10:16:51 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc.validate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.factory.decorator.button.HelpButtonDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;

/**
 * <p>
 * Title: AbstractValidateView1
 * </p>
 * <p>
 * Description:����ʵ���Զ�У�鹦��View�ĳ����࣬�ڲ�Ĭ�ϰ�����Ϣ���������ѡ��������Ϣ���������Action����
 * </p>
 * <p>
 * Action����Ĭ�ϰ���������ȷ���͹ر�������ť
 * </p>
 * <p>
 * ��Ϣ�����������֧����ʾ����Ľ�����Ϣ�ı��⣬���ܵľ������ݣ����������Զ�У��ؼ�У�����ʱ����ʾ������ʾ��Ϣ
 * </p>
 * <p>
 * ����������Զ�У�����ʱ��ȷ����ťΪ������
 * </p>
 * @author aaron lee
 * created 2009-4-10 ����10:16:51
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractValidateView<T extends Object> extends AbstractClientView<T> {
    private static final long serialVersionUID = 7799394414839194782L;
    /**
     * ��Ϣ�������Panel
     */
    protected MessagePane messagePane;
    /**
     * ������ť
     */
    protected JButton helpBtn;
    /**
     * ȷ����ť
     */
    protected JButton okButton;
    /**
     * �رհ�ť
     */
    protected JButton cancelButton;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#addListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    public final <V, M> void addListener(ClientController<T, V, M> controller) {
          addButtonListener(controller);
          if(messagePane != null)
              messagePane.initPropertyChangeListener();
          cancelButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  dispose();
              }
          });
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        JComponent contentPane = createContentPane();
        JComponent actionPane = createActionPane();
        actionPane.setBorder(ClientCoreContext.getSwingFactory().getEmptyBorder(0, 0, 0, 5));
        messagePane = new MessagePane(getSensitiveButtons());
        // JScrollPane scrollPane = YotcSwingFactory.getScrollPane(contentPane);
        // scrollPane.setBorder(null);
        setLayout(new BorderLayout());
        add(messagePane, BorderLayout.NORTH);
        add(contentPane, BorderLayout.CENTER);
        add(actionPane, BorderLayout.SOUTH);
        setPreferredSize(getValidateViewPreferredSize());
        setHeader();
        setDescription();
        setMessageLogoName();
    }
    
    /**
     * ���첻������Ϣ������Ľ���
     */
    protected void constructUiNoMessagePane(){
        JComponent contentPane = createContentPane();
        JComponent actionPane = createActionPane();
        actionPane.setBorder(ClientCoreContext.getSwingFactory().getEmptyBorder(0, 0, 0, 5));
        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
        add(actionPane, BorderLayout.SOUTH);
        setPreferredSize(getValidateViewPreferredSize());
    }

    /**
     * ���ؽ����С
     * @return Dimension �����С
     */
    protected Dimension getValidateViewPreferredSize() {
        return new Dimension(450, 500);
    }

    /**
     * �����м����������Panel���û������Լ���Ҫ������ӿؼ�
     * @return JComponent ���������Panel
     */
    protected abstract JComponent createContentPane();

    /**
     * ��Ӱ�ť���¼�������
     * @param <V> ClientView
     * @param <M> ClientModel
     * @param controller ClientController
     */
    protected abstract <V,M> void addButtonListener(ClientController<T,V,M> controller);

    /**
     * ��ʼ����������
     * @param data ��Ҫ��䵽�����������Ϣ�б�
     */
    protected void initializeData(List<T> data) {

    }

    /**
     * ��ȡ��Ϣ������ı�����Ϣ,Ĭ�Ϸ���Title
     * @return String ������Ϣ
     */
    protected String getHeader(){
        String title = getTitle();
        String header = ClientCoreContext.getString(title);
        return header;
    }

    /**
     * ��ȡ��Ϣ�������������Ϣ
     * @return String �������������Ϣ
     */
    protected abstract String getDescription();

    /**
     * ��ȡ��Ϣ�������ͼ������Ĭ�Ϸ��ؿ�
     * @return String �������ͼ����
     */
    protected String getMessageLogoName(){
        return "";
    }

    /**
     * ����չ�ְ�ť��Panel
     * @return JComponent չʾ��ť��Panel
     */
    protected JComponent createActionPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        helpBtn = swingFactory.getButton(new HelpButtonDecorator(getHelpId()));
        helpBtn.setVisible(true);
        // for buttons
        okButton = swingFactory.getButton(new ButtonDecorator("common.button.ok",'o'));
        cancelButton = swingFactory.getButton(new ButtonDecorator("common.button.cancel",'c'));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton[] btns = getButtons();
        if (btns == null) {
            btns = new JButton[0];
        }
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
        index = 1;
        for (JButton btn : btns) {
            index += 2;
            btnPanel.add(btn, index + ",2,r,c");
        }
        okButton.setEnabled(false);
        return btnPanel;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return okButton;
    }
    
    /**
     * ͨ������������Ըı�Ĭ����ʾ�İ�ť��������ť������ʾ������������ť���Զ����Ƿ���ʾ��Ҳ�������������ť
     * @return JButton[] ��ť����
     */
    public JButton[] getButtons(){
        return new JButton[] { okButton, cancelButton };
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
    }

    // private boolean isCommited() {
    // Set<Entry<Integer, AbstractEditor<? extends Validator>>> entrySet =
    // editors.entrySet();
    // for (Entry<Integer, AbstractEditor<? extends Validator>> entry :
    // entrySet) {
    // AbstractEditor<? extends Validator> value = entry.getValue();
    // if (!value.isCommited())
    // return false;
    // }
    // return true;
    // }
    /**
     * ������Ϣ������ı���
     */
    public void setHeader() {
        String header = getHeader();
        if (!StringUtils.isEmpty(header))
            messagePane.setHeader(header);
    }

    /**
     * ������Ϣ�����������
     */
    public void setDescription() {
        String description = getDescription();
        if (!StringUtils.isEmpty(description))
            messagePane.setDescription(description);
    }

    /**
     * ������Ϣ�������ͼƬ��
     */
    public void setMessageLogoName() {
        String messageLogoName = getMessageLogoName();
        if (!StringUtils.isEmpty(messageLogoName))
            messagePane.setLogo(messageLogoName);
    }

    /**
     * <p>
     * �����������״̬����Ҫ����һ��checkbox����һЩ����Ƿ���õĳ���
     * </p>
     * <code>
     *      updateEnabled(checkbox1.isEnabled(), checkbox2, label1, textfeild1);
     * </code>
     * 
     * @param enabled
     *            ����������õ�ʹ��״̬
     * @param components
     *            ���
     */
    protected void updateEnabled(boolean enabled, JComponent... components) {
        if (components == null || components.length == 0) {
            return;
        }
        for (JComponent jc : components) {
            if (jc != null) {
                jc.setEnabled(enabled);
            }
        }
    }

    /**
     * @return ���ʻ���ȡ�ӿڷ�װ
     */
//    protected String getString(String key, Object... values) {
//        return ClientContext.getResourceFactory().getString(key, values);
//    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#initData(java.util.List)
     */
    @Override
    public final void initData(List<T> boList) {
        if(messagePane != null)
            messagePane.init();
        JButton[] sensitiveButtons = getSensitiveButtons();
        for (JButton button : sensitiveButtons) {
            button.setEnabled(false);            
        }
        initializeData(boList);
    }
    
    /**
     * ��ȡ���а�ť�����飬���а�ť������Զ�У��ؼ���У����
     * @return JButton[] ��ť����
     */
    protected JButton[] getSensitiveButtons(){
        return new JButton[]{okButton};
    }
}
