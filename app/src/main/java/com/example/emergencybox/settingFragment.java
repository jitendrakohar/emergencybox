package com.example.emergencybox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emergencybox.databinding.FragmentSettingBinding;


public class settingFragment extends Fragment {

    private FragmentSettingBinding binding;

    public settingFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListeners();
    }

    private void setListeners() {
        binding.clContact.setOnClickListener(v -> {

            intentContact();

        });

        
        binding.tvContact.setOnClickListener(v -> {

            intentContact();

        });
        binding.clEditMyProfile.setOnClickListener(v->
        {
            startActivity(new Intent(getActivity(), MainActivity.class));
        });


    }

    private void intentContact() {
        startActivity(new Intent(getActivity(), AddContact.class));
//addContactClass is in above
    }


}