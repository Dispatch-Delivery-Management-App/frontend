package com.fullstack.frontend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnBoardingActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private static final String TAG = "OnBoardingActivity";
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

         apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }



    /**
     * Call the API by clicking the button
     */

//    public void getTodos(View view) {
//        Call<List<Todo>> call = apiInterface.getTodos();
//        call.enqueue(new Callback<List<Todo>>() {
//            @Override
//            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
//                Log.e(TAG, "onResponse: " + response.body() );
//            }
//
//            @Override
//            public void onFailure(Call<List<Todo>> call, Throwable t) {
//                Log.e(TAG, "onFaile: " + t.getLocalizedMessage() );
//            }
//        });
//    }

//    public void getTodoUsingRouteParameter(View view) {
//
//    }
//
//    public void getTodosUsingQuery(View view) {
//
//    }
//
//    public void postTodo(View view) {
//
//    }
}
