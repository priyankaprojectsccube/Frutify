package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.BaseUrl;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.home.CommentOrderDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    ArrayList<AllProductPojo> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;
    StringRequest stringRequest;


    public OrderDetailAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_orderdetail, parent, false);

        return new OrderDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        requestQueue= Volley.newRequestQueue(mctx);

        Log.d("dsdsdsdsds",items.getProductImg());



        if(!items.getProductImg().equals("null")) {

            Picasso.with(mctx).load(BaseUrl.MYOFFERIMAGEURL.concat(items.getProductImg())).error(R.drawable.icon).into(holder.img);
        }


        holder.product.setText(" "+items.getProductTitle());
        holder.price.setText(" "+items.getPrice().concat(mctx.getResources().getString(R.string.currency)));
        holder.quentity.setText(" "+items.getUnit());
        Double total= Double.valueOf(items.getPrice())+Double.valueOf(items.getDeliverycharge());
        holder.total.setText(" "+total+""+(mctx.getResources().getString(R.string.currency)));
        holder.shippingcost.setText(" "+items.getDeliverycharge().concat(mctx.getResources().getString(R.string.currency)));

        if(items.getStatus().equalsIgnoreCase("New")){
            holder.status.setText(mctx.getResources().getString(R.string.waitingforvalidation));
        }
        else  if(items.getStatus().equalsIgnoreCase("Canceled")){
            holder.status.setText(mctx.getResources().getString(R.string.canceled));
        }
        else  if(items.getStatus().equalsIgnoreCase("Delivered")){
            holder.status.setText(mctx.getResources().getString(R.string.delivered));
        }
        else if (items.getStatus().equalsIgnoreCase("Collected")) {
            holder.status.setText("Livré/Récupéré");
        }
        else{
            holder.status.setText(items.getStatus());
        }


        if(items.getReviewstatus().equals("1")){
            holder.adcomment.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(Float.parseFloat(items.getRating()));

        }

        holder.adcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mctx, CommentOrderDetailActivity.class);
                intent.putExtra("productid",items.getGardenid());
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
        TextView product,price,delete,movetocart,shippingcost,quentity,total,status,adcomment;
        RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            product = (TextView) view.findViewById(R.id.product);
            shippingcost = (TextView) view.findViewById(R.id.shippingcost);
            quentity = (TextView) view.findViewById(R.id.quentity);
            price = (TextView) view.findViewById(R.id.price);
            delete = (TextView) view.findViewById(R.id.delete);
            movetocart = (TextView) view.findViewById(R.id.movetocart);
            total= (TextView) view.findViewById(R.id.total);
            status= (TextView) view.findViewById(R.id.status);
            adcomment = (TextView) view.findViewById(R.id.adcomment);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }
}


