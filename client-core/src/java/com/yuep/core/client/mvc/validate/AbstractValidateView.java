/*
 * $Id: AbstractValidateView1.java, 2009-4-10 上午10:16:51 aaron lee Exp $
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
 * Description:可以实现自动校验功能View的抽象类，内部默认包含消息输出区（可选不构造消息输出区）和Action区，
 * </p>
 * <p>
 * Action区：默认包含帮助、确定和关闭三个按钮
 * </p>
 * <p>
 * 消息输出区：可以支持显示界面的介绍信息的标题，介绍的具体内容；当界面内自动校验控件校验出错时会显示错误提示信息
 * </p>
 * <p>
 * 如果界面中自动校验出错时，确定按钮为不可用
 * </p>
 * @author aaron lee
 * created 2009-4-10 上午10:16:51
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractValidateView<T extends Object> extends AbstractClientView<T> {
    private static final long serialVersionUID = 7799394414839194782L;
    /**
     * 消息输出区的Panel
     */
    protected MessagePane messagePane;
    /**
     * 帮助按钮
     */
    protected JButton helpBtn;
    /**
     * 确定按钮
     */
    protected JButton okButton;
    /**
     * 关闭按钮
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
     * 构造不包含消息输出区的界面
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
     * 返回界面大小
     * @return Dimension 界面大小
     */
    protected Dimension getValidateViewPreferredSize() {
        return new Dimension(450, 500);
    }

    /**
     * 返回中间内容区域的Panel，用户根据自己需要布局添加控件
     * @return JComponent 内容区域的Panel
     */
    protected abstract JComponent createContentPane();

    /**
     * 添加按钮的事件监听者
     * @param <V> ClientView
     * @param <M> ClientModel
     * @param controller ClientController
     */
    protected abstract <V,M> void addButtonListener(ClientController<T,V,M> controller);

    /**
     * 初始化界面数据
     * @param data 需要填充到界面的数据信息列表
     */
    protected void initializeData(List<T> data) {

    }

    /**
     * 获取消息输出区的标题信息,默认返回Title
     * @return String 标题信息
     */
    protected String getHeader(){
        String title = getTitle();
        String header = ClientCoreContext.getString(title);
        return header;
    }

    /**
     * 获取消息输出区的描述信息
     * @return String 输出区的描述信息
     */
    protected abstract String getDescription();

    /**
     * 获取消息输出区的图标名，默认返回空
     * @return String 输出区的图标名
     */
    protected String getMessageLogoName(){
        return "";
    }

    /**
     * 构造展现按钮的Panel
     * @return JComponent 展示按钮的Panel
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
     * 通过这个方法可以改变默认显示的按钮，帮助按钮必须显示，其他两个按钮可以定义是否显示，也可以添加其他按钮
     * @return JButton[] 按钮数组
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
     * 设置消息输出区的标题
     */
    public void setHeader() {
        String header = getHeader();
        if (!StringUtils.isEmpty(header))
            messagePane.setHeader(header);
    }

    /**
     * 设置消息输出区的描述
     */
    public void setDescription() {
        String description = getDescription();
        if (!StringUtils.isEmpty(description))
            messagePane.setDescription(description);
    }

    /**
     * 设置消息输出区的图片名
     */
    public void setMessageLogoName() {
        String messageLogoName = getMessageLogoName();
        if (!StringUtils.isEmpty(messageLogoName))
            messagePane.setLogo(messageLogoName);
    }

    /**
     * <p>
     * 更新组件可用状态，主要用于一个checkbox控制一些组件是否可用的场景
     * </p>
     * <code>
     *      updateEnabled(checkbox1.isEnabled(), checkbox2, label1, textfeild1);
     * </code>
     * 
     * @param enabled
     *            后面组件设置的使能状态
     * @param components
     *            组件
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
     * @return 国际化获取接口封装
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
     * 获取敏感按钮的数组，敏感按钮是真的自动校验控件的校验结果
     * @return JButton[] 按钮数组
     */
    protected JButton[] getSensitiveButtons(){
        return new JButton[]{okButton};
    }
}
