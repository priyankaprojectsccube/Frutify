package com.app.fr.fruiteefy.user_client.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.util.HashMap;
import java.util.Map;


public class GardenProductActivity extends AppCompatActivity {


    private View v;
    private ArrayList<AllProductPojo> allProductPojos=new ArrayList<AllProductPojo>();
    private RecyclerView rv_all_products;
    private GoogleApiClient googleApiClient;
    private Location loc;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest1;
    private FusedLocationProviderClient fusedLocationClient;
    private BottomNavigationView bottomNavigationView;
    private String userid,lat,lng;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);


        // Inflate the layout for this fragment

        setTitle(getResources().getString(R.string.productavailable));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initView();
        if(getIntent().hasExtra("userid")){
            userid=getIntent().getStringExtra("userid");
            lat=getIntent().getStringExtra("lat");
            lng=getIntent().getStringExtra("lng");
        }



        Validation validation = new Validation();

        if (!validation.checkPermissions(GardenProductActivity.this)) {

            ActivityCompat.requestPermissions(GardenProductActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            },2);

        } else {

            getGpsEnable();





        }



    }



    private void getGpsEnable(){


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(GardenProductActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(GardenProductActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(GardenProductActivity.this);

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(GardenProductActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {

                                    Log.d("sdsadasd",String.valueOf(location.getLatitude()));

                                    PrefManager.setCURRENTLAT(String.valueOf(location.getLatitude()),GardenProductActivity.this);
                                    PrefManager.setCURRENTLNG(String.valueOf(location.getLongitude()),GardenProductActivity.this);
                                    getAllProductsDetails();

                                }
                                else{
                                    getAllProductsDetails();

                                }
                            }
                        });



            }
        });

        task.addOnFailureListener(GardenProductActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(GardenProductActivity.this,
                                5);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });
    }


    public void initView()
    {
        rv_all_products=findViewById(R.id.rv_all_products);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(GardenProductActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
    }
    //initViewClose

    public  void getAllProductsDetails() {
      //  allProductPojos.clear();


        RequestQueue requestQueue= Volley.newRequestQueue(GardenProductActivity.this);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("adasds",response);
                allProductPojos.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("productlist");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo=new AllProductPojo();
                        allProductPojo.setProductId(jsonObject1.getString("product_id"));
                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                        allProductPojo.setPrice(jsonObject1.getString("price"));
                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
                        allProductPojo.setWeight(jsonObject1.getString("weight"));
                        allProductPojo.setUnit(jsonObject1.getString("unit"));

                       if(jsonObject1.getString("rating").equals("null")){
                           allProductPojo.setRating("0");

                       }
                       else {
                           allProductPojo.setRating(jsonObject1.getString("rating"));

                        }
                        allProductPojo.setDistance(jsonObject1.getString("km"));
                        allProductPojos.add(allProductPojo);



                    }

                    AllProductAdapter adapter=new AllProductAdapter(allProductPojos,GardenProductActivity.this);
                    rv_all_products.setAdapter(adapter);
                    rv_all_products.setHasFixedSize(true);
                    rv_all_products.setItemViewCacheSize(20);
                    rv_all_products.setDrawingCacheEnabled(true);
                    rv_all_products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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

                    Toast.makeText(GardenProductActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(GardenProductActivity.this, message, Toast.LENGTH_SHORT).show();

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();



                 if(!PrefManager.getLAT(GardenProductActivity.this).equals("") && !PrefManager.getLNG(GardenProductActivity.this).equals("")){
                    param.put("latitude",PrefManager.getLAT(GardenProductActivity.this));
                    param.put("longitude", PrefManager.getLNG(GardenProductActivity.this));
                }
              else  if (!PrefManager.getCURRENTLAT(GardenProductActivity.this).equals("") && !PrefManager.getCURRENTLNG(GardenProductActivity.this).equals("")) {
                    param.put("latitude", PrefManager.getCURRENTLAT(GardenProductActivity.this));
                    param.put("longitude", PrefManager.getCURRENTLNG(GardenProductActivity.this));
                }
//                else{
//                    param.put("latitude",lat);
//                    param.put("longitude",lng);
//                }

   //             param.put("searchbtn", "filtergarden");
                param.put("selected_user_id",userid);
              Log.d("useridfrompro",userid+ " "+ lat+" "+lng);
                return param;
            }
        };

        requestQueue.add(stringRequest);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {

                getGpsEnable();


            }

            if (resultCode == Activity.RESULT_CANCELED) {

                getAllProductsDetails();
            }
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getGpsEnable();
                } else {
                   getAllProductsDetails();
                }
                return;
            }


        }
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
