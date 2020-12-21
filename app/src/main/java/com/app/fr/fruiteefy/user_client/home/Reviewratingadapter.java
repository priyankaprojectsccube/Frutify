package com.app.fr.fruiteefy.user_client.home;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.user_client.products.ProductPojo;


import java.util.ArrayList;

public class Reviewratingadapter extends RecyclerView.Adapter<Reviewratingadapter.MyViewHolder> {

    private ArrayList<ProductPojo> arrayList=new ArrayList<>();
    private Context context;
    private LinearLayout linearLayout;

    public Reviewratingadapter(ArrayList<ProductPojo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    ;




    @NonNull
    @Override
    public Reviewratingadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_reviewrating, viewGroup, false);

        return new Reviewratingadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Reviewratingadapter.MyViewHolder myViewHolder, int i) {

        ProductPojo productPojo=arrayList.get(i);

        myViewHolder.username.setText(productPojo.getNickname());
        myViewHolder.title.setText("Title: "+productPojo.getTitle());
        myViewHolder.comment.setText("Comment: "+productPojo.getReview());
        myViewHolder.ratingBar.setRating(Float.parseFloat(productPojo.getRating()));


        productPojo.getIngname();


    }

    @Override
    public int getItemCount() {
        Log.d("hjhjgj", String.valueOf(arrayList.size()));
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView username,title,comment;
        RatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username);
            title=itemView.findViewById(R.id.title);
            comment=itemView.findViewById(R.id.comment);
            ratingBar=itemView.findViewById(R.id.ratingBar);


        }
    }
}

