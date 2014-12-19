package com.yipl.phostory.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yipl.phostory.DetailActivity;
import com.yipl.phostory.R;
import com.yipl.phostory.adapter.helper.recyclerview.BaseAdapter;
import com.yipl.phostory.adapter.helper.recyclerview.BitmapLoader;
import com.yipl.phostory.utils.Constants;
import com.yipl.phostory.adapter.helper.recyclerview.ViewHolder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.yipl.phostory.utils.Constants.*;

/**
 * Created by rakeeb on 12/16/14.
 */
public class ImageGridAdapter extends BaseAdapter {

    Activity activity;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public ImageGridAdapter(Activity activity, List<Map<String, Object>> data) {
        super(data);
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_image_grid, viewGroup, false);
        ViewHolder vh = new ViewHolder(v, Constants.IMAGE_GRID);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Map<String, Object> map = data.get(i);
        int imgRes = (int) map.get(IMG);
//        CardView.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        int marginNormal = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, activity.getResources().getDisplayMetrics()));
//        viewHolder.getCardView().setLayoutParams(layoutParams);
        BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.getImageView(), activity);
        bitmapLoader.execute(imgRes);
        setAnimation(viewHolder.getCardView(), i);
        viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DATA, (Serializable) map);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, new Pair<View, String>(viewHolder.getImageView(), IMG));
                activity.startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
