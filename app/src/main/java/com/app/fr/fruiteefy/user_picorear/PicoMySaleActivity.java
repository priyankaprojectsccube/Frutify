package com.app.fr.fruiteefy.user_picorear;

import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.app.fr.fruiteefy.user_picorear.Adapter.MySaleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicoMySaleActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recviewpicosale;
    ArrayList<Product> allProductPojos=new ArrayList<>();
    MySaleAdapter mySaleAdapter;
    private String searchString="";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        MenuItem menuItem=menu.findItem(R.id.actionsearch);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda",query);
        this.searchString=query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductname().toLowerCase();
            String text1 = model.getOfferid().toLowerCase();
            String text2 = model.getOfferPlace().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) || text1.contains(query) || text2.contains(query) ) {
                filteredModelList.add(model);
            }
        }

        MySaleAdapter adapter=new MySaleAdapter(filteredModelList,PicoMySaleActivity.this);

        recviewpicosale.setLayoutManager(new LinearLayoutManager(PicoMySaleActivity.this));
        recviewpicosale.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Product> filteredModelList = filter(allProductPojos, newText);
        if (filteredModelList.size() > 0) {

            new MySaleAdapter(allProductPojos,PicoMySaleActivity.this).setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pico_my_sale);

        setTitle(getResources().getString(R.string.mysale));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recviewpicosale=findViewById(R.id.recviewpicosale);


        RequestQueue  requestQueue= Volley.newRequestQueue(PicoMySaleActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("mysales"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Cgdsgsdfs",response);



                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("result");

                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Product product=new Product();
                            product.setProductimg(jsonObject1.getString("product_image"));
                            product.setProductname(jsonObject1.getString("product_name"));
                           product.setOfferid(jsonObject1.getString("totalsold"));
                           product.setOfferPlace(jsonObject1.getString("stock"));
//                            product.setPrice(jsonObject1.getString("offer_price")+" "+getResources().getString(R.string.currency));
//                            product.setStock(jsonObject1.getString("stock")+" "+jsonObject1.getString("stock_unit"));
//                            product.setStatus(jsonObject1.getString("status"));
//                            product.setOffertype(jsonObject1.getString("offer_status"));
                           allProductPojos.add(product);
                        }

                        recviewpicosale.setLayoutManager(new LinearLayoutManager(PicoMySaleActivity.this));
                        recviewpicosale.setAdapter(new MySaleAdapter(allProductPojos,PicoMySaleActivity.this));
                        recviewpicosale.getAdapter().notifyDataSetChanged();

                        Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                Log.d("dsdsdads",volleyError.toString());

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

                    Toast.makeText(PicoMySaleActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PicoMySaleActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("user_id", PrefManager.getUserId(PicoMySaleActivity.this));
                return param;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
