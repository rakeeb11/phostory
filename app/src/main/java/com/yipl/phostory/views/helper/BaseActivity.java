package com.yipl.phostory.views.helper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yipl.phostory.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by rakeeb on 12/16/14.
 */
public abstract class BaseActivity extends ActionBarActivity {


    @Optional
    @InjectView(R.id.toolbar)
    public Toolbar toolbar;

    public Set<String> favorite;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("phoStory-prefs", MODE_PRIVATE);
        editor = preferences.edit();
        favorite = preferences.getStringSet("favorite", new HashSet<String>());
    }

    public void setFavorite(String caption) {
        favorite.add(caption);
        changeFavorite(favorite);
    }

    public void removeFavorite(String caption) {
        favorite.remove(caption);
        changeFavorite(favorite);
    }

    public void changeFavorite(Set<String> favorite) {
        editor.putStringSet("favorite", favorite);
        editor.commit();
    }

    public Set<String> getFavorites() {
        return favorite;
    }


    public boolean checkFavorite(String content) {
        if (favorite.isEmpty()) {
            return false;
        } else {
            if (favorite.contains(content))
                return true;
            else
                return false;
        }
    }

    public abstract int getLayout();

}
