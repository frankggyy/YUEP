/*
 * $Id: AbstractTabController.java, 2009-3-16 上午10:55:37 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.lang.ArrayUtils;

import twaver.Node;

import com.yuep.base.log.def.Logger;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: AbstractTabController
 * </p>
 * <p>
 * Description:导航控件中展示具体内容的Tab页Controller的抽象类
 * </p>
 * 
 * @author Victor,aaron
 * created 2009-3-16 上午10:55:37
 * @modified aaron lee 2010-3-30 将其实现<code>ClientController<T, V, M></code>接口
 * check [who date description]
 */
public abstract class AbstractTabController<T, V extends AbstractTabView<T>, M extends AbstractTabModel<T>>
        implements ClientController<T, V, M> {

    protected Logger logger;
    protected V clientView = null;
    protected M clientModel = null;
    private List<AbstractFilter> filters = new ArrayList<AbstractFilter>();
    private boolean modified = false;
    private Node selectNode = null;
    private boolean isInit = false;

    /**
     * 构造方法
     * @param v 视图
     * @param m 模型
     */
    public AbstractTabController(Class<V> v, Class<M> m) {
        logger=ClientCoreContext.getLogger();

        try {
            clientView = v.newInstance();
            clientModel = m.newInstance();
            clientModel.addObserver(clientView);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#initView(java.lang.Object[])
     */
    @Override
    public void initView(Object... viewParams) {
        try {
            if (!isInit) {
                if(viewParams != null)
                    clientView.setViewParams(viewParams);
                clientView.constructUi();
                clientView.addListener(this);
                clientView.initPropertyChangeListener();
                initFilters(filters);
                isInit = true;
            }
        } catch (Exception e) {
            logger.debug(e.getLocalizedMessage(), e);
            DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), e);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#initData(java.lang.Object[])
     */
    public boolean initData(Object... objects) {
        clientModel.init(objects);
        // 保存按钮默认设为不可用
        JButton[] sensitiveButtons = clientView.getSensitiveButtons();
        if (!ArrayUtils.isEmpty(sensitiveButtons)) {
            for (JButton sensitiveButton : sensitiveButtons) {
                sensitiveButton.setEnabled(clientView.isModified());
            }
        }
        List<T> modelDatas = clientModel.getModelDatas();
        clientView.initData(modelDatas);
        return true;
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.ClientController#addDatas(java.util.List)
     */
    @Override
    public void addDatas(List<T> datas) {
        clientModel.addDatas(datas);
        clientView.addDatas(datas);
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.ClientController#deleteDatas(java.util.List)
     */
    @Override
    public void deleteDatas(List<T> datas) {
        clientModel.deleteDatas(datas);
        clientView.deleteDatas(datas);
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.ClientController#getDatas()
     */
    @Override
    public List<T> getDatas() {
        List<T> collectData = clientView.collectData();
        return collectData;
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.ClientController#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<T> datas) {
        clientModel.modifyDatas(datas);
        clientView.modifyDatas(datas);
    }

    /**
     * 过滤器
     * 
     * @return
     */
    public final List<AbstractFilter> getFilters() {
        return filters;
    }

    /**
     * 初始化本页面的过滤器，用户导航设备过滤使用
     * 
     * @param filterList
     */
    protected void initFilters(List<AbstractFilter> filterList){
        
    }

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * 内容修改后必须调用此方法，以通知框架进行判断
     * 
     * @param modified
     *            the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * @return 国际化获取接口封装
     */
    protected String getString(String key, Object... values) {
        return ClientCoreContext.getString(key, values);
    }

    /**
     * 对界面的校验，如果检验不通过需要抛出一个ValidInputException.
     * 
     * @throws ValidInputException
     */
    protected void verify() {// throws ValidInputException {
    }

    /**
     * @return the selectNode
     */
    public Node getSelectNode() {
        return selectNode;
    }

    /**
     * @param selectNode
     *            the selectNode to set
     */
    public void setSelectNode(Node selectNode) {
        this.selectNode = selectNode;
        clientView.setSelectNode(selectNode);
        clientModel.setSelectNode(selectNode);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#dispose()
     */
    @Override
    public void dispose() {
        clientView.dispose();
        clientModel.clearData();
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#collectData()
     */
    @Override
    public void collectData() {
        List<T> collectData = clientView.collectData();
        List<T> modelDatas = clientModel.getModelDatas();
        modelDatas.clear();
        modelDatas.addAll(collectData);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#getWindow()
     */
    @Override
    public Window getWindow() {
        return clientView.getWindow();
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#clearData()
     */
    @Override
    public void clearData() {
        clientModel.clearData();
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#getClientView()
     */
    @Override
    public V getClientView() {
        return clientView;
    }
}
