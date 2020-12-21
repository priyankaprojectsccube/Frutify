package com.app.fr.fruiteefy.user_client.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
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
import com.app.fr.fruiteefy.user_client.checkout.FragmentCheckOut;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentOrderSummary extends Fragment implements View.OnClickListener {
    private View mMainView;
    private EditText edtCoupon;
    private TextView reservecart,applycoupon,mTxtShippingCost, mTxtSubtotal, mTxtPromotionalCodeReduction, mTxtServiceCharge, mTxtTotal;
    private LinearLayout mLytBtnReserve;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.layout_myordersummary, container, false);
        setUI();
        return mMainView;
    }

//    public FragmentOrderSummary() {
//        // Required empty public constructor
//    }

    private void setUI() {
        mLytBtnReserve = mMainView.findViewById(R.id.BtnReserve);
        applycoupon = mMainView.findViewById(R.id.applycoupon);
        edtCoupon = mMainView.findViewById(R.id.edtCoupon);
        mTxtShippingCost = mMainView.findViewById(R.id.txtshippingcost);
        mTxtPromotionalCodeReduction = mMainView.findViewById(R.id.promocodereduction);
        mTxtServiceCharge = mMainView.findViewById(R.id.servicecharge);
        mTxtSubtotal = mMainView.findViewById(R.id.subtotal);
        mTxtTotal = mMainView.findViewById(R.id.TxtTotal);
        setOnclickListener();
        GetCartSummary();





        applycoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  if(edtCoupon.getText().toString().equals("")){

                      edtCoupon.setError("Please fill the field");

                       }
                  else {
                          RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                          StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("apply_coupon_oncart"), new Response.Listener<String>() {

                              @Override
                     public void onResponse(String response) {
                            Log.d("sdsadd", response);


            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getString("status").equals("1")) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.rl_main, new FragmentOrderSummary()).commit();
                }

                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
            param.put("coupon_code", edtCoupon.getText().toString());


            return param;
        }
    };

    requestQueue.add(stringRequest);


}
            }
        });
    }

    private void setOnclickListener() {
        mLytBtnReserve.setOnClickListener(this);
    }

    private void GetCartSummary() {

        CustomUtil.ShowDialog(getActivity(),false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("cart_summary"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Log.d("sdsadd", response);
                 CustomUtil.DismissDialog(getActivity());
                try {
                    // myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("mycartdata");
                        mTxtShippingCost.setText(jsonObject1.getDouble("shipping_cost")+" "+getResources().getString(R.string.currency));
                        mTxtServiceCharge.setText(jsonObject1.getDouble("service_charge")+" "+getResources().getString(R.string.currency));
                        mTxtSubtotal.setText(jsonObject1.getDouble("subtotal")+" "+getResources().getString(R.string.currency));
                        mTxtPromotionalCodeReduction.setText(jsonObject1.getDouble("discount")+" "+getResources().getString(R.string.currency));
                        mTxtTotal.setText(jsonObject1.getDouble("final_total")+" "+getResources().getString(R.string.currency));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
                CustomUtil.DismissDialog(getActivity());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtnReserve:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.rl_main, new FragmentCheckOut()).commit();

                break;
        }
    }
}
