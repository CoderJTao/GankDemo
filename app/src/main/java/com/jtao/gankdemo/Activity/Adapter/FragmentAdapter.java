package com.jtao.gankdemo.Activity.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> tabList;

    public FragmentAdapter(FragmentManager fm, List<Fragment>fragments, List<String>tabList) {
        super(fm);
        this.fragmentList = fragments;
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}
