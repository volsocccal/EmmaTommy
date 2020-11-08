package emmaTommy.DBServerAbstraction.Actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import emmaTommy.DBClient.ActorsMessages.Queries.AcquireDBLock;
import emmaTommy.DBClient.ActorsMessages.Queries.GetAllServiziInCollection;
import emmaTommy.DBClient.ActorsMessages.Queries.GetAllServiziInCollectionByProperties;
import emmaTommy.DBClient.ActorsMessages.Queries.GetCollectionList;
import emmaTommy.DBClient.ActorsMessages.Queries.GetServizioByID;
import emmaTommy.DBClient.ActorsMessages.Queries.IsCollectionByNamePresent;
import emmaTommy.DBClient.ActorsMessages.Queries.IsDBAlive;
import emmaTommy.DBClient.ActorsMessages.Queries.IsDBLocked;
import emmaTommy.DBClient.ActorsMessages.Queries.IsDBManagerActive;
import emmaTommy.DBClient.ActorsMessages.Queries.IsServizioByIDPresent;
import emmaTommy.DBClient.ActorsMessages.Queries.MoveServizioByID;
import emmaTommy.DBClient.ActorsMessages.Queries.ReleaseDBLock;
import emmaTommy.DBClient.ActorsMessages.Queries.RemoveServizioByID;
import emmaTommy.DBClient.ActorsMessages.Queries.UpdateServizioByID;
import emmaTommy.DBClient.ActorsMessages.Queries.UpdateServizioEnrichedByID;
import emmaTommy.DBClient.ActorsMessages.Queries.WriteNewServizioByID;
import emmaTommy.DBClient.ActorsMessages.Queries.WriteNewServizioEnrichedByID;
import emmaTommy.DBClient.ActorsMessages.Replies.CollectionFound;
import emmaTommy.DBClient.ActorsMessages.Replies.CollectionListSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.CollectionNotFound;
import emmaTommy.DBClient.ActorsMessages.Replies.DBIsAlive;
import emmaTommy.DBClient.ActorsMessages.Replies.DBIsAlreadyLocked;
import emmaTommy.DBClient.ActorsMessages.Replies.DBIsLockedByYou;
import emmaTommy.DBClient.ActorsMessages.Replies.DBIsNotAlive;
import emmaTommy.DBClient.ActorsMessages.Replies.DBIsNotLocked;
import emmaTommy.DBClient.ActorsMessages.Replies.DBLockAcquired;
import emmaTommy.DBClient.ActorsMessages.Replies.DBLockReleaseUnowning;
import emmaTommy.DBClient.ActorsMessages.Replies.DBLockReleased;
import emmaTommy.DBClient.ActorsMessages.Replies.DBManagerActive;
import emmaTommy.DBClient.ActorsMessages.Replies.DBManagerErrorState;
import emmaTommy.DBClient.ActorsMessages.Replies.DBManagerInitializing;
import emmaTommy.DBClient.ActorsMessages.Replies.DBManagerPause;
import emmaTommy.DBClient.ActorsMessages.Replies.DBManagerStatus;
import emmaTommy.DBClient.ActorsMessages.Replies.DBOperationFaillure;
import emmaTommy.DBClient.ActorsMessages.Replies.MoveServizioByIDSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.RemoveServizioByIDSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.ReplyServiziInCollection;
import emmaTommy.DBClient.ActorsMessages.Replies.ReplyServiziInCollectionEnriched;
import emmaTommy.DBClient.ActorsMessages.Replies.ReplyServizioById;
import emmaTommy.DBClient.ActorsMessages.Replies.ReplyServizioByIdEnriched;
import emmaTommy.DBClient.ActorsMessages.Replies.ServizioByIDAlreadyPresentInCollection;
import emmaTommy.DBClient.ActorsMessages.Replies.ServizioByIDFound;
import emmaTommy.DBClient.ActorsMessages.Replies.ServizioByIDNotFound;
import emmaTommy.DBClient.ActorsMessages.Replies.UpdateServizioByIDEnrichedSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.UpdateServizioByIDSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.WriteNewServizioByIDEnrichedSuccess;
import emmaTommy.DBClient.ActorsMessages.Replies.WriteNewServizioByIDSuccess;
import emmaTommy.DBServerAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBServerAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.DBServerAbstraction.DBHandlers.AbstractDB;
import emmaTommy.DataModel.DateAdapterYYMMDD;
import emmaTommy.DataModel.DateTimeAdapterHHmm;
import emmaTommy.TommyDataModel.Assistito;
import emmaTommy.TommyDataModel.Membro;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.TommyDataModelEnums;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import emmaTommy.TommyDataModel.Factories.ServizioQueryField;

public class DBServer extends AbstractActor {

	protected org.apache.logging.log4j.Logger logger;
	
	protected String DBServerActorName;	
	
	protected DBLock lock;
	
	protected enum DBState {
		ACTIVE,
		PAUSE,
		INITIALIZING,
		ERROR
	} 
	protected DBState state;
	
	protected AbstractDB db;

	protected DBServer(String DBServerActorName, AbstractDB db) {
		
		// Logger Method Name
		String method_name = "::AbstractDBServer(): ";
		this.state = DBState.INITIALIZING;
		
		// Check Input Data
		if (DBServerActorName == null) {
			throw new NullPointerException("Reveived Null DBServerActorName");
		}
		if (DBServerActorName.isBlank()) {
			throw new NullPointerException("Reveived Blank DBServerActorName");
		}
		if (db == null) {
			throw new NullPointerException("Reveived Null db instance");
		}
		
		// Save Configuration Data
		this.DBServerActorName = DBServerActorName;		
		this.logger = LogManager.getLogger(DBServerActorName);
		this.db = db;
		this.state = DBState.ACTIVE;
		
		// Create Lock
		this.lock = new DBLock();
		
		// Log Start
		logger.info(method_name + "Started DB Server Actor");
		logger.info(method_name + "DB Server Actor Name: " + DBServerActorName);
		logger.info(method_name + "DB Server Actor State: " + this.state.name());
		
		
	}
	
	public Boolean checkValidLock(ActorRef actor, String actorName, String actorID) {
		if (this.lock.isLocked()) {
			String lockOwnerName = this.lock.getLockOwnerName();
			String lockOwnerID = this.lock.getLockOwnerID();
			if (lockOwnerName.compareTo(actorName) != 0) { // Actor Name Is Different
				actor.tell(new DBIsAlreadyLocked(lockOwnerName, lockOwnerID), this.getSelf());
				return false;
			} else { // Actor Name is Same
				if (lockOwnerID.compareTo(actorID) != 0) { // Actor ID Is Different
					actor.tell(new DBIsAlreadyLocked(lockOwnerName, lockOwnerID), this.getSelf());
					return false;
				} else { //
					return true;
				}
			}
		} else {
			return true;
		}
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(AcquireDBLock.class, this::onAquireDBLockQuery)
				.match(GetCollectionList.class, this::onGetCollectionListQuery)
				.match(GetServizioByID.class, this::onGetServizioByIDQuery)
				.match(GetAllServiziInCollectionByProperties.class, this::onGetAllServiziInCollectionByPropertiesQuery)
				.match(GetAllServiziInCollection.class, this::onGetAllServiziInCollectionQuery)
				.match(IsCollectionByNamePresent.class, this::onIsCollectionByNamePresentQuery)
				.match(IsDBAlive.class, this::onIsDBAliveQuery)
				.match(IsDBLocked.class, this::onIsDBLockedQuery)
				.match(IsDBManagerActive.class, this::onIsDBManagerActiveQuery)
				.match(IsServizioByIDPresent.class, this::onIsServizioByIDPresent)
				.match(MoveServizioByID.class, this::onMoveServizioByIDQuery)
				.match(ReleaseDBLock.class, this::onReleaseDBLockQuery)
				.match(RemoveServizioByID.class, this::onRemoveServizioByIDQuery)
				.match(UpdateServizioByID.class, this::onUpdateServizioByIDQuery)
				.match(UpdateServizioEnrichedByID.class, this::onUpdateServizioEnrichedByIDQuery)
				.match(WriteNewServizioByID.class, this::onWriteNewServizioByIDQuery)
				.match(WriteNewServizioEnrichedByID.class, this::onWriteNewServizioEnrichedByIDQuery)
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	
	protected void onAquireDBLockQuery(AcquireDBLock queryObj) {
		String method_name = "::onAquireDBLockQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Received new AcquireDBLock Query from " + callingClientName + " ID " + callingClientID);
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwnerName().compareTo(callingClientName) == 0 
				&& this.lock.getLockOwnerID().compareTo(callingClientID) == 0) {				
				logger.trace(method_name + "DB is already locked by calling client " + callingClientName + " ID " + callingClientID);
				this.getSender().tell(new DBIsLockedByYou(), this.getSelf());
				logger.trace(method_name + "Sent DBIsLockedByYou Reply to " + callingClientName);
			} else {
				logger.trace(method_name + "DB is locked by " + this.lock.getLockOwnerName() + " ID " + this.lock.getLockOwnerID());;
				this.getSender().tell(new DBIsAlreadyLocked(this.lock.getLockOwnerName(), this.lock.getLockOwnerID()), this.getSelf());
				logger.trace(method_name + "Sent DBIsAlreadyLocked Reply to " + callingClientName);
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.lock.acquireLock(callingClientName, callingClientID);
			logger.info(method_name + "DB Lock Acquired by " + callingClientName + " ID " + callingClientID);
			this.getSender().tell(new DBLockAcquired(), this.getSelf());
			logger.trace(method_name + "Sent DBLockAcquired Reply to " + callingClientName);
		}
	}
	
	protected void onGetCollectionListQuery(GetCollectionList queryObj) {
		String method_name = "::onGetCollectionListQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived CollectionListQuery from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {	
			try {
				logger.trace(method_name + "Reading CollectionList from DB");
				ArrayList<String> collectionListNames = this.db.getCollectionList();
				this.getSender().tell(new CollectionListSuccess(collectionListNames), 
										this.getSelf());
				logger.trace(method_name + "Sent CollectionList to " + callingClientName);
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}
		}
	}
	
	protected void onGetServizioByIDQuery(GetServizioByID queryObj) {
		String method_name = "::onGetServizioByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived GetServizioByIDQuery from " + callingClientName + " ID " + callingClientID);
		String wantedCollectionName = queryObj.getCollectionName();
		String servizioID = queryObj.getID();
		logger.trace(method_name + callingClientName + " wants servizio " + servizioID + " from collection " +  wantedCollectionName);
		try {				
			if (this.db.areServiziEnriched()) { // Enriched JSON					
				TommyEnrichedJSON servizio = this.db.getServizioEnrichedByID(servizioID, wantedCollectionName);
				logger.trace(method_name + "Sending servizio " + servizioID + " from collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServizioByIdEnriched(servizioID, 
																	servizio,
																	wantedCollectionName), 
									  this.getSelf());				
			} else { // Raw JSON
				String servizio = this.db.getServizioByID(servizioID, wantedCollectionName);
				logger.trace(method_name + "Sending servizio " + servizioID + " from collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServizioById(servizioID, 
																	servizio,
																	wantedCollectionName), 
									  this.getSelf());	
			}
			
		} catch (CollectionNotPresentException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
								  this.getSelf());
		} catch (ServizioNotPresentException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new ServizioByIDNotFound(e.getServizioID(), e.getCollectionName()), 
									this.getSelf());
		} catch (UnknownDBException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
									this.getSelf());
		}		
	}
	
	protected void onGetAllServiziInCollectionByPropertiesQuery (GetAllServiziInCollectionByProperties queryObj) {
		String method_name = "::onGetAllServiziInCollectionByPropertiesQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived GetAllServiziInCollectionByProperties from " + callingClientName + " ID " + callingClientID);
		TreeMap<ServizioQueryField, String> propNVmap = queryObj.getPropertyNamesValuesMap();
		String wantedCollectionName = queryObj.getCollectionName();
		logger.trace(method_name + callingClientName + " wants servizi in collection " +  wantedCollectionName);
		try {
			if (this.db.areServiziEnriched()) { // Enriched JSON
				HashMap<String, TommyEnrichedJSON> serviziEnrichedMap = this.db.GetAllServiziEnrichedInCollectionByProp(wantedCollectionName, propNVmap);
				logger.trace(method_name + "Sending " + serviziEnrichedMap.size() + " servizi enriched in collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServiziInCollectionEnriched(serviziEnrichedMap, wantedCollectionName), 
									  this.getSelf());
			} else { // Raw JSON
				HashMap<String, String> serviziMap = this.db.GetAllServiziInCollectionByProp(wantedCollectionName, propNVmap);
				logger.trace(method_name + "Sending all servizi in collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServiziInCollection(serviziMap, wantedCollectionName), 
									  this.getSelf());
			}
		} catch (CollectionNotPresentException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
								  this.getSelf());
		} catch (UnknownDBException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
									this.getSelf());
		}
	}
	protected Boolean checkServizioProp(GetAllServiziInCollectionByProperties queryObj, Servizio s) {
		Boolean deleteServizio = false;
		DateTimeAdapterHHmm timeFormatter = new DateTimeAdapterHHmm();
		DateAdapterYYMMDD dateFormatter = new DateAdapterYYMMDD();
		for (ServizioQueryField propName: queryObj.getPropertyNamesValuesMap().keySet()) {
			String propValue = queryObj.getPropertyNamesValuesMap().get(propName);
			if (propName == ServizioQueryField.assistitoFemale) {
				if (s.getAssistiti() == null)
					deleteServizio = true;
				else if (s.getAssistiti().isEmpty())
					deleteServizio = true;
				Boolean localDelete = true;
				for (Assistito ass: s.getAssistiti()) {
					if (ass == null)
						deleteServizio = true;
					else if (ass.getSesso().compareTo(TommyDataModelEnums.FEMALE_GENDER) == 0)
						localDelete = false;
				}
				if (localDelete)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.assistitoMale) {
				if (s.getAssistiti() == null)
					deleteServizio = true;
				else if (s.getAssistiti().isEmpty())
					deleteServizio = true;
				Boolean localDelete = true;
				for (Assistito ass: s.getAssistiti()) {
					if (ass == null)
						deleteServizio = true;
					else if (ass.getSesso().compareTo(TommyDataModelEnums.MALE_GENDER) == 0)
						localDelete = false;
				}
				if (localDelete)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.codice_servizio) {
				if (s.getCodiceServizio().compareTo(propValue) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_0) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (!s.getSquadra().isEmpty())
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_1) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (s.getSquadra().isEmpty())
					deleteServizio = true;
				else if (s.getSquadra().size() != 1)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_2) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (s.getSquadra().isEmpty())
					deleteServizio = true;
				if (s.getSquadra().size() != 2)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_3) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (s.getSquadra().isEmpty())
					deleteServizio = true;
				else if (s.getSquadra().size() != 3)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_4) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (s.getSquadra().isEmpty())
					deleteServizio = true;
				else if (s.getSquadra().size() != 4)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.equipaggio_trainee) {
				if (s.getSquadra() == null)
					deleteServizio = true;
				else if (s.getSquadra().isEmpty())
					deleteServizio = true;
				Boolean localDelete = true;
				for (Membro m: s.getSquadra()) {
					if (m == null)
						deleteServizio = true;
					if (m.getTagIdQualifica().compareTo(TommyDataModelEnums.SOCCORRITORE_ADD) == 0)
						localDelete = false;
				}
				if (localDelete)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.km) {
				if (Integer.toString(s.getKM()).compareTo(propValue) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.luogo_partenza) {
				if (s.getLuogoPartenza().compareTo(propValue) != 0) 
					deleteServizio = true;
			} else if (propName == ServizioQueryField.luogo_arrivo) {
				if (s.getLuogoArrivo().compareTo(propValue) != 0) 
					deleteServizio = true;
			} else if (propName == ServizioQueryField.membro_equipaggio) {
				String[] anagraphic = propValue.split(", ");
				if (anagraphic.length < 1 || anagraphic.length > 2)
					deleteServizio = true;
				else {
					String surname_name = anagraphic[0];
					String qualifica = null;
					if (anagraphic.length == 2)
						qualifica = anagraphic[1];
					Boolean localDelete = false;
					for (Membro m: s.getSquadra()) {
						if (m == null)
							deleteServizio = true;
						if (surname_name.compareTo(m.getTagIdAnagrafica()) == 0) {
							localDelete = false;
							if (qualifica != null) {
								if (qualifica.compareTo(m.getTagIdQualifica()) != 0)
									localDelete = true;
								else
									localDelete = false;
							}
						} else {
							localDelete = true;
						}
					}
					if (localDelete)
						deleteServizio = true;
				}
			} else if (propName == ServizioQueryField.missioneDate) {
				if (s.getMissioneDate().compareTo(dateFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_inizio_servizio) {
				if (s.getOrarioInizioServizio().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_arrivo_posto) {
				if (s.getOrarioArrivoPosto().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_partenza_posto) {
				if (s.getOrarioPartenzaPosto().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_arrivo_ospedale) {
				if (s.getOrarioArrivoOspedale().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_partenza_ospedale) {
				if (s.getOrarioPartenzaOspedale().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.orario_fine_servizio) {				
				if (s.getOrarioFineServizio().compareTo(timeFormatter.unmarshal(propValue)) != 0)
					deleteServizio = true;
			} else if (propName == ServizioQueryField.patient) {
				String[] anagraphic = propValue.split(", ");
				if (anagraphic.length != 2)
					deleteServizio = true;
				else {
					String surname = anagraphic[0];
					String name = anagraphic[1];
					Boolean localDelete = false;
					for (Assistito ass: s.getAssistiti()) {
						if (ass == null)
							deleteServizio = true;
						if (surname.compareTo(ass.getCognome()) == 0) {
							localDelete = false;
							if (name.compareTo(ass.getNome()) != 0)
								localDelete = true;
							else
								localDelete = false;
						} else {
							localDelete = true;
						}
					}
					if (localDelete)
						deleteServizio = true;
				}
			} else if (propName == ServizioQueryField.tag_idintervento) {
				if (propValue.compareTo(s.getTagIntervento()) != 0)
					deleteServizio = true;
			}  else if (propName == ServizioQueryField.tag_idautomezzo) {
				if (propValue.compareTo(s.getTagIdAutomezzo()) != 0)
					deleteServizio = true;
			}
		}
		return deleteServizio;
	}
	
	protected void onGetAllServiziInCollectionQuery(GetAllServiziInCollection queryObj) {
		String method_name = "::onGetAllServiziInCollectionQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived GetAllServiziInCollection from " + callingClientName + " ID " + callingClientID);
		String wantedCollectionName = queryObj.getCollectionName();
		logger.trace(method_name + callingClientName + " wants all servizi in collection " +  wantedCollectionName);
		try {
			if (this.db.areServiziEnriched()) { // Enriched JSON
				logger.trace(method_name + "Sending all servizi enriched in collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServiziInCollectionEnriched(this.db.getAllServiziEnrichedInCollection(wantedCollectionName), wantedCollectionName), 
									  this.getSelf());
			} else { // Raw JSON
				logger.trace(method_name + "Sending all servizi in collection " +  wantedCollectionName + " to " + callingClientName);
				this.getSender().tell(new ReplyServiziInCollection(this.db.getAllServiziInCollection(wantedCollectionName), wantedCollectionName), 
									  this.getSelf());
			}
		} catch (CollectionNotPresentException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
								  this.getSelf());
		} catch (UnknownDBException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
									this.getSelf());
		}	
	}
	
	protected void onIsCollectionByNamePresentQuery(IsCollectionByNamePresent queryObj) {
		String method_name = "::onIsCollectionByNamePresentQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived IsCollectionByNamePresentQuery from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {	
			try {
				String wantedCollectionName = queryObj.getCollectionName();
				if (this.db.isCollectionByNamePresent(wantedCollectionName)) { // Collection Found
					logger.trace(method_name + "Collection " +  wantedCollectionName + " was present in the DB");
					this.getSender().tell(new CollectionFound(wantedCollectionName), 
										  this.getSelf());
				} else { // Collection Not Found
					logger.error(method_name + "Collection " +  wantedCollectionName + " wasn't present in the DB");
					this.getSender().tell(new CollectionNotFound(wantedCollectionName), 
										  this.getSelf());
				}
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}
		}
	}
	
	protected void onIsDBAliveQuery(IsDBAlive queryObj) {
		String method_name = "::onIsDBAliveQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived IsDBAliveQuery from " + callingClientName + " ID " + callingClientID);
		try {
			if (this.db.isDBAlive()) {
				logger.trace(method_name + "DB Is Alive");
				this.getSender().tell(new DBIsAlive(), this.getSelf());
			} else {
				logger.error(method_name + "DB Is Not Alive");
				this.getSender().tell(new DBIsNotAlive("Couldn't Connect to DB"), this.getSelf());
			}
		} catch (UnknownDBException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
									this.getSelf());
		}
	}
	
	protected void onIsDBLockedQuery(IsDBLocked queryObj) {
		String method_name = "::onIsDBLockedQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Received IsDBLocked Query from " + callingClientName + " ID " + callingClientID);
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwnerName().compareTo(callingClientName) == 0 
				&& this.lock.getLockOwnerID().compareTo(callingClientID) == 0) {				
				logger.trace(method_name + "DB is already locked by calling client " + callingClientName + " ID " + callingClientID);
				this.getSender().tell(new DBIsLockedByYou(), this.getSelf());
				logger.trace(method_name + "Sent DBIsLockedByYou Reply to " + callingClientName);
			} else {
				logger.trace(method_name + "DB is already locked by " + this.lock.getLockOwnerName() + " ID " + this.lock.getLockOwnerID());;
				this.getSender().tell(new DBIsAlreadyLocked(this.lock.getLockOwnerName(), this.lock.getLockOwnerID()), this.getSelf());
				logger.trace(method_name + "Sent DBIsAlreadyLocked Reply to " + callingClientName);
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.getSender().tell(new DBIsNotLocked(), this.getSelf());
			logger.trace(method_name + "Sent DBIsNotLocked Reply to " + callingClientName);
		}
	}
	
	protected void onIsDBManagerActiveQuery(IsDBManagerActive queryObj) {
		String method_name = "::onIsDBManagerActiveQuery(): ";
		logger.trace("Received IsDBManagerActive Query from " + this.getSender().path().name());
		logger.trace(method_name + "Current state is: " + this.state.name());
		DBManagerStatus st;
		if (this.state == DBState.ACTIVE) 
			st = new DBManagerActive();
		else if (this.state == DBState.INITIALIZING)
			st = new DBManagerInitializing();
		else if (this.state == DBState.PAUSE)
			st = new DBManagerPause();
		else if (this.state == DBState.ERROR)
			st = new DBManagerErrorState();
		else {
			logger.error("Unknown local state: " + this.state.name());
			st = new DBManagerErrorState();
		}
		this.getSender().tell(st, this.getSelf());
		logger.trace(method_name + "Sent DBManagerStatus Reply to " + this.getSender().path().name());
	}
	
	protected void onIsServizioByIDPresent(IsServizioByIDPresent queryObj) {
		String method_name = "::onIsServizioByIDPresent(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived IsServizioByIDPresent from " + callingClientName + " ID " + callingClientID);
		String wantedCollectionName = queryObj.getCollectionName();
		String servizioID = queryObj.getID();
		logger.trace(method_name + callingClientName + " wants to know if servizio " + servizioID + " in collection " +  wantedCollectionName + " is present");
		try {
			HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(wantedCollectionName);
			String serviziLog = "Servizi in Collection " + wantedCollectionName + ": " + serviziInCollection.size();
			if (serviziInCollection.isEmpty()) {
				serviziLog += " - Empty";
			} else {
				for (String servizioId: serviziInCollection.keySet()) {
					serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
				}				
			}
			logger.trace(method_name + serviziLog);
			if (this.db.isServizioByIDPresent(servizioID, wantedCollectionName)) { // Servizio Found
				logger.trace(method_name + "Sending servizioByIdFound (servizio " + servizioID + " from collection " +  wantedCollectionName + ") to " + callingClientName);
				this.getSender().tell(new ServizioByIDFound(servizioID, wantedCollectionName), 
									  this.getSelf());
			} else {  // Servizio Not Found
				logger.warn(method_name + "Sending servizioByIdJNotFound (servizio " + servizioID + " from collection " +  wantedCollectionName + ") to " + callingClientName);
				this.getSender().tell(new ServizioByIDNotFound(servizioID, wantedCollectionName), 
						  				this.getSelf());
			}
		} catch (CollectionNotPresentException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
								  this.getSelf());
		} catch (UnknownDBException e) {
			logger.error(method_name + e.getMessage());
			this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
									this.getSelf());
		}
	}
	
	protected void onMoveServizioByIDQuery(MoveServizioByID queryObj) {
		String method_name = "::onMoveServizioByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived MoveServizioByIDQuery from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {	
			String oldCollectionName = queryObj.GetOldCollectionName();
			String newCollectionName = queryObj.GetNewCollectionName();
			String servizioID = queryObj.getServizioID();
			logger.trace(method_name + callingClientName + " wants to move servizio " + servizioID 
									 + " from collection " +  oldCollectionName
									 + " to collection " +  newCollectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					this.db.moveServizioByID(servizioID, oldCollectionName, newCollectionName);
				} else { // Raw JSON
					this.db.moveServizioByID(servizioID, oldCollectionName, newCollectionName);
				}
				logger.trace(method_name + "Moved servizio " + servizioID 
						 + " from collection " +  oldCollectionName
						 + " to collection " +  newCollectionName);
				this.getSender().tell(new MoveServizioByIDSuccess(servizioID, 
																	oldCollectionName,
																	newCollectionName), 
								  		this.getSelf());
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDNotFound(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (ServizioAlreadyInCollectionDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}	
		}
	}
	
	protected void onReleaseDBLockQuery(ReleaseDBLock queryObj) {		
		String method_name = "::onReleaseDBLockQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Received ReleaseDBLock Query from " + callingClientName + " ID " + callingClientID);
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwnerName().compareTo(callingClientName) == 0 
				&& this.lock.getLockOwnerID().compareTo(callingClientID) == 0) {				
				logger.trace(method_name + "DB is locked by calling client " + callingClientName + " ID " + callingClientID);
				this.lock.releaseLock(callingClientName, callingClientID);
				this.getSender().tell(new DBLockReleased(), this.getSelf());
				logger.trace(method_name + "Sent DBLockReleased Reply to " + callingClientName);
			} else {
				logger.trace(method_name + "DB is locked by " + this.lock.getLockOwnerName() + " ID " + this.lock.getLockOwnerID());;
				this.getSender().tell(new DBLockReleaseUnowning(this.lock.getLockOwnerName(), this.lock.getLockOwnerID()), this.getSelf());
				logger.trace(method_name + "Sent DBLockReleaseUnowning Reply to " + callingClientName);
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.getSender().tell(new DBIsNotLocked(), this.getSelf());
			logger.trace(method_name + "Sent DBIsNotLocked Reply to " + callingClientName);
		}
	}
	
	protected void onRemoveServizioByIDQuery(RemoveServizioByID queryObj) {
		String method_name = "::onRemoveServizioByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived RemoveServizioByIDQuery from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {			
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getServizioID();
			logger.trace(method_name + callingClientName + " wants to remove servizio " + servizioID + " from collection " +  collectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					TommyEnrichedJSON servizio = this.db.removeServizioEnrichedByID(servizioID, collectionName);
					logger.trace(method_name + "Removed Servizio " + servizioID + " from collection " +  collectionName);
					this.getSender().tell(new RemoveServizioByIDSuccess(servizioID, servizio.getJsonServizio(), collectionName), 
									  		this.getSelf());
				} else { // Raw JSON
					String servizio = this.db.removeServizioByID(servizioID, collectionName);
					logger.trace(method_name + "Removed Servizio " + servizioID + " from collection " +  collectionName);
					this.getSender().tell(new RemoveServizioByIDSuccess(servizioID, servizio, collectionName), 
									  		this.getSelf());
				}
				HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(collectionName);
				String serviziLog = "Servizi in Collection " + collectionName + ": " + serviziInCollection.size();
				if (serviziInCollection.isEmpty()) {
					serviziLog += " - Empty";
				} else {
					for (String servizioId: serviziInCollection.keySet()) {
						serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
					}				
				}
				logger.trace(method_name + serviziLog);
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDNotFound(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}
		}
	}
	
	protected void onUpdateServizioByIDQuery(UpdateServizioByID queryObj) {
		String method_name = "::onUpdateServizioByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived UpdateServizioByID from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {			
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getServizioID();
			String servizioUpdated = queryObj.getUpdatedServizioJSON();
			logger.trace(method_name + callingClientName + " wants to update servizio " + servizioID + " from collection " +  collectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					TommyEnrichedJSON servizioOldEnriched = this.db.getServizioEnrichedByID(servizioID, collectionName);
					this.db.updateServizioEnrichedByID(servizioID, collectionName, new TommyEnrichedJSON(servizioID, servizioUpdated));
					logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
					this.getSender().tell(new UpdateServizioByIDSuccess(servizioID, 
																		servizioOldEnriched.getJsonServizio(), 
																		servizioUpdated, 
																		collectionName), 
					  						this.getSelf());
				} else { // Raw JSON
					String servizioOld = this.db.getServizioByID(servizioID, collectionName);
					this.db.updateServizioByID(servizioID, collectionName, servizioUpdated);
					logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
					this.getSender().tell(new UpdateServizioByIDSuccess(servizioID, 
																		servizioOld, 
																		servizioUpdated, 
																		collectionName), 
					  						this.getSelf());
				}
				HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(collectionName);
				String serviziLog = "Servizi in Collection " + collectionName + ": " + serviziInCollection.size();
				if (serviziInCollection.isEmpty()) {
					serviziLog += " - Empty";
				} else {
					for (String servizioId: serviziInCollection.keySet()) {
						serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
					}				
				}
				logger.trace(method_name + serviziLog);
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDNotFound(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}			
		}
	}
	
	protected void onUpdateServizioEnrichedByIDQuery(UpdateServizioEnrichedByID queryObj) {
		String method_name = "::onUpdateServizioEnrichedByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived UpdateServizioByID from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {			
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getServizioID();
			TommyEnrichedJSON servizioEnrichedUpdated = queryObj.getUpdatedServizioEnrichedJSON();
			logger.trace(method_name + callingClientName + " wants to update servizio " + servizioID + " in collection " +  collectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					TommyEnrichedJSON servizioOldEnriched = this.db.getServizioEnrichedByID(servizioID, collectionName);
					this.db.updateServizioEnrichedByID(servizioID, collectionName, servizioEnrichedUpdated);
					logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
					this.getSender().tell(new UpdateServizioByIDEnrichedSuccess(servizioID, 
																		servizioOldEnriched, 
																		servizioEnrichedUpdated, 
																		collectionName), 
					  						this.getSelf());
				} else { // Raw JSON
					String servizioOld = this.db.getServizioByID(servizioID, collectionName);
					this.db.updateServizioByID(servizioID, collectionName, servizioEnrichedUpdated.getJsonServizio());
					logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
					this.getSender().tell(new UpdateServizioByIDSuccess(servizioID, 
																		servizioOld, 
																		servizioEnrichedUpdated.getJsonServizio(), 
																		collectionName), 
					  						this.getSelf());
				}
				HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(collectionName);
				String serviziLog = "Servizi in Collection " + collectionName + ": " + serviziInCollection.size();
				if (serviziInCollection.isEmpty()) {
					serviziLog += " - Empty";
				} else {
					for (String servizioId: serviziInCollection.keySet()) {
						serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
					}				
				}
				logger.trace(method_name + serviziLog);
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDNotFound(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}			
		}
	}
	
	protected void onWriteNewServizioByIDQuery(WriteNewServizioByID queryObj) {
		String method_name = "::onWriteNewServizioByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived WriteNewServizioByID from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {			
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getServizioID();
			String servizioNew = queryObj.getNewServizioJSON();
			logger.trace(method_name + callingClientName + " wants to write servizio " + servizioID + " in collection " +  collectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					this.db.writeNewServizioEnrichedByID(servizioID, collectionName, new TommyEnrichedJSON(servizioID, servizioNew));
					logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
					this.getSender().tell(new WriteNewServizioByIDSuccess(servizioID, servizioNew, collectionName), 
					  						this.getSelf());
				} else { // Raw JSON
					this.db.writeNewServizioByID(servizioID, collectionName, servizioNew);
					logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
					this.getSender().tell(new WriteNewServizioByIDSuccess(servizioID, servizioNew, collectionName), 
									  		this.getSelf());
				}
				HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(collectionName);
				String serviziLog = "Servizi in Collection " + collectionName + ": " + serviziInCollection.size();
				if (serviziInCollection.isEmpty()) {
					serviziLog += " - Empty";
				} else {
					for (String servizioId: serviziInCollection.keySet()) {
						serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
					}				
				}
				logger.trace(method_name + serviziLog);
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioAlreadyInCollectionDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}
		}
	}
	
	protected void onWriteNewServizioEnrichedByIDQuery(WriteNewServizioEnrichedByID queryObj) {
		String method_name = "::onWriteNewServizioEnrichedByIDQuery(): ";
		String callingClientName = queryObj.getCallingActorName();
		String callingClientID = queryObj.getCallingActorID();
		logger.trace("Reveived WriteNewServizioByID from " + callingClientName + " ID " + callingClientID);
		if (checkValidLock(this.getSender(), callingClientName, callingClientID)) {			
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getServizioID();
			TommyEnrichedJSON servizioEnrichedNew = queryObj.getNewServizioEnrichedJSON();
			logger.trace(method_name + callingClientName + " wants to write servizio " + servizioID + " in collection " +  collectionName);
			try {
				if (this.db.areServiziEnriched()) { // Enriched JSON
					this.db.writeNewServizioEnrichedByID(servizioID, collectionName, servizioEnrichedNew);
					logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
					this.getSender().tell(new WriteNewServizioByIDEnrichedSuccess(servizioID, servizioEnrichedNew, collectionName), 
					  						this.getSelf());
				} else { // Raw JSON
					this.db.writeNewServizioByID(servizioID, collectionName, servizioEnrichedNew.getJsonServizio());
					logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
					this.getSender().tell(new WriteNewServizioByIDSuccess(servizioID, servizioEnrichedNew.getJsonServizio(), collectionName), 
									  		this.getSelf());
				}
				HashMap<String, TommyEnrichedJSON> serviziInCollection = this.db.getAllServiziEnrichedInCollection(collectionName);
				String serviziLog = "Servizi in Collection " + collectionName + ": " + serviziInCollection.size();
				if (serviziInCollection.isEmpty()) {
					serviziLog += " - Empty";
				} else {
					for (String servizioId: serviziInCollection.keySet()) {
						serviziLog += "\n" + servizioId + " - Status " + serviziInCollection.get(servizioId).getPostStatusStr();
					}				
				}
				logger.trace(method_name + serviziLog);
			} catch (CollectionNotPresentException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new CollectionNotFound(e.getCollectionName()), 
									  this.getSelf());
			} catch (ServizioAlreadyInCollectionDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(e.getServizioID(), e.getCollectionName()), 
										this.getSelf());
			} catch (UnknownDBException e) {
				logger.error(method_name + e.getMessage());
				this.getSender().tell(new DBOperationFaillure(e.getMessage()), 
										this.getSelf());
			}
		}
	}

	
}
