package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CategoryDetailAdapter extends RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;


    public CategoryDetailAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public CategoryDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_categorydetail, parent, false);

        return new CategoryDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);

        holder.title.setText(items.getProductname());
        Picasso.with(mctx).load(items.getProductimg()).into(holder.iv_product_image);

    }

    @Override
    public int getItemCount() {

        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product_image;
        TextView title;

        public MyViewHolder(View view) {
            super(view);

            iv_product_image = (ImageView) view.findViewById(R.id.imgvew);
            title = (TextView) view.findViewById(R.id.txtvew);

        }
    }




}
