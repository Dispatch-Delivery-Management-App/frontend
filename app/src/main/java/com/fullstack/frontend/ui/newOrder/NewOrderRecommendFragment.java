package com.fullstack.frontend.ui.newOrder;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.databinding.NewOrderRecommendCardBinding;
import com.fullstack.frontend.databinding.NewOrderRecommendFragmentBinding;

public class NewOrderRecommendFragment extends BaseFragment<NewOrderRecommendViewModel,ConfirmOrderRepository> {
    private NewOrderRecommendFragmentBinding binding;

    public static NewOrderRecommendFragment newInstance() {
        return new NewOrderRecommendFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected NewOrderRecommendViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(NewOrderRecommendViewModel.class);
    }

    @Override
    protected ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new NewOrderRecommendViewModel(getRepository());
            }
        };
    }

    @Override
    protected ConfirmOrderRepository getRepository() {
        return new ConfirmOrderRepository();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewOrderRecommendFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Plan[] plans = NewOrderRecommendFragmentArgs.fromBundle(getArguments()).getReturnedPlans();
        GetPlansRequest request = NewOrderRecommendFragmentArgs.fromBundle(getArguments()).getReturnedRequest();
        for (Plan plan:plans) {
            inflatePlan(plan);
        }

        Button buttonConfirm = binding.buttonOrderConfirm;
        buttonConfirm.setEnabled(false);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonConfirm.setEnabled(false);
                setRequest(request);
                viewModel.confirmOrder(request);
                NewOrderRecommendFragmentDirections.RecommendToDetail action = NewOrderRecommendFragmentDirections.recommendToDetail();
                viewModel.getOrder_id().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        action.setOrderId(integer);
                        buttonConfirm.setEnabled(true);
                        Navigation.findNavController(v).navigate(action);
                    }
                });

            }
        });

        EnhancedRadioGroup radioGroup = binding.cardGroup;
        radioGroup.setOnSelectionChanged(new EnhancedRadioGroup.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(RadioButton radioButton, int index) {
                binding.buttonOrderConfirm.setEnabled(true);
            }
        });
    }


    private void setRequest(GetPlansRequest request) {
        Plan plan = null;
        int selectedID =  binding.cardGroup.getSelectedItem().getId();
        if (selectedID == binding.recommendedPlan.radioButton5.getId()){
            plan = viewModel.getBest();
        }else if (selectedID == binding.cheapPlan.radioButton5.getId()){
            plan = viewModel.getCheap();
        }else if (selectedID == binding.fastPlan.radioButton5.getId()){
            plan = viewModel.getFast();
        }
        request.amount = plan.amount;
        request.station = plan.station;
        request.shipping_method = plan.shipping_method;
        request.fee = plan.fee;
    }

    private void inflatePlan(Plan plan) {
        NewOrderRecommendCardBinding option = null;
        int planType = 0;
        switch (plan.type){
            case 0:option = binding.recommendedPlan;planType = R.string.best;viewModel.setBest(plan); break;
            case 1:option = binding.cheapPlan;planType = R.string.cheap;viewModel.setCheap(plan);break;
            case 2:option = binding.fastPlan;planType = R.string.fast; viewModel.setFast(plan);break;
            default:
                Log.d("TTT","plan type invalid");
        }
        if (option != null){
            option.planOption.setText(planType);
            option.fee.setText(String.valueOf(plan.fee));
            option.duration.setText(String.valueOf(plan.duration));
            option.stationID.setText(String.valueOf(plan.station));
            option.shippingMethodType.setText(plan.shipping_method);
            option.Number.setText(String.valueOf(plan.amount));
            option.ratingBar.setRating((float) plan.rating);
        }
    }



}
