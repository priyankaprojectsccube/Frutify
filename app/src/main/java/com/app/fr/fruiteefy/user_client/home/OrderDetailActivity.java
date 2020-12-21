package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
import com.app.fr.fruiteefy.user_client.adapter.OrderDetailAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {

    String orderid;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    TextView date,price,transactioncharge;
    RecyclerView recvew;
    String str;
    private ArrayList<AllProductPojo> wishlistarr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        recvew=findViewById(R.id.recvew);
        date=findViewById(R.id.date);
        price=findViewById(R.id.price);
        transactioncharge=findViewById(R.id.transactioncharge);

        setTitle(getResources().getString(R.string.orderdetail));

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if(getIntent().hasExtra("order_id")){
            orderid=getIntent().getStringExtra("order_id");
        }

        setTitle(getResources().getString(R.string.command)+" "+orderid);
        CustomUtil.ShowDialog(OrderDetailActivity.this,true);
        requestQueue= Volley.newRequestQueue(OrderDetailActivity.this);
        stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("orders_details"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomUtil.DismissDialog(OrderDetailActivity.this);

                Log.d("dfdfdsf",response);



                wishlistarr.clear();

                Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);





                    JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setGardenid(jsonObject1.getString("product_id"));
                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                        allProductPojo.setPrice(jsonObject1.getString("price"));
                        allProductPojo.setReviewstatus(jsonObject1.getString("reviewstatus"));
                        allProductPojo.setRating(jsonObject1.getString("rating"));
                        allProductPojo.setUnit(jsonObject1.getString("quentity"));
                        allProductPojo.setWeight(jsonObject1.getString("sub_total"));
                        allProductPojo.setDeliverycharge(jsonObject1.getString("delivery_fees"));
                        allProductPojo.setStatus(jsonObject1.getString("status"));

                        Log.d("sdfdsfsf", String.valueOf(allProductPojo));

                        wishlistarr.add(allProductPojo);


                    }
                    Log.d("dsdd", String.valueOf(wishlistarr.size()));

                    OrderDetailAdapter adapter = new OrderDetailAdapter(wishlistarr, OrderDetailActivity.this);
                    recvew.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
                    recvew.setAdapter(adapter);



                    String inputPattern = "dd-MM-yyyy";
                    String outputPattern = "dd MMM yyyy";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                    Date date1 = null;
                    String str = null;

                    try {
                        date1 = inputFormat.parse(jsonObject.getString("order_date"));
                        str = outputFormat.format(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    price.setText("Total "+jsonObject.getString("final_price").concat(getResources().getString(R.string.currency)));
                    date.setText("Commande le "+str);
                    Double a= Double.valueOf(jsonObject.getString("final_price"));
                    Double b= Double.valueOf(jsonObject.getString("final_total"));
                    Double servicecharge=a-b;
                    transactioncharge.setText("Dont frais de transaction "+String.format("%.2f", servicecharge)+" "+(getResources().getString(R.string.currency)));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomUtil.DismissDialog(OrderDetailActivity.this);

                Log.d("dfdfdsf", String.valueOf(volleyError));

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
                    Toast.makeText(OrderDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>  param=new HashMap<>();

                param.put("user_id", PrefManager.getUserId(OrderDetailActivity.this));
                param.put("order_id",orderid);
                return param;
            }
        };

        requestQueue.add(stringRequest);


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
