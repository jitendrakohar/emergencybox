package com.example.emergencybox;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.emergencybox.utilities.Constant;

public class requestPermission extends AppCompatActivity {
    private  Context context;

    public  requestPermission(Context context){
    this.context=context;
}


    //Checking of the permission in android
    public void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            //if permission denied then request for a permission
            Toast.makeText(context, "request permission is handling", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
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
                Toast.makeText(context, "Fine location permission granted", Toast.LENGTH_SHORT).show();
//            else
            Toast.makeText(context, "Fine Location permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Constant.COARSE_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(context, " COARSE LOCATION permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "COARSE LOCATION  permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

}
