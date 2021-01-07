package com.example.mobileoilstation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobileoilstation.login.LoginActivity;
import com.example.mobileoilstation.registration.RegistrationActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    private int progressInt = 0;
    private final Handler handler = new Handler();

    ConnectivityManager cm;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);

        mProgressBar = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        Drawable progressDrawable = mProgressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        mProgressBar.setProgressDrawable(progressDrawable);
        cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //Check internet off/on
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(new NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startNewThread();
                        }
                    }, 800);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    showToastAboutLossInternet();
                }
            });
        }
        //Check internet off/on

        //set thread
        if (isOnline(this)) {
            startNewThread();
        } else {
            showToastAboutLossInternet();
        }
        //set thread
    }

    public void startNewThread() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    while (progressInt < 100 && isOnline(SplashScreenActivity.this)) {
                        progressInt += 10;

                        Thread.sleep(300);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(progressInt);
                            }
                        });
                    }
                    if (!checkFirstStart() && isOnline(SplashScreenActivity.this)) {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                        finish();
                    } else if (!checkToken() && isOnline(SplashScreenActivity.this)) {
                        finish();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void showToastAboutLossInternet() {
        Toast toast = Toast.makeText(this, "У Вас выключен интернет", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 160);
        toast.show();
        progressInt = 0;
    }

    private long back_pressed = 0;

    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public boolean checkFirstStart() {
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        return sharedPreferences.getBoolean("firstStart", false);
    }

    public boolean checkToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        return sharedPreferences.getLong("token", 0) != 0;
    }

    public boolean isOnline(Context context) {
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}