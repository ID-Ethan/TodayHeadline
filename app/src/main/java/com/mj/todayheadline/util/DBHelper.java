package com.mj.todayheadline.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DB_NAME = "news.db";
    private static final String TB_USER = "user";
    private static final String TB_NEWS = "news";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_USER = "create table user (_id integer primary key autoincrement,username " +
                "text,password text)";
        db.execSQL(CREATE_USER);
//        String CREATE_NEWS = "create table news (_id integer primary key autoincrement,username
// text,password text,)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long register(ContentValues contentValues) {
        long result = 0l;   //定义返回值
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();  //开始事务
        try {
            result = sqLiteDatabase.insert("user", null, contentValues);
            sqLiteDatabase.setTransactionSuccessful();  //设置成功标志
        } finally {
            sqLiteDatabase.endTransaction(); //关闭事务
        }
        return result;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "select * from user where username=? and password=?";
        String[] user = {username, password};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, user);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
