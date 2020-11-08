package emmaTommy.DBServerAbstraction.DBHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import emmaTommy.TommyDataModel.Factories.ServizioFactory;

public class MongoDB extends AbstractDB {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected ServizioFactory sFact;
	
	public MongoDB(String DBInstanceName, String DBUrl, int DBPort, String userName, String psswd) throws UnknownDBException {
		super(DBInstanceName, "MongoDB", "NoSql", false, false);
		
		try {
			MongoClientOptions options =  MongoClientOptions.builder().sslEnabled(true).build();
			MongoCredential credentials = MongoCredential.createCredential(userName, DBInstanceName, psswd.toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(DBUrl, DBPort), credentials, options);
	        this.database = mongoClient.getDatabase(DBInstanceName);
		} catch (IllegalArgumentException e) {
			throw new UnknownDBException("Failed to load DB: " + e.getMessage());
		}
        //mongoClient.close();
        
        this.sFact = new ServizioFactory();
		
	}

	@Override
	public ArrayList<String> getCollectionList() throws UnknownDBException {
		return database.listCollectionNames().into(new ArrayList<String>());
	}

	@Override
	public String getServizioByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		String servizioJSON = "";
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			Document condition = new Document("$eq", servizioID);
			Document filter = new Document("ID", condition);
			for (Document document: this.database.getCollection(collectionName).find(filter)) {
				try {
					Servizio s = this.sFact.buildServizioUnmarshallJSON(document.toJson());
					servizioJSON = s.toJSON();
				} catch (JAXBException e) {
					throw new UnknownDBException("Unable elaborate read Servizio " + servizioID + ": " + e.getMessage());
				}
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
		return servizioJSON;
	}

	@Override
	public TommyEnrichedJSON getServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		TommyEnrichedJSON servizioEnriched = null;
		if (this.isServizioByIDPresent(servizioID, collectionName)) {
			Document condition = new Document("$eq", servizioID);
			Document filter = new Document("ID", condition);
			for (Document document: this.database.getCollection(collectionName).find(filter)) {
				servizioEnriched = new TommyEnrichedJSON(servizioID, document.toJson());
			}
		} else {
			throw new ServizioNotPresentException("Servizio " + servizioID + " not found in Collection " + collectionName, servizioID, collectionName);
		}
		return servizioEnriched;
	}

	@Override
	public HashMap<String, String> getAllServiziInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		HashMap<String, String> serviziMap = new HashMap<String, String>();
		if (this.isCollectionByNamePresent(collectionName)) {
			for (Document document: this.database.getCollection(collectionName).find()) {
				try {
					Servizio s = this.sFact.buildServizioUnmarshallJSON(document.toJson());
					String servizioJSON = s.toJSON();
					String servizioID = s.getCodiceServizio();
					serviziMap.put(servizioID, servizioJSON);
				} catch (JAXBException e) {
					throw new UnknownDBException("Unable elaborate read Servizio : " + e.getMessage());
				}
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
		return serviziMap;
	}
	
	@Override
	public HashMap<String, TommyEnrichedJSON> getAllServiziEnrichedInCollection(String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		HashMap<String, TommyEnrichedJSON> serviziMap = new HashMap<String, TommyEnrichedJSON>();
		if (this.isCollectionByNamePresent(collectionName)) {
			for (Document document: this.database.getCollection(collectionName).find()) {
				try {
					Servizio s = this.sFact.buildServizioUnmarshallJSON(document.toJson());
					String servizioJSON = s.toJSON();
					String servizioID = s.getCodiceServizio();
					TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioID, servizioJSON);
					serviziMap.put(servizioID, servizioEnriched);
				} catch (JAXBException e) {
					throw new UnknownDBException("Unable elaborate read Servizio : " + e.getMessage());
				}
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
		return serviziMap;
	}

	@Override
	public Boolean isCollectionByNamePresent(String collectionName) throws UnknownDBException, UnknownDBException {
		return this.getCollectionList().contains(collectionName);
	}

	@Override
	public Boolean isDBAlive() throws UnknownDBException {
		return true;
	}

	@Override
	public Boolean isServizioByIDPresent(String servizioID, String collectionName)
			throws CollectionNotPresentException, UnknownDBException {
		if (this.isCollectionByNamePresent(collectionName)) {
			Document condition = new Document("$eq", servizioID);
			Document filter = new Document("ID", condition);
			int servizioInstancesNum = 0;
			for (Document document: this.database.getCollection(collectionName).find(filter)) {
				servizioInstancesNum += 1;
			}
			if (servizioInstancesNum == 0) {
				return false;
			} else if (servizioInstancesNum == 1) {
				return true;
			} else {
				throw new UnknownDBException("Servizio " + servizioID + " was found " + servizioInstancesNum + " in DB");
			}		
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}

	@Override
	public void moveServizioByID(String servizioID, String oldCollectionName, String newCollectionName)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, ServizioNotPresentException,
			UnknownDBException {
		if (this.areServiziEnriched()) { // Enriched JSON
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
		if (this.isCollectionByNamePresent(collectionName)) {
			String servizio = this.getServizioByID(servizioID, collectionName);
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("ID", servizioID);
			DeleteResult result = this.database.getCollection(collectionName).deleteMany(deleteQuery);
			if (result.getDeletedCount() != 1) {
				throw new UnknownDBException("Servizio " + servizioID + " was deleted " + result.getDeletedCount() + " times in DB");
			}
			return servizio;			
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}

	@Override
	public TommyEnrichedJSON removeServizioEnrichedByID(String servizioID, String collectionName)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		if (this.isCollectionByNamePresent(collectionName)) {
			TommyEnrichedJSON servizioEnriched = this.getServizioEnrichedByID(servizioID, collectionName);
			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("ID", servizioID);
			DeleteResult result = this.database.getCollection(collectionName).deleteMany(deleteQuery);
			if (result.getDeletedCount() != 1) {
				throw new UnknownDBException("Servizio " + servizioID + " was deleted " + result.getDeletedCount() + " times in DB");
			}
			return servizioEnriched;			
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}

	@Override
	public void updateServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		this.removeServizioByID(servizioID, collectionName);		
		try {
			this.writeNewServizioByID(servizioID, collectionName, servizioJSON);
		} catch (ServizioAlreadyInCollectionDBException | UnknownDBException e) {
			throw new UnknownDBException("Failed to Update Servizio " + servizioID + ": " + e.getMessage());
		}
	}

	@Override
	public void updateServizioEnrichedByID(String servizioID, String collectionName, TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioNotPresentException, UnknownDBException {
		this.removeServizioByID(servizioID, collectionName);		
		try {
			this.writeNewServizioEnrichedByID(servizioID, collectionName, servizioEnriched);
		} catch (ServizioAlreadyInCollectionDBException | UnknownDBException e) {
			throw new UnknownDBException("Failed to Update Servizio " + servizioID + ": " + e.getMessage());
		}
	}

	@Override
	public void writeNewServizioByID(String servizioID, String collectionName, String servizioJSON)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		if (this.isCollectionByNamePresent(collectionName)) {
			if (!this.isServizioByIDPresent(servizioID, collectionName)) {
				Document servizioDocument = Document.parse(servizioJSON);				;
				this.database.getCollection(collectionName).insertOne(servizioDocument);
			} else {
				throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already Present in collection " + collectionName, servizioID, collectionName);
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}
	}

	@Override
	public void writeNewServizioEnrichedByID(String servizioID, String collectionName,
			TommyEnrichedJSON servizioEnriched)
			throws CollectionNotPresentException, ServizioAlreadyInCollectionDBException, UnknownDBException {
		if (this.isCollectionByNamePresent(collectionName)) {
			if (!this.isServizioByIDPresent(servizioID, collectionName)) {
				Document servizioDocument = Document.parse(servizioEnriched.getJsonServizio());				;
				this.database.getCollection(collectionName).insertOne(servizioDocument);
			} else {
				throw new ServizioAlreadyInCollectionDBException("Servizio " + servizioID + " already Present in collection " + collectionName, servizioID, collectionName);
			}
		} else {
			throw new CollectionNotPresentException("Collection " + collectionName + " Not Present in DB", collectionName);
		}

	}

}
