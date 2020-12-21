package com.app.fr.fruiteefy.user_client.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.fr.fruiteefy.R;

public class MyCartActivity extends AppCompatActivity {
    static FragmentManager fragmentManager;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        fragmentManager = getSupportFragmentManager();
        initView();
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView() {
        setTitle(getResources().getString(R.string.my_cart));
        fragmentManager = getSupportFragmentManager();
        mContext = MyCartActivity.this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.containercart, new MyCartFragment()).commit();


    }//initViewClose


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public static class MyClickEvent {
        void onClickMethod() {
            fragmentManager.beginTransaction().replace(R.id.container, new FragmentOrderSummary()).commit();
        }

    }
}
