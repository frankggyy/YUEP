/*
 * $Id: ToolBarInterpreterImpl.java, 2009-2-18 下午02:23:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar.interpreter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.yuep.base.resource.ResourceFactory;
import com.yuep.base.xml.XmlFileReader;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBar;
import com.yuep.core.client.component.toolbar.interpreter.xmldao.XToolBarButton;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: ToolBarInterpreterImpl
 * </p>
 * <p>
 * Description: 根据配置文件解析toolbar
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 下午02:23:36
 * modified [who date description]
 * check [who date description]
 */
public class ToolBarInterpreterImpl implements ToolBarInterpreter {

    /**
     * 菜单文件的file path for <code>filePath</code>
     */
    private String FILE_PATH = "toolbar/";

    /**
     * 工具栏文件的名字 for <code>menuFileName</code>
     */
    private String DEFAULT_FILE_NAME = "client-toolbar";

    public ToolBarInterpreterImpl() {
    }

    List<XToolBar> toolBars = new ArrayList<XToolBar>();
    
    Map<XToolBarButton, String> toolBarButtonMap = new HashMap<XToolBarButton, String>();

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.toolbar.interpreter.ToolBarInterpreter#getToolBar()
     */
    @Override
    public JButton getToolBarButton(XToolBarButton toolBarButton) {
        ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
        String moduleName = toolBarButtonMap.get(toolBarButton);
        String actionClazz = toolBarButton.getClazz();
        String iconName = toolBarButton.getIcon();
        Icon icon = resourceFactory.getIcon(moduleName,iconName);
        JButton button = new JButton(icon);
        String rolloverIconName = toolBarButton.getRolloverIcon();
        Icon rolloverIcon = resourceFactory.getIcon(moduleName,rolloverIconName);
        button.setRolloverIcon(rolloverIcon);
        if (!StringUtils.isEmpty(actionClazz)) {
            try {
                // 根据action class name获得Action Class
                Class<?> clazz = Class.forName(actionClazz);

                // 通过Action的Class生成构造器，
                // 构造器包含五个参数，分别是是否支持多选、设备的OID、产生菜单式所选择的对象数组、acion所需要的参数（上下文）、action所需参数的类型的class路径
                Constructor<? extends AbstractXAction> constructor = (Constructor<? extends AbstractXAction>) clazz
                        .getConstructor(String.class);
                AbstractXAction action = constructor.newInstance(getClazzSimpleName(actionClazz));
                button.addActionListener(action);
            } catch (Exception e) {
                System.err.println("ToolBarInterpreterImpl.getToolBarButton Aaction class interpreter error. class name is " + actionClazz+",ex="+e.getClass().getSimpleName());
            }
        }
        String toolTip = toolBarButton.getToolTip();
        String toolTipStr = ClientCoreContext.getString(toolTip);
        button.setToolTipText(toolTipStr);
        return button;
    }

    /**
     * @see com.yuep.core.client.component.toolbar.interpreter.ToolBarInterpreter#getXToolBars()
     */
    @Override
    public List<XToolBar> getXToolBars() {
        return toolBars;
    }

    /**
     * @see com.yuep.core.client.component.toolbar.interpreter.ToolBarInterpreter#loadMainMenuFiles(java.lang.String[])
     */
    @Override
    public void loadMainMenuFiles(String... modules) {
        if (ArrayUtils.isEmpty(modules))
            return;
        for (String module : modules) {
            Object obj = null;
            try {
                ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
                URL mappingFile = resourceFactory.getFile(resourceFactory.getCoreModuleName(), FILE_PATH,
                        DEFAULT_FILE_NAME + "-mapping.xml");
                URL dataFile = resourceFactory.getFile(module, FILE_PATH, DEFAULT_FILE_NAME + "-data.xml");
                if (mappingFile == null || dataFile == null)
                    continue;
                obj = XmlFileReader.getXmlConfig(mappingFile.openStream(), dataFile.openStream());
            } catch (IOException e) {
                DialogUtils.showErrorExpandDialog(null, e);
            }
            Assert.notNull(obj);
            XToolBar xtoolBar = (XToolBar) obj;
            List<XToolBarButton> toolBarButtons = xtoolBar.getToolBarButtons();
            for (XToolBarButton toolBarButton : toolBarButtons) {
                toolBarButtonMap.put(toolBarButton, module);
            }
            toolBars.add(xtoolBar);
        }
    }
    
    private String getClazzSimpleName(String clazz) {
        if (StringUtils.isEmpty(clazz))
            return "";
        int lastIndexOf = clazz.lastIndexOf(".");
        String clazzSimpleName = clazz.substring(lastIndexOf + 1, clazz.length());
        return clazzSimpleName;
    }

}
