package com.example.mobileoilstation.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobileoilstation.HomeActivity;
import com.example.mobileoilstation.R;
import com.example.mobileoilstation.api.ApiRequest;
import com.example.mobileoilstation.databinding.ActivityLoginBinding;
import com.example.mobileoilstation.model.Login;
import com.example.mobileoilstation.model.Token;
import com.example.mobileoilstation.registration.RegistrationActivity;
import com.example.mobileoilstation.utils.ShowDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ConnectivityManager cm;
    private boolean isConnect = false;
    private ActivityLoginBinding binding;
    private String BASE_URL = "http://mobile-oil-station.ru";

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            assert cm != null;
            cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnect = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    Toast.makeText(LoginActivity.this, "Включите интрнет", Toast.LENGTH_LONG).show();
                    isConnect = false;
                }
            });
        }
    }

    public void registerNow(View view){
        ShowDialog.showDialog(this);
        view.setEnabled(false);
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        this.finish();
    }

    public void logIn(View view){
        ShowDialog.showDialog(this);
        view.setEnabled(false);
        if (isConnect){
            if (!binding.login.getText().toString().equals("") && !binding.password.getText().toString().equals("")) {
                ApiRequest retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiRequest.class);

                Call<Token> service = retrofit.logIn(new Login(binding.login.getText().toString(), binding.password.getText().toString()));
                service.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() != 403){
                            String token = response.body().getToken();
                            System.out.println(token);
                            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), 0);
                            SharedPreferences.Editor ed = sharedPreferences.edit();
                            ed.putString("token", token);
                            ed.apply();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Snackbar.make(binding.sendLogIn.getRootView(), "Такого пользователя не существует", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        } else {
            Snackbar.make(view, "Включите интернет соединение", Snackbar.LENGTH_SHORT).show();
        }
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