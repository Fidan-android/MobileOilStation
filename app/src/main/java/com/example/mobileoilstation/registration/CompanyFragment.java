package com.example.mobileoilstation.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileoilstation.R;
import com.example.mobileoilstation.databinding.FragmentCompanyBinding;
import com.example.mobileoilstation.login.LoginActivity;

public class CompanyFragment extends Fragment {

    FragmentCompanyBinding binding;
    private Activity activity;

    public CompanyFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyBinding.inflate(inflater, container, false);
        binding.backLogin.setOnClickListener(v -> {
            startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        });
        return binding.getRoot();
    }
}