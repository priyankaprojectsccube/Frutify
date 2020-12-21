package com.app.fr.fruiteefy.user_client.products;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.app.fr.fruiteefy.user_picorear.Adapter.SlotsAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.user_client.adapter.PagerListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
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
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.cart.AddToCartActivity;
import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
FloatingActionButton fb_add_to_cart,add_whishlist;
     String productid,offeravailabledate="",productname="",availablequantity="",stockid="",stroffertypetowishlist="";
     TextView  productdetail,receipe,piconame,picoaddress,antigaspitips,provenance_of_product,sendmsg,Varietytxt,offertype,addcarttxt,catname,date,time,availableslottxt,timeslot;
     ImageView iv_product_image;
     FloatingActionButton fb_share;
     String name,picoid;
     ImageView    share;
     LinearLayout llca,layoutcollectdate,addcartredll,addcartgreyll;
   //  RelativeLayout fbshare,twittershare;
    private PageIndicatorView indicator;
     ArrayList<ProductPojo> arrproductPojos;
     String Token="";
    RecyclerView availableslots;
     LinearLayout linlayantigaspitips,linlayreceipy,linlayingridiant;
    private LoopingViewPager mPager;
    private ArrayList<String> arrayListimg=new ArrayList<>();;
     RecyclerView recview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        setTitle(getResources().getString(R.string.product_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        Intent intent=getIntent();
        if(intent!=null) {
             productid = intent.getStringExtra("productid");
        }





        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(PrefManager.IsLogin(ProductDetailsActivity.this)) {

                    Intent intent=new Intent(ProductDetailsActivity.this, MessageActivity.class);
                    intent.putExtra("receiverid",picoid);
                    intent.putExtra("token",Token);
                    intent.putExtra("name",name);
                    startActivity(intent);

                }
                else{

                    Intent intent=new Intent(ProductDetailsActivity.this, LoginActivity.class);
                    intent.putExtra("data","login");
                    startActivity(intent);

                }


            }
        });

        arrproductPojos=new ArrayList<>();

        RequestQueue  requestQueue= Volley.newRequestQueue(ProductDetailsActivity.this);
        //get_product_details
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_new_product_details"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("get_new_product_details",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject object=jsonObject.getJSONObject("product_details");

                    //    productid = object.getString("product_id");
                    productname=object.getString("product_name");

                    JSONArray jsonarrimg=jsonObject.getJSONArray("product_images");
                    for(int i=0;i<jsonarrimg.length();i++){
                        JSONObject jsonObject1=jsonarrimg.getJSONObject(i);

                        Log.d("fsfsfs",jsonObject1.getString("image_url"));
                        arrayListimg.add(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("image_url")));

                    }
                    if(arrayListimg.size()==0){
                        arrayListimg.add(BaseUrl.PRODUCTURL.concat(object.getString("product_image")));
                    }
                    Log.d("dfsdfsdff", String.valueOf(arrayListimg));
                    mPager.setAdapter(new PagerListAdapter(ProductDetailsActivity.this,arrayListimg,true));

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
                    JSONArray jsonArrayCombo;
                    JSONObject jsonObject1=jsonObject.getJSONObject("product_details");
                   if( jsonObject.getString("status").equals("1")) {


                       jsonArrayCombo=jsonObject.getJSONArray("product_stock");
                       Log.d("kjkjkjkj", String.valueOf(jsonArrayCombo));
                       if(jsonArrayCombo.length() > 0) {


                           for (int i = 0; i < jsonArrayCombo.length(); i++) {

                               JSONObject jsonObject2 = jsonArrayCombo.getJSONObject(i);
                               Log.d("inkjkjkjkj", String.valueOf(jsonArrayCombo.length()));
                               if (i == 0) {
                                   stockid = jsonObject2.getString("stock_id");
                                   if (jsonObject2.getString("stock") != null) {
                                       availablequantity = jsonObject2.getString("stock");
                                   } else {
                                       availablequantity = "0";
                                   }

                               }
                           }
                       }
                       offeravailabledate=jsonObject1.getString("offer_avail_date");
                       if(jsonObject1.getString("available_type").equals("1")){
                           layoutcollectdate.setVisibility(View.VISIBLE);
                           availableslottxt.setVisibility(View.VISIBLE);
                           availableslots.setVisibility(View.GONE);
                           availableslottxt.setText("Tous les jours");
                           if(jsonObject1.getString("offer_available_time") !=  null || !jsonObject1.getString("offer_available_time").equals("null") ||!jsonObject1.getString("offer_available_time").equals("")){
                               timeslot.setVisibility(View.VISIBLE);
                               timeslot.setText("Horaire:"+" "+jsonObject1.getString("offer_available_time"));
                           }
                       }
                       else if(jsonObject1.getString("available_type").equals("2")){
                           layoutcollectdate.setVisibility(View.VISIBLE);
                           availableslottxt.setVisibility(View.VISIBLE);
                           availableslots.setVisibility(View.GONE);
                           availableslottxt.setText("Chaque weekend");
                           if(jsonObject1.getString("offer_available_time") !=  null || !jsonObject1.getString("offer_available_time").equals("null") ||!jsonObject1.getString("offer_available_time").equals("")){
                               timeslot.setVisibility(View.VISIBLE);
                               timeslot.setText("Horaire:"+" "+jsonObject1.getString("offer_available_time"));
                           }
                       }
                       else if(jsonObject1.getString("available_type").equals("3")) {
                           Log.d("offeravailabledate", offeravailabledate);
                           if (offeravailabledate.equals("null") || offeravailabledate.equals("")) {
                               //  llca.setVisibility(View.GONE);
                               layoutcollectdate.setVisibility(View.GONE);

                           } else {
                               String str[] = offeravailabledate.split(",");
                               List<String> al = new ArrayList<String>();
                               al = Arrays.asList(str);
                               for (String s : al) {
                                   System.out.println(s);
                               }
                               Log.d("sdasda", al.toString());
                               availableslottxt.setVisibility(View.GONE);
availableslots.setVisibility(View.VISIBLE);
                               availableslots.setLayoutManager(new GridLayoutManager(ProductDetailsActivity.this, 3));
                               availableslots.setAdapter(new SlotsAdapter(al, ProductDetailsActivity.this));
                               layoutcollectdate.setVisibility(View.VISIBLE);
if(jsonObject1.getString("offer_available_time") !=  null || !jsonObject1.getString("offer_available_time").equals("null") ||!jsonObject1.getString("offer_available_time").equals("")){
    timeslot.setVisibility(View.VISIBLE);
    timeslot.setText("Horaire:"+" "+jsonObject1.getString("offer_available_time"));
}



//                           llca.setVisibility(View.VISIBLE);
//                           catname.setText(jsonObject1.getString("cat_name"));
//                           date.setText(offeravailabledate);
//                           time.setText(jsonObject1.getString("offer_available_time"));
                           }
                       }
               //        Picasso.with(ProductDetailsActivity.this).load(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("product_image"))).into(iv_product_image);
                       // tv_price.setText(getResources().getString(R.string.min_price));
                       // tv_remaining.setText(jsonObject1.getString("stock").concat(" "+getResources().getString(R.string.remainng_items)));

//                       availablequantity = jsonObject1.getString("stock");
//                       stockid = jsonObject1.getString("stock_id");
//                       Log.d("stockid",jsonObject1.getString("stock_id"));
                       productdetail.setText(jsonObject1.getString("description"));
                       Varietytxt.setText(jsonObject1.getString("variety"));
                       stroffertypetowishlist = jsonObject1.getString("offer_type");
                       offertype.setText(jsonObject1.getString("offer_type"));
                         Log.d("checkofftype",jsonObject1.getString("offer_type"));

                       if(jsonObject1.getString("offer_type").equals("Vente")){
                           addcartgreyll.setVisibility(View.GONE);
                           addcartredll.setVisibility(View.VISIBLE);
                       }
                       else if(jsonObject1.getString("offer_type").equals("Vente (Echange possible)")){
                           addcartgreyll.setVisibility(View.GONE);
                           addcartredll.setVisibility(View.VISIBLE);
                       }
                       else if (jsonObject1.getString("offer_type").equals("Dons")){
                           addcartredll.setVisibility(View.GONE);
                     addcartgreyll.setVisibility(View.VISIBLE);
                       }else  if (jsonObject1.getString("offer_type").equals("Echange")){
                           addcartredll.setVisibility(View.GONE);
                           addcartgreyll.setVisibility(View.VISIBLE);
                       }
                       else  if (jsonObject1.getString("offer_type").equals("Recherche")){
                           addcartredll.setVisibility(View.GONE);
                           addcartgreyll.setVisibility(View.VISIBLE);
                       }


                       if(!jsonObject1.getString("recipy_desc").equals("")) {
                           receipe.setText(jsonObject1.getString("recipy_desc"));
                       }
                       else {
                           linlayreceipy.setVisibility(View.GONE);
                       }

                       provenance_of_product.setText(getResources().getString(R.string.From).concat(" "+jsonObject1.getString("firstname").concat(" " + jsonObject1.getString("lastname")))+" "+getResources().getString(R.string.locatedat)+" "
                       +jsonObject1.getString("city"));



                       if(!jsonObject1.getString("tips").equals("")){
                           linlayantigaspitips.setVisibility(View.VISIBLE);
                           antigaspitips.setText(jsonObject1.getString("tips"));

                       }

                       picoid=jsonObject1.getString("user_id");
                       Token=jsonObject1.getString("fcm_token");
                       name=jsonObject1.getString("firstname").concat(" " + jsonObject1.getString("lastname"));
                       piconame.setText(jsonObject1.getString("firstname").concat(" " + jsonObject1.getString("lastname")));
                       picoaddress.setText(jsonObject1.getString("city"));

                       JSONArray jsonArray = jsonObject.getJSONArray("product_inc");
                       Log.d("hjjjj", String.valueOf(jsonArray));

                       if (jsonArray.toString().equals("[]")) {

                           linlayingridiant.setVisibility(View.GONE);

                       }
                       else {

                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                               ProductPojo productPojo = new ProductPojo();
                               productPojo.setIngname(jsonObject2.getString("incrediant_name"));
                               productPojo.setIngweight(jsonObject2.getString("weight"));
                               productPojo.setIngunit(jsonObject2.getString("unit"));

                               arrproductPojos.add(productPojo);

                           }

                           Log.d("kkkkdssdsk", String.valueOf(arrproductPojos.size()));

                               recview1.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
                               recview1.setAdapter(new ProductDetailReceiptAdapter(arrproductPojos, ProductDetailsActivity.this));
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

                    Toast.makeText(ProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ProductDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newproductname=productname.replaceAll(" ","_");
                String url="https://fruiteefy.fr/Offres/"+newproductname+"/"+productid;
               Log.d("url",url);
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
//                String newproductname=productname.replaceAll(" ","_");
//
//                String url="https://fruiteefy.fr/Acheter/"+newproductname+"/"+productid;
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

//        twittershare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String newproductname=productname.replaceAll(" ","_");
//
//                String url="https://fruiteefy.fr/Acheter/"+newproductname+"/"+productid;
//
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



        fb_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(PrefManager.IsLogin(ProductDetailsActivity.this)) {
                    Intent intent1=new Intent(ProductDetailsActivity.this,AddToCartActivity.class);
                    intent1.putExtra("produ_id",productid);
                    intent1.putExtra("offeravaidate",offeravailabledate);
                    startActivity(intent1);
                }
                else{
                    Intent intent=new Intent(ProductDetailsActivity.this, LoginActivity.class);
                    intent.putExtra("data","login");
                    startActivity(intent);
                }
            }
        });


        add_whishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Vente
//                Vente (Echange possible)
//                Dons
//                        Echange
//                Recherche
                if(stroffertypetowishlist.equals("Vente")){
                    callwhishlist(productid,stockid);
                }
                else if(stroffertypetowishlist.equals("Vente (Echange possible)")){
                    callwhishlist(productid,stockid);
                }
              else if(stroffertypetowishlist.equals("Dons")){
                    callwhishlist(productid,"");
                }
              else if(stroffertypetowishlist.equals("Echange")){
                    callwhishlist(productid,"");
                }
              else if(stroffertypetowishlist.equals("Recherche")){
                    callwhishlist(productid,"");
                }
            }
        });

    }//onCreateClose

    private void callwhishlist(String proid,String stoid) {
        if(PrefManager.IsLogin(ProductDetailsActivity.this)){


            if(availablequantity.equals("0") ){
                Toast.makeText(this, "Ce produit n'est plus disponible", Toast.LENGTH_SHORT).show();

            }
            else {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_wishlist"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("add_to_wishlist", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ProductDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString("status").equals("1")) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sdsadsa", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(ProductDetailsActivity.this));
                        param.put("stock_id", stoid);
                        param.put("product_id",proid);
                        param.put("offer_type",stroffertypetowishlist);

                        Log.d("awtstockid",stoid);
                        Log.d("atwproid", proid );
                        Log.d("atwoty", stroffertypetowishlist );
                        Log.d("atwuid",PrefManager.getUserId(ProductDetailsActivity.this));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        }
        else{
            Intent intent=new Intent(ProductDetailsActivity.this, LoginActivity.class);
            intent.putExtra("data","login");
            startActivity(intent);
        }
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


    public void initView()
    {
        add_whishlist = findViewById(R.id.add_whishlist);
        fb_add_to_cart=findViewById(R.id.fb_add_to_cart);
        iv_product_image=findViewById(R.id.iv_product_image);
        indicator = findViewById(R.id.indicator);
        provenance_of_product=findViewById(R.id.provenance_of_product);
        linlayantigaspitips=findViewById(R.id.linlayantigaspitips);
        antigaspitips=findViewById(R.id.antigaspitips);
        mPager = findViewById(R.id.pager);
        linlayingridiant=findViewById(R.id.linlayingridiant);
        sendmsg=findViewById(R.id.sendmsg);
        share = findViewById(R.id.share);
//        fbshare=findViewById(R.id.fbshare);
//        twittershare=findViewById(R.id.twittershare);
      //  tv_price=findViewById(R.id.tv_price);
        availableslots=findViewById(R.id.availableslots);
        productdetail=findViewById(R.id. productdetail);
        linlayreceipy=findViewById(R.id. linlayreceipy);
        receipe=findViewById(R.id. receipe);
       // tv_remaining=findViewById(R.id.tv_remaining);
        recview1=findViewById(R.id.recvew);
        fb_share=findViewById(R.id.fb_share);
        piconame=findViewById(R.id. piconame);
        picoaddress=findViewById(R.id. picoaddress);
        Varietytxt = findViewById(R.id.Varietytxt);
        offertype = findViewById(R.id.offertype);
        addcarttxt = findViewById(R.id.addcarttxt);
        catname = findViewById(R.id.catname);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        availableslottxt = findViewById(R.id.availableslottxt);
        timeslot = findViewById(R.id.timeslot);
        llca = findViewById(R.id.llca);
        layoutcollectdate= findViewById(R.id.layoutcollectdate);
        addcartredll = findViewById(R.id.addcartredll);
        addcartgreyll = findViewById(R.id.addcartgreyll);
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
