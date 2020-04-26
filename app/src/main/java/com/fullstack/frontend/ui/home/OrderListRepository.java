package com.fullstack.frontend.ui.home;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderListRepository implements Serializable {

    public List<OrderResponse> orderResponses;
    public static List<OrderResponse> sortedOrderResponses;
    public static ArrayList<Integer> statuses = new ArrayList<Integer>();
    public static ArrayList<Integer> ids = new ArrayList<Integer>();
    public static ArrayList<String> categories = new ArrayList<String>();
    public static ArrayList<String> receivers = new ArrayList<String>();

    public void getOrders(OrderDetailRequest request) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<List<OrderResponse>>> getOrders = apiService.getOrderList(new OrderDetailRequest(1));
        getOrders.enqueue(new Callback<BaseResponse<List<OrderResponse>>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<BaseResponse<List<OrderResponse>>> call,
                                   Response<BaseResponse<List<OrderResponse>>> response) {
                if (response.isSuccessful()) {
                    orderResponses = response.body().response;
                    sortedOrderResponses = sort(orderResponses);
                    for (OrderResponse response1 : orderResponses) {
                        Log.d("test ", response1.category + response1.lastname);
                        statuses.add(response1.status);
                        ids.add(response1.id);
                        categories.add(response1.category);
                        receivers.add(response1.lastname);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<OrderResponse>>> call, Throwable t) {

            }
        });
    }

    // sort the orders based on status to suit the partition
    public List<OrderResponse> sort(List<OrderResponse> orderResponses) {
         List<OrderResponse> result = new ArrayList<OrderResponse>();
         List<OrderResponse> notStartOrders = new ArrayList<OrderResponse>();
         List<OrderResponse> shippedOrders = new ArrayList<OrderResponse>();
         List<OrderResponse> completeOrders = new ArrayList<OrderResponse>();
         List<OrderResponse> draft = new ArrayList<OrderResponse>();

         for (OrderResponse response1 : orderResponses) {
             if (response1.status == 1) {
                 draft.add(response1);
             } else if (response1.status == 2) {
                 notStartOrders.add(response1);
             } else if (response1.status == 3) {
                 shippedOrders.add(response1);
             } else {
                 completeOrders.add(response1);
             }
         }

         // shipping
         for (OrderResponse response1 : notStartOrders) {
             result.add(response1);
         }
         for (OrderResponse response1 : shippedOrders) {
             result.add(response1);
         }
         // delivered
         for (OrderResponse response1 : completeOrders) {
             result.add(response1);
         }
         // draft
         for (OrderResponse response1 : draft) {
             result.add(response1);
         }

         return result;
    }

}
