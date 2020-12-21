package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_picorear.AddOfferActivity;
import com.app.fr.fruiteefy.user_picorear.UserPicorearHomeActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GardenPicoAdapter extends RecyclerView.Adapter<GardenPicoAdapter.MyViewHolder> {
    ArrayList<AllProductPojo> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;


    public GardenPicoAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public GardenPicoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_garden, parent, false);

        return new GardenPicoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GardenPicoAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        requestQueue= Volley.newRequestQueue(mctx);
        holder.cat.setText(items.getCategory());
        holder.deletegarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomUtil.ShowDialog(mctx,false);
                StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("delete_garden"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                CustomUtil.DismissDialog(mctx);
                                Log.d("dfdfsffdf",response);
                                try {
                                    JSONObject  jsonObject=new JSONObject(response);
                                    Toast.makeText(mctx, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    if (jsonObject.getString("status").equals("1")){
                                        Intent intent = new Intent(mctx, UserPicorearHomeActivity.class);
                                        intent.putExtra("type","gardendelete");
                                        mctx.startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("dfdfsffdf",volleyError.toString());

                        Log.d("dfddf", volleyError.toString());
                        CustomUtil.DismissDialog(mctx);
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
                            Toast.makeText(mctx, message, Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", PrefManager.getUserId(mctx));
                        param.put("garden_id", items.getGardenid());

                        return param;
                    }
                };
                requestQueue.add(stringRequest);


            }
        });

        holder.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(mctx, AddOfferActivity.class);
                mIntent.putExtra("From", "Add");
                mctx.startActivity(mIntent);

            }
        });

        holder.subcat.setText(items.getSubcategory());
//        holder.price.setText(items.getPrice().concat(" € ("+items.getWeight()+" "+items.getUnit()+")"));
//        holder.remaining.setText(items.getProductStock().concat(" Remaining"));
//        holder.cv_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mctx, ProductDetailsActivity.class);
//                intent.putExtra("productid",items.getProductId());
//                mctx.startActivity(intent);
//
//            }
//        });


        if(!items.getGardenimg().equals("null")) {

            Picasso.with(mctx).load(BaseUrl.SUBCATIMG.concat(items.getGardenimg())).error(R.drawable.icon).into(holder.img);
        }


    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView  cat,subcat,deletegarden,sell;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            cat = (TextView) view.findViewById(R.id.cat);
            subcat = (TextView) view.findViewById(R.id.subcat);
            sell=view.findViewById(R.id.sell);
            deletegarden = (TextView) view.findViewById(R.id.deletegarden);
        }
    }

    public void setFilter(List<AllProductPojo> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}

