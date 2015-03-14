package io.bxbxbai.zhuanlan.utils;

/**
 *
 * @author bxbxbai
 */
public enum  CacheKey {

    SETTING("setting", String.class);

    String mKey;
    Class mClazz;

    CacheKey(String key, Class clazz) {
        mKey = key;
        mClazz = clazz;
    }

    public Class getClazz() {
        return mClazz;
    }

    public String getKey() {
        return mKey;
    }
}
