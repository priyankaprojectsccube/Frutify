package com.app.fr.fruiteefy.user_client.mywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.fr.fruiteefy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WalletActivity extends AppCompatActivity {

    ImageView backimg;
    BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet2);

      //  backimg=findViewById(R.id.backimg);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        backimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyWalletInforFragment()).commit();

      //  getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new FragmentMyWallet()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;


            switch (item.getItemId()) {
                case R.id.nav_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyWalletInforFragment()).commit();
                    return true;
                case R.id.nav_map:

                    getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new InvoiceFragment()).commit();
                    return true;
                case R.id.nav_purchase:

                    getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new FragmentBalanceSheet()).commit();
                    return true;

            }
            return false;
        }
    };

}
