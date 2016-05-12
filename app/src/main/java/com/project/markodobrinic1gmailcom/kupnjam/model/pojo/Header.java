package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

import com.project.markodobrinic1gmailcom.kupnjam.R;

/**
 * Created by marko.dobrinic1@gmail.com on 6.5.2016..
 */
public class Header extends Item {

    private int resourceId;

    public Header(int storeId) {
        this.resourceId = sortResource(storeId);
    }

    private int sortResource(int storeId) {
        switch (storeId) {
            case 1:
                return R.drawable.konzum;
            case 2:
                return R.drawable.billa;
            default:
                return R.drawable.spar;
        }
    }

    public int getResourceId() {
        return resourceId;
    }

    @Override
    public int getType() {
        return HEADER_TYPE;
    }
}
