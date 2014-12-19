package com.yipl.phostory.adapter.helper.listview;

import com.yipl.phostory.adapter.helper.BaseItem;

/**
 * Created by rakeeb on 12/16/14.
 */
public class NavHeader implements BaseItem {

    public static final int VIEW_TYPE = 0;
    String header;

    public static NavHeader initHeader(String header) {
        NavHeader navHeader = new NavHeader();
        navHeader.setHeader(header);
        return navHeader;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public int getType() {
        return VIEW_TYPE;
    }
}
