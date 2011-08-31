/*
 * $Id: MenuInterpreter.java, 2009-4-8 ����05:38:29 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.interpreter;

import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.yuep.core.client.component.menu.interpreter.xmldao.XMenu;

/**
 * <p>
 * Title: MenuInterpreter
 * </p>
 * <p>
 * Description:���ò˵�������
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-8 ����05:38:29
 * modified [who date description]
 * check [who date description]
 */
public interface MenuInterpreter {

    /**
     * ���ݲ˵����������Ҫ�Ĳ˵�
     * 
     * @param menuName
     *            �˵���
     */
    public JMenu getMenu(String menuName);

    /**
     * @param menuName
     *            �˵���
     * @param selectedButtonTexts
     *            ��ѡ�в˵���text
     * @return
     */
    public JMenu getMenu(String menuName, String[] selectedButtonTexts);

    /**
     * ���ݲ˵����Ͳ����������Ҫ���Ҽ��˵�
     * 
     * @param menuName
     *            �˵���
     * @param paramObjs
     *            ����
     * @return JPopupMenu ����Ҫ�Ҽ��Ĳ˵�
     */
    public JPopupMenu getPopupMenu(String menuName);

    /**
     * ���ݲ˵����Ͳ����������Ҫ���Ҽ��˵�
     * 
     * @param menuName
     *            �˵���
     * @param paramObjs
     *            ����
     * @return JPopupMenu ����Ҫ�Ҽ��Ĳ˵�
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects);

    /**
     * @param menuName
     *            �˵���
     * @param paramObjs
     *            ����
     * @param selectObjects
     * @param judgeSelectedObject
     *            �Ƿ����ѡ��ͬ���Ͷ���
     * @return JPopupMenu ����Ҫ�Ҽ��Ĳ˵�
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            boolean judgeSelectedObject);

    /**
     * ���ݲ˵����Ͳ����������Ҫ���Ҽ��˵�
     * 
     * @param menuName
     *            �˵���
     * @param paramObjs
     *            ����
     * @param selectedButtonTexts
     *            ��ǰ��ѡ�е�radioButton��text
     * @return JPopupMenu ����Ҫ�Ҽ��Ĳ˵�
     */
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            String[] selectedButtonTexts);
    
    Map<String, XMenu> getXmenuMap();

    /**
     * ����menu�����ļ�
     * @param fileNames Ҫ���صĲ˵��ļ���
     */
    public void loadMenuFiles(String... fileNames);
    
    /**
     * �������˵������˵����ļ����̶�Ϊ"main-menu-data.xml"
     * @param modules Ҫ������˵���ģ��
     */
    public void loadMainMenuFiles(String... modules);
}
