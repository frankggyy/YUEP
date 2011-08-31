/*
 * $Id: ClientContentView.java, 2009-2-19 ����09:37:16 aaron lee Exp $
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
 * Description:�ͻ���MVCģʽ��չ���ࣨview���Ľӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-19 ����09:37:16
 * modified [who date description]
 * check [who date description]
 */
public interface ClientView<T> extends Observer {
    /**
     * ��ʼ����������
     */
    void initData(List<T> boList);
    
    /**
     * Controller�е���
     * �������ʼ���������õ�����
     * @param viewParams �����ʼ������
     */
    void setViewParams(Object... viewParams);

    /**
     * ���������Ϣ
     */
    void constructUi();
    
    /**
     * ���Listener
     * @param <V> ʵ��ClientView�ӿ�
     * @param <M> ʵ��ClientModel�ӿ�
     * @param controller ĳЩListener����Ҫʹ�õ�Controller
     */
    <V,M> void addListener(ClientController<T, V, M> controller);

    /**
     * �ռ���������
     * 
     * @return <code>List<T></code> ������Ϣ�б�
     */
    List<T> collectData();

    /**
     * ��ҳ��ı���
     * 
     * @return <code>String</code> ����
     */
    String getTitle();

    /**
     * ��ҳ��İ���ID��������Ҫ�����������Ķ�λ�����滺��
     * 
     * @return <code>String</code> ����ID
     */
    String getHelpId();

    /**
     * �ڶԻ����д�ʱ����������Ϊȱʡ��ť
     * 
     * @return JButton will be set default button in dialog
     */
    JButton getDefaultButton();

    /**
     * ����򿪺���Ĭ��Focus�Ŀؼ�
     * @return
     */
    JComponent getDefaultFocus();

    /**
     * ��ȡ����Ĵ�������
     * @return <code>Window</code> ����Ĵ�������
     */
    Window getWindow();

    /**
     * �رս���
     */
    void dispose();
    
    /**
     * ���½�����ʾ��������ݣ�����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ��ӵ������б�
     */
    void addDatas(List<T> datas);
    
    /**
     * ���½�����ʾ���޸����ݣ�����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫ�޸ĵ������б�
     */
    void modifyDatas(List<T> datas);
    
    /**
     * ���½�����ʾ��ɾ�����ݣ�����������Ͷ���������ʹ�ø÷���
     * @param datas Ҫɾ���������б�
     */
    void deleteDatas(List<T> datas);
}
