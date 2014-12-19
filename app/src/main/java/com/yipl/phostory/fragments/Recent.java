package com.yipl.phostory.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yipl.phostory.R;
import com.yipl.phostory.adapter.TimeLineAdapter;
import com.yipl.phostory.views.SlidingTabLayout;
import com.yipl.phostory.views.helper.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rakeeb on 12/16/14.
 */
public class Recent extends Fragment {

    @InjectView(R.id.slidingTab)
    SlidingTabLayout slidingTab;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    String[] title = {"1 week ago", "2 weeks ago", "1 month ago", "2 months ago"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recents, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TimeLineAdapter adapter = new TimeLineAdapter(getActivity().getSupportFragmentManager(), title);
        viewPager.setAdapter(adapter);
        slidingTab.setViewPager(viewPager);
        ((BaseActivity) getActivity()).toolbar.setTitle("Timeline");
    }



}
