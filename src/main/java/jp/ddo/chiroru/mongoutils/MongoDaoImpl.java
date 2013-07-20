package jp.ddo.chiroru.mongoutils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class MongoDaoImpl<T>
        implements MongoDao<T> {

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 27017;
    private final static String DB_NAME = "sample";

    protected MongoClient client;
    protected DB db;
    protected DBCollection collection;
    protected Class<T> clazz;

    public MongoDaoImpl(String collectionName, Class<T> clazz)
            throws UnknownHostException {
        this.client = new MongoClient(HOST, PORT);
        this.client.setWriteConcern(WriteConcern.SAFE);
        this.db = client.getDB(DB_NAME);
        this.collection = db.getCollection(collectionName);
        this.clazz = clazz;
    }
    
    @Override
    public T findById(String id) {
        DBObject o = null;
        o = collection.findOne(new ObjectId(id));
        return DBObjectUtils.toDomain(o, clazz);
    }

    @Override
    public List<T> findAll() {
        List<T> l = new ArrayList<>();
        DBCursor c = collection.find();
        while (c.hasNext()) {
            l.add(DBObjectUtils.toDomain(c.next(), clazz));
        }
        return l;
    }

    @Override
    public int regist(Object domain) {
        WriteResult result = collection.insert(DBObjectUtils.toDBObject(domain));
        return result.getN();
    }

    @Override
    public int save(Object domain) {
        DBObject target = DBObjectUtils.toDBObject(domain);
        DBObject query = DBObjectUtils.toDBObjectOnelyId(domain);
        WriteResult result = collection.update(query, target);
        return result.getN();
    }

    @Override
    public int remove(String id) {
        DBObject o = DBObjectUtils.toDBObject(findById(id));
        WriteResult r = collection.remove(o);
        return r.getN();
    }

}
