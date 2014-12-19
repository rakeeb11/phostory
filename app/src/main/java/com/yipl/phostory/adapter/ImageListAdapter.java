package com.yipl.phostory.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.Spannable;
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

import static com.yipl.phostory.utils.Constants.DATA;
import static com.yipl.phostory.utils.Constants.IMG;

/**
 * Created by rakeeb on 12/17/14.
 */
public class ImageListAdapter extends BaseAdapter {

    Activity activity;
    private int lastPosition = -1;

    public ImageListAdapter(Activity activity, List<Map<String, Object>> data) {
        super(data);
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_image_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v, Constants.IMAGE_LIST);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Map<String, Object> map = data.get(i);
        Object caption = map.get(Constants.CAPTION);
        String time = map.get(Constants.TIME).toString();
        if (caption instanceof Spannable) {
            viewHolder.getTvCaption().setText((Spannable) caption);
        } else if (caption instanceof String) {
            viewHolder.getTvCaption().setText(caption.toString());
        }
        viewHolder.getTvTime().setText(time);
        int imgRes = (int) map.get(Constants.IMG);
        BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.getImageView(), activity);
        bitmapLoader.execute(imgRes);
        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.getListContainer(), i);
        viewHolder.getListContainer().setOnClickListener(new View.OnClickListener() {
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
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}