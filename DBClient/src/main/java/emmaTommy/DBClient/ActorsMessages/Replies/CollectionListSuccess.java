package emmaTommy.DBClient.ActorsMessages.Replies;

import java.util.ArrayList;

public class CollectionListSuccess extends Reply {
	protected ArrayList<String> collectionList;
	public CollectionListSuccess() {
		super();
		this.collectionList = new ArrayList<String>();
	}
	public CollectionListSuccess(String singleCollection) {
		super();
		this.collectionList = new ArrayList<String>();
		if (singleCollection == null) {
			throw new NullPointerException("Received collection name was null");
		}
		if (singleCollection.isBlank()) {
			throw new NullPointerException("Received collection name was blanck");
		}
		this.collectionList.add(singleCollection);
	}
	public CollectionListSuccess(ArrayList<String> collectionList) {
		super();
		this.collectionList = new ArrayList<String>();
		if (collectionList == null) {
			throw new NullPointerException("Received collectionList was null");
		}
		for (String singleCollection: collectionList) {
			if (singleCollection == null) {
				throw new NullPointerException("Received collection name was null");
			}
			if (singleCollection.isBlank()) {
				throw new NullPointerException("Received collection name was blanck");
			}
			this.collectionList.add(singleCollection);
		}
	}
	public ArrayList<String> getCollectionList() {
		return this.collectionList;
	}
}
