package com.app.fr.fruiteefy.user_picorear.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
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
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.app.fr.fruiteefy.user_picorear.Adapter.PicoOfferAdapter;
import com.app.fr.fruiteefy.user_picorear.AddOfferActivity;
import com.app.fr.fruiteefy.user_picorear.ManagePicoofferActivity;
import com.app.fr.fruiteefy.user_picorear.PicoOrderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PicoMyOfferFragment extends Fragment implements SearchView.OnQueryTextListener {

    View v;
    ArrayList<Product> allProductPojos = new ArrayList<Product>();
    RecyclerView rv_all_products;
    BottomNavigationView bottomNavigationView;
    TextView managemyoffer, mydonation, myorder,textpicomyoffer;
    TextView mTxtAddAnoffer;
    LinearLayout linlayanti;
    EditText gardensearch;
    private Activity activity;
    ImageView filtericon,searchicon;
  ///  View view, view1;
//    LinearLayout linlay;
  //  SearchView searchview;
    private String searchString = "";
    PicoOfferAdapter adapter;

    public PicoMyOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pico_my_offer, container, false);
        activity=(UserPicorearHomeActivity)getActivity();
        initView(v);
        linlayanti.setVisibility(View.VISIBLE);
        getAllProductsDetails();


       // view = activity.findViewById(R.id.linlay);

      //  view1 = activity.findViewById(R.id.searchview);
        myorder = v.findViewById(R.id.myorders);


        filtericon =activity.findViewById(R.id.filtericon);
        searchicon=activity.findViewById(R.id.searchicon);
        gardensearch=activity.findViewById(R.id.gardensearch);
        textpicomyoffer=v.findViewById(R.id.textpicomyoffer);
        filtericon.setVisibility(View.GONE);
        searchicon.setVisibility(View.GONE);
        gardensearch.setVisibility(View.GONE);


//        if (view instanceof LinearLayout) {
//            linlay = (LinearLayout) view;
//        }

//        if (view1 instanceof SearchView) {
//            //searchview = (SearchView) view1;
//        }


       // searchview.setOnQueryTextListener(this);


        mTxtAddAnoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(activity, AddOfferActivity.class);
                mIntent.putExtra("From", "Add");
                startActivity(mIntent);
            }
        });




        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(activity, PicoOrderActivity.class));
            }
        });


        managemyoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ManagePicoofferActivity.class);
                startActivity(intent);

            }
        });


//        linlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linlay.setVisibility(View.GONE);
//                searchview.setVisibility(View.VISIBLE);
//                searchview.setIconifiedByDefault(true);
//                searchview.setFocusable(true);
//                searchview.setIconified(false);
//                searchview.requestFocusFromTouch();
//
//            }
//        });

//        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                linlay.setVisibility(View.VISIBLE);
//                searchview.setVisibility(View.GONE);
//                return false;
//            }
//        });


        Log.d("fdfdf", PrefManager.getUserId(activity));
        rv_all_products.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && bottomNavigationView.isShown()) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (dy < 0) {
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

    public void initView(View v) {
        rv_all_products = v.findViewById(R.id.rv_all_products);
        linlayanti = v.findViewById(R.id.linlayanti);
        mTxtAddAnoffer = v.findViewById(R.id.addanoffer);

        managemyoffer = v.findViewById(R.id.managemyoffer);
        // mydonation = v.findViewById(R.id.mydonation);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
    }   //initViewClose

    public void getAllProductsDetails() {
        allProductPojos.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("myownproduct"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("myownproduct_r", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        textpicomyoffer.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setProductid(jsonObject1.getString("product_id"));
                            product.setWeight(jsonObject1.getString("weight"));
                            product.setPrice(jsonObject1.getString("price"));
                            product.setStock(jsonObject1.getString("stock"));
                            product.setUnit(jsonObject1.getString("unit"));
                            product.setProductname(jsonObject1.getString("product_name"));
                            product.setProductimg(jsonObject1.getString("product_image"));

                              product.setOffertype(jsonObject1.getString("offer_type"));
                              product.setStatusai(jsonObject1.getString("status"));
//                                product.setLat(jsonObject1.getString("lat"));
//                                product.setLng(jsonObject1.getString("lng"));

//                                product.setOfferPlace(jsonObject1.getString("offer_place"));
//                                product.setAvailable(jsonObject1.getString("offer_status"));

//                                product.setDesc(jsonObject1.getString("is_treated_description"));
                            allProductPojos.add(product);
                        }

                        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                        adapter = new PicoOfferAdapter(allProductPojos, activity);
                        rv_all_products.setAdapter(adapter);
                        rv_all_products.getAdapter().notifyDataSetChanged();
                    }


                    else if(jsonObject.getString("status").equals("0")){
                        textpicomyoffer.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("adasds", volleyError.toString());
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
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(activity));
                return param;
            }


        };

        requestQueue.add(stringRequest);


    }


    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda", query);
        this.searchString = query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductname().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }

        adapter = new PicoOfferAdapter(filteredModelList, activity);

        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
        rv_all_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("jhhjgh", newText);
        final List<Product> filteredModelList = filter(allProductPojos, newText);
        if (filteredModelList.size() > 0) {
            adapter.setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }


}
