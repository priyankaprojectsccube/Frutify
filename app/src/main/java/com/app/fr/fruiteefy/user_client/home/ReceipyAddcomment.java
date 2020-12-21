package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReceipyAddcomment extends AppCompatActivity {

    TextView submit;
    EditText username,reviewtitle,comment;

    StringRequest stringRequest;
    RequestQueue requestQueue;
    RatingBar ratingBar;
    String productid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipy_addcomment);

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

       setTitle(" ");

        requestQueue= Volley.newRequestQueue(ReceipyAddcomment.this);




        username=findViewById(R.id.username);
        reviewtitle=findViewById(R.id.reviewtitle);
        comment=findViewById(R.id.comment);
        submit=findViewById(R.id.submit);
        ratingBar=findViewById(R.id.ratingBar);

        if(getIntent().hasExtra("productid")){
            productid=getIntent().getStringExtra("productid");

        }




       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Validation validation=new Validation();

               if(ratingBar.getRating()==0){
                   Toast.makeText(ReceipyAddcomment.this, getResources().getString(R.string.pleasegiverating), Toast.LENGTH_SHORT).show();
               }

               else if(!validation.edttxtvalidation(username,ReceipyAddcomment.this)){

               }

               else  if(!validation.edttxtvalidation(reviewtitle,ReceipyAddcomment.this)){

               }
               else  if(!validation.edttxtvalidation(comment,ReceipyAddcomment.this)){

               }



               else {
                   stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_review_recipy"), new
                           Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {

                                   Log.d("dfsfdf", response);

                                   try {
                                       JSONObject jsonObject=new JSONObject(response);

                                       if(jsonObject.getString("status").equals("1")){
                                           finish();
                                       }
                                       Toast.makeText(ReceipyAddcomment.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }


                               }


                           }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError volleyError) {
                           Log.d("dfsfdf", volleyError.toString());

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

                               Toast.makeText(ReceipyAddcomment.this, message, Toast.LENGTH_SHORT).show();

                           } else {
                               Toast.makeText(ReceipyAddcomment.this, "An error occured", Toast.LENGTH_SHORT).show();

                           }

                       }
                   }) {

                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {

                           Map<String, String> param = new HashMap<>();

                           Log.d("dfdsfdsf",productid+" "+PrefManager.getUserId(ReceipyAddcomment.this));
                           param.put("product_id", productid);
                           param.put("nick_name", username.getText().toString());
                           param.put("user_id", PrefManager.getUserId(ReceipyAddcomment.this));
                           param.put("rating", String.valueOf(ratingBar.getRating()));
                           param.put("title", reviewtitle.getText().toString());
                           param.put("review", comment.getText().toString());
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
