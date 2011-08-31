/*
 * $Id: Decorator.java, 2010-10-15 ����03:26:17 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator;


/**
 * <p>
 * Title: Decorator
 * </p>
 * <p>
 * Description:�ͻ��˿ؼ���װ����,�ǿͻ��˿ؼ���չ�ӿ�
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 ����03:26:17
 * modified [who date description]
 * check [who date description]
 */
public interface Decorator<T> {

    /**
     * ����ؼ�,�����ع���Ŀؼ�����
     * @return T ����Ŀؼ�
     */
    T contructEditor();
    
    /**
     * װ�οؼ����Բ����еĿؼ�����װ��
     * @param t Ҫ����װ�εĿؼ�����
     */
    void decorate(T t);
}
