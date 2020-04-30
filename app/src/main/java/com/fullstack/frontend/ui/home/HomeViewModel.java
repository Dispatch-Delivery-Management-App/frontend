package com.fullstack.frontend.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderListRequest;
import com.fullstack.frontend.Retro.OrderResponse;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final OrderListRepository orderListRepository;

    private MutableLiveData<OrderListRequest> userIdInput = new MutableLiveData<>();

    public HomeViewModel(OrderListRepository orderListRepository) {
        this.orderListRepository = orderListRepository;
    }

    public void setOrderRequest(OrderListRequest request) {
        userIdInput.setValue(request);
    }

    public LiveData<List<OrderResponse>> getOrders() {
        return Transformations.switchMap(userIdInput, orderListRepository::getOrders);
    }
}