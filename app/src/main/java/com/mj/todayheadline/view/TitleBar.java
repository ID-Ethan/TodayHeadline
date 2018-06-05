package com.mj.todayheadline.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TitleBar extends LinearLayout {
    private Context context;

    public TitleBar(Context context) {
        super(context);
        this.context = context;
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
}
