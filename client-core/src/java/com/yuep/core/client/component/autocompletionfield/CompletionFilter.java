/*
 * AutoCompletionFilter.java
 * 
 * Created on 2007-6-21, 22:57:11
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yuep.core.client.component.autocompletionfield;

import java.util.List;

/**
 * 自动完成控件的默认过滤器接口
 * 
 * @author William Chen
 */
public interface CompletionFilter {
    
    /**
     * 过滤返回的列表
     * 
     * @param text pattern
     * @return
     */
    List filter(String text);
}
