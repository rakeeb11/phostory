package com.yipl.phostory.adapter.helper.listview;

import com.yipl.phostory.adapter.helper.BaseItem;

/**
 * Created by rakeeb on 12/16/14.
 */
public class NavContent implements BaseItem {

    public static final int VIEW_TYPE = 1;

    public String content;
    public int icon;
    public int position;

    public static NavContent initContent(String content, int icon, int position) {
        NavContent navContent = new NavContent();
        navContent.setContent(content);
        navContent.setIcon(icon);
        navContent.setPosition(position);
        return navContent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int getType() {
        return VIEW_TYPE;
    }
}
