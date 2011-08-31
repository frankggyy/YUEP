/*
 * $Id: MoNamingUserType.java, 2011-3-28 上午10:46:36 yangtao Exp $
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoNamingUserType
 * </p>
 * <p>
 * Description: MoNaming存入DB时的UserType
 * 
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午10:46:36
 * modified [who date description]
 * check [who date description]
 */
public class MoNamingUserType implements UserType,Serializable {

    private static final long serialVersionUID = -3968937974301097953L;

    @Override
    public Object assemble(Serializable arg0, Object arg1)
            throws HibernateException {
        return arg0;
    }

    @Override
    public Object deepCopy(Object arg0) throws HibernateException {
        return arg0;
    }

    @Override
    public Serializable disassemble(Object arg0) throws HibernateException {
        return (Serializable) arg0;
    }

    @Override
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        return arg0 == arg1;

    }

    @Override
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] arg1, Object arg2)
            throws HibernateException, SQLException {
        if (arg1[0] == null)
            return null;
        String name = rs.getString(arg1[0]);
        if(StringUtils.isEmpty(name))
            return null;
        return new MoNaming(name);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null)
            ps.setString(index, "");
        else
            ps.setString(index, (new MoNaming(value.toString())).toString());
    }

    @Override
    public Object replace(Object arg0, Object arg1, Object arg2)
            throws HibernateException {
        return arg0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class returnedClass() {
        return MoNaming.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Hibernate.STRING.sqlType() };
    }

}
