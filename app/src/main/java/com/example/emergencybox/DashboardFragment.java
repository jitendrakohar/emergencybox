package com.example.emergencybox;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.emergencybox.databinding.FragmentDashboardBinding;
import com.example.emergencybox.utilities.Constant;
import com.google.common.util.concurrent.ListenableFuture;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private Context context;
    public static final int PERMISSION_ID = 44;

    public DashboardFragment(Context context) {
        this.context = context;
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setListener() {
        binding.cvGPS.setOnClickListener(v -> {
         gps();
        });
        binding.ivLocation.setOnClickListener(v -> {
            gps();
        });

        binding.ivMMS.setOnClickListener(v -> {
            if (checkPermissionOfAll(Manifest.permission.CAMERA)) {
                camera();
            } else {
                requestPermission(Manifest.permission.CAMERA, Constant.CAMERA_PERMISSION_CODE);
            }
        });

    }

    public void gps() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
//                Intent intent = new Intent(context, contactLocation.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
               if(checkPermissionOfAll(Manifest.permission.SEND_SMS)) {

                   locationsend locationsends = new locationsend(getActivity());
                   locationsends.getLastLocation();
               }
               else{
                   requestPermission(Manifest.permission.SEND_SMS,Constant.SEND_SMS_PERMISSION_CODE);
               }
            }
            else
            {
                Toast.makeText(context.getApplicationContext(), "Location disabled, Please Turn on your location...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else
        {
            requestPermission();
        }
    }

    public void camera() {
        startActivity(new Intent(getContext(), snapshotOfIntruders.class));

    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //Checking of the permission in android
    public void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            //if permission denied then request for a permission
            Toast.makeText(context, "Requesting permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
            if (checkPermissionOfAll(Manifest.permission.CAMERA)) {
                camera();
            }
        } else {
            Toast.makeText(context, permission + "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camera();
            } else {
                Toast.makeText(context, "you cannot access this features without this permission", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.FINE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(context, "Fine location permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Fine Location permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(context, " COARSE LOCATION permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "COARSE LOCATION  permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermissionOfAll(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}