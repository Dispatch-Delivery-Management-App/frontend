package com.fullstack.frontend.onboard;

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

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.OnBoardingResponse;
import com.fullstack.frontend.base.OnBoardingBaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    EditText username, email, password, cpassword;
    Button btnRegister, login;
    NavController navController;

    private static final String TAG = "RegisterFragment";

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_register, container, false);

        username = view.findViewById(R.id.registerUsername);
        email = view.findViewById(R.id.registerEmail);
        password = view.findViewById(R.id.registerPassword);
        cpassword = view.findViewById(R.id.confirmPassword);
        login = view.findViewById(R.id.login);

        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.loginFragment);
            }
        });

        return view;
    }

    private void validateUserData() {
        String reg_name = username.getText().toString();
        String reg_email = email.getText().toString();
        String reg_password = password.getText().toString();
        String reg_cpassword = cpassword.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(reg_name)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        //checking if email is empty
        if (TextUtils.isEmpty(reg_email)) {
            email.setError("Please enter email");
            email.requestFocus();
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reg_password)) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        //checking if password matches
        if (!reg_password.equals(reg_cpassword)) {
            password.setError("Password Does not Match");
            password.requestFocus();
            return;
        }

        //After Validating we register User
        registerUser(reg_name, reg_email, reg_password);
    }
    private void registerUser(String user_name, String user_email, String user_pass) {

        // call retrofit
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OnBoardingResponse> register = apiService.register(user_name, user_email, user_pass);
        register.enqueue(new Callback<OnBoardingResponse>() {
            @Override
            public void onResponse(Call<OnBoardingResponse> call, Response<OnBoardingResponse> response) {
                if (response.body().getStatus() == 201) {
                    Log.e(TAG, "onResponse: " + response.body());
                    Toast.makeText(getActivity(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                }
                if (response.body().getStatus() == 400) {
                    Log.e(TAG, "onResponse: " + response.body());
                    Toast.makeText(getActivity(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OnBoardingResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: None");
                Toast.makeText(getActivity(), "Register Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
