package com.app.fr.fruiteefy.user_client.profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditprofileActivity extends AppCompatActivity {


    //  PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude),EditprofileActivity.this);
    //  PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude),EditprofileActivity.this);

    private EditText edt_fname,edt_lname,spiner_location,edt_city,edt_state,edt_country,edt_zip;
    private View parentLayout;
    private EditText edt_dob,edt_mobileno;
    private String lat="",lng="";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle(getResources().getString(R.string.editprofile));

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        parentLayout = findViewById(android.R.id.content);
        edt_dob=findViewById(R.id.edt_dob);
        edt_mobileno=findViewById(R.id.edt_mobileno);
        Init();
        onClick();


        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mPreviousYear = getCalculatedDate("yyyy-MM-dd", -20);

                Calendar calendar = Calendar.getInstance();
                String[] mstrDate = mPreviousYear.split("-");
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(EditprofileActivity.this,android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edt_dob.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);

                    }
                }, Integer.parseInt(mstrDate[0]), Integer.parseInt(mstrDate[1]), Integer.parseInt(mstrDate[2]));
                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.getDatePicker().setMaxDate(System.currentTimeMillis());
                dp.show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(EditprofileActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void editprofile(View view) {


        Validation validation=new Validation();
        if(!validation.edttxtvalidation(edt_fname,EditprofileActivity.this)){

        }
        else  if(!validation.edttxtvalidation(edt_lname,EditprofileActivity.this)){

        }
        else if(!validation.edttxtvalidation(edt_dob,EditprofileActivity.this)){

        }

        else if(!validation.edttxtvalidation(edt_mobileno,EditprofileActivity.this)){

        }
        else if(!validation.edttxtvalidation(spiner_location,EditprofileActivity.this)){

        }
        else{
            CustomUtil.ShowDialog(EditprofileActivity.this,false);
            RequestQueue requestQueue= Volley.newRequestQueue(EditprofileActivity.this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile_update"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    CustomUtil.DismissDialog(EditprofileActivity.this);

                    Log.d("sdsdsdsd",response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("status").equals("1")){



                            Toast.makeText(EditprofileActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject1=jsonObject.getJSONObject("user_details");
                            PrefManager.setdob(jsonObject1.getString("birthday"),EditprofileActivity.this);
Log.d("getname",jsonObject1.getString("firstname"));
                            PrefManager.setMobile(jsonObject1.getString("phoneno"),EditprofileActivity.this);
                            PrefManager.setFirstName(jsonObject1.getString("firstname"),EditprofileActivity.this);
                            PrefManager.setLastName(jsonObject1.getString("lastname"),EditprofileActivity.this);
                            PrefManager.setAddress(jsonObject1.getString("address"),EditprofileActivity.this);
                            PrefManager.setCity(jsonObject1.getString("city"),EditprofileActivity.this);
                            PrefManager.setCOUNTRY(jsonObject1.getString("country"),EditprofileActivity.this);
                            PrefManager.setSTATE(jsonObject1.getString("state"),EditprofileActivity.this);
                            PrefManager.setLAT(jsonObject1.getString("lat"),EditprofileActivity.this);;
                            PrefManager.setLNG(jsonObject1.getString("lng"),EditprofileActivity.this);
                            PrefManager.setZip(jsonObject1.getString("zip"),EditprofileActivity.this);



                            if(getIntent().hasExtra("activity")){

                                if(getIntent().getStringExtra("activity").equals("home")) {
                                    Intent intent = new Intent(EditprofileActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                    finish();
                                }

                            }
                            else {


                                Intent intent = new Intent(EditprofileActivity.this, UserCliantHomeActivity.class);//ProfileActivity
                                startActivity(intent);

                                finish();
                            }

                        }
                        else if(jsonObject.getString("status").equals("0")){


                            Snackbar.make(parentLayout,jsonObject.getString("message") , Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    CustomUtil.DismissDialog(EditprofileActivity.this);

                    Log.d("sdsdsdsd",volleyError.toString());

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
                        Toast.makeText(EditprofileActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(EditprofileActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> param=new HashMap<>();

                    param.put("user_id", PrefManager.getUserId(EditprofileActivity.this ));
                    param.put("lastname",edt_lname.getText().toString());
                    param.put("firstname",edt_fname.getText().toString());
                    param.put("phoneno",edt_mobileno.getText().toString());
                    param.put("dateofbirth",edt_dob.getText().toString());
                    param.put("address",spiner_location.getText().toString());
                    if(!lat.equals("") && !lng.equals("")) {
                        param.put("lat", lat);
                        param.put("lng",lng);
                    }
                    else{
                        param.put("lat", PrefManager.getLAT(EditprofileActivity.this));
                        param.put("lng",PrefManager.getLNG(EditprofileActivity.this));
                    }

                    param.put("city",edt_city.getText().toString());
                    param.put("state",edt_state.getText().toString());
                    param.put("country",edt_country.getText().toString());
                    param.put("zip",edt_zip.getText().toString());

                    return param;

                }


            };

            requestQueue.add(stringRequest);

        }
    }

    private void Init(){


        edt_fname=findViewById(R.id.edt_fname);
        edt_lname=findViewById(R.id.edt_lname);
        spiner_location=findViewById(R.id.spiner_location);
      //  img_prof=findViewById(R.id.img_prof);
       // tv_title=findViewById(R.id.tv_title);
       // iv_back=findViewById(R.id.iv_back);
        edt_city=findViewById(R.id.edt_city);
        edt_state=findViewById(R.id.edt_state);
        edt_country=findViewById(R.id.edt_country);
        edt_zip=findViewById(R.id.edt_zip);
    }

    private void onClick(){

      //  tv_title.setText(getResources().getString(R.string.edt_prof_act));

//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(EditprofileActivity.this,ProfileActivity.class));
//                finish();
//
//            }
//        });





        edt_fname.setText(PrefManager.getFirstName(EditprofileActivity.this));
        edt_lname.setText(PrefManager.getLastName(EditprofileActivity.this));
        edt_mobileno.setText(PrefManager.getMobile(EditprofileActivity.this));
        if(!PrefManager.getdob(EditprofileActivity.this).equals("null")) {
            edt_dob.setText(PrefManager.getdob(EditprofileActivity.this));
        }
        spiner_location.setText(PrefManager.getAddress(EditprofileActivity.this));
        edt_city.setText(PrefManager.getCity(EditprofileActivity.this));
        edt_state.setText(PrefManager.getState(EditprofileActivity.this));
        edt_country.setText(PrefManager.getCOUNTRY(EditprofileActivity.this));
        edt_zip.setText(PrefManager.getZip(EditprofileActivity.this));

        spiner_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!Places.isInitialized()) {
                    Places.initialize(EditprofileActivity.this,  getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(EditprofileActivity.this);
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setCountry("FR")
                        .build(EditprofileActivity.this);
                startActivityForResult(intent, 1);
            }
        });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                spiner_location.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
                Autocomplete.getPlaceFromIntent(data).getLatLng();


                lat= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                lng= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);


                Geocoder geocoder = new Geocoder(this);
                try
                {
                    List<Address> addresses = geocoder.getFromLocation(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude,Autocomplete.getPlaceFromIntent(data).getLatLng().longitude, 1);
                    if(addresses!=null) {
                        edt_state.setText(addresses.get(0).getAdminArea());
                        edt_city.setText(addresses.get(0).getLocality());
                        edt_country.setText(addresses.get(0).getCountryName());
                        edt_zip.setText(addresses.get(0).getPostalCode());
                    }

                } catch (IOException e)
                {

                    e.printStackTrace();
                }

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
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(EditprofileActivity.this,ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public static String getCalculatedDate(String dateFormat, int YEARS) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.YEAR, YEARS);
        return s.format(new Date(cal.getTimeInMillis()));
    }

}
