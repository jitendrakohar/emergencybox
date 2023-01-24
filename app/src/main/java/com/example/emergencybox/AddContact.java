package com.example.emergencybox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emergencybox.adapter.contactAdapter;
import com.example.emergencybox.databinding.ActivityAddContactBinding;
import com.example.emergencybox.model.Contact;
import com.example.emergencybox.utilities.Constant;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddContact extends AppCompatActivity {
    private ActivityAddContactBinding binding;
    public static final int CONTACT_PERMISSION_CODE = 101;
    public static final int REQUEST_CONTACT = 1;
    Intent pickContact;
    contactAdapter adapter;
    private ArrayList<Contact> contactList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getApplicationContext().getSharedPreferences(Constant.KEY_PREFERENCES_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadData();
        setListeners();


    }


    private void setListeners() {

        binding.fabAddContact.setOnClickListener(v -> {
            if (contactList != null) {
                if (contactList.size() > 5) {
                    Toast.makeText(this, "YOU CANNOT ADD MORE THAN 5 CONTACT", Toast.LENGTH_SHORT).show();
                }
            }
            if (hasContactPermission()) {

                pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI), REQUEST_CONTACT);

            } else {
                checkPermission(Manifest.permission.READ_CONTACTS, CONTACT_PERMISSION_CODE);
            }

        });
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(AddContact.this, "check Permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(AddContact.this, new String[]{permission}, requestCode);
        } else {

            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Contact permission granted", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Contact permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean hasContactPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            Cursor cursor = null;
            try {
                String phoneNo;
                cursor = getContentResolver()
                        .query(contactUri, null, null, null, null);
                if (cursor.getCount() == 0) return;
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                int phoneIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phoneNo = cursor.getString(phoneIndex);
            if(contactList.size()<=5)
                saveData(name, phoneNo);
            else
                Toast.makeText(this, "you cannot add more than 5 Emergency CONTACT \n DELETE some contact to add a contact", Toast.LENGTH_SHORT).show();;
            } finally {
                cursor.close();
            }

        }

    }


    public void saveData(String name, String age) {
        contactList.add(new Contact(name, age));
        commitData();
        loadData();
    }

    public void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.KEY_CONTACT, null);
        Type type = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        contactList = gson.fromJson(json, type);
        if (contactList != null) {
            adapter = new contactAdapter(this, contactList);
            binding.rcvContact.setAdapter(adapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return true;
                }

                @Override
                public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                    super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);

                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Contact deletedCourse = contactList.get(viewHolder.getAdapterPosition());
                    int position = viewHolder.getAdapterPosition();
                    contactList.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    commitData();
                    Snackbar.make(binding.rcvContact, deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            contactList.add(position, deletedCourse);
                            adapter.notifyItemInserted(position);
                            commitData();
                        }
                    }).show();
                }
            }
            ).attachToRecyclerView(binding.rcvContact);
        } else if (contactList == null) {
            contactList = new ArrayList<>();
            binding.rcvContact.setAdapter(adapter);
        }

    }


    public void commitData() {
        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        editor.putString(Constant.KEY_CONTACT, json);
        editor.apply();
    }

}

