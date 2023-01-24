package com.example.emergencybox;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emergencybox.databinding.ActivityDashboardBinding;
import com.example.emergencybox.utilities.Constant;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new DashboardFragment(DashboardActivity.this));
        setListeners();

    }

    private void setListeners() {
//setting up the bottom navigation bar android
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.dashboard:
                    replaceFragment(new DashboardFragment(DashboardActivity.this));
                    break;
                case R.id.community:
                    replaceFragment(new CommunityFragment());
                    break;

                case R.id.setting:
                    replaceFragment(new settingFragment());
                    break;
            }
            return true;
        });

//Asking for permission
//        requestPermission(Manifest.permission.INTERNET, Constant.INTERNET_PERMISSION_CODE);
//        requestPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, Constant.LOCATION_PERMISSION_CODE);
//        requestPermission(Manifest.permission.CAMERA, Constant.CAMERA_PERMISSION_CODE);
//        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Constant.STORAGE_PERMISSION_CODE);
//        requestPermission(Manifest.permission.RECORD_AUDIO, Constant.AUDIO_PERMISSION_CODE);
//        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, Constant.FINE_LOCATION_PERMISSION_CODE);
//        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Constant.COARSE_LOCATION_PERMISSION_CODE);
    }


    //Changing up the fragment android
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }



    //Checking of the permission in android
    public void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(DashboardActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            //if permission denied then request for a permission
            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{permission}, requestCode);
        } else {
//            Toast.makeText(this, permission.toString()+"Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.CAMERA_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();

        } else if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
////                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.LOCATION_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
////                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            else
////                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();

        } else if (requestCode == Constant.INTERNET_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this, "Internet Permission Granted", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(this, "Internet Permission Denied", Toast.LENGTH_SHORT).show();

        } else if (requestCode == Constant.AUDIO_PERMISSION_CODE) {
//            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this, "Audio permission granted", Toast.LENGTH_SHORT).show();
//            else
////                Toast.makeText(this, "Microphone permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.FINE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Fine location permission granted", Toast.LENGTH_SHORT).show();
//            else
            Toast.makeText(this, "Fine Location permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, " COARSE LOCATION permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "COARSE LOCATION  permission Denied", Toast.LENGTH_SHORT).show();
        }
    }


}