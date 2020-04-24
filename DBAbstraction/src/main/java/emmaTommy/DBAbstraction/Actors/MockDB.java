package emmaTommy.DBAbstraction.Actors;

import java.util.ArrayList;
import java.util.HashMap;

import akka.actor.Props;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetCollectionList;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsCollectionByNamePresent;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.IsDBAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.MoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.RemoveServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.UpdateServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.WriteNewServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionListSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.CollectionNotFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsAlive;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.MoveServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.RemoveServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServizioById;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServizioByIdEnriched;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDAlreadyPresentInCollection;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDNotFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioNotValid;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.UpdateServizioByIDSuccess;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.WriteNewServizioByIDSuccess;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;


public class MockDB extends AbstractDBServer {
	
	protected HashMap<String, HashMap<String, TommyEnrichedJSON>> db_enriched;
	protected HashMap<String, HashMap<String, String>> db_raw;
	protected ArrayList<String> collectionListNames;
	
	public static Props props(String text, 
								String DBName, 
								String DBTech, 
								String DBType,  
								Boolean supportEnrichedJSON,
								ArrayList<String> collectionListNames) {
        return Props.create(MockDB.class, text, 
			        		DBName, 
							DBTech, 
							DBType, 
							supportEnrichedJSON,
							collectionListNames);
    }

	private MockDB(String DBName, 
			String DBTech, 
			String DBType, 
			Boolean supportEnrichedJSON,
			ArrayList<String> collectionListNames) {
		super(DBName, DBTech, DBType, true, supportEnrichedJSON);
		
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
		}
		
		// Create DB Map
		if (this.supportEnrichedJSON) {
			this.db_enriched = new HashMap<String, HashMap<String, TommyEnrichedJSON>>();
			for (String collectionName: this.collectionListNames) {
				this.db_enriched.put(collectionName, new HashMap<String, TommyEnrichedJSON>());
			}
		} else {
			this.db_raw = new HashMap<String, HashMap<String, String>>();
			for (String collectionName: this.collectionListNames) {
				this.db_raw.put(collectionName, new HashMap<String, String>());
			}
		}
		
	}

	@Override
	protected void onGetCollectionListQuery(GetCollectionList queryObj) {
		String method_name = "::onGetCollectionListQuery(): ";
		logger.trace("Reveived CollectionListQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {	
			logger.trace(method_name + "Sending CollectionList to " + this.getSender().path().name());
			this.getSender().tell(new CollectionListSuccess(this.collectionListNames), 
									this.getSelf());
		}
	}

	@Override
	protected void onGetServizioByIDQuery(GetServizioByID queryObj) {
		String method_name = "::onGetServizioByIDQuery(): ";
		logger.trace("Reveived GetServizioByIDQuery from " + this.getSender().path().name());
		String wantedCollectionName = queryObj.getCollectionName();
		String servizioID = queryObj.getID();
		logger.trace(method_name + this.getSender().path().name() + " wants servizio " + servizioID + " from collection " +  wantedCollectionName);
		if (this.collectionListNames.contains(wantedCollectionName)) { // Collection Found
			if (this.supportEnrichedJSON) { // Enriched JSON
				if (this.db_enriched.get(wantedCollectionName).containsKey(servizioID)) {
					TommyEnrichedJSON servizio = this.db_enriched.get(wantedCollectionName)
																 .get(servizioID);
					logger.trace(method_name + "Sending servizio " + servizioID + " from collection " +  wantedCollectionName + " to " + this.getSender().path().name());
					this.getSender().tell(new ReplyServizioByIdEnriched(servizioID, 
																		servizio,
																		wantedCollectionName), 
										  this.getSelf());
				} else {  // Servizio Not Found
					logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  wantedCollectionName);
					this.getSender().tell(new ServizioByIDNotFound(servizioID, wantedCollectionName), 
							  				this.getSelf());
				}
			} else { // Raw JSON
				if (this.db_raw.get(wantedCollectionName).containsKey(servizioID)) {
					String json = this.db_raw.get(wantedCollectionName)
											 .get(servizioID);
					logger.trace(method_name + "Sending servizio " + servizioID + " from collection " +  wantedCollectionName + " to " + this.getSender().path().name());
					this.getSender().tell(new ReplyServizioById(servizioID, 
																json,
																wantedCollectionName), 
										  this.getSelf());
				} else {  // Servizio Not Found
					logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  wantedCollectionName);
					this.getSender().tell(new ServizioByIDNotFound(servizioID, wantedCollectionName), 
			  								this.getSelf());
				}
			}
		} else { // Collection Not Found
			logger.error(method_name + "Collection " +  wantedCollectionName + " wasn't present in the DB");
			this.getSender().tell(new CollectionNotFound(wantedCollectionName), 
								  this.getSelf());
		}
	}

	@Override
	protected void onIsCollectionByNamePresentQuery(IsCollectionByNamePresent queryObj) {
		String method_name = "::onIsCollectionByNamePresentQuery(): ";
		logger.trace("Reveived IsCollectionByNamePresentQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {	
			String wantedCollectionName = queryObj.getCollectionName();
			if (this.collectionListNames.contains(wantedCollectionName)) {
				logger.trace(method_name + "Collection " +  wantedCollectionName + " was present in the DB");
				this.getSender().tell(new CollectionFound(wantedCollectionName), 
									  this.getSelf());
			} else {
				logger.error(method_name + "Collection " +  wantedCollectionName + " wasn't present in the DB");
				this.getSender().tell(new CollectionNotFound(wantedCollectionName), 
									  this.getSelf());
			}
		}
	}

	@Override
	protected void onIsDBAliveQuery(IsDBAlive queryObj) {
		String method_name = "::onIsDBAliveQuery(): ";
		logger.trace("Reveived IsDBAliveQuery from " + this.getSender().path().name());
		logger.trace(method_name + "DB Is Alive");
		this.getSender().tell(new DBIsAlive(), this.getSelf());
	}

	@Override
	protected void onMoveServizioByIDQuery(MoveServizioByID queryObj) {
		String method_name = "::onMoveServizioByIDQuery(): ";
		logger.trace("Reveived MoveServizioByIDQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {	
			String oldCollectionName = queryObj.GetOldCollectionName();
			String newCollectionName = queryObj.GetNewCollectionName();
			String servizioID = queryObj.getID();
			logger.trace(method_name + this.getSender().path().name() + " wants to move servizio " + servizioID 
									 + " from collection " +  oldCollectionName
									 + " to collection " +  newCollectionName);
			if (this.collectionListNames.contains(oldCollectionName)) { // Old Collection Name Found
				if (this.collectionListNames.contains(newCollectionName)) {  // New Collection Name Found					
					if (this.supportEnrichedJSON) { // Enriched JSON
						if (this.db_enriched.get(oldCollectionName).containsKey(servizioID)) { // Servizio Found in Old Collection
							if (!this.db_enriched.get(newCollectionName).containsKey(servizioID)) { // Servizio Not Found in New Collection
								TommyEnrichedJSON servizio = this.db_enriched.get(oldCollectionName)
																			 .get(servizioID);
								this.db_enriched.get(oldCollectionName).remove(servizioID);
								this.db_enriched.get(oldCollectionName).put(servizioID, servizio);
								logger.trace(method_name + "Moved servizio " + servizioID 
										 + " from collection " +  oldCollectionName
										 + " to collection " +  newCollectionName);
								this.getSender().tell(new MoveServizioByIDSuccess(servizioID, 
																					oldCollectionName,
																					newCollectionName), 
												  	this.getSelf());
							} else { // Servizio Found in New Collection
								logger.error(method_name + "Servizio " + servizioID 
										 				 + " was already present in destination collection " +  newCollectionName);
								this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(servizioID, newCollectionName), 
														this.getSelf());

							}
						} else {  // Servizio Not Found  in Old Collection
							logger.error(method_name + "Servizio " + servizioID 
					 				 				 + " wasn't present in destination collection " +  oldCollectionName);
							this.getSender().tell(new ServizioByIDNotFound(servizioID, oldCollectionName), 
									  				this.getSelf());
						}
					} else { // Raw JSON
						if (this.db_raw.get(oldCollectionName).containsKey(servizioID)) {  // Servizio Found in Old Collection
							if (!this.db_raw.get(newCollectionName).containsKey(servizioID)) { // Servizio Not Found in New Collection
								String servizio = this.db_raw.get(oldCollectionName)
																			 .get(servizioID);
								this.db_raw.get(oldCollectionName).remove(servizioID);
								this.db_raw.get(oldCollectionName).put(servizioID, servizio);
								logger.trace(method_name + "Moved servizio " + servizioID 
														 + " from collection " +  oldCollectionName
														 + " to collection " +  newCollectionName);
								this.getSender().tell(new MoveServizioByIDSuccess(servizioID, 
																					oldCollectionName,
																					newCollectionName), 
												  		this.getSelf());
							} else { // Servizio Found in New Collection
								logger.error(method_name + "Servizio " + servizioID 
						 				 							   + " was already present in destination collection " +  newCollectionName);
								this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(servizioID, newCollectionName), 
														this.getSelf());

							}
						} else {  // Servizio Not Found  in Old Collection
							logger.error(method_name + "Servizio " + servizioID 
													 + " wasn't present in destination collection " +  oldCollectionName);
							this.getSender().tell(new ServizioByIDNotFound(servizioID, oldCollectionName), 
					  								this.getSelf());
						}
					}
				} else {  // New Collection Name Not Found
					logger.error(method_name + "Destination Collection " +  newCollectionName + " wasn't present in the DB");
					this.getSender().tell(new CollectionNotFound(newCollectionName), 
										  this.getSelf());
				}
			} else {  // Old Collection Name Not Found
				logger.error(method_name + "Start Collection " +  newCollectionName + " wasn't present in the DB");
				this.getSender().tell(new CollectionNotFound(oldCollectionName), 
									  this.getSelf());
			}
		}
	}

	@Override
	protected void onRemoveServizioByIDQuery(RemoveServizioByID queryObj) {
		String method_name = "::onRemoveServizioByIDQuery(): ";
		logger.trace("Reveived RemoveServizioByIDQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {				
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getID();
			logger.trace(method_name + this.getSender().path().name() + " wants to remove servizio " + servizioID + " from collection " +  collectionName);
			if (this.collectionListNames.contains(collectionName)) { // Collection Name Found
				if (this.supportEnrichedJSON) { // Enriched JSON
					if (this.db_enriched.get(collectionName).containsKey(servizioID)) {  // Servizio Found in Collection						
						TommyEnrichedJSON servizio = this.db_enriched.get(collectionName).get(servizioID);
						this.db_enriched.get(collectionName).remove(servizioID);
						logger.trace(method_name + "Removed Servizio " + servizioID + " from collection " +  collectionName);
						this.getSender().tell(new RemoveServizioByIDSuccess(servizioID, servizio.getJsonServizio(), collectionName), 
										  		this.getSelf());					
					} else {  // Servizio Not Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDNotFound(servizioID, collectionName), 
				  								this.getSelf());
					}
				} else { // Raw JSON
					if (this.db_raw.get(collectionName).containsKey(servizioID)) {  // Servizio Found in Collection						
							String servizio = this.db_raw.get(collectionName).get(servizioID);
							this.db_raw.get(collectionName).remove(servizioID);
							logger.trace(method_name + "Removed Servizio " + servizioID + " from collection " +  collectionName);
							this.getSender().tell(new RemoveServizioByIDSuccess(servizioID, servizio, collectionName), 
											  		this.getSelf());						
					} else {  // Servizio Not Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDNotFound(servizioID, collectionName), 
				  								this.getSelf());
					}
				}
				
			} else {  // Collection Name Not Found
				logger.error(method_name + "Collection " +  collectionName + " wasn't present in the DB");
				this.getSender().tell(new CollectionNotFound(collectionName), 
									  this.getSelf());
			}
		}
	}

	@Override
	protected void onUpdateServizioByIDQuery(UpdateServizioByID queryObj) {
		String method_name = "::onUpdateServizioByIDQuery(): ";
		logger.trace("Reveived UpdateServizioByIDQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {	
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getID();
			logger.trace(method_name + this.getSender().path().name() + " wants to update servizio " + servizioID + " in collection " +  collectionName);
			if (this.collectionListNames.contains(collectionName)) { // Collection Name Found	
				String json = queryObj.getUpdatedServizioJSON();
				if (this.supportEnrichedJSON) { // Enriched JSON
					if (this.db_enriched.get(collectionName).containsKey(servizioID)) {  // Servizio Found in Collection
						String oldJson = this.db_enriched.get(collectionName).get(servizioID).getJsonServizio();
						try {
							TommyEnrichedJSON servizio = new TommyEnrichedJSON(json);						
							this.db_enriched.get(collectionName).put(servizioID, servizio);
							logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
							this.getSender().tell(new UpdateServizioByIDSuccess(servizioID, oldJson, servizio.getJsonServizio(), collectionName), 
											  		this.getSelf());
						} catch (IllegalArgumentException e){
							logger.error(method_name + "Servizio " + servizioID + " wasn't valid: " +  e.getMessage());
							this.getSender().tell(new ServizioNotValid(e.getMessage(), servizioID, json, collectionName), 
							  						this.getSelf());
						}
					} else {  // Servizio Not Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDNotFound(servizioID, collectionName), 
				  								this.getSelf());
					}
				} else { // Raw JSON
					if (this.db_raw.get(collectionName).containsKey(servizioID)) {  // Servizio Found in Collection		
						String oldJson = this.db_raw.get(collectionName).get(servizioID);
						this.db_raw.get(collectionName).put(servizioID, json);
						logger.trace(method_name + "Updated Servizio " + servizioID + " in collection " +  collectionName);
						this.getSender().tell(new UpdateServizioByIDSuccess(servizioID, oldJson, json, collectionName), 
										  		this.getSelf());						
					} else {  // Servizio Not Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " not found in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDNotFound(servizioID, collectionName), 
				  								this.getSelf());
					}
				}
				
			} else {  // Collection Name Not Found
				logger.error(method_name + "Collection " +  collectionName + " wasn't present in the DB");
				this.getSender().tell(new CollectionNotFound(collectionName), 
									  this.getSelf());
			}
		}
	}

	@Override
	protected void onWriteNewServizioByIDQuery(WriteNewServizioByID queryObj) {
		String method_name = "::onWriteNewServizioByIDQuery(): ";
		logger.trace("Reveived WriteNewServizioByIDQuery from " + this.getSender().path().name());
		if (checkValidLock(this.getSender())) {	
			String collectionName = queryObj.getCollectionName();
			String servizioID = queryObj.getID();
			logger.trace(method_name + this.getSender().path().name() + " wants to write servizio " + servizioID + " to collection " +  collectionName);
			if (this.collectionListNames.contains(collectionName)) { // Collection Name Found		
				String json = queryObj.getNewServizioJSON();
				if (this.supportEnrichedJSON) { // Enriched JSON
					if (!this.db_enriched.get(collectionName).containsKey(servizioID)) {  // Servizio Not Found in Collection
						try {
							TommyEnrichedJSON servizio = new TommyEnrichedJSON(json);						
							this.db_enriched.get(collectionName).put(servizioID, servizio);
							logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
							this.getSender().tell(new RemoveServizioByIDSuccess(servizioID, servizio.getJsonServizio(), collectionName), 
											  		this.getSelf());
						} catch (IllegalArgumentException e){
							logger.error(method_name + "Servizio " + servizioID + " wasn't valid " +  e.getMessage());
							this.getSender().tell(new ServizioNotValid(e.getMessage(), servizioID, json, collectionName), 
							  						this.getSelf());
						}
					} else {  // Servizio Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " already present in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(servizioID, collectionName), 
				  								this.getSelf());
					}
				} else { // Raw JSON
					if (!this.db_raw.get(collectionName).containsKey(servizioID)) {  // Servizio NotFound in Collection		
						this.db_raw.get(collectionName).put(servizioID, json);
						logger.trace(method_name + "Wrote Servizio " + servizioID + " to collection " +  collectionName);
						this.getSender().tell(new WriteNewServizioByIDSuccess(servizioID, json, collectionName), 
										  		this.getSelf());						
					} else {  // Servizio Found in Collection
						logger.error(method_name + "Servizio " + servizioID + " already present in collection " +  collectionName);
						this.getSender().tell(new ServizioByIDAlreadyPresentInCollection(servizioID, collectionName), 
  												this.getSelf());
					}
				}
				
			} else {  // Collection Name Not Found
				logger.error(method_name + "Collection " +  collectionName + " wasn't present in the DB");
				this.getSender().tell(new CollectionNotFound(collectionName), 
									  this.getSelf());
			}
		}
	}

}
