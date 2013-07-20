package jp.ddo.chiroru.mongoutils;

import java.util.List;

public interface MongoDao<T> {

    T findById(String id);

    List<T> findAll();

    int regist(T domain);

    int save(T domain);

    int remove(String id);
}
