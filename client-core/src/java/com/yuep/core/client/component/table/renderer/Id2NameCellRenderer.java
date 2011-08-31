package com.yuep.core.client.component.table.renderer;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * ����ID(long,int,string)ת��Ϊһ������ʾ���ַ���
 * @author sufeng
 *
 */
public class Id2NameCellRenderer extends XTableCellRenderer {

	private static final long serialVersionUID = 824210750967383183L;

	@SuppressWarnings("unchecked")
	private Map id2NameMap;
	
	/**
	 * ����ת���ӳ���
	 * @param id2NameMap key������integer,object,string,long,value������string
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
