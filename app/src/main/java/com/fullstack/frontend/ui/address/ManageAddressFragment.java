package com.fullstack.frontend.ui.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fullstack.frontend.databinding.FragmentAddressBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fullstack.frontend.R;

public class ManageAddressFragment extends Fragment {

    private AddressViewModel addressViewModel;
    private FragmentAddressBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddressBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
