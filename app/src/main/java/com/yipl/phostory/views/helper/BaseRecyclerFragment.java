package com.yipl.phostory.views.helper;

import android.app.Fragment;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yipl.phostory.R;
import com.yipl.phostory.adapter.ImageGridAdapter;
import com.yipl.phostory.adapter.ImageListAdapter;
import com.yipl.phostory.adapter.helper.recyclerview.DividerDecoration;
import com.yipl.phostory.service.NotificationJobService;
import com.yipl.phostory.utils.Constants;
import com.yipl.phostory.views.CustomRecycler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.yipl.phostory.utils.Constants.IMAGE_GRID;
import static com.yipl.phostory.utils.Constants.IMAGE_LIST;
import static com.yipl.phostory.utils.Utils.Views.setEmptyMessage;

/**
 * Created by rakeeb on 12/16/14.
 */
public abstract class BaseRecyclerFragment extends android.support.v4.app.Fragment implements SearchView.OnQueryTextListener {

    @InjectView(R.id.emptyView)
    View emptyView;

    @InjectView(R.id.recyclerView)
    public CustomRecycler customRecycler;

    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    public int currentSelection = IMAGE_GRID;

    List<Map<String, Object>> data;
    DividerDecoration decoration;

    ImageListAdapter imageListAdapter;
    ImageGridAdapter imageGridAdapter;

    public int[] images = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
            R.drawable.img11,
            R.drawable.img12,
            R.drawable.img13,
            R.drawable.img14,
            R.drawable.img15
    };

    public String[] captions;
    public String[] time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasMenu());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        setEmptyMessage(emptyView, emptyImage(), emptyMessage());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // init default data
        captions = getResources().getStringArray(R.array.img_caption);
        time = getResources().getStringArray(R.array.img_time);

        data = setData();
        imageGridAdapter = new ImageGridAdapter(getActivity(), data);
        imageListAdapter = new ImageListAdapter(getActivity(), data);
        decoration = new DividerDecoration(getActivity(), null);
        setRecyclerView(IMAGE_GRID);
        if(setToolbarTitle() != null) {
            ((BaseActivity) getActivity()).toolbar.setTitle(setToolbarTitle());
        }
    }

    public Set<String> getFavorites() {
        return ((BaseActivity) getActivity()).getFavorites();
    }


    public abstract String emptyMessage();

    public abstract int emptyImage();

    public abstract List<Map<String, Object>> setData();

    public abstract int setMenu();

    public abstract boolean hasMenu();

    public abstract String setToolbarTitle();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(setMenu(), menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            switch (item.getItemId()) {
                case R.id.imageLayout:
                    if (currentSelection == IMAGE_GRID) {
                        item.setTitle("List");
                        item.setIcon(R.drawable.ic_listview);
                    } else {
                        item.setTitle("Grid");
                        item.setIcon(R.drawable.ic_gridview);
                    }
                    break;
                case R.id.search:
                    SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                    searchView.setOnQueryTextListener(this);
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imageLayout:
                if (currentSelection == IMAGE_GRID) {
                    setRecyclerView(IMAGE_LIST);
                    currentSelection = IMAGE_LIST;
                    getActivity().invalidateOptionsMenu();
                } else {
                    setRecyclerView(IMAGE_GRID);
                    currentSelection = IMAGE_GRID;
                    getActivity().invalidateOptionsMenu();
                }
                break;
            case R.id.job:
                JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putString("message", "My personal message, sent from persistable bundle");
                JobInfo jobInfo = new JobInfo.Builder(Constants.JOB_ID, new ComponentName(getActivity(), NotificationJobService.class))
                        .setExtras(persistableBundle)
                        .setRequiresCharging(true)
                        .setOverrideDeadline(10*1000)
                        .build();
                jobScheduler.schedule(jobInfo);
                break;
        }

        return true;
    }

    public void setRecyclerView(int currentSelection) {
        if (currentSelection == IMAGE_GRID) {
            customRecycler.setLayoutManager(gridLayoutManager);
            customRecycler.removeItemDecoration(decoration);
            customRecycler.setAdapter(imageGridAdapter);
        } else {
            customRecycler.setLayoutManager(linearLayoutManager);
            customRecycler.addItemDecoration(decoration);
            customRecycler.setAdapter(imageListAdapter);
        }
        customRecycler.setEmptyView(emptyView);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (currentSelection == IMAGE_GRID) {
            imageGridAdapter.getFilter().filter(s);
        } else {
            imageListAdapter.getFilter().filter(s);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
}
