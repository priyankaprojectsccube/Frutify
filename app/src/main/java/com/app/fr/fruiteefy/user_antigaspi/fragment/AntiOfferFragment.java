package com.app.fr.fruiteefy.user_antigaspi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.app.fr.fruiteefy.user_antigaspi.DonationActivity;
import com.app.fr.fruiteefy.user_antigaspi.ManageofferActivity;
import com.app.fr.fruiteefy.user_antigaspi.adapter.AntiOfferAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AntiOfferFragment extends Fragment implements SearchView.OnQueryTextListener {
    View v;
    ArrayList<Product> allProductPojos = new ArrayList<Product>();
    RecyclerView rv_all_products;
    BottomNavigationView bottomNavigationView;
    TextView managemyoffer, mydonation,textviewantioffer;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    LinearLayout linlayanti;
    View view, view1;
    private RelativeLayout tool,tool1,tool3;
    LinearLayout linlay;
    SearchView searchview;
    private String searchString = "";
    AntiOfferAdapter adapter;
private Activity activity;
    public AntiOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        activity=(UserAntigaspiHomeActivity)getActivity();
        initView(v);

        tool=activity.findViewById(R.id.tool);
        tool1=activity.findViewById(R.id.tool2);
        tool3 = activity.findViewById(R.id.tool3);
        textviewantioffer=v.findViewById(R.id.textviewantioffer);


        tool3.setVisibility(View.GONE);
        tool1.setVisibility(View.GONE);
        tool.setVisibility(View.VISIBLE);

        linlayanti.setVisibility(View.VISIBLE);
        requestQueue=Volley.newRequestQueue(getContext());


        view = activity.findViewById(R.id.linlay);

        view1 = activity.findViewById(R.id.searchview);


        if (view instanceof LinearLayout) {
            linlay = (LinearLayout) view;
        }

        if (view1 instanceof SearchView) {
            searchview = (SearchView) view1;
        }


        searchview.setOnQueryTextListener(this);


        mydonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, DonationActivity.class));
            }
        });


        managemyoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ManageofferActivity.class));
            }
        });


        linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linlay.setVisibility(View.GONE);
                searchview.setVisibility(View.VISIBLE);
                searchview.setIconifiedByDefault(true);
                searchview.setFocusable(true);
                searchview.setIconified(false);
                searchview.requestFocusFromTouch();

            }
        });

        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                linlay.setVisibility(View.VISIBLE);
                searchview.setVisibility(View.GONE);
                return false;
            }
        });


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

    @Override
    public void onResume() {
        super.onResume();
        getAllProductsDetails();
    }

    public void initView(View v) {
        rv_all_products = v.findViewById(R.id.rv_all_products);
        linlayanti = v.findViewById(R.id.linlayanti);

        managemyoffer = v.findViewById(R.id.managemyoffer);
        mydonation = v.findViewById(R.id.mydonation);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
    }   //initViewClose

    public void getAllProductsDetails() {
        allProductPojos.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_offer"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.d("adasds",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        textviewantioffer.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);



                            Product product = new Product();

                            product.setProductid(jsonObject1.getString("offer_id"));
                            product.setUnit(jsonObject1.getString("stock_unit"));
                            product.setAvailable(jsonObject1.getString("offer_status"));
                            product.setProductimg(jsonObject1.getString("offer_image"));
                            product.setProductname(jsonObject1.getString("offer_title"));
                            product.setOfferid(jsonObject1.getString("offer_id"));
                            product.setOffertype(jsonObject1.getString("offer_type"));
                            product.setOfferPlace(jsonObject1.getString("address"));//offer_place
                            product.setPrice(jsonObject1.getString("offer_price") + " " + activity.getResources().getString(R.string.currency));
                            product.setStock(jsonObject1.getString("stock") + " " + jsonObject1.getString("stock_unit"));
                            product.setStatus(jsonObject1.getString("status"));
                           // product.setOffertype(jsonObject1.getString("offer_status"));
                            product.setmZipCode(jsonObject1.getString("zip"));
                            product.setOffer_cat_id(jsonObject1.getString("offer_cat_id"));
                            product.setOffer_subcat_id(jsonObject1.getString("offer_subcat_id"));
                            product.setmStock(jsonObject1.getString("stock"));
                            product.setmStockUnit(jsonObject1.getString("stock_unit"));
                            product.setmPrice(jsonObject1.getString("offer_price"));
                            product.setmOfferAvailableData(jsonObject1.getString("offer_available_date"));
                            product.setmOfferAvailableTime(jsonObject1.getString("offer_available_time"));
                            product.setmPrefered_picoreur(jsonObject1.getString("prefered_picoreur"));
                            product.setmIsTreated(jsonObject1.getString("is_treated"));
                            product.setIsTreadedProductList(jsonObject1.getString("is_treated_product_list"));
                            product.setIsTreaded_Desc(jsonObject1.getString("is_treated_description"));
                            product.setDesc(jsonObject1.getString("is_treated_description"));
                            product.setLat(jsonObject1.getString("lat"));
                            product.setLng(jsonObject1.getString("lng"));

                            product.setAcces(jsonObject1.getString("acces"));
                            product.setAddress(jsonObject1.getString("address"));
                            product.setType(jsonObject1.getString("type"));
                            product.setNom(jsonObject1.getString("nom"));

                            product.setmSalePick(jsonObject1.getString("selpick"));
                            product.setmAvailableType(jsonObject1.getString("available_type"));
                            product.setmStrQuickAvailbality(jsonObject1.getString("quick_availability"));
                            product.setDon_address_id_fk(jsonObject1.getString("don_address_id_fk"));
                            product.setVariety(jsonObject1.getString("variety"));

                            allProductPojos.add(product);

                        }

                        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                        adapter = new AntiOfferAdapter(allProductPojos, activity);
                        rv_all_products.setAdapter(adapter);

                    }
                    else if (jsonObject.getString("status").equals("0")){
                        textviewantioffer.setVisibility(View.VISIBLE);
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

        adapter = new AntiOfferAdapter(filteredModelList, activity);

        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
        rv_all_products.setAdapter(adapter);


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
