package com.app.fr.fruiteefy.user_client.home;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_client.adapter.homeGardenAdapter;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GardenFragment extends Fragment implements Spinner.OnItemSelectedListener {


    private TextView setkm, applyfilter, km,remove;
    private EditText edit_name;
    private RecyclerView recvirewgarden;
    private TextView searchin;
    private String citylat="0",citylng="0";
    private String lastName = "";
    private String firstName = "";
    private String selleractive="";
    private String category_id = "", subcategory_id = "", type_id = "",seller_type_id="";
    private RequestQueue requestQueue, requestQueue1, requestQueue2, requestQueue3;
    private ImageView  search, citysearchimg;
    private  TextView Earsefilter,filter;
    private StringRequest stringRequest, stringRequest1, stringRequest3;
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> subcategory = new ArrayList<>();
    private String searchString = "";
    private Spinner type_of_sellerspinner, typespinner, activesellerspinner, spinnercat, spinnersubcat;
    private SeekBar seekBar;
    private CardView filtecardview;
    private ArrayList<String> sellertype;
    private ArrayList<String> activeseller;
    private ArrayList<String> gardentypes;
    public static int LENGTH_MIN_VALUE = 0;
    private JSONArray result, result1, resultcat, resultgarden;
    public static int LENGTH_MAX_VALUE = 100;
    private Activity activity;
    private View view, view1;
    private EditText  gardensearch;
    private RelativeLayout rellay;
    private TextView citysearch;
    private homeGardenAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<AllProductPojo> antiprodArr = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_garden, container, false);


        activity = (GardensActivity) getActivity();





        sellertype=new ArrayList<>();
        activeseller=new ArrayList<>();
        gardentypes=new ArrayList<>();

        view = activity.findViewById(R.id.citysearch);
        remove=activity.findViewById(R.id.remove);
        rellay=activity.findViewById(R.id.rellay);
        view1 = activity.findViewById(R.id.gardensearch);
        recvirewgarden = v.findViewById(R.id.recvirewgarden);
        searchin=v.findViewById(R.id.searchin);
        edit_name = v.findViewById(R.id.edit_name);
        km = v.findViewById(R.id.km);
        activesellerspinner= v.findViewById(R.id.activeseller);
        applyfilter= v.findViewById(R.id.applyfilter);
        type_of_sellerspinner = v.findViewById(R.id.type_of_seller);
        spinnercat= v.findViewById(R.id.spinner_cat);
        spinnersubcat= v.findViewById(R.id.spinnersubcat);
        typespinner= v.findViewById(R.id.typespinner);
        setkm = v.findViewById(R.id.setkm);
        seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        filtecardview=v.findViewById(R.id.filtecardview);
        filter=activity.findViewById(R.id.filtericon);
        Earsefilter = activity.findViewById(R.id.Earsefilter);
        search=activity.findViewById(R.id.searchicon);
        citysearchimg=activity.findViewById(R.id.searchcity);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue2= Volley.newRequestQueue(activity);
        requestQueue1= Volley.newRequestQueue(activity);
        requestQueue3= Volley.newRequestQueue(activity);


        if (view instanceof TextView) {
            citysearch = ( TextView) view;
        }

        if (view1 instanceof TextView) {
            gardensearch = (EditText) view1;
        }

        citysearch.setText("");
        gardensearch.setText("");
        searchin.setVisibility(View.GONE);


        sellertype.add(0,activity.getResources().getString(R.string.select));
        sellertype.add(1,activity.getResources().getString(R.string.particular));
        sellertype.add(2,activity.getResources().getString(R.string.association));

        activeseller.add(0,activity.getResources().getString(R.string.select));
        activeseller.add(1,activity.getResources().getString(R.string.yes));
        activeseller.add(2,activity.getResources().getString(R.string.no));

        type_of_sellerspinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, sellertype));
        activesellerspinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item,activeseller ));

        subcategory.add(0,activity.getResources().getString(R.string.select));
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item,subcategory ));


        gardensearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(gardensearch.getText().toString().length()==0) {

                    searchin.setVisibility(View.GONE);
                    getGardenlist();
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citysearch.setText("");
                citylat="0";
                citylng="0";

                getGardenlist();
                searchin.setVisibility(View.GONE);
            }
        });

        Validation validation = new Validation();

        if (!validation.checkPermissions(activity)) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            },2);

        } else {

            getGpsEnable();

        }




        stringRequest3=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_types"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("sdsfasf",response);

                gardentypes.add(activity.getResources().getString(R.string.select));

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    resultgarden = jsonObject.getJSONArray("garden_types");

                    for (int i = 0; i <= resultgarden.length(); i++) {

                        JSONObject json = resultgarden.getJSONObject(i);

                        gardentypes.add(json.getString("dropdown"));

                        typespinner.setAdapter(new ArrayAdapter<String> ( activity, android.R.layout.simple_spinner_dropdown_item, gardentypes));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsddd",error.toString());

            }
        });


        requestQueue3.add(stringRequest3);



        stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("sdasdada",response);

                category.add(activity.getResources().getString(R.string.select));
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    resultcat = jsonObject.getJSONArray("catlist");






                    for (int i = 0; i <= resultcat.length(); i++) {

                        JSONObject json = resultcat.getJSONObject(i);

                        category.add(json.getString("cat_name"));

                        spinnercat.setAdapter(new ArrayAdapter<String> ( activity, android.R.layout.simple_spinner_dropdown_item, category));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dsdsddd",error.toString());

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                return param;
            }

        };


        requestQueue1.add(stringRequest1);




        type_of_sellerspinner.setOnItemSelectedListener(this);
        typespinner.setOnItemSelectedListener(this);
        activesellerspinner.setOnItemSelectedListener(this);
        spinnercat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);



        seekBar.setMax(LENGTH_MAX_VALUE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String value= String.valueOf(progress);

                setkm.setText(value);

                km.setText(" KM");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));

                if(filtecardview.isShown()){
                    filtecardview.setVisibility(View.GONE);


                }
                else{
                    filtecardview.setVisibility(View.VISIBLE);
                }

            }
        });

        Earsefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citysearch.setText("");
                citylat="0";
                citylng="0";
gardensearch.setText("");
                getGardenlist();
                searchin.setVisibility(View.GONE);
            }
        });
        rellay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(activity,  activity.getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(activity);
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setTypeFilter(TypeFilter.REGIONS)
                        .setCountry("FR")
                        .build(activity);
                startActivityForResult(intent, 1);
            }
        });




        citysearchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                antiprodArr.clear();

                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        searchin.setVisibility(View.VISIBLE);

                        searchin.setText(activity.getResources().getString(R.string.searchtext)+" "+gardensearch.getText().toString()+"-"+citysearch.getText().toString());

//                        citysearch.setText("");
//                        gardensearch.setText("");


                        antiprodArr.clear();

                        Log.d("searchin", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));

                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            }
                            else{
                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("dfdfdsf", error.toString());

                    }
                }) {



                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();

                        if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                            param.put("latitude",PrefManager.getLAT(activity));
                            param.put("longitude", PrefManager.getLNG(activity));
                        }
                        else  if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
                        }

//                            param.put("latitude",citylat);
//                            param.put("longitude",citylng);



//                        param.put("searchbtn","search");
//                        param.put("searchkey",gardensearch.getText().toString());
                        param.put("city",citysearch.getText().toString());
                        param.put("garden_name",gardensearch.getText().toString());
                        param.put("searchbtn","filter");



                        return param;


                    }
                };

                requestQueue.add(stringRequest);



            }
        });

        applyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searchin.setVisibility(View.GONE);
                antiprodArr.clear();
                String name = edit_name.getText().toString();

                if(name.split("\\w+").length>1){

                    lastName = name.substring(name.lastIndexOf(" ")+1);
                    firstName = name.substring(0, name.lastIndexOf(' '));
                }
                else{
                    firstName = name;
                }


                filtecardview.setVisibility(View.GONE);

                antiprodArr.clear();

                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        Log.d("adasdssddddvvvdvsdsdsdd", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            
                            

                            if(jsonObject.getString("status").equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));

                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                adapter.notifyDataSetChanged();
                            }
                            else{


                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);
                                    Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                adapter.notifyDataSetChanged();

                                Toast.makeText(activity, activity.getResources().getString(R.string.nomatchingresult), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("dfdfdsf", error.toString());

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();


//                        if (lat1 != null && lon1 != null) {
//                            param.put("latitude", lat1);
//                            param.put("longitude", lon1);
//
//                        }
//                        else {

                         if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                            param.put("latitude",PrefManager.getLAT(activity));
                            param.put("longitude", PrefManager.getLNG(activity));
                            Log.d("dsdsadsd",PrefManager.getLAT(activity));
                            Log.d("dsdsadsd",PrefManager.getLNG(activity));

                        }
                         else if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                             param.put("latitude", PrefManager.getCURRENTLAT(activity));
                             param.put("longitude", PrefManager.getCURRENTLNG(activity));

                             Log.d("dsdsadsd",PrefManager.getCURRENTLAT(activity));
                             Log.d("dsdsadsd",PrefManager.getCURRENTLNG(activity));
                         }
//                        else{
//                            param.put("latitude","46.2276");
//                            param.put("longitude","2.2137");
//                            Log.d("dsdsadsd","46.2276");
//                            Log.d("dsdsadsd","2.2137");
//                        }

                        param.put("searchbtn","filter");
                        param.put("distance",setkm.getText().toString());
                        param.put("vendor_type",seller_type_id);

                        param.put("vendor_fname",firstName);
                        param.put("vendor_lname",lastName);
                        param.put("type",type_id);
                        param.put("catlist",category_id);
                        param.put("sub_catlist",subcategory_id);
                        param.put("active_seller", selleractive);

                        Log.d("dsdsds",setkm.getText().toString());
                        Log.d("dsdsds",seller_type_id);
                        Log.d("dsdsds",firstName);
                        Log.d("dsdsds",lastName);
                        Log.d("dsdsds",setkm.getText().toString());
                        Log.d("dsdsds",selleractive);
                       // param.put("city",citysearch.getText().toString());
                      //  param.put("garden_name",gardensearch.getText().toString());




                        return param;


                    }
                };

                requestQueue.add(stringRequest);


            }
        });


        return v;
    }

    private void getGpsEnable(){


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

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {

                                    Log.d("sdsadasd",String.valueOf(location.getLatitude()));

                                    PrefManager.setCURRENTLAT(String.valueOf(location.getLatitude()),activity);
                                    PrefManager.setCURRENTLNG(String.valueOf(location.getLongitude()),activity);
                                    getGardenlist();

                                }
                                else{
                                    getGardenlist();

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







////done/////


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {


        if(adapterView.getId() == R.id.spinner_cat)

        {


            spinnersubcat.setSelection(0);
            String selectedItem = adapterView.getItemAtPosition(i).toString();

            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                category_id = "";
            } else {
                subcategory.clear();
                category_id = getCatId(i - 1);


                requestQueue1 = Volley.newRequestQueue(activity);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("dsdsddd", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            result1 = jsonObject.getJSONArray("subcatlist");
                            getSubCatrName(result1);
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
                        param.put("cat_id", category_id);
                        Log.d("ddasdsdad",category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
        }

        else if(adapterView.getId() == R.id.spinnersubcat)
        {


            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                subcategory_id = "";
            } else {
                subcategory_id = getsubCatId(i - 1);

            }
        }

        else if(adapterView.getId()==R.id.type_of_sellerspinner){
            String selectedItem = adapterView.getItemAtPosition(i).toString();


            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                seller_type_id = "";
            } else if(selectedItem.equals(activity.getResources().getString(R.string.particular))){
                seller_type_id = "Individual";

            }else if(selectedItem.equals(activity.getResources().getString(R.string.association))){
                seller_type_id = "Company";

            }




        }

        else if(adapterView.getId()==R.id.activeseller){
            String selectedItem = adapterView.getItemAtPosition(i).toString();



            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                selleractive = "";
            } else if(selectedItem.equals(activity.getResources().getString(R.string.yes))){
                selleractive = "1";

            }else if(selectedItem.equals(activity.getResources().getString(R.string.no))){
                selleractive = "2";

            }




        }


        else if(adapterView.getId() == R.id.typespinner){

            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                type_id = "";
            } else {
                type_id = gettypeidId(i - 1);

            }
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private String getCatId(int position){
        String Catid="";
        try {
            JSONObject json = resultcat.getJSONObject(position);

            Catid = json.getString("cat_id");
            Log.d("sdfgfgffgfsdsd",Catid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return Catid;
    }


    private String gettypeidId(int position){
        String type_id="";
        try {
            JSONObject json = resultgarden.getJSONObject(position);

            type_id = json.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return type_id;
    }




    private String getsubCatId(int position){
        String subCatid="";
        try {
            JSONObject json = result1.getJSONObject(position);

            subCatid = json.getString("subcat_id");
            Log.d("sdsdsd",subCatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return subCatid;
    }


    private  void getSubCatrName(JSONArray j)  {
        try{

            subcategory.add(activity.getResources().getString(R.string.select));
            for (int i = 0; i <= j.length(); i++) {
                JSONObject json = j.getJSONObject(i);
                subcategory.add(json.getString("subcat_name"));
                Log.d("sdsdsddsd", String.valueOf(subcategory));

            }
        }catch(JSONException e){

        }
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subcategory));

    }

    public void getGardenlist() {


        stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                antiprodArr.clear();

                Log.d("adasdssddddvvvdvsdsdsdd", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setUserid(jsonObject1.getString("user_id"));
                        allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                        allProductPojo.setLastname(jsonObject1.getString("lastname"));
                        allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                        allProductPojo.setAbout(jsonObject1.getString("about_me"));
                        allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                        allProductPojo.setKilometer(jsonObject1.getString("km"));
                        allProductPojo.setRating(jsonObject1.getString("rating"));
                        allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                        allProductPojo.setLat(jsonObject1.getString("lat"));
                        allProductPojo.setLng(jsonObject1.getString("lng"));



                        antiprodArr.add(allProductPojo);


                    }
                    Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                    adapter = new homeGardenAdapter(antiprodArr, activity);
                    recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                    recvirewgarden.setAdapter(adapter);
                    recvirewgarden.setHasFixedSize(true);
                    recvirewgarden.setItemViewCacheSize(20);
                    recvirewgarden.setDrawingCacheEnabled(true);
                    recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("adasdssddddvvvdvsdsdsdd", volleyError.toString());

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
                if (message != null && !message.equals("")) {

                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();


                 if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
                    param.put("latitude",PrefManager.getLAT(activity));
                    param.put("longitude", PrefManager.getLNG(activity));
                    Log.d("savelatng",PrefManager.getLAT(activity)+" "+PrefManager.getLNG(activity));
                }
                else  if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
                     Log.d("currentlatng",PrefManager.getCURRENTLAT(activity)+" "+PrefManager.getCURRENTLNG(activity));
                }
//                else{
//                    param.put("latitude","46.2276");
//                    param.put("longitude","2.2137");
//                }


                return param;
            }
        };
        requestQueue.add(stringRequest);

    }



    //////  done////////










    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                citysearch.setText(Autocomplete.getPlaceFromIntent(data).getAddress());

                citylat= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                citylng= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);




                antiprodArr.clear();

                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        antiprodArr.clear();
                        searchin.setVisibility(View.VISIBLE);

                        searchin.setText(activity.getResources().getString(R.string.searchtext)+" "+gardensearch.getText().toString()+"-"+citysearch.getText().toString());

//                        citysearch.setText("");
//                        gardensearch.setText("");

                        Log.d("sdadsdasda", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));

                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            }
                            else{
                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

                                adapter = new homeGardenAdapter(antiprodArr, activity);
                                recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
                                recvirewgarden.setAdapter(adapter);
                                recvirewgarden.setHasFixedSize(true);
                                recvirewgarden.setItemViewCacheSize(20);
                                recvirewgarden.setDrawingCacheEnabled(true);
                                recvirewgarden.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                                Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("dfdfdsf", error.toString());

                    }
                }) {



                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
//
//                        if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
//                            param.put("latitude",PrefManager.getLAT(activity));
//                            param.put("longitude", PrefManager.getLNG(activity));
//                        }
//                        else  if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
//                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                        }
                        param.put("latitude",citylat);
                        param.put("longitude",citylng);


                        param.put("city",citysearch.getText().toString());
                        param.put("garden_name",gardensearch.getText().toString());
                        param.put("searchbtn","filter");



                        return param;


                    }
                };

                requestQueue.add(stringRequest);
            }

            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }

        }





    }



}
