package com.fullstack.frontend.onboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fullstack.frontend.onboard.login.LoginFragment;
import com.fullstack.frontend.onboard.register.RegisterFragment;

public class OnBoardingPageAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;

    public OnBoardingPageAdapter( FragmentManager fragmentManager ) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //return LoginFragment.newInstance();
            case 1:
                //return RegisterFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}
