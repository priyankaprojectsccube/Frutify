package com.app.fr.fruiteefy.user_client.cart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.app.fr.fruiteefy.CustomUtil;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PositionInterfaceforCart;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.cart.modelClass.MyCartModel;
import com.app.fr.fruiteefy.user_client.home.UserCliantHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements View.OnClickListener {

    RecyclerView rv_my_cart;
    RequestQueue requestQueue;
    private Activity activity;
    StringRequest stringRequest;
    RelativeLayout rellay1,rellay2;
    String availablequantity;
    View  view1;
    EditText productsearch;
    private ImageView searchicon;
    TextView tv_product_name, continueshopping, cleardata,tv_noti_itm;
    ArrayList<MyCartModel> myCartModelList = new ArrayList<>();
    View v;
    TextView mLytReserve,filtericon;
    RelativeLayout tool2;
    private TextView removecity,applyfilter,setkm;
//
//    public MyCartFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_cart, container, false);
        activity=(UserCliantHomeActivity)getActivity();
        initView(v);


        view1 = activity.findViewById(R.id.gardensearch);
        rellay1=activity.findViewById(R.id.rellay1);
        rellay2=activity.findViewById(R.id.rellay2);
        tool2 = activity.findViewById(R.id.tool2);
        mLytReserve = v.findViewById(R.id.BtnReserve);

        mLytReserve.setOnClickListener(this);


        if (view1 instanceof EditText) {
            productsearch = (EditText) view1;
        }

        filtericon=activity.findViewById(R.id.filtericon);
        searchicon=activity.findViewById(R.id.searchicon);
        removecity=activity.findViewById(R.id.removecity);

        removecity.setVisibility(View.GONE);
        searchicon.setVisibility(View.GONE);
        filtericon.setVisibility(View.GONE);
        tool2.setVisibility(View.GONE);


        productsearch.setVisibility(View.GONE);
        rellay1.setVisibility(View.GONE);
        rellay2.setVisibility(View.GONE);
        cleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue = Volley.newRequestQueue(activity);
                stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("clear_cart"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {
                                startActivity(new Intent(activity, UserCliantHomeActivity.class));
                            }
                            Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();

                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(activity));
                        return param;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });

        continueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UserCliantHomeActivity.class);
                startActivity(intent);
            }
        });
        wsMyCartData();


        return v;
    }

    private void setOnclickListener() {
        mLytReserve.setOnClickListener(this);
    }

    private void wsMyCartData() {

        CustomUtil.ShowDialog(activity,true);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("mycart"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdsadd", response);
                CustomUtil.DismissDialog(activity);
                try {
                    myCartModelList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("mycartdata");
                    //    tv_noti_itm.setText(" "+jsonArray.length()+" ");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            MyCartModel myCartModel = new MyCartModel();

                            myCartModel.setId(jsonObject1.getString("id"));
                            //  Log.d("sdcsdsddsddsad", jsonObject1.getString("product_name"));
                            myCartModel.setName(jsonObject1.getString("product_name"));
                            myCartModel.setStock(jsonObject1.getString("stock_all"));
                            myCartModel.setPrice(jsonObject1.getString("price"));
                            myCartModel.setQty(jsonObject1.getString("quentity"));
                            myCartModel.setSubtotal(jsonObject1.getString("sub_total"));
                            myCartModel.setImg(jsonObject1.getString("product_image"));
                            myCartModelList.add(myCartModel);

                        }
                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                        rv_my_cart.setLayoutManager(verticalLayoutManager);
                        MyCartAdapter adapter = new MyCartAdapter(myCartModelList, activity, positionInterfaceforCart);
                        rv_my_cart.setAdapter(adapter);
                        rv_my_cart.setHasFixedSize(true);
                        rv_my_cart.setItemViewCacheSize(20);
                        rv_my_cart.setDrawingCacheEnabled(true);
                        rv_my_cart.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                        if(myCartModelList.size()==0){
                            mLytReserve.setEnabled(false);
                        }
                    }

                    if (jsonObject.getString("status").equals("0")) {
                    //    tv_noti_itm.setText(" 0 ");
                        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                        rv_my_cart.setLayoutManager(verticalLayoutManager);
                        MyCartAdapter adapter = new MyCartAdapter(myCartModelList, activity, positionInterfaceforCart);
                        rv_my_cart.setAdapter(adapter);
                        rv_my_cart.setHasFixedSize(true);
                        rv_my_cart.setItemViewCacheSize(20);
                        rv_my_cart.setDrawingCacheEnabled(true);
                        rv_my_cart.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                        if(myCartModelList.size()==0){
                            mLytReserve.setEnabled(false);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomUtil.DismissDialog(activity);
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(activity));
                Log.d("dfdfdf", PrefManager.getUserId(activity));
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    PositionInterfaceforCart positionInterfaceforCart = new PositionInterfaceforCart() {
        @Override
        public void onClick(int flag, int value, int pos) {
            if (flag == 2) {
                wsDeleteCart(pos);
            } else {
                wsCallUpdateCartAPi(myCartModelList.get(pos).getId(), myCartModelList.get(pos).getQty(), myCartModelList.get(pos).getPrice());
            }

        }
    };

    private void wsDeleteCart(int pos) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_cart_item"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdsadsa", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (jsonObject.getString("status").equals("1")) {
                        Intent  intent=new Intent(activity,UserCliantHomeActivity.class);
                        intent.putExtra("homeact","cart");
                        startActivity(intent);

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
                param.put("user_id", PrefManager.getUserId(activity));
                param.put("id", myCartModelList.get(pos).getId());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void wsCallUpdateCartAPi(String id, String value, String price) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("update_product_qty"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sdsadd", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    wsMyCartData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sdsadd", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(activity));
                param.put("quentity", value);
                param.put("cartid", id);
                param.put("price", price);

                return param;
            }
        };

        requestQueue.add(stringRequest);
    }



    private ArrayList<MyCartModel> filter(ArrayList<MyCartModel> models, String query) {


        Log.d("sdsda", query);

        final ArrayList<MyCartModel> filteredModelList = new ArrayList<>();
        for (MyCartModel model : models) {

            String text = model.getName().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }

        MyCartAdapter adapter = new MyCartAdapter(filteredModelList, activity, positionInterfaceforCart);

        rv_my_cart.setLayoutManager(new LinearLayoutManager(activity));
        rv_my_cart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        rv_my_cart.setHasFixedSize(true);
        rv_my_cart.setItemViewCacheSize(20);
        rv_my_cart.setDrawingCacheEnabled(true);
        rv_my_cart.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return filteredModelList;
    }


    public void initView(View v) {


        rv_my_cart = v.findViewById(R.id.rv_my_cart);
        rv_my_cart = v.findViewById(R.id.rv_my_cart);
        continueshopping = v.findViewById(R.id.continueshopping);
        cleardata = v.findViewById(R.id.cleardata);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_my_cart.setLayoutManager(verticalLayoutManager);
    }//initViewClose

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtnReserve:

                try {
//                    MyCartActivity.MyClickEvent myClickEvent = new MyCartActivity.MyClickEvent();
//                    myClickEvent.onClickMethod();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.rl_main, new FragmentOrderSummary()).commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}
