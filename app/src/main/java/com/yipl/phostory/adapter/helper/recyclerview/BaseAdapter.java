package com.yipl.phostory.adapter.helper.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by rakeeb on 12/17/14.
 */
public class BaseAdapter extends RecyclerView.Adapter<ViewHolder> implements DataFilter.DataFilterInterface {

    public List<Map<String, Object>> data;
    public List<Map<String, Object>> originalData;
    DataFilter filter;

    public BaseAdapter(List<Map<String, Object>> data) {
        this.data = data;
        originalData = data;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public List<Map<String, Object>> getData() {
        return originalData;
    }

    @Override
    public void changeData(List<Map<String, Object>> data, int count) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        if (filter == null) {
            return new DataFilter(this);
        } else {
            return filter;
        }
    }

}
