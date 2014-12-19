package com.yipl.phostory.fragments;

import com.yipl.phostory.R;
import com.yipl.phostory.views.helper.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yipl.phostory.utils.Constants.*;

/**
 * Created by rakeeb on 12/16/14.
 */
public class Home extends BaseRecyclerFragment {

    @Override
    public String emptyMessage() {
        return "No data found ...";
    }

    @Override
    public int emptyImage() {
        return R.drawable.ic_nav_home;
    }

    @Override
    public List<Map<String, Object>> setData() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put(IMG, images[i]);
            map.put(CAPTION, captions[i]);
            map.put(TIME, time[i]);
            data.add(map);
        }
        return data;
    }

    @Override
    public int setMenu() {
        return R.menu.recycler_menu;
    }

    @Override
    public boolean hasMenu() {
        return true;
    }

    @Override
    public String setToolbarTitle() {
        return "Home";
    }
}
