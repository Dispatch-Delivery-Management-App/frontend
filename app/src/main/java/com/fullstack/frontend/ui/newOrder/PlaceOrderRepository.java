package com.fullstack.frontend.ui.newOrder;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderRepository extends BaseRepository {

    public void getPlans(GetPlansRequest request,MutableLiveData<List<Plan>> plans){

        plansRequestApi.postOrderGetPlans(request)
                .enqueue(new Callback<BaseResponse<List<Plan>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Plan>>> call, Response<BaseResponse<List<Plan>>> response) {
                        if (!response.isSuccessful()){
                            Log.d("TTT","response code "+response.code());
                            return;
                        }
                        if (response.body() != null) {
                            Log.d("TTT","Success");
                            plans.setValue(response.body().response);
                        }
                    }

                    @Override
                             public void onFailure(Call<BaseResponse<List<Plan>>> call, Throwable t) {
                                 Log.d("TTT","failed: "+t.getMessage());
                             }
                         }
                );
    }

}
