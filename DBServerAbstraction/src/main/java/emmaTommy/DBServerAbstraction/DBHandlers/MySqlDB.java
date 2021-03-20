package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import emmaTommy.TommyDataModel.Factories.ServizioQueryField;

public class MySqlDB extends AbstractDB {
	
	protected HibernateHelper helperHB;

	public MySqlDB(String DBInstanceName, String DBUrl, int DBPort, String userName, String psswd, String hibernateCfgPath) throws UnknownDBException {
		super(DBInstanceName, "MySql", "SQL", false, true);
		
		// Check Input Data
		/**
		if (hibernateCfgPath == null) {
			throw new NullPointerException("Reveived Null hibernateCfgPath");
		}
		if (hibernateCfgPath.isBlank()) {
			throw new NullPointerException("Reveived Blank hibernateCfgPath");
		}
		this.helperHB = new HibernateHelper(hibernateCfgPath);
		*/
		
		// Load MySqlDriver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String mySqlUrl = "jdbc:mysql://" + DBUrl + "/feedback?" + "user=" + userName + "&password=" + psswd;
			Connection dbConnection = DriverManager.getConnection(mySqlUrl);
		} catch (ClassNotFoundException | SQLException e) {
			throw new UnknownDBException(e.getMessage());
		}
		
		
	}

	@Override
	public ArrayList<String> getCollectionList() throws UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServizioByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> getAllServiziInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isDBAlive() throws UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isServizioByIDPresent(String servizioID, String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveServizioByID(String servizioID, String oldCollectionName, String newCollectionName)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, ServizioNotPresentException,
			UnknownDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public String removeServizioByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TommyEnrichedJSON removeServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNewServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNewServizioEnrichedByID(String servizioID, String collectionName,
			TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		
		Session session = helperHB.getNewSession();
		session.beginTransaction();		 
	    session.save(servizioEnriched);
	    session.getTransaction().commit();
	    session.close();

	}

	@Override
	public HashMap<String, String> GetAllServiziInCollectionByProp(String collectionName,
			TreeMap<ServizioQueryField, String> propNVmap) throws CollectionNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, TommyEnrichedJSON> GetAllServiziEnrichedInCollectionByProp(String collectionName,
			TreeMap<ServizioQueryField, String> propNVmap) throws CollectionNotPresentException, UnknownDBException {
		// TODO Auto-generated method stub
		return null;
	}

}
