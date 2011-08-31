/*
 * $Id: MessagePane.java, 2009-3-9 ����12:18:48 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc.validate;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.label.IconLabelDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.editor.XEditor;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.component.validate.editor.XTableEditor;
import com.yuep.core.client.component.validate.editor.XTextIPSpace;
import com.yuep.core.client.component.validate.editor.XTreeEditor;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;

/**
 * <p>
 * Title: MessagePane
 * </p>
 * <p>
 * Description:��Ϣ�������������ʾ���������������Ϳؼ������У�������Ϣ
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 ����12:18:48
 * modified [who date description]
 * check [who date description]
 */
public class MessagePane extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 4176171703233025114L;
    /**
     * �����Label
     */
    private JLabel headLbl;
    /**
     * ������Ϣ��Label
     */
    private JLabel errorLbl;
    /**
     * ������Ϣ��ʾ����
     */
    private JTextArea msgLbl;
    /**
     * ͼƬ��Label
     */
    private JLabel logoLbl;
    /**
     * ������Ϣ
     */
    private String desc;
    /**
     * IP�ؼ�������˳��
     */
    private Map<String, List<String>> ipSortMap;

    /**
     * �洢У�������Ϣ��Map
     */
    private Map<String, ValidateMessage> messageMap;
    /**
     * �ؼ�������˳��
     */
    private List<String> sortList;
    /**
     * ��ҪУ��Ŀؼ��б�
     */
    private List<XEditor> validatorList;
    /**
     * �Ƿ��һ�γ�ʼPropertyChangeListener
     */
    private boolean isFirst = true;
    /**
     * ���а�ť������
     */
    private JButton[] sensitiveButtons;

    /**
     * ���췽��
     * 
     * @param sensitiveButtons
     *            ���а�ť
     */
    public MessagePane(JButton[] sensitiveButtons) {
        this.sensitiveButtons =sensitiveButtons;
        messageMap = new ConcurrentHashMap<String, ValidateMessage>();
        ipSortMap = new ConcurrentHashMap<String, List<String>>();
        validatorList = new ArrayList<XEditor>();
        sortList = new ArrayList<String>();
        double[][] table = { { 5, 16, XTableLayout.FILL, 75 }, { 2, 26, 40, 2 } };
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        LayoutManager tableLayout = swingFactory.getTableLayout(table);
        setLayout(tableLayout);
        // headLbl =
        // YotcSwingFactory.getLabel(ClientContext.getResourceFactory().getString(
        // "common.frame.default.header"));
        headLbl = swingFactory.getLabel(new LabelDecorator(""));
        headLbl.setFont(new Font(headLbl.getFont().getName(), Font.BOLD, headLbl.getFont().getSize() + 5));
        headLbl.setBackground(Color.white);
        headLbl.setOpaque(false);
        errorLbl = swingFactory.getLabel(new IconLabelDecorator("nav_error.gif"));
        errorLbl.setVisible(false);
        msgLbl = new JTextArea();
        msgLbl.setBorder(null);
        msgLbl.setEditable(false);
        msgLbl.setBackground(Color.white);
        msgLbl.setOpaque(false);
        msgLbl.setRows(2);
        msgLbl.setFocusable(false);
        msgLbl.setLineWrap(true);
        logoLbl = swingFactory.getLabel(new IconLabelDecorator("nav_default.gif"));
        setOpaque(true);
        setBackground(new Color(255, 255, 255));
        add(headLbl, "1,1,2,f");
        add(errorLbl, "1,2,t,t");
        add(msgLbl, "2,2,2,f");
        add(logoLbl, "3,1,3,2");
        add(swingFactory.getSeparator(), "0,3,3,f");
        setSize(getSize().width, 100);

    }

    /**
     * ������������ʾ������Ϣ���д���ͼ�����
     * 
     * @param error
     *            ����������Ϣ
     */
    public void showError(String error) {
        if (!StringUtils.isEmpty(error)) {
            errorLbl.setIcon(ClientCoreContext.getResourceFactory().getIcon("nav_error.gif"));
            errorLbl.setVisible(true);
            msgLbl.setText(error);
        } else {
            errorLbl.setVisible(false);
            setDescription(desc);
        }
    }

    /**
     * 
     * ������������ʾ������Ϣ���о���ͼ�����
     * 
     * @param warning
     *            ����������Ϣ
     */
    public void showWarning(String warning) {
        if (!StringUtils.isEmpty(warning)) {
            errorLbl.setIcon(ClientCoreContext.getResourceFactory().getIcon("nav_warning.gif"));
            errorLbl.setVisible(true);
            msgLbl.setText(warning);
        } else {
            errorLbl.setVisible(false);
            setDescription(desc);
        }
    }

    /**
     * 
     * ������������ʾ��Ϣ��Ϣ����ͼ��
     * 
     * @param msg
     *            ��Ϣ����
     */
    public void showMsg(String msg) {
        errorLbl.setVisible(false);
        msgLbl.setText(msg);
    }

    /**
     * ��������������
     * 
     * @param header
     *            ����������
     */
    public void setHeader(String header) {
        headLbl.setText(header);
    }

    /**
     * ������������Ҫ��������
     * 
     * @param desc
     *            ��������Ҫ��������
     */
    public void setDescription(String desc) {
        errorLbl.setVisible(false);
        if (StringUtils.isEmpty(this.desc) || !this.desc.equals(desc)) {
            String textStr = ClientCoreContext.getString(desc);
            this.desc = textStr;
        }
        msgLbl.setText(this.desc);
    }

    /**
     * �����������ұ�logoͼƬ
     * 
     * @param iconName
     *            �������ұ�logoͼƬ�������META-INF/images·��������
     */
    public void setLogo(String iconName) {
        logoLbl.setIcon(ClientCoreContext.getResourceFactory().getIcon(iconName));
    }

    /*
     * (non-Javadoc)
     * 
     * @seejava.beans.PropertyChangeListener#propertyChange(java.beans.
     * PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (isFirst) {
            isFirst = false;
            for (int i = validatorList.size() - 1; i >= 0; i--) {
                XEditor editor = validatorList.get(i);
                ValidateMessage validateRequire = editor.initValidateRequire();
                if (validateRequire != null
                        && !validateRequire.getMessageType().equals(ValidateMessage.MessageType.CLEAN)) {
                    addMessage(editor.getPropertyName(), validateRequire);
                }
            }
        }
        Object newValue = evt.getNewValue();
        if (newValue instanceof ValidateMessage) {
            ValidateMessage validateMessage = (ValidateMessage) newValue;
            String messageType = validateMessage.getMessageType();
            String propertyName = evt.getPropertyName();
            if (!StringUtils.isEmpty(messageType) && messageType.equals(ValidateMessage.MessageType.CLEAN)) {
                messageMap.remove(propertyName);
                sortList.remove(propertyName);
                processMsg(propertyName, null);
            }
            if (!StringUtils.isEmpty(messageType) && messageType.equals(ValidateMessage.MessageType.DESC_MSG)) {
                String msg = validateMessage.getMsg();
                setDescription(msg);
            } else {
                if (!validateMessage.getMessageType().equals(ValidateMessage.MessageType.CLEAN))
                    addMessage(propertyName, validateMessage);
                processMsg(propertyName, validateMessage);
            }

            setSensitiveButtonEnabled(isCommited(evt.getPropertyName()));
        }
    }

    /**
     * �������а�ť��ʹ��״̬
     * 
     * @param enabled
     *            ʹ��״̬
     */
    public void setSensitiveButtonEnabled(boolean enabled) {
        for (JButton button : sensitiveButtons) {
            button.setEnabled(enabled);
        }
    }

    private boolean isCommited(String propertyName) {
        List<XEditor> editors = new ArrayList<XEditor>();
        Set<Boolean> set = new HashSet<Boolean>();
        for (XEditor editor : validatorList) {
            if (editor.isModified()) {
                editors.add(editor);
                if (!(editor instanceof XTableEditor) && !(editor instanceof XTreeEditor)) {
                    if (!editor.isCommited()) {
                        editor.setErrorBorder();
                    } else {
                        editor.clearErrorBorder();
                    }
                }
            }
        }
        for (XEditor editor : editors) {
            set.add(editor.isCommited());
        }
        if (!CollectionUtils.isEmpty(set)) {
            if (set.size() == 2)
                return false;
            else
                return set.iterator().next();
        } else {
            return false;
        }
    }

    private void addMessage(String propertyName, ValidateMessage validateRequire) {
        if (messageMap.containsKey(propertyName)) {
            messageMap.remove(propertyName);
            sortList.remove(propertyName);
        }
        messageMap.put(propertyName, validateRequire);
        sortList.add(0, propertyName);
    }

    /**
     * Initialized PropertyChangeListener
     */
    public void initPropertyChangeListener() {
        isFirst = true;
        Container parent = this.getParent();
        if (parent == null){
            System.out.println("Message panel parent is null!");
            return;
        }
        Component[] components = parent.getComponents();
        validatorList.clear();
        getAllValidators(components);
        for (XEditor editor : validatorList) {
            if (editor instanceof XIpAddressEditor) {
                registerIpEditorListener(editor);
            } else {
                editor.addPropertyChangeListener(editor.getPropertyName(), this);
            }
        }
    }

    /**
     * Ϊĳ���ؼ������ڵ��Զ�У��ؼ����PropertyChangeListener
     * 
     * @param parent
     *            �ؼ�����
     */
    public void initPropertyChangeListener(JComponent parent) {
        if (parent == null){
            System.out.println("Message panel parent is null!");
            return ;
        }
        Component[] components = parent.getComponents();
        validatorList.clear();
        getAllValidators(components);
        for (XEditor editor : validatorList) {
            if (editor instanceof XIpAddressEditor) {
                registerIpEditorListener(editor);
            } else {
                editor.addPropertyChangeListener(editor.getPropertyName(), this);
            }
        }
    }

    /**
     * Remove PropertyChangeListener
     */
    public void removePorpertyChangeListener() {
        isFirst = true;
        for (XEditor editor : validatorList) {
            if (editor instanceof XIpAddressEditor) {
                removeIpEditorListener(editor);
            } else {
                if (editor instanceof DefaultXEditor)
                    editor.removePropertyChangeListener(editor.getPropertyName(), this);
            }
        }
    }

    private void getAllValidators(Component[] cs) {
        for (Component c : cs) {
            // logger.debug("" + c);
            if (c instanceof XEditor && !validatorList.contains(c)) {
                validatorList.add((XEditor) c);
                continue;
            } else if (c instanceof JComponent) {
                JComponent jp = (JComponent) c;
                getAllValidators(jp.getComponents());
            }
            // else {
            // logger.debug("Unknown Components:" + c.getClass());
            // }
        }
    }

    /**
     * @param value
     */
    @SuppressWarnings("unchecked")
    private void removeIpEditorListener(XEditor value) {
        XIpAddressEditor<IpAddressValidator> ipAddressEditor = (XIpAddressEditor<IpAddressValidator>) value;

        String propertyName = ipAddressEditor.getPropertyName();
        List<String> list = ipSortMap.get(propertyName);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<String>();
            ipSortMap.put(propertyName, list);
        }
        ipAddressEditor.removePropertyChangeListener(propertyName, this);
        XTextIPSpace t1 = ipAddressEditor.getT1();
        list.remove(propertyName + t1.getIndex());
        t1.removePropertyChangeListener(propertyName + t1.getIndex(), this);
        XTextIPSpace t2 = ipAddressEditor.getT2();
        list.remove(propertyName + t2.getIndex());
        t2.removePropertyChangeListener(propertyName + t2.getIndex(), this);
        XTextIPSpace t3 = ipAddressEditor.getT3();
        list.remove(propertyName + t3.getIndex());
        t3.removePropertyChangeListener(propertyName + t3.getIndex(), this);
        XTextIPSpace t4 = ipAddressEditor.getT4();
        list.remove(propertyName + t4.getIndex());
        t4.removePropertyChangeListener(propertyName + t4.getIndex(), this);
    }

    /**
     * @param value
     */
    @SuppressWarnings("unchecked")
    private void registerIpEditorListener(XEditor value) {
        XIpAddressEditor<IpAddressValidator> ipAddressEditor = (XIpAddressEditor<IpAddressValidator>) value;
        String propertyName = ipAddressEditor.getPropertyName();
        List<String> list = ipSortMap.get(propertyName);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<String>();
            ipSortMap.put(propertyName, list);
        }
        ipAddressEditor.addPropertyChangeListener(propertyName, this);
        XTextIPSpace t1 = ipAddressEditor.getT1();
        list.add(propertyName + t1.getIndex());
        t1.addPropertyChangeListener(propertyName + t1.getIndex(), this);
        XTextIPSpace t2 = ipAddressEditor.getT2();
        list.add(propertyName + t2.getIndex());
        t2.addPropertyChangeListener(propertyName + t2.getIndex(), this);
        XTextIPSpace t3 = ipAddressEditor.getT3();
        list.add(propertyName + t3.getIndex());
        t3.addPropertyChangeListener(propertyName + t3.getIndex(), this);
        XTextIPSpace t4 = ipAddressEditor.getT4();
        list.add(propertyName + t4.getIndex());
        t4.addPropertyChangeListener(propertyName + t4.getIndex(), this);
    }

    private void processMsg(String propertyName, ValidateMessage message) {
        // if (message == null) {
        if (message != null) {
            if (!message.getMessageType().equals(ValidateMessage.MessageType.CLEAN))
                showMessage(message);
        } else {
            if (CollectionUtils.isEmpty(sortList)) {
                ValidateMessage validateMessage = new ValidateMessage();
                showMessage(validateMessage);
            }
            for (String name : sortList) {
                ValidateMessage validateMessage = messageMap.get(name);
                if (validateMessage != null) {
                    showMessage(validateMessage);
                    return;
                } else {
                    validateMessage = new ValidateMessage();
                    showMessage(validateMessage);
                    return;
                }
            }
        }
        // for (Editor editor : validatorList) {
        // if (editor instanceof IpAddressEditor) {
        // List<String> list = ipSortMap.get(editor.getPropertyName());
        // for (String string : list) {
        // message = messageMap.get(string);
        // if (message != null) {
        // showMessage(message);
        // return;
        // }
        // }
        // } else {
        // message = messageMap.get(editor.getPropertyName());
        // }
        // if (message != null) {
        // showMessage(message);
        // return;
        // } else {
        // if (!CollectionUtils.isEmpty(sortList)) {
        // for (String string : sortList) {
        // ValidateMessage validateMessage = messageMap.get(string);
        // if (validateMessage != null) {
        // showMessage(validateMessage);
        // return;
        // }
        // }
        // } else {
        // showMessage(new ValidateMessage());
        // }
        // }
        // }
        // } else {
        // showMessage(message);
        // }
    }

    /**
     * ��ʾ�Զ�У������Ϣ
     * 
     * @param message
     *            �Զ�У������Ϣ
     */
    public void showMessage(ValidateMessage message) {
        String messageType = message.getMessageType();
        if (StringUtils.isEmpty(messageType)) {
            setDescription(desc);
        } else if (messageType.equals(ValidateMessage.MessageType.WARNING)) {
            String msg = message.getMsg();
            showWarning(msg);
        } else if (messageType.equals(ValidateMessage.MessageType.ERROR)) {
            String msg = message.getMsg();
            showError(msg);
        } else if (messageType.equals(ValidateMessage.MessageType.CLEAN)) {
            String msg = message.getMsg();
            showError(msg);
        }
    }

    /**
     * ��ʼ�������������Ϣ�Ļ��棬����������Ϣ
     */
    public void init() {
        messageMap.clear();
        setDescription(desc);
    }
}
