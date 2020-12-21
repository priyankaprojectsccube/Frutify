package com.app.fr.fruiteefy.user_client.mywallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.Invoice;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_client.mywallet.adapter.InvoiceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SaleInvoiceFragment extends Fragment {



    RecyclerView recyclerView;
    ArrayList<Invoice> arrlistinvoices=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_saleinvoice, container, false);

        recyclerView=view.findViewById(R.id.recvewdata);

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("payment_sales_invoice"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                arrlistinvoices.clear();

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("orderlist");

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        Invoice invoice=new Invoice();

                        invoice.setOrderdetail(jsonObject1.getString("order_details"));
                        invoice.setAddress(jsonObject1.getString("address"));
                        invoice.setSelleraddress(jsonObject1.getString("seller_address"));
                        invoice.setOrderid(jsonObject1.getString("order_id"));
                        invoice.setOrderdate(jsonObject1.getString("order_date"));
                        invoice.setOrderno(jsonObject1.getString("order_no"));
                        invoice.setShippingcost(jsonObject1.getString("shipping_cost"));
                        invoice.setSubtotal(jsonObject1.getString("sub_total"));
                        invoice.setPromocode(jsonObject1.getString("promo_code"));
                        invoice.setServicecharge(jsonObject1.getString("service_charge"));
                        invoice.setFinaltotal(jsonObject1.getString("final_total"));


                        arrlistinvoices.add(invoice);


                        JSONArray jsonArray1=jsonObject1.getJSONArray("product_list");
                        for(int i1=0;i1<jsonArray1.length();i1++) {


                            JSONObject jsonObject5 = jsonArray1.getJSONObject(i1);

                            Product product6 = new Product();

                            product6.setProductname(jsonObject5.getString("product_name"));
                            product6.setProductimg(jsonObject5.getString("product_image"));
                            product6.setPrice(jsonObject5.getString("price"));
                            product6.setDesc(jsonObject5.getString("quentity"));
                            product6.setAction(jsonObject5.getString("delivery_fees"));
                            product6.setAvailable(jsonObject5.getString("sub_total"));
                            product6.setDate(jsonObject5.getString("delivery_date"));
                            arrlistinvoices.get(arrlistinvoices.size()-1).getmArrSubList().add(product6);
                            //  antisubprodArr.add(product6);

                        }
                    }


                    InvoiceAdapter adapter = new InvoiceAdapter(arrlistinvoices, getActivity());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("dsfdfdfd",volleyError.toString());
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (message != null) {

                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                }

            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                return param;
            }

        };

        requestQueue.add(stringRequest);
        return view;
    }


}
