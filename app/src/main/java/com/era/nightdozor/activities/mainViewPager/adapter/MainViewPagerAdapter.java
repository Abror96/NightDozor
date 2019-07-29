package com.era.nightdozor.activities.mainViewPager.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.era.nightdozor.activities.mainViewPager.fragments.HomeFragment;
import com.era.nightdozor.activities.mainViewPager.fragments.PersonalCabFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PersonalCabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
