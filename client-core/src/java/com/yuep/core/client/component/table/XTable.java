/*
 * $Id: XTable.java, 2010-3-23 ����02:22:59 aaron lee Exp $
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
 * Description:��չTElementTable��֧�ֶ��󻯵���ӡ�ɾ�����޸Ĳ�������֧�������Ķ��󱣴棬�е����չʾ�ȹ���
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-23 ����02:22:59
 * modified [who date description]
 * check [who date description]
 */
@SuppressWarnings("unchecked")
public class XTable<T> extends TElementTable implements InstanceProvider {

    private static final long serialVersionUID = 3839692428603819591L;

    /**
     * չʾ����
     */
    protected List<String> columnNames;
    /**
     * �������ͣ������ʵ�������ʹ���ʱ��Ӧ���ǰ�װ�Ķ�������
     */
    protected Class<T> clazz;

    /**
     * ��ʵ�Ķ�������
     */
    protected Class realClass;
    /**
     * Ψһ��������
     */
    protected String uniquePropertyName;
    /**
     * �洢���������Ϣ��Map
     */
    protected Map<String, Map<String, List<String>>> composePropertyMap = new ConcurrentHashMap<String, Map<String, List<String>>>();

    /**
     * Constructor.
     * 
     * @param clazz
     *            Ҫ�洢�Ķ�������
     * @param uniquePropertyName
     *            Ҫ�洢����Ψһ��������
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
     *            ��ʵ�������͵Ķ���
     * @param clazz
     *            Ҫ�洢�Ķ�������
     * @param realClass
     *            Ҫ�洢����ʵ���������
     * @param uniquePropertyName
     *            Ҫ�洢����Ψһ��������
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
     *            Ҫ�洢�Ķ�������
     * @param uniquePropertyName
     *            Ҫ�洢����Ψһ��������
     * @param propertyNames
     *            ��Ҫչʾ������������
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
     *            ��ʵ�������͵Ķ���
     * @param clazz
     *            Ҫ�洢�İ�װ��������
     * @param realClass
     *            Ҫ�洢����ʵ���������
     * @param uniquePropertyName
     *            Ҫ�洢����Ψһ��������
     * @param propertyNames
     *            ��Ҫչʾ������������
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
     * ���ݶ���������������
     * 
     * @param propertyName
     *            �����������
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
     * ��ʼ����ͷ���е�Renderer����������Ĭ�ϵ�renderer����boolean��EqNaming��
     * <p>
     * ������������������͵����ݣ�Ĭ������ַ���Renderer
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
     * ��������������Ϊ�����CellRenderer
     * 
     * @param column
     *            ��Ҫ���Renderer����
     * @param type
     *            �е���������
     */
    private void setColumnCellRenderer(TTableColumn column, Class<?> type) {
        if (type.equals(Boolean.class)) {
            column.setCellRenderer(new BooleanRenderer());
            // TODO ����common�����еĶ�������޸�
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
     * ���һ������
     * 
     * @param data
     *            Ҫ��ӵ����ݶ���
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
     * �����table��ʾ���ǰ�װ������ô�����������Ч������ʵ�������õ�Node
     * 
     * @param data
     *            ��װ����
     * @param node
     *            table��ʾ��Node����
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
     * ƴװ������Ե�ֵ
     * 
     * @param data
     *            �洢�����ݶ���
     * @param node
     *            �洢�Ľڵ�
     * @param columnName
     *            ������Ե�����
     * @return <code>String</code> ƴװ����������ֵ
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
     * ͨ������Ӵ洢�����ϻ�ȡĳ�����Ե�ֵ
     * 
     * @param data
     *            �洢�����ݶ���
     * @param columnName
     *            ��Ҫ�����ݶ����Ϸ��������
     * @return <code>Object</code> �����ϻ�ȡ��<code>columnName</code>���Ե�ֵ
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
     * ��Ӷ�������
     * 
     * @param datas
     *            �����б�
     */
    public void addRowDatas(List<T> datas) {
        for (T t : datas) {
            addRowData(t);
        }
    }

    /**
     * ��ȡTable�б�ѡ�е�������Ϣ
     * 
     * @return List<T> ��ѡ�е������б�
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
     * ��ȡtable�б�ѡ�е���ʵ�����б�
     * 
     * @param <R>
     *            ��ʵ���������
     * @return <code>List<R></code> ��ѡ�е���ʵ�����б�
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
     * ��ȡTable�б�ѡ�е�������Ϣ
     * 
     * @return <code>T</code> ��ѡ�е�ĳһ������
     */
    public T getSelectionData() {
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        Object object = allSelectedElement.get(0);
        Node node = (Node) object;
        Object businessObject = node.getBusinessObject();
        return (T) businessObject;
    }

    /**
     * ��ȡtable�б�ѡ�е���ʵ����
     * 
     * @param <R>
     *            ��ʵ���������
     * @return <code>R</code> ��ѡ�е���ʵ����
     */
    public <R> R getSelectionRealData() {
        List allSelectedElement = box.getSelectionModel().getAllSelectedElement();
        Object object = allSelectedElement.get(0);
        Node node = (Node) object;
        Object userObject = node.getUserObject();
        return (R) userObject;
    }

    /**
     * ����Table�е�������Ϣ
     * 
     * @param datas
     *            Ҫ���µ�������Ϣ�б�
     */
    public void updateRowDatas(List<T> datas) {
        for (T t : datas) {
            updateRowData(t);
        }
    }

    /**
     * ����Table�е�������Ϣ
     * 
     * @param data
     *            Ҫ���µ�������Ϣ
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
     * ɾ��Table�е�������Ϣ
     * 
     * @param data
     *            Ҫɾ����������Ϣ
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
     * ɾ��Table�е�������Ϣ
     * 
     * @param datas
     *            Ҫɾ����������Ϣ�б�
     */
    public void deleteRowDatas(List<T> datas) {
        for (T t : datas) {
            deleteRowData(t);
        }
    }

    /**
     * ��ȡTable�е�����������Ϣ
     * 
     * @return <code>List<T></code> ����е�����������Ϣ
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
     * ��ȡTable��������ʵ������б�
     * 
     * @param <R>
     *            ��ʵ���������
     * @return <code>List<R></code> ��ʵ������б�
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
     * ���Table�е���������
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
     * ����Table����Ҫ��ϵ�������Ϣ����Ҫ�������Щ������Ҫtable�е�ĳ���л��߶�����ϵ�һ������ʾ������ķ���
     * 
     * @param composeProperty
     *            ��ʾΪ������Ե���
     * @param separator
     *            �������֮��ķָ���
     * @param beComposeProperties
     *            ��Ҫ����ϵ���������
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
