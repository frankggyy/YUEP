/*
 * $Id: HibernateTemplateImpl.java, 2011-3-22 下午04:58:11 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.hibernate.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.yuep.core.db.session.def.HibernateCallback;
import com.yuep.core.db.session.def.Session;

/**
 * <p>
 * Title: HibernateTemplateImpl
 * </p>
 * <p>
 * Description: HibernateTemplate接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-22 下午04:58:11
 * modified [who date description]
 * check [who date description]
 */
public class HibernateTemplateImpl<T> implements HibernateTemplate<T> {
    
    /**
     * bo类
     */
	private final Class<T> pojoClass;
	
	/**
	 * db session
	 */
	private final Session session;

	public HibernateTemplateImpl(Class<T> pojoClass, Session session) {
		this.pojoClass = pojoClass;
		this.session = session;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#count()
	 */
	@Override
	public int count() {
		String hql = "select count(*) from " + pojoClass.getSimpleName();
		Object obj = session.query(hql);
		List result = (List) obj;
		Long count = (Long) result.get(0);
		return count.intValue();
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#deleteAllEntities()
	 */
	@Override
	public void deleteAllEntities() {
		session.execute("delete from " + pojoClass.getSimpleName());
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#deleteAllEntities(java.util.Collection)
	 */
	@Override
	public void deleteAllEntities(Collection<T> entities) {
		session.delete(new ArrayList<T>(entities));
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#deleteAllEntities(T[])
	 */
	@Override
	public void deleteAllEntities(T... entities) {
		session.delete(entities);
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#deleteEntity(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEntity(T entity) {
		session.delete(entity);
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#deleteEntityByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void deleteEntityByProperty(final String propertyName, final  Object value) {
		final String hql = "delete from " + pojoClass.getSimpleName()
				+ " obj where obj." + propertyName + "=:propertyName";
		session.execute(new HibernateCallback(){
            @Override
            public Object doInHibernate(org.hibernate.Session session) {
                Query query=session.createQuery(hql);
                query.setParameter("propertyName", value);
                query.executeUpdate();
                return null;
            }
		});
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getAllEntities()
	 */
	@Override
	public List<T> getAllEntities() {
		String hql = "from " + pojoClass.getSimpleName();
		return session.query(hql);
	}
 
	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getEntitiesByOneProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
    @Override
	public List<T> getEntitiesByOneProperty(String propertyName, final Object value) {
        final String hql = "from " + pojoClass.getSimpleName()
                + "  bo where bo." + propertyName + "=:propertyName";
        return (List<T>)session.execute(new HibernateCallback(){
            @Override
            public Object doInHibernate(org.hibernate.Session session) {
                Query query=session.createQuery(hql);
                query.setParameter("propertyName", value);
                return query.list();
            }
        });
    
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getEntitiesByOrCondition(java.lang.String,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
    @Override
	public List<T> getEntitiesByOrCondition(final String propertyName,final Object... values) {
	    
	    if(StringUtils.isBlank(propertyName))
	    	throw new IllegalArgumentException();
		if (ArrayUtils.isEmpty(values))
			throw new IllegalArgumentException();
	
		return (List<T>)session.execute(new HibernateCallback(){
            @Override
            public Object doInHibernate(org.hibernate.Session hibernateSession) {
                Criteria c = hibernateSession.createCriteria(pojoClass);
                Disjunction d = Restrictions.disjunction();
                c = c.add(d);
                for (Object value : values) {
                    d.add(Restrictions.eq(propertyName, value));
                }
                return c.list();
            
            }
		});
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getEntitiesByPropNames(java.lang.String[],
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
    @Override
	public List<T> getEntitiesByPropNames(final String[] propertyNames,
			final Object[] values) {
        if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values)
                || propertyNames.length != values.length) {
            throw new IllegalArgumentException("arguments is invalid.");
        }
        return (List<T>) session.execute(
                new HibernateCallback() {
                    @Override
                    public Object doInHibernate(org.hibernate.Session session){
                        Criteria c = session.createCriteria(pojoClass);
                        for (int i = 0; i < propertyNames.length; i++) {
                            if(values[i]!=null)
                                c.add(Restrictions.eq(propertyNames[i], values[i]));
                            else
                                c.add(Restrictions.isNull(propertyNames[i]));
                        }
                        return c.list();
                    }
                });
    }

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getEntityProps(java.lang.String[],
	 *      java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<?> getEntityProps(String[] selectedProps,
			final String[] whereConditions, final Object[] values, boolean distinct) {
        if (ArrayUtils.isEmpty(selectedProps) || whereConditions==null || values==null
                || whereConditions.length != values.length) {
            throw new IllegalArgumentException("getEntityProps arguments is invalid.");
        }
        final StringBuilder sql = new StringBuilder("select ");
        if(distinct)
            sql.append("distinct ");
        for (int i = 0, n = selectedProps.length; i < n; i++) {
            if (i != n - 1) {
                sql.append(selectedProps[i] + ",");
            } else {
                sql.append(selectedProps[i] + " from ");
            }
        }
        
        sql.append(pojoClass.getName()).append(" ");//
        if(whereConditions.length>0)
            sql.append("where ");
        for (int i = 0, n = whereConditions.length; i < n; i++) {
            if (i != n - 1) {
                sql.append(whereConditions[i]);
                sql.append(" = ? and ");
            } else {
                sql.append(whereConditions[i]);
                sql.append(" = ?");
            }
        }
        return (List<?>)session.execute(new HibernateCallback(){
            @Override
            public Object doInHibernate(org.hibernate.Session session) {
                Query query=session.createQuery(sql.toString());
                for(int i=0;i<whereConditions.length;i++){
                    query.setParameter(i, values[i]);
                }
                return query.list();
            }
        });
    }

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getUniqueEntityByOneProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public T getUniqueEntityByOneProperty(String propertyName, Object value) {
        List<T> result=getEntitiesByOneProperty(propertyName, value);
        if(CollectionUtils.isNotEmpty(result))
            return result.get(0);
        return null;
    
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#getUniqueEntityByPropNames(java.lang.String[],
	 *      java.lang.Object[])
	 */
	@Override
	public T getUniqueEntityByPropNames(String[] propertyNames, Object[] values) {
        List<T> result=getEntitiesByPropNames(propertyNames, values);
        if (CollectionUtils.isNotEmpty(result))
            return result.get(0);
        return null;
    
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#isEntityExisted(java.lang.String[],
	 *      java.lang.Object[])
	 */
	@Override
	public boolean isEntityExisted(String[] propertyNames, Object[] values) {
        List<T> entities=getEntitiesByPropNames(propertyNames, values);
        if(CollectionUtils.isNotEmpty(entities)) {
            session.evict(entities);
            return true;
        }
        return false;
    }

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#saveBatch(java.util.Collection)
	 */
	@Override
	public void saveBatch(Collection<T> entities) {
		session.save(new ArrayList<T>(entities));
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#saveBatch(T[])
	 */
	@Override
	public void saveBatch(T... entities) {
		session.save(entities);
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#saveEntity(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveEntity(T entity) {
		session.save(entity);
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#updateBatch(java.util.Collection)
	 */
	@Override
	public void updateBatch(Collection<T> entities) {
		session.update(new ArrayList<T>(entities));
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#updateBatch(T[])
	 */
	@Override
	public void updateBatch(T... entities) {
		session.update(entities);
	}

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#updateEntity(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity(T entity) {
		session.update(entity);
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<T> findByCriteria(String hqlOrSqlExpress) {
	    String condition=null;
	    if(hqlOrSqlExpress==null || hqlOrSqlExpress.equals(""))
	        condition="";
	    else
	        condition=" where " + hqlOrSqlExpress;
	    final String hql = "from " + pojoClass.getSimpleName() + condition;
        return (List<T>) session.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(org.hibernate.Session session) {
                Query query = session.createQuery(hql);
                return query.list();
            }
});
	}

    /**
     * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#merge(T[])
     */
    @Override
    public void merge(T... entities) {
        session.merge(entities);
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.hibernate.template.HibernateTemplate#findPageByQuery(int, int, java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPageByQuery(final int pageNo, final int pageSize, final String hql, final Map map) {
       return  (List<T>)session.execute(new HibernateCallback(){
            @Override
            public Object doInHibernate(org.hibernate.Session session) {
                List<Object> result = null;  
                    Query query = session.createQuery(hql);  
                    Iterator it = map.keySet().iterator();  
                    while (it.hasNext())  
                    {  
                        Object key = it.next();  
                        query.setParameter(key.toString(), map.get(key));  
                    }  
                    query.setFirstResult((pageNo - 1) * pageSize);  
                    query.setMaxResults(pageSize);  
                    result = query.list();  
                    return result;
            }
        });

    }

}
