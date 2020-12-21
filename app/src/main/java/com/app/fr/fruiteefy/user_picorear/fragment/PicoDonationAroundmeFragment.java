package com.app.fr.fruiteefy.user_picorear.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.Util.ChooseHellperClass;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterAccess;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.AdapterChoose;
import com.app.fr.fruiteefy.user_antigaspi.AntigaspiAdapter.Adapterlastname;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import com.app.fr.fruiteefy.Util.Product;

import com.app.fr.fruiteefy.user_picorear.Adapter.PicoDonationaroundmeAdapter;
import com.app.fr.fruiteefy.user_picorear.PicoMySaleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PicoDonationAroundmeFragment extends Fragment implements SearchView.OnQueryTextListener, Spinner.OnItemSelectedListener {

    View v;
    ArrayList<Product> allProductPojos = new ArrayList<Product>();
    RecyclerView rv_all_products;
    BottomNavigationView bottomNavigationView;
    CardView filtecardview;
    private RelativeLayout tool, tool1, tool3;
    private Activity activity;
    private String category_id = "", subcategory_id = "";
    //View view,view1;
    SeekBar seekBar;
    RequestQueue requestQueuechoose;
    StringRequest stringRequestchoose;
    JSONArray resultchoose;
    private Spinner spinner_cat, spinnersubcat, spinner_status, spinner_accesss, spinner_type_d_s, spinner_lastname;
    private JSONArray resultcat, resultsubcat;
    TextView myorder, mySale, proceed_from_donation, setkm, km, Earsefilter, hideunavailable;
    LinearLayout linlaypico;
    private FusedLocationProviderClient fusedLocationClient;
    EditText gardensearch;

    TextView mapview, applyfilter, textviewtextdonation, filtericon;
    RequestQueue requestQueue1;
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> subcategory = new ArrayList<>();
    private ArrayList<String> arraytypeds = new ArrayList<>();
    private ArrayList<String> arrlastname = new ArrayList<>();
    private ArrayList<String> arraceess = new ArrayList<>();
    private ArrayList<String> arrstatus = new ArrayList<>();
    ArrayList<ChooseHellperClass> chooselist = new ArrayList<>();
    StringRequest stringRequest1;
    //SearchView searchview;
    ImageView searchicon;
    private String searchString = "", strselectedtypeds = "", strselectedlastname = "", strselectedaccess = "", strselectedstatus = "";
    PicoDonationaroundmeAdapter adapter;

    public PicoDonationAroundmeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_picohome, container, false);
        activity = (UserAntigaspiHomeActivity) getActivity();
        initView(v);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestQueuechoose= Volley.newRequestQueue(activity);
        calladdresslist();


        tool = activity.findViewById(R.id.tool);
        tool1 = activity.findViewById(R.id.tool2);
        tool3 = activity.findViewById(R.id.tool3);

        tool3.setVisibility(View.VISIBLE);
        tool1.setVisibility(View.VISIBLE);
        tool.setVisibility(View.GONE);

        setkm = v.findViewById(R.id.setkm);
        km = v.findViewById(R.id.km);

        spinner_type_d_s = v.findViewById(R.id.spinner_type_d_s);
        spinner_lastname = v.findViewById(R.id.spinner_lastname);
        spinner_accesss = v.findViewById(R.id.spinner_accesss);
        spinner_status = v.findViewById(R.id.spinner_status);

        spinner_cat = v.findViewById(R.id.spinner_cat);
        textviewtextdonation = v.findViewById(R.id.textviewtextdonation);
        spinnersubcat = v.findViewById(R.id.spinnersubcat);
        mapview = v.findViewById(R.id.mapview);
        seekBar = v.findViewById(R.id.seekbar);
        applyfilter = v.findViewById(R.id.applyfilter);
        myorder.setVisibility(View.GONE);


        spinner_cat.setOnItemSelectedListener(this);
        spinnersubcat.setOnItemSelectedListener(this);
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





        Validation validation = new Validation();

        if (!validation.checkPermissions(activity)) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 2);

        } else {
            getGpsEnable();
        }


        mySale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, PicoMySaleActivity.class));

            }
        });


        applyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomUtil.ShowDialog(activity,true);
                requestQueue1 = Volley.newRequestQueue(activity);

                filtecardview.setVisibility(View.GONE);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        CustomUtil.DismissDialog(activity);
                        allProductPojos.clear();

                        Log.d("drm_filter", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                            Log.d("offerlistsize", String.valueOf(jsonArray.length()));


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Log.d("offerlistsize", String.valueOf(jsonObject1));
                                Product product = new Product();
                                product.setProductid(jsonObject1.getString("offer_id"));
                                product.setProductname(jsonObject1.getString("offer_title"));
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
//                              product.setWeight(jsonObject1.getString("weight"));
                                product.setPrice(jsonObject1.getString("offer_price"));
                                if (jsonObject1.has("is_treated_description")) {
                                    product.setDesc(jsonObject1.getString("is_treated_description"));


                                }
                                allProductPojos.add(product);
                            }

                            rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                            adapter = new PicoDonationaroundmeAdapter(allProductPojos, activity);
                            rv_all_products.setAdapter(adapter);
                            rv_all_products.setHasFixedSize(true);
                            rv_all_products.setItemViewCacheSize(20);
                            rv_all_products.setDrawingCacheEnabled(true);
                            rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            rv_all_products.getAdapter().notifyDataSetChanged();


                            Log.d("offerlistsize", String.valueOf(allProductPojos.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CustomUtil.DismissDialog(activity);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomUtil.DismissDialog(activity);
                        Log.d("applyfilter", error.toString());

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("searchbtn", "filter");
                        param.put("cat_id", category_id);
                        param.put("subcat_id", subcategory_id);
                        Log.d("catsubcat",category_id+" "+subcategory_id);
                        param.put("user_id", PrefManager.getUserId(activity));
                        if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
                        } else if (!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")) {
                            param.put("latitude", PrefManager.getLAT(activity));
                            param.put("longitude", PrefManager.getLNG(activity));
                        } else {
                            param.put("latitude", "46.2276");
                            param.put("longitude", "2.2137");
                        }
                        param.put("distance", setkm.getText().toString());

                        param.put("type",strselectedtypeds);
                        param.put("nom",strselectedlastname);
                        param.put("acces",strselectedaccess);
                        param.put("offer_status",strselectedstatus);






                        return param;


                    }
                };

                requestQueue1.add(stringRequest);


            }
        });


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


        requestQueue1 = Volley.newRequestQueue(activity);

        stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (activity != null) {
                    category.add(activity.getResources().getString(R.string.select));
                }
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


        proceed_from_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new PicoProductFragment()).commit();
            }
        });

        linlaypico.setVisibility(View.VISIBLE);

//        view=getActivity().findViewById(R.id.linlay);
//
//        view1=getActivity().findViewById(R.id.searchview);

        searchicon = activity.findViewById(R.id.searchicon);
        filtericon = activity.findViewById(R.id.filtericon);


        gardensearch = activity.findViewById(R.id.gardensearch);
        Earsefilter = activity.findViewById(R.id.Earsefilter);
        hideunavailable = activity.findViewById(R.id.hideunavailable);

        filtericon.setVisibility(View.VISIBLE);
        searchicon.setVisibility(View.VISIBLE);
        gardensearch.setVisibility(View.VISIBLE);


        filtericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (filtecardview.isShown()) {
                    filtecardview.setVisibility(View.GONE);


                } else {
                    filtecardview.setVisibility(View.VISIBLE);
                }

            }
        });


        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final List<Product> filteredModelList = filter(allProductPojos, gardensearch.getText().toString());
                if (filteredModelList.size() > 0) {
                    adapter.setFilter(filteredModelList);

                } else {
                    // If not matching search filter data


                }

            }
        });
        Earsefilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gardensearch.setText("");
                getAllProductsDetails();
            }
        });

        hideunavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
callhide();
            }
        });

//
//         myorder.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//
//                 startActivity(new Intent(getActivity(), PicoOrderActivity.class));
//             }
//         });

//        if( view instanceof LinearLayout) {
//            linlay = (LinearLayout) view;
//        }
//
//        if( view1 instanceof SearchView) {
//            //searchview = (SearchView) view1;
//        }


        //  searchview.setOnQueryTextListener(this);


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
//
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








        arraceess.add(0, activity.getResources().getString(R.string.access_spinner));
        arraceess.add(1, activity.getResources().getString(R.string.public_spinner));
        arraceess.add(2, activity.getResources().getString(R.string.private_spinner));
        spinner_accesss.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, arraceess));


        arrstatus.add(0, activity.getResources().getString(R.string.status_spinner));
        arrstatus.add(1, activity.getResources().getString(R.string.available_spinner));
        arrstatus.add(2, activity.getResources().getString(R.string.unavailable_spinner));
        spinner_status.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, arrstatus));
        return v;


    }

    private void callhide() {
        requestQueue1 = Volley.newRequestQueue(activity);

        filtecardview.setVisibility(View.GONE);
        CustomUtil.ShowDialog(activity,true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                allProductPojos.clear();
                CustomUtil.DismissDialog(activity);
                Log.d("drm_hide", response);
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
//                              product.setWeight(jsonObject1.getString("weight"));
                        product.setPrice(jsonObject1.getString("offer_price"));
                        if (jsonObject1.has("is_treated_description")) {
                            product.setDesc(jsonObject1.getString("is_treated_description"));


                        }
                        allProductPojos.add(product);
                    }

                    rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                    adapter = new PicoDonationaroundmeAdapter(allProductPojos, activity);
                    rv_all_products.setAdapter(adapter);
                    rv_all_products.setHasFixedSize(true);
                    rv_all_products.setItemViewCacheSize(20);
                    rv_all_products.setDrawingCacheEnabled(true);
                    rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    rv_all_products.getAdapter().notifyDataSetChanged();


                    Log.d("Dsfdsfsfds", String.valueOf(allProductPojos.size()));
                } catch (JSONException e) {
                    CustomUtil.DismissDialog(activity);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomUtil.DismissDialog(activity);
                Log.d("dfdfdsf", error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put("offer_status_avail","1");
                param.put("user_id",PrefManager.getUserId(activity));






                return param;


            }
        };

        requestQueue1.add(stringRequest);



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
                Log.d("checkarrsize", String.valueOf(subcategory.size()));
             //  subcategory.clear();
                category_id = getCatId(i - 1);
                    Log.d("checkid",category_id);
                requestQueue1 = Volley.newRequestQueue(activity);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("errorincat", response);

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
                        Log.d("errorincat", error.toString());

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
//        else if (adapterView.getId() == R.id.spinner_type_d_s) {
//
//                String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//                Log.d("choosekjkjkjkj", String.valueOf(i));
//                if (selectedItem.equals(getResources().getString(R.string.select))) {
//                    strselectedtypeds = "";
//                } else {
//                    strselectedtypeds = getChoose_id(i - 1);
//                }
//
//
//        }

//        else if (adapterView.getId() == R.id.spinner_lastname) {
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//
//            if (selectedItem.equals(activity.getResources().getString(R.string.lastname))) {
//                strselectedlastname = "";
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.ime))) {
//                strselectedlastname = activity.getResources().getString(R.string.ime);
//
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.pins))) {
//                strselectedlastname = activity.getResources().getString(R.string.pins);
//
//            }
//
//        }
//        else if (adapterView.getId() == R.id.spinner_accesss) {
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//
//            if (selectedItem.equals(activity.getResources().getString(R.string.access_spinner))) {
//                strselectedaccess = "";
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.private_spinner))) {
//                strselectedaccess = activity.getResources().getString(R.string.private_spinner);
//
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.public_spinner))) {
//                strselectedaccess = activity.getResources().getString(R.string.public_spinner);
//
//            }
//
//        }
//        else if (adapterView.getId() == R.id.spinner_status) {
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//
//            if (selectedItem.equals(activity.getResources().getString(R.string.status_spinner))) {
//                strselectedstatus = "";
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.available_spinner))) {
//                strselectedstatus = activity.getResources().getString(R.string.available_spinner);
//
//            } else if (selectedItem.equals(activity.getResources().getString(R.string.unavailable_spinner))) {
//                strselectedstatus = activity.getResources().getString(R.string.unavailable_spinner);
//
//            }
//
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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


    public void initView(View v) {
        rv_all_products = v.findViewById(R.id.rv_all_products);
        filtecardview = v.findViewById(R.id.filtecardview);
        mySale = v.findViewById(R.id.mySale);
        proceed_from_donation = v.findViewById(R.id.proceed_from_donation);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation);

        linlaypico = v.findViewById(R.id.linlaypico);
        myorder = v.findViewById(R.id.myorders);

    }   //initViewClose

    public void getAllProductsDetails() {

        allProductPojos.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("donations_around_me"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("drm_all", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                    Log.d("dfdsfsddf", String.valueOf(jsonArray.length()));

                    if (jsonArray.length() == 0) {
                        textviewtextdonation.setVisibility(View.VISIBLE);
                    }


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.d("fdsfsdfsf", String.valueOf(jsonObject1));
                        Product product = new Product();
                        product.setProductid(jsonObject1.getString("offer_id"));
                        product.setProductname(jsonObject1.getString("offer_title"));
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
                        product.setPrice(jsonObject1.getString("offer_price"));
                        if (jsonObject1.has("is_treated_description")) {
                            product.setDesc(jsonObject1.getString("is_treated_description"));
                        }
                        allProductPojos.add(product);
                    }

                    rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
                    adapter = new PicoDonationaroundmeAdapter(allProductPojos, activity);
                    rv_all_products.setAdapter(adapter);
                    rv_all_products.setHasFixedSize(true);
                    rv_all_products.setItemViewCacheSize(20);
                    rv_all_products.setDrawingCacheEnabled(true);
                    rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    rv_all_products.getAdapter().notifyDataSetChanged();


                    Log.d("Dsfdsfsfds", String.valueOf(allProductPojos.size()));
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;

                Log.d("dsfdfsddfd", volleyError.toString());
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
                if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
                } else if (!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getLAT(activity));
                    param.put("longitude", PrefManager.getLNG(activity));
                } else {
                    param.put("latitude", "46.2276");
                    param.put("longitude", "2.2137");
                }
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

        adapter = new PicoDonationaroundmeAdapter(filteredModelList, activity);

        rv_all_products.setLayoutManager(new LinearLayoutManager(activity));
        rv_all_products.setAdapter(adapter);
        rv_all_products.setHasFixedSize(true);
        rv_all_products.setItemViewCacheSize(20);
        rv_all_products.setDrawingCacheEnabled(true);
        rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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
                                    getAllProductsDetails();

                                } else {
                                    getAllProductsDetails();

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





}
