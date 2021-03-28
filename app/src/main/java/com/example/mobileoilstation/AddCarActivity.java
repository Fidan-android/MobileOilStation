package com.example.mobileoilstation;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileoilstation.api.ApiRequest;
import com.example.mobileoilstation.databinding.ActivityAddCarBinding;
import com.example.mobileoilstation.model.Car;
import com.example.mobileoilstation.model.Message;
import com.example.mobileoilstation.utils.ShowDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.Body;

public class AddCarActivity extends AppCompatActivity {

    private ActivityAddCarBinding binding;
    private final String BaseUrl= "http://mobile-oil-station.ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backHome.setOnClickListener((view) -> {
            onBackPressed();
            //startActivity(new Intent(AddCarActivity.this, HomeActivity.class));
        });

        binding.createCar.setOnClickListener((v) -> {
            ShowDialog.showDialog(this);
            v.setEnabled(false);
            SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.app_name), 0);
            String token = preferences.getString("token", "");

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    })
                    .build();

            ApiRequest retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(client)
                    .build()
                    .create(ApiRequest.class);

            Call<Message> service = retrofit.createCar(new Car("", "", "", ""));
            service.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {
                    assert response.body() != null;
                    String message = response.body().getMessage();
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            v.setEnabled(true);
        });
    }
}