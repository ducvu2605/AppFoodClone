package com.example.foodclone.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodclone.Adapter.Adapter_Viewpager_Home;
import com.example.foodclone.R;

public class Home_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private ViewPager viewPagerHome;
    private RadioButton rdFood,rdPlace;
    private RadioGroup rdGroup_Food_Place;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        viewPagerHome = findViewById(R.id.vp_Home);
        rdFood = findViewById(R.id.rd_Food);
        rdPlace = findViewById(R.id.rd_Place);
        rdGroup_Food_Place = findViewById(R.id.rd_Group_Food_Place);
        Adapter_Viewpager_Home adapter_viewpager_home = new Adapter_Viewpager_Home(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapter_viewpager_home);

        viewPagerHome.addOnPageChangeListener(this);
        rdGroup_Food_Place.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // positon of Page Fragment
        Log.d("CheckPositionPageFm",position+"");
        switch (position){
            case 0:
                rdFood.setChecked(true);
                break;
            case 1:
                rdPlace.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
            //int i is Checked ID of radioButton
        switch (i){
            case R.id.rd_Food:
                viewPagerHome.setCurrentItem(0); // click chuyển page .
                break;
            case R.id.rd_Place:
                viewPagerHome.setCurrentItem(1);
                break;
        }
    }
}
