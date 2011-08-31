package com.yuep.core.client.component.table.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.yuep.core.client.ClientCoreContext;

/**
 * 
 * <p>
 * Title: XTableCellRenderer
 * </p>
 * <p>
 * Description:ʹ�ñ�񵼳���excell����ʱ��Ҫ�̳д��ಢ��дgetRenderVaule����
 * </p>
 * 
 * @author Yao
 * created 2009-7-6 ����11:34:40
 * modified [who date description]
 * check [who date description]
 */
public abstract class XTableCellRenderer extends DefaultTableCellRenderer implements GetRenderVaule {
    private static final long serialVersionUID = 7720511619523768165L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String renderString = getRenderVaule(value);
        if (renderString != null)
            setText(renderString);
        else
            setText("");
        return comp;
    }

    protected String getString(String key) {
        return ClientCoreContext.getString(key);
    }
    
}
