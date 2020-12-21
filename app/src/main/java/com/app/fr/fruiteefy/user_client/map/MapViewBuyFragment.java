package com.app.fr.fruiteefy.user_client.map;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
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
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.hp_pc.metinpot.R;
//import com.example.hp_pc.metinpot.Util.BaseUrl;
//import com.example.hp_pc.metinpot.Util.MapCluster;
//import com.example.hp_pc.metinpot.Util.PrefManager;
//import com.example.hp_pc.metinpot.user_client.adapter.AllProductAdapter;
//import com.example.hp_pc.metinpot.user_client.adapter.AllProductsearchAdapter;
//import com.example.hp_pc.metinpot.user_client.home.AllProductPojo;
//import com.example.hp_pc.metinpot.user_client.home.SeetheGardenActivity;
//import com.example.hp_pc.metinpot.user_client.products.ProductDetailsActivity;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLngBounds;
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
//public class MapViewBuyFragment extends Fragment implements  Spinner.OnItemSelectedListener,OnMapReadyCallback,ClusterManager.OnClusterClickListener<MapCluster>, ClusterManager.OnClusterInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemClickListener<MapCluster>, ClusterManager.OnClusterItemInfoWindowClickListener<MapCluster> {
//
//    private GoogleMap mMap;
//    private RequestQueue requestQueue;
//    private CardView filtecardview;

//    private String category_id="",subcategory_id="",type_id="";
//    private EditText citysearch,productsearch;
//    private StringRequest stringRequest;
//    private View view, view1;
//    private TextView setprice,km;
//    private EditText edit_name;
//    SeekBar seekBar,seekbarprice;
//    private ArrayList<String> category;
//    private ArrayList<String> subcategory=new ArrayList<>();
//    private TextView removecity,applyfilter,setkm;
//    private Spinner type_of_sellerspinner,spinner_cat,spinnersubcat;
//    private ImageView searchicon,filtericon;
//    private ArrayList<AllProductPojo> antiprodArr = new ArrayList<>();
//    private ClusterManager<MapCluster> mClusterManager;
//    private Switch switchproductavailable;
//    private RequestQueue requestQueue1;
//    private StringRequest stringRequest1;
//    private ArrayList<String> sellertype;
//    private JSONArray resultcat,resultsubcat;
//
//    View v;
//
//
//    public MapViewBuyFragment() {
//
//        }
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//
//
//
//            v= inflater.inflate(R.layout.fragment_map_view, container, false);
//            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                    .findFragmentById(R.id.map);
//
//            category=new ArrayList<>();
//            mapFragment.getMapAsync(this);
//            sellertype=new ArrayList<>();
//            view = getActivity().findViewById(R.id.citysearch);
//            seekBar=v.findViewById(R.id.seekbar);
//            seekbarprice=v.findViewById(R.id.seekbarprice);
//            recitem=v.findViewById(R.id.recitem);
//            view1 = getActivity().findViewById(R.id.gardensearch);
//            type_of_sellerspinner=v.findViewById(R.id.type_of_sellerspinner);
//            requestQueue1=Volley.newRequestQueue(getContext());
//            spinner_cat=v.findViewById(R.id.spinner_cat);
//            setkm=v.findViewById(R.id.setkm);
//            edit_name=v.findViewById(R.id.edit_name);
//            switchproductavailable=v.findViewById(R.id.switchproductavailable);
//            spinnersubcat=v.findViewById(R.id.spinnersubcat);
//            applyfilter=v.findViewById(R.id.applyfilter);
//            setprice=v.findViewById(R.id.setprice);
//            km=v.findViewById(R.id.km);
//
//        if (view instanceof EditText) {
//            citysearch = (EditText) view;
//        }
//
//        if (view1 instanceof EditText) {
//            productsearch = (EditText) view1;
//        }
//
//        filtericon=getActivity().findViewById(R.id.filtericon);
//        searchicon=getActivity().findViewById(R.id.searchicon);
//        removecity=getActivity().findViewById(R.id.removecity);
//        filtecardview=v.findViewById(R.id.filtecardview);
//
//            removecity.setVisibility(View.GONE);
//            searchicon.setVisibility(View.VISIBLE);
//            filtericon.setVisibility(View.VISIBLE);
//
//            citysearch.setVisibility(View.VISIBLE);
//            productsearch.setVisibility(View.VISIBLE);
//
//
//            citysearch.setText("");
//            productsearch.setText("");
//
//
//            sellertype.add(0,getResources().getString(R.string.select));
//            sellertype.add(1,getResources().getString(R.string.particular));
//            sellertype.add(2,getResources().getString(R.string.association));
//            type_of_sellerspinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sellertype));
//
//
//            spinner_cat.setOnItemSelectedListener(this);
//            spinnersubcat.setOnItemSelectedListener(this);
//
//            filtericon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));
//
//                    if(filtecardview.isShown()){
//                        filtecardview.setVisibility(View.GONE);
//
//
//                    }
//                    else{
//                        filtecardview.setVisibility(View.VISIBLE);
//                    }
//
//                }
//            });
//
//            citysearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!Places.isInitialized()) {
//                        Places.initialize(getActivity(),  getResources().getString(R.string.google_maps_key));
//                        PlacesClient placesClient = Places.createClient(getActivity());
//                    }
//
//
//                    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.PLUS_CODE);
//                    Intent intent = new Autocomplete.IntentBuilder(
//                            AutocompleteActivityMode.OVERLAY, fields)
//                            .setTypeFilter(TypeFilter.REGIONS)

//  .setCountry("FR")
//                            .build(getActivity());
//                    startActivityForResult(intent, 1);
//                }
//            });
//
//
//            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                    String value= String.valueOf(progress);
//
//                    setkm.setText(value);
//
//                    km.setText(" KM");
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {}
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {}
//            });
//
//
//            seekbarprice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                    String value= String.valueOf(progress);
//
//                    setprice.setText(value);
//
//
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {}
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {}
//            });
//
//
//            removecity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    citysearch.setText("");
//                }
//            });
//
//
//            stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_category"), new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    category.add(getActivity().getResources().getString(R.string.select));
//                    try {
//                        JSONObject jsonObject=new JSONObject(response);
//                        resultcat = jsonObject.getJSONArray("catlist");
//
//
//
//
//
//
//                        for (int i = 0; i <= resultcat.length(); i++) {
//
//                            JSONObject json = resultcat.getJSONObject(i);
//
//                            category.add(json.getString("cat_name"));
//
//                            spinner_cat.setAdapter(new ArrayAdapter<String>( getActivity(), android.R.layout.simple_spinner_dropdown_item, category));
//
//                        }
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("dsdsddd",error.toString());
//
//                }
//            }){
//
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    Map<String, String> param = new HashMap<>();
//                    param.put("user_id", PrefManager.getUserId(getActivity()));
//                    return param;
//                }
//
//            };
//
//
//            requestQueue1.add(stringRequest1);
//
//
//
//
//            searchicon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mMap.clear();
//                antiprodArr.clear();
//
//                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        antiprodArr.clear();
//                        Log.d("adasds", response);
//                        try {
//
//                            filtecardview.setVisibility(View.GONE);
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONArray jsonArray = jsonObject.getJSONArray("productlist");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                AllProductPojo allProductPojo = new AllProductPojo();
//                                allProductPojo.setProductId(jsonObject1.getString("product_id"));
//                                allProductPojo.setProductImg(jsonObject1.getString("product_image"));
//                                allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
//                                allProductPojo.setPrice(jsonObject1.getString("price"));
//                                allProductPojo.setProductStock(jsonObject1.getString("stock"));
//                                allProductPojo.setWeight(jsonObject1.getString("weight"));
//                                allProductPojo.setUnit(jsonObject1.getString("unit"));
//                                allProductPojo.setDistance(jsonObject1.getString("km"));
//                                allProductPojo.setRating(jsonObject1.getString("rating"));
//                                allProductPojo.setLat(jsonObject1.getString("lat"));
//                                allProductPojo.setLng(jsonObject1.getString("lng"));
//                                antiprodArr.add(allProductPojo);
//
//
//                            }
//
//
//                            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                            recitem.setLayoutManager(verticalLayoutManager);
//
//                            AllProductsearchAdapter adapter = new AllProductsearchAdapter(antiprodArr, getActivity());
//                            recitem.setAdapter(adapter);
//
//                            mMap.clear();
//
//                            mClusterManager=new ClusterManager<MapCluster>(getActivity(),mMap);
//
//                            mMap.setOnCameraIdleListener(mClusterManager);
//                            mMap.setOnMarkerClickListener(mClusterManager);
//                            mMap.setOnInfoWindowClickListener(mClusterManager);
//
//                            for(int j=0;j<antiprodArr.size();j++) {
//
//                                AllProductPojo allProductPojo=antiprodArr.get(j);
//
//                                Double lat1= Double.valueOf(allProductPojo.getLat());
//                                Double lng1= Double.valueOf(allProductPojo.getLng());
//                                String title = allProductPojo.getProductTitle();
//                                String snippet = "More Detail";
//                                String id=allProductPojo.getProductId();
//
//                                Log.d("fdgfdgf",lat1+" "+lng1);
//
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
//                    public void onErrorResponse(VolleyError volleyError) {
//                        String message = null;
//                        if (volleyError instanceof NetworkError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (volleyError instanceof ServerError) {
//                            message = "The server could not be found. Please try again after some time!!";
//                        } else if (volleyError instanceof AuthFailureError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (volleyError instanceof ParseError) {
//                            message = "Parsing error! Please try again after some time!!";
//                        } else if (volleyError instanceof NoConnectionError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (volleyError instanceof TimeoutError) {
//                            message = "Connection TimeOut! Please check your internet connection.";
//                        }
//                        if (message != null) {
//
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Map<String, String> param = new HashMap<>();
//                        param.put("searchbtn", "filter");
//
//                        param.put("product_name",productsearch.getText().toString());
//                        param.put("city",citysearch.getText().toString());
//
//
//
//
//
//                        return param;
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
//            applyfilter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    mMap.clear();
//                    antiprodArr.clear();
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d("adasds", response);
//
//                            try {
//
//                                filtecardview.setVisibility(View.GONE);
//                                JSONObject jsonObject = new JSONObject(response);
//
//                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                    AllProductPojo allProductPojo = new AllProductPojo();
//                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
//                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
//                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
//                                    allProductPojo.setPrice(jsonObject1.getString("price"));
//                                    allProductPojo.setLat(jsonObject1.getString("lat"));
//                                    allProductPojo.setLng(jsonObject1.getString("lng"));
//                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
//                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
//                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
//                                    allProductPojo.setDistance(jsonObject1.getString("km"));
//                                    allProductPojo.setRating(jsonObject1.getString("rating"));
//                                    antiprodArr.add(allProductPojo);
//
//
//                                }
//
//                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                                recitem.setLayoutManager(verticalLayoutManager);
//
//                                AllProductsearchAdapter adapter = new AllProductsearchAdapter(antiprodArr, getActivity());
//                                recitem.setAdapter(adapter);
//
//                                mMap.setOnCameraIdleListener(mClusterManager);
//                                mMap.setOnMarkerClickListener(mClusterManager);
//                                mMap.setOnInfoWindowClickListener(mClusterManager);
//
//                                for(int j=0;j<antiprodArr.size();j++) {
//
//                                    AllProductPojo allProductPojo=antiprodArr.get(j);
//
//                                    Double lat1= Double.valueOf(allProductPojo.getLat());
//                                    Double lng1= Double.valueOf(allProductPojo.getLng());
//                                    String title = allProductPojo.getProductTitle();
//                                    String snippet = "More Detail";
//                                    String id=allProductPojo.getProductId();
//
//                                    Log.d("fdgfdgf",lat1+" "+lng1);
//
//                                    MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getGardenimg(),
//                                            allProductPojo.getAbout()  ,allProductPojo.getRating());
//                                    mClusterManager.addItem(offsetItem);
//
//                                }
//
//                                mClusterManager.cluster();
//                                mClusterManager.setAnimation(true);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            String message = null;
//                            if (volleyError instanceof NetworkError) {
//                                message = "Cannot connect to Internet...Please check your connection!";
//                            } else if (volleyError instanceof ServerError) {
//                                message = "The server could not be found. Please try again after some time!!";
//                            } else if (volleyError instanceof AuthFailureError) {
//                                message = "Cannot connect to Internet...Please check your connection!";
//                            } else if (volleyError instanceof ParseError) {
//                                message = "Parsing error! Please try again after some time!!";
//                            } else if (volleyError instanceof NoConnectionError) {
//                                message = "Cannot connect to Internet...Please check your connection!";
//                            } else if (volleyError instanceof TimeoutError) {
//                                message = "Connection TimeOut! Please check your internet connection.";
//                            }
//                            if (message != null) {
//
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//
//                            Map<String, String> param = new HashMap<>();
//                            param.put("searchbtn", "filter");
//
//                            param.put("cat_id",category_id);
//                            param.put("subcat_id",subcategory_id);
//                            param.put("minprice","");
//                            param.put("maxprice",setprice.getText().toString());
//
//                            if (switchproductavailable.isChecked()) {
//                                param.put("available", "1");
//                            }
//                            if (!switchproductavailable.isChecked()) {
//                                param.put("available", "0");
//
//                            }
//                            param.put("distance",setkm.getText().toString());
//                            param.put("vendor_name",edit_name.getText().toString());
//                            param.put("vendor_type",type_of_sellerspinner.getSelectedItem().toString());
//                            param.put("product_name",productsearch.getText().toString());
//                            param.put("city",citysearch.getText().toString());
//
//
//
//
//
//                            return param;
//                        }
//                    };
//
//                    requestQueue.add(stringRequest);
//
//
//
//
//                }
//            });
//
//
//
//        return v;
//    }
//
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
//
//
//        if (adapterView.getId() == R.id.spinner_cat) {
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
//                            resultsubcat = jsonObject.getJSONArray("subcatlist");
//                            getSubCatrName(resultsubcat);
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
//        } else if (adapterView.getId() == R.id.spinnersubcat) {
//
//
//            String selectedItem = adapterView.getItemAtPosition(i).toString();
//            if (selectedItem.equals(getResources().getString(R.string.subcategory))) {
//                subcategory_id = "";
//            } else {
//                subcategory_id = getsubCatId(i - 1);
//                Log.d("sdfdsf", subcategory_id);
//            }
//
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
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
//    private String getsubCatId(int position){
//        String subCatid="";
//        try {
//            JSONObject json = resultsubcat.getJSONObject(position);
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
//           mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
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
//        Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
//        intent.putExtra("productid",item.getId());
//        startActivity(intent);
//    }
//
//
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//
//        requestQueue = Volley.newRequestQueue(getActivity());
//
//
//
//
//        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.d("ffgfdgdg",response);
//
//                antiprodArr.clear();
//
//
//                try {
//                    JSONObject jsonObject=new JSONObject(response);
//
//                    JSONArray jsonArray=jsonObject.getJSONArray("productlist");
//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                        AllProductPojo allProductPojo=new AllProductPojo();
//                        allProductPojo.setProductId(jsonObject1.getString("product_id"));
//                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
//                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
//                        allProductPojo.setPrice(jsonObject1.getString("price"));
//                        allProductPojo.setLat(jsonObject1.getString("lat"));
//                        allProductPojo.setLng(jsonObject1.getString("lng"));
//                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
//                        allProductPojo.setWeight(jsonObject1.getString("weight"));
//                        allProductPojo.setUnit(jsonObject1.getString("unit"));
//                        allProductPojo.setDistance(jsonObject1.getString("km"));
//                        allProductPojo.setRating(jsonObject1.getString("rating"));
//                        antiprodArr.add(allProductPojo);
//
//
//
//                    }
//
//                    antiprodArr.get(1).setLat("43.949317");
//                    antiprodArr.get(1).setLng("4.805528");
//
//                    antiprodArr.get(2).setLat("43.949317");
//                    antiprodArr.get(2).setLng("4.805528");
//
//                    mClusterManager = new ClusterManager<MapCluster>(getActivity(), mMap);
//                    mMap.setOnCameraIdleListener(mClusterManager);
//                    mMap.setOnMarkerClickListener(mClusterManager);
//
//
//                    for(int j=0;j<antiprodArr.size();j++) {
//
//                        AllProductPojo allProductPojo=antiprodArr.get(j);
//
//                        Double lat1= Double.valueOf(allProductPojo.getLat());
//                        Double lng1= Double.valueOf(allProductPojo.getLng());
//                        String title = allProductPojo.getProductTitle();
//                        String snippet = "More Detail";
//                        String id=allProductPojo.getProductId();
//
//                        Log.d("fdgfdgf",lat1+" "+lng1);
//
//                        MapCluster offsetItem = new MapCluster(lat1, lng1);
//                        mClusterManager.addItem(offsetItem);
//
//                    }
//
//                    mClusterManager.cluster();
//                    mClusterManager.setAnimation(true);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
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
//
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> param=new HashMap<>();
//
//                    param.put("searchbtn", "");
//
//
//                 //   if (lat1 != null && lon1 != null) {
//                        param.put("latitude", "");
//                        param.put("longitude", "");
//                  //  }
//
//                return param;
//
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//
//
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//
//                Log.d("dfdfdfdf",Autocomplete.getPlaceFromIntent(data).getAddress());
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
//
//}


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.Util.MapCluster;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.adapter.AllProductsearchAdapter;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_client.home.mapviewlatlng;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapViewBuyFragment extends Fragment implements ClusterManager.OnClusterItemInfoWindowClickListener<MapCluster>, ClusterManager.OnClusterItemClickListener<MapCluster>, ClusterManager.OnClusterClickListener<MapCluster>, ClusterManager.OnClusterInfoWindowClickListener<MapCluster>, OnMapReadyCallback, Spinner.OnItemSelectedListener {

    View v;

    private String searchString = "";
    private TextView removecity;
    private RequestQueue requestQueue1;
    private StringRequest stringRequest1;
    private ImageView searchicon, searchcity;
    String category_id = "", subcategory_id = "", type_id = "", seller_type_id = "",selectedoffertype = "";
    ArrayList<AllProductPojo> allProductPojos = new ArrayList<AllProductPojo>();
    private GoogleApiClient googleApiClient;
    private Location loc;
    private ArrayList latlngarrlist;
    private RecyclerView recitem;
    private EditText edit_name;
    EditText productsearch;

    View view, view1;
    private GoogleMap mMap;
    private String citylat = "0", citylng = "0";

    private TextView setkm, km, applyfilter, citysearch, filtericon,Earsefilter;
    SeekBar seekBar, seekbarprice;
    private CardView filtecardview;
    AllProductAdapter adapter;
    private Activity activity;
    private String lat1, lon1;
    RelativeLayout rellay1, rellay2;
    private Spinner type_of_sellerspinner, spinner_cat, spinnersubcat,offertypespin;
    private FusedLocationProviderClient fusedLocationClient;
    ArrayList<String> category;
    private JSONArray resultcat, resultsubcat;
    TextView setprice;
    private ArrayList<String> sellertype;
    ArrayList<String> subcategory = new ArrayList<>();
    private Switch switchproductavailable;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest1;
    private ClusterManager<MapCluster> mClusterManager;
    BottomNavigationView bottomNavigationView;
    RelativeLayout tool2;

    private ArrayList<String> offertype = new ArrayList<>();
    public MapViewBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v = inflater.inflate(R.layout.fragment_map_view, container, false);

        activity = (UserCliantHomeActivity) getActivity();
        initView(v);

        latlngarrlist = new ArrayList();
        sellertype = new ArrayList<>();
        filtecardview = v.findViewById(R.id.filtecardview);
        edit_name = v.findViewById(R.id.edit_name);
        setprice = v.findViewById(R.id.setprice);
        recitem = v.findViewById(R.id.recitem);
        rellay2 = activity.findViewById(R.id.rellay2);
        searchcity = activity.findViewById(R.id.searchcity);
        tool2 = activity.findViewById(R.id.tool2);
        spinner_cat = v.findViewById(R.id.spinner_cat);
        switchproductavailable = v.findViewById(R.id.switchproductavailable);
        applyfilter = v.findViewById(R.id.applyfilter);
        spinnersubcat = v.findViewById(R.id.spinnersubcat);
        offertypespin= activity.findViewById(R.id.offertypespin);
        type_of_sellerspinner = v.findViewById(R.id.type_of_sellerspinner);
        requestQueue1 = Volley.newRequestQueue(getContext());
        filtericon = activity.findViewById(R.id.filtericon);
        Earsefilter = activity.findViewById(R.id.Earsefilter);
        searchicon = activity.findViewById(R.id.searchicon);
        removecity = activity.findViewById(R.id.removecity);
        category = new ArrayList<>();
        seekBar = v.findViewById(R.id.seekbar);
        seekbarprice = v.findViewById(R.id.seekbarprice);
        setkm = v.findViewById(R.id.setkm);
        km = v.findViewById(R.id.km);
        rellay1 = activity.findViewById(R.id.rellay1);
        view = activity.findViewById(R.id.citysearch);
        view1 = activity.findViewById(R.id.gardensearch);

        if (view instanceof TextView) {
            citysearch = (TextView) view;
        }

        if (view1 instanceof TextView) {
            productsearch = (EditText) view1;
        }

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
                    else if (selectedoffertype.equals("Les Ventes (Échanges possibles))")) //activity.getResources().getString(R.string.sell_ep_ot)
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
                    //    selectoffercalllist(selectedoffertype);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subcategory.add(0, activity.getResources().getString(R.string.select));
        spinnersubcat.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subcategory));


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

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

        removecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllProduc();
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

                    getAllProduc();
                }

            }
        });


        searchcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

//                Log.d("dfsfdsfd", String.valueOf(filtecardview.getVisibility()));

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
                citylat = "0";
                citylng = "0";
                offertypespin.setSelection(0);
                productsearch.setText("");
//                getAllProduc();
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
                latlngarrlist.clear();

                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("get_product_list_search", response);
                        try {


                            allProductPojos.clear();
                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {
                                recitem.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllProductPojo allProductPojo = new AllProductPojo();
                                    mapviewlatlng mapviewlatlng = new mapviewlatlng();
                                    allProductPojo.setProductId(jsonObject1.getString("product_id"));
                                    allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                                    allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                                    allProductPojo.setOffer_type(jsonObject1.getString("offer_type"));
                                    allProductPojo.setPrice(jsonObject1.getString("price"));
                                    allProductPojo.setProductStock(jsonObject1.getString("stock"));
                                    allProductPojo.setWeight(jsonObject1.getString("weight"));
                                    allProductPojo.setUnit(jsonObject1.getString("unit"));
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    mapviewlatlng.setLat(jsonObject1.getString("lat"));
                                    mapviewlatlng.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);
                                    latlngarrlist.add(mapviewlatlng);


                                }

                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);
                                AllProductsearchAdapter adapter = new AllProductsearchAdapter(allProductPojos, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


                                mClusterManager.clearItems();

                                for (int j = 0; j < allProductPojos.size(); j++) {

                                    AllProductPojo allProductPojo = allProductPojos.get(j);


                                    Double lat1 = Double.valueOf(allProductPojo.getLat());
                                    Double lng1 = Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getProductTitle();
                                    String snippet = "More Details";
                                    String id = allProductPojo.getProductId();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1, title, snippet, id, allProductPojo.getProductImg(),
                                            allProductPojo.getAbout(), allProductPojo.getRating(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getDistance(),
                                            allProductPojo.getPrice(), allProductPojo.getProductStock(), allProductPojo.getWeight(), allProductPojo.getUnit(), allProductPojo.getOffer_type());

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }


//                                adapter = new AllProductAdapter(allProductPojos, getActivity());
//                                rv_all_products.setAdapter(adapter);
                            } else {
                                recitem.setVisibility(View.GONE);
                                mClusterManager.clearItems();
                                mMap.clear();
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
                        Log.d("sdadd", volleyError.toString());
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
                        param.put("city", citysearch.getText().toString());

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


                allProductPojos.clear();

                RequestQueue requestQueue = Volley.newRequestQueue(activity);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("get_product_list_filter", response);
                        try {


                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {
                                recitem.setVisibility(View.VISIBLE);
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
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }

                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);
                                AllProductsearchAdapter adapter = new AllProductsearchAdapter(allProductPojos, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                                mClusterManager.clearItems();

                                for (int j = 0; j < allProductPojos.size(); j++) {

                                    AllProductPojo allProductPojo = allProductPojos.get(j);


                                    Double lat1 = Double.valueOf(allProductPojo.getLat());
                                    Double lng1 = Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getProductTitle();
                                    String snippet = "More Details";
                                    String id = allProductPojo.getProductId();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1, title, snippet, id, allProductPojo.getProductImg(),
                                            allProductPojo.getAbout(), allProductPojo.getRating(), allProductPojo.getFirstname(), allProductPojo.getLastname(), allProductPojo.getDistance(),
                                            allProductPojo.getPrice(), allProductPojo.getProductStock(), allProductPojo.getWeight(), allProductPojo.getUnit(), allProductPojo.getOffer_type());

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }


//                                adapter = new AllProductAdapter(allProductPojos, getActivity());
//                                rv_all_products.setAdapter(adapter);
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
                    public void onErrorResponse(VolleyError volleyError) {

                        Log.d("sdadd", volleyError.toString());
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
                        param.put("distance", setkm.getText().toString());
                        param.put("vendor_name", edit_name.getText().toString());
                        param.put("vendor_type", seller_type_id);


                        return param;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });

        stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_category"), new Response.Listener<String>() {
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
                //   param.put("user_id", PrefManager.getUserId(getActivity()));
                return param;
            }

        };


        requestQueue1.add(stringRequest1);


//        rv_all_products.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0 && bottomNavigationView.isShown()) {
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else if (dy < 0) {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });

        return v;
    }

    private void selectoffercalllist(String selectedoffertype) {
        CustomUtil.ShowDialog(activity,true);




        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("get_product_list_offer", response);
                CustomUtil.DismissDialog(activity);
Log.d("responseallpojo", String.valueOf(allProductPojos.size()));
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
                        allProductPojo.setLat(jsonObject1.getString("lat"));
                        allProductPojo.setLng(jsonObject1.getString("lng"));

                        if(jsonObject1.getString("rating").equals("null")){
                            allProductPojo.setRating("0");
                        }
                        else {
                            allProductPojo.setRating(jsonObject1.getString("rating"));
                        }

                        allProductPojos.add(allProductPojo);



                    }



                    LatLng coordinate = new LatLng(46.2276, 2.2137);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                    mMap.animateCamera(location);
                    Log.d("checksizeafteroffer", String.valueOf(allProductPojos.size()));
                    mClusterManager.clearItems();
                    for(int j=0;j<allProductPojos.size();j++) {

                        AllProductPojo allProductPojo=allProductPojos.get(j);


                        Double lat1= Double.valueOf(allProductPojo.getLat());
                        Double lng1= Double.valueOf(allProductPojo.getLng());
                        String title = allProductPojo.getProductTitle();
                        String snippet = "More Details";
                        String id=allProductPojo.getProductId();

                        MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getProductImg(),
                                allProductPojo.getAbout()  ,allProductPojo.getRating(),allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getDistance(),
                                allProductPojo.getPrice(),allProductPojo.getProductStock(),allProductPojo.getWeight(),allProductPojo.getUnit(),allProductPojo.getOffer_type());

                        mClusterManager.addItem(offsetItem);
                        mClusterManager.cluster();
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


                return param;
            }
        };

        requestQueue.add(stringRequest);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mClusterManager = new ClusterManager<MapCluster>(activity, mMap);


        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {


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

                requestQueue1 = Volley.newRequestQueue(activity);
                stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_picorear_subcategory"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("get_anti_subcateg", response);

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
                        //   param.put("user_id", PrefManager.getUserId(getActivity()));
                        param.put("cat_id", category_id);
                        return param;
                    }

                };


                requestQueue1.add(stringRequest1);
            }
        } else if (adapterView.getId() == R.id.type_of_sellerspinner) {
            String selectedItem = adapterView.getItemAtPosition(i).toString();


            if (selectedItem.equals(activity.getResources().getString(R.string.select))) {
                seller_type_id = "";
            } else if (selectedItem.equals(activity.getResources().getString(R.string.particular))) {
                seller_type_id = "Individual";

            } else if (selectedItem.equals(activity.getResources().getString(R.string.association))) {
                seller_type_id = "Company";

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


    public void initView(View v) {
//        rv_all_products = v.findViewById(R.id.rv_all_products);
//        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv_all_products.setLayoutManager(verticalLayoutManager);
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


    public void getAllProduc() {



        allProductPojos.clear();


        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                Log.d("get_product_list_all_map", response);


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
                        allProductPojo.setLat(jsonObject1.getString("lat"));
                        allProductPojo.setLng(jsonObject1.getString("lng"));

                        if(jsonObject1.getString("rating").equals("null")){
                            allProductPojo.setRating("0");
                        }
                        else {
                            allProductPojo.setRating(jsonObject1.getString("rating"));
                        }

                        allProductPojos.add(allProductPojo);

Log.d("sizemain", String.valueOf(allProductPojos.size()));
                    }



                    LatLng coordinate = new LatLng(46.2276, 2.2137);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 3);
                    mMap.animateCamera(location);
      Log.d("allProductPojos_all", String.valueOf(allProductPojos.size()));
      mClusterManager.clearItems();
                    for(int j=0;j<allProductPojos.size();j++) {

                        AllProductPojo allProductPojo=allProductPojos.get(j);


                        Double lat1= Double.valueOf(allProductPojo.getLat());
                        Double lng1= Double.valueOf(allProductPojo.getLng());
                        String title = allProductPojo.getProductTitle();
                        String snippet = "More Details";
                        String id=allProductPojo.getProductId();

                        MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getProductImg(),
                                allProductPojo.getAbout()  ,allProductPojo.getRating(),allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getDistance(),
                                allProductPojo.getPrice(),allProductPojo.getProductStock(),allProductPojo.getWeight(),allProductPojo.getUnit(),allProductPojo.getOffer_type());
Log.d("clusteraddedfromall","clusteraddedfromall");
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

//                if (!PrefManager.getCURRENTLAT(activity).equals("") && !PrefManager.getCURRENTLNG(activity).equals("")) {
//                    param.put("latitude", PrefManager.getCURRENTLAT(activity));
//                    param.put("longitude", PrefManager.getCURRENTLNG(activity));
//                }
//                else if(!PrefManager.getLAT(activity).equals("") && !PrefManager.getLNG(activity).equals("")){
//                    param.put("latitude",PrefManager.getLAT(activity));
//                    param.put("longitude", PrefManager.getLNG(activity));
//                }
//                else{
//                    param.put("latitude","46.2276");
//                    param.put("longitude","2.2137");
//                }
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onClusterClick(Cluster<MapCluster> cluster) {
      Log.d("fdsfsfs", String.valueOf(cluster));



        float maxZoomLevel = mMap.getMaxZoomLevel();
        float currentZoomLevel = mMap.getCameraPosition().zoom;


        if(maxZoomLevel==currentZoomLevel){
            if(cluster.getSize()>1) {
                allProductPojos.clear();
                for (MapCluster mapCluster : cluster.getItems()) {

                    AllProductPojo allProductPojo = new AllProductPojo();
                    allProductPojo.setProductId(mapCluster.getmId());
                    allProductPojo.setProductImg(mapCluster.getmGardenimg());
                    allProductPojo.setProductTitle(mapCluster.getmTitle());
                    allProductPojo.setPrice(mapCluster.getMprice());
                    allProductPojo.setProductStock(mapCluster.getMstock());
                    allProductPojo.setOffer_type(mapCluster.getMoffer_type());
                    allProductPojo.setWeight(mapCluster.getMweight());
                    allProductPojo.setUnit(mapCluster.getMunit());
                    allProductPojo.setLat(String.valueOf(mapCluster.getPosition().latitude));
                    allProductPojo.setLng(String.valueOf(mapCluster.getPosition().longitude));
                    allProductPojo.setDistance(mapCluster.getmKm());
                    if (mapCluster.getmRating().equals("null")) {
                        allProductPojo.setRating("0");
                    } else {
                        allProductPojo.setRating(mapCluster.getmRating());
                    }
                    allProductPojos.add(allProductPojo);
                }


                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                recitem.setLayoutManager(verticalLayoutManager);
                AllProductsearchAdapter adapter = new AllProductsearchAdapter(allProductPojos, activity);
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

                        Log.d("sdfdsf",response);
                        try {

                            allProductPojos.clear();




                            filtecardview.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("status").equals("1")) {

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
                                    allProductPojo.setLat(jsonObject1.getString("lat"));
                                    allProductPojo.setLng(jsonObject1.getString("lng"));
                                    allProductPojo.setDistance(jsonObject1.getString("km"));
                                    if (jsonObject1.getString("rating").equals("null")) {
                                        allProductPojo.setRating("0");
                                    } else {
                                        allProductPojo.setRating(jsonObject1.getString("rating"));
                                    }
                                    allProductPojos.add(allProductPojo);


                                }
                                recitem.setVisibility(View.VISIBLE);
                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                                recitem.setLayoutManager(verticalLayoutManager);
                                AllProductsearchAdapter adapter = new AllProductsearchAdapter(allProductPojos, activity);
                                recitem.setAdapter(adapter);
                                recitem.setHasFixedSize(true);
                                recitem.setItemViewCacheSize(20);
                                recitem.setDrawingCacheEnabled(true);
                                recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


                                mClusterManager.clearItems();

                                for(int j=0;j<allProductPojos.size();j++) {

                                    AllProductPojo allProductPojo=allProductPojos.get(j);


                                    Double lat1= Double.valueOf(allProductPojo.getLat());
                                    Double lng1= Double.valueOf(allProductPojo.getLng());
                                    String title = allProductPojo.getProductTitle();
                                    String snippet = "More Details";
                                    String id=allProductPojo.getProductId();

                                    MapCluster offsetItem = new MapCluster(lat1, lng1,title,snippet,id,allProductPojo.getProductImg(),
                                            allProductPojo.getAbout()  ,allProductPojo.getRating(),allProductPojo.getFirstname(),allProductPojo.getLastname(),allProductPojo.getDistance(),
                                            allProductPojo.getPrice(),allProductPojo.getProductStock(),allProductPojo.getWeight(),allProductPojo.getUnit(),allProductPojo.getOffer_type());

                                    mClusterManager.addItem(offsetItem);
                                    mClusterManager.cluster();
                                }



//                                adapter = new AllProductAdapter(allProductPojos, getActivity());
//                                rv_all_products.setAdapter(adapter);
                            }
                            else{

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

                        param.put("product_name",productsearch.getText().toString());
                        param.put("city",citysearch.getText().toString());





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


    @Override
    public void onClusterInfoWindowClick(Cluster<MapCluster> cluster) {
    }

    @Override
    public boolean onClusterItemClick(MapCluster mapCluster) {

        allProductPojos.clear();


        AllProductPojo allProductPojo = new AllProductPojo();
        allProductPojo.setProductId(mapCluster.getmId());
        allProductPojo.setProductImg(mapCluster.getmGardenimg());
        allProductPojo.setProductTitle(mapCluster.getmTitle());
        allProductPojo.setPrice(mapCluster.getMprice());
        allProductPojo.setOffer_type(mapCluster.getMoffer_type());
        allProductPojo.setProductStock(mapCluster.getMstock());
        allProductPojo.setWeight(mapCluster.getMweight());
        allProductPojo.setUnit(mapCluster.getMunit());
        allProductPojo.setLat(String.valueOf(mapCluster.getPosition().latitude));
        allProductPojo.setLng(String.valueOf(mapCluster.getPosition().longitude));
        allProductPojo.setDistance(mapCluster.getmKm());
        if (mapCluster.getmRating().equals("null")) {
            allProductPojo.setRating("0");
        } else {
            allProductPojo.setRating(mapCluster.getmRating());
        }
        allProductPojos.add(allProductPojo);



        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recitem.setLayoutManager(verticalLayoutManager);
        AllProductsearchAdapter adapter = new AllProductsearchAdapter(allProductPojos, activity);
        recitem.setAdapter(adapter);
        recitem.setHasFixedSize(true);
        recitem.setItemViewCacheSize(20);
        recitem.setDrawingCacheEnabled(true);
        recitem.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);



        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(MapCluster mapCluster) {



    }

}


