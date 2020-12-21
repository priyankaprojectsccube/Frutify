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
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.home.GardenProductActivity;
import com.app.fr.fruiteefy.user_client.home.SeetheGardenActivity;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class homeGardenAdapter extends RecyclerView.Adapter<homeGardenAdapter.MyViewHolder> {
    ArrayList<AllProductPojo> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;


    public homeGardenAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public homeGardenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gardenhome, parent, false);

        return new homeGardenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeGardenAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        requestQueue= Volley.newRequestQueue(mctx);

        if(items.getUserid().equals(PrefManager.getUserId(mctx))){
            holder.sendmessage.setVisibility(View.GONE);
        }
        holder.type.setText(items.getReviewstatus());
        holder.title.setText(items.getFirstname().concat(" "+items.getLastname()));
        holder.desc.setText(items.getAbout());
        holder.km.setText(items.getKilometer()+" km");
        holder.ratingBar.setRating(Float.parseFloat(items.getRating()));
        holder.productavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
Log.d("userid",items.getUserid());
                Intent intent=new Intent(mctx, GardenProductActivity.class);
                intent.putExtra("userid",items.getUserid());
                intent.putExtra("lat",items.getLat());
                intent.putExtra("lng",items.getLng());
                mctx.startActivity(intent);

            }
        });

        holder.sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(PrefManager.IsLogin(mctx)) {

                    Intent intent=new Intent(mctx, MessageActivity.class);
                    intent.putExtra("token",items.getToken());
                    intent.putExtra("receiverid",items.getUserid());
                    intent.putExtra("name",items.getFirstname()+" "+items.getLastname());
                    mctx.startActivity(intent);
                }
                else{

                    Intent intent=new Intent(mctx, LoginActivity.class);
                    intent.putExtra("data","login");
                    mctx.startActivity(intent);

                }




            }
        });

        holder.seethegarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mctx, SeetheGardenActivity.class);
                intent.putExtra("userid",items.getUserid());
                intent.putExtra("img",items.getGardenimg());
                intent.putExtra("aboutme",items.getAbout());
                intent.putExtra("rating",items.getRating());
                intent.putExtra("name",items.getFirstname()+" "+items.getLastname());
                mctx.startActivity(intent);


            }
        });

        Glide.with(mctx)
                .load(items.getGardenimg())
                .thumbnail(0.1f)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView  title,desc,productavailable,seethegarden,km,sendmessage,type;
        RatingBar ratingBar;


        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            km = (TextView) view.findViewById(R.id.km);
            type=view.findViewById(R.id.type);
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
            sendmessage= (TextView) view.findViewById(R.id.sendmessage);
            productavailable = (TextView) view.findViewById(R.id.productavailable);
            seethegarden = (TextView) view.findViewById(R.id.seethegarden);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }



    public void setFilter(List<AllProductPojo> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }
}

