package io.bxbxbai.zhuanlan.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * database
 */
public final class ZhuanlanDBHelper extends SQLiteOpenHelper {

    /**
     * database params
     */
    public static final String DATABASE_NAME = "zhuanlan.db";
    public static final int DATABASE_VERSION = 1;


    /**
     * table names
     */
    public static final String TABLE_POST = "posts";
    public static final String TABLE_PEOPLE = "people";

    /**
     * table columns
     */
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SLUG = "slug";
    public static final String COLUMN_AVATAR_URL = "avatar_url";
    public static final String COLUMN_FOLLOWER_COUNT = "follower_count";
    public static final String COLUMN_POST_COUNT = "post_count";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_CONTENT = "content";


    private static final String SQL_CREATE_TABLE_POST
            = "CREATE TABLE " + TABLE_POST
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " CHAR(8) UNIQUE, "
            + COLUMN_CONTENT + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_PEOPLE
            = "CREATE TABLE " + TABLE_PEOPLE
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_SLUG + " TEXT NOT NULL, "
            + COLUMN_AVATAR_URL + " TEXT NOT NULL, "
            + COLUMN_FOLLOWER_COUNT + " INTEGER NOT NULL, "
            + COLUMN_POST_COUNT + " INTEGER NOT NULL, "
            + COLUMN_DESC + " TEXT NOT NULL, "
            + COLUMN_CONTENT + " TEXT NOT NULL);";

    public ZhuanlanDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_POST);
        db.execSQL(SQL_CREATE_TABLE_PEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        onCreate(db);
    }
}
