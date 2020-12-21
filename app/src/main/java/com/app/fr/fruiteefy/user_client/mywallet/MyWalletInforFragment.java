package com.app.fr.fruiteefy.user_client.mywallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.home.HomeFragment;
import com.app.fr.fruiteefy.user_client.home.MyOrderalletFragment;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.KYCDetailsAdapter;
import com.app.fr.fruiteefy.user_client.mywallet.walletfragment.FragmentTransfer;
import com.app.fr.fruiteefy.user_client.mywallet.walletfragment.KYCFragment;
import com.app.fr.fruiteefy.user_picorear.PicoOrderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyWalletInforFragment extends Fragment implements View.OnClickListener {
    private View mMainView;
    private String availableamount="";
    private LinearLayout linlayaddnewcard;
    private TextView mTxtBalance, mTxtMyInfo,amounttobereceived,myorder,mysale;
    private LinearLayout mLytBalance, mLytMyInformation, mLytBtnToTransfer;
    private RecyclerView mRecylerCardList,recyclerkyc;
    private TextView mTxtAccountHolderName, mTxtDateOfBirth, mTxtbillingAddress, mTxtCountry, mTxtCity, mTxtPostalCode, mTxtFullName;
    private ArrayList<HashMap<String, String>> mArrayCardList, mArrBankList,mArrBankListcount,kcdetailslist;
    private RecyclerView mRecyclerBankList;
    private TextView mTxtPendingAmount, mTxtAvailableAmount;
    private LinearLayout mLytAddBank;
    private ImageView filtericon;
    ImageView backimg;
    private Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_walletinfo, container, false);
        activity=(WalletActivity)getActivity();
        backimg=activity.findViewById(R.id.backimg);
        setUI();

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return mMainView;
    }

    private void wsGetKYCStatus() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("kyc_status"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("kyc_status ",response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("0")) {
                        Fragment fragment = new KYCFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, fragment).commit();
                    }else{
                        callapi();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());

                NetworkResponse networkResponse=error.networkResponse;
                String s=new String(networkResponse.data);

                Log.d("sdsadd",s);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                 Log.d("kycuserid", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setUI() {
        kcdetailslist = new ArrayList<>();
        mArrayCardList = new ArrayList<>();
        mArrBankList = new ArrayList<>();
        mArrBankListcount = new ArrayList<>();
        mLytAddBank = mMainView.findViewById(R.id.lytaddbank);
        amounttobereceived=mMainView.findViewById(R.id.amounttobereceived);
        myorder=mMainView.findViewById(R.id.myorder);
        linlayaddnewcard = mMainView.findViewById(R.id.linlayaddnewcard);
        mLytBtnToTransfer = mMainView.findViewById(R.id.btntotransfer);
        mTxtPendingAmount = mMainView.findViewById(R.id.tv_pendingamount);
        mTxtAvailableAmount = mMainView.findViewById(R.id.tv_availableamont);
        mysale=mMainView.findViewById(R.id.mysale);
        mTxtBalance = mMainView.findViewById(R.id.balance);
        mTxtMyInfo = mMainView.findViewById(R.id.myinformation);
        mLytBalance = mMainView.findViewById(R.id.balancelyt);
        filtericon = getActivity().findViewById(R.id.filtericon);
        mLytMyInformation = mMainView.findViewById(R.id.lytmyinfo);
        recyclerkyc = mMainView.findViewById(R.id.recyclerkyc);
        mRecylerCardList = mMainView.findViewById(R.id.recyclerpaymentmethod);
        mRecyclerBankList = mMainView.findViewById(R.id.recylertransferoption);

        filtericon.setVisibility(View.GONE);

        mRecyclerBankList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerBankList.getContext(),
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false).getOrientation());
        mRecyclerBankList.addItemDecoration(dividerItemDecoration);

        mRecylerCardList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(mRecyclerBankList.getContext(),
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false).getOrientation());
        mRecylerCardList.addItemDecoration(dividerItemDecoration1);

        recyclerkyc.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerkyc.getContext(),
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false).getOrientation());
        recyclerkyc.addItemDecoration(dividerItemDecoration2);

        mTxtAccountHolderName = mMainView.findViewById(R.id.tv_accountholdername);
        mTxtDateOfBirth = mMainView.findViewById(R.id.tv_dob);
        mTxtbillingAddress = mMainView.findViewById(R.id.tv_billingaddress);
        mTxtCountry = mMainView.findViewById(R.id.tv_country);
        mTxtFullName = mMainView.findViewById(R.id.tv_fullname);
        mTxtPostalCode = mMainView.findViewById(R.id.tv_postalcode);
        mTxtCity = mMainView.findViewById(R.id.tv_city);
        setIntiallySelection();
        setOnclickListener();




    }

    private void setOnclickListener() {
        mTxtBalance.setOnClickListener(this);
        mTxtMyInfo.setOnClickListener(this);
        mLytBtnToTransfer.setOnClickListener(this);

        mLytAddBank.setOnClickListener(this);


        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, new MyOrderalletFragment()).commit();
            }
        });

        mysale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PicoOrderActivity.class));

            }
        });

        linlayaddnewcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),AddCardActivity.class);
                startActivityForResult(intent,1);



            }
        });
    }






    private void setIntiallySelection() {
        mTxtBalance.setBackgroundColor(Color.parseColor("#009639"));
        mTxtMyInfo.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
        mLytBalance.setVisibility(View.VISIBLE);
        mLytMyInformation.setVisibility(View.GONE);
        wsGetBalanceInfo();
    }

    private void setSelection() {
        mTxtBalance.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
        mTxtMyInfo.setBackgroundColor(Color.parseColor("#a2a9a9a9"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance:
                setSelection();
                mTxtBalance.setBackgroundColor(Color.parseColor("#009639"));
                mLytMyInformation.setVisibility(View.GONE);
                mLytBalance.setVisibility(View.VISIBLE);

                wsGetBalanceInfo();

                break;
            case R.id.myinformation:
                setSelection();
                mTxtMyInfo.setBackgroundColor(Color.parseColor("#009639"));
                mLytMyInformation.setVisibility(View.VISIBLE);
                mLytBalance.setVisibility(View.GONE);

                wsGetPersonalInfo();
                wsGetPaymentMethod();
                wsBankDetail();
                wsKycDetails();
                break;

            case R.id.btntotransfer:

                wsGetKYCStatus();






                break;
            case R.id.lytaddbank:

                startActivity(new Intent(getActivity(), AddBankActivity.class));

                break;
        }
    }

    private void wsKycDetails() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("kyc_status_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                kcdetailslist.clear();
                Log.d("kyc_status_list", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("getKycDocument");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);

                                HashMap<String, String> mhash = new HashMap<>();
                                mhash.put("Id", mJsonObj.getString("Id"));
                                mhash.put("Status", mJsonObj.getString("Status"));
                                mhash.put("Type", mJsonObj.getString("Type"));

                                kcdetailslist.add(mhash);

                        }

                        KYCDetailsAdapter kycDetailsAdapter = new KYCDetailsAdapter(kcdetailslist, getActivity());
                        recyclerkyc.setAdapter(kycDetailsAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kyc_status_list", String.valueOf(error));
                /// Log.d("sdsadd", error.toString());
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

    private void callapi() {
        CustomUtil.ShowDialog(getActivity(),true);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("bank_acc_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomUtil.DismissDialog(getActivity());
                mArrBankListcount.clear();
                Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                            if(mJsonObj.getString("status").equalsIgnoreCase("ACTIVE")) {
                                HashMap<String, String> mhash = new HashMap<>();
                                mhash.put("Id", mJsonObj.getString("Id"));

                                mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
                                mhash.put("account_no", mJsonObj.getString("account_no"));
                                mhash.put("status", mJsonObj.getString("status"));
                                mArrBankListcount.add(mhash);
                            }
                        }
                        if(mArrBankListcount.size()>0){
                            String[] mStr = availableamount.split(" ");


                            Fragment fragment = new FragmentTransfer();
                            Bundle bundle = new Bundle();
                            bundle.putString("Amount", mStr[0].toString());
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmlay, fragment).commit();

                        }
                        else{

                            startActivity(new Intent(getActivity(), AddBankActivity.class));
                        }
                    }
                    else if (jsonObject.getString("status").equals("0")){
                        startActivity(new Intent(getActivity(), AddBankActivity.class));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomUtil.DismissDialog(getActivity());
                Log.d("sdsadd", error.toString());
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

    private void wsBankDetail() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("bank_acc_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mArrBankList.clear();
                // Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                            if(mJsonObj.getString("status").equalsIgnoreCase("ACTIVE")) {
                                HashMap<String, String> mhash = new HashMap<>();
                                mhash.put("Id", mJsonObj.getString("Id"));

                                mhash.put("OwnerName", mJsonObj.getString("OwnerName"));
                                mhash.put("account_no", mJsonObj.getString("account_no"));
                                mhash.put("status", mJsonObj.getString("status"));
                                mArrBankList.add(mhash);
                            }
                        }
                        setAdapterBank();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /// Log.d("sdsadd", error.toString());
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





    private void setAdapterBank() {
        MyBankListAdapter myBankListAdapter = new MyBankListAdapter(mArrBankList, getActivity(), positionInterface);
        mRecyclerBankList.setAdapter(myBankListAdapter);
    }

    PositionInterface positionInterface = new PositionInterface() {
        @Override
        public void onClick(int pos) {
            wsDeleteBank(mArrBankList.get(pos).get("Id"));
        }

        @Override
        public void onClickWithFlag(int pos, int flag) {

        }
    };

    private void wsDeleteBank(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_bank_account"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                param.put("bank_id", id);

                // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void wsGetPaymentMethod() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("card_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mArrayCardList.clear();
                Log.d("sdsdfdfdsfdfadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray mJsonArr = jsonObject.getJSONArray("mycartdata");
                        for (int i = 0; i < mJsonArr.length(); i++) {
                            JSONObject mJsonObj = mJsonArr.getJSONObject(i);
                            if(mJsonObj.getString("status").equalsIgnoreCase("ACTIVE")) {
                                HashMap<String, String> mhash = new HashMap<>();
                                mhash.put("Id", mJsonObj.getString("Id"));
                                mhash.put("CardType", mJsonObj.getString("CardType"));
                                mhash.put("Alias", mJsonObj.getString("Alias"));
                                mhash.put("status", mJsonObj.getString("status"));
                                mArrayCardList.add(mhash);
                            }
                        }
                        setAdapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setAdapter() {
        MyPaymentMethodAdapter myPaymentMethodAdapter = new MyPaymentMethodAdapter(mArrayCardList, getActivity(), positionInterface1);
        mRecylerCardList.setAdapter(myPaymentMethodAdapter);
    }

    PositionInterface positionInterface1 = new PositionInterface() {
        @Override
        public void onClick(int pos) {
            wsDeleteCard(mArrayCardList.get(pos).get("Id"));
        }

        @Override
        public void onClickWithFlag(int pos, int flag) {

        }
    };

    private void wsDeleteCard(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("deactivate_card"), new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            mArrayCardList.clear();
            // Log.d("sdsadd", response);
            try {
                // myCartModelList.clear();
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("1")) {
                    Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("sdsadd", error.toString());
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> param = new HashMap<>();
            param.put("card_id", id);

            // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
            return param;
        }
    };

        requestQueue.add(stringRequest);
}


    private void wsGetPersonalInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("personal_information"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdsadd", response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject mJsonObj = jsonObject.getJSONObject("personal_information");
                        mTxtAccountHolderName.setText(mJsonObj.getString("name"));
                        mTxtDateOfBirth.setText(mJsonObj.getString("DOB"));
                        mTxtbillingAddress.setText(mJsonObj.getString("address"));
                        mTxtCountry.setText(mJsonObj.getString("country"));
                        mTxtFullName.setText(mJsonObj.getString("full_name"));
                        mTxtPostalCode.setText(mJsonObj.getString("postal_code"));
                        mTxtCity.setText(mJsonObj.getString("city"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void wsGetBalanceInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("balance_summary"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //    mArrayCardList.clear();
                // Log.d("sdsadd", response);

                Log.d("fdsfdsf",response);
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject mJsonObj = jsonObject.getJSONObject("balance_summary");
                        mTxtPendingAmount.setText(mJsonObj.getString("Amount to pay")+" "+getResources().getString(R.string.currency));
                        availableamount=mJsonObj.getString("Balance_available");
                        mTxtAvailableAmount.setText(availableamount+" "+getResources().getString(R.string.currency));
                        amounttobereceived.setText(mJsonObj.getString("Amount to be received")+" "+getResources().getString(R.string.currency));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());

                NetworkResponse networkResponse=error.networkResponse;
                String s=new String(networkResponse.data);

                Log.d("sdsadd",s);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                // Log.d("dfdfdf", PrefManager.getUserId(getActivity()));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                wsGetPaymentMethod();
                wsKycDetails();
            }
        }
    }
}
