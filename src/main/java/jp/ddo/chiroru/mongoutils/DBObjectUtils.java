package jp.ddo.chiroru.mongoutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectUtils {

    private final static String ID_STRING = "_id";

    public static <T> T toDomain(DBObject o, Class<T> clazz) {
        T domain;
        try {
            domain = clazz.newInstance();
            BeanUtils.copyProperties(domain, o);
            System.out.println();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("DBObject can't copy to " + clazz.getSimpleName(), e);
        }
        return domain;
    }

    public static <T> DBObject toDBObject(T domain) {
        DBObject o = new BasicDBObject();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> m = BeanUtils.describe(domain);
            for(String key : m.keySet()) {
                if (ID_STRING.equals(key)) {
                    String value = (String)m.get(ID_STRING);
                    if (value != null)
                        BeanUtils.setProperty(o, ID_STRING, new ObjectId(value));
                } else {
                    BeanUtils.setProperty(o, key, m.get(key));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Domain can't copy to DBObject", e);
        }
        return o;
    }

    public static <T> DBObject toDBObjectOnelyId(T domain) {
        DBObject o = new BasicDBObject();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> m = BeanUtils.describe(domain);
            BeanUtils.setProperty(o, ID_STRING, new ObjectId((String)m.get(ID_STRING)));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Domain can't copy to DBObject", e);
        }
        return o;
    }
}
