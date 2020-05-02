package com.fullstack.frontend.onboard.register;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.base.BaseRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel extends BaseRepository {

    private String username;
    private String email;
    private String password;
//    private String cPassword;

    public RegisterModel() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

//    public String getcPassword() { return cPassword; }
//
//    public void setcPassword(String cPassword) { this.cPassword = cPassword; }

    public MutableLiveData<OnBoardingResponse> userRegister() {
        MutableLiveData<OnBoardingResponse> registerResponse = new MutableLiveData<>();
        OnBoardingResponse onBoardingResponse = new OnBoardingResponse();
        onBoardingResponse.setUsername(username);
        onBoardingResponse.setEmail(email);
        onBoardingResponse.setPassword(password);
//        onBoardingResponse.setCpassword(cPassword);

        Call<BaseResponse<OnBoardingResponse>> call = plansRequestApi.register(onBoardingResponse);
        call.enqueue(new Callback<BaseResponse<OnBoardingResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OnBoardingResponse>> call, Response<BaseResponse<OnBoardingResponse>> response) {
                if (response.isSuccessful()) {
                    registerResponse.setValue(response.body().response);
                } else {
                    onBoardingResponse.setError("User Already Exist!");
                    registerResponse.setValue(onBoardingResponse);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<OnBoardingResponse>> call, Throwable t) {
                onBoardingResponse.setError(t.getMessage());
                registerResponse.setValue(onBoardingResponse);
            }
        });
        return registerResponse;
    }
}
