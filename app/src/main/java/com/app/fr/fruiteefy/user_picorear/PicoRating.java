package com.app.fr.fruiteefy.user_picorear;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
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
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;



public class PicoRating extends AppCompatActivity {

    RatingBar ratingBarpunctuality,ratingBarquality,ratingBarconfirmity;
    TextView submit;
    EditText opinion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antigaspi_rating);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ratingBarpunctuality=findViewById(R.id.ratingBarpunctuality);
        ratingBarquality=findViewById(R.id.ratingBarquality);
        ratingBarconfirmity=findViewById(R.id.ratingBarconfirmity);
        submit=findViewById(R.id.submit);
        opinion=findViewById(R.id.opinion);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Validation validation=new Validation();
                if(ratingBarpunctuality.getRating()==0){
                    Toast.makeText(PicoRating.this, getResources().getString(R.string.pleasegiveratingpunctuality), Toast.LENGTH_SHORT).show();

                }
                else  if(ratingBarquality.getRating()==0){
                    Toast.makeText(PicoRating.this, getResources().getString(R.string.pleasegiveratingquanity), Toast.LENGTH_SHORT).show();
                }
                else  if(ratingBarconfirmity.getRating()==0){
                    Toast.makeText(PicoRating.this, getResources().getString(R.string.pleasegiveratingconfirmity), Toast.LENGTH_SHORT).show();
                }
                else if(!validation.edttxtvalidation(opinion, PicoRating.this)){

                }

                else {


                    CustomUtil.ShowDialog(PicoRating.this,false);
                    RequestQueue requestQueue= Volley.newRequestQueue(PicoRating.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat(""), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            CustomUtil.DismissDialog(PicoRating.this);

                            Log.d("ffasf",response);

                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("status").equals("1")){
                                   finish();
                                }
                                Toast.makeText(PicoRating.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("ffasf",volleyError.toString());
                            CustomUtil.DismissDialog(PicoRating.this);
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

                                Toast.makeText(PicoRating.this, message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PicoRating.this, message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> param=new HashMap<>();
                            param.put("pun_rating", String.valueOf(ratingBarpunctuality.getRating()));
                            param.put("qua_rating",String.valueOf(ratingBarquality.getRating()));
                            param.put("con_rating",String.valueOf(ratingBarconfirmity.getRating()));
                            param.put("review",opinion.getText().toString());
                            param.put("user_id", PrefManager.getUserId(PicoRating.this));
                            param.put("nick_name",PrefManager.getFirstName(PicoRating.this));

                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);

                }
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
