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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.adapter.PagerListAdapter;
import com.app.fr.fruiteefy.user_picorear.Adapter.GardensCategoryAdapter;
import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeetheGardenActivity extends AppCompatActivity {

    String id,img,rating,name,Token="";
    TextView aboutme, picoaddress,seeavailableproduct,sendmessage,type;
    RecyclerView recview;
    private PageIndicatorView indicator;
    private LoopingViewPager mPager;
    RatingBar ratingBar;
    ImageView share;
    private ArrayList<String> arrayListimg=new ArrayList<>();
   // RelativeLayout fbshare,twittershare;
    private ArrayList<Product> antiprodArr;
    private String title="";
    private ArrayList<Product> antisubprodArr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seethe_garden);


        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        antiprodArr=new ArrayList<>();
        aboutme=findViewById(R.id.aboutme);
     //   fbshare=findViewById(R.id.fbshare);
        mPager = findViewById(R.id.pager);
        share = findViewById(R.id.share);
        type=findViewById(R.id.type);
        indicator = findViewById(R.id.indicator);
        sendmessage=findViewById(R.id.sendmessage);
        ratingBar=findViewById(R.id.ratingBar);
        //twittershare=findViewById(R.id.twittershare);
        picoaddress=findViewById(R.id.picoaddress);
        recview=findViewById(R.id.recview);
        seeavailableproduct=findViewById(R.id.seeavailableproduct);


        if(getIntent().hasExtra("userid")){

            id=getIntent().getStringExtra("userid");
        }

        if(getIntent().hasExtra("img")){

            img=getIntent().getStringExtra("img");

        }

        if(getIntent().hasExtra("rating")){

            rating=getIntent().getStringExtra("rating");
        }


        if(getIntent().hasExtra("aboutme")){

            aboutme.setText(getIntent().getStringExtra("aboutme"));
        }


        if(getIntent().hasExtra("name")){

            name=getIntent().getStringExtra("name");
        }

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(PrefManager.IsLogin(SeetheGardenActivity.this)) {

                    Intent intent=new Intent(SeetheGardenActivity.this, MessageActivity.class);
                    intent.putExtra("receiverid",id);
                    intent.putExtra("name",name);
                    intent.putExtra("token",Token);
                    startActivity(intent);
                }
                else{

                    Intent intent=new Intent(SeetheGardenActivity.this, LoginActivity.class);
                    intent.putExtra("data","login");
                    startActivity(intent);

                }

            }
        });


        seeavailableproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SeetheGardenActivity.this, GardenProductActivity.class);
                intent.putExtra("userid",id);
                startActivity(intent);
            }
        });








        RequestQueue requestQueue= Volley.newRequestQueue(SeetheGardenActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("user_garden_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dsdsdsad",response);
                try {
                    antiprodArr.clear();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONObject jsonObject1=jsonObject.getJSONObject("userdata");
                        setTitle(jsonObject1.getString("firstname").toUpperCase()+" "+jsonObject1.getString("lastname").toUpperCase());

                        Token=jsonObject1.getString("fcm_token");
                        ratingBar.setRating(Float.parseFloat(rating));
                        picoaddress.setText(jsonObject1.getString("city"));
                        type.setText(jsonObject.getString("dropdown"));

                        title=jsonObject1.getString("firstname")+" "+jsonObject1.getString("lastname");


                        JSONArray jsonarrimg=jsonObject.getJSONArray("images");


                        for(int i=0;i<jsonarrimg.length();i++){
                            AllProductPojo allProductPojo=new AllProductPojo();
                            JSONObject jsonObjectimg=jsonarrimg.getJSONObject(i);

                            Log.d("fsfsfs",jsonObjectimg.getString("image"));
                            arrayListimg.add(BaseUrl.GARDENURL.concat(jsonObjectimg.getString("image")));

                        }


                        Log.d("dfsdfsdff", String.valueOf(arrayListimg.size()));
                        if(arrayListimg.size()==0){
                            arrayListimg.add("https://fruiteefy.fr/assets_client/img/core-img/icon.png");
                        }

                        mPager.setInterval(60000);
                        mPager.setAdapter(new PagerListAdapter(SeetheGardenActivity.this,arrayListimg,true));

                        indicator.setCount(mPager.getIndicatorCount());
                        mPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                            @Override
                            public void onIndicatorProgress(int selectingPosition, float progress) {
                                indicator.setProgress(selectingPosition, progress);
                            }

                            @Override
                            public void onIndicatorPageChange(int newIndicatorPosition) {
//                indicatorView.setSelection(newIndicatorPosition);
                            }
                        });



                        JSONArray jsonArray=jsonObject.getJSONArray("catlist");

                        antisubprodArr.clear();
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                            Product product = new Product();

                            product.setProductname(jsonObject3.getString("cat_name"));



                            antiprodArr.add(product);
                            JSONArray jsonArray1=jsonObject3.getJSONArray("finalres");
                            for(int i1=0;i1<jsonArray1.length();i1++) {


                                JSONObject jsonObject5 = jsonArray1.getJSONObject(i1);

                                Product product6 = new Product();

                                product6.setProductname(jsonObject5.getString("subcatname"));
                                product6.setProductimg(jsonObject5.getString("subcatimage"));
                                antiprodArr.get(antiprodArr.size()-1).getmArrSubList().add(product6);
                              //  antisubprodArr.add(product6);

                            }


                         //   product.setAntisubprodlist(antisubprodArr);

                          //  antiprodArr.add(product);
                        }

                        GardensCategoryAdapter adapter = new GardensCategoryAdapter(antiprodArr, SeetheGardenActivity.this);
                        recview.setLayoutManager(new LinearLayoutManager(SeetheGardenActivity.this));
                        recview.setAdapter(adapter);
                        recview.setHasFixedSize(true);
                        recview.setItemViewCacheSize(20);
                        recview.setDrawingCacheEnabled(true);
                        recview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                    }
                  //  Toast.makeText(SeetheGardenActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("fsdfsdfdsf",error.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<>();
                param.put("user_id",id);
                Log.d("sdfdsfdsf",id);
                return param;
            }
        };

        requestQueue.add(stringRequest);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newproductname=title.replaceAll(" ","_");
                String url="https://fruiteefy.fr/Jardins/"+newproductname+"/"+id;


//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, url);
//                intent.putExtra(Intent.EXTRA_TITLE,url);
//                intent.putExtra(Intent.EXTRA_STREAM, url);
//                startActivity(Intent.createChooser(intent, "Share Via"));

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Try This");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


//        fbshare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                String newproductname=title.replaceAll(" ","_");
//
//                String url="https://fruiteefy.fr/Jardins/"+newproductname+"/"+id;
//
//
//                boolean installed = checkAppInstall("com.facebook.katana");
//                if (installed) {
//
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.setPackage("com.facebook.katana");
//                    intent.putExtra(Intent.EXTRA_TITLE,url);
//                    intent.putExtra(Intent.EXTRA_TEXT,url);
//                    intent.putExtra(Intent.EXTRA_STREAM, url);
//                    startActivity(intent);
//
//                } else {
//
//                    Intent intent = new Intent();
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/sharer.php?u="+url));
//                    startActivity(intent);
//                }
//            }
//        });
//
//        twittershare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String newproductname=title.replaceAll(" ","_");
//
//                String url="https://fruiteefy.fr/Jardins/"+newproductname+"/"+id;
//
//                boolean installed = checkAppInstall("com.twitter.android");
//                if (installed) {
//
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.setPackage("com.twitter.android");
//                    intent.putExtra(Intent.EXTRA_TITLE,url);
//                    intent.putExtra(Intent.EXTRA_TEXT,url);
//                    intent.putExtra(Intent.EXTRA_STREAM, url);
//                    startActivity(intent);
//                } else {
//
//                    Intent intent = new Intent();
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com/share?text=Fruiteefy &url="+url));
//                    startActivity(intent);
//
//                }
//
//            }
//        });



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
