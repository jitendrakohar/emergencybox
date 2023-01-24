package com.example.emergencybox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class locationsend {

    FusedLocationProviderClient mFusedLocationClient;
    public static final int PERMISSION_ID = 44;
    String Latitude, Longitude;
    private Context context;


    public locationsend(Context context) {
        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Toast.makeText(context, "latitude " + location.getLatitude() + " Longitude " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                Latitude = "" + location.getLatitude();
                Longitude = "" + location.getLongitude();
                sendMessage("7232913419");

            }
        });
    }

    public void Distance() {
        Location startPoint = new Location("LocationA");
        startPoint.setLatitude(Double.parseDouble(Latitude));
        startPoint.setLongitude(Double.parseDouble(Longitude));
        Location endPoint = new Location("locationA");
        endPoint.setLongitude(14.555555555);
        endPoint.setLatitude(80.0000231342);
        double distance = startPoint.distanceTo(endPoint);
        Toast.makeText(context, "distance between these two points " + distance, Toast.LENGTH_SHORT).show();

    }

    public void sendMessage(String number) {
        String message = " I am in Emergency please reach out at this location to help me out " + "http://maps.google.com/maps?q=loc:" + Latitude + "," + Longitude + "";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);

            alertDialog("Message Sent successfully");
        } catch (Exception e) {
            Toast.makeText(context, "unable to send a Message " + e.getMessage(), Toast.LENGTH_SHORT).show();
            alertDialog(e.getMessage());
        }
    }

    public void alertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Emergency Alert");
        builder.setMessage("" + message);
        builder.setCancelable(false);
        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
