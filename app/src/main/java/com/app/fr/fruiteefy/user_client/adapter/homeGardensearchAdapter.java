package com.app.fr.fruiteefy.user_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.app.fr.fruiteefy.Chat.MessageActivity;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PrefManager;
import com.app.fr.fruiteefy.user_client.Login.LoginView.LoginActivity;
import com.app.fr.fruiteefy.user_client.home.AllProductPojo;
import com.app.fr.fruiteefy.user_client.home.GardenProductActivity;
import com.app.fr.fruiteefy.user_client.home.SeetheGardenActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class homeGardensearchAdapter extends RecyclerView.Adapter<homeGardensearchAdapter.MyViewHolder> {
    ArrayList<AllProductPojo> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;


    public homeGardensearchAdapter(ArrayList<AllProductPojo> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public homeGardensearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gardensearchhome, parent, false);

        return new homeGardensearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeGardensearchAdapter.MyViewHolder holder, int position) {
        final AllProductPojo items=allProductPojos.get(position);
        requestQueue= Volley.newRequestQueue(mctx);

        if(items.getUserid().equals(PrefManager.getUserId(mctx))){
            holder.sendmessage.setVisibility(View.INVISIBLE);
        }

        holder.title.setText(items.getFirstname().concat(" "+items.getLastname()));
        holder.desc.setText(items.getAbout());
        holder.km.setText(items.getKilometer()+" km");
        holder.type.setText(items.getReviewstatus());
        holder.ratingBar.setRating(Float.parseFloat(items.getRating()));
        holder.productavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mctx, GardenProductActivity.class);
                intent.putExtra("userid",items.getUserid());
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

        Picasso.with(mctx).load(items.getGardenimg()).into(holder.img);

    
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
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
            sendmessage= (TextView) view.findViewById(R.id.sendmessage);
            productavailable = (TextView) view.findViewById(R.id.productavailable);
            seethegarden = (TextView) view.findViewById(R.id.seethegarden);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            type = (TextView) view.findViewById(R.id.type);
        }
    }



    public void setFilter(List<AllProductPojo> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }
}

