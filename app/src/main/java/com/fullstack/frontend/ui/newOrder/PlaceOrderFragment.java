package com.fullstack.frontend.ui.newOrder;



import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;
import com.fullstack.frontend.base.BaseFragment;
import com.fullstack.frontend.config.UserInfo;
import com.fullstack.frontend.databinding.AddressFormBinding;
import com.fullstack.frontend.databinding.PlaceOrderFragmentBinding;
import com.fullstack.frontend.ui.address.AddressViewModel;
import com.fullstack.frontend.ui.address.ModalAddressesListDialogFragment;

import java.util.List;

public class PlaceOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener, ModalAddressesListDialogFragment.SheetCallBack {
    private PlaceOrderFragmentBinding binding;
    private ModalAddressesListDialogFragment modalAddressesListDialogFragment;
    private AddressResponse addressResponse;
    private PlaceOrderViewModel viewModel;
    public PlaceOrderFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
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

        Button fromAddressBookButton = binding.fromAddForm.chooseAddressButton;
        Button toAddressBookButton = binding.toAddForm.chooseAddressButton;

        fromAddressBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromAddressBookButton.getText().equals(getResources().getString(R.string.myAddressesButton))) {
                    viewModel.setFormBinding(binding.fromAddForm);
                    viewModel.setCurForm(0);
                    modalAddressesListDialogFragment = ModalAddressesListDialogFragment.newInstance();
                    modalAddressesListDialogFragment.setDialogCallBack((ModalAddressesListDialogFragment.SheetCallBack) getParentFragmentManager().getFragments().get(0));
                    modalAddressesListDialogFragment.show((requireActivity()).getSupportFragmentManager(), "dialog");
                }else{
                    fromAddressBookButton.setText(R.string.myAddressesButton);
                    toggleForm(binding.fromAddForm);
                    viewModel.getRequest().fromAddress.addr_id=0;
                }

            }
        });

        toAddressBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toAddressBookButton.getText().equals(getResources().getString(R.string.myAddressesButton))) {
                    viewModel.setFormBinding(binding.toAddForm);
                    viewModel.setCurForm(1);
                    modalAddressesListDialogFragment = ModalAddressesListDialogFragment.newInstance();
                    modalAddressesListDialogFragment.setDialogCallBack((ModalAddressesListDialogFragment.SheetCallBack) getParentFragmentManager().getFragments().get(0));
                    modalAddressesListDialogFragment.show((requireActivity()).getSupportFragmentManager(), "dialog");
                }else{
                    toAddressBookButton.setText(R.string.myAddressesButton);
                    toggleForm(binding.fromAddForm);
                    viewModel.getRequest().fromAddress.addr_id=0;
                }

            }
        });

        //Next Step Button
        Button confirmButton = binding.buttonShowRecommend;
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setEnabled(false);
                // NewOrder order
                GetPlansRequest request = viewModel.getRequest();
                boolean dataValid = setPlaceOrderInfo(request);
                request.user_id= UserInfo.getInstance().getUserId();

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
                        confirmButton.setEnabled(true);
                        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(action);
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
        if(!validateUserData(binding.fromAddForm.fromFirst,"please enter first name"))
            return false;
        request.fromAddress.lastname = binding.fromAddForm.fromLast.getText().toString();
        if(!validateUserData(binding.fromAddForm.fromLast,"please enter last name"))
            return false;
        request.fromAddress.street = binding.fromAddForm.fromAdd1.getText().toString()+ binding.fromAddForm.fromAdd2.getText().toString();
        if(!validateUserData(binding.fromAddForm.fromAdd1,"please enter your address"))
            return false;
        request.fromAddress.city = binding.fromAddForm.fromCity.getText().toString();
        if(!validateUserData(binding.fromAddForm.fromCity,"please enter your city"))
            return false;
        request.fromAddress.state = binding.fromAddForm.fromState.getText().toString();
        if(!validateUserData(binding.fromAddForm.fromState,"please enter your state"))
            return false;
        request.fromAddress.zipcode = isInteger(binding.fromAddForm.fromZIP.getText().toString())? Integer.parseInt(binding.fromAddForm.fromZIP.getText().toString()):0;

        request.toAddress.firstname = binding.toAddForm.fromFirst.getText().toString();
        if(!validateUserData(binding.toAddForm.fromFirst,"please enter first name"))
            return false;
        request.toAddress.lastname = binding.toAddForm.fromLast.getText().toString();
        if(!validateUserData(binding.toAddForm.fromLast,"please enter last name"))
            return false;
        request.toAddress.street = binding.toAddForm.fromAdd1.getText().toString()+ binding.fromAddForm.fromAdd2.getText().toString();
        if(!validateUserData(binding.toAddForm.fromAdd1,"please enter your address"))
            return false;
        request.toAddress.city = binding.toAddForm.fromCity.getText().toString();
        if(!validateUserData(binding.toAddForm.fromCity,"please enter your city"))
            return false;
        request.toAddress.state = binding.toAddForm.fromState.getText().toString();
        if(!validateUserData(binding.toAddForm.fromState,"please enter your state"))
            return false;
        request.toAddress.zipcode = isInteger(binding.toAddForm.fromZIP.getText().toString())? Integer.parseInt(binding.toAddForm.fromZIP.getText().toString()):0;

        request.packageCategory = binding.packType.getSelectedItem().toString();
        request.packageWeight = isDouble(binding.packWeight.getText().toString());
        if(!validateUserData(binding.packWeight,"please enter an estimated weight"))
            return false;
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


    protected PlaceOrderViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(PlaceOrderViewModel.class);
    }



    protected PlaceOrderRepository getRepository() {
        return new PlaceOrderRepository();
    }


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

    @Override
    public AddressResponse getAddressResponse(){
        return this.addressResponse;
    }

    @Override
    public void dismissSheet() {
        modalAddressesListDialogFragment.dismiss();
        inflateForm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void inflateForm() {
        AddressFormBinding formBinding = this.viewModel.getFormBinding();
        AddressResponse response = this.viewModel.getAddressResponse();
        formBinding.fromFirst.setText(response.firstname);
        formBinding.fromLast.setText(response.lastname);
        formBinding.fromAdd1.setText(response.street);
        formBinding.fromCity.setText(response.city);
        formBinding.fromState.setText(response.state);
        formBinding.fromZIP.setText(String.valueOf(response.zipcode));
        formBinding.chooseAddressButton.setText(R.string.editAdd);
        toggleForm(formBinding);
        GetPlansRequest request = viewModel.getRequest();
        if (viewModel.getCurForm()==0){
            request.fromAddress.addr_id=response.address_id;
        }else {
            request.toAddress.addr_id=response.address_id;
        }
    }

    private void toggleForm(AddressFormBinding formBinding){

        formBinding.fromFirst.setEnabled(!formBinding.fromFirst.isEnabled());
        formBinding.fromLast.setEnabled(!formBinding.fromLast.isEnabled());
        formBinding.fromAdd1.setEnabled(!formBinding.fromAdd1.isEnabled());
        formBinding.fromCity.setEnabled(!formBinding.fromCity.isEnabled());
        formBinding.fromState.setEnabled(!formBinding.fromState.isEnabled());
        formBinding.fromZIP.setEnabled(!formBinding.fromZIP.isEnabled());

    }

    @Override
    public PlaceOrderViewModel getParentViewModel(){
        return this.viewModel;
    }

}
