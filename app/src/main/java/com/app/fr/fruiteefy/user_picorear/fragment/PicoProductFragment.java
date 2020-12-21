package com.app.fr.fruiteefy.user_picorear.fragment;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.Adapter.PicoProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PicoProductFragment extends Fragment {

RecyclerView recviewproduct;
    private ArrayList<Product> picoprodArr=new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    ImageView searchicon,filtericon;
    EditText gardensearch;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pico_product, container, false);


        inIt(view);

        searchicon=getActivity().findViewById(R.id.searchicon);
        filtericon=getActivity().findViewById(R.id.filtericon);

        gardensearch=getActivity().findViewById(R.id.gardensearch);

        filtericon.setVisibility(View.GONE);
        searchicon.setVisibility(View.GONE);
        gardensearch.setVisibility(View.GONE);


        recviewproduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNavigationView.isShown()) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        onClick();
        return view;
    }


    private void inIt(View view){
        recviewproduct=view.findViewById(R.id.recviewproduct);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
    }

    private void onClick(){

        RequestQueue requestQueue1= Volley.newRequestQueue(getActivity());
        CustomUtil.ShowDialog(getActivity(),true);
        StringRequest stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picoproduct_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dfddfrfgfdgfgdgfdg",response);

                CustomUtil.DismissDialog(getActivity());
                picoprodArr.clear();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("productlist");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();
                            product.setProductid(jsonObject1.getString("product_id"));
                            product.setOffername(jsonObject1.getString("offer_name"));
                            product.setOfferid(jsonObject1.getString("offer_id"));
                            product.setReservationid(jsonObject1.getString("reservation_id"));
                            product.setIscollected(jsonObject1.getString("is_collected"));
                            product.setProductimg(jsonObject1.getString("product_image"));
                            product.setOfferimg(jsonObject1.getString("offer_image"));
                            product.setCollectdate(jsonObject1.getString("collect_date"));
                            product.setStatus(jsonObject1.getString("status"));
                            product.setOffertime(jsonObject1.getString("offertime"));
                            product.setDesc(jsonObject1.getString("description"));
                            picoprodArr.add(product);
                        }

                        recviewproduct.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recviewproduct.setAdapter(new PicoProductAdapter(picoprodArr,getActivity()));
                        recviewproduct.getAdapter().notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf",volleyError.toString());
                CustomUtil.DismissDialog(getActivity());
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

        requestQueue1.add(stringRequest1);

    }

}
