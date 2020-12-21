package com.app.fr.fruiteefy.user_client.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.app.fr.fruiteefy.user_client.products.ProductPojo;
import com.app.fr.fruiteefy.user_picorear.Adapter.ReceipyproductavailableAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReceipyDetailActivity extends AppCompatActivity {
    private String productid;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private RelativeLayout fbshare,twittershare;
    private ImageView iv_product_image;
    private TextView productname,receipe,antigaspitips;
    private RecyclerView recvew,recvewreview;
    private TextView addcomment;
    private ArrayList<ProductPojo> arrproductPojos;
    private ArrayList<ProductPojo> ratingPojos;
    LinearLayout     linlayantigaspitips,linlayingrediant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipy_detail);

        setTitle(getResources().getString(R.string.recipes));

        iv_product_image=findViewById(R.id.iv_product_image);
        productname=findViewById(R.id.productname);
        receipe=findViewById(R.id.receipe);
        fbshare=findViewById(R.id.fbshare);
        twittershare=findViewById(R.id.twittershare);
        recvewreview=findViewById(R.id.recvewreview);
        antigaspitips=findViewById(R.id.antigaspitips);
        linlayantigaspitips=findViewById(R.id.linlayantigaspitips);
        linlayingrediant=findViewById(R.id.linlayingrediant);
        addcomment=findViewById(R.id.addcomment);
        arrproductPojos=new ArrayList<>();
        ratingPojos=new ArrayList<>();
        recvew=findViewById(R.id.recvew);


        if(getIntent().hasExtra("receipyid")){
            productid=getIntent().getStringExtra("receipyid");

        }

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Log.d("dfdfsf",productid);

        requestQueue= Volley.newRequestQueue(ReceipyDetailActivity.this);
        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("recipes_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("dsadsad", response);

                try {
                    JSONObject jsonObject1 = new JSONObject(response);

                    JSONObject jsonObject = jsonObject1.getJSONObject("product_details");
                    if (!jsonObject.getString("product_image").equals("")) {
                        Picasso.with(ReceipyDetailActivity.this).load(BaseUrl.RECEPYPICURL.concat(jsonObject.getString("product_image"))).into(iv_product_image);
                    }

                    productname.setText(jsonObject.getString("product_name"));
                    receipe.setText(jsonObject.getString("recipy_desc"));


                    if (!jsonObject.getString("tips").equals("")) {
                        linlayantigaspitips.setVisibility(View.VISIBLE);
                        antigaspitips.setText(jsonObject.getString("tips"));
                    }


                    JSONArray jsonArray = jsonObject1.getJSONArray("product_inc");
                    Log.d("hjjjj", String.valueOf(jsonArray));

                    if (jsonArray.toString().equals("[]")) {
                        linlayingrediant.setVisibility(View.GONE);
                    } else {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            ProductPojo productPojo = new ProductPojo();
                            productPojo.setStock(jsonObject2.getString("incrediant_name"));
                            productPojo.setPrice(jsonObject2.getString("weight"));
                            productPojo.setIngunit(jsonObject2.getString("unit"));


                            arrproductPojos.add(productPojo);

                        }

                        Log.d("kkkkk", String.valueOf(arrproductPojos.size()));

                        recvew.setLayoutManager(new LinearLayoutManager(ReceipyDetailActivity.this));
                        recvew.setAdapter(new ReceipyproductavailableAdapter(arrproductPojos, ReceipyDetailActivity.this));

                    }


                        JSONArray jsonArray1 = jsonObject1.getJSONArray("reviews");
                        Log.d("hjjjj", String.valueOf(jsonArray1));

                        if (jsonArray1.toString().equals("[]")) {

                        } else {

                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                                ProductPojo productPojo = new ProductPojo();
                                productPojo.setTitle(jsonObject2.getString("title"));
                                productPojo.setNickname(jsonObject2.getString("nick_name"));
                                productPojo.setReview(jsonObject2.getString("review"));
                                productPojo.setRating(jsonObject2.getString("rating"));
                                ratingPojos.add(productPojo);

                            }

                            Log.d("kkkkk", String.valueOf(ratingPojos.size()));

                            recvewreview.setLayoutManager(new LinearLayoutManager(ReceipyDetailActivity.this));
                            recvewreview.setAdapter(new Reviewratingadapter(ratingPojos, ReceipyDetailActivity.this));



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dsadsad",volleyError.toString());
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

                    Toast.makeText(ReceipyDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ReceipyDetailActivity.this, "", Toast.LENGTH_SHORT).show();

                }
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("product_id",productid);
                return param;
            }
        };

        requestQueue.add(stringRequest);




     addcomment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(ReceipyDetailActivity.this,ReceipyAddcomment.class);
             intent.putExtra("productid",productid);
             startActivity(intent);

         }
     });





        fbshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url="https://www.fruiteefy.fr/client/Front/recipes_details/"+productid;

                boolean installed = checkAppInstall("com.facebook.katana");
                if (installed) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.facebook.katana");
                    intent.putExtra(Intent.EXTRA_TITLE,url);
                    intent.putExtra(Intent.EXTRA_TEXT,url);
                    intent.putExtra(Intent.EXTRA_STREAM, url);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/sharer.php?u="+url));
                    startActivity(intent);
                }
            }
        });

        twittershare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://www.fruiteefy.fr/client/Front/recipes_details/"+productid;

                boolean installed = checkAppInstall("com.twitter.android");
                if (installed) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.twitter.android");
                    intent.putExtra(Intent.EXTRA_TITLE,url);
                    intent.putExtra(Intent.EXTRA_TEXT,url);
                    intent.putExtra(Intent.EXTRA_STREAM, url);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com/share?text=Fruiteefy &url="+url));
                    startActivity(intent);

                }

            }
        });



    }


    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
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
