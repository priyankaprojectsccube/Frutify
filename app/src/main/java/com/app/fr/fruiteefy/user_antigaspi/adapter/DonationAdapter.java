package com.app.fr.fruiteefy.user_antigaspi.adapter;

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
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.Util.Product;
import com.app.fr.fruiteefy.user_antigaspi.DonationActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DonationAdapter   extends RecyclerView.Adapter<DonationAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;


    public DonationAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public DonationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_donation, parent, false);

        return new DonationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);

        Picasso.with(mctx).load(items.getProductimg()).into(holder.img);

        holder.offerno.setText(" "+items.getOfferid());
        holder.offertype.setText(" "+items.getOffertype());
        holder.status.setText(" "+items.getStatus());
        holder.bookby.setText(" "+items.getBookby());
        holder.action.setText(items.getAction());
        holder.title.setText(" "+items.getProductname());

        if(items.getActionclass().equals("disabled")){

            holder.action.setEnabled(false);
            holder.action.setFocusable(false);
            holder.action.setCursorVisible(false);
            holder.action.setAlpha(0.5f);

        }
        else {

            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RequestQueue requestQueue= Volley.newRequestQueue(mctx);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrl.BASEURL.concat("cancel_reserved_offer"), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                            JSONObject jsonObject=new JSONObject();

                            if(jsonObject.getString("status").equals("1")){
                                Intent intent=new Intent(mctx,DonationActivity.class);
                                mctx.startActivity(intent);
                            }


                                Toast.makeText(mctx, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(mctx, message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(mctx, "An error occured", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<>();
                            param.put("offer_id",items.getOfferid());
                            param.put("user_id", PrefManager.getUserId(mctx));

                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);


                }
            });
        }

    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView  texttitle,offerno,offertype,status,bookby,title;
        TextView action;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            texttitle = (TextView) view.findViewById(R.id.texttitle);
            offerno = (TextView) view.findViewById(R.id.offerno);
            offertype = (TextView) view.findViewById(R.id.offertype);
            status = (TextView) view.findViewById(R.id.status);
            bookby = (TextView) view.findViewById(R.id.bookby);
            title = (TextView) view.findViewById(R.id.title);
            action = (TextView) view.findViewById(R.id.action);
        }
    }

    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}

