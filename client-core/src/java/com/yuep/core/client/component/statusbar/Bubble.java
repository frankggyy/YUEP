/*
 * $Id: Bubble.java, 2009-3-5 上午09:51:31 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * <p>
 * Title: Bubble
 * </p>
 * <p>
 * Description:状态栏上弹出的ToolTip
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 上午09:51:31
 * modified [who date description]
 * check [who date description]
 */
public class Bubble extends JComponent{
    
    private static final long serialVersionUID = 1792670305802019611L;
    
    private static final int x = 0;
    private static final int y = 0;
    private static final int dogear = 5;
    private static final int DefaultWidth = 90;
    private static final int DefaultHeight = 36;
    private int width;
    private int height;
    private static final int npoints = 11;
    private int xpoints[];
    private int ypoints[];
    private Polygon shape;
    //BubbleStatusBarItem parent;
    private String message;
    JTextArea textArea;
    Insets insets;
    private FontMetrics stringMeasure;

    /**
     * 构造函数
     * @param message
     */
    public Bubble(String message)
    {
        width = 90;
        height = 36;
        xpoints = new int[11];
        ypoints = new int[11];
        shape = null;
        this.message = null;
        textArea = new JTextArea();
        insets = new Insets(10, 13, 30, 13);
        stringMeasure = null;
        setLayout(null);
        setDoubleBuffered(true);
        textArea.setLocation(insets.left, insets.top);
        textArea.setEditable(false);
        add(textArea);
        setOpaque(false);
        this.message = message;
        resize();
        reShape();
    }

    /**
     * 
     */
    public Insets getInsets()
    {
        return insets;
    }

    /**
     * 重画ToolTip大小
     */
    private void resize()
    {
        String tipText = message;
        if(tipText == null || tipText.length() == 0)
        {
            this.width = 0;
            this.height = 0;
            return;
        }
        String lineSeparator = System.getProperty("line.separator");
        StringTokenizer stringTokenizer = new StringTokenizer(tipText, lineSeparator);
        int tokenCount = stringTokenizer.countTokens();
        String tokens[] = new String[tokenCount];
        for(int i = 0; i < tokens.length; i++)
        {
            tokens[i] = stringTokenizer.nextToken();
        }

        setFont(UIManager.getFont("Label.font"));
        int rowCount = 0;
        String delimiter = null;
        for(int i = 0; i < lineSeparator.length(); i++)
        {
            delimiter = String.valueOf(lineSeparator.charAt(i));
            for(int startIndex = 0; (startIndex = tipText.indexOf(delimiter, startIndex)) >= 0; startIndex++)
            {
                rowCount++;
            }

            if(tipText.endsWith(delimiter))
            {
                rowCount--;
            }
        }

        rowCount++;
        int height = stringMeasure.getHeight() * rowCount;
        int width = 0;
        for(int i = 0; i < tokens.length; i++)
        {
            if(width < stringMeasure.stringWidth(tokens[i]))
            {
                width = stringMeasure.stringWidth(tokens[i]);
            }
        }

        width = width >= 90 ? width : 90;
        height = height >= 36 ? height : 36;
        textArea.setSize(new Dimension(width + 2, height + 2));
        textArea.setText(tipText);
        setPreferredSize(new Dimension(width + insets.left + insets.right, height + insets.top + insets.bottom));
    }

    /**
     * 设置ToolTip字体
     */
    public void setFont(Font font)
    {
        if(font != null)
        {
            super.setFont(font);
            Image image = new BufferedImage(10, 10, 2);
            Graphics g = image.getGraphics();
            stringMeasure = g.getFontMetrics(font);
        }
    }
    /**
     * 绘制ToolTip形状
     */
    private void reShape()
    {
        width = getPreferredSize().width;
        height = getPreferredSize().height;
        xpoints[0] = 5;
        xpoints[1] = 0;
        xpoints[2] = 0;
        xpoints[3] = 5;
        xpoints[4] = width - 33;
        xpoints[5] = width - 15;
        xpoints[6] = width - 15;
        xpoints[7] = width - 1 - 5;
        xpoints[8] = width - 1;
        xpoints[9] = width - 1;
        xpoints[10] = width - 1 - 5;
        ypoints[0] = 0;
        ypoints[1] = 5;
        ypoints[2] = height - 19 - 5;
        ypoints[3] = height - 19;
        ypoints[4] = height - 19;
        ypoints[5] = height - 1;
        ypoints[6] = height - 19;
        ypoints[7] = height - 19;
        ypoints[8] = height - 19 - 5;
        ypoints[9] = 5;
        ypoints[10] = 0;
        shape = new Polygon(xpoints, ypoints, 11);
    }

    /**
     * 打印
     */
    public void paint(Graphics g)
    {
        g.setColor(UIManager.getColor("text"));
        g.fillPolygon(shape);
        g.setColor(Color.black);
        g.drawPolygon(shape);
        super.paint(g);
    }

    /**
     * 坐标x,y是否包含在当前ToolTip对象位置中
     */
    public boolean contains(int x, int y)
    {
        shape.translate(getX(), getY());
        if(shape.contains(x, y))
        {
            shape.translate(0 - getX(), 0 - getY());
            return true;
        } else
        {
            shape.translate(0 - getX(), 0 - getY());
            return false;
        }
    }

    /**
     * 坐标p是否包换在当前ToolTip位置中
     */
    public boolean contains(Point p)
    {
        return contains(p.x, p.y);
    }
}
