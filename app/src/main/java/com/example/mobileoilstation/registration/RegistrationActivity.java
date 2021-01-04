package com.example.mobileoilstation.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mobileoilstation.R;
import com.google.android.material.tabs.TabLayout;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);


    }

    private long backPressed = 0;
    @Override
    public void onBackPressed() {
        if (backPressed + 1000 > System.currentTimeMillis()){
            finish();
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}