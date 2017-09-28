package upskills.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import upskills.database.dao.impl.HbnIssueDao;
import upskills.database.model.Issue;

public class test {

	public static void main(String[] args) {
		System.out.println("--------------begin-------------------");
		
/*		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");	
		SessionFactory sessionFactory = cfg.buildSessionFactory();
		Session ses = sessionFactory.openSession();
		Transaction tran = null;
		
		
		Issue issue = new Issue();
		issue.setFieldValues("test add new issue 18");
	
		//System.out.println(sessionFactory.getCurrentSession());
		System.out.println(ses.isConnected());
		System.out.println(ses.isOpen());
		
	try {
			tran = ses.beginTransaction();			
			ses.save(issue);			
			tran.commit();
		}
		catch(Exception e) {
			tran.rollback();
		}
		finally {
			ses.close();
		}*/
		
		Issue issue = new Issue();
		issue.setFieldValues("test add new issue");
		
		HbnIssueDao hbnIssueDao = new HbnIssueDao();
		
		hbnIssueDao.deleteById(17);

		System.out.println("--------------end-------------------");

	}
}
