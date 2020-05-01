package com.fullstack.frontend.ui.newOrder;



import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.ashokvarma.gander.persistence.GanderPersistence;
import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.GetPlansRequest;
import com.fullstack.frontend.Retro.MyApp;
import com.fullstack.frontend.Retro.Plan;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.config.UserInfo;
import com.fullstack.frontend.databinding.PlaceOrderFragmentBinding;

import java.util.List;

public class PlaceOrderFragment extends BaseFragment<PlaceOrderViewModel, PlaceOrderRepository> implements AdapterView.OnItemSelectedListener {
    private PlaceOrderFragmentBinding binding;

    public PlaceOrderFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static PlaceOrderFragment newInstance() {
        return new PlaceOrderFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PlaceOrderFragmentBinding.inflate(inflater, container, false);

        Spinner pickupDay = binding.pickupDay;
        String[] dateArray = viewModel.getDate();
        this.inflateSpinner(pickupDay,dateArray);

        Spinner packType = binding.packType;
        String[] typeArray = viewModel.getCategories();
        this.inflateSpinner(packType,typeArray);

        Spinner pickupSlot = binding.pickupSlot;
        String[] slotArray = viewModel.getSlots();
        this.inflateSpinner(pickupSlot,slotArray);

        return binding.getRoot();
    }

    private void inflateSpinner(Spinner spinner,String[] array){
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

                // NewOrder order
                GetPlansRequest request = new GetPlansRequest();
                boolean dataValid = setPlaceOrderInfo(request);
                request.user_id= UserInfo.getUser_id();
    request.order_status=2;
    request.fromAddress.street="1000 W Maude Ave";
    request.fromAddress.city="Sunnyvale";
    request.fromAddress.state="CA";
    request.toAddress.street="1200 Getty Center Dr";
    request.toAddress.city="Los Angeles";
    request.toAddress.state="CA";
    request.item_info="Linggg's order";
    request.packageWeight=8.0;
                if (!dataValid){
                    return;
                }

                viewModel.postOrder(request);

                MutableLiveData<List<Plan>> planLiveData = viewModel.getReturnedPlans();
                planLiveData.observe(requireActivity(), new Observer<List<Plan>>() {
                    @Override
                    public void onChanged(List<Plan> plans) {
                        Plan[] plans1 = PlaceOrderFragment.this.convert(plans);

                        PlaceOrderFragmentDirections.PlaceToRecommend action = PlaceOrderFragmentDirections.placeToRecommend();
                        action.setReturnedPlans(plans1);
                        action.setReturnedRequest(request);

                        Navigation.findNavController(v).navigate(action);
                    }
                });



            }
        });

    }

    private Plan[] convert(List<Plan> ps){
        Plan[] plans = new Plan[ps.size()];
        for (int i=0;i<ps.size();i++){
            plans[i]=ps.get(i);
        }
        return plans;
    }

    private boolean setPlaceOrderInfo(GetPlansRequest request) {
        request.item_info = binding.itemInfo.getText().toString();

        request.fromAddress.firstname = binding.fromAddForm.fromFirst.getText().toString();
//        if(!validateUserData(binding.fromAddForm.fromFirst,"please enter first name"))
//            return false;
        request.fromAddress.lastname = binding.fromAddForm.fromLast.getText().toString();
//        if(!validateUserData(binding.fromAddForm.fromLast,"please enter last name"))
//            return false;
        request.fromAddress.street = binding.fromAddForm.fromAdd1.getText().toString()+ binding.fromAddForm.fromAdd2.getText().toString();
        if(!validateUserData(binding.fromAddForm.fromLast,"please enter your address"))
            return false;
        request.fromAddress.city = binding.fromAddForm.fromCity.getText().toString();
        request.fromAddress.state = binding.fromAddForm.fromState.getText().toString();
        request.fromAddress.zipcode = isInteger(binding.fromAddForm.fromZIP.getText().toString())? Integer.parseInt(binding.fromAddForm.fromZIP.getText().toString()):0;

        request.toAddress.firstname = binding.toAddForm.fromFirst.getText().toString();
        request.toAddress.lastname = binding.toAddForm.fromLast.getText().toString();
        request.toAddress.street = binding.toAddForm.fromAdd1.getText().toString()+ binding.fromAddForm.fromAdd2.getText().toString();
        request.toAddress.city = binding.toAddForm.fromCity.getText().toString();
        request.toAddress.state = binding.toAddForm.fromState.getText().toString();
        request.toAddress.zipcode = isInteger(binding.toAddForm.fromZIP.getText().toString())? Integer.parseInt(binding.toAddForm.fromZIP.getText().toString()):0;

        request.packageCategory = binding.packType.getSelectedItem().toString();
        request.packageWeight = isDouble(binding.packWeight.getText().toString());
        request.MMDD = binding.pickupDay.getSelectedItem().toString();
        request.startSlot = binding.pickupSlot.getSelectedItem().toString();

        return true;
    }

    private boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);
            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
        }
        return isValidInteger;
    }

    private Double isDouble(String s) {
        Double valid;
        try
        {
            valid = Double.parseDouble(s);
        }
        catch (NumberFormatException ex)
        {
            valid = 0.0;
        }
        return valid;
    }

    /**
     * ValidateUserData
     */
    private boolean validateUserData(EditText view, String errorMessage) {
        String userInput = view.getText().toString();
        if (!TextUtils.isEmpty(userInput)) {
            return true;
        }
        view.setError(errorMessage);
        view.requestFocus();
        return false;
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
