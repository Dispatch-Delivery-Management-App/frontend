package com.fullstack.frontend.onboard.login;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.base.BaseRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel extends BaseRepository {
    private String userName;
    private String passWord;

    public LoginModel() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MutableLiveData<OnBoardingResponse> userLogin() {
        MutableLiveData<OnBoardingResponse> loginResponse = new MutableLiveData<>();
        OnBoardingResponse onBoardingResponse = new OnBoardingResponse();
        onBoardingResponse.setUsername(userName);
        onBoardingResponse.setPassword(passWord);

        Call<BaseResponse<OnBoardingResponse>> call = plansRequestApi.login(onBoardingResponse);
        call.enqueue(new Callback<BaseResponse<OnBoardingResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OnBoardingResponse>> call, Response<BaseResponse<OnBoardingResponse>> response) {
                if (response.isSuccessful()) {
                    loginResponse.setValue(response.body().response);
                } else  {
                    onBoardingResponse.setError("Invalid Username or Password");
                    loginResponse.setValue(onBoardingResponse);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<OnBoardingResponse>> call, Throwable t) {
                onBoardingResponse.setError(t.getMessage());
                loginResponse.setValue(onBoardingResponse);
            }

        });

        return loginResponse;

    }
}
