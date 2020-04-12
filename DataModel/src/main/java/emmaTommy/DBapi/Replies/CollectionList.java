package emmaTommy.DBapi.Replies;

import java.util.ArrayList;

public class CollectionList extends Reply {
	protected ArrayList<String> collectionList;
	public CollectionList() {
		super();
		this.collectionList = new ArrayList<String>();
	}
	public CollectionList(String singleCollection) {
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
	public CollectionList(ArrayList<String> collectionList) {
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
