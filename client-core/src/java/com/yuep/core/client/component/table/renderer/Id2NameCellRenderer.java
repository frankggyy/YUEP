package com.yuep.core.client.component.table.renderer;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 整形ID(long,int,string)转换为一个可显示的字符串
 * @author sufeng
 *
 */
public class Id2NameCellRenderer extends XTableCellRenderer {

	private static final long serialVersionUID = 824210750967383183L;

	@SuppressWarnings("unchecked")
	private Map id2NameMap;
	
	/**
	 * 设置转义的映射表
	 * @param id2NameMap key可以是integer,object,string,long,value必须是string
	 */
	@SuppressWarnings("unchecked")
	public void setId2NameMap(Map id2NameMap) {
		this.id2NameMap = id2NameMap;
	}

	@Override
	public String getRenderVaule(Object value) {
		if(MapUtils.isEmpty(id2NameMap)){
			return value==null ? "" : value.toString();
		}else{
			String name=(String)id2NameMap.get(value);
			return name==null ? "" : name;
		}
	}
	
	

}
