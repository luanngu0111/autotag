package upskills.database.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import upskills.database.dao.IssueDao;
import upskills.database.model.Issue;
import upskills.database.model.Trade;


public class HbnIssueDao extends AbstractHbnDao<Issue> implements IssueDao {
	//Implement abstract methods of Issue
	public Issue getIssueById(int id) {
		Issue result = new Issue();
		Session session = getSession();
		Transaction tx = null;	
		
		try {
			tx = session.beginTransaction();			
			result = (Issue)session
					 .getNamedQuery("getIssueById")
					 .setParameter("ID", id);		
		} catch(Exception e) {
			e.printStackTrace();
		}	
		finally {
			session.close();
		}
		return result;	
	}
	
	
}
