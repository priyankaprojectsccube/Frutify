package com.app.fr.fruiteefy.user_antigaspi.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.fr.fruiteefy.user_antigaspi.adapter.AntiProductAdapter;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AntiProductFragment extends Fragment implements SearchView.OnQueryTextListener{

    RecyclerView recviewproduct;
    private ArrayList<AllProductPojo> antiprodArr=new ArrayList<>();
    View view,view1;
    LinearLayout linlay;
    SearchView searchview;
    private String searchString="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_pico_product, container, false);


        inIt(v);
        onClick();


        view=getActivity().findViewById(R.id.linlay);

        view1=getActivity().findViewById(R.id.searchview);


        if( view instanceof LinearLayout) {
            linlay = (LinearLayout) view;
        }

        if( view1 instanceof SearchView) {
            searchview = (SearchView) view1;
        }


        searchview.setOnQueryTextListener(this);


        linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linlay.setVisibility(View.GONE);
                searchview.setVisibility(View.VISIBLE);
                searchview.setIconifiedByDefault(true);
                searchview.setFocusable(true);
                searchview.setIconified(false);
                searchview.requestFocusFromTouch();

            }
        });

        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                linlay.setVisibility(View.VISIBLE);
                searchview.setVisibility(View.GONE);
                return false;
            }
        });

        return v;
    }

    private void inIt(View view){
        recviewproduct=view.findViewById(R.id.recviewproduct);
    }

    private void onClick(){

        RequestQueue requestQueue1= Volley.newRequestQueue(getActivity());
        CustomUtil.ShowDialog(getActivity(),true);

        StringRequest stringRequest1=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("get_antiproduct_list"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dfddfrfgfdgfgdgfdg",response);

                CustomUtil.DismissDialog(getActivity());
                antiprodArr.clear();

                Log.d("adasds",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("productlist");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        AllProductPojo allProductPojo=new AllProductPojo();
                        allProductPojo.setProductId(jsonObject1.getString("product_id"));
                        allProductPojo.setProductImg(jsonObject1.getString("product_image"));
                        allProductPojo.setProductTitle(jsonObject1.getString("product_name"));
                        allProductPojo.setPrice(jsonObject1.getString("price"));
                        allProductPojo.setDesc(jsonObject1.getString("description"));
                        allProductPojo.setProductStock(jsonObject1.getString("stock"));
                        allProductPojo.setWeight(jsonObject1.getString("weight"));
                        allProductPojo.setUnit(jsonObject1.getString("unit"));
                        allProductPojo.setRating(jsonObject1.getString("rating"));
                        allProductPojo.setDistance("");
                        antiprodArr.add(allProductPojo);



                    }
                    Log.d("dsdd", String.valueOf(antiprodArr.size()));

                    AntiProductAdapter adapter=new AntiProductAdapter(antiprodArr,getActivity());
                    recviewproduct.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recviewproduct.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("dfddf",volleyError.toString());
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();

                    param.put("user_id", PrefManager.getUserId(getActivity()));

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

            String text = model.getProductTitle().toLowerCase();
//
//
//            String text1=model.getLastname().toLowerCase();
//
//            String text2=model.getFirstname().toLowerCase()+" "+model.getLastname().toLowerCase();

            if (text.contains(query) ) {
                filteredModelList.add(model);
            }
        }
        AntiProductAdapter adapter=new AntiProductAdapter(filteredModelList,getActivity());


        recviewproduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        recviewproduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return filteredModelList;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Log.d("jhhjgh",newText);
        final List<AllProductPojo> filteredModelList = filter(antiprodArr, newText);
        if (filteredModelList.size() > 0) {
            new AntiProductAdapter(antiprodArr,getActivity()).setFilter(filteredModelList);
            return true;
        } else {
            // If not matching search filter data

            return false;
        }
    }

}
