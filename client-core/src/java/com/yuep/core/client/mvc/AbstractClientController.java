/*
 * $Id: AbstractClientController.java, 2009-2-12 ����10:48:52 aaron lee Exp $
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

import javax.swing.SwingWorker;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: AbstractClientController
 * </p>
 * <p>
 * Description: ����Ŀͻ��˽��������
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-12 ����10:48:52
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractClientController<T extends Object, V extends AbstractClientView<T>, M extends AbstractClientModel<T>>
        implements ClientController<T, V, M> {
    /**
     * �ͻ�����ʾ����
     */
    protected V clientView;
    /**
     * �ͻ�����ʾ���������ģ��
     */
    protected M clientModel;
    /**
     * �Ƿ���ֵȴ���
     */
    protected boolean isWaiting = false;
    /**
     * �Ƿ��ں�̨�߳�ִ������ģ�ͼ���
     */
    protected boolean isInBackgroud = false;
    /**
     * ���ݳ�ʼ���Ƿ�ɹ�
     */
    protected boolean isSuccess = true;

    /**
     * ���췽��
     * @param viewClass �����Class
     * @param modelClass Model��Class
     */
    public AbstractClientController(Class<V> viewClass, Class<M> modelClass) {
        try {
            clientModel = modelClass.newInstance();
            clientView = viewClass.newInstance();
            initView();
        } catch (Exception e) {
            DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), e);
        }
    }
    
    public AbstractClientController(Class<V> viewClass, Class<M> modelClass,Object... viewParams) {
        try {
            clientModel = modelClass.newInstance();
            clientView = viewClass.newInstance();
            initView(viewParams);
        } catch (Exception e) {
            DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), e);
        }
    }

    /**
     * (non-Javadoc)
     * @see com.yotc.opview.framework.client.component.module.ClientController#initData(java.lang.Object[])
     */
    @Override
    public boolean initData(final Object... objects) {
        isSuccess = true;
        if (isWaiting) {
            final WaitingDialog waitingDialog = DialogUtils.showWaitingDialog(false, false, null, clientView
                    .getWindow());

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        clientModel.init(objects);
                    } catch (Exception e) {
                        isSuccess = false;
                        DialogUtils.showErrorExpandDialog(clientView.getWindow(), e);
                    } finally {
                        waitingDialog.closeWaitingDialog();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                    try {
                        List<T> model = clientModel.getModelDatas();
                        clientView.initData(model);
                    } catch (Exception e) {
                        isSuccess = false;
                        DialogUtils.showErrorExpandDialog(clientView.getWindow(), e);
                    } finally {
                        waitingDialog.closeWaitingDialog();
                        clientView.getWindow().toFront();
                    }
                }
            }.execute();

        } else {
            try {
                if (isInBackgroud) {
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            clientModel.init(objects);
                            return null;
                        }

                        @Override
                        protected void done() {
                            super.done();
                            List<T> model = clientModel.getModelDatas();
                            clientView.initData(model);
                        }
                    }.execute();
                } else {
                    clientModel.init(objects);
                    List<T> model = clientModel.getModelDatas();
                    clientView.initData(model);
                }
            } catch (Exception e) {
                isSuccess = false;
                DialogUtils.showErrorExpandDialog(clientView.getWindow(), e);
            }
        }
        return isSuccess;
    }

    /**
     * �Ƿ���ֵȴ��Ի���<code>true</code>
     * Ϊ�ڳ��ֵȴ��Ի���,������ֵȴ��Ի�����ôclientModel��init�����ں�̨�߳��м���
     * 
     * @param isWaiting
     */
    public void setWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
        if (isWaiting) {
            this.isInBackgroud = isWaiting;
        }
    }

    /**
     * clientModel��init�Ƿ���Ҫ�ں�̨�߳��м��أ�<code>true</code>Ϊ�ں�̨�߳��м���
     * ֻ�е��ȴ���û�г����ǿ��Ե��������Ƿ��ں�̨�߳��м���
     * 
     * @param isInBackgroud
     */
    public void setInBackgroud(boolean isInBackgroud) {
        if (!isWaiting) {
            this.isInBackgroud = isInBackgroud;
        }
    }
    
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#initView(java.lang.Object[])
     */
    @Override
    public void initView(Object... viewParams) {
        try {
            if(viewParams != null)
                clientView.setViewParams(viewParams);
            clientModel.addObserver(clientView);
            clientView.constructUi();
            clientView.addListener(this);
        } catch (Exception e) {
            DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), e);
        }
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
        collectData();
        List<T> modelDatas = clientModel.getModelDatas();
        return modelDatas;
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
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientController#colseWindow()
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
        if(collectData != null)
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
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#clearData()
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
