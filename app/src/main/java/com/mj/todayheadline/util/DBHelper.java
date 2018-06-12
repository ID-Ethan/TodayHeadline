package com.mj.todayheadline.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.mj.todayheadline.domain.Content;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DB_NAME = "news.db";
//    private static final String TB_USER = "user";
//    private static final String TB_NEWS = "news";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_USER = "create table user (_id integer primary key autoincrement,username " +
                "text,password text)";
        db.execSQL(CREATE_USER);
        String CREATE_NEWS = "create table news (_id integer primary key autoincrement ,title " +
                "text,writer text,pinglun text,type integer,drawable text)";
        db.execSQL(CREATE_NEWS);
        //向news中插入数据
        addNews();
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

    /*插入新闻数据*/
    private void addNews() {
        db.execSQL("insert into news (title,writer,pinglun,type,drawable) values " +
                "('特朗普称金正恩接受访问白宫邀请 暂不解除对朝制裁','腾讯网自媒体','2018个评论',0,'01.jpg')");
        db.execSQL("insert into news (title,writer,pinglun,type,drawable) values " +
                "('国企工资改革2019年将全面实施 配套政策有望年内落地','腾讯网自媒体','2018个评论',0,'02.jpg')");
        db.execSQL("insert into news (title,writer,pinglun,type,drawable) values " +
                "('河北人大常委会原副主任杨崇勇一审被指受贿超2.06亿','人民日报','2018个评论',0,'03.jpg')");
        db.execSQL("insert into news (title,writer,pinglun,type,drawable) values " +
                "('习近平主席的上合时间','央视网','2018个评论',0,'04.jpg')");
        db.execSQL("insert into news (title,writer,pinglun,type) values " +
                "('买房送户口？起底天津个别楼盘买房落户骗局','法制晚报','2018个评论',1)");
        db.execSQL("insert into news (title,writer,pinglun,type) values " +
                "('太原人才落户新政：本科不超45岁直接落户','山西晚报','2018个评论',1)");
        db.execSQL("insert into news (title,writer,pinglun,type) values " +
                "('国际足联：2022年卡塔尔世界杯扩军请求已被撤回','新华社','2018个评论',1)");
        db.execSQL("insert into news (title,writer,pinglun,type) values " +
                "('内蒙古现巨型“沙墙”遮天蔽日 白天瞬间黑如夜晚','梨视频','2018个评论',1)");

    }
}
