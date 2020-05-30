package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;


public class MockDB extends AbstractDB {
	
	protected HashMap<String, HashMap<String, TommyEnrichedJSON>> db_enriched;
	protected HashMap<String, HashMap<String, String>> db_raw;
	protected ArrayList<String> collectionListNames;

	public MockDB(String DBName, 
			String DBTech, 
			String DBType, 
			Boolean supportEnrichedJSON,
			ArrayList<String> collectionListNames) throws UnknownDBException {
		super(DBName, DBTech, DBType, true, supportEnrichedJSON);
		
		// Logger Method Name
		String method_name = "::MockDB(): ";
		
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
		
		// Create DB Map
		if (this.supportEnrichedJSON) {
			this.db_enriched = new HashMap<String, HashMap<String, TommyEnrichedJSON>>();
			for (String collectionName: this.collectionListNames) {
				this.db_enriched.put(collectionName, new HashMap<String, TommyEnrichedJSON>());
			}
			logger.info(method_name + "Initialized Enriched DB");
		} else {
			this.db_raw = new HashMap<String, HashMap<String, String>>();
			for (String collectionName: this.collectionListNames) {
				this.db_raw.put(collectionName, new HashMap<String, String>());
			}
			logger.info(method_name + "Initialized Raw DB");
		}
		
	}

	@Override
	public ArrayList<String> getCollectionList() {
		return this.collectionListNames;
	}
	
	@Override
	public String getServizioByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException {		
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			if (this.areServiziEnriched()) {
				return this.db_enriched.get(collectionName).get(servizioID).getJsonServizio();	
			} else {
				return this.db_raw.get(collectionName).get(servizioID);	
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}
	
	@Override
	public TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException {		
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			if (this.areServiziEnriched()) {
				return this.db_enriched.get(collectionName).get(servizioID);	
			} else {
				String servizioJSON =  this.db_raw.get(collectionName).get(servizioID);
				TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
				return servizioEnriched;
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}
	
	@Override 
	public HashMap<String, String> getAllServiziInCollection(String collectionName) throws CollectionNotPresentException {
		if (this.isCollectionByNamePresent(collectionName)) {
			if (this.supportEnrichedJSON) {
				HashMap<String, TommyEnrichedJSON> serviziEnrichedMap = this.db_enriched.get(collectionName);
				HashMap<String, String> serviziMap = new HashMap<String, String>();
				for (String servizioID: serviziEnrichedMap.keySet()) {
					serviziMap.put(servizioID, serviziEnrichedMap.get(servizioID).getJsonServizio());
				}
				return serviziMap;
			} else {
				return this.db_raw.get(collectionName);	
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}
	
	@Override 
	public HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection(String collectionName) throws CollectionNotPresentException {
		if (this.isCollectionByNamePresent(collectionName)) {
			if (this.supportEnrichedJSON) {
				return this.db_enriched.get(collectionName);	
			} else {				
				HashMap<String, String> serviziMap = this.db_raw.get(collectionName);
				HashMap<String, TommyEnrichedJSON> serviziEnrichedMap = new HashMap<String, TommyEnrichedJSON>();
				for (String servizioID: serviziMap.keySet()) {
					serviziEnrichedMap.put(servizioID, new TommyEnrichedJSON(servizioID, serviziMap.get(servizioID)));
				}
				return serviziEnrichedMap;
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}
	
	@Override 
	public Boolean isCollectionByNamePresent(String collectionName) {
		return this.getCollectionList().contains(collectionName);
	}
	
	@Override 
	public Boolean isDBAlive() {
		return true;
	}


	@Override 
	public Boolean isServizioByIDPresent(String servizioID, String collectionName) throws CollectionNotPresentException {
		if (this.isCollectionByNamePresent(collectionName)) {
			if (this.supportEnrichedJSON) { // Enriched JSON
				return this.db_enriched.get(collectionName).containsKey(servizioID);	
			} else { // Raw JSON
				return this.db_raw.get(collectionName).containsKey(servizioID);	
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}
	
	@Override
	public void moveServizioByID(String servizioID, String oldCollectionName, String newCollectionName) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, ServizioNotPresentException {
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
	public String removeServizioByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException {
		if (this.supportEnrichedJSON) { // Enriched JSON
			TommyEnrichedJSON servizioEnriched = this.getServizioEnrichedByID(servizioID, collectionName);
			this.db_enriched.get(collectionName).remove(servizioID);
			return servizioEnriched.getJsonServizio();
		} else { // Raw JSON
			String servizio = this.getServizioByID(servizioID, collectionName);
			this.db_raw.get(collectionName).remove(servizioID);
			return servizio;
		}
	}
	
	@Override
	public TommyEnrichedJSON removeServizioEnrichedByID(String servizioID, String collectionName) throws CollectionNotPresentException, ServizioNotPresentException {
		if (this.supportEnrichedJSON) { // Enriched JSON
			TommyEnrichedJSON servizioEnriched = this.getServizioEnrichedByID(servizioID, collectionName);
			this.db_enriched.get(collectionName).remove(servizioID);
			return servizioEnriched;
		} else { // Raw JSON
			String servizio = this.getServizioByID(servizioID, collectionName);
			this.db_raw.get(collectionName).remove(servizioID);
			return new TommyEnrichedJSON(servizioID, servizio);
		}
	}

	@Override
	public void updateServizioByID(String servizioID, String collectionName, String servizioJSON) throws CollectionNotPresentException, ServizioNotPresentException {
		if (this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio present in collection
			if (this.supportEnrichedJSON) { // Enriched JSON
				this.db_enriched.get(collectionName).put(servizioID, new TommyEnrichedJSON(servizioID, servizioJSON));				
			} else { // Raw JSON
				this.db_raw.get(collectionName).put(servizioID, servizioJSON);				
			}
		} else { // Servizio not present in collection
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}
	
	@Override
	public void updateServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched) throws CollectionNotPresentException, ServizioNotPresentException {
		if (this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio present in collection
			if (this.supportEnrichedJSON) { // Enriched JSON
				this.db_enriched.get(collectionName).put(servizioID, servizioEnriched);				
			} else { // Raw JSON
				this.db_raw.get(collectionName).put(servizioID, servizioEnriched.getJsonServizio());				
			}
		} else { // Servizio not present in collection
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
	}
	
	@Override
	public void writeNewServizioByID(String servizioID, String collectionName, String servizioJSON) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException {
		if (!this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio not present in collection
			if (this.supportEnrichedJSON) { // Enriched JSON
				this.db_enriched.get(collectionName).put(servizioID, new TommyEnrichedJSON(servizioID, servizioJSON));				
			} else { // Raw JSON
				this.db_raw.get(collectionName).put(servizioID, servizioJSON);				
			}
		} else { // Servizio present in collection
			throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already present in Collection " + collectionName);
		}
	}
	
	@Override
	public void writeNewServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched) throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException {
		if (!this.isServizioByIDPresent(servizioID, collectionName)) { // Servizio present in collection
			if (this.supportEnrichedJSON) { // Enriched JSON
				this.db_enriched.get(collectionName).put(servizioID, servizioEnriched);				
			} else { // Raw JSON
				this.db_raw.get(collectionName).put(servizioID, servizioEnriched.getJsonServizio());				
			}
		} else { // Servizio present in collection
			throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already present in Collection " + collectionName);
		}
	}

}
