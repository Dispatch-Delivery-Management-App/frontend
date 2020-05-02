package com.fullstack.frontend.ui.address;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.newOrder.AddressListRequest;
import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListRepository extends BaseRepository {

    public MutableLiveData<List<AddressResponse>> getAddresses(AddressListRequest request){
        MutableLiveData<List<AddressResponse>> addresses = new MutableLiveData<>();
        plansRequestApi.getAddressList(request)
                .enqueue(new Callback<BaseResponse<List<AddressResponse>>>() {
                             @Override
                             public void onResponse(Call<BaseResponse<List<AddressResponse>>> call, Response<BaseResponse<List<AddressResponse>>> response) {
                                 if (!response.isSuccessful()){
                                     Log.d("TTT","address response code "+response.code());
                                     return;
                                 }
                                 if (response.body() != null) {
                                     Log.d("TTT","address Success");
                                     addresses.setValue(response.body().response);
                                 }
                             }

                             @Override
                             public void onFailure(Call<BaseResponse<List<AddressResponse>>> call, Throwable t) {
                                 Log.d("TTT","address failed: "+t.getMessage());
                             }
                         }
                );
        return addresses;
    }
}
