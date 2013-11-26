package org.church.rss.feed.domain.dao;

import java.io.Serializable;
import java.util.List;

import org.church.management.interfaces.entity.Entity;

import org.hibernate.Session;


public interface GenericDao<T extends Entity<?>, I>
{
	
	public Session getCurrentSession();
	
	public Session getOpenSession();
	
	public void persist(T entity);
	
	public void delete(T entity);
	
	public void evict(T entity);
	
	public T merge(T entity);
	
	public T load(Serializable id);
	
	public T get(Serializable id);
	
	public List<T> findAll();
	
	public List<T> findAllOrderBy(String value, String asc);
	
	public boolean exists(T entity);
	
	public int rowCount();
	
	public List<T> query(String query);
	
	public List<T> query(String queryString, List<Object> parameters);
}
