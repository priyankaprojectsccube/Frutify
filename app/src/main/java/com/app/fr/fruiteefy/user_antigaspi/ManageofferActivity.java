package com.app.fr.fruiteefy.user_antigaspi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_antigaspi.adapter.ManagemyofferAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageofferActivity extends AppCompatActivity {

    RecyclerView recvewdonation;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<Product> allProductPojos = new ArrayList<>();
    private TextView mTxtNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageoffer);

        setTitle(getResources().getString(R.string.managemyoffer));

        (getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recvewdonation = findViewById(R.id.recvewdonation);
        requestQueue = Volley.newRequestQueue(ManageofferActivity.this);
        mTxtNotFound = findViewById(R.id.notfound);
        getOfferProduct();


    }

    //get Offer List Added by Antigaspi.....
    private void getOfferProduct() {

        CustomUtil.ShowDialog(ManageofferActivity.this, false);
        stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_offer"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CustomUtil.DismissDialog(ManageofferActivity.this);
                 Log.d("dsfdfdfdf",response);

                allProductPojos.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("offerlist");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setProductimg(jsonObject1.getString("offer_image"));
                            product.setProductname(jsonObject1.getString("offer_title"));
                            product.setOfferid(jsonObject1.getString("offer_id"));
                            product.setOffertype(jsonObject1.getString("offer_type"));
                            product.setOfferPlace(jsonObject1.getString("offer_place"));
                            product.setPrice(jsonObject1.getString("offer_price") + " " + getResources().getString(R.string.currency));
                            product.setStock(jsonObject1.getString("stock") + " " + jsonObject1.getString("stock_unit"));
                            product.setStatus(jsonObject1.getString("status"));
                            product.setOffertype(jsonObject1.getString("offer_status"));
                            product.setmZipCode(jsonObject1.getString("zip"));
                            product.setOffer_cat_id(jsonObject1.getString("offer_cat_id"));
                            product.setOffer_subcat_id(jsonObject1.getString("offer_subcat_id"));
                            product.setmStock(jsonObject1.getString("stock"));
                            product.setmStockUnit(jsonObject1.getString("stock_unit"));
                            product.setmPrice(jsonObject1.getString("offer_price"));
                            product.setmOfferAvailableData(jsonObject1.getString("offer_available_date"));
                            product.setmOfferAvailableTime(jsonObject1.getString("offer_available_time"));
                            product.setmPrefered_picoreur(jsonObject1.getString("prefered_picoreur"));
                            product.setmIsTreated(jsonObject1.getString("is_treated"));
                            product.setIsTreadedProductList(jsonObject1.getString("is_treated_product_list"));
                            product.setIsTreaded_Desc(jsonObject1.getString("is_treated_description"));
                            product.setLat(jsonObject1.getString("lat"));
                            product.setLng(jsonObject1.getString("lng"));
                            product.setmSalePick(jsonObject1.getString("selpick"));
                            product.setmAvailableType(jsonObject1.getString("available_type"));
                            product.setmStrQuickAvailbality(jsonObject1.getString("quick_availability"));
                            product.setDon_address_id_fk(jsonObject1.getString("don_address_id_fk"));
                            product.setVariety(jsonObject1.getString("variety"));
                            allProductPojos.add(product);
                        }

                        if (allProductPojos.size() > 0) {
                            recvewdonation.setLayoutManager(new LinearLayoutManager(ManageofferActivity.this));
                            recvewdonation.setAdapter(new ManagemyofferAdapter(allProductPojos, ManageofferActivity.this, positionInterface));
                            recvewdonation.getAdapter().notifyDataSetChanged();
                            mTxtNotFound.setVisibility(View.GONE);
                            recvewdonation.setVisibility(View.VISIBLE);
                        } else {
                            mTxtNotFound.setVisibility(View.VISIBLE);
                            recvewdonation.setVisibility(View.GONE);
                        }


                        //     Log.d("dsdsadsad", String.valueOf(allProductPojos.size()));
                    } else {
                        mTxtNotFound.setVisibility(View.VISIBLE);
                        recvewdonation.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                CustomUtil.DismissDialog(ManageofferActivity.this);

                Log.d("dsdsdads", volleyError.toString());

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

                    Toast.makeText(ManageofferActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManageofferActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

                }
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(ManageofferActivity.this));
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


    PositionInterface positionInterface = new PositionInterface() {



        @Override
        public void onClick(int pos) {

        }

        @Override
        public void onClickWithFlag(int pos, int flag) {
            if (flag == 1) {
                Intent mIntent = new Intent(ManageofferActivity.this, AntiAddofferActivity.class);
                mIntent.putExtra("Flag", "yes");
                mIntent.putExtra("Data", allProductPojos.get(pos));
                startActivity(mIntent);

            } else {
                requestQueue = Volley.newRequestQueue(ManageofferActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("offerdelete"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getOfferProduct();

                        //   Log.d("ffgfdg",response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //   Log.d("ffgfdg",error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("offer_id", allProductPojos.get(pos).getOfferid());
                        param.put("user_id", PrefManager.getUserId(ManageofferActivity.this));

                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        }
    };
}
