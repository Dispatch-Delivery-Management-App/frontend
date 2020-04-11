package com.fullstack.frontend.onboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullstack.frontend.MainActivity;
import com.fullstack.frontend.R;
import com.fullstack.frontend.base.OnBoardingBaseFragment;

public class LoginFragment extends OnBoardingBaseFragment {

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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        submitButton.setText(getString(R.string.login));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainPage();
            }

        });
        return view;
    }

    private void openMainPage() {
        Intent intent = new Intent(submitButton.getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

}