/*
 * $Id: HibernateTemplate.java, 2011-3-22 下午04:57:14 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.hibernate.template;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: HibernateTemplate
 * </p>
 * <p>
 * Description:
 * Hibernate数据库访问模板
 * </p>
 * 
 * @author yangtao
 * created 2011-3-22 下午04:57:14
 * modified [who date description]
 * check [who date description]
 */
public interface HibernateTemplate <T>{
    /**
     * 总条目数
     * @return
     */
    int count();
    /**
     * 删除所有的对象
     */
    void deleteAllEntities();

    /**
     * 删除所有实体
     * 
     * @param entities
     */
    void deleteAllEntities(Collection<T> entities);

    /**
     * 删除指定的1个或多个实体
     * @param entities
     */
    void deleteAllEntities(T... entities);

    /**
     * 删除单个实体
     * 
     * @param entity
     * 
     */
    void deleteEntity(T entity);

    /**
     * 根据指定的属性名-值，删除实体
     * 
     * @param propertyName
     * @param value
     */
    void deleteEntityByProperty(String propertyName, final Object value);

    /**
     * 查询所有实体
     * 
     * @param entityName
     * @return
     */
    List<T> getAllEntities();

    /**
     * 根据实体对象的某个属性查询实体对象，举个例子如下： from Ne where type = 'OLT';
     * 
     * @param propertyName
     *            实体对象的属性名
     * @param value
     *            属性对应的值
     * 
     * @return 符合该特定查询条件的多个实体
     */
    List<T> getEntitiesByOneProperty(final String propertyName,final Object value);

    /**
     * 根据SQL的or条件查询，举个例子如下： from Ne where (type = 'OLT' or type =
     * 'ONU');
     * 
     * <p>
     * Notes: 请在进行or查询时调用这个方法
     * 
     * @param propertyName
     *            属性名
     * @param values
     *            属性的可能值
     * 
     * @return 符合条件的记录
     */
    List<T> getEntitiesByOrCondition(final String propertyName,
            final Object... values);

    /**
     * 根据实体对象的多个属性查询实体对象，举个例子如下： from Ne where type = 'OLT' and
     * temp = false;
     * 
     * @param propertyNames
     *            匹配的属性名
     * @param values
     *            属性对应的值
     * 
     * @return 符合该特定查询条件的多个实体
     */
    List<T> getEntitiesByPropNames(final String[] propertyNames,final Object[] values);

    /**
     * 操作实体的全名称，如：MapNode.class.getName()、MapLink.class.getName()等
     * 
     * @return
     */
    List<?> getEntityProps(final String[] selectedProps,
            final String[] whereConditions, final Object[] values, boolean distinct);

    /**
     * 根据实体对象的某个属性查询唯一的实体对象
     * 
     * @param propertyName
     *            实体对象的属性名
     * @param value
     *            属性对应的值
     * 
     * @return 符合该特定查询条件的唯一实体，如果没有找到，则返回null
     */
    T getUniqueEntityByOneProperty(final String propertyName, final Object value);

    /**
     * 根据实体对象的多个属性查询实体对象，举个例子如下： from Ne where type = 'OLT' and
     * temp = false and ip = '192.168.0.8'; 在这个例子中，我知道查询只会返回唯一值
     * 
     * <p>
     * 在你确定查询返回唯一一个值的情况下调用这个方法，否则请使用{
     * {@link #getEntitiesByPropNames(String[], Object[])}
     * 
     * @param propertyNames
     *            匹配的属性名
     * @param values
     *            属性对应的值
     * 
     * @return 符合该特定查询条件的唯一实体
     */
    T getUniqueEntityByPropNames(final String[] propertyNames,
            final Object[] values);

    /**
     * 判断指定条件的实体对象是否存在
     * 
     * @param propertyNames
     * @param values
     * @return 返回true如果指定条件的对象存在，否则返回false
     */
    boolean isEntityExisted(final String[] propertyNames, final Object[] values);

    /**
     * 保存实体
     * 
     * @param entity
     * @return hibernate id
     */
    void saveEntity(T entity);

    /**
     * 更新单个实体
     * 
     * @param entity
     * 
     */
    void updateEntity(T entity);

    /**
     * 批量保存
     * @param entities
     */
    public void saveBatch(final Collection<T> entities);
     
    /**
     * 批量保存
     * @param entities
     */
    public void saveBatch(final T... entities);
   
    /**
     * 批量更新
     * @param entities
     */
    public void updateBatch(final Collection<T> entities);
    
    /**
     * 批量更新
     * @param entities
     */
    public void updateBatch(T... entities);
    
    /**
     * merge
     * @param entities
     */
    public void merge(T...entities);
    
    /**
     * 按条件查询
     * @param hqlExpress 条件语句
     * @return
     */
    public List<T> findByCriteria(String hqlExpress);
  
    /**
     * 分页查询
     * @param pageNo
     *         页号
     * @param pageSize
     *         页面记录数
     * @param hql
     *         hql语句
     * @param map
     *         hql语句参数值(Hibernate Query Language)
     * @return
     */
    public List<T> findPageByQuery(int pageNo, int pageSize, String hql,  Map map);

}
