package com.app.fr.fruiteefy.user_client.home;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.Util.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceipyFragment extends Fragment {

    ArrayList<Product> allreceipy = new ArrayList<Product>();
    RecyclerView recyclerviewrecipy;
    BottomNavigationView bottomNavigationView;
    View v
            //,viewsearch, viewtext
            ;
    EditText textviewtitle;
    LinearLayout linlay;
    //SearchView searchview;

    ImageView  searchicon,backimg;
    private String searchString = "";
    RecipyAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_receipy, container, false);






        inIt(v);
        onClick();


        searchicon=v.findViewById(R.id.searchicon);
        backimg=v.findViewById(R.id.backimg);
        textviewtitle=v.findViewById(R.id.textviewtitle);





        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();

            }
        });


        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<Product> filteredModelList = filter(allreceipy, textviewtitle.getText().toString());
                if (filteredModelList.size() > 0) {
                    adapter.setFilter(filteredModelList);

                }
            }
        });


//        if( viewtext instanceof LinearLayout ) {
//            linlay = (LinearLayout) viewtext;
//        }
//
//        if( viewsearch instanceof SearchView ) {
//            //searchview = (SearchView) viewsearch;
//        }

        if(linlay!=null) {


            linlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    linlay.setVisibility(View.GONE);
//                    searchview.setVisibility(View.VISIBLE);
//                    searchview.setIconifiedByDefault(true);
//                    searchview.setFocusable(true);
//                    searchview.setIconified(false);
//                    searchview.requestFocusFromTouch();

                }
            });
        }


//        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//
//                linlay.setVisibility(View.VISIBLE);
//                searchview.setVisibility(View.GONE);
//                return false;
//            }
//        });


      //  searchview.setOnQueryTextListener(this);


        if (bottomNavigationView != null) {
            recyclerviewrecipy.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 && bottomNavigationView.isShown()) {
                        bottomNavigationView.setVisibility(View.GONE);
                    } else if (dy < 0) {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

        return v;
    }

    private void inIt(View v) {

        recyclerviewrecipy = v.findViewById(R.id.rv_all_products);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }



    private void onClick() {


        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        CustomUtil.ShowDialog(getActivity(), true);

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("recipes"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dfddfrfgfdgfgdgfdg", response);

                CustomUtil.DismissDialog(getActivity());
                allreceipy.clear();

                Log.d("adasds", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("productlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Product product = new Product();
                        product.setProductid(jsonObject1.getString("product_id"));
                        product.setProductname(jsonObject1.getString("product_name"));
                        product.setOfferimg(jsonObject1.getString("recipe_image_url"));
                        product.setRating(jsonObject1.getString("rating"));


                        allreceipy.add(product);


                    }
                    Log.d("dsdd", String.valueOf(allreceipy.size()));

                    adapter = new RecipyAdapter(allreceipy, getActivity());
                    recyclerviewrecipy.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerviewrecipy.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf", volleyError.toString());
                CustomUtil.DismissDialog(getActivity());
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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                } else {

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("user_id", PrefManager.getUserId(getActivity()));
                param.put("searchbtn","");
                return param;
            }


        };

        requestQueue1.add(stringRequest1);
    }


    private ArrayList<Product> filter(ArrayList<Product> models, String query) {


        Log.d("sdsda", query);
        this.searchString = query;

        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            String text = model.getProductname().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }

        adapter = new RecipyAdapter(filteredModelList, getActivity());

        recyclerviewrecipy.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerviewrecipy.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }




}
