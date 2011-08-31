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
 * Description: �Զ��岼���࣬Ŀǰ������ͨ���ԣ�����RowPanel��Ӧ��
 * ����ĳһ�пؼ�������Ĳ��ֻ��Զ����϶��룬��ʾ���ؿؼ��У��ָ������ò
 * </p>
 * 
 * @author ChenYong
 * created 2010-3-3 ����12:17:31
 * modified [who date description]
 * check [who date description]
 */
class XRowLayout implements LayoutManager, Serializable {

    private static final long serialVersionUID = -7262534875583282631L;

    /**
     * �������������
     */
    public final static int FILL = -1;

    /**
     * content�߶�
     */
    private int contentHeight;

    /**
     * ��ֱ����Fill����
     */
    private int vFills = 0;

    /**
     * ��ǩ�й��õ������ֵ����ʼȡֵΪ100
     */
    private int maxLbWidth = 60;

    /**
     * JLabel��ؼ�֮��ļ���Լ������������Ŀհ׿��
     */
    private int hgap;

    /**
     * �������пؼ�֮��ļ��
     */
    private int vgap;

    /**
     * ����JLabel����
     */
    private List<Component> lbList = new ArrayList<Component>();

    /**
     * ���з�JLabel����
     */
    private List<Component> compList = new ArrayList<Component>();

    /**
     * ��¼ÿ�и߶�
     */
    private List<Integer> heights = new ArrayList<Integer>();

    XRowLayout() {
        this(10, 4);
    }

    /**
     * 
     * ���Ҽ�������¼��
     * 
     * @param hgap
     * @param vgap
     */
    XRowLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    /**
     * ָ�������������иߣ��������ܲ����������Щ����
     * 
     * @param height
     */
    void setNewRowHeight(int height) {
        if (height == XRowLayout.FILL)
            vFills++;

        heights.add(height);
    }

    /**
     * �����������ٲ��֣�compΪ���ʱ���Ԥ��������
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
                fl.setVgap(0); // ��ֹ����Ŀؼ���ʾ��ȫ������֮�٣�������������
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
                        height += 40; // �˴�Ĭ��Fill�����С�߶�Ϊ40��������
                    else
                        height += heights.get(i);

                    height += vgap;
                }
            }
            return new Dimension(width, height);
        }
    }

    /**
     * ����ϸ��
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

                        // ���ʱ����
                        if (!lbList.contains(m))
                            lbList.add(m);
                    } else {
                        if (x == insets.left + hgap) {
                            width = rowWidth;

                            // ����ӦLabel�ؼ����ʱ�����һ����Label
                            if (lbList.size() == compList.size())
                                lbList.add(null);

                        } else { // �ؼ������Labelʱ
                            width = rowWidth - maxLbWidth - hgap;
                        }

                        m.setBounds(x, y, width, height);
                        y += height;
                        y += vgap;
                        row++;
                        x = insets.left + hgap; // ��һ���ִ�Label�����п�ʼ����

                        // ���ʱ����
                        if (!compList.contains(m))
                            compList.add(m);
                    }
                } else if (!(m instanceof JLabel)) {
                    row++; // �ؼ����ɼ�ʱ��������߶�ֵ
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
     * ������RowPanel����
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
            c.setEnabled(false); // ʹ���ϵͳ����У��
            c.setVisible(false);
        }
    }

    /**
     * ��ʾ��row�У�����RowPanel����
     * 
     * @param row
     *            ��0������
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
            c.setEnabled(true); // �ָ�У��
            c.setVisible(true);
        }
    }

    /**
     * ��ʾ���c������
     * 
     * @param c
     *            ��JLabel����
     */
    public void showRow(Component c) {
        showRow(compList.indexOf(c));
    }

    /**
     * �������c������
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