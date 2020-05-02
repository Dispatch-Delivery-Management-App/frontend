package com.fullstack.frontend.onboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullstack.frontend.MainActivity;
import com.fullstack.frontend.R;
import com.fullstack.frontend.databinding.FragmentOnBoardingBinding;
import com.fullstack.frontend.onboard.login.LoginFragment;
import com.fullstack.frontend.onboard.register.RegisterFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingFragment extends Fragment {

    FragmentOnBoardingBinding binding;

    class OnBoardingPageAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> onBoardingFragmentList = new ArrayList<>();

        public OnBoardingPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return onBoardingFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return onBoardingFragmentList.size();
        }

        void addOnBoardingFragment(Fragment fragment) {
            onBoardingFragmentList.add(fragment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        OnBoardingPageAdapter pageAdapter = new OnBoardingPageAdapter(getChildFragmentManager());
        pageAdapter.addOnBoardingFragment(new LoginFragment());
        pageAdapter.addOnBoardingFragment(new RegisterFragment());
        viewPager.setAdapter(pageAdapter);
        ((MainActivity) getActivity()).triggerDrawer(false);
        ((MainActivity) getActivity()).triggerTitleBar(false);
        return view;
    }
}
