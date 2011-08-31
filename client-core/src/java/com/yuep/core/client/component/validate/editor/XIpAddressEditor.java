/*
 * $Id: XIpAddressEditor.java, 2009-3-16 下午08:33:06 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeSupport;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.validate.validator.Validator;

/**
 * <p>
 * Title: XIpAddressEditor
 * </p>
 * <p>
 * Description:IP地址自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-16 下午08:33:06
 * modified [who date description]
 * check [who date description]
 */
public class XIpAddressEditor<IpAddressValidator> extends DefaultXEditor<Validator> {

    private static final long serialVersionUID = -887061973339209948L;

    private String st1 = "";

    private String st2 = "";

    private String st3 = "";

    private String st4 = "";

    private XTextIPSpace t1;

    private XTextIPSpace t2;

    private XTextIPSpace t3;

    private XTextIPSpace t4;

    final private static int RADII = 2;

    final private static int SPACEINTERVAL = 2;

    final private static int SEPARATOR = 3;

    private Color DefaultColor_Selected = Color.BLUE;

    private Color DefaultColor_NoSelected = Color.black;

    private JLabel label1;

    private JLabel label2;

    private JLabel label3;

    /**
     * constructor
     * 
     * @param attributeName
     *            属性名
     * @param isRequire
     * @param validator
     */
    public XIpAddressEditor() {
        this(false);
    }

    /**
     * constructor
     * 
     * @param isSelected
     * @param attributeName
     *            属性名
     * @param isRequire
     * @param validator
     */
    public XIpAddressEditor(boolean isSelected) {
        this(isSelected, "");
    }

    /**
     * constructor
     * 
     * @param ipAddress
     */
    public XIpAddressEditor(String ipAddress) {
        this(false, ipAddress);
    }

    /**
     * constructor
     * 
     * @param isSelected
     * @param ipAddress
     */
    public XIpAddressEditor(boolean isSelected, String ipAddress) {
        this(isSelected, ipAddress, null, null);
    }

    /**
     * constructor
     * 
     * @param isSelected
     * @param colorSelect
     * @param colorNoSelect
     */
    public XIpAddressEditor(boolean isSelected, Color colorSelect, Color colorNoSelect, String attributeName,
            boolean isRequire, Validator validator) {
        this(isSelected, "", colorSelect, colorNoSelect);
    }

    /**
     * constructor
     * 
     * @param isSelected
     * @param ipAddress
     * @param colorSelect
     * @param colorNoSelect
     */
    public XIpAddressEditor(boolean isSelected, String ipAddress, Color colorSelect, Color colorNoSelect) {
        super();
        propertyChangeSupport = new PropertyChangeSupport(this);
        t1 = new XTextIPSpace(true, "1", this);
        t2 = new XTextIPSpace(true, "2", this);
        t3 = new XTextIPSpace(true, "3", this);
        t4 = new XTextIPSpace(true, "4", this);
        label1 = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator("."));
        label2 = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator("."));
        label3 = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator("."));
        analyzeStr(ipAddress);
        setChildComponent(false);
        setLayout(null);
        changeChildComponent();
        t1.setPrevNextComponent(null, t2);
        t2.setPrevNextComponent(t1, t3);
        t3.setPrevNextComponent(t2, t4);
        t4.setPrevNextComponent(t3, null);
        // Dimension preferredSize = t4.getPreferredSize();
        add(t1);
        add(label1);
        add(t2);
        add(label2);
        add(t3);
        add(label3);
        add(t4);
        setSize(getPreferredSize());
        addComponentListener(new ComponentListener() {

            public void componentResized(ComponentEvent e) {
                changeChildComponent();
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });
        setChildComponent(true);
        if (colorSelect != null)
            DefaultColor_Selected = colorSelect;
        if (colorNoSelect != null)
            DefaultColor_NoSelected = colorNoSelect;
        setSelectEdge(isSelected, DefaultColor_Selected, DefaultColor_NoSelected);
        // t1.gotoComponent(t1);
    }

    public XTextIPSpace getT1() {
        return t1;
    }

    public XTextIPSpace getT2() {
        return t2;
    }

    public XTextIPSpace getT3() {
        return t3;
    }

    public XTextIPSpace getT4() {
        return t4;
    }

    /**
     * 第一个输入框获取鼠标焦点
     */
    public void requestFocusFirstIpSpace() {
        t1.requestFocus();
    }

    /**
     * @param listener
     */
    public void addFirstIpSpaceMouseListener(MouseListener listener) {
        t1.addMouseListener(listener);
    }

    /**
     * @param listener
     */
    public void addFirstIpSpaceFocusListener(FocusListener listener) {
        t1.addFocusListener(listener);
    }

    public void addLastIpSpaceFocusListener(FocusListener listener) {
        t4.addFocusListener(listener);
    }

    public XTextIPSpace getFirstIpSpace() {
        return t1;
    }

    /**
     * 最后一个输入框获取鼠标焦点
     */
    public void requestFocusLastIpSpace() {
        t4.requestFocus();
    }

    /**
     * 输入框不能编辑
     */
    public void setTextFieldEditable(boolean b) {
        super.setEditable(b);

        t1.setEditable(b);
        t2.setEditable(b);
        t3.setEditable(b);
        t4.setEditable(b);
    }

    public void setTextFieldBackground(Color c) {
        super.setBackground(c);

        t1.setBackground(c);
        t2.setBackground(c);
        t3.setBackground(c);
        t4.setBackground(c);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        t1.setEnabled(enabled);
        t2.setEnabled(enabled);
        t3.setEnabled(enabled);
        t4.setEnabled(enabled);
    }

    // 设置选中或未选中的颜色
    public void setSelected(Color color) {
        DefaultColor_Selected = color;
    }

    public void setSelected() {
        DefaultColor_Selected = Color.blue;
    }

    public void setNoSelected(Color color) {
        DefaultColor_NoSelected = color;
    }

    public void setNoSelected() {
        DefaultColor_NoSelected = Color.black;
    }

    public void setSelectEdge(boolean isSelected, Color selected, Color noSelected) {
        Border border;
        if (isSelected)
            border = BorderFactory.createLineBorder(selected.brighter(), 1);
        else
            border = BorderFactory.createLineBorder(noSelected, 1);
        setBorder(border);
    }

    public Dimension getPreferredSize() {
        // FontMetrics fm = getGraphics().getFontMetrics();
        // return new Dimension(fm.charWidth('8') * 3 + 5, fm.getHeight() + 4);
        Dimension dim = new Dimension(40, 23);
        return dim;
    }

    private void setTextIPField() {
        t1.setText(st1);
        t2.setText(st2);
        t3.setText(st3);
        t4.setText(st4);
    }

    /**
     * 重载setText(String text).
     */
    public void setText(String text) {
        st1 = "";
        st2 = "";
        st3 = "";
        st4 = "";
        analyzeStr(text);
        setTextIPField();
    }

    /**
     * 设置显示的IP.
     * 
     * @param ip
     *            所显示的IP(字符串形式)
     */
    public void setIP(String ip) {
        setText(ip);
    }

    /**
     * 允许不输入信息，不输入返回...
     * 
     * @return
     */
    public String getUserInputIpAllowEmpty() {
        String result = availIp();
        return result;
    }

    /**
     * 得到Field内的IP.出错提示：输入的IP地址不正确，请重新输入！
     * 
     * @return 返回IP(字符串形式)**.**.**.**
     */
    public String getUserInputIp() {
        // return getUserInputIpWithErrorMessage(TopoI18nConst.ERR_IP_ADDR);
        return "";
    }

    /**
     * 设置Field内的IP值.
     * 
     * @param ip
     *            IP的值
     */
    public void setIPValue(int ip) {
        // TODO
    }

    /**
     * 得到IP的值.
     * 
     * @return 返回IP的值
     */
    public int getIPValue() {
        return isIPaddress(getUserInputIp());
    }

    /**
     * 将显示的IP转换成标准IP,显示的IP中存在' '.
     * 
     * @param ip
     *            显示的IP
     * @return 标准的IP
     */
    public String availIp() {
        String str1 = t1.getText().trim();
        String str2 = t2.getText().trim();
        String str3 = t3.getText().trim();
        String str4 = t4.getText().trim();
        String[] strTemp = { trimPrefixZero(str1), trimPrefixZero(str2), trimPrefixZero(str3), trimPrefixZero(str4) };
        StringBuffer result = new StringBuffer();
        for (int i = 0, len = strTemp.length; i < len; i++) {
            result.append(strTemp[i]);
            if (i != len - 1)
                result.append(".");
        }
        return result.toString();
    }

    private String trimPrefixZero(String str) {
        if (str.length() == 2) {
            if (str.startsWith("0")) {
                return str.substring(1);
            }
        }
        if (str.length() == 3) {
            if (str.startsWith("0")) {
                str = str.substring(1);
            }
            if (str.startsWith("0")) {
                return str.substring(1);
            }
        }
        return str;
    }

    /**
     * 判断IP地址的正确性.
     * 
     * @param ipAddress
     *            IP字符串
     * @return 如果IP有效返回IP的值 否则返回-1
     */
    public static int isIPaddress(String ipAddress) {
        int pointNum = 0, numberNum = 0;
        int numberVal = 0;
        int len = ipAddress.length();
        int m = 0;
        int IPVal = 0;
        char tempchar;
        while (m < len) {
            tempchar = ipAddress.charAt(m);
            if ((tempchar) == '.' || Character.isDigit(tempchar)) {
                m++;
                if (('0' <= tempchar) && (tempchar <= '9')) {
                    if (numberNum > 2)
                        return -1;
                    numberVal *= 10;
                    numberVal += tempchar - '0';
                    if (numberVal > 255)
                        return -1;
                    numberNum++;
                } else {
                    if (numberNum == 0)
                        return -1;
                    if (pointNum == 3)
                        return -1;
                    pointNum++;
                    IPVal *= 256;
                    IPVal += numberVal;
                    numberNum = 0;
                    numberVal = 0;
                }
            } else
                break;// 如果不为.或者数字，那么break，这样也可以用来判断非IP地址控件生成的IP地址是否合法，比如1.1.1.-1,by
            // pl
        }
        if (m != len || pointNum != 3 || numberNum == 0)
            return -1;
        pointNum++;
        IPVal *= 256;
        IPVal += numberVal;
        return IPVal;
    }

    private void analyzeStr(String str) {
        try {
            StringTokenizer st = new StringTokenizer(str, ".");
            String tk = "";
            int i = 0;
            while (st.hasMoreTokens() && i <= 3) {
                i++;
                tk = st.nextToken();
                if (!tk.equals("")) {
                    switch (i) {
                    case 1:
                        st1 = tk;
                        break;
                    case 2:
                        st2 = tk;
                        break;
                    case 3:
                        st3 = tk;
                        break;
                    case 4:
                        st4 = tk;
                        break;
                    default:
                        break;
                    }
                }
                tk = "";
            }
        } catch (Exception ex) {
            st1 = "";
            st2 = "";
            st3 = "";
            st4 = "";
        }
    }

    private void changeChildComponent() {
        Dimension sz = getSize();
        int spaceWidth = sz.width / 4 - SEPARATOR;
        int spaceHeight = sz.height - 2 * SPACEINTERVAL;
        // int r = Math.min(sz.height,sz.width)/6;
        int r = RADII;
        t1.setBounds(0 + SPACEINTERVAL, 0 + SPACEINTERVAL, spaceWidth - r, spaceHeight);
        label1.setBounds(spaceWidth + r, 0 + SPACEINTERVAL, SEPARATOR, spaceHeight);
        t2.setBounds(spaceWidth + r + SEPARATOR, 0 + SPACEINTERVAL, spaceWidth - r, spaceHeight);
        label2.setBounds(2 * spaceWidth + r + SEPARATOR, 0 + SPACEINTERVAL, SEPARATOR, spaceHeight);
        t3.setBounds(2 * spaceWidth + 2 * SEPARATOR + r, 0 + SPACEINTERVAL, spaceWidth - r, spaceHeight);
        label3.setBounds(3 * spaceWidth + 2 * SEPARATOR + r, 0 + SPACEINTERVAL, SEPARATOR, spaceHeight);
        t4
                .setBounds(3 * spaceWidth + 3 * SEPARATOR + r, 0 + SPACEINTERVAL, spaceWidth - r - SPACEINTERVAL,
                        spaceHeight);
        repaint();
    }

    private void setChildComponent(boolean isVisible) {
        t1.setVisible(isVisible);
        t2.setVisible(isVisible);
        t3.setVisible(isVisible);
        t4.setVisible(isVisible);
        repaint();
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        Dimension sz = getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, sz.width, sz.height);
        int spaceWidth = sz.width / 4;
        int spaceHeight = sz.height;
        // int r = Math.min(sz.height,sz.width)/6;
        int r = RADII;
        g.setColor(Color.black);
        for (int i = 1; i <= 3; i++) {
            g.fillOval(i * spaceWidth, spaceHeight - r, r, r);
        }
        // super.paintComponents(g);
    }

    /**
     * 如果没有输入或者输入合法则返回true，否则返回false
     * 
     * @return
     */
    public boolean isIpValidOrNull() {
        String inputIp = availIp();
        if (inputIp != null && !inputIp.equals("...") && isIPaddress(inputIp) == -1)
            return false;
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.text.JTextComponent#getText()
     */
    @Override
    public String getText() {
        String ip1 = t1.getText();
        String ip2 = t2.getText();
        String ip3 = t3.getText();
        String ip4 = t4.getText();

        if (StringUtils.isEmpty(ip1) || StringUtils.isEmpty(ip2) || StringUtils.isEmpty(ip3)
                || StringUtils.isEmpty(ip4)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(ip1).append(".").append(ip2).append(".").append(ip3).append(".").append(ip4);
        return builder.toString();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.AbstractXEditor#isModified()
     */
    @Override
    public boolean isModified() {
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.AbstractXEditor#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        t1.setDescription(description);
        t2.setDescription(description);
        t3.setDescription(description);
        t4.setDescription(description);
    }

    /**
     * 清除border
     */
    public void clearBorder() {
        Border emptyBorder = ClientCoreContext.getSwingFactory().getEmptyBorder(0, 0, 0, 0);
        t1.setBorder(emptyBorder);
        t2.setBorder(emptyBorder);
        t3.setBorder(emptyBorder);
        t4.setBorder(emptyBorder);
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.DefaultXEditor#setPropertyName(java.lang.String)
     */
    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        t1.setPropertyName(propertyName);
        t2.setPropertyName(propertyName);
        t3.setPropertyName(propertyName);
        t4.setPropertyName(propertyName);
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.DefaultXEditor#setRequire(boolean)
     */
    @Override
    public void setRequire(boolean isRequire) {
        super.setRequire(isRequire);
        t1.setRequire(isRequire);
        t2.setRequire(isRequire);
        t3.setRequire(isRequire);
        t4.setRequire(isRequire);
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.DefaultXEditor#setValidator(com.yuep.core.client.component.validate.validator.Validator)
     */
    @Override
    public void setValidator(Validator validator) {
        super.setValidator(validator);
        t1.setValidator(validator);
        t2.setValidator(validator);
        t3.setValidator(validator);
        t4.setValidator(validator);
    }
}