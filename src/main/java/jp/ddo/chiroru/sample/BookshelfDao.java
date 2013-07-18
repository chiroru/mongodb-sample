package jp.ddo.chiroru.sample;

import java.util.List;

public interface BookshelfDao {

    Bookshelf findById(String id);
    
    List<Bookshelf> findAll();
    
    int regist(Bookshelf bookshelf);
    
    int save(Bookshelf bookshelf);
    
    int remove(String id);
}
