package com.example.mobileoilstation.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
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

import java.util.ArrayList;
import java.util.List;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);

        this.prepareViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
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

    private void prepareViewPager(ViewPager viewPager){
        ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager());

        adapter.addFragment(new UserFragment(), "Физическое лицо");
        adapter.addFragment(new CompanyFragment(), "Компания");

        viewPager.setAdapter(adapter);
    }

    public static class ViewAdapter extends FragmentPagerAdapter {

        ArrayList<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        public void addFragment(Fragment fragment, String title){
            this.titles.add(title);
            this.fragments.add(fragment);
        }

        public ViewAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return this.titles.get(position);
        }
    }
}