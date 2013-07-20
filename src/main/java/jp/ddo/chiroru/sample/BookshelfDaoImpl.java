package jp.ddo.chiroru.sample;

import java.net.UnknownHostException;

import jp.ddo.chiroru.mongoutils.MongoDaoImpl;

public class BookshelfDaoImpl
        extends MongoDaoImpl<Bookshelf>
        implements BookshelfDao {

    private final static String COLLECTION_NAME = "shelves";

    public BookshelfDaoImpl()
            throws UnknownHostException {
        super(COLLECTION_NAME, Bookshelf.class);
    }
}
