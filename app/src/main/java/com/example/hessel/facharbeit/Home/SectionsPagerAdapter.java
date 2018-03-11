package com.example.hessel.facharbeit.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hessel on 18.12.2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";

    private final List<Fragment> mFragmentlistList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentlistList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentlistList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentlistList.add(fragment);
    }
}
