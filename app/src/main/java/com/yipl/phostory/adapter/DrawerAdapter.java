package com.yipl.phostory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yipl.phostory.R;
import com.yipl.phostory.adapter.helper.BaseItem;
import com.yipl.phostory.adapter.helper.listview.NavContent;
import com.yipl.phostory.adapter.helper.listview.NavHeader;

import java.util.List;

/**
 * Created by rakeeb on 12/16/14.
 */
public class DrawerAdapter extends ArrayAdapter<BaseItem> {

    LayoutInflater inflater;

    public DrawerAdapter(Context context, List<BaseItem> navItems) {
        super(context, 0, navItems);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItem(position).getType() == NavContent.VIEW_TYPE) {
            return getContentView((NavContent) getItem(position), convertView, parent);
        } else {
            return getHeaderView((NavHeader) getItem(position), convertView, parent);
        }
    }

    public View getHeaderView(NavHeader navHeader, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.view_nav_header, parent, false);
        ((TextView) convertView.findViewById(R.id.headerText)).setText(navHeader.getHeader());
        return convertView;
    }

    public View getContentView(NavContent navContent, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.view_nav_content, parent, false);
        TextView navigationOption = ((TextView) convertView.findViewById(R.id.navigationOption));
        navigationOption.setText(navContent.getContent());
        navigationOption.setCompoundDrawablesRelativeWithIntrinsicBounds(navContent.getIcon(), 0, 0, 0);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public boolean isEnabled(int position) {
        if (getItemViewType(position) == NavHeader.VIEW_TYPE) {
            /* if view type is header, disable the view */
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getViewTypeCount() {
        /* there are two types of views here,
         * a header view and a content view
         */
        return 2;
    }
}
