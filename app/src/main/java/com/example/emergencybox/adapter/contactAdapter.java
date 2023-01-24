package com.example.emergencybox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emergencybox.R;
import com.example.emergencybox.model.Contact;

import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder> {
    private List<Contact> mContacts;
    Context context;

    public contactAdapter(Context context, List<Contact> mContacts) {

        this.mContacts = mContacts;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactitem, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.contactNumber.setText(mContacts.get(position).getPhoneNumber());
        holder.contactName.setText(mContacts.get(position).getName());

    }

    @Override
    public int getItemCount() {

        return mContacts.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber, idImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.ContactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);

        }

    }
}
