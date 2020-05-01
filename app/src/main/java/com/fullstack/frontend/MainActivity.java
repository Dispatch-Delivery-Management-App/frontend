package com.fullstack.frontend;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.ashokvarma.gander.Gander;
import com.ashokvarma.gander.imdb.GanderIMDB;
import com.ashokvarma.gander.persistence.GanderPersistence;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;

import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.TokenRequest;
import com.fullstack.frontend.ui.address.ManageAddressFragment;
import com.fullstack.frontend.ui.home.HomeFragment;
import com.fullstack.frontend.ui.notification.MyFirebaseMessagingService;

import com.google.android.gms.tasks.OnCompleteListener;

import com.fullstack.frontend.Retro.MyApp;

import com.google.android.gms.tasks.OnSuccessListener;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupNavigation();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.d("test", instanceIdResult.getToken());
            }
        });


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
     */
    private void setupNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        drawer = findViewById(R.id.drawer_layout);//side bar
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


    public void triggerDrawer(boolean enable) {
        int mode = enable ? LOCK_MODE_UNLOCKED : LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(mode);
    }

    public void triggerTitleBar(boolean enable){
        ActionBar actionBar = getSupportActionBar();
        if (enable){
            actionBar.show();
        }else {
            actionBar.hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
                if (fragment instanceof HomeFragment || fragment instanceof ManageAddressFragment) {
                    drawer.openDrawer(GravityCompat.START);
                } else  {
                    onBackPressed();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
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








