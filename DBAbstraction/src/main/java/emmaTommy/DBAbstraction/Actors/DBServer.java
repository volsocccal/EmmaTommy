package emmaTommy.DBAbstraction.Actors;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.AcquireDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetAllServiziInCollection;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetCollectionList;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsCollectionByNamePresent;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBManagerActive;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsServizioByIDPresent;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.MoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.ReleaseDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.RemoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.UpdateServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.WriteNewServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionListSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionNotFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsAlreadyLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsLockedByYou;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsNotAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsNotLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockAcquired;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockReleaseUnowning;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockReleased;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerActive;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerErrorState;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerInitializing;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerPause;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerStatus;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBOperationFaillure;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.MoveServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.RemoveServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServiziInCollection;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServiziInCollectionEnriched;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServizioById;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServizioByIdEnriched;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDAlreadyPresentInCollection;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDNotFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.UpdateServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.WriteNewServizioByIDSuccess;
import emmaTommy.DBAbstraction.DBExceptions.CollectionNotPresentException;
import emmaTommy.DBAbstraction.DBExceptions.ServizioAlreadyInCollectionDBException;
import emmaTommy.DBAbstraction.DBExceptions.ServizioNotPresentException;
import emmaTommy.DBAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.DBAbstraction.DBHandlers.AbstractDB;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;

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
				.match(WriteNewServizioByID.class, this::onWriteNewServizioByIDQuery)
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
		logger.trace(method_name + callingClientName + " wants servizio " + servizioID + " from collection " +  wantedCollectionName);
		try {
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
			logger.trace(method_name + callingClientName + " wants to remove servizio " + servizioID + " from collection " +  collectionName);
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
			logger.trace(method_name + callingClientName + " wants to remove servizio " + servizioID + " from collection " +  collectionName);
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
