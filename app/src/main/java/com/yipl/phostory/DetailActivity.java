package com.yipl.phostory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yipl.phostory.views.helper.BaseActivity;
import com.yipl.phostory.utils.Constants;
import com.yipl.phostory.utils.Utils;

import static com.yipl.phostory.utils.Utils.Views.getDp;


import java.util.Map;

import butterknife.InjectView;

/**
 * Created by rakeeb on 12/17/14.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.mainImage)
    ImageView imageView;
    @InjectView(R.id.likableText)
    TextView titleText;
    @InjectView(R.id.textContent)
    TextView textView;
    @InjectView(R.id.love)
    ImageButton loveButton;
    @InjectView(R.id.mapInfo)
    ImageButton mapButton;
    @InjectView(R.id.textContainer)
    LinearLayout textContainer;

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    int vibrantColor;
    int colorDark;

    String caption;
    Bitmap bitmap;

    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.ic_ab_up_white);
        ViewCompat.setTransitionName(imageView, Constants.IMG);
        Map<String, Object> map = (Map<String, Object>) getIntent().getSerializableExtra(Constants.DATA);
        int imgRes = (int) map.get(Constants.IMG);
        caption = map.get(Constants.CAPTION).toString();
        titleText.setText(caption);
        bitmap = Utils.Image.getScaledDownBitmap(getResources(), 2, imgRes);
        imageView.setImageBitmap(bitmap);

        initPalette();
        setupMap();

        // check if image is favorite or not
        isFavorite = checkFavorite(caption);
        if (isFavorite)
            loveButton.setSelected(true);

        loveButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
    }

    public void initPalette() {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    titleText.setTextColor(vibrantSwatch.getTitleTextColor());
                    textView.setTextColor(vibrantSwatch.getBodyTextColor());
                    textContainer.setBackgroundColor(vibrantSwatch.getRgb());
                } else {
                    titleText.setTextColor(Color.WHITE);
                    textView.setTextColor(Color.WHITE);
                }
                vibrantColor = palette.getVibrantColor(Color.parseColor("#AEAEAE"));
                colorDark = palette.getDarkVibrantColor(Color.parseColor("#797979"));
                getWindow().setStatusBarColor(colorDark);
                setTaskDescription(vibrantColor);
            }
        });
    }

    public void setTaskDescription(int color) {
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(caption, bitmap, color);
        setTaskDescription(td);
    }

    private void setupMap() {
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        MapFragment mapFragment = MapFragment.newInstance(options);

        getFragmentManager().beginTransaction().replace(R.id.map_container, mapFragment).commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setMapToolbarEnabled(false);
                float zoom = 15.0f;
                LatLng position = new LatLng(Constants.LAT, Constants.LNG);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
                googleMap.addMarker(new MarkerOptions().position(position));
            }
        });
    }

    private void toggleMapView(View view) {
        final View infoContainer = findViewById(R.id.map_container);

        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        float radius = Math.max(infoContainer.getWidth(), infoContainer.getHeight()) * 2.0f;

        Animator reveal;
        if (infoContainer.getVisibility() == View.INVISIBLE) {
            infoContainer.setVisibility(View.VISIBLE);
            ((ImageButton) view).setImageResource(R.drawable.ic_map_dark);
            reveal = ViewAnimationUtils.createCircularReveal(
                    infoContainer, cx, cy, 0, radius);
            reveal.setInterpolator(new AccelerateInterpolator(2.0f));
        } else {
            ((ImageButton) view).setImageResource(R.drawable.ic_map_white);
            reveal = ViewAnimationUtils.createCircularReveal(
                    infoContainer, cx, cy, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    infoContainer.setVisibility(View.INVISIBLE);
                }
            });
            reveal.setInterpolator(new DecelerateInterpolator(2.0f));
        }
        reveal.setDuration(600);
        reveal.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.love:
                if (loveButton.isSelected()) {
                    loveButton.setSelected(false);
                    removeFavorite(caption);
                } else {
                    loveButton.setSelected(true);
                    setFavorite(caption);
                }
                break;
            case R.id.mapInfo:
                toggleMapView(view);
                break;
        }
    }
}
