package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public abstract class AbstractDB {
	
	protected org.apache.logging.log4j.Logger logger;
	
	protected String DBInstanceName;
	protected String DBTech;	
	protected String DBType;
	protected Boolean DBMock;
	protected Boolean supportEnrichedJSON;
	protected ArrayList<String> collectionListNames;

	public AbstractDB(String DBInstanceName, String DBTech, String DBType, Boolean DBMock, Boolean supportEnrichedJSON, ArrayList<String> collectionListNames) {
		
		// Logger Method Name
		String method_name = "::AbstractDB(): ";
		
		// Check Input Data
		if (DBInstanceName == null) {
			throw new NullPointerException("Reveived Null DBInstanceName");
		}
		if (DBInstanceName.isBlank()) {
			throw new NullPointerException("Reveived Blank DBInstanceName");
		}
		if (DBTech == null) {
			throw new NullPointerException("Reveived Null DBTech");
		}
		if (DBTech.isBlank()) {
			throw new NullPointerException("Reveived Blank DBTech");
		}
		if (DBType == null) {
			throw new NullPointerException("Reveived Null DBType");
		}
		if (DBType.isBlank()) {
			throw new NullPointerException("Reveived Blank DBType");
		}
				
		// Save Configuration Data
		this.DBInstanceName = DBInstanceName;		
		this.DBTech = DBTech;		
		this.DBType = DBType;
		this.DBMock = DBMock;
		this.supportEnrichedJSON = supportEnrichedJSON;
		this.logger = LogManager.getLogger(DBInstanceName);
				
		// Log Start
		logger.info(method_name + "Starting DB");
		logger.info(method_name + "DB Name: " + DBInstanceName);
		logger.info(method_name + "DB Tech: " + DBTech);
		logger.info(method_name + "DB Type: " + DBType);
		logger.info(method_name + "DB Mock: " + DBMock);
		logger.info(method_name + "DB Supports Enriched JSON: " + supportEnrichedJSON);
		
		// Log CollectionNames
		if (collectionListNames == null) {
			throw new NullPointerException("Reveived Null collectionListNames");
		}
		if (collectionListNames.isEmpty()) {
			throw new NullPointerException("Reveived Empty collectionListNames");
		}
		this.collectionListNames = new ArrayList<String>();
		for (String collectionName: collectionListNames) {
			if (collectionName == null) {
				throw new NullPointerException("Reveived Null collectionName");
			}
			if (collectionListNames.isEmpty()) {
				throw new NullPointerException("Reveived Empty collectionName");
			}
			this.collectionListNames.add(collectionName);
			logger.info(method_name + "Added Collection " + collectionName + " to DB");
		}
		
	}
	
	public Boolean areServiziEnriched() {
		return this.supportEnrichedJSON;
	}
	
	public abstract ArrayList<String> getCollectionList() throws UnknownDBException;
	
	public abstract String getServizioByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract HashMap<String, String> getAllServiziInCollection(String collectionName) throws CollectionNotPresentException, UnknownDBException;
	
	public abstract HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection (String collectionName) throws CollectionNotPresentException, UnknownDBException;
	
	public abstract Boolean isCollectionByNamePresent(String collectionName) throws UnknownDBException, UnknownDBException;
	
	public abstract Boolean isDBAlive() throws UnknownDBException;
	
	public abstract Boolean isServizioByIDPresent(String servizioID, String collectionName) throws CollectionNotPresentException, UnknownDBException;
	
	public abstract void moveServizioByID(String servizioID, String oldCollectionName, String newCollectionName) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, ServizioNotPresentException, UnknownDBException;	
	
	public abstract String removeServizioByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
		
	public abstract TommyEnrichedJSON removeServizioEnrichedByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract void updateServizioByID(String servizioID, String collectionName, String servizioJSON) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract void updateServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract void writeNewServizioByID(String servizioID, String collectionName, String servizioJSON) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException;
	
	public abstract void writeNewServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException;

}
