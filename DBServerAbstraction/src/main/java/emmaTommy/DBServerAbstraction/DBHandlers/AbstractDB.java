package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import emmaTommy.TommyDataModel.Factories.ServizioFactory;
import emmaTommy.TommyDataModel.Factories.ServizioQueryField;

public abstract class AbstractDB {
	
	protected org.apache.logging.log4j.Logger logger;
	
	protected String DBInstanceName;
	protected String DBTech;	
	protected String DBType;
	protected Boolean DBMock;
	protected Boolean supportEnrichedJSON;

	public AbstractDB(String DBInstanceName, String DBTech, String DBType, Boolean DBMock, Boolean supportEnrichedJSON) throws UnknownDBException {
		
		// Logger Method Name
		String method_name = "::AbstractDB(): ";
		
		// Check Input Data
		if (DBInstanceName == null) {
			throw new UnknownDBException("Reveived Null DBInstanceName");
		}
		if (DBInstanceName.isBlank()) {
			throw new UnknownDBException("Reveived Blank DBInstanceName");
		}
		if (DBTech == null) {
			throw new UnknownDBException("Reveived Null DBTech");
		}
		if (DBTech.isBlank()) {
			throw new UnknownDBException("Reveived Blank DBTech");
		}
		if (DBType == null) {
			throw new UnknownDBException("Reveived Null DBType");
		}
		if (DBType.isBlank()) {
			throw new UnknownDBException("Reveived Blank DBType");
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
		
	}
	
	public Boolean areServiziEnriched() {
		return this.supportEnrichedJSON;
	}
	
	public abstract ArrayList<String> getCollectionList() throws UnknownDBException;
	
	public abstract String getServizioByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException;
	
	public abstract HashMap<String, String> getAllServiziInCollection(String collectionName) throws CollectionNotPresentException, UnknownDBException;
	
	public HashMap<String, String> GetAllServiziInCollectionByProp(String collectionName, TreeMap<ServizioQueryField, String> propNVmap) throws CollectionNotPresentException, UnknownDBException
	{
		HashMap<String, String> serviziMap = getAllServiziInCollection(collectionName);
		HashMap<String, String> serviziAcceptedMap = new HashMap<String, String>();
		ServizioFactory sFactory = new ServizioFactory();
		for (String servizioID: serviziMap.keySet()) {			
			try {
				if (servizioID.compareTo("201185334") == 0)
	    		{    			
	    			logger.info("Servizio 201185334");
	    		}
				if (sFactory.checkPropertiesUnmarshallJSON(propNVmap, serviziMap.get(servizioID))) {
					serviziAcceptedMap.put(servizioID, serviziMap.get(servizioID));
				}
			} catch (JAXBException e) {
				throw new UnknownDBException(e.getMessage());
			}
		}
		return serviziAcceptedMap;
	}
	
	public abstract HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection (String collectionName) throws CollectionNotPresentException, UnknownDBException;
	
	public HashMap<String, TommyEnrichedJSON> GetAllServiziEnrichedInCollectionByProp(String collectionName, TreeMap<ServizioQueryField, String> propNVmap) throws CollectionNotPresentException, UnknownDBException
	{
		HashMap<String, TommyEnrichedJSON> serviziEnrichedMap = getAllServiziEnrichedInCollection(collectionName);
		HashMap<String, TommyEnrichedJSON> serviziEnrichedAcceptedMap = new HashMap<String, TommyEnrichedJSON>();
		ServizioFactory sFactory = new ServizioFactory();
		for (String servizioID: serviziEnrichedMap.keySet()) {			
			try {
				if (sFactory.checkPropertiesUnmarshallJSON(propNVmap, serviziEnrichedMap.get(servizioID).getJsonServizio())) {
					serviziEnrichedAcceptedMap.put(servizioID, serviziEnrichedMap.get(servizioID));
				}
			} catch (JAXBException e) {
				throw new UnknownDBException(e.getMessage());
			}
		}
		return serviziEnrichedAcceptedMap;
	}
	
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
