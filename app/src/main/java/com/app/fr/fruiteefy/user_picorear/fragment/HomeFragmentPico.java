package com.app.fr.fruiteefy.user_picorear.fragment;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;

import com.app.fr.fruiteefy.user_picorear.Adapter.PicoBookingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragmentPico extends Fragment {

    View v;
    ArrayList<Product> allProductPojos=new ArrayList<Product>();
    RecyclerView rv_all_products;
    TextView managemyoffer;
    ImageView searchicon,filtericon;
    BottomNavigationView bottomNavigationView;
    private CardView filtecardview;
    EditText gardensearch;

    public HomeFragmentPico() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        getAllProductsDetails();

        filtericon=getActivity().findViewById(R.id.filtericon);
        gardensearch=getActivity().findViewById(R.id.gardensearch);
        searchicon=getActivity().findViewById(R.id.searchicon);

        filtericon.setVisibility(View.GONE);
        searchicon.setVisibility(View.GONE);
        gardensearch.setVisibility(View.GONE);



        rv_all_products.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        return v;
    }

    public void initView(View v)
    {

        Log.d("Sdsdsadad",PrefManager.getUserId(getActivity()));
        rv_all_products=v.findViewById(R.id.rv_all_products);
        managemyoffer=v.findViewById(R.id.managemyoffer);


        filtecardview=v.findViewById(R.id.filtecardview);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);


    }   //initViewClose

    public  void getAllProductsDetails() {

        allProductPojos.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(
                getActivity());


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("reservation"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("adasds",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("reservation");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();
                            product.setProductid(jsonObject1.getString("reservation_id"));
                            product.setProductname(jsonObject1.getString("item_name"));
                            //product.setOffertype(jsonObject1.getString("offer_type"));
                            product.setProductimg(jsonObject1.getString("offerimage"));
                           // product.setLat(jsonObject1.getString("lat"));
                           // product.setLng(jsonObject1.getString("lng"));
                           // product.setStock(jsonObject1.getString("stock"));
                           // product.setUnit(jsonObject1.getString("stock_unit"));
                           // product.setOfferPlace(jsonObject1.getString("offer_place"));
                            product.setAvailable(jsonObject1.getString("status"));
                            //    product.setWeight(jsonObject1.getString("weight"));

                          //  product.setPrice(jsonObject1.getString("offer_price"));
                            product.setDesc(jsonObject1.getString("description"));
                            allProductPojos.add(product);
                        }

                        rv_all_products.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_all_products.setAdapter(new PicoBookingAdapter(allProductPojos,getActivity()));
                        rv_all_products.getAdapter().notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
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


//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("dfsdfdsf","dsfdfd");
//
//                Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));
//
//                if(filtecardview.isShown()){
//                    filtecardview.setVisibility(View.GONE);
//
//
//                }
//                else{
//                    filtecardview.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });





    }




}
