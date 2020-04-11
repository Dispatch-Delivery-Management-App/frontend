package com.fullstack.frontend.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullstack.frontend.R;
import com.fullstack.frontend.base.OnBoardingBaseFragment;

public class RegisterFragment extends OnBoardingBaseFragment {

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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        submitButton.setText(R.string.register);
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register;
    }

}


