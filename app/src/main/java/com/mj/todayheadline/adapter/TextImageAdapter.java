package com.mj.todayheadline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mj.todayheadline.R;
import com.mj.todayheadline.domain.Content;

import java.util.ArrayList;
import java.util.List;

public class TextImageAdapter extends BaseAdapter {
    private Context context;
    private List<Content> list = new ArrayList<>();

    public TextImageAdapter(Context context, List<Content> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*匹配数据+控件渲染*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Content content = list.get(position);
        int type = getItemViewType(position);
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        if (convertView == null) {
            switch (type) {
                case 0:
                    viewHolder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder1,
                            null);
                    viewHolder1.title = convertView.findViewById(R.id.title);
                    viewHolder1.writer = convertView.findViewById(R.id.writer);
                    viewHolder1.pinglun = convertView.findViewById(R.id.pinglun);
                    viewHolder1.img = convertView.findViewById(R.id.img);
                    viewHolder1.title.setText(content.getTitle());
                    viewHolder1.writer.setText(content.getWriter());
                    viewHolder1.pinglun.setText(content.getPinglun());
                    viewHolder1.img.setImageDrawable(content.getView());
                    break;
                case 1:
                    viewHolder2 = new ViewHolder2();
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder2,
                            null);
                    viewHolder2.title = convertView.findViewById(R.id.title);
                    viewHolder2.writer = convertView.findViewById(R.id.writer);
                    viewHolder2.pinglun = convertView.findViewById(R.id.pinglun);
                    viewHolder2.title.setText(content.getTitle());
                    viewHolder2.writer.setText(content.getWriter());
                    viewHolder2.pinglun.setText(content.getPinglun());
                    break;
            }
        }
        return convertView;
    }

    /*几种显示类型：这里有两种，带图片的和不带图片的*/
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        Content content = list.get(position);
        return content.getType();
    }

    /*带图片的viewHolder*/
    class ViewHolder1 {
        public TextView title;
        public TextView writer;
        public TextView pinglun;
        public ImageView img;
    }

    /*不带图片的viewHolder*/
    class ViewHolder2 {
        public TextView title;
        public TextView writer;
        public TextView pinglun;
    }
}
