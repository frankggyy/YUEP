package com.yuep.core.client.component.table.editor;

import java.awt.Component;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.commons.collections.MapUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.table.renderer.Id2NameComboBoxRenderer;

/**
 * 表格中数字名字下拉框的editor
 * @author sufeng
 *
 */
@SuppressWarnings("unchecked")
public class Id2NameComboBoxTableCellEditor extends AbstractCellEditor implements TableCellEditor{

	private static final long serialVersionUID = 7779029290458245881L;
	
	private SwingFactory swingFactory;
	private JComboBox comboBox;
	private Map<Integer,String> id2NameMap;
	private Id2NameComboBoxRenderer renderer;
	
	public void setId2NameMap(Map<Integer, String> id2NameMap) {
		this.id2NameMap = id2NameMap;
		renderer.setId2NameMap(id2NameMap);
	}
	
	public Id2NameComboBoxTableCellEditor(){
	    swingFactory = ClientCoreContext.getSwingFactory();
	    comboBox=swingFactory.getXEditor(new ComboBoxEditorDecorator("id2name"));
		renderer = new Id2NameComboBoxRenderer();
        comboBox.setRenderer(renderer);
	}
	
	public JComboBox getJComboBox(){
		return comboBox;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		comboBox.removeAllItems();
		try {
			if(MapUtils.isNotEmpty(id2NameMap)){
			    for(Iterator<Integer> it=id2NameMap.keySet().iterator(); it.hasNext();) {
			    	Integer code=it.next();
			    	comboBox.addItem(code);
	            }
			}
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!(table instanceof XTable))
            return comboBox;
        XTable xtable = (XTable) table;
        Object valueAt = xtable.getValueAt(row, column);
        if (valueAt != null && valueAt instanceof Integer) {
        	Integer data = (Integer) valueAt;
            comboBox.setSelectedItem(data);
        }else{
            comboBox.setSelectedItem(null);
        }
        comboBox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        comboBox.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        return comboBox;
	}

	@Override
	public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	}

	
}
