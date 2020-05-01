package com.fullstack.frontend.ui.newOrder;

import androidx.lifecycle.MutableLiveData;

import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseViewModel;


public class NewOrderRecommendViewModel extends BaseViewModel<ConfirmOrderRepository> {
    private Plan best;
    private Plan cheap;
    private Plan fast;
    private MutableLiveData<Integer> order_id = new MutableLiveData<>();

    protected NewOrderRecommendViewModel(ConfirmOrderRepository baseRepository) {
        super(baseRepository);
    }

    public void confirmOrder(GetPlansRequest request){
        repository.confirmOrder(request,order_id);
    }

    public MutableLiveData<Integer> getOrder_id() {
        return order_id;
    }

    public void setBest(Plan best) {
        this.best = best;
    }

    public void setCheap(Plan cheap) {
        this.cheap = cheap;
    }

    public Plan getBest() {
        return best;
    }

    public Plan getCheap() {
        return cheap;
    }

    public Plan getFast() {
        return fast;
    }

    public void setFast(Plan fast) {
        this.fast = fast;
    }
}
