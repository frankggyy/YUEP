/*
 * $Id: SbiBindPropertyDaoImpl.java, 2011-4-15 下午01:17:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.dao.impl;

import java.util.List;

import com.yuep.core.db.access.basedao.BaseDaoUtil;
import com.yuep.core.db.access.basedao.DaoExecute;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.sbimanager.model.SbiBindProperty;
import com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao;

/**
 * <p>
 * Title: SbiBindPropertyDaoImpl
 * </p>
 * <p>
 * Description:
 * SbiBindPropertyDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午01:17:31
 * modified [who date description]
 * check [who date description]
 */
public class SbiBindPropertyDaoImpl implements SbiBindPropertyDao {
    private SessionFactory sessionFactory;
   
    public SbiBindPropertyDaoImpl(SessionFactory sessionFactory){
         this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#createSbiBindProperty(com.yuep.nms.core.common.sbimanager.model.SbiBindProperty)
     */
    @Override
    public void createSbiBindProperty(final SbiBindProperty sbiBindProperty) {
        BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                template.saveBatch(sbiBindProperty);
                return sbiBindProperty;
            }
        });
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#deleteSbiBindPropertyByNe(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbiBindPropertyByNe(final MoNaming ne) {
        BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                template.deleteEntityByProperty("ne", ne);
                return null;
            }
        });
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#deleteSbiBindPropertyBySbiNaming(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteSbiBindPropertyBySbiNaming(final MoNaming sbiNaming) {
        BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                template.deleteEntityByProperty("sbiNaming", sbiNaming);
                return null;
            }
        });
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#getSbiBindProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public SbiBindProperty getSbiBindProperty(final MoNaming ne) {
        return (SbiBindProperty)BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                return template.getUniqueEntityByOneProperty("ne", ne);
            }
        });
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#setSbiBindProperty(com.yuep.nms.core.common.sbimanager.model.SbiBindProperty)
     */
    @Override
    public void setSbiBindProperty(final SbiBindProperty sbiBindProperty) {
         BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                 template.updateBatch(sbiBindProperty);
                 return null;
            }
        });
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao#getSbiBindProperties(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SbiBindProperty> getSbiBindProperties(final MoNaming sbiNaming) {
        return  (List<SbiBindProperty>) BaseDaoUtil.doSql(sessionFactory, SbiBindProperty.class, new DaoExecute<SbiBindProperty>(){
            @Override
            public Object execute(HibernateTemplate<SbiBindProperty> template) {
                 return template.getEntitiesByOneProperty("sbiNaming", sbiNaming);
            }
        });
    }

}
