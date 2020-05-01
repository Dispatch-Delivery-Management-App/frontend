package com.fullstack.frontend.ui.address;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.newOrder.AddressListRequest;
import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.base.BaseViewModel;

import java.util.List;

public class AddressViewModel extends BaseViewModel<AddressListRepository> {

    private MutableLiveData<List<AddressResponse>> addresses;

    protected AddressViewModel(AddressListRepository baseRepository) {
        super(baseRepository);
    }

    public void getOrders(AddressListRequest request) {
        repository.getAddresses(request,addresses);
    }
    public MutableLiveData<List<AddressResponse>> getAddresses(){
        return addresses;
    }
}