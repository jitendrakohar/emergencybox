package com.example.emergencybox;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.emergencybox.databinding.ActivityContactLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class contactLocation extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;
    private ActivityContactLocationBinding binding;
    public static final int PERMISSION_ID = 44;
    String Latitude, Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        setListener();
    }

    public void setListener() {
        binding.actualDistance.setOnClickListener(v -> {
            Distance();
            sendMessage("832847");
        });
    }

    private void getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        Toast.makeText(getApplicationContext(), "latitude " + location.getLatitude() + " Longitude " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        Latitude = "" + location.getLatitude();
                        Longitude = "" + location.getLongitude();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Location disabled, Please Turn on your location...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "requesting for permission", Toast.LENGTH_SHORT).show();
//            requestPermission();
        }
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void Distance() {
        Location startPoint = new Location("LocationA");
        startPoint.setLatitude(Double.parseDouble(Latitude));
        startPoint.setLongitude(Double.parseDouble(Longitude));
        Location endPoint = new Location("locationA");
        endPoint.setLongitude(14.555555555);
        endPoint.setLatitude(80.0000231342);
        double distance = startPoint.distanceTo(endPoint);
        Toast.makeText(this, "distance between these two points " + distance, Toast.LENGTH_SHORT).show();

    }

    public void sendMessage(String number) {
        String message = " I am in Emergency please reach out at this location to help me out " + "http://maps.google.com/maps?q=loc:" + Latitude + "," + Longitude + "";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            smsManager.notify();
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "unable to send a Message " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}