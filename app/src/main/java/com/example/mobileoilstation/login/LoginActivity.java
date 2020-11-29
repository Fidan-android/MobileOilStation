package com.example.mobileoilstation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileoilstation.R;
import com.example.mobileoilstation.registration.RegistrationActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText phone = findViewById(R.id.login);
        TextInputLayout ePhone = findViewById(R.id.eLogin);
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
    }

    public void registerNow(View view){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }
}