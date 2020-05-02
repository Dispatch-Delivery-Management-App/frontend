package com.fullstack.frontend.ui.address;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullstack.frontend.Retro.newOrder.AddressListRequest;
import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.base.BaseViewModel;

import java.util.List;

public class AddressViewModel extends BaseViewModel<AddressListRepository> {

    protected AddressViewModel(AddressListRepository baseRepository) {
        super(baseRepository);
    }

    public MutableLiveData<List<AddressResponse>> postRequest(int userID) {
        AddressListRequest request = new AddressListRequest();
        request.user_id = userID;
        MutableLiveData<List<AddressResponse>> addresses = repository.getAddresses(request);
        return addresses;
    }



}