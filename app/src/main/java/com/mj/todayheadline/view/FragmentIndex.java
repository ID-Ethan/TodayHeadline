package com.mj.todayheadline.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mj.todayheadline.R;
import com.mj.todayheadline.adapter.TextImageAdapter;
import com.mj.todayheadline.domain.Content;
import com.mj.todayheadline.util.DBHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentIndex extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public List<Content> list = new ArrayList<>();

    public FragmentIndex() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment, null);
        if (list != null && list.size() > 0) {
            //如果list中有数据
            ListView listView = view.findViewById(R.id.lv_index);
            TextImageAdapter adapter = new TextImageAdapter(getContext(), list);
            listView.setAdapter(adapter);
        }
        return view;
    }

    /*加载数据库*/
    private void initDatabase() {
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news", null);
        String s = Environment.getExternalStorageDirectory().getPath();
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String writer = cursor.getString(cursor.getColumnIndex("writer"));
            String pinglun = cursor.getString(cursor.getColumnIndex("pinglun"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                    .getPath() +File.separator+ cursor.getString(cursor.getColumnIndex("drawable")));
            Drawable drawable = new BitmapDrawable(bitmap);
            Content content = new Content(title, drawable, writer, pinglun, type);
            list.add(content);
        }
    }
}
