package com.app.fr.fruiteefy.user_client.home;

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
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipyAdapter  extends RecyclerView.Adapter<RecipyAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;


    public RecipyAdapter(ArrayList<Product> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public RecipyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_receipy, parent, false);

        return new RecipyAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipyAdapter.MyViewHolder holder, int position) {
        final Product items=allProductPojos.get(position);


        holder.title.setText(items.getProductname());
        holder.ratingBar.setRating(Float.parseFloat(items.getRating()));
        Picasso.with(mctx).load(items.getOfferimg()).into(holder.iv_product_image);
        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mctx,ReceipyDetailActivity.class);
                intent.putExtra("receipyid",items.getProductid());
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
        ImageView iv_product_image;
        TextView title;
        CardView cv_main;
        RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            cv_main = (CardView) view.findViewById(R.id.cv_main);
            title = (TextView) view.findViewById(R.id.texttitle);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        }
    }


    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}
