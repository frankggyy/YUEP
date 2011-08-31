/*
 * $Id: XTable.java, 2010-3-23 下午02:22:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table;

import java.beans.BeanInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.SwingUtilities;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import twaver.ElementAttribute;
import twaver.Node;
import twaver.TWaverUtil;
import twaver.table.TElementTable;
import twaver.table.TTableColumn;
import twaver.table.renderer.BooleanRenderer;
import twaver.table.renderer.StringRenderer;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.InstanceProvider;
import com.yuep.core.client.component.table.renderer.XTableHeaderRenderer;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: XTable
 * </p>
 * <p>
 * Description:扩展TElementTable，支持对象化的添加、删除、修改操作，并支持完整的对象保存，列的组合展示等功能
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-23 下午02:22:59
 * modified [who date description]
 * check [who date description]
 */
@SuppressWarnings("unchecked")
public class XTable<T> extends TElementTable implements InstanceProvider {

    private static final long serialVersionUID = 3839692428603819591L;

    /**
     * 展示的列
     */
    protected List<String> columnNames;
    /**
     * 对象类型，如果真实对象类型存在时，应该是包装的对象类型
     */
    protected Class<T> clazz;

    /**
     * 真实的对象类型
     */
    protected Class realClass;
    /**
     * 唯一的属性名
     */
    protected String uniquePropertyName;
    /**
     * 存储组合属性信息的Map
     */
    protected Map<String, Map<String, List<String>>> composePropertyMap = new ConcurrentHashMap<String, Map<String, List<String>>>();

    /**
     * Constructor.
     * 
     * @param clazz
     *            要存储的对象类型
     * @param uniquePropertyName
     *            要存储对象唯一的属性名
     */
    public XTable(Class<T> clazz, String uniquePropertyName) {
        this.clazz = clazz;
        this.uniquePropertyName = uniquePropertyName;
        columnNames = new ArrayList<String>();
        setTableBodyPopupMenuFactory(null);
    }

    /**
     * Constructor.
     * 
     * @param <R>
     *            真实对象类型的定义
     * @param clazz
     *            要存储的对象类型
     * @param realClass
     *            要存储的真实对象的类型
     * @param uniquePropertyName
     *            要存储对象唯一的属性名
     */
    public <R> XTable(Class<T> clazz, Class<R> realClass, String uniquePropertyName) {
        this.clazz = clazz;
        this.realClass = realClass;
        this.uniquePropertyName = uniquePropertyName;
        columnNames = new ArrayList<String>();
        setTableBodyPopupMenuFactory(null);
    }

    /**
     * Constructor.
     * 
     * @param clazz
     *            要存储的对象类型
     * @param uniquePropertyName
     *            要存储对象唯一的属性名
     * @param propertyNames
     *            需要展示的属性名数组
     */
    public XTable(Class<T> clazz, String uniquePropertyName, String... propertyNames) {
        this.clazz = clazz;
        this.uniquePropertyName = uniquePropertyName;
        this.columnNames = new ArrayList<String>();
        for (String name : propertyNames) {
            this.columnNames.add(name);
        }
        setElementClass(Node.class);
        initTableColumnRenderer();
        setTableBodyPopupMenuFactory(null);
    }

    /**
     * Constructor.
     * 
     * @param <R>
     *            真实对象类型的定义
     * @param clazz
     *            要存储的包装对象类型
     * @param realClass
     *            要存储的真实对象的类型
     * @param uniquePropertyName
     *            要存储对象唯一的属性名
     * @param propertyNames
     *            需要展示的属性名数组
     */
    public <R> XTable(Class<T> clazz, Class<R> realClass, String uniquePropertyName, String... propertyNames) {
        this.clazz = clazz;
        this.realClass = realClass;
        this.uniquePropertyName = uniquePropertyName;
        this.columnNames = new ArrayList<String>();
        for (String name : propertyNames) {
            this.columnNames.add(name);
        }
        setElementClass(Node.class);
        initTableColumnRenderer();
        setTableBodyPopupMenuFactory(null);
    }

    /**
     * 根据对象的属性名添加列
     * 
     * @param propertyName
     *            对象的属性名
     */
    public void addColumnByName(String propertyName) {
        Assert.notNull(propertyName);
        if (columnNames.contains(propertyName)) {
            DialogUtils.showWarnDialog(null, "The column is exist! column name : " + propertyName);
            return;
        }
        columnNames.add(propertyName);
        setElementClass(Node.class);
        initTableColumnRenderer();
    }

    /**
     * 初始化列头和列的Renderer，包括几种默认的renderer，有boolean、EqNaming，
     * <p>
     * 如果不是上述两张类型的数据，默认添加字符串Renderer
     */
    private void initTableColumnRenderer() {
        for (String name : columnNames) {
            TTableColumn column = getColumnByName(name);
            column.setHeaderRenderer(new XTableHeaderRenderer());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (name.equals(fieldName)) {
                    Class<?> type = field.getType();
                    setColumnCellRenderer(column, type);
                } else {
                    if (realClass != null) {
                        Field[] realFields = realClass.getDeclaredFields();
                        for (Field realField : realFields) {
                            String realFieldName = realField.getName();
                            if (name.equals(realFieldName)) {
                                Class<?> type = realField.getType();
                                setColumnCellRenderer(column, type);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据列数据类型为其添加CellRenderer
     * 
     * @param column
     *            需要添加Renderer的列
     * @param type
     *            列的数据类型
     */
    private void setColumnCellRenderer(TTableColumn column, Class<?> type) {
        if (type.equals(Boolean.class)) {
            column.setCellRenderer(new BooleanRenderer());
            // TODO 根据common工程中的定义进行修改
            // } else if (type.equals(EqNaming.class)) {
            // column.setCellRenderer(new EqNamingRenderer());
            // } else if (type.getInterfaces() != null &&
            // type.getInterfaces().length > 0
            // && type.getInterfaces()[0].equals(EnumValue.class)) {
            // column.setCellRenderer(new EnumCellRenderer());
        } else {
            column.setCellRenderer(new StringRenderer());
        }
    }

    @Override
    public List<BeanInfo> getAllBeanInfo(Class clazz) {
        List<BeanInfo> result = new ArrayList<BeanInfo>(1);
        List<ElementAttribute> attrList = new ArrayList<ElementAttribute>(2);
        for (String columnName : columnNames) {
            String displayColumnName = ClientCoreContext.getString(this.clazz.getSimpleName() + "." + columnName);
            ElementAttribute attr = new ElementAttribute();
            attr.setDisplayName(displayColumnName);
            attr.setEditable(false);
            attr.setClientPropertyKey(columnName);
            attrList.add(attr);
        }
        result.add(TWaverUtil.createBeanInfo(clazz, attrList));
        return result;
    }

    /**
     * 添加一行数据
     * 
     * @param data
     *            要添加的数据对象
     */
    public void addRowData(T data) {
        Object uniqueData = getUniqueData(data);
        Node node = null;
        if (uniqueData == null)
            node = new Node();
        else
            node = new Node(uniqueData);
        node.setBusinessObject(data);
        setUserObject(data, node);
        for (String columnName : columnNames) {
            if (composePropertyMap.containsKey(columnName)) {
                String composeStr = composeObj(data, node, columnName);
                node.putClientProperty(columnName, composeStr);

            } else {
                Object obj = reflectProperty2Data(data, node, columnName);
                node.putClientProperty(columnName, obj);
            }
        }
        addNode2Box(node);
    }

    /**
     * 如果该table显示的是包装对象，那么这个方法才有效，将真实对象设置到Node
     * 
     * @param data
     *            包装对象
     * @param node
     *            table显示的Node对象
     */
    private void setUserObject(T data, Node node) {
        if (realClass != null) {
            Method[] methods = data.getClass().getMethods();
            for (Method method : methods) {
                try {
                    String name = method.getName();
                    if (name.indexOf("get") != -1) {
                        Object invoke = method.invoke(data);
                        if (realClass.isInstance(invoke)) {
                            node.setUserObject(invoke);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("XTable:The method " +data.getClass().getSimpleName()+"."+ method.getName() + " is not reflection.");
                    continue;
                }
            }
        }
    }

    /**
     * 拼装组合属性的值
     * 
     * @param data
     *            存储的数据对象
     * @param node
     *            存储的节点
     * @param columnName
     *            组合属性的列名
     * @return <code>String</code> 拼装后的组合属性值
     */
    private String composeObj(T data, Node node, String columnName) {
        Map<String, List<String>> map = composePropertyMap.get(columnName);
        Set<Entry<String, List<String>>> entrySet = map.entrySet();
        String composeStr = "";
        for (Entry<String, List<String>> entry : entrySet) {
            List<String> value = entry.getValue();
            String separator = entry.getKey();
            for (int i = 0; i < value.size(); i++) {
                String string = value.get(i);
                Object obj = reflectProperty2Data(data, node, string);
                if (obj == null)
                    continue;
                if (i == 0 || StringUtils.isEmpty(composeStr))
                    composeStr = obj.toString();
                else
                    composeStr = composeStr + separator + obj.toString();
            }
        }
        return composeStr;
    }

    /**
     * 通过反射从存储对象上获取某个属性的值
     * 
     * @param data
     *            存储的数据对象
     * @param columnName
     *            需要从数据对象上反射的属性
     * @return <code>Object</code> 对象上获取的<code>columnName</code>属性的值
     */
    private Object reflectProperty2Data(T data, Node node, String columnName) {
        Object obj = null;
        try {
            String getterName = "get" + XGuiUtils.capitalize(columnName);
            Method method = data.getClass().getMethod(getterName);
            obj = method.invoke(data);
        } catch (Exception e) {
            try {
                String getterName = "is" + XGuiUtils.capitalize(columnName);
                Method method = data.getClass().getMethod(getterName);
                obj = method.invoke(data);
            } catch (Exception e1) {
                if (realClass != null) {
                    Object userObject = node.getUserObject();
                    try {
                        String getterName = "get" + XGuiUtils.capitalize(columnName);
                        Method getMethod = userObject.getClass().getMethod(getterName);
                        obj = getMethod.invoke(userObject);
                    } catch (Exception e2) {
                        try {
                            String getterName = "is" + XGuiUtils.capitalize(columnName);
                            Method isMethod = userObject.getClass().getMethod(getterName);
                            obj = isMethod.invoke(userObject);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                } else {
                    e1.printStackTrace();
                }
            }
        }

        return obj;
    }

    /**
     * 添加多上数据
     * 
     * @param datas
     *            数据列表
     */
    public void addRowDatas(List<T> datas) {
        for (T t : datas) {
            addRowData(t);
        }
    }

    /**
     * 获取Table中被选中的数据信息
     * 
     * @return List<T> 被选中的数据列表
     */
    public List<T> getSelectionDatas() {
        List<T> list = new ArrayList<T>();
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        for (Object object : allSelectedElement) {
            Node node = (Node) object;
            Object businessObject = node.getBusinessObject();
            if (businessObject != null)
                list.add((T) businessObject);
        }
        return list;
    }

    /**
     * 获取table中被选中的真实对象列表
     * 
     * @param <R>
     *            真实对象的类型
     * @return <code>List<R></code> 被选中的真实对象列表
     */
    public <R> List<R> getSelectionRealDatas() {
        List<R> list = new ArrayList<R>();
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        for (Object object : allSelectedElement) {
            Node node = (Node) object;
            Object userObject = node.getUserObject();
            if (userObject != null)
                list.add((R) userObject);
        }
        return list;
    }

    /**
     * 获取Table中被选中的数据信息
     * 
     * @return <code>T</code> 被选中的某一条数据
     */
    public T getSelectionData() {
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        Object object = allSelectedElement.get(0);
        Node node = (Node) object;
        Object businessObject = node.getBusinessObject();
        return (T) businessObject;
    }

    /**
     * 获取table中被选中的真实对象
     * 
     * @param <R>
     *            真实对象的类型
     * @return <code>R</code> 被选中的真实对象
     */
    public <R> R getSelectionRealData() {
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        Object object = allSelectedElement.get(0);
        Node node = (Node) object;
        Object userObject = node.getUserObject();
        return (R) userObject;
    }

    /**
     * 更新Table中的数据信息
     * 
     * @param datas
     *            要更新的数据信息列表
     */
    public void updateRowDatas(List<T> datas) {
        for (T t : datas) {
            updateRowData(t);
        }
    }

    /**
     * 更新Table中的数据信息
     * 
     * @param data
     *            要更新的数据信息
     */
    public void updateRowData(T data) {
        Object uniqueData = getUniqueData(data);
        List allElements = box.getAllElements();
        if (uniqueData == null) {
            for (Object object : allElements) {
                Node node = (Node) object;
                Object businessObject = node.getBusinessObject();
                if (businessObject.equals(data)) {
                    updateData(data, node);
                }
            }
        } else {
            for (Object object : allElements) {
                Node node = (Node) object;
                Object id = node.getID();
                if (id.equals(uniqueData)) {
                    updateData(data, node);
                }
            }
        }
    }

    private void updateData(T data, Node node) {
        for (String columnName : columnNames) {
            node.setBusinessObject(data);
            setUserObject(data, node);
            if (composePropertyMap.containsKey(columnName)) {
                String composeStr = composeObj(data, node, columnName);
                node.putClientProperty(columnName, composeStr);

            } else {
                Object obj = reflectProperty2Data(data, node, columnName);
                node.putClientProperty(columnName, obj);
            }
        }
    }

    private void addNode2Box(final Node node) {
        if (SwingUtilities.isEventDispatchThread()) {
            if (!box.containsByID(node.getID()))
                box.addElement(node);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (!box.containsByID(node.getID()))
                        box.addElement(node);
                }
            });
        }
    }

    /**
     * 删除Table中的数据信息
     * 
     * @param data
     *            要删除的数据信息
     */
    public void deleteRowData(T data) {
        final Object uniqueData = getUniqueData(data);
        if (uniqueData == null) {
            List allElements = box.getAllElements();
            for (Object object : allElements) {
                final Node node = (Node) object;
                if (node.getBusinessObject().equals(data)) {
                    if (SwingUtilities.isEventDispatchThread()) {
                        box.removeElement(node);
                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                box.removeElement(node);
                            }
                        });
                    }
                }
            }
        } else {
            if (SwingUtilities.isEventDispatchThread()) {
                box.removeElementByID(uniqueData);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        box.removeElementByID(uniqueData);
                    }
                });
            }
        }
    }

    /**
     * 删除Table中的数据信息
     * 
     * @param datas
     *            要删除的数据信息列表
     */
    public void deleteRowDatas(List<T> datas) {
        for (T t : datas) {
            deleteRowData(t);
        }
    }

    /**
     * 获取Table中的所有数据信息
     * 
     * @return <code>List<T></code> 表格中的所有数据信息
     */
    public List<T> getAllDatas() {
        List<T> list = new ArrayList<T>();
        List allElements = box.getAllElements();
        for (Object object : allElements) {
            Node node = (Node) object;
            Object businessObject = node.getBusinessObject();
            list.add((T) businessObject);
        }
        return list;
    }

    /**
     * 获取Table中所有真实对象的列表
     * 
     * @param <R>
     *            真实对象的类型
     * @return <code>List<R></code> 真实对象的列表
     */
    public <R> List<R> getAllRealDatas() {
        List<R> list = new ArrayList<R>();
        List allElements = box.getAllElements();
        for (Object object : allElements) {
            Node node = (Node) object;
            Object userObject = node.getUserObject();
            list.add((R) userObject);
        }
        return list;
    }

    /**
     * 清除Table中的所有数据
     */
    public void clearDatas() {
        if (SwingUtilities.isEventDispatchThread()) {
            box.clear();
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    box.clear();
                }
            });
        }
    }

    private Object getUniqueData(T data) {
        if (StringUtils.isEmpty(uniquePropertyName)) {
            return null;
        }
        try {
            if (realClass != null) {
                Method[] methods = data.getClass().getMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    try {
                        if (name.indexOf("get") != -1) {
                            Object invoke = method.invoke(data);
                            if (realClass.isInstance(invoke)) {
                                String getterName = "get" + XGuiUtils.capitalize(uniquePropertyName);
                                Method uniqueMethod = invoke.getClass().getMethod(getterName);
                                Object obj = uniqueMethod.invoke(invoke);
                                return obj;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("XTable:The method " +data.getClass().getSimpleName()+"."+method.getName() + " is not reflection.");
                        continue;
                    }
                }
            } else {
                String getterName = "get" + XGuiUtils.capitalize(uniquePropertyName);
                Method uniqueMethod = data.getClass().getMethod(getterName);
                Object obj = uniqueMethod.invoke(data);
                return obj;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.menu.action.InstanceProvider#getSelectedCount()
     */
    @Override
    public int getSelectedCount() {
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        return allSelectedElement.size();
    }

    /**
     * 设置Table中需要组合的属性信息，主要是针对有些属性需要table中的某两列或者多列组合到一起来显示的情况的方法
     * 
     * @param composeProperty
     *            显示为组合属性的列
     * @param separator
     *            组合属性之间的分隔符
     * @param beComposeProperties
     *            需要被组合的属性数组
     */
    public void setComposeProperty(String composeProperty, String separator, String... beComposeProperties) {
        Map<String, List<String>> separatorMap = composePropertyMap.get(composeProperty);
        if (MapUtils.isEmpty(separatorMap)) {
            separatorMap = new ConcurrentHashMap<String, List<String>>();
            composePropertyMap.put(composeProperty, separatorMap);
        }
        List<String> list = separatorMap.get(separator);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<String>();
            separatorMap.put(separator, list);
        }
        for (String property : beComposeProperties) {
            list.add(property);
        }
    }
}
