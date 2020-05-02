package com.fullstack.frontend.onboard.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.base.BaseRepository;
import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.base.RemoteRequestListener;
import com.fullstack.frontend.databinding.FragmentRegisterBinding;
import com.fullstack.frontend.onboard.login.LoginViewModel;
import com.fullstack.frontend.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends BaseFragment<RegisterViewModel, RegisterModel> implements RemoteRequestListener {

    private FragmentRegisterBinding binding;

    EditText username, email, password, cpassword;
    Button btnRegister, login;
    NavController navController;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentRegisterBinding.inflate(inflater, container, false);
        viewModel.setRemoteRequestListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.etUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                viewModel.setUsername(binding.etUsername.getText().toString());
            }
        });
        binding.etEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                viewModel.setEmail(binding.etEmail.getText().toString());
            }
        });
        binding.etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                viewModel.setPassword(binding.etPassword.getText().toString());
            }
        });
//        binding.etCpassword.setOnFocusChangeListener((v, hasFocus) -> {
//            if (!hasFocus) {
//                viewModel.setCpassword(binding.etCpassword.getText().toString());
//            }
//        });
        binding.btnRegister.setOnClickListener(v -> {
            binding.etPassword.clearFocus();
            viewModel.Register();
        });

    }


    @Override
    protected RegisterViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(RegisterViewModel.class);
    }

    @Override
    protected ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RegisterViewModel(getRepository());
            }
        };
    }

    @Override
    protected RegisterModel getRepository() {
        return new RegisterModel();
    }

    @Override
    public void onSuccess(LiveData<OnBoardingResponse> loginResponse) {
        loginResponse.observe(this, it -> {


            Util.showToast(getContext(), it.getError()).show();

            if (it.getError().equals("OK")) {
                Toast.makeText(getActivity(), "Register Successfully!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.loginFragment);
            }
        });
    }

    @Override
    public void onFailure(String message) {
        Util.showToast(getContext(), message).show();
    }

}
