package com.app.fr.fruiteefy.user_client.mywallet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.fr.fruiteefy.R;

public class FragmentMyWallet extends Fragment implements View.OnClickListener {
    private View mMainView;
    private TextView mTxtMyWallet, mTxtInvoice, mTxtBalanceSheet;
    private EditText citysearch;
    static FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_mywallet, container, false);
        setUI();

        setSelection();
        mTxtMyWallet.setBackgroundColor(Color.parseColor("#009639"));
        Fragment fragmentC = new MyWalletInforFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mywalletcontainer, fragmentC).commit();

        return mMainView;
    }

    //inflate all views from .xml
    private void setUI() {
        mTxtMyWallet = mMainView.findViewById(R.id.mywallet);
        mTxtInvoice = mMainView.findViewById(R.id.txtinvoice);
        citysearch=mMainView.findViewById(R.id.citysearch);
        mTxtBalanceSheet = mMainView.findViewById(R.id.balancesheet);
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        selectIntially();
        setOnclickListener();




    }

    private void setOnclickListener() {
        mTxtMyWallet.setOnClickListener(this);
        mTxtInvoice.setOnClickListener(this);
        mTxtBalanceSheet.setOnClickListener(this);


    }

    private void selectIntially() {
        mTxtMyWallet.setBackgroundColor(Color.parseColor("#009639"));
        mTxtInvoice.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
        mTxtBalanceSheet.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mywallet:
                setSelection();
                mTxtMyWallet.setBackgroundColor(Color.parseColor("#009639"));
                Fragment fragmentC = new MyWalletInforFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.mywalletcontainer, fragmentC).commit();

                break;

            case R.id.txtinvoice:
                setSelection();
                mTxtInvoice.setBackgroundColor(Color.parseColor("#009639"));

                Fragment fragmenti = new InvoiceFragment();
                FragmentTransaction transactionin = getChildFragmentManager().beginTransaction();
                transactionin.replace(R.id.mywalletcontainer, fragmenti).commit();
                break;
            case R.id.balancesheet:
                setSelection();
                Fragment fragment = new FragmentBalanceSheet();
                FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
                transaction1.replace(R.id.mywalletcontainer, fragment).commit();
                mTxtBalanceSheet.setBackgroundColor(Color.parseColor("#009639"));
                break;
        }
    }

    private void setSelection() {
        mTxtMyWallet.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
        mTxtInvoice.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
        mTxtBalanceSheet.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
    }


}
