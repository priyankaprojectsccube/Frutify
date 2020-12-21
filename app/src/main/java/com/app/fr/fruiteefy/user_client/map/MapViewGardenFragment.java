package com.app.fr.fruiteefy.user_client.map;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//import android.widget.SeekBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.hp_pc.metinpot.R;
//import com.example.hp_pc.metinpot.Util.BaseUrl;
//import com.example.hp_pc.metinpot.Util.MapCluster;
//import com.example.hp_pc.metinpot.Util.PrefManager;
//import com.example.hp_pc.metinpot.user_client.adapter.AllProductsearchAdapter;
//import com.example.hp_pc.metinpot.user_client.adapter.homeGardenAdapter;
//import com.example.hp_pc.metinpot.user_client.adapter.homeGardensearchAdapter;
//import com.example.hp_pc.metinpot.user_client.home.AllProductPojo;
//import com.example.hp_pc.metinpot.user_client.home.GardenProductActivity;
//import com.example.hp_pc.metinpot.user_client.home.GardensActivity;
//import com.example.hp_pc.metinpot.user_client.home.SeetheGardenActivity;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.model.TypeFilter;
//import com.google.android.libraries.places.api.net.PlacesClient;
//import com.google.android.libraries.places.widget.Autocomplete;
//import com.google.android.libraries.places.widget.AutocompleteActivity;
//import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
//import com.google.maps.android.clustering.Cluster;
//import com.google.maps.android.clustering.ClusterItem;
//import com.google.maps.android.clustering.ClusterManager;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class MapViewGardenFragment extends Fragment implements  OnMapReadyCallback,ClusterManager.OnClusterClickListener<MapCluster>, ClusterManager.OnClusterInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemClickListener<MapCluster>, ClusterManager.OnClusterItemInfoWindowClickListener<MapCluster>,Spinner.OnItemSelectedListener  {
//
//    private GoogleMap mMap;
//    private RecyclerView recitem;
//    private RequestQueue requestQueue;
//    private TextView setkm,applyfilter,km;
//    private EditText edit_name;
//    private String lastName = "";
//    private String firstName= "";
//    private String category_id="",subcategory_id="",type_id="";
//    private RequestQueue requestQueue1,requestQueue2,requestQueue3;
//    private ImageView filter,search,citysearchimg;
//    private View view, view1;
//    private String searchString = "";
//    private Spinner type_of_sellerspinner,typespinner,activesellerspinner,spinnercat,spinnersubcat;
//    private ArrayList<String> category;
//    private ArrayList<String> subcategory=new ArrayList<>();
//    private ArrayList<String> sellertype;
//    private ArrayList<String> activeseller;
//    private ArrayList<String> gardentypes;
//    private EditText citysearch,gardensearch;
//    private StringRequest stringRequest,stringRequest1,stringRequest3;
//    private SeekBar seekBar;
//    public static int LENGTH_MAX_VALUE = 100;
//    private JSONArray result,result1,resultcat,resultgarden;
//    private CardView filtecardview;
//    private ArrayList<AllProductPojo> antiprodArr = new ArrayList<>();
//    private ClusterManager<MapCluster> mClusterManager;
//    private ImageView searchcityimg;
//    private View v;
//    private LinearLayout linlay;
//
//    public MapViewGardenFragment() {
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//
//        v= inflater.inflate(R.layout.fragment_gardenmap_view, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        Activity activity=(GardensActivity)activity;
//        sellertype=new ArrayList<>();
//        activeseller=new ArrayList<>();
//        gardentypes=new ArrayList<>();
//        category=new ArrayList<>();
//
//
//        view = getActivity().findViewById(R.id.citysearch);
//        view1 = getActivity().findViewById(R.id.gardensearch);
//
//        filter=getActivity().findViewById(R.id.filtericon);
//        search=getActivity().findViewById(R.id.searchicon);
//        type_of_sellerspinner = v.findViewById(R.id.type_of_seller);
//        recitem=v.findViewById(R.id.recitem);
//
//        if (view instanceof TextView) {
//            citysearch = (EditText) view;
//        }
//
//        if (view1 instanceof TextView) {
//            gardensearch = (EditText) view1;
//        }
//        requestQueue2= Volley.newRequestQueue(getActivity());
//        requestQueue1= Volley.newRequestQueue(getActivity());
//        requestQueue3= Volley.newRequestQueue(getActivity());
//
//        edit_name = v.findViewById(R.id.edit_name);
//        km = v.findViewById(R.id.km);
//        activesellerspinner= v.findViewById(R.id.activeseller);
//        applyfilter= v.findViewById(R.id.applyfilter);
//        spinnercat= v.findViewById(R.id.spinner_cat);
//        spinnersubcat= v.findViewById(R.id.spinnersubcat);
//        typespinner= v.findViewById(R.id.typespinner);
//        setkm = v.findViewById(R.id.setkm);
//        seekBar = (SeekBar) v.findViewById(R.id.seekbar);
//        filtecardview=v.findViewById(R.id.filtecardview);
//        citysearchimg=getActivity().findViewById(R.id.searchcity);
//        searchcityimg=getActivity().findViewById(R.id.searchcity);
//        type_of_sellerspinner.setOnItemSelectedListener(this);
//        typespinner.setOnItemSelectedListener(this);
//        activesellerspinner.setOnItemSelectedListener(this);
//        spinnercat.setOnItemSelectedListener(this);
//        spinnersubcat.setOnItemSelectedListener(this);
//
//
//
//        citysearch.setText("");
//        gardensearch.setText("");
//
//
//
//
//        sellertype.add(0,getResources().getString(R.string.select));
//        sellertype.add(1,getResources().getString(R.string.particular));
//        sellertype.add(2,getResources().getString(R.string.association));
//
//        activeseller.add(0,getResources().getString(R.string.select));
//        activeseller.add(1,getResources().getString(R.string.yes));
//        activeseller.add(2,getResources().getString(R.string.no));
//
//
//        Log.d("dfsfdsfds",getResources().getString(R.string.category));
//
//        type_of_sellerspinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sellertype));
//        activesellerspinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,activeseller ));
//
//        type_of_sellerspinner.setOnItemSelectedListener(this);
//
//        typespinner.setOnItemSelectedListener(this);
//        activesellerspinner.setOnItemSelectedListener(this);
//
//
//        seekBar.setMax(LENGTH_MAX_VALUE);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                String value= String.valueOf(progress);
//
//                setkm.setText(value);
//                km.setText(" KM");
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//
//        });
//
//
//        searchcityimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                citysearch.setText("");
//            }
//        });
//
//        citysearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!Places.isInitialized()) {
//                    Places.initialize(getActivity(),  getResources().getString(R.string.google_maps_key));
//                    PlacesClient placesClient = Places.createClient(getActivity());
//                }
//
//
//                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.PLUS_CODE);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields)
//                        .setTypeFilter(TypeFilter.REGIONS)

// .setCountry("FR")
//                        .build(getActivity());
//                startActivityForResult(intent, 1);
//            }
//        });
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                mMap.clear();
//
//                mClusterManager=new ClusterManager<MapCluster>(getActivity(),mMap);
//
//                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//
//                        antiprodArr.clear();
//
//                        Log.d("adasdssddddvvvdvsdsdsdd", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("userlist");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                AllProductPojo allProductPojo = new AllProductPojo();
//                                allProductPojo.setUserid(jsonObject1.getString("user_id"));
//                                allProductPojo.setFirstname(jsonObject1.getString("firstname"));
//                                allProductPojo.setLastname(jsonObject1.getString("lastname"));
//                                allProductPojo.setAbout(jsonObject1.getString("about_me"));
//                                allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
//                                allProductPojo.setKilometer(jsonObject1.getString("km"));
//
//                                if(jsonObject1.getString("rating").equals("null")){
//                                    allProductPojo.setRating("0");
//                                }
//                                else{
//                                    allProductPojo.setRating(jsonObject1.getString("rating"));
//                                }
//
//                                allProductPojo.setLat(jsonObject1.getString("lat"));
//                                allProductPojo.setLng(jsonObject1.getString("lng"));
//
//                                antiprodArr.add(allProductPojo);
//
//
//                            }
//                            Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));
//
//                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                            recitem.setLayoutManager(verticalLayoutManager);
//
//                            homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, getActivity());
//                            recitem.setAdapter(adapter);
//
//
//                            mMap.clear();
//
//
//                            mMap.setOnCameraIdleListener(mClusterManager);
//                            mMap.setOnMarkerClickListener(mClusterManager);
//                            mMap.setOnInfoWindowClickListener(mClusterManager);
//
//
//
//                            for(int j=0;j<antiprodArr.size();j++) {
//
//                                AllProductPojo allProductPojo=antiprodArr.get(j);
//
//
//
//                                Double lat1= Double.valueOf(allProductPojo.getLat());
//                                Double lng1= Double.valueOf(allProductPojo.getLng());
//
//
//
//                                String title = allProductPojo.getFirstname()+" "+allProductPojo.getLastname();
//                                String snippet = "see the garden";
//                                String id=allProductPojo.getUserid();
//                                MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
//                                        allProductPojo.getAbout()  ,allProductPojo.getRating());
//                                mClusterManager.addItem(offsetItem);
//
//                            }
//
//                            mClusterManager.cluster();
//                            mClusterManager.setAnimation(true);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.d("dfdfdsf", error.toString());
//
//                    }
//                }) {
//
//
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//
//
////                        if (lat1 != null && lon1 != null) {
////                            param.put("latitude", lat1);
////                            param.put("longitude", lon1);
////
////                        }
//                      //  param.put("city",citysearch.getText().toString());
//                        param.put("garden_name",gardensearch.getText().toString());
//                        param.put("searchbtn","filter");
//
//
//
//                        return param;
//
//
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//
//
//
//            }
//        });
//
//
//        citysearchimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        mMap.clear();
//
//                        mClusterManager=new ClusterManager<MapCluster>(getActivity(),mMap);
//
//
//                        antiprodArr.clear();
//
//                        Log.d("sdadsdasda", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("userlist");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                AllProductPojo allProductPojo = new AllProductPojo();
//                                allProductPojo.setUserid(jsonObject1.getString("user_id"));
//                                allProductPojo.setFirstname(jsonObject1.getString("firstname"));
//                                allProductPojo.setLastname(jsonObject1.getString("lastname"));
//                                allProductPojo.setAbout(jsonObject1.getString("about_me"));
//                                allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
//                                allProductPojo.setKilometer(jsonObject1.getString("km"));
//
//                                if(jsonObject1.getString("rating").equals("null")){
//                                    allProductPojo.setRating("0");
//                                }
//                                else{
//                                    allProductPojo.setRating(jsonObject1.getString("rating"));
//                                }
//
//                                allProductPojo.setLat(jsonObject1.getString("lat"));
//                                allProductPojo.setLng(jsonObject1.getString("lng"));
//
//                                antiprodArr.add(allProductPojo);
//
//
//                            }
//
//                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                            recitem.setLayoutManager(verticalLayoutManager);
//
//                            homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, getActivity());
//                            recitem.setAdapter(adapter);
//
//                            mMap.clear();
//
//
//                            mMap.setOnCameraIdleListener(mClusterManager);
//                            mMap.setOnMarkerClickListener(mClusterManager);
//                            mMap.setOnInfoWindowClickListener(mClusterManager);
//
//
//
//                            for(int j=0;j<antiprodArr.size();j++) {
//
//                                AllProductPojo allProductPojo=antiprodArr.get(j);
//
//
//
//                                Double lat1= Double.valueOf(allProductPojo.getLat());
//                                Double lng1= Double.valueOf(allProductPojo.getLng());
//
//
//
//                                String title = allProductPojo.getFirstname()+" "+allProductPojo.getLastname();
//                                String snippet = "see the garden";
//                                String id=allProductPojo.getUserid();
//                                MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
//                                        allProductPojo.getAbout()  ,allProductPojo.getRating());
//                                mClusterManager.addItem(offsetItem);
//
//                            }
//
//                            mClusterManager.cluster();
//                            mClusterManager.setAnimation(true);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.d("dfdfdsf", error.toString());
//
//                    }
//                }) {
//
//
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//
////
////                        if (lat1 != null && lon1 != null) {
////                            param.put("latitude", lat1);
////                            param.put("longitude", lon1);
////
////                        }
////                        param.put("searchbtn","search");
////                        param.put("searchkey",gardensearch.getText().toString());
//                        param.put("city",citysearch.getText().toString());
//                        //  param.put("garden_name",gardensearch.getText().toString());
//                        param.put("searchbtn","filter");
//
//
//
//                        return param;
//
//
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//            }
//        });
//
//
//
//
//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
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
//
//
//
//
//        applyfilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String name = edit_name.getText().toString();
//
//                if(name.split("\\w+").length>1){
//
//                    lastName = name.substring(name.lastIndexOf(" ")+1);
//                    firstName = name.substring(0, name.lastIndexOf(' '));
//                }
//                else{
//                    firstName = name;
//                }
//
//
//                filtecardview.setVisibility(View.GONE);
//
//                mMap.clear();
//
//                mClusterManager=new ClusterManager<MapCluster>(getActivity(),mMap);
//
//
//                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        filtecardview.setVisibility(View.GONE);
//
//
//                        antiprodArr.clear();
//
//
//                        Log.d("adasdssddddvvvdvsdsdsdd", response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("userlist");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                AllProductPojo allProductPojo = new AllProductPojo();
//                                allProductPojo.setUserid(jsonObject1.getString("user_id"));
//                                allProductPojo.setFirstname(jsonObject1.getString("firstname"));
//                                allProductPojo.setLastname(jsonObject1.getString("lastname"));
//                                allProductPojo.setAbout(jsonObject1.getString("about_me"));
//                                allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
//                                allProductPojo.setKilometer(jsonObject1.getString("km"));
//                                if(jsonObject1.getString("rating").equals("null")){
//                                    allProductPojo.setRating("0");
//                                }
//                                else {
//                                    allProductPojo.setRating(jsonObject1.getString("rating"));
//                                }
//                                allProductPojo.setLat(jsonObject1.getString("lat"));
//                                allProductPojo.setLng(jsonObject1.getString("lng"));
//                                antiprodArr.add(allProductPojo);
//
//
//
//                            }
//
//
//                            mMap.setOnCameraIdleListener(mClusterManager);
//                            mMap.setOnMarkerClickListener(mClusterManager);
//                            mMap.setOnInfoWindowClickListener(mClusterManager);
//
//
//                            for(int j=0;j<antiprodArr.size();j++) {
//
//                                AllProductPojo allProductPojo=antiprodArr.get(j);
//
//
//
//                                Double lat1= Double.valueOf(allProductPojo.getLat());
//                                Double lng1= Double.valueOf(allProductPojo.getLng());
//
//
//                                String title = allProductPojo.getFirstname()+" "+allProductPojo.getLastname();
//                                String snippet = "see the garden";
//                                String id=allProductPojo.getUserid();
//                                MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
//                                        allProductPojo.getAbout()  ,allProductPojo.getRating());
//                                mClusterManager.addItem(offsetItem);
//
//                            }
//
//                            mClusterManager.cluster();
//                            mClusterManager.setAnimation(true);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.d("dfdfdsf", error.toString());
//
//                    }
//                }) {
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> param = new HashMap<>();
//
////
////                        if (lat1 != null && lon1 != null) {
////                            param.put("latitude", lat1);
////                            param.put("longitude", lon1);
////
////                        }
//
//                        param.put("searchbtn","filter");
//
//                        param.put("vendor_type",type_id);
//
//                        param.put("distance",setkm.getText().toString());
//
//                        param.put("vendor_fname",firstName);
//
//                        param.put("vendor_lname",lastName);
//
//                        param.put("catlist",category_id);
//
//                        param.put("sub_catlist",subcategory_id);
//
//                        if(!activesellerspinner.getSelectedItem().equals(getResources().getString(R.string.select))) {
//                            param.put("active_seller", String.valueOf(activesellerspinner.getSelectedItem()));
//
//                        }
//
//                        param.put("city",citysearch.getText().toString());
//                        param.put("garden_name",gardensearch.getText().toString());
//
//                        return param;
//
//
//                    }
//                };
//
//                requestQueue.add(stringRequest);
//
//
//            }
//        });
//
//
//
//        stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                category.add(activity.getResources().getString(R.string.select));
//                try {
//                    JSONObject jsonObject=new JSONObject(response);
//                    resultcat = jsonObject.getJSONArray("catlist");
//
//
//
//
//
//
//                    for (int i = 0; i <= resultcat.length(); i++) {
//
//                        JSONObject json = resultcat.getJSONObject(i);
//
//                        category.add(json.getString("cat_name"));
//
//                        spinnercat.setAdapter(new ArrayAdapter<String> ( activity, android.R.layout.simple_spinner_dropdown_item, category));
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("dsdsddd",error.toString());
//
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> param = new HashMap<>();
//                param.put("user_id", PrefManager.getUserId(getActivity()));
//                return param;
//            }
//
//        };
//
//
//        requestQueue1.add(stringRequest1);
//
//
//
//
//
//        stringRequest3=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_types"), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.d("sdsfasf",response);
//
//                gardentypes.add(activity.getResources().getString(R.string.select));
//
//                try {
//                    JSONObject jsonObject=new JSONObject(response);
//                    resultgarden = jsonObject.getJSONArray("garden_types");
//
//
//
//
//
//
//                    for (int i = 0; i <= resultgarden.length(); i++) {
//
//                        JSONObject json = resultgarden.getJSONObject(i);
//
//                        gardentypes.add(json.getString("dropdown"));
//
//                        typespinner.setAdapter(new ArrayAdapter<String> ( activity, android.R.layout.simple_spinner_dropdown_item, gardentypes));
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("dsdsddd",error.toString());
//
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> param = new HashMap<>();
//                param.put("user_id", PrefManager.getUserId(getActivity()));
//                return param;
//            }
//
//        };
//
//
//        requestQueue3.add(stringRequest3);
//
//
//
//
//        return v;
//    }
//
//
//
//    private  void getSubCatrName(JSONArray j)  {
//        try{
//
//            subcategory.add(getResources().getString(R.string.subcategory));
//            for (int i = 0; i <= j.length(); i++) {
//                JSONObject json = j.getJSONObject(i);
//                subcategory.add(json.getString("subcat_name"));
//                Log.d("sdsdsddsd", String.valueOf(subcategory));
//
//            }
//        }catch(JSONException e){
//
//        }
//        spinnersubcat.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subcategory));
//
//    }
//
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
//
//
//        if(adapterView.getId() == R.id.spinner_cat)
//
//        {
//
//
//            Log.d("dsfdsfds", String.valueOf(i));
//            spinnersubcat.setSelection(0);
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//
//            Log.d("kjjjjjkkjkjjk", selectedItem);
//            if (selectedItem.equals(getResources().getString(R.string.category))) {
//                category_id = "";
//            } else {
//                subcategory.clear();
//                category_id = getCatId(i - 1);
//
//                requestQueue1 = Volley.newRequestQueue(getActivity());
//                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_subcategory"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.d("dsdsddd", response);
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            result1 = jsonObject.getJSONArray("subcatlist");
//                            getSubCatrName(result1);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("dsdsddd", error.toString());
//
//                    }
//                }) {
//
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Map<String, String> param = new HashMap<>();
//                        param.put("user_id", PrefManager.getUserId(getActivity()));
//                        param.put("cat_id", category_id);
//                        return param;
//                    }
//
//                };
//
//
//                requestQueue1.add(stringRequest1);
//            }
//        }
//
//        else if(adapterView.getId() == R.id.spinnersubcat)
//        {
//
//
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//            if (selectedItem.equals(getResources().getString(R.string.subcategory))) {
//                subcategory_id = "";
//            } else {
//                subcategory_id = getsubCatId(i - 1);
//                Log.d("sdfdsf",subcategory_id);
//            }
//        }
//
//
//        else if(adapterView.getId() == R.id.typespinner){
//
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//            if (selectedItem.equals(getResources().getString(R.string.subcategory))) {
//                type_id = "";
//            } else {
//                type_id = gettypeidId(i - 1);
//
//            }
//        }
//
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//
//
//
//    private String gettypeidId(int position){
//        String type_id="";
//        try {
//            JSONObject json = resultgarden.getJSONObject(position);
//
//            type_id = json.getString("id");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Returning the name
//        return type_id;
//    }
//
//
//    private String getsubCatId(int position){
//        String subCatid="";
//        try {
//            JSONObject json = result1.getJSONObject(position);
//
//            subCatid = json.getString("subcat_id");
//            Log.d("sdsdsd",subCatid);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Returning the name
//        return subCatid;
//    }
//
//    private String getCatId(int position){
//        String Catid="";
//        try {
//            JSONObject json = resultcat.getJSONObject(position);
//
//            Catid = json.getString("cat_id");
//            Log.d("sdfgfgffgfsdsd",Catid);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Returning the name
//        return Catid;
//    }
//
//    @Override
//    public boolean onClusterClick(Cluster<MapCluster> cluster) {
//
//        LatLngBounds.Builder builder = LatLngBounds.builder();
//        for (ClusterItem item : cluster.getItems()) {
//            builder.include(item.getPosition());
//        }
//
//        final LatLngBounds bounds = builder.build();
//
//        try {
//           mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//
//    @Override
//    public void onClusterInfoWindowClick(Cluster<MapCluster> cluster) {
//
//    }
//
//    @Override
//    public boolean onClusterItemClick(MapCluster item) {
//
//        return false;
//    }
//
//    @Override
//    public void onClusterItemInfoWindowClick(MapCluster item) {
//
//        Intent intent=new Intent(getActivity(), SeetheGardenActivity.class);
//        intent.putExtra("userid",item.getId());
//        intent.putExtra("img",item.getGardenimg());
//        intent.putExtra("aboutme",item.getAboutme());
//        intent.putExtra("rating",item.getRating());
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//        mClusterManager = new ClusterManager<MapCluster>(getActivity(), mMap);
//        requestQueue = Volley.newRequestQueue(getActivity());
//
//
//
//
//        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                antiprodArr.clear();
//
//
//                Log.d("adasdssddddvvvdvsdsdsdd", response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    JSONArray jsonArray = jsonObject.getJSONArray("userlist");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        AllProductPojo allProductPojo = new AllProductPojo();
//                        allProductPojo.setUserid(jsonObject1.getString("user_id"));
//                        allProductPojo.setFirstname(jsonObject1.getString("firstname"));
//                        allProductPojo.setLastname(jsonObject1.getString("lastname"));
//                        allProductPojo.setAbout(jsonObject1.getString("about_me"));
//                        allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
//                        allProductPojo.setKilometer(jsonObject1.getString("km"));
//
//                        if(jsonObject1.getString("rating").equals("null")){
//                            allProductPojo.setRating("0");
//                        }
//                        else {
//                            allProductPojo.setRating(jsonObject1.getString("rating"));
//                        }
//                        allProductPojo.setLat(jsonObject1.getString("lat"));
//                        allProductPojo.setLng(jsonObject1.getString("lng"));
//                        antiprodArr.add(allProductPojo);
//
//
//
//                    }
//
//
//
//                    mMap.setOnCameraIdleListener(mClusterManager);
//                    mMap.setOnMarkerClickListener(mClusterManager);
//                    mMap.setOnInfoWindowClickListener(mClusterManager);
//
//
//                    for(int j=0;j<antiprodArr.size();j++) {
//
//                        AllProductPojo allProductPojo=antiprodArr.get(j);
//
//
//
//                        Double lat1= Double.valueOf(allProductPojo.getLat());
//                        Double lng1= Double.valueOf(allProductPojo.getLng());
//
//                        Log.d("fdgfdgfdggfg",lat1+" "+lng1);
//
//                        String title = allProductPojo.getFirstname()+" "+allProductPojo.getLastname();
//                        String snippet = "see the garden";
//                        String id=allProductPojo.getUserid();
//                        MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
//                              allProductPojo.getAbout()  ,allProductPojo.getRating());
//                        mClusterManager.addItem(offsetItem);
//
//                    }
//
//
//                    mClusterManager.cluster();
//                    mClusterManager.setAnimation(true);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.d("dfdfdsf",error.toString());
//
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> param=new HashMap<>();
//
//                return param;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//
//        mClusterManager.setOnClusterClickListener(this);
//        mClusterManager.setOnClusterInfoWindowClickListener(this);
//        mClusterManager.setOnClusterItemClickListener(this);
//        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
//
//
//
//
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                citysearch.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
//
//            }
//
//            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.d("fdgfdgfd", String.valueOf(status));
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//
//
//
//
//
//        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//
//
//
//        }
//
//
//
//    }
//
//
//
//}


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.MapCluster;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import com.app.fr.fruiteefy.user_client.adapter.homeGardenAdapter;
import com.app.fr.fruiteefy.user_client.adapter.homeGardensearchAdapter;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.home.GardensActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapViewGardenFragment extends Fragment implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<MapCluster>, ClusterManager.OnClusterInfoWindowClickListener<MapCluster>, Spinner.OnItemSelectedListener, ClusterManager.OnClusterItemInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemClickListener<MapCluster> {


    private TextView setkm, applyfilter, km;
    private EditText edit_name;
    private RelativeLayout rellay;
    // private RecyclerView recvirewgarden;
    private String lastName = "";
    private String firstName = "";
    private String selleractive = "";
    private String category_id = "", subcategory_id = "", type_id = "", seller_type_id = "";
    private  TextView Earsefilter,filter;
    private RequestQueue requestQueue, requestQueue1, requestQueue2, requestQueue3;
    private ImageView  search, citysearchimg;
    private StringRequest stringRequest, stringRequest1, stringRequest3;
    private ArrayList<String> category = new ArrayList<>();
    private ArrayList<String> subcategory = new ArrayList<>();
    private String searchString = "";
    private Spinner type_of_sellerspinner, typespinner, activesellerspinner, spinnercat, spinnersubcat;
    private SeekBar seekBar;
    private GoogleMap mMap;
    private CardView filtecardview;
    private ArrayList<String> sellertype;
    private ArrayList<String> activeseller;
    private ArrayList<String> gardentypes;
    public static int LENGTH_MIN_VALUE = 0;
    private JSONArray result, result1, resultcat, resultgarden;
    public static int LENGTH_MAX_VALUE = 100;
    private Activity activity;
    private View view, view1;
    private EditText gardensearch;
    private TextView citysearch;
    private homeGardenAdapter adapter;
    private String citylat = "0", citylng = "0";
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<AllProductPojo> antiprodArr = new ArrayList<>();
    private ClusterManager<MapCluster> mClusterManager;
    private RecyclerView recitem;
    private TextView remove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gardenmap_view, container, false);


        activity = (GardensActivity) getActivity();


        sellertype = new ArrayList<>();
        activeseller = new ArrayList<>();
        gardentypes = new ArrayList<>();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        view = activity.findViewById(R.id.citysearch);
        view1 = activity.findViewById(R.id.gardensearch);
        rellay = activity.findViewById(R.id.rellay);
        remove = activity.findViewById(R.id.remove);
        //  recvirewgarden = v.findViewById(R.id.recvirewgarden);
        edit_name = v.findViewById(R.id.edit_name);
        km = v.findViewById(R.id.km);
        activesellerspinner = v.findViewById(R.id.activeseller);
        applyfilter = v.findViewById(R.id.applyfilter);
        type_of_sellerspinner = v.findViewById(R.id.type_of_seller);
        spinnercat = v.findViewById(R.id.spinner_cat);
        recitem = v.findViewById(R.id.recitem);
        spinnersubcat = v.findViewById(R.id.spinnersubcat);
        typespinner = v.findViewById(R.id.typespinner);
        setkm = v.findViewById(R.id.setkm);
        seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        filtecardview = v.findViewById(R.id.filtecardview);
        filter = activity.findViewById(R.id.filtericon);
        Earsefilter = activity.findViewById(R.id.Earsefilter);
        search = activity.findViewById(R.id.searchicon);
        citysearchimg = activity.findViewById(R.id.searchcity);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue2 = Volley.newRequestQueue(activity);
        requestQueue1 = Volley.newRequestQueue(activity);
        requestQueue3 = Volley.newRequestQueue(activity);


        if (view instanceof TextView) {
            citysearch = (TextView) view;
        }

        if (view1 instanceof TextView) {
            gardensearch = (EditText) view1;
        }

        citysearch.setText("");
        gardensearch.setText("");


        subcategory.add(0, activity.getResources().getString(R.string.select));
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subcategory));


        sellertype.add(0, activity.getResources().getString(R.string.select));
        sellertype.add(1, activity.getResources().getString(R.string.particular));
        sellertype.add(2, activity.getResources().getString(R.string.association));

        activeseller.add(0, activity.getResources().getString(R.string.select));
        activeseller.add(1, activity.getResources().getString(R.string.yes));
        activeseller.add(2, activity.getResources().getString(R.string.no));

        type_of_sellerspinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, sellertype));
        activesellerspinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, activeseller));


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citysearch.setText("");
                citylat = "0";
                citylng = "0";
            }
        });

        gardensearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (gardensearch.getText().toString().length() == 0) {


                    getGardenlist();
                }

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        stringRequest3 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_garden_types"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("sdsfasf", response);

                gardentypes.add(activity.getResources().getString(R.string.select));

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resultgarden = jsonObject.getJSONArray("garden_types");


                    for (int i = 0; i <= resultgarden.length(); i++) {

                        JSONObject json = resultgarden.getJSONObject(i);

                        gardentypes.add(json.getString("dropdown"));

                        typespinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, gardentypes));

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
        });


        requestQueue3.add(stringRequest3);


        stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("sdasdada", response);

                category.add(activity.getResources().getString(R.string.select));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resultcat = jsonObject.getJSONArray("catlist");


                    for (int i = 0; i <= resultcat.length(); i++) {

                        JSONObject json = resultcat.getJSONObject(i);

                        category.add(json.getString("cat_name"));

                        spinnercat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, category));

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


        filter.setOnClickListener(new View.OnClickListener() {
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

                Earsefilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        citysearch.setText("");
                        citylat="0";
                        citylng="0";
                        gardensearch.setText("");
                        getGardenlist();
                      //  searchin.setVisibility(View.GONE);

            }
        });
        rellay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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


        citysearchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        antiprodArr.clear();
//                        citysearch.setText("");
//                        gardensearch.setText("");

                        Log.d("sdadsdasda", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {

                                recitem.setVisibility(View.VISIBLE);

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
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

//                            adapter = new homeGardenAdapter(antiprodArr, activity);
//                            recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
//                            recvirewgarden.setAdapter(adapter);

                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);

                                homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                mClusterManager.clearItems();

                                for (int j = 0; j < antiprodArr.size(); j++) {

                                    AllProductPojo allProductPojo = antiprodArr.get(j);


                                    Double lat1 = Double.valueOf(allProductPojo.getLat());
                                    Double lng1 = Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getFirstname() + " " + allProductPojo.getLastname();
                                    String snippet = activity.getResources().getString(R.string.seethegarden);
                                    String id = allProductPojo.getUserid();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1, title, snippet, id, allProductPojo.getGardenimg(),
                                            allProductPojo.getAbout(), allProductPojo.getRating(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getKilometer(), allProductPojo.getToken(), allProductPojo.getReviewstatus(), "", "");

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }

                            } else {


                                mClusterManager.clearItems();
                                mMap.clear();
                                recitem.setVisibility(View.GONE);
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


//                        param.put("latitude", citylat);
//                        param.put("longitude", citylng);

                        param.put("city", citysearch.getText().toString());
                        param.put("garden_name", gardensearch.getText().toString());
                        param.put("searchbtn", "filter");


                        return param;


                    }
                };

                requestQueue.add(stringRequest);


            }
        });

        applyfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = edit_name.getText().toString();

                if (name.split("\\w+").length > 1) {

                    lastName = name.substring(name.lastIndexOf(" ") + 1);
                    firstName = name.substring(0, name.lastIndexOf(' '));
                } else {
                    firstName = name;
                }


                filtecardview.setVisibility(View.GONE);


                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        antiprodArr.clear();

                        Log.d("adasdssddddvvvdvsdsdsdd", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }

                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

//                            adapter = new homeGardenAdapter(antiprodArr, activity);
//                            recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
//                            recvirewgarden.setAdapter(adapter);

                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);

                                homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                mClusterManager.clearItems();

                                for (int j = 0; j < antiprodArr.size(); j++) {

                                    AllProductPojo allProductPojo = antiprodArr.get(j);


                                    Double lat1 = Double.valueOf(allProductPojo.getLat());
                                    Double lng1 = Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getFirstname() + " " + allProductPojo.getLastname();
                                    String snippet = activity.getResources().getString(R.string.seethegarden);
                                    String id = allProductPojo.getUserid();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1, title, snippet, id, allProductPojo.getGardenimg(),
                                            allProductPojo.getAbout(), allProductPojo.getRating(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getKilometer(), allProductPojo.getToken(), allProductPojo.getReviewstatus(), "", "");

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }

                            } else {

                                mClusterManager.clearItems();
                                mMap.clear();
                                recitem.setVisibility(View.GONE);
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

//                        if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getCURRENTLAT(activity));
//                            param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                        } else if (!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")) {
//                            param.put("latitude", PrefManager.getLAT(activity));
//                            param.put("longitude", PrefManager.getLNG(activity));
//                        } else {
//                            param.put("latitude", "46.2276");
//                            param.put("longitude", "2.2137");
//                        }

                        param.put("searchbtn", "filter");
                        param.put("distance", setkm.getText().toString());
                        param.put("vendor_type", seller_type_id);
                        param.put("vendor_fname", firstName);
                        param.put("vendor_lname", lastName);
                        param.put("type", type_id);
                        param.put("catlist", category_id);
                        param.put("sub_catlist", subcategory_id);
                        param.put("active_seller", selleractive);


                        Log.d("dsdsds", setkm.getText().toString());
                        Log.d("dsdsds", seller_type_id);
                        Log.d("dsdsds", firstName);
                        Log.d("dsdsds", lastName);
                        Log.d("dsdsds", setkm.getText().toString());
                        Log.d("dsdsds", selleractive);
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

            mMap.setMyLocationEnabled(true);
            getGpsEnable();


        }


    }

    private void getGpsEnable() {


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
                                    getGardenlist();

                                } else {
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
                                6);
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

        CustomUtil.ShowDialog(activity,true);

        stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("garden_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CustomUtil.DismissDialog(getContext());

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
                        allProductPojo.setLat(jsonObject1.getString("lat"));
                        allProductPojo.setLng(jsonObject1.getString("lng"));
                        allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                        allProductPojo.setKilometer(jsonObject1.getString("km"));
                        allProductPojo.setRating(jsonObject1.getString("rating"));
                        allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                        antiprodArr.add(allProductPojo);


                    }


//                    LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
//                    recitem.setLayoutManager(verticalLayoutManager);
//
//                    homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
//                    recitem.setAdapter(adapter);

                    Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

//                    adapter = new homeGardenAdapter(antiprodArr, activity);
//                    recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
//                    recvirewgarden.setAdapter(adapter);

                    LatLng coordinate = new LatLng(46.2276, 2.2137);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                    mMap.animateCamera(location);

                                        for(int j=0;j<antiprodArr.size();j++) {

                        AllProductPojo allProductPojo=antiprodArr.get(j);


                        Double lat1= Double.valueOf(allProductPojo.getLat());
                        Double lng1= Double.valueOf(allProductPojo.getLng());
                        String title = allProductPojo.getFirstname()+" "+allProductPojo.getLastname();
                        String snippet = activity.getResources().getString(R.string.seethegarden);
                        String id=allProductPojo.getUserid();

                        MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
                             allProductPojo.getAbout()  ,allProductPojo.getRating(),allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getKilometer(),allProductPojo.getToken(),allProductPojo.getReviewstatus(),"","");

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
                CustomUtil.DismissDialog(getContext());
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
                }
                 else  if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
                }



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
//
//                        citysearch.setText("");
//                        gardensearch.setText("");

                        Log.d("sdadsdasda", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {
                                recitem.setVisibility(View.VISIBLE);

                                JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    allProductPojo.setUserid(jsonObject1.getString("user_id"));
                                    allProductPojo.setFirstname(jsonObject1.getString("firstname"));
                                    allProductPojo.setLastname(jsonObject1.getString("lastname"));
                                    allProductPojo.setAbout(jsonObject1.getString("about_me"));
                                    allProductPojo.setToken(jsonObject1.getString("fcm_token"));
                                    allProductPojo.setGardenimg(jsonObject1.getString("profile_pic"));
                                    allProductPojo.setKilometer(jsonObject1.getString("km"));
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setReviewstatus(jsonObject1.getString("dropdown"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    antiprodArr.add(allProductPojo);


                                }
                                Log.d("adasdssddddvvvdvsdsdsdd", String.valueOf(antiprodArr.size()));

//                            adapter = new homeGardenAdapter(antiprodArr, activity);
//                            recvirewgarden.setLayoutManager(new LinearLayoutManager(activity));
//                            recvirewgarden.setAdapter(adapter);


                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);

                                homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                mClusterManager.clearItems();

                                for (int j = 0; j < antiprodArr.size(); j++) {

                                    AllProductPojo allProductPojo = antiprodArr.get(j);


                                    Double lat1 = Double.valueOf(allProductPojo.getLat());
                                    Double lng1 = Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getFirstname() + " " + allProductPojo.getLastname();
                                    String snippet = activity.getResources().getString(R.string.seethegarden);
                                    String id = allProductPojo.getUserid();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1, title, snippet, id, allProductPojo.getGardenimg(),
                                            allProductPojo.getAbout(), allProductPojo.getRating(),allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getKilometer(),allProductPojo.getToken(),allProductPojo.getReviewstatus(),"","");

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }
                            }
                            else{
                                mMap.clear();
                                recitem.setVisibility(View.GONE);
                                mClusterManager.clearItems();
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


    @Override
    public void onClusterItemInfoWindowClick(MapCluster mapCluster) {


    }

    @Override
    public boolean onClusterItemClick(MapCluster mapCluster) {
        antiprodArr.clear();



        AllProductPojo allProductPojo=new AllProductPojo();

        allProductPojo.setUserid(mapCluster.getGid());
        allProductPojo.setFirstname(mapCluster.getGfirstname());
        allProductPojo.setLastname(mapCluster.getGlastname());
        allProductPojo.setAbout(mapCluster.getGabout());
        allProductPojo.setGardenimg(mapCluster.getGproductImg());
        allProductPojo.setKilometer(mapCluster.getGdistance());
        allProductPojo.setLat(String.valueOf(mapCluster.getPosition().latitude));
        allProductPojo.setLng(String.valueOf(mapCluster.getPosition().longitude));
        allProductPojo.setToken(mapCluster.getGprice());
        allProductPojo.setReviewstatus(mapCluster.getGproductStock());
        if (mapCluster.getGrating().equals("null")) {
            allProductPojo.setRating("0");
        } else {
            allProductPojo.setRating(mapCluster.getGrating());
        }

        antiprodArr.add(allProductPojo);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recitem.setLayoutManager(verticalLayoutManager);

        homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
        recitem.setAdapter(adapter);
        recitem.setHasFixedSize(true);
        recitem.setItemViewCacheSize(20);
        recitem.setDrawingCacheEnabled(true);
        recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<MapCluster> cluster) {

        float maxZoomLevel = mMap.getMaxZoomLevel();
        float currentZoomLevel = mMap.getCameraPosition().zoom;


        if(maxZoomLevel==currentZoomLevel){
          if(cluster.getSize()>1) {
              antiprodArr.clear();
              for (MapCluster mapCluster : cluster.getItems()) {


                  AllProductPojo allProductPojo = new AllProductPojo();

                  allProductPojo.setUserid(mapCluster.getGid());
                  allProductPojo.setFirstname(mapCluster.getGfirstname());
                  allProductPojo.setLastname(mapCluster.getGlastname());
                  allProductPojo.setAbout(mapCluster.getGabout());
                  allProductPojo.setGardenimg(mapCluster.getGproductImg());
                  allProductPojo.setKilometer(mapCluster.getGdistance());
                  allProductPojo.setLat(String.valueOf(mapCluster.getPosition().latitude));
                  allProductPojo.setLng(String.valueOf(mapCluster.getPosition().longitude));
                  allProductPojo.setToken(mapCluster.getGprice());
                  allProductPojo.setReviewstatus(mapCluster.getGproductStock());
                  if (mapCluster.getGrating().equals("null")) {
                      allProductPojo.setRating("0");
                  } else {
                      allProductPojo.setRating(mapCluster.getGrating());
                  }


                  antiprodArr.add(allProductPojo);
              }

              LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
              recitem.setLayoutManager(verticalLayoutManager);

              homeGardensearchAdapter adapter = new homeGardensearchAdapter(antiprodArr, activity);
              recitem.setAdapter(adapter);
              recitem.setHasFixedSize(true);
              recitem.setItemViewCacheSize(20);
              recitem.setDrawingCacheEnabled(true);
              recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

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


