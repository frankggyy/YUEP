/*
 * $Id: HibernateTemplate.java, 2011-3-22 ����04:57:14 yangtao Exp $
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
 * Hibernate���ݿ����ģ��
 * </p>
 * 
 * @author yangtao
 * created 2011-3-22 ����04:57:14
 * modified [who date description]
 * check [who date description]
 */
public interface HibernateTemplate <T>{
    /**
     * ����Ŀ��
     * @return
     */
    int count();
    /**
     * ɾ�����еĶ���
     */
    void deleteAllEntities();

    /**
     * ɾ������ʵ��
     * 
     * @param entities
     */
    void deleteAllEntities(Collection<T> entities);

    /**
     * ɾ��ָ����1������ʵ��
     * @param entities
     */
    void deleteAllEntities(T... entities);

    /**
     * ɾ������ʵ��
     * 
     * @param entity
     * 
     */
    void deleteEntity(T entity);

    /**
     * ����ָ����������-ֵ��ɾ��ʵ��
     * 
     * @param propertyName
     * @param value
     */
    void deleteEntityByProperty(String propertyName, final Object value);

    /**
     * ��ѯ����ʵ��
     * 
     * @param entityName
     * @return
     */
    List<T> getAllEntities();

    /**
     * ����ʵ������ĳ�����Բ�ѯʵ����󣬾ٸ��������£� from Ne where type = 'OLT';
     * 
     * @param propertyName
     *            ʵ������������
     * @param value
     *            ���Զ�Ӧ��ֵ
     * 
     * @return ���ϸ��ض���ѯ�����Ķ��ʵ��
     */
    List<T> getEntitiesByOneProperty(final String propertyName,final Object value);

    /**
     * ����SQL��or������ѯ���ٸ��������£� from Ne where (type = 'OLT' or type =
     * 'ONU');
     * 
     * <p>
     * Notes: ���ڽ���or��ѯʱ�����������
     * 
     * @param propertyName
     *            ������
     * @param values
     *            ���ԵĿ���ֵ
     * 
     * @return ���������ļ�¼
     */
    List<T> getEntitiesByOrCondition(final String propertyName,
            final Object... values);

    /**
     * ����ʵ�����Ķ�����Բ�ѯʵ����󣬾ٸ��������£� from Ne where type = 'OLT' and
     * temp = false;
     * 
     * @param propertyNames
     *            ƥ���������
     * @param values
     *            ���Զ�Ӧ��ֵ
     * 
     * @return ���ϸ��ض���ѯ�����Ķ��ʵ��
     */
    List<T> getEntitiesByPropNames(final String[] propertyNames,final Object[] values);

    /**
     * ����ʵ���ȫ���ƣ��磺MapNode.class.getName()��MapLink.class.getName()��
     * 
     * @return
     */
    List<?> getEntityProps(final String[] selectedProps,
            final String[] whereConditions, final Object[] values, boolean distinct);

    /**
     * ����ʵ������ĳ�����Բ�ѯΨһ��ʵ�����
     * 
     * @param propertyName
     *            ʵ������������
     * @param value
     *            ���Զ�Ӧ��ֵ
     * 
     * @return ���ϸ��ض���ѯ������Ψһʵ�壬���û���ҵ����򷵻�null
     */
    T getUniqueEntityByOneProperty(final String propertyName, final Object value);

    /**
     * ����ʵ�����Ķ�����Բ�ѯʵ����󣬾ٸ��������£� from Ne where type = 'OLT' and
     * temp = false and ip = '192.168.0.8'; ����������У���֪����ѯֻ�᷵��Ψһֵ
     * 
     * <p>
     * ����ȷ����ѯ����Ψһһ��ֵ������µ������������������ʹ��{
     * {@link #getEntitiesByPropNames(String[], Object[])}
     * 
     * @param propertyNames
     *            ƥ���������
     * @param values
     *            ���Զ�Ӧ��ֵ
     * 
     * @return ���ϸ��ض���ѯ������Ψһʵ��
     */
    T getUniqueEntityByPropNames(final String[] propertyNames,
            final Object[] values);

    /**
     * �ж�ָ��������ʵ������Ƿ����
     * 
     * @param propertyNames
     * @param values
     * @return ����true���ָ�������Ķ�����ڣ����򷵻�false
     */
    boolean isEntityExisted(final String[] propertyNames, final Object[] values);

    /**
     * ����ʵ��
     * 
     * @param entity
     * @return hibernate id
     */
    void saveEntity(T entity);

    /**
     * ���µ���ʵ��
     * 
     * @param entity
     * 
     */
    void updateEntity(T entity);

    /**
     * ��������
     * @param entities
     */
    public void saveBatch(final Collection<T> entities);
     
    /**
     * ��������
     * @param entities
     */
    public void saveBatch(final T... entities);
   
    /**
     * ��������
     * @param entities
     */
    public void updateBatch(final Collection<T> entities);
    
    /**
     * ��������
     * @param entities
     */
    public void updateBatch(T... entities);
    
    /**
     * merge
     * @param entities
     */
    public void merge(T...entities);
    
    /**
     * ��������ѯ
     * @param hqlExpress �������
     * @return
     */
    public List<T> findByCriteria(String hqlExpress);
  
    /**
     * ��ҳ��ѯ
     * @param pageNo
     *         ҳ��
     * @param pageSize
     *         ҳ���¼��
     * @param hql
     *         hql���
     * @param map
     *         hql������ֵ(Hibernate Query Language)
     * @return
     */
    public List<T> findPageByQuery(int pageNo, int pageSize, String hql,  Map map);

}
