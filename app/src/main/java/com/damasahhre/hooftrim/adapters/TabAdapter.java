package com.damasahhre.hooftrim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.damasahhre.hooftrim.R;

import java.util.ArrayList;
import java.util.List;


/**
 * مدیریت کننده اطلاعات در لیست
 * پایین صفحه اصلی
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public View getTabView(int position) {
        View view = inflater.inflate(R.layout.tab_layout, null);
        view.setTag(mFragmentTitleList.get(position));
        TextView name = view.findViewById(R.id.item_name);
        name.setText(mFragmentTitleList.get(position));
        return view;
    }

    public View getSelectedTabView(int position) {
        View view = inflater.inflate(R.layout.tab_layout_selected, null);
        TextView name = view.findViewById(R.id.item_name);
        name.setText(mFragmentTitleList.get(position));
        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}