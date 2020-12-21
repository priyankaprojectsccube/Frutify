package com.app.fr.fruiteefy.user_client.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;

import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;
import com.app.fr.fruiteefy.user_picorear.Adapter.SlotsAdapter;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.user_client.products.remainigAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToCartActivity extends AppCompatActivity {
TextView producttitle;
AppBarLayout app_bar_layout;
    private Spinner spiner_combo;
    private Button plus,minus;
    private RecyclerView recview1;
    private LinearLayout layoutcollectdate;
    private TextView count;
    private  String pid;
    private String  deliverfees,collectdate;
    private String stockid,radioValue,availablequantity;
    private RadioButton radiodeliver,radiocollect;
    private ArrayList<String> comboArrList=new ArrayList<>();
    private ArrayList<ProductPojo> arrcomboproj=new ArrayList<>();
    private RadioGroup radioGroup;
    private String offeravaildate;
ImageView iv_back,header_image;
RecyclerView availableslots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        initView();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              if(checkedId==R.id.radiocollect){
                  layoutcollectdate.setVisibility(View.VISIBLE);
              }

                if(checkedId==R.id.radiodeliver){
                    layoutcollectdate.setVisibility(View.GONE);
                }
            }
        });

        availableslots=findViewById(R.id.availableslots);
        Intent intent=getIntent();
        pid=intent.getStringExtra("produ_id");
        offeravaildate=intent.getStringExtra("offeravaidate");
        Log.d("sdad",pid);

        String str[] = offeravaildate.split(",");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);
        for(String s: al){
            System.out.println(s);
        }

        Log.d("sdasda",al.toString());

        availableslots.setLayoutManager(new GridLayoutManager(AddToCartActivity.this, 3));
        availableslots.setAdapter(new SlotsAdapter( al, AddToCartActivity.this));




        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num= Integer.parseInt(count.getText().toString());
                int available= Integer.parseInt(availablequantity);
                    if(num<available) {
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



        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        iv_back.setImageDrawable(getDrawable(R.drawable.ic_back_white));

                    }
                    else
                    {
                        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_white));


                    }


                }
                else
                {
                    iv_back.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));



                }
            }
        });


        CustomUtil.ShowDialog(AddToCartActivity.this,false);
        RequestQueue requestQueue= Volley.newRequestQueue(AddToCartActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_product_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                CustomUtil.DismissDialog(AddToCartActivity.this);
                Log.d("ffgfdgf",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArrayCombo;
                    JSONObject jsonObject1=jsonObject.getJSONObject("product_details");
                    if( jsonObject.getString("status").equals("1")){
                        Picasso.with(AddToCartActivity.this).load(BaseUrl.PRODUCTURL.concat(jsonObject1.getString("product_image"))).into(header_image);
                        producttitle.setText(jsonObject1.getString("product_name"));

                        deliverfees=jsonObject1.getString("delivery_fees");
                       // collectdate=jsonObject1.getString("collect_date");
                        if(jsonObject1.getString("delivery").equals("0")){
                            radiodeliver.setVisibility(View.GONE);
                            radiocollect.setChecked(true);
                        }
                        if(jsonObject1.getString("collect").equals("")){
                            radiodeliver.setChecked(true);
                            radiocollect.setVisibility(View.GONE);
                        }

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
                                comboArrList.add(jsonObject2.getString("weight").concat(" " + jsonObject2.getString("unit")));
                                Log.d("dfdfdf", String.valueOf(comboArrList));
                                ProductPojo productPojo = new ProductPojo();
                                productPojo.setStock(jsonObject2.getString("stock"));
                                productPojo.setIngweight(jsonObject2.getString("weight"));
                                productPojo.setIngunit(jsonObject2.getString("unit"));
                                productPojo.setPrice(jsonObject2.getString("price"));
                                arrcomboproj.add(productPojo);
                                Log.d("dsfdsdsdf", String.valueOf(comboArrList.size() + " " + jsonObject2.getString("stock")));
                            }

                        }
                        else {
                            Log.d("take","test");
                            availablequantity = "0";
                        }

                        spiner_combo.setAdapter(new ArrayAdapter<String>(AddToCartActivity.this, android.R.layout.simple_spinner_dropdown_item, comboArrList));
                        recview1.setLayoutManager(new LinearLayoutManager(AddToCartActivity.this));
                        recview1.setAdapter(new remainigAdapter(arrcomboproj,AddToCartActivity.this));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                CustomUtil.DismissDialog(AddToCartActivity.this);


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

                    Toast.makeText(AddToCartActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddToCartActivity.this, message, Toast.LENGTH_SHORT).show();

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
        app_bar_layout=findViewById(R.id.app_bar_layout);
        iv_back=findViewById(R.id.iv_back);
        layoutcollectdate=findViewById(R.id.layoutcollectdate);
        header_image=findViewById(R.id.header_image);
       producttitle=findViewById(R.id.productname);
        spiner_combo=findViewById(R.id.spiner_combo);
        recview1=findViewById(R.id.recvew);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        count=findViewById(R.id.count);
        radiodeliver=findViewById(R.id.radiodeliver);
        radiocollect=findViewById(R.id.radiocollect);
        radioGroup=findViewById(R.id.radioGroup);

    }//closeInitView

    public void addtowishlist(View view) {

        if(PrefManager.IsLogin(AddToCartActivity.this)){


            if(availablequantity.equals("0") ){
                Toast.makeText(this, "Ce produit n'est plus disponible", Toast.LENGTH_SHORT).show();
                
            }else {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_wishlist"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sdsadsa", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(AddToCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        param.put("user_id", PrefManager.getUserId(AddToCartActivity.this));
                        param.put("product_id", stockid);

                        Log.d("dfdsfsdfsd", stockid);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        }
        else{
            Intent intent=new Intent(AddToCartActivity.this, LoginActivity.class);
            intent.putExtra("data","login");
            startActivity(intent);
        }
    }

    public void addtobasket(View view) {
        
 
        int countno= Integer.parseInt(count.getText().toString());
        int availcount= Integer.parseInt(availablequantity);
        
        final int selectedId = radioGroup.getCheckedRadioButtonId();
        Log.d("dsgdg", String.valueOf(selectedId));
        Log.d("dadd",PrefManager.getUserId(AddToCartActivity.this));
        if (radiodeliver.isShown()&& radiodeliver.isChecked()) {
            radioValue = "1";
        } else if (radiocollect.isShown()&& radiocollect.isChecked()) {
            radioValue = "2";
        }

        if (selectedId == -1) {

            Toast.makeText(this, "Veuillez sélectionner le mode de livraison", Toast.LENGTH_SHORT).show();
        } 
        
        else if(availcount<countno){
            Toast.makeText(this, "quantité non disponible", Toast.LENGTH_SHORT).show();
        }
            else {

            if (PrefManager.IsLogin(AddToCartActivity.this)) {
                CustomUtil.ShowDialog(AddToCartActivity.this,false);
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_cart"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sdsadsa", response);
                        CustomUtil.DismissDialog(AddToCartActivity.this);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {

                                count.setText("1");


                                    final Dialog dialog = new Dialog(AddToCartActivity.this); // Context, this, etc.
                                    dialog.setContentView(R.layout.layout_cartconfirmation);
                                    dialog.show();

                                    TextView gotocart,ok;
                                gotocart=dialog.findViewById(R.id.gotocart);
                                ok=dialog.findViewById(R.id.ok);

                                gotocart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(AddToCartActivity.this, UserCliantHomeActivity.class);
                                        intent.putExtra("homeact","cart");
                                        startActivity(intent);
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
                            else if (jsonObject.getString("status").equals("0"))  {
                                Toast.makeText(AddToCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }


                           else if (jsonObject.getString("status").equals("2")) {


                                final Dialog dialog = new Dialog(AddToCartActivity.this); // Context, this, etc.
                                dialog.setContentView(R.layout.layout_clearcartconfirmation);
                                dialog.show();

                                TextView clearcart,ok;
                                clearcart=dialog.findViewById(R.id.clearcart);
                                ok=dialog.findViewById(R.id.ok);

                                clearcart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                      RequestQueue  requestQueue = Volley.newRequestQueue(AddToCartActivity.this);
                                      StringRequest  stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("clear_cart"), new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (jsonObject.getString("status").equals("1")) {

                                                        RequestQueue requestQueue = Volley.newRequestQueue(AddToCartActivity.this);
                                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("add_to_cart"), new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                Log.d("sdsadsa", response);
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    if (jsonObject.getString("status").equals("1")) {


                                                                                Intent intent=new Intent(AddToCartActivity.this, UserCliantHomeActivity.class);
                                                                                intent.putExtra("homeact","cart");
                                                                                startActivity(intent);



                                                                        //    getSupportFragmentManage().beginTransaction().replace(R.id.rl_main, new MyCartFragment()).commit();

                                                                    }



                                                                    else {
                                                                        Toast.makeText(AddToCartActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                                                                param.put("user_id", PrefManager.getUserId(AddToCartActivity.this));
                                                                param.put("quentity", count.getText().toString());
                                                                param.put("stock_id", stockid);
                                                                param.put("delivery_fees", deliverfees);
                                                                param.put("collect_date", offeravaildate);
                                                                param.put("radioValue", radioValue);


                                                                Log.d("ssadad",offeravaildate);

                                                                return param;
                                                            }
                                                        };
                                                        requestQueue.add(stringRequest);









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

                                                    Toast.makeText(AddToCartActivity.this, message, Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(AddToCartActivity.this, "", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> param = new HashMap<>();
                                                param.put("user_id", PrefManager.getUserId(AddToCartActivity.this));
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomUtil.DismissDialog(AddToCartActivity.this);
                        Log.d("sdsadsa", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();

                        param.put("user_id", PrefManager.getUserId(AddToCartActivity.this));
                        param.put("quentity", count.getText().toString());
                        param.put("stock_id", stockid);
                        param.put("delivery_fees", deliverfees);
                        param.put("collect_date", offeravaildate);
                        param.put("radioValue", radioValue);


                        Log.d("ssadad",offeravaildate);

                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }

            else{
                Intent intent=new Intent(AddToCartActivity.this, LoginActivity.class);
                intent.putExtra("data","login");
                startActivity(intent);
            }
        }
    }
}
