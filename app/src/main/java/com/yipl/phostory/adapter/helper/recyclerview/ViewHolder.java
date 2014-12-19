package com.yipl.phostory.adapter.helper.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yipl.phostory.R;
import com.yipl.phostory.adapter.helper.BaseItem;
import com.yipl.phostory.utils.Constants;

/**
 * Created by rakeeb on 12/17/14.
 */
public class ViewHolder extends RecyclerView.ViewHolder implements BaseItem {

    int type;
    ImageView imageView;
    TextView tvCaption;
    TextView tvTime;
    CardView cardView;
    FrameLayout listContainer;

    public ViewHolder(View v, int type) {
        super(v);
        this.type = type;
        imageView = (ImageView) v.findViewById(R.id.image);
        if (type == Constants.IMAGE_LIST) {
            tvCaption = (TextView) v.findViewById(R.id.caption);
            tvTime = (TextView) v.findViewById(R.id.time);
            listContainer = (FrameLayout) v.findViewById(R.id.listContainer);
        } else {
            cardView = (CardView) v.findViewById(R.id.cardView);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTvCaption() {
        return tvCaption;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public CardView getCardView() {
        return cardView;
    }

    public FrameLayout getListContainer() {
        return listContainer;
    }

    @Override
    public int getType() {
        return type;
    }
}
