/*
 * $Id: ClientController.java, 2009-2-24 上午09:29:33 aaron lee Exp $
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

/**
 * <p>
 * Title: ClientController
 * </p>
 * <p>
 * Description: 客户端MVC模式的控制类（controller）的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-24 上午09:29:33
 * modified [who date description]
 * check [who date description]
 */
public interface ClientController<T, V, M> {
    /**
     * 初始化界面和model,数据不应该在此方法，此方法一个界面应该只调用一次。
     * @param viewParams 界面初始化参数
     */
    void initView(Object... viewParams);

    /**
     * 初始化数据的方法，首先初始构建Model中的数据，并将model的数据更新到界面显示控件
     * @param objects 初始化的参数
     * @return 初始化是否成功，成功返回<code>true</code>，否则返回<code>false</code>
     */
    boolean initData(Object... objects);

    /**
     * 添加数据到Model并更新界面，单对象操作和多对象操作都使用该方法
     * @param datas 要添加的数据列表
     */
    void addDatas(List<T> datas);
    
    /**
     * 修改数据到Model并更新界面，单对象操作和多对象操作都使用该方法
     * @param datas 要修改的数据列表
     */
    void modifyDatas(List<T> datas);
    
    /**
     * 删除数据到Model并更新界面，单对象操作和多对象操作都使用该方法
     * @param datas 要删除的数据列表
     */
    void deleteDatas(List<T> datas);

    /**
     * 获取界面上的数据
     * @return 获取Model中的所有数据信息
     */
    List<T> getDatas();
    
    /**
     * 收集界面数据，将界面数据提交到Model
     * 
     */
    void collectData();
    
    /**
     * 关闭界面并清除Model中的数据缓存
     */
    void dispose();
    
    /**
     * 获取当前窗口
     * @return <code>Window</code> 当前窗口
     */
    Window getWindow();
    
    /**
     * 清除Model缓存的数据
     */
    void clearData();
    
    /**
     * 获取客户端ClientView
     * @return V 客户端界面
     */
    V getClientView();
}
