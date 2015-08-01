package io.bxbxbai.zhuanlan.core;


import com.jakewharton.disklrucache.DiskLruCache;
import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SimpleDiskCache {

    private static final int VALUE_IDX = 0;
    private static final int METADATA_IDX = 1;
    private static final List<File> usedDirs = new ArrayList<File>();

    private final DiskLruCache diskLruCache;

    private SimpleDiskCache(File dir, int appVersion, long maxSize) throws IOException {
        diskLruCache = DiskLruCache.open(dir, appVersion, 2, maxSize);
    }

    static synchronized SimpleDiskCache open(File dir, int appVersion, long maxSize) throws IOException {
        if (usedDirs.contains(dir)) {
            throw new IllegalStateException("Cache dir " + dir.getAbsolutePath() + " was used " +
                    "before.");
        }

        usedDirs.add(dir);
        return new SimpleDiskCache(dir, appVersion, maxSize);
    }

    StringEntry getString(String key) throws IOException {
        DiskLruCache.Snapshot snapshot = diskLruCache.get(toInternalKey(key));
        if (snapshot == null)
            return null;

        try {
            return new StringEntry(snapshot.getString(VALUE_IDX));
        } finally {
            snapshot.close();
        }
    }

    boolean contains(String key) throws IOException {
        DiskLruCache.Snapshot snapshot = diskLruCache.get(toInternalKey(key));
        if (snapshot == null)
            return false;

        snapshot.close();
        return true;
    }

    private OutputStream openStream(String key, Map<String, ? extends Serializable> metadata) throws
            IOException {
        DiskLruCache.Editor editor = diskLruCache.edit(toInternalKey(key));
        try {
            writeMetadata(metadata, editor);
            BufferedOutputStream bos = new BufferedOutputStream(editor.newOutputStream(VALUE_IDX));
            return new CacheOutputStream(bos, editor);
        } catch (IOException e) {
            editor.abort();
            throw e;
        }
    }

    void put(String key, String value) throws IOException {
        put(key, value, new HashMap<String, Serializable>());
    }

    void delete(String key) throws IOException {
        put(key, "", new HashMap<String, Serializable>());
    }

    private void put(String key, String value, Map<String, ? extends Serializable> annotations)
            throws IOException {
        OutputStream cos = null;
        try {
            cos = openStream(key, annotations);
            cos.write(value.getBytes());
        } finally {
            if (cos != null)
                cos.close();
        }

    }

    private void writeMetadata(Map<String, ? extends Serializable> metadata,
                               DiskLruCache.Editor editor) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(editor.newOutputStream
                    (METADATA_IDX)));
            oos.writeObject(metadata);
        } finally {
            IOUtils.closeQuietly(oos);
        }
    }

    private static String toInternalKey(String key) {
        return md5(key);
    }

    private static String md5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes("UTF-8"));
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    private class CacheOutputStream extends FilterOutputStream {

        private final DiskLruCache.Editor editor;
        private boolean failed = false;

        private CacheOutputStream(OutputStream os, DiskLruCache.Editor editor) {
            super(os);
            this.editor = editor;
        }

        @Override
        public void close() throws IOException {
            IOException closeException = null;
            try {
                super.close();
            } catch (IOException e) {
                closeException = e;
            }

            if (failed) {
                editor.abort();
            } else {
                editor.commit();
            }

            if (closeException != null)
                throw closeException;
        }

        @Override
        public void flush() throws IOException {
            try {
                super.flush();
            } catch (IOException e) {
                failed = true;
                throw e;
            }
        }

        @Override
        public void write(int oneByte) throws IOException {
            try {
                super.write(oneByte);
            } catch (IOException e) {
                failed = true;
                throw e;
            }
        }

        @Override
        public void write(byte[] buffer) throws IOException {
            try {
                super.write(buffer);
            } catch (IOException e) {
                failed = true;
                throw e;
            }
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            try {
                super.write(buffer, offset, length);
            } catch (IOException e) {
                failed = true;
                throw e;
            }
        }
    }

    static class StringEntry {
        private final String string;

        StringEntry(String string) {
            this.string = string;
        }

        String getString() {
            return string;
        }

    }
}

