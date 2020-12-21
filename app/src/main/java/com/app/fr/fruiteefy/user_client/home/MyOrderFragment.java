package com.app.fr.fruiteefy.user_client.home;

import android.app.Activity;
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
import android.widget.RelativeLayout;
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
import com.app.fr.fruiteefy.user_client.adapter.MyOrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyOrderFragment extends Fragment   {




    View v;
    ArrayList<Product> allProductPojos=new ArrayList<Product>();
    RelativeLayout rellay1,rellay2;
    private Activity activity;
    RecyclerView rv_all_products;
    private TextView removecity;
    EditText productsearch;
    TextView citysearch;
    BottomNavigationView bottomNavigationView;
    View view, view1;
    ImageView searchicon;
    TextView filtericon;
    RelativeLayout tool2;
    public MyOrderFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_my_order, container, false);
        activity=(UserCliantHomeActivity)getActivity();
        initView(v);












        view = activity.findViewById(R.id.citysearch);
        view1 = activity.findViewById(R.id.gardensearch);
        rellay1=activity.findViewById(R.id.rellay1);
        rellay2=activity.findViewById(R.id.rellay2);
        removecity=activity.findViewById(R.id.removecity);
        tool2 = activity.findViewById(R.id.tool2);
        filtericon=activity.findViewById(R.id.filtericon);
        searchicon=activity.findViewById(R.id.searchicon);

        if (view instanceof TextView) {
            citysearch = (TextView) view;
        }

        if (view1 instanceof TextView) {
            productsearch = (EditText) view1;
        }
        removecity.setVisibility(View.GONE);
        searchicon.setVisibility(View.GONE);
        filtericon.setVisibility(View.GONE);
        tool2.setVisibility(View.GONE);

        citysearch.setVisibility(View.GONE);
        productsearch.setVisibility(View.GONE);


        rellay1.setVisibility(View.GONE);
        rellay2.setVisibility(View.GONE);
        getAllProductsDetails();
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
        rv_all_products=v.findViewById(R.id.rv_all_products);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView=activity.findViewById(R.id.bottom_navigation);

    }   //initViewClose

    public  void getAllProductsDetails()
    {allProductPojos.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(activity);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("myorder"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("adasdsdsdsdsdds",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("orderlist");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            Double a= Double.parseDouble(jsonObject1.getString("final_total"));
                            Double b=Double.parseDouble(jsonObject1.getString("service_charge"));
                            Double finaltot=a+b;

                            Log.d("sdasdas",a+" "+b+" "+finaltot);
                            Product product=new Product();
                            product.setProductid(jsonObject1.getString("order_id"));
                            product.setName(jsonObject1.getString("first_name").concat(" "+jsonObject1.getString("last_name")));
                            product.setBookby(jsonObject1.getString("seller_first_name").concat(" "+jsonObject1.getString("seller_last_name")));
                            product.setStatus(jsonObject1.getString("order_status2"));
                            product.setDate(jsonObject1.getString("created_date"));
                            product.setAction(jsonObject1.getString("product_delivered_status"));
                            product.setToken(jsonObject1.getString("seller_fcm_token"));
                            product.setUserid(jsonObject1.getString("product_seller_id"));
                            product.setValidationstatus(jsonObject1.getString("is_validate"));
                            product.setPrice(String.valueOf(finaltot));


                            allProductPojos.add(product);
                        }

                        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                        rv_all_products.setAdapter(new MyOrderAdapter(allProductPojos,activity));
                        rv_all_products.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
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

                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                } else {
                  //  Toast.makeText(getActivity(), "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(activity));
                Log.d("sfsff",PrefManager.getUserId(activity));
                return param;
            }


        };

        requestQueue.add(stringRequest);




    }




    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda",query);
        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductid().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) ) {
                filteredModelList.add(model);
            }
        }

        MyOrderAdapter adapter=new MyOrderAdapter(filteredModelList,activity);

        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
        rv_all_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }




}
