package com.fullstack.frontend.onboard;

import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullstack.frontend.MainActivity;
import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.base.OnBoardingBaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginFragment extends Fragment {
    EditText etUsername, etPassword;
    Button btnLogin;
    NavController navController;

    private static final String TAG = "LoginFragment";

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);


        view.findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
                navController = Navigation.findNavController(v);
            }
        });

        view.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.registerFragment);
            }
        });

        return view;
    }

    /**
     * ValidateUserData
     */
    private void validateUserData() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // checking if username is empty
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("please enter your username");
            etUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("please enter your password");
            etPassword.requestFocus();
            return;
        }

        //Login User if everything is fine
        loginUser(username, password);
    }

    /**
     * Call API
     */
    private void loginUser(String username, String password) {
        //making api call
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OnBoardingResponse> login = apiService.login(username, password);
        login.enqueue(new Callback<OnBoardingResponse>() {
            @Override
            public void onResponse(Call<OnBoardingResponse> call, Response<OnBoardingResponse> response) {
//              if (response.body().getStatus() == 200) {
//                    Log.e(TAG, "onResponse: " + response.body().getResponse());
//                    Toast.makeText(getActivity(), "Login Successfully!", Toast.LENGTH_SHORT).show();
//                    navController.navigate(R.id.action_loginFragment_to_nav_home);
//              }
//              else {
//                  Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT).show();
//              }
                if (response.body() == null) {
                    Log.e(TAG, "onResponse: " + response.body());
                    Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 200) {
                    Log.e(TAG, "onResponse: " + response.body().getStatus());
                    Toast.makeText(getActivity(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_loginFragment_to_nav_home);
                }

            }

            @Override
            public void onFailure(Call<OnBoardingResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: None");
                Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}