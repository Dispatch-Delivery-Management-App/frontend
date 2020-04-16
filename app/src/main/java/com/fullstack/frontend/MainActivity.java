package com.fullstack.frontend;

import android.os.Bundle;
import android.view.Menu;

import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ApiInterface apiInterface;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //
        setupNavigation();

    }

//    /**
//     ** Retrofit Login
//     */
//    public void getTodos(View view) {
//
//        Call<List<ResModel>> call = apiInterface.getTodos();
//        ((Call) call).enqueue(new Callback<List<ResModel>>() {
//            @Override
//            public void onResponse(Call<List<ResModel>> call, Response<List<ResModel>> response) {
//                Log.e(TAG, "onResponse: " +  response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<ResModel>> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
//            }
//        });
//
//    }

    /**
     * setupNavigation
     *
     */
    private void setupNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);//side bar
        NavigationView navigationView = findViewById(R.id.nav_view);//side bar menu
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_addresses)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);  // Hostfragment
        Fragment fragment = navHost.getChildFragmentManager().getPrimaryNavigationFragment();
        if (fragment != null && fragment.isResumed()) {
            if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                fragment.getChildFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
