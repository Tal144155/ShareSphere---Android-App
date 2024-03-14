package com.example.facebook_like_android.retrofit;

import com.example.facebook_like_android.R;
import com.example.facebook_like_android.ShareSphere;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(40, TimeUnit.SECONDS) // Set connection timeout
                    .readTimeout(40, TimeUnit.SECONDS) // Set read timeout
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ShareSphere.context.getString(R.string.BaseUrl))
                    .client(client)
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
