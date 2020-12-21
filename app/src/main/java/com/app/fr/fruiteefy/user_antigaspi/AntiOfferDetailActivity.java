package com.app.fr.fruiteefy.user_antigaspi;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AntiOfferDetailActivity extends AppCompatActivity {

    TextView title,addr,productdetail;
    ImageView iv_product_image;
    Button opinioncommentry;
    FloatingActionButton fabedit,fabdlete;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_offer);

        setTitle(getResources().getString(R.string.myoffer));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        requestQueue= Volley.newRequestQueue(AntiOfferDetailActivity.this);

        title=findViewById(R.id.title);
        addr=findViewById(R.id.addr);
        opinioncommentry=findViewById(R.id.opinioncommentry);
        productdetail=findViewById(R.id.productdetail);
        iv_product_image=findViewById(R.id.iv_product_image);
        fabedit=findViewById(R.id.fabedit);
        fabdlete=findViewById(R.id.fabdlete);

        if(getIntent().hasExtra("offerdata")) {

            Intent intent = getIntent();
            final Product product= (Product) intent.getSerializableExtra("offerdata");
            Picasso.with(AntiOfferDetailActivity.this).load(BaseUrl.ANTIGASPIURL.concat(product.getProductimg())).into(iv_product_image);
            Log.d("dffsfsfssf",product.getDesc());
            title.setText(product.getProductname());
           if(product.getAvailable().equals("1")){

               //fabedit.setVisibility(View.VISIBLE);
              // fabdlete.setVisibility(View.VISIBLE);
           }
            addr.setText(product.getProductname().concat(" "+getResources().getString(R.string.from).concat(" "+ PrefManager
                    .getFirstName(AntiOfferDetailActivity.this).concat(" "+PrefManager.getLastName(AntiOfferDetailActivity.this)).concat(" "+AntiOfferDetailActivity.this.getResources().getString(R.string.locatedat)+" "+product.getOfferPlace()))));

            productdetail.setText(product.getDesc());
            if(product.getAvailable().equals("2")){
                opinioncommentry.setVisibility(View.VISIBLE);
            }

            opinioncommentry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(AntiOfferDetailActivity.this,AntigaspiRating.class));
                }
            });

            fabdlete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("offerdelete"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("ffgfdg",response);

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
                            param.put("offer_id",product.getOfferid());
                            param.put("user_id",PrefManager.getUserId(AntiOfferDetailActivity.this));

                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
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
