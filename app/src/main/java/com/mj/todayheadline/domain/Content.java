package com.mj.todayheadline.domain;

import android.graphics.drawable.Drawable;

public class Content {
    private String title;
    private Drawable view;
    private String writer;
    private String pinglun;
    private int type;

    public Content(String title, Drawable view, String writer, String pinglun, int type) {
        this.title = title;
        this.view = view;
        this.writer = writer;
        this.pinglun = pinglun;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getView() {
        return view;
    }

    public void setView(Drawable view) {
        this.view = view;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPinglun() {
        return pinglun;
    }

    public void setPinglun(String pinglun) {
        this.pinglun = pinglun;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
