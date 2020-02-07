package com.india.speechtotext.retrofitsdk;

import android.content.Context;

import com.india.speechtotext.BuildConfig;
import com.india.speechtotext.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private final Retrofit retrofit;
    private Service service;

    public APIClient(Retrofit retrofit) {
        this.retrofit = retrofit;
        createService();
    }


    public static class Builder {
        public Builder() {
        }

        public APIClient build(Context context) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (BuildConfig.DEBUG) {
                okHttpClient.addInterceptor(loggingInterceptor);
            }
            Retrofit retrofit = null;
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(context.getResources().getString(R.string.base_url))
                    .build();

            return new APIClient(retrofit);
        }
    }

    private void createService() {
        service = retrofit.create(Service.class);
    }

    public Service getService() {
        return service;
    }
}
