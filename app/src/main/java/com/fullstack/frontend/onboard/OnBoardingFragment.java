package com.fullstack.frontend.onboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullstack.frontend.R;
import com.fullstack.frontend.base.OnBoardingBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingFragment extends Fragment {

    public OnBoardingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding, container, false);
    }
}