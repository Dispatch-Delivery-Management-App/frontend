package com.fullstack.frontend.onboard.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.base.RemoteRequestListener;
import com.fullstack.frontend.databinding.FragmentLoginBinding;
import com.fullstack.frontend.databinding.FragmentRegisterBinding;
import com.fullstack.frontend.util.Util;


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
            Toast.makeText(getActivity(), "Login Successfully!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.nav_home);
        });
    }

    @Override
    public void onFailure(String message) {
        Util.showToast(getContext(), message).show();
    }
}