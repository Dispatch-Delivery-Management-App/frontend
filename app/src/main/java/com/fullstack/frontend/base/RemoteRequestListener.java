package com.fullstack.frontend.base;

import androidx.lifecycle.LiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OnBoardingResponse;

public interface RemoteRequestListener {

    public void onSuccess(LiveData<OnBoardingResponse> loginResponse);
    public void onFailure(String message);
}
