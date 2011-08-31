/*
 * $Id: XmlParseUtil.java, 2009-2-9 ����02:35:57 victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: XmlParseUtil
 * </p>
 * <p>
 * Description: XML����������,�ܴ�file,url,inputstream�ж�ȡXML�ļ�
 * </p>
 * 
 * @author victor
 * created 2008-12-16 ����10:53:56
 * modified [who date description]
 * check [who date description]
 */
public class XmlParseUtil {
    
    private URL xmlFile = null;
    private InputStream inputStream=null;
    
    private Element root = null;

    /**
     * @param root
     * @param nameAttr
     * @return
     */
    public static String getNodeAttr(Element node, String nameAttr) {
        return node.getAttributeValue(nameAttr);
    }

    /**
     * @param node
     * @param nameAttr
     * @param string
     * @return
     */
    public static String getNodeAttr(Element node, String nameAttr, String defaultValue) {
        return node.getAttributeValue(nameAttr, defaultValue);
    }
    
    /**
     * xml file url
     * 
     * @param file
     */
    public XmlParseUtil(URL file) {
        this.xmlFile = file;
        parseByFile();
    }

    public XmlParseUtil(InputStream inputStream){
        this.inputStream=inputStream;
        parseByInputStream();
    }
    

    private void parseByFile() {
        
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(xmlFile);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            throw new YuepException(ex);
        } catch (IOException ex) {
            throw new YuepException(ex);
        }
    }
    
    private void parseByInputStream() {

        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(inputStream);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            throw new YuepException(ex);
        } catch (IOException ex) {
            throw new YuepException(ex);
        }
    }

    /**
     * @return
     */
    public Element getDocumentElement() {
        return root;
    }
    
    /**
     * �ر�XML�ļ�
     */
    public void close(){
        if(inputStream!=null){
            try{
                inputStream.close();
            }catch(Exception ex){
                throw new YuepException(ex);
            }
        }
    }
    
}
