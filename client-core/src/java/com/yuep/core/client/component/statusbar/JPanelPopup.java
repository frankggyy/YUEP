/*
 * $Id: JPanelPopup.java, 2009-3-5 上午09:30:47 aaron lee Exp $
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
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.Serializable;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * <p>
 * Title: JPanelPopup
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:30:47
 * modified [who date description]
 * check [who date description]
 */
public class JPanelPopup extends JPanel implements Serializable {

    private static final long serialVersionUID = 2910800669924248043L;

    int desiredLocationX;
    int desiredLocationY;
    Component invoker;

    public JPanelPopup(Component _invoker) {
        invoker = null;
        invoker = _invoker;
        setLayout(new BorderLayout());
        setDoubleBuffered(true);
        setOpaque(false);
    }

    public void paint(Graphics g) {
        if (invoker != null) {
            Point p = invoker.getLocationOnScreen();
            int x = (p.x + invoker.getWidth()) - getPreferredSize().width;
            int y = (p.y + invoker.getHeight() / 2) - getPreferredSize().height;
            setLocationOnScreen(x, y);
        }
        super.paint(g);
    }

    public void pack() {
        setSize(getPreferredSize());
    }

    public void show(Component invoker) {
        Container parent = null;
        if (invoker != null) {
            parent = invoker.getParent();
        }
        //Window parentWindow = null;
        Container p;
        for (p = parent; p != null; p = p.getParent()) {
            if (p instanceof JRootPane) {
                if (p.getParent() instanceof JInternalFrame) {
                    continue;
                }
                parent = ((JRootPane) p).getLayeredPane();
                for (p = parent.getParent(); p != null && !(p instanceof Window); p = p.getParent()) {
                }
                //parentWindow = (Window) p;
                break;
            }
            if (!(p instanceof Window)) {
                continue;
            }
            parent = p;
            //parentWindow = (Window) p;
            break;
        }

        Point point = convertScreenLocationToParent(parent, desiredLocationX, desiredLocationY);
        setLocation(point.x, point.y);
        if (parent instanceof JLayeredPane) {
            ((JLayeredPane) parent).add(this, JLayeredPane.POPUP_LAYER, 0);
        } else {
            parent.add(this);
        }
        parent.validate();
    }

    public void hide() {
        Container parent = getParent();
        Rectangle r = getBounds();
        if (parent != null) {
            parent.remove(this);
            parent.repaint(r.x, r.y, r.width, r.height);
        }
    }

    Point convertScreenLocationToParent(Container parent, int x, int y) {
        Window parentWindow = null;
        Container p = parent;
        do {
            if (p == null) {
                break;
            }
            if (p instanceof Window) {
                parentWindow = (Window) p;
                break;
            }
            p = p.getParent();
        } while (true);
        if (parentWindow != null) {
            Point point = new Point(x, y);
            SwingUtilities.convertPointFromScreen(point, parent);
            return point;
        } else {
            throw new Error("convertScreenLocationToParent: no window ancestor found");
        }
    }

    /**
     * 设置在屏幕中的位置
     * @param x
     *        x坐标
     * @param y
     *        y坐标
     */
    public void setLocationOnScreen(int x, int y) {
        Container parent = getParent();
        if (parent != null) {
            Point p = convertScreenLocationToParent(parent, x, y);
            setLocation(p.x, p.y);
        } else {
            desiredLocationX = x;
            desiredLocationY = y;
        }
    }
}
