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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommentOrderDetailActivity extends AppCompatActivity {

    String productid;
    EditText     titleo,reviewtitle,comment;
    TextView submitcomment;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_order_detail);
        setTitle(getResources().getString(R.string.orderdetail));
        titleo=findViewById(R.id.titleo);
        reviewtitle=findViewById(R.id.reviewtitle);
        comment=findViewById(R.id.comment);
        submitcomment=findViewById(R.id.submitcomment);
        ratingBar=findViewById(R.id.ratingBar);

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if(getIntent().hasExtra("productid")){

            productid=getIntent().getStringExtra("productid");

        }



        submitcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation validation=new Validation();



        if(ratingBar.getRating()==0){

           Toast.makeText(CommentOrderDetailActivity.this, getResources().getString(R.string.pleasegiverating), Toast.LENGTH_SHORT).show();
        }

        else if(!validation.edttxtvalidation(titleo,CommentOrderDetailActivity.this)){

        }

        else if(!validation.edttxtvalidation(reviewtitle,CommentOrderDetailActivity.this)){

        }

        else if(!validation.edttxtvalidation(comment,CommentOrderDetailActivity.this)){

        }
        else {

            CustomUtil.ShowDialog(CommentOrderDetailActivity.this,false);

            requestQueue= Volley.newRequestQueue(CommentOrderDetailActivity.this);
            stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_review_order_product"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    CustomUtil.DismissDialog(CommentOrderDetailActivity.this);
                    Log.d("dsdsdads",response);

                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(response);

                    if(jsonObject.getString("status").equals("1")){
                        finish();
                        Toast.makeText(CommentOrderDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    CustomUtil.DismissDialog(CommentOrderDetailActivity.this);

                    Log.d("dsdsdads",volleyError.toString());

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

                        Toast.makeText(CommentOrderDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(CommentOrderDetailActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                    }
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> param=new HashMap<>();

                    param.put("user_id", PrefManager.getUserId(CommentOrderDetailActivity.this));
                    param.put("nick_name",titleo.getText().toString());
                    param.put("product_id",productid);
                    param.put("rating", String.valueOf(ratingBar.getRating()));
                    param.put("title",reviewtitle.getText().toString());
                    param.put("review",comment.getText().toString());

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
