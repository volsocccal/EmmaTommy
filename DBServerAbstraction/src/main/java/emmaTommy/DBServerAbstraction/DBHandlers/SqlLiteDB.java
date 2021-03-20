package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class SqlLiteDB extends AbstractDB {
	
	private String dbPath;
	private String tableName;
	private Connection connection = null;
	
	private static final int SQL_QUERY_TIMEOUT = 30;
		
	private static final String TABLE_COL_NAME = "name";
	private static final String SERVIZIO_ID_COL_NAME = "codice_servizio";
	private static final String CODICE_MEZZO_COL_NAME = "codice_mezzo";
	private static final String KM_COL_NAME = "km";
	private static final String SERVIZIO_START_DATE_COL_NAME = "servizio_start_date";
	private static final String SERVIZIO_START_TIME_COL_NAME = "servizio_start_time";
	private static final String SERVIZIO_TIMESTAMP_COL_NAME = "servizio_timestamp";
	private static final String SERVIZIO_POST_STATUS_COL_NAME = "post_status";
	private static final String SERVIZIO_JSON_COL_NAME = "json";
	
	
	public SqlLiteDB(String DBInstanceName, String DBPath, String tableName) 
			throws UnknownDBException {
		super(DBInstanceName, "SqlLite", "SQL", false, true);

		// Logger Method Name
		String method_name = "::SqlLiteDB(): ";
		
	    this.dbPath = "jdbc:sqlite:" + DBPath;
	    this.tableName = tableName;
	    
	    String startQuery = "  CREATE TABLE IF NOT EXISTS " + this.tableName + " (\n" + 
	      		"    " + SERVIZIO_ID_COL_NAME + " varchar(12) NOT NULL,\n" + 
	      		"    " + CODICE_MEZZO_COL_NAME + " varchar(45) NOT NULL,\n" + 
	      		"    " + KM_COL_NAME + " int(11) DEFAULT NULL,\n" + 
	      		"    " + SERVIZIO_START_DATE_COL_NAME + " varchar(45) NOT NULL,\n" + 
	      		"    " + SERVIZIO_START_TIME_COL_NAME + " varchar(45) NOT NULL,\n" + 
	      		"    " + SERVIZIO_TIMESTAMP_COL_NAME + " varchar(45) NOT NULL,\n" + 
	      		"    " + SERVIZIO_POST_STATUS_COL_NAME + " varchar(12) NOT NULL DEFAULT 'POSTING',\n" + 
	      		"    " + SERVIZIO_JSON_COL_NAME  + " BLOB NOT NULL,\n" + 
	      		"    PRIMARY KEY (codice_servizio)\n" + 
	      		"    );";	    
	    executeUpdate(startQuery, method_name);
	    
	}
	
private void activateDBConnection() throws UnknownDBException, SQLException
{
	if (this.dbPath == null)
		throw new UnknownDBException("Null SQL Lite DB Path ");
	if (this.dbPath.isBlank())
		throw new UnknownDBException("Blanck SQL Lite DB Path ");
	if (connection == null)
		connection = DriverManager.getConnection(this.dbPath);
	if (connection.isClosed())
		connection = DriverManager.getConnection(this.dbPath);
	if (!connection.isValid(10))
		throw new UnknownDBException("SQL Lite Connection is Open but isn't Valid");	
	Boolean autoCommit = connection.getAutoCommit();
	if (!autoCommit)
		logger.trace("Autocommit: " + ((autoCommit) ? ("true") : ("false")));	  
}
	
public void executeUpdate (String sqlQuery, String callerMethod) throws UnknownDBException {
		
		if (sqlQuery == null)
			throw new UnknownDBException("Null SQL Query received from " + callerMethod);
		if (sqlQuery.isBlank())
			throw new UnknownDBException("Blanck SQL Query received from " + callerMethod);
		
		try
	    {
			activateDBConnection();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(SQL_QUERY_TIMEOUT);
			statement.executeUpdate(sqlQuery);	      
			logger.trace("Executed Sql Query from method " + callerMethod + ":\n" + sqlQuery);	     
	    } catch(Exception e) {
	    	logger.error("Failed to Execute Sql Query from method " + callerMethod + ":\n" + sqlQuery + "\nError: " + e.getMessage());
	    	throw new UnknownDBException(e.getMessage());
	    }	
	}
	
	public ResultSet executeQuery (String sqlQuery, String callerMethod) throws UnknownDBException {
		
		if (sqlQuery == null)
			throw new UnknownDBException("Null SQL Query received from " + callerMethod);
		if (sqlQuery.isBlank())
			throw new UnknownDBException("Blanck SQL Query received from " + callerMethod);
		
		ResultSet rs = null;
		
		try
	    {
			activateDBConnection();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(SQL_QUERY_TIMEOUT);
			rs = statement.executeQuery(sqlQuery);
			logger.trace("Executed Sql Query from method " + callerMethod + ":\n" + sqlQuery);
	    } catch(Exception e) {
	    	logger.error("Failed to Execute Sql Query from method " + callerMethod + ":\n" + sqlQuery + "\nError: " + e.getMessage());
	    	throw new UnknownDBException(e.getMessage());
	    }
		
		return rs;
		
	}
	

	@Override
	public ArrayList<String> getCollectionList() throws UnknownDBException {
		String method_name = "::SqlLiteDB():getCollectionList() ";
		ArrayList<String> collectionList = new ArrayList<String>();
		String sqlQuery = "SELECT " + TABLE_COL_NAME + " FROM sqlite_master WHERE type = \"table\"";
		ResultSet rs = executeQuery(sqlQuery, method_name);
		try {
			while(rs.next()) {
				if (rs.isClosed())
					throw new UnknownDBException(method_name + "Received RS is closed");
				collectionList.add(rs.getString(TABLE_COL_NAME));
			}
		} catch (Exception e) {
			throw new UnknownDBException(e.getMessage());
		}
		return collectionList;
	}

	@Override
	public String getServizioByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():getServizioByID() ";
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			String sqlQuery = "SELECT * FROM " + collectionName + " WHERE " + SERVIZIO_ID_COL_NAME + " is '" + servizioID + "';";
			ResultSet rs = executeQuery(sqlQuery, method_name);
			try {
				if (!rs.next())
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but I couldn't read it");
				byte[] blob = rs.getBytes(SERVIZIO_JSON_COL_NAME);
				if (blob == null)
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is null");
				String servizioJSON = new String(blob);
				if (servizioJSON.isBlank())
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is empty");
				return servizioJSON;
			} catch (SQLException e) {
				throw new UnknownDBException(e.getMessage());
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}

	@Override
public TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {	
		String method_name = "::SqlLiteDB():getServizioEnrichedByID() ";
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			String sqlQuery = "SELECT * FROM " + collectionName + " WHERE " + SERVIZIO_ID_COL_NAME + " is '" + servizioID + "';";
			ResultSet rs = executeQuery(sqlQuery, method_name);
			try {
				if (!rs.next())
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but I couldn't read it");
				if (rs.isClosed())
					throw new UnknownDBException(method_name + "Received RS is closed");
				byte[] blob = rs.getBytes(SERVIZIO_JSON_COL_NAME);
				if (blob == null)
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is null");
				String servizioJSON = new String(blob);
				if (servizioJSON.isBlank())
					throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is empty");
				String postStatusStr = rs.getString(SERVIZIO_POST_STATUS_COL_NAME);
				String timeStampStr = rs.getString(SERVIZIO_TIMESTAMP_COL_NAME);
				TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
				servizioEnriched.setPostStatusStr(postStatusStr);
				servizioEnriched.setTimeStamp(timeStampStr);
				return servizioEnriched;
			} catch (SQLException e) {
				throw new UnknownDBException(e.getMessage());
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}

	@Override
	public HashMap<String, String> getAllServiziInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():getAllServiziInCollection() ";
		HashMap<String, String> serviziMap = new HashMap<String, String>();
		if (this.isCollectionByNamePresent(collectionName)) {
			String sqlQuery = "SELECT * FROM " + collectionName + ";";		
			ResultSet rs = executeQuery(sqlQuery, method_name);
			try {
				while (rs.next()) {
					if (rs.isClosed())
						throw new UnknownDBException(method_name + "Received RS is closed");
					String servizioID = rs.getString(SERVIZIO_ID_COL_NAME);
					byte[] blob = rs.getBytes(SERVIZIO_JSON_COL_NAME);
					if (blob == null)
						throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is null");
					String servizioJSON = new String(blob);
					if (servizioJSON.isBlank())
						throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is empty");
					serviziMap.put(servizioID, servizioJSON);
				}
			} catch (SQLException e) {
				throw new UnknownDBException(e.getMessage());
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
		return serviziMap;	
	}

	@Override
	public HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():getAllServiziEnrichedInCollection() ";
		HashMap<String, TommyEnrichedJSON> serviziEnrichedMap = new HashMap<String, TommyEnrichedJSON>();
		if (this.isCollectionByNamePresent(collectionName)) {
			String sqlQuery = "SELECT * FROM " + collectionName + ";";		
			ResultSet rs = executeQuery(sqlQuery, method_name);
			try {
				while (rs.next()) {
					if (rs.isClosed())
						throw new UnknownDBException(method_name + "Received RS is closed");
					String servizioID = rs.getString(SERVIZIO_ID_COL_NAME);
					byte[] blob = rs.getBytes(SERVIZIO_JSON_COL_NAME);
					if (blob == null)
						throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is null");
					String servizioJSON = new String(blob);
					if (servizioJSON.isBlank())
						throw new UnknownDBException("Servizio " + servizioID + " was found in Collection " + collectionName + " but the json is empty");
					String postStatusStr = rs.getString(SERVIZIO_POST_STATUS_COL_NAME);
					String timeStampStr = rs.getString(SERVIZIO_TIMESTAMP_COL_NAME);
					TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
					servizioEnriched.setPostStatusStr(postStatusStr);
					servizioEnriched.setTimeStamp(timeStampStr);
					serviziEnrichedMap.put(servizioID, servizioEnriched);
				}
			} catch (Exception e) {
				throw new UnknownDBException(e.getMessage());
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
		return serviziEnrichedMap;			
	}

	@Override
	public Boolean isDBAlive() throws UnknownDBException {
		if (connection == null)
			return false;
		try {
			return connection.isClosed();
		} catch (Exception e) {
			throw new UnknownDBException(e.getMessage());
		}
	}

	@Override
	public Boolean isServizioByIDPresent(String servizioID, String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():isServizioByIDPresent() ";
		if (this.isCollectionByNamePresent(collectionName)) {
			String sqlQuery = "SELECT 1 FROM " + collectionName + " WHERE " + SERVIZIO_ID_COL_NAME + " is '" + servizioID + "';";
			ResultSet rs = executeQuery(sqlQuery, method_name);
			try {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw new UnknownDBException(e.getMessage());
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}

	@Override
	public void moveServizioByID(String servizioID, String oldCollectionName, String newCollectionName)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, ServizioNotPresentException,
			UnknownDBException {
		if (this.supportEnrichedJSON) { // Enriched JSON
			TommyEnrichedJSON servizioEnriched = this.getServizioEnrichedByID(servizioID, oldCollectionName);
			this.writeNewServizioEnrichedByID(servizioID, newCollectionName, servizioEnriched);
			this.removeServizioByID(servizioID, oldCollectionName);
		} else { // Raw JSON
			String servizio = this.getServizioByID(servizioID, oldCollectionName);
			this.writeNewServizioByID(servizioID, newCollectionName, servizio);
			this.removeServizioByID(servizioID, oldCollectionName);
		}
	}

	@Override
	public String removeServizioByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():removeServizioByID() ";
		String servizio = this.getServizioByID(servizioID, collectionName);
		String sqlQuery = "DELETE FROM " + collectionName + " WHERE " + SERVIZIO_ID_COL_NAME + " = '" + servizioID + "';";
		this.executeUpdate(sqlQuery, method_name);
		return servizio;
	}

	@Override
	public TommyEnrichedJSON removeServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():removeServizioEnrichedByID() ";
		TommyEnrichedJSON servizioEnriched = this.getServizioEnrichedByID(servizioID, collectionName);
		String sqlQuery = "DELETE FROM " + collectionName + " WHERE " + SERVIZIO_ID_COL_NAME + " = '" + servizioID + "';";
		this.executeUpdate(sqlQuery, method_name);
		return servizioEnriched;
	}

	@Override
	public void updateServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():updateServizioByID() ";
		if (this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio present in collection
			TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
			String sqlQuery = "UPDATE " + collectionName + "\n"
					+ "SET "
					//+ SERVIZIO_ID_COL_NAME + " = " + "'" + servizioEnriched.getCodiceServizio() + "'" + ", "
		      		+ CODICE_MEZZO_COL_NAME + " = " + "'" + servizioEnriched.getCodiceMezzo() + "'" + ", "
		      		+ KM_COL_NAME + " = " + "'" + servizioEnriched.getKm() + "'" + ", "
		      		+ SERVIZIO_START_DATE_COL_NAME + " = " + "'" + servizioEnriched.getMissioneStartDateStr() + "'" + ", "
		      		+ SERVIZIO_START_TIME_COL_NAME + " = " + "'" + servizioEnriched.getMissioneStartTimeStr() + "'" + ", "
		      		+ SERVIZIO_TIMESTAMP_COL_NAME + " = " + "'" + servizioEnriched.getTimeStampStr() + "'" + ", "
		      		+ SERVIZIO_POST_STATUS_COL_NAME + " = " + "'" + servizioEnriched.getPostStatusStr() + "'" + ", "
		      		+ SERVIZIO_JSON_COL_NAME + " = " + "'" + servizioEnriched.getJsonServizio() + "'"
		      		+ "\n"
		      		+ "WHERE " 
		      		+ SERVIZIO_ID_COL_NAME + " = " + "'" + servizioEnriched.getCodiceServizio() + "'"
		      		+ ";";
			this.executeUpdate(sqlQuery, method_name);
		} else { // Servizio not present in collection
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}

	@Override
	public void updateServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String method_name = "::SqlLiteDB():getServizioEnrichedByID() ";
		if (this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio present in collection
			String sqlQuery = "UPDATE " + collectionName + "\n"
					+ "SET "
					//+ SERVIZIO_ID_COL_NAME + " = " + "'" + servizioEnriched.getCodiceServizio() + "'" + ", "
		      		+ CODICE_MEZZO_COL_NAME + " = " + "'" + servizioEnriched.getCodiceMezzo() + "'" + ", "
		      		+ KM_COL_NAME + " = " + "'" + servizioEnriched.getKm() + "'" + ", "
		      		+ SERVIZIO_START_DATE_COL_NAME + " = " + "'" + servizioEnriched.getMissioneStartDateStr() + "'" + ", "
		      		+ SERVIZIO_START_TIME_COL_NAME + " = " + "'" + servizioEnriched.getMissioneStartTimeStr() + "'" + ", "
		      		+ SERVIZIO_TIMESTAMP_COL_NAME + " = " + "'" + servizioEnriched.getTimeStampStr() + "'" + ", "
		      		+ SERVIZIO_POST_STATUS_COL_NAME + " = " + "'" + servizioEnriched.getPostStatusStr() + "'" + ", "
		      		+ SERVIZIO_JSON_COL_NAME + " = " + "'" + servizioEnriched.getJsonServizio() + "'"
		      		+ "\n"
		      		+ "WHERE " 
		      		+ SERVIZIO_ID_COL_NAME + " = " + "'" + servizioEnriched.getCodiceServizio() + "'"
		      		+ ";";
			this.executeUpdate(sqlQuery, method_name);
		} else { // Servizio not present in collection
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}

	@Override
	public void writeNewServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		String method_name = "::SqlLiteDB():writeNewServizioByID() ";
		if (!this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio not present in collection
			TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
			String sqlQuery = "INSERT INTO " + collectionName 
					+ "("
					+ SERVIZIO_ID_COL_NAME + ", "
		      		+ CODICE_MEZZO_COL_NAME + ", "
		      		+ KM_COL_NAME + ", "
		      		+ SERVIZIO_START_DATE_COL_NAME + ", "
		      		+ SERVIZIO_START_TIME_COL_NAME + ", " 
		      		+ SERVIZIO_TIMESTAMP_COL_NAME + ", "
		      		+ SERVIZIO_POST_STATUS_COL_NAME + ", "
		      		+ SERVIZIO_JSON_COL_NAME
		      		+ ")\n"
		      		+ "VALUES " 
		      		+ "(" 
		      		+ "'" + servizioEnriched.getCodiceServizio() + "'" + ", "
		      		+ "'" + servizioEnriched.getCodiceMezzo() + "'" + ", "
		      		+ "'" + servizioEnriched.getKm() + "'" + ", "
		      		+ "'" + servizioEnriched.getMissioneStartDateStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getMissioneStartTimeStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getTimeStampStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getPostStatusStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getJsonServizio().replaceAll("'", "''") + "'"
		      		+ ");";
			this.executeUpdate(sqlQuery, method_name);
		} else { // Servizio present in collection
			throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already present in Collection " + collectionName);
		}
	}

	@Override
	public void writeNewServizioEnrichedByID(String servizioID, String collectionName,
			TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		String method_name = "::SqlLiteDB():writeNewServizioEnrichedByID() ";
		if (!this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio not present in collection
			String sqlQuery = "INSERT INTO " + collectionName 
					+ "("
					+ SERVIZIO_ID_COL_NAME + ", "
		      		+ CODICE_MEZZO_COL_NAME + ", "
		      		+ KM_COL_NAME + ", "
		      		+ SERVIZIO_START_DATE_COL_NAME + ", "
		      		+ SERVIZIO_START_TIME_COL_NAME + ", " 
		      		+ SERVIZIO_TIMESTAMP_COL_NAME + ", "
		      		+ SERVIZIO_POST_STATUS_COL_NAME + ", "
		      		+ SERVIZIO_JSON_COL_NAME
		      		+ ")\n"
		      		+ "VALUES " 
		      		+ "(" 
		      		+ "'" + servizioEnriched.getCodiceServizio() + "'" + ", "
		      		+ "'" + servizioEnriched.getCodiceMezzo() + "'" + ", "
		      		+ "'" + servizioEnriched.getKm() + "'" + ", "
		      		+ "'" + servizioEnriched.getMissioneStartDateStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getMissioneStartTimeStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getTimeStampStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getPostStatusStr() + "'" + ", "
		      		+ "'" + servizioEnriched.getJsonServizio().replaceAll("'", "''") + "'"
		      		+ ");";
			this.executeUpdate(sqlQuery, method_name);
		} else { // Servizio present in collection
			throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already present in Collection " + collectionName);
		}
	}

}
