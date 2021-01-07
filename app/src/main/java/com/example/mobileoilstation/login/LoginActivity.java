package com.example.mobileoilstation.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileoilstation.R;
import com.example.mobileoilstation.registration.RegistrationActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    
    ConnectivityManager cm;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        EditText phone = findViewById(R.id.login);
        TextInputLayout ePhone = findViewById(R.id.eLogin);
        Button sendLogin = findViewById(R.id.sendLogIn);
        //text watcher
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ePhone.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(s.toString().trim().matches("(\\+7)(\\d{10})|(8\\d{10})"))){
                    ePhone.setError("Неверный телефон");
                }
            }
        });
        //text watcher
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    sendLogin.setEnabled(true);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    Toast.makeText(LoginActivity.this, "Включите интрнет", Toast.LENGTH_LONG).show();
                    sendLogin.setEnabled(false);
                }
            });
        }
    }

    public void registerNow(View view){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        this.finish();
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