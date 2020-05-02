package com.fullstack.frontend.ui.address;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.config.UserInfo;
import com.fullstack.frontend.ui.newOrder.EnhancedRadioGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fullstack.frontend.R;
import com.fullstack.frontend.databinding.FragmentModalAddressesListDialogListDialogBinding;

import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ModalAddressesListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ModalAddressesListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String USER_ID = "user_id";
    private  FragmentModalAddressesListDialogListDialogBinding binding;
    private SheetCallBack sheetCallBack;
    private AddressViewModel addressViewModel;

    // TODO: Customize parameters
    public static ModalAddressesListDialogFragment newInstance(int userID) {
        final ModalAddressesListDialogFragment fragment = new ModalAddressesListDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addressViewModel = getViewModel();
    }

    public interface SheetCallBack{
        void dismissSheet();//return the chosen address's view/id
    }

    public void setDialogCallBack(SheetCallBack sheetCallBack) {
        this.sheetCallBack = sheetCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentModalAddressesListDialogListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button = binding.saveAddress;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetCallBack.dismissSheet();
            }
        });

        EnhancedRadioGroup radioGroup = binding.addressGroup;
        radioGroup.setOnSelectionChanged(new EnhancedRadioGroup.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(RadioButton radioButton, int index) {
                binding.saveAddress.setEnabled(true);
            }
        });

        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
UserInfo.getInstance().setUserId(5);
        MutableLiveData<List<AddressResponse>> mutableLiveData = addressViewModel.postRequest(UserInfo.getInstance().getUserId());
        mutableLiveData.observe(requireActivity(), new Observer<List<AddressResponse>>() {
            @Override
            public void onChanged(List<AddressResponse> addressResponses) {
                binding.list.setAdapter(new ModalAddressesAdapter(addressResponses));
            }
        });

    }

    private AddressViewModel getViewModel() {
        return new ViewModelProvider(requireActivity(), getFactory()).get(AddressViewModel.class);
    }

    private AddressListRepository getRepository() {
        return new AddressListRepository();
    }

    private ViewModelProvider.Factory getFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AddressViewModel(getRepository());
            }
        };
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView first;
        final TextView last;
        final TextView address;
        final TextView cityState;
        final TextView zip;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_modal_addresses_list_dialog_list_dialog_item, parent, false));
            first = itemView.findViewById(R.id.first);
            last = itemView.findViewById(R.id.last);
            address = itemView.findViewById(R.id.add);
            cityState = itemView.findViewById(R.id.cityState);
            zip = itemView.findViewById(R.id.zip);
        }
    }

    private class ModalAddressesAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<AddressResponse> addresses;

        ModalAddressesAdapter(List<AddressResponse> addresses) {
            this.addresses = addresses;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            AddressResponse addressResponse = addresses.get(position);
            holder.first.setText(addressResponse.firstname);
            holder.last.setText(addressResponse.lastname);
            String address = String.format("%s, %s ",addressResponse.city,addressResponse.state);
            holder.cityState.setText(address);
            holder.address.setText(addressResponse.street);
            holder.zip.setText(String.valueOf(addressResponse.zipcode));
        }

        @Override
        public int getItemCount() {
            return addresses.size();
        }

    }

}
