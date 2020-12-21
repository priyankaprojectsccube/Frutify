package com.app.fr.fruiteefy.user_client.mywallet;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;


public class InvoiceFragment extends Fragment {


    private TextView purchase,sale;
    private Activity activity;
    ImageView backimg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_invoice, container, false);
        activity=(WalletActivity)getActivity();
        backimg=activity.findViewById(R.id.backimg);
        purchase=view.findViewById(R.id.purchase);
        sale=view.findViewById(R.id.sale);

        purchase.setBackgroundColor(Color.parseColor("#009639"));
        sale.setBackgroundColor(Color.parseColor("#a2a9a9a9"));


        getChildFragmentManager().beginTransaction().replace(R.id.containerfrmlay, new PurchaseInvoiceFragment()).commit();
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyWalletInforFragment()).commit();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                purchase.setBackgroundColor(Color.parseColor("#009639"));
                sale.setBackgroundColor(Color.parseColor("#a2a9a9a9"));

                getChildFragmentManager().beginTransaction().replace(R.id.containerfrmlay, new PurchaseInvoiceFragment()).commit();

            }
        });



        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sale.setBackgroundColor(Color.parseColor("#009639"));
                purchase.setBackgroundColor(Color.parseColor("#a2a9a9a9"));

                getChildFragmentManager().beginTransaction().replace(R.id.containerfrmlay, new SaleInvoiceFragment()).commit();

            }
        });



        return view;
    }


}
