package io.bxbxbai.zhuanlan.core;

import android.content.Context;
import orm.LiteOrm;
import orm.db.DataBase;
import orm.db.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data center
 *
 * @author bxbxbai
 */
public final class DataCenter {

    private static DataCenter instance;

    private DataBase db;

    private DataCenter(Context context, String dbName) {
        db = LiteOrm.newSingleInstance(context, dbName);
    }

    public static void init(Context context, String dbName) {
        if (instance == null) {
            synchronized (DataCenter.class) {
                if (instance == null) {
                    instance = new DataCenter(context.getApplicationContext(), dbName);
                }
            }
        }
    }

    public static DataCenter instance() {
        if (instance == null) {
            throw new IllegalStateException("you must call LiteManager.init(context, dbName) first");
        }
        return instance;
    }

    public <T> long queryCount(Class<T> clazz) {
        return db.queryCount(clazz);
    }

    public <T> int clear(Class<T> clazz) {
        return db.delete(clazz);
    }

    public void save(Object o) {
        db.save(o);
    }

    public <T> List<T> queryAll(Class<T> clazz) {
        List<T> list = db.query(clazz);
        return list == null ? new ArrayList<T>() : list;
    }

    private class CacheContainer<T> extends HashMap<Class<T>, Map<String, T>> {


    }

}
