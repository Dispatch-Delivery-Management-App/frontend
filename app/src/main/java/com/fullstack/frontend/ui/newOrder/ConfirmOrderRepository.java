package com.fullstack.frontend.ui.newOrder;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.base.BaseRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderRepository extends BaseRepository {

    public int confirmOrder(GetPlansRequest request, MutableLiveData<Integer> order_id){
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
                            order_id.setValue(1);
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
