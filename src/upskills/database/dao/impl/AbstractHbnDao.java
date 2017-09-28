package upskills.database.dao.impl;


import java.io. Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import upskills.database.dao.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {
	
	// Initialize configuration from hibernate configuration
	private Configuration cfg = new Configuration().configure("resources/hibernate.cfg.xml");
	// Create session depend on hibernate configuration
	private SessionFactory sessionFactory = cfg.buildSessionFactory();
	
	private Class<T> domainClass;

	protected Session getSession() {	
		if(sessionFactory.getCurrentSession().isConnected())
			return sessionFactory.getCurrentSession();
		return sessionFactory.openSession();		
	}
		
	private Class<T> getDomainClass() {
		if(domainClass == null) {
			ParameterizedType thisType = 
					(ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	private String getDomainClassName() {
		return this.getDomainClass().getName();
	}
	
	 public void create(T t) {
		 Transaction tx = this.getSession().beginTransaction();
		 try {
			 this.getSession().save(t);
			 tx.commit();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		 }
		 finally {
			this.getSession().close();
		}		 
	 }
		
	 public void delete(T t) {
		 Transaction tx = this.getSession().beginTransaction();
		 try {
			 this.getSession().delete(t);
			 tx.commit();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		 }
		 finally {
			 this.getSession().close();
		}
	 }
	 
	 public void deleteById(Serializable id) {		 
		 Transaction tx = this.getSession().beginTransaction();
		 try {
			 if(!tx.isActive())
				 this.delete(load(id));
			 	 tx.commit();
			 	 //System.out.println("delete");
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		 }
		 finally {
			 this.getSession().close();
		}
	 }
	 
	 public void deleteAll() {
		 Transaction tx = this.getSession().beginTransaction();
		 try {
			 this.getSession().createQuery("delete " + 
			 			getDomainClassName()).executeUpdate();
			 tx.commit();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		 }
		 finally {
			this.getSession().close();
		}
	 }
	
	 public void update(T t) {		
		 Transaction tx = this.getSession().beginTransaction();
		 try {
			 this.getSession().update(t);
			 tx.commit();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 tx.rollback();
		 }
		 finally {
			this.getSession().close();
		}
	 }
	
	 public T get(Serializable id) {
		 return (T)this.getSession().get(getDomainClass(), id);
	 }
	 
	 public T load(Serializable id) {
		 return (T)this.getSession().load(getDomainClass(), id);
	 }
	 
	 public List<T> getAll() {
		 return this.getSession().createQuery("from " + getDomainClassName()).list();
	 }
	
	 public long count() {
		 return (Long)this.getSession().createQuery(
				 			"select count(*) from " + 
		 					getDomainClassName()).uniqueResult();
	 }
	 
	 public boolean exists(Serializable id) {
		 return (this.get(id) != null);
	 }
	
}
