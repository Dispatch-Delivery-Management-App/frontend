package com.fullstack.frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fullstack.frontend.ui.newOrder.NewOrderRecommend;

public class NewOrderActivity extends AppCompatActivity {
    private NewOrderRecommend dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        Button confirmButton = findViewById(R.id.button_orderConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecommendPlans();
            }
        });
    }

    private void showRecommendPlans() {
        dialog = NewOrderRecommend.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(android.R.id.content,dialog)
                .addToBackStack(null)
                .commit();
    }

}
