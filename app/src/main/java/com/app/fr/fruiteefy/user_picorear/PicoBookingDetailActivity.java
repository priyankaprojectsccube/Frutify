package com.app.fr.fruiteefy.user_picorear;

import android.content.Intent;
import android.os.Bundle;

import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PicoBookingDetailActivity extends AppCompatActivity {

    TextView title,addr,productdetail;
    ImageView iv_product_image,green,red;
    TextView opinioncommentry,collection,sendmsg;
    FloatingActionButton fabedit,fabdlete;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String name,picoid,Token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pico_booking_detail);

        setTitle(getResources().getString(R.string.mybooking));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        requestQueue= Volley.newRequestQueue(PicoBookingDetailActivity.this);

        title=findViewById(R.id.title);
        addr=findViewById(R.id.addr);
        green=findViewById(R.id.green);
        red=findViewById(R.id.red);
        sendmsg=findViewById(R.id.sendmsg);
        opinioncommentry=findViewById(R.id.opinioncommentry);
        collection=findViewById(R.id.collection);
        productdetail=findViewById(R.id.productdetail);
        iv_product_image=findViewById(R.id.iv_product_image);
        fabedit=findViewById(R.id.fabedit);
        fabdlete=findViewById(R.id.fabdlete);

        if(getIntent().hasExtra("offerdata")) {

            Intent intent = getIntent();
            final Product product= (Product) intent.getSerializableExtra("offerdata");



                    stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("reservation_details"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("gsdfdsgdsg",response);

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONObject jsonObject1=jsonObject.getJSONObject("reservation_details");
                                Picasso.with(PicoBookingDetailActivity.this).load(jsonObject1.getString("offerimage")).into(iv_product_image);
                                title.setText(jsonObject1.getString("item_name"));
                                addr.setText(jsonObject1.getString("offerplace"));
                                picoid=jsonObject1.getString("userid");
                                Token=jsonObject1.getString("fcm_token");
                                name=jsonObject1.getString("firstname").concat(" "+jsonObject1.getString("lastname"));
                              //   productdetail.setText(product.getDesc());

                                if(jsonObject1.getString("status").equals("Reserved")){
                                    green.setVisibility(View.VISIBLE);
                                    red.setVisibility(View.GONE);
                                }
                                else if(jsonObject1.getString("status").equals("Collected")){
                                   red.setVisibility(View.VISIBLE);
                                    green.setVisibility(View.GONE);
                                }


                                Log.d("sdddsdd",jsonObject1.getString("is_collected"));

                                if(jsonObject1.getString("is_collected").equals("1")){



                                    opinioncommentry.setEnabled(false);
                                    opinioncommentry.setFocusable(false);
                                    opinioncommentry.setCursorVisible(false);
                                    opinioncommentry.setKeyListener(null);
                                    opinioncommentry.setAlpha(0.5f);


                                }
                                else{


                                    collection.setEnabled(false);
                                    collection.setFocusable(false);
                                    collection.setCursorVisible(false);
                                    collection.setAlpha(0.5f);

                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





//                            if(product.getAvailable().equals("1")){
//
//                                fabedit.setVisibility(View.VISIBLE);
//                                fabdlete.setVisibility(View.VISIBLE);
//                            }


//                            if(product.getAvailable().equals("2")){
//                                opinioncommentry.setVisibility(View.VISIBLE);
//                            }

                            opinioncommentry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(PicoBookingDetailActivity.this,PicoRating.class));
                                }
                            });





                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("ffgfdg",error.toString());

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<>();
                            param.put("reservation_id",   product.getProductid());


                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);


            sendmsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(PrefManager.IsLogin(PicoBookingDetailActivity.this)) {

                        Intent intent=new Intent(PicoBookingDetailActivity.this, MessageActivity.class);
                        intent.putExtra("token",Token);
                        intent.putExtra("receiverid",picoid);
                        intent.putExtra("name",name);
                        startActivity(intent);

                    }
                    else{

                        Intent intent=new Intent(PicoBookingDetailActivity.this, LoginActivity.class);
                        intent.putExtra("data","login");
                        startActivity(intent);

                    }
                }
            });
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
