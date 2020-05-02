package com.fullstack.frontend.ui.newOrder;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseViewModel;
import com.fullstack.frontend.config.PackageCategory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PlaceOrderViewModel extends BaseViewModel<PlaceOrderRepository> {
    private final MutableLiveData<List<Plan>> exposedPlans = new MutableLiveData<>();

    protected PlaceOrderViewModel(PlaceOrderRepository repository) {
        super(repository);
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

    public String[] getCategories(){
        String[] categories = new String[PackageCategory.values().length];
        int index = 0;
        for (PackageCategory category:PackageCategory.values()){
            categories[index++] = category.toString();
        }
        return categories;
    }

}
