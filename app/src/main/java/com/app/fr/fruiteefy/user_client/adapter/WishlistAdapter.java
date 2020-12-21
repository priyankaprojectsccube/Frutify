package com.app.fr.fruiteefy.user_client.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.fr.fruiteefy.user_client.home.MovetocartActivity;
import com.app.fr.fruiteefy.user_client.home.WishlistActivity;
import com.app.fr.fruiteefy.user_client.products.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    ArrayList<AllProductPojo> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;
    StringRequest stringRequest;


    public WishlistAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public WishlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_wishlist, parent, false);

        return new WishlistAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        requestQueue= Volley.newRequestQueue(mctx);

        Log.d("dsdsdsdsds",items.getProductImg());

        if(!items.getProductImg().equals("null")) {

            Picasso.with(mctx).load(items.getProductImg()).error(R.drawable.icon).into(holder.img);
        }
        holder.offertype.setText(items.getOffer_type());
        holder.product.setText(items.getProductTitle());
        if (items.getPrice().equals("null")){
            Log.d("pricenull",items.getPrice());
            holder.price.setText("N/A");
            holder.movetocart.setBackgroundColor(ContextCompat.getColor(mctx,R.color.faintgreen));
            holder.movetocart.setClickable(false);
            holder.movetocart.setFocusable(false);
            holder.movetocart.setEnabled(false);
            holder.movetocart.setFocusableInTouchMode(false);

        }else {
            Log.d("pricenotnull",items.getPrice());
            holder.price.setText(items.getPrice().concat("("+items.getWeight().concat(" "+items.getUnit()+")")));
            holder.movetocart.setClickable(true);
            holder.movetocart.setFocusable(true);
            holder.movetocart.setEnabled(true);
            holder.movetocart.setFocusableInTouchMode(true);
        }



        holder.movetocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Intent intent1=new Intent(mctx, MovetocartActivity.class);
                intent1.putExtra("produ_id",items.getProductId());
                intent1.putExtra("wishlistid",items.getGardenid());
                mctx.startActivity(intent1);
                ((Activity)mctx).finish();


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("dgfgfgggfgfsdsdsd", items.getGardenid());
                CustomUtil.ShowDialog(mctx,false);
                stringRequest =new StringRequest(Request.Method.POST,BaseUrl.BASEURL.concat("delete_wishlist_item"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CustomUtil.DismissDialog(mctx);
                        Log.d("fgfgfgffgf", response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equals("1")){
                                mctx.startActivity(new Intent(mctx,WishlistActivity.class));
                                ((Activity)mctx).finish();


                            }
                            Toast.makeText(mctx, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Log.d("fgfgfgffgf", volleyError.toString());
                        CustomUtil.DismissDialog(mctx);
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {

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
                        Map<String,String> paramnew=new HashMap<>();

                        paramnew.put("wishlist_id", items.getGardenid());
                        paramnew.put("user_id", PrefManager.getUserId(mctx));
                        return paramnew;
                    }


                };

                requestQueue.add(stringRequest);


            }
        });

holder.layoutmain.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mctx,ProductDetailsActivity.class);
        intent.putExtra("productid",items.getProductId());
        mctx.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView product,price,delete,movetocart,offertype;
        LinearLayout layoutmain;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            product = (TextView) view.findViewById(R.id.product);
            price = (TextView) view.findViewById(R.id.price);
            delete = (TextView) view.findViewById(R.id.delete);
            movetocart = (TextView) view.findViewById(R.id.movetocart);
            layoutmain= (LinearLayout) view.findViewById(R.id.layoutmain);
            offertype = (TextView) view.findViewById(R.id.offertype);
        }
    }
}


