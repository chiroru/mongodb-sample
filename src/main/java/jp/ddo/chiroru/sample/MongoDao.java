package jp.ddo.chiroru.sample;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDao {

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 27018;
    private final static String DB_NAME = "sample";

    protected MongoClient client;
    protected DB db;
    
    public MongoDao()
            throws UnknownHostException {
        client = new MongoClient(HOST, PORT);
        db = client.getDB(DB_NAME);
    }
}
