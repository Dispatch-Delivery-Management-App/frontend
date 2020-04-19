package com.fullstack.frontend.ui.newOrder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.NewOrder;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderMainResponse;
import com.fullstack.frontend.Retro.OrderResponse;
import com.fullstack.frontend.base.BaseRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderRepository extends BaseRepository {
    //LiveData<List<Plans>>
    public void getPlans(OrderDetailRequest request){
        MutableLiveData<List<OrderResponse>> responseLiveData = new MutableLiveData<>();

        plansRequestApi.postOrderGetPlans(request)
                .enqueue(new Callback<OrderMainResponse>() {
                             @Override
                             public void onResponse(Call<OrderMainResponse> call, Response<OrderMainResponse> response) {
                                 if (!response.isSuccessful()){
                                     Log.d("TTT","response code "+response.code());
                                     return;
                                 }
                                 if (response.body() != null) {
                                     OrderMainResponse orderMainResponse = (OrderMainResponse) response.body();
                                     for (OrderResponse orderResponse:orderMainResponse.response){
                                         Log.d("TTT",orderResponse.lastname);
                                     }

                                 }

                             }

                             @Override
                             public void onFailure(Call<OrderMainResponse> call, Throwable t) {
                                 Log.d("TTT","failed: "+t.getMessage());
                             }
                         }
                );
    }
}
