package com.app.fr.fruiteefy.user_picorear;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_picorear.Adapter.PicoMyOrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PicoOrderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {





    ArrayList<Product> allProductPojos=new ArrayList<Product>();
    RecyclerView rv_all_products;
    LinearLayout linlay;
    private String searchString="";

    public PicoOrderActivity() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_orderpico);

        setTitle(getResources().getString(R.string.my_order));

        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getAllProductsDetails();
    }



    public void initView()
    {
        rv_all_products=findViewById(R.id.rv_all_products);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(PicoOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_all_products.setLayoutManager(verticalLayoutManager);

    }   //initViewClose

    public  void getAllProductsDetails()
    {
        allProductPojos.clear();
        RequestQueue requestQueue= Volley.newRequestQueue(PicoOrderActivity.this);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("myorder_pico"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("adasdsdsdsdsdds",response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("result");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();
                            product.setProductid(jsonObject1.getString("order_id"));
                            product.setDate(jsonObject1.getString("order_date"));
                            product.setToken(jsonObject1.getString("fcm_token"));
                            product.setPrice(jsonObject1.getString("final_total"));
                            product.setStatus(jsonObject1.getString("order_status"));
                            product.setFirstname(jsonObject1.getString("first_name"));
                            product.setLastname(jsonObject1.getString("last_name"));
                            product.setUserid(jsonObject1.getString("user_id"));
                            product.setValue(jsonObject1.getString("total_without_servic_charge"));
                            product.setDeliverystatus(jsonObject1.getString("product_delivered_status "));
                            product.setValidationstatus(jsonObject1.getString("isvalidate "));
                            allProductPojos.add(product);
                        }

                        rv_all_products.setLayoutManager(new LinearLayoutManager(PicoOrderActivity.this));
                        rv_all_products.setAdapter(new PicoMyOrderAdapter(allProductPojos,PicoOrderActivity.this));
                        rv_all_products.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dsadda",volleyError.toString());
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

                    Toast.makeText(PicoOrderActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PicoOrderActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(PicoOrderActivity.this));
                return param;
            }


        };

        requestQueue.add(stringRequest);


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Product> filteredModelList = filter(allProductPojos, newText);
        if (filteredModelList.size() > 0) {
            new PicoMyOrderAdapter(allProductPojos,PicoOrderActivity.this).setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }



    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda",query);
        this.searchString=query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductid().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) ) {
                filteredModelList.add(model);
            }
        }

        PicoMyOrderAdapter adapter=new PicoMyOrderAdapter(filteredModelList,PicoOrderActivity.this);

        rv_all_products.setLayoutManager(new LinearLayoutManager(PicoOrderActivity.this));
        rv_all_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
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
