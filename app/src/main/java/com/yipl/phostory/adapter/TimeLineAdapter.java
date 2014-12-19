package com.yipl.phostory.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.yipl.phostory.fragments.TimeLine;

/**
 * Created by rakeeb on 12/18/14.
 */
public class TimeLineAdapter extends FragmentStatePagerAdapter {

    String[] title;

    // saving fragments
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public TimeLineAdapter(android.support.v4.app.FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new TimeLine();
        Bundle bundle = new Bundle();
        bundle.putString("timeline", title[i]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}
