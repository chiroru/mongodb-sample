package jp.ddo.chiroru.mongoutils;

import java.util.List;

public interface MongoDao<T> {

    T findById(String id);

    List<T> findAll();

    List<T> findAll(int numToSkip , int batchSize);

    int regist(T domain);

    int save(T domain);

    int remove(String id);

    long getCount();
}
