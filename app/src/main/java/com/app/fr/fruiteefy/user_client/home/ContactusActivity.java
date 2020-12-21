package com.app.fr.fruiteefy.user_client.home;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.app.fr.fruiteefy.Util.Validation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactusActivity extends AppCompatActivity implements OnMapReadyCallback {

    EditText et_fname,et_lname,et_email,et_comment,et_phoneno;
    Button btn_submit;
    private GoogleMap mMap;


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
        setContentView(R.layout.activity_contactus);
        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.contactus));

        et_fname=findViewById(R.id.et_fname);
        et_lname=findViewById(R.id.et_lname);
        et_phoneno=findViewById(R.id.et_phoneno);
        et_email=findViewById(R.id.et_email);
        et_comment=findViewById(R.id.et_comment);
        btn_submit=findViewById(R.id.btn_submit);





        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation validation=new Validation();

                if(!validation.edttxtvalidation(et_fname,ContactusActivity.this)){

                }
                else  if(!validation.edttxtvalidation(et_lname,ContactusActivity.this)){

                }
                else  if(!validation.edttxtvalidation(et_email,ContactusActivity.this)){

                }
                else  if(!validation.emailvalidation(et_email,ContactusActivity.this)){

                }
                else  if(!validation.edttxtvalidation(et_phoneno,ContactusActivity.this)){

                }
                else  if(!validation.edttxtvalidation(et_comment,ContactusActivity.this)){

                }
               else {

                    RequestQueue requestQueue = Volley.newRequestQueue(ContactusActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("contactus"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("dsdsdsds", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(ContactusActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            startActivity(new Intent(ContactusActivity.this, UserCliantHomeActivity.class));

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("dsdsdsds", error.toString());
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("firstname", et_fname.getText().toString());
                            param.put("lastname", et_lname.getText().toString());
                            param.put("email", et_email.getText().toString());
                            param.put("contactno", et_phoneno.getText().toString());
                            param.put("message", et_comment.getText().toString());
                            return param;
                        }

                    };


                    requestQueue.add(stringRequest);

                }

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        this.mMap = mMap;

        LatLng latLngnew=new LatLng(43.491070,1.402630);


        mMap.addMarker(new MarkerOptions().position(latLngnew).title("11 Route de Lacroix falgarde, 31860, Pins-Justaret\n"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngnew,14));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngnew));
      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        mMap.getUiSettings().setZoomGesturesEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }
}
