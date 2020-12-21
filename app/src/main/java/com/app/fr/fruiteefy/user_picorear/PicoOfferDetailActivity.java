package com.app.fr.fruiteefy.user_picorear;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.app.fr.fruiteefy.user_picorear.Adapter.StockproductavailableAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PicoOfferDetailActivity extends AppCompatActivity {

    TextView title,addr,productdetail,Varietydetails;
    ImageView iv_product_image,green,red;
    Button opinioncommentry;
    FloatingActionButton fabedit,fabdlete;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private ArrayList<ProductPojo> arrcomboproj=new ArrayList<>();
    private ArrayList<ProductPojo> arrcomboproj2=new ArrayList<>();
    RecyclerView recview1,recview2;
    String productid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pico_offer_detail);

        setTitle(getResources().getString(R.string.myoffer));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Varietydetails = findViewById(R.id.Varietydetails);
        title=findViewById(R.id.title);
        addr=findViewById(R.id.addr);
        green=findViewById(R.id.green);
        red=findViewById(R.id.red);
        recview1=findViewById(R.id.recvew);
        opinioncommentry=findViewById(R.id.opinioncommentry);
        productdetail=findViewById(R.id.productdetail);
        iv_product_image=findViewById(R.id.iv_product_image);
        fabedit=findViewById(R.id.fabedit);
        fabdlete=findViewById(R.id.fabdlete);
        recview2=findViewById(R.id.recvewrec);





        if(getIntent().hasExtra("offerdata")) {

            Intent intent = getIntent();
            productid= intent.getStringExtra("offerdata");



            requestQueue= Volley.newRequestQueue(PicoOfferDetailActivity.this);
            stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("product_details_transformer_own"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("dfsff",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        JSONObject jsonObject1=jsonObject.getJSONObject("product_details");

                        if(jsonObject.getString("status").equals("1")){
                            green.setVisibility(View.VISIBLE);
                        }
                        if(jsonObject.getString("status").equals("0")){
                             red.setVisibility(View.VISIBLE);
                        }

                        title.setText(jsonObject1.getString("product_name"));
                        Picasso.with(PicoOfferDetailActivity.this).load(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("product_image"))).into(iv_product_image);

                       // addr.setText(product.getProductname().concat(" "+getResources().getString(R.string.from).concat(" "+ PrefManager
                              //  .getFirstName(PicoOfferDetailActivity.this).concat(" "+PrefManager.getLastName(PicoOfferDetailActivity.this)).concat(" "+PicoOfferDetailActivity.this.getResources().getString(R.string.locatedat)+" "+product.getOfferPlace()))));

                       JSONArray jsonArrayCombo1=jsonObject.getJSONArray("product_stock");
                        Log.d("kjkjkjkj", String.valueOf(jsonArrayCombo1));
                        for (int i=0;i<jsonArrayCombo1.length();i++) {

                            JSONObject jsonObject2 = jsonArrayCombo1.getJSONObject(i);

                            ProductPojo productPojo=new ProductPojo();
                            productPojo.setStock(jsonObject2.getString("stock"));
                            productPojo.setIngweight(jsonObject2.getString("weight"));
                            productPojo.setIngunit(jsonObject2.getString("unit"));
                            productPojo.setPrice(jsonObject2.getString("price"));
                            arrcomboproj.add(productPojo);

                        }

                        recview1.setLayoutManager(new LinearLayoutManager(PicoOfferDetailActivity.this));
                        recview1.setAdapter(new StockproductavailableAdapter(arrcomboproj,PicoOfferDetailActivity.this));


                        JSONArray jsonArrayCombo=jsonObject.getJSONArray("product_inc");
                        Log.d("kjkjkjkj", String.valueOf(jsonArrayCombo));
                        for (int i=0;i<jsonArrayCombo.length();i++) {

                            JSONObject jsonObject2 = jsonArrayCombo.getJSONObject(i);


                            ProductPojo productPojo1=new ProductPojo();


                            productPojo1.setStock(jsonObject2.getString("incrediant_name"));
                            productPojo1.setPrice(jsonObject2.getString("weight"));
                            productPojo1.setIngunit(jsonObject2.getString("unit"));

                            arrcomboproj2.add(productPojo1);

                        }

                        recview2.setLayoutManager(new LinearLayoutManager(PicoOfferDetailActivity.this));
                        recview2.setAdapter(new ReceipyproductavailableAdapter(arrcomboproj2,PicoOfferDetailActivity.this));





                        productdetail.setText(jsonObject1.getString("description"));
                        addr.setText(jsonObject1.getString("recipy_desc"));

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

                        Toast.makeText(PicoOfferDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PicoOfferDetailActivity.this, message, Toast.LENGTH_SHORT).show();

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





        }




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
