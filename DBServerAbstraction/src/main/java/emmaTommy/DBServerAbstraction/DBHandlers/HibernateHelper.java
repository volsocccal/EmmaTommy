package emmaTommy.DBServerAbstraction.DBHandlers;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateHelper {
	
	protected String hibernateCfgPath;
	protected SessionFactory sessionFactory;
	
	public HibernateHelper(String hibernateCfgPath) throws HibernateException {
		// Check Input Data
		if (hibernateCfgPath == null) {
			throw new NullPointerException("Reveived Null hibernateCfgPath");
		}
		if (hibernateCfgPath.isBlank()) {
			throw new NullPointerException("Reveived Blank hibernateCfgPath");
		}
		this.hibernateCfgPath = hibernateCfgPath;
		
		// Create new Session Factory
		this.sessionFactory = this.buildSessionFactory();
		
	}

	protected SessionFactory buildSessionFactory() throws HibernateException { 
		return new Configuration().configure(this.hibernateCfgPath).buildSessionFactory();
	}
	
	public Session getNewSession() throws HibernateException {
		return this.sessionFactory.openSession();
	}
 
	// Close caches and connection pools
	public void shutdown() throws HibernateException {
		this.sessionFactory.close();
	}
  
}
