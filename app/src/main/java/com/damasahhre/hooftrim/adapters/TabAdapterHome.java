package com.damasahhre.hooftrim.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.damasahhre.hooftrim.R;
import com.damasahhre.hooftrim.activities.tabs.AddLivestockActivity;
import com.damasahhre.hooftrim.activities.tabs.BlankFragment;
import com.damasahhre.hooftrim.activities.tabs.HomeActivity;
import com.damasahhre.hooftrim.activities.tabs.MarkedActivity;
import com.damasahhre.hooftrim.activities.tabs.ReportsActivity;
import com.damasahhre.hooftrim.activities.tabs.SearchActivity;
import com.google.android.material.tabs.TabLayout;


/**
 * مدیریت کننده اطلاعات در لیست
 * پایین صفحه اصلی
 */
public class TabAdapterHome extends FragmentStateAdapter {

    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private Context context;

    public TabAdapterHome(@NonNull FragmentActivity fragmentActivity, TabLayout tabLayout,
                          ViewPager2 pager) {
        super(fragmentActivity);
        this.context = fragmentActivity;
        this.tabLayout = tabLayout;
        inflater = LayoutInflater.from(context);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(getSelectedTabView(tab.getPosition()));
                if (tab.getPosition() == 2) {
                    Intent intent = new Intent(context, AddLivestockActivity.class);
                    context.startActivity(intent);
                }else{
                    pager.setCurrentItem(tab.getPosition(),true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(getTabView(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public View getTabView(int position) {
        if (position == 2) {
            return inflater.inflate(R.layout.home_tab_layout_center, null);
        }
        View view = inflater.inflate(R.layout.home_tab_layout, null);

        String title = null;
        Drawable drawable = null;
        switch (position) {
            case 0: {
                title = context.getResources().getString(R.string.marked);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_bookmark);
                break;
            }
            case 1: {
                title = context.getResources().getString(R.string.report);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_report);
                break;
            }
            case 3: {
                title = context.getResources().getString(R.string.search);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_search);
                break;
            }
            case 4: {
                title = context.getResources().getString(R.string.home);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_home);
            }
        }

        view.setTag(title);
        ImageView image = view.findViewById(R.id.item_image);
        image.setImageDrawable(drawable);
        image.setColorFilter(R.color.tab_home);
        return view;
    }

    public View getSelectedTabView(int position) {
        if (position == 2) {
            return inflater.inflate(R.layout.home_tab_layout_center, null);
        }
        View view = inflater.inflate(R.layout.home_tab_layout_selected, null);

        String title = null;
        Drawable drawable = null;
        switch (position) {
            case 0: {
                title = context.getResources().getString(R.string.marked);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_bookmark_fill);
                break;
            }
            case 1: {
                title = context.getResources().getString(R.string.report);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_report_fill);
                break;
            }
            case 3: {
                title = context.getResources().getString(R.string.search);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_search_fill);
                break;
            }
            case 4: {
                title = context.getResources().getString(R.string.home);
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_home_fill);
            }
        }

        TextView name = view.findViewById(R.id.item_name);
        name.setText(title);
        ImageView image = view.findViewById(R.id.item_image);
        image.setImageDrawable(drawable);
        image.setColorFilter(R.color.selected_tab_home);
        return view;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: {
                return new MarkedActivity();
            }
            case 1: {
                return new ReportsActivity();
            }
            case 2:{
                return new BlankFragment();
            }
            case 3: {
                return new SearchActivity();
            }
            case 4: {
                return new HomeActivity();
            }
            default:
                return new HomeActivity();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}