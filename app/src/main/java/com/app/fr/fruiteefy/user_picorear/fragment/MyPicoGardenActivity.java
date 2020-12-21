package com.app.fr.fruiteefy.user_picorear.fragment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_antigaspi.AddtogardenActivity;

import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_picorear.Adapter.GardenPicoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPicoGardenActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recviewproduct;
    FloatingActionButton addtogarden;
    private ArrayList<AllProductPojo> antiprodArr=new ArrayList<>();
   // View view,view1;
    LinearLayout linlay;
    //SearchView searchview;
    GardenPicoAdapter adapter;
    private String searchString="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_garden);


        inIt();
        onClick();

        setTitle(getResources().getString(R.string.mygarden));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addtogarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPicoGardenActivity.this, AddtogardenActivity.class);
                startActivity(intent);

            }
        });

//        view=getActivity().findViewById(R.id.linlay);
//
//        view1=getActivity().findViewById(R.id.searchview);

//        if( view instanceof LinearLayout) {
//            linlay = (LinearLayout) view;
//        }
//
//        if( view1 instanceof SearchView) {
//            searchview = (SearchView) view1;
//        }


//        searchview.setOnQueryTextListener(this);
//
//        linlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linlay.setVisibility(View.GONE);
//                searchview.setVisibility(View.VISIBLE);
//                searchview.setIconifiedByDefault(true);
//                searchview.setFocusable(true);
//                searchview.setIconified(false);
//                searchview.requestFocusFromTouch();
//
//            }
//        });
//
//        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                linlay.setVisibility(View.VISIBLE);
//                searchview.setVisibility(View.GONE);
//                return false;
//            }
//        });


    }

    private void inIt(){
        recviewproduct=findViewById(R.id.rv_antigardn);
        addtogarden=findViewById(R.id.addtogarden);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<AllProductPojo> filteredModelList = filter(antiprodArr, newText);
        if (filteredModelList.size() > 0) {
            adapter.setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }


    private void onClick() {

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyPicoGardenActivity.this);
        CustomUtil.ShowDialog(MyPicoGardenActivity.this, true);





        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_anti_garden_data"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dfddfrfgfdgfgdgfdg", response);

                CustomUtil.DismissDialog(MyPicoGardenActivity.this);
                antiprodArr.clear();

                Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("gardenlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo = new AllProductPojo();
                        allProductPojo.setGardenid(jsonObject1.getString("garden_id"));
                        allProductPojo.setGardenimg(jsonObject1.getString("subcat_image"));
                        allProductPojo.setCategory(jsonObject1.getString("catname"));
                        allProductPojo.setSubcategory(jsonObject1.getString("subcatname"));
//                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
//                        allProductPojo.setPrice(jsonObject1.getString("price"));
//                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
//                        allProductPojo.setWeight(jsonObject1.getString("weight"));
//                        allProductPojo.setUnit(jsonObject1.getString("unit"));
//                        allProductPojo.setDistance("10");
                        antiprodArr.add(allProductPojo);


                    }
                    Log.d("dsdd", String.valueOf(antiprodArr.size()));

                     adapter = new GardenPicoAdapter(antiprodArr,MyPicoGardenActivity.this);
                    recviewproduct.setLayoutManager(new LinearLayoutManager(MyPicoGardenActivity.this));
                    recviewproduct.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(MyPicoGardenActivity.this);
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
                    Toast.makeText(MyPicoGardenActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(MyPicoGardenActivity.this));
                return param;
            }


        };

        requestQueue1.add(stringRequest1);






    }



    private ArrayList<AllProductPojo> filter(ArrayList<AllProductPojo> models, String query) {


        Log.d("sdsda",query);
        this.searchString=query;

        final ArrayList<AllProductPojo> filteredModelList = new ArrayList<>();
        for (AllProductPojo model : models) {

            String text = model.getCategory().toLowerCase();
            String text1 = model.getSubcategory().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) || text1.contains(query) ) {
                filteredModelList.add(model);
            }
        }

        adapter=new GardenPicoAdapter(filteredModelList,MyPicoGardenActivity.this);

        recviewproduct.setLayoutManager(new LinearLayoutManager(MyPicoGardenActivity.this));
        recviewproduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
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