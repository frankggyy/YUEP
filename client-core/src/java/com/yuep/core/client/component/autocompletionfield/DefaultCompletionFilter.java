/*
 * DefaultCompletionFilter.java
 *
 * Created on 2007-6-21, 23:18:58
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yuep.core.client.component.autocompletionfield;

import java.util.ArrayList;
import java.util.List;

/**
 * �Զ���ɿؼ���Ĭ�Ϲ�����
 * 
 * @author William Chen
 */
public class DefaultCompletionFilter implements CompletionFilter {

    private List strList;

    /**
     * Ĭ������ģ�ͣ������ݶ����б�
     */
    public DefaultCompletionFilter() {
        strList = new ArrayList<String>(0);
    }

    /**
     * ָ�������б�
     * 
     * @param list
     */
    public DefaultCompletionFilter(List list) {
        strList = list;
    }

    /**
     * ���ݹ��ˣ������ִ�Сд
     * 
     * ��filter�ӿ��Զ����ã��û����ù�
     * 
     * @see com.ycignp.veapo.client.framework.component.autocompletionfield.CompletionFilter#filter(java.lang.String)
     */
    public List filter(String text) {
        List list = new ArrayList();
        String txt = text.trim();
        int length = txt.length();
        for (Object str : strList) {
            if (length == 0 || str.toString().toLowerCase().startsWith(txt.toLowerCase()))
                list.add(str);
        }
        return list;
    }
}
