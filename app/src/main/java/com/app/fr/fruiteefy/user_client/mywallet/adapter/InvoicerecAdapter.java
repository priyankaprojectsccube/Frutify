package com.app.fr.fruiteefy.user_client.mywallet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class InvoicerecAdapter extends RecyclerView.Adapter<InvoicerecAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;


    public InvoicerecAdapter(ArrayList<Product> allProductPojos, Context mctx) {

        this.allProductPojos = allProductPojos;
        this.mctx = mctx;

    }

    @NonNull
    @Override
    public InvoicerecAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_invoicerec, parent, false);

        return new InvoicerecAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InvoicerecAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);


        holder.product.setText(" "+items.getProductname());
        holder.quantity.setText(" "+items.getDesc());
        holder.price.setText(" "+items.getPrice()+" "+mctx.getResources().getString(R.string.currency)+" ("+mctx.getResources().getString(R.string.delivercost)+items.getAction()+")");
        holder.subtotal.setText(" "+items.getAvailable()+" "+mctx.getResources().getString(R.string.currency));
        holder.collectiondate.setText(" "+items.getDate());

        Picasso.with(mctx).load(items.getProductimg()).into(holder.img);



    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  product,quantity,price,subtotal,collectiondate;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);

            img=(ImageView) view.findViewById(R.id.img);
            product = (TextView) view.findViewById(R.id.product);
            quantity= (TextView) view.findViewById(R.id.quantity);
            price= (TextView) view.findViewById(R.id.price);
            subtotal= (TextView) view.findViewById(R.id.subtotal);
            collectiondate= (TextView) view.findViewById(R.id.collectiondate);




        }
    }



}
