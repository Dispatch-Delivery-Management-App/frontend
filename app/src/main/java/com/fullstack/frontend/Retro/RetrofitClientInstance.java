package com.fullstack.frontend.Retro;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static retrofit2.Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static retrofit2.Retrofit getRetrofitInstance() {
        if (retrofit == null) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …
// add logging as last interceptor
//            httpClient.addInterceptor(logging);  // <-- this is the important line!
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
