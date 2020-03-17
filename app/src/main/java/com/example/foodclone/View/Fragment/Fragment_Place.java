package com.example.foodclone.View.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodclone.Controller.Place_Controller;
import com.example.foodclone.Model.Restaurants_Model;
import com.example.foodclone.R;

public class Fragment_Place extends Fragment {
    Place_Controller placeController;
    Restaurants_Model restaurantsModel;
    RecyclerView recyclerPlace;
    ProgressBar progressBarPlace;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_place,container,false);

        recyclerPlace = view.findViewById(R.id.recycler_Place);
        progressBarPlace = view.findViewById(R.id.progressbar_Place);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
//       restaurantsModel = new Restaurants_Model();
//       restaurantsModel.getDanhSachQuanAn();
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location currentLocation = new Location(" ");
        currentLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("Latitude","0")));
        currentLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("Longtitude","0")));
//        Log.d("CheckShareLocation",sharedPreferences.getString("Latitude","0")+"");
//        Log.d("CheckShareLocation",sharedPreferences.getString("Longtitude","0")+"");
        placeController = new Place_Controller(getContext());
        placeController.getDanhSachQuanAnController(recyclerPlace,progressBarPlace);
    }
}
