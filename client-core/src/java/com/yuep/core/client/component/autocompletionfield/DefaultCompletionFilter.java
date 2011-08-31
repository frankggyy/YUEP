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
 * 自动完成控件的默认过滤器
 * 
 * @author William Chen
 */
public class DefaultCompletionFilter implements CompletionFilter {

    private List strList;

    /**
     * 默认数据模型，无数据对象列表
     */
    public DefaultCompletionFilter() {
        strList = new ArrayList<String>(0);
    }

    /**
     * 指定数据列表
     * 
     * @param list
     */
    public DefaultCompletionFilter(List list) {
        strList = list;
    }

    /**
     * 数据过滤，不区分大小写
     * 
     * 由filter接口自动调用，用户不用管
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
