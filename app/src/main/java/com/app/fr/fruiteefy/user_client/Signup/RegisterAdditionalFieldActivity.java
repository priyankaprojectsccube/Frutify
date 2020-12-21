package com.app.fr.fruiteefy.user_client.Signup;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import androidx.annotation.Nullable;

import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.UserAntigaspiHomeActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterAdditionalFieldActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{


  //  private Spinner spiner_category;
    private ArrayList<String> categoryArrList;
    private String posiam;
  //  private TextInputLayout textInputLayout;
    private EditText loc,edt_city,edt_state,edt_country,edt_zip;
    private Button submit;
    private boolean iam_selstat=false;
    String lat,lng,addr,city,country,state,zip,type
            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_additional_field);
       // spiner_category=findViewById(R.id.spiner_category);
    //    textInputLayout=findViewById(R.id.lay1);
        //et_company=findViewById(R.id.company);
        submit=findViewById(R.id.submit);
        loc=findViewById(R.id.loc);
        edt_city=findViewById(R.id.edt_city);
        edt_state=findViewById(R.id.edt_state);
        edt_country=findViewById(R.id.edt_country);
        edt_zip=findViewById(R.id.edt_zip);
        categoryArrList=new ArrayList<>();

        final Intent intent=getIntent();
         type=intent.getStringExtra("type");

         Log.d("dgfdgfd",type);

         categoryArrList.add(0,getResources().getString(R.string.select));
        categoryArrList.add(1,getResources().getString(R.string.particular));
        categoryArrList.add(2,getResources().getString(R.string.association));
     //   spiner_category.setOnItemSelectedListener(this);

        loc.setText(PrefManager.getAddress(RegisterAdditionalFieldActivity.this));
        edt_city.setText(PrefManager.getCity(RegisterAdditionalFieldActivity.this));
        edt_state.setText(PrefManager.getState(RegisterAdditionalFieldActivity.this));
        edt_country.setText(PrefManager.getCOUNTRY(RegisterAdditionalFieldActivity.this));
        edt_zip.setText(PrefManager.getZip(RegisterAdditionalFieldActivity.this));


       // spiner_category.setAdapter(new ArrayAdapter<String>(RegisterAdditionalFieldActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryArrList));


        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(RegisterAdditionalFieldActivity.this,  getResources().getString(R.string.google_maps_key));
                    PlacesClient placesClient = Places.createClient(RegisterAdditionalFieldActivity.this);
                }


                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.PLUS_CODE);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .setCountry("FR")
                        .build(RegisterAdditionalFieldActivity.this);
                startActivityForResult(intent, 1);
            }
        });

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(type.equals("picorear")){
                sendReq("become_picoreur");

            }
            else if(type.equals("antigaspi")){

                sendReq("become_antigespi");

            }

        }
    });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d("sfsdfds", String.valueOf(position));


        if(position==2){
            posiam="Company";
          //  textInputLayout.setVisibility(View.VISIBLE);
            iam_selstat=true;
        }
        else if(position==1){
            posiam="Individual";
         //   textInputLayout.setVisibility(View.GONE);
            iam_selstat=true;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                // piclatlng = Autocomplete.getPlaceFromIntent(data).getLatLng();


                loc.setText(Autocomplete.getPlaceFromIntent(data).getAddress());
                Autocomplete.getPlaceFromIntent(data).getLatLng();


                lat= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude);
                lng= String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude);

                PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().latitude),RegisterAdditionalFieldActivity.this);
                PrefManager.setLAT(String.valueOf(Autocomplete.getPlaceFromIntent(data).getLatLng().longitude),RegisterAdditionalFieldActivity.this);
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


    private void sendReq(final String url){

      //  textInputLayout.getVisibility();
        Log.d("ddfdfdsf", url);

//        if(iam_selstat==false){
//            Toast.makeText(this, getResources().getString(R.string.select_iam), Toast.LENGTH_SHORT).show();
//        }
//
//        else if(textInputLayout.getVisibility()==View.VISIBLE && et_company.getText().toString().equals("")){
//            et_company.setError(getResources().getString(R.string.fill_field));
//        }
//        else {



            RequestQueue requestQueue = Volley.newRequestQueue(RegisterAdditionalFieldActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat(url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("bcvbvcbvb", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("0")) {
                            Toast.makeText(RegisterAdditionalFieldActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                        }
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(RegisterAdditionalFieldActivity.this, jsonObject.getString("messge"), Toast.LENGTH_SHORT).show();
                            if(type.equals("picorear")){


                                RequestQueue requestQueue= Volley.newRequestQueue(RegisterAdditionalFieldActivity.this);

                                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("profile"), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        Log.d("ssdd",response);

                                        try {
                                            JSONObject jsonObject=new JSONObject(response);

                                            if(jsonObject.getString("status").equals("1")){


                                                JSONObject jsonObject1=jsonObject.getJSONObject("profiledata");
                                                if (PrefManager.IsLogin(RegisterAdditionalFieldActivity.this)) {

                                                    if (jsonObject1.getString("is_pico").equals("0")) {
                                                        Intent intent = new Intent(RegisterAdditionalFieldActivity.this, RegisterAdditionalFieldActivity.class);
                                                        intent.putExtra("type", "picorear");
                                                        startActivity(intent);

                                                    }

                                                    else if(jsonObject1.getString("subscribe_status").equals("0")){
                                                        //startActivity(new Intent(RegisterAdditionalFieldActivity.this, SubscriptionActivity.class));
                                                        startActivity(new Intent(RegisterAdditionalFieldActivity.this, UserPicorearHomeActivity.class));


                                                    }
                                                    else if (jsonObject1.getString("is_approve").equals("1")) {
                                                       // Toast.makeText(RegisterAdditionalFieldActivity.this, getResources().getString(R.string.pleaseckecksubmail), Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegisterAdditionalFieldActivity.this, UserPicorearHomeActivity.class));

                                                    }

                                                    else if (jsonObject1.getString("is_approve").equals("2")) {
                                                        startActivity(new Intent(RegisterAdditionalFieldActivity.this, UserPicorearHomeActivity.class));

                                                    }

                                                } else {
                                                    Intent intent = new Intent(RegisterAdditionalFieldActivity.this, LoginActivity.class);
                                                    intent.putExtra("data", "picoact");
                                                    startActivity(intent);

                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                         finish();

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){


                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> param = new HashMap<>();
                                        param.put("user_id", PrefManager.getUserId(RegisterAdditionalFieldActivity.this));

                                        return param;
                                    }
                                };

                                requestQueue.add(stringRequest);



                            }

                            else if(type.equals("antigaspi")){
                                startActivity(new Intent(RegisterAdditionalFieldActivity.this, UserAntigaspiHomeActivity.class));
                                JSONObject jsonObject1=jsonObject.getJSONObject("user_details");
                                PrefManager.setIsAnti(jsonObject1.getString("is_anti"),RegisterAdditionalFieldActivity.this);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("bcvbvcbvb", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();

                    param.put("i_am", PrefManager.getIam(RegisterAdditionalFieldActivity.this));
                    param.put("companyname", PrefManager.getIsCompanyname(RegisterAdditionalFieldActivity.this));
                    param.put("address", loc.getText().toString());
                    param.put("lat", PrefManager.getLAT(RegisterAdditionalFieldActivity.this));
                    param.put("lng", PrefManager.getLNG(RegisterAdditionalFieldActivity.this));
                    param.put("city", edt_city.getText().toString());
                    param.put("country", edt_country.getText().toString());
                    param.put("state", edt_state.getText().toString());
                    param.put("zip", edt_zip.getText().toString());
                    param.put("user_id", PrefManager.getUserId(RegisterAdditionalFieldActivity.this));

                    return param;


                }
            };

            requestQueue.add(stringRequest);
       // }
    }
}
