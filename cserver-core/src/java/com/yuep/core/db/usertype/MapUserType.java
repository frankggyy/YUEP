/*
 * $Id: MapUserType.java, 2009-2-19 上午09:30:12 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * <p>
 * Title: MapUserType
 * </p>
 * <p>
 * Description:
 *       java.utils.Map类型的ORM映射自定义类型
 * </p>
 * 
 * @author yangtao
 * created 2009-2-19 上午09:30:12
 * modified [who date description]
 * check [who date description]
 */
public class MapUserType implements UserType {

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public Class returnedClass() {

        return String.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
     *      java.lang.Object)
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (x == null || y == null)
            return false;
        return x.equals(y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object arg0) throws HibernateException {

        return arg0.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
     *      java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {

        String value = resultSet.getString(names[0]);
        if (resultSet.wasNull())
            return null;
        return fromString(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement,
     *      java.lang.Object, int)
     */
    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, toString((Map) value));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object arg0) throws HibernateException {

        return arg0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    public Serializable disassemble(Object arg0) throws HibernateException {

        return (Serializable) arg0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
     *      java.lang.Object)
     */
    public Object assemble(Serializable arg0, Object arg1)
            throws HibernateException {
        return arg0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     */
    public Object replace(Object arg0, Object arg1, Object arg2)
            throws HibernateException {

        return arg0;
    }

    public static Map fromString(String value) {
        if (value==null) return null;
        StringTokenizer tokenizer = new StringTokenizer(value,"\n\r");
        String line ;
        int pos;
        Map<String,String> map = new HashMap<String,String>(5);
        while(tokenizer.hasMoreElements()){
            line = tokenizer.nextToken();
            pos = line.indexOf('=');
            if (pos>0){
                map.put(line.substring(0,pos),StringEscapeUtils.unescapeJava(line.substring(pos+1)));
            }
        }
        return map;
    }

    public static String toString(Map map) {
        if (map==null) return null;
        StringBuffer buf = new StringBuffer();
        Map.Entry entry;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            if(entry.getValue()!=null)
                buf.append(entry.getKey()).append('=');
                escape(buf,(String) entry.getValue());
                buf.append('\n');
        }
        return buf.toString();
    }

    /**
     * @param entry
     * @return
     */
    private static void escape(StringBuffer buf,String str) {
//      buf.append(StringEscapeUtils.escapeJava(str));
        if (str==null) return;
        int len = str.length();
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if(c=='\n')
                buf.append("\\n");
            else if(c=='\r')
                buf.append("\\r");
            else if(c=='\\')
                buf.append("\\\\");
            else
                buf.append(c);
        }
    }
}
