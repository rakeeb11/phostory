package com.yipl.phostory.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yipl.phostory.R;
import com.yipl.phostory.adapter.DrawerAdapter;
import com.yipl.phostory.adapter.helper.BaseItem;
import com.yipl.phostory.adapter.helper.listview.NavContent;
import com.yipl.phostory.adapter.helper.listview.NavHeader;
import com.yipl.phostory.fragments.Favorites;
import com.yipl.phostory.fragments.Home;
import com.yipl.phostory.fragments.Recent;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawer extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 1;

    public NavigationDrawer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, parent);
            }
        });

        List<BaseItem> navigationItems = new ArrayList<>();
        navigationItems.add(NavHeader.initHeader("Phostory"));
        navigationItems.add(NavContent.initContent("Home", R.drawable.ic_nav_home, 1));
        navigationItems.add(NavContent.initContent("Favorites", R.drawable.ic_nav_fav, 2));
        navigationItems.add(NavContent.initContent("Timeline", R.drawable.ic_nav_recents, 3));

        DrawerAdapter drawerAdapter = new DrawerAdapter(getActivity(), navigationItems);
        mDrawerListView.setAdapter(drawerAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void selectItem(int position, AdapterView item) {
        mCurrentSelectedPosition = position;
        if (item != null) {
            BaseItem baseItem = (BaseItem) item.getItemAtPosition(position);
            if (baseItem instanceof NavContent) {
                NavContent navContent = (NavContent) baseItem;
                android.support.v4.app.Fragment fragment = null;
                switch (navContent.getPosition()) {
                    case 1:
                        // home
                        fragment = new Home();
                        break;
                    case 2:
                        fragment = new Favorites();
                        // favorites
                        break;
                    case 3:
                        fragment = new Recent();
                        // recent
                        break;
                }

                if (mDrawerListView != null) {
                    mDrawerListView.setItemChecked(position, true);
                }
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(mFragmentContainerView);
                }
                if (mCallbacks != null) {
                    mCallbacks.onNavigationDrawerItemSelected(position, fragment);
                }
            }
        }
        // initially
        else {
            if (mDrawerListView != null) {
                mDrawerListView.setItemChecked(position, true);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {
                mCallbacks.onNavigationDrawerItemSelected(position, new Home());
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position, android.support.v4.app.Fragment fragment);
    }
}
