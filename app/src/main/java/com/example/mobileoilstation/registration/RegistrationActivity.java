package com.example.mobileoilstation.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileoilstation.R;
import com.example.mobileoilstation.model.User;
import com.example.mobileoilstation.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registration);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);

        this.prepareViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        User user = new User("","", "","", null, null, null, null);

    }

    public void logIn(View view){
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
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

    public void registerNow(View view) {
        String email = ((EditText) findViewById(R.id.login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String dPassword = ((EditText) findViewById(R.id.d_password)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
        String name = ((EditText) findViewById(R.id.name)).getText().toString();

        if (!(email.matches("^([a-z0-9]+)@[a-z]+\\.[a-z]{1,3}$"))){
            
        } else {

        }
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