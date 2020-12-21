package com.app.fr.fruiteefy.user_client.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.volley.NetworkResponse;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Switch;
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
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_client.adapter.AllProductAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements Spinner.OnItemSelectedListener {

    View v;

    private String searchString = "",strcatname="",strsubcatname="",strcatdecs="",strsubcatdesc="",value;
    private TextView removecity, textviewproductsearch,Earsefilter;
    private String citylat = "0", citylng = "0";
    private RequestQueue requestQueue1;
    private StringRequest stringRequest1;
    private ImageView searchicon, searchcity;
    String category_id = "", subcategory_id = "", type_id = "", seller_type_id = "", selectedoffertype = "";
    ArrayList<AllProductPojo> allProductPojos = new ArrayList<AllProductPojo>();
    RecyclerView rv_all_products;
    private GoogleApiClient googleApiClient;
    private Location loc;
    private EditText edit_name;
    EditText productsearch;
    TextView citysearch;
    View view, view1;
    private TextView setkm, km, applyfilter;
    SeekBar seekBar, seekbarprice;
    private CardView filtecardview;
    AllProductAdapter adapter;
    private Activity activity;
    private String lat1, lon1;
    RelativeLayout rellay1, rellay2;
    private Spinner type_of_sellerspinner, spinner_cat, spinnersubcat, offertypespin;
    private FusedLocationProviderClient fusedLocationClient;
    ArrayList<String> category;
    private JSONArray resultcat, resultsubcat;
    TextView setprice;
    private ArrayList<String> sellertype;
    private ArrayList<String> offertype = new ArrayList<>();
    ArrayList<String> subcategory = new ArrayList<>();
    private Switch switchproductavailable;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest1;
    BottomNavigationView bottomNavigationView;
    private TextView searchin, filtericon;
    RelativeLayout tool2;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v = inflater.inflate(R.layout.fragment_home, container, false);

        activity = (UserCliantHomeActivity) getActivity();
        initView(v);

        sellertype = new ArrayList<>();
        offertypespin = activity.findViewById(R.id.offertypespin);
        filtecardview = v.findViewById(R.id.filtecardview);
        Earsefilter = activity.findViewById(R.id.Earsefilter);
        textviewproductsearch = v.findViewById(R.id.textviewproductsearch);
        edit_name = v.findViewById(R.id.edit_name);
        setprice = v.findViewById(R.id.setprice);
        rellay1 = activity.findViewById(R.id.rellay1);
        rellay2 = activity.findViewById(R.id.rellay2);
        searchcity = activity.findViewById(R.id.searchcity);
        spinner_cat = v.findViewById(R.id.spinner_cat);
        switchproductavailable = v.findViewById(R.id.switchproductavailable);
        applyfilter = v.findViewById(R.id.applyfilter);
        spinnersubcat = v.findViewById(R.id.spinnersubcat);
        type_of_sellerspinner = v.findViewById(R.id.type_of_sellerspinner);
        requestQueue1 = Volley.newRequestQueue(getContext());
        filtericon = activity.findViewById(R.id.filtericon);
        tool2 = activity.findViewById(R.id.tool2);
        searchin = v.findViewById(R.id.searchin);
        searchicon = activity.findViewById(R.id.searchicon);
        removecity = activity.findViewById(R.id.removecity);
        category = new ArrayList<>();
        seekBar = v.findViewById(R.id.seekbar);
        seekbarprice = v.findViewById(R.id.seekbarprice);
        setkm = v.findViewById(R.id.setkm);
        km = v.findViewById(R.id.km);

        view = activity.findViewById(R.id.citysearch);
        view1 = activity.findViewById(R.id.gardensearch);
        if (view instanceof TextView) {
            citysearch = (TextView) view;
        }

        if (view1 instanceof TextView) {
            productsearch = (EditText) view1;
        }
        searchin.setVisibility(View.GONE);
        searchicon.setVisibility(View.VISIBLE);
        filtericon.setVisibility(View.VISIBLE);

        citysearch.setVisibility(View.VISIBLE);
        productsearch.setVisibility(View.VISIBLE);
        tool2.setVisibility(View.VISIBLE);

        rellay1.setVisibility(View.VISIBLE);
        rellay2.setVisibility(View.VISIBLE);
        citysearch.setText("");
        productsearch.setText("");

        spinner_cat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);
        type_of_sellerspinner.setOnItemSelectedListener(this);



        offertypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                if (adapterView.getId() == R.id.offertypespin) {

                    selectedoffertype = adapterView.getItemAtPosition(i).toString();
//      Les Ventes
//        Les Ventes (Échanges possibles)
//        Les Dons
//        Les Échanges
//        Les Recherches
                    if (selectedoffertype.equals("Les Ventes"))   //activity.getResources().getString(R.string.sell_ot)
                    {
                        selectedoffertype = "vendre";
                        selectoffercalllist(selectedoffertype);
                    }
                    else if (selectedoffertype.equals("Les Ventes (Échanges possibles)")) //activity.getResources().getString(R.string.sell_ep_ot)
                    {
                        selectedoffertype = "echange possible";
                        selectoffercalllist(selectedoffertype);
                    }
                    else if (selectedoffertype.equals("Les Dons"))  //activity.getResources().getString(R.string.give_ot)
                    {
                        selectedoffertype = "donner";
                        selectoffercalllist(selectedoffertype);
                    }
                    else if (selectedoffertype.equals("Les Échanges")) //activity.getResources().getString(R.string.toexchange_ot)
                    {
                        selectedoffertype = "echanger";
                        selectoffercalllist(selectedoffertype);
                    }
                    else if (selectedoffertype.equals("Les Recherches"))  //activity.getResources().getString(R.string.search_ot)
                    {
                        selectedoffertype = "chercher";
                        selectoffercalllist(selectedoffertype);
                    } else if (selectedoffertype.equals("Type d’ annonce")) //activity.getResources().getString(R.string.select_offer_type
                    {
                        selectedoffertype = "";
                     //   selectoffercalllist(selectedoffertype);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subcategory.add(0, activity.getResources().getString(R.string.select));
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subcategory));


        Validation validation = new Validation();

        if (!validation.checkPermissions(activity)) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 2);

        } else {
            getGpsEnable();
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                 value = String.valueOf(progress);





            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setkm.setText(value);
                km.setText(" KM");
            }
        });

        removecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllProduc();
                searchin.setVisibility(View.GONE);
                citysearch.setText("");
                citylat = "0";
                citylng = "0";
            }
        });


        productsearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (productsearch.getText().toString().length() == 0) {

                    searchin.setVisibility(View.GONE);
                    getAllProduc();
                }

            }
        });


        searchcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Earsefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citysearch.setText("");
                citylat = "0";
                citylng = "0";
                productsearch.setText("");
                searchin.setVisibility(View.GONE);
                getAllProduc();
            }
        });

        rellay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(activity, activity.getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(activity);
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setTypeFilter(TypeFilter.REGIONS)
                        .setCountry("FR")
                        .build(activity);
                startActivityForResult(intent, 1);
            }
        });


        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           searchin.setVisibility(View.GONE);

//                Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));

                if (filtecardview.isShown()) {
                    filtecardview.setVisibility(View.GONE);


                } else {
                    filtecardview.setVisibility(View.VISIBLE);
                }

            }
        });


        seekbarprice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String value = String.valueOf(progress);

                setprice.setText(value);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        sellertype.add(0, activity.getResources().getString(R.string.select));
        sellertype.add(1, activity.getResources().getString(R.string.particular));
        sellertype.add(2, activity.getResources().getString(R.string.association));
        type_of_sellerspinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, sellertype));

//        Les Ventes
//        Les Ventes (Échanges possibles)
//        Les Dons
//        Les Échanges
//        Les Recherches

        //offertype spinner
        offertype.add(0, "Type d’ annonce");           //activity.getResources().getString(R.string.select_offer_type)
        offertype.add(1, "Les Ventes");                       //activity.getResources().getString(R.string.sell_ot)
        offertype.add(2, "Les Ventes (Échanges possibles)");            //activity.getResources().getString(R.string.sell_ep_ot)
        offertype.add(3, "Les Dons");                 //activity.getResources().getString(R.string.give_ot)
        offertype.add(4, "Les Échanges");         //activity.getResources().getString(R.string.toexchange_ot)
        offertype.add(5, "Les Recherches");      //activity.getResources().getString(R.string.search_ot)

        offertypespin.setAdapter(new ArrayAdapter<String>(activity, R.layout.simple_spinner_dropdown_item, offertype));


        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                allProductPojos.clear();


                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("get_product_list", response);
                        searchin.setVisibility(View.VISIBLE);

                        searchin.setText(activity.getResources().getString(R.string.searchtextproduct) + " " + productsearch.getText().toString() + "-" + citysearch.getText().toString());

//                        citysearch.setText("");
//                        productsearch.setText("");


                        try {


                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {
                                textviewproductsearch.setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            } else {
                                textviewproductsearch.setVisibility(View.VISIBLE);

                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        NetworkResponse networkResponse = volleyError.networkResponse;

                        Log.d("szddsa", String.valueOf(networkResponse.data));
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


                        if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                            param.put("latitude",PrefManager.getLAT(activity));
                            param.put("longitude", PrefManager.getLNG(activity));
                            Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
                        }
                        else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
                            Log.d("latlongcurrent",PrefManager.getCURRENTLAT(activity)+" "+ PrefManager.getCURRENTLNG(activity));
                        }
//                        param.put("latitude", citylat);
//                        param.put("longitude", citylng);


                        param.put("product_name", productsearch.getText().toString());
                        param.put("city", citysearch.getText().toString());


                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });


        applyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                allProductPojos.clear();
                CustomUtil.ShowDialog(activity,true);
                RequestQueue requestQueue = Volley.newRequestQueue(activity);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CustomUtil.DismissDialog(activity);
                     Log.d("get_product_list_f", response);
                      //  Log.d("strcatname", strcatname);
//                        if(strcatdecs != null || !strcatdecs.isEmpty())
//                        {
//
//                            searchin.setText(strcatdecs);
//                            searchin.setVisibility(View.VISIBLE);
//                        }
//                        if(strsubcatdesc != null || !strsubcatdesc.isEmpty())
//                        {
//                            searchin.setText(strsubcatdesc);
//                            searchin.setVisibility(View.VISIBLE);
//                        }
                        if(strcatname != null || !strcatname.isEmpty()
                        || strsubcatname != null || !strsubcatname.isEmpty()
                                || seller_type_id != null || !strcatname.isEmpty()
                                || edit_name.getText().toString() != null || !edit_name.getText().toString().isEmpty())
                        {
                            if(!strsubcatname.equals("") || !strcatname.equals("") || !seller_type_id.equals("") || !edit_name.getText().toString().equals(""))
                            {
                                searchin.setText(activity.getResources().getString(R.string.searchtextproduct) + " " +strcatname+"-"+strsubcatname+"-"+seller_type_id+"-"+edit_name.getText().toString());
                                searchin.setVisibility(View.VISIBLE);
                            }

                        }


                        try {


                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {
                                textviewproductsearch.setVisibility(View.GONE);
                                edit_name.setText("");
                                type_of_sellerspinner.setSelection(0);
                                spinner_cat.setSelection(0);
                                switchproductavailable.setChecked(false);
                                strcatname = "";

                                strsubcatname = "";
                                seller_type_id = "";

                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));


                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            } else {
                                textviewproductsearch.setVisibility(View.VISIBLE);

                                edit_name.setText("");
                                type_of_sellerspinner.setSelection(0);
                                spinner_cat.setSelection(0);
                                switchproductavailable.setChecked(false);
                                strcatname= "";
                                strsubcatname = "";
                                seller_type_id = "";

                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));


                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            CustomUtil.DismissDialog(activity);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        CustomUtil.DismissDialog(activity);
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
                        Log.d("checking",category_id);
                        Map<String, String> param = new HashMap<>();
                        param.put("searchbtn", "filter");
                        if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                            param.put("latitude",PrefManager.getLAT(activity));
                            param.put("longitude", PrefManager.getLNG(activity));
                            Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
                        }
                        else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
                            Log.d("latlongcurrent",PrefManager.getCURRENTLAT(activity)+" "+ PrefManager.getCURRENTLNG(activity));
                        }

//                        if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
//
//                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                        } else if (!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getLAT(activity));
//                            param.put("longitude", PrefManager.getLNG(activity));
//                        } else {
//                            param.put("latitude", "46.2276");
//                            param.put("longitude", "2.2137");
//                        }
                        param.put("cat_id", category_id);
                        param.put("subcat_id", subcategory_id);
                        param.put("minprice", "");
                        param.put("maxprice", setprice.getText().toString());

                        if (switchproductavailable.isChecked()) {
                            param.put("available", "1");
                        }
                        if (!switchproductavailable.isChecked()) {
                            param.put("available", "0");

                        }
                        param.put("distance",setkm.getText().toString());
                        param.put("vendor_name", edit_name.getText().toString());
                        param.put("vendor_type", seller_type_id);
                        param.put("offer_type",selectedoffertype);

                        Log.d("filteroffrtype",selectedoffertype);
                        Log.d("setprice",setprice.getText().toString());



                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });
//get_picorear_category
        stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("get_anti_category", response);
                category.add(activity.getResources().getString(R.string.select));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resultcat = jsonObject.getJSONArray("catlist");

Log.d("catlistlength", String.valueOf(resultcat.length()));
                    for (int i = 0; i < resultcat.length(); i++) {

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
                //   param.put("user_id", PrefManager.getUserId(activity));
                return param;
            }

        };


        requestQueue1.add(stringRequest1);


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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
Log.d("testingadapter","parichay");

        if (adapterView.getId() == R.id.spinner_cat) {

            Log.d("dsfdsfds", String.valueOf(i));
            spinnersubcat.setSelection(0);
            String selectedItem = adapterView.getItemAtPosition(i).toString();

            Log.d("kjjjjjkkjkjjk", selectedItem);
            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                category_id = "";
            } else {
                subcategory.clear();
                category_id = getCatId(i - 1);

                requestQueue1 = Volley.newRequestQueue(activity);//get_picorear_subcategory
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("get_anti_subcategory", response);

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
                        //   param.put("user_id", PrefManager.getUserId(activity));
                        param.put("cat_id", category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
        } else if (adapterView.getId() == R.id.spinnersubcat) {


            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                subcategory_id = "";
            } else {
                subcategory_id = getsubCatId(i - 1);

                Log.d("sdfdsf", subcategory_id);
            }

        }
        else if (adapterView.getId() == R.id.type_of_sellerspinner) {
            String selectedItem = adapterView.getItemAtPosition(i).toString();


            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                seller_type_id = "";
            } else if (selectedItem.equals(activity.getResources().getString(R.string.particular))) {
                seller_type_id = "Individual";

            } else if (selectedItem.equals(activity.getResources().getString(R.string.association))) {
                seller_type_id = "Company";

            }

        }
//
    }

    private void selectoffercalllist(String selectedoffertype) {
        CustomUtil.ShowDialog(activity,true);
        allProductPojos.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CustomUtil.DismissDialog(activity);
                Log.d("product_offer_type", response);
                searchin.setVisibility(View.VISIBLE);
                searchin.setText(activity.getResources().getString(R.string.searchtextproduct) + "-" +selectedoffertype);
                try {

                    filtecardview.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equals("1")) {
                        textviewproductsearch.setVisibility(View.GONE);
//offertypespin.setSelection(0);
                        JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AllProductPojo allProductPojo = new AllProductPojo();
                            allProductPojo.setProductId(jsonObject1.getString("product_id"));
                            allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                            allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                            allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                            allProductPojo.setPrice(jsonObject1.getString("price"));
                            allProductPojo.setProductStock(jsonObject1.getString("stock"));
                            allProductPojo.setWeight(jsonObject1.getString("weight"));
                            allProductPojo.setUnit(jsonObject1.getString("unit"));
                            allProductPojo.setDistance(jsonObject1.getString("km"));


                            if (jsonObject1.getString("rating").equals("null")) {
                                allProductPojo.setRating("0");
                            } else {
                                allProductPojo.setRating(jsonObject1.getString("rating"));
                            }

                            allProductPojos.add(allProductPojo);


                        }

                        adapter = new AllProductAdapter(allProductPojos, activity);
                        rv_all_products.setAdapter(adapter);
                        rv_all_products.setHasFixedSize(true);
                        rv_all_products.setItemViewCacheSize(20);
                        rv_all_products.setDrawingCacheEnabled(true);
                        rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    } else {
                        textviewproductsearch.setVisibility(View.VISIBLE);
//                        offertypespin.setSelection(0);
                        JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            AllProductPojo allProductPojo = new AllProductPojo();
                            allProductPojo.setProductId(jsonObject1.getString("product_id"));
                            allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                            allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                            allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                            allProductPojo.setPrice(jsonObject1.getString("price"));
                            allProductPojo.setProductStock(jsonObject1.getString("stock"));
                            allProductPojo.setWeight(jsonObject1.getString("weight"));
                            allProductPojo.setUnit(jsonObject1.getString("unit"));
                            allProductPojo.setDistance(jsonObject1.getString("km"));


                            if (jsonObject1.getString("rating").equals("null")) {
                                allProductPojo.setRating("0");
                            } else {
                                allProductPojo.setRating(jsonObject1.getString("rating"));
                            }

                            allProductPojos.add(allProductPojo);


                        }

                        adapter = new AllProductAdapter(allProductPojos, activity);
                        rv_all_products.setAdapter(adapter);
                        rv_all_products.setHasFixedSize(true);
                        rv_all_products.setItemViewCacheSize(20);
                        rv_all_products.setDrawingCacheEnabled(true);
                        rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    CustomUtil.DismissDialog(activity);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomUtil.DismissDialog(activity);
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
//                param.put("searchbtn", "filter");
//
//                if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
//
//                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                } else if (!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")) {
//                    param.put("latitude", PrefManager.getLAT(activity));
//                    param.put("longitude", PrefManager.getLNG(activity));
//                } else {
//                    param.put("latitude", "46.2276");
//                    param.put("longitude", "2.2137");
//                }
//                param.put("cat_id", category_id);
//                param.put("subcat_id", subcategory_id);
//                param.put("minprice", "");
//                param.put("maxprice", setprice.getText().toString());
//
//                if (switchproductavailable.isChecked()) {
//                    param.put("available", "1");
//                }
//                if (!switchproductavailable.isChecked()) {
//                    param.put("available", "0");
//
//                }
//                param.put("distance", setkm.getText().toString());
//                param.put("vendor_name", edit_name.getText().toString());
//                param.put("vendor_type", seller_type_id);
                if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                    param.put("latitude",PrefManager.getLAT(activity));
                    param.put("longitude", PrefManager.getLNG(activity));
                    Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
                }
                else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
                    Log.d("latlongcurrent",PrefManager.getCURRENTLAT(activity)+" "+ PrefManager.getCURRENTLNG(activity));
                }
                param.put("offer_type",selectedoffertype);

                param.put("cat_id", category_id);
                param.put("subcat_id", subcategory_id);
                param.put("minprice", "");
                param.put("maxprice", setprice.getText().toString());

                if (switchproductavailable.isChecked()) {
                    param.put("available", "1");
                }
                if (!switchproductavailable.isChecked()) {
                    param.put("available", "0");

                }
                param.put("distance", setkm.getText().toString());
                param.put("vendor_name", edit_name.getText().toString());
                param.put("vendor_type", seller_type_id);



                return param;
            }
        };

        requestQueue.add(stringRequest);



    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getCatId(int position) {
        String Catid = "";
        try {
            JSONObject json = resultcat.getJSONObject(position);

            Catid = json.getString("cat_id");
            strcatname = json.getString("cat_name");
            strcatdecs = json.getString("cat_description");
            Log.d("sdfgfgffgfsdsd", Catid);
            Log.d("strcatdecs",strcatdecs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return Catid;
    }


    private void getSubCatrName(JSONArray j) {
        try {

            subcategory.add(activity.getResources().getString(R.string.select));
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
            strsubcatname= json.getString("subcat_name");
            strsubcatdesc = json.getString("subcat_desc");
            Log.d("sdsdsd", subCatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return subCatid;
    }


    public void initView(View v) {
        rv_all_products = v.findViewById(R.id.rv_all_products);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
    }
    //initViewClose


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

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                    5);
                        } catch (IntentSender.SendIntentException sendEx) {

                        }
                    }
                }
            });


    }


    public void getAllProduc() {

//        CustomUtil.ShowDialog(activity,true);

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getproductlist_main", response);
//                CustomUtil.DismissDialog(activity);

                allProductPojos.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setProductId(jsonObject1.getString("product_id"));
                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                        allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                        allProductPojo.setPrice(jsonObject1.getString("price"));
                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
                        allProductPojo.setWeight(jsonObject1.getString("weight"));
                        allProductPojo.setUnit(jsonObject1.getString("unit"));
                        allProductPojo.setDistance(jsonObject1.getString("km"));

                        if(jsonObject1.getString("rating").equals("null")){
                            allProductPojo.setRating("0");
                        }
                        else {
                            allProductPojo.setRating(jsonObject1.getString("rating"));
                        }

                        allProductPojos.add(allProductPojo);


                    }

                    adapter = new AllProductAdapter(allProductPojos, activity);
                    rv_all_products.setAdapter(adapter);
                    rv_all_products.setHasFixedSize(true);
                    rv_all_products.setItemViewCacheSize(20);
                    rv_all_products.setDrawingCacheEnabled(true);
                    rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                } catch (JSONException e) {
                   // CustomUtil.DismissDialog(activity);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
//                CustomUtil.DismissDialog(activity);

                Log.d("dsdsdasdsd",volleyError.toString());
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
                param.put("searchbtn", "");

                   if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                    param.put("latitude",PrefManager.getLAT(activity));
                    param.put("longitude", PrefManager.getLNG(activity));
                    Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
                }
                   else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
                    Log.d("latlongcurrent",PrefManager.getCURRENTLAT(activity)+" "+ PrefManager.getCURRENTLNG(activity));
                }
//                else if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
//                    param.put("latitude",PrefManager.getLAT(activity));
//                    param.put("longitude", PrefManager.getLNG(activity));
//                    Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
//                }
//                else{
//                    param.put("latitude","46.2276");
//                    param.put("longitude","2.2137");
//                    Log.d("latlong","46.2276"+" "+ "2.2137");
//                }
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("fdffsf",Autocomplete.getPlaceFromIntent(data).getAddress());

                citysearch.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
                citylat= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                citylng= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);





                allProductPojos.clear();




                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("cityresponse", response);

                        searchin.setVisibility(View.VISIBLE);

                        searchin.setText(activity.getResources().getString(R.string.searchtextproduct)+" "+productsearch.getText().toString()+"-"+citysearch.getText().toString());

//                        citysearch.setText("");
//                        productsearch.setText("");
                        try {

                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject=new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {
                                textviewproductsearch.setVisibility(View.GONE);

                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                            }
                            else{

                                textviewproductsearch.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }

                                adapter = new AllProductAdapter(allProductPojos, activity);
                                rv_all_products.setAdapter(adapter);
                                rv_all_products.setHasFixedSize(true);
                                rv_all_products.setItemViewCacheSize(20);
                                rv_all_products.setDrawingCacheEnabled(true);
                                rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();
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

//                        if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
//                            param.put("latitude",PrefManager.getLAT(activity));
//                            param.put("longitude", PrefManager.getLNG(activity));
//                            Log.d("latlong",PrefManager.getLAT(activity)+" "+ PrefManager.getLNG(activity));
//                        }
//                        else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
//                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                            Log.d("latlongcurrent",PrefManager.getCURRENTLAT(activity)+" "+ PrefManager.getCURRENTLNG(activity));
//                        }
                        param.put("latitude",citylat);
                        param.put("longitude",citylng);
                        Log.d("citylatlong",citylat+" "+citylng);
                        param.put("product_name",productsearch.getText().toString());
                        param.put("city",citysearch.getText().toString());

                        Log.d("asdsada",citysearch.getText().toString());
                        Log.d("asdsada",citylat);
                        Log.d("asdsada",citylng);




                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }

            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.d("fdgfdgfd", String.valueOf(status));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }



}
