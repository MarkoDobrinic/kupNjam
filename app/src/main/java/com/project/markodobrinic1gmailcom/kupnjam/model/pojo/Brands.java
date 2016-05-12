package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

import com.project.markodobrinic1gmailcom.kupnjam.R;

/**
 * Created by marko.dobrinic1@gmail.com on 6.5.2016..
 */
public enum Brands {

    konzum(R.drawable.konzum, 0), billa(R.drawable.billa, 1), spar(R.drawable.spar, 2);

    private final int resId;
    private int priority;

    Brands(int resId, int priority)  {
        this.resId = resId;
        this.priority = priority;
    }

    public int getResId() {
        return resId;
    }

    public int getPriority() {
        return priority;
    }
}
