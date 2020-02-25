package emmaTommy.EmmaTommyConverter.Actors;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBConnect;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBDisconnect;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBMoveData;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBReadData;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBRemoveData;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MongoDBWriteData;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.ServizioDataJSON;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.Factories.ServizioFactory;

public class MongoHandler extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected Mongo mongoClient;
	protected DB db;
	protected String dbName;
	protected String idFieldName;
	protected String dbIP;
	protected int dbPort;
	//protected String username = "";
	//protected String psswd = "";
	protected ArrayList<String> managedCollectionsNames;
	protected ServizioFactory sFact;
	
	public static Props props(String text, String confPath) {
        return Props.create(MongoHandler.class, text, confPath);
    }
	
	public MongoHandler (String confPath) {
		
		// Logger Method Name
		String method_name = "::MongoHandler(): ";
		
		// Define and Load Configuration File
		Properties prop = new Properties();
		logger.trace(method_name + "Loading Properties FileName: " + confPath);
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(confPath);
		} catch (FileNotFoundException e) {
			logger.fatal(method_name + e.getMessage());
		}
		try {
		    prop.load(fileStream);
		    logger.trace(method_name + prop.toString());
		} catch (IOException e) {
			logger.fatal(method_name + e.getMessage());
		}		
				
		// Load Configuration Data
		this.dbName = prop.getProperty("dbName");
		this.idFieldName = prop.getProperty("idFieldName");
		this.dbIP = prop.getProperty("dbIP"); // = "localhost";
		this.dbPort = Integer.parseInt(prop.getProperty("dbPort")); // = 27017;
		//this.username = prop.getProperty("username");
		//this.psswd = prop.getProperty("psswd");
		
		// Create Servizio Factory
		this.sFact = new ServizioFactory(); 		
		this.managedCollectionsNames = new ArrayList<String>();
		
	}
	
	protected void connectMongoDb() throws UnknownHostException {	
		String method_name = "::connectMongoDb(): ";
		this.mongoClient = new MongoClient(dbIP, dbPort);
		if (this.mongoClient == null) {
			throw new NullPointerException("Couldn't Create Connection to MongoDB Server");
		}
		this.db = this.mongoClient.getDB(dbName);
		if (this.db == null) {
			throw new NullPointerException("Couldn't Create Connection to DB " + dbName);
		}
		// Check if all collections are present, otherwise create them
		Set<String> dbCollectionNames = this.db.getCollectionNames();		
		for (String collectionName : this.managedCollectionsNames) {
            if (!dbCollectionNames.contains(collectionName)) { // Collection doesn't exists, will create it
            	logger.warn(method_name + "Collection " + collectionName + " doesn't exists in db " + dbName + ", will create it");
            	this.db.createCollection(collectionName, null);
            }	
        }		
	}
	
	protected void disconnectMongoDb() {
	
		if (this.mongoClient != null) {
			this.mongoClient.close();
			this.db = null;
		}
	}
	
	protected ArrayList<String> readJSONMongo (int servizioID, String collectionName) {		
		ArrayList<String> res = new ArrayList<String>();
		if (!managedCollectionsNames.contains(collectionName)) {
			throw new IllegalArgumentException("Collection " + collectionName + " is not in the list of the service collections");
		}
		if (this.db == null) {
			throw new NullPointerException ("Mongo Db was null");
		}
		DBCollection collection = this.db.getCollection(collectionName);
		if (collection == null) {
			throw new NullPointerException ("Mongo Connection " +  collectionName + " was null");
		}
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(this.idFieldName, servizioID);		
		DBCursor cursor = collection.find(searchQuery);
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			String dbJSON = dbObj.toString();
			res.add(dbJSON);
		}		
		return res;
	}
	
	protected ArrayList<String> readCollectionJSONMongo (String collectionName) {		
		ArrayList<String> res = new ArrayList<String>();
		if (!managedCollectionsNames.contains(collectionName)) {
			throw new IllegalArgumentException("Collection " + collectionName + " is not in the list of the service collections");
		}
		if (this.db == null) {
			throw new NullPointerException ("Mongo Db was null");
		}
		DBCollection collection = this.db.getCollection(collectionName);
		if (collection == null) {
			throw new NullPointerException ("Mongo Connection " +  collectionName + " was null");
		}	
		DBCursor cursor = (DBCursor) collection.find().iterator();
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			String dbJSON = dbObj.toString();
			res.add(dbJSON);
		}		
		return res;
	}
	
	protected void removeJSONMongo (int servizioID, String collectionName) {	
		if (!managedCollectionsNames.contains(collectionName)) {
			throw new IllegalArgumentException("Collection " + collectionName + " is not in the list of the service collections");
		}
		if (this.db == null) {
			throw new NullPointerException ("Mongo Db was null");
		}
		DBCollection collection = this.db.getCollection(collectionName);
		if (collection == null) {
			throw new NullPointerException ("Mongo Connection " +  collectionName + " was null");
		}
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(this.idFieldName, servizioID);		
		WriteResult wr = collection.remove(searchQuery);
		if (collection.find(searchQuery).hasNext()) {
			throw new MongoException("Failed to remove Servizio " + servizioID + ": unknown error");
		}
	}
	
	protected void writeJSONMongo(String json, int servizioID, String destCollection) throws JAXBException {		
		if (!managedCollectionsNames.contains(destCollection)) {
			throw new IllegalArgumentException("Destination Collection " + destCollection + " is not in the list of the service collections");
		}
		if (this.db == null) {
			throw new NullPointerException ("Mongo Db was null");
		}
		DBCollection collection = this.db.getCollection(destCollection);
		if (collection == null) {
			throw new NullPointerException ("Mongo Connection " +  destCollection + " was null");
		}		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(this.idFieldName, servizioID);
		DBCursor cursor = collection.find(searchQuery);
		if (cursor.hasNext()) { // If there is already a Servizio, check if I have to update it
			DBObject dbObj = cursor.next();
			String dbJSON = dbObj.toString();
			Servizio dbServizio = sFact.buildServizioUnmarshallJSON(dbJSON);
			Servizio newServizio = sFact.buildServizioUnmarshallJSON(json);
			if (!dbServizio.equals(newServizio)) {
				DBObject dbObjectUpdatedServizio = (DBObject) JSON.parse(json);					
				WriteResult wr = collection.update(searchQuery, dbObjectUpdatedServizio);
				if (wr.getN() <= 0) {
					throw new MongoException("Failed to update Servizio " + servizioID + ": " + wr.getError());
				}
			}
		} else { // Write the new Servizio in the DB
			DBObject dbObjectNewServizio = (DBObject) JSON.parse(json);
			WriteResult wr = collection.insert(dbObjectNewServizio);
			if (wr.getN() <= 0) {
				throw new MongoException("Failed to insert new Servizio " + servizioID + ": " + wr.getError());
			}
		}		
	}
	
	protected void moveJSONMongo (int servizioID, String srcCollectionName, String destCollectionName) throws JAXBException {
		
		if (!managedCollectionsNames.contains(srcCollectionName)) {
			throw new IllegalArgumentException("Source Collection " + srcCollectionName + " is not in the list of the service collections");
		}
		if (!managedCollectionsNames.contains(destCollectionName)) {
			throw new IllegalArgumentException("Destination Collection " + destCollectionName + " is not in the list of the service collections");
		}
		if (this.db == null) {
			throw new NullPointerException ("Mongo Db was null");
		}
		ArrayList<String> res = this.readJSONMongo(servizioID, srcCollectionName);
		if (!res.isEmpty()) {
			String json = res.get(0);
			try {
				this.writeJSONMongo(json, servizioID, destCollectionName);
				this.removeJSONMongo(servizioID, srcCollectionName);
			} catch (JAXBException e) {
				throw e;
			}			
		}			
	}
	
	
	protected void statisticsMongo() {
		
		
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MongoDBConnect.class, this::connect)
				.match(MongoDBDisconnect.class, this::disconnect)
				.match(MongoDBReadData.class, this::onReadData)
				.match(MongoDBWriteData.class, this::onWriteData)
				.match(MongoDBMoveData.class, this::onMoveData)
				.match(MongoDBRemoveData.class, this::onRemoveData)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void connect(MongoDBConnect conn) {
		String method_name = "::connect(): ";
		logger.info(method_name + "Received Connect Event");
		this.managedCollectionsNames = new ArrayList<String>();
		this.managedCollectionsNames.add(conn.getWipCollectionName());
		logger.info(method_name + "Added " + conn.getWipCollectionName() + " to Managed Collections");
		this.managedCollectionsNames.add(conn.getPersCollectionName());
		logger.info(method_name + "Added " + conn.getPersCollectionName() + " to Managed Collections");
		try {
			this.connectMongoDb();
		} catch (Exception e) {
			logger.error(method_name + e.getMessage());
			this.managedCollectionsNames = new ArrayList<String>();
		}
	}
	
	protected void disconnect(MongoDBDisconnect conn) {
		String method_name = "::disconnect(): ";
		logger.error(method_name + "Got new disconnect Event");
		try {
			this.disconnectMongoDb();
			this.managedCollectionsNames = new ArrayList<String>();
		} catch (Exception e) {
			logger.error(method_name + e.getMessage());
		}
	}
	
	protected void onReadData(MongoDBReadData rd) {
		String method_name = "::onReadData(): ";
		ArrayList<Integer> ids = rd.getIDs();
		String collectionName = rd.getCollectionName();
		logger.error(method_name + "Reading from Collection " + collectionName);
		ArrayList<String> readJSONS = new ArrayList<String>();

		// Read Data
		if (ids.isEmpty()) {
			try {
				readJSONS = this.readCollectionJSONMongo(collectionName);
			} catch (Exception e) {
				logger.error(method_name + "Collection " + collectionName + ": " + e.getMessage());
			}
		} else {
			for (Integer servizioID: ids) {
				try {
					readJSONS.addAll(this.readJSONMongo(servizioID, collectionName));
					logger.info(method_name + "Read Servizio " + servizioID);
				} catch (Exception e) {
					logger.error(method_name + "Servizio " + servizioID + ": " + e.getMessage());
				}
			}
		}
		
		// Send Back read data
		for (String json: readJSONS) {			
			try {
				Servizio s = this.sFact.buildServizioUnmarshallJSON(json);
				this.getSender().tell(new ServizioDataJSON(Integer.parseInt(s.getCodiceServizio()), s.toJSON(), s.validateObject()), this.getSelf());
				logger.info(method_name + "Sent Servizio " + s.getCodiceServizio() + " to " + this.getSender().path().name());
			} catch (NumberFormatException e) {
				logger.error(method_name + "Failed to get Codice Servizio: " + e.getMessage());
			} catch (JAXBException e) {
				logger.error(method_name + "Failed to Marshall/Unmarshall Servizio: " + e.getMessage());
			}
		}
		
	}
	
	protected void onWriteData(MongoDBWriteData wd) {
		String method_name = "::onWriteData(): ";
		int servizioID = wd.getID();
		String collectionName = wd.getCollectionName();
		String json = wd.getJSON();
		try {
			this.writeJSONMongo(json, servizioID, collectionName);
			logger.info(method_name + "Wrote Servizio " + servizioID + " to Collection " + collectionName);
		} catch (Exception e) {
			logger.error(method_name + "Servizio " + servizioID + ": " + e.getMessage());
		}
	}
	
	protected void onMoveData(MongoDBMoveData md) {
		String method_name = "::onMoveData(): ";
		int servizioID = md.getID();
		String srcCollectionName = md.getSrcCollectionName();
		String destCollectionName = md.getDestCollectionName();
		try {
			this.moveJSONMongo(servizioID, srcCollectionName, destCollectionName);
			logger.info(method_name + "Removed Servizio " + servizioID + " from Collection " + srcCollectionName + " to Collection " + destCollectionName);
		} catch (Exception e) {
			logger.error(method_name + "Servizio " + servizioID + ": " + e.getMessage());
		}
	}
	
	protected void onRemoveData(MongoDBRemoveData rmd) {
		String method_name = "::onRemoveData(): ";
		int servizioID = rmd.getID();
		String collectionName = rmd.getCollectionName();
		try {
			this.removeJSONMongo(servizioID, collectionName);
			logger.info(method_name + "Removed Servizio " + servizioID + " from Collection " + collectionName);
		} catch (Exception e) {
			logger.error(method_name + "Servizio " + servizioID + ": " + e.getMessage());
		}
		
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		logger.info(method_name + "Closing Mongo Connection");
		this.disconnectMongoDb();
		
		
	}
	
	
}
