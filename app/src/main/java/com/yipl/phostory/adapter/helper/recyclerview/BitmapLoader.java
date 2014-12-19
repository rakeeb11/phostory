package com.yipl.phostory.adapter.helper.recyclerview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.yipl.phostory.adapter.helper.BaseItem;

import java.lang.ref.WeakReference;

import static com.yipl.phostory.utils.Utils.Image.getScaledDownBitmap;

/**
 * Created by rakeeb on 12/17/14.
 */
public class BitmapLoader extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    Activity activity;

    public BitmapLoader(ImageView imageView, Activity activity) {
        imageViewReference = new WeakReference<>(imageView);
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        return getScaledDownBitmap(activity.getResources(), 6, params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
