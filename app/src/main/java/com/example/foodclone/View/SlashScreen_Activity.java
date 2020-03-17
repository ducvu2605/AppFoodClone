package com.example.foodclone.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodclone.R;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class SlashScreen_Activity extends AppCompatActivity  {
    public static int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private TextView tvVersion;
    private LocationRequest locationRequest;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slashscreen);

        sharedPreferences  = getSharedPreferences("toado",MODE_PRIVATE);
        tvVersion = findViewById(R.id.txt_Version);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Nếu nhấn allow thì getCurrentLocation
                getCurrentLocation();
            } else {

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getCurrentLocation() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(getApplicationContext())
                        .removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latesLocationIndex = locationResult.getLocations().size() - 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    double latitude = locationResult.getLocations().get(latesLocationIndex).getLatitude();
                    double longtitude = locationResult.getLocations().get(latesLocationIndex).getLongitude();
                    editor.putString("Latitude", String.valueOf(latitude));
                    editor.putString("Longtitude", String.valueOf(longtitude));
                    editor.commit();
                    Log.d("Check", latitude + " - " + longtitude);
                }

                try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            tvVersion.setText(getString(R.string.Version)+" "+packageInfo.versionName);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iLogin = new Intent(SlashScreen_Activity.this,Login_Activity.class);
                    startActivity(iLogin);
                    finish(); // ket thuc chuyen screen
                }
            },2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
            }

        }, Looper.getMainLooper());



    }


}


