package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MySaleAdapter extends RecyclerView.Adapter<MySaleAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;


    public MySaleAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public MySaleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mysale, parent, false);

        return new MySaleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MySaleAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);

        Picasso.with(mctx).load(items.getProductimg()).into(holder.img);

        holder.quantity
                .setText(" "+items.getOfferid());
        holder.offertype.setText(" "+items.getProductname());
        holder.quantavailable.setText(" "+items.getOfferPlace());


    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView  texttitle,offertype,quantavailable,quantity;


        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            texttitle = (TextView) view.findViewById(R.id.texttitle);
            offertype = (TextView) view.findViewById(R.id.offertype);
            quantavailable = (TextView) view.findViewById(R.id.quantavailable);

            quantity = (TextView) view.findViewById(R.id.quantity);



        }
    }

    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}

