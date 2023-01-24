package com.example.emergencybox;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.example.emergencybox.utilities.Constant;

public class MainActivity extends AppCompatActivity {
    requestPermission requestsPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       requestsPermission = new requestPermission(MainActivity.this);
        requestsPermission.requestPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, Constant.LOCATION_PERMISSION_CODE);;

    }
}