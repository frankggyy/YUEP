/*
 * $Id: OutputManager.java, 2009-2-26 下午03:38:13 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.output;

import javax.swing.JComponent;

/**
 * <p>
 * Title: OutputManager
 * </p>
 * <p>
 * Description:* 主界面最底下的输出域，主要用来显示两类内容
 * <ul>
 * <li>系统消息，主要由下向上，由后端向前端发送的消息
 * <li>操作消息，主要由上向下，由前端向后端发送的操作请求
 * </ul>
 * 实现要求如下：
 * 1、OutputManager中所定义的外部接口所代表的功能
 * 2、应该为Message和Operation这两大类设定不同的颜色（前景、背景）
 * 3、能够resize其大小
 * 4、在输出域上面弹出式菜单，支持：
 * <ul>
 * <li>Copy
 * <li>Select All
 * <li>Clear
 * <li>Save
 * <li>Lock
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-26 下午03:38:13
 * modified [who date description]
 * check [who date description]
 */
public interface OutputManager{
    String NAME = "OutputManager";
    /**
     * <pre>
     * 实际的Component，这个接口没有限定用户采用什么控件来实现，一般采用JTextArea，
     * 具体方式可以参考M2K
     * 对于消息的显示，有如下的要求：
     * 1、能够自动为所有的消息加上当前时间
     * 2、能够自动为不同类别的消息设定不同的显示样式，一般是前景、背景色；
     * 不建议采用字体的大小、粗细、斜正来区别
     * </pre>
     * @return
     */
    JComponent getView();
    /**
     * 添加一条上送的消息，之所以将消息类型设为宽泛的Object类型，也就是OutputManager可以对其进行解析
     * @param msg
     */
    void addSystemMessage( Object msg );
    /**
     * 添加一条下发的操作消息，之所以将类型设为宽泛的Object类型，也就是OutputManager可以对其进行解析
     * @param operation
     */
    void addOperationMessage( Object operation );
    /**
     * 清除当前显示的所有被选择的消息 
     */
    void clearSelected();
    /**
     * 清除当前显示的所有消息
     */
    void clear();
    /**
     * 设定消息框是否自动滚动
     * @param autoScroll
     */
    void setAutoScroll( boolean autoScroll );

    boolean isAutoScroll();
    /**
     * 设定最大的行数，一旦超过了这个行数，上面的消息就自动被丢弃
     * @param maxLines
     */
    void setMaxLines(int maxLines );
    /**
     * 得到输出域的最大行数
     * @return
     */
    int getMaxLines();
    /**
     * 得到输出域的高度
     * @param height
     */
    void setHeight( int height );
}
