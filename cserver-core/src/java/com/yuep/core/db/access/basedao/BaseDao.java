/*
 * $Id: BaseDao.java, 2011-3-31 下午01:37:51 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.basedao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yuep.core.db.access.hibernate.template.HibernateTemplate;

/**
 * <p>
 * Title: BaseDao
 * </p>
 * <p>
 * Description: dao基类
 * </p>
 * 
 * @author sufeng
 * created 2011-3-31 下午01:37:51
 * modified [who date description]
 * check [who date description]
 */
public class BaseDao<T> implements HibernateTemplate<T>{

    /**
     * bo类
     */
    private Class<T> clazz;
    
    public BaseDao(Class<T> clazz){
        this.clazz=clazz;
    }
    
    @Override
    public int count() {
        return (Integer)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
              return template.count();
            }  
        });
    }

    @Override
    public void deleteAllEntities() {
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.deleteAllEntities();
                return null;
            }  
        });
    }

    @Override
    public void deleteAllEntities(final Collection<T> entities) {
        if(entities==null || entities.size()==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.deleteAllEntities(entities);
                return null;
            }  
        });
    }

    @Override
    public void deleteAllEntities(final T... entities) {
        if(entities==null || entities.length==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.deleteAllEntities(entities);
                return null;
            }  
        });
    }

    @Override
    public void deleteEntity(final T entity) {
        if(entity==null)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.deleteEntity(entity);
                return null;
            }  
        });
    }

    @Override
    public void deleteEntityByProperty(final String propertyName, final Object value) {
        if(propertyName==null || propertyName.equals(""))
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.deleteEntityByProperty(propertyName,value);
                return null;
            }  
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(final String hqlExpress) {
        if(hqlExpress==null || hqlExpress.equals(""))
            return null;
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.findByCriteria(hqlExpress);
            }  
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAllEntities() {
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getAllEntities();
            }  
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getEntitiesByOneProperty(final String propertyName, final Object value) {
        if(propertyName==null || propertyName.equals(""))
            return null;
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getEntitiesByOneProperty(propertyName,value);
            }  
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getEntitiesByOrCondition(final String propertyName, final Object... values) {
        if(propertyName==null || propertyName.equals(""))
            return null;
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getEntitiesByOrCondition(propertyName,values);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getEntitiesByPropNames(final String[] propertyNames, final Object[] values) {
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getEntitiesByPropNames(propertyNames,values);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<?> getEntityProps(final String[] selectedProps, final String[] whereConditions, final Object[] values, final boolean distinct) {
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getEntityProps(selectedProps,whereConditions,values,distinct);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getUniqueEntityByOneProperty(final String propertyName, final Object value) {
        return (T)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getUniqueEntityByOneProperty(propertyName,value);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getUniqueEntityByPropNames(final String[] propertyNames, final Object[] values) {
        return (T)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.getUniqueEntityByPropNames(propertyNames,values);
            }
        });
    }

    @Override
    public boolean isEntityExisted(final String[] propertyNames, final Object[] values) {
        return (Boolean)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.isEntityExisted(propertyNames,values);
            }
        });
    }

    @Override
    public void saveBatch(final Collection<T> entities) {
        if(entities==null || entities.size()==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.saveBatch(entities);
                return null;
            }
        });
    }

    @Override
    public void saveBatch(final T... entities) {
        if(entities==null || entities.length==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.saveBatch(entities);
                return null;
            }
        });
    }

    @Override
    public void saveEntity(final T entity) {
        if(entity==null)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.saveEntity(entity);
                return null;
            }
        });
    }

    @Override
    public void updateBatch(final Collection<T> entities) {
        if(entities==null || entities.size()==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.updateBatch(entities);
                return null;
            }
        });
    }

    @Override
    public void updateBatch(final T... entities) {
        if(entities==null || entities.length==0)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.updateBatch(entities);
                return null;
            }
        });
    }

    @Override
    public void updateEntity(final T entity) {
        if(entity==null)
            return;
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.updateEntity(entity);
                return null;
            }
        });
    }

    /**
     * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#merge(T[])
     */
    @Override
    public void merge(final T... entities) {
        BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                template.merge(entities);
                return null;
            }
        });
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#findPageByQuery(int, int, java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPageByQuery(final int pageNo, final int pageSize, final String hql, final Map map) {
        return (List<T>)BaseDaoUtil.doSql(clazz,new DaoExecute<T>(){
            @Override
            public Object execute(HibernateTemplate<T> template) {
                return template.findPageByQuery(pageNo, pageSize, hql, map);
            }
        });
    }

}
