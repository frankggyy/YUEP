package com.yuep.core.client.component.table.renderer;

import java.awt.Component;
import java.util.Map;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.commons.collections.MapUtils;

/**
 * id到名字转换的下拉框renderer,用于table中,被Id2NameComboBoxTableCellEditor使用
 * @author sufeng
 *
 */
public class Id2NameComboBoxRenderer extends BasicComboBoxRenderer{

	private static final long serialVersionUID = 2922036290612779449L;

	private Map<Integer,String> id2NameMap;
	
	public Id2NameComboBoxRenderer(){}
	public Id2NameComboBoxRenderer(Map<Integer,String> id2NameMap){
		setId2NameMap(id2NameMap);
	}
	
	public void setId2NameMap(Map<Integer, String> id2NameMap) {
		this.id2NameMap = id2NameMap;
	}
	
	@Override
    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1) {
        Component comp = super.getListCellRendererComponent(jlist, obj, i, flag, flag1);
        if(obj instanceof Integer){
        	Integer data = (Integer) obj;
        	if(MapUtils.isEmpty(id2NameMap))
        		setText(obj.toString());
        	else
        		setText(id2NameMap.get(data));
        }
        return comp;
    }
	
}
