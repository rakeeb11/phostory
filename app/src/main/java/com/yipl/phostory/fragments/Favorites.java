package com.yipl.phostory.fragments;

import com.yipl.phostory.R;
import com.yipl.phostory.views.helper.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.yipl.phostory.utils.Constants.CAPTION;
import static com.yipl.phostory.utils.Constants.IMG;
import static com.yipl.phostory.utils.Constants.TIME;

/**
 * Created by rakeeb on 12/16/14.
 */
public class Favorites extends BaseRecyclerFragment {


    @Override
    public String emptyMessage() {
        return "No favorites ...";
    }

    @Override
    public int emptyImage() {
        return R.drawable.ic_nav_fav;
    }

    @Override
    public List<Map<String, Object>> setData() {
        List<Map<String, Object>> data = new ArrayList<>();
        Set<String> favorites = getFavorites();
        if (!favorites.isEmpty()) {
            List<Integer> positions = new ArrayList<>();
            List<String> captionList = Arrays.asList(captions);
            for (String caption : favorites) {
                int position = captionList.indexOf(caption);
                positions.add(position);
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
        return true;
    }

    @Override
    public String setToolbarTitle() {
        return "Favorites";
    }
}
