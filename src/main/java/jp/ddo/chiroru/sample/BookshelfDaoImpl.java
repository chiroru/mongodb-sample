package jp.ddo.chiroru.sample;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class BookshelfDaoImpl
extends MongoDao
implements BookshelfDao {

    private final static String COLLECTION_NAME = "shelves";

    private DBCollection shelves;

    public BookshelfDaoImpl()
            throws UnknownHostException {
        super();
        shelves = db.getCollection(COLLECTION_NAME);
    }

    @Override
    public Bookshelf findById(String id) {
        DBObject o = null;
        BasicDBObject query = new BasicDBObject("_id", id);
        DBCursor c = shelves.find(query);
        if (c.length() != 1) throw new RuntimeException();
        while (c.hasNext()) {
            o = c.next();
        }
        return dBObject2Bookshelf(o);
    }

    @Override
    public List<Bookshelf> findAll() {
        List<Bookshelf> l = new ArrayList<Bookshelf>();
        DBCursor c = shelves.find();
        while (c.hasNext()) {
            l.add(dBObject2Bookshelf(c.next()));
        }
        return l;
    }

    @Override
    public int regist(Bookshelf bookshelf) {
        WriteResult result = shelves.insert(bookshelf2DBObject(bookshelf));
        return result.getN();
    }

    @Override
    public int save(Bookshelf bookshelf) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int remove(String id) {
        WriteResult r = shelves.remove(bookshelf2DBObject(findById(id)));
        return r.getN();
    }

    private Bookshelf dBObject2Bookshelf(DBObject o) {
        if (o == null) throw new AssertionError();
        Bookshelf b = new Bookshelf();
        b.setId(((ObjectId)o.get("_id")).toStringMongod());
        b.setName((String)o.get("name"));
        b.setDescription((String)o.get("description"));
        return b;
    }

    private DBObject bookshelf2DBObject(Bookshelf bookshelf) {
        if (bookshelf == null) throw new AssertionError();
        BasicDBObject dbo = new BasicDBObject("name", bookshelf.getName())
        .append("description", bookshelf.getDescription());
        return dbo;
    }
}
