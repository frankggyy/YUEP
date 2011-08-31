/*
 * $Id: ClientModel.java, 2009-2-24 上午09:29:03 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.util.List;

/**
 * <p>
 * Title: ClientModel
 * </p>
 * <p>
 * Description:客户端MVC模式的数据模型类（model）的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-24 上午09:29:03
 * modified [who date description]
 * check [who date description]
 */
public interface ClientModel<T> {
    /**
     * 初始化Model中的数据，初始化成功后controller会获取初始化的数据，然后将数据更新到界面
     * @param objects 初始化的参数
     */
    void init(Object... objects);

    /**
     * 添加数据到Model，单对象操作和多对象操作都使用该方法
     * @param datas 要添加的数据列表
     */
    void addDatas(List<T> datas);
    
    /**
     * 修改数据到Model，单对象操作和多对象操作都使用该方法
     * @param datas 要修改的数据列表
     */
    void modifyDatas(List<T> datas);
    
    /**
     * 删除数据到Model，单对象操作和多对象操作都使用该方法
     * @param datas 要删除的数据列表
     */
    void deleteDatas(List<T> datas);

    /**
     * 获取Model中的所有数据信息
     * @return List<T> 所有数据信息
     */
    List<T> getModelDatas();
    
    /**
     * 清除Model中的缓存数据，在关闭窗口是调用
     */
    void clearData();
}
