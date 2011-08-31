package com.yuep.core.client.component.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * <p>
 * Title: XRowLayout
 * </p>
 * <p>
 * Description: 自定义布局类，目前不具有通用性，仅在RowPanel中应用
 * 隐藏某一行控件，下面的部分会自动向上对齐，显示隐藏控件行，恢复最初面貌
 * </p>
 * 
 * @author ChenYong
 * created 2010-3-3 下午12:17:31
 * modified [who date description]
 * check [who date description]
 */
class XRowLayout implements LayoutManager, Serializable {

    private static final long serialVersionUID = -7262534875583282631L;

    /**
     * 随容器上下填充
     */
    public final static int FILL = -1;

    /**
     * content高度
     */
    private int contentHeight;

    /**
     * 竖直方向Fill个数
     */
    private int vFills = 0;

    /**
     * 标签列共用的最大宽度值，初始取值为100
     */
    private int maxLbWidth = 60;

    /**
     * JLabel与控件之间的间距以及面板左右两侧的空白宽度
     */
    private int hgap;

    /**
     * 相邻两行控件之间的间距
     */
    private int vgap;

    /**
     * 所有JLabel对象
     */
    private List<Component> lbList = new ArrayList<Component>();

    /**
     * 所有非JLabel对象
     */
    private List<Component> compList = new ArrayList<Component>();

    /**
     * 记录每行高度
     */
    private List<Integer> heights = new ArrayList<Integer>();

    XRowLayout() {
        this(10, 4);
    }

    /**
     * 
     * 左右间隔，上下间隔
     * 
     * @param hgap
     * @param vgap
     */
    XRowLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    /**
     * 指定待添加组件的行高，参数可能不合理，最好作些处理
     * 
     * @param height
     */
    void setNewRowHeight(int height) {
        if (height == XRowLayout.FILL)
            vFills++;

        heights.add(height);
    }

    /**
     * 先添加组件，再布局，comp为面板时最好预先做处理
     */
    public void addLayoutComponent(String name, Component comp) {
        if (comp instanceof JLabel) {
            JLabel lb = (JLabel) comp;
            int lbWidth = lb.getPreferredSize().width;
            if (lbWidth > maxLbWidth)
                this.maxLbWidth = lbWidth;
            return;
        } else if (comp instanceof JPanel) {
            JPanel pane = (JPanel) comp;
            LayoutManager layout = pane.getLayout();
            if (layout instanceof FlowLayout) {
                FlowLayout fl = (FlowLayout) layout;
                fl.setVgap(0); // 防止里面的控件显示不全，无奈之举，处理力度有限
            }
        }
    }

    public void removeLayoutComponent(Component comp) {

    }

    public Dimension preferredLayoutSize(Container target) {
        return minimumLayoutSize(target);
    }

    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int height = insets.top + insets.bottom + vgap * 2;
            int width = insets.left + insets.right + hgap * 2;
            for (int i = 0; i < compList.size(); i++) {
                Component c = compList.get(i);
                int h = heights.get(i);
                if (c.isVisible()) {
                    if (h == XRowLayout.FILL)
                        height += 40; // 此处默认Fill组件最小高度为40，似无用
                    else
                        height += heights.get(i);

                    height += vgap;
                }
            }
            return new Dimension(width, height);
        }
    }

    /**
     * 布局细节
     */
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int rowWidth = target.getWidth() - (insets.left + insets.right + hgap * 2);
            contentHeight = target.getHeight() - (insets.top + insets.bottom + vgap * 4);
            int count = target.getComponentCount();
            int x = insets.left + hgap;
            int y = insets.top + vgap * 2;
            int width = 0;

            Component m = null;
            int row = 0;
            for (int i = 0; i < count; i++) {
                m = target.getComponent(i);
                if (m.isVisible()) {
                    int height = getHeight(y, row);

                    if (m instanceof JLabel) {
                        m.setBounds(x, y, maxLbWidth, height);
                        x += maxLbWidth + hgap;

                        // 添加时调用
                        if (!lbList.contains(m))
                            lbList.add(m);
                    } else {
                        if (x == insets.left + hgap) {
                            width = rowWidth;

                            // 无相应Label控件添加时，添加一个空Label
                            if (lbList.size() == compList.size())
                                lbList.add(null);

                        } else { // 控件左侧有Label时
                            width = rowWidth - maxLbWidth - hgap;
                        }

                        m.setBounds(x, y, width, height);
                        y += height;
                        y += vgap;
                        row++;
                        x = insets.left + hgap; // 下一行又从Label所在列开始绘制

                        // 添加时调用
                        if (!compList.contains(m))
                            compList.add(m);
                    }
                } else if (!(m instanceof JLabel)) {
                    row++; // 控件不可见时，跳过其高度值
                }
            }
        }
    }

    private int getHeight(int y, int row) {
        int height = heights.get(row);

        if (height == XRowLayout.FILL && vFills > 0) {
            return (contentHeight - getUnsizeHeight()) / vFills;
        } else
            return height;
    }

    private int getUnsizeHeight() {
        int total = 0;
        for (int i = 0; i < compList.size(); i++) {
            if (heights.get(i) != XRowLayout.FILL && compList.get(i).isVisible()) {
                total += heights.get(i);
                total += vgap;
            }
        }
        total += vgap;
        return total;
    }

    /**
     * 仅限于RowPanel调用
     * 
     * @param row
     */
    void hideRow(int row) {
        if (row < 0 || row >= compList.size())
            return;

        if (compList.get(row).isVisible() && heights.get(row) == XRowLayout.FILL) {
            vFills--;
        }

        Component c = lbList.get(row);
        if (c != null && c.isVisible())
            c.setVisible(false);

        c = compList.get(row);
        if (c != null && c.isVisible()) {
            c.setEnabled(false); // 使框架系统不再校验
            c.setVisible(false);
        }
    }

    /**
     * 显示第row行，限于RowPanel调用
     * 
     * @param row
     *            从0计行数
     */
    void showRow(int row) {
        if (row < 0 || row >= compList.size())
            return;

        if (!compList.get(row).isVisible() && heights.get(row) == XRowLayout.FILL) {
            vFills++;
        }

        Component c = lbList.get(row);
        if (c != null && !c.isVisible())
            c.setVisible(true);

        c = compList.get(row);
        if (c != null && !c.isVisible()) {
            c.setEnabled(true); // 恢复校验
            c.setVisible(true);
        }
    }

    /**
     * 显示组件c所在行
     * 
     * @param c
     *            非JLabel对象
     */
    public void showRow(Component c) {
        showRow(compList.indexOf(c));
    }

    /**
     * 隐藏组件c所在行
     * 
     * @param c
     */
    public void hideRow(Component c) {
        hideRow(compList.indexOf(c));
    }

    /**
     * @param row
     */
    public void removeRow(int row) {
        if (row < 0 || row >= compList.size())
            return;

        lbList.remove(row);
        compList.remove(row);
        heights.remove(row);
    }

    /**
     * @param c
     */
    public void removeRow(Component c) {
        removeRow(compList.indexOf(c));
    }

    public String toString() {
        return getClass().getName();
    }
    
}