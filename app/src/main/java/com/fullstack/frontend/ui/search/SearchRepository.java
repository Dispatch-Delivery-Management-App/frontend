package com.fullstack.frontend.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderResponse;
import com.fullstack.frontend.Retro.SearchRequest;
import com.fullstack.frontend.Retro.SearchResponse;
import com.fullstack.frontend.base.BaseRepository;
import com.fullstack.frontend.config.UserInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository extends BaseRepository {
    @SerializedName("user_id")
    private String key;

    public SearchRepository() {}

    public void setKey(String key) {
        this.key = key;
    }

    public MutableLiveData<List<SearchResponse>> searchResult() {
        final MutableLiveData<List<SearchResponse>> searchResult = new MutableLiveData<>();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setUserId(UserInfo.getInstance().getUserId());
        searchRequest.setKey(key);
        Call<BaseResponse<List<SearchResponse>>> call = plansRequestApi.search(searchRequest);
        call.enqueue(new Callback<BaseResponse<List<SearchResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<SearchResponse>>> call, Response<BaseResponse<List<SearchResponse>>> response) {
                if (response.code() == 200) {
                    searchResult.setValue(response.body().response);
                    Log.d("TTT", "DATA" + response.body());
                } else {
                    Log.d("TTT", "Invalid" + response.body());

                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<SearchResponse>>> call, Throwable t) {
                Log.d("TTT", "Invalid");
            }
        });
        return searchResult;
    }
}
