/*
 * $Id: CommonListUserType.java, 2009-2-25 上午10:42:51 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * <p>
 * Title: CommonListUserType
 * </p>
 * <p>
 * Description: 通用List映射类型
 * </p>
 * 
 * @author yangtao
 * created 2009-2-25 上午10:42:51
 * modified [who date description]
 * check [who date description]
 */
public class CommonListUserType implements EnhancedUserType, ParameterizedType,Serializable {

    private static final long serialVersionUID = -297890547687871876L;

    private Class elementClass;

    private ListElementUserType userType;

    public void setParameterValues(Properties parameters) {
        String userTypeStr = parameters.getProperty("userType");
        String elementClassStr = parameters.getProperty("elementClass");
        try {
            elementClass = Class.forName(elementClassStr);
            userType = ((Class<ListElementUserType>) Class.forName(userTypeStr))
                    .newInstance();
        } catch (Exception e) {
            throw new HibernateException("", e);
        }

    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (x == null || y == null)
            return false;
        return x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (rs.wasNull())
            return null;
        return fromXMLString(value);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, toXMLString((List) value));
        }
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    public Class returnedClass() {
        return String.class;
    }

    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.EnhancedUserType#fromXMLString(java.lang.String)
     */
    @Override
    public Object fromXMLString(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, "\n");
        String line;
        int pos;
        List list = new ArrayList<String>();
        while (tokenizer.hasMoreElements()) {
            line = tokenizer.nextToken();
            Object obj = userType.toObject(line, elementClass);
            list.add(obj);
        }

        return list;
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.EnhancedUserType#objectToSQLString(java.lang.Object)
     */
    @Override
    public String objectToSQLString(Object obj) {
        return '\'' + toXMLString(obj) + '\'';
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see org.hibernate.usertype.EnhancedUserType#toXMLString(java.lang.Object)
     */
    @Override
    public String toXMLString(Object obj1) {
        List list = (List) obj1;
        StringBuffer buf = new StringBuffer();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object obj = (Object) iterator.next();
            String str = userType.toString(obj, elementClass);
            buf.append(str).append("\n");
        }
        return buf.toString();
    }

}
