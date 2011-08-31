/*
 * $Id: DefaultStatusBarCell.java, 2009-3-5 上午09:41:04 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: DefaultStatusBarCell
 * </p>
 * <p>
 * Description:默认状态栏上的一块
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:41:04
 * modified [who date description]
 * check [who date description]
 */
public class DefaultStatusBarCell implements StatusBarCell {

    private JLabel label;

    private JPanel pane;

    private Status status;

    private JPanelPopup popupWindow;

    private Bubble tipBubble;

    private boolean enableAutoHide;

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        public void mouseClicked(MouseEvent evt) {
            hideBubble();
        }

    };

    private ActionPropertyChangeListener actionPropertyChangeListener;

    private Timer hideTimer;

    /**
     * constructor with status
     */
    public DefaultStatusBarCell(Status status) {
        this.status = status;
        init();
    }

    protected void init() {
        label = new StatusLabel();
        hideTimer = new Timer(5000, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                hideBubble();
            }

        });

        if (status.getAction() != null) {
            actionPropertyChangeListener = new ActionPropertyChangeListener();
            status.getAction().addPropertyChangeListener(actionPropertyChangeListener);
        }

        MouseAdapter labelMouseAdapter = new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && status.getAction() != null && status.getAction().isEnabled()) {

                    ActionEvent event = new ActionEvent(label, ActionEvent.ACTION_PERFORMED, "");
                    status.getAction().actionPerformed(event);
                }
            }
        };

        label.addMouseListener(labelMouseAdapter);
        paintStatus();
    }

    public JComponent getComponent() {
        // TODO add separator
        BorderLayout borderLayout = ClientCoreContext.getSwingFactory().getBorderLayout();
        pane = ClientCoreContext.getSwingFactory().getPanel(borderLayout);
        borderLayout.setVgap(0);
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(sep.getWidth(), 18));
        pane.add(sep, BorderLayout.WEST);
        pane.add(label, BorderLayout.CENTER);
        return pane;
    }

    /**
     * 获取状态栏对象
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 设置状态栏对象
     */
    public void setStatus(Status status) {

        this.status = status;
        refresh();
    }

    /**
     * 显示状态TopTip
     */
    public void showBubble(Object userObj) {
        if (!label.isShowing()) {
            return;
        }
        hideBubble();
        String message = String.valueOf(userObj);
        popupWindow = new JPanelPopup(label);
        popupWindow.setLayout(new BorderLayout());
        tipBubble = new Bubble(message);

        tipBubble.addMouseListener(mouseAdapter);
        tipBubble.textArea.addMouseListener(mouseAdapter);
        popupWindow.add(tipBubble, "Center");
        Point p = label.getLocationOnScreen();
        int x = (p.x + label.getWidth()) - popupWindow.getPreferredSize().width;
        int y = (p.y + label.getHeight() / 2) - popupWindow.getPreferredSize().height;
        popupWindow.setLocationOnScreen(x, y);
        popupWindow.pack();
        popupWindow.show(label);
        // this.setIcon(brightIcon);
        if (enableAutoHide) {
            hideTimer.start();
        }
    }

    /**
     * 隐藏状态TopTip
     */
    public void hideBubble() {
        hideTimer.stop();
        if (tipBubble != null) {
            tipBubble.removeMouseListener(mouseAdapter);
            tipBubble.textArea.removeMouseListener(mouseAdapter);
            tipBubble = null;
        }
        if (popupWindow != null) {
            popupWindow.setVisible(false);
            // getComponent().getRootPane().getLayeredPane().remove(popupWindow);
            popupWindow = null;
        }
    }

    /**
     * 刷新
     */
    public void refresh() {

        paintStatus();
    }

    protected void paintStatus() {

        Object statusValue = status.getUserObject();
        String text = String.valueOf(statusValue);
        if (label == null)
            return;
        label.setText(text);

        String tip = status.getToolTip();
        if (tip == null) {
            label.setToolTipText(text);
        } else {
            label.setToolTipText(tip);
        }
        label.setIcon(status.getIcon());

        // label.setBorder(BorderFactory.createLineBorder(Color.gray));

        Dimension dim = new Dimension(label.getPreferredSize().width, XStatusBar.DEFAULT_HEIGHT);
        if (!status.isExpand()) {
            if (status.getWidth() >= 0) {
                dim.width = status.getWidth();
            }
            label.setMinimumSize(dim);
            label.setPreferredSize(dim);
            label.setMaximumSize(dim);
        } else {
            label.setPreferredSize(dim);
        }
        label.setEnabled(status.getAction() == null || status.getAction().isEnabled());
    }

    public boolean isEnableAutoHide() {
        return enableAutoHide;
    }

    public void setEnableAutoHide(boolean enableAutoHide) {
        this.enableAutoHide = enableAutoHide;
    }

    static class StatusLabel extends JLabel {

        private static final long serialVersionUID = -4565796967142147361L;

        @Override
        public Point getToolTipLocation(MouseEvent event) {
            return XStatusBar.computeToolTipLocation(event.getPoint());
        }

    }

    class ActionPropertyChangeListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            if ("enabled".equals(evt.getPropertyName())) {
                label.setEnabled((Boolean) evt.getNewValue());
            }
        }

    }
}