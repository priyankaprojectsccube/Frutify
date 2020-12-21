package com.app.fr.fruiteefy.user_antigaspi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.user_client.products.ProductDetailReceiptAdapter;
import com.app.fr.fruiteefy.user_client.products.ProductPojo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AntiProductDetailsActivity extends AppCompatActivity {

    //FloatingActionButton fb_add_to_cart;
    String productid;
    TextView productdetail,receipe,antigaspitips,current_price;
    ImageView iv_product_image;
    ArrayList<ProductPojo> arrproductPojos;
    LinearLayout linlayantigaspitips;
    RecyclerView recview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_product_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        Intent intent=getIntent();
        if(intent!=null) {
            productid = intent.getStringExtra("productid");
        }

        arrproductPojos=new ArrayList<>();

        RequestQueue requestQueue= Volley.newRequestQueue(AntiProductDetailsActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ffgfdgf",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1=jsonObject.getJSONObject("product_details");
                    if( jsonObject.getString("status").equals("1")) {


                        Picasso.with(AntiProductDetailsActivity.this).load(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("product_image"))).into(iv_product_image);
                        // tv_price.setText(getResources().getString(R.string.min_price));
                        // tv_remaining.setText(jsonObject1.getString("stock").concat(" "+getResources().getString(R.string.remainng_items)));
                        productdetail.setText(jsonObject1.getString("description"));


                        setTitle(jsonObject1.getString("product_name"));
                        if(!jsonObject1.getString("recipy_desc").equals("")) {
                            receipe.setText(jsonObject1.getString("recipy_desc"));
                        }
                        else {
//                            linlayreceipy.setVisibility(View.GONE);
                        }


                        if(!jsonObject1.getString("tips").equals("")){
                            linlayantigaspitips.setVisibility(View.VISIBLE);
                            antigaspitips.setText(jsonObject1.getString("tips"));

                        }
//                        piconame.setText(jsonObject1.getString("firstname").concat(" " + jsonObject1.getString("lastname")));
                     //   picoaddress.setText(jsonObject1.getString("address"));

                        JSONArray jsonArray2 = jsonObject.getJSONArray("product_stock");

                        if (jsonArray2.toString().equals("[]")) {

                           // linlayingridiant.setVisibility(View.GONE);

                        }else {

                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject3  = jsonArray2.getJSONObject(i);

                                current_price.setText(jsonObject3.getString("price")+" "+getResources().getString(R.string.currency)+"("+jsonObject3.getString("weight")
                                +" "+jsonObject3.getString("unit")+")");


                            }

                        }





                        JSONArray jsonArray = jsonObject.getJSONArray("product_inc");
                        Log.d("hjjjj", String.valueOf(jsonArray));





                        if (jsonArray.toString().equals("[]")) {

                           // linlayingridiant.setVisibility(View.GONE);

                        }else {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                ProductPojo productPojo = new ProductPojo();
                                productPojo.setIngname(jsonObject2.getString("incrediant_name"));
                                productPojo.setIngweight(jsonObject2.getString("weight"));
                                productPojo.setIngunit(jsonObject2.getString("unit"));

                                arrproductPojos.add(productPojo);

                            }

                            Log.d("kkkkdssdsk", String.valueOf(arrproductPojos.size()));

                            recview1.setLayoutManager(new LinearLayoutManager(AntiProductDetailsActivity.this));
                            recview1.setAdapter(new ProductDetailReceiptAdapter(arrproductPojos, AntiProductDetailsActivity.this));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                Log.d("ffgfdgf",volleyError.toString());
                volleyError.printStackTrace();
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

                    Toast.makeText(AntiProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AntiProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

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
//        fb_add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//                if(PrefManager.IsLogin(AntiProductDetailsActivity.this)) {
//                    Intent intent1=new Intent(AntiProductDetailsActivity.this, AddToCartActivity.class);
//                    intent1.putExtra("produ_id",productid);
//                    startActivity(intent1);
//                }
//                else{
//                    Intent intent=new Intent(AntiProductDetailsActivity.this, LoginActivity.class);
//                    intent.putExtra("data","login");
//                    startActivity(intent);
//                }
//            }
//        });

    }//onCreateClose




    public void initView()
    {
       // fb_add_to_cart=findViewById(R.id.fb_add_to_cart);
        iv_product_image=findViewById(R.id.iv_product_image);
        linlayantigaspitips=findViewById(R.id.linlayantigaspitips);
        antigaspitips=findViewById(R.id.antigaspitips);
     //   linlayingridiant=findViewById(R.id.linlayingridiant);
        current_price=findViewById(R.id.current_price);
        //  tv_price=findViewById(R.id.tv_price);
        productdetail=findViewById(R.id. productdetail);
       // linlayreceipy=findViewById(R.id. linlayreceipy);
        receipe=findViewById(R.id. receipe);
        // tv_remaining=findViewById(R.id.tv_remaining);
      //  recview1=findViewById(R.id.recvew);
       // piconame=findViewById(R.id. piconame);
        //picoaddress=findViewById(R.id. picoaddress);


    }//initViewClose

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
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
