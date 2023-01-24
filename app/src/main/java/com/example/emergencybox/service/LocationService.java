package com.example.emergencybox.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.emergencybox.R;
import com.example.emergencybox.utilities.Constant;

public class LocationService extends Service {

    public LocationService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        String Latitude=intent.getStringExtra("latitude");
        String Longitude=intent.getStringExtra("longitude");


        Toast.makeText(getApplicationContext(), "Latitude " + Latitude+ " and " + Longitude, Toast.LENGTH_SHORT).show();
        createNotificationChannel();

//Intent to be implemented for both helper and Needy People so i have to use both the uri function on the both client side android


//        setting up the uri intent android java google maps
        // Create a Uri from an intent string. Use the result to create an Intent.
//        Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+15.2993+","+74.1240+"");
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?q=loc:" + Latitude + "," + Longitude + "");
// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        notificationIntent.setPackage("com.google.android.apps.maps");


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, Constant.CHANNEL_ID)
                .setContentTitle(""+Latitude+" longitude "+Longitude)
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_location)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(Constant.CHANNEL_ID, "Foreground Service channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
