//package com.example.hp_pc.metinpot.user_client.mywallet.walletfragment;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.hp_pc.metinpot.R;
//import com.example.hp_pc.metinpot.Util.BaseUrl;
//import com.example.hp_pc.metinpot.Util.PrefManager;
//import com.example.hp_pc.metinpot.user_client.mywallet.adapter.AdapterSpinnerBankList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class FragmentTransfer extends Fragment {
//    private View mMainView;
//    private TextView mTxtBalanceAvailable, mTxtTransionCost, mTxtAmountTransfer, mTxtBalanceAfter;
//    private EditText mEdtGetTransferAmount;
//    private Spinner mSpnBankAccount;
//    private LinearLayout mLytBtnValidate;
//    private ArrayList<HashMap<String, String>> mArrBankList;
//    private String balance;
//    long delay = 1000; // 1 seconds after user stops typing
//    long last_text_edit = 0;
//    Handler handler = new Handler();
//
//    private Runnable input_finish_checker = new Runnable() {
//        public void run() {
//            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
//
//
//
//
//            }
//        }
//    };
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mMainView = inflater.inflate(R.layout.layout_tranferfragment, container, false);
//        setUI();
//        return mMainView;
//    }
//
//    private void setUI() {
//        mArrBankList = new ArrayList<>();
//        mTxtBalanceAvailable = mMainView.findViewById(R.id.tv_availableamont);
//        mTxtTransionCost = mMainView.findViewById(R.id.transactioncost);
//        mTxtAmountTransfer = mMainView.findViewById(R.id.netamounttotransfer);
//        mTxtBalanceAfter = mMainView.findViewById(R.id.balanceafter);
//        mLytBtnValidate = mMainView.findViewById(R.id.btnvalidate);
//        mEdtGetTransferAmount = mMainView.findViewById(R.id.amounttotransfer);
//        mSpnBankAccount = mMainView.findViewById(R.id.spinnerbank);
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            balance = bundle.getString("Amount");
//        }
//        mTxtBalanceAvailable.setText("" + balance);
//
//        mEdtGetTransferAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//               // (2/100*$enter_amt)+0.2;
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////You need to remove this to run only once
//                handler.removeCallbacks(input_finish_checker);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //avoid triggering event when text is empty
//                if (s.length() > 0) {
//                    last_text_edit = System.currentTimeMillis();
//                    handler.postDelayed(input_finish_checker, delay);
//                } else {
//
//                }
//            }
//        });
//
//        setPreviousData();
//        wsGetBankList();
//    }
//
//    private void wsGetBankList() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("bank_acc_list"), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mArrBankList.clear();
//                Log.d("sdsadd", response);
//                try {
//                    // myCartModelList.clear();
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("status").equals("1")) {
//                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
//                        for (int i = 0; i < mJsonArr.length(); i++) {
//                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
//                            HashMap<String, String> mhash = new HashMap<>();
//
//                            if(mJsonObj.getString("status").equals("ACTIVE")) {
//                                mhash.put("Id", mJsonObj.getString("Id"));
//                                mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
//                                mhash.put("account_no", mJsonObj.getString("account_no"));
//                                mhash.put("status", mJsonObj.getString("status"));
//                                mArrBankList.add(mhash);
//                            }
//
//                        }
//                        setAdapterSpinner();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                /// Log.d("sdsadd", error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("user_id", PrefManager.getUserId(getActivity()));
//                //// Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
//                return param;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//    }
//
//    private void setAdapterSpinner() {
//        mSpnBankAccount.setAdapter(new AdapterSpinnerBankList(mArrBankList, getActivity()));
//
//        mSpnBankAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }
//
//    private void setPreviousData() {
//    }
//}



package com.app.fr.fruiteefy.user_client.mywallet.walletfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.mywallet.WalletActivity;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.AdapterSpinnerBankList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentTransfer extends Fragment {
    private View mMainView;
    private TextView mTxtBalanceAvailable, mTxtTransionCost, mTxtAmountTransfer, mTxtBalanceAfter;
    private EditText mEdtGetTransferAmount;
    private Spinner mSpnBankAccount;
    private LinearLayout mLytBtnValidate;
    private ArrayList<HashMap<String, String>> mArrBankList;
    private String balance;
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                // DoStuff();

                if (!mEdtGetTransferAmount.getText().toString().equals("")) {
                    double amount = Double.parseDouble(mEdtGetTransferAmount.getText().toString());
                    double res = (amount / 100.0f) * 2;
                    double v = res + 0.2;
                    //  Toast.makeText(getActivity(), "" + mEdtGetTransferAmount.getText().toString(), Toast.LENGTH_SHORT).show();


                    mTxtTransionCost.setText("" +Math.floor(v*100)/100+" ");

                    double amounttobetrasnfer = Double.parseDouble(mEdtGetTransferAmount.getText().toString()) - v;
                    mTxtAmountTransfer.setText("" +Math.floor(amounttobetrasnfer*100)/100+" ");
                    double balafter = Double.parseDouble(balance) - Double.parseDouble(mEdtGetTransferAmount.getText().toString());

                    mTxtBalanceAfter.setText("" + Math.floor(balafter*100)/100+" ");

                    //    Toast.makeText(getApplicationContext(), "" + res, Toast.LENGTH_SHORT).show();
                } else {
                    //  Toast.makeText(getApplicationContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show();

                }


            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_tranferfragment, container, false);
        setUI();
        return mMainView;
    }

    private void setUI() {
        mArrBankList = new ArrayList<>();
        mTxtBalanceAvailable = mMainView.findViewById(R.id.tv_availableamont);
        mTxtTransionCost = mMainView.findViewById(R.id.transactioncost);
        mTxtAmountTransfer = mMainView.findViewById(R.id.netamounttotransfer);
        mTxtBalanceAfter = mMainView.findViewById(R.id.balanceafter);
        mLytBtnValidate = mMainView.findViewById(R.id.btnvalidate);
        mEdtGetTransferAmount = mMainView.findViewById(R.id.amounttotransfer);
        mSpnBankAccount = mMainView.findViewById(R.id.spinnerbank);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            balance = bundle.getString("Amount");
        }
        mTxtBalanceAvailable.setText("" + balance+" ");

        mEdtGetTransferAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//You need to remove this to run only once
                handler.removeCallbacks(input_finish_checker);

                }


            @Override
            public void afterTextChanged(Editable s) {
                //avoid triggering event when text is empty
                if (s.length() > 0) {

                    Double price=Double.parseDouble(s.toString());
                    Double balanceavailable=Double.parseDouble(mTxtBalanceAvailable.getText().toString());
                    if(price>balanceavailable) {
                        Toast.makeText(getContext(), getResources().getString(R.string.transferamountcannotbegreaterthanavailableamount), Toast.LENGTH_SHORT).show();

                    }
                    else {


                        last_text_edit = System.currentTimeMillis();
                        handler.postDelayed(input_finish_checker, delay);
                    }
                } else {

                }
            }
        });

        setPreviousData();
        wsGetBankList();
        mLytBtnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wsSaveCardData();
            }
        });

    }

    private void wsSaveCardData() {
        CustomUtil.ShowDialog(getActivity(),false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("transfer_wallet_to_bank"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // mArrBankList.clear();
                CustomUtil.DismissDialog(getActivity());
                 Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                        Intent intent=new Intent(getActivity(), WalletActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

//                    if (jsonObject.getString("status").equals("1")) {
//                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
//                        for (int i = 0; i < mJsonArr.length(); i++) {
//                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
//                            HashMap<String, String> mhash = new HashMap<>();
//                            mhash.put("Id", mJsonObj.getString("Id"));
//                            mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
//                            mhash.put("account_no", mJsonObj.getString("account_no"));
//                            mhash.put("status", mJsonObj.getString("status"));
//                            mhash.put("Type", mJsonObj.getString("Type"));
//                            mArrBankList.add(mhash);
//                        }
//                        setAdapterSpinner();
                    //  }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());

                CustomUtil.DismissDialog(getActivity());


                NetworkResponse networkResponse=error.networkResponse;

                Log.d("szddsa", String.valueOf(networkResponse.data));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                param.put("select_bank_account", mArrBankList.get(mSpnBankAccount.getSelectedItemPosition()).get("Id"));
                param.put("text_amount_to_transfer", mTxtAmountTransfer.getText().toString());
                param.put("commission_for_payout", mTxtTransionCost.getText().toString());

                Log.d("ddfdsfd",PrefManager.getUserId(getActivity())+" "+mArrBankList.get(mSpnBankAccount.getSelectedItemPosition()).get("Id"));

                Log.d("ddfdsfd",mTxtAmountTransfer.getText().toString()+" "+mTxtTransionCost.getText().toString());

                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void wsGetBankList() {

        CustomUtil.ShowDialog(getActivity(),false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("bank_acc_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mArrBankList.clear();
                CustomUtil.DismissDialog(getActivity());
                // Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                            HashMap<String, String> mhash = new HashMap<>();
//                            mhash.put("Id", mJsonObj.getString("Id"));
//                            mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
//                            mhash.put("account_no", mJsonObj.getString("account_no"));
//                            mhash.put("status", mJsonObj.getString("status"));
//                            mhash.put("Type", mJsonObj.getString("Type"));
//                            mArrBankList.add(mhash);

                  if(mJsonObj.getString("status").equals("ACTIVE")) {
                                mhash.put("Id", mJsonObj.getString("Id"));
                                mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
                                mhash.put("account_no", mJsonObj.getString("account_no"));
                                mhash.put("status", mJsonObj.getString("status"));
                      mhash.put("Type", mJsonObj.getString("Type"));
                                mArrBankList.add(mhash);
                            }
                        }
                        setAdapterSpinner();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /// Log.d("sdsadd", error.toString());
                CustomUtil.DismissDialog(getActivity());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                //// Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setAdapterSpinner() {
        mSpnBankAccount.setAdapter(new AdapterSpinnerBankList(mArrBankList, getActivity()));

        mSpnBankAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setPreviousData() {
    }
}
