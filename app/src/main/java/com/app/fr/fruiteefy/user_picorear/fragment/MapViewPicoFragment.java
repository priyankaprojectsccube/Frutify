package com.app.fr.fruiteefy.user_picorear.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.app.fr.fruiteefy.Util.ChooseHellperClass;
import com.app.fr.fruiteefy.Util.MapCluster;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterAccess;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterChoose;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.Adapterlastname;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_picorear.Adapter.PicoDonationaroundmemapAdapter;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewPicoFragment extends Fragment implements Spinner.OnItemSelectedListener, OnMapReadyCallback, ClusterManager.OnClusterClickListener<MapCluster>, ClusterManager.OnClusterInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemClickListener<MapCluster> {
    RequestQueue requestQueuechoose;
    StringRequest stringRequestchoose;
    JSONArray resultchoose;
    private GoogleMap mMap;
    private RequestQueue requestQueue,requestQueue2;
    private CardView filtecardview;
    private RecyclerView recitem;
    Activity activity;
    SeekBar seekBar;
    private RelativeLayout tool, tool1, tool3;
    public static int LENGTH_MAX_VALUE = 100;
    private String category_id = "", subcategory_id = "", type_id = "";
    private EditText productsearch;
    private StringRequest stringRequest,stringRequest2;
    private View view1;
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<String> category;
    private ArrayList<String> subcategory = new ArrayList<>();
    private TextView applyfilter, setkm, km, filtericon,hideunavailable,Earsefilter;
    private String searchString = "", strselectedtypeds = "", strselectedlastname = "", strselectedaccess = "", strselectedstatus = "";
    private Spinner spinner_cat, spinnersubcat,spinner_status, spinner_accesss, spinner_type_d_s, spinner_lastname;
    private ImageView searchicon;
    private RequestQueue requestQueue1;
    private StringRequest stringRequest1;
    private ArrayList<String> sellertype;
    private JSONArray resultcat, resultsubcat;
    private ClusterManager<MapCluster> mClusterManager;
    ArrayList<Product> allProductPojos = new ArrayList<Product>();
    View v;
    ArrayList<ChooseHellperClass> chooselist = new ArrayList<>();

    public MapViewPicoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_mapdonationaroundme_view, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        category = new ArrayList<>();

        activity = (UserAntigaspiHomeActivity) getActivity();
        requestQueuechoose= Volley.newRequestQueue(activity);
        calladdresslist();
        mapFragment.getMapAsync(this);
        sellertype = new ArrayList<>();
        recitem = v.findViewById(R.id.recitem);
        km = v.findViewById(R.id.km);
        seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        view1 = activity.findViewById(R.id.gardensearch);
        requestQueue1 = Volley.newRequestQueue(getContext());
        spinner_cat = v.findViewById(R.id.spinner_cat);
        setkm = v.findViewById(R.id.setkm);
        spinnersubcat = v.findViewById(R.id.spinnersubcat);
        applyfilter = v.findViewById(R.id.applyfilter);
        tool = activity.findViewById(R.id.tool);
        tool1 = activity.findViewById(R.id.tool2);
        tool3 = activity.findViewById(R.id.tool3);

        tool1.setVisibility(View.VISIBLE);
        tool.setVisibility(View.GONE);
        tool3.setVisibility(View.VISIBLE);

        Log.d("ssdsadsad", PrefManager.getUserId(activity));

        if (view1 instanceof EditText) {
            productsearch = (EditText) view1;
        }

        filtericon = activity.findViewById(R.id.filtericon);
        hideunavailable =activity.findViewById(R.id.hideunavailable);
        Earsefilter = activity.findViewById(R.id.Earsefilter);

        searchicon = activity.findViewById(R.id.searchicon);

        filtecardview = v.findViewById(R.id.filtecardview);


        searchicon.setVisibility(View.VISIBLE);
        filtericon.setVisibility(View.VISIBLE);
        hideunavailable.setVisibility(View.VISIBLE);
        Earsefilter.setVisibility(View.VISIBLE);

        productsearch.setVisibility(View.VISIBLE);


        productsearch.setText("");


        spinner_cat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);


        spinner_type_d_s = v.findViewById(R.id.spinner_type_d_s);
        spinner_lastname = v.findViewById(R.id.spinner_lastname);
        spinner_accesss = v.findViewById(R.id.spinner_accesss);
        spinner_status = v.findViewById(R.id.spinner_status);


        spinner_type_d_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (adapterView.getId() == R.id.spinner_type_d_s) {

                    String selectedItem = adapterView.getItemAtPosition(i).toString();

                    Log.d("choosekjkjkjkj", String.valueOf(i));
                    if (selectedItem.equals(getResources().getString(R.string.select))) {
                        strselectedtypeds = "";
                    } else {
                        strselectedtypeds = getChoose_id(i - 1);
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_lastname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getId() == R.id.spinner_lastname) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();


                    if (selectedItem.equals(activity.getResources().getString(R.string.lastname))) {
                        strselectedlastname = "";
                    } else  {
                        strselectedlastname = getchoosename(i-1);

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_accesss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (adapterView.getId() == R.id.spinner_accesss) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();


                    if (selectedItem.equals(activity.getResources().getString(R.string.access_spinner))) {
                        strselectedaccess = "";
                    } else
                    {
                        strselectedaccess = getchooseaccess(i-1);

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (adapterView.getId() == R.id.spinner_status) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();


                    if (selectedItem.equals(activity.getResources().getString(R.string.status_spinner))) {
                        strselectedstatus = "";
                    } else if (selectedItem.equals(activity.getResources().getString(R.string.available_spinner))) {
                        strselectedstatus = "1";

                    } else if (selectedItem.equals(activity.getResources().getString(R.string.unavailable_spinner))) {
                        strselectedstatus = "2";

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));

                if (filtecardview.isShown()) {
                    filtecardview.setVisibility(View.GONE);


                } else {
                    filtecardview.setVisibility(View.VISIBLE);
                }

            }
        });


        seekBar.setMax(LENGTH_MAX_VALUE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String value = String.valueOf(progress);

                setkm.setText(value);

                km.setText(" KM");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                category.add(activity.getResources().getString(R.string.select));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resultcat = jsonObject.getJSONArray("catlist");


                    for (int i = 0; i <= resultcat.length(); i++) {

                        JSONObject json = resultcat.getJSONObject(i);

                        category.add(json.getString("cat_name"));

                        spinner_cat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, category));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsddd", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(activity));
                return param;
            }

        };


        requestQueue1.add(stringRequest1);


        hideunavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callhide();
            }
        });
        Earsefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productsearch.setText("");
               getAllProduc();
            }
        });
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("drm_search", response);

                        allProductPojos.clear();
                        mMap.clear();


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                            Log.d("dfdsfsddf", String.valueOf(jsonArray.length()));


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Log.d("fdsfsdfsf", String.valueOf(jsonObject1));
                                Product product = new Product();
                                product.setProductid(jsonObject1.getString("offer_id"));
                                product.setProductname(jsonObject1.getString("offer_title"));
                                if (jsonObject1.has("is_treated_description")) {
                                    product.setDesc(jsonObject1.getString("is_treated_description"));
                                }
                                product.setOffertype(jsonObject1.getString("offer_type"));
                                product.setProductimg(jsonObject1.getString("offer_image"));
                                //   product.setLat(jsonObject1.getString("lat"));
                                //  product.setLng(jsonObject1.getString("lng"));
                                product.setStock(jsonObject1.getString("stock"));
                                product.setFirstname(jsonObject1.getString("firstname"));
                                product.setLastname(jsonObject1.getString("lastname"));
                                product.setUnit(jsonObject1.getString("stock_unit"));
                                product.setPricestatus(jsonObject1.getString("price_status"));
                                product.setOfferPlace(jsonObject1.getString("city"));
                                product.setAvailable(jsonObject1.getString("offer_status_text"));
                                product.setAcces(jsonObject1.getString("acces"));
                                product.setAddress(jsonObject1.getString("address"));
                                product.setType(jsonObject1.getString("type"));
                                product.setNom(jsonObject1.getString("nom"));
//                            product.setWeight(jsonObject1.getString("weight"));
                                product.setLat(jsonObject1.getString("latitude"));
                                product.setLng(jsonObject1.getString("longitudeFrom"));
                                product.setPrice(jsonObject1.getString("offer_price"));

                                allProductPojos.add(product);
                            }


                            LatLng coordinate = new LatLng(46.2276, 2.2137);
                            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                            mMap.animateCamera(location);
//                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        recitem.setLayoutManager(verticalLayoutManager);
//
//                        PicoDonationaroundmeAdapter adapter = new PicoDonationaroundmeAdapter(allProductPojos, getActivity());
//                        recitem.setAdapter(adapter);

                            mClusterManager.clearItems();

                            for (int j = 0; j < allProductPojos.size(); j++) {

                                Product allProductPojo = allProductPojos.get(j);

                                Double lat1 = Double.valueOf(allProductPojo.getLat());
                                Double lng1 = Double.valueOf(allProductPojo.getLng());
                                String title = allProductPojo.getProductname();
                                String snippet = activity.getResources().getString(R.string.moredetails);
                                String id = allProductPojo.getProductid();


                                MapCluster offsetItem = new MapCluster(id, title, allProductPojo.getProductimg(),
                                        allProductPojo.getStock(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getUnit(), allProductPojo.getOffertype(),
                                        allProductPojo.getPricestatus(), allProductPojo.getOfferPlace(),
                                        allProductPojo.getAvailable(), allProductPojo.getDesc(), allProductPojo.getPrice(), allProductPojo.getAddress(), allProductPojo.getNom(), allProductPojo.getType(), lat1, lng1, snippet);
                                mClusterManager.addItem(offsetItem);
                                mClusterManager.cluster();

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
                        param.put("searchbtn", "filter");
                        param.put("user_id", PrefManager.getUserId(activity));
                        param.put("product_name", productsearch.getText().toString());

                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });


        applyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                filtecardview.setVisibility(View.GONE);

                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("drm_filter", response);

                        filtecardview.setVisibility(View.GONE);
                        Log.d("ffgfdgdg", response);

                        allProductPojos.clear();
                        mMap.clear();


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                            Log.d("dfdsfsddf", String.valueOf(jsonArray.length()));


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Log.d("fdsfsdfsf", String.valueOf(jsonObject1));
                                Product product = new Product();
                                product.setProductid(jsonObject1.getString("offer_id"));
                                product.setProductname(jsonObject1.getString("offer_title"));
                                product.setOffertype(jsonObject1.getString("offer_type"));
                                product.setProductimg(jsonObject1.getString("offer_image"));
                                product.setAcces(jsonObject1.getString("acces"));
                                product.setAddress(jsonObject1.getString("address"));
                                product.setType(jsonObject1.getString("type"));
                                product.setNom(jsonObject1.getString("nom"));
                                //   product.setLat(jsonObject1.getString("lat"));
                                //  product.setLng(jsonObject1.getString("lng"));
                                product.setStock(jsonObject1.getString("stock"));
                                product.setFirstname(jsonObject1.getString("firstname"));
                                product.setLastname(jsonObject1.getString("lastname"));
                                product.setUnit(jsonObject1.getString("stock_unit"));
                                product.setPricestatus(jsonObject1.getString("price_status"));
                                product.setOfferPlace(jsonObject1.getString("city"));
                                product.setAvailable(jsonObject1.getString("offer_status_text"));
                                product.setLat(jsonObject1.getString("latitude"));
                                product.setLng(jsonObject1.getString("longitudeFrom"));
//                            product.setWeight(jsonObject1.getString("weight"));
                                product.setPrice(jsonObject1.getString("offer_price"));
                                if (jsonObject1.has("is_treated_description")) {
                                    product.setDesc(jsonObject1.getString("is_treated_description"));

                                }
                                allProductPojos.add(product);
                            }


                            LatLng coordinate = new LatLng(46.2276, 2.2137);
                            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                            mMap.animateCamera(location);
//                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        recitem.setLayoutManager(verticalLayoutManager);
//
//                        PicoDonationaroundmeAdapter adapter = new PicoDonationaroundmeAdapter(allProductPojos, getActivity());
//                        recitem.setAdapter(adapter);

                            mClusterManager.clearItems();

                            for (int j = 0; j < allProductPojos.size(); j++) {

                                Product allProductPojo = allProductPojos.get(j);

                                Double lat1 = Double.valueOf(allProductPojo.getLat());
                                Double lng1 = Double.valueOf(allProductPojo.getLng());
                                String title = allProductPojo.getProductname();
                                String snippet = activity.getResources().getString(R.string.moredetails);
                                String id = allProductPojo.getProductid();


                                MapCluster offsetItem = new MapCluster(id, title, allProductPojo.getProductimg(),
                                        allProductPojo.getStock(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getUnit(), allProductPojo.getOffertype(),
                                        allProductPojo.getPricestatus(), allProductPojo.getOfferPlace(),
                                        allProductPojo.getAvailable(), allProductPojo.getDesc(), allProductPojo.getPrice(), allProductPojo.getAddress(), allProductPojo.getNom(), allProductPojo.getType(), lat1, lng1, snippet);

                                mClusterManager.addItem(offsetItem);
                                mClusterManager.cluster();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;

                        Log.d("zffdffdsfdfsf", volleyError.toString());
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


                        param.put("product_name", productsearch.getText().toString());
                        param.put("cat_id", category_id);
                        param.put("subcat_id", subcategory_id);
                        param.put("user_id", PrefManager.getUserId(activity));
                        param.put("latitude", "");
                        param.put("longitude", "");
                        param.put("distance", setkm.getText().toString());

                        param.put("type",strselectedtypeds);
                        param.put("nom",strselectedlastname);
                        param.put("acces",strselectedaccess);
                        param.put("offer_status",strselectedstatus);
                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });


        return v;
    }

    private void callhide() {
        requestQueue2 = Volley.newRequestQueue(activity);

        stringRequest2=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("drm_all",response);

                allProductPojos.clear();
                mMap.clear();



                try {
                    JSONObject jsonObject=new JSONObject(response);
                    //   if (jsonObject.getString("status").equals("1")) {
                    JSONArray jsonArray=jsonObject.getJSONArray("offerlist");

                    Log.d("dfdsfsddf", String.valueOf(jsonArray.length()));



                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        Log.d("fdsfsdfsf", String.valueOf(jsonObject1));
                        Product product = new Product();


                        product.setOffertype(jsonObject1.getString("offer_type"));
                        product.setPricestatus(jsonObject1.getString("price_status"));
                        product.setOfferPlace(jsonObject1.getString("city"));
                        product.setAvailable(jsonObject1.getString("offer_status_text"));
                        product.setAcces(jsonObject1.getString("acces"));
                        product.setAddress(jsonObject1.getString("address"));
                        product.setType(jsonObject1.getString("type"));
                        product.setNom(jsonObject1.getString("nom"));
                        product.setPrice(jsonObject1.getString("offer_price"));
                        product.setUnit(jsonObject1.getString("stock_unit"));
                        product.setStock(jsonObject1.getString("stock"));
                        product.setFirstname(jsonObject1.getString("firstname"));
                        product.setLastname(jsonObject1.getString("lastname"));
                        product.setProductimg(jsonObject1.getString("offer_image"));
                        product.setProductid(jsonObject1.getString("offer_id"));
                        product.setProductname(jsonObject1.getString("offer_title"));
                        product.setLat(jsonObject1.getString("latitude"));
                        product.setLng(jsonObject1.getString("longitudeFrom"));
                        if(jsonObject1.has("is_treated_description")) {
                            product.setDesc(jsonObject1.getString("is_treated_description"));

                        }
                        allProductPojos.add(product);
                    }


                    LatLng coordinate = new LatLng(46.2276, 2.2137);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                    mMap.animateCamera(location);
//                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        recitem.setLayoutManager(verticalLayoutManager);
//
//                        PicoDonationaroundmeAdapter adapter = new PicoDonationaroundmeAdapter(allProductPojos, getActivity());
//                        recitem.setAdapter(adapter);

                    mClusterManager.clearItems();

                    for (int j = 0; j < allProductPojos.size(); j++) {

                        Product allProductPojo = allProductPojos.get(j);

                        Double lat1 = Double.valueOf(allProductPojo.getLat());
                        Double lng1 = Double.valueOf(allProductPojo.getLng());
                        String title = allProductPojo.getProductname();
                        String snippet = activity.getResources().getString(R.string.moredetails);
                        String id = allProductPojo.getProductid();


                        MapCluster offsetItem = new MapCluster(id,title, allProductPojo.getProductimg(),
                                allProductPojo.getStock(), allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getUnit(),allProductPojo.getOffertype(),
                                allProductPojo.getPricestatus(),allProductPojo.getOfferPlace(),
                                allProductPojo.getAvailable(),allProductPojo.getDesc(),allProductPojo.getPrice(),allProductPojo.getAddress(),allProductPojo.getNom(),allProductPojo.getType(),lat1,lng1,snippet);

                        mClusterManager.addItem(offsetItem);
                        mClusterManager.cluster();

                    }
                    //  }
//                         else{
//                        mMap.clear();
//                        recitem.setVisibility(View.GONE);
//                        mClusterManager.clearItems();
//                        Toast.makeText(activity, getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();
//
//                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("dfdfdsf",error.toString());

            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();

                param.put("offer_status_avail","1");
                param.put("user_id", PrefManager.getUserId(activity));




                return param;

            }
        };


        requestQueue2.add(stringRequest2);
    }
    private void calladdresslist() {
        //choose_spinner
        stringRequestchoose = new StringRequest(Request.Method.GET, BaseUrl.BASEURL.concat("get_donner_address"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("getchooseResponse", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            resultchoose = jsonObject.getJSONArray("address_list");
                            getchhoselist(resultchoose);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        requestQueuechoose.add(stringRequestchoose);
    }

    private void getchhoselist(JSONArray resultchoose) {
        chooselist.clear();
        try {
            // category.add(getResources().getString(R.string.category));
            //  for (int i = 0; i <= j.length(); i++) {
            //  JSONObject json = j.getJSONObject(i);
            ChooseHellperClass chooseHellperClass = new ChooseHellperClass();
            chooseHellperClass.setType(activity.getResources().getString(R.string.select));
            chooseHellperClass.setNom(activity.getResources().getString(R.string.select));
            chooseHellperClass.setAcces(activity.getResources().getString(R.string.select));
            chooseHellperClass.setDonner_address_id("");

            chooselist.add(chooseHellperClass);

            // }


            for (int i = 0; i <= resultchoose.length(); i++) {
                JSONObject json = resultchoose.getJSONObject(i);
                ChooseHellperClass chooseHellperClass1 = new ChooseHellperClass();
                chooseHellperClass1.setType(json.getString("type"));
                chooseHellperClass1.setNom(json.getString("nom"));
                chooseHellperClass1.setDonner_address_id(json.getString("donner_address_id"));
                chooseHellperClass1.setAcces(json.getString("acces"));

                chooselist.add(chooseHellperClass1);

            }

        } catch (JSONException e) {

        }
        //spinnercat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category));
        spinner_type_d_s.setAdapter(new AdapterChoose(chooselist, activity,1));

        spinner_lastname.setAdapter(new Adapterlastname(chooselist,activity));

        spinner_accesss.setAdapter(new AdapterAccess(chooselist,activity));


    }
    private String getChoose_id(int position) {
        String chooseid = "";
        try {
            JSONObject jsonObject = resultchoose.getJSONObject(position);
            chooseid = jsonObject.getString("type");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chooseid;
    }


    private String getchoosename(int position) {
        String choosename = "";
        try {
            JSONObject jsonObject = resultchoose.getJSONObject(position);
            choosename = jsonObject.getString("nom");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return choosename;
    }


    private String getchooseaccess(int i) {
        String chooseaccess = "";
        try {
            JSONObject jsonObject = resultchoose.getJSONObject(i);
            chooseaccess = jsonObject.getString("acces");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chooseaccess;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {


        if (adapterView.getId() == R.id.spinner_cat) {

            Log.d("dsfdsfds", String.valueOf(i));
            spinnersubcat.setSelection(0);
            String selectedItem = adapterView.getItemAtPosition(i).toString();

            Log.d("kjjjjjkkjkjjk", selectedItem);
            if (selectedItem.equals(activity.getResources().getString(R.string.category))) {
                category_id = "";
            } else {

                category_id = getCatId(i - 1);

                requestQueue1 = Volley.newRequestQueue(activity);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("dsdsddd", response);
                        subcategory.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            resultsubcat = jsonObject.getJSONArray("subcatlist");
                            getSubCatrName(resultsubcat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dsdsddd", error.toString());

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(activity));
                        param.put("cat_id", category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
        } else if (adapterView.getId() == R.id.spinnersubcat) {


            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(activity.getResources().getString(R.string.subcategory))) {
                subcategory_id = "";
            } else {
                subcategory_id = getsubCatId(i - 1);
                Log.d("sdfdsf", subcategory_id);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getCatId(int position) {
        String Catid = "";
        try {
            JSONObject json = resultcat.getJSONObject(position);

            Catid = json.getString("cat_id");
            Log.d("sdfgfgffgfsdsd", Catid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return Catid;
    }


    private void getSubCatrName(JSONArray j) {
        try {

            subcategory.add(activity.getResources().getString(R.string.subcategory));
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                subcategory.add(json.getString("subcat_name"));
                Log.d("sdsdsddsd", String.valueOf(subcategory));

            }
        } catch (JSONException e) {

        }
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subcategory));

    }

    private String getsubCatId(int position) {
        String subCatid = "";
        try {
            JSONObject json = resultsubcat.getJSONObject(position);

            subCatid = json.getString("subcat_id");
            Log.d("sdsdsd", subCatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return subCatid;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mClusterManager = new ClusterManager<MapCluster>(activity, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);


        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);


        mClusterManager.setRenderer(new CustomRenderer(getActivity(), googleMap, mClusterManager));


        Validation validation = new Validation();

        if (!validation.checkPermissions(activity)) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 3);

        } else {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            getGpsEnable();


        }


    }


    public void getGpsEnable() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {


                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {


                                    Log.d("sdsadasd", String.valueOf(location.getLatitude()));

                                    PrefManager.setCURRENTLAT(String.valueOf(location.getLatitude()), activity);
                                    PrefManager.setCURRENTLNG(String.valueOf(location.getLongitude()), activity);
                                    getAllProduc();

                                } else {
                                    getAllProduc();

                                }

                            }
                        });


            }
        });

        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(activity,
                                6);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });


    }


    public void getAllProduc(){

        requestQueue = Volley.newRequestQueue(activity);

        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("drm_all",response);

                allProductPojos.clear();
                mMap.clear();



                try {
                    JSONObject jsonObject=new JSONObject(response);
                 //   if (jsonObject.getString("status").equals("1")) {
                    JSONArray jsonArray=jsonObject.getJSONArray("offerlist");

                    Log.d("dfdsfsddf", String.valueOf(jsonArray.length()));



                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            Log.d("fdsfsdfsf", String.valueOf(jsonObject1));
                            Product product = new Product();


                                product.setOffertype(jsonObject1.getString("offer_type"));
                                product.setPricestatus(jsonObject1.getString("price_status"));
                                product.setOfferPlace(jsonObject1.getString("city"));
                                product.setAvailable(jsonObject1.getString("offer_status_text"));
                                product.setAcces(jsonObject1.getString("acces"));
                                product.setAddress(jsonObject1.getString("address"));
                                product.setType(jsonObject1.getString("type"));
                                product.setNom(jsonObject1.getString("nom"));
                                product.setPrice(jsonObject1.getString("offer_price"));
                                product.setUnit(jsonObject1.getString("stock_unit"));
                                product.setStock(jsonObject1.getString("stock"));
                                product.setFirstname(jsonObject1.getString("firstname"));
                                product.setLastname(jsonObject1.getString("lastname"));
                                product.setProductimg(jsonObject1.getString("offer_image"));
                                product.setProductid(jsonObject1.getString("offer_id"));
                                product.setProductname(jsonObject1.getString("offer_title"));
                                    product.setLat(jsonObject1.getString("latitude"));
                                    product.setLng(jsonObject1.getString("longitudeFrom"));
                                if(jsonObject1.has("is_treated_description")) {
                                    product.setDesc(jsonObject1.getString("is_treated_description"));

                                }
                        allProductPojos.add(product);
                        }


                    LatLng coordinate = new LatLng(46.2276, 2.2137);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                    mMap.animateCamera(location);
//                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        recitem.setLayoutManager(verticalLayoutManager);
//
//                        PicoDonationaroundmeAdapter adapter = new PicoDonationaroundmeAdapter(allProductPojos, getActivity());
//                        recitem.setAdapter(adapter);

                        mClusterManager.clearItems();

                        for (int j = 0; j < allProductPojos.size(); j++) {

                            Product allProductPojo = allProductPojos.get(j);

                            Double lat1 = Double.valueOf(allProductPojo.getLat());
                            Double lng1 = Double.valueOf(allProductPojo.getLng());
                            String title = allProductPojo.getProductname();
                            String snippet = activity.getResources().getString(R.string.moredetails);
                            String id = allProductPojo.getProductid();


                            MapCluster offsetItem = new MapCluster(id,title, allProductPojo.getProductimg(),
                                    allProductPojo.getStock(), allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getUnit(),allProductPojo.getOffertype(),
                                    allProductPojo.getPricestatus(),allProductPojo.getOfferPlace(),
                                    allProductPojo.getAvailable(),allProductPojo.getDesc(),allProductPojo.getPrice(),allProductPojo.getAddress(),allProductPojo.getNom(),allProductPojo.getType(),lat1,lng1,snippet);

                            mClusterManager.addItem(offsetItem);
                            mClusterManager.cluster();

                        }
                  //  }
//                         else{
//                        mMap.clear();
//                        recitem.setVisibility(View.GONE);
//                        mClusterManager.clearItems();
//                        Toast.makeText(activity, getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();
//
//                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("dfdfdsf",error.toString());

            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();

                param.put("searchbtn", "");
                param.put("user_id", PrefManager.getUserId(activity));

                //   if (lat1 != null && lon1 != null) {
                param.put("latitude", "");
                param.put("longitude", "");
                //  }

                return param;

            }
        };


        requestQueue.add(stringRequest);

    }



    @Override
    public void onClusterItemInfoWindowClick(MapCluster mapCluster) {


    }

    @Override
    public boolean onClusterItemClick(MapCluster mapCluster) {


        allProductPojos.clear();


            Product product = new Product();
            product.setProductid(mapCluster.getMmId());
            product.setProductname(mapCluster.getMmTitle());
            product.setProductimg(mapCluster.getMmGardenimg());
            product.setStock(mapCluster.getMmstock());
            product.setFirstname(mapCluster.getMmfirstname());
            product.setLastname(mapCluster.getMmlastname());
            product.setUnit(mapCluster.getMmstockunit());
            product.setOffertype(mapCluster.getMmoffer_type());
            product.setPricestatus(mapCluster.getMmpricestatus());
            product.setOfferPlace(mapCluster.getMmcity());
            product.setAvailable(mapCluster.getMmofferstatustext());
            product.setPrice(mapCluster.getMmoffer_price());
            product.setDesc(mapCluster.getMmdescription());
            product.setAddress(mapCluster.getMmaddress());
            product.setNom(mapCluster.getMmnom());
            product.setType(mapCluster.getMmtype());
            product.setLat(String.valueOf(mapCluster.getPosition().latitude));
            product.setLng(String.valueOf(mapCluster.getPosition().longitude));

            allProductPojos.add(product);


        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recitem.setLayoutManager(verticalLayoutManager);
        PicoDonationaroundmemapAdapter adapter = new PicoDonationaroundmemapAdapter(allProductPojos, getActivity());
        recitem.setAdapter(adapter);



        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<MapCluster> cluster) {

        float maxZoomLevel = mMap.getMaxZoomLevel();
        float currentZoomLevel = mMap.getCameraPosition().zoom;
        allProductPojos.clear();

        if(maxZoomLevel==currentZoomLevel){
            if(cluster.getSize()>1) {
                allProductPojos.clear();
                for (MapCluster mapCluster : cluster.getItems()) {





                    Product product = new Product();


                    product.setProductid(mapCluster.getMmId());
                    product.setProductname(mapCluster.getMmTitle());
                    product.setProductimg(mapCluster.getMmGardenimg());
                    product.setStock(mapCluster.getMmstock());
                    product.setFirstname(mapCluster.getMmfirstname());
                    product.setLastname(mapCluster.getMmlastname());
                    product.setUnit(mapCluster.getMmstockunit());
                    product.setOffertype(mapCluster.getMmoffer_type());
                    product.setPricestatus(mapCluster.getMmpricestatus());
                    product.setOfferPlace(mapCluster.getMmcity());
                    product.setAvailable(mapCluster.getMmofferstatustext());
                    product.setPrice(mapCluster.getMmoffer_price());
                    product.setDesc(mapCluster.getMmdescription());
                    product.setAddress(mapCluster.getMmaddress());
                    product.setNom(mapCluster.getMmnom());
                    product.setType(mapCluster.getMmtype());
                    product.setLat(String.valueOf(mapCluster.getPosition().latitude));
                    product.setLng(String.valueOf(mapCluster.getPosition().longitude));
                    allProductPojos.add(product);



                }

                Log.d("sadasd", String.valueOf(allProductPojos.size()));

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recitem.setLayoutManager(verticalLayoutManager);
                PicoDonationaroundmemapAdapter adapter = new PicoDonationaroundmemapAdapter(allProductPojos, getActivity());
                recitem.setAdapter(adapter);

            }
        }

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }

        final LatLngBounds bounds = builder.build();

        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MapCluster> cluster) {



    }



}
class CustomRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {
    public CustomRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {

        return cluster.getSize() > 1;
    }

}
