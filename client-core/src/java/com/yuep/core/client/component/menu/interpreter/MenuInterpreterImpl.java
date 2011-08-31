/*
 * $Id: MenuInterpreterImpl.java, 2009-4-8 下午05:38:45 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.interpreter;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import twaver.Node;

import com.yuep.base.resource.ResourceFactory;
import com.yuep.base.xml.XmlFileReader;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.component.menu.interpreter.xmldao.XMenu;
import com.yuep.core.client.component.menu.interpreter.xmldao.XMenuItem;
import com.yuep.core.client.component.menu.interpreter.xmldao.XMenus;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: MenuInterpreterImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-8 下午05:38:45
 * modified [who date description]
 * check [who date description]
 */
public class MenuInterpreterImpl implements MenuInterpreter {

    /**
     * 菜单mapping文件的file path for <code>filePath</code>
     */
    private static final String FILE_PATH = "menu/";
    /**
     * 模块名
     */
    private String module;

    private Map<String, XMenu> xmenuMap = new ConcurrentHashMap<String, XMenu>();

    private Map<String, ButtonGroup> groupMap;

    private String[] selectedButtonTexts;

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.interpreter.MenuInterpreter#getMenu(java.lang.String)
     */
    @Override
    public JMenu getMenu(String menuName) {
        return getMenu(menuName, null);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.interpreter.MenuInterpreter#getMenu(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public JMenu getMenu(String menuName, String[] selectedButtonTexts) {
        this.selectedButtonTexts = selectedButtonTexts;
        return (JMenu) interpreterMenu(menuName, null, null, true);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.interpreter.MenuInterpreter#getPopupMenu(java.lang.String)
     */
    @Override
    public JPopupMenu getPopupMenu(String menuName) {
        return getPopupMenu(menuName, null, null);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.interpreter.MenuInterpreter#getPopupMenu(java.lang.String,
     *      java.lang.Object[], java.lang.Object[])
     */
    @Override
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects) {
        return getPopupMenu(menuName, paramObjs, selectObjects, null);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.interpreter.MenuInterpreter#getPopupMenu(java.lang.String,
     *      java.lang.Object[], java.lang.Object[], java.lang.String[])
     */
    @Override
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            String[] selectedButtonTexts) {
        this.selectedButtonTexts = selectedButtonTexts;
        return (JPopupMenu) interpreterMenu(menuName, paramObjs, selectObjects, true);
    }

    @Override
    public JPopupMenu getPopupMenu(String menuName, Object[] paramObjs, Object[] selectObjects,
            boolean judgeSelectedObject) {
        return (JPopupMenu) interpreterMenu(menuName, paramObjs, selectObjects, judgeSelectedObject);
    }

    /**
     * constructor，初始化加载客户端配置的action信息，将信息缓存
     */
    public MenuInterpreterImpl(String module) {
        this.module = module;
    }

    List<XMenu> mainMenus = new ArrayList<XMenu>();
    public void loadMenuFiles(String... fileNames) {
        if (ArrayUtils.isEmpty(fileNames))
            return;
        for (String fileName : fileNames) {
            Object obj = null;
            try {
                ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
                
                InputStream mappingFile = resourceFactory.getFile(resourceFactory.getCoreModuleName(), FILE_PATH, "client-menu-mapping.xml")
                        .openStream();
                InputStream dataFile = resourceFactory.getFile(module, FILE_PATH, fileName).openStream();
                obj = XmlFileReader.getXmlConfig(mappingFile, dataFile);
            } catch (IOException e) {
                DialogUtils.showErrorExpandDialog(null, e);
            }
            Assert.notNull(obj);
            XMenus xmenus = (XMenus) obj;
            List<XMenu> menus = xmenus.getMenus();
            if(CollectionUtils.isEmpty(menus))
                continue;
            for (XMenu menu : menus) {
                xmenuMap.put(menu.getName(), menu);
            }
        }
    }

    public void loadMainMenuFiles(String... modules) {
        if (ArrayUtils.isEmpty(modules))
            return;
        for (String module : modules) {
            Object obj = null;
            try {
                ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
                URL mappingFile = resourceFactory.getFile(resourceFactory.getCoreModuleName(), FILE_PATH, "client-menu-mapping.xml");
                URL dataFile = resourceFactory.getFile(module, FILE_PATH, "main-menu-data.xml");
                if(mappingFile == null || dataFile == null)
                    continue;
                obj = XmlFileReader.getXmlConfig(mappingFile.openStream(), dataFile.openStream());
            } catch (IOException e) {
                DialogUtils.showErrorExpandDialog(null, e);
            }
            Assert.notNull(obj);
            XMenus xmenus = (XMenus) obj;
            List<XMenu> menus = xmenus.getMenus();
            if(CollectionUtils.isEmpty(menus))
                continue;
            for (XMenu menu : menus) {
                String name = menu.getName();
                XMenu xmenu = xmenuMap.get(name);
                if(xmenu == null){
                    mainMenus.add(menu);
                    xmenuMap.put(name, menu);
                }else{
                    List<XMenuItem> menuItems = menu.getXMenuItems();
                    List<XMenuItem> menuItems2 = xmenu.getXMenuItems();
                    menuItems2.addAll(menuItems);
                    xmenu.setXMenuItems(menuItems2);
                }
            }
        }
    }

    private JComponent interpreterMenu(String menuName, Object[] paramObjs, Object[] selectedObjects,
            boolean judgeSelectedObject) {
        XMenu xmenu = xmenuMap.get(menuName);
        if (xmenu == null)
            return null;
        JComponent component = null;
        String referenceMenu = xmenu.getReferenceMenu();
        if (!StringUtils.isEmpty(referenceMenu)) {
            interpreterMenu(referenceMenu, paramObjs, selectedObjects, judgeSelectedObject);
        } else {
            String text = xmenu.getText();
            if (StringUtils.isEmpty(text)) {
                component = new JPopupMenu();
            } else {
                component = new JMenu();
                Character mnemonic = xmenu.getMnemonic();
                int vk = XGuiUtils.getMnemonicKey(mnemonic);
                String displayName = ClientCoreContext.getString(text) + "(" + KeyEvent.getKeyText(vk) + ")";

                ((JMenu) component).setText(displayName);
                ((JMenu) component).setMnemonic(mnemonic);
            }
            intepreterMenuItem(xmenu, component, selectedObjects, paramObjs, judgeSelectedObject);
        }
        return component;
    }

    private void intepreterMenuItem(XMenu xmenu, JComponent menu, Object[] selectedObjects, Object[] paramObjs,
            boolean judgeSelectedObject) {
        List<XMenuItem> menuItems = xmenu.getXMenuItems();
        if (CollectionUtils.isEmpty(menuItems)) {
            return;
        }

        for (XMenuItem xmenuItem : menuItems) {
            String text = xmenuItem.getText();
            String referenceMenu = xmenuItem.getReferenceMenu();
            if (!StringUtils.isEmpty(referenceMenu)) {
                JComponent subMenu = interpreterMenu(referenceMenu, paramObjs, selectedObjects, judgeSelectedObject);
                menu.add(subMenu);
            } else {
                if (StringUtils.isEmpty(referenceMenu) && StringUtils.isEmpty(text)
                        && StringUtils.isEmpty(xmenuItem.getClazz()))
                    menu.add(new JSeparator());
                else {
                    if (judgeSelectedObject && judgeSelectedObjectClass(selectedObjects)) {
                        return;
                    }

                    // 判断菜单是否支持，通过SM，[目前多个分割线放在一起还没有处理]
                    if (selectedObjects != null) {
                        boolean support = true;
                        for (Object obj : selectedObjects) {
                            if (obj instanceof Node) {
                                Object userObject = ((Node) obj).getUserObject();
                                String methodName = "getMoNaming";

                                Object id;
                                try {
                                    Method method = userObject.getClass().getMethod(methodName);
                                    id = method.invoke(userObject);
                                    support = ClientCoreContext.getAccessCheck().check(getClazzSimpleName(xmenuItem.getClazz()),id);
                                } catch (Exception e) {
                                    ClientCoreContext.getLogger().error("", e);
                                }
                            }
                        }
                        if (!support)
                            continue;
                    }

                    if (!StringUtils.isEmpty(referenceMenu)) {
                        JComponent interpreterMenu = interpreterMenu(referenceMenu, selectedObjects, paramObjs,
                                judgeSelectedObject);
                        Character mnemonic = xmenuItem.getMnemonic();
                        if (mnemonic != null) {
                            int vk = XGuiUtils.getMnemonicKey(mnemonic);
                            String displayName = ClientCoreContext.getString(text) + "(" + KeyEvent.getKeyText(vk)
                                    + ")";
                            interpreterMenu.setName(displayName);
                            ((AbstractButton) interpreterMenu).setMnemonic(mnemonic);
                        } else {
                            String displayName = ClientCoreContext.getString(text);
                            interpreterMenu.setName(displayName);
                        }
                        menu.add(interpreterMenu);
                    } else {
                        constructMenuItem(menu, selectedObjects, paramObjs, xmenuItem);
                    }
                }
            }
        }
    }

    /**
     * @param menu
     * @param selectedObjects
     * @param paramObj
     * @param xmenuItem
     * @param actionId
     */
    private void constructMenuItem(JComponent menu, Object[] selectedObjects, Object[] paramObjs, XMenuItem xmenuItem) {
        String paramClass = xmenuItem.getParamClass();
        String clazz = xmenuItem.getClazz();
        boolean multiple = xmenuItem.getMultiple();
        try {
            Class<? extends AbstractXAction> actionClazz = (Class<? extends AbstractXAction>) Class.forName(clazz);
            if (selectedObjects == null) {
                Constructor<? extends AbstractXAction> constructor = actionClazz.getConstructor(String.class);
                AbstractXAction action = constructor.newInstance(getClazzSimpleName(clazz));
                setActionDisplayInfo(xmenuItem, action);
                createMenuItem(menu, xmenuItem, action);
            } else {
                Constructor<? extends AbstractXAction> constructor = actionClazz.getConstructor(Boolean.class,
                        String.class, Object[].class, Object.class, String.class);
                AbstractXAction action = null;
                if (StringUtils.isEmpty(paramClass)) {
                    action = constructor.newInstance(multiple, getClazzSimpleName(clazz), selectedObjects, null, null);
                } else {
                    Class<?> paramClazz = Class.forName(paramClass);

                    for (Object object : paramObjs) {
                        if (paramClazz.isInstance(object)) {
                            action = constructor.newInstance(multiple, getClazzSimpleName(clazz), selectedObjects,
                                    object, paramClass);
                        }
                    }
                }
                setActionDisplayInfo(xmenuItem, action);
                createMenuItem(menu, xmenuItem, action);
            }
        } catch (Exception e) {
            DialogUtils.showErrorExpandDialog(null, e);
        }
    }

    private String getClazzSimpleName(String clazz) {
        if (StringUtils.isEmpty(clazz))
            return "";
        int lastIndexOf = clazz.lastIndexOf(".");
        String clazzSimpleName = clazz.substring(lastIndexOf + 1, clazz.length());
        return clazzSimpleName;
    }

    /**
     * @param menu
     * @param xmenuItem
     * @param itemType
     * @param action
     */
    private void createMenuItem(JComponent menu, XMenuItem xmenuItem, AbstractXAction action) {
        String itemType = xmenuItem.getItemType();
        if (StringUtils.isEmpty(itemType)) {
            if (menu instanceof JPopupMenu) {
                ((JPopupMenu) menu).add(action);
            } else {
                ((JMenu) menu).add(action);
            }
        } else {
            JMenuItem menuItem = null;
            if (itemType != null && itemType.equals("radioButton")) {
                if (groupMap == null) {
                    groupMap = new ConcurrentHashMap<String, ButtonGroup>();
                }
                String buttonGroupName = xmenuItem.getButtonGroupName();
                if (!StringUtils.isEmpty(buttonGroupName)) {
                    ButtonGroup buttonGroup = groupMap.get(buttonGroupName);
                    if (buttonGroup == null) {
                        buttonGroup = new ButtonGroup();
                        groupMap.put(buttonGroupName, buttonGroup);
                    }
                    menuItem = new JRadioButtonMenuItem(action.getValue(Action.NAME).toString(), false);
                    buttonGroup.add(menuItem);
                }
            } else if (itemType != null && itemType.equals("checkBox")) {
                menuItem = new JCheckBoxMenuItem(action.getValue(Action.NAME).toString(), false);
            } else {
                menuItem = new JMenuItem(action.getValue(Action.NAME).toString());
                Object value = action.getValue(Action.ACTION_COMMAND_KEY);
                if (value != null) {
                    menuItem.setActionCommand(value.toString());
                }
            }
            if(menuItem==null)
                return;
            menuItem.addActionListener(action);
            menuItem.setEnabled(action.isEnabled());
            action.setMenuItem(menuItem);
            if (selectedButtonTexts != null) {
                for (String text : selectedButtonTexts) {
                    if (text.equals(xmenuItem.getText())) {
                        menuItem.setSelected(true);
                    }
                }
            }
            menu.add(menuItem);
        }
    }

    /**
     * 判断所选择对象的类型是否一致，如果不一致则不能生产菜单
     * 
     * @return boolean 如果一致返回<code>false</code>，否则返回<code>true</code>。
     */
    private boolean judgeSelectedObjectClass(Object[] selectedObjects) {
        if (ArrayUtils.isEmpty(selectedObjects)) {
            return false;
        }
        Object object = selectedObjects[0];
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        for (Object obj : selectedObjects) {
            if (!clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置Action的显示信息，包括名字、快捷键、图标等
     * 
     * @param xmenuItem
     *            配置的菜单项信息，信息从这里获取
     * @param action
     *            需要设置的action
     */
    private void setActionDisplayInfo(XMenuItem xmenuItem, AbstractXAction action) {
        String text = xmenuItem.getText();
        if (!StringUtils.isEmpty(text)) {
            Character mnemonic = xmenuItem.getMnemonic();
            String mnemonicStr = "";
            String textStr = ClientCoreContext.getString(text);
            if (mnemonic != null) {
                int vk = XGuiUtils.getMnemonicKey(mnemonic);
                if (textStr.toLowerCase().indexOf(String.valueOf(mnemonic).toLowerCase()) == -1) {
                    mnemonicStr = new StringBuilder("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
                }
                action.putValue(Action.MNEMONIC_KEY, vk);
            }
            String displayName = textStr + mnemonicStr;
            action.putValue(Action.NAME, displayName);
        }
        if (xmenuItem.getIcon() != null) {
            String iconStr = xmenuItem.getIcon();
            URL url_Icon = null;
            if (!StringUtils.isEmpty(iconStr)) {
                ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
                url_Icon = resourceFactory.getIconUrl(module, iconStr);
                if (url_Icon != null) {
                    action.putValue(Action.LARGE_ICON_KEY, url_Icon);
                }
            }
        }
    }

    /**
     * @return the xmenuMap
     */
    public Map<String, XMenu> getXmenuMap() {
        return xmenuMap;
    }

    /**
     * @return the mainMenus
     */
    public List<XMenu> getMainMenus() {
        return mainMenus;
    }
    
}
