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
		private Configuration cfg = new Configuration().configure("/resources/hibernate.cfg.xml");	
		// Create session depend on hibernate configuration
		private SessionFactory sessionFactory = cfg.buildSessionFactory();
		
		private Class<T> domainClass;

		protected Session getSession() {	
			try
			{		
				return sessionFactory.getCurrentSession();
			}
			catch(Exception e)
			{
				return sessionFactory.openSession();
			}
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
			 Transaction tx = null;
			 Session session = this.getSession();
			 try {
				 tx = session.beginTransaction();
				 session.save(t);
				 tx.commit();
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 tx.rollback();
			 }
			 finally {
				 session.close();
			}		 
		 }
			
		 public void delete(T t) {
			 Transaction tx = null;
			 Session session = this.getSession();
			 
			 try {
				 tx = session.beginTransaction();
				 session.delete(t);
				 tx.commit();
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 tx.rollback();
			 }
			 finally {
				 session.close();
			}
		 }
		 
		 public void deleteById(Serializable id) {	
			 this.delete(load(id));
		 }
		 
		 public void deleteAll() {
			 Transaction tx = null;
			 Session session = this.getSession();
			 
			 try {
				 session.beginTransaction();
				 session.createQuery("delete " + 
				 			getDomainClassName()).executeUpdate();
				 tx.commit();
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 tx.rollback();
			 }
			 finally {
				 session.close();
			}
		 }
		
		 public void update(T t) {		
			 Transaction tx = null;
			 Session session = this.getSession();
			 
			 try {
				 session.beginTransaction();
				 session.update(t);
				 tx.commit();
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 tx.rollback();
			 }
			 finally {
				 session.close();
			}
		 }
		
		 public T get(Serializable id) {
			 Transaction tx = null;
			 Session session = this.getSession();	
			 
			 try {
				 tx = session.beginTransaction();		 
				 return (T)this.getSession().get(getDomainClass(), id);
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return null;
			 }
			 finally {
				 session.close();
			 }	
		 }
		 
		 //Open transaction for load (Exception in thread "main" org.hibernate.HibernateException: load is not valid without active transaction)
		 public T load(Serializable id) {
			 Transaction tx = null;
			 Session session = this.getSession();	
			 
			 try {
				 tx = session.beginTransaction();		 
				 return (T)this.getSession().load(getDomainClass(), id);
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return null;
			 }
			 finally {
				 session.close();
			 }			
		 }
		 
		 public List<T> getAll() {
			 Transaction tx = null;
			 Session session = this.getSession();	
			 
			 try {
				 tx = session.beginTransaction();
				 return this.getSession().createQuery("from " + getDomainClassName()).list();
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return null;
			 }
			 finally {
				 session.close();
			 }	
			 
		 }
		
		 public long count() {
			 Transaction tx = null;
			 Session session = this.getSession();	
			 
			 try {
				 tx = session.beginTransaction();		 
				 return (Long)this.getSession().createQuery(
				 			"select count(*) from " + 
		 					getDomainClassName()).uniqueResult();
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			 }
			 finally {
				 session.close();
			 }	
		 }
		 
		 public boolean exists(Serializable id) {
			 Transaction tx = null;
			 Session session = this.getSession();	
			 
			 try {
				 tx = session.beginTransaction();		 
				 return (this.get(id) != null);
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return false;	
			 }
			 finally {
				 session.close();
			 }				
		 }	
}
