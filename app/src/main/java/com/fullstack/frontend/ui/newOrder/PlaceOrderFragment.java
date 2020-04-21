package com.fullstack.frontend.ui.newOrder;



import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.NewOrder;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderResponse;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.base.BaseRepository;
import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.databinding.PlaceOrderFragmentBinding;

public class PlaceOrderFragment extends BaseFragment<PlaceOrderViewModel, PlaceOrderRepository> {
    private PlaceOrderFragmentBinding binding;

    public PlaceOrderFragment() {
    }

    public static PlaceOrderFragment newInstance() {
        return new PlaceOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PlaceOrderFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Next Step Button
        Button confirmButton = getActivity().findViewById(R.id.button_showRecommend);

        //Click Button Next Step
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // NewOrder order = setPlaceOrderInfo(v);
                OrderDetailRequest request = new OrderDetailRequest();
                request.user_id= 1;
                viewModel.postOrder(request);
                Navigation.findNavController(v).navigate(R.id.place_to_recommend);
            }
        });


    }

    private NewOrder setPlaceOrderInfo(View view) {

        String fromFirstname = binding.fromAddForm.fromFirst.getText().toString();
        String fromLastname = binding.fromAddForm.fromLast.getText().toString();
        String fromAddress = binding.fromAddForm.fromAdd1.getText().toString()+ binding.fromAddForm.fromAdd2.getText().toString();

        String toFirstname = binding.toAddForm.fromFirst.getText().toString();
        String toLastname = binding.toAddForm.fromLast.getText().toString();
        String toAddress = binding.toAddForm.fromAdd1.getText().toString()+binding.toAddForm.fromAdd2.getText().toString();

        return null;

    }

    @Override
    protected PlaceOrderViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(PlaceOrderViewModel.class);
    }


    @Override
    protected PlaceOrderRepository getRepository() {
        return new PlaceOrderRepository();
    }

    @Override
    protected ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PlaceOrderViewModel(getRepository());
            }
        };
    }


}
