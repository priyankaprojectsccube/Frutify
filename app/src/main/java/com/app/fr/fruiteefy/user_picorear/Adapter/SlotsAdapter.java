package com.app.fr.fruiteefy.user_picorear.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fr.fruiteefy.R;

import java.util.List;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.MyViewHolder> {
    List<String> allProductPojos;
    Context mctx;



    public SlotsAdapter(List<String> allProductPojos, Context mctx) {
        this.allProductPojos = allProductPojos;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public SlotsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_slot, parent, false);

        return new SlotsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsAdapter.MyViewHolder holder, int position) {




        holder.textslot.setText(allProductPojos.get(position));


    }

    @Override
    public int getItemCount() {
        Log.d("sdsdsd", String.valueOf(allProductPojos.size()));
        return allProductPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textslot;


        public MyViewHolder(View view) {
            super(view);

            textslot = (TextView) view.findViewById(R.id.textslot);




        }
    }


}

