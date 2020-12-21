package com.app.fr.fruiteefy.user_client.home;

import android.app.Dialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.app.fr.fruiteefy.user_client.products.ProductPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovetocartActivity extends AppCompatActivity {
  //  TextView producttitle;
   // AppBarLayout app_bar_layout;
    private Spinner spiner_combo;
    private Button plus,minus;
   // private RecyclerView recview1;
    private TextView count;
    private  String pid,wishlistid;
    private String  deliverfees,collectdate;
    private String stockid,radioValue;
    private RadioButton radiodeliver,radiocollect;
    private ArrayList<String> comboArrList=new ArrayList<>();
    private ArrayList<ProductPojo> arrcomboproj=new ArrayList<>();
    private RadioGroup radioGroup;
    //ImageView iv_back,iv_search,iv_basket;
    //header_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movetocart);
        initView();

        setTitle(getResources().getString(R.string.movetocart));


        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        pid=intent.getStringExtra("produ_id");
if(getIntent().hasExtra("wishlistid")) {
    wishlistid=getIntent().getStringExtra("wishlistid");
}
        Log.d("sdad",pid);

      //  iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num= Integer.parseInt(count.getText().toString());
                if(num<100) {
                    count.setText((num + 1) + "");
                    Log.d("ddddddddddddddd", (String) count.getText());
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num= Integer.parseInt(count.getText().toString());
                if(num>1) {
                    count.setText((num - 1) + "");
                    Log.d("ddddddddddddddd", (String) count.getText());
                }
            }
        });
//        tv_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             startActivity(new Intent(AddToCartActivity.this,AddAddressActivity.class));
//            }
//        });

//        iv_basket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MovetocartActivity.this, MyCartActivity.class));
//            }
//        });

//        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
//                {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        iv_back.setImageDrawable(getDrawable(R.drawable.ic_back_white));
//                        iv_search.setImageDrawable(getDrawable(R.drawable.search_white));
//                        iv_basket.setImageDrawable(getDrawable(R.drawable.shop_basket_white));
//                    }
//                    else
//                    {
//                        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_white));
//
//                        iv_search.setImageDrawable(getResources().getDrawable(R.drawable.search_white));
//                        iv_basket.setImageDrawable(getResources().getDrawable(R.drawable.shop_basket_white));
//                    }
//
//
//                }
//                else
//                {
//                    iv_back.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
//                    iv_search.setImageDrawable(getResources().getDrawable(R.drawable.search_black));
//                    iv_basket.setImageDrawable(getResources().getDrawable(R.drawable.shop_basket_black));
//
//
//                }
//            }
//        });


        RequestQueue requestQueue= Volley.newRequestQueue(MovetocartActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ffgfdgf",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArrayCombo;
                    JSONObject jsonObject1=jsonObject.getJSONObject("product_details");
                    if( jsonObject.getString("status").equals("1")){
                       // Picasso.with(MovetocartActivity.this).load(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("product_image"))).into(header_image);
                     //   producttitle.setText(jsonObject1.getString("product_name"));

                        deliverfees=jsonObject1.getString("delivery_fees");
                        // collectdate=jsonObject1.getString("collect_date");
                        if(jsonObject1.getString("delivery").equals("0")){
                            radiodeliver.setVisibility(View.GONE);
                        }
                        if(jsonObject1.getString("collect").equals("")){
                            radiocollect.setVisibility(View.GONE);
                        }

                        jsonArrayCombo=jsonObject.getJSONArray("product_stock");
                        Log.d("kjkjkjkj", String.valueOf(jsonArrayCombo));
                        for (int i=0;i<jsonArrayCombo.length();i++) {

                            JSONObject jsonObject2 = jsonArrayCombo.getJSONObject(i);
                            if(i==0){
                                stockid=jsonObject2.getString("stock_id");
                            }
                            comboArrList.add(jsonObject2.getString("weight").concat(" "+jsonObject2.getString("unit")));
                            Log.d("dfdfdf", String.valueOf(comboArrList));
                            ProductPojo productPojo=new ProductPojo();
                            productPojo.setStock(jsonObject2.getString("stock"));
                            productPojo.setIngweight(jsonObject2.getString("weight"));
                            productPojo.setIngunit(jsonObject2.getString("unit"));
                            productPojo.setPrice(jsonObject2.getString("price"));
                            arrcomboproj.add(productPojo);
                            Log.d("dsfdsdsdf", String.valueOf(comboArrList.size()+" "+jsonObject2.getString("stock")));
                        }
                        spiner_combo.setAdapter(new ArrayAdapter<String>(MovetocartActivity.this, android.R.layout.simple_spinner_dropdown_item, comboArrList));
                     //   recview1.setLayoutManager(new LinearLayoutManager(MovetocartActivity.this));
                      //  recview1.setAdapter(new remainigAdapter(arrcomboproj,MovetocartActivity.this));
                    }


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

                    Toast.makeText(MovetocartActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MovetocartActivity.this, message, Toast.LENGTH_SHORT).show();

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("product_id",pid);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void initView()

    {
           //tv_change=findViewById(R.id.tv_change);
          //  app_bar_layout=findViewById(R.id.app_bar_layout);
         //        iv_back=findViewById(R.id.iv_back);
        //        iv_basket=findViewById(R.id.iv_basket);
       //        iv_search=findViewById(R.id.iv_search);
      //  header_image=findViewById(R.id.header_image);
     //   producttitle=findViewById(R.id.productname);
        spiner_combo=findViewById(R.id.spiner_combo);
      //  recview1=findViewById(R.id.recvew);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        count=findViewById(R.id.count);
        radiodeliver=findViewById(R.id.radiodeliver);
        radiocollect=findViewById(R.id.radiocollect);
        radioGroup=findViewById(R.id.radioGroup);

    }//closeInitView

    public void addtowishlist(View view) {

        if(PrefManager.IsLogin(MovetocartActivity.this)){


            RequestQueue requestQueue=Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_wishlist"), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("sdsadsa",response);
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Toast.makeText(MovetocartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        if(jsonObject.getString("status").equals("1")){

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("sdsadsa",error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> param=new HashMap<>();
                    param.put("user_id", PrefManager.getUserId(MovetocartActivity.this));
                    param.put("product_id",stockid);
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }
        else{
            Intent intent=new Intent(MovetocartActivity.this, LoginActivity.class);
            intent.putExtra("data","login");
            startActivity(intent);
        }
    }

    public void addtobasket(View view) {

        final int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("dsgdg", String.valueOf(selectedId));
        Log.d("dadd",PrefManager.getUserId(MovetocartActivity.this));
        if (selectedId==2131231029) {
            radioValue = String.valueOf(1);
        } else if (selectedId == 2131231028) {
            radioValue = String.valueOf(2);
        }

        if (selectedId == -1) {

            Toast.makeText(this, "Please select mode of delivery", Toast.LENGTH_SHORT).show();
        } else {

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_cart"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sdsadsa", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {
                                //    getSupportFragmentManager().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();



                RequestQueue requestQueue=Volley.newRequestQueue(MovetocartActivity.this);
                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("move_to_cart"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("dfdfdfdsf",response);
                        JSONObject jsonObject1= null;
                        try {




                            Intent intent=new Intent(MovetocartActivity.this,WishlistActivity.class);
                            startActivity(intent);
                            finish();
                            jsonObject1 = new JSONObject(response);

                        Toast.makeText(MovetocartActivity.this, jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("fgfgfgffgf", volleyError.toString());

                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {

                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        if (message != null) {

                            Toast.makeText(MovetocartActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String>  param=new HashMap<>();
                        param.put("user_id",PrefManager.getUserId(MovetocartActivity.this));
                        param.put("wishlist_id",wishlistid);


                        return param;
                    }
                };

                requestQueue.add(stringRequest);


                            }

                           else if (jsonObject.getString("status").equals("2")) {


                                final Dialog dialog = new Dialog(MovetocartActivity.this); // Context, this, etc.
                                dialog.setContentView(R.layout.layout_clearcartconfirmation);
                                dialog.show();

                                TextView clearcart,ok;
                                clearcart=dialog.findViewById(R.id.clearcart);
                                ok=dialog.findViewById(R.id.ok);

                                clearcart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        RequestQueue  requestQueue = Volley.newRequestQueue(MovetocartActivity.this);
                                        StringRequest  stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("clear_cart"), new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (jsonObject.getString("status").equals("1")) {
                                                        startActivity(new Intent(MovetocartActivity.this, UserCliantHomeActivity.class));
                                                    }
                                                    Toast.makeText(MovetocartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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

                                                    Toast.makeText(MovetocartActivity.this, message, Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(MovetocartActivity.this, "", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> param = new HashMap<>();
                                                param.put("user_id", PrefManager.getUserId(MovetocartActivity.this));
                                                return param;
                                            }
                                        };

                                        requestQueue.add(stringRequest);


                                    }
                                });

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                //    getSupportFragmentManage().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();

                            }
                            else {
                                Toast.makeText(MovetocartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                        param.put("user_id", PrefManager.getUserId(MovetocartActivity.this));
                        param.put("quentity", count.getText().toString());
                        param.put("stock_id", stockid);
                        param.put("delivery_fees", deliverfees);
                        param.put("collect_date", "");
                        param.put("radioValue", "1");

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
