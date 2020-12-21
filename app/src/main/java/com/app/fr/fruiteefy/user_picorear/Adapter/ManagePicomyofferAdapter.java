package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.app.fr.fruiteefy.R;
import com.app.fr.fruiteefy.Util.PositionInterface;
import com.app.fr.fruiteefy.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ManagePicomyofferAdapter extends RecyclerView.Adapter<ManagePicomyofferAdapter.MyViewHolder> {
    ArrayList<Product> allProductPojos;
    Context mctx;
    RequestQueue requestQueue;
    private PositionInterface positionInterface;

    public ManagePicomyofferAdapter(ArrayList<Product> allProductPojos, Context mctx, PositionInterface positionInterface) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
        this.positionInterface = positionInterface;
    }

    @NonNull
    @Override
    public ManagePicomyofferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_managepicomyoffer, parent, false);

        return new ManagePicomyofferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagePicomyofferAdapter.MyViewHolder holder, int position) {
        final Product items = allProductPojos.get(position);

        Picasso.with(mctx).load(items.getProductimg()).into(holder.img);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionInterface.onClickWithFlag(position,2);



            }
        });

        // holder.offerno.setText(" "+items.getOfferid());
        holder.offertype.setText(" " + items.getProductname());
        // holder.status.setText(" "+items.getStatus());
        holder.location.setText(" " + items.getDesc());
        holder.price.setText(" " + items.getPrice());
        //  holder.quantity.setText(" "+items.getStock());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positionInterface.onClickWithFlag(position,1);



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
        LinearLayout btn;
        TextView texttitle, offertype, location, quantity, price, delete, edit;
        Button action;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            texttitle = (TextView) view.findViewById(R.id.texttitle);
            //  offerno = (TextView) view.findViewById(R.id.offerno);
            offertype = (TextView) view.findViewById(R.id.offertype);
            //  status = (TextView) view.findViewById(R.id.status);
            delete = (TextView) view.findViewById(R.id.delete);
            edit = (TextView) view.findViewById(R.id.edit);

            location = (TextView) view.findViewById(R.id.location);
            //  quantity = (TextView) view.findViewById(R.id.quantity);
            price = (TextView) view.findViewById(R.id.price);
            btn = (LinearLayout) view.findViewById(R.id.btn);


        }
    }

    public void setFilter(List<Product> countryModels) {
        allProductPojos = new ArrayList<>();
        allProductPojos.addAll(countryModels);
        notifyDataSetChanged();
    }

}

