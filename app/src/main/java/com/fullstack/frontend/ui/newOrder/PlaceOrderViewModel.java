package com.fullstack.frontend.ui.newOrder;

import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.NewOrder;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.base.BaseViewModel;

import retrofit2.Call;

public class PlaceOrderViewModel extends BaseViewModel<PlaceOrderRepository> {
    protected PlaceOrderViewModel(PlaceOrderRepository repository) {
        super(repository);
    }

    // TODO: Implement the ViewModel
    public void postOrder(OrderDetailRequest request){

     //   NewOrder order
        repository.getPlans(request);
    }
}
