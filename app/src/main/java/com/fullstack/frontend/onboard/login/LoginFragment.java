package com.fullstack.frontend.onboard.login;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.Retro.TokenRequest;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.base.RemoteRequestListener;
import com.fullstack.frontend.config.UserInfo;
import com.fullstack.frontend.databinding.FragmentLoginBinding;
import com.fullstack.frontend.databinding.FragmentRegisterBinding;
import com.fullstack.frontend.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment<LoginViewModel, LoginModel> implements RemoteRequestListener {

    NavController navController;

    private FragmentLoginBinding binding;
    private FragmentRegisterBinding registerBinding;
    //private NavigationManager navigationManager;

    public LoginFragment() {
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //  navigationManager = (NavigationManager) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel.setRemoteRequestListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        binding.etUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                viewModel.setUserName(binding.etUsername.getText().toString());
            }
        });

        binding.etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                viewModel.setPassword(binding.etPassword.getText().toString());
            }
        });

        binding.btnLogin.setOnClickListener( v -> {
            binding.etPassword.clearFocus();
            viewModel.Login();
        });
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(LoginViewModel.class);
    }

    @Override
    protected ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(getRepository());
            }
        };
    }

    @Override
    protected LoginModel getRepository() {
        return new LoginModel();
    }

    @Override
    public void onSuccess(LiveData<OnBoardingResponse> loginResponse) {
        loginResponse.observe(this, it -> {
            Util.showToast(getContext(), it.getError()).show();

            if (it.getError().equals("OK")) {


               Toast.makeText(getActivity(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                UserInfo.getInstance().setUserId(it.getId());

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {


                        Log.d("token", instanceIdResult.getToken());

                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        //Log.d(TAG, "user id:" + UserInfo.getUser_id());
                        TokenRequest request = new TokenRequest(UserInfo.getInstance().getUserId(), instanceIdResult.getToken());
                        Call<BaseResponse> postToken = apiService.postToken(request);
                        postToken.enqueue(new Callback<BaseResponse>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response.isSuccessful()) {

                                    Log.d("FirebaseService", "Send token: " + response.code());
                                }
                                if (response.body() != null) {
                                    Log.d("FirebaseService", "Send token: " + "Success");


                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {

                                Log.d("FirebaseService", "Send token failed");


                            }
                        });
                    }
                });
                Navigation.findNavController(getView()).navigate(R.id.nav_home);

            }

        });
    }

    @Override
    public void onFailure(String message) {
        Util.showToast(getContext(), message).show();
    }
}