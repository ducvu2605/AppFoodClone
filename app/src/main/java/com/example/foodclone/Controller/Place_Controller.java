package com.example.foodclone.Controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodclone.Adapter.AdapterRecycler_Place;
import com.example.foodclone.Controller.Interfaces.Place_Interface;
import com.example.foodclone.Model.Restaurants_Model;
import com.example.foodclone.R;

import java.util.ArrayList;
import java.util.List;

public class Place_Controller {
    Context context;
    Restaurants_Model restaurantsModel;
    AdapterRecycler_Place adapterRecyclerPlace;

    public Place_Controller(Context context) {
        this.context = context;
        restaurantsModel = new Restaurants_Model();
    }

    public void getDanhSachQuanAnController(RecyclerView recyclerPlace, final ProgressBar progressBar){

        final List<Restaurants_Model> restaurantsModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerPlace.setLayoutManager(layoutManager);

        adapterRecyclerPlace = new AdapterRecycler_Place(restaurantsModelList, R.layout.custom_layout_recyleview_place);
        recyclerPlace.setAdapter(adapterRecyclerPlace);
        Place_Interface placeInterface = new Place_Interface() {
            @Override
            public void getDanhsachQuanAnModel(Restaurants_Model restaurantsModel) {
                Log.d("KiemTra",restaurantsModel.getNameRE()+"");
                restaurantsModelList.add(restaurantsModel);
                adapterRecyclerPlace.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE );
            }
        };
        restaurantsModel.getDanhsachQuanAn(placeInterface);
    }
}
