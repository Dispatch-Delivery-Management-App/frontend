package com.fullstack.frontend.ui.newOrder;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.GetPlansRequest;
import com.fullstack.frontend.Retro.Plan;
import com.fullstack.frontend.base.BaseRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderRepository extends BaseRepository {

    public void getPlans(GetPlansRequest request){
        MutableLiveData<List<Plan>> responseLiveData = new MutableLiveData<>();

        plansRequestApi.postOrderGetPlans(request)
                .enqueue(new Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        if (!response.isSuccessful()){
                            Log.d("TTT","response code "+response.code());
                            return;
                        }
                        if (response.body() != null) {
                            Log.d("TTT",response.body().toString());
                        }
                    }

                    @Override
                             public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                                 Log.d("TTT","failed: "+t.getMessage());
                             }
                         }
                );
    }
}
