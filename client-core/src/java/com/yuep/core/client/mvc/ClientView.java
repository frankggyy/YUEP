/*
 * $Id: ClientContentView.java, 2009-2-19 上午09:37:16 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.awt.Window;
import java.util.List;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * <p>
 * Title: ClientContentView
 * </p>
 * <p>
 * Description:客户端MVC模式的展现类（view）的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-19 上午09:37:16
 * modified [who date description]
 * check [who date description]
 */
public interface ClientView<T> extends Observer {
    /**
     * 初始化界面数据
     */
    void initData(List<T> boList);
    
    /**
     * Controller中调用
     * 将界面初始化参数设置到界面
     * @param viewParams 界面初始化参数
     */
    void setViewParams(Object... viewParams);

    /**
     * 构造界面信息
     */
    void constructUi();
    
    /**
     * 添加Listener
     * @param <V> 实现ClientView接口
     * @param <M> 实现ClientModel接口
     * @param controller 某些Listener中需要使用的Controller
     */
    <V,M> void addListener(ClientController<T, V, M> controller);

    /**
     * 收集界面数据
     * 
     * @return <code>List<T></code> 界面信息列表
     */
    List<T> collectData();

    /**
     * 该页面的标题
     * 
     * @return <code>String</code> 标题
     */
    String getTitle();

    /**
     * 该页面的帮助ID，现在主要用来做帮助的定位，界面缓存
     * 
     * @return <code>String</code> 帮助ID
     */
    String getHelpId();

    /**
     * 在对话框中打开时，将被设置为缺省按钮
     * 
     * @return JButton will be set default button in dialog
     */
    JButton getDefaultButton();

    /**
     * 界面打开后获得默认Focus的控件
     * @return
     */
    JComponent getDefaultFocus();

    /**
     * 获取界面的窗口容器
     * @return <code>Window</code> 界面的窗口容器
     */
    Window getWindow();

    /**
     * 关闭界面
     */
    void dispose();
    
    /**
     * 更新界面显示，添加数据，单对象操作和多对象操作都使用该方法
     * @param datas 要添加的数据列表
     */
    void addDatas(List<T> datas);
    
    /**
     * 更新界面显示，修改数据，单对象操作和多对象操作都使用该方法
     * @param datas 要修改的数据列表
     */
    void modifyDatas(List<T> datas);
    
    /**
     * 更新界面显示，删除数据，单对象操作和多对象操作都使用该方法
     * @param datas 要删除的数据列表
     */
    void deleteDatas(List<T> datas);
}
