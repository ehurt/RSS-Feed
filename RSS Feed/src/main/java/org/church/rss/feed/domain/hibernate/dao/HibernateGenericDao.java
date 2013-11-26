package org.church.rss.feed.domain.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.church.management.interfaces.entity.Entity;
import org.church.rss.feed.domain.dao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HibernateGenericDao<T extends Entity<?>, I> implements GenericDao<T , I>
{
	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "des";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> persistedClass;
	
	public HibernateGenericDao(Class<T> c)
	{
		this.persistedClass = c;
	}
	
	public SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) 
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(T entity)
	{
		getCurrentSession().persist(entity);
	}
	
	public void delete(T entity)
	{
		getCurrentSession().delete(entity);
	}
	
	public void evict(T entity)
	{
		getCurrentSession().evict(entity);
	}
	
	public T merge(T entity)
	{
		return (T) getCurrentSession().merge(entity);
	}
	
	public T load(Serializable id)
	{
		return (T) getCurrentSession().load(persistedClass, id);
	}
	
	public T get(Serializable id)
	{
		return (T) getCurrentSession().get(persistedClass, id);
	}
	
	public List<T> findAll()
	{
		return getCurrentSession().createCriteria(persistedClass).list();
	}
	
	public boolean exists(T entity)
	{
		return getCurrentSession().get(persistedClass, entity.getId()) != null ? true : false;
	}
	
	public int rowCount()
	{
		Criteria criteria = getCurrentSession().createCriteria(persistedClass).setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}
	
	public List<T> query(String query)
	{
		return getCurrentSession().createQuery(query).list();
	}
	
	public List<T> query(String queryString, List<Object> parameters)
	{
		Query query = getCurrentSession().createQuery(queryString);
		
		for(int i = 0; i < parameters.size(); i++)
		{
			Object parameter = parameters.get(i);
			query.setParameter(i, parameter);
		}
		
		return (List<T>) query.list();
	}

	public Session getCurrentSession() 
	{
		return sessionFactory.getCurrentSession();
	}

	public Session getOpenSession() 
	{
		return sessionFactory.openSession();
	}

	public List<T> findAllOrderBy(String property, String asc) 
	{
		Order order = null;
		
		if(asc.equals(ASCENDING))
		{
			order = Order.asc(property);
		}
		
		else
		{
			order = Order.desc(property);
		}
		
		return this.getCurrentSession().createCriteria(persistedClass).addOrder(order).list();
	}
}
