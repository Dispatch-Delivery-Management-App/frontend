package com.fullstack.frontend.ui.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fullstack.frontend.R;

public class ManageAddressFragment extends Fragment {

    private AddressViewModel addressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addressViewModel =
                ViewModelProviders.of(this).get(AddressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_address, container, false);

        return root;
    }
}
