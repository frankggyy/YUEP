package com.yuep.core.client.component.table.editor;

import java.awt.Component;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import org.apache.commons.collections.MapUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.ComboBoxEditorDecorator;
import com.yuep.core.client.component.table.renderer.Id2NameComboBoxRenderer;

/**
 * @author sufeng
 *
 */
public class Id2NameComboBoxEditor extends BasicComboBoxEditor{

    private SwingFactory swingFactory;
	private JComboBox comboBox;
	private Map<Integer,String> id2NameMap;
	private Id2NameComboBoxRenderer renderer;
	
	public Id2NameComboBoxEditor(){
	    swingFactory = ClientCoreContext.getSwingFactory();
	    comboBox=swingFactory.getXEditor(new ComboBoxEditorDecorator("id2name"));
	}
	
	public Id2NameComboBoxEditor(Map<Integer,String> id2NameMap){
	    this();
		setId2NameMap(id2NameMap);
	}
	
	public void setId2NameMap(Map<Integer, String> id2NameMap) {
		this.id2NameMap=id2NameMap;
		renderer=new Id2NameComboBoxRenderer(this.id2NameMap);
		comboBox.setRenderer(renderer);
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
	}
	
	@Override
	public Component getEditorComponent() {
		return comboBox;
	}
	
	
}
