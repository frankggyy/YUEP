/*
 * $Id: OutputManager.java, 2009-2-26 ����03:38:13 aaron lee Exp $
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
 * Description:* ����������µ��������Ҫ������ʾ��������
 * <ul>
 * <li>ϵͳ��Ϣ����Ҫ�������ϣ��ɺ����ǰ�˷��͵���Ϣ
 * <li>������Ϣ����Ҫ�������£���ǰ�����˷��͵Ĳ�������
 * </ul>
 * ʵ��Ҫ�����£�
 * 1��OutputManager����������ⲿ�ӿ�������Ĺ���
 * 2��Ӧ��ΪMessage��Operation���������趨��ͬ����ɫ��ǰ����������
 * 3���ܹ�resize���С
 * 4������������浯��ʽ�˵���֧�֣�
 * <ul>
 * <li>Copy
 * <li>Select All
 * <li>Clear
 * <li>Save
 * <li>Lock
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-26 ����03:38:13
 * modified [who date description]
 * check [who date description]
 */
public interface OutputManager{
    String NAME = "OutputManager";
    /**
     * <pre>
     * ʵ�ʵ�Component������ӿ�û���޶��û�����ʲô�ؼ���ʵ�֣�һ�����JTextArea��
     * ���巽ʽ���Բο�M2K
     * ������Ϣ����ʾ�������µ�Ҫ��
     * 1���ܹ��Զ�Ϊ���е���Ϣ���ϵ�ǰʱ��
     * 2���ܹ��Զ�Ϊ��ͬ������Ϣ�趨��ͬ����ʾ��ʽ��һ����ǰ��������ɫ��
     * �������������Ĵ�С����ϸ��б��������
     * </pre>
     * @return
     */
    JComponent getView();
    /**
     * ���һ�����͵���Ϣ��֮���Խ���Ϣ������Ϊ����Object���ͣ�Ҳ����OutputManager���Զ�����н���
     * @param msg
     */
    void addSystemMessage( Object msg );
    /**
     * ���һ���·��Ĳ�����Ϣ��֮���Խ�������Ϊ����Object���ͣ�Ҳ����OutputManager���Զ�����н���
     * @param operation
     */
    void addOperationMessage( Object operation );
    /**
     * �����ǰ��ʾ�����б�ѡ�����Ϣ 
     */
    void clearSelected();
    /**
     * �����ǰ��ʾ��������Ϣ
     */
    void clear();
    /**
     * �趨��Ϣ���Ƿ��Զ�����
     * @param autoScroll
     */
    void setAutoScroll( boolean autoScroll );

    boolean isAutoScroll();
    /**
     * �趨����������һ������������������������Ϣ���Զ�������
     * @param maxLines
     */
    void setMaxLines(int maxLines );
    /**
     * �õ��������������
     * @return
     */
    int getMaxLines();
    /**
     * �õ������ĸ߶�
     * @param height
     */
    void setHeight( int height );
}
