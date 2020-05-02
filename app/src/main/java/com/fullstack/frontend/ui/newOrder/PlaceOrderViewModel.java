package com.fullstack.frontend.ui.newOrder;

import android.os.Build;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.config.PackageCategory;
import com.fullstack.frontend.databinding.AddressFormBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PlaceOrderViewModel extends BaseViewModel<PlaceOrderRepository> {
    private final MutableLiveData<List<Plan>> exposedPlans = new MutableLiveData<>();
    private AddressResponse addressResponse;
    private AddressFormBinding formBinding;
    private int curForm;
    private  GetPlansRequest request;
    protected PlaceOrderViewModel(PlaceOrderRepository repository) {
        super(repository);
        request = new GetPlansRequest();
    }

    public int getCurForm() {//0:from,1:to
        return curForm;
    }

    public void setCurForm(int curForm) {
        this.curForm = curForm;
    }

    public GetPlansRequest getRequest() {
        return request;
    }

    // TODO: Implement the ViewModel
    public void postOrder(GetPlansRequest request){
         repository.getPlans(request,exposedPlans);
    }

    public MutableLiveData<List<Plan>> getReturnedPlans(){
        return exposedPlans;
         }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getDate(){
        LocalDateTime today =  LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);
        LocalDateTime dayAfterTomorrow = today.plusDays(2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return new String[]{dateTimeFormatter.format(tomorrow),dateTimeFormatter.format(dayAfterTomorrow)};
    }

    public String[] getSlots(){
        return new String[]{"9:00-11:00","11:00-13:00","13:00-15:00","15:00-17:00"};
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }


    public void setAddressResponse(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }

    public String[] getCategories(){
        String[] categories = new String[PackageCategory.values().length];
        int index = 0;
        for (PackageCategory category:PackageCategory.values()){
            categories[index++] = category.toString();
        }
        return categories;
    }

    public AddressFormBinding getFormBinding() {
        return formBinding;
    }

    public void setFormBinding(AddressFormBinding formBinding) {
        this.formBinding = formBinding;
    }

}
