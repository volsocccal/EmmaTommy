package emmaTommy.Mongo.Actors;


import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class MongoReadWriteManager {
	
	
	protected Mongo mongo;
	protected DB db;
	protected DBCollection collection;
	
	public MongoReadWriteManager () {
		
		try {
			this.mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.db = this.mongo.getDB("yourdb");
		this.collection = this.db.getCollection("dummyColl");
		
	}
	
	protected void writeJSONMongo(String jsonData) {
		
		try {
			DBObject dbObject = (DBObject) JSON.parse(jsonData);
			
			this.collection.insert(dbObject);
			System.out.println("Done");
			
		} catch (MongoException e) {
			e.printStackTrace();
		}
		
	}
	
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }
}
