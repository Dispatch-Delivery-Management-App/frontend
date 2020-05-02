package com.fullstack.frontend.ui.home;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderListRequest;
import com.fullstack.frontend.Retro.OrderResponse;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderListRepository {

    public LiveData<List<OrderResponse>> getOrders(OrderListRequest request) {
        MutableLiveData<List<OrderResponse>> liveData = new MutableLiveData<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<List<OrderResponse>>> getOrders = apiService.getOrderList(request);
        getOrders.enqueue(new Callback<BaseResponse<List<OrderResponse>>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<BaseResponse<List<OrderResponse>>> call,
                                   Response<BaseResponse<List<OrderResponse>>> response) {
                if (response.isSuccessful()) {
                    List<OrderResponse> orderResponses = response.body().response;
                    orderResponses = response.body().response;
                    Collections.sort(orderResponses, (Comparator<OrderResponse>) (o1, o2) -> {
                        if (o1.status < o2.status) {
                            return -1;
                        } else {
                            return 1;
                        }
                    });
                    liveData.setValue(orderResponses);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<OrderResponse>>> call, Throwable t) {

            }
        });
        return liveData;
    }
}
