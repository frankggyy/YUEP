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
 * �Զ���ɿؼ���Ĭ�Ϲ������ӿ�
 * 
 * @author William Chen
 */
public interface CompletionFilter {
    
    /**
     * ���˷��ص��б�
     * 
     * @param text pattern
     * @return
     */
    List filter(String text);
}
