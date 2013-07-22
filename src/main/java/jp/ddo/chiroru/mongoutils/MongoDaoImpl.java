package jp.ddo.chiroru.mongoutils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class MongoDaoImpl<T>
        implements MongoDao<T> {

    private static final MongoManager manager = MongoManager.getInstance();
    protected final static DB db = manager.getDb();
    protected DBCollection collection;
    protected Class<T> clazz;

    public MongoDaoImpl(String collectionName, Class<T> clazz)
            throws UnknownHostException {
        // collectionはプールから取得される 
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
    public List<T> findAll(int numToSkip , int batchSize) {
        List<T> l = new ArrayList<>();
        DBCursor c = collection.find();
        c.skip(numToSkip);
        c.batchSize(batchSize);
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

    @Override
    public long getCount() {
        return collection.getCount();
    }
}
