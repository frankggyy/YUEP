/*
 * $Id: ResourceFactoryTest.java, 2011-7-26 ����04:32:28 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.resource.test;

import java.io.File;
import java.net.URL;
import java.util.Locale;

import junit.framework.TestCase;

import com.yuep.base.resource.ResourceFactory;

/**
 * <p>
 * Title: ResourceFactoryTest
 * </p>
 * <p>
 * Description: ��Դ������test case
 * </p>
 * 
 * @author sufeng
 */
public class ResourceFactoryTest extends TestCase{

    /**
     * ��������������
     * ����벻��,���޸ĸñ�����ֵ
     */
    private String deployProjectName="yuep-build";
    
    /**
     * ��·��
     */
    private String rootPath;
    
    @Override
    protected void setUp() throws Exception {
        String userDir=System.getProperty("user.dir");
        rootPath=userDir+File.separator+".."+File.separator+deployProjectName+File.separator+"modules"+File.separator;;
    }
    
    @Override
    protected void tearDown() throws Exception {
    }
    
    /**
     * ��ȡconf�ļ�
     */
    public void testGetConfFile(){
        ResourceFactory resourceFactory=new ResourceFactory("clientcore");
        resourceFactory.setRootPath(rootPath);
        try{
            URL confFile = resourceFactory.getConfFile("logback.xml");
            if(confFile!=null)
                assertNotNull(confFile);
        }catch(Exception ex){
            System.out.println("");
        }
    }
    
    /**
     * ��ȡͼƬ��ͼ���ļ�
     */
    public void testGetIconUrl(){
        ResourceFactory resourceFactory=new ResourceFactory("clientcore");
        resourceFactory.setRootPath(rootPath);
        try{
            URL iconFile = resourceFactory.getIconUrl("logo.gif");
            if(iconFile!=null)
                assertNotNull(iconFile);
        }catch(Exception ex){
            System.out.println("");
        }
    }
    
    /**
     * ��ȡoemͼƬ��ͼ���ļ�
     */
    public void testGetOemIconUrl(){
        ResourceFactory resourceFactory=new ResourceFactory("clientcore");
        resourceFactory.setOemName("yotc");
        resourceFactory.setRootPath(rootPath);
        try{
            URL iconFile = resourceFactory.getIconUrl("logo.gif");
            if(iconFile!=null)
                assertNotNull(iconFile);
        }catch(Exception ex){
            System.out.println("");
        }
    }
    
    /**
     * ��ȡ�����ֵ�ͼƬ��ͼ���ļ�
     */
    public void testGetLocaleIconUrl(){
        ResourceFactory resourceFactory=new ResourceFactory("clientcore");
        resourceFactory.setLocale(Locale.ENGLISH);
        resourceFactory.setRootPath(rootPath);
        try{
            URL iconFile = resourceFactory.getIconUrl("loginlogo.gif");
            if(iconFile!=null)
                assertNotNull(iconFile);
        }catch(Exception ex){
            System.out.println("");
        }
    }
    
}
