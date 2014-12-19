package com.yipl.phostory.fragments;


import com.yipl.phostory.R;
import com.yipl.phostory.views.helper.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yipl.phostory.utils.Constants.CAPTION;
import static com.yipl.phostory.utils.Constants.IMG;
import static com.yipl.phostory.utils.Constants.TIME;

/**
 * Created by rakeeb on 12/18/14.
 */
public class TimeLine extends BaseRecyclerFragment {


    @Override
    public String emptyMessage() {
        return "No photo found ...";
    }

    @Override
    public int emptyImage() {
        return R.drawable.ic_nav_recents;
    }

    @Override
    public List<Map<String, Object>> setData() {
        List<Map<String, Object>> data = new ArrayList<>();
        String timeline = getArguments().getString("timeline");
        if (!timeline.isEmpty()) {
            List<String> timeList = Arrays.asList(time);
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < timeList.size(); i++) {
                if (timeList.get(i).equalsIgnoreCase(timeline))
                    positions.add(i);
            }
            for (int i : positions) {
                Map<String, Object> map = new HashMap<>();
                map.put(IMG, images[i]);
                map.put(CAPTION, captions[i]);
                map.put(TIME, time[i]);
                data.add(map);
            }
        }
        return data;
    }

    @Override
    public int setMenu() {
        return R.menu.recycler_menu;
    }

    @Override
    public boolean hasMenu() {
        return false;
    }

    @Override
    public String setToolbarTitle() {
        return null;
    }
}
