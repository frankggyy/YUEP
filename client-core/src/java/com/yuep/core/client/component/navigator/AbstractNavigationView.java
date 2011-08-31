/*
 * $Id: AbstractNavigationView.java, 2009-3-16 上午10:40:52 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import twaver.CheckableFilter;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.Element;
import twaver.Node;
import twaver.PopupMenuFactory;
import twaver.tree.TTree;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.TreeEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: AbstractNavigationView
 * </p>
 * <p>
 * Description:抽象的导航控件的View
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 上午10:40:52
 * @modified aaron lee 2010-3-30 将其从<code>AbstractValidateView<T></code>继承
 * check [who date description]
 */
public abstract class AbstractNavigationView<T> extends AbstractValidateView<T> {
    private static final long serialVersionUID = 267831199369815290L;
    /**
     * 导航树
     */
    protected TTree navigationTree;
    /**
     * 过滤控件
     */
    private JTextField filterField;
    /**
     * 树的标题
     */
    private JLabel treeTitle;
    /**
     * 导航树的过滤条件
     */
    private List<AbstractFilter> filters;
    /**
     * 内容显示区域
     */
    protected JPanel tabPanel;
    
    /**
     * 被选节点
     */
    protected Node selectedNode;
    /**
     * 多Tab页时的Tab页容器
     */
    protected JTabbedPane tp;
    /**
     * 所有Tab页信息
     */
    private Map<String, List<AbstractTabController<?, ?, ?>>> tabMap = new ConcurrentHashMap<String, List<AbstractTabController<?, ?, ?>>>();

    /**
     * 上一次被选择的Tab页的Controller
     */
    private AbstractTabController<?, ?, ?> lastSelectedTabController;

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#constructUi()
     */
    public void constructUi() {
        // 需要先初始化过滤器
        filters = new ArrayList<AbstractFilter>();
        // name filter of default
        filters.add(new AbstractFilter() {
            @Override
            public String getDescription() {
                return ClientCoreContext.getString("common.navigation.filter.namefilter");
            }

            @Override
            public boolean filter(Element data, String value) {
                return data.getName().toLowerCase().indexOf(value) != -1;
            }

            @Override
            public boolean isFilter(String name) {
                return name != null && name.length() > 0;
            }
        });
        List<AbstractFilter> list = getFilters();
        if (!CollectionUtils.isEmpty(list)) {
            filters.addAll(list);
        }
        super.constructUi();
    }

    /**
     * 获取要过滤的字符串
     * @return <code>String</code> 要过滤的字符串
     */
    public String getFilterText() {
        return filterField.getText();
    }

    /**
     * 设置导航树的名字
     * @param title
     *            导航树名字
     */
    public void setTreeTitle(String title) {
        treeTitle.setText(title);
    }

    /**
     * 清空导航树节点
     */
    public void resetNavigation() {
        navigationTree.clearSelection();
        navigationTree.getDataBox().clear();
        // 清空导航过滤框
        filterField.setText("");
    }

    /**
     * 通过节点名称导航到某个节点，如果树上有名称相同的多个节点只能导航到其中一个，此场景请使用navigationByNodeId
     * 
     * 如果找不到对应节点，则导航到第一个节点
     * 
     * @param nodeName
     *            节点名称
     * @see navigationByNodeId
     */
    public void navigationByNodeName(final String nodeName) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                while (navigationTree.getDataBox().size() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                    }
                }
                Element node = navigationTree.getDataBox().getElementByName(nodeName);
                if (node == null) {
                    node = navigationTree.getElementByRowIndex(0);
                }
                System.out.println("" + node.getName());
                navigationTree.getDataBox().getSelectionModel().setSelection(node);
                // node.setSelected(true);
            }
        });
    }

    /**
     * 通过节点ID导航到某个节点
     * 
     * 如果找不到对应节点，则导航到第一个节点
     * 
     * @param nodeId
     *            节点ID,肯定是唯一
     * @see navigationByNodeName
     */
    public void navigationByNodeId(final Object nodeId) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                while (navigationTree.getDataBox().size() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                    }
                }
                Element node = navigationTree.getDataBox().getElementByID(nodeId);
                if (node == null) {
                    node = navigationTree.getElementByRowIndex(0);
                }
                System.out.println("" + node.getID());
                navigationTree.getDataBox().getSelectionModel().setSelection(node);
                // node.setSelected(true);
            }
        });
    }

    /**
     * 不能动态更改，只能在子实现时重写本方法控制是否显示过滤栏
     * 
     * @return 是否显示输入过滤栏，default显示
     */
    protected boolean showFilter() {
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#getWorkspace()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JSplitPane jSplitPane = swingFactory.getSplitPane();
        double[][] table = new double[][] { { 3, XTableLayout.FILL }, { 20, 20, 5, XTableLayout.FILL } };
        JPanel treePanel = swingFactory.getPanel(swingFactory.getTableLayout(table));
        navigationTree = swingFactory.getXEditor(new TreeEditorDecorator(""));
        // 设置tree属性
        navigationTree.setToolTipText(null);
        navigationTree.setSelectableOnRightClick(true);
        navigationTree.setClearSelectionOnMarginClicked(true);
        // 单选
        navigationTree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        navigationTree.setShowsRootHandles(true);
        navigationTree.setPopupMenuFactory(getPopupMenuFactory());
        navigationTree.setEnableTristateCheckBox(true);
        // navigationTree.setTTreeSelectionMode(TTree.CHECK_DESCENDANT_ANCESTOR_SELECTION);
        navigationTree.setCheckableFilter(new CheckableFilter() {
            @Override
            public boolean isCheckable(Element element) {
                return false;
            }
        });
        JScrollPane scroll = swingFactory.getScrollPane(navigationTree);
        filterField = swingFactory.getXEditor(new StringEditorDecorator(""));
        StringBuilder tip = new StringBuilder();
        tip.append(ClientCoreContext.getString("common.navigation.filter.tip"));
        for (AbstractFilter filter : filters) {
            tip.append("<li>");
            tip.append(filter.getDescription());
            tip.append("</li>");
        }
        tip.append("</ul></html>");
        filterField.setToolTipText(tip.toString());
        treeTitle = swingFactory.getLabel(new LabelDecorator("common.navigation.tree.title"));
        treePanel.add(treeTitle, "1,0,f,f");
        if (showFilter()) {
            treePanel.add(filterField, "1,1,f,f");
            treePanel.add(scroll, "1,3,f,f");
        } else {
            treePanel.add(scroll, "1,1,1,3");
        }
        treePanel.setPreferredSize(new Dimension(170, getHeight() - 35));

        tabPanel = swingFactory.getPanel(swingFactory.getBorderLayout());
        jSplitPane.add(treePanel, JSplitPane.LEFT);
        jSplitPane.add(tabPanel, JSplitPane.RIGHT);
        jSplitPane.setDividerLocation(230);
        jSplitPane.getTopComponent().setMinimumSize(new Dimension(200, 600));
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setBorder(null);
        return jSplitPane;
    }

    /**
     * 添加过滤器，默认添加一个名称过滤器
     * 
     * @param filterList
     */
    protected abstract List<AbstractFilter> getFilters();

    /**
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable obs, Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof List) {
            System.out.println("add tree nodes: " + obj);
            navigationTree.getDataBox().clear();
            navigationTree.getDataBox().addElements((List) obj);
            navigationTree.expandAll();
        } else if (obj instanceof Node) {
            Node node = (Node) obj;
            System.out.println("add tree node: " + obj);
            navigationTree.getDataBox().addElement(node);
            navigationTree.expandAll();
            navigationByNodeName(node.getName());
        } else if (obj instanceof Map) {
            tabMap.clear();
            tabMap.putAll((Map<? extends String, ? extends List<AbstractTabController<?, ?, ?>>>) obj);
        }
    }

    /**
     * 删除导航树上的一个节点
     * @param node 要删除的节点
     */
    public void removeNode(Node node) {
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * 根据导航树上节点的名字删除节点
     * @param nodeName 节点名字
     */
    public void removeNodeByName(String nodeName) {
        Element node = navigationTree.getDataBox().getElementByName(nodeName);
        if (node == null)
            return;
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * 根据导航树上节点的ID删除节点
     * @param nodeId 节点ID
     */
    public void removeNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        if (node == null)
            return;
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * 添加一个节点到导航树上
     * @param node 要添加的节点
     */
    public void addNode(Node node) {
        System.out.println("add tree node: " + node);
        navigationTree.getDataBox().addElement(node);
        navigationTree.expandAll();
        navigationByNodeName(node.getName());
    }

    /**
     * 根据节点的名字获取导航树的一个节点
     * @param nodeName 节点名字
     * @return Element 与名字相符的节点
     */
    public Element getNodeByName(String nodeName) {
        Element node = navigationTree.getDataBox().getElementByName(nodeName);
        return node;
    }

    /**
     * 根据节点的ID获取导航树的一个节点
     * @param nodeId 节点ID
     * @return Element 与ID相符的节点
     */
    public Element getNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        return node;
    }

    /**
     * 根据节点的ID获取该节点的父节点
     * @param nodeId 节点ID
     * @return Element 父节点
     */
    public Element getParentNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        return node.getParent();
    }

    /**
     * (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 550);
    }

    /**
     * 选择多个节点时调用
     * 
     * @param nodes
     *            多个节点
     */
    protected abstract void onSelectMulti(List<Node> nodes);

    /**
     * 获取当前选中Tab页的controller
     * @return <code>AbstractTabController</code> 当前选中Tab页的controller
     */
    public AbstractTabController getCurrentTabController() {
        Object userObject = selectedNode.getUserObject();
        List<AbstractTabController<?, ?, ?>> tabs = tabMap.get(userObject);
        if (CollectionUtils.isEmpty(tabs)) {
            return null;
        }
        if (tabs.size() == 1)
            return tabs.get(0);
        Component selectedComponent = tp.getSelectedComponent();
        int indexOfComponent = tp.indexOfComponent(selectedComponent);
        String title = tp.getTitleAt(indexOfComponent);
        String tabTitle = null;
        for (AbstractTabController<?, ?, ?> abstractTabController : tabs) {
            Object clientView = abstractTabController.getClientView();
            tabTitle = ClientCoreContext.getString(((AbstractTabView) clientView).getTabTitle());
            if (tabTitle.equals(title)) {
                return abstractTabController;
            }
        }
        return null;
    }

    /**
     * 获取当前选择导航树节点的所有关联的页面的Controller列表
     * @return <code>List<AbstractTabController<?, ?, ?>></code> 节点的所有关联的页面的Controller列表
     */
    public List<AbstractTabController<?, ?, ?>> getCurrentNodeController() {
        Object userObject = selectedNode.getUserObject();
        List<AbstractTabController<?, ?, ?>> tabs = tabMap.get(userObject);
        if (CollectionUtils.isEmpty(tabs)) {
            return null;
        }
        return tabs;
    }

    /**
     * 选择单个节点时调用
     * 
     * @param node
     *            单个节点
     */
    @SuppressWarnings("unchecked")
    protected void onSelectOne(Node node) {
        if (node == null) {
            List rootElements = navigationTree.getDataBox().getRootElements();
            if (CollectionUtils.isNotEmpty(rootElements)) {
                Object object = rootElements.get(0);
                if (object instanceof Node) {
                    this.selectedNode = (Node) object;
                }
            }
        } else {
            this.selectedNode = node;
        }
        messagePane.showMessage(new ValidateMessage());
        messagePane.removePorpertyChangeListener();
        tabPanel.removeAll();
        Object userObject=null;
        if(selectedNode!=null)
         userObject = selectedNode.getUserObject();
        if (userObject == null) {
            return;
        } else {
            List<AbstractTabController<?, ?, ?>> tabs = new ArrayList<AbstractTabController<?, ?, ?>>();
            if (MapUtils.isEmpty(tabMap)) {
                if (userObject instanceof List)
                    tabs = (List<AbstractTabController<?, ?, ?>>) userObject;
                else
                    tabs.add((AbstractTabController<?, ?, ?>) userObject);
            } else {
                tabs = tabMap.get(userObject);
            }
            if (CollectionUtils.isEmpty(tabs))
                return;
            if (tabs.size() == 1) {
                AbstractTabController<?, ?, ?> abstractTabController = tabs.get(0);
                abstractTabController.initView();
                tabPanel.add(abstractTabController.getClientView(), BorderLayout.CENTER);
                abstractTabController.setSelectNode(selectedNode);
                abstractTabController.initData(selectedNode.getBusinessObject());
                messagePane.setDescription(abstractTabController.getClientView().getDescription());
                messagePane.removePorpertyChangeListener();
                messagePane.initPropertyChangeListener(tabPanel);
            } else {
                tp = ClientCoreContext.getSwingFactory().getTabbedPane();
                final List<AbstractTabController<?, ?, ?>> tabList = new ArrayList<AbstractTabController<?, ?, ?>>(
                        tabs);
                tp.addChangeListener(new ChangeListener() {

                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Component selectedComponent = tp.getSelectedComponent();
                        messagePane.showMessage(new ValidateMessage());
                        messagePane.removePorpertyChangeListener();
                        int indexOfComponent = tp.indexOfComponent(selectedComponent);
                        if (indexOfComponent >= 0) {
                            String title = tp.getTitleAt(indexOfComponent);
                            for (AbstractTabController<?, ?, ?> abstractTabController : tabList) {
                                Object view = abstractTabController.getClientView();
                                String tabTitle = ((AbstractTabView) view).getTabTitle();
                                if (ClientCoreContext.getString(tabTitle).equals(title)) {
                                    lastSelectedTabController = abstractTabController;
                                    abstractTabController.initView();
                                    abstractTabController.initData(selectedNode.getBusinessObject());
                                    messagePane.setDescription(abstractTabController.getClientView().getDescription());
                                }
                            }
                        }
                        messagePane.initPropertyChangeListener((JComponent) selectedComponent);
                        messagePane.setSensitiveButtonEnabled(false);
                    }

                });
                for (AbstractTabController<?, ?, ?> tab : tabs) {
                    String title = ClientCoreContext.getString(tab.getClientView().getTabTitle());
                    tp.add(title, tab.getClientView());
                    tab.setSelectNode(selectedNode);
                }
                tabPanel.add(tp, BorderLayout.CENTER);
            }
        }
        tabPanel.updateUI();
        tabPanel.getParent().validate();
//        messagePane.initPropertyChangeListener();
        messagePane.setSensitiveButtonEnabled(false);
    }

    /**
     * @return 导航树的弹出菜单
     */
    protected abstract PopupMenuFactory getPopupMenuFactory();

    /**
     * aaron add. 为导航树添加排序的比较器
     * 
     * @param comparator
     * @author aaron lee
     */
    public void setTreeSortComparator(Comparator<Node> comparator) {
        navigationTree.setSortComparator(comparator);
        navigationTree.updateUI();
    }

    /**
     * 判断节点对象是否显示
     * 
     * @param element
     *            树节点
     * @return 是否显示
     */
    private boolean isVisible(Element element) {
        System.out.println(element.getName() + ".attachment=" + element.containsAttachment("ensureVisible"));
        if (element.containsAttachment("ensureVisible")) {
            System.out.println("Ensure show " + element.getName());
            return true;
        }
        if (element.getBusinessObject() == null) {
            return false;
        }
        if (getFilterText() == null || getFilterText().length() == 0) {
            return true;
        }
        boolean isVisible = false;
        String filter = getFilterText().toLowerCase();
        try {
            isVisible = filter(element, filter);
        } catch (Exception ex) {
        }
        if (isVisible) {
            Element parent = element.getParent();
            while (parent != null) {
                parent.setVisible(true);
                if (!parent.containsAttachment("ensureVisible")) {
                    parent.addAttachment("ensureVisible");
                }
                parent = parent.getParent();
            }
        }
        return isVisible;
    }

    /**
     * 对导航树进行过滤
     * 
     * @param data
     *            树节点
     * @param value
     *            过滤字符串
     * @return 是否显示
     */
    private boolean filter(Element data, String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        for (AbstractFilter filter : filters) {
            if (filter.isFilter(value) && filter.filter(data, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    public <V,M> void addButtonListener(ClientController<T,V,M> controller) {
        if (showFilter()) {
            filterField.addKeyListener(new KeyAdapter() {
                @SuppressWarnings("unchecked")
                @Override
                public void keyReleased(KeyEvent event) {
                    List<Element> es = navigationTree.getDataBox().getAllElements();
                    for (Element e : es) {
                        e.removeAttachment("ensureVisible");
                    }
                    for (Element e : es) {
                        e.setVisible(isVisible(e));
                        System.out.println(e.getName() + "=" + e.isVisible());
                    }
                    navigationTree.updateTViewUI();
                    navigationTree.expandAll();
                }
            });
        }
        navigationTree.getDataBox().getSelectionModel().addDataBoxSelectionListener(
                new DataBoxSelectionListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void selectionChanged(DataBoxSelectionEvent event) {
                        try {
                            List<Node> selected = event.getBoxSelectionModel().getAllSelectedElement();
                            if (CollectionUtils.isEmpty(selected)) {
                                return;
                            }
                            if (selected.size() == 1) {
                                Node node = selected.get(0);
                                selectedNode = node;
                                Object userObject = node.getUserObject();
                                if (userObject == null) {
                                    return;
                                } else {
                                    List<AbstractTabController<?, ?, ?>> tabs = new ArrayList<AbstractTabController<?, ?, ?>>();
                                    if (MapUtils.isEmpty(tabMap)) {
                                        if (userObject instanceof List)
                                            tabs = (List<AbstractTabController<?, ?, ?>>) userObject;
                                        else {
                                            if (userObject instanceof AbstractTabController)
                                                tabs.add((AbstractTabController<?, ?, ?>) userObject);
                                            else
                                                return;
                                        }
                                    } else {
                                        tabs = tabMap.get(userObject);
                                    }
                                    if (!CollectionUtils.isEmpty(tabs)) {
                                        for (AbstractTabController<?, ?, ?> tab : tabs) {
                                            tab.setSelectNode(node);
                                            tab.initView();
                                        }
                                    }
                                }
                                onSelectOne(node);
                            } else {
                                // TODO 选个多个时处理，目前不支持
                                onSelectMulti(selected);
                            }
                            // updateVerifiers();
                        } catch (Exception ex) {
                            System.err.println(ExceptionUtils.getCommonExceptionInfo(ex));
                        }
                    }
                });
    }

    /**
     * 获取整个导航树的信息
     * @return the navigationTree
     */
    public TTree getNavigationTree() {
        return navigationTree;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getButtons()
     */
    @Override
    public JButton[] getButtons() {
        return new JButton[] { cancelButton };
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<T> collectData() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getHelpId()
     */
    @Override
    public abstract String getHelpId();

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public abstract String getTitle();

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return null;
    }

    /**
     * 获取存储所有Tab页的MAP
     * @return <code>Map<String, List<AbstractTabController<?, ?, ?>>></code> 存储所有Tab页的MAP
     */
    public Map<String, List<AbstractTabController<?, ?, ?>>> getTabMap() {
        return tabMap;
    }

    /**
     * 获取当前选择的导航树节点
     * @return <code>Node</code> 当前选择的导航树节点
     */
    public Node getSelectedNode() {
        return selectedNode;
    }
}
