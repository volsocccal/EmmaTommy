package emmaTommy.DBAbstraction.Actors;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.AcquireDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetCollectionList;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsCollectionByNamePresent;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBManagerActive;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.MoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.ReleaseDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.RemoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.UpdateServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.WriteNewServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsAlreadyLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsLockedByYou;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsNotLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockAcquired;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockReleaseUnowning;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockReleased;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerActive;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerErrorState;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerInitializing;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerPause;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBManagerStatus;

public abstract class AbstractDBServer extends AbstractActor {

	protected org.apache.logging.log4j.Logger logger;
	
	protected String DBName; 
	protected String DBTech;	
	protected String DBType;
	protected Boolean DBMock;
	protected Boolean supportEnrichedJSON;
	
	protected DBLock lock;
	
	protected enum DBState {
		ACTIVE,
		PAUSE,
		INITIALIZING,
		ERROR
	} 
	protected DBState state;

	protected AbstractDBServer(String DBName, String DBTech, String DBType, Boolean DBMock, Boolean supportEnrichedJSON) {
		
		// Logger Method Name
		String method_name = "::AbstractDBServer(): ";
		this.state = DBState.INITIALIZING;
		
		// Check Input Data
		if (DBName == null) {
			throw new NullPointerException("Reveived Null DBName");
		}
		if (DBName.isBlank()) {
			throw new NullPointerException("Reveived Blank DBName");
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
		this.DBName = DBName;		
		this.DBTech = DBTech;		
		this.DBType = DBType;
		this.DBMock = DBMock;
		this.supportEnrichedJSON = supportEnrichedJSON;
		this.logger = LogManager.getLogger(DBName);
		this.state = DBState.ACTIVE;
		
		// Log Start
		logger.info(method_name + "Starting DB");
		logger.info(method_name + "DB Name: " + DBName);
		logger.info(method_name + "DB Tech: " + DBTech);
		logger.info(method_name + "DB Type: " + DBType);
		logger.info(method_name + "DB Mock: " + DBMock);
		logger.info(method_name + "DB Supports Enriched JSON: " + supportEnrichedJSON);
		logger.info(method_name + "DB State: " + this.state.name());
		
		
	}
	
	public Boolean checkValidLock(ActorRef client) {
		if (this.lock.isLocked()) {
			String lockOwner = this.lock.getLockOwner();
			if (lockOwner.compareTo(client.path().name()) != 0) {
				client.tell(new DBIsAlreadyLocked(lockOwner), this.getSelf());
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(AcquireDBLock.class, this::onAquireDBLockQuery)
				.match(GetCollectionList.class, this::onGetCollectionListQuery)
				.match(GetServizioByID.class, this::onGetServizioByIDQuery)
				.match(IsCollectionByNamePresent.class, this::onIsCollectionByNamePresentQuery)
				.match(IsDBAlive.class, this::onIsDBAliveQuery)
				.match(IsDBLocked.class, this::onIsDBLockedQuery)
				.match(IsDBManagerActive.class, this::onIsDBManagerActiveQuery)
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
		logger.trace("Received new AcquireDBLock Query from " + this.getSender().path().name());
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwner().compareTo(this.getSender().path().name()) == 0) {
				logger.warn(method_name + "DB is already locked by " + this.lock.getLockOwner());
				this.getSender().tell(new DBIsAlreadyLocked(this.lock.getLockOwner()), this.getSelf());
				logger.trace(method_name + "Sent DBIsAlreadyLocked Reply to " + this.getSender().path().name());
			} else {
				logger.trace(method_name + "DB is already locked by calling client" + this.lock.getLockOwner());
				this.getSender().tell(new DBIsLockedByYou(), this.getSelf());
				logger.trace(method_name + "Sent DBIsLockedByYou Reply to " + this.getSender().path().name());
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.lock.acquireLock(this.getSender().path().name());
			logger.info(method_name + "DB Lock Acquired by " + this.lock.getLockOwner());
			this.getSender().tell(new DBLockAcquired(), this.getSelf());
			logger.trace(method_name + "Sent DBLockAcquired Reply to " + this.getSender().path().name());
		}
	}
	
	protected abstract void onGetCollectionListQuery(GetCollectionList queryObj);
	
	protected abstract void onGetServizioByIDQuery(GetServizioByID queryObj);
	
	protected abstract void onIsCollectionByNamePresentQuery(IsCollectionByNamePresent queryObj);
	
	protected abstract void onIsDBAliveQuery(IsDBAlive queryObj);
	
	protected void onIsDBLockedQuery(IsDBLocked queryObj) {
		String method_name = "::onIsDBLockedQuery(): ";
		logger.trace("Received IsDBLocked Query from " + this.getSender().path().name());
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwner().compareTo(this.getSender().path().name()) == 0) {
				logger.trace(method_name + "DB is already locked by " + this.lock.getLockOwner());
				this.getSender().tell(new DBIsAlreadyLocked(this.lock.getLockOwner()), this.getSelf());
				logger.trace(method_name + "Sent DBIsAlreadyLocked Reply to " + this.getSender().path().name());
			} else {
				logger.trace(method_name + "DB is already locked by calling client" + this.lock.getLockOwner());
				this.getSender().tell(new DBIsLockedByYou(), this.getSelf());
				logger.trace(method_name + "Sent DBIsLockedByYou Reply to " + this.getSender().path().name());
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.getSender().tell(new DBIsNotLocked(), this.getSelf());
			logger.trace(method_name + "Sent DBIsNotLocked Reply to " + this.getSender().path().name());
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
	
	
	protected abstract void onMoveServizioByIDQuery(MoveServizioByID queryObj);
	
	protected void onReleaseDBLockQuery(ReleaseDBLock queryObj) {		
		String method_name = "::onReleaseDBLockQuery(): ";
		logger.trace("Received ReleaseDBLock Query from " + this.getSender().path().name());
		if (this.lock.isLocked()) {
			if (this.lock.getLockOwner().compareTo(this.getSender().path().name()) == 0) {
				logger.warn(method_name + "DB is locked by someone else" + this.lock.getLockOwner());
				this.getSender().tell(new DBLockReleaseUnowning(this.lock.getLockOwner()), this.getSelf());
				logger.trace(method_name + "Sent DBLockReleaseUnowning Reply to " + this.getSender().path().name());
			} else {
				logger.trace(method_name + "DB is locked by calling client" + this.getSender().path().name());
				this.lock.releaseLock(this.getSender().path().name());
				this.getSender().tell(new DBLockReleased(), this.getSelf());
				logger.trace(method_name + "Sent DBLockReleased Reply to " + this.getSender().path().name());
			}
		} else {
			logger.trace(method_name + "Currently DB is not locked");
			this.getSender().tell(new DBIsNotLocked(), this.getSelf());
			logger.trace(method_name + "Sent DBIsNotLocked Reply to " + this.getSender().path().name());
		}
		
	}
	
	protected abstract void onRemoveServizioByIDQuery(RemoveServizioByID queryObj);
	
	protected abstract void onUpdateServizioByIDQuery(UpdateServizioByID queryObj);
	
	protected abstract void onWriteNewServizioByIDQuery(WriteNewServizioByID queryObj);

	
}
