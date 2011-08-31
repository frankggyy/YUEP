/*
 * $Id: JarPathFileSearcher.java, 2011-1-7 ����02:21:44 Owner Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource;

import java.util.List;

/**
 * <p>
 * Title: JarPathFileSearcher
 * </p>
 * <p>
 * Description:
 * <br>��Jar�������������������ļ�
 * </p>
 * 
 * @author yangtao
 * created 2011-1-7 ����02:21:44
 * modified [who date description]
 * check [who date description]
 */
public interface JarPathFileSearcher {
    /**
     * ��ָ��Jar���������ļ�
     * @param jarPath
     *        jar��·��
     * @param fileNameMatchPattern
     *        �ļ�����ƥ�����,����**yuep*.txt
     * @return
     */
    public List<String> search(String jarPath,String fileNameMatchPattern);

}
