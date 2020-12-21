package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.products.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.MyViewHolder> {
ArrayList<AllProductPojo> allProductPojos;
Context mctx;


    public AllProductAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_products, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        Log.d("sdsd",items.getDistance());
        holder.tv_distance.setText(items.getDistance()+" "+mctx.getResources().getString(R.string.km));
        holder.title.setText(items.getProductTitle());
        if(items.getPrice().equals("null") ){
            holder.price.setText(items.getOffer_type());
        }else{
            holder.price.setText(items.getPrice().concat(" "+mctx.getResources().getString(R.string.currency)+" ("+items.getWeight()+" "+items.getUnit()+")"));
        }
if(items.getProductStock().equals("null")){
    holder.remaining.setText("");
}else{
    holder.remaining.setText(items.getProductStock().concat(" "+mctx.getResources().getString(R.string.articleavailable)));
}


        if(items.getRating().equals("null")){
            holder.ratingBar.setRating(0);
        }
        else {
            holder.ratingBar.setRating(Float.parseFloat(items.getRating()));
        }
        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mctx,ProductDetailsActivity.class);
                intent.putExtra("productid",items.getProductId());
                mctx.startActivity(intent);

            }
        });
        Picasso.with(mctx).load(items.getProductImg()).into(holder.imgveg);



    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_image;
        ImageView imgveg;
        TextView tv_distance,title,price,remaining;
        RatingBar ratingBar;
        CardView cv_main;
        public MyViewHolder(View view) {
            super(view);
            iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            cv_main = (CardView) view.findViewById(R.id.cv_main);
            title = (TextView) view.findViewById(R.id.title);
            ratingBar=(RatingBar) view.findViewById(R.id.ratingBar);
            price = (TextView) view.findViewById(R.id.price);
            imgveg = (ImageView) view.findViewById(R.id.imgveg);
            remaining = (TextView) view.findViewById(R.id.remaining);
        }
    }


    public void setFilter(List<AllProductPojo> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }


}
