package com.fullstack.frontend.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import com.fullstack.frontend.R;

//baseFragment for register/login
public abstract class OnBoardingBaseFragment extends Fragment {
    protected EditText usernameEditText;
    protected EditText passwordEditText;
    protected Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayout(), container, false);
        usernameEditText = (EditText) view.findViewById(R.id.editTextLogin);
        passwordEditText = (EditText) view.findViewById(R.id.editTextPassword);
        submitButton = (Button) view.findViewById(R.id.submit);

        return view;

    }


    @LayoutRes
    protected abstract int getLayout();
}

