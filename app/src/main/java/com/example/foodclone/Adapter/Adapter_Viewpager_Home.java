package com.example.foodclone.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.foodclone.View.Fragment.Fragment_Food;
import com.example.foodclone.View.Fragment.Fragment_Place;

public class Adapter_Viewpager_Home extends FragmentStatePagerAdapter {
    Fragment_Food fragmentFood;
    Fragment_Place fragmentPlace;
    public Adapter_Viewpager_Home(FragmentManager fm) {
        super(fm);
        fragmentFood = new Fragment_Food();
        fragmentPlace = new Fragment_Place();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return fragmentFood;

            case 1:
                return fragmentPlace;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
