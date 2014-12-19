package com.yipl.phostory.adapter.helper.recyclerview;

import android.util.Log;
import android.widget.Filter;

import com.yipl.phostory.utils.Constants;
import com.yipl.phostory.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rakeeb on 12/17/14.
 */
public class DataFilter extends Filter {

    DataFilterInterface mInterface;

    public static interface DataFilterInterface {
        List<Map<String, Object>> getData();

        void changeData(List<Map<String, Object>> data, int count);
    }

    public DataFilter(DataFilterInterface dataFilterInterface) {
        mInterface = dataFilterInterface;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        List<Map<String, Object>> newData = new ArrayList<>();
        if (charSequence == null || charSequence.length() == 0) {
            results.values = mInterface.getData();
            results.count = mInterface.getData().size();
            return results;
        } else {
            String filterString = charSequence.toString().toLowerCase(
                    Locale.getDefault());
            String filterableString;

            for (int i = 0; i < mInterface.getData().size(); i++) {
                Map<String, Object> searchQuery = new HashMap<>(
                        mInterface.getData().get(i));
                String caption = searchQuery.get(Constants.CAPTION).toString();
                filterableString = caption;
                if (filterableString.toLowerCase(Locale.getDefault())
                        .contains(filterString)) {
                    searchQuery.put(Constants.CAPTION, Utils
                            .highlight(filterString, filterableString));
                    newData.add(searchQuery);
                }
            }
            results.values = newData;
            results.count = newData.size();
            return results;
        }
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mInterface.changeData((List<Map<String, Object>>) filterResults.values, filterResults.count);
    }
}
