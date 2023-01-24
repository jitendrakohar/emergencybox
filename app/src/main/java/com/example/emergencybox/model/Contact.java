package com.example.emergencybox.model;

import android.graphics.Bitmap;

public class Contact {
    private String Name;
    private String phoneNumber;
    private Bitmap idPhoto=null;


    public Contact(String name, String phoneNumber) {
        Name = name;
        this.phoneNumber = phoneNumber;


    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Bitmap idPhoto) {
        this.idPhoto = idPhoto;
    }
}
