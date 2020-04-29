package com.fullstack.frontend.ui.newOrder;

import android.util.Log;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.ConfirmOrderRequest;
import com.fullstack.frontend.base.BaseRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderRepository extends BaseRepository {

    public int confirmOrder(ConfirmOrderRequest request){
        plansRequestApi.confirmOrder(request)
                .enqueue(new Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        if (!response.isSuccessful()){
                            Log.d("TTT","Confirm response"+response.code());
                            return;
                        }
                        if (response.body() != null){
                            Log.d("TTT","Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                        Log.d("TTT","failed: "+t.getMessage());
                    }
                });
        return 1;
    }
}
